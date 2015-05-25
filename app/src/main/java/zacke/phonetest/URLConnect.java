package zacke.phonetest;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Joakim on 2015-05-20.
 */
public class URLConnect extends AsyncTask<Void, Void, String>{

    @Override
    protected String doInBackground(Void... urls) {

        String response = "";

                try {
                        URLConnection httpGet;
                        URL theUrl = new URL("http://gg.gustav-nordlander.se/");
                        httpGet = theUrl.openConnection();
                        httpGet.connect();
                        InputStream content =  httpGet.getInputStream();
                        BufferedReader buffer = new BufferedReader(
                                new InputStreamReader(content));
                        String s;
                        while ((s = buffer.readLine()) != null) {
                            response += s;
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                }

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
    }

}
