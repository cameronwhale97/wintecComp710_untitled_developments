package com.untitleddevelopments.wintecdegreeplanner.DB;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SPModRepo {
    private static final String TAG = "SPModRepo";
    private static SPModRepo instance;
    private ArrayList<SPMod> modsYetToComp = new ArrayList<>();
    private ArrayList<SPMod> modsCompleted = new ArrayList<>();

    /**
     * Singleton Pattern per coding with Mitch - MVVVM
     * @return
     */
    public static SPModRepo getInstance(){
        if(instance == null){
            instance = new SPModRepo();
        }
        return instance;
    }
    //pretend to get data from a webservice or online service
    public MutableLiveData<List<SPMod>> getModsYetToComp(){
        loadUpYetToComp();  //this is where database queries would go
        MutableLiveData<List<SPMod>> data = new MutableLiveData<>();
        data.setValue(modsYetToComp);
        return data;
    }
    public MutableLiveData<List<SPMod>> getModsCompleted(){
        loadUpCompleted();  //this is where database queries would go
        MutableLiveData<List<SPMod>> data = new MutableLiveData<>();
        data.setValue(modsCompleted);
        return data;
    }


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
}
