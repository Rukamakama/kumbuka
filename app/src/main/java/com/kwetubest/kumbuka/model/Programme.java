package com.kwetubest.kumbuka.model;


import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Programme {

    public static final int NBR_MAX_RADIO = 10;
    private Calendar periode;
    private Emission emission;

    public Programme(Calendar periode){
        this.periode = periode;
    }

    public Emission getEmission() {
        return emission;
    }

    public void setEmission(Emission emission) {
        this.emission = emission;
    }

    public Calendar getPeriode() {
        return periode;
    }

    public String periodeString(int day){
        int moth = periode.get(Calendar.MONTH);
        return ((day < 10) ? "0"+day : day)+"."+
                ((moth<9)? "0"+(moth+1) : (moth+1));
    }

    public List<String> makeString() {
        List<String> closestPrograms = new ArrayList<>(NBR_MAX_RADIO);
        //output String format : "Mar 01.03 15h30 RTCT"
        List<Radio> radios = emission.getRadios();
        for (Radio radio: radios){
            String str = radio.getJour().getName()+" "+periodeString(radio.getJour().getActiveDay())+
                    " "+radio.getJour().getHour()+" "+radio.getAppelation();
            closestPrograms.add(str);
        }
        return closestPrograms;
    }

    public int getClosestPositon() throws Exception {
        List<Radio> radios = emission.getRadios();
        if (emission.isSorted()){
            int hour = periode.get(Calendar.HOUR_OF_DAY);
            int minute = periode.get(Calendar.MINUTE)+1;
            Jour today = new Jour(periode.get(Calendar.DAY_OF_WEEK),
                    ((hour < 10 ) ? "0"+hour : ""+hour)+"h"+
                            ((minute < 10 ) ? "0"+minute : ""+minute));
            for (int i = 0; i < radios.size(); i++){
                if(today.compareTo(radios.get(i).getJour()) < 0){//Available
                    return i;
                }
            }
        }else {
            throw new Exception("The radio list should be sorted");
        }
        return NBR_MAX_RADIO-1;
    }
}
