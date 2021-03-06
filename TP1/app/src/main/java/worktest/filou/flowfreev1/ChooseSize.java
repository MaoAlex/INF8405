package worktest.filou.flowfreev1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ChooseSize extends AppCompatActivity {
    private static final String TAG = "ChooseSize";
    private ArrayList<Button> buttons = new ArrayList<>();
    private ArrayList<Integer> btnToId= new ArrayList<>();
    private LevelsBySize levelsBySize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_size);
        levelsBySize =  getIntent().getParcelableExtra("LevelsBySize");
        LinearLayout layout = (LinearLayout) findViewById(R.id.choose_size_activity_layout);
        //Création des boutons pour le choix de la taille
        for (Levels levels : levelsBySize.getList()) {
            Button button = new Button(this);
            int id = View.generateViewId();
            button.setId(id);
            btnToId.add(id);
            buttons.add(button);
            layout.addView(button);
            button.setText(levels.getLevel(0).getWidth() + "x" + levels.getLevel(0).getHeight());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoChooseLevels(v.getId());
                }
            });

        }
    }
    //Fonction de création de la nouvelle activité après le choix de la taille
    private void gotoChooseLevels(int id) {
        Intent intent = new Intent(ChooseSize.this, ChooseLevel.class);
        intent.putExtra("id", btnToId.indexOf(id));
        Log.d(TAG, "La liste est vide valeur levelId transmis au choose level pour la fonction onActivity! : " + btnToId.indexOf(id));
        intent.putExtra("Levels", levelsBySize.getLevels(btnToId.indexOf(id)));
        ChooseSize.this.startActivityForResult(intent,1);
    }
    //Fonction de choix du niveau après un retour de l'activité Choose LEvel
    private void reChooseLevels(int id) {
        Intent intent = new Intent(ChooseSize.this, ChooseLevel.class);
        intent.putExtra("id", 4);
        intent.putExtra("Levels", levelsBySize.getLevels(id));
        ChooseSize.this.startActivityForResult(intent,-1);
    }
    @Override
    //Fonction permettant la récupération des informations de l'activité fille
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Levels levels = data.getParcelableExtra("newLevels");
        int levelId = data.getIntExtra("nouvelId", -1);
        LevelsBySize levelsBySizetemp = new LevelsBySize();
        if(levelId==0){
            levelsBySizetemp.addLevels(levels);
            levelsBySizetemp.addLevels(levelsBySize.getLevels((levelId+1)%2));
        }else
        {
            levelsBySizetemp.addLevels(levelsBySize.getLevels((levelId+1)%2));
            levelsBySizetemp.addLevels(levels);
        }
        levelsBySize = levelsBySizetemp;
          Log.d(TAG, "La liste est vide valeur levelId transmis au choose level pour la fonction onActivity! : " + levelId);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                if(levelId<3) {
                    Log.d(TAG, "le niveau " + levelId + "est verouille ");
                    reChooseLevels(levelId);
                }
                else{

                }
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }//onActivityResult

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();

        //R.menu.menu est l'id de notre menu
        inflater.inflate(R.menu.menu, menu);

        return true;

    }

    @Override

    public boolean onOptionsItemSelected (MenuItem item)
    {

        switch(item.getItemId())
        {

            case R.id.menu_quit:
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.quit_title))
                        .setMessage(getResources().getString(R.string.quit_question))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                            }

                        })
                        .setNegativeButton(getResources().getString(R.string.no), null)
                        .show();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("niveau", levelsBySize);
        Log.d(TAG, "Retour dans MainActivity depuis ChooseSize " + levelsBySize);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
