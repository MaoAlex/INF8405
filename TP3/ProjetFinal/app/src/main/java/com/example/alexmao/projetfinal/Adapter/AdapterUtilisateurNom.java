package com.example.alexmao.projetfinal.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Fabien on 27/04/2016.
 */
public class AdapterUtilisateurNom extends RecyclerView.Adapter<AdapterUtilisateurNom.UtilisateurNomViewHolder>{

    private List<Utilisateur> persons;
    private List<Utilisateur> utilisateursSelectionnes;

    public AdapterUtilisateurNom(List<Utilisateur> persons, List<Utilisateur> utilisateursSelectionnes){
        this.persons = persons;
        this.utilisateursSelectionnes = utilisateursSelectionnes;
        initializeData();
    }

    public List<Utilisateur> getUtilisateursSelectionnes() {
        return this.utilisateursSelectionnes;
    }

    public void updateDataset(List<Utilisateur> newDataset) {
        this.persons = newDataset;
        notifyDataSetChanged();
    }
    @Override
    //Retourne le nombre d'éléments dans la liste
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public UtilisateurNomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_membre_short, viewGroup, false);
        UtilisateurNomViewHolder unvh = new UtilisateurNomViewHolder(v);
        return unvh;
    }

    @Override
    //Fonction permettant d'assigner les différents éléments avec leur valeur
    public void onBindViewHolder(AdapterUtilisateurNom.UtilisateurNomViewHolder holder, int position) {
        holder.personName.setText(persons.get(position).getNom());
        if(utilisateursSelectionnes.contains(persons.get(position))) {
            holder.personSel.setChecked(true);
        }
        holder.personSel.setTag(persons.get(position));
        holder.personSel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    utilisateursSelectionnes.add((Utilisateur) buttonView.getTag());
                } else {
                    utilisateursSelectionnes.remove((Utilisateur) buttonView.getTag());
                }
            }
        });
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class UtilisateurNomViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        CheckBox personSel;

        UtilisateurNomViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_user);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personSel = (CheckBox)itemView.findViewById(R.id.person_sel);
        }
    }

    private void initializeData(){
        persons = new ArrayList<>();
        Date date;
        Utilisateur u1 = new Utilisateur();
        u1.setNom("Jean Paul");
        GregorianCalendar test = new GregorianCalendar(1993, 02, 24);
        date = test.getTime();
        u1.setDateNaissance(test.getTimeInMillis());
        Utilisateur u2 = new Utilisateur();
        u2.setNom("Poly Technique");
        u2.setDateNaissance(test.getTimeInMillis());
        Utilisateur u3 = new Utilisateur();
        u3.setNom("Mont Real");
        u3.setDateNaissance(test.getTimeInMillis());
        persons.add(u1);
        persons.add(u2);
        persons.add(u3);
    }
}
