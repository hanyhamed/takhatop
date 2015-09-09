package com.khalifacomputer.speechtosign;

import android.app.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by hany on 19/08/2015.
 */

public class SoundService extends Activity {
    Button BtnExit;
    WebView WView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_service);
        WView=(WebView) findViewById(R.id.WView);

        Log.i("---->","On create");
        BtnExit = (Button) findViewById(R.id.buttonexit);
        BtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Go
                Intent myIntent = new Intent(v.getContext(),MainActivity.class);

                //Log.i("EDIT_TEXT", txtText.getText().toString());
                startActivity(myIntent);
                finish();

            }

        });


    }
    @Override
    protected void onStart()
    {
        Log.i("---->","On start");
        super.onStart();
   
        WView.setWebChromeClient(new WebChromeClient());
        WView.getSettings().setJavaScriptEnabled(true);
        WView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        WView.getSettings().setDomStorageEnabled(true);
       // WView.getSettings().setAllowFileAccessFromFileURLs(true);
        WView.getSettings().setJavaScriptEnabled(true);


// Added in API level 8
        WView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //WView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        String url=getIntent().getExtras().getString("SentText").toString();
        Log.i("url---->",url);
        WView.loadUrl(url);

    }
}
