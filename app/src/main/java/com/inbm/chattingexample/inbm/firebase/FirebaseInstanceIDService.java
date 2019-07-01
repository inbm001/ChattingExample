package com.inbm.chattingexample.inbm.firebase;

import android.widget.Toast;

import com.inbm.chattingexample.inbm.App;
import com.inbm.chattingexample.inbm._shared;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;


public class FirebaseInstanceIDService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseIIDService";

    // [START refresh_token]
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String deviceToken = s;
        _shared.setDeviceToken(s);
        App.firebase_token = deviceToken;
        sendToken2Server(s);
    }

    //TODO send token to server
    private void sendToken2Server(String token) {
        SendBird.registerPushTokenForCurrentUser(token, (pushTokenRegistrationStatus, e) -> {
            if (e != null) {
                App._toast("" + e.getCode() + ":" + e.getMessage());
                return;
            }
            if (pushTokenRegistrationStatus == SendBird.PushTokenRegistrationStatus.PENDING) {
                App._toast("Connection required to register push token.");
            }
        });
    }

}