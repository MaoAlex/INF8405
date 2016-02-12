package worktest.filou.flowfreev1;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private LevelsBySize levelsBySize = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        XmlResourceParser xml = getResources().getXml(R.xml.levels);
        XmlParser parser = new XmlParser();

        try {
            levelsBySize = parser.parse(xml);
        } catch (Exception e) {
            Log.d(TAG, "onCreate: " + "cannot parse " + e.getCause());
        }
        
        Button start_button = (Button) findViewById(R.id.start_app);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseSize.class);
                intent.putExtra("LevelsBySize", MainActivity.this.levelsBySize);
                MainActivity.this.startActivity(intent);
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
}
