package com.dbsql.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;

/**
 * Created by LY on 2018/4/22.
 */

public class DBHelper {
    private String batabaseName = "message.db";
    private String batabaseNameTo = "messageTo.db";
    public static String tableName = "message" ;
    private DBOpenHelper dbOpenHelper ;
    private static  DBHelper dbHelper;
    private int mNewVersion = 1 ;
    private Context mContext ;
    /**
     * 获取本类实例
     * @param context
     * @return
     */
    public static DBHelper getInstance(Context context){

        if(dbHelper == null ){
           synchronized (DBHelper.class){
              if(dbHelper == null){
                  dbHelper = new DBHelper(context);
              }
           }
        }
        return dbHelper ;
    }

    private DBHelper(Context context ){
        mContext = context ;
        dbOpenHelper = new DBOpenHelper(context ,  getDBPath(context , batabaseName ) , null , mNewVersion );
    }

    /**
     * 设置最新的数据库版本号
     * @param newVersion
     */
    public void setNewVersion( int newVersion ){
        if(newVersion <= mNewVersion){
            return;
        }
        dbOpenHelper = new DBOpenHelper( mContext ,  getDBPath( mContext , batabaseName ) , null , mNewVersion );
    }



    /**
     * 获取保存数据库的路径
     * @param context
     * @return
     */
    private String getDBPath( Context context , String DBName ){
        File file = context.getExternalCacheDir();
        Log.d("TAG" ,file.getParent() + "/baseData/"+ DBName);
        return file.getParent() + "/baseData/"+ DBName;
    }


    /**
     * 创建表
     */
    public void createTable(String tableName){
        if(true){
            return;
        }
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String createTable = "create table if not exists "+ tableName + "( _id int primary key , type int not null , content text default '' , time int check(time > 20)  )";
        db.execSQL(createTable);
    }

    /**
     * 修改表名
     * 经过测试，修改表名，新的表名不能和旧的表名一样
     * 旧的表名一定要存在。所以修改表明一定要注意，防止修改的表不存在，和重复修改（新表明和旧表名一样）
     */
    public void resetTableName( String newTableName){
        if(TextUtils.isEmpty(newTableName) || newTableName.equals(tableName)){ //旧表名与新表名一样
            return ;
        }
        if(true){ // 还要防止旧表名不存在的情况
            return;
        }
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase() ;
        String sql = "alter table "+ tableName + " rename to "+ newTableName ;
        db.execSQL(sql);
    }

    /**
     * 1.不能添加已存在的字段
     * 2.column 关键字可以不用写
     * 3 SQLite 不支持一次增加多列
     * 添加字段
     */
    public void addColumn(){
        if(true){
            return;
        }
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "alter table "+ tableName + " add  address int  ";
        db.execSQL(sql);
    }

    /**
     * 删除字段
     * 发现怎样都报错，应该不支持删除列的操作
     */
    public void deleteColumn(){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "alter table "+ tableName + " delete column regist_date" ;
        db.execSQL(sql);
        db.insert("" , "" , null ) ;
        db.delete("" , "" , new String[]{""});
        db.query(tableName , new String[]{"product_id" , "product_type" , "product_name" , "sale_price" , "purcgase_price" , "regist_date"  } , "sale_price >=?" , new String[]{"100"} ,"product_type"  , null , "asc");
    }


    /**
     * 修改列
     * "alter table "+ tableName + " alter column address text" ; // int -> text
     * SQLite不支持这样的修改
     */
    public void restColumn(){
        if(true){
            return;
        }
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "alter table "+ tableName + " alter column address text" ;  //
        db.execSQL(sql);
    }

    /**
     * 删除表
     */
    public void dropTable(){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        String sql = "drop table bbbbbb";
        db.execSQL(sql);
    }

