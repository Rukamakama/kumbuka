package com.kwetubest.kumbuka.view;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.kwetubest.kumbuka.R;

import java.util.HashMap;
import java.util.Map;

public class IdentificationView extends AbstractView {

    Map<String,String> inputInfo;

    public IdentificationView(Context context, byte appType) {
        super(context, R.layout.identification_view, appType);
        initComponents();
    }

    @Override
    protected void initComponents() {
        Toolbar toolbar = rootView.findViewById(R.id.toobarId);
        AppCompatTextView tvTitle = toolbar.findViewById(R.id.toobar_title);
        tvTitle.setText(context.getString(R.string.strIdentif));

        inputInfo  = new HashMap<>(8);

        AppCompatEditText edtName = rootView.findViewById(R.id.edtNames);
        AppCompatEditText edtAdress = rootView.findViewById(R.id.edtAdress);
        AppCompatEditText edtEmail = rootView.findViewById(R.id.edtEmail);
        AppCompatEditText edtTel = rootView.findViewById(R.id.edtTel);

        AppCompatRadioButton radH = rootView.findViewById(R.id.radH);
        AppCompatRadioButton radF = rootView.findViewById(R.id.radF);

        final Spinner spnAge = rootView.findViewById(R.id.spnAge);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context,R.array.strArrayAges,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAge.setAdapter(adapter);
        spnAge.setSelected(true);
        //On met le tranche d'age selectionner dans le map
        inputInfo.put(context.getString(R.string.strAge),spnAge.getSelectedItem().toString());
        spnAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inputInfo.put(context.getString(R.string.strAge),spnAge.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing to do
            }
        });

        AppCompatButton btnSuivant = rootView.findViewById(R.id.btnEnregistrer);
        btnSuivant.setOnClickListener(v -> {

            if (true){// TODO : Si les informations entrées sont correctes
                requestedAction = IviewIds.MAIN_VIEW;
                ((IViewActions)context).onforwardView(IdentificationView.this);
            }
        });
    }
}
