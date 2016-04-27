package com.example.alexmao.projetfinal.Activites;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.alexmao.projetfinal.Activites.fragments.ParametresFragment;
import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.FromClassAppToEBDD;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDExterne.UserParamsEBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.ParametresUtilisateur;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.example.alexmao.projetfinal.custom.CustomActivity;

public class Parametres extends CustomActivity implements ParametresFragment.OnRayonChangeListener, ParametresFragment.OnLocalisationChangeListener, ParametresFragment.OnMasquerNomChangeListener {

    private Utilisateur utilisateur;
    private RemoteBD remoteBD;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ParametresFragment frag = new ParametresFragment();

        // Chargement de l'utilisateur courant
        this.chargerUtilisateur();
        //Mise en place de la flèche pour le retour en arrière
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Transmission des paramètres actuels (pour affichage et initialisation
        Bundle bundle = new Bundle();
        bundle.putParcelable("params", utilisateur.getParametres());

        frag.onAttach((Context) this);
        frag.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.preferences_frag, frag)
                .commit();
        setContentView(R.layout.activity_parametres);
        remoteBD = new FireBaseBD(this);
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

    //TODO appeler cette fonction pour mettre à jour les données
    private void updateParamOnServer(ParametresUtilisateur parametresUtilisateur, String userID) {
        UserParamsEBDD paramsEBDD = FromClassAppToEBDD.translateParametres(parametresUtilisateur);
        remoteBD.updateUserParams(userID, paramsEBDD);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Gestion du clique sur le retour en arrière
        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}

