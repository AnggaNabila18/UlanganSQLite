package com.example.lat2sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private int waktu_load =4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home=new Intent(MainActivity.this, NotesActivity.class);
                startActivity(home);
                finish();
            }
        },waktu_load);

    }
}