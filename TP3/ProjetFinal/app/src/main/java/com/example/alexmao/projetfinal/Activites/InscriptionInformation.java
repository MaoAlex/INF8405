package com.example.alexmao.projetfinal.Activites;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDExterne.UtilisateurProfilEBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.custom.CustomActivity;
import com.example.alexmao.projetfinal.utils.Utils;

import java.util.Date;
import java.util.GregorianCalendar;


/**
 * The Class Inscription is the Activity class that shows user registration screen
 * that allows user to activity_inscription itself on Parse server for this Chat app.
 */
public class InscriptionInformation extends CustomActivity
{
    private RemoteBD remoteBD;

    /** The username EditText. */
    private EditText nomVue;

    /** The password EditText. */
    private EditText prenomVue;

    /** The email EditText. */
    private EditText dateNaissanceVue;

    private ImageView photoProfilVue;
//    private Button test;
    private static int RESULT_LOAD_IMAGE = 1;

    private DatePicker datePicker;
    private int day;
    private int month;
    private int year;
    private Date date;
    Bitmap bp;
    /* (non-Javadoc)
     * @see com.chatt.custom.CustomActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_information);
        remoteBD = new FireBaseBD(this);
        setTouchNClick(R.id.btnReg);

        nomVue = (EditText) findViewById(R.id.nom);
        prenomVue = (EditText) findViewById(R.id.prenom);
        dateNaissanceVue = (EditText) findViewById(R.id.dateNaissance);
        photoProfilVue = (ImageView) findViewById(R.id.photoProfil);


        dateNaissanceVue.setInputType(InputType.TYPE_NULL);

        //test = (Button) findViewById(R.id.test);
        dateNaissanceVue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                affichageChoixDate();
            }
        });

//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                affichageChoixDate();
//            }
//        });

        photoProfilVue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //.Builder builder = new AlertDialog.Builder(this);
                choixPhotoProfil();
            }
        });
    }

    /* (non-Javadoc)
     * @see com.chatt.custom.CustomActivity#onClick(android.view.View)
     */
    @Override
    public void onClick(View v)
    {
        super.onClick(v);

        String nom = nomVue.getText().toString();
        String prenom = prenomVue.getText().toString();
        String dateAffiche = dateNaissanceVue.getText().toString();
        if (nom.length() == 0 || prenom.length() == 0 || dateAffiche.length() == 0)
        {
            Utils.showDialog(this, R.string.err_fields_empty);
            return;
        }
        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_wait));

        final UtilisateurProfilEBDD user = new UtilisateurProfilEBDD();
        Intent intent = getIntent();
        String email = intent.getStringExtra("mail");
        user.setMailAdr(email);
        user.setLastName(prenom);
        user.setFirstName(nom);
        user.setDateBirth(date.getTime());
        //Ajout de l'utilisateur dans la BDD externe
        final String idFirebase = remoteBD.addUserProfil(user);
        String mdp = intent.getStringExtra("mdp");
        remoteBD.addMdpToUser(email, mdp);
        //user.setDateBirth();
        //Ajout de l'utilisateur dans la BDD externe
//        final String idFirebase = remoteBD.addUserProfil(user);
//        remoteBD.addMdpToUser(e, p);

        startActivity(new Intent(InscriptionInformation.this, Accueil.class));
        setResult(RESULT_OK);
        finish();
        //Completer les différents elements de l'utilisateur
		/*pu.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(ParseException e)
			{
				dia.dismiss();
				if (e == null)
				{
					UserList.user = pu;
					startActivity(new Intent(Inscription.this, UserList.class));
					setResult(RESULT_OK);
					finish();
				}
				else
				{
					Utils.showDialog(
							Inscription.this,
							getString(R.string.err_singup) + " "
									+ e.getMessage());
					e.printStackTrace();
				}
			}
		});*/

    }

    private void affichageChoixDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener, year, month, day);
        datePickerDialog.updateDate(2016,04,21);
        datePickerDialog.show();

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            dateNaissanceVue.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
            GregorianCalendar test = new GregorianCalendar(year, month, day);
            date = test.getTime();
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data!=null) {
            super.onActivityResult(requestCode, resultCode, data);
            if (data.getExtras() != null) {
                bp = (Bitmap) data.getExtras().get("data");
//                UsersBDD usersBDD = new UsersBDD(this);
//                usersBDD.open();
//                User uT = usersBDD.getProfil();
                if (bp != null) {
//                    uT.setPhoto(data.getData());
//                    mLocalUser.setProfilPic(new Picture(bp));
//                    mRemoteBD.addPicToUser(mLocalUser.getDataBaseId(), mLocalUser.getProfilPic());
                    photoProfilVue.setImageBitmap(bp);
                } else {
                    Uri u = data.getData();
//                    uT.setPhoto(data.getData());
                    photoProfilVue.setImageURI(u);
                    //On envoie la photo au serveur

//                    mLocalUser.setProfilPic(new Picture(bp));
//                    RemoteBD(mLocalUser.getDataBaseId(), mLocalUser.getProfilPic());
                    //photoProfilVue.setRotation(1);
                }


            }
        }
    }

    private void choixPhotoProfil(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Choisissez une photo de profil :");
        builder.setCancelable(true);
        builder.setPositiveButton("Prendre une photo", new TakePictureOnClickListener());
        builder.setNegativeButton("Choisir une photo", new ChoosePictureOnClickListener());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            /*Toast.makeText(getApplicationContext(), "Bon courage",
                    Toast.LENGTH_LONG).show();*/

        }
    }
    private final class ChoosePictureOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            /*Toast.makeText(getApplicationContext(), "Bon courage",
                    Toast.LENGTH_LONG).show();*/
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //ligne suivante inutile pour le moment
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, RESULT_LOAD_IMAGE);
        }
    }
    private final class TakePictureOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            //InGame.this.finish();

//            //Intent intent = new Intent(InGame.this, InGame.class);
//            Intent returnIntent = new Intent();
//
//            setResult(Activity.RESULT_OK, returnIntent);
//            finish();

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            //ligne suivante inutile pour le moment
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, 0);

        }
    }
}
