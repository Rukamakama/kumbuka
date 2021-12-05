package com.kwetubest.kumbuka.model;

public class Radio {

    private String appelation;
    private String frequence;
    private Jour jour;

    public Radio(String appelation, String frequence) {
        this.appelation = appelation;
        this.frequence = frequence;
    }

    public void setJour(Jour jour) {
        this.jour = jour;
    }

    public Jour getJour() {
        return jour;
    }

    public String getFrequence() {
        return frequence;
    }

    public String getAppelation() {
        return appelation;
    }

}
