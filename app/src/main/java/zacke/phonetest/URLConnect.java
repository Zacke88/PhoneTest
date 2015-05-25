package zacke.phonetest;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;


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
                        String s = "";
                        while ((s = buffer.readLine()) != null) {
                            response += s;
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            /*    DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {

                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }*/

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
    }

}
