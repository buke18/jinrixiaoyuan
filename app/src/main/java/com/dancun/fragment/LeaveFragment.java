package com.dancun.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dancun.R;
import com.dancun.activity.LeaveDetail;
import com.dancun.activity.LeaveStateActivity;
import com.dancun.activity.MapActivity;
import com.dancun.databinding.FragmentLeaveBinding;
import com.dancun.entity.BaseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaveFragment extends Fragment {
    private BaseEntity baseEntity;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentLeaveBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LeaveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaveFragment newInstance(String param1, String param2) {
        LeaveFragment fragment = new LeaveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//

    }
    private void setValue() throws Exception{
        Bundle bundle = getArguments();
        baseEntity = (BaseEntity) bundle.getSerializable("baseEntity");
        binding.tvStartDate.setText("" + baseEntity.getStartDate());
        binding.tvEndDate.setText("" + baseEntity.getEndDate());
        binding.tvCause.setText("" + baseEntity.getLeaveCause());
        binding.tvCarbon.setText("" + baseEntity.getCarbon());
        binding.tvCarbon1.setText("[辅导员]" + baseEntity.getCarbon() + " - 审批");
        binding.tvLocal.setText("" + baseEntity.getLocal());
        String data=new SimpleDateFormat("MM-dd ").format(new Date());
        Integer time =Integer.parseInt(new SimpleDateFormat("hh").format(new Date()));
        binding.date1.setText(baseEntity.getStartDate());
        binding.date2.setText(data+(time-1)+":15");
        binding.date3.setText(data+(time-1)+":37");
        binding.date4.setText(data+(time-1)+":59");
        binding.tvTel.setText("" + baseEntity.getTel());
        binding.tvAlltime.setText(formatDate(baseEntity.getStartDate(),baseEntity.getEndDate()));
        if(baseEntity.getExeat()==0){
            binding.isleaveing.setText("非离校");
        }
        binding.tvLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaveFragment.this.getContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        if(baseEntity.getAnnul()!=null && !baseEntity.getAnnul().equals("")){
            String d = "共("+formatDate(baseEntity.getStartDate(),baseEntity.getAnnul())+")";
            binding.tvLeaveDate.setText(baseEntity.getStartDate()+" ~ " +baseEntity.getAnnul()+"    "+d);
            binding.tvAlltime.setText(formatDate(baseEntity.getStartDate(),baseEntity.getAnnul()));
            binding.tvIsleave.setText("已完成");
            binding.tvLeaveDate.setTextColor(0xffed9e34);
            binding.llTop.setBackgroundResource(R.mipmap.top_03_01);
            binding.gif.setBackgroundResource(R.mipmap.sc_01);
        }


        try {
            binding.tvName.setText("" + baseEntity.getName());
        }catch (Exception e){
            Toast.makeText(this.getContext(),e.getStackTrace().toString(),Toast.LENGTH_SHORT).show();
        }
        if(baseEntity.getCarbon()==null||baseEntity.getCarbon1()=="" || baseEntity.getCarbon1().equals("")){//只有一级审核
            binding.llOne.setVisibility(View.GONE);
            binding.llTwo.setVisibility(View.GONE);
            binding.level.setText("一级 : ");
        }else{
            if(baseEntity.getCarbon2()==null||baseEntity.getCarbon2()=="" || baseEntity.getCarbon2().equals("")){//只有二级审核
                binding.llTwo.setVisibility(View.GONE);
                binding.tvCarbon2.setText("[辅导员]" + baseEntity.getCarbon() + " - 审批");//把第一个框作为一级审核
                binding.level.setText("二级 : ");
                binding.tvCarbon1.setText(baseEntity.getCarbon1());
            }else{//三级审核

                binding.tvCarbon2.setText("[辅导员]" + baseEntity.getCarbon() + " - 审批");//把第一个框作为一级审核
                binding.tvCarbon3.setText(baseEntity.getCarbon1());
                binding.level.setText("三级 : ");
                binding.tvCarbon1.setText(baseEntity.getCarbon2());
            }
        }
        try{
           Uri uri = Uri.parse(baseEntity.getImage());
            binding.assets.setImageURI(uri);
        }catch (Exception e){
            e.printStackTrace();
            binding.assets.setImageURI(null);
        }


    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    binding.thistime.setText("当前时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    break;
            }
            return false;
        }
    });
    public class TimeThread extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentLeaveBinding.inflate(inflater,  container, false);
//        inflater.inflate(R.layout.fragment_leave, container, false)
        try {
            setValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new TimeThread().start();
        return binding.getRoot();
    }
    private String formatDate(String date1,String Date2){
        try {
            Date start = new SimpleDateFormat("MM-dd hh:mm").parse(date1);
            Date end = new SimpleDateFormat("MM-dd hh:mm").parse(Date2);
            int min = (int) ((new Date(end.getTime()-start.getTime())).getTime()/ 1000 / 60);
            int house =(min/60)%24;
            int day = min/60/24;
            min=min%60;
            return (day==0?"":day+"天 ")+house+"小时 "+min+"分钟";
        }catch (Exception e){
            return  "8小时20分钟";
        }
    }

}