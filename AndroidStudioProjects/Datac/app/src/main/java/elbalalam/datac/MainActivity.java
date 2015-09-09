package elbalalam.datac;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    Button ButtonSelect;
    Button BtnExit;
    LocationManager lm;
    Location location ;
    double longitude ;
    double latitude ;
    Spinner s;
    TextView TVGps;
    //View view1, view2;
    public String ss="";

    //Step 2:Items to be displayed in the spinner
    String[]cars={"الآثار","السدود والحواجز المائية","المقابر","المساجد والمعابد والكنائس","الاشجار",
            "الأنهار والعيون","الجبال والمرتفعات","الوديـان والهضاب","الطـرقات","المدارس التعليمية النظامية",
            "المدارس العلمية","الجامعات والمعاهد","المستشفيات","الطيور","المحميات الطبيعية" ,"السـكان",
    "المصانع والمعامل","الحيوانات", "الزواحف","الحشرات","الأسماك والأحياء البحرية","الأسر العلمية وبيوت العلم",
    "الأعلام البارزين من أبناء المنطقة","المواقع التاريخية أو العسكرية أو السياحية","الحمامات الطبيعية","الاحجاروالمعادن",
            "الآبار"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  view1 = getLayoutInflater().inflate(R.layout.activity_main, null);
      //  view2 = getLayoutInflater().inflate(R.layout.basicdata, null);
        setContentView(R.layout.activity_main);

        s=(Spinner)findViewById(R.id.spinner1);
        TVGps=(TextView) findViewById(R.id.TextViewGPS);


//STEP 4:ArrayAdapter is used to push the array of strings that will be displayed by the list view

//STEP 4.1:simple_list_item_single_choice is used make spinner view with selectable (Radio button)
        ArrayAdapter<String> ad= new ArrayAdapter<String>(this,android.R.layout. simple_list_item_single_choice,cars);
        s.setAdapter(ad);
        s.setOnItemSelectedListener(new OnItemSelectedListener()
        {

            //STEP 5:setOnItemSelected() is invoked when ever a item in list is clicked
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3)
            {
// TODO Auto-generated method stub
                int index=arg0.getSelectedItemPosition();

//STEP 6 :Text to be displayed in toast message, here it is set as name of the item selected
                Toast.makeText(getBaseContext(), "أنت إخترت - "+cars[index],Toast.LENGTH_LONG).show();
                ss=cars[index];
            }

            //STEP 7:onNothingSelected() is invoked when ever the user clicks the back button
            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
// TODO Auto-generated method stub
            }
        });
        ButtonSelect= (Button)findViewById(R.id.ButtonSelect);

        ButtonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Go
                if (ss=="الآثار") {
                    Intent myIntent = new Intent(v.getContext(), RelicActivity.class);

                    startActivity(myIntent);

                }

            }

        });

        BtnExit=(Button)findViewById(R.id.ButtonExit);
        BtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Go
                finish();

            }

        });
        try{
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        catch (Exception e)
        {}


        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                try {

                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }
                catch (Exception e)
                {}
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {TVGps.setVisibility(View.GONE);}

            public void onProviderDisabled(String provider) {TVGps.setText("من فضلك افتح الجي بي إس ");
                                                              TVGps.setVisibility(View.VISIBLE);}
        };
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        }
        catch(Exception e) {}

    }
}

