package zacke.phonetest;

/**
 * Created by Schulze on 2015-05-13.
 */
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class HttpConnect extends AsyncTask<Void, String, String>{

    private TextView tv2;
    public AsyncResponse listener=null;


    @Override
    protected void onPostExecute(String result) {
            listener.processFinish(result);
    }

    @Override
    protected String doInBackground(Void ... params){
        String i = "";

        String returned;
        try {
            i = getInternetData();
            System.out.println("i = " + i);
        } catch(Exception e) {
            Log.e("TAG",Log.getStackTraceString(e));
            i = "le fail2";
        }

        return i;
    }

    public String getInternetData() throws Exception {
        BufferedReader in = null;
        String data = "";

        try {
            HttpClient client = new DefaultHttpClient();
            URI website = new URI("http://gg.gustav-nordlander.se");
            HttpGet request = new HttpGet();
            request.setURI(website);
            HttpResponse response = client.execute(request);
            response.getStatusLine().getStatusCode();

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String l = "";
            String nl = System.getProperty("line.separator");
            //String restUrl = URLEncoder.encode("http://gg.gustav-nordlander.se/?coord=" + cords + ";" + phoneNr, "UTF-8");
            //httpget = new HttpGet(restUrl);
            //httpget = new HttpGet("http://gg.gustav-nordlander.se/?coord=" + cords + ";" + phoneNr);

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
