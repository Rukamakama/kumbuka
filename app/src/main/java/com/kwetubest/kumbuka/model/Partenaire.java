package com.kwetubest.kumbuka.model;

/**
 * Classe qui represente un Partenaire de l'emission
 */
public class Partenaire {

    private long id;
    private String designation;
    private String contacts;

    public Partenaire(long id, String designation, String contacts) {
        this.id = id;
        this.designation = designation;
        this.contacts = contacts;
    }
}
