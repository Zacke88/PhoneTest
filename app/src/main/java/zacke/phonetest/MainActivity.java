package zacke.phonetest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;


public class MainActivity  extends ActionBarActivity implements AsyncResponse{

    private LocationManager locationManager;
    private MyCurrentLocationListener locationListener;
    private String ls;
    private String emergencyNumber = "112";
    private CountDownTimer countDownTimer;
    private TextView tv2;
    private TextView tv3;
    private boolean timerStarted = false;
    private String telenum;
    private boolean hasStoredVar;
    private boolean hasNum = false;


    public void processFinish(String output){
        //this you will received result fired from async class of onPostExecute(result) method.
        tv2 = (TextView)findViewById(R.id.textView2);
        if(output != null) {
            tv2.setText(output);
        }
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("started");
        //get Your Current Location
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        locationListener = new MyCurrentLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        SharedPreferences mPrefs = getSharedPreferences("LAGG", Context.MODE_PRIVATE);
        String mString = mPrefs.getString("telenum", "null");
        hasStoredVar = lookUpStoredVar(mString);

        if(!getPhonenr().equals(null) ){
            telenum = getPhonenr();
            hasNum = true;
        }
        else{
            if(hasStoredVar == false)
            {
                Toast.makeText(getApplicationContext(), "Could not find phone number, you can input it in settings", Toast.LENGTH_LONG).show();
            }
            else{
                telenum = mString;
            }
        }
    }

    public boolean lookUpStoredVar(String m){
        boolean hasVar = false;

        if(m != null){
            hasVar = true;
        }

        return hasVar;
    }

    public void enterPhonenr(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);


