package elbalalam.datac;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

/**
 * Created by Hany on 31/05/2015.
 */
public class RelicActivity extends ActionBarActivity {
    LocationManager lm;
    Location location ;
    double longitude ;
    double latitude ;
    Button BtnBack;
    EditText EditLong;
    EditText EditAlt;
    ScrollView vScroll;
    private float mx, my;
    float curX, curY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.basicdata);

        try{
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        catch (Exception e)
        {}
        vScroll = (ScrollView) findViewById(R.id.vScroll);
        EditLong=(EditText)findViewById(R.id.editTextLong);
        EditAlt=(EditText)findViewById(R.id.editTextAlt);
        BtnBack=(Button) findViewById(R.id.BtnBack);
        BtnBack.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
//Back

    finish();
        }

        });

        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                try {

                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    EditLong.setText(Double.toString(longitude));
                    EditAlt.setText(Double.toString(latitude));

                }
                catch (Exception e)
                {}
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        }
        catch(Exception e) {}

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mx = event.getX();
                my = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                // hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                mx = curX;
                my = curY;
                break;
            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                //hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                break;
        }

        return true;
    }
}
