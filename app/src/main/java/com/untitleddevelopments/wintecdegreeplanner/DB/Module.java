package com.untitleddevelopments.wintecdegreeplanner.DB;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

public class Module {
    private static final String TAG = "Module";

    private int module_ID;
    private String code;
    private String name;
    private String description;
    private int NZQALevel;
    private int NZQACredits;
    private String coReq;

    //constructor
    public Module(int module_ID, String code, String name, String description, int NZQALevel, int NZQACredits, String coReq) {
        this.module_ID = module_ID;
        this.code = code;
        this.name = name;
        this.description = description;
        this.NZQALevel = NZQALevel;
        this.NZQACredits = NZQACredits;
        this.coReq = coReq;
    }
//constructor passing in an ID - and getting details from DB
    public Module(int module_ID) {
        String query = "SELECT * FROM " + DBHelper.TBL_MODULE +
               " WHERE " + DBHelper.MODULE_ID + " = " + module_ID;
        Log.d(TAG, "Getting a module: " + query);
        DBManager.getInstance().openDatabase();
        Cursor cursor = DBManager.getInstance().getDetails(query);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            this.module_ID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_ID)));
            this.code = cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_CODE));
            this.name = cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_NAME));
            this.description = cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_DESCRIPTION));
            this.NZQALevel = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_NZQALEVEL)));
            this.NZQACredits = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_NZQACREDITS)));
            this.coReq = cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_COREQ));
        }
    }

    @Override
    public String toString() {
        return "Module{" +
                "module_ID=" + module_ID +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", NZQALevel=" + NZQALevel +
                ", NZQACredits=" + NZQACredits +
                ", coReq='" + coReq + '\'' +
                '}';
    }

    //getter setter
    public int getModule_ID() {
        return module_ID;
    }

    public void setModule_ID(int module_ID) {
        this.module_ID = module_ID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNZQALevel() {
        return NZQALevel;
    }

    public void setNZQALevel(int NZQALevel) {
        this.NZQALevel = NZQALevel;
    }

    public int getNZQACredits() {
        return NZQACredits;
    }

    public void setNZQACredits(int NZQACredits) {
        this.NZQACredits = NZQACredits;
    }

    public String getCoReq() {
        return coReq;
    }

    public void setCoReq(String coReq) {
        this.coReq = coReq;
    }

    public int getYear() {
        return Integer.parseInt(this.code.substring(5,6)) -4;
    }

    public static boolean isCompleted(int student_ID, int module_ID){
        boolean comp = false;
        int compFromDB = 0;
        String query = "SELECT " + DBHelper.STUMOD_COMPLETED +
                " FROM " + DBHelper.TBL_STUMOD +
                " WHERE " + DBHelper.STUMOD_STU_ID + " = " + student_ID +
                " AND " + DBHelper.STUMOD_MOD_ID + " = " + module_ID;
        Log.d(TAG, "isCompleted:" + query);
        DBManager.getInstance().openDatabase();
        Cursor cursor = DBManager.getInstance().getDetails(query);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            compFromDB = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.STUMOD_COMPLETED)));
            if(compFromDB == 1) {
                comp = true;
                Log.d(TAG, "isCompleted: fromare.. module is completed!");
            }
        }
        return comp;
    } //isCompleted

    public static String getModCodeFrmDB(int module_ID){
        String code = "";
        String query = "SELECT " + DBHelper.MODULE_CODE +
                " FROM " + DBHelper.TBL_MODULE +
                " WHERE " + DBHelper.MODULE_ID + " = " + module_ID;
        Log.d(TAG, "Getting module code: " + query);
        DBManager.getInstance().openDatabase();
        Cursor cursor = DBManager.getInstance().getDetails(query);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            code = cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_CODE));
        }
        return code;
    } //getModCodeFrmDB




}
