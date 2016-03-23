package com.example.alexmao.tp2final;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alexMAO on 14/03/2016.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.ViewHolder> {

    private static final String DEBUG_TAG =  "TestRecyclerViewAdapter";
    List<Object> contents;
    private Context context;
    private int indiceClasse;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public TextView mTextView;
        protected static TextView vName;
        protected static TextView vSurname;
        protected static TextView vEmail;
        protected static TextView vTitle;
        protected static TextView vAddress;
        public ViewHolder(View convertView) {
            super(convertView);
            //mTextView = convertView;
            vName =  (TextView) convertView.findViewById(R.id.txtName);
            vSurname = (TextView)  convertView.findViewById(R.id.txtSurname);
            vEmail = (TextView)  convertView.findViewById(R.id.txtEmail);
            vTitle = (TextView) convertView.findViewById(R.id.title);
            vAddress = (TextView) convertView.findViewById(R.id.txtAdd);
        }
    }


    public TestRecyclerViewAdapter(Context context, List<Object> contents, int indiceClasseRecu) {
        this.indiceClasse = indiceClasseRecu;
        this.contents = contents;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card, parent, false);
        return new ViewHolder(view) {

        };
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(DEBUG_TAG, "zd");
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder(convertView);
            Log.d(DEBUG_TAG, "On est dans getView");
            /*holder.vN = (TextView) convertView
                    .findViewById(R.id.txt_user_id);
            holder.userNameTxt = (TextView) convertView
                    .findViewById(R.id.txt_user_name);
            holder.userDobTxt = (TextView) convertView
                    .findViewById(R.id.txt_user_dob);
            holder.userSalaryTxt = (TextView) convertView
                    .findViewById(R.id.txt_user_salary);
            holder.userDeptNameTxt = (TextView) convertView
                    .findViewById(R.id.txt_user_dept);*/
            /*holder.vName =  (TextView) convertView.findViewById(R.id.txtName);
            holder.vSurname = (TextView)  convertView.findViewById(R.id.txtSurname);
            holder.vEmail = (TextView)  convertView.findViewById(R.id.txtEmail);
            holder.vTitle = (TextView) convertView.findViewById(R.id.title);*/
            /*User ci = (User) contents.get(position);

            ViewHolder.vName.setText(ci.getNom());
            ViewHolder.vSurname.setText(ci.getPrenom());
            ViewHolder.vEmail.setText(ci.getMail_());
            ViewHolder.vTitle.setText("");*/
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        User users = new User("Jean", "Paul", "jean.paul@gmail.com", null, true, null, null  );
        //holder.vName.setText(users.getId() + "");
        //holder.userNameTxt.setText(users.getName());
        //holder.userSalaryTxt.setText(users.getSalary() + "");
        //holder.userDeptNameTxt.setText(users.getDepartment().getName());

        //holder.userDobTxt.setText(formatter.format(users.getDateOfBirth()));

        return convertView;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(indiceClasse == 0) {
            User ci = (User) contents.get(position);
            holder.vName.setText(ci.getNom());
            holder.vSurname.setText(ci.getPrenom());
            holder.vEmail.setText(ci.getMail_());
            holder.vTitle.setText(ci.getNom() + " " + ci.getPrenom());
            holder.vAddress.setText(position+"");
        }else{
            Groupe g = (Groupe) contents.get(position);

            String surname = "Nombre d'utilisateurs : " + g.getUsers().size();
            holder.vAddress.setText(surname);
            holder.vName.setText(g.getNomGroupe());
            holder.vSurname.setText(position+"");
            String email = "Pas d'email car groupe";
            holder.vEmail.setText(email);
            String title = "Position " + position;
            holder.vTitle.setText(g.getNomGroupe());
        }

    }
}