package com.dancun.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dancun.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class SQLiteUtils extends SQLiteOpenHelper {
    public static int version=1;
    private String sql ="CREATE TABLE leave(" +
            "id integer PRIMARY KEY autoincrement, "+
            "schoolname VARCHAR(50)," +
            "startdate VARCHAR(50)," +
            "enddate VARCHAR(50)," +
            "leavetype VARCHAR(50)," +
            "leavecause VARCHAR(50)," +
            "carbon VARCHAR(50)," +
            "carbon1 VARCHAR(50)," +
            "carbon2 VARCHAR(50)," +
            "local VARCHAR(50)," +
            "tel VARCHAR(50)," +
            "name VARCHAR(50)," +
            "cause VARCHAR(50)," +
            "annul VARCHAR(50)," +
            "exeat integer default 1"+
            ");";
    public SQLiteUtils(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {super(context, name+".db", factory, version);
    }
    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);

    }
    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table leave");
        db.execSQL(sql);
//         db.execSQL("ALTER TABLE leave ADD phone VARCHAR(50) NULL");
    }
    public boolean insert(BaseEntity baseEntity) {
        boolean i = false;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("annul",baseEntity.getAnnul());
            values.put("exeat",baseEntity.getExeat());
            values.put("schoolname", baseEntity.getSchoolName());
            values.put("carbon", baseEntity.getCarbon());
            values.put("carbon1", baseEntity.getCarbon1());
            values.put("carbon2", baseEntity.getCarbon2());
            values.put("local", baseEntity.getLocal());
            values.put("enddate", baseEntity.getEndDate());
            values.put("cause", baseEntity.getLeaveCause());
            values.put("startdate", baseEntity.getStartDate());
            values.put("leavetype", baseEntity.getLeaveType());
            values.put("name",baseEntity.getName());
            values.put("tel",baseEntity.getTel());
            db.insert("leave", null, values);
            i=true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", "insert: " + e.getStackTrace().toString());
            i = false;
        }
        return i;
    }

    public List<BaseEntity> getAll() {
        List<BaseEntity> list = new ArrayList<BaseEntity>();

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query("leave", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    BaseEntity baseEntity = new BaseEntity();
                    baseEntity.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    baseEntity.setAnnul(cursor.getString(cursor.getColumnIndex("annul")));
                    baseEntity.setExeat(cursor.getInt(cursor.getColumnIndex("exeat")));
                    baseEntity.setName(cursor.getString(cursor.getColumnIndex("name")));
                    baseEntity.setSchoolName(cursor.getString(cursor.getColumnIndex("schoolname")));
                    baseEntity.setCarbon(cursor.getString(cursor.getColumnIndex("carbon")));
                    baseEntity.setCarbon1(cursor.getString(cursor.getColumnIndex("carbon1")));
                    baseEntity.setCarbon2(cursor.getString(cursor.getColumnIndex("carbon2")));
                    baseEntity.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                    baseEntity.setEndDate(cursor.getString(cursor.getColumnIndex("enddate")));
                    baseEntity.setLeaveCause(cursor.getString(cursor.getColumnIndex("cause")));
                    baseEntity.setStartDate(cursor.getString(cursor.getColumnIndex("startdate")));
                    baseEntity.setLeaveType(cursor.getString(cursor.getColumnIndex("leavetype")));
                    baseEntity.setTel(cursor.getString(cursor.getColumnIndex("tel")));
                    list.add(baseEntity);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", "insert: ");
        }
        return list;
    }
    public boolean annulLeave(int id,String annul){
        boolean i=false;
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("update leave set annul= '"+annul+"' where id="+id);
            i=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }
}