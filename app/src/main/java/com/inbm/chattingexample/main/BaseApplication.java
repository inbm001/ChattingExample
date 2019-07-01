package com.inbm.chattingexample.main;


import com.inbm.chattingexample.inbm.App;
import com.inbm.chattingexample.utils.PreferenceUtils;
import com.sendbird.android.SendBird;

public class BaseApplication extends App {

    private static final String APP_ID = "28D0C620-5CA7-4ACD-8DB6-18025FC1C4D2"; // US-1 Demo
    public static final String VERSION = "3.0.40";

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtils.init(getApplicationContext());
        SendBird.init(APP_ID, getApplicationContext());

    }
}
