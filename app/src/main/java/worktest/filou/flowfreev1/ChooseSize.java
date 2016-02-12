package worktest.filou.flowfreev1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

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

        for (Levels levels : levelsBySize.getList()) {
            Button button = new Button(this);
            int id = View.generateViewId();
            button.setId(id);
            btnToId.add(id);
            buttons.add(button);
            layout.addView(button);
            //We can take the first element because each list should never be empty
            button.setText(levels.getLevel(0).getWidth() + "x" + levels.getLevel(0).getHeight());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoChooseLevels(v.getId());
                }
            });

        }
    }

    private void gotoChooseLevels(int id) {
        Intent intent = new Intent(ChooseSize.this, ChooseLevel.class);
        intent.putExtra("id", btnToId.indexOf(id));
        intent.putExtra("Levels", ChooseSize.this.levelsBySize.getLevels(btnToId.indexOf(id)));
        ChooseSize.this.startActivity(intent);
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
