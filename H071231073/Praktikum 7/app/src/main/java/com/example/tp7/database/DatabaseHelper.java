package com.example.tp7.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

public class DatabaseHelper extends SQLiteOpenHelper {
//    public final String DATABASE_NAME = "buku";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_BUKU =
            String.format(
                    "CREATE TABLE %s"
                            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + " %s TEXT NOT NULL,"
                            + " %s TEXT NOT NULL,"
                            + " %s TEXT NOT NULL)",
                    DatabaseContract.TABLE_NAME,
                    DatabaseContract.BookColumns._ID,
                    DatabaseContract.BookColumns.JUDUL,
                    DatabaseContract.BookColumns.DESKRIPSI,
                    DatabaseContract.BookColumns.INFO
            );
    public DatabaseHelper(Context context) {
        super(context, "buku", null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_BUKU);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(db);
    }

}
