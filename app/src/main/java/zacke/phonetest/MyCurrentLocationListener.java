package zacke.phonetest;

import android.widget.TextView;
import android.location.Location;
import android.location.LocationListener;

/**
 * Created by Joakim on 2015-04-15.
 */
public class MyCurrentLoctionListenerTest implements LocationListener {

    public String myLocation;
    private TextView mTextView;

    MyCurrentLoctionListenerTest(TextView tv) {
        this.mTextView = tv;
    }

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();

        mTextView.setText("Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude());

    }
