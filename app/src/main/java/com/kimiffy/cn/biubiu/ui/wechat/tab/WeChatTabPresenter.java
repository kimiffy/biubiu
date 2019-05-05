package com.kimiffy.cn.biubiu.ui.wechat.tab;

import com.kimiffy.cn.biubiu.base.BasePresenter;

/**
 * Description:
 * Created by kimiffy on 2019/5/3.
 */

public class WeChatTabPresenter extends BasePresenter<WeChatTabContract.View> implements WeChatTabContract.Presenter{


    public WeChatTabPresenter(WeChatTabContract.View view) {
        mView=view;
    }

    @Override
    public void getData() {

    }
}
