package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPMod;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPModRepo;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPYrData;

import java.util.List;

public class PageViewModel extends ViewModel {
    private static final String TAG = "PageViewModel";
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    //gg     Create reference to the repo
    private SPModRepo SPModRepo;
    //yet to comp
    private MutableLiveData<List<SPMod>> modsYetToComp;
    public LiveData<List<SPMod>> getModsYetToComp(){
        return modsYetToComp;
    }
    public void setModsYetToComp(int year){
        modsYetToComp = SPModRepo.getModsYetToComp(year);
    }

    //completed
    private MutableLiveData<List<SPMod>> modsCompleted;
    public LiveData<List<SPMod>> getModsCompleted(){ return modsCompleted; }
    public void setModsCompleted(int year){
        modsCompleted = SPModRepo.getModsCompleted(year);
    }

    private MutableLiveData<String> mIndexText;
    public LiveData<String> getmIndexText() {return mIndexText; }
    public void setmIndexText(String index) {
        mIndexText.setValue(index);
    }
    //stuff from Tabs...
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });
    public void setIndex(int index) {
        mIndex.setValue(index);
    }
    public LiveData<String> getText() {
        return mText;
    }
    public void init(){
        Log.d(TAG, "init: *********************************************************************************************");
        SPModRepo = SPModRepo.getInstance();
        SPModRepo.loadUpYrData();
        modsYetToComp = SPModRepo.getModsYetToComp(0);
        modsCompleted = SPModRepo.getModsCompleted(0);
        //mSPYearArray = SPModRepo.getModules();
        MutableLiveData<String> data = new MutableLiveData<>();
        data.setValue("");
        mIndexText = data;
        return;
    }
}