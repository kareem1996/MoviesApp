package com.kareem.moviesapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kareem on 7/25/2016.
 */

public class DataGrabber extends AsyncTask<Void, Void, String> {
    String link = "";
    //reference to classes that could possibly call the dataGrabbing function
    Listener Listener;

    public DataGrabber(String link,Listener listener) {
        this.link = link;
        this.Listener = listener;
    }

    public String grabData() throws IOException {
        //grabs the data in the specified link from the internet
        URL url = new URL(link);
        HttpURLConnection connector = (HttpURLConnection) url.openConnection();
        connector.setRequestMethod("GET");
        connector.connect();
        InputStream is = connector.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String s;
        if (is == null) {
            // Nothing to do.
            return null;
        }
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        if (sb.length() == 0) {
            // Stream was empty.  No point in parsing.
            return null;
        }
        s = sb.toString();
        if (connector != null) {
            connector.disconnect();
        }
        if (br != null) {
            br.close();
        }
        return s;
    }

    @Override
    protected String doInBackground(Void... voids){
        try {
            return grabData();
        } catch (IOException io) {
            Log.e("DataGrabbertera", io.getMessage());
            //display toast to the appropriate activity
            return null;
//            Toast.makeText(Listener.context(),"Error",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //for triggering the correct Listener in the correct class,applying open,closed principles
        Listener.listened(s);
    }
}
