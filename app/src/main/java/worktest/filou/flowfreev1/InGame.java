package worktest.filou.flowfreev1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InGame extends AppCompatActivity {
    private static final String TAG = "InGame";

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
}
