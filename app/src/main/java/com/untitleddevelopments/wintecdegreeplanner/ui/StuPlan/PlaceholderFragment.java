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
import com.untitleddevelopments.wintecdegreeplanner.DB.SPMod;
import com.untitleddevelopments.wintecdegreeplanner.R;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 * Author Geoff Genner
 * Works with the tabs near the top of the StuPlanActivity
 */
public class PlaceholderFragment extends Fragment {
    private static final String TAG = "PlaceholderFragment";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
//Geoffs vbls
    private RecyclerView recyclerVYetToComp;
    private SPRecycViewAdapt adapterYTC;
    private RecyclerView recyclerVComp;
    private SPRecycViewAdapt adapterComp;
    private TextView SPGeoffTV;
    private int yearIndex;

    public static PlaceholderFragment newInstance(int index) {
        Log.d(TAG, "Placeholder fragment New Instance index: "+Integer.toString(index));
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
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        yearIndex = index-1;
        //pageViewModel.setIndex(index);
        Log.d(TAG, "*onCreate: calling pageViewModelInit  ");
        pageViewModel.init();

        //pageViewModel.setmIndexText(Integer.toString(index));
        Log.d(TAG, "*onCreate: index =  " + Integer.toString(index));
        Globals.setYear(index);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView, yearIndex: " + Integer.toString(yearIndex) );
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
        //
        recyclerVYetToComp = view.findViewById(R.id.sp_recyclerv_yet_to_comp);
        adapterYTC = new SPRecycViewAdapt(getActivity(), pageViewModel.getModsYetToComp().getValue());
        recyclerVYetToComp.setAdapter(adapterYTC);
        recyclerVYetToComp.setLayoutManager(new LinearLayoutManager(getActivity()));
//        The follwing code is used to deal with swipes...
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToCompUncomp(adapterYTC));
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
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }
}