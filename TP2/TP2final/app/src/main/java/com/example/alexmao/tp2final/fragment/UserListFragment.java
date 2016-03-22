package com.example.alexmao.tp2final.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alexmao.tp2final.GroupeBDD;
import com.example.alexmao.tp2final.R;
import com.example.alexmao.tp2final.User;
import com.example.alexmao.tp2final.UsersBDD;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
/**
 * Created by alexMAO on 21/03/2016.
 */
public class UserListFragment extends Fragment implements OnItemClickListener,
        OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "user_list";
    private static final String DEBUG_TAG ="UserListFragment" ;

    Activity activity;
    ListView userListView;
    ArrayList<User> users;

    UserListAdapter userListAdapter;
    UsersBDD userBDD;
    GroupeBDD groupeBDD;
    private GetUserTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        userBDD = new UsersBDD(activity);
        groupeBDD = new GroupeBDD(activity);
        Log.d(DEBUG_TAG, "on a fini on create");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "On repasse dans la création de la vue avec la mise des cards");
        View view = inflater.inflate(R.layout.fragment_user_list, container,
                false);
        findViewsById(view);

        task = new GetUserTask(activity);
        task.execute((Void) null);

        userListView.setOnItemClickListener(this);
        userListView.setOnItemLongClickListener(this);
        return view;
    }

    private void findViewsById(View view) {
        userListView = (ListView) view.findViewById(R.id.list_emp);
    }

    @Override
    public void onItemClick(AdapterView<?> list, View view, int position,
                            long id) {
        Log.d(DEBUG_TAG, "Click d'un item");
        User user = (User) list.getItemAtPosition(position);

        if (user != null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("selectedUser", user);
            CustomUserDialogFragment customUserDialogFragment = new CustomUserDialogFragment();
            customUserDialogFragment.setArguments(arguments);
            customUserDialogFragment.show(getFragmentManager(),
                    CustomUserDialogFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
        User user = (User) parent.getItemAtPosition(position);
        // Use AsyncTask to delete from database
        userBDD.removeUserWithID(user.getId());
        userListAdapter.remove(user);

        return true;
    }

    public class GetUserTask extends AsyncTask<Void, Void, ArrayList<User>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetUserTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<User> doInBackground(Void... arg0) {
            groupeBDD.open();
            ArrayList<User> userList = groupeBDD.getGroupeBDD("equipe2").getUsers();
            //ArrayList<User> userList = new ArrayList<>();
            Log.d(DEBUG_TAG, "recuperation de la liste d'utilisateur");
            groupeBDD.close();
            return userList;
        }

        @Override
        protected void onPostExecute(ArrayList<User> empList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                users = empList;
                if (empList != null) {
                    if (empList.size() != 0) {
                        userListAdapter = new UserListAdapter(activity,
                                empList);
                        userListView.setAdapter(userListAdapter);
                    } else {
                        Toast.makeText(activity, "No User Records",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    /*
     * This method is invoked from MainActivity onFinishDialog() method. It is
     * called from CustomUserDialogFragment when an user record is updated.
     * This is used for communicating between fragments.
     */
    public void updateView() {
        task = new GetUserTask(activity);
        task.execute((Void) null);
    }

    @Override
    public void onResume() {
        Log.d(DEBUG_TAG, "on resume");
        getActivity().setTitle(R.string.app_name);
        //getActivity().getActionBar().setTitle(R.string.app_name);
        super.onResume();
        Log.d(DEBUG_TAG, "sortie de on resume");
    }
}