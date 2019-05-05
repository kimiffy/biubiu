package com.kimiffy.cn.biubiu.ui.wechat.tab;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.bean.WxTitleBean;

import java.util.List;

/**
 * Description:
 * Created by kimiffy on 2019/5/3.
 */

public class WeChatTabContract  {

    public interface Presenter extends IPresenter<WeChatTabContract.View> {

        void getData();

    }

    public interface View extends IBaseView {
        void getDataSuccess(List<WxTitleBean> bean);

        void getDataFail(String info);

    }
}
