package com.example.alexmao.projetfinal.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexmao.projetfinal.Activites.interfaces.BtnConnexionAccepterClickListener;
import com.example.alexmao.projetfinal.Activites.interfaces.BtnConnexionRefuserClickListener;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.InvitationConnexion;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Fabien on 26/04/2016.
 */
public class AdapterInvitationConnexion extends RecyclerView.Adapter<AdapterInvitationConnexion.InvitationConnexionViewHolder> {
    private List<InvitationConnexion> invitationsConnexions;
    private BtnConnexionAccepterClickListener listenerCA;
    private BtnConnexionRefuserClickListener listenerCR;

    public AdapterInvitationConnexion(List<InvitationConnexion> invitationConnexions, BtnConnexionAccepterClickListener listenerCA,
                                      BtnConnexionRefuserClickListener listenerCR){
//        this.invitationsConnexions = new ArrayList<>();
        this.invitationsConnexions = invitationConnexions;
        this.listenerCA = listenerCA;
        this.listenerCR = listenerCR;
        //initializeData();
    }



    public void removeItem(InvitationConnexion invConnexion) {
        invitationsConnexions.remove(invConnexion);
        notifyDataSetChanged();
    }

    @Override
    //Retourne le nombre d'éléments dans la liste
    public int getItemCount() {
        return invitationsConnexions.size();
    }

    @Override
    public InvitationConnexionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_invitation_connexion, viewGroup, false);
        InvitationConnexionViewHolder icvh = new InvitationConnexionViewHolder(v);
        return icvh;
    }

    @Override
    //Fonction permettant d'assigner les différents éléments avec leur valeur
    public void onBindViewHolder(AdapterInvitationConnexion.InvitationConnexionViewHolder holder, int position) {
        holder.personName.setText(invitationsConnexions.get(position).getExpediteur().getNom());
        holder.personAge.setText("22 ans");
        holder.personPhoto.setImageResource(R.drawable.user_chat1);
        holder.btnAccepter.setTag(invitationsConnexions.get(position));
        holder.btnRefuser.setTag(invitationsConnexions.get(position));
        holder.btnAccepter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (listenerCA != null)
                    listenerCA.onBtnConnexionAccepterClick((InvitationConnexion) v.getTag());
            }
        });
        holder.btnRefuser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (listenerCR != null)
                    listenerCR.onBtnConnexionRefuserClick((InvitationConnexion) v.getTag());
            }
        });
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class InvitationConnexionViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;
        Button btnAccepter;
        Button btnRefuser;

        InvitationConnexionViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.invc_cv);
            personName = (TextView)itemView.findViewById(R.id.invc_person_name);
            personAge = (TextView)itemView.findViewById(R.id.invc_person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.invc_person_photo);
            btnAccepter = (Button) itemView.findViewById(R.id.invc_btn_accepter);
            btnRefuser = (Button) itemView.findViewById(R.id.invc_btn_refuser);
        }
    }


    private void initializeData(){
        invitationsConnexions = new ArrayList<>();
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
        InvitationConnexion inv1 = new InvitationConnexion();
        inv1.setExpediteur(u2);
        InvitationConnexion inv2 = new InvitationConnexion();
        inv2.setExpediteur(u3);
        invitationsConnexions.add(inv1);
        invitationsConnexions.add(inv2);
    }
}
