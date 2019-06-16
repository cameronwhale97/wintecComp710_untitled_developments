package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

public class SwipeToCompUncomp extends ItemTouchHelper.SimpleCallback {
    private static final String TAG = "SwipeToCompUncomp";
    private SPRecycViewAdapt mAdapter;
    public SwipeToCompUncomp(SPRecycViewAdapt adapter) {
        super(0, ItemTouchHelper.RIGHT);
        //super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

        mAdapter = adapter;
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        //mAdapter.deleteItem(position);
        Log.d(TAG, "onSwiped: direction:"+Integer.toString(direction)+ " position:"+Integer.toString(position));

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // used for up and down movements
        return false;
    }
}
