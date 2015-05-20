package zacke.phonetest;

/**
 * Created by Schulze on 2015-05-13.
 */
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class HttpConnect extends AsyncTask<String, String, String>{

    private TextView tv2;
    public AsyncResponse listener=null;


    @Override
    protected void onPostExecute(String result) {
        if(listener != null){
            listener.processFinish(result);
        }
    }

    @Override
    protected String doInBackground(String ... params){
        String i = "";

        String returned;
        try {
            i = getInternetData(params[0], params[1]);
            //System.out.println(i);
        } catch(Exception e) {
            Log.e("TAG",Log.getStackTraceString(e));
            i = "le fail2";
        }

        return i;
    }

    public String getInternetData(String cords, String phoneNr) throws Exception {
        BufferedReader in = null;
        String data = "";

        try {
            DefaultHttpClient httpClient;
            HttpGet httpget;
            httpClient = new DefaultHttpClient();
            //String restUrl = URLEncoder.encode("http://gg.gustav-nordlander.se/?coord=" + cords + ";" + phoneNr, "UTF-8");
            //httpget = new HttpGet(restUrl);
            //httpget = new HttpGet("http://gg.gustav-nordlander.se/?coord=" + cords + ";" + phoneNr);
            httpget = new HttpGet("http://gg.gustav-nordlander.se");
            HttpResponse httpResponse = httpClient.execute(httpget);

            in = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
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
