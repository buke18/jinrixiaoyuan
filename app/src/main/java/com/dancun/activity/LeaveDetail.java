package com.dancun.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.dancun.R;
import com.dancun.adapt.LeaveDetailAdapt;
import com.dancun.entity.BaseEntity;
import com.dancun.utils.SQLiteUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LeaveDetail extends AppCompatActivity {
    private TextView tvLeaveDate,tvDates;
    private BaseEntity baseEntity ;
    private ListView lv_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<BaseEntity> list = new SQLiteUtils(this,"leave",null,SQLiteUtils.version).getAll();
        baseEntity = (BaseEntity) getIntent().getExtras().getSerializable("baseEntity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_detail);
        lv_detail = findViewById(R.id.lv_detail);
        LeaveDetailAdapt lda=new LeaveDetailAdapt(this,list);
        lv_detail.setAdapter(lda);

    }
    public void leave(View v){
        Intent intent = new Intent(LeaveDetail.this,LeaveActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("baseEntity",baseEntity);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        List<BaseEntity> list = new SQLiteUtils(this,"leave",null,SQLiteUtils.version).getAll();
        LeaveDetailAdapt lda=new LeaveDetailAdapt(this,list);
        lv_detail.setAdapter(lda);
    }
}