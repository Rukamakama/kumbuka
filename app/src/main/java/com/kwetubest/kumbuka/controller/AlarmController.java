package com.kwetubest.kumbuka.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kwetubest.kumbuka.model.Programme;
import com.kwetubest.kumbuka.model.Radio;

import java.util.Calendar;
import java.util.List;

public class AlarmController extends Controller{

    public AlarmController(Context context){
        super(context);
    }

    public void setAlarm(Programme programme,int position){
        Calendar calendar = Calendar.getInstance();
        List<Radio> radios = programme.getEmission().getRadios();
        Intent intent = new Intent(context,Notification.class);
        intent.putExtra(Notification.EMISS_NAME,programme.getEmission().getName());

        for (int i = position; i < radios.size(); i++){
            calendar.set(programme.getPeriode().get(Calendar.YEAR),
                    programme.getPeriode().get(Calendar.MONTH),
                    radios.get(i).getJour().getActiveDay(),
                    radios.get(i).getJour().getHourInt(),
                    radios.get(i).getJour().getMinute(),
                    0);
            long timeInMillis = calendar.getTimeInMillis();
            intent.putExtra(Notification.RADIO_NAME,radios.get(i).getAppelation());//Sending the radio name
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,i,intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC,timeInMillis,pendingIntent);
            }
        }
    }
}
