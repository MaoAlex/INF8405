package com.example.alexmao.projetfinal.Activites;

import android.content.Context;
import android.os.Bundle;

import com.example.alexmao.projetfinal.Activites.fragments.ParametresFragment;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.ParametresUtilisateur;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.example.alexmao.projetfinal.custom.CustomActivity;

public class Parametres extends CustomActivity implements ParametresFragment.OnRayonChangeListener, ParametresFragment.OnLocalisationChangeListener, ParametresFragment.OnMasquerNomChangeListener {

    private Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ParametresFragment frag = new ParametresFragment();

        // Chargement de l'utilisateur courant
        this.chargerUtilisateur();

        // Transmission des paramètres actuels (pour affichage et initialisation
        Bundle bundle = new Bundle();
        bundle.putParcelable("params", utilisateur.getParametres());

        frag.onAttach((Context) this);
        frag.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.preferences_frag, frag)
                .commit();
        setContentView(R.layout.activity_parametres);
    }

    /**
     * Appelé lorsque la valeur de l'attribut masquerNom est changé par le menu des préférences
     * @param masquerNom : la nouvelle valeur de l'attribut masquerNom
     */
    @Override
    public void onMasquerNomChange(boolean masquerNom) {
        utilisateur.getParametres().setMasquerNom(masquerNom);
        // TODO : Mettre à jour le paramètre dans la BDD locale et externe.
    }

    /**
     * Appelé lorsque la valeur de l'attribut rayon est changé par le menu des préférences
     * @param rayon : la nouvelle valeur de l'attribut rayon
     */
    @Override
    public void onRayonChange(int rayon) {
        utilisateur.getParametres().setRayon(rayon);
        // TODO : Mettre à jour le paramètre dans la BDD locale et externe.
    }

    /**
     * Appelé lorsque la valeur de l'attribut localisation est changé par le menu des préférences
     * @param localisation : la nouvelle valeur de l'attribut localisation
     */
    @Override
    public void onLocalisationChange(boolean localisation) {
        utilisateur.getParametres().setLocalisation(localisation);
        // TODO : Mettre à jour le paramètre dans la BDD locale et externe.
    }

    // Charge l'utilisateur courant comme attribut de l'activité.
    protected void chargerUtilisateur() {
        // TODO : Récupérer les vraies valeurs dans la BDD locale/externe

        utilisateur = new Utilisateur();
        ParametresUtilisateur initialParams = new ParametresUtilisateur();
        initialParams.setRayon(500);
        initialParams.setLocalisation(true);
        initialParams.setMasquerNom(false);
        utilisateur.setParametres(initialParams);
    }
}

