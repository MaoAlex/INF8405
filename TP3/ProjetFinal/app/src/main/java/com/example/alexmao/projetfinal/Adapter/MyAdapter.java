package com.example.alexmao.projetfinal.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Groupe;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;
    private Evenement evenement;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        TextView mNomEvenement;
        TextView mNomSport;
        TextView mNombreParticipant;
        TextView mLieu;
        TextView mOrganisateur;
        public ViewHolder(View v) {
            super(v);
            mNomEvenement = (TextView)v.findViewById(R.id.nom_evenement);
            mNomSport = (TextView)v.findViewById(R.id.nom_sport);
            mNombreParticipant = (TextView)v.findViewById(R.id.nombre_participant);
            mLieu = (TextView)v.findViewById(R.id.nomLieu);
            mOrganisateur = (TextView)v.findViewById(R.id.nom_organisateur);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
        initializeData();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);
        holder.mNomEvenement.setText(evenement.getNomEvenement());
        holder.mNomSport.setText(evenement.getSport());
        holder.mNombreParticipant.setText(evenement.getGroupeAssocie().getListeMembre().size() + "/" + evenement.getNbreMaxParticipants());
        holder.mLieu.setText(evenement.getLieu());
        holder.mOrganisateur.setText(evenement.getOrganisateur().getNom());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    private void initializeData(){
        Utilisateur u1 = new Utilisateur();
        u1.setNom("Jean Paul");
        evenement = new Evenement();
        evenement.setNbreMaxParticipants(10);
        GregorianCalendar test = new GregorianCalendar(2016, 03, 27);
        Date date = test.getTime();
        evenement.setDate(test.getTimeInMillis());
        evenement.setLieu("parc kent");
        evenement.setNomEvenement("Fin de session");
        evenement.setOrganisateur(u1);
        evenement.setSport("foot");
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
        Evenement evenement1 = new Evenement();

    }
}