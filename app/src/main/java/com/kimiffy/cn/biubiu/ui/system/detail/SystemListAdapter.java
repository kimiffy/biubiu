package com.kimiffy.cn.biubiu.ui.system.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Description:体系分类列表
 * Created by kimiffy on 2019/5/3.
 */

public class SystemListAdapter extends FragmentPagerAdapter {


    private List<String> titleList;
    private List<Fragment> fragments;


    SystemListAdapter(FragmentManager fragmentManager, List<Fragment> fragments, List<String> titles) {
        super(fragmentManager);
        this.titleList=titles;
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        //空实现,注释父类的方法,不让销毁视图
    }
}
