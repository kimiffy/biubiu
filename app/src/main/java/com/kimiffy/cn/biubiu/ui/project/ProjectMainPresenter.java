package com.kimiffy.cn.biubiu.ui.project;

import com.kimiffy.cn.biubiu.base.BaseBean;
import com.kimiffy.cn.biubiu.base.BasePresenter;
import com.kimiffy.cn.biubiu.bean.ProjectTitleBean;
import com.kimiffy.cn.biubiu.constant.Key;
import com.kimiffy.cn.biubiu.http.callback.BaseObserver;
import com.kimiffy.cn.biubiu.http.exception.ErrorType;
import com.kimiffy.cn.biubiu.utils.GsonUtil;
import com.kimiffy.cn.biubiu.utils.SpUtil;

import java.util.List;

/**
 * Description:项目控制层
 * Created by kimiffy on 2019/5/17.
 */

public class ProjectMainPresenter extends BasePresenter<ProjectMainContract.View> implements ProjectMainContract.Presenter {

    ProjectMainPresenter(ProjectMainContract.View view) {
        mView = view;
    }

    @Override
    public void getProjectTitleList() {
        addDisposable(mApiService.getProjectTitleList(), new BaseObserver<BaseBean<List<ProjectTitleBean>>>() {
            @Override
            public void onSuccess(BaseBean<List<ProjectTitleBean>> bean) {
                List<ProjectTitleBean> data = bean.data;
                String projectTabList = GsonUtil.toJson(data);
                SpUtil.putString(Key.PREF_PROJECT_TAB_LIST, projectTabList);
                mView.getTitleListSuccess(data);
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                mView.getTitleListFail(msg);
                String json = SpUtil.getString(Key.PREF_PROJECT_TAB_LIST, "");
                List<ProjectTitleBean> list = GsonUtil.toList(json, ProjectTitleBean.class);
                if (null != list && !list.isEmpty()) {
                    mView.getTitleListSuccess(list);
                } else {
                    mView.getTitleListFail(msg);
                }
            }
        });

    }
}
