package com.kimiffy.cn.biubiu.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseMVPActivity;
import com.kimiffy.cn.biubiu.bean.UserBean;
import com.kimiffy.cn.biubiu.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:登录
 * Created by kimiffy on 2019/3/6.
 */

public class LoginActivity extends BaseMVPActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_login_out)
    Button btnLoginOut;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initUI() {

    }

    @OnClick({R.id.btn_login, R.id.btn_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mPresenter.login("kimiffy", "123456");
                break;
            case R.id.btn_login_out:
                mPresenter.loginOut();
                break;
        }
    }

    @Override
    public void loginSuccess(UserBean bean) {
        ToastUtil.showToast("登录成功!");
    }

    @Override
    public void loginError(String errorMsg) {
        ToastUtil.showToast(errorMsg);
    }





}
