package com.example.alexmao.tp2final.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexmao.tp2final.R;
import com.example.alexmao.tp2final.User;

import java.util.List;

/**
 * Created by alexMAO on 22/03/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.UserViewHolder> {

    private String[] mDataset;

    private List<User> userList;

    public MyAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public void onBindViewHolder(UserViewHolder userUserViewHolder, int i) {
        User ci = userList.get(i);
        String nouveauNom = ci.getNom().concat(" ").concat(ci.getPrenom());
        UserViewHolder.vName.setText(nouveauNom);
        Log.d("testActivity", "nom affiché : " + nouveauNom);
        UserViewHolder.vSurname.setText(ci.getPrenom());
        Log.d("testActivity", "prenom : " + ci.getPrenom());
        UserViewHolder.vEmail.setText(ci.getMail_());
        Log.d("testActivity", "adresseMail : " + ci.getMail_());
        UserViewHolder.vTitle.setText("Panda");
        UserViewHolder.vAdress.setText(ci.getMail_());
        Log.d("testActivity", "adresseMail : " + ci.getMail_());

    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.my_text_view, viewGroup, false);

        return new UserViewHolder(itemView);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        protected static TextView vName;
        protected static TextView vSurname;
        protected static TextView vEmail;
        protected static TextView vTitle;
        protected static TextView vAdress;
        public UserViewHolder(View v) {
            super(v);
            //mTextView = v;
            vName =  (TextView) v.findViewById(R.id.txtName);
            vSurname = (TextView)  v.findViewById(R.id.txtSurname);
            vEmail = (TextView)  v.findViewById(R.id.txtEmail);
            vTitle = (TextView) v.findViewById(R.id.title);
            vAdress = (TextView) v.findViewById(R.id.txtAdd);
        }
    }
}