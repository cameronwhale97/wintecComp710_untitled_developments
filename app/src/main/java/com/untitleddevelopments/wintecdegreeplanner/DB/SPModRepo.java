package com.untitleddevelopments.wintecdegreeplanner.DB;

import android.arch.lifecycle.MutableLiveData;
import android.database.Cursor;
import android.util.Log;

import com.untitleddevelopments.wintecdegreeplanner.global.Globals;

import java.util.ArrayList;
import java.util.List;

public class SPModRepo {
    private static final String TAG = "SPModRepo";
    private static SPModRepo instance;
    private String query;
    private Cursor cursor;
    private ArrayList<SPMod> modsYetToComp = new ArrayList<>();
    private ArrayList<SPMod> modsCompleted = new ArrayList<>();
    private ArrayList<SPMod> modListY0 = new ArrayList<SPMod>();
    private ArrayList<SPMod> modListC0 = new ArrayList<SPMod>();
    private ArrayList<SPMod> modListY1 = new ArrayList<SPMod>();
    private ArrayList<SPMod> modListC1 = new ArrayList<SPMod>();
    private ArrayList<SPMod> modListY2 = new ArrayList<SPMod>();
    private ArrayList<SPMod> modListC2 = new ArrayList<SPMod>();
    private ArrayList<SPMod> modListY3 = new ArrayList<SPMod>();
    private ArrayList<SPMod> modListC3 = new ArrayList<SPMod>();

    /**
     * Singleton Pattern per coding with Mitch - MVVVM
     * @return
     */
    public static SPModRepo getInstance(){
        if(instance == null){
            instance = new SPModRepo();
        }
        return instance;
    } //static SPModRepo

    //pretend to get data from a webservice or online service
    public MutableLiveData<List<SPMod>> getModsYetToComp(int year){
        MutableLiveData<List<SPMod>> data = new MutableLiveData<>();
        switch (year){
            case 0:
                Log.d(TAG, "getModsYetToComp: case 0");
                data.setValue(modListY0);
                return data;
            case 1:
                Log.d(TAG, "getModsYetToComp: case 1");
                data.setValue(modListY1);
                return data;
            case 2:
                Log.d(TAG, "getModsYetToComp: case 2");
                data.setValue(modListY2);
                return data;
            case 3:
                Log.d(TAG, "getModsYetToComp: case 3");
                data.setValue(modListY3);
                return data;
        }
        return data;
    }

    public MutableLiveData<List<SPMod>> getModsCompleted(int year){
        //loadUpCompleted();  //this is where database queries would go
        MutableLiveData<List<SPMod>> data = new MutableLiveData<>();
        switch (year){
            case 0:
                data.setValue(modListC0);
                Log.d(TAG, "getModsCompleted: case 0");
                return data;
            case 1:
                Log.d(TAG, "getModsCompleted: case 1");

                data.setValue(modListC1);
                return data;
            case 2:
                Log.d(TAG, "getModsCompleted: case 2");

                data.setValue(modListC2);
                return data;
            case 3:
                Log.d(TAG, "getModsCompleted: case 3");
                data.setValue(modListC3);
                return data;
        }
        return data;
    }

