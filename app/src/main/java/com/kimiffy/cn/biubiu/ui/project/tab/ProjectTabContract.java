package com.kimiffy.cn.biubiu.ui.project.tab;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.bean.ProjectListBean;

/**
 * Description:项目tab 契约类
 * Created by kimiffy on 2019/5/17.
 */

public class ProjectTabContract {


    public interface Presenter extends IPresenter<ProjectTabContract.View> {

        void firstFresh(int id);

        void reFresh();

        void loadMore();

        void doCollect(int id,int position);

        void unCollect(int id,int position);

    }

    public interface View extends IBaseView {
        void getDataSuccess(ProjectListBean bean, boolean isRefresh);

        void getDataFail(String info);

        void collectSuccess(int position);

        void collectFail(int position, String msg);

        void unCollectSuccess(int position);

        void unCollectFail(int position, String msg);


    }

}
