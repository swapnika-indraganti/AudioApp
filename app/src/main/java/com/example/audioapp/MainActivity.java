package com.example.audioapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.audioapp.R;  // Ensure this matches your package name


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private WebView webDisplayer;

    public static WebView wv;
    public static Bitmap img;
    public static String webTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        askNotificationPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            askNotificationPermission();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void askNotificationPermission() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED
            ) {
                setContentView(R.layout.activity_main);
                webDisplayer = findViewById(R.id.webview);
                webDisplayer.setWebViewClient(new WebViewClient());
                webDisplayer.getSettings().setUserAgentString(webDisplayer.getSettings().getUserAgentString().replace("; wv", ""));
                webDisplayer.loadUrl("https://listenbeyond.com/app/full-audiobook");
                webDisplayer.getSettings().setUserAgentString(webDisplayer.getSettings().getUserAgentString().replace("; wv", ""));

                webDisplayer.setWebChromeClient(new WebChromeClient() {

                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        super.onProgressChanged(view, newProgress);
                        if (newProgress == 100) {
                            String code = "var mediaElement;" +
                                    "setTimeout(() => {" +
                                    "console.log('Executing in timeout');" +
                                    "mediaCheck();" +
                                    "document.onclick = function(){" +
                                    "    console.log('REceived document onClick');" +
                                    "    mediaCheck();" +
                                    "};" +
                                    "function mediaCheck(){" +
                                    "    console.log('Length of app-list-view ', document.getElementsByTagName('app-list-view').length);" +
                                    "    for(var i = 0; i < document.getElementsByTagName('app-list-view').length; i++){" +
                                    "       document.getElementsByTagName('app-list-view')[i].getElementsByClassName('list__item')[0].onclick = () => { " +
                                    "           console.log('Triggered onClick of listview');" +
                                    "           var playPause = document.getElementsByClassName('amplitude-play-pause');" +
                                    "           if (playPause.length > 0) {" +
                                    "               console.log('playpause length greater than 0');" +
                                    "               mediaElement = playPause[0];" +
                                    "               playPause[0].onclick = () => {" +
                                    "                   console.log('Received onClick of playpause');" +
                                    "                   JSOUT.mediaAction();" +
                                    "               }" +
                                    "           }" +

                                    "           JSOUT.clickedOnItem();" +
                                    "       };" +
                                    "    }" +
                                    "}}, 1000)";
                            webDisplayer.evaluateJavascript(code, null);
                        }
                    }

                    @Override
                    public void onReceivedIcon(WebView view, Bitmap icon) {
                        super.onReceivedIcon(view, icon);
                        img = icon;
                    }

                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        webTitle = title;
                    }
                });

                webDisplayer.getSettings().setLoadsImagesAutomatically(true);
                webDisplayer.getSettings().setJavaScriptEnabled(true);
                webDisplayer.getSettings().setBuiltInZoomControls(true);
                webDisplayer.getSettings().setDomStorageEnabled(true);
                webDisplayer.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                webDisplayer.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webDisplayer.addJavascriptInterface(new JSInterface(this), "JSOUT");
                webDisplayer.setClickable(true);
                wv = webDisplayer;
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                }
            }
        }
}
