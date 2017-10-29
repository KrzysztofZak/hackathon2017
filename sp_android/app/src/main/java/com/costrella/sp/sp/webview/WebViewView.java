package com.costrella.sp.sp.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.costrella.sp.sp.R;


public class WebViewView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webview = new WebView(getApplicationContext());
        setContentView(webview);

        webview.loadUrl("https://www.szlachetnapaczka.pl/wplacam");
    }
}
