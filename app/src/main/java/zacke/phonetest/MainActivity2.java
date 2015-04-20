package zacke.phonetest;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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


public class MainActivity2 extends ActionBarActivity {

    private LocationManager locationManager;
    private MyCurrentLocationListener locationListener;
    private String ls;
    final Context context = this;

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


        final AlertDialog ad = alert.create();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


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

       /* LinearLayout lila1= new LinearLayout(this);
        LinearLayout linbuttons = new LinearLayout(this);
        linbuttons.setOrientation(LinearLayout.HORIZONTAL);
        Button btnPositive = new Button(this);
        Button btnNegative = new Button(this);
        btnPositive.setText("OK");
        btnPositive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // write your code for sending
                onBackPressed();
            }
        });
        btnNegative.setText("Cancel");
        btnNegative.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        linbuttons.addView(btnPositive);
        linbuttons.addView(btnNegative);
        lila1.addView(linbuttons);*/




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

        boolean thebool = false;
        TextView tv = (TextView)findViewById(R.id.textView2);
        if(s.equals("112") != true)
        {

        }
        //String texten = " PUSHED THE BUTTON ";
    }
}
