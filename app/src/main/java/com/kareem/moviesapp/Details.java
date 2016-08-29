package com.kareem.moviesapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.kareem.moviesapp.Database.ID;
import static com.kareem.moviesapp.Database.Poster;
import static com.kareem.moviesapp.Database.Rate;
import static com.kareem.moviesapp.Database.ReleaseDate;
import static com.kareem.moviesapp.Database.Summary;
import static com.kareem.moviesapp.Database.TableName;
import static com.kareem.moviesapp.Database.Title;


public class Details extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatils);
        if(savedInstanceState==null){
            Bundle b = new Bundle();
            b.putSerializable("Results",this.getIntent().getSerializableExtra("Results"));
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().add(R.id.activity_details,detailsFragment).commit();
        }



    }
}
