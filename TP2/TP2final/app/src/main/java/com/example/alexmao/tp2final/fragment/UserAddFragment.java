package com.example.alexmao.tp2final.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alexmao.tp2final.Groupe;
import com.example.alexmao.tp2final.GroupeBDD;
import com.example.alexmao.tp2final.R;
import com.example.alexmao.tp2final.User;
import com.example.alexmao.tp2final.UsersBDD;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserAddFragment extends Fragment implements OnClickListener {

    private static final String DEBUG_TAG = "UserAddFragment" ;
    // UI references
    private EditText userNameEtxt;
    private EditText userSalaryEtxt;
    private EditText userDobEtxt;
    private Spinner deptSpinner;
    private Button addButton;
    private Button resetButton;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;

    User user = null;
    private UsersBDD userBDD;
    private GroupeBDD groupeBDD;
    private GetDeptTask task;
    private AddEmpTask addEmpTask;

    public static final String ARG_ITEM_ID = "user_add_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userBDD = new UsersBDD(getActivity());
        groupeBDD = new GroupeBDD(getActivity());
        Log.d(DEBUG_TAG, "C'est parti pour ajouter les fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "Debut de la création des différents fragments");
        View rootView = inflater.inflate(R.layout.fragment_add_user, container,
                false);

        findViewsById(rootView);

        setListeners();

        // Used for orientation change
		/*
		 * After entering the fields, change the orientation.
		 * NullPointerException occurs for date. This piece of code avoids it.
		 */
        if (savedInstanceState != null) {
            dateCalendar = Calendar.getInstance();
            if (savedInstanceState.getLong("dateCalendar") != 0)
                dateCalendar.setTime(new Date(savedInstanceState
                        .getLong("dateCalendar")));
        }

        // asynchronously retrieves groupe from table and sets it in Spinner
        task = new GetDeptTask(getActivity());
        task.execute((Void) null);

        return rootView;
    }

    private void setListeners() {
        userDobEtxt.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(),
                new OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateCalendar = Calendar.getInstance();
                        dateCalendar.set(year, monthOfYear, dayOfMonth);
                        userDobEtxt.setText(formatter.format(dateCalendar
                                .getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        addButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    protected void resetAllFields() {
        userNameEtxt.setText("");
        userSalaryEtxt.setText("");
        userDobEtxt.setText("");
        if (deptSpinner.getAdapter().getCount() > 0)
            deptSpinner.setSelection(0);
    }

    private void setUser() {
        user = new User();
        user.setNom(userNameEtxt.getText().toString());
        /*user.setSalary(Double.parseDouble(userSalaryEtxt.getText()
                .toString()));
        if (dateCalendar != null)
            user.setDateOfBirth(dateCalendar.getTime());
        Groupe selectedDept = (Groupe) deptSpinner.getSelectedItem();
        user.setGroupe(selectedDept);*/
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.add_user);
        getActivity().getActionBar().setTitle(R.string.add_user);
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (dateCalendar != null)
            outState.putLong("dateCalendar", dateCalendar.getTime().getTime());
    }

    private void findViewsById(View rootView) {
        userNameEtxt = (EditText) rootView.findViewById(R.id.etxt_name);
        userSalaryEtxt = (EditText) rootView.findViewById(R.id.etxt_salary);
        userDobEtxt = (EditText) rootView.findViewById(R.id.etxt_dob);
        userDobEtxt.setInputType(InputType.TYPE_NULL);

        deptSpinner = (Spinner) rootView.findViewById(R.id.spinner_dept);
        addButton = (Button) rootView.findViewById(R.id.button_add);
        resetButton = (Button) rootView.findViewById(R.id.button_reset);
    }

    @Override
    public void onClick(View view) {
        if (view == userDobEtxt) {
            datePickerDialog.show();
        } else if (view == addButton) {
            setUser();
            addEmpTask = new AddEmpTask(getActivity());
            addEmpTask.execute((Void) null);
        } else if (view == resetButton) {
            resetAllFields();
        }
    }

    public class GetDeptTask extends AsyncTask<Void, Void, Void> {

        private final WeakReference<Activity> activityWeakRef;
        private List<Groupe> groupes;

        public GetDeptTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //groupes = groupeBDD.getGroupeBDD("equipe1");
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {

                ArrayAdapter<Groupe> adapter = new ArrayAdapter<Groupe>(
                        activityWeakRef.get(),
                        android.R.layout.simple_list_item_1, groupes);
                deptSpinner.setAdapter(adapter);

                addButton.setEnabled(true);
            }
        }
    }

    public class AddEmpTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;

        public AddEmpTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            long result = userBDD.insertUser(user);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                if (result != -1)
                    Toast.makeText(activityWeakRef.get(), "User Saved",
                            Toast.LENGTH_LONG).show();
            }
        }
    }
}