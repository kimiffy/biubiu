package com.kimiffy.cn.biubiu.ui.articledetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseMVPActivity;
import com.kimiffy.cn.biubiu.constant.Key;
import com.kimiffy.cn.biubiu.utils.StringUtil;
import com.kimiffy.cn.biubiu.utils.ToastUtil;
import com.kimiffy.cn.biubiu.utils.aop.FilterType;
import com.kimiffy.cn.biubiu.utils.aop.annotation.LoginFilter;
import com.kimiffy.cn.biubiu.utils.aop.annotation.SingleClick;
import com.kimiffy.cn.biubiu.utils.event.Event;
import com.kimiffy.cn.biubiu.utils.event.EventBusUtil;
import com.kimiffy.cn.biubiu.utils.event.EventCode;

import butterknife.BindView;

/**
 * Description:文章详情
 * Created by kimiffy on 2019/3/13.
 */
public class ArticleDetailActivity extends BaseMVPActivity<ArticleDetailPresenter> implements ArticleDetailContract.View {
    @BindView(R.id.fl_web)
    FrameLayout flWeb;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private String mLink;
    private AgentWeb mAgentWeb;
    private String mToolBarTitle;
    private boolean mIsCollect;
    private boolean mIsShowMenu;
    private int mId;
    private ImageView mIvCollect;
    private String mTitle;

    @Override
    protected ArticleDetailPresenter createPresenter() {
        return new ArticleDetailPresenter(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            mLink = bundle.getString(Key.BUNDLE_LINK);
            mToolBarTitle = bundle.getString(Key.BUNDLE_TOOLBAR_TITLE);
            mTitle = bundle.getString(Key.BUNDLE_TITLE);
            mIsCollect = bundle.getBoolean(Key.BUNDLE_COLLECT, false);
            mId = bundle.getInt(Key.BUNDLE_ID, -1);
            mIsShowMenu = bundle.getBoolean(Key.BUNDLE_SHOW_MENU, true);//默认显示menu
        }
    }

    @Override
    protected void initUI() {
        initWebView();
        initToolBar();
    }


    @Override
    protected void setListener() {

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_share:
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        CharSequence title = StringUtil.formatTitle(mTitle);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "【" + title + "】" + "\n" + mLink);
                        startActivity(Intent.createChooser(shareIntent, getString(R.string.share)));
                        break;
                }
                return true;
            }
        });
    }

    private void initWebView() {
        mAgentWeb = AgentWeb.with(this)
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .setAgentWebParent(flWeb, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .createAgentWeb()
                .ready()
                .go(mLink);
    }


    private void initToolBar() {
        mToolbar.setTitle(mToolBarTitle);
        setSupportActionBar(mToolbar);
        if (mIsShowMenu) {
            mToolbar.inflateMenu(R.menu.menu_toolbar_article_detail);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mIsShowMenu) {
            getMenuInflater().inflate(R.menu.menu_toolbar_article_detail, menu);
            initMenu(menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void initMenu(Menu menu) {
        final MenuItem item = menu.findItem(R.id.action_collect);
        final View view = item.getActionView();
        mIvCollect = view.findViewById(R.id.iv_action_collect);
        if (mIsCollect) {
            mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_collect));
        } else {
            mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_collect_normal));
        }
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuClick();
            }
        });
    }

    @LoginFilter(FilterType.JUMP)
    @SingleClick
    private void menuClick() {
        if (mIsCollect) {
            mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_collect_normal));
//          mIvCollect.startAnimation(AnimationUtils.loadAnimation(ArticleDetailActivity.this, R.anim.collect));
            mPresenter.collectClick(true, mId);
        } else {
            mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_collect));
            mIvCollect.startAnimation(AnimationUtils.loadAnimation(ArticleDetailActivity.this, R.anim.collect));
            mPresenter.collectClick(false, mId);
        }
    }


    @Override
    public void collectSuccess() {
        mIsCollect = true;
        EventBusUtil.post(new Event<>(EventCode.COLLECT_ARTICLE_SUCCESS, mId));
        ToastUtil.showToast(getString(R.string.collect_success));
    }

    @Override
    public void collectFail(String msg) {
        mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_collect_normal));
        ToastUtil.showToast(msg);

    }

    @Override
    public void unCollectSuccess() {
        mIsCollect = false;
        EventBusUtil.post(new Event<>(EventCode.CANCEL_COLLECT_ARTICLE_SUCCESS, mId));
    }

    @Override
    public void unCollectFail(String msg) {
        mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_collect));
        ToastUtil.showToast(msg);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean back = mAgentWeb.back();
            if (back) {
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.destroy();
    }

}
