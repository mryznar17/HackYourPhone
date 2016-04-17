package com.marek.sensors;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.marek.fragments.PhoneInfo;
import com.marek.fragments.PhoneStatus;

import static com.marek.sensors.R.id.tv_phoneStatus;

public class PhoneActivity extends AppCompatActivity {

    TextView textOut;
    TelephonyManager telephonyManager;
    PhoneStateListener listener;

    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_info);
//        setPhoneStatus();
//        setPhoneInfo();
    }

    private void setPhoneStatus(){
        // Get the UI
        textOut = (TextView) findViewById(tv_phoneStatus);
        textOut.setText("Phone Status: \n");
        // Get the telephony manager
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // Create a new PhoneStateListener
        listener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                String stateString = "N/A";
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                        stateString = "Idle";
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        stateString = "Off Hook";
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        stateString = "Ringing";
                        break;
                }
                textOut.append(String.format("\nonCallStateChanged: %s", stateString));
            }
        };

        // Register the listener with the telephony manager
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    protected void setPhoneInfo(){
        textView1=(TextView)findViewById(R.id.tv_phoneInfo);

        TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String IMEINumber=tm.getDeviceId();
        String subscriberID=tm.getDeviceId();
        String SIMSerialNumber=tm.getSimSerialNumber();
        String networkCountryISO=tm.getNetworkCountryIso();
        String SIMCountryISO=tm.getSimCountryIso();
        String softwareVersion=tm.getDeviceSoftwareVersion();
        String voiceMailNumber=tm.getVoiceMailNumber();

        //Get the phone type
        String strphoneType="";

        int phoneType=tm.getPhoneType();

        switch (phoneType)
        {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strphoneType="CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                strphoneType="GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                strphoneType="NONE";
                break;
        }

        //getting information if phone is in roaming
        boolean isRoaming=tm.isNetworkRoaming();

        String info="Phone Details:\n";
        info+="\n IMEI Number:"+IMEINumber;
        info+="\n SubscriberID:"+subscriberID;
        info+="\n Sim Serial Number:"+SIMSerialNumber;
        info+="\n Network Country ISO:"+networkCountryISO;
        info+="\n SIM Country ISO:"+SIMCountryISO;
        info+="\n Software Version:"+softwareVersion;
        info+="\n Voice Mail Number:"+voiceMailNumber;
        info+="\n Phone Network Type:"+strphoneType;
        info+="\n In Roaming? :"+isRoaming;

        textView1.setText(info);
    }

    public void onButtonTapPhoneInfo(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PhoneInfo pi = new PhoneInfo();
        fragmentTransaction.add(R.id.fragment_phone_info,pi);
        setPhoneInfo();
        fragmentTransaction.commit();
    }

    public void onButtonTapPhoneStatus(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PhoneStatus ps = new PhoneStatus();
        fragmentTransaction.add(R.id.fragment_phone_status,ps);
        setPhoneStatus();
        fragmentTransaction.commit();
    }
}
