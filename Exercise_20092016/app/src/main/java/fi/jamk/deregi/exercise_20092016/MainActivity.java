package fi.jamk.deregi.exercise_20092016;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String[] notifications = {"L...", "do you know...", "Death Gods...", "eat only apples."};
    private int index = 0;
    private int notificationId = 1;
    private boolean isAppInBackground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isAppInBackground = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isAppInBackground && index < 4){
                    Message message = mHandler.obtainMessage();
                    message.sendToTarget();
                    try{
                        Thread.sleep(5000);
                    }
                    catch (Exception e){
                        System.out.println("Sleep? Nope.");
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        isAppInBackground = false;
        index = 0;
        super.onResume();
    }

    Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            sendNotification();
        }
    };

    public void sendNotification(){
        if (index < 4){
            Notification notification = new Notification.Builder(this)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setContentTitle("Message")
                    .setContentText(notifications[index])
                    .setSmallIcon(R.drawable.ptm)
                    .setAutoCancel(true)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .build();
            index++;
            NotificationManager notiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationId++;
            notiManager.notify(notificationId, notification);
        }
    }
}
