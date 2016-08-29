package com.kareem.moviesapp;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

import static com.kareem.moviesapp.Database.ID;
import static com.kareem.moviesapp.Database.TableName;

public class MainActivity extends AppCompatActivity {
    Button previous;
    Button next;
    TextView pagenum;
    int num = 1;
    Builder builder;
    HashMap<String, Object> map = null;
    JSONParser parser;
    GridView gridview;
    Intent intent;
    MyCustomizedAdapter custom;
    boolean change = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main, new MainFragment()).commit();
        }
    }
}
