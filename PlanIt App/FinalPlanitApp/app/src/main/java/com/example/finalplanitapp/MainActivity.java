package com.example.finalplanitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.finalplanitapp.dao.UserDAO;
import com.example.finalplanitapp.dao.Config;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button guestButton = (Button)findViewById(R.id.guestButton);
        Button registerButton = (Button)findViewById(R.id.registerButton);
        Button loginButton = (Button)findViewById(R.id.loginButton);

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistration();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });
    }

    public void openCalendar() {
        Intent intent = new Intent(this, DateActivity.class);
        startActivity(intent);
    }

    public void openRegistration() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        //Intent intent = new Intent(this, JourneyListActivity.class);
        startActivity(intent);
    }

    public void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
