package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.untitleddevelopments.wintecdegreeplanner.DB.SPMod;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPModRepo;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;

public class SwipeToCompUncomp extends ItemTouchHelper.SimpleCallback {

    private SPModRepo sPModRepo;            //Create reference to Geoffs repo
    private static final String TAG = "SwipeToCompUncomp";
    private SPRecycViewAdapt mAdapter;
    public SwipeToCompUncomp(SPRecycViewAdapt adapter) {
        super(0, ItemTouchHelper.LEFT);
        //super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        sPModRepo = SPModRepo.getInstance();
        mAdapter = adapter;
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        //mAdapter.deleteItem(position);
        Log.d(TAG, "onSwiped: direction:"+Integer.toString(direction)+ " position:"+Integer.toString(position));
        dealWithCompleted(position, Globals.getYear());
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // used for up and down movements
        return false;
    }

    public void dealWithCompleted (int position, int year) {
        Log.d(TAG, "dealWithCompleted: year="+Integer.toString(year)+ " pos="+Integer.toString(position));
        switch (year){
            case 0:
                SPMod spMod = sPModRepo.modListY0.get(position);
                sPModRepo.modListC0.add(spMod);
                sPModRepo.modListY0.remove(position);
                return;
            default:
        }
    }
}
