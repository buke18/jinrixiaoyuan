package com.dancun.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dancun.R;
import com.dancun.databinding.FragmentLeaveBinding;
import com.dancun.databinding.FragmentQuickMarkBinding;
import com.dancun.entity.BaseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuickMarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuickMarkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentQuickMarkBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  BaseEntity baseEntity;

    public QuickMarkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuickMarkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuickMarkFragment newInstance(String param1, String param2) {
        QuickMarkFragment fragment = new QuickMarkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        new TimeThread().start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        baseEntity = (BaseEntity) bundle.getSerializable("baseEntity");
        binding = FragmentQuickMarkBinding.inflate(inflater, container, false);
        if(baseEntity.getAnnul()!=null && !baseEntity.getAnnul().equals("")){
            binding.tvIsleave.setText("已完成");
            binding.llTop.setBackgroundResource(R.mipmap.top_03_01);
            binding.gif.setBackgroundResource(R.mipmap.sc_01);
        }
        return binding.getRoot();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    binding.shapetime.setText("当前时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    binding.marktime.setText("当前时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    break;
            }
            return false;
        }
    });

    public class TimeThread extends Thread {
        @Override
        public void run() {
            while (true) {
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
}
