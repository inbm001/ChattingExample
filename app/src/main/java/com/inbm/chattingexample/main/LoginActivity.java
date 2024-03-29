package com.inbm.chattingexample.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.inbm.chattingexample.inbm.AbsActivity;
import com.inbm.chattingexample.inbm.App;
import com.inbm.chattingexample.inbm._actionbar;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.inbm.chattingexample.R;
import com.inbm.chattingexample.utils.PreferenceUtils;
import com.inbm.chattingexample.utils.PushUtils;

public class LoginActivity extends AbsActivity {

    private CoordinatorLayout mLoginLayout;
    private TextInputEditText mUserIdConnectEditText, mUserNicknameEditText;
    private Button mConnectButton;
    private ContentLoadingProgressBar mProgressBar;


    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void buildUI() {
        mLoginLayout = findViewById(R.id.layout_login);
        mUserIdConnectEditText = findViewById(R.id.edittext_login_user_id);
        mUserNicknameEditText = findViewById(R.id.edittext_login_user_nickname);
        mConnectButton = findViewById(R.id.button_login_connect);
        // A loading indicator
        mProgressBar = findViewById(R.id.progress_bar_login);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String id = PreferenceUtils.getUserId();
        String nick = PreferenceUtils.getNickname();
        if (!id.isEmpty()) mUserIdConnectEditText.setText(id);
        mUserIdConnectEditText.setText("");
        if (!nick.isEmpty()) mUserNicknameEditText.setText(nick);
        mUserIdConnectEditText.setText("");

        mConnectButton.setOnClickListener(v -> {
            String userId = mUserIdConnectEditText.getText().toString();
            // Remove all spaces from userID
            userId = userId.replaceAll("\\s", "");

            String userNickname = mUserNicknameEditText.getText().toString();

            PreferenceUtils.setUserId(userId);
            PreferenceUtils.setNickname(userNickname);

            connectToSendBird(userId, userNickname);

        });

        mUserIdConnectEditText.setSelectAllOnFocus(true);
        mUserNicknameEditText.setSelectAllOnFocus(true);


        // Display current SendBird and app versions in a TextView
        String sdkVersion = String.format(getResources().getString(R.string.all_app_version),
                BaseApplication.VERSION, SendBird.getSDKVersion());
        ((TextView) findViewById(R.id.text_login_versions)).setText(sdkVersion);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (PreferenceUtils.getConnected()) {
            connectToSendBird(PreferenceUtils.getUserId(), PreferenceUtils.getNickname());
        }
    }

    /**
     * Attempts to connect a user to SendBird.
     *
     * @param userId       The unique ID of the user.
     * @param userNickname The user's nickname, which will be displayed in chats.
     */
    private void connectToSendBird(final String userId, final String userNickname) {
        // Show the loading indicator
        showProgressBar(true);
        mConnectButton.setEnabled(false);

        ConnectionManager.login(userId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                // Callback received; hide the progress bar.
                showProgressBar(false);

                if (e != null) {
                    // Error!
                    Toast.makeText(
                            LoginActivity.this, "" + e.getCode() + ": " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    // Show login failure snackbar
                    showSnackbar("Login to SendBird failed");
                    mConnectButton.setEnabled(true);
                    PreferenceUtils.setConnected(false);
                    return;
                }

                PreferenceUtils.setConnected(true);

                // Update the user's nickname
                updateCurrentUserInfo(userNickname);
                updateCurrentUserPushToken();

                // Proceed to MainActivity
                Intent intent = new Intent(LoginActivity.this, TabActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Update the user's push token.
     */
    private void updateCurrentUserPushToken() {
        PushUtils.registerPushTokenForCurrentUser(LoginActivity.this, null);
    }

    /**
     * Updates the user's nickname.
     *
     * @param userNickname The new nickname of the user.
     */
    private void updateCurrentUserInfo(final String userNickname) {
        SendBird.updateCurrentUserInfo(userNickname, null, new SendBird.UserInfoUpdateHandler() {
            @Override
            public void onUpdated(SendBirdException e) {
                if (e != null) {
                    // Error!
                    Toast.makeText(
                            LoginActivity.this, "" + e.getCode() + ":" + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    // Show update failed snackbar
                    showSnackbar("Update user nickname failed");

                    return;
                }

                PreferenceUtils.setNickname(userNickname);
            }
        });
    }

    // Displays a Snackbar from the bottom of the screen
    private void showSnackbar(String text) {
        Snackbar snackbar = Snackbar.make(mLoginLayout, text, Snackbar.LENGTH_SHORT);

        snackbar.show();
    }

    // Shows or hides the ProgressBar
    private void showProgressBar(boolean show) {
        if (show) {
            mProgressBar.show();
        } else {
            mProgressBar.hide();
        }
    }
}
