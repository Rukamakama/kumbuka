package com.kwetubest.kumbuka.view;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.kwetubest.kumbuka.R;
import com.kwetubest.kumbuka.model.Programme;
import com.kwetubest.kumbuka.model.Radio;

import static com.kwetubest.kumbuka.MainActivity.ADMIN_APP;
import static com.kwetubest.kumbuka.MainActivity.USER_APP;

public class MainView extends AbstractView {

    public static final byte REACT_FB = 1;
    public static final byte REACT_SMS = 2;
    public static final byte SEND_SMS = 3;
    public static final byte REACT_WH = 4;
    public static final byte REACT_GM = 5;
    public static final byte RADIO_ACTION = 6;
    private Programme programme;
    private int topRadioPosition;
    private boolean isDataReady;

    //******  Changing state components   *************
    private AppCompatTextView tvSujet;
    private AppCompatTextView tvIntervenant;
    private AppCompatTextView tvPresentateur;
    private AppCompatTextView tvPartener;
    private AppCompatButton btnVpSujet;
    private AppCompatButton btnVpPres;
    private AppCompatButton btnVpInter;
    private AppCompatButton btnVpPart;

    public MainView(Context context, byte appType) {
        super(context, R.layout.main_view, appType);
    }

    public void setParams(Programme programme, int position){
        this.topRadioPosition = position;
        this.programme = programme;
        initComponents();
    }

    @Override
    protected void initComponents() {
        //Toolbar customizing
        Toolbar toolbar = rootView.findViewById(R.id.toobarId);
        AppCompatTextView tvTitle = toolbar.findViewById(R.id.toobar_title);
        tvTitle.setText(context.getString(R.string.strProgramEmis));

        // *********   TextView Declaration  **********************
        AppCompatTextView tvRadioName = rootView.findViewById(R.id.tvRadioName);
        AppCompatTextView tvDayHour = rootView.findViewById(R.id.tvDayHour);
        AppCompatTextView tvDate = rootView.findViewById(R.id.tvDate);
        AppCompatTextView tvFrequence = rootView.findViewById(R.id.tvFrequence);
        AppCompatTextView tvReagit = rootView.findViewById(R.id.tvReagit);
        // Les textview des informations de mise
        tvSujet = rootView.findViewById(R.id.tvSujetContent);
        tvIntervenant = rootView.findViewById(R.id.tvIntervContent);
        tvPresentateur = rootView.findViewById(R.id.tvPresentContent);
        tvPartener = rootView.findViewById(R.id.tvPartenerContent);
        //****************    TextView Setting   ********************
        Radio onTopRadio = programme.getEmission().getRadios().get(topRadioPosition);
        tvRadioName.setText(onTopRadio.getAppelation());
        String dayHour = onTopRadio.getJour().getName()+", "+onTopRadio.getJour().getHour();
        tvDayHour.setText(dayHour);
        tvDate.setText(programme.periodeString(onTopRadio.getJour()
                .getActiveDay()).replace("."," . "));
        tvFrequence.setText(onTopRadio.getFrequence());

        // *********   Buttons Declaration  **********************
        AppCompatButton btnLancerRadio = rootView.findViewById(R.id.btnLancerRadio);
        btnVpSujet = rootView.findViewById(R.id.btnVpSujet);
        btnVpPres = rootView.findViewById(R.id.btnVpPresent);
        btnVpInter = rootView.findViewById(R.id.btnVpInterv);
        btnVpPart = rootView.findViewById(R.id.btnVpPart);
        AppCompatImageButton btnSms = rootView.findViewById(R.id.btnSms);
        AppCompatImageButton btnGmail = rootView.findViewById(R.id.btnGmail);
        AppCompatImageButton btnWhatsapp = rootView.findViewById(R.id.btnWhatsapp);
        AppCompatImageButton btnFacebook = rootView.findViewById(R.id.btnFacebook);
        AppCompatImageButton btnPrograms = rootView.findViewById(R.id.btnPrograms);
        //****************    Buttons Setting   ********************
        btnGmail.setOnClickListener(btnReactListener);
        btnSms.setOnClickListener(btnReactListener);
        btnWhatsapp.setOnClickListener(btnReactListener);
        btnFacebook.setOnClickListener(btnReactListener);
        btnVpInter.setOnClickListener(btnVpListener);
        btnVpPart.setOnClickListener(btnVpListener);
        btnVpPres.setOnClickListener(btnVpListener);
        btnVpSujet.setOnClickListener(btnVpListener);

        if (appType == ADMIN_APP) {
            tvReagit.setText(context.getString(R.string.strEnvoyerSms));
            btnGmail.setVisibility(View.GONE);
            btnWhatsapp.setVisibility(View.GONE);
            btnFacebook.setVisibility(View.GONE);
        }

        btnLancerRadio.setOnClickListener(v -> {
            requestedAction = RADIO_ACTION;
            ((IViewActions)context).onforwardView(MainView.this);
        });

        final Spinner spnProgram = rootView.findViewById(R.id.spnPrograms);
        //TODO : must use the String array passed to the view
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                R.layout.support_simple_spinner_dropdown_item, programme.makeString());
        spnProgram.setAdapter(adapter);
        spnProgram.setSelected(false);
        spnProgram.setVisibility(View.INVISIBLE);
        btnPrograms.setOnClickListener(v -> spnProgram.performClick());
    }

    private View.OnClickListener btnReactListener = v -> {
        switch (v.getId()){
            case R.id.btnGmail :
                requestedAction = REACT_GM;
                break;
            case R.id.btnFacebook :
                requestedAction = REACT_FB;
                break;
            case R.id.btnWhatsapp :
                requestedAction = REACT_WH;
                break;
            case R.id.btnSms :
                if (appType == USER_APP)
                    requestedAction = REACT_SMS;
                else if (appType == ADMIN_APP)
                    requestedAction = SEND_SMS;
                break;
        }
        ((IViewActions)context).onforwardView(MainView.this);
    };

    private View.OnClickListener btnVpListener = v -> vpAction(v.getId());

    //Response to VoirPlus buttons click can also be
    // called externally when requested data are found
    public void vpAction(int viewId){
        if (isDataReady){ //Data are ready display them
            switch (viewId){
                case R.id.btnVpInterv : btnVpInter.setVisibility(View.INVISIBLE);
                    tvIntervenant.setVisibility(View.VISIBLE);
                    tvIntervenant.setText(programme.getEmission().getInervenants());
                    break;
                case R.id.btnVpPart : btnVpPart.setVisibility(View.INVISIBLE);
                    tvPartener.setVisibility(View.VISIBLE);
                    tvPartener.setText(programme.getEmission().getInervenants());
                    break;
                case R.id.btnVpSujet : btnVpSujet.setVisibility(View.INVISIBLE);
                    tvSujet.setVisibility(View.VISIBLE);
                    tvSujet.setText(programme.getEmission().getInervenants());
                    break;
                case R.id.btnVpPresent : btnVpPres.setVisibility(View.INVISIBLE);
                    tvPresentateur.setVisibility(View.VISIBLE);
                    tvPresentateur.setText(programme.getEmission().getInervenants());
                    break;
            }
        }else {
            //TODO Forward the data request to the activity and display waiting
        }
    }

    public void setDataReady(boolean dataReady) {
        isDataReady = dataReady;
    }
}
