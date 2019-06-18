package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPMod;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPModRepo;

import java.util.List;

public class PageViewModel extends ViewModel {
    private static final String TAG = "PageViewModel";

    //private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
//    private int currYear;

    //gg     Create reference to the repo
    private SPModRepo sPModRepo;
    //yet to comp
    private MutableLiveData<List<SPMod>> modsYetToComp;
    public LiveData<List<SPMod>> getModsYetToComp(){
        return modsYetToComp;
    }
    public void setModsYetToComp(int year){
        modsYetToComp = sPModRepo.getModsYetToComp(year);
    }

    //completed
    private MutableLiveData<List<SPMod>> modsCompleted;
    public LiveData<List<SPMod>> getModsCompleted(){ return modsCompleted; }
    public void setModsCompleted(int year){
        modsCompleted = sPModRepo.getModsCompleted(year);
    }


    public void loadUpArrays(){
        //Log.d(TAG, "load up arrys from repo - which accesses DB*******************************");
        sPModRepo = sPModRepo.getInstance();
        sPModRepo.loadUpYrData();
    }

    public void initMutables(){
        Log.d(TAG, "init Mutables:");
//        currYear = 0;
        sPModRepo = SPModRepo.getInstance();
        //initialise our yet to comp and complete arrays to year 0
        modsYetToComp = sPModRepo.getModsYetToComp(0);
        modsCompleted = sPModRepo.getModsCompleted(0);
        //MutableLiveData<String> data = new MutableLiveData<>();
        //data.setValue("");
        return;
    }

//    public int getCurrYear(){
//        return currYear;
//    }

//    public void setCurrYear(int year){currYear = year;}
}