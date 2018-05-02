package com.dbsql.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by LY on 2018/5/3.
 */
@VersionCode(2)
public class VersionSecond extends Upgrade {
    @Override
    public void update(SQLiteDatabase db) {
        String sql = "alter table "+DBOpenHelper.tableName + " add column salesperson char(10) " ;
        db.execSQL(sql);
    }
}
