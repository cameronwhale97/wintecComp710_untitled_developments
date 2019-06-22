package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.DB.SPMod;
import com.untitleddevelopments.wintecdegreeplanner.R;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;

import java.util.List;

public class SPRecycViewAdapt extends RecyclerView.Adapter<SPRecycViewAdapt.ViewHolder>
    implements View.OnClickListener {
    //The ViewHolder class is defined as a class at the bottom
    private static final String TAG = "SPRecycViewAdapt";
    private List<SPMod> mSPMods;
    private Context mContext;
    private View.OnClickListener clickListener;
    private boolean isYTC;

    private RecyclerView recyclerView;

        //constructor
    public SPRecycViewAdapt(RecyclerView recyclerView, Context context, List<SPMod> mMods, Boolean isYTC ) {
        //Log.d(TAG, "SPRecycViewAdapt Constrcting: First Item= " + mMods.toString());
        this.mSPMods = mMods;
        this.mContext = context;
        this.recyclerView = recyclerView;
        this.isYTC = isYTC;
    }

    @NonNull
    @Override
    //this method is responsible for inflating the view...
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    } //onCreateViewHolder

    //This method gets called every time an item gets added to the list...
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.code.setText(mSPMods.get(position).getCode());
        holder.name.setText(mSPMods.get(position).getName());
        holder.preReq.setText(mSPMods.get(position).toStringPreReqs());
        if(mSPMods.get(position).getLocked()) {
            holder.padlock.setImageResource(R.drawable.baseline_lock_2);
        } else {
            if(mSPMods.get(position).getCompleted()){
                holder.padlock.setImageResource(R.drawable.green_tick);
            } else {
                holder.padlock.setImageResource(R.drawable.baseline_lock_open_11);
            }
        }
        holder.layoutItem.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        int pos = recyclerView.getChildLayoutPosition(view);
        String geoff = isYTC ? "YTC" : "Complete";
        SPMod spMod =  mSPMods.get(pos);
        Globals.setSPMod(spMod);
        SPModuleDetailFragment  spModuleDetailFragment = new SPModuleDetailFragment();
//        spModuleDetailFragment.show();
        Toast.makeText(mContext, "pos: " +   geoff + " " + pos + mSPMods.get(pos).getCode(), Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return mSPMods.size();
    } //getItemCount

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView code;
        TextView name;
        TextView preReq;
        LinearLayout layoutItem;
        ImageView padlock;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.SPTVModuleCode);
            name = itemView.findViewById(R.id.SPTVModuleName);
            preReq = itemView.findViewById(R.id.SPTVPreReqs);
            layoutItem = itemView.findViewById(R.id.SPListItemLayout);
            padlock = itemView.findViewById(R.id.SPIVlock);
        }
    } //ViewHolder
}
