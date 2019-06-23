package com.untitleddevelopments.wintecdegreeplanner.DB;

import android.database.Cursor;
import android.util.Log;

import com.untitleddevelopments.wintecdegreeplanner.global.Globals;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class SPMod {
    private int module_ID;
    private String code;
    private String name;
    private ArrayList<PreReq> preReqs;
    private Boolean completed;
    private boolean locked;

    //constructor full

    public SPMod(int module_ID, String code, String name, ArrayList<PreReq> preReqs, Boolean completed, Boolean locked) {
        this.module_ID = module_ID;
        this.code = code;
        this.name = name;
        this.preReqs = preReqs;
        this.completed = completed;
        this.locked = locked;
    }
    //blank constructor
    public SPMod(){}

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

    public ArrayList<PreReq> getPreReqs() {
        return preReqs;
    }

    public void setPreReqs(ArrayList<PreReq> preReqs) {
        this.preReqs = preReqs;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public int getYear() {
        return (Integer.parseInt(this.code.substring(4,5) ) -4 );

    } //getYear

    public String toStringPreReqs() {
        int len = preReqs.size();
        String preString = "";
        if(len != 0){
            if (len == 1){
                preString = "Prerequsite: ";
            } else {
                preString = "Prerequsites: ";
            }
            int count = 0;
            for (PreReq pReqSingle : preReqs){
                if(count != 0) preString += ", ";
                preString += pReqSingle.getCode();
                count ++;
            }
        }
        return preString;
    } //toStringPreReqs

    public boolean isCore(){
        DBManager.getInstance().openDatabase();
        //find out how many streams we have
        String query = "SELECT * FROM " + DBHelper.TBL_STREAM;
        Cursor cursor = DBManager.getInstance().getDetails(query);
        int noOfStreams = cursor.getCount();
        Log.d(TAG,  query + "isCore #streams=" + noOfStreams);

        //Now join stream with table_stream for a particular module and get count.
        //If the result of this count = the number of streams the subject is core.
        query = "SELECT * FROM " + DBHelper.TBL_STREAM +
                " INNER JOIN " + DBHelper.TBL_MODSTR +
                " ON " + DBHelper.TBL_STREAM + "." + DBHelper.STREAM_ID + " = " +
                DBHelper.TBL_MODSTR + "." + DBHelper.MODSTR_STR_ID + " WHERE " +
                DBHelper.MODSTR_MOD_ID + " = " + module_ID;
        cursor = DBManager.getInstance().getDetails(query);
        int noInJoined = cursor.getCount();
        Log.d(TAG,  query + "isCore #in joined" + noInJoined);

        if(noInJoined == noOfStreams) return true;
        else return false;
    }




}

