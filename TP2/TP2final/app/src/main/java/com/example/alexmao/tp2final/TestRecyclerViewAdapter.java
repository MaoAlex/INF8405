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
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String DEBUG_TAG =  "TestRecyclerViewAdapter";
    List<Object> contents;
    private Context context;
    private class ViewHolder {
        TextView userIdTxt;
        TextView userNameTxt;
        TextView userDobTxt;
        TextView userSalaryTxt;
        TextView userDeptNameTxt;
    }
    public TestRecyclerViewAdapter(Context context, List<Object> contents) {
        this.contents = contents;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card, parent, false);
        return new RecyclerView.ViewHolder(view) {

        };
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(DEBUG_TAG, "zd");
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.userNameTxt = (TextView) convertView
                    .findViewById(R.id.txt_user_id);
            holder.userNameTxt = (TextView) convertView
                    .findViewById(R.id.txt_user_name);
            holder.userDobTxt = (TextView) convertView
                    .findViewById(R.id.txt_user_dob);
            holder.userSalaryTxt = (TextView) convertView
                    .findViewById(R.id.txt_user_salary);
            holder.userDeptNameTxt = (TextView) convertView
                    .findViewById(R.id.txt_user_dept);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        User users = new User("Jean", "Paul", "jean.paul@gmail.com", null, true, null, null  );
        holder.userIdTxt.setText(users.getId() + "");
        //holder.userNameTxt.setText(users.getName());
        //holder.userSalaryTxt.setText(users.getSalary() + "");
        //holder.userDeptNameTxt.setText(users.getDepartment().getName());

        //holder.userDobTxt.setText(formatter.format(users.getDateOfBirth()));

        return convertView;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }
}