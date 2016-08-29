package com.kareem.moviesapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by kareem on 8/27/2016.
 */

public class DetailsFragment extends Fragment {
    Results r;
    ImageView poster, backdrop;
    TextView title, summary, date, rate;
    Button reviews, trials, addToFavourite;
    SQLiteDatabase database;
    SQLiteDatabase databaseread;

    public DetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init_variables(view);
        super.onViewCreated(view, savedInstanceState);
    }

    public void init_variables(View v) {
        //to initialize required variables
        poster = (ImageView) v.findViewById(R.id.poster);
        backdrop = (ImageView) v.findViewById(R.id.backdrop);
        title = (TextView) v.findViewById(R.id.title);
        summary = (TextView) v.findViewById(R.id.summary);
        date = (TextView) v.findViewById(R.id.releasedata);
        rate = (TextView) v.findViewById(R.id.usersrate);
        reviews = (Button) v.findViewById(R.id.reviews);
        trials = (Button) v.findViewById(R.id.trials);
        addToFavourite = (Button) v.findViewById(R.id.addtofavourite);
        r = (Results) this.getArguments().getSerializable("Results");
        database = new Database(getContext()).getWritableDatabase();
        databaseread = new Database(getContext()).getReadableDatabase();
    }

    @Override
    public void onStart() {
        super.onStart();
        theChecker();
    }

    public void theChecker() {

        //check for the ID if its already exist or not in the database
        Cursor c = databaseread.rawQuery("SELECT * FROM " + TableName + " WHERE id=" + r.getId(), null);
        //represents the id_found condition
        boolean b;
        //if the preference is not 'favourite' or table is empty(=id not found) this if shall be entered
        if ((!(b = c.moveToFirst())) || (!PreferenceManager.getDefaultSharedPreferences(getContext()).getString("sorttype", "vote_average").equals("favourite"))) {
            //**reviews and trials are not included in the database**

            //if b then the movie is selected as favourite before and add_to_favourite button shall not be visible
            if (b)
                hideFavouriteBtn();

            init_listeners();

            loadDataFromInternet();
        } else {
            //means its loaded from database
            hideUnusedComponents();
            hideFavouriteBtn();
            loadDataFromDatabase(c);
        }
    }

    public void loadDataFromInternet() {
        //grab data to the UI
        if (r.getPoster_path() != null && !r.getPoster_path().equals("null") && !r.getPoster_path().isEmpty())
            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w342/" + r.getPoster_path()).into(poster);
        if (r.getBackdrop_path() != null && !r.getBackdrop_path().equals("null") && !r.getBackdrop_path().isEmpty())
            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/original/" + r.getBackdrop_path()).into(backdrop);
        if (r.getOriginal_title() != null && !r.getOriginal_title().equals("null") && !r.getOriginal_title().isEmpty())
            title.setText(r.getOriginal_title());
        if (r.getOverview() != null && !r.getOverview().equals("null") && !r.getOverview().isEmpty())
            summary.setText(r.getOverview());
        if (r.getRelease_date() != null && !r.getRelease_date().equals("null"))
            date.setText(r.getRelease_date().toString());
        rate.setText(r.getVote_average() + "");
    }

    public void hideUnusedComponents() {
        //set backdropImg,reviews,trials,addToFavourite to invisible as they will not be loaded with data
        //backdrop is not stored in database as its very big img
        backdrop.setVisibility(View.INVISIBLE);
        reviews.setVisibility(View.INVISIBLE);
        trials.setVisibility(View.INVISIBLE);
    }

    public void init_listeners() {
        //add listeners to reviews and trials
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Reviews.class);
                i.putExtra("Results", r);
                startActivity(i);
            }
        });

        trials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Trials.class);
                i.putExtra("Results", r);
                startActivity(i);
            }
        });
        addToFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavourite(view);
            }
        });
    }

    public void hideFavouriteBtn() {
        addToFavourite.setVisibility(View.INVISIBLE);
    }

    public void loadDataFromDatabase(Cursor c) {
        //load posterImg
        byte[] array = c.getBlob(c.getColumnIndex(Poster));
        if (array.length > 0)
            poster.setImageBitmap(new array_bitmap().convertArray(array));

        title.setText(c.getString(c.getColumnIndex(Title)));
        summary.setText(c.getString(c.getColumnIndex(Summary)));
        date.setText(c.getString(c.getColumnIndex(ReleaseDate)));
        rate.setText(c.getString(c.getColumnIndex(Rate)));
    }

    public void addToFavourite(View view) {
        addFavourite(r.getId(), r.getOriginal_title(), r.getOverview(),
                r.getRelease_date().toString(), r.getVote_average(),

                //convert posterImg to byte array to be stored in database
                new array_bitmap().convertBitMap(((BitmapDrawable) poster.getDrawable()).getBitmap()));

        //hide the (add to favourite) button
        addToFavourite.setVisibility(View.INVISIBLE);
    }

    public void addFavourite(String id, String title, String summary,
                             String releaseDate, double rate, byte[] image) throws SQLiteException {
        //pass all data needed to be stored in database
        ContentValues cv = new ContentValues();
        cv.put(ID, id);
        cv.put(Title, title);
        cv.put(Summary, summary);
        cv.put(ReleaseDate, releaseDate);
        cv.put(Rate, rate);
        cv.put(Poster, image);
        database.insert(TableName, null, cv);
    }
}
