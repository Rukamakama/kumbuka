package com.kwetubest.kumbuka.controller;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kwetubest.kumbuka.App;
import com.kwetubest.kumbuka.MainActivity;
import com.kwetubest.kumbuka.R;
import com.kwetubest.kumbuka.view.IviewIds;


/**
 * Class to fire notification it's calling by the system
 * When a recall notification is ready
 */
public class Notification extends BroadcastReceiver {

    public static final String EMISS_NAME = "emission_name";
    public static final String RADIO_NAME = "radio_name";

    @Override
    public void onReceive(Context context, Intent intent) {
        String emission = "", radio = "";
        if (intent != null){
            emission = intent.getStringExtra(EMISS_NAME);
            radio = intent.getStringExtra(RADIO_NAME);
        }
        //Intent to be launched when clicking the notification
        Intent targetIntent = new Intent(context, MainActivity.class);
        targetIntent.putExtra(MainActivity.STR_VIEW, IviewIds.MAIN_VIEW);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, targetIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        android.app.Notification notification = new NotificationCompat.Builder(context, App.CHANEL_RECALL_ID)
                .setSmallIcon(R.drawable.ic_radio)
                .setLargeIcon(bitmap)
                .setColor(context.getResources().getColor(R.color.colorPrimary))
                .setVibrate(new long[]{200,200,200, 200})
                .setContentTitle("Rappel de l'emission")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setContentText(radio+": l'émission " +emission+" passe maintenant")
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .build();
        managerCompat.notify(1,notification);
        //Change this id if you want multi-notification with something from intent extra
    }
}
