package com.inbm.chattingexample.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.inbm.chattingexample.R;
import com.inbm.chattingexample.groupchannel.GroupChannelListFragment;
import com.inbm.chattingexample.groupchannel.GroupChatFragment;
import com.inbm.chattingexample.inbm.AbsActivity;
import com.inbm.chattingexample.inbm.Fragmentable;

public class TabActivity extends AbsActivity implements Fragmentable {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main:
                Intent intent = new Intent(TabActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_tab;
    }

    @Override
    protected void buildUI() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);


        viewPager = findViewById(R.id.pager);
        viewPager = findViewById(R.id.pager);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);

        setTab();
        setPager();

//        String channelUrl = getIntent().getStringExtra("groupChannelUrl");
//        if (channelUrl != null) {
////            If started from notification
//            Fragment fragment = GroupChatFragment.newInstance(channelUrl);
//            FragmentManager manager = getSupportFragmentManager();
//            manager.beginTransaction()
//                    .replace(R.id.container_group_channel, fragment)
//                    .addToBackStack(null)
//                    .commit();
//        }
    }

    private void setTab() {
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void setPager() {
        adapter = new PagerAdapter
                (this, getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void fromFragment(String str) {
        Log.e("inbm", str);
    }


//          TODO Tab Activity 로 넘어감.

//}
//
//
//    interface onBackPressedListener {
//        boolean onBack();
//
//    }
//
//    private onBackPressedListener mOnBackPressedListener;
//
//    public void setOnBackPressedListener(onBackPressedListener listener) {
//        mOnBackPressedListener = listener;
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mOnBackPressedListener != null && mOnBackPressedListener.onBack()) {
//            return;
//        }
//        super.onBackPressed();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    void setActionBarTitle(String title) {
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(title);
//        }
//    }
}