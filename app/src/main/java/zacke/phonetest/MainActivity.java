package zacke.phonetest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private LocationManager locationManager;
    private MyCurrentLocationListener locationListener;
    private String ls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get Your Current Location
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        locationListener = new MyCurrentLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }


    public void buttonOnClick(View v) {
    // do something when the button is clicked

        TextView tv = (TextView)findViewById(R.id.textView);



        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        String ls = "Latitude = " + l.getLatitude() + " Longitude = " + l.getLongitude();
        //ls = locationListener.getls();

        Button button=(Button) v;
        tv.setText(ls);
        //Log.d("app", locationListener.getls());
        ((Button) v).setText("Clicked");


        AlertDialog.Builder alert = new AlertDialog.Builder(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        alert.setTitle("Emergency number");
        //alert.setMessage("Message");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        //input.setRawInputType(Configuration.KEYBOARD_12KEY);
        alert.setView(input);
        input.requestFocus();

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Editable value = input.getText();
                // Do something with value!
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
