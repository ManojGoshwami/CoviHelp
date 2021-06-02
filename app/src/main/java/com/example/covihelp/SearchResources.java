package com.example.covihelp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class SearchResources extends AppCompatActivity {
    WebView anotherWeb;
    ImageView imgBack;
    private AdView mAdView;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_search_resources );
        imgBack=findViewById(R.id.backWebResource);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });


        final ProgressDialog progressBar = new ProgressDialog(SearchResources.this);
        progressBar.setMessage("Please wait...");
        anotherWeb=findViewById ( R.id.anotherWeb );
        WebSettings webSettings=anotherWeb.getSettings ();
        webSettings.setJavaScriptEnabled ( true );
        anotherWeb.setWebViewClient ( new Callback () );
        anotherWeb.loadUrl ( "https://verifiedcovidleads.com/" );
        anotherWeb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!progressBar.isShowing()) {
                    progressBar.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
        });


        MobileAds.initialize(this, new OnInitializationCompleteListener () {
            @Override
            public void onInitializationComplete( InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adSearchResource);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private class Callback extends WebViewClient
    {
        @Override
        public boolean shouldOverrideKeyEvent ( WebView view , KeyEvent event ) {
            return false;
        }
    }
}