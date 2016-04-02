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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexMAO on 14/03/2016.
 */
public class RecyclerViewFragment extends Fragment {

    private static final String DEBUG_TAG = "RecyclerViewFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static AbstractBDD aBdd;
    private static int indiceTab;
    private static User userCourant = new User();
    public static RecyclerViewFragment newInstance(int indice) {
        Log.d(DEBUG_TAG, " On met a jour l'indice a : " + indiceTab);

        indiceTab = indice;
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
        GroupeBDD groupeBDD = new GroupeBDD(getActivity());
        groupeBDD.open();
        UsersBDD usersBDD = new UsersBDD(getActivity());
        usersBDD.open();
        userCourant = usersBDD.getProfil();
        List<Object> mContentItems = new ArrayList<>();
        Log.d(DEBUG_TAG, " INDICE TAB EST A  : " + indiceTab);

        /*if(indiceTab == 0){

            List<User> test;

            groupeBDD.affichageGroupes();
            String gName;
            Log.d(DEBUG_TAG, " userCourant id : " + userCourant.getId());
            //Log.d(DEBUG_TAG, " nom du groupe : " + groupeBDD.getGroupesByUserId(userCourant.getId()).get(0));
            if(!groupeBDD.getGroupesByUserId(userCourant.getId()).isEmpty()) {

                gName = groupeBDD.getGroupesByUserId(userCourant.getId()).get(0);

                test = new ArrayList<>(groupeBDD.getGroupeBDD(gName).getUsers());
                Log.d(DEBUG_TAG, " taille de la liste a afficher : " + test.size());
                for (int i = 0; i<test.size(); i++)
                    mContentItems.add(test.get(i));
            }

*/
        /*for (int i = 0; i < 10; ++i)
            mContentItems.add(new Object());*/

        //}else {
            int compteGroupe = 0;
            HashMap<String, Groupe> groupesDansBDD =  groupeBDD.getGroupesBDD();
            for(Map.Entry<String, Groupe> entry : groupesDansBDD.entrySet()) {
                for (User u : entry.getValue().getUsers()){
                    //if(u.getId() == userCourant.getId()){

                    //}
                }
                mContentItems.add(entry.getValue());
                compteGroupe++;

            }

        //mContentItems.add(groupeBDD.getGroupeBDD(groupeBDD.getGroupesByUserId(usersBDD.getProfil().getId()).get(0)))
        Log.d(DEBUG_TAG, " taille de la liste de Groupe a afficher : " + compteGroupe);

        //}





        //penser à passer notre Adapter (ici : TestRecyclerViewAdapter) à un RecyclerViewMaterialAdapter

        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(getContext(), mContentItems, indiceTab));
        mRecyclerView.removeAllViews();
        mRecyclerView.setAdapter(mAdapter);
        groupeBDD.close();
        //notifier le MaterialViewPager qu'on va utiliser une RecyclerView
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }


}