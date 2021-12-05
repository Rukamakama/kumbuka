package com.kwetubest.kumbuka;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.kwetubest.kumbuka.controller.AlarmController;
import com.kwetubest.kumbuka.controller.Controller;
import com.kwetubest.kumbuka.controller.IntentController;
import com.kwetubest.kumbuka.controller.ProgramControler;
import com.kwetubest.kumbuka.model.Programme;
import com.kwetubest.kumbuka.view.AbstractView;
import com.kwetubest.kumbuka.view.HomeView;
import com.kwetubest.kumbuka.view.IViewActions;
import com.kwetubest.kumbuka.view.IdentificationView;
import com.kwetubest.kumbuka.view.MainView;

import java.util.List;

import static com.kwetubest.kumbuka.view.IviewIds.HOME_VIEW;
import static com.kwetubest.kumbuka.view.IviewIds.IDENTIFICATION_VIEW;
import static com.kwetubest.kumbuka.view.IviewIds.MAIN_VIEW;

public class MainActivity extends AppCompatActivity implements IViewActions {

    private AbstractView mainView;
    public static final byte USER_APP = 0;
    public static final byte ADMIN_APP = 1;
    public static final String STR_VIEW = "view";
    private byte appType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appType = USER_APP;
        Intent intent = getIntent();
        if (intent != null){
            setView(intent.getByteExtra(STR_VIEW, HOME_VIEW));
        }else {
            setView(new HomeView(this,appType));
        }
    }

    @Override
    public void onforwardView(AbstractView view) {
        if (view instanceof HomeView){
            if(view.getRequestedAction() == IDENTIFICATION_VIEW)
                setView(new IdentificationView(this,appType));
        }else if(view instanceof  IdentificationView){
            if (view.getRequestedAction() == MAIN_VIEW)
                toMainView();
        }else if(view instanceof  MainView){
            switch (view.getRequestedAction()){
                case MainView.REACT_FB :
                case MainView.REACT_SMS :
                case MainView.SEND_SMS :
                case MainView.REACT_WH :
                case MainView.REACT_GM :
                case MainView.RADIO_ACTION :
                    IntentController controller = new IntentController(this);
                    controller.launchOtherApp(view.getRequestedAction());
                    break;
            }
        }
    }

    private void toMainView(){
        ProgramControler controler = new ProgramControler(this);
        controler.setProgramme();
        Programme programme = controler.getProgramme();
        int position = controler.topRadioPosition();

        //TODO : SET THE ALARM HERE WITH THIS DATA
        MainView mainView = new MainView(this, appType);
        mainView.setParams(programme,position);
        setView(mainView);
        //Setting alarms
        AlarmController alarmController = new AlarmController(this);
        alarmController.setAlarm(programme,position);
    }

    private void setView(AbstractView view) {
        mainView = view;
        setContentView(mainView.getRootView());
    }

    private void setView(int viewId){
        switch (viewId){
            case HOME_VIEW : setView(new HomeView(this,appType));
                break;
            case MAIN_VIEW : toMainView();
                break;
        }
    }

    private void logPackages(){
        List<PackageInfo> list = getPackageManager().getInstalledPackages(0);
        for (PackageInfo info : list){
            //String appName = info.applicationInfo.loadDescription(getPackageManager()).toString();
            String packName = info.packageName;
            Log.e("SOLIDE", " : "+packName);

        }
    }
}

