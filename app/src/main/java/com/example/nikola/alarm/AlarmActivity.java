package com.example.nikola.alarm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nikola.alarm.R;

import java.util.Calendar;


public class AlarmActivity extends Activity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTime;
    private static AlarmActivity inst;

    public static AlarmActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        alarmTime = findViewById(R.id.alarmTime);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void onClickEnable(View view) {

            Button buttonD = findViewById(R.id.alarmDisable);
            Button buttonE = findViewById(R.id.alarmStart);
            buttonD.setVisibility(view.VISIBLE);
            buttonE.setVisibility(view.INVISIBLE);
            Toast.makeText(AlarmActivity.this, "Activated", Toast.LENGTH_LONG).show();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTime.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTime.getCurrentMinute());
            Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }
    public void onClickDisable(View view) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(AlarmActivity.this, "Deactivated", Toast.LENGTH_LONG).show();
            Button buttonD =  findViewById(R.id.alarmDisable);
            Button buttonE =  findViewById(R.id.alarmStart);
            buttonE.setVisibility(view.VISIBLE);
            buttonD.setVisibility(view.INVISIBLE);
    }
}
