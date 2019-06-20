package com.untitleddevelopments.wintecdegreeplanner.DB;

import android.util.Log;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class SPMod {
    private int module_ID;
    private String code;
    private String name;
    private ArrayList<PreReq> preReqs;
    private Boolean completed;


    //constructor full

    public SPMod(int module_ID, String code, String name, ArrayList<PreReq> preReqs, Boolean completed) {
        this.module_ID = module_ID;
        this.code = code;
        this.name = name;
        this.preReqs = preReqs;
        this.completed = completed;
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
    }
}

