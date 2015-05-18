package zacke.phonetest;

/**
 * Created by Schulze on 2015-05-13.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;



public class HttpConnect extends AsyncTask<Void, String, String>{

    private TextView tv2;

    public AsyncResponse listener=null;



    @Override
    protected void onPostExecute(String result) {
        if(listener != null){
            listener.processFinish(result);
        }
    }

    @Override
    protected String doInBackground(Void ... params){
        String i = "";
        String returned;
        try {
            i = getInternetData();
            //System.out.println(i);
        } catch(Exception e) {
            Log.e("TAG",Log.getStackTraceString(e));
            i = "le fail2";
        }

        return "hi";
    }

    private String doGet(String url,OAuthConsumer consumer) throws Exception {




        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        System.out.println("Using URL : " + url );
        consumer.sign(request);
        HttpResponse response = httpclient.execute(request);
        System.out.println("Statusline : " + response.getStatusLine());
        InputStream data = response.getEntity().getContent();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data));
        String responeLine;
        StringBuilder responseBuilder = new StringBuilder();
        while ((responeLine = bufferedReader.readLine()) != null) {
            responseBuilder.append(responeLine);
        }
        return responseBuilder.toString();
    }


    public String getInternetData() throws Exception {
        BufferedReader in = null;
        String data = "";


        try {
            InputStream is = null;
            JSONObject jObj = null;
            String json = "";
            DefaultHttpClient httpClient;
            HttpPost httpPost;
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost("http://gg.gustav-nordlander.se:8080/");

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("id", "test_user"));
            nameValuePair.add(new BasicNameValuePair("type", "cords"));
            nameValuePair.add(new BasicNameValuePair("long", "123456789"));
            nameValuePair.add(new BasicNameValuePair("lat", "123456789"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));


            HttpResponse httpResponse = httpClient.execute(httpPost);
            /*HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();*/

            in = new BufferedReader(new InputStreamReader(httpPost.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String l = "";
            String nl = System.getProperty("line.separator");
            while((l = in.readLine()) != null) {
                sb.append(l + nl);
            }
            in.close();
            data = sb.toString();
            return data;
        } finally {
            if(in != null) {
                try{
                    in.close();
                    return data;
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
