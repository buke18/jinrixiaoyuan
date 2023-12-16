package com.dancun.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dancun.entity.BaseEntity;
import com.dancun.fragment.QuickMarkFragment;
import com.dancun.R;
import com.dancun.fragment.LeaveFragment;

public class LeaveStateActivity extends AppCompatActivity {
    private BaseEntity baseEntity;
    private TextView leaveBtn, ewmBtn;
    private LeaveFragment leaveFragment = new LeaveFragment();
    private QuickMarkFragment quickMarkFragment = new QuickMarkFragment();
    private LinearLayout footer;
    private LinearLayout footer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_state);
        baseEntity = (BaseEntity) getIntent().getExtras().getSerializable("baseEntity");
        initView();
        setFragment(leaveFragment);

    }


    public void toDetail(View v) {
        switch (v.getId()){
            case R.id.tv_returndetal:
                Intent intent = new Intent(LeaveStateActivity.this, LeaveDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baseEntity",baseEntity);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case  R.id.leave_framg:
                footer.setVisibility(View.VISIBLE);
                setFragment(leaveFragment);
                leaveBtn.setBackgroundResource(R.drawable.shape_state_btn);
                leaveBtn.setTextColor(0xff0077ff);
                ewmBtn.setBackgroundColor(0xffffff);
                ewmBtn.setTextColor(0xff666666);
                break;
            default:
                footer.setVisibility(View.GONE);
                setFragment(quickMarkFragment);
                ewmBtn.setBackgroundResource(R.drawable.shape_state_btn);
                ewmBtn.setTextColor(0xff0077ff);
                leaveBtn.setBackgroundColor(0xffffff);
                leaveBtn.setTextColor(0xff666666);
                break;
        }

    }

    private void setFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("baseEntity",baseEntity);
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();//获取FragmentManager实例
        FragmentTransaction transaction = fm.beginTransaction();//获取FragmentTransaction实例
        transaction.replace(R.id.main_framg, fragment);//添加一个Fragment
        transaction.commit();//提交事务
    }
    private void initView(){
        leaveBtn=findViewById(R.id.leave_framg);
        ewmBtn=findViewById(R.id.ewm_framg);
        if(baseEntity.getAnnul()!=null && !baseEntity.getAnnul().equals(""))
            footer=findViewById(R.id.footer_01);
        else
            footer=findViewById(R.id.footer);

        footer.setVisibility(View.VISIBLE);

    }
    public void salesLeave(View v){
        Intent intent = new Intent(this,SalesLeaveActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("baseEntity",baseEntity);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}