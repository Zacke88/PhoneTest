package zacke.phonetest;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Schulze on 2015-05-22.
 */
public class HttpGetThread extends Activity implements Runnable {

    private TextView tv3;
    private TextView tv2;
    private boolean timerStarted = false;

    HttpGetThread(Context context) {
        tv3 = (TextView) ((Activity) context).findViewById(R.id.textView3);
        tv2 = (TextView) ((Activity) context).findViewById(R.id.textView2);
    }


    public void run() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                String urlTime = "";
                while(!urlTime.contains("time")) {
                    try {
                        Thread.sleep(5000);
                        URLConnect timeString = new URLConnect();
                        urlTime = timeString.execute("http://gg.gustav-nordlander.se").get();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("TAG", urlTime);

                urlTime=urlTime.split(";")[1];
                int timeInt = Integer.parseInt(urlTime);
                final CounterClass timer = new CounterClass(timeInt, 1000);
                tv3.setVisibility(View.VISIBLE);
                //final CounterClass timer = new CounterClass(1200000, 1000);

                timer.start();

            }
        });

    }




    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            tv3.setText("ETA: " + hms);
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            tv3.setText("Ambulance should arrive shortly");
        }
    }



}
        


/*    public void run(){

        String timeString= "";

        while(!timeString.contains("time")){

            timeString = getHttp();
            System.out.println("Getted");
            System.out.println("timestring: " + timeString);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        String intString = timeString.split(";")[1];
        int timeInt = Integer.parseInt(intString);
        if(timerStarted == false) {

            tv3.setVisibility(View.VISIBLE);
            //final CounterClass timer = new CounterClass(1200000, 1000);
            final CounterClass timer = new CounterClass(timeInt, 1000);
            timer.start();
            timerStarted = true;
        }
    }


    public void processFinish(String output){
        //this you will received result fired from async class of onPostExecute(result) method.
        if(output != null) {
            tv2.setText(output);
        }
    }


    public String getHttp(){

        String httpstring = "";
        try {
            HttpConnect test = new HttpConnect();
            test.listener = this;
            httpstring = test.execute().get();
            //processFinish();
        } catch(Exception e) {
            tv2.setText("Le fail1");
        }
        return httpstring;
    }*/


