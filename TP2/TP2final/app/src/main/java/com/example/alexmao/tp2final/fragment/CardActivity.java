package com.example.alexmao.tp2final.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alexmao.tp2final.GroupeBDD;
import com.example.alexmao.tp2final.R;
import com.example.alexmao.tp2final.UsersBDD;

/**
 * Created by alexMAO on 21/03/2016.
 */

public class CardActivity extends FragmentActivity implements CustomUserDialogFragment.CustomUserDialogFragmentListener {

    private static final String DEBUG_TAG = "CardActivity";
    private Fragment contentFragment;
    private UserListFragment userListFragment;
    private UserAddFragment userAddFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Log.d(DEBUG_TAG, "Création du fragment réussi");
        UsersBDD usersBDD = new UsersBDD(this);
        GroupeBDD groupeBDD = new GroupeBDD(this);

        Log.d(DEBUG_TAG, "récupération des groupes réussis");
		/*
		 * This is called when orientation is changed.
		 */
        if (savedInstanceState != null) {
            Log.d(DEBUG_TAG, "savedInstanceState différent de null");
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(UserAddFragment.ARG_ITEM_ID)) {
                    if (fragmentManager
                            .findFragmentByTag(UserAddFragment.ARG_ITEM_ID) != null) {
                        setFragmentTitle(R.string.add_user);
                        contentFragment = fragmentManager
                                .findFragmentByTag(UserAddFragment.ARG_ITEM_ID);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(UserListFragment.ARG_ITEM_ID) != null) {
                userListFragment = (UserListFragment) fragmentManager
                        .findFragmentByTag(UserListFragment.ARG_ITEM_ID);
                contentFragment = userListFragment;
            }
        } else {
            Log.d(DEBUG_TAG, "saveInstance est null");
            userListFragment = new UserListFragment();
            Log.d(DEBUG_TAG, "creation userlistFragment");

            setFragmentTitle(R.string.app_name);

            switchContent(userListFragment, UserListFragment.ARG_ITEM_ID);
        }
        /*usersBDD.close();
        groupeBDD.close();*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                setFragmentTitle(R.string.add_user);
                userAddFragment = new UserAddFragment();
                switchContent(userAddFragment, UserAddFragment.ARG_ITEM_ID);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof UserAddFragment) {
            outState.putString("content", UserAddFragment.ARG_ITEM_ID);
        } else {
            outState.putString("content", UserListFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }

    /*
     * We consider UserListFragment as the home fragment and it is not added to
     * the back stack.
     */
    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            // Only UserAddFragment is added to the back stack.
            if (!(fragment instanceof UserListFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    protected void setFragmentTitle(int resourseId) {
        setTitle(resourseId);
        if(getActionBar()!=null)
            getActionBar().setTitle(resourseId);

    }

    /*
     * We call super.onBackPressed(); when the stack entry count is > 0. if it
     * is instanceof UserListFragment or if the stack entry count is == 0, then
     * we prompt the user whether to quit the app or not by displaying dialog.
     * In other words, from UserListFragment on back press it quits the app.
     */
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof UserListFragment
                || fm.getBackStackEntryCount() == 0) {
            //finish();
            //Shows an alert dialog on quit
            onShowQuitDialog();
        }
    }

    public void onShowQuitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        builder.setMessage("Do You Want To Quit?");
        builder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.setNegativeButton(android.R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    /*
     * Callback used to communicate with UserListFragment to notify the list adapter.
     * Communication between fragments goes via their Activity class.
     */
    @Override
    public void onFinishDialog() {
        if (userListFragment != null) {
            userListFragment.updateView();
        }
    }
}