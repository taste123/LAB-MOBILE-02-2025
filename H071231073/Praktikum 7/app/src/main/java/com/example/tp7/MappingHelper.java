package com.example.tp7;

import android.database.Cursor;

import com.example.tp7.database.DatabaseContract;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Book> mapCursorToArrayList(Cursor bookCursor) {
        ArrayList<Book> books = new ArrayList<>();
        while (bookCursor.moveToNext()) {
            int id = bookCursor.getInt(bookCursor.getColumnIndexOrThrow(DatabaseContract.BookColumns._ID));
            String judul = bookCursor.getString(bookCursor.getColumnIndexOrThrow(DatabaseContract.BookColumns.JUDUL));
            String deskripsi = bookCursor.getString(bookCursor.getColumnIndexOrThrow(DatabaseContract.BookColumns.DESKRIPSI));
            String info = bookCursor.getString(bookCursor.getColumnIndexOrThrow(DatabaseContract.BookColumns.INFO));
            books.add(new Book(id, judul, deskripsi, info));
        }
        return books;
    }

}
