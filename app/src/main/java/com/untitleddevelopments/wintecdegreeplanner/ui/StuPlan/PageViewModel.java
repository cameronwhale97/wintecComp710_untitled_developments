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
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    //gg
    private SPModRepo SPModRepo;      //Create reference to the repo
    private MutableLiveData<List<SPMod>> modsYetToComp;
    public LiveData<List<SPMod>> getModsYetToComp(){
        return modsYetToComp;
    }
    private MutableLiveData<List<SPMod>> modsCompleted;
    public LiveData<List<SPMod>> getModsCompleted(){
        return modsCompleted;
    }
    public void init(){
        if(modsYetToComp == null){
            Log.d(TAG, "init: ");
            SPModRepo = SPModRepo.getInstance();
            modsYetToComp = SPModRepo.getModsYetToComp();
            modsCompleted = SPModRepo.getModsCompleted();
        }
        return;
    }
    //stuff from Tabs...
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });
    private LiveData<String> lString;
    public void setIndex(int index) {
        mIndex.setValue(index);
    }
    public LiveData<String> getText() {
        return mText;
    }
}