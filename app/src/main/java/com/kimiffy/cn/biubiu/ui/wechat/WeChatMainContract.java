package com.kimiffy.cn.biubiu.ui.wechat;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.bean.WxTitleBean;

import java.util.List;

/**
 * Description: 公众号主页面契约
 * Created by kimiffy on 2019/5/3.
 */

public class WeChatMainContract {

    public interface Presenter extends IPresenter<WeChatMainContract.View> {

        void getWxList();

    }

    public interface View extends IBaseView {
        void getWxListSuccess(List<WxTitleBean> bean);

        void getWxListFail(String info);

    }

}
