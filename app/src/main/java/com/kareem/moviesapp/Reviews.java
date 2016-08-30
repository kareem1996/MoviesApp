package com.kareem.moviesapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

public class Reviews extends AppCompatActivity implements Listener {

    ListView reviews;
    ProgressDialog dialog;
    MovieUnit r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        initVariables();
        organizer(new Builder(r.getId(), "reviews").trials());
    }
public void initVariables(){
    //initialize required variables
    reviews = (ListView) findViewById(R.id.reviews);
    dialog = new ProgressDialog(this);
    dialog.setMessage("Loading...");
    r = (MovieUnit) this.getIntent().getSerializableExtra("MovieUnit");
}
    public void listened(String s) {
        //trigered whenever asyncTask finish its download
        try {

            JSONParser json = new JSONParser();
            createAdapter(json.resultsReviewsCreator((JSONArray) json.organizeDetailedData(s).get("results")));
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
