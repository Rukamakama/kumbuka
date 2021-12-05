package com.kwetubest.kumbuka.model;

public interface IRadioData {

    String[] names = {"RTCT","VBR","RAO","RTSM","RSI","POLE","Sol-lev"};
    //Ordre : RTCT, VBR, RAO, RSTM, RSI, POLE, SOL-LEV
    String[] freqs = {"88.1MHz","98.5MHz","101MHz","98.9MHz","89MHz","91.4MHz","94.3MHz"};

    //Jour et heure de difusion et redifusion
    String[][] jourHeure = {{Jour.MARDI,"18h45",Jour.VENDREDI,"05h45"},//RTCT
            {Jour.MARDI,"05h00",Jour.SAMEDI,"18h45"},// VBR
            {Jour.MARDI,"19h15",Jour.LUNDI,"16h41"}, //RAO
            {Jour.MARDI,"20h15",Jour.SAMEDI,"09h15"}, //RTSM
            {Jour.MARDI,"06h45",Jour.VENDREDI,"20h30"}, //RSI
            {Jour.MARDI,"18h00",Jour.LUNDI,"17h00"}, //POLE
            {Jour.MERCREDI,"07h45",Jour.SAMEDI,"00h46"}//SOLEIL LEVANT
    };

    //TODO when connected use of multi alarm, vibrating notification
}
