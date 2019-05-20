package com.kimiffy.cn.biubiu.ui.system;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.bean.SystemBean;

import java.util.List;

/**
 * Description:知识体系契约类
 * Created by kimiffy on 2019/5/20.
 */

public class SystemMainContract {


    public interface Presenter extends IPresenter<View> {

        void firstFresh();

        void refresh();
    }

    public interface View extends IBaseView {

        void getDataSuccess(List<SystemBean> dataBean);

        void getDataFail(String info);
    }
}
