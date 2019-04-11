package com.kimiffy.cn.biubiu.ui.login;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.bean.UserBean;

/**
 * Description:登录契约类
 * Created by kimiffy on 2019/3/11.
 */

public class LoginContract {

    public interface Presenter extends IPresenter<View> {
        void login(String name, String psw);
        void loginOut();
    }

    public interface View extends IBaseView {
        void loginSuccess(UserBean bean);

        void loginError(String errorInfo);
    }

}
