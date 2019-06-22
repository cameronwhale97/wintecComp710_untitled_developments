package com.untitleddevelopments.wintecdegreeplanner.DB;

import android.arch.lifecycle.MutableLiveData;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.parseInt;

public class SPModRepo {
    private static final String TAG = "SPModRepo";
    private static SPModRepo instance;
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
        String query = "SELECT * FROM " + DBHelper.TBL_MODULE +
                " INNER JOIN " + DBHelper.TBL_MODSTR +
                " ON " + DBHelper.TBL_MODULE + "." + DBHelper.MODULE_ID + " = " +
                DBHelper.TBL_MODSTR + "." + DBHelper.MODSTR_MOD_ID + " WHERE " +
                DBHelper.MODSTR_STR_ID + " = " + stream_ID;
        Log.e(TAG, "Get Modules for stream: " + query);
        DBManager.getInstance().openDatabase();
        Cursor cursorModule = DBManager.getInstance().getDetails(query);
        if (cursorModule != null && cursorModule.getCount() > 0) {
            cursorModule.moveToFirst();
            while (!cursorModule.isAfterLast()) {
                SPMod spMod = new SPMod( //using the Module Class constructor
                        parseInt(cursorModule.getString(cursorModule.getColumnIndex(DBHelper.MODULE_ID))),
                        cursorModule.getString(cursorModule.getColumnIndex(DBHelper.MODULE_CODE)),
                        cursorModule.getString(cursorModule.getColumnIndex(DBHelper.MODULE_NAME)),
                        null,           //I get pre reqs in the next few lines - because I need to access other Tables in DB
                        false,           //I get completed just below - because I need to access other tables in DB
                        true
                );
                ArrayList<PreReq> preReqs = getPreReqs(spMod.getModule_ID());
                spMod.setPreReqs(preReqs);
                Log.d(TAG, "loadUpYrData: allpre reqs done" + spMod.toStringPreReqs());
                if(Module.isCompleted(Globals.getStudent_ID(),spMod.getModule_ID())) {
                    spMod.setCompleted(true);
                    spMod.setLocked(false);
                } else {
                    Log.d(TAG, "loadUpYrData: calling arePreReqDone for spMod: "+spMod.getModule_ID()+spMod.getCode());
                    if (arePreReqsComplete(preReqs)) spMod.setLocked(false);
                }
                loadAppropriateModList(spMod);
                cursorModule.moveToNext();
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

    public void updateDBStuMod(SPMod spMod){
        int completed = spMod.getCompleted() ? 1 : 0;

        ContentValues contentStuMod = new ContentValues();
        contentStuMod.put(DBHelper.STUMOD_STU_ID, Globals.getStudent_ID());
        contentStuMod.put(DBHelper.STUMOD_MOD_ID, spMod.getModule_ID());
        contentStuMod.put(DBHelper.STUMOD_COMPLETED, completed );
        DBManager.getInstance().openDatabase();
        DBManager.getInstance().replace(DBHelper.TBL_STUMOD, contentStuMod);
        Log.d(TAG, "updateDBStuMod: ");    } //updateDBStuMod

    public static ArrayList<PreReq> getPreReqs(int module_id) {
        ArrayList<PreReq> preReqs = new ArrayList<PreReq>();
        String query = "SELECT * FROM " + DBHelper.TBL_PREREQ +
                " WHERE " + DBHelper.PREREQ_MOD_ID  + " = " + module_id;
        Log.d(TAG, "Get Pre Reqs: " + query);
        DBManager.getInstance().openDatabase();
        Cursor cursorPreReq = DBManager.getInstance().getDetails(query);
//        Log.d(TAG, "getPreReqs: cursor count: "+cursorPreReq.getCount());
        if (cursorPreReq != null && cursorPreReq.getCount() > 0) {
            cursorPreReq.moveToFirst();
            while (!cursorPreReq.isAfterLast()) {
                PreReq preSingle = new PreReq( //using the PreReq Class constructor
                        parseInt(cursorPreReq.getString(cursorPreReq.getColumnIndex(DBHelper.PREREQ_PREREQ_ID))),
                        Module.getModCodeFrmDB(parseInt(cursorPreReq.getString(cursorPreReq.getColumnIndex(DBHelper.PREREQ_PREREQ_ID))))
                );
//                Log.d(TAG, "getPreReqs: Add preSingle");
                preReqs.add(preSingle);
                cursorPreReq.moveToNext();
            }
//            Log.d(TAG, "getPreReqs: first prereq." + preReqs.get(0).getCode());
        }
        return preReqs;
    } //getPreReqs

    public static Boolean arePreReqsComplete(ArrayList<PreReq> preReqs){
        Boolean done = true;
        if(preReqs != null){
            for (PreReq pReqSingle : preReqs){
                Log.d(TAG, "arePreReqsComplete calling isCompleted for PreReqID: " + pReqSingle.getPreReq_ID()+pReqSingle.getCode());
                if(!Module.isCompleted(Globals.getStudent_ID(), pReqSingle.getPreReq_ID())){
                    Log.d(TAG, "arePreReqsComplete: done is false!");
                    done = false;
                    break; //once false break out of for loop
                }
                //so if any pre-req is not completed our preReqs are not done
            }
        }
        return done;
    } //arePreReqsComplete

    public static ArrayList<Module> completedParentModules(int module_ID){
        ArrayList<Module> compPMods = new ArrayList<>();
        String query = "SELECT " + DBHelper.PREREQ_MOD_ID + "" +
                " FROM " + DBHelper.TBL_PREREQ +
                " WHERE " + DBHelper.PREREQ_PREREQ_ID  + " = " + module_ID;
        Log.d(TAG, "Get Parents: " + query);
        DBManager.getInstance().openDatabase();
        Cursor cursorPreReq = DBManager.getInstance().getDetails(query);
//        Log.d(TAG, "getPreReqs: cursor count: "+cursorPreReq.getCount());
        if (cursorPreReq != null && cursorPreReq.getCount() > 0) {
            cursorPreReq.moveToFirst();
            while (!cursorPreReq.isAfterLast()) {
                int parent_ID = Integer.parseInt(cursorPreReq.getString(cursorPreReq.getColumnIndex(DBHelper.PREREQ_MOD_ID)));
                Module mod = new Module(parent_ID);
                Log.d(TAG, "getParentsOf: Add parent: "+ parent_ID +mod.getCode());
                if(Module.isCompleted(Globals.getStudent_ID(), mod.getModule_ID())){
                    compPMods.add(mod);
                }
                cursorPreReq.moveToNext();
            }
            Log.d(TAG, "completedParentModules compmods size: " + compPMods.size());
        }
        return compPMods;
    }

} //SPModRepo
