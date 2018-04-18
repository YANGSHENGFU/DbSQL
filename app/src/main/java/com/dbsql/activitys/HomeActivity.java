package com.dbsql.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.dbsql.R;

/**
 * Created by LY on 2018/4/18.
 */

public class HomeActivity  extends Activity implements View.OnClickListener{

    private TextView textView1 ;
    private TextView textView2 ;
    private TextView textView3 ;
    private TextView textView4 ;
    private TextView textView5 ;

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
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.text_1 :
                break;
            case R.id.text_2 :
                break;
            case R.id.text_3 :
                break;
            case R.id.text_4 :
                break;
            case R.id.text_5 :
                break;
        }
    }
}
