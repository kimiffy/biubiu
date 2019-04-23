package com.kimiffy.cn.biubiu.ui.articledetail;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseActivity;

import butterknife.BindView;

/**
 * Description:文章详情
 * Created by kimiffy on 2019/3/13.
 */

public class ArticleDetailActivity extends BaseActivity {
    @BindView(R.id.fl_web)
    FrameLayout flWeb;
    private String link;
    private AgentWeb mAgentWeb;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            link = bundle.getString("link");
        }
    }

    @Override
    protected void initUI() {
        //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
        //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
// 使用默认进度条
        mAgentWeb = AgentWeb.with(this)
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .setAgentWebParent(flWeb, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .createAgentWeb()
                .ready()
                .go(link);
        WebView mWebView = mAgentWeb.getWebCreator().getWebView();
        WebSettings mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);
        //不显示缩放按钮
        mSettings.setDisplayZoomControls(false);
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        mSettings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        mSettings.setLoadWithOverviewMode(true);
        //自适应屏幕
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.destroy();
    }
}
