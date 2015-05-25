package zacke.phonetest;

import android.os.AsyncTask;
import android.text.Html;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Joakim on 2015-05-20.
 */
public class URLConnect extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String... urls) {

        String response = "";

            for (String url : urls) {




                DefaultHttpClient client = new DefaultHttpClient();
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
                }
            }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
    //    textView.setText(Html.fromHtml(result));
    }

}
