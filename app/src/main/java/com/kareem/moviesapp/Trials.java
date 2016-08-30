package com.kareem.moviesapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

public class Trials extends AppCompatActivity implements AdapterView.OnItemClickListener, Listener {
    ListView trials;
    MovieUnit r;
    ProgressDialog dialog;
    Uri[] uris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trials);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        trials = (ListView) findViewById(R.id.trailers);
        r = (MovieUnit) this.getIntent().getSerializableExtra("MovieUnit");
        organizer(new Builder(r.getId(), "videos").trials());
    }

    public void listened(String s) {
        try {
            JSONParser json = new JSONParser();
            createAdapter(json.resultsDetailsCreator((JSONArray) json.organizeDetailedData(s).get("results")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context context() {
        return this.context();
    }

    public void createAdapter(String[] array) {
        uris = new Uri[array.length];
        for (int i = 0; i < array.length; i++) {
            uris[i] = Uri.parse("http://www.youtube.com/watch?v=" + array[i]);
        }
        String[] strings = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            strings[i] = "Trial " + (i + 1);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);
        trials.setAdapter(arrayAdapter);
        trials.setOnItemClickListener(this);
        dialog.dismiss();
    }

    public void organizer(String url) {

        new DataGrabber(url, Trials.this).execute();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uris[i]);
        startActivity(intent);
    }

}