    public void loadUpYrData() {
        int stream_ID = Globals.getStream_ID();
        query = "SELECT * FROM " + DBHelper.TBL_MODULE +
                " INNER JOIN " + DBHelper.TBL_MODSTR +
                " ON " + DBHelper.TBL_MODULE + "." + DBHelper.MODULE_ID + " = " +
                DBHelper.TBL_MODSTR + "." + DBHelper.MODSTR_MOD_ID + " WHERE " +
                DBHelper.MODSTR_STR_ID + " = " + stream_ID;
        Log.e(TAG, "Get Modules for stream: " + query);
        DBManager.getInstance().openDatabase();
        cursor = DBManager.getInstance().getDetails(query);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                SPMod spMod = new SPMod( //using the Module Class constructor
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_ID))),
                        cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_CODE)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_NAME)),
                        "",
                        false
                );
                if(Module.isCompleted(Globals.getStudent_ID(),spMod.getModule_ID())) spMod.setCompleted(true);
                //ToDo get prereqs
                loadAppropriateModList(spMod);
                cursor.moveToNext();
            }
        }
        Log.d(TAG, "loadUpYrData finished:****************************************************************** ");
    }

    private void loadAppropriateModList(SPMod spMod) {
        Log.d(TAG, "loadAppropriateModList getYear: " + Integer.toString(spMod.getYear()));
        Log.d(TAG, "loadAppropriateModList getYear: " + Integer.toString(spMod.getYear()));
        Log.d(TAG, "loadAppropriateModList: ");

        switch (spMod.getYear()){
            case 1:
                if(spMod.getCompleted()) {
                    modListC1.add(spMod);
                    modListC0.add(spMod);
                }
                else {
                    modListY1.add(spMod);
                    modListY0.add(spMod);
                }
                return;
            case 2:
                if(spMod.getCompleted()) {
                    modListC2.add(spMod);
                    modListC0.add(spMod);
                }
                else {
                    modListY2.add(spMod);
                    modListY0.add(spMod);
                }
                return;
            case 3:
                if(spMod.getCompleted()) {
                    modListC3.add(spMod);
                    modListC0.add(spMod);
                }
                else {
                    modListY3.add(spMod);
                    modListY0.add(spMod);
                }
                return;
            default:
                Log.d(TAG, "loadAppropriateModList: ************************************Errror in case");
        }
        Log.d(TAG, "loadAppropriateModList: ");
    }//loadAppropriateModList
    //Load Student Module
               /*
        //Year 0..all years
        modListY0.clear();
        modListY0.add(new SPMod(51,"Comp501", "SPMod Name 501", "Description 501"));
        modListY0.add(new SPMod(52,"Comp502", "SPMod Name 502", "Description 502"));

        modListC0.clear();
        modListC0.add(new SPMod(53,"Comp503", "SPMod Name 503", "Description 503"));
        //Year 1
        modListY1.clear();
        modListY1.add(new SPMod(51,"Comp501", "SPMod Name 501", "Description 501"));
        modListY1.add(new SPMod(52,"Comp502", "SPMod Name 502", "Description 502"));
        modListY1.add(new SPMod(53,"Comp503", "SPMod Name 503", "Description 503"));

        modListC1.clear();
        modListC1.add(new SPMod(54,"Comp504", "SPMod Name 504", "Description 504"));
        modListC1.add(new SPMod(55,"Comp505", "SPMod Name 505", "Description 505"));
        modListC1.add(new SPMod(56,"Comp506", "SPMod Name 506", "Description 506"));
        modListC1.add(new SPMod(57,"Comp507", "SPMod Name 507", "Description 507"));
        //Year 2
        modListY2.clear();
        modListY2.add(new SPMod(61,"Comp601", "SPMod Name 601", "Description 601"));
        modListY2.add(new SPMod(62,"Comp602", "SPMod Name 602", "Description 602"));
        modListY2.add(new SPMod(63,"Comp603", "SPMod Name 603", "Description 603"));
        modListY2.add(new SPMod(64,"Comp604", "SPMod Name 604", "Description 604"));
        modListY2.add(new SPMod(65,"Comp605", "SPMod Name 605", "Description 605"));
        modListY2.add(new SPMod(66,"Comp606", "SPMod Name 606", "Description 606"));

        modListC2.clear();
        modListC2.add(new SPMod(67,"Comp607", "SPMod Name 607", "Description 607"));
        //Year 3
        modListY3.clear();
        modListY3.add(new SPMod(71,"Comp701", "SPMod Name 701", "Description 701"));
        modListY3.add(new SPMod(72,"Comp702", "SPMod Name 702", "Description 702"));
        modListY3.add(new SPMod(73,"Comp703", "SPMod Name 703", "Description 703"));
        modListY3.add(new SPMod(74,"Comp704", "SPMod Name 704", "Description 704"));

        modListC3.clear();
        modListC3.add(new SPMod(75,"Comp705", "SPMod Name 705", "Description 705"));
        modListC3.add(new SPMod(76,"Comp706", "SPMod Name 706", "Description 706"));
        modListC3.add(new SPMod(77,"Comp707", "SPMod Name 707", "Description 707"));
        */
/*
    private void loadUpYetToComp() {
        Log.d(TAG, "loadUpYetToComp: ");
        modsYetToComp.clear();
        modsYetToComp.add(new SPMod(71,"Comp701", "SPMod Name 701", "Description 701"));
        modsYetToComp.add(new SPMod(72,"Comp702", "SPMod Name 702", "Description 702"));
        modsYetToComp.add(new SPMod(73,"Comp703", "SPMod Name 703", "Description 703"));
        modsYetToComp.add(new SPMod(74,"Comp704", "SPMod Name 704", "Description 704"));
        modsYetToComp.add(new SPMod(75,"Comp705", "SPMod Name 705", "Description 705"));
        modsYetToComp.add(new SPMod(76,"Comp706", "SPMod Name 706", "Description 706"));
        modsYetToComp.add(new SPMod(77,"Comp707", "SPMod Name 707", "Description 707"));
    }
    private void loadUpCompleted(){
        Log.d(TAG, "loadUpCompleted: ");
        modsCompleted.clear();
        modsCompleted.add(new SPMod(51,"Comp501", "SPMod Name 501", "Description 501"));
        modsCompleted.add(new SPMod(52,"Comp502", "SPMod Name 502", "Description 502"));
        modsCompleted.add(new SPMod(53,"Comp503", "SPMod Name 503", "Description 503"));
        modsCompleted.add(new SPMod(54,"Comp504", "SPMod Name 504", "Description 504"));
        modsCompleted.add(new SPMod(55,"Comp505", "SPMod Name 505", "Description 505"));
        modsCompleted.add(new SPMod(56,"Comp506", "SPMod Name 506", "Description 506"));
        modsCompleted.add(new SPMod(57,"Comp507", "SPMod Name 507", "Description 507"));
    }
    */
}
