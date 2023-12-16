package com.dancun.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dancun.R;
import com.dancun.entity.BaseEntity;
import com.dancun.utils.SQLiteUtils;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LeaveActivity extends AppCompatActivity {
    private TextView tvStartDate,tvStartTime,tvEndDate,tvEndTime;
    private BaseEntity baseEntity ;
    private EditText editCause,editCarbon,editCarbon2,editCarbon3,editLocal,editTel;
    private ImageView image_choose;
    private Switch isLeaveing;
    private String startTime="06:00";
    private String endTime="23:00";
    private String startDate=new SimpleDateFormat("MM-dd ").format(new Date());
    private String endDate=new SimpleDateFormat("MM-dd ").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        baseEntity = (BaseEntity) getIntent().getExtras().getSerializable("baseEntity");
        baseEntity.setAnnul(null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void initView() throws Exception{
        tvStartDate=findViewById(R.id.tv_edit_satrt_date);
        tvStartTime = findViewById(R.id.tv_satrt_time);
        tvEndTime = findViewById(R.id.tv_end_time);
        tvEndDate = findViewById(R.id.tv_edit_end_date);
        isLeaveing = findViewById(R.id.isleaveing);
        isLeaveing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    baseEntity.setExeat(1);
                else
                    baseEntity.setExeat(0);
            }
        });

        editCarbon = findViewById(R.id.edit_carbon);
        editCarbon.setText(baseEntity.getCarbon());

        editCarbon2 = findViewById(R.id.edit_carbon1);
        editCarbon2.setText(baseEntity.getCarbon1());

        editCarbon3 = findViewById(R.id.edit_carbon2);
        editCarbon3.setText(baseEntity.getCarbon2());

        editLocal = findViewById(R.id.edit_local);
        editLocal.setText(baseEntity.getLocal());

        editCause = findViewById(R.id.edit_cause);
        editCause.setText(baseEntity.getLeaveCause());

        editTel = findViewById(R.id.edit_tel);
        editTel.setText(baseEntity.getTel());

        image_choose = findViewById(R.id.addAssets);
        image_choose.setImageURI(Uri.parse(baseEntity.getImage()));
    }
    public void setDate(View v){
        Date date = new Date();
        new DatePickerDialog(LeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override//当选择某一个日期会执行 onDateSet这个函数
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month+=1;
                String date = String.format("%s-%s ",(month<10?""+month:""+month),(day<10?"0"+day:""+day));
                if (v.getId()==R.id.tv_edit_satrt_date) {
                    tvStartDate.setText(date + "  ▼");
                    startDate=date;
                }
                else{
                    tvEndDate.setText(date+"  ▼");
                    endDate=date;
                }
                Toast.makeText(LeaveActivity.this,date,Toast.LENGTH_SHORT).show();
            }
        },
                Integer.parseInt(new SimpleDateFormat("yyyy").format(date)),
                Integer.parseInt(new SimpleDateFormat("MM").format(date))-1,
                Integer.parseInt(new SimpleDateFormat("dd").format(date))
        ).show();
    }
    public void setTime(View v){
        new TimePickerDialog(LeaveActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                String time=String.format("%s:%s",(h<10?"0"+h:""+h),(m<10?"0"+m:""+m));
                if (v.getId()==R.id.tv_end_time){
                    tvEndTime.setText(time);
                   endTime = time;
                }else{
                    tvStartTime.setText(time);
                    startTime = time;
                }

            }
        },0,0,true).show();
    }
    public void onsub(View v) {
        baseEntity.setCarbon(editCarbon.getText().toString());
        baseEntity.setLeaveCause(editCause.getText().toString());
        baseEntity.setCarbon1(editCarbon2.getText().toString());
        baseEntity.setCarbon2(editCarbon3.getText().toString());
        baseEntity.setLeaveType("事假");
        baseEntity.setTel(editTel.getText().toString());
        baseEntity.setLocal(editLocal.getText().toString());
        baseEntity.setStartDate(startDate+startTime);
        baseEntity.setEndDate(endDate+endTime);
        Uri uri=null;
        try {
             uri= Uri.parse(baseEntity.getImage());
        }catch (Exception e){
            Log.d("log", e.getStackTrace().toString());
        }

        baseEntity.setImage(null);


        SharedPreferences sharedPreferences = getSharedPreferences("baseEntity",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        String conf =new Gson().toJson(baseEntity);
        new SQLiteUtils(this,"leave",null,SQLiteUtils.version).insert(baseEntity);
        List<BaseEntity> list = new SQLiteUtils(this,"leave",null,SQLiteUtils.version).getAll();
        edit.putString("baseEntity",conf);
        edit.commit();
        try {
            baseEntity.setImage(uri.toString());
        }catch (Exception e){
            Log.d("log", "onsub: "+e.getStackTrace());
        }

        Intent intent = new Intent(LeaveActivity.this,LeaveStateActivity.class);
        Bundle bundle =new Bundle();
        bundle.putSerializable("baseEntity",baseEntity);
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
                image_choose.setImageURI(data.getData());
                baseEntity.setImage(data.getData().toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}