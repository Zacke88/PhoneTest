package zacke.phonetest;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Schulze on 2015-04-15.
 */


public class MyCurrentLocationListener implements LocationListener {

    private String myLocation;
    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();

        myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

        //I make a log to see the results
        Log.e("MY CURRENT LOCATION", myLocation);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public String getlocationstring (){
        return myLocation;

    }
}