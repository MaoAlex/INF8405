package worktest.filou.flowfreev1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
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
}
