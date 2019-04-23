package com.kimiffy.cn.biubiu.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.kimiffy.cn.biubiu.utils.event.BindEventBus;
import com.kimiffy.cn.biubiu.utils.event.Event;
import com.kimiffy.cn.biubiu.utils.event.EventBusUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:基类
 * Created by kimiffy on 2019/2/13.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {


    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        unbinder = ButterKnife.bind(this);
        initData(savedInstanceState);
        initStateView();
        initUI();
        initEventBus();
        setListener();
    }


    /**
     * 获取界面布局id
     *
     * @return 该页面的layout
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 数据初始化
     *
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 视图初始化
     *
     *
     */
    protected abstract void initUI();


    /**
     * 设置监听
     */
    protected void setListener() {

    }

    /**
     * 初始化状态布局
     */
    protected void initStateView() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 初始化EventBus
     */
    private void initEventBus() {
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtil.register(this);
        }
    }

    /**
     * 订阅事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    /**
     * 订阅粘性事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接受处理黏性事件
     *
     * @param event 事件
     */
    protected void receiveStickyEvent(Event event) {
    }

    /**
     * 接受处理事件
     *
     * @param event 事件
     */
    protected void receiveEvent(Event event) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtil.unregister(this);
        }
    }

    //---------------------------------------公共方法-----------------------------------------------

    /**
     * 页面跳转
     *
     * @param clz 要跳转的Activity
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 页面跳转
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    public void startActivity(Class<?> clz, Intent intent) {
        intent.setClass(this, clz);
        startActivity(intent);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz    要跳转的Activity
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 页面跳转并要求获取返回结果
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundle数据
     * @param requestCode requestCode
     */
    public void startActivityForResult(Class<?> clz, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


}
