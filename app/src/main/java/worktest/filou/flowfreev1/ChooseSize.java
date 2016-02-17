package worktest.filou.flowfreev1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        intent.putExtra("Levels", levelsBySize.getLevels(btnToId.indexOf(id)));
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
                        .setTitle("Quit Flow free V1")
                        .setMessage("Do you really want to quit the game ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
