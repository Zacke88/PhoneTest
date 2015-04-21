package zacke.phonetest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    private LocationManager locationManager;
    private MyCurrentLocationListener locationListener;
    private String ls;
    String emergencyNumber = "112";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get Your Current Location
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        locationListener = new MyCurrentLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }


    public void embuttonOnClick(View v) {
    // do something when the button is clicked

        TextView tv2 = (TextView)findViewById(R.id.textView2);

        try {
            Location l = getCords();
            tv2.setText("Your location is: \n" +
                    " Latitude = " + l.getLatitude() + " Longitude = " + l.getLongitude());
        }catch (NullPointerException e){
            tv2.setText("Location lookup failed");
            Toast.makeText(getApplicationContext(), "Could not determinate location. Check GPS ", Toast.LENGTH_LONG).show();
        }

        AlertDialog ad = createAlert(v);
        showAlert(ad);

    }

    public void mapbuttonOnClick(View v) {
        Toast.makeText(getApplicationContext(), "This is where a map is shown with your location on it", Toast.LENGTH_LONG).show();
        Location l = getCords();
        double latitude = l.getLatitude();
        double longitude = l.getLongitude();

        /*String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);*/

        Intent i = new Intent(MainActivity.this, MapsActivity.class);
        Bundle b = new Bundle();
        b.putDouble("Longitude", getCords().getLongitude());
        b.putDouble("Latitude", getCords().getLatitude());
        i.putExtras(b);
        startActivity(i);
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

    public boolean OKButton(String s)  {

        boolean theBool = false;
        if(s.equals(emergencyNumber))
        {
            theBool= true;
        }
        return theBool;
    }

    public void PushCoordinate()    {


    }

    public Location getCords() throws NullPointerException{

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return l;
    }

    public AlertDialog createAlert(View v){

        AlertDialog.Builder alert = new AlertDialog.Builder(this,android.R.style.Theme_Black);
        final EditText input = new EditText(this);
        alert.setTitle("Emergency number");
        input.setId(R.id.emdialog);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        input.setTextColor(Color.WHITE);
        input.setTextSize(TypedValue.COMPLEX_UNIT_SP,140);
        input.setSingleLine(true);
        int maxLength = 3;
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        alert.setPositiveButton("Ok", null);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
        });

        final AlertDialog ad = alert.create();

        ad.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = ad.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        boolean theBool = false;
                        Editable value = input.getText();
                        theBool = OKButton(input.getText().toString());

                        if (theBool){
                            //Close
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                            //Dismiss once everything is OK.

                            Button b = (Button)findViewById(R.id.embutton);
                            b.setBackgroundResource(R.drawable.greenbutton);
                            if(getCords() != null){
                                Button mapbutton = (Button) findViewById(R.id.mapbutton);
                                mapbutton.setVisibility(View.VISIBLE);
                            }
                            ad.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "You entered wrong number", Toast.LENGTH_LONG).show();
                            input.setText("");
                        }
                    }
                });
            }
        });

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    boolean theBool = false;
                    Editable value = input.getText();

                    theBool = OKButton(input.getText().toString());
                    if (theBool){
                        //Close
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                        //Dismiss once everything is OK.

                        Button b = (Button)findViewById(R.id.embutton);
                        b.setBackgroundResource(R.drawable.greenbutton);
                        if(getCords() != null){
                            Button mapbutton = (Button) findViewById(R.id.mapbutton);
                            mapbutton.setVisibility(View.VISIBLE);
                        }
                        ad.dismiss();
                    }
                    else{

                        Toast.makeText(getApplicationContext(), "You entered wrong number", Toast.LENGTH_SHORT).show();
                        input.setText("");

                    }
                }
                return true;
            }
        });

        return ad;
    }

    public void showAlert(AlertDialog ad){

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        ad.show();
    }

}
