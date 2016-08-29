package com.kareem.moviesapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by kareem on 7/31/2016.
 */

public class Builder extends URLBuilder{
    String sort_by;
    public Builder(String sort_by){
        //for building movies url(list of movies)
        super("discover","movie",sort_by+".desc","58891bb706282946dace8971f7fe1854");
        this.sort_by = sort_by;
    }
    public Builder(String id,String empty){
        //for building movie url(trials,reviews)
        super("movie",id,empty,"58891bb706282946dace8971f7fe1854");
    }
}
