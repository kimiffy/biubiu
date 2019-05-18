package com.kimiffy.cn.biubiu.ui.project;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.bean.ProjectTitleBean;

import java.util.List;

/**
 * Description:项目契约类
 * Created by kimiffy on 2019/5/17.
 */

public class ProjectMainContract {

    public interface Presenter extends IPresenter<ProjectMainContract.View> {

        void getProjectTitleList();
    }

    public interface View extends IBaseView {

        void getTitleListSuccess(List<ProjectTitleBean> data);

        void getTitleListFail(String info);

    }
}
