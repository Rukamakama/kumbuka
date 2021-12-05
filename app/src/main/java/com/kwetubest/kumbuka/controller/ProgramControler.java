package com.kwetubest.kumbuka.controller;

import android.content.Context;

import com.kwetubest.kumbuka.model.Emission;
import com.kwetubest.kumbuka.model.Jour;
import com.kwetubest.kumbuka.model.Programme;
import com.kwetubest.kumbuka.model.Radio;

import static com.kwetubest.kumbuka.model.IRadioData.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProgramControler extends Controller {

    private Programme programme;

    public ProgramControler(Context context) {
        super(context);
        programme = new Programme(Calendar.getInstance());
    }

    private List<Radio> getRadioList() {

        List<Radio> radios = new ArrayList<>(Programme.NBR_MAX_RADIO);
        Radio radio;
        Jour jour;

        for (int i = 0; i < names.length; i++) {
            radio = new Radio(names[i], freqs[i]);
            jour = new Jour(jourHeure[i][0], jourHeure[i][1]);
            jour.setDaysInMonth(getDaysOfMonth(jour.getDayOfWeek()));
            radio.setJour(jour);
            radios.add(radio);
            //Deuxiem jour du radio
            radio = new Radio(names[i], freqs[i]);
            jour = new Jour(jourHeure[i][2], jourHeure[i][3]);
            jour.setDaysInMonth(getDaysOfMonth(jour.getDayOfWeek()));
            radio.setJour(jour);
            radios.add(radio);
        }
        return radios;
    }

    private List<Integer> getDaysOfMonth(int day) {
        List<Integer> daysOfMonth = new ArrayList<>(5);

        //Getting the current system date
        Calendar calendar = Calendar.getInstance();
        final int YEAR = calendar.get(Calendar.YEAR);
        final int MONTH = calendar.get(Calendar.MONTH);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int dayOfMonth = 1; dayOfMonth <= daysInMonth; dayOfMonth++) {
            calendar.set(YEAR, MONTH, dayOfMonth);

            if (day == calendar.get(Calendar.DAY_OF_WEEK)) {
                daysOfMonth.add(dayOfMonth);
            }
        }
        return daysOfMonth;
    }

    /**
     *Cherche les programmes le plus proche
     */
    public void setProgramme() {
        List<Radio> radios = getRadioList(); //Available radio
        List<Radio> radiosActive = new ArrayList<>(Programme.NBR_MAX_RADIO);
        Emission emission = new Emission("");

        //Getting the current date
        Calendar calendar = programme.getPeriode();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        while (dayOfMonth <= daysInMonth) {
            for (Radio radio : radios) {
                for (int d : radio.getJour().getDaysInMonth()){
                    if (d == dayOfMonth){
                        radio.getJour().setActiveDay(d);
                        radiosActive.add(radio);
                    }
                }if (radiosActive.size() == Programme.NBR_MAX_RADIO)
                    break;
            }
            if (radiosActive.size() == Programme.NBR_MAX_RADIO)
                break;

            dayOfMonth++;
        }
        emission.setRadios(radiosActive);
        programme.setEmission(emission);
    }

    public int topRadioPosition(){
        try {
            return programme.getClosestPositon();
        } catch (Exception e) {
            e.printStackTrace();
            return Programme.NBR_MAX_RADIO-1;
        }
    }

    public Programme getProgramme() {
        return programme;
    }
}
