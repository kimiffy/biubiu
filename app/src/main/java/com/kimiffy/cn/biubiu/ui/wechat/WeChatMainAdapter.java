package com.kimiffy.cn.biubiu.ui.wechat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Description:
 * Created by kimiffy on 2019/5/3.
 */

public class WeChatMainAdapter extends FragmentPagerAdapter {


    List<String> titleList;
    private List<Fragment> fragments;

    public WeChatMainAdapter(FragmentManager fm, List<Fragment> fragments,List<String> titleList) {
        super(fm);
        this.fragments = fragments;
        this.titleList=titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if(fragments==null){
            return 0;
        }else{
            return fragments.size();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
