package com.example.alexmao.projetfinal.Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexmao.projetfinal.Activites.AffichageEvenement;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Groupe;
import com.example.alexmao.projetfinal.classeApp.Sport;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 */
public class AdapterEvenement extends RecyclerView.Adapter<AdapterEvenement.ViewHolder> {
    private String[] mDataset;
    public static List<Evenement> evenements;
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
        Sport.initialize();
        mDataset = myDataset;
        //initializeData();
    }

    // Constructeur
    public AdapterEvenement(List<Evenement> evenements) {
        Sport.initialize();
        this.evenements = evenements;
        notifyDataSetChanged();
    }

    public void AjouterEvenement(Evenement evenement) {
        Sport.initialize();
        evenements.add(evenement);
        notifyDataSetChanged();
    }

    // Crée les nouvelles vues
    @Override
    public AdapterEvenement.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   final int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CharSequence text = "Hello toast!";
                RecyclerView r = (RecyclerView) v.getParent();
                int pos = r.getChildAdapterPosition(v);
                Toast.makeText(r.getContext(), text, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(r.getContext(), AffichageEvenement.class);
                //Parcel parcel =  Parcel.obtain();
                //evenements.get(pos).writeToParcel(parcel,0);
                //evenements.get(pos).getIdFirebase();
                intent.putExtra("evenement", evenements.get(pos).getIdFirebase());
                r.getContext().startActivity(intent);
            }
        });
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
        holder.mPhoto.setImageResource(Sport.stringToDrawable.get(evenements.get(position).getSport()));
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
        evenement.setDate(test.getTimeInMillis());
        evenement.setLieu("parc kent");
        evenement.setNomEvenement("Fin de session");
        evenement.setOrganisateur(u1);
        evenement.setSport("football");
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
        evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);
        evenements.add(evenement);
        Evenement evenement1 = new Evenement();
        evenement1 = evenement;
        evenements.add(evenement1);

    }

}
