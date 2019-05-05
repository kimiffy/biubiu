package com.kimiffy.cn.biubiu.ui.wechat;

import com.kimiffy.cn.biubiu.base.BaseBean;
import com.kimiffy.cn.biubiu.base.BasePresenter;
import com.kimiffy.cn.biubiu.bean.WxTitleBean;
import com.kimiffy.cn.biubiu.constant.Key;
import com.kimiffy.cn.biubiu.http.callback.BaseObserver;
import com.kimiffy.cn.biubiu.http.exception.ErrorType;
import com.kimiffy.cn.biubiu.utils.GsonUtil;
import com.kimiffy.cn.biubiu.utils.SpUtil;

import java.util.List;

/**
 * Description:公众号控制层
 * Created by kimiffy on 2019/5/3.
 */

public class WeChatMainPresenter extends BasePresenter<WeChatMainContract.View> implements WeChatMainContract.Presenter {

    WeChatMainPresenter(WeChatMainContract.View view) {
        mView = view;
    }

    @Override
    public void getWxList() {
        addDisposable(mApiService.getWXList(), new BaseObserver<BaseBean<List<WxTitleBean>>>() {

            @Override
            public void onSuccess(BaseBean<List<WxTitleBean>> bean) {
                List<WxTitleBean> data = bean.data;
                String WxTabList= GsonUtil.toJson(data);
                SpUtil.putString(Key.WX_TAB_LIST,WxTabList);
                mView.getWxListSuccess(data);

            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                String json = SpUtil.getString(Key.WX_TAB_LIST, "");
                List<WxTitleBean> list = GsonUtil.toList(json, WxTitleBean.class);
                if(null!=list){
                    mView.getWxListSuccess(list);
                }else{
                    mView.getWxListFail(msg);
                }


            }
        });
    }


}
