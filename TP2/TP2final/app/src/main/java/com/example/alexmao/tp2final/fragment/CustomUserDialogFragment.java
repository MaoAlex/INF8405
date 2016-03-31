package com.example.alexmao.tp2final.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alexmao.tp2final.Groupe;
import com.example.alexmao.tp2final.GroupeBDD;
import com.example.alexmao.tp2final.R;
import com.example.alexmao.tp2final.User;
import com.example.alexmao.tp2final.UsersBDD;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by alexMAO on 21/03/2016.
 */
public class CustomUserDialogFragment extends DialogFragment {

    private static final String DEBUG_TAG = "CustomUserDialog";
    // UI references
    private EditText userNameEtxt;
    private EditText userPrenomEtxt;
    private EditText userMailEtxt;
    private Spinner deptSpinner;
    private LinearLayout submitLayout;

    private User user;

    UsersBDD userBDD;
    ArrayAdapter<Groupe> adapter;

    public static final String ARG_ITEM_ID = "user_dialog_fragment";

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    /*
     * Callback used to communicate with EmpListFragment to notify the list adapter.
     * CardActivity implements this interface and communicates with EmpListFragment.
     */
    public interface CustomUserDialogFragmentListener {
        void onFinishDialog();
    }

    public CustomUserDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        userBDD = new UsersBDD(getActivity());
        Log.d(DEBUG_TAG, "On est dans CustomUserDialogFragment");
        Bundle bundle = this.getArguments();
        user = bundle.getParcelable("selectedUser");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customDialogView = inflater.inflate(R.layout.fragment_add_user,
                null);
        builder.setView(customDialogView);

        userNameEtxt = (EditText) customDialogView.findViewById(R.id.etxt_name);
        userPrenomEtxt = (EditText) customDialogView
                .findViewById(R.id.etxt_salary);
        userMailEtxt = (EditText) customDialogView.findViewById(R.id.etxt_dob);
        deptSpinner = (Spinner) customDialogView
                .findViewById(R.id.spinner_dept);
        submitLayout = (LinearLayout) customDialogView
                .findViewById(R.id.layout_submit);
        submitLayout.setVisibility(View.GONE);
        setValue();

        builder.setTitle(R.string.update_user);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.update,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*try {
                            //user.setDateOfBirth(formatter.parse(userMailEtxt.getText().toString()));
                            user.setMail_("test@gmail.com");
                        } catch (ParseException e) {
                            Toast.makeText(getActivity(),
                                    "Invalid date format!",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }*/
                        user.setNom(userNameEtxt.getText().toString());
                        //user.setPrenom(Double.parseDouble(userPrenomEtxt
                        //        .getText().toString()));
                        Groupe dept = (Groupe) adapter
                                .getItem(deptSpinner.getSelectedItemPosition());
                        //user.setGroupe(dept);
                        long result = userBDD.updateUser(user.getId(), user);
                        if (result > 0) {
                            CardActivity activity = (CardActivity) getActivity();
                            activity.onFinishDialog();
                        } else {
                            Toast.makeText(getActivity(),
                                    "Unable to update user",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    private void setValue() {
        GroupeBDD groupeBDD = new GroupeBDD(getActivity());
        groupeBDD.open();
        List<Groupe> groupes = null ;
                groupeBDD.getGroupes();
       /* adapter = new ArrayAdapter<Groupe>(getActivity(), android.R.layout.simple_list_item_1, null);
        deptSpinner.setAdapter(adapter);
        int pos = adapter.getPosition(user.getId());

        if (user != null) {
            userNameEtxt.setText(user.getNom());
            userPrenomEtxt.setText(user.getPrenom() + "");
            userMailEtxt.setText(user.getMail_());
            deptSpinner.setSelection(pos);
        }*/
    }
}