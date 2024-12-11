package com.example.audioapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class JSInterface {

    private Context mContext;
    static boolean playing = false;

    JSInterface(Context context) {
        this.mContext = context;
    }

    @JavascriptInterface
    public void mediaAction() {
        playing = !playing;
        if (playing) {
            createNotificationChannel();

            RemoteViews expandedView = new RemoteViews(mContext.getPackageName(), R.layout.notification_ui_expanded);

            expandedView.setImageViewBitmap(R.id.icon, MainActivity.img);
            expandedView.setTextViewText(R.id.title, "Song Playing");
            expandedView.setTextViewText(R.id.desc, MainActivity.webTitle);
            expandedView.setImageViewResource(R.id.pausePlay, R.drawable.ic_baseline_pause_24);

            PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(
                    mContext, 0, new Intent(
                            mContext,
                            NotificationListener.class
                    ), PendingIntent.FLAG_MUTABLE
            );
            expandedView.setOnClickPendingIntent(R.id.pausePlay, pendingSwitchIntent);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "0")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setCustomContentView(expandedView)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManager notificationManager =
                    (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, builder.build());

        } else {
            createNotificationChannel();

            RemoteViews expandedView = new RemoteViews(mContext.getPackageName(), R.layout.notification_ui_expanded);
            expandedView.setImageViewBitmap(R.id.icon, MainActivity.img);
            expandedView.setTextViewText(R.id.title, "Song Paused");
            expandedView.setTextViewText(R.id.desc, MainActivity.webTitle);
            expandedView.setImageViewResource(R.id.pausePlay, R.drawable.ic_baseline_play_arrow_24);

            PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(
                    mContext, 0, new Intent(
                            mContext,
                            NotificationListener.class
                    ), PendingIntent.FLAG_MUTABLE
            );
            expandedView.setOnClickPendingIntent(R.id.pausePlay, pendingSwitchIntent);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "0")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setCustomContentView(expandedView)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManager notificationManager =
                    (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, builder.build());
        }
    }

    @JavascriptInterface
    public void clickedOnItem() {
        playing = false;
        mediaAction();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    "0",
                    "Demo",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = mContext.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

}
