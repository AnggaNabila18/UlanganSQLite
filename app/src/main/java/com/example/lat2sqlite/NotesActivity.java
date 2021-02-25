package com.example.lat2sqlite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NotesActivity extends AppCompatActivity {

    static RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Item> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.listView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setupRecyclerView();

        if(listItem.isEmpty()){
            Snackbar.make(recyclerView, "", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void setupRecyclerView(){
        DatabaseHelper db = new DatabaseHelper(this);
        listItem = db.selectContentList();
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(listItem);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public static void setupRecyclerView(Context context, List<Item> itemList, RecyclerView recyclerView){
        DatabaseHelper db = new DatabaseHelper(context);
        itemList = db.selectContentList();

        RecyclerviewAdapter adapter = new RecyclerviewAdapter(itemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onClickAddItem(View view){
        Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
        startActivity(intent);
    }
}
