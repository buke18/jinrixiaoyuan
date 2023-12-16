package com.dancun.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GnssAntennaInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dancun.R;
import com.dancun.entity.BaseEntity;
import com.dancun.utils.SQLiteUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SalesLeaveActivity extends AppCompatActivity {
    LocationManager locMan;
    private EditText editLocal;
    private String provider;
    private BaseEntity baseEntity;
    private ImageView imageChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_leave);
        baseEntity = (BaseEntity) getIntent().getExtras().getSerializable("baseEntity");
        initView();
        initDate();

    }

    private void initView() {
        editLocal = findViewById(R.id.edit_local);
        imageChoose = findViewById(R.id.addAssets);
    }

    private void initDate() {
        editLocal.setText(baseEntity.getLocal());
        imageChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAssets(v);
            }
        });
    }
    public void sales(View v){
        new SQLiteUtils(this,"leave",null,SQLiteUtils.version).annulLeave(baseEntity.getId(),new SimpleDateFormat("MM-dd HH:mm").format(new Date()));
        baseEntity.setAnnul(new SimpleDateFormat("MM-dd HH:mm").format(new Date()));
        Bundle bundle = new Bundle();
        bundle.putSerializable("baseEntity",baseEntity);
        Intent intent = new Intent(this,LeaveStateActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    public void addAssets(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent, 0x1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                imageChoose.setImageURI(data.getData());
//                baseEntity.setImage(data.getData().toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        finish();
//    }


}