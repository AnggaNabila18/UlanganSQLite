package com.example.lat2sqlite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

public class EditActivity extends AppCompatActivity {
    Button Edit;
    Item item;
    Bundle intentData;
    TextInputLayout edtJudul, edtDesc;
    private String judul;
    private String desc;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtJudul = findViewById(R.id.txteditinput);
        edtDesc = findViewById(R.id.txteditinput2);

        intentData = getIntent().getExtras();
        edtJudul.getEditText().setText(intentData.getString("judul"));
        edtDesc.getEditText().setText(intentData.getString("deskripsi"));

        Edit = findViewById(R.id.update);
        Edit.setOnClickListener(v -> {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            item = new Item();
            judul = edtJudul.getEditText().getText().toString();
            desc = edtDesc.getEditText().getText().toString();

            if(judul.isEmpty() && desc.isEmpty()){
                Toast.makeText(this, "", Toast.LENGTH_LONG).show();
            }else {
                item.setId(intentData.getInt("id"));
                item.setJudul(judul);
                item.setDesc(desc);

                dbHelper.update(item);

                Intent intent = new Intent(getApplicationContext(), NotesActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NotesActivity home = new NotesActivity();
                home.setupRecyclerView();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        int ID = item.getItemId();

        if(ID == R.id.delete){
            dbHelper.delete(intentData.getInt("id"));

            Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
            startActivity(intent);

            Toast.makeText(this, "", Toast.LENGTH_LONG).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
