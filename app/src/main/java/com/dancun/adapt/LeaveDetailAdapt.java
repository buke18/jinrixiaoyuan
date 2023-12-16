package com.dancun.adapt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dancun.R;
import com.dancun.activity.LeaveDetail;
import com.dancun.activity.LeaveStateActivity;
import com.dancun.entity.BaseEntity;
import com.dancun.utils.SQLiteUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeaveDetailAdapt extends BaseAdapter {
    private List<BaseEntity> list = new ArrayList();
    private Context context;
    public  LeaveDetailAdapt(Context context, List<BaseEntity> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
                return list.size()-position-1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Leave leave;
        BaseEntity baseEntity = list.get(position);
        if (convertView==null){
            leave = new Leave();
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_leave_list,null);
            leave.tvDates = convertView.findViewById(R.id.tv_dates);
            leave. tvLeaveDate = convertView.findViewById(R.id.tv_leave_date);
            leave.ll_leave_detail = convertView.findViewById(R.id.ll_leave_detail);
            leave.isleaveing = convertView.findViewById(R.id.isleaveing);
            leave.tv_practical_date = convertView.findViewById(R.id.tv_practical_date);

            try {
                Date now=new Date();
                now=new SimpleDateFormat("MM-dd HH:mm").parse(new SimpleDateFormat("MM-dd HH:mm").format(now));
                Date end = new SimpleDateFormat("MM-dd hh:mm").parse(baseEntity.getEndDate());
                if(end.getTime() < now.getTime()&&(baseEntity.getAnnul()==null || baseEntity.getAnnul().equals(""))){
                    new SQLiteUtils(context,"leave",null,SQLiteUtils.version).annulLeave(baseEntity.getId(),baseEntity.getEndDate());
                    baseEntity.setAnnul(baseEntity.getEndDate());
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(baseEntity.getAnnul()!=null && !baseEntity.getAnnul().equals("")) {
                leave.isleaveing.setText("已完成");
                String date = formatDate(baseEntity.getStartDate(),baseEntity.getAnnul());
                leave.tv_practical_date.setText(baseEntity.getStartDate()+" ~ "+baseEntity.getAnnul()+"   "+date);
                leave.tv_practical_date.setTextColor(0xfffe9901);
            }
            leave.ll_leave_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leaveState(v,list.get(position));
                }
            });
            String date="";
            date=formatDate(baseEntity.getStartDate(),baseEntity.getEndDate());

            leave.tvDates.setText(baseEntity.getStartDate()+" 至 "+baseEntity.getEndDate()+"    "+date);
            leave.tvLeaveDate.setText(new SimpleDateFormat("MM-dd").format(new Date()));
            convertView.setTag(leave);
        }else{
            leave = (Leave) convertView.getTag();

        }
        return convertView;
    }
    private class Leave{
        TextView tvLeaveDate,tvDates,isleaveing,tv_practical_date;
        LinearLayout ll_leave_detail;
    }
    public void leaveState(View v,BaseEntity baseEntity){
        Intent intent = new Intent(context, LeaveStateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("baseEntity",baseEntity);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }
    private String formatDate(String date1,String Date2){
        try {
            Date start = new SimpleDateFormat("MM-dd hh:mm").parse(date1);
            Date end = new SimpleDateFormat("MM-dd hh:mm").parse(Date2);
            int min = (int) ((new Date(end.getTime()-start.getTime())).getTime()/ 1000 / 60);
            int house =(min/60)%24;
            int day = min/60/24;
            min=min%60;
            return "(共"+(day==0?"":day+"天 ")+house+"小时 "+min+"分钟)";
        }catch (Exception e){
            return  "共8小时20分";
        }
    }
}
