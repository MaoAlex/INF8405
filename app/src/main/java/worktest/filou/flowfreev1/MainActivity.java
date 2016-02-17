package worktest.filou.flowfreev1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private LevelsBySize levelsBySize = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        XmlResourceParser xml = getResources().getXml(R.xml.levels);
        XmlParser parser = new XmlParser();

        try {
            levelsBySize = parser.parse(xml);
        } catch (Exception e) {
            Log.d(TAG, "onCreate: " + "cannot parse " + e.getCause());
        }
        
        Button start_button = (Button) findViewById(R.id.start_app);
        Button instructions_button = (Button) findViewById(R.id.instructions);
        Button quit_button = (Button) findViewById(R.id.quit_app);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseSize.class);
                intent.putExtra("LevelsBySize", MainActivity.this.levelsBySize);
                MainActivity.this.startActivityForResult(intent, 1);
            }
        });
        instructions_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        quit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle(v.getContext().getResources().getString(R.string.quit_title))
                        .setMessage(v.getContext().getResources().getString(R.string.quit_question))
                        .setPositiveButton(v.getContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                            }

                        })
                        .setNegativeButton(v.getContext().getResources().getString(R.string.no), null)
                        .show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "Retour dans onActivity ");
        //levelsBySize =  data.getParcelableExtra("LevelsBySize");
        Log.d(TAG, "Retour dans onActivity " + levelsBySize);
        levelsBySize =  data.getParcelableExtra("niveau");

        Log.d(TAG, "Retour dans onActivity " +levelsBySize);
          if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result

            }
        }
    }//onActivityResult
}
