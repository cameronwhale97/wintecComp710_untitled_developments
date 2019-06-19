package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.untitleddevelopments.wintecdegreeplanner.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.Tab_All, R.string.Tab_Year1, R.string.Tab_Year2, R.string.Tab_Year3};
    private final Context mContext;
    private static final String TAG = "SectionsPagerAdapter";

    //ToDo remove code below
    private Fragment mCurrentFragment;

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
//Down to here

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
//TODO remove this method
//    @Override
//    public void setPrimaryItem(ViewGroup container, int position, Object object) {
//        if (getCurrentFragment() != object) {
//            mCurrentFragment = ((Fragment) object);
//        }
//        super.setPrimaryItem(container, position, object);
//    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(TAG, "getPageTitle: " + mContext.getResources().getString(TAB_TITLES[position] ));
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}