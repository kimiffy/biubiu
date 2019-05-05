package com.kimiffy.cn.biubiu.ui.wechat.tab;

import android.os.Bundle;

import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.LazyMVPFragment;
import com.kimiffy.cn.biubiu.bean.WxTitleBean;
import com.kimiffy.cn.biubiu.constant.Key;

import java.util.List;

/**
 * Description:公众号文章列表
 * Created by kimiffy on 2019/5/3.
 */

public class WeChatTabFragment extends LazyMVPFragment<WeChatTabPresenter> implements WeChatTabContract.View {


    private int id;

    public static WeChatTabFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(Key.WE_CHAT_TAB_FRAGMENT_ID, id);
        WeChatTabFragment fragment = new WeChatTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected WeChatTabPresenter createPresenter() {
        return new WeChatTabPresenter(this);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_we_chat_tab;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (null != arguments) {
            id = arguments.getInt(Key.WE_CHAT_TAB_FRAGMENT_ID, -1);
        }
    }

    @Override
    protected void initUI() {

    }


    @Override
    public void getDataSuccess(List<WxTitleBean> bean) {

    }

    @Override
    public void getDataFail(String info) {

    }

}
