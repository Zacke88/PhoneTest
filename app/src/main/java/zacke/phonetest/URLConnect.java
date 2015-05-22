package zacke.phonetest;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Joakim on 2015-05-20.
 */
public class URLConnect extends AsyncTask{

    @Override
    protected String doInBackground(Object[] params) {
        URL uri= null;
        try {
            uri = new URL("http://gg.gustav-nordlander.se");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection ec = null;
        try {
            ec = uri.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    ec.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputLine;
        StringBuilder a = new StringBuilder();

        try {
            while ((inputLine = in.readLine()) != null)
                a.append(inputLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return a.toString();
    }

}
