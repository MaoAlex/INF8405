

package com.example.alexmao.tp2final;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.alexmao.tp2final.firebase.LocalUser;

import java.io.File;
//Classe permettant la prise de photo et le stockage de la photo
public class PhotoActivity extends Activity {
    ImageButton b1;
    Button b2;
    ImageView iv;
    private static int RESULT_LOAD_IMAGE = 1;
    File mFichier;
    Bitmap bp;
    private LocalUser mLocalUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        mLocalUser = getIntent().getParcelableExtra("localUser");

        b1=(ImageButton)findViewById(R.id.button);
        b2=(Button) findViewById(R.id.gallery);
        iv = (ImageView) findViewById(R.id.imageView);
        bp = null;
        //deux lignes suivantes à modifier
        //mFichier = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
        //final Uri fileUri = Uri.fromFile(mFichier);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //ligne suivante inutile pour le moment
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, 0);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //ligne suivante inutile pour le moment
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });
        final Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoActivity.this, MainActivity_Home.class);
                intent.putExtra("localUser", mLocalUser);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data!=null) {
            super.onActivityResult(requestCode, resultCode, data);
            if (data.getExtras() != null) {
                bp = (Bitmap) data.getExtras().get("data");
//                mLocalUser.setProfilPic(new Picture(bp));
                UsersBDD usersBDD = new UsersBDD(this);
                usersBDD.open();
                User uT = usersBDD.getProfil();
                if (bp != null) {
                    uT.setPhoto(data.getData());
                    iv.setImageBitmap(bp);
                } else {
                    Uri u = data.getData();
                    uT.setPhoto(data.getData());
                    iv.setImageURI(u);
                    //iv.setRotation(1);
                }
                usersBDD.updateUser(uT.getId(), uT);
                usersBDD.deconnexion();
                usersBDD.connexion(uT);

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("bp", bp);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        bp = savedInstanceState.getParcelable("bp");

        if(bp!=null) {
            iv.setImageBitmap(bp);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}