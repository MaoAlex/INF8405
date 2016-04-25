package com.example.alexmao.tp2final.calendrier;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.alexmao.tp2final.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    // Projection array. Creating indices for this array instead of doing
    // dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
    private static final String DEBUG_TAG = "MainActivity";


    public static final String[] INSTANCE_PROJECTION = new String[] {
            CalendarContract.Instances.EVENT_ID,      // 0
            CalendarContract.Instances.BEGIN,         // 1
            CalendarContract.Instances.TITLE,       // 2
            CalendarContract.Instances.END           //3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_BEGIN_INDEX = 1;
    private static final int PROJECTION_TITLE_INDEX = 2;
    private static final int PROJECTION_END_INDEX = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendrier);
        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button) findViewById(R.id.querybut);
        // Run query
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Cursor cur = null;
                Uri l_calendars;
                ContentResolver cr = getContentResolver();
                Uri uri = CalendarContract.Calendars.CONTENT_URI;
                if (Build.VERSION.SDK_INT >= 8) {
                    l_calendars = Uri.parse("content://com.android.calendar/calendars");
                } else {
                    l_calendars = Uri.parse("content://calendar/calendars");
                }
                String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                        + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND (" +
                        CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
                String[] selectionArgs = new String[] {"sampleuser@gmail.com", "com.google",
                        "sampleuser@gmail.com"};
                String[] selectionArgs = new String[] {"mao.alexandre@gmail.com", "com.google", "mao.alexandre@gmail.com"};
                // Submit the query and get a Cursor object back.
                checkPermission(Manifest.permission.READ_CALENDAR, 0, 0);
                cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
                //cur = cr.query(uri, EVENT_PROJECTION, null, null, null);

                // Use the cursor to step through the returned records
                Log.d("MainActivity", "Requete en cours");
                Log.d("MainActivity", "valeur de cur : " + cur.toString());
                while (cur.moveToNext()) {
                    Log.d("MainActivity", "valeur de cur : " + cur.toString());

                    Log.d("MainActivity", "Début boucle");
                    long calID = 0;
                    String displayName = null;
                    String accountName = null;
                    String ownerName = null;

                    // Get the field values
                    calID = cur.getLong(PROJECTION_ID_INDEX);
                    displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                    accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                    ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

                    // Do something with the values...
                    Log.d("MainActivity", "cal ID : " + calID + " display Name : " + displayName + " accountName : " + accountName + " ownerName : " + ownerName);

                }
                Log.d("MainActivity", "Requete en terminée");
                Log.d("MainActivity", "Ajout de l'événement");

                long calID = 3;
                long startMillis = 0;
                long endMillis = 0;
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2016, 3, 18, 17, 30);

                startMillis = beginTime.getTimeInMillis();
                Calendar endTime = Calendar.getInstance();
                endTime.set(2016, 3, 18, 18, 45);
                endMillis = endTime.getTimeInMillis();

                ContentResolver cr1 = getContentResolver();
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, startMillis);
                values.put(CalendarContract.Events.DTEND, endMillis);
                values.put(CalendarContract.Events.TITLE, "Evenement Test");
                values.put(CalendarContract.Events.DESCRIPTION, "BLABLABLABLABALBLABLABALBALB");
                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
                Uri uri1 = cr1.insert(CalendarContract.Events.CONTENT_URI, values);
                Log.d("MainActivity", "Evenement Ajouté");

// get the event ID that is the last element in the Uri
                long eventID = Long.parseLong(uri1.getLastPathSegment());
                Log.d("MainActivity", "Evenement id :" + eventID);
                //inserCalendarContract.Calendars.
*/

                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2016, 2, 19, 7, 30);
                Calendar endTime = Calendar.getInstance();
                endTime.set(2012, 2, 19, 8, 30);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, "Test pour le TP")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                        .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
                startActivity(intent);
            }
        });

        //modification du titre du calendrier
        /*long calID = 2;
        ContentValues values = new ContentValues();
        // The new display name for the calendar
        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Trevor's Calendar");
        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Calendars.CONTENT_URI, calID);
        int rows = getContentResolver().update(updateUri, values, null, null);
        Log.i(DEBUG_TAG, "Rows updated: " + rows);*/
        //fin



        Button affichage = (Button) findViewById(R.id.check);
        // Run query
        assert affichage != null;
        affichage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Specify the date range you want to search for recurring
// event instances
               Calendar beginTime = Calendar.getInstance();
                beginTime.set(2016, 2, 16, 8, 0);

                long startMillis = beginTime.getTimeInMillis();
                Calendar endTime = Calendar.getInstance();
                endTime.set(2016, 2, 22, 8, 0);
                long endMillis = endTime.getTimeInMillis();

                Cursor cur = null;
                ContentResolver cr = getContentResolver();

// The ID of the recurring event whose instances you are searching
// for in the Instances table
                String selection = CalendarContract.Instances.EVENT_ID + " = ?";
                String[] selectionArgs = new String[] {"207"};

// Construct the query with the desired date range.
                Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
                ContentUris.appendId(builder, startMillis);
                ContentUris.appendId(builder, endMillis);
                Log.i(DEBUG_TAG, "Verification des disponibilités");

// Submit the query
                /*cur =  cr.query(builder.build(),
                        INSTANCE_PROJECTION,
                        selection,
                        selectionArgs,
                        null);*/
                cur =  cr.query(builder.build(),
                        INSTANCE_PROJECTION,
                        null,
                        null,
                        null);

                while (cur.moveToNext()) {
                    String title = null;
                    long eventID = 0;
                    long beginVal = 0;
                    long endVal =0;
                    String beginValS;
                    String endValS;
                    // Get the field values
                    eventID = cur.getLong(PROJECTION_ID_INDEX);
                    beginVal = cur.getLong(PROJECTION_BEGIN_INDEX);
                    title = cur.getString(PROJECTION_TITLE_INDEX);
                    endVal = cur.getLong(PROJECTION_END_INDEX);
                    beginValS = cur.getString(PROJECTION_BEGIN_INDEX);

                    endValS = cur.getString(PROJECTION_END_INDEX);
                    // Do something with the values.
                    Log.i(DEBUG_TAG, "Event " + eventID + ":  " + title);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(beginVal);
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    Log.i(DEBUG_TAG, "Date: " + formatter.format(calendar.getTime()));
                    Log.i(DEBUG_TAG, "Debut: " + getDateTimeStr(beginValS) + " Fin : "+ getDateTimeStr(endValS));


                }


/*
                long startMillis;
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, startMillis);

                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(builder.build());
                startActivity(intent);*/
            }
        });


    }
    private static final String DATE_TIME_FORMAT = "yyyy MMM dd, HH:mm:ss";
    public static String getDateTimeStr(int p_delay_min) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        if (p_delay_min == 0) {
            return sdf.format(cal.getTime());
        } else {
            Date l_time = cal.getTime();
            l_time.setMinutes(l_time.getMinutes() + p_delay_min);
            return sdf.format(l_time);
        }
    }
    public static String getDateTimeStr(String p_time_in_millis) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date l_time = new Date(Long.parseLong(p_time_in_millis));
        return sdf.format(l_time);
    }
}




