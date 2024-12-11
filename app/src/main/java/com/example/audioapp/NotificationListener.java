package com.example.audioapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (JSInterface.playing) {
            MainActivity.wv.evaluateJavascript("mediaElement.pause();", null);
        } else {
            MainActivity.wv.evaluateJavascript("mediaElement.play();", null);
        }
    }
}
