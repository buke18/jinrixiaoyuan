package com.dancun.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dancun.R;
import com.dancun.entity.BaseEntity;
import com.dancun.utils.SQLiteUtils;
import com.dancun.utils.StatusBarUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {
    private BaseEntity baseEntity;
    private EditText sNEdit,editName;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteUtils sqlite=new SQLiteUtils(this,"leave",null,1);
        set();
        init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseEntity.setSchoolName(sNEdit.getText().toString());
                baseEntity.setName(editName.getText().toString());
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baseEntity",baseEntity);
                intent.replaceExtras(bundle);
                startActivity(intent);
                finish();
            }
        });


    }

    void init(){
        button =findViewById(R.id.btn_agree);
        sNEdit = findViewById(R.id.schoolName);
        editName = findViewById(R.id.name);
        SharedPreferences sharedPreferences = getSharedPreferences("baseEntity", 0);
        String conf = sharedPreferences.getString("baseEntity", "");
        Gson gson=new Gson();

        try{
            baseEntity = gson.fromJson(conf, new TypeToken<BaseEntity>(){}.getType());
            editName.setText(baseEntity.getName());
            sNEdit.setText(baseEntity.getSchoolName());
        }catch (Exception e){
            Log.d("tag", e.getStackTrace().toString());
            baseEntity=baseEntity==null?new BaseEntity():baseEntity;
        }

    }

    void set(){
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
    }


}