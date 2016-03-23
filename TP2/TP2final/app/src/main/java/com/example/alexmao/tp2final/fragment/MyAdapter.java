package com.example.alexmao.tp2final.fragment;

import android.support.v7.widget.RecyclerView;
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

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    /*
    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.UserViewHolder onCreateUserViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        //UserViewHolder vh = new UserViewHolder(v);
        //return vh;
        return null;
    }

        // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindUserViewHolder(UserViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }*/


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
        UserViewHolder.vName.setText(ci.getNom());
        UserViewHolder.vSurname.setText(ci.getPrenom());
        UserViewHolder.vEmail.setText(ci.getMail_());
        UserViewHolder.vTitle.setText(ci.getNom() + " " + ci.getPrenom());
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
        public UserViewHolder(View v) {
            super(v);
            //mTextView = v;
            vName =  (TextView) v.findViewById(R.id.txtName);
            vSurname = (TextView)  v.findViewById(R.id.txtSurname);
            vEmail = (TextView)  v.findViewById(R.id.txtEmail);
            vTitle = (TextView) v.findViewById(R.id.title);
        }
    }
}