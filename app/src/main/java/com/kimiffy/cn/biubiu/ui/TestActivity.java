package com.kimiffy.cn.biubiu.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseActivity;
import com.kimiffy.cn.biubiu.constant.EventCode;
import com.kimiffy.cn.biubiu.database.model.User;
import com.kimiffy.cn.biubiu.database.model.User_Table;
import com.kimiffy.cn.biubiu.http.dowload.DownLoadListener;
import com.kimiffy.cn.biubiu.http.dowload.DownLoadManager;
import com.kimiffy.cn.biubiu.ui.articlelist.ArticleListActivity;
import com.kimiffy.cn.biubiu.utils.ToastUtil;
import com.kimiffy.cn.biubiu.utils.aop.FilterType;
import com.kimiffy.cn.biubiu.utils.aop.annotation.LoginFilter;
import com.kimiffy.cn.biubiu.utils.aop.annotation.NeedPermission;
import com.kimiffy.cn.biubiu.utils.event.BindEventBus;
import com.kimiffy.cn.biubiu.utils.event.Event;
import com.kimiffy.cn.biubiu.utils.event.EventBusUtil;
import com.kimiffy.cn.biubiu.utils.imageloader.ImageLoader;
import com.kimiffy.cn.biubiu.utils.imageloader.base.ScaleType;
import com.kimiffy.cn.biubiu.utils.imageloader.glide.CommonOption;
import com.kimiffy.cn.biubiu.utils.permission.Permission;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页面
 */
@BindEventBus//注册eventbus
public class TestActivity extends BaseActivity {
    private static final String TAG = "TestActivity";
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_article)
    Button btnArticle;
    @BindView(R.id.btn_permission)
    Button btnPermission;
    @BindView(R.id.btn_load_image)
    Button btnLoadImage;
    @BindView(R.id.btn_insert)
    Button btnInsert;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.btn_send_event)
    Button btnSendEvent;
    @BindView(R.id.btn_download)
    Button btnDownload;
    @BindView(R.id.tv_progress)
    TextView tvProgress;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initUI() {

    }

    @OnClick({R.id.btn_login, R.id.btn_article, R.id.btn_permission, R.id.btn_load_image, R.id.btn_insert, R.id.btn_delete, R.id.btn_send_event, R.id.btn_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_article:
                goArticleListPage();
                break;
            case R.id.btn_permission:
                takePhoto();
                break;
            case R.id.btn_load_image:
                loadImage();
                break;
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_send_event:
                sendEvent();
                break;
            case R.id.btn_download:
                downLoad();
                break;
        }
    }


    @NeedPermission({Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE})
    private void downLoad() {
        String url = "https://www.wanandroid.com/blogimgs/64542bbb-b0f6-4c0e-828f-e38eb3abdc00.apk";
        DownLoadManager.getInstance().downFile(url, new DownLoadListener() {

            @Override
            public void onStart(long totalSize) {

            }

            @Override
            public void onProgress(long totalSize, long downSize) {
                int progress = (int) (downSize * 100 / totalSize);
                if (null != tvProgress) {
                    tvProgress.setText(progress + "%" + downSize);
                }
            }

            @Override
            public void onSuccess(String path) {
                ToastUtil.showToast("下载成功，path=" + path);
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showToast("下载失败，msg=" + msg);
            }

            @Override
            public void onComplete() {

            }

        });
    }


    //订阅事件
    @Override
    public void onEventCome(Event event) {
        super.onEventCome(event);
        int code = event.getCode();
        if (code == EventCode.TEST_CODE) {
            String event1 = (String) event.getEvent();
            ToastUtil.showToast(event1);
        }
    }

    private void sendEvent() {
        EventBusUtil.post(new Event<>(EventCode.TEST_CODE, "我是测试事件"));
    }

    private void insert() {
        User user = new User();
        user.name = "小王";
        user.age = 25;
        user.insert();

        User user1 = search();
        if (null != user1) {
            tvUser.setText(user1.name + "  " + user1.age);
        }
    }

    private User search() {
        User user = SQLite.select()
                .from(User.class)
                .where(User_Table.name.is("小王"))
                .querySingle();
        return user;
    }

    private void delete() {
        User user = search();
        if (null != user) {
            user.delete();
        }

        User user1 = search();
        if (null == user1) {
            tvUser.setText("");
        }
    }


    private void loadImage() {
        CommonOption commonOption = CommonOption.builder()
                .load(R.mipmap.ic_launcher)
                .isCircleImage(true)
                .scaleType(ScaleType.CENTER_INSIDE)
                .into(iv)
                .build();
        ImageLoader.getInstance().loadImage(this, commonOption);
    }

    //检查权限
    @NeedPermission({Permission.CAMERA})
    private void takePhoto() {
        ToastUtil.showToast("拍照啦!!");
    }

    //统一管理登录
    @LoginFilter(FilterType.JUMP)
    private void goArticleListPage() {
        startActivity(ArticleListActivity.class);
    }

    private void login() {
        startActivity(WebActivity.class);
    }

}
