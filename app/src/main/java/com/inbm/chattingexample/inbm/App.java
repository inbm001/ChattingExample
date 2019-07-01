package com.inbm.chattingexample.inbm;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.FirebaseApp;
import com.inbm.chattingexample.utils.PreferenceUtils;
import com.sendbird.android.SendBird;

/**
 * Created by inbm on 2017. 2. 27..
 */
public class App extends Application {
    private static Context context;

    public static String firebase_token;

    private static final String APP_ID = "9DA1B1F4-0BE6-4DA8-82C5-2E81DAB56F23"; // US-1 Demo
    public static final String VERSION = "3.0.40";

    @Override
    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();

        //this is to ignore file expose in taking pictures from camera. another solution is using FileProvider
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //firebase Setting
        _firebase.setToken(context);

    }

//    @Override public void onLowMemory() {
//        super.onLowMemory();
//        Glide.get(this).clearMemory();
//    }
//
//    @Override public void onTrimMemory(int level) {
//        super.onTrimMemory(level);
//        Glide.get(this).trimMemory(level);
//    }

    public static Context getStaticContext() {
        return App.context;
    }

    public static RequestManager getGlideManger() {
        return Glide.with(getStaticContext());
    }

    public static String getFirebase_token() {
        return firebase_token;
    }

    public static void _toast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void _toast(int id) {
        Toast.makeText(context, context.getText(id), Toast.LENGTH_LONG).show();
    }
}