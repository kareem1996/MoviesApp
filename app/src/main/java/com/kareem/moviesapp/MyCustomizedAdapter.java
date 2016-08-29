package com.kareem.moviesapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import static com.kareem.moviesapp.Database.Poster;
import static com.kareem.moviesapp.Database.TableName;

/**
 * Created by kareem on 7/31/2016.
 */

public class MyCustomizedAdapter extends ArrayAdapter<Results> {
    Context myContext;
    LayoutInflater myInflater;
    Results[] arrayOfresults;


    public MyCustomizedAdapter(Context context, int resource, Results[] results) {
        super(context, resource, results);
        myContext = context;
        myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arrayOfresults = results;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView myImageView;
        View myView;
        if (convertView == null) {
            //if its a new view
            myView = myInflater.inflate(R.layout.list_of_movies, null);
        } else {
            //if its a reused view
            myView = convertView;
        }
        myImageView = (ImageView) myView.findViewById(R.id.imageview_list_of_movies_movie);
        SQLiteDatabase database = new Database(myContext).getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM " + TableName + " WHERE id=" + arrayOfresults[position].getId(), null);

        if ((!c.moveToFirst()) || (!PreferenceManager.getDefaultSharedPreferences(myContext).getString("sorttype", "vote_average").equals("favourite"))) {
            //if id doesn`t exist or sort type isn't favourite
            //load img from the internet
            String string = arrayOfresults[position].getPoster_path();

            if ((!string.equals("null")) && (string.length() != 0) && (string != null)) {
                Picasso.with(myContext).load("http://image.tmdb.org/t/p/w342/" + string).into(myImageView);
            } else {
                //set default img if there is no img associated with that movie
                myImageView.setImageBitmap(BitmapFactory.decodeResource(myView.getResources(), R.mipmap.video));
            }
        } else {
            //load poster images from the stored movies
            int i = c.getColumnIndex(Poster);
            byte[] array = c.getBlob(i);
            if (array.length > 0) {
                Bitmap b = new array_bitmap().convertArray(array);
                myImageView.setImageBitmap(b);
            }
        }
        return myView;
    }
}
