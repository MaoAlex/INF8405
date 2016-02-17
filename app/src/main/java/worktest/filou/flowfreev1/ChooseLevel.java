package worktest.filou.flowfreev1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ChooseLevel extends AppCompatActivity {
    private static final int launchlevel = 1;
    private static final String TAG = "ChooseLevel";
    private ArrayList<Button> buttons = new ArrayList<>();
    private ArrayList<Integer> btnToId= new ArrayList<>();
    private int levelsId = 0;
    private Levels levels = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);

        levelsId = getIntent().getIntExtra("id", -1);
        levels = getIntent().getParcelableExtra("Levels");
        LinearLayout layout = (LinearLayout) findViewById(R.id.choose_level_activity_layout);

        for (Level level : levels.getList()) {
            Button button = new Button(this);
            int id = View.generateViewId();
            button.setId(id);
            btnToId.add(id);
            buttons.add(button);
            layout.addView(button);
            button.setText(getResources().getString(R.string.Level) + " " + level.getId());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoInGame(v.getId());
                }
            });
        }

    }

    private void gotoInGame(int id) {
        int indexInLevels = btnToId.indexOf(id);
        if( !levels.getLevel(indexInLevels).isUnlocked())
            return;
        Intent intent = new Intent(ChooseLevel.this, InGame.class);
        intent.putExtra("LevelId", indexInLevels);
        intent.putExtra("Level", levels.getLevel(indexInLevels));
        ChooseLevel.this.startActivityForResult(intent,1);
    }

    private void reGoInGame(int id) {
        Intent intent = new Intent(ChooseLevel.this, InGame.class);

        Log.d(TAG, "valeur levelId transmis au choose level pour la fonction rechoose! : " + id);
        intent.putExtra("LevelId", id);
        intent.putExtra("Level", ChooseLevel.this.levels.getLevel(id));//faire attention a l'id qui est parfois celui dans le sens android
        ChooseLevel.this.startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "On rentre dans onActivityREsultat ");

        Level level = data.getParcelableExtra("Level");
        int levelId = data.getIntExtra("nouvelId", -1);
       // if(btnToId.isEmpty())
         //   Log.d(TAG, "La liste est vide valeur levelId transmis au choose level pour la fonction onActivity! : " + levelId);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Log.d(TAG, "le niveau " + levelId + "est verouille ");
                levels.getLevel(levelId).unlocked();
                if(levels.getLevel(levelId).isUnlocked())
                    Log.d(TAG, "le niveau " + levelId + "est deverouille ");
                reGoInGame(levelId);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
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
}
