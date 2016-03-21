package com.example.alexmao.tp2.fragment;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alexmao.tp2.R;
import com.example.alexmao.tp2.User;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class UserListAdapter extends ArrayAdapter<User> {

        private Context context;
        List<User> users;

        private static final SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.ENGLISH);

        public UserListAdapter(Context context, List<User> users) {
            super(context, R.layout.list_item, users);
            this.context = context;
            this.users = users;
        }

        private class ViewHolder {
            TextView empIdTxt;
            TextView empNameTxt;
            TextView empDobTxt;
            TextView empSalaryTxt;
            TextView empDeptNameTxt;
        }

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public User getItem(int position) {
            return users.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder();

                holder.empIdTxt = (TextView) convertView
                        .findViewById(R.id.txt_emp_id);
                holder.empNameTxt = (TextView) convertView
                        .findViewById(R.id.txt_emp_name);
                holder.empDobTxt = (TextView) convertView
                        .findViewById(R.id.txt_emp_dob);
                holder.empSalaryTxt = (TextView) convertView
                        .findViewById(R.id.txt_emp_salary);
                holder.empDeptNameTxt = (TextView) convertView
                        .findViewById(R.id.txt_emp_dept);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            User users = (User) getItem(position);
            holder.empIdTxt.setText(users.getId() + "");
            //holder.empNameTxt.setText(users.getName());
            //holder.empSalaryTxt.setText(users.getSalary() + "");
            //holder.empDeptNameTxt.setText(users.getDepartment().getName());

           //holder.empDobTxt.setText(formatter.format(users.getDateOfBirth()));

            return convertView;
        }

        @Override
        public void add(User user) {
            users.add(user);
            notifyDataSetChanged();
            super.add(user);
        }

        @Override
        public void remove(User user) {
            users.remove(user);
            notifyDataSetChanged();
            super.remove(user);
        }
    }

