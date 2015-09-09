package com.khalifacomputer.speechtosign;

/**
 * Created by hany on 10/08/2015.
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import  com.khalifacomputer.speechtosign.JSONVParser;


public class video_player extends Activity {

    public VideoView myVideoView;
    private int position = 0;
    String CurrentUrl;
    private ProgressDialog progressDialog;
  //  private MediaController mediaControls;
   //ArrayList<String> words = new ArrayList<String>();
    ArrayList<String> videoos = new ArrayList<String>();

    String ThisWord;
    //int current_word=0;
    static  int current_video=0;
    private static final String TAG_WORDS = "videos";
    private static final String TAG_WORD= "vpath";
    JSONArray android = null;

    //private static String url = "http://127.0.0.1/takhtop_backend/hp/video.php";
    private static String url = "http://khalifacomputer.com/tamkeen/tamkeen1/words/php/video.php";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the main layout of the activity
        setContentView(R.layout.video_player);

        ThisWord=getIntent().getExtras().getString("SentText");

    }
protected  void onStart()
{
   super.onStart();
    new JSONVParse().execute();

}
     protected  void  GoBack()
    {
        Intent myIntent = new Intent(this,MainActivity.class);

        //Log.i("EDIT_TEXT", txtText.getText().toString());
        startActivity(myIntent);
        finish();
    }

    private class JSONVParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //RTV = (TextView) findViewById(R.id.ResultTV);
            //name = (TextView) findViewById(R.id.status);
            // api = (TextView) findViewById(R.id.alt);
            pDialog = new ProgressDialog(video_player.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            JSONVParser jParser = new JSONVParser();
           // String phr = edttxt.getText().toString();
            // Getting JSON from URL
            JSONObject jsonv = jParser.getJSONVFromUrl(url, ThisWord);
            return jsonv;
        }

        @Override
        protected void onPostExecute(JSONObject jsonv) {
            pDialog.dismiss();
            //RTV.setText("Fail");
            Log.i("onPostExecute-->","I'm Here");
            if (jsonv != null) {
                try {
                    // mylist.clear();
                    // Getting JSON Array from URL
                    android = jsonv.getJSONArray(TAG_WORDS);
                  videoos.clear();
                    for (int i = 0; i < android.length(); i++) {
                        JSONObject c = android.getJSONObject(i);

                        // Storing  JSON item in a Variable
                        String ver = c.getString(TAG_WORD);
                       Log.i("ver",ver);
                        videoos.add(i,ver);
                        // mylist.add(ver);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
              if(videoos.size()>0) {
                  current_video=0;
                  //String url = videoos.get(current_video).toString();
                  String url ="";
                  for (int i=0;i<videoos.size();i++)  if(i==0)

                      url=url+videoos.get(i).toString().replace("http://khalifacomputer.com/tamkeen/tamkeen1/words/res/video/","");
                       else url=url+"~" +videoos.get(i).toString().replace("http://khalifacomputer.com/tamkeen/tamkeen1/words/res/video/","");
          Intent myIntent = new Intent(getApplicationContext(),playit.class);

                  myIntent.putExtra("SentText", url);

                  startActivity(myIntent);
                  finish();
                  //startActivityForResult(myIntent,0);

              }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("CheckStartActivity", "onActivityResult and resultCode = " + resultCode);
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // A contact was picked.  Here we will just display it
            // to the user.if(videoos.size()>0) {
            current_video=current_video+1;
            if(current_video<videoos.size()-1) {
                current_video = 0;
                String url = videoos.get(current_video).toString();
                 Log.i("sent url---->",url);

                Intent myIntent = new Intent(getApplicationContext(), playit.class);

                myIntent.putExtra("SentText", url);

                //startActivity(myIntent);
                startActivityForResult(myIntent, 0);
            }
            else GoBack();
        }


    }

}