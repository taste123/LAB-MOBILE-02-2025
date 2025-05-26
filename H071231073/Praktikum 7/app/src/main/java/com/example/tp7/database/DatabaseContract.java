package com.example.tp7.database;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_NAME = "buku";

    public static final class   BookColumns implements BaseColumns {
        public static String JUDUL = "judul";
        public static String DESKRIPSI = "deskripsi";
        public static String INFO = "info";
    }
}
