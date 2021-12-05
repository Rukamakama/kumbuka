package com.kwetubest.kumbuka.model;

/**
 * Classe qui represente un presentateur de l'emission
 */
public class Presentateur {
    private long id;
    private String noms;
    private String contacts;

    public Presentateur(long id, String noms, String contacts) {
        this.id = id;
        this.noms = noms;
        this.contacts = contacts;
    }
}
