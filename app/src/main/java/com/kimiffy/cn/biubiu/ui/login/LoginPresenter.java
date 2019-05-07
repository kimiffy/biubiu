package com.kimiffy.cn.biubiu.ui.login;

import com.kimiffy.cn.biubiu.base.BaseBean;
import com.kimiffy.cn.biubiu.base.BasePresenter;
import com.kimiffy.cn.biubiu.bean.UserBean;
import com.kimiffy.cn.biubiu.constant.Key;
import com.kimiffy.cn.biubiu.http.callback.BaseObserver;
import com.kimiffy.cn.biubiu.http.exception.ErrorType;
import com.kimiffy.cn.biubiu.utils.GsonUtil;
import com.kimiffy.cn.biubiu.utils.SpUtil;

/**
 * Description:登录控制层
 * Created by kimiffy on 2019/3/11.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    LoginPresenter(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void login(String name, String psw) {

        addDisposable(mApiService.login(name, psw), new BaseObserver<BaseBean<UserBean>>() {
            @Override
            public void onSuccess(BaseBean<UserBean> bean) {
                String userInfo = GsonUtil.toJson(bean);
                SpUtil.putString(Key.PREF_LOGIN_INFO, userInfo);
                mView.loginSuccess(bean.data);
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                mView.loginError(msg);
            }
        });

    }

    @Override
    public void loginOut() {
        SpUtil.putString(Key.PREF_LOGIN_INFO, "");
    }

}
