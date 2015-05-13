package zacke.phonetest;

/**
 * Created by Schulze on 2015-05-13.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
            System.out.println(i);
        } catch(Exception e) {
            Log.e("TAG",Log.getStackTraceString(e));
            i = "le fail2";
        }

        return i;
    }

    public String getInternetData() throws Exception {
        BufferedReader in = null;
        String data = "";
        String CLIENT_SECRET = "0qRvOresKmSiWBs2f3VrNIhQ";
        String CLIENT_ID = "574992239100-v2b9ka91p707feinj1bq7i7m20civine";
        String REFRESH_TOKEN = "http://gg.gustav-nordlander.se:8080/oauth2callback";

        try {
            HttpClient client = new DefaultHttpClient();
            //HttpGet request = new HttpGet("http://nordlanderik.eu");
            HttpPost post = new HttpPost("http://gg.gustav-nordlander.se:8080");
            post.setHeader("Content-type", "application/x-www-form-urlencoded");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("grant_type",    "refresh_token"));
            nameValuePairs.add(new BasicNameValuePair("client_id",      CLIENT_ID));
            nameValuePairs.add(new BasicNameValuePair("client_secret",  CLIENT_SECRET));
            nameValuePairs.add(new BasicNameValuePair("refresh_token",  REFRESH_TOKEN));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(post);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
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
