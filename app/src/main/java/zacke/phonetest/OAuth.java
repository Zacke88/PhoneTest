package zacke.phonetest;

/**
 * Created by Joakim on 2015-05-18.
 */
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * Simple example that shows how to get OAuth 2.0 access token from Facebook
 * using Amber OAuth 2.0 library
 *
 *
 *
 *
 */
public class OAuth extends AsyncTask<Void, String, String> {

    @Override
    protected String doInBackground(Void... params){


        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://gg.gustav-nordlander.se:8080");


        try {
            httppost.setEntity(new StringEntity("TJENNE"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpResponse resp = null;
        try {
            resp = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity ent = resp.getEntity();


        return "Done!";
    }

}