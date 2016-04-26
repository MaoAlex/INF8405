package com.example.alexmao.projetfinal.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Groupe;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 */
public class AdapterEvenement extends RecyclerView.Adapter<AdapterEvenement.ViewHolder> {
    private String[] mDataset;
    private List<Evenement> evenements;
    // Fourni une référence à la vue pour chaque élément
    // Les objets avec des données complexes peuvent avoir besoin de plus d'une vue
    //L'accès à toutes les vues se fait à traver le view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Pour l'instant chaque objet est juste un string
        public TextView mTextView;
        TextView mNomEvenement;
        TextView mNomSport;
        TextView mNombreParticipant;
        TextView mLieu;
        TextView mOrganisateur;
        ImageView mPhoto;
        CardView cv;
        public ViewHolder(View v) {
            super(v);
            cv = (CardView)v.findViewById(R.id.card_view);
            //mTextView = (TextView)v.findViewById(R.id.info_text);
            mNomEvenement = (TextView)v.findViewById(R.id.nom_evenement);
            mNomSport = (TextView)v.findViewById(R.id.nom_sport);
            mNombreParticipant = (TextView)v.findViewById(R.id.nombre_participant);
            mLieu = (TextView)v.findViewById(R.id.nomLieu);
            mPhoto = (ImageView) v.findViewById(R.id.sport_photo);
            mOrganisateur = (TextView)v.findViewById(R.id.nom_organisateur);
        }
    }

    // Constructeur
    public AdapterEvenement(String[] myDataset) {
        mDataset = myDataset;
        initializeData();
    }

    // Constructeur
    public AdapterEvenement(List<Evenement> evenements) {
        this.evenements = evenements;
    }

    // Crée les nouvelles vues
    @Override
    public AdapterEvenement.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        // Mise en forme de la vue
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Remplacement du contenu de la vue
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - Recupere l'elmeent des donnees à cette position
        // - Remplace le contenu de la vue avec ces données
        holder.mNomEvenement.setText(evenements.get(position).getNomEvenement());
        holder.mNomSport.setText(evenements.get(position).getSport());
        holder.mNombreParticipant.setText(evenements.get(position).getGroupeAssocie().getListeMembre().size() + "/" + evenements.get(position).getNbreMaxParticipants());
        holder.mLieu.setText(evenements.get(position).getLieu());
        holder.mOrganisateur.setText(evenements.get(position).getOrganisateur().getNom());
        if(holder.mNomSport.getText().equals("basket")){
            holder.mPhoto.setImageResource(R.drawable.basketball);
        }
        else if(holder.mNomSport.getText().equals("tennis"))
            holder.mPhoto.setImageResource(R.drawable.tennis);

    }

    // Retourne la taille des données (utilisé par le layout manager)
    @Override
    public int getItemCount() {
        return evenements.size();
    }

    private void initializeData(){
        evenements = new ArrayList<>();
        Date date = new Date();
        Utilisateur u1 = new Utilisateur();
        u1.setNom("Jean Paul");
        evenements = new ArrayList<>();
        Evenement evenement = new Evenement();
        evenement.setNbreMaxParticipants(10);
        GregorianCalendar test = new GregorianCalendar(2016, 03, 27);
        date = test.getTime();
        evenement.setDate(date);
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
        evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);
        Evenement evenement1 = new Evenement();
        evenement1 = evenement;
        evenements.add(evenement1);

    }
}
