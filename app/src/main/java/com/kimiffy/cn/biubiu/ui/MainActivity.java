package com.kimiffy.cn.biubiu.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseActivity;
import com.kimiffy.cn.biubiu.ui.home.HomeFragment;
import com.kimiffy.cn.biubiu.utils.BottomNavigationViewHelper;

import butterknife.BindView;

/**
 * Description:主页面
 * Created by kimiffy on 2019/4/16.
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    @BindView(R.id.bnv_bar)
    BottomNavigationView mBnvBar;
    private Fragment[] mFragments = new Fragment[5];
    private int lastIndex;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initFragments();
        //恢复数据
        if (null != savedInstanceState) {
            String indexString = savedInstanceState.getString("lastIndex");
            if (!TextUtils.isEmpty(indexString)) {
                lastIndex = Integer.parseInt(indexString);
            }
        }
    }

    /**
     * 初始化fragments
     */
    private void initFragments() {
        mFragments[0] = HomeFragment.newInstance();
        mFragments[1] = HomeFragment.newInstance();
        mFragments[2] = HomeFragment.newInstance();
        mFragments[3] = HomeFragment.newInstance();
        mFragments[4] = HomeFragment.newInstance();
    }

    @Override
    protected void initUI() {
        mBnvBar.setItemIconTintList(null);
        BottomNavigationViewHelper.disableShiftMode(mBnvBar);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBnvBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectFragment(0);
                        break;
                    case R.id.navigation_sys:
                        selectFragment(1);
                        break;
                    case R.id.navigation_we_chat:
                        selectFragment(2);
                        break;
                    case R.id.navigation_project:
                        selectFragment(3);
                        break;
                    case R.id.navigation_me:
                        selectFragment(4);
                        break;
                }
                return true;
            }
        });
        setSelectedItemId(lastIndex);
    }

    /**
     * 设置选中的tab
     *
     * @param lastIndex 上一次记录的下标
     */
    private void setSelectedItemId(int lastIndex) {
        switch (lastIndex) {
            case 0:
                mBnvBar.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                mBnvBar.setSelectedItemId(R.id.navigation_sys);
                break;
            case 2:
                mBnvBar.setSelectedItemId(R.id.navigation_we_chat);
                break;
            case 3:
                mBnvBar.setSelectedItemId(R.id.navigation_project);
                break;
            case 4:
                mBnvBar.setSelectedItemId(R.id.navigation_me);
                break;
        }
    }


    /**
     * 设置选中fragment
     *
     * @param index 下标
     */
    private void selectFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragments[index];
        Fragment lastFragment = mFragments[lastIndex];
        lastIndex = index;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.fl_content, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存数据
        outState.putString("lastIndex", lastIndex + "");

    }


}
