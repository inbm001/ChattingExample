package com.inbm.chattingexample.groupchannel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.inbm.chattingexample.R;
import com.inbm.chattingexample.inbm.AbsFragment;


public class GroupChannelFragment extends AbsFragment {

    Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_group_channel, container, false);
        setHasOptionsMenu(true);
        toolbar = v.findViewById(R.id.toolbar_group_channel);
        //TODO Toolbar 교체 시 한번에
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_white);
//        }
        return super.onCreateView(inflater, container, savedInstanceState);

    }

}
