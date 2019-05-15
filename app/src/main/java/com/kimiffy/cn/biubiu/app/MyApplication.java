package com.kimiffy.cn.biubiu.app;

import android.app.Application;
import android.os.Build;

import com.kimiffy.cn.biubiu.database.AppDataBase;
import com.kimiffy.cn.biubiu.database.SQLCipherHelperImpl;
import com.kimiffy.cn.biubiu.utils.AppUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseHelperListener;
import com.raizlabs.android.dbflow.structure.database.OpenHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Description:
 * Created by kimiffy on 2019/2/25.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        initDBFlow();
        Logger.addLogAdapter(new AndroidLogAdapter());
        AppUtils.syncIsDebug(getApplicationContext());
        hidePDialog();
    }


    /**
     * 数据库初始化
     */
    private void initDBFlow() {
        FlowConfig flowConfig = FlowConfig.builder(this)
                .addDatabaseConfig(new DatabaseConfig.Builder(AppDataBase.class)
                        .openHelper(new DatabaseConfig.OpenHelperCreator() {
                            @Override
                            public OpenHelper createHelper(DatabaseDefinition databaseDefinition, DatabaseHelperListener helperListener) {
                                return new SQLCipherHelperImpl(databaseDefinition, helperListener);
                            }
                        })
                        .build())
                .build();
        FlowManager.init(flowConfig);
    }


    /**
     * 解决安卓P 使用了系统隐藏方法 时的弹窗警告
     */
    private void hidePDialog(){
        if (Build.VERSION.SDK_INT < 28)return;
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static MyApplication getInstance() {
        return myApplication;
    }


}
