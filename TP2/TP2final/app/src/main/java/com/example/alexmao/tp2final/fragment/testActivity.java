package com.example.alexmao.tp2final.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alexmao.tp2final.Groupe;
import com.example.alexmao.tp2final.GroupeBDD;
import com.example.alexmao.tp2final.R;
import com.example.alexmao.tp2final.User;
import com.example.alexmao.tp2final.UsersBDD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alexMAO on 22/03/2016.
 */
public class testActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UsersBDD usersBDD = new UsersBDD(this);
        usersBDD.open();
        usersBDD.affichageUsers();
        GroupeBDD groupeBDD = new GroupeBDD(this);
        groupeBDD.open();
        groupeBDD.affichageGroupes();
        setContentView(R.layout.recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<User> test1 = new ArrayList<>();
        ArrayList<String> p1 = new ArrayList<>();
        ArrayList<String> p2 = new ArrayList<>();
        ArrayList<String> p3 = new ArrayList<>();
        User currentUser = usersBDD.getProfil();
        Log.d("testActivity", "Fin de l'ajout des éléments");
        HashMap<String, Groupe> listeGroupe = groupeBDD.getGroupesBDD();
        ArrayList<String> listGroupUser = groupeBDD.getGroupesByUserId(currentUser.getId());
        ArrayList<String> userInGroupe = groupeBDD.getGroupesByUserId(currentUser.getId());
        if(userInGroupe!=null && !userInGroupe.isEmpty()) {
            test1 = groupeBDD.getGroupeBDD(userInGroupe.get(0)).getUsers();
        }
        //if(test1.isEmpty())
        mAdapter = new MyAdapter(test1);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private List<User> createList(int size) {

        List<User> result = new ArrayList<User>();
        for (int i=1; i <= size; i++) {
            User ci = new User();
            /*ci.setNom(); = User.NAME_PREFIX + i;
            ci.surname = User.SURNAME_PREFIX + i;
            ci.email = User.EMAIL_PREFIX + i + "@test.com";
*/
            result.add(ci);

        }

        return result;
    }
    //...
}