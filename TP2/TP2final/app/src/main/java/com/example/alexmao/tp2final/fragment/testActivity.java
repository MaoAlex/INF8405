package com.example.alexmao.tp2final.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.alexmao.tp2final.GroupeBDD;
import com.example.alexmao.tp2final.R;
import com.example.alexmao.tp2final.User;
import com.example.alexmao.tp2final.UsersBDD;

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
        // specify an adapter (see also next example)
        //String[] test = {"a", "b", "c", "d", "e", "f"};
        List<User> test1 = groupeBDD.getGroupeBDD("equipe1!").getUsers();

        if(test1.isEmpty())
            Log.d("testActivity", "liste test1 vide");
        mAdapter = new MyAdapter(test1);
        mRecyclerView.setAdapter(mAdapter);


    }
    //...
}