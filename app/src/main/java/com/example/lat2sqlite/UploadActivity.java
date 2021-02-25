package com.example.lat2sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

public class UploadActivity extends AppCompatActivity {
    TextInputLayout inputJudul, inputDesc;
    Button submit;
    Item item;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inputJudul = findViewById(R.id.txtinput);
        inputDesc = findViewById(R.id.txtinput2);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(this);
            item = new Item();
            String title = inputJudul.getEditText().getText().toString();
            String Desc = inputDesc.getEditText().getText().toString();
            item.setJudul(title);
            item.setDesc(Desc);

            db.insert(item);

            Intent intent = new Intent(getApplicationContext(), NotesActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        });
    }
}
