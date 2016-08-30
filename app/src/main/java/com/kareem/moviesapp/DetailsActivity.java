package com.kareem.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kareem.moviesapp.fragments.DetailsFragment;


public class DetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatils);
        if(savedInstanceState==null){
            Bundle b = new Bundle();
            b.putSerializable("MovieUnit",this.getIntent().getSerializableExtra("MovieUnit"));
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().add(R.id.activity_details,detailsFragment).commit();
        }



    }
}
