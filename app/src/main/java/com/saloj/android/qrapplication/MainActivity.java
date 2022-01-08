package com.saloj.android.qrapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private ImageView imageView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageView);
        imageView.setVisibility(View.GONE);

// Initialize Calendar service with valid OAuth credentials
//        Calendar service = new Calendar
//                .Builder(httpTransport, jsonFactory, credentials)
//                .setApplicationName("applicationName").build();
//
//// Retrieve the calendar
//        com.google.api.services.calendar.model.Calendar calendar =
//                service.calendars().get('primary').execute();
//
//        System.out.println(calendar.getSummary());

/*
        addEventToCalendar("Test shyam","test calender","",
                0,0,0,0,
                0,0,0,0,0,0,
                false,"");
*/

    }

    public void addEventToCalendar(String EventTitle, String EventDescription,
                                   String EventLocation, int EventStartDateYear,
                                   int EventStartDateMonth, int EventStartDateDay,
                                   int EventEndDateYear, int EventEndDateMonth, int EventEndDateDay,
                                   int BeginHour, int BeginMin, int EndHour, int EndMin,
                                   Boolean AllDay, String EventFrequency) {
        Toast.makeText(this, "Adding Event To Your Calendar...", Toast.LENGTH_SHORT).show();
        ContentValues event = new ContentValues();
        Calendar startcalendar = Calendar.getInstance();
        Calendar endcalendar = Calendar.getInstance();
        startcalendar.set(EventStartDateYear, EventStartDateMonth, EventStartDateDay, BeginHour, BeginMin);
        endcalendar.set(EventStartDateYear, EventEndDateMonth, EventEndDateDay, EndHour, EndMin);
        event.put(CalendarContract.Events.CALENDAR_ID, 1);
        event.put(CalendarContract.Events.TITLE, EventTitle);
        event.put(CalendarContract.Events.DESCRIPTION, EventDescription);
        event.put(CalendarContract.Events.EVENT_LOCATION, EventLocation);
        event.put(CalendarContract.Events.DTSTART, startcalendar.getTimeInMillis());
        event.put(CalendarContract.Events.DTEND, endcalendar.getTimeInMillis());
        event.put(CalendarContract.Events.ALL_DAY, AllDay);
        event.put(CalendarContract.Events.HAS_ALARM, true);
        event.put(CalendarContract.Events.RRULE, EventFrequency);
        event.put(CalendarContract.Events.EVENT_TIMEZONE, "GMT-05:00");

        Uri url = getContentResolver().insert(CalendarContract.Events.CONTENT_URI, event);

        long eventId = Long.parseLong(url.getLastPathSegment());
        ContentValues reminder = new ContentValues();
        reminder.put(CalendarContract.Reminders.EVENT_ID, eventId);
        reminder.put(CalendarContract.Reminders.MINUTES, 10);
        reminder.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI, reminder);

        Toast.makeText(this, "Event Added To Your Calendar!", Toast.LENGTH_SHORT).show();
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


    public void QRCodeButton(View view) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(editText.getText().toString(), BarcodeFormat.QR_CODE, 900, 900);
            Bitmap bitmap = Bitmap.createBitmap(900, 900, Bitmap.Config.RGB_565);
            for (int x = 0; x < 900; x++) {
                for (int y = 0; y < 900; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageView.setVisibility(View.VISIBLE);
        addEvent("Demo for Selfie base attendance solution Timekompas App", "office", 06, 12);
    }
}