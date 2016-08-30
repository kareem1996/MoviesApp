package com.kareem.moviesapp;

import android.content.Context;

/**
 * Created by kareem on 8/21/2016.
 */

public interface Listener {
    //method trigered when asyntask finishes downloading
    public void listened(String s);
    //method to bring context from the activity to display toast on it later
    public Context context();
}
