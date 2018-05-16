package com.example.nikola.alarm;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        AlarmActivity inst = AlarmActivity.instance();


        //parsanje zvuka alarma
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        //provjera povrata
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE); //ako nije postavljen zvuk alarma dohvati zvuk obavjesi
        }
        final Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);


        if(ringtone!=null){
            ringtone.setStreamType(AudioManager.STREAM_ALARM);

        }
        ringtone.play();//pokreni zvuk

        //pokazi gumb stop
        Button button=(Button) inst.findViewById(R.id.alarmStop);
        button.setVisibility(View.VISIBLE);
        Toast.makeText(AlarmActivity.instance(), "Activated", Toast.LENGTH_LONG).show();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ringtone.stop(); //ma klik zaustavi reprodukciju zvuka
            }
        });

        ComponentName cmp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(cmp)));
        setResultCode(Activity.RESULT_OK);
    }


}
