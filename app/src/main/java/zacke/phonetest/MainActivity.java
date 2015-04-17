package zacke.phonetest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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
      // final AlertDialog ad = alert.setView(new View(this)).create();
        // (That new View is just there to have something inside the dialog that can grow big enough to cover the whole screen.)

//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(d.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        d.show();
//        d.getWindow().setAttributes(lp);

        alert.setTitle("Emergency number");

        //alert.setMessage("Message");
//        AlertDialog ad = alert.create();
//        ad.setView(v,0,0,0,0);
// Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setId(R.id.emdialog);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        input.setTextColor(Color.WHITE);
        input.setTextSize(TypedValue.COMPLEX_UNIT_SP,140);
        input.setSingleLine(true);
        int maxLength = 3;

        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});






        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                Editable value = input.getText();
                // Do something with value!
                //Lägg in close keypad
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                OKButton(input.getText().toString());
                //d.cancel();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                //Lägg in close keypad
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                //d.cancel();
            }
        });


        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        final AlertDialog ad = alert.create();

        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    OKButton(input.getText().toString());
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                    ad.dismiss();



                }
                return false;
            }
        });

        ad.show();





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

    public void OKButton(String s)  {

        TextView tv = (TextView)findViewById(R.id.textView2);

        //String texten = " PUSHED THE BUTTON ";
        tv.setText(s);
        Log.e("Tag", "Tjennnee");

    }
}
