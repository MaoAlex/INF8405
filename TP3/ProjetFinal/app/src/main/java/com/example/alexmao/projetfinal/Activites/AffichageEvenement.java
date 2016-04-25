package com.example.alexmao.projetfinal.Activites;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.alexmao.projetfinal.Adapter.AdapterEvenement;
import com.example.alexmao.projetfinal.R;

/**
 * Created by alexMAO on 24/04/2016.
 */
public class AffichageEvenement extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] myDataset;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_layout);

        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //setActionBar(toolbar);

        ViewCompat.setTransitionName(findViewById(R.id.appBarLayout), "Name");

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Titre");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = null;//"test"};
        mAdapter = new AdapterEvenement(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }


}
