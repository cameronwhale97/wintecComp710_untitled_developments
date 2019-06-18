package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.untitleddevelopments.wintecdegreeplanner.DB.DataChangedListener;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPMod;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPModRepo;
import com.untitleddevelopments.wintecdegreeplanner.R;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import java.util.List;

/**
 * A placeholder fragment containing Everything below the tabs
 * Author Geoff Genner
 * Contains 2 Recycler views
 * This fragment utilises MVVM - model view view model. The viewModel used by this fragment is called
 * viewModel.
 */

public class PlaceholderFragment extends Fragment  {
    private static final String TAG = "PlaceholderFragment";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
//Geoffs vbls
    private RecyclerView recyclerVYetToComp;
    private SPRecycViewAdapt adapterYTC;

    private RecyclerView recyclerVComp;
    private SPRecycViewAdapt adapterComp;
//    private TextView SPGeoffTV;
    private int yearIndex;                  //tells us what year we are dealing with

    public static PlaceholderFragment newInstance(int index) {
        Log.d(TAG, "Placeholder fragment New Instance index: "+ index);
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //instantiate the view model
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;                          //index is used by tabs
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        Log.d(TAG, "*onCreate: calling pageViewModel InitMuts. FYI index =  " + Integer.toString(index));
        pageViewModel.initMutables();
        yearIndex = index-1;            //
        Globals.setPageViewModel(pageViewModel);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView, calling setMods methods: " + yearIndex );

        pageViewModel.setModsYetToComp(yearIndex);
        pageViewModel.setModsCompleted(yearIndex);

        View view = inflater.inflate(R.layout.fragment_stu_plan, container, false);

        //Deal to yet to complete...
        pageViewModel.getModsYetToComp().observe(this, new Observer<List<SPMod>>() {
            @Override
            public void onChanged(@Nullable List<SPMod> modsYetToComp) {
                adapterYTC.notifyDataSetChanged();
            }
        });
        recyclerVYetToComp = view.findViewById(R.id.sp_recyclerv_yet_to_comp);
        adapterYTC = new SPRecycViewAdapt(getActivity(), pageViewModel.getModsYetToComp().getValue());
        recyclerVYetToComp.setAdapter(adapterYTC);
        recyclerVYetToComp.setLayoutManager(new LinearLayoutManager(getActivity()));

//        The follwing code is used to deal with swipes...
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToComplete(adapterYTC));
        itemTouchHelper.attachToRecyclerView(recyclerVYetToComp);

        //Deal to completed...
        pageViewModel.getModsCompleted().observe(this, new Observer<List<SPMod>>() {
            @Override
            public void onChanged(@Nullable List<SPMod> modsCompleted) {
                adapterComp.notifyDataSetChanged();
            }
        });

        recyclerVComp = view.findViewById(R.id.sp_recyclerv_comp);

        adapterComp = new SPRecycViewAdapt(getActivity(),  pageViewModel.getModsCompleted().getValue());

        recyclerVComp.setAdapter(adapterComp);
        recyclerVComp.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Log.d(TAG, "onViewCreated yearIndex:"+Integer.toString(yearIndex));
        super.onViewCreated(view, savedInstanceState);
    }

    public void refreshDataLists() {
        Log.d(TAG, "--------- refreshDataLists-----------" );
        recyclerVComp.setAdapter(adapterComp);
        recyclerVYetToComp.setAdapter(adapterYTC);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class SwipeToComplete extends ItemTouchHelper.SimpleCallback {
        private SPModRepo sPModRepo;            //Create reference to Geoffs Data repo
        private static final String TAG = "SwipeToComplete";
        private SPRecycViewAdapt mAdapter;
        public SwipeToComplete(SPRecycViewAdapt adapter) {
            super(0, ItemTouchHelper.LEFT);
            //super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            sPModRepo = SPModRepo.getInstance();
            mAdapter = adapter;
        }
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Log.d(TAG, "onSwiped: direction: "+ direction+ " position: "+ position);
            dealWithCompleted(position, Globals.getYear());
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            // used for up and down movements
            return false;
        } //onMove

        public void dealWithCompleted (int position, int year) {
            Log.d(TAG, "dealWithCompleted: year="+ year + " pos=" + position);
            SPMod spMod = new SPMod();
            switch (year){          //get the module...
                case 0:
                    spMod = sPModRepo.modListY0.get(position);
                    break;
                case 1:
                    spMod = sPModRepo.modListY1.get(position);
                    break;
                case 2:
                    spMod = sPModRepo.modListY2.get(position);
                    break;
                case 3:
                    spMod = sPModRepo.modListY3.get(position);
                    break;
                default:
                    Log.d(TAG, "dealWithCompleted: ******************** ERROR IN CASE Year not Valid");
            }
            spMod.setCompleted(true);       //we want to add a completed item
            sPModRepo.loadAppropriateModList(spMod);
            spMod.setCompleted(false);      //we want to remove a yet to complete item
            sPModRepo.removeAppropriateModList(spMod);
            //Globals.getPageViewModel().setModsYetToComp(year);
            pageViewModel.setModsYetToComp(year);
            pageViewModel.setModsCompleted(year);
            refreshDataLists();
        } //dealWithCompleted
    }

}