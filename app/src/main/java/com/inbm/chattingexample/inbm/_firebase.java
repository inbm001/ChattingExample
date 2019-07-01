package com.inbm.chattingexample.inbm;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

public class _firebase {
    public static void setToken(Context context) {
        FirebaseApp.initializeApp(context);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
            String token = instanceIdResult.getToken();
            _shared.setDeviceToken(token);
            App.firebase_token = token;
            _log.e(instanceIdResult.getToken());
        });
    }

}
