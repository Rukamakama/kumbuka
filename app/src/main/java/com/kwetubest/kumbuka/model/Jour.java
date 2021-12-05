package com.kwetubest.kumbuka.model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

public class Jour implements Comparable {

    public static final String LUNDI = "Lun";
    public static final String MARDI = "Mar";
    public static final String MERCREDI = "Mer";
    private static final String JEUDI = "Jeu";
    public static final String VENDREDI = "Ven";
    public static final String SAMEDI = "Sam";
    private static final String DIMANCHE = "Dim";

    private String name;
    private String hour;
    private int dayOfWeek;
    private List<Integer> daysInMonth;
    private int activeDay;

    public Jour(String name, String hour) {
        this.name = name;
        this.hour = hour;
        setDays();
    }

    public Jour(int dayOfWeek, String hour){
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
        setNames();
    }

    private void setDays(){
        switch (name){
            case LUNDI : dayOfWeek = Calendar.MONDAY;
                break;
            case MARDI : dayOfWeek = Calendar.TUESDAY;
                break;
            case MERCREDI : dayOfWeek = Calendar.WEDNESDAY;
                break;
            case JEUDI : dayOfWeek = Calendar.THURSDAY;
                break;
            case VENDREDI : dayOfWeek = Calendar.FRIDAY;
                break;
            case SAMEDI : dayOfWeek = Calendar.SATURDAY;
                break;
            case DIMANCHE : dayOfWeek = Calendar.SUNDAY;
                break;
        }
    }

    private void setNames(){
        switch (dayOfWeek){
            case Calendar.MONDAY : name = LUNDI;
                break;
            case Calendar.TUESDAY : name = MARDI;
                break;
            case Calendar.WEDNESDAY : name = MERCREDI;
                break;
            case Calendar.THURSDAY : name = JEUDI;
                break;
            case Calendar.FRIDAY : name = VENDREDI;
                break;
            case Calendar.SATURDAY : name = SAMEDI;
                break;
            case Calendar.SUNDAY : name = DIMANCHE;
                break;

        }
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public String getName() {
        return name;
    }

    public String getHour() {
        return hour;
    }

    public List<Integer> getDaysInMonth() {
        return daysInMonth;
    }

    public void setDaysInMonth(List<Integer> daysInMonth) {
        this.daysInMonth = daysInMonth;
    }

    public void setActiveDay(int activeDay) {
        this.activeDay = activeDay;
    }

    public int getActiveDay() {
        return activeDay;
    }

    public int getHourInt(){
       return Integer.valueOf(this.getHour().substring(0,2));
    }

    public int getMinute(){
        return Integer.valueOf(this.getHour().substring(3));
    }

    /**
     * method the actually object with the param
     * @param object second object
     * @return 0 there are equal, -1 object1 is closer and +1  otherwise
     */
    @Override
    public int compareTo(@NonNull Object object) {
        Jour jour = (Jour)object;
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int diff1 = currentDay - this.getDayOfWeek();
        int diff2 = currentDay - jour.getDayOfWeek();

        if (diff1 == diff2){
            return this.getHour().compareTo(jour.getHour());
        }
        if ((diff1<=0 && diff2<=0) || (diff1>0 && diff2>0)){
            //S'ils sont de memes signes
            if (diff1 > diff2)
                return -1;
            else return +1;
        }else {
            //S'ils sont de signe contraire
            if (diff1 < diff2)
                return -1;
            else return +1;
        }
    }
}
