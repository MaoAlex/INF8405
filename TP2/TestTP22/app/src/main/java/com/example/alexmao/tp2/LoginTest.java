package com.example.alexmao.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by alexMAO on 14/03/2016.
 */
public class LoginTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final Button loginButton = (Button) findViewById(R.id.connect);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginTest.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
