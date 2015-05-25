package zacke.phonetest;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Schulze on 2015-05-22.
 */
public class HttpGetThread extends Activity implements Runnable {

    private TextView tv3;

    HttpGetThread(Context context) {
        tv3 = (TextView) ((Activity) context).findViewById(R.id.textView3);
    }

    public void run() {

        String urlTime = "";
                while(!urlTime.contains("time")) {
                    try {
                        Thread.sleep(5000);
                        URLConnect timeString = new URLConnect();
                        urlTime = timeString.execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
        urlTime=urlTime.split(";")[1];
        final int timeInt = Integer.parseInt(urlTime);

        runOnUiThread(new Runnable()  {
            @Override
            public void run() {

                final CounterClass timer = new CounterClass(timeInt, 1000);
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
        public void onTick(long millis) {
            // TODO Auto-generated method stub

            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            tv3.setText("ETA: " + hms);
            System.out.println("Timer: " + hms);
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            tv3.setText("Ambulance should arrive shortly");
        }
    }
}
