package com.developtech.contactappwithsqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

    // public static final String VARIABLE_ID="_id";
    public static final String VARIABLE_NAME = "name";
    public static final String VARIABLE_CONTACTID = "_id";
    public static final String VARIABLE_MOBILE = "mobile";
    public static final String VARIABLE_EMAIL = "email";
    public static final String VARIABLE_DOB = "dob";
    public static final String VARIABLE_USERNAME = "username";
    public static final String VARIABLE_PASSWORD = "password";
    public static final String VARIABLE_BIRTHDAY = "bdayreminder";
    public static final String VARIABLE_IMAGE = "image";
    public static final String TABLE_NAME = "contactmaster";
    public static final String TABLE_NAME1 = "usermaster";
    private static final String DATABASE_NAME = "contact.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_CREATE = "create table " + TABLE_NAME + " (" + VARIABLE_CONTACTID + " INTEGER PRIMARY KEY AUTOINCREMENT," + VARIABLE_NAME + " text," + VARIABLE_MOBILE + " text," + VARIABLE_EMAIL + " text," + VARIABLE_DOB + " text," + VARIABLE_BIRTHDAY + " int," + VARIABLE_IMAGE + " text)";
    private static final String TABLE_CREATE1 = "create table " + TABLE_NAME1 + " (" + VARIABLE_USERNAME + " text unique," + VARIABLE_PASSWORD + " text unique)";
    private static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String TABLE_DROP1 = "DROP TABLE IF EXISTS " + TABLE_NAME1;
    private Context c;


    public MySqliteOpenHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);//create database..emp.db
        this.c = c;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Toast.makeText(c, "on cretae", Toast.LENGTH_SHORT).show();
        try {
            sqLiteDatabase.execSQL(TABLE_CREATE);
            sqLiteDatabase.execSQL(TABLE_CREATE1);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Toast.makeText(c, "on upgrade", Toast.LENGTH_SHORT).show();
        try {
            sqLiteDatabase.execSQL(TABLE_DROP);
            sqLiteDatabase.execSQL(TABLE_DROP1);
            Toast.makeText(c, "on ", Toast.LENGTH_SHORT).show();
            onCreate(sqLiteDatabase);
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}

