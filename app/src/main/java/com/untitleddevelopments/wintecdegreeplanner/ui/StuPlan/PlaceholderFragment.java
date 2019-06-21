package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;

import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.DB.Module;
import com.untitleddevelopments.wintecdegreeplanner.DB.PreReq;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPMod;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPModRepo;
import com.untitleddevelopments.wintecdegreeplanner.R;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;

import java.util.ArrayList;
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
    } //PlaceholderFragment static instance

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //instantiate the view model
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        int index = 1;                          //index is used by tabs
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        Log.d(TAG, "*onCreate: calling pageViewModel InitMuts. FYI index =  " + index);
        pageViewModel.initMutables();
        yearIndex = index-1;            //
        //Globals.setPageViewModel(pageViewModel);
    } //onCreate

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView, calling setMods methods: " + yearIndex );

        pageViewModel.setModsYetToComp(yearIndex);
        pageViewModel.setModsCompleted(yearIndex);

        View view = inflater.inflate(R.layout.fragment_stu_plan, container, false);

        // ********************************   Deal to yet to complete...
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

//        The follwing code is used to deal with swipes TO THE LEFT
        ItemTouchHelper itemTouchHlpLeft = new
                ItemTouchHelper(new SwipeToComplete(adapterYTC));
        itemTouchHlpLeft.attachToRecyclerView(recyclerVYetToComp);

        // ********************************   Deal to completed Modules

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
//        The follwing code is used to deal with swipes TO THE RIGHT
        ItemTouchHelper itemTouchHlpRight = new
                ItemTouchHelper(new SwipeToUnComp(adapterComp));
        itemTouchHlpRight.attachToRecyclerView(recyclerVComp);

        return view;
    } //onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Log.d(TAG, "onViewCreated yearIndex:"+Integer.toString(yearIndex));
        super.onViewCreated(view, savedInstanceState);

    } //onViewCreated

    public void setUserVisibleHint(boolean isVisibleToUser) {
//        This is used to refresh the fragment as we enter so our lists are up to date
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            refreshDataLists();
        }
    } //setUserVisibleHint

    public void refreshDataLists() {
        Log.d(TAG, "--------- refreshDataLists-----------" );
        if(adapterComp != null ) recyclerVComp.setAdapter(adapterComp);
        if(adapterYTC != null) recyclerVYetToComp.setAdapter(adapterYTC);
    }

    @Override
    public void onResume() {
        super.onResume();
    } //onResume

//    ******************************************* Deal with Left Swipe Complete ******************************************
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
//            dealWithCompleted(position, Globals.getYear());
            SPMod spMod = new SPMod();
            switch (Globals.getYear()){          //get the module we have swiped as complete
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
                    Log.d(TAG, "on swiped left : ******************** ERROR IN CASE Year not Valid");
            }
            if(spMod.getLocked()) {
                displayToast("This course cannot be completed until it's pre-requisites have been completed");
            } else {
                //process the change from incomplete to complete
                sPModRepo.removeAppropriateModList(spMod);
                spMod.setCompleted(true);       //we want to add a completed item
                sPModRepo.loadAppropriateModList(spMod);
                sPModRepo.updateDBStuMod(spMod);

                //*** now we need to check all other courses which were previously locked to see if we have to unlock
                ArrayList<Module> parents = SPModRepo.getParentsOf(spMod.getModule_ID());
                //parents is a list of modules that depend on the current module we have just swiped
                for (Module modSingle : parents){
                    //now check if each of the parents prereqs to see if they have been completed
                    ArrayList<PreReq> preReqs = SPModRepo.getPreReqs(modSingle.getModule_ID());
                    if(SPModRepo.arePreReqsDone(preReqs)){
                        //we can unlock the modsingle. First we need to find the modsingle. Given that we have just completed
                        //a module its parents must be in yet to complete
                        //TODO upto here

                    }

                }

            }
            refreshDataLists();
        } //onSwiped

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            // used for up and down movements
            return false;
        } //onMove

    } //deal Swipe left to complete finished

//    ******************************************* Deal with Right Swipe Undo ******************************************

    public class SwipeToUnComp extends ItemTouchHelper.SimpleCallback {
        private SPModRepo sPModRepo;            //Create reference to Geoffs Data repo
        private static final String TAG = "SwipeToUnComp";
        private SPRecycViewAdapt mAdapter;
        public SwipeToUnComp(SPRecycViewAdapt adapter) {
            super(0, ItemTouchHelper.RIGHT);
            //super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            sPModRepo = SPModRepo.getInstance();
            mAdapter = adapter;
        }
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Log.d(TAG, "onSwiped: direction: "+ direction+ " position: "+ position);
//            dealWithCompleted(position, Globals.getYear());
            SPMod spMod = new SPMod();
            switch (Globals.getYear()){          //get the module we have swiped to undo
                case 0:
                    spMod = sPModRepo.modListC0.get(position);
                    break;
                case 1:
                    spMod = sPModRepo.modListC1.get(position);
                    break;
                case 2:
                    spMod = sPModRepo.modListC2.get(position);
                    break;
                case 3:
                    spMod = sPModRepo.modListC3.get(position);
                    break;
                default:
                    Log.d(TAG, "on swipe right ******************** ERROR IN CASE Year not Valid");
            }

            sPModRepo.removeAppropriateModList(spMod);
            spMod.setCompleted(false);       //we want to add a yet to complete
            sPModRepo.loadAppropriateModList(spMod);
            sPModRepo.updateDBStuMod(spMod);


            //Globals.getPageViewModel().setModsYetToComp(year);
//            pageViewModel.setModsYetToComp(spMod.getYear());
//            pageViewModel.setModsCompleted(spMod.getYear());
            //refreshDataLists();
            refreshDataLists();

            sPModRepo.updateDBStuMod(spMod);
        } //onSwiped

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            // used for up and down movements
            return false;
        } //onMove

//        public void dealWithCompleted (int position, int year) {
//            Log.d(TAG, "dealWithCompleted: year="+ year + " pos=" + position);
    } //dealWith Swipe Right tio undo finished

    private void displayToast(String message){
        //this is used for debugging
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
} //Pageholder Fragment

