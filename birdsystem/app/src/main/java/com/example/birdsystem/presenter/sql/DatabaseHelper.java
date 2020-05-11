package com.example.birdsystem.presenter.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, int version) {
        super(context,"sqlite_bird", null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sb.append("bird_info")
                .append('(')
                .append("birdname").append(" VARCHAR PRIMARY KEY,")
                .append("habitat").append(" INTEGER,")
                .append("lifestyle").append(" INTEGER,")
                .append("residence").append(" INTEGER,")
                .append("url").append(" VARCHAR,")
                .append("img").append(" VARCHAR,")
                .append("details").append(" VARCHAR")
                .append(')');
        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
