package com.example.alexmao.tp2final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by alexMAO on 14/03/2016.
 */
public class UserProfileActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        final Button loginButton = (Button) findViewById(R.id.modifier);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, UserProfileActivityModify.class);
                startActivity(intent);
            }

        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();

        //R.menu.menu est l'id de notre menu
        inflater.inflate(R.menu.menu_connecte, menu);

        return true;

    }

}