package com.kimiffy.cn.biubiu.ui.system.detail.tab;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.bean.SystemDetailBean;

import java.util.List;

/**
 * Description:知识体系文章列表契约类
 * Created by kimiffy on 2019/5/20.
 */

public class SysTabContract {

    public interface Presenter extends IPresenter<SysTabContract.View> {

        void firstFresh(int id);

        void reFresh();

        void loadMore();

        void doCollect(int id, int position);

        void unCollect(int id, int position);

    }

    public interface View extends IBaseView {
        void getDataSuccess(List<SystemDetailBean.DatasBean> bean, boolean isRefresh);

        void getDataFail(String info);

        void stopRefresh();

        void noMoreData();

        void collectSuccess(int position);

        void collectFail(int position, String msg);

        void unCollectSuccess(int position);

        void unCollectFail(int position, String msg);



    }
}
