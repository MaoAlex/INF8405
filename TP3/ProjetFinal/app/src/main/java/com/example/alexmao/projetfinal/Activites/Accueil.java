package com.example.alexmao.projetfinal.Activites;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alexmao.projetfinal.BDDExterne.FetchFullDataFromEBDD;
import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.FullGroupWrapper;
import com.example.alexmao.projetfinal.BDDExterne.NotificationBDD;
import com.example.alexmao.projetfinal.BDDExterne.NotificationTypes;
import com.example.alexmao.projetfinal.BDDExterne.OnGroupsReady;
import com.example.alexmao.projetfinal.BDDExterne.OnNotificationReceived;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Accueil extends Activity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        UtilisateurBDD utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();
        GroupeBDD groupeBDD = new GroupeBDD(this);
        groupeBDD.open();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }*/

    //méthode pour la création du menu, dans notre cas les éléments de la tab bar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    //méthode permettant la gestion des actions en fonctions des boutons
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_about:
//                // Comportement du bouton "A Propos"
//                return true;
//            case R.id.menu_help:
//                // Comportement du bouton "Aide"
//                return true;
//            case R.id.menu_refresh:
//                // Comportement du bouton "Rafraichir"
//                return true;
//            case R.id.menu_search:
//                // Comportement du bouton "Recherche"
//                return true;
//            case R.id.menu_settings:
//                // Comportement du bouton "Paramètres"
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /*
    // This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;

    // These are the Contacts rows that we will retrieve
    static final String[] PROJECTION = new String[] {ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME};

    // This is the select criteria
    static final String SELECTION = "((" +
            ContactsContract.Data.DISPLAY_NAME + " NOTNULL) AND (" +
            ContactsContract.Data.DISPLAY_NAME + " != '' ))";

    // Called when a new Loader needs to be created
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(this, ContactsContract.Data.CONTENT_URI,
                PROJECTION, SELECTION, null, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);

        // For the cursor adapter, specify which columns go into which views
        String[] fromColumns = {ContactsContract.Data.DISPLAY_NAME};
        int[] toViews = {android.R.id.text1}; // The TextView in simple_list_item_1

        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        mAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, null,
                fromColumns, toViews, 0);
        setListAdapter(mAdapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }



    // Called when a previously created loader has finished loading
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data);
    }

    // Called when a previously created loader is reset, making the data unavailable
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
    }
*/

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RemoteBD remoteBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO passer l'ID firebase de l'utilisateur dans l'INTENT
        setContentView(R.layout.activity_accueil);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        String[] myDataset = {"test"};
        mAdapter = new AdapterEvenement(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        remoteBD = new FireBaseBD(this);
        String userID = "TODO initialiser l'userID";
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
            public void onNotificationReceived(NotificationBDD notificationBDD) {
                onContactInvitation(notificationBDD);
            }
        });
        typesToAction.put(NotificationTypes.eventInvitation, new OnNotificationReceived() {
            @Override
            public void onNotificationReceived(NotificationBDD notificationBDD) {
                onEventInvitation(notificationBDD);
            }
        });
        remoteBD.listenToNotification(userID, typesToAction);
    }

    private void onGroup(List<FullGroupWrapper> groupWrappers) {
        //faire quelque chose Stocker,
        for (FullGroupWrapper groupWrapper : groupWrappers) {
            groupWrapper.getConversationEBDD();
            groupWrapper.getFullUserWrappers();
            groupWrapper.getMyLocalEventEBDD();
        }
    }

    private void onContactInvitation(NotificationBDD notificationBDD) {
        //TODO faire quelque chose (invitation connexion)
    }

    private void onEventInvitation(NotificationBDD notificationBDD) {
        //TODO faire quelque chose (invitation pour un evenement)
    }
}
