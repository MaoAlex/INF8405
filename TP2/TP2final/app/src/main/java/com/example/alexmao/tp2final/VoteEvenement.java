package com.example.alexmao.tp2final;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alexmao.tp2final.firebase.ConnectedMapActivity;
import com.example.alexmao.tp2final.fragment.SearchFragment;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VoteEvenement extends ConnectedMapActivity implements IObserver {
    private static final String DEBUG_TAG = "VoteEvenement" ;
    private ListView mListLieu = null;
    private static SearchFragment searchFragment;
    /** Affichage de la liste des lieux pour le vote **/
    private ListView mListDisponibilite = null;
    /** Bouton pour envoyer le vote **/
    private Button mSend = null;
    /** Contient les lieux **/
    private ArrayList<String> mLieu = null;
    private ArrayList<Lieu> lieuCalcule = null;
    private String[] prefTab;
    private Place[] placeCalcule = new Place[3];
    private double latitude;
    private double longitude;
    /** Contient différents langages de programmation **/
    private final int rayon = 10000;//10km de rayon de recherche
    private ArrayList<String> mDispo = null;

    private ArrayList<Place> listPlaces;
    private ArrayList<IObserver> listObservers;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //searchFragment = new SearchFragment();
        Log.d(DEBUG_TAG, "On serachFragment est lancé");
        listObservers = new ArrayList<IObserver>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_evenement);
        //On récupère les trois vues définies dans notre layout
        PreferenceBDD preferenceBDD = new PreferenceBDD(this);
        preferenceBDD.open();
        preferenceBDD.affichagePreferences();
        UsersBDD usersBDD = new UsersBDD(this);
        usersBDD.open();
        usersBDD.affichageUtilisateurConnecte();
        usersBDD.affichageUtilisateurConnecte();
        Log.d(DEBUG_TAG, "Affichage des utilisateurs");

        usersBDD.affichageUsers();
        User userTest = usersBDD.getProfil();
        userTest.setLocalisation_(new Localisation(45.504836, -73.6219958));
        Log.d(DEBUG_TAG, "L'id du de l'utilisateur courant est : " + userTest.getId() + " , sa position est " + userTest.getLocalisation_().getPositionX_()+ ", " + userTest.getLocalisation_().getPositionY_());

        User userCourant;
        if(userTest!=null)
            userCourant = new User(userTest);
        else {
            Log.d(DEBUG_TAG, "Il n'y a pas d'utilisateur connecte");
            userCourant = new User(userTest);

        }

        LocalisationBDD localisationBDD = new LocalisationBDD(this);
        localisationBDD.open();
        //userCourant.setLocalisation_(localisationBDD.getlocalisationUser(userCourant.getId()));
        //Localisation loc = new Localisation(41.756, 12.456);

        //Localisation loc = new Localisation(getmLatLng().latitude, getmLatLng().longitude);
        //userCourant.setLocalisation_(loc);
        preferenceBDD.affichagePreferences();
        HashMap<String, ArrayList<User>> pref = preferenceBDD.getPreferences();
        ArrayList<Integer> preferenceOrdonne = new ArrayList<>();
        //HashMap<Integer, String> classementPref = new HashMap<>();
        ArrayList<Integer> classement = new ArrayList<>();
        int indice = 0;
        HashMap<String, Integer> prefValeur = new HashMap<>();
        for (Map.Entry<String, ArrayList<User>> p : pref.entrySet()){

                //classementPref.put(p.getValue().size(), p.getKey());
                prefValeur.put(p.getKey(), p.getValue().size());
                classement.add(p.getValue().size());
                //On ne fait rien sinon car on propose déjà l'activité
        }
        Collections.sort(classement);
        Collections.reverse(classement);
        if(classement.size()>2) {
            prefTab = new String[3];
            if(classement.get(0)!=null) {
                for (Map.Entry<String, Integer> p : prefValeur.entrySet()) {
                    if(p.getValue() == classement.get(0)) {
                        prefTab[0] = p.getKey();
                        prefValeur.remove(p.getKey());
                        Log.d(DEBUG_TAG, " valeur de la preference 0 : " + prefTab[0]);
                        Log.d(DEBUG_TAG, " valeur de la preference  iuhhpu : " + classement.get(0));
                        break;
                    }
                }

            }
            if (classement.get(1) != null) {
                for (Map.Entry<String, Integer> p : prefValeur.entrySet()) {
                    if(p.getValue()==classement.get(1)) {
                        prefTab[1] = p.getKey();
                        prefValeur.remove(p.getKey());
                        Log.d(DEBUG_TAG, " valeur de la preference 1 : " + prefTab[1]);
                        break;
                    }
                }


            }
            if (classement.get(2) != null) {
                for (Map.Entry<String, Integer> p : prefValeur.entrySet()) {
                    if(p.getValue()==classement.get(2)) {

                        prefTab[2] = p.getKey();
                        prefValeur.remove(p.getKey());
                        Log.d(DEBUG_TAG, " valeur de la preference 2 : " + prefTab[2]);
                        break;
                    }
                }
                //prefTab[2] = classementPref.get(classement.get(2));


            }
        }else {
            prefTab = new String[classement.size()];
            for(int in = 0; in < classement.size(); in++){
                if (classement.get(in) != null) {
                    for (Map.Entry<String, Integer> p : prefValeur.entrySet()) {
                        if(p.getValue()==classement.get(in)) {

                            prefTab[in] = p.getKey();
                            prefValeur.remove(p.getKey());
                            Log.d(DEBUG_TAG, " valeur de la preference " + in +  " : " + prefTab[in]);
                            Log.d(DEBUG_TAG, " valeur du classement " + in + " : " + classement.get(in));
                            break;
                        }
                    }
                    //prefTab[in] = classementPref.get(classement.get(in));


                }
            }
        }

        //VoteEvenement.searchFragment.attach(VoteEvenement.this);
        attach(this);
        LatLng centre = new LatLng(userCourant.getLocalisation_().getPositionX_(), userCourant.getLocalisation_().getPositionY_());

        if(prefTab.length <1)
            Log.d(DEBUG_TAG, "Le talbeau des preferences est vide");
        Log.d(DEBUG_TAG, "taille du tableau prefTab : " + prefTab.length);

        //VoteEvenement.searchFragment.doResearchByPreferences(centre, rayon, prefTab);//Mettre les bons arguments
        //String[] testL = new String[1];
        //testL[0] = "restaurant";
        doResearchByPreferences(centre, rayon, prefTab);
        //ArrayList<Place> listPlace = new ArrayList<>() ;
        //listPlace = VoteEvenement.searchFragment.getListPlaces();
        //listPlaces = getListPlaces();

        /*if(!listPlaces.isEmpty()) {
            //update(listPlaces);
            //update(listPlace);
        }
        else
            Log.d(DEBUG_TAG, "listPlaces est vide ");*/

        mListLieu = (ListView) findViewById(R.id.listLieu);

        mListDisponibilite = (ListView) findViewById(R.id.listHoraire);

        Log.d(DEBUG_TAG, "taille du tableau placeCalcule : " + placeCalcule.length);
        if(placeCalcule[0]==null){
            Log.d(DEBUG_TAG, "Le tableau placeCacule contient des eleemtns nuls : " + placeCalcule.length);
        }

        mSend = (Button) findViewById(R.id.envoyer);
        usersBDD.close();
        preferenceBDD.close();

    }

    @Override
    public void update(ArrayList<Place> listPlaces) {
        Random r1 = new Random();
        Random r2 = new Random();
        Random r3 = new Random();
        int choix1 = r1.nextInt(listPlaces.size());
        int choix2 = r2.nextInt(listPlaces.size());
        int choix3 = r3.nextInt(listPlaces.size());
        Log.d("update", "aiojdoadozijdoajiz");
        Log.d("update", "listPlaces taille : " + listPlaces.size());

        Place choixPotentiel1 = new Place(listPlaces.get(choix1));
        Place choixPotentiel2 = new Place(listPlaces.get(choix2));
        Place choixPotentiel3 = new Place(listPlaces.get(choix3));
        /*while(choixPotentiel1!= null && prefTab[0]!= null && choixPotentiel1.getType()!=prefTab[0]) {
            choix2 = (new Random()).nextInt(listPlaces.size());
            choixPotentiel2 = listPlaces.get(choix2);
        }*/
        int compteur = 0;
        if(placeCalcule[0]==null)
          placeCalcule[0] = new Place(choixPotentiel1);
        while(prefTab.length > 1 && choixPotentiel2 != null && choixPotentiel2.getType()==choixPotentiel1.getType() && compteur<20){
            Log.d("update", "boucle une, pref 1 : " + prefTab[0] + " preference 2 : " + prefTab[1] + " longueur places trouvés : " + listPlaces.size());
            choix2 = (new Random()).nextInt(listPlaces.size());
            choixPotentiel2 = listPlaces.get(choix2);
            Log.d("update", " nom du choix : " + choixPotentiel2.getNom() + " type : " + choixPotentiel2.getType());
            compteur++;
        }
        compteur=0;
        if (placeCalcule[1]==null)
            placeCalcule[1] = new Place(choixPotentiel2);
        if(prefTab.length > 2){
            Log.d("update", "boucle 2");
            while(choixPotentiel3 != null && choixPotentiel1.getType()==choixPotentiel3.getType()
                    && choixPotentiel1.getType()==choixPotentiel3.getType()&& compteur<20){
                choix3 = (new Random()).nextInt(listPlaces.size());
                choixPotentiel3 = listPlaces.get(choix3);
                compteur++;
            }
        }else if(prefTab.length > 1){
            Log.d("update", "boucle 3");
            while(choixPotentiel3 != null && choixPotentiel2.getType()==choixPotentiel3.getType()&& compteur<20){
                choix3 = (new Random()).nextInt(listPlaces.size());
                choixPotentiel3 = listPlaces.get(choix3);
                compteur++;
            }
        }
        if (placeCalcule[2]==null)
            placeCalcule[2] = new Place(choixPotentiel3);

        String choix = new String();
        mLieu = new ArrayList<>();
        //for(int i =0; i< placeCalcule.length; i++) {
        int i = 0;
        while(i<placeCalcule.length) {
            choix = placeCalcule[i].getNom() + " - " + placeCalcule[i].getType();
            Log.d("update", "voici le choix " + i + " : " + choix);

            mLieu.add(choix);
            i++;
        }
        //}
        //mDispo = new String[]{"Restaurant", "Bar", "Cafe", "Cinema"};
        //On ajoute un adaptateur qui affiche des boutons radio (c'est l'affichage à considérer quand on ne peut
        //sélectionner qu'un élément d'une liste)
        mListLieu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, mLieu));
        //On déclare qu'on sélectionne de base le premier élément ()
        mListLieu.setItemChecked(0, true);
        //On ajoute un adaptateur qui affiche des cases à cocher (c'est l'affichage à considérer quand on peut sélectionner
        //autant d'éléments qu'on veut dans une liste)
        //mListDisponibilite.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, mDispo));
        //On déclare qu'on sélectionne de base le second élément ()
        //mListDisponibilite.setItemChecked(1, true);
        //Que se passe-t-il dès qu'on clique sur le bouton ?
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VoteEvenement.this, "Merci ! Votre vote a été pris en compte !", Toast.LENGTH_LONG).show();
                //On déclare qu'on ne peut plus sélectionner d'élément
                mListLieu.setChoiceMode(ListView.CHOICE_MODE_NONE);
                //On affiche un layout qui ne permet pas de sélection
                mListLieu.setAdapter(new ArrayAdapter<String>(VoteEvenement.this, android.R.layout.simple_list_item_1,
                        mLieu));
                //On déclare qu'on ne peut plus sélectionner d'élément
                mListDisponibilite.setChoiceMode(ListView.CHOICE_MODE_NONE);
                //On affiche un layout qui ne permet pas de sélection
                //mListDisponibilite.setAdapter(new ArrayAdapter<String>(VoteEvenement.this, android.R.layout.simple_list_item_1, mDispo));
                //On désactive le bouton
                mSend.setEnabled(false);
            }

        });
        Log.d("update", "on a fini la ");

    }

    public void setListPlaces(ArrayList<Place> listPlaces) {
        this.listPlaces = listPlaces;
    }

    public void attach(IObserver observer) {
        if(listObservers==null)
            listObservers = new ArrayList<>();

        listObservers.add(observer);
    }

    public void detach(IObserver observer) {
        listObservers.remove(observer);
    }

    public void notifyObservers() {
        Log.d("Search Fragment", "aiojdoadozijdoajiz");
        for(IObserver observer : listObservers) {
            observer.update(this.listPlaces);
        }
    }

    public ArrayList<Place> getListPlaces() {
        return this.listPlaces;
    }

    public void doResearchByPreferences(LatLng centre, int radius, String[] types) {
        Log.d("Search Fragment", "doResearchByPreferences");

        if(listPlaces==null)
            listPlaces= new ArrayList<>();
        for(String type : types) {
            Log.d("Search Fragment", "en cours de traitement");
            Log.d("Recherche", type);

            initiateSearch(centre, radius, type.toLowerCase());
        }
    }

    public void initiateSearch(LatLng centre, int radius, String type) {
        Log.d("Search Fragment", "initiateSearch");

        SearchPlacesTask task = new SearchPlacesTask();
        SearchCriteria criteria = new SearchCriteria(centre, radius, type, "AIzaSyDrc1KaMS_FKuphctJRk6ttUPFWvD1Ksic");
        Log.d("Search Fragment", "après searchPlacesTask");

        task.execute(criteria);
    }

    public void parseJSON(String jsonResponse, String type) {
        Log.d("Search Fragment", "parseJSON");

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
        Log.d("Search Fragment", "Decrementation du compteur");
        if(counter == 0) {
            Log.d("Search Fragment", "on notifie l'utilisateur");

            notifyObservers();
        }
    }

    private class SearchPlacesTask extends AsyncTask<SearchCriteria, Void, String> {

        private String type;

        protected String doInBackground(SearchCriteria... criteres) {
            Log.d("Search Fragment", "on rentre dans le background");

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
            Log.d("Search Fragment", "le parse est fini");

            return "";
        }

        protected void onPostExecute(String result) {
            Log.d("Search Fragment", "terminaison de la tache");

            parseJSON(result, type);
        }
    }
}

