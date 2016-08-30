package com.kareem.moviesapp;

import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by kareem on 8/30/2016.
 */

public interface CursorInterface {
    void listener(Cursor cursor, int position, View v, ImageView img);
}