    /**
     * 插入值
     * 1.每次插入一行
     * 2.列名和值用逗号分开，分别放在（）内，这种形式成为清单（列清单和值清单）
     * 例：insert into tableName ( column1 , column2, column3 , column4 ,  ... ) values ( value1 , value2 , value3 , value4 , ...) ;
     * 3.插入表中所有的值是，表名后面的列清单可以省略不写 ， values 还是要写的
     * 例：insert into tableName  values ( value1 , value2 , value3 , value4 ) ; 前提：列值清单中的数要与表中的列数一样 ， 即没一列都要值
     * 4.插入null 值是，需要在值清单中 显示写出null
     * 例：insert into tableName ( column1 , column2, column3 , column4 ,  ... ) values ( value1 , value2 , null , value4 , ...) ;
     * 5.插入默认值可以通过两种方法实现，一：在values值清单中显示的使用default关键字表示该列值插入该列的默认值；二：在列清单中该列不写出来。
     * 例：insert into tableName ( column1 , column2, column3 , column4 ,  ... ) values ( value1 , value2 , default , value4 , ...) ;
     * 6.使用insert select 向一个表中插入另一个表的值。（复制数据的效果）
     * 例：insert into tableName1 (column1 , column2, column3 , column4 ,  ... ) select column1 , column2, column3 , column4 ,  ...  from tableName2 where ? ;
     *  把 tableName2 中的数据复制到 tableName1 中 ，前提： tabelNmae1 和 tabelName2 的列的属性也一样的， 列名可以不一样。
     */
    public void intoValues(){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql1 = "insert into " + DBOpenHelper.tableName + "  values( '001'  , 'T恤衫' , '衣服' , 1000 , 500 , '2009-09-20' )" ;
        db.execSQL(sql1);
        String sql2 = "insert into " + DBOpenHelper.tableName + "  values( '002'  , '打孔器' , '办公用品' , 500 , 320 , '2009-09-11' )" ;
        db.execSQL(sql2);
        String sql3 = "insert into " + DBOpenHelper.tableName + "  values( '003'  , '运动T恤' , '衣服' , 4000 , 2800 , null )" ;
        db.execSQL(sql3);
        String sql4 = "insert into " + DBOpenHelper.tableName + "  values( '004'  , '菜刀' , '厨房餐具' , 3000 , null , '2009-09-20' )" ;
        db.execSQL(sql4);
        String sql5 = "insert into " + DBOpenHelper.tableName + "  values( '005'  , '高压锅' , '厨房餐具' , 6800 , 2800 , '2009-01-15' )" ;
        db.execSQL(sql5);
        String sql6 = "insert into " + DBOpenHelper.tableName + "  values( '006'  , '叉子' , '厨房餐具' , 500 , 5000 , '2009-09-20' )" ;
        db.execSQL(sql6);
        String sql7 = "insert into " + DBOpenHelper.tableName + "  values( '007'  , '擦菜板' , '厨房餐具' , 880 , null , '2009-04-28' )" ;
        db.execSQL(sql7);
        String sql8 = "insert into " + DBOpenHelper.tableName + "  values( '008'  , '圆珠笔' , '办公用品' , 100 , 790 , '2009-11-11' )" ;
        db.execSQL(sql8);
    }

    /**
     * 删除数据 ， 删除数据需要指定：删除哪张表中的数据(即from )，删除的条件是什么(where 即什么样的行被删除）
     * delete from tableName where ?  where 为true  的行被删除
     * 注： deleta from tableName ; 语句中如果忘记写了 from 或是 多了列名都会出错
     * 例： delete tableName ;  因为 delete 删除的对象是 行  而不是表 ( drop tableName) ;
     * 例： delete from tableName column1 ; 因为delete删除的对象是行而不是列
     * 注：没有条件就是全部删除
     */
    public void deleteData(String where){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "delete from "+DBOpenHelper.tableName+ " where " + where ;
        db.execSQL(sql);
    }

    /**
     * 数据更新 更新数据需要指出：更新哪一张表中的数据？(即from )。更新哪些列的值，值是什么？更新的条件是什么？（不指明条件就更新表中所有行）
     * 例： update tableName set column1 = values1 , column2 = values12 ;  更新所有行
     * 例： update tableName set column1 = values1 , column2 = values12  where ? ;  where为true的行更新
     */
    public void upDate( String... s){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "update "+DBOpenHelper.tableName+ " set product_name = " + s[0] + " , product_type = "+ s[1] + " , purcgase_price = "+ s[2] +" where "+s[3];
        db.execSQL(sql);
    }


