package com.example.alexmao.projetfinal.Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexmao.projetfinal.Activites.ProfilUtilisateur;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 */
public class AdapterUtilisateur extends RecyclerView.Adapter<AdapterUtilisateur.UtilisateurViewHolder>{

    private List<Utilisateur> persons;

    public AdapterUtilisateur(List<Utilisateur> persons){
        Log.d("AdapterUtilisater", "On crée l'adapter");
        this.persons = persons;
        //initializeData();
    }

    public AdapterUtilisateur(){
        Log.d("AdapterUtilisater", "On crée l'adapter");
        initializeData();
    }

    public void ajouterUtilisateur(Utilisateur utilisateur){
        persons.add(utilisateur);
        notifyDataSetChanged();
    }

    @Override
    //Retourne le nombre d'éléments dans la liste
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public UtilisateurViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_membre, viewGroup, false);
        UtilisateurViewHolder uvh = new UtilisateurViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CharSequence text = "Hello toast!";
                RecyclerView r = (RecyclerView) v.getParent();
                int pos = r.getChildAdapterPosition(v);
                Toast.makeText(r.getContext(), text, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(r.getContext(), ProfilUtilisateur.class);
                //Parcel parcel =  Parcel.obtain();
                //evenements.get(pos).writeToParcel(parcel,0);
                //evenements.get(pos).setIdFirebase("test");
                Log.d("Adapter Utilisateur", "id de l'utilisateur : "  + persons.get(pos).getIdBDD());
                intent.putExtra("id", (persons.get(pos).getIdBDD()));
                r.getContext().startActivity(intent);
            }
        });


        return uvh;
    }

    @Override
    //Fonction permettant d'assigner les différents éléments avec leur valeur
    public void onBindViewHolder(AdapterUtilisateur.UtilisateurViewHolder holder, int position) {
        holder.personName.setText(persons.get(position).construireNom());
        holder.personAge.setText(persons.get(position).construireDDN());
        holder.personPhoto.setImageResource(R.drawable.user_chat1);
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class UtilisateurViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        UtilisateurViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
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