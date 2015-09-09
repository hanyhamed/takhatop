package com.khalifacomputer.getvideohttp_path;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.khalifacomputer.getvideohttp_path.JSONParser;

public class MainActivity extends Activity {

    TextView RTV;
    String outptstr="";
    Button Btngetdata;
    EditText edttxt;
    Button BtnExit;
    //ArrayList<String> mylist = new ArrayList<String>();

    //URL to get JSON Array
    private static String url = "http://khalifacomputer.com/tamkeen/tamkeen1/words/php/video.php";

    //JSON Node Names
    private static final String TAG_WORDS = "videos";
    private static final String TAG_WORD= "vpath";
   // private static final String TAG_STATUS = "status";
    //private static final String TAG_ALT = "alternates";

    JSONArray android = null;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        edttxt=(EditText) findViewById(R.id.phrase);
        Btngetdata = (Button) findViewById(R.id.getdata);
        BtnExit=(Button)findViewById(R.id.buttonexit);

        BtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Go
                finish();

            }

        });

        Btngetdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new JSONParse().execute();

            }
        });

    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            RTV = (TextView) findViewById(R.id.ResultTV);
            //name = (TextView) findViewById(R.id.status);
           // api = (TextView) findViewById(R.id.alt);
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            JSONParser jParser = new JSONParser();
            String phr=edttxt.getText().toString();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url,phr);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            RTV.setText("Fail");
            if(json!=null) {
                try {
                    // mylist.clear();
                    // Getting JSON Array from URL
                    android = json.getJSONArray(TAG_WORDS);
                    outptstr = "";
                    for (int i = 0; i < android.length(); i++) {
                        JSONObject c = android.getJSONObject(i);

                        // Storing  JSON item in a Variable
                        String ver = c.getString(TAG_WORD);
                      if(i<1)  outptstr = outptstr + "\n" + ver;
                        else  outptstr = outptstr + "\n\n" +  ver;
                        // mylist.add(ver);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RTV.setText(outptstr);
            }
        }
    }
}