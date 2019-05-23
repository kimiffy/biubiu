package com.kimiffy.cn.biubiu.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * Description:闪屏页
 * Created by kimiffy on 2019/5/23.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 屏蔽物理返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
