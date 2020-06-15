import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.manjurulhoque.myrecipes.R;
import com.manjurulhoque.myrecipes.activity.MainActivity;

public class MyService extends Service {

    private Vibrator v;
    //private static Timer timer = new Timer();
    NotificationCompat.Builder notification;
    private static final int uniqueID = 71399;
    @Override
    public void onCreate() {
        super.onCreate();

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SharedPreferences sharedPreferences = getSharedPreferences("Timer", Context.MODE_PRIVATE);
        int dur = sharedPreferences.getInt("duration", 0);
        CountDownTimer countDownTimer = new CountDownTimer(dur,    1000) {

            @Override
            public void onTick(long param) {
            }

            @Override
            public void onFinish() {
                long n[] = {1,1000,500,1000,500,1000,500,1000,500,1000,500,1000,500,1000,500,1000,500,1000};
                v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                //v.vibrate(n, -1);
                startnotif();
                onDestroy();
            }
        };
        countDownTimer.start();
        return START_STICKY;
    }


    public void onDestroy() {

        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //notification
    public void startnotif()
    {
        notification.setSmallIcon(R.drawable.rabbit);
        notification.setTicker("apps are now unblocked!");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("You survived!");
        notification.setContentText("Apps are now unblocked!");

        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());

    }

}