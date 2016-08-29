package com.kareem.moviesapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

public class Reviews extends AppCompatActivity implements listener {

    ListView reviews;
    ProgressDialog dialog;
    Results r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        init_variables();
        organizer(new Builder(r.getId(), "reviews").trials());
    }
public void init_variables(){
    //initialize required variables
    reviews = (ListView) findViewById(R.id.reviews);
    dialog = new ProgressDialog(this);
    dialog.setMessage("Loading...");
    r = (Results) this.getIntent().getSerializableExtra("Results");
}
    public void listened(String s) {
        //trigered whenever asyncTask finish its download
        try {

            JSONParser json = new JSONParser();
            createAdapter(json.resultsreviewsCreator((JSONArray) json.organizeDetailedData(s).get("results")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context context() {
        return this.context();
    }

    public void createAdapter(String[] array) {
        //initialize adapter with simple listView type
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        reviews.setAdapter(arrayAdapter);
        dialog.dismiss();
    }

    public void organizer(String url) {
//call asyncTask
        new DataGrabber(url,Reviews.this).execute();
    }
}
