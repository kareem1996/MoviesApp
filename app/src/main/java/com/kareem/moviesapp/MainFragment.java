package com.kareem.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import static com.kareem.moviesapp.Database.ID;
import static com.kareem.moviesapp.Database.TableName;

/**
 * Created by kareem on 8/27/2016.
 */

public class MainFragment extends Fragment implements listener, View.OnClickListener {
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

    public MainFragment() {
setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivityForResult(intent,1);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            change =  data.getExtras().getBoolean("changed");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        init_variables(view);
        callListeners();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    public void init_variables(View v) {
        //initialize essential variables
        previous = (Button) v.findViewById(R.id.previouspage);
        previous.setVisibility(View.INVISIBLE);
        next = (Button) v.findViewById(R.id.nextpage);
        pagenum = (TextView) v.findViewById(R.id.pagenum);
        pagenum.setText(num + "");
        parser = new JSONParser();
        gridview = (GridView) v.findViewById(R.id.gridview_content_main2_list_of_movies);
        intent = new Intent(getContext(), settings.class);
    }

    public void callListeners() {
        //add listeners to buttons
        previous.setOnClickListener(this);
        next.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        triger();
    }

    public void triger() {
        String s = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("sorttype", "favourite");
        if (!s.equals("favourite")) {
            //grab the data from the internet
            builder = new Builder(s);
            String url = builder.uriBuilder();
            organizer(url);
        } else {
            //load favourite movies from database
            Cursor c = new Database(getContext()).getReadableDatabase().rawQuery("SELECT id FROM " + TableName, null);
            c.moveToFirst();

            if (c != null && c.getCount() > 0) {
                ArrayList<Results> arraylist = new ArrayList<>();
                int i = c.getColumnIndex(ID);
                Results r;
                do {
                    r = new Results(c.getString(i));
                    arraylist.add(r);
                } while (c.moveToNext());
                finalizeView(arraylist.toArray(new Results[arraylist.size()]));
            }
        }
    }

    public void organizer(String url) {
//trigger asynctask
        ConnectivityManager manager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null&&info.isAvailable()&&info.isConnected()) {
            new DataGrabber(url, MainFragment.this).execute();
        }else{
            force_preferences();
        }
    }

    public void listened(String data) {
//      called whenever the download is completed
        if(data!=null) {
            if (change) {
                pagenum.setText(1 + "");

            }
            for (int i = 0; i < 5; i++) {
                try {
                    map = parser.organizeData(data);
                    break;
                } catch (Exception json) {
                    Log.e("MainActivity", "JSONException Occured while trying to Parse Data  : " + json.getMessage());
                }
            }
            JSONArray array = (JSONArray) map.get("results");
            finalizeView(parser.resultsCreator(array));
        }else{
            force_preferences();
        }
    }

    public void force_preferences(){
        //if there is no connection at any point the sort type should be changed to favourite movies
        SharedPreferences manager = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = manager.edit();
        editor.putString("sorttype", "favourite");
        editor.commit();
        triger();
    }

    @Override
    public Context context() {
        return this.context();
    }

    public void finalizeView(Results[] results) {
        //view the results on the gridview and put listener to items
        custom = new MyCustomizedAdapter(getContext(), R.layout.list_of_movies, results);
        gridview.setAdapter(custom);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Results r = custom.getItem(position);
//                Intent i = new Intent(getContext(),Details.class);
//                i.putExtra("Results", r);
//                startActivity(i);
                Log.v("brrrrrrrrrrrr", "i am here yaaaaay");

                if (getResources().getBoolean(R.bool.Tablet)) {
                    Bundle b = new Bundle();
                    b.putSerializable("Results", r);
                    DetailsFragment detailsFragment = new DetailsFragment();
                    detailsFragment.setArguments(b);
                    MainFragment.this.getFragmentManager().beginTransaction().add(R.id.activity_details, detailsFragment).commit();
                }else{
                    Intent i = new Intent(getContext(),Details.class);
                    i.putExtra("Results", r);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextpage:
                //for handling next page call
                String url = builder.nextPage();
                organizer(url);
                increasenum();
                pagenum.setText(num + "");
                previous.setVisibility(View.VISIBLE);
                break;
            case R.id.previouspage:
                //for handling previous page call
                String url1 = builder.previousPage();
                organizer(url1);
                decreasenum();
                pagenum.setText(num + "");
                if (num == 1)
                    previous.setVisibility(View.INVISIBLE);
                break;
        }
    }
    public void increasenum() {
        num++;
    }

    public void decreasenum() {
        num--;
    }
}
