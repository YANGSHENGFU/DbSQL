package com.dbsql.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by LY on 2018/5/3.
 */

public abstract class Upgrade {
    public abstract void update(SQLiteDatabase db);
}
