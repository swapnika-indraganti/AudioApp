package com.example.audioapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            MainActivity.wv.evaluateJavascript("mediaElement.click();mediaElement.dispatchEvent(new TouchEvent('touchstart', { bubbles: true, cancelable: true }));" +
                    "mediaElement.dispatchEvent(new TouchEvent('touchend', { bubbles: true, cancelable: true }));", null);
    }
}
