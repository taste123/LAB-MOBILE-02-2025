package com.example.tp7.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BookHelper {
    public static String TABLE_NAME = DatabaseContract.TABLE_NAME;
    public static DatabaseHelper databaseHelper;
    public static SQLiteDatabase sqLiteDatabase;
    public static volatile BookHelper INSTANCE;

    public BookHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static BookHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BookHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    public Cursor queryAll() {
        return sqLiteDatabase.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.BookColumns._ID + " ASC"
        );
    }

    public long insert(ContentValues values) {
        return sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    public int update(String id, ContentValues values) {
        return sqLiteDatabase.update(TABLE_NAME, values, DatabaseContract.BookColumns._ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return sqLiteDatabase.delete(TABLE_NAME, DatabaseContract.BookColumns._ID + " = ?", new String[]{id});
    }
}
