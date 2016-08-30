package com.kareem.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kareem.moviesapp.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main, new MainFragment()).commit();
        }
    }
}