    /**
     * 选择语句
     * 1 ：select * from tableName ; 选择所有列，选出来列的顺序和表中的声明的顺序一样
     * 2 ：select column1 , column2 , column3 from ; 选出部分列，选出来的结果列的顺序与列清单写的顺序一致。
     * 3 ：select 语句中可以为列设置别名。例： select column1 AS "第一列的别名（随便写） , column2 AS "第二列的别名（随便写）" ; 注： 别名一定要用 ""  括起来 。使用了别名后，在查询出来的结果集中就就不是列名了，而是别名
     * 4 ：对选出来的结果集去重，distinct 关键字的使用。例：select distinct column1 from tableName ;
     * 注：distinct去重（重是对列的值而言的），即使用了distinct 的列的值存在重复值时，查询出来的就只有一个（行），这列重复值的其他行就没有被选出来。
     * 注：distinct 也可以用多列，当写了多列时，只有在写出的所有列的值同时重复值才成立。例：表中有两行是 column1 ="12"   column2 = "22" ， 只有column1 和column2 值都等了 distinct 才成立
     * 注：distinct 只能写在 列名的前面 ，并且只能写在第一列的前面。若写为 ：select column1 ,distinct column2  from tableName ; 会报错，应写为： select distinct column1 ,column2 from tableName ;
     * 注：当使用distinct关键字时，表中有值重复的两行，那查询出来的是那一行呢？仔细想一下其实那一行都没有关系，因为使用了distinct 关键字后 ，只有所有的写出来的所有的列的值都重复时才会成立，选取出来的集中也只有那写出来的那些列。
     * 注：distinct 关键字对null值算一种特殊的值，当某列多行值是null, 算一个。（行）
     * 注：select 语句选出来结果的顺序（即行的顺序与表中行的顺序可能不一致的，且每次执行结果可能不一样）行顺序与列顺序不是同一个概念
     */
    public void selectData(){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "select distinct product_type , regist_date from "+ DBOpenHelper.tableName ;
        Cursor cursor =  db.rawQuery(sql , null);

        if(cursor == null || cursor.getCount() <=0 ){
            Log.d("TAG" , "没有值");
            return;
        }
        int count = cursor.getCount() ;

        if(!cursor.moveToFirst()){ // 移到第一行，不加这句往往回报下标越界的异常
            return;
        }
        for( int i = 0 ; i < count ; i++ ){
            String s1 = cursor.getString(cursor.getColumnIndex("product_type"));
            String s2 = cursor.getString(cursor.getColumnIndex("regist_date"));
            if(i == 0 ){
                Log.d("TAG"+ i , "     product_type     ,   regist_date       ") ;
            }
                Log.d("TAG"+ i , "     " + s1 + "              ,          "+ s2 +   "      ") ;
            cursor.moveToNext();
        }
        cursor.close();
    }


    /**
     * 聚合函数 ： 汇总的函数称为聚合函数。所谓聚合，就是将多行汇总为一行。实际上，所有的聚合函数都是这样的，输入多行输出一行。
     * 聚合函数之count:
     * 注：通常，聚合函数会对null以外的对象进行汇总。但count聚合函数例外
     * 计算表中所有的行数值可以使用：select count(*) from tableName ; 这个值包含：null在内
     * 计算非null的行数可以使用：select count( columnNmae) from tableName ; 这个值是 columnName 列 值为not null 的总行数。
     * 注：总结：对于count聚合函数来说，计算列不同计算，返回的值可能不同。
     */
    public void getCount(){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "select count(regist_date) from "+ DBOpenHelper.tableName ; // 计算表中的所有行数
        Cursor cursor = db.rawQuery(sql , null);
        cursor.moveToFirst();
        Log.d("TAG" , "总行数 count = "+ cursor.getLong(0));

        String sql2 = "select count(distinct regist_date) from "+ DBOpenHelper.tableName ; // 是先删除重复的值在计算行数 值为;5 。注意执行顺序
        Cursor cursor2 = db.rawQuery(sql2 , null);
        cursor2.moveToFirst();
        Log.d("TAG" , "总行数 count = "+ cursor2.getLong(0));

    }

    /**
     * 聚合函数 ： 汇总的函数称为聚合函数。所谓聚合，就是将多行汇总为一行。实际上，所有的聚合函数都是这样的，输入多行输出一行。
     * 聚合函数之SUM ：
     * 注：SUM需要只能具体的列且是数值列，即不能使用*作为参数：例 select sum(ColumnName) from tableName ; 若写为： selcet sum(*) from tableName ; 会报错。
     */
    public void getSUM(){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "select sum(sale_price) from "+ DBOpenHelper.tableName ; // 计算表中的所有行数
        Cursor cursor = db.rawQuery(sql , null);
        cursor.moveToFirst();
        Log.d("TAG" , "sum = "+ cursor.getInt(0));
    }

