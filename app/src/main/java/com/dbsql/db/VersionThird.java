package com.dbsql.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by LY on 2018/5/3.
 */

@VersionCode(3)
public class VersionThird extends Upgrade{
    @Override
    public void update(SQLiteDatabase db) {
        String sql = "create table if not exsits Shop ( shop_id char(4) primary key , shop_name text )" ;
        db.execSQL(sql);
    }
}
