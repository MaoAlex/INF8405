package com.example.alexmao.tp2final.calendrier;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Fabien on 23/03/2016.
 */
public class CalendrierService {

    public static HashSet<String> getCalendriers(Context context) {
        ContentResolver contentResolver = context.getContentResolver();

        Cursor curseur = contentResolver.query(Uri.parse("content://com.android.calendar/events"),
                new String[]{"calendar_id", "title", "description", "dtstart", "dtend", "eventLocation"},
                null, null, null);
        HashSet<String> idCalendriers = new HashSet<String>();
        try {
            if(curseur.getCount() > 0) {
                while(curseur.moveToNext()) {
                    String idCalendrier = curseur.getString(0);
                    idCalendriers.add(idCalendrier);
                }
            }
            curseur.close();
        }
        catch(AssertionError ex)
        {
            ex.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return idCalendriers;
    }

    // A utiliser
    public static List<EvenementCalendrier> getEvenementsCalendrier(Context context, int jours) {
        ContentResolver contentResolver = context.getContentResolver();
        HashSet<String> idCalendriers = getCalendriers(context);

        List<EvenementCalendrier> listeEvenements = new ArrayList<EvenementCalendrier>();
        for(String idCalendrier : idCalendriers) {
            Uri.Builder builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
            Date aujourdhui = new Date();
            long tempsActuel = aujourdhui.getTime();

            ContentUris.appendId(builder, tempsActuel);
            ContentUris.appendId(builder, tempsActuel + (DateUtils.DAY_IN_MILLIS * jours));

            Cursor curseurEvenements = contentResolver.query(builder.build(),
                    new String[]  { "title", "begin", "end", "allDay"}, "Calendars._id=" + idCalendrier,
                    null, "startDay ASC, startMinute ASC");

            if(curseurEvenements.getCount() > 0) {
                curseurEvenements.moveToFirst();
                EvenementCalendrier evenementCalendrier = new EvenementCalendrier(curseurEvenements.getString(0),
                        new Date(curseurEvenements.getLong(1)),
                        new Date(curseurEvenements.getLong(2)));
                listeEvenements.add(evenementCalendrier);

                while(curseurEvenements.moveToNext()) {
                    evenementCalendrier = new EvenementCalendrier(curseurEvenements.getString(0),
                            new Date(curseurEvenements.getLong(1)),
                            new Date(curseurEvenements.getLong(2)));
                    listeEvenements.add(evenementCalendrier);
                }
            }
                Collections.sort(listeEvenements);
        }
        return listeEvenements;
    }
}
