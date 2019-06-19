package com.untitleddevelopments.wintecdegreeplanner.DB;

import android.arch.lifecycle.MutableLiveData;
import android.content.ContentValues;
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
    public ArrayList<SPMod> modListY0 = new ArrayList<SPMod>();
    public ArrayList<SPMod> modListC0 = new ArrayList<SPMod>();
    public ArrayList<SPMod> modListY1 = new ArrayList<SPMod>();
    public ArrayList<SPMod> modListC1 = new ArrayList<SPMod>();
    public ArrayList<SPMod> modListY2 = new ArrayList<SPMod>();
    public ArrayList<SPMod> modListC2 = new ArrayList<SPMod>();
    public ArrayList<SPMod> modListY3 = new ArrayList<SPMod>();
    public ArrayList<SPMod> modListC3 = new ArrayList<SPMod>();

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
        modListY0.clear();
        modListY1.clear();
        modListY2.clear();
        modListY3.clear();
        modListC0.clear();
        modListC1.clear();
        modListC2.clear();
        modListC3.clear();
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
    } //loadUpYrData

    public void loadAppropriateModList(SPMod spMod) {
    Log.d(TAG, "loadAppropriateModList getYear: " + spMod.getYear());
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
    //Log.d(TAG, "loadAppropriateModList: ");
} //loadAppropriateModList

    public void removeAppropriateModList(SPMod spMod) {
        Log.d(TAG, "removeAppropriateModList getYear: " + spMod.getYear());
        switch (spMod.getYear()){
            case 1:
                if(spMod.getCompleted()) {
                    modListC1.remove(spMod);
                    modListC0.remove(spMod);
                }
                else {
                    modListY1.remove(spMod);
                    modListY0.remove(spMod);
                }
                return;
            case 2:
                if(spMod.getCompleted()) {
                    modListC2.remove(spMod);
                    modListC0.remove(spMod);
                }
                else {
                    modListY2.remove(spMod);
                    modListY0.remove(spMod);
                }
                return;
            case 3:
                if(spMod.getCompleted()) {
                    modListC3.remove(spMod);
                    modListC0.remove(spMod);
                }
                else {
                    modListY3.remove(spMod);
                    modListY0.remove(spMod);
                }
                return;
            default:
                Log.d(TAG, "loadAppropriateModList: ************************************Errror in case");
        }
        //Log.d(TAG, "loadAppropriateModList: ");
    } //removeAppropriateModList

    public void updateDBStuMod(SPMod spMod){
        int student_ID = Globals.getStudent_ID();
        int module_ID = spMod.getModule_ID();
        String myMsg;

        boolean updatedOK = false;
        ContentValues contentStuMod = new ContentValues();
        contentStuMod.put(DBHelper.STUMOD_STU_ID, student_ID);
        contentStuMod.put(DBHelper.STUMOD_MOD_ID, module_ID);
        contentStuMod.put(DBHelper.STUMOD_COMPLETED,spMod.getCompleted());

        updatedOK = DBManager.getInstance().update(
                DBHelper.TBL_STUMOD,                          //pass in table name
                contentStuMod,                                //pass in content values this can be one or many columns of a row.
                DBHelper.STUMOD_STU_ID + "=? " + DBHelper.STUMOD_MOD_ID + "=?"  ,        //pass in where clause - note the ?
                new String[] {Integer.toString(student_ID), Integer.toString(module_ID)});                         //pass in a String array - in this case my array is just 1 item
        myMsg = updatedOK ? " Update Success!" : " Not Deleted - bugger";
        Log.e(TAG,  myMsg);
    }
}
