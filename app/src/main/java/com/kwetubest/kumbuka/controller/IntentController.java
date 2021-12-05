package com.kwetubest.kumbuka.controller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Telephony;
import android.widget.Toast;

import com.kwetubest.kumbuka.R;
import com.kwetubest.kumbuka.view.MainView;

import java.util.List;


public class IntentController extends Controller {

    private static final String NUM_WHATSAPP = "+243970661744";
    private static final String NUM_SMS = "+243970661744,+243970001745,+243970661788";
    private static final String ID_FB_PAGE = "103656457827199";
    private static final String ADRESS_MAIL = "info.coracon@gmail.com";
    private static final byte PACK_RAD = 1;
    private static final byte PACK_WHT = 2;

    public IntentController(Context context) {
        super(context);
    }

    public void launchOtherApp(byte requestedAction) {
        final String FB_PACKAGE = "com.facebook.katana";
        final String FB_LITE_PACKAGE = "com.facebook.lite";
        final String GMAIL_PACKAGE = "com.google.android.gm";
        final String WHT_PACKAGE = "com.whatsapp";

        Intent intent = null;
        Uri uri;
        switch (requestedAction) {
            case MainView.REACT_FB:
                uri = Uri.parse("fb://page/" + ID_FB_PAGE);
                if (isPackageInstalled(FB_PACKAGE)){
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage(FB_PACKAGE);
                }else if(isPackageInstalled(FB_LITE_PACKAGE)){
                    intent = context.getPackageManager().getLaunchIntentForPackage(FB_LITE_PACKAGE);
                    if (intent != null){
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                }else {
                    uri = Uri.parse("https://www.facebook.com/" + ID_FB_PAGE);
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                }
                break;
            case MainView.REACT_SMS:
                String smsPackage = Telephony.Sms.getDefaultSmsPackage(context);
                uri = Uri.parse("smsto:" + NUM_SMS);
                intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.setPackage(smsPackage);
                break;
            case MainView.REACT_WH:
                uri = Uri.parse("smsto:" + NUM_WHATSAPP);
                if (isPackageInstalled(WHT_PACKAGE)){
                    intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.setPackage(WHT_PACKAGE);
                }else {
                    String packName = getpackage(PACK_WHT);
                    if (isPackageInstalled(packName)) {
                        PackageManager manager = context.getPackageManager();
                        intent = manager.getLaunchIntentForPackage(packName);
                        if (intent != null) {
                            intent.setAction(Intent.ACTION_SENDTO);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                    }
                    else{
                        Toast.makeText(context, R.string.strNoWht,Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case MainView.REACT_GM:
                uri = Uri.parse("mailto:" + ADRESS_MAIL);
                intent = new Intent(Intent.ACTION_SENDTO, uri);
                if(isPackageInstalled(GMAIL_PACKAGE))
                    intent.setPackage(GMAIL_PACKAGE);
                break;
            case MainView.RADIO_ACTION:
                String packName = getpackage(PACK_RAD);
                if (isPackageInstalled(packName)) {
                    PackageManager manager = context.getPackageManager();
                    intent = manager.getLaunchIntentForPackage(packName);
                    if (intent != null){
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    }
                } else
                    Toast.makeText(context, R.string.strNoRadio,Toast.LENGTH_SHORT).show();
                break;
        }
        if (intent != null) {
            context.startActivity(intent);
        }
    }

    private boolean isPackageInstalled(String packageName){
        try {
            return context.getPackageManager().getApplicationInfo(packageName, 0).enabled;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private String getpackage(byte pack) {
        String matchPackage = "";
        List<PackageInfo> list = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo info : list) {
            //String appName = info.applicationInfo.loadDescription(getPackageManager()).toString();
            String packName = info.packageName;
            if (pack == PACK_RAD){
                if (packName.contains("fm") && packName.contains("radio")) {
                    matchPackage = packName;
                    break;
                }
            }else if (pack == PACK_WHT){
                if (packName.contains("whatsapp")) {
                    matchPackage = packName;
                    break;
                }
            }

        }
        return matchPackage;
    }
}
