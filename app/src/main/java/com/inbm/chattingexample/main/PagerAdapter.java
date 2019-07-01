package com.inbm.chattingexample.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.inbm.chattingexample.R;
import com.inbm.chattingexample.groupchannel.GroupChannelListFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Context context;

    FragmentManager fm;

    public PagerAdapter(Context context, FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.context = context;
        this.fm = fm;
        this.mNumOfTabs = NumOfTabs;

//
////        if (savedInstanceState == null) {
//        // Load list of Group Channels
//
//
//        fm.beginTransaction()
//                .replace(R.id.container_group_channel, fragment)
//                .commit();
////        }
    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:
                Fragment fragment = GroupChannelListFragment.newInstance();
                fm.popBackStack();
                return fragment;
            case 1:
                TabFragment2 tab2 = new TabFragment2(context);
                return tab2;
            case 2:
                TabFragment3 tab3 = new TabFragment3(context);
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}