/*package com.example.alexmao.tp2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

public class PhotoActivity extends Activity {
    private File mFichier;
    //final String PHOTO_RESULT = "photo_result";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        // L'endroit où sera enregistrée la photo

// Remarquez que mFichier est un attribut de ma classe
        mFichier = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
// On récupère ensuite l'URI associée au fichier
        Uri fileUri = Uri.fromFile(mFichier);
// Maintenant, on crée l'intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// Et on déclare qu'on veut que l'image soit enregistrée là où pointe l'URI
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
// Enfin, on lance l'intent pour que l'application de photo se lance
        startActivityForResult(intent, PHOTO_RESULT);



    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Si on revient de l'activité qu'on avait lancée avec le code PHOTO_RESULT
        if (requestCode == PHOTO_RESULT && resultCode == RESULT_OK) {
            // Si l'image est une miniature
            if (data != null) {
                if (data.hasExtra("data"))
                    Bitmap thumbnail = data.getParcelableExtra("data");
            } else {
                // On sait ici que le fichier pointé par mFichier est accessible, on peut donc faire ce qu'on veut avec, par exemple en faire un Bitmap
                Bitmap image = BitmapFactory.decodeFile(mFichier);
            }
        }
    }

}
*/

package com.example.alexmao.tp2final;
/*
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoActivity extends Activity {


    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data)
            {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                //-- Resize Image to 64x64
                Bitmap ImageFromGal = BitmapFactory
                        .decodeFile(imgDecodableString);
                ImageFromGal = Bitmap.createScaledBitmap(
                        ImageFromGal, 64, 64, false);
                bFic = UtilityImg.getBytes(ImageFromGal);
                DbHelper.updateContact(dbContact, bFic);
                //-- Set Image in Image View
                imgView.setImageBitmap(ImageFromGal);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else
        {
            Toast.makeText(this, "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }
    } catch (Exception e) {
        Toast.makeText(this, "Can't load picture...", Toast.LENGTH_LONG)
                .show();
    }
}
}
*/

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class PhotoActivity extends ActionBarActivity {
    Button b1,b2;
    ImageView iv;
    private static int RESULT_LOAD_IMAGE = 1;
    File mFichier;
    Bitmap bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);

        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.gallery);
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
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        bp = (Bitmap) data.getExtras().get("data");
        iv.setImageBitmap(bp);
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}