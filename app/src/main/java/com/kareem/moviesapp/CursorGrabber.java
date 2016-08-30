package com.kareem.moviesapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import static com.kareem.moviesapp.Database.TableName;

/**
 * Created by kareem on 8/30/2016.
 */

public class CursorGrabber extends AsyncTask<Void, Void, Cursor> {
    String command;
    CursorInterface listener;
    SQLiteDatabase database;
    int position;
    View v;
    ImageView img;
    public CursorGrabber(SQLiteDatabase database, int position, View v, ImageView img, String command, CursorInterface listener) {
    this.command = command;
        this.listener = listener;
        this.database = database;
        this.position = position;
        this.v=v;
        this.img = img;
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        return database.rawQuery(command, null);
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        listener.listener(cursor,position,v,img);
    }
}
