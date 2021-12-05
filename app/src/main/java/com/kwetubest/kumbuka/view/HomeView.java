package com.kwetubest.kumbuka.view;

import android.content.Context;

import androidx.appcompat.widget.AppCompatButton;

import com.kwetubest.kumbuka.R;

public class HomeView extends AbstractView {


    public HomeView(Context context, byte appType) {
        super(context, R.layout.home_view, appType);
        initComponents();
    }

    @Override
    protected void initComponents() {
        AppCompatButton btnLancer = rootView.findViewById(R.id.btnStart);
        btnLancer.setOnClickListener(v -> {
            requestedAction = IviewIds.IDENTIFICATION_VIEW;
            ((IViewActions)context).onforwardView(HomeView.this);
        });

    }
}