        if(telenum == null){
            final String maintext = new String("Current number is: (Could not find number)");
            alert.setMessage(maintext);
        }
        else{
            final String maintext = new String("Current number is: " + telenum);
            alert.setMessage(maintext);
        }
        alert.setTitle("Enter 10 digits phone number");
        input.setId(R.id.phonenrdialog);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        alert.setView(input);
        input.setTextColor(Color.BLACK);
        input.setTextSize(TypedValue.COMPLEX_UNIT_SP,80);
        input.setSingleLine(true);
        int maxLength = 10;
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
                            theBool = input.getText().toString().length() == 10;
                            if (theBool) {
                                {
                                    telenum = input.getText().toString();
                                    SharedPreferences mPrefs = getSharedPreferences("LAGG", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor mEditor = mPrefs.edit();
                                    mEditor.putString("telenum", telenum).commit();
                                }

                                //Close
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                                //Dismiss once everything is OK.
                                ad.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(), "You didn't enter a 10 digits number", Toast.LENGTH_SHORT).show();
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

                    theBool = input.getText().toString().length() == 10;
                    if (theBool){
                        if (hasNum == false) {
                            telenum = input.getText().toString();
                            SharedPreferences mPrefs = getSharedPreferences("LAGG", Context.MODE_PRIVATE);
                            SharedPreferences.Editor mEditor = mPrefs.edit();
                            mEditor.putString("telenum", telenum).commit();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Phone number could be determined automatically. You can't manually input it.", Toast.LENGTH_SHORT).show();
                        }
                        //Close
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                        //Dismiss once everything is OK.
                        ad.dismiss();
                    }
                    else{

                        Toast.makeText(getApplicationContext(), "You didn't enter a 10 digits number", Toast.LENGTH_SHORT).show();
                        input.setText("");

                    }
                }
                return true;
            }
        });

        ad.show();
        input.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }

    public void embuttonOnClick(View v) {
    // do something when the button is clicked
        System.out.println("em click");
        tv2 = (TextView)findViewById(R.id.textView2);
        tv3 = (TextView)findViewById(R.id.textView3);

        try {
            Location l = getCords();
//            tv2.setText("Your location is: \n" +
//                    " Latitude = " + l.getLatitude() + " Longitude = " + l.getLongitude());
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(), "Could not determinate location", Toast.LENGTH_LONG).show();
        }

        AlertDialog ad = createAlert(v);
        showAlert(ad);



    }

    public void mapbuttonOnClick(View v) {
        Location l = getCords();
        double latitude = l.getLatitude();
        double longitude = l.getLongitude();

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
        switch (id) {

            case R.id.action_settings:
                if (hasNum == false) {
                    enterPhonenr();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Your number could be determined automatically, it's not possible to set it", Toast.LENGTH_LONG).show();

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean OKButton(String s)  {

        boolean theBool = false;
        if(s.equals(emergencyNumber))
        {
            theBool= true;
        }
        return theBool;
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

        alert.setNegativeButton("Ok", null);
        alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
        });

        final AlertDialog ad = alert.create();

        ad.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = ad.getButton(AlertDialog.BUTTON_NEGATIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        boolean theBool = false;
                        Editable value = input.getText();
                        theBool = OKButton(input.getText().toString());

                        if (theBool){
                            Button b = (Button)findViewById(R.id.embutton);
                            b.setBackgroundResource(R.drawable.greenbutton);
                            //Close
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                            //Dismiss once everything is OK.


                            if(getCords() != null){
                                Button mapbutton = (Button) findViewById(R.id.mapbutton);
                                mapbutton.setVisibility(View.VISIBLE);
                            }
                            else{
                                tv2.setText("Location could not be determined, location have not been sent to emergencycentral");
                            }
                            ad.dismiss();

                            try {
                                doEm();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "You must enter 112", Toast.LENGTH_LONG).show();
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
                        Button b = (Button)findViewById(R.id.embutton);
                        b.setBackgroundResource(R.drawable.greenbutton);
                        //Close
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                        //Dismiss once everything is OK.


                        if(getCords() != null){
                            Button mapbutton = (Button) findViewById(R.id.mapbutton);
                            mapbutton.setVisibility(View.VISIBLE);
                        }
                        else{
                            tv2.setText("Location could not be determined, location have not been sent to emergencycentral");
                        }
                        ad.dismiss();

                        try {
                            doEm();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    else{

                        Toast.makeText(getApplicationContext(), "You must enter 112", Toast.LENGTH_SHORT).show();
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

    public void doEm() throws IOException {

        /*Intent callintent = new Intent(Intent.ACTION_CALL);
        callintent.setData(Uri.parse("tel:0725154893"));
        startActivity(callintent);*/
        loadURL();

        HttpGetThread Httpgetter = new HttpGetThread(this);
        Thread theThread = new Thread(Httpgetter);
        theThread.start();
        System.out.println("After run");
        /*String timeString= "";

        while(!timeString.contains("time")){

            //urlTime = getURLString();

           timeString = getHttp();

        }
        tv2.setText("after time loop");
        timeString = timeString.split(";")[1];
        int timeInt = Integer.parseInt(timeString);
            if(timerStarted == false) {

                tv3.setVisibility(View.VISIBLE);
                //final CounterClass timer = new CounterClass(1200000, 1000);
                final CounterClass timer = new CounterClass(timeInt, 1000);
                timer.start();
                timerStarted = true;
            }
*/

    }

    public String getPhonenr(){

        TelephonyManager telemamanger = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String telenum = telemamanger.getLine1Number();

        return telenum;
    }

    public void loadURL()   {

        Location locat = getCords();

        WebView webview = new WebView(MainActivity.this);
        webview.loadUrl("http://gg.gustav-nordlander.se/?coord=coord;" + telenum + ";" +
                Double.toString(locat.getLatitude())  + "," + Double.toString(locat.getLongitude()));

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

    }

    public String getURLString() throws IOException {

        URL uri = new URL("http://gg.gustav-nordlander.se/");
        URLConnection ec = uri.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(ec.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine);
        in.close();

        return a.toString();
    }

    public String getHttp(){

        String httpstring = "";
        try {
            HttpConnect test = new HttpConnect();
            test.listener = this;
            test.execute();
            processFinish(httpstring);
        } catch(Exception e) {
            tv2.setText("Le fail1");
        }
        return httpstring;
    }

    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub


            tv3 = (TextView)findViewById(R.id.textView3);
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            tv3.setText("ETA: " + hms);
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            tv3.setText("");
            tv3.setText("Completed");
        }
    }
}