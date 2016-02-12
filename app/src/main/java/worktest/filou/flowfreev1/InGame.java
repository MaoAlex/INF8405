package worktest.filou.flowfreev1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public class InGame extends AppCompatActivity {
    private static final String TAG = "InGame";

    public Level getLevel() {
        return level;
    }

    private Level level = null;
    private int levelId = 0;
    private FlowFreeSimpleGridView gameGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        level = getIntent().getParcelableExtra("Level");
        levelId = getIntent().getIntExtra("LevelId", -1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);



        gameGrid = (FlowFreeSimpleGridView)findViewById(R.id.game_grid);
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
