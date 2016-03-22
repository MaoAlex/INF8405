package com.othian.testplaces;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PlacesActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Place> listPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
   @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Ajout d'un marqueur à Montréal, la caméra est déplacée
        LatLng mtl = new LatLng(45.504836, -73.6219958);
        mMap.addMarker(new MarkerOptions().position(mtl).title("Chez nous"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mtl));

       // initialisation de l'attribut qui contient la liste des places.
       listPlaces = new ArrayList<Place>();
       // On crée les deux tasks (une pour les cafés, l'autre pour les restaurants)
       SearchPlacesTask task = new SearchPlacesTask();
       SearchPlacesTask task2 = new SearchPlacesTask();
       // Définition des critères, objets (qui encapsulent les paramètres) qui vont être passées en paramètre aux tâches
       SearchCriteria criteria = new SearchCriteria(mtl, 1000, "cafe", "AIzaSyDrc1KaMS_FKuphctJRk6ttUPFWvD1Ksic", BitmapDescriptorFactory.HUE_AZURE);
       SearchCriteria criteria2 = new SearchCriteria(mtl, 1000, "restaurant", "AIzaSyDrc1KaMS_FKuphctJRk6ttUPFWvD1Ksic", BitmapDescriptorFactory.HUE_VIOLET);
       task.execute(criteria);
       task2.execute(criteria2);
    }

    public void parseJSON(String jsonResponse, float color) {
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
                    // Le champ nom est directement un attribut dans l'objet JSON
                    String name = currentObj.getString("name");
                    // L'emplacement (lat, lng) sont dans un élément location lui même inclus dans l'élément geometry de l'objet JSON de la place
                    JSONObject geometry = currentObj.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    double lat = location.getDouble("lat");
                    double lng = location.getDouble("lng");
                    // On crée un objet pour encapsuler et l'ajouter à la liste attribut
                    Place place = new Place(lat, lng, name);
                    listPlaces.add(place);
                    // On prépare l'affichage sur la carte
                    LatLng lieu = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(lieu).title(name).icon(BitmapDescriptorFactory.defaultMarker(color)));
                    Log.w("Test Places", "Result found : ".concat(name).concat(" - ").concat(String.valueOf(lat).concat(",").concat(String.valueOf(lng))));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class SearchPlacesTask extends AsyncTask<SearchCriteria, Void, String> {
        private float color;

        protected String doInBackground(SearchCriteria... criteres) {
            int count = criteres.length;
            if(count==1) {
                StringBuilder stringBuilder = new StringBuilder();
                SearchCriteria critere = criteres[0];
                // Coordonnées du centre
                double latitude = critere.getLatLng().latitude;
                double longitude = critere.getLatLng().longitude;

                // Critère uniquement esthétique qui n'est pas utilisé ici, seulement passé en retour à la méthode de parsing pour l'affichage des marqueurs
                this.color = critere.getColor();
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
            parseJSON(result, color);
        }
    }

}
