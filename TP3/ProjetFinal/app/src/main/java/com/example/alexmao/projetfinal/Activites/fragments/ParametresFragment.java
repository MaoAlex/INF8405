package com.example.alexmao.projetfinal.Activites.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.text.Html;

import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.ParametresUtilisateur;

/**
 * Created by Fabien on 24/04/2016.
 */
public class ParametresFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    final String KEY_PREF_RAYON = "rayon";
    final String KEY_PREF_LOCALISATION = "localisation";
    final String KEY_PREF_MASQUERNOM = "masquerNom";
    ParametresUtilisateur params;
    OnRayonChangeListener mCallbackRayon;
    OnLocalisationChangeListener mCallbackLocalisation;
    OnMasquerNomChangeListener mCallbackMasquerNom;

    public interface OnRayonChangeListener {
        void onRayonChange(int rayon);
    }

    public interface OnLocalisationChangeListener {
        void onLocalisationChange(boolean localisation);
    }

    public interface OnMasquerNomChangeListener {
        void onMasquerNomChange(boolean masquerNom);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.parametres);
        Bundle bundle = this.getArguments();
        params = bundle.getParcelable("params");
    }

    private void updateSummary(int rayon) {
        Resources res = getResources();
        String rayonSummary = String.format(res.getString(R.string.rayon_summary), String.valueOf(rayon));
        CharSequence styledRayonSummary = Html.fromHtml(rayonSummary);
        EditTextPreference editText = (EditTextPreference) findPreference("rayon");
        if(editText != null) {
            editText.setSummary(styledRayonSummary);
        }
    }

    /**
     * Appelée lorsque la valeur d'au moins une préférence change. Permet de déclencher les callbacks (et la mise à jour du résumé pour le rayon)
     * @param sharedPreferences
     * @param key
     */
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if (key.equals(KEY_PREF_RAYON)) {
            String strRayon = sharedPreferences.getString(key, "1000");
            int rayon = Integer.parseInt(strRayon);
            updateSummary(rayon);
            mCallbackRayon.onRayonChange(rayon);
        }
        if (key.equals(KEY_PREF_LOCALISATION)) {
            boolean localisation = sharedPreferences.getBoolean(key, true);
            mCallbackLocalisation.onLocalisationChange(localisation);
        }
        if (key.equals(KEY_PREF_MASQUERNOM)) {
            boolean masquerNom = sharedPreferences.getBoolean(key, false);
            mCallbackMasquerNom.onMasquerNomChange(masquerNom);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedIstanceState) {
        super.onActivityCreated(savedIstanceState);
        initializeValues();
    }

    /**
     * Initialise les valeurs des préférences avec celles passées dans le Bundle (paramètres utilisateur)
     * Met à jour le résumé pour le rayon.
     * Ne doit être appelée qu'une fois l'activité créée
     */
    private void initializeValues() {
        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences((Context) getActivity()).edit();
        prefs.putString(KEY_PREF_RAYON, String.valueOf(params.getRayon()));
        prefs.putBoolean(KEY_PREF_LOCALISATION, params.isLocalisation());
        prefs.putBoolean(KEY_PREF_MASQUERNOM, params.isMasquerNom());
        prefs.apply();

        updateSummary(params.getRayon());
    }

    /**
     * Crée les callbacks et attache l'activité à ce fragment pour les callbacks.
     * @param context
     */
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbackRayon = (OnRayonChangeListener) context;
        mCallbackLocalisation = (OnLocalisationChangeListener) context;
        mCallbackMasquerNom = (OnMasquerNomChangeListener) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}
