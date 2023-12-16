package com.dancun.entity;

import android.net.Uri;

import java.io.Serializable;




public class BaseEntity implements Serializable {
    private int id;
    private  String schoolName;
    private  String startDate;
    private  String endDate;
    private  String leaveType;
    private  String leaveCause;
    private  String carbon;
    private  String carbon1;
    private  String carbon2;
    private  String local;
    private  String tel;
    private  String name;
    private  String image;
    private String annul;
    private int exeat;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveCause() {
        return leaveCause;
    }

    public void setLeaveCause(String leaveCause) {
        this.leaveCause = leaveCause;
    }

    public String getCarbon() {
        return carbon;
    }

    public void setCarbon(String carbon) {
        this.carbon = carbon;
    }

    public String getCarbon1() {
        return carbon1;
    }

    public void setCarbon1(String carbon1) {
        this.carbon1 = carbon1;
    }

    public String getCarbon2() {
        return carbon2;
    }

    public void setCarbon2(String carbon2) {
        this.carbon2 = carbon2;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAnnul() {
        return annul;
    }

    public void setAnnul(String annul) {
        this.annul = annul;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExeat() {
        return exeat;
    }

    public void setExeat(int exeat) {
        this.exeat = exeat;
    }
}
