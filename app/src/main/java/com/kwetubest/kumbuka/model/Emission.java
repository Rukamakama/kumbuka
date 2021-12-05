package com.kwetubest.kumbuka.model;

import java.util.ArrayList;
import java.util.List;

public class Emission {

    private List<Partenaire> partenaires;
    private List<Intervenant> intervenant;
    private List<Presentateur> presentateurs;
    private List<Radio> radios;
    private String sujet;
    private String name;
    private boolean isSorted;

    public Emission(String sujet){
        this.sujet = sujet;
        this.isSorted = false;
        //TODO : Set Emission name management
        this.name = "Ebola";//Default name
    }

    public void setRadios(List<Radio> radios) {
        this.radios = radios;
    }

    public List<Radio> getRadios() {
        sort(); //First we sort radios
        return radios;
    }

    private void sort(){
        int NBR = radios.size();
        List<Radio> sorteRadios = new ArrayList<>(NBR);

        Radio closest; //Radio avec le programme le plus proche
        for (int j=0; j<NBR; j++){
            closest = radios.get(0);
            if (radios.size() > 1){
                for (int i=0; i<radios.size(); i++){
                    if (radios.get(i).getJour().compareTo(closest.getJour()) < 0){
                        closest = radios.get(i);
                    }
                }
            }
            radios.remove(closest);
            sorteRadios.add(closest);
        }
        isSorted = true;
        radios = sorteRadios;
    }

    boolean isSorted() {
        return isSorted;
    }

    public String getName() {
        return name;
    }

    public String getParteners(){

        return "";//Un string bien formater
    }
    public String getInervenants(){

        return "";//Un string bien formater
    }
    public String getPresentateurs(){

        return "";//Un string bien formater
    }
}
