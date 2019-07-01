package com.inbm.chattingexample.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.inbm.chattingexample.groupchannel.SelectableUserListAdapter;
import com.inbm.chattingexample.inbm.AbsActivity;
import com.sendbird.android.SendBird;
import com.sendbird.android.User;
import com.sendbird.android.UserListQuery;
import com.inbm.chattingexample.R;

import java.util.ArrayList;
import java.util.List;

public class BlockedMembersListActivity extends AbsActivity {

    private static final int STATE_NORMAL = 0;
    private static final int STATE_EDIT = 1;

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private SelectableUserListAdapter mListAdapter;
    private UserListQuery mUserListQuery;

    private Button mButtonEdit, mButtonUnblock;

    private List<String> mSelectedIds;
    private int mCurrentState;

    private Toolbar mToolbar;


    @Override
    protected int getLayout() {
        return R.layout.activity_blocked_members_list;
    }

    @Override
    protected void buildUI() {
        mButtonEdit = findViewById(R.id.button_edit);
        mButtonUnblock = findViewById(R.id.button_unblock);
        mToolbar = findViewById(R.id.toolbar_blocked_members_list);
        mRecyclerView = findViewById(R.id.recycler_select_user);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectedIds = new ArrayList<>();
        setToolbar(mToolbar);
        setButtons();
        setRV();
        loadInitialUserList(15);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_white);
        }
    }


    /**
     *  버튼 초기화
     */
    private void setButtons(){
        mButtonEdit.setOnClickListener(v -> setState(STATE_EDIT));

        mButtonUnblock.setOnClickListener(v -> {
            mListAdapter.unblock();
            setState(STATE_NORMAL);
        });

        mButtonEdit.setEnabled(false);
        mButtonUnblock.setEnabled(false);

    }

    /**
     * 수정 / 노말 상태 조정
     *
     * @param state 상태
     */
    void setState(int state) {
        if (state == STATE_EDIT) {
            mCurrentState = STATE_EDIT;
            mButtonUnblock.setVisibility(View.VISIBLE);
            mButtonEdit.setVisibility(View.GONE);
            mListAdapter.setShowCheckBox(true);
        } else if (state == STATE_NORMAL) {
            mCurrentState = STATE_NORMAL;
            mButtonUnblock.setVisibility(View.GONE);
            mButtonEdit.setVisibility(View.VISIBLE);
            mListAdapter.setShowCheckBox(false);
        }
    }


    /**
     * 유저 클릭
     *
     * @param selected 클릭 유무
     * @param userId 유저 아이디
     */
    public void userSelected(boolean selected, String userId) {
        if (selected) {
            mSelectedIds.add(userId);
        } else {
            mSelectedIds.remove(userId);
        }
        if (mSelectedIds.size() > 0) mButtonUnblock.setEnabled(true);
        else mButtonUnblock.setEnabled(false);
    }


    /**
     * RecyclerView 셋팅부분
     *
     * TODO Inbm RecyclerView
     */
    private void setRV() {
        mListAdapter = new SelectableUserListAdapter(this, true, false);
        mListAdapter.setItemCheckedChangeListener((user, checked) -> {
            if (checked) {
                userSelected(true, user.getUserId());
            } else {
                userSelected(false, user.getUserId());
            }
        });
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mListAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (mLayoutManager.findLastVisibleItemPosition() == mListAdapter.getItemCount() - 1) {
                    loadNextUserList(10);
                }
            }
        });
    }




    private void loadInitialUserList(int size) {
        mUserListQuery = SendBird.createBlockedUserListQuery();

        mUserListQuery.setLimit(size);
        mUserListQuery.next((list, e) -> {
            if (e != null) {
                // Error!
                return;
            }

            mListAdapter.setUserList(list);
            mButtonEdit.setEnabled(list.size() > 0);
        });
    }

    private void loadNextUserList(int size) {
        mUserListQuery.setLimit(size);

        mUserListQuery.next((list, e) -> {
            if (e != null) {
                // Error!
                return;
            }

            for (User user : list) {
                mListAdapter.addLast(user);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mCurrentState == STATE_EDIT) {
            setState(STATE_NORMAL);
        } else {
            super.onBackPressed();
        }
    }

    public void blockedMemberCount(int size) {
        mButtonEdit.setEnabled(size > 0);
    }
}
