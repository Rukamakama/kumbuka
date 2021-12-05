package com.kwetubest.kumbuka;


import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANEL_RECALL_ID = "Chaine1";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChanel();
    }

    private void createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANEL_RECALL_ID,
                    "Rappel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Chaine de rappel l\'émission \"Mazungumuzo ya vijana\"");
//            channel.set
            //TODO : Custom others settings here like lock screenVisiblilty, vibration, ...
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
