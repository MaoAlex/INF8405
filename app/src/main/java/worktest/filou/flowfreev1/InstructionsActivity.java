package worktest.filou.flowfreev1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On récupère notre ressource au format String
        String instructions = getResources().getString(R.string.instructions_text);
        // On le convertit en Spanned
        Spanned html_instructions = Html.fromHtml(instructions);

        setContentView(R.layout.activity_instructions);

        TextView view_instructions = (TextView) findViewById(R.id.instructions_view);
        view_instructions.setText(html_instructions);
        view_instructions.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();

        //R.menu.menu est l'id de notre menu
        inflater.inflate(R.menu.menu_instructions, menu);

        return true;

    }

    @Override

    public boolean onOptionsItemSelected (MenuItem item)
    {

        switch(item.getItemId())
        {

            case R.id.menu_instructions_quit:
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
            case R.id.menu_instructions_demarrer:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
