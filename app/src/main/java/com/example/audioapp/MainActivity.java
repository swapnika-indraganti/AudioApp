package com.example.audioapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.audioapp.R;  // Ensure this matches your package name


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView webDisplayer;

    public static WebView wv;
    public static Bitmap img;
    public static String webTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webDisplayer = findViewById(R.id.webview);
        webDisplayer.loadUrl("https://www.jiosaavn.com");

        webDisplayer.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    String code = "var mediaElement;" +
                            "mediaCheck();" +
                            "document.onclick = function(){" +
                            "    mediaCheck();" +
                            "};" +
                            "function mediaCheck(){" +
                            "    for(var i = 0; i < document.getElementsByTagName('audio').length; i++){" +
                            "        var media = document.getElementsByTagName('audio')[i];" +
                            "        media.onplay = function(){" +
                            "            mediaElement = media;" +
                            "            JSOUT.mediaAction('true');" +
                            "        };" +
                            "        media.onpause = function(){" +
                            "            mediaElement = media;" +
                            "            JSOUT.mediaAction('false');" +
                            "        };" +
                            "    }" +
                            "}";
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
        webDisplayer.getSettings().setDomStorageEnabled(true);
        webDisplayer.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webDisplayer.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webDisplayer.addJavascriptInterface(new JSInterface(this), "JSOUT");
        wv = webDisplayer;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveBackToPreviousPage();
    }

    private void moveBackToPreviousPage() {
        if (webDisplayer.canGoBack()) {
            webDisplayer.goBack();
        } else {
            finish();
        }
    }
}
