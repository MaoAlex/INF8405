package com.example.alexmao.projetfinal.Activites;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alexmao.projetfinal.Adapter.AdapterEvenement;
import com.example.alexmao.projetfinal.BDDExterne.FetchFullDataFromEBDD;
import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.FullGroupWrapper;
import com.example.alexmao.projetfinal.BDDExterne.MessageEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MyEventEBDD;
import com.example.alexmao.projetfinal.BDDExterne.NotificationBDD;
import com.example.alexmao.projetfinal.BDDExterne.NotificationTypes;
import com.example.alexmao.projetfinal.BDDExterne.OnGroupsReady;
import com.example.alexmao.projetfinal.BDDExterne.OnMessageReceiveCallback;
import com.example.alexmao.projetfinal.BDDExterne.OnNotificationReceived;
import com.example.alexmao.projetfinal.BDDExterne.OnPositionReceived;
import com.example.alexmao.projetfinal.BDDExterne.OnTemporaryEvents;
import com.example.alexmao.projetfinal.BDDExterne.Position;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDInterne.UtilisateurBDD;
import com.example.alexmao.projetfinal.MapResources.ConnectedMapActivity;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Groupe;
import com.example.alexmao.projetfinal.classeApp.InvitationConnexion;
import com.example.alexmao.projetfinal.classeApp.InvitationEvenement;
import com.example.alexmao.projetfinal.classeApp.ParametresUtilisateur;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.example.alexmao.projetfinal.utils.ShakeEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Accueil extends ConnectedMapActivity implements NavigationView.OnNavigationItemSelectedListener {


    public final static String SELECTED_TAB_EXTRA_KEY = "selectedTabIndex";
    public final static int ACCUEIL_TAB = 0;
    public final static int DECOUVERTES_TAB = 1;
    public final static int PARTICIPATIONS_TAB = 2;

    private RemoteBD remoteBD;
    private Utilisateur utilisateurConnecte;
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;
    ViewPager mViewPager;
    TabLayout tabLayout;
    PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO passer l'ID firebase de l'utilisateur dans l'INTENT
        setContentView(R.layout.activity_accueil);

        //Récupération de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(android.R.drawable.ic_menu_add);
//        toolbar.inflateMenu(R.menu.menu);
        //Récupération du DrawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Récupération de la tabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout1);

        // Definition de la gravité et de son mode
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        // Instanciation du PageAdapter pour la naviagation latéral
        pagerAdapter = new AccueilPagerAdapter(getSupportFragmentManager(), this);

        // Définition des couleurs à utiliser en cas d'actions sur les tabs
        // Fonctionnel que si l'association est fait avant l'ajout des tabs
        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.tab));

        // Récupération du ViewPager
        mViewPager = (ViewPager) super.findViewById(R.id.viewpager);

        // Affectation de l'adapter au ViewPager
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setClipToPadding(false);
        mViewPager.setPageMargin(12);

        // Affectation du ShakeEventListener
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                if(mViewPager.getCurrentItem() == DECOUVERTES_TAB) {
                    Toast.makeText(Accueil.this, "Shake!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Association de la TabLayout au viewPager
        tabLayout.setupWithViewPager(mViewPager);

        // Ajout d'un listener pour le changement de tab
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            /*Au clic sur une tab on se met sur la bonne page*/
                mViewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {/*do nothing*/}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {/*do nothing*/}
        });
        //Ouverture de la BDD interne sur les Utilisateurs
        UtilisateurBDD utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();
        //Récupération de l'utilisateur connecté
        utilisateurConnecte = utilisateurBDD.obtenirProfil();
        //Ouverture de la BDD externe
        remoteBD = new FireBaseBD(this);
        //String userID = utilisateurConnecte.getIdFirebase();//"TODO initialiser l'userID";
        utilisateurConnecte = new Utilisateur();
        utilisateurConnecte.setIdFirebase("a");

        //Initialisation des paramètres de l'utilisateur
        ParametresUtilisateur initialParams = new ParametresUtilisateur();
        initialParams.setRayon(500);
        initialParams.setLocalisation(true);
        initialParams.setMasquerNom(false);
        utilisateurConnecte.setParametres(initialParams);

        String userID = utilisateurConnecte.getIdFirebase();

        //récupération de toutes les données associées à l'utilisateur
        FetchFullDataFromEBDD.fetchallGroups(userID, remoteBD, new OnGroupsReady() {
            @Override
            public void onGroupsReady(List<FullGroupWrapper> groupWrappers) {
                onGroup(groupWrappers);
            }
        });
        //Partie écoute notification
        Map<String, OnNotificationReceived> typesToAction = new HashMap<>();
        typesToAction.put(NotificationTypes.conctactInvitation, new OnNotificationReceived() {
            @Override
            public void onNotificationReceived(NotificationBDD notificationBDD, String notId) {
                notificationBDD.setId(notId);
                onContactInvitation(notificationBDD);
            }
        });
        typesToAction.put(NotificationTypes.eventInvitation, new OnNotificationReceived() {
            @Override
            public void onNotificationReceived(NotificationBDD notificationBDD, String notId) {
                notificationBDD.setId(notId);
                onEventInvitation(notificationBDD);
            }
        });
        remoteBD.listenToNotification(userID, typesToAction);
        utilisateurBDD.close();
        remoteBD.listenToConversations(userID, new OnMessageReceiveCallback() {
            @Override
            public void onNewMessage(MessageEBDD message) {
                onNewMsg(message);
            }
        });

        //Gestion les positions
        startPositionUpdateProcess(userID, new OnPositionReceived() {
            @Override
            public void onPostionReceived(Position position) {
                onPositionChanged(position);
            }
        });
    }

    private void onNewMsg(MessageEBDD messageEBDD) {
        //TODO gèrer l'arrivée dans nouveau message
    }

    //appelée à intervalle régulier mettre à jour la position
    private void onPositionChanged(Position position) {
        //TODO faire qulque chose avec la position
    }

    private void onGroup(List<FullGroupWrapper> groupWrappers) {
        //faire quelque chose Stocker,
        for (FullGroupWrapper groupWrapper : groupWrappers) {
            groupWrapper.getConversationEBDD();
            groupWrapper.getFullUserWrappers();
            groupWrapper.getMyLocalEventEBDD();
        }

        //récupération de tous les événements temporaires
        remoteBD.getTemporaryEvent(new Date().getTime(), new OnTemporaryEvents() {
            @Override
            public void onTemporaryEvents(List<MyEventEBDD> eventEBDDs) {
                onTemporaryEventReceived(eventEBDDs);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment;
        if (id == R.id.nav_accueil) {
            mViewPager.setCurrentItem(ACCUEIL_TAB);
        } else if (id == R.id.nav_evenements) {
            mViewPager.setCurrentItem(PARTICIPATIONS_TAB);
        } else if (id == R.id.nav_rechercher) {
            Intent intent = new Intent(this, InviterConnexion.class);
            startActivity(intent);
        } else if(id == R.id.nav_invitations) {
            Intent intent = new Intent(this, GererInvitations.class);
            startActivity(intent);
        } else if (id == R.id.nav_chat) {
            Intent intent = new Intent(this, ChatList.class);
            startActivity(intent);
        } else if (id == R.id.nav_profil) {
            Intent intent = new Intent(this, ProfilUtilisateur.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onContactInvitation(NotificationBDD notificationBDD) {
        //TODO faire quelque chose (invitation connexion)
        InvitationConnexion invitationConnexion = new InvitationConnexion();
        invitationConnexion.setDate(new Date(notificationBDD.getDate()));
        invitationConnexion.setIdFirebase(notificationBDD.getId());
    }

    private void onEventInvitation(NotificationBDD notificationBDD) {
        //TODO faire quelque chose (invitation pour un evenement) utiliser la
        //BD interne pour récupérer les utilisateur ou demander à la EBDD
        InvitationEvenement invitationEvenement = new InvitationEvenement();
        invitationEvenement.setDate(new Date(notificationBDD.getDate()));
        invitationEvenement.setIdFirebase(notificationBDD.getId());

    }

    private void onTemporaryEventReceived(List<MyEventEBDD> eventEBDDs) {
        //TODO faire quekque chose, ajouter dans la BD Interne, trier etc
    }

    //Adapter pour gérér les différentes pages du viewPager
    public static class AccueilPagerAdapter extends FragmentPagerAdapter {

        private final ArrayList<android.support.v4.app.Fragment> fragments;
        private RecyclerView mRecyclerView;
        private Context context;
        public AccueilPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            fragments = new ArrayList<android.support.v4.app.Fragment>();
            fragments.add(new EvenenementsFragment());
            fragments.add(new DecouvertesFragment());
            this.context = context;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            switch (i) {
                case 0:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.

                    return fragments.get(i);
                case 1 :
                    return fragments.get(i);
                default:
                    // Les sections sont actuellements des sections par défaut
                    android.support.v4.app.Fragment fragment = new ParticipationsFragment();
                    //fragment.setArguments(args);
                    return fragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //Assignation des titres aux différentes tab
            if(position == 0)
                return "Evenement";
            else if(position == 1)
                return "Decouvertes";
            else
                return "Participation";
        }

    }

    /**
     * Fragment qui correspond au premier onglet
     * C'est l'onglet qui correspond à l'affichage des préférences de sport de l'utilisateur connecté
     */
    public static class EvenenementsFragment extends android.support.v4.app.Fragment {
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //On crée un recycler vue pour afficher les éléments sous forme de liste
            mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_evenement, container, false);
            mRecyclerView.setHasFixedSize(true);
            //Création du LayoutManager
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            //Création d'un evenement test
            ArrayList<Evenement> evenements = new ArrayList<>();
            Date date = new Date();
            Utilisateur u1 = new Utilisateur();
            u1.setNom("FR");
            evenements = new ArrayList<>();
            Evenement evenement = new Evenement();
            evenement.setNbreMaxParticipants(7);
            GregorianCalendar test = new GregorianCalendar(2016, 03, 27);
            date = test.getTime();
            evenement.setDate(test.getTimeInMillis());
            evenement.setLieu("cepsum");
            evenement.setNomEvenement("One day");
            evenement.setOrganisateur(u1);
            evenement.setSport("tennis");
            Groupe g = new Groupe();

            g.setListeMembre(new ArrayList<Utilisateur>());
            g.getListeMembre().add(u1);

            Utilisateur u2 = new Utilisateur();
            u2.setNom("Poly Technique");
            u2.setDateNaissance(test.getTimeInMillis());
            Utilisateur u3 = new Utilisateur();
            u3.setNom("Mont Real");
            u3.setDateNaissance(test.getTimeInMillis());
            g.getListeMembre().add(u2);
            g.getListeMembre().add(u3);
            evenement.setGroupeAssocie(g);
            evenements.add(evenement);
            Evenement evenement1 = new Evenement();
            evenement1 = evenement;
            evenements.add(evenement1);

            //Création d'un adapter pour l'affichage des éléments sous forme de carte
            mAdapter = new AdapterEvenement(evenements);

            mRecyclerView.setAdapter(mAdapter);

            return mRecyclerView;
        }


    }

    /**
     * Fragment qui servira dans le dernier onglet
     * C'est dans ce fragment que l'on affiche les événements auxquels participent les utilisateurs
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class ParticipationsFragment extends android.support.v4.app.Fragment {

        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //Création du recycler vue pour l'affichage des éléments sous forme de liste
            mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_participations, container, false);
            mRecyclerView.setHasFixedSize(true);

            //Création du layoutManager
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            //Création d'evenements tests
            ArrayList<Evenement> evenements = new ArrayList<>();
            Date date = new Date();
            Utilisateur u1 = new Utilisateur();
            u1.setNom("FR");
            evenements = new ArrayList<>();
            Evenement evenement = new Evenement();
            evenement.setNbreMaxParticipants(8);
            GregorianCalendar test = new GregorianCalendar(2016, 03, 27);
            date = test.getTime();
            evenement.setDate(test.getTimeInMillis());
            evenement.setLieu("cepsum");
            evenement.setNomEvenement("One day");
            evenement.setOrganisateur(u1);
            evenement.setSport("basket");
            Groupe g = new Groupe();

            g.setListeMembre(new ArrayList<Utilisateur>());
            g.getListeMembre().add(u1);

            Utilisateur u2 = new Utilisateur();
            u2.setNom("Poly Technique");
            u2.setDateNaissance(test.getTimeInMillis());
            Utilisateur u3 = new Utilisateur();
            u3.setNom("Mont Real");
            u3.setDateNaissance(test.getTimeInMillis());
            g.getListeMembre().add(u2);
            g.getListeMembre().add(u3);
            evenement.setGroupeAssocie(g);
            evenements.add(evenement);
            evenement.setSport("tennis");
            evenements.add(evenement);
            evenements.add(evenement);
            evenements.add(evenement);
            evenements.add(evenement);
            evenement.setSport("basket");
            Evenement evenement1 = new Evenement();
            evenement1.setDate(test.getTimeInMillis());
            evenement1.setLieu("Somewhere");
            evenement1.setNomEvenement("Tomorrow");
            evenement1.setOrganisateur(u1);
            evenement1.setSport("tennis");
            evenement.setNbreMaxParticipants(15);
            evenement1.setGroupeAssocie(g);

            evenements.add(evenement1);
            evenement.setGroupeAssocie(g);

            //Création d'un adapter pour l'affichage des événements sous forme de cardView
            mAdapter = new AdapterEvenement(evenements);
            mRecyclerView.setAdapter(mAdapter);

            return mRecyclerView;
        }
    }

    public static class DecouvertesFragment extends android.support.v4.app.Fragment {
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //Création du recycler vue pour l'affichage des éléments sous forme de liste
            mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_decouvertes, container, false);
            mRecyclerView.setHasFixedSize(true);
            //Création du layoutManager
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            //Création d'un adapter pour l'affichage des événements sous forme de cardView
            //Création d'un élément test
            String[] myDataset = {"test"};
            mAdapter = new AdapterEvenement(myDataset);
            mRecyclerView.setAdapter(mAdapter);
            return mRecyclerView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            Intent intent = new Intent(this, CreerEvenement.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

}
