package com.khalifacomputer.speechtosign;

/**
 * Created by hany on 05/08/2015.
 */
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;

import com.khalifacomputer.speechtosign.JSONParser;
public class CorrectPharase extends Activity {
    int startfrom = 0;
    TextView senttxt;
    Button BtnExit,BtnCorrect;
    RadioButton Rad1, Rad2, Rad3;
    TextView alerttxt;
    String CurrentWord;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
    boolean correctflag = false;
    //URL to get JSON Array

   // private static String url = "http://127.0.0.1/takhtop_backend/php/phrase2.php";
    private static String url = "http://khalifacomputer.com/tamkeen/tamkeen1/words/php/phrase2.php";

    //JSON Node Names
    private static final String TAG_WORDS = "words";
    private static final String TAG_WORD = "word";
    private static final String TAG_STATUS = "status";
    private static final String TAG_ALT = "alternates";

    JSONArray android = null;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.correction);
        oslist = new ArrayList<HashMap<String, String>>();
        senttxt = (TextView) findViewById(R.id.senttxt);
        //Btngetdata = (Button) findViewById(R.id.getdata);
        Rad1 = (RadioButton) findViewById(R.id.choice1);
        Rad2 = (RadioButton) findViewById(R.id.choice2);
        Rad3 = (RadioButton) findViewById(R.id.choice3);
        alerttxt=(TextView)  findViewById(R.id.alerttxt);
        BtnCorrect=(Button)findViewById(R.id.buttontrans);
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

        BtnCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Go
                if(BtnCorrect.getText().toString().indexOf("تصحيح")>=0)  {
                    if(Rad1.isChecked() && Rad1.getVisibility() == View.VISIBLE) {String ss= senttxt.getText().toString();
                                   ss= ss.replace(CurrentWord, Rad1.getText().toString());senttxt.setText(ss);
                        oslist.get(startfrom-1).put(TAG_WORD,Rad1.getText().toString());
                    }
                    if(Rad2.isChecked() && Rad2.getVisibility() == View.VISIBLE) {String ss= senttxt.getText().toString();
                       ss= ss.replace(CurrentWord, Rad2.getText().toString()); senttxt.setText(ss);
                        oslist.get(startfrom-1).put(TAG_WORD,Rad2.getText().toString());
                    }
                    if(Rad3.isChecked() && Rad3.getVisibility() == View.VISIBLE) {String ss= senttxt.getText().toString();
                        ss=ss.replace(CurrentWord, Rad3.getText().toString()); senttxt.setText(ss);
                        oslist.get(startfrom-1).put(TAG_WORD,Rad3.getText().toString());
                    }
                    FindAlt(startfrom);
                }
                else { String cc="";
                    for(int i=0;i<oslist.size();i++)
                         cc=cc+oslist.get(i).get(TAG_WORD)+",";
                    cc=cc.substring(0,cc.length()-1);
                    Intent myIntent = new Intent(v.getContext(), video_player.class);
                    myIntent.putExtra("SentText", cc);
                    //Log.i("EDIT_TEXT", txtText.getText().toString());
                    startActivity(myIntent);
                    finish();

                }

            }

        });


        //        Btngetdata.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//       public void onClick(View view) {
//            new JSONParse().execute();
//
//         }
//    });

    }

    @Override
    protected void onStart() {
        super.onStart();
        new JSONParse().execute();
    }


    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // ver = (TextView) findViewById(R.id.word);
            // name = (TextView) findViewById(R.id.status);
            // api = (TextView) findViewById(R.id.alt);
            pDialog = new ProgressDialog(CorrectPharase.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            JSONParser jParser = new JSONParser();
            String phr = getIntent().getExtras().getString("SentText");
            senttxt.setText(phr);
           // Log.i("Sent Text:", phr);
            // Getting JSON from URL

            JSONObject json = jParser.getJSONFromUrl(url, phr);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array from URL
                android = json.getJSONArray(TAG_WORDS);
                for (int i = 0; i < android.length(); i++) {
                    JSONObject c = android.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String ver = c.getString(TAG_WORD);
                    String name = c.getString(TAG_STATUS);
                    String api = c.getString(TAG_ALT);

                    // Adding value HashMap key => value

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_WORD, ver);
                    map.put(TAG_STATUS, name);
                    map.put(TAG_ALT, api);

                    oslist.add(map);
                    //   list = (ListView) findViewById(R.id.list);

                    // ListAdapter adapter = new SimpleAdapter(MainActivity.this, oslist,
                    //         R.layout.list_v,
                    //         new String[]{TAG_WORD, TAG_STATUS, TAG_ALT}, new int[]{
                    //         R.id.word, R.id.status, R.id.alt});

                    // list.setAdapter(adapter);
                    // list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    //     @Override
                    //     public void onItemClick(AdapterView<?> parent, View view,
                    //                             int position, long id) {
                    //         Toast.makeText(MainActivity.this, "You Clicked at " + oslist.get(+position).get("name"), Toast.LENGTH_SHORT).show();

                    //    }
                    //});

                }

                FindAlt(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    protected void FindAlt(int sf) {
        boolean flag = false;
        int i;
        i = sf;
        Rad1.setVisibility(View.INVISIBLE);
        Rad2.setVisibility(View.INVISIBLE);
        Rad3.setVisibility(View.INVISIBLE);
        alerttxt.setVisibility(View.INVISIBLE);

        //Log.i("List Size-->> ", Integer.toString(oslist.size()));
        while (i < oslist.size() && !flag) {
                 if (oslist.get(i).get(TAG_STATUS).indexOf("yes")>=0) {
                    flag = true;
                CurrentWord=oslist.get(i).get(TAG_WORD).trim();
                String ss = oslist.get(i).get(TAG_ALT).trim();
                String[] sss;
                if (ss.indexOf(",") > 0) {
                    sss = ss.split(",");
                    Rad1.setText(sss[0]);
                    Rad1.setVisibility(View.VISIBLE);

                    Rad2.setText(sss[1]);
                    Rad2.setVisibility(View.VISIBLE);
                    if (sss.length > 2) {
                        Rad3.setText(sss[2]);
                        Rad3.setVisibility(View.VISIBLE);
                    }

                    alerttxt.setText("المقصود بكلمة:" +CurrentWord);
                    alerttxt.setVisibility(View.VISIBLE);
                    BtnCorrect.setVisibility(View.VISIBLE);

                }

                else if (ss.trim().equals(CurrentWord.trim()) ) {
                    startfrom=i+1;
                    FindAlt(startfrom);
                }

                else
                {
                    Rad1.setText(ss.trim());
                    Rad1.setVisibility(View.VISIBLE);
                    Rad2.setText(CurrentWord.trim());
                    Rad2.setVisibility(View.VISIBLE);

                    BtnCorrect.setVisibility(View.VISIBLE);
                }
                startfrom=i+1;
            } else i++;
            if(flag==false)  {alerttxt.setVisibility(View.INVISIBLE); BtnCorrect.setText("ترجمة للغة الإشارة");BtnCorrect.setVisibility(View.VISIBLE); }
        }

    }


}
