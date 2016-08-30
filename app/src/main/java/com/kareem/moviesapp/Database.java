package com.kareem.moviesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kareem on 8/17/2016.
 */

public class Database extends SQLiteOpenHelper {
    //database class responsible for storing data in table with columns(id,title,summary,releaseDate,rate,posterImg)

    private static final int DatabaseVersion = 1;

    //database file Name
    private static final String DatabaseName = "MyFavourites";

    //database table
    public static final String TableName = "favourites";

    //columns for the database table
    public static final String ID = "id";
    public static final String Title = "title";
    public static final String Summary = "summary";
    public static final String ReleaseDate = "release";
    public static final String Rate = "rate";
    public static final String Poster = "poster";

//command that is responsible for creating the table with appropriate columns
    static final String CreateTable = "CREATE TABLE IF NOT EXISTS " + TableName + " ("
            + ID + " TEXT NOT NULL , "
            + Title + " TEXT , "
            + Summary + " TEXT , "
            + ReleaseDate + " TEXT , "
            + Rate + " REAL , "
            + Poster + " BLOB );";


    public Database(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(sqLiteDatabase);
    }


}