    /**
     * 聚合函数 ： 汇总的函数称为聚合函数。所谓聚合，就是将多行汇总为一行。实际上，所有的聚合函数都是这样的，输入多行输出一行。
     * 聚合函数之ARG ：
     * 注：AVG需要只能具体的列且是数值列，即不能使用*作为参数：例 select avg(ColumnName) from tableName ; 若写为： selcet avg(*) from tableName ; 会报错。
     * 注：计算平均值时，若指定列有null值存在，这个unll值是不参数与计算的，如：一个表中的有8 行，columnName 数值列存在两个NULL值，那么计算平均值是 /6 而不是 /8 。
     */
    public void getAVG(){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "select avg(sale_price) from "+ DBOpenHelper.tableName ; // 计算表中的所有行数
        Cursor cursor = db.rawQuery(sql , null);
        cursor.moveToFirst();
        Log.d("TAG" , "avg = "+ cursor.getDouble(0));
    }

    /**
     * 聚合函数 ： 汇总的函数称为聚合函数。所谓聚合，就是将多行汇总为一行。实际上，所有的聚合函数都是这样的，输入多行输出一行。
     * 聚合函数之MAX ：
     * 注：MAX需要只能具体的列且是数值列，即不能使用*作为参数：例 select max(ColumnName) from tableName ; 若写为： selcet max(*) from tableName ; 会报错。
     */
    public void getMAX(){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "select max(sale_price) from "+ DBOpenHelper.tableName ; // 计算表中的所有行数
        Cursor cursor = db.rawQuery(sql , null);
        cursor.moveToFirst();
        Log.d("TAG" , "max = "+ cursor.getInt(0));
    }

    /**
     * 聚合函数 ： 汇总的函数称为聚合函数。所谓聚合，就是将多行汇总为一行。实际上，所有的聚合函数都是这样的，输入多行输出一行。
     * 聚合函数之MIN ：
     * 注：MIN需要只能具体的列且是数值列，即不能使用*作为参数：例 select min(ColumnName) from tableName ; 若写为： selcet min(*) from tableName ; 会报错。
     */
    public void getMIN(){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "select min(sale_price) from "+ DBOpenHelper.tableName ; // 计算表中的所有行数
        Cursor cursor = db.rawQuery(sql , null);
        cursor.moveToFirst();
        Log.d("TAG" , "min = "+ cursor.getInt(0));
    }

    /**
     * 对表进行分组 ：对表进行分组就是使用聚合函数Group by 函数以聚合键将表分成几组。就像分蛋糕一样，安人数将蛋糕切分。
     * 语句如：select column1 , column2 , column3 , column4 from tableName group by  column1 , column2 , column3 , column4;
     * 聚合键：在Group by 子句中的列称为聚合键 。上例：column1 , column2 , column3 , column4 都是聚合键。
     * 注：当作为聚合键的列的值存在null 时，group by 会将null 作为一种特殊的值（不确定值）来处理，和distinct 关键字一样。
     * 书写顺序：select -> from -> where -> group by ;  书写顺寻不能错乱，否则执行报错。
     * 执行顺寻：from -> where -> group by -> select : 即从指定tableName 中 先使用where 进行过滤 ，对过后的数据在进行 broup by 分组 ，最后在select 。
     * 注：使用group by常见的错误
     * 在select 中多写了 broup by 聚合键中之外的列也是可以的，SQLite3是个特例。使用了group by 后 select 中使能  （常数）,（聚合函数count sum avg max min ）, ((聚合键以及聚合键之外的列)
     * 分组后，一组一个(行)结果表示。一行代表一组。
     * 例1：在group by 中写了列的别名 如：selcet column ad "第一列的别名" from tableName group by 第一列的别名 ；
     * 出错的原因从执行的顺序来找，别名是写在 select 子局中定义的 ，而 group by 的执行顺序比 select 先执行，所以在执行到 group by 中时 出现了别名，这时并不h知道它是什么，所以报错。
     * 例2：认为使用了Group by 后查询出来的结果是有序的。在没有排序函数时，查询出来的结果都是无序的。
     * 例3：where 中使用聚合函数。（聚合函数只能出现在select havint order by 中）
     */
    public void getGroupBy(){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "select purcgase_price , count(*) from "+ DBOpenHelper.tableName +" where product_type = '衣服' group by purcgase_price" ; // 计算表中的所有行数
        Cursor cursor = db.rawQuery(sql , null);
        if(!cursor.moveToFirst()) {
            Log.d("TAG" , "null" );
            return;
        }
        int count = cursor.getCount() ;
        for( int i = 0 ; i < count ; i++ ){
            String s1 = cursor.getString(0);
            int s2 = cursor.getInt(1);
            if(i == 0 ){
                Log.d("TAG"+ i , "     purcgase_price     ,   count       ") ;
            }
            Log.d("TAG"+ i , "     " + s1 + "              ,          "+ s2 +   "      ") ;
            cursor.moveToNext();
        }
        cursor.close();
    }


