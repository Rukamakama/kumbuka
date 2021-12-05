package com.kwetubest.kumbuka.model;

/**
 * Classe qui represente un Intervenant de l'emission
 */
public class Intervenant {

    private long id;
    private String noms;
    private String contacts;

    public Intervenant(long id, String noms, String contacts) {
        this.id = id;
        this.noms = noms;
        this.contacts = contacts;
    }
}
