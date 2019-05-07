package com.kimiffy.cn.biubiu.ui.wechat.tab;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.bean.WxArticleListBean;
import com.kimiffy.cn.biubiu.bean.WxTitleBean;

import java.util.List;

/**
 * Description:公众号文章列表契约类
 * Created by kimiffy on 2019/5/3.
 */

public class WeChatTabContract  {

    public interface Presenter extends IPresenter<WeChatTabContract.View> {

        void reFresh();

        void loadMore();

        void getData(int id,int page);

    }

    public interface View extends IBaseView {
        void getDataSuccess(WxArticleListBean bean, boolean isRefresh);

        void getDataFail(String info);

    }
}
