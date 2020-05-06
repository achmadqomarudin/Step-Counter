package com.example.stepcounter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.stepcounter.util.API26Wrapper;
import com.example.stepcounter.util.Logger;

public class AppUpdatedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (BuildConfig.DEBUG) Logger.log("app updated");
        if (Build.VERSION.SDK_INT >= 26) {
            API26Wrapper.startForegroundService(context, new Intent(context, SensorListener.class));
        } else {
            context.startService(new Intent(context, SensorListener.class));
        }
    }
}
