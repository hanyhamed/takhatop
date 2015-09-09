package com.khalifacomputer.analyzepharase;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.khalifacomputer.analyzepharase.JSONParser;

public class MainActivity extends Activity {
    ListView list;
    TextView ver;
    TextView name;
    TextView api;
    Button Btngetdata;
    EditText edttxt;
    Button BtnExit;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    //URL to get JSON Array
    private static String url = "http://khalifacomputer.com/tamkeen/tamkeen1/words/php/phrase.php";

    //JSON Node Names
    private static final String TAG_WORDS = "words";
    private static final String TAG_WORD= "word";
    private static final String TAG_STATUS = "status";
    private static final String TAG_ALT = "alternates";

    JSONArray android = null;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        oslist = new ArrayList<HashMap<String, String>>();
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
            ver = (TextView) findViewById(R.id.word);
            name = (TextView) findViewById(R.id.status);
            api = (TextView) findViewById(R.id.alt);
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
                    list = (ListView) findViewById(R.id.list);

                    ListAdapter adapter = new SimpleAdapter(MainActivity.this, oslist,
                            R.layout.list_v,
                            new String[]{TAG_WORD, TAG_STATUS, TAG_ALT}, new int[]{
                            R.id.word, R.id.status, R.id.alt});

                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Toast.makeText(MainActivity.this, "You Clicked at " + oslist.get(+position).get("name"), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}