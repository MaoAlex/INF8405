package worktest.filou.flowfreev1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InGame extends AppCompatActivity {
    private static final String TAG = "InGame";
    private final static int DIALOG_ALERT=100; //id pour finir afficher la victoire
    public Level getLevel() {
        return level;
    }

    private Level level = null;
    private int levelId = 0;
    private FlowFreeSimpleGridView gameGrid;
    private FlowFreeSimpleGridView.OnTubEndedListener onTubEndedListener;
    private TextView textView;
    private Button buttonRestart;
    private Button buttonUndo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        level = getIntent().getParcelableExtra("Level");
        levelId = getIntent().getIntExtra("LevelId", -1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        textView = (TextView) findViewById(R.id.show_score_value);
        onTubEndedListener = new FlowFreeSimpleGridView.OnTubEndedListener() {
            @Override
            public void onTubEnded(View view, int nbTubs) {
                textView.setText(Integer.toString(nbTubs));
            }
        };
        gameGrid = (FlowFreeSimpleGridView)findViewById(R.id.game_grid);
        gameGrid.setOnTubEndedListener(onTubEndedListener);
        //Affichage de la reussite du niveau
        gameGrid.setVictoryListener(new Victory.VictoryListener() {
            @Override
            public void showVictory() {
                Log.d(TAG, "Show Victory: Il a bien gagne");
                showDialog(DIALOG_ALERT);
            }
        });
        buttonRestart = (Button) findViewById(R.id.eraser_move);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameGrid.restart();
            }
        });

        buttonUndo = (Button) findViewById(R.id.undo_move);
        buttonUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameGrid.undo();
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
/*
    private void VictoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
        builder.setMessage("you Won");
        builder.setPositiveButton("Call Now",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        builder.setNeutralButton("Setup",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        builder.setNegativeButton("Exit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
    }
*/
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                // Create out AlterDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("This will end the activity");
                builder.setCancelable(true);
                builder.setPositiveButton("Passez au niveau suivant", new OkOnClickListener());
                builder.setNeutralButton("Recommencer la partie", new NeutralOnClickListener());
                builder.setNegativeButton("Retour au choix de niveaux", new CancelOnClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        return super.onCreateDialog(id);
    }


    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            /*Toast.makeText(getApplicationContext(), "Bon courage",
                    Toast.LENGTH_LONG).show();*/

        }
    }
    private final class NeutralOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            /*Toast.makeText(getApplicationContext(), "Bon courage",
                    Toast.LENGTH_LONG).show();*/
            gameGrid.restart();
        }
    }
    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            int niveauCourant = level.getId();
            if(niveauCourant<3){
                Log.d(TAG, "valeur level : " + level.getId());
                level.setId(level.getId() + 1);
                Log.d(TAG, "valeur level : " + level.getId());
                Log.d(TAG, "valeur levelId : " + levelId);
                levelId++;
                Log.d(TAG, "valeur levelId : " + levelId);
                Log.d(TAG, "Niveau modifie");
                 }else{
                    //A completer
            }
            Intent returnIntent = new Intent();
            returnIntent.putExtra("nouvelId", levelId);
            returnIntent.putExtra("Level", level);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}
