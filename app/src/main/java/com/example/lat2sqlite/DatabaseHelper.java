package com.example.lat2sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ulangansql.db";
    private static final int DB_VERSION = 1;
    private static String TABLE_NAME = "content";
    static final String ID = "id";
    static final String JUDUL = "judul";
    static final String DESC = "deskripsi";
    static final String WAKTU = "waktu";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createContentTable = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY," + JUDUL + " TEXT NOT NULL," + DESC + " TEXT NOT NULL," + WAKTU + " TEXT NOT NULL" + ")";
        db.execSQL(createContentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insert(Item content){
        SQLiteDatabase db = getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues values = new ContentValues();
        values.put(JUDUL, content.getJudul());
        values.put(DESC, content.getDesc());
        values.put(WAKTU, dateFormat.format(date));
        db.insert(TABLE_NAME, null, values);
    }

    public List<Item> selectContentList(){
        ArrayList<Item> contentList = new ArrayList<Item>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = { ID, JUDUL, DESC, WAKTU };

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String judul = cursor.getString(1);
            String desc = cursor.getString(2);
            String waktu = cursor.getString(3);

            Item item = new Item();
            item.setId(id);
            item.setJudul(judul);
            item.setDesc(desc);
            item.setWaktu(waktu);

            contentList.add(item);
        }
        return contentList;
    }

    public void update(Item content){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        values.put(JUDUL, content.getJudul());
        values.put(DESC, content.getDesc());
        values.put(WAKTU, dateFormat.format(date));
        String whereClause = ID + " = ' " + content.getId() + "'";
        db.update(TABLE_NAME, values, whereClause, null);
    }

    public void delete(int ID){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID + "= '" + ID + "'";

        db.delete(TABLE_NAME, whereClause, null);
    }

}
