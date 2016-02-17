package worktest.filou.flowfreev1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        ChooseLevel.this.startActivity(intent);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