    /**
     * 对分组后的过滤
     * where 自居用来指定数据行的条件，having子句用来指定分组的条件。
     * 问题：怎样选出分组后为两行的组？这时就可以使用having 了。
     * 书写顺序：select -> form -> where -> group by -> having -> order by
     * 执行顺序：from -> where -> groug by -> having -> select -> order by
     * 注：having 的组成 (常数) , (聚合函数) , (聚合键以及聚合键以外的键)
     */
    public void groupByHaving(){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "select product_type , count(*) from "+ DBOpenHelper.tableName +" group by product_type having count(*)=2" ; // 选出为两行的组
        String sq2 = "select product_type , avg(sale_price) from "+ DBOpenHelper.tableName +" group by product_type having avg(sale_price)>=2500" ; // 选出分组后sale_price>=2500的组
        Cursor cursor = db.rawQuery(sq2 , null);
        if(!cursor.moveToFirst()) {
            Log.d("TAG" , "null" );
            return;
        }
        int count = cursor.getCount() ;
        // sql1
//        for( int i = 0 ; i < count ; i++ ){
//            String s1 = cursor.getString(0);
//            int s2 = cursor.getInt(1);
//            if(i == 0 ){
//                Log.d("TAG"+ i , "     product_type     ,   count       ") ;
//            }
//            Log.d("TAG"+ i , "     " + s1 + "              ,          "+ s2 +   "      ") ;
//            cursor.moveToNext();
//        }

        // sql2
        for( int i = 0 ; i < count ; i++ ){
            String s1 = cursor.getString(0);
            double s2 = cursor.getDouble(1);
            if(i == 0 ){
                Log.d("TAG"+ i , "     product_type     ,   avg       ") ;
            }
            Log.d("TAG"+ i , "     " + s1 + "              ,          "+ s2 +   "      ") ;
            cursor.moveToNext();
        }
        cursor.close();
    }


    /**
     * 排序：asc (升序，默认的顺序), desc (降序)。
     * 书写顺序：select -> form -> where -> group by -> having -> order by。
     * 执行顺序：from -> where -> groug by -> having -> select -> order by。
     * 所以order by 中可以使用 列的别名 （但不能使用列的编号）。
     * 注：写在 order by 中的列称为排序键。
     * 注：语句中使用了order by 后 不写 asc 或desc 时，默认使用的是asc。
     * 注：排序键的值存在null时，在SQLite3中会把他当成0来处理。
     * 注：排序键可以使用任何键；
     * 注：order by 中可以使用聚合函数，别名，所有列。（不能使用编号)。
     * 注：排序键可以写多列，逗号隔开。优先级从左到有。
     */
    public void orderBy(){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.getReadableDatabase();
        String sql = "select product_id , product_name , sale_price , purcgase_price from "+ DBOpenHelper.tableName +" order by purcgase_price desc  , sale_price asc " ; // 选出为两行的组
        String sq2 = "select product_id , product_name , sale_price , purcgase_price , product_type from "+ DBOpenHelper.tableName +" group by product_type order by count(*) " ; // 行数的从下到大的排序
        Cursor cursor = db.rawQuery(sq2 , null);
        if(!cursor.moveToFirst()) {
            Log.d("TAG" , "null" );
            return;
        }
        int count = cursor.getCount() ;
        for( int i = 0 ; i < count ; i++ ){
            String s1 = cursor.getString(cursor.getColumnIndex("product_id"));
            String s2 = cursor.getString(cursor.getColumnIndex("product_name"));
            int s3 = cursor.getInt(cursor.getColumnIndex("sale_price"));
            int s4 = cursor.getInt(cursor.getColumnIndex("purcgase_price"));
            String s5 = cursor.getString(cursor.getColumnIndex("product_type"));
            if(i == 0 ){
                Log.d("TAG"+ i , "     product_id     ,   product_name     ,    sale_price    ,  purcgase_price  , purcgase_price ") ;
            }
            Log.d("TAG"+ i , "     " + s1 + "              ,          "+ s2 +   "      "  + s3 +   "      "  + s4 +   "      "+ s5) ;
            cursor.moveToNext();
        }
        cursor.close();
    }

}
