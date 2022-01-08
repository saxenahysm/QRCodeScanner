package com.saloj.android.qrapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.Toast;

import java.util.Calendar;

public class GoogleCalenderEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_calender_event);
        addEvent("Demo for Selfie base attendance solution Timekompas App", "google meet", 6, 12);
    }
    @SuppressLint("QueryPermissionsNeeded")
    public void addEvent(String title, String location, long begin, long end) {
        Calendar calendar = Calendar.getInstance();
        Calendar startcalendar = Calendar.getInstance();
        Calendar endcalendar = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.DESCRIPTION, "Demo for Timekompas app")
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.ALL_DAY, false)
                .putExtra(Intent.EXTRA_EMAIL, "shyam@entitcs.com")
                .putExtra(CalendarContract.Events.DTSTART, startcalendar.getTimeInMillis())
                .putExtra(CalendarContract.Events.DTEND, endcalendar.getTimeInMillis())
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.Events.EVENT_TIMEZONE, "GMT-05:00")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis() + 60 * 60 * 1000)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendar.getTimeInMillis() + 60 * 60 * 1000);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "There is no app that support this action", Toast.LENGTH_SHORT).show();
        }
    }

}