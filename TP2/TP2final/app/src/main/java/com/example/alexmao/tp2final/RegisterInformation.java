package com.example.alexmao.tp2final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.alexmao.tp2final.firebase.ConnectedMapActivity;
import com.example.alexmao.tp2final.firebase.ExistWrapper;
import com.example.alexmao.tp2final.firebase.LocalUser;
import com.example.alexmao.tp2final.firebase.MyLocalGroup;
import com.example.alexmao.tp2final.firebase.RemoteBD;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class RegisterInformation extends ConnectedMapActivity {
    private static final String DEBUG_TAG = "RegisterInformation";
    private Spinner liste1 = null;
    private Spinner liste2 = null;
    private Spinner liste3 = null;
    private boolean dejaAffiche = false;
    final String EXTRA_PREFERENCE1 = "preference_1";
    final String EXTRA_PREFERENCE2 = "preference_2";
    final String EXTRA_PREFERENCE3 = "preference_3";
    final String EXTRA_EST_ORGANISATEUR = "est_organisateur";
    final String EXTRA_MAIL = "user_mail";
    final String EXTRA_PASSWORD = "user_password";
    final String EXTRA_GROUP = "user_group";
    final String EXTRA_NOM = "user_nom";
    final String EXTRA_PRENOM = "user_prenom";
    boolean estOrganisateur = false;

    private String[] tableauChoix = {"", "Bar", "Cafe", "Restaurant", "Parc", "Cinema", "Musee" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_information);
        final UsersBDD userBDD = new UsersBDD(this);
        final GroupeBDD groupeBDD = new GroupeBDD(this);
        final PreferenceBDD preferenceBDD = new PreferenceBDD(this);
        final LocalisationBDD localisationBDD = new LocalisationBDD(this);
        userBDD.open();
        groupeBDD.open();
        localisationBDD.open();
        preferenceBDD.open();
        User user = new User("Jean", "Paul");

        userBDD.insertUser(user);
        Log.d(DEBUG_TAG, "Insertion test reussi ");

        liste1 = (Spinner) findViewById(R.id.spinner_preference1);
        liste2 = (Spinner) findViewById(R.id.spinner_preference2);
        liste3 = (Spinner) findViewById(R.id.spinner_preference3);
        List<String> exemple = new ArrayList<String>();
        for(String s : tableauChoix){
            exemple.add(s);
        }
        List<String> exemple1 = exemple;
        List<String> exemple2 = exemple;

        //TODO Verifier l'utilisation des adaptateurs
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple);
        //Le layout par défaut est android.R.layout.simple_spinner_dropdown_item
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste1.setAdapter(adapter1);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple1);
        //Le layout par défaut est android.R.layout.simple_spinner_dropdown_item
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste2.setAdapter(adapter2);

        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple2);
        //Le layout par défaut est android.R.layout.simple_spinner_dropdown_item
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste3.setAdapter(adapter3);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        //On met par défaut non
        radioGroup.check(R.id.radioNo);
        //Recuperation de l'information sur l'organisateur du groupe

        final int checkedRadioButton = radioGroup.getCheckedRadioButtonId();// Verifier la valeur
        final String radioButtonSelected;
        switch (checkedRadioButton) {
            case R.id.radioNo : radioButtonSelected = "No";
                estOrganisateur=false;
                break;
            case R.id.radioYes : radioButtonSelected = "Yes";
                estOrganisateur = true;
                break;
        }

        liste1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                  /* Toast.makeText(parent.getContext(),
                        ">> : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();*/
            }
            public void onNothingSelected(AdapterView arg0) {
                // rien du tout
            }
        });

        liste2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                 /*Toast.makeText(parent.getContext(),
                        ">> : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();*/
            }
            public void onNothingSelected(AdapterView arg0) {
                // rien du tout
            }
        });
        liste3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
               /* if(position!=0){
                    adapter2.remove(adapter2.getItem(adapter2.getPosition(adapter3.getItem(position))));
                    adapter1.remove(adapter1.getItem(adapter1.getPosition(adapter3.getItem(position))));
                }*/
                /*Toast.makeText(parent.getContext(),
                        ">> : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();*/
            }

            public void onNothingSelected(AdapterView arg0) {
                // rien du tout
            }
        });

        final Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterInformation.this, PhotoActivity.class);
                String login = getIntent().getStringExtra(EXTRA_MAIL);
                String password = getIntent().getStringExtra(EXTRA_PASSWORD);
                final String groupName = getIntent().getStringExtra(EXTRA_GROUP);
                String nom = getIntent().getStringExtra(EXTRA_NOM);
                String prenom = getIntent().getStringExtra(EXTRA_PRENOM);
                User user;
                String pref1 = liste1.getSelectedItem().toString();
                String pref2 = liste2.getSelectedItem().toString();
                String pref3 = liste3.getSelectedItem().toString();
                //Création de l'utilisateur et stockage dans la bdd
                //creation de la liste de preference
                ArrayList<String> preference = new ArrayList<String>();


                if (estOrganisateur)
                    user = new User(nom, prenom, login, null, true, preference, null);
                else
                    user = new User(nom, prenom, login, null, false, preference, null);
                int id = (int) userBDD.insertUser(user);
                userBDD.affichageUsers();
                user.setId(id);
                if (pref1 != "") {
                    preference.add(pref1);
                    preferenceBDD.insertPreference(id, pref1);
                }
                if (pref2 != "" && pref2 != pref1) {
                    preference.add(pref2);
                    preferenceBDD.insertPreference(id, pref2);
                }
                if (pref3 != "" && pref3 != pref2 && pref3 != pref1) {
                    preference.add(pref3);
                }
                    //insertion des données dans la BDD           }
                    preferenceBDD.affichagePreferences();
                    //insertion des données dans la BDD
                    if (!dejaAffiche) {
                        userBDD.affichageUtilisateurConnecte();
                        LocalUser userFirebase = new LocalUser(nom, prenom, login, preference);
                        final String idFirebase = getMyRemoteBD().addUser(userFirebase);
                        userFirebase.setDataBaseId(idFirebase);
                        setLocalUser(userFirebase);
                        final MyLocalGroup myGroup = new MyLocalGroup();
                        myGroup.setGroupName(groupName);
                        ExistWrapper existWrapper = new ExistWrapper();
                        existWrapper.setOnConnectedListener(new ExistWrapper.OnConnectedListener() {
                            @Override
                            public void onConnected(boolean exist) {
                                if (exist) {
                                    //TODO debug
                                    myGroup.setChangeListener(new MyLocalGroup.ChangeListener() {
                                        @Override
                                        public void onChange(MyLocalGroup myLocalGroup) {
                                            myGroup.setChangeListener(null);
                                            getMyRemoteBD().addUserToGroup(myLocalGroup.getDatabaseID(), idFirebase);
                                        }
                                    });
                                    getMyRemoteBD().getGroupFromName(groupName, myGroup);
                                } else {
                                    myGroup.setGroupName(groupName);
                                    myGroup.addMember(idFirebase);
                                    myGroup.setDatabaseID(getMyRemoteBD().addGroup(myGroup));
                                }

                            }
                        });
                        getMyRemoteBD().getExistGroup(groupName, existWrapper);
                        userBDD.deconnexion();
                        userBDD.connexion(user);

                        groupeBDD.insertInGroup(groupName, id, estOrganisateur);
                        getMyRemoteBD().addMdpToUser(user.getMail_(), password);
                        LatLng posUtilisateurCourant = getmLatLng();
                        localisationBDD.open();
                        /*while(posUtilisateurCourant==null) {

                        }*/

                        localisationBDD.insertLocalisation(new Localisation((float) posUtilisateurCourant.latitude, (float) posUtilisateurCourant.longitude), id);
                        localisationBDD.affichageLocalisations();

                        dejaAffiche = true;
                    }
                    intent.putExtra("localUser", getLocalUser());
                    startActivity(intent);
                    //userBDD.close();
                }

        });


    }


    private void setupGroup(MyLocalGroup myLocalGroup) {
        List<String> ids = myLocalGroup.getMembersID();
        RemoteBD remoteBD = getMyRemoteBD();
        for (String id : ids) {
            if (id.equals(getLocalUser().getDataBaseId())) {
                localUser.setChangeListener(new LocalUser.ChangeListener() {
                    @Override
                    public void onPositionChanged(LocalUser localUser) {
                        //TODO do something (bear in mind, this user may already be in the BDD)
                        //this user is the current user
                    }
                });
            } else {
                LocalUser localUserFromRemote = new LocalUser();
                localUserFromRemote.setDataBaseId(id);

                localUserFromRemote.setChangeListener(new LocalUser.ChangeListener() {
                    @Override
                    public void onPositionChanged(LocalUser localUser) {
                       //TODO add user to BDD
                    }
                });

                remoteBD.getUser(id, localUserFromRemote);
            }
        }
    }

}
