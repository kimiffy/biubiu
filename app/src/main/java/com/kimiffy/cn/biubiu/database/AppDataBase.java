package com.kimiffy.cn.biubiu.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Description:DBFlow 创建数据库, 数据库版本升级,具体表结构的修改 需要在这里操作
 * Created by kimiffy on 2019/3/8.
 */
@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
public class AppDataBase {
    public static final String NAME = "BiuBiu";//数据库名
    public static final int VERSION = 1;//数据库版本号

}
