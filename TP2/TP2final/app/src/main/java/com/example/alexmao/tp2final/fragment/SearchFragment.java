package com.example.alexmao.tp2final.fragment;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.alexmao.tp2final.IObserver;
import com.example.alexmao.tp2final.Place;
import com.example.alexmao.tp2final.SearchCriteria;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Fabien on 23/03/2016.
 * Les observateurs (par exemple l'activité qui lance ce fragment) doivent implémenter IObserver et
 * s'attacher à ce fragment, afin d'être notifiés lorsque la recherche par préférences est finie.
 */
public class SearchFragment extends Fragment {
    private ArrayList<Place> listPlaces;
    private ArrayList<IObserver> listObservers;
    private int counter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listObservers = new ArrayList<IObserver>();
        setRetainInstance(true);
    }

    public void setListPlaces(ArrayList<Place> listPlaces) {
        this.listPlaces = listPlaces;
    }

    public void attach(IObserver observer) {
        listObservers.add(observer);
    }

    public void detach(IObserver observer) {
        listObservers.remove(observer);
    }

    public void notifyObservers() {
        for(IObserver observer : listObservers) {
            observer.update(this.listPlaces);
        }
    }

    public ArrayList<Place> getListPlaces() {
        return this.listPlaces;
    }

    public void doResearchByPreferences(LatLng centre, int radius, String[] types) {
        for(String type : types) {
            initiateSearch(centre, radius, type);
        }
    }

    public void initiateSearch(LatLng centre, int radius, String type) {
        SearchPlacesTask task = new SearchPlacesTask();
        SearchCriteria criteria = new SearchCriteria(centre, radius, type, "AIzaSyDrc1KaMS_FKuphctJRk6ttUPFWvD1Ksic");
        task.execute(criteria);
    }

    public void parseJSON(String jsonResponse, String type) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Les places retournées sont dans un JSONArray results du JSON retourné
        JSONArray jsonArray = jsonObject.optJSONArray("results");

        if(jsonArray!=null) {
            // On récupère le nombre de places retournées
            int nbResults = jsonArray.length();
            for(int i=0; i<nbResults; i++) {
                try {
                    // Pour chacun, on récupère l'objet JSON associé
                    JSONObject currentObj = jsonArray.getJSONObject(i);
                    // On crée un objet pour encapsuler et l'ajouter à la liste attribut
                    Place place = new Place(currentObj);
                    place.setType(type);
                    listPlaces.add(place);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        counter--;

        if(counter == 0) {
            notifyObservers();
        }
    }

    private class SearchPlacesTask extends AsyncTask<SearchCriteria, Void, String> {
        private String type;

        protected String doInBackground(SearchCriteria... criteres) {
            counter++;
            int count = criteres.length;
            if(count==1) {
                StringBuilder stringBuilder = new StringBuilder();
                SearchCriteria critere = criteres[0];
                // Coordonnées du centre
                double latitude = critere.getLatLng().latitude;
                double longitude = critere.getLatLng().longitude;

                this.type = critere.getType();
                // Construction de l'URL
                // L'attribut location doit valoir "lat,lng", d'où la mise en forme à base de concat.
                // Voir documentation Google Places Web Services
                Uri searchUri = Uri.parse("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
                        .buildUpon()
                        .appendQueryParameter("location", String.valueOf(latitude).concat(",").concat(String.valueOf(longitude)))
                        .appendQueryParameter("radius", String.valueOf(critere.getRadius()))
                        .appendQueryParameter("key", critere.getAPIKey())
                        .appendQueryParameter("type", critere.getType())
                        .build();
                try {
                    // On essaye de récupérer l'InputStream de la page web
                    // /!\ Non testé avec des interruptions de connexion etc, à voir
                    URL searchUrl = new URL(searchUri.toString());
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    searchUrl.openStream()));

                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                        stringBuilder.append(inputLine);

                    in.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return stringBuilder.toString();
            }

            return "";
        }

        protected void onPostExecute(String result) {
            parseJSON(result, type);
        }
    }
}
