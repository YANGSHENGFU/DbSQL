package com.dbsql.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.net.PortUnreachableException;

/**
 * Created by LY on 2018/4/21.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private Context mContext ;
    public static String batabaseName = "message.db";
    public static String tableName = "message" ;
    public static String product_name = "product_name";
    public static String product_type = "product_type";
    public static String sale_price = "sale_price";
    public static String purcgase_price = "purcgase_price";
    public static String regist_date = "regist_date";
    public static String sendname = "sendname";

    private final int INITIAL_VERSION = 1 ; // 初始版本号
    public static int NEW_VERSION = 0 ;       // 最新的版本号

    /**
     * 经测试发现version不能小于等于0 否则报错
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context ;
        NEW_VERSION = version ;
    }

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context ;
        NEW_VERSION = version ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table if not exists "+ tableName + "( product_id char(4) primary key , product_name varchar(100) not null , product_type varchar(32) not null , " +
                             "sale_price int , purcgase_price int , regist_date date )";
        db.execSQL(createTable);
        Log.d("TAG" , "数据库创建");
        onUpgrade( db , INITIAL_VERSION , NEW_VERSION );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db , int oldVersion , int newVersion ) {
        update(db, oldVersion, newVersion);
    }

    /**
     * 数据库版本递归更新
     * @param oldVersion 数据库当前版本号
     * @param newVersion 数据库升级后的版本号
     * @author lh
     * @retrun void
     */
    public void update(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("TAG" , "onUpgrade oldVersion = "+ oldVersion + "  newVersion = "+ newVersion);
        Upgrade upgrade = null;
        if (oldVersion < newVersion) {
            oldVersion++;
            upgrade = VersionFactory.getUpgrade(oldVersion);
            if (upgrade == null) {
                return;
            }
            upgrade.update(db);
            update(db, oldVersion, newVersion); // 递归调用
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        Log.d("TAG" , "onDowngrade oldVersion = "+ oldVersion + "  newVersion = "+ newVersion);
    }
}
