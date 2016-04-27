package com.example.alexmao.projetfinal.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexmao.projetfinal.Activites.interfaces.BtnEvenementAccepterClickListener;
import com.example.alexmao.projetfinal.Activites.interfaces.BtnEvenementRefuserClickListener;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.InvitationConnexion;
import com.example.alexmao.projetfinal.classeApp.InvitationEvenement;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Fabien on 26/04/2016.
 */
public class AdapterInvitationEvenement extends RecyclerView.Adapter<AdapterInvitationEvenement.InvitationEvenementViewHolder> {
    private List<InvitationEvenement> invitationsEvenements;
    private BtnEvenementAccepterClickListener listenerEA;
    private BtnEvenementRefuserClickListener listenerER;

    public AdapterInvitationEvenement(List<InvitationEvenement> invitationEvenements, BtnEvenementAccepterClickListener listenerEA,
                                      BtnEvenementRefuserClickListener listenerER){
        this.invitationsEvenements = invitationsEvenements;
        this.listenerEA = listenerEA;
        this.listenerER = listenerER;
        initializeData();
    }

    public void removeItem(InvitationEvenement invEvenement) {
        invitationsEvenements.remove(invEvenement);
        notifyDataSetChanged();
    }

    @Override
    //Retourne le nombre d'éléments dans la liste
    public int getItemCount() {
        return invitationsEvenements.size();
    }

    @Override
    public InvitationEvenementViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_invitation_evenement, viewGroup, false);
        InvitationEvenementViewHolder ievh = new InvitationEvenementViewHolder(v);
        return ievh;
    }

    @Override
    //Fonction permettant d'assigner les différents éléments avec leur valeur
    public void onBindViewHolder(AdapterInvitationEvenement.InvitationEvenementViewHolder holder, int position) {
        holder.personName.setText("Invité par "+invitationsEvenements.get(position).getExpediteur().getNom());
        holder.eventName.setText(invitationsEvenements.get(position).getEvenement().getNomEvenement());
        holder.personPhoto.setImageResource(R.drawable.user_chat1);
        holder.btnAccepter.setTag(invitationsEvenements.get(position));
        holder.btnRefuser.setTag(invitationsEvenements.get(position));
        holder.btnAccepter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (listenerEA != null)
                    listenerEA.onBtnEvenementAccepterClick((InvitationEvenement) v.getTag());
            }
        });
        holder.btnRefuser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (listenerER != null)
                    listenerER.onBtnEvenementRefuserClick((InvitationEvenement) v.getTag());
            }
        });
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class InvitationEvenementViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView eventName;
        ImageView personPhoto;
        Button btnAccepter;
        Button btnRefuser;

        InvitationEvenementViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.invc_cv);
            personName = (TextView)itemView.findViewById(R.id.inve_person_name);
            eventName = (TextView)itemView.findViewById(R.id.inve_event_name);
            personPhoto = (ImageView)itemView.findViewById(R.id.inve_person_photo);
            btnAccepter = (Button) itemView.findViewById(R.id.inve_btn_accepter);
            btnRefuser = (Button) itemView.findViewById(R.id.inve_btn_refuser);
        }
    }

    private void initializeData(){
        invitationsEvenements = new ArrayList<>();
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
        // Evenements
        Evenement evenement = new Evenement();
        evenement.setNbreMaxParticipants(10);
        GregorianCalendar test2 = new GregorianCalendar(2016, 03, 27);
        date = test2.getTime();
        evenement.setDate(date.getTime());
        evenement.setLieu("parc kent");
        evenement.setNomEvenement("Fin de session");
        evenement.setOrganisateur(u1);
        evenement.setSport("foot");
        //Création invitations
        InvitationEvenement inv1 = new InvitationEvenement();
        inv1.setEvenement(evenement);
        inv1.setExpediteur(u2);
        InvitationEvenement inv2 = new InvitationEvenement();
        inv2.setEvenement(evenement);
        inv2.setExpediteur(u3);
        invitationsEvenements.add(inv1);
        invitationsEvenements.add(inv2);
    }
}
