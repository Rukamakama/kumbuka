package com.kwetubest.kumbuka.controller;

import android.content.Context;

public abstract class Controller {

    protected Context context;

    public Controller(Context context){
        this.context = context;
    }
}
