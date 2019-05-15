package com.kimiffy.cn.biubiu.database.model;

import com.kimiffy.cn.biubiu.database.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Description:登录信息
 * Created by kimiffy on 2019/5/15.
 */
@Table(database = AppDataBase.class)
public class LoginInfo extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public int id;
    @Column
    public String email;
    @Column
    public String icon;
    @Column
    public String password;
    @Column
    public int type;
    @Column
    public String username;
    @Column
    public String collectIds;
    @Column
    public String chapterTops;
}
