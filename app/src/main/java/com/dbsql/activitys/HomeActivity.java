package com.dbsql.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.dbsql.R;
import com.dbsql.db.DBHelper;

/**
 * Created by LY on 2018/4/18.
 */

public class HomeActivity  extends Activity implements View.OnClickListener{

    private TextView textView1 ;
    private TextView textView2 ;
    private TextView textView3 ;
    private TextView textView4 ;
    private TextView textView5 ;
    private TextView textView6 ;
    private TextView textView7 ;
    private TextView textView8 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        findView();
    }

    private void findView(){
        textView1 = findViewById(R.id.text_1);
        textView2 = findViewById(R.id.text_2);
        textView3 = findViewById(R.id.text_3);
        textView4 = findViewById(R.id.text_4);
        textView5 = findViewById(R.id.text_5);
        textView6 = findViewById(R.id.text_6);
        textView7 = findViewById(R.id.text_7);
        textView8 = findViewById(R.id.text_8);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);
        textView6.setOnClickListener(this);
        textView7.setOnClickListener(this);
        textView8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.text_1 :
                DBHelper.getInstance(this , DBHelper.mVersion );
                break;
            case R.id.text_2 :
                DBHelper.getInstance(this , DBHelper.mVersion ).addColumn();
                break;
            case R.id.text_3 :
                DBHelper.getInstance(this , DBHelper.mVersion).deleteColumn();
                break;
            case R.id.text_4 :
                DBHelper.getInstance(this , DBHelper.mVersion).resetTableName("aaaaaaa");
                break;
            case R.id.text_5 :
                DBHelper.getInstance(this , DBHelper.mVersion).createTable("bbbbbb");
                break;
            case R.id.text_6 :
                DBHelper.getInstance(this , DBHelper.mVersion).upDate("'T'" , "'A'" , "purcgase_price / 0 " , "  purcgase_price/0 is not null  " ); // 总结 null 参与数值运算 得到 null ; 非法的数值运算的到null 如 ** / 0 ;**代表任何值
                break;
            case R.id.text_7 :
                DBHelper.getInstance(this , DBHelper.mVersion).intoValues();
//                DBHelper.getInstance(this).selectData();
                DBHelper.getInstance(this, DBHelper.mVersion).getCount();
                DBHelper.getInstance(this, DBHelper.mVersion).getSUM();
                DBHelper.getInstance(this, DBHelper.mVersion).getAVG();
                DBHelper.getInstance(this, DBHelper.mVersion).getMAX();
                DBHelper.getInstance(this, DBHelper.mVersion).getMIN();
                DBHelper.getInstance(this, DBHelper.mVersion).getGroupBy();
                DBHelper.getInstance(this, DBHelper.mVersion).groupByHaving();
                DBHelper.getInstance(this,  DBHelper.mVersion ).orderBy();
                break;
            case R.id.text_8 :
//                DBHelper.getInstance(this).deleteData(" regist_date is null "); // regist_date = null 不报错，但是不起作用（总结：null可以参数比较运算，但是不起作用)
                break;
        }
    }
}
