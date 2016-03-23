package com.example.alexmao.tp2final;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexMAO on 14/03/2016.
 */
public class RecyclerViewFragment extends Fragment {

    private static final String DEBUG_TAG = "RecyclerViewFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static AbstractBDD aBdd;
    public static RecyclerViewFragment newInstance(AbstractBDD bdd) {
        aBdd = bdd;
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        //permet un affichage sous forme liste verticale
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        GroupeBDD groupeBDD = new GroupeBDD(getContext());
        groupeBDD.open();
        groupeBDD.affichageGroupes();
        List<User> test = groupeBDD.getGroupeBDD("equipe1!").getUsers();
        //10 faux contenu
        List<Object> mContentItems = new ArrayList<>();
        Log.d(DEBUG_TAG, " taille de la liste a afficher : " + test.size());

        /*for (int i = 0; i < 10; ++i)
            mContentItems.add(new Object());*/
        for (int i = 0; i<test.size(); i++)
            mContentItems.add(test.get(i));

        //penser à passer notre Adapter (ici : TestRecyclerViewAdapter) à un RecyclerViewMaterialAdapter

        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(getContext(), mContentItems));
        mRecyclerView.setAdapter(mAdapter);

        //notifier le MaterialViewPager qu'on va utiliser une RecyclerView
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }


}