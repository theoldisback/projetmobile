package com.example.projetmobile.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AppDatabase.db";
    private static final int DATABASE_VERSION = 4;

    // User Table
    public static final String TABLE_USER = "User";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_FIRSTNAME = "firstname";
    public static final String COLUMN_USER_LASTNAME = "lastname";
    public static final String COLUMN_USER_ADDRESS = "adress";
    public static final String COLUMN_USER_BIRTHDATE = "birthdate";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_ROLE = "role";

    public static final String COLUMN_USER_IMAGE = "image";  // New column for image

    // SQL statement to create the User table
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " ("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_USERNAME + " TEXT NOT NULL, "
            + COLUMN_USER_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_USER_FIRSTNAME + " TEXT, "
            + COLUMN_USER_LASTNAME + " TEXT, "
            + COLUMN_USER_ADDRESS + " TEXT, "
            + COLUMN_USER_BIRTHDATE + " TEXT, "
            + COLUMN_USER_EMAIL + " TEXT UNIQUE, "
            + COLUMN_USER_ROLE + " TEXT DEFAULT 'User', "  // Set default value for 'role' column

            + COLUMN_USER_IMAGE + " BLOB)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the User table
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
