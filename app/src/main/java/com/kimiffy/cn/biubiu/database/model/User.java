package com.kimiffy.cn.biubiu.database.model;

import com.kimiffy.cn.biubiu.database.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Description:创建User表
 * 1.不要重写构造方法
 * 2.字段需要申明为public 如果使用的是private 需写对应的get set方法 否则会报错Error:Execution failed for task ':xxx:compileDebugJavaWithJavac'
 * Created by kimiffy on 2019/3/8.
 */
@Table(database = AppDataBase.class)
public class User extends BaseModel {

    @PrimaryKey(autoincrement = true)//主键
    public int id;

    @Column//字段
    public String name;

    @Column
    public int age;

}
