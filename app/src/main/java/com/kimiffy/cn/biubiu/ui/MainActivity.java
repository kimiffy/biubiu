package com.kimiffy.cn.biubiu.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseActivity;
import com.kimiffy.cn.biubiu.ui.home.HomeFragment;
import com.kimiffy.cn.biubiu.utils.BottomNavigationViewHelper;
import com.kimiffy.cn.biubiu.utils.LogUtil;

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
                        switchFragment(0);
                        break;
                    case R.id.navigation_sys:
                        switchFragment(1);
                        break;
                    case R.id.navigation_we_chat:
                        switchFragment(2);
                        break;
                    case R.id.navigation_project:
                        switchFragment(3);
                        break;
                    case R.id.navigation_me:
                        switchFragment(4);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        setSelectedTab(lastIndex);
    }

    /**
     * 设置选中的tab
     *
     * @param lastIndex 上一次记录的下标
     */
    private void setSelectedTab(int lastIndex) {
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
            default:
                break;
        }
    }


    /**
     * 选择显示Fragment
     *
     * @param position 下标
     */
    private void switchFragment(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : mFragments) {
            if (null != fragment) {
                ft.hide(fragment);
            }
        }
        Fragment fragment = mFragments[position];
        if (null == fragment) {
            mFragments[position] = createFragment(position);
            ft.add(R.id.fl_content, mFragments[position]);
        } else {
            ft.show(fragment);
        }
        lastIndex = position;
        ft.commit();
    }

    /**
     * 实例化Fragment
     * @param position 下标
     * @return fragment
     */
    private Fragment createFragment(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=HomeFragment.newInstance();
                break;
            case 1:
                fragment=HomeFragment.newInstance();
                break;
            case 2:
                fragment=HomeFragment.newInstance();
                break;
            case 3:
                fragment=HomeFragment.newInstance();
                break;
            case 4:
                fragment=HomeFragment.newInstance();
                break;
        }
        return fragment;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //保存上一次显示的下标
        outState.putInt("lastIndex", lastIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        lastIndex = savedInstanceState.getInt("lastIndex");
        setSelectedTab(lastIndex);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragments = null;
    }
}
