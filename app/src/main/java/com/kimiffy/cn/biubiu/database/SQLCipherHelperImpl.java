package com.kimiffy.cn.biubiu.database;

import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.app.MyApplication;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sqlcipher.SQLCipherOpenHelper;
import com.raizlabs.android.dbflow.structure.database.DatabaseHelperListener;

/**
 * Description:加密工具类
 * Created by kimiffy on 2019/5/15.
 */

public class SQLCipherHelperImpl extends SQLCipherOpenHelper {

    public SQLCipherHelperImpl(DatabaseDefinition databaseDefinition, DatabaseHelperListener listener) {
        super(databaseDefinition, listener);
    }


    @Override
    protected String getCipherSecret() {
        return MyApplication.getInstance().getString(R.string.app_name);
    }
}
