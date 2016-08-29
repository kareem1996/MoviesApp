package com.kareem.moviesapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;

/**
 * Created by kareem on 7/31/2016.
 */

public class JSONParser {
    JSONObject j1;
    JSONArray results;

    public HashMap<String, Object> organizeData(String data) throws JSONException {
        //return data about the page number and an array of included movies + initialize data for the rest of methods
        HashMap<String, Object> map = new HashMap<>();
        j1 = new JSONObject(data);
        map.put("page", j1.getInt("page"));
        map.put("total_results", j1.getInt("total_results"));
        map.put("total_pages", j1.getInt("total_pages"));
        results = j1.getJSONArray("results");
        map.put("numOfResults", results.length());
        map.put("results", results);
        return map;
    }

    public HashMap<String, Object> organizeDetailedData(String data) throws JSONException {
        //return node results array from downloaded string
        HashMap<String, Object> map = new HashMap<>();
        j1 = new JSONObject(data);
        results = j1.getJSONArray("results");
        map.put("results", results);
        return map;
    }

    public Results resultCreator(JSONArray dataArray, int position) throws JSONException, ParseException {
        //create object results from an array with specific position
        JSONObject oneResult = new JSONObject(dataArray.get(position).toString());
        String date = null;
        String t = oneResult.getString("release_date");
        if (!t.isEmpty() && t.length() != 0)
            date = oneResult.getString("release_date");
        JSONArray temp = oneResult.getJSONArray("genre_ids");
        if (temp.length() != 0) {
            String[] arrayOfStrings = temp.join(",").split(",");
            int len = arrayOfStrings.length;
            int[] arrayOfInts = new int[len];
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    try {
                        arrayOfInts[i] = Integer.parseInt(arrayOfStrings[i]);
                    } catch (NumberFormatException nfe) {
                        Log.e("JSONParser", "NumberFormatException occuered while converting genre ids to integers : " + nfe.getMessage());
                    }
                }
            }
            Results result = new Results(oneResult.getString("poster_path"), oneResult.getBoolean("adult"), oneResult.getString("overview"), date, arrayOfInts, oneResult.getString("id"), oneResult.getString("original_title"), oneResult.getString("original_language"), oneResult.getString("title"), oneResult.getString("backdrop_path"), oneResult.getDouble("popularity"), oneResult.getInt("vote_count"), oneResult.getBoolean("video"), oneResult.getDouble("vote_average"));
            return result;
        } else {
            Results result = new Results(oneResult.getString("poster_path"), oneResult.getBoolean("adult"), oneResult.getString("overview"), date, null, oneResult.getString("id"), oneResult.getString("original_title"), oneResult.getString("original_language"), oneResult.getString("title"), oneResult.getString("backdrop_path"), oneResult.getDouble("popularity"), oneResult.getInt("vote_count"), oneResult.getBoolean("video"), oneResult.getDouble("vote_average"));
            return result;
        }
    }

    public String resultDetailsCreator(JSONArray dataArray, int position) throws JSONException, ParseException {
        //create a string from an array at a specific position
        JSONObject oneResult = new JSONObject(dataArray.get(position).toString());
        return oneResult.getString("key");
    }

    public Results[] resultsCreator(JSONArray dataArray) {
        //create an array of results
        Results[] returned = new Results[dataArray.length()];
        try {
            for (int i = 0; i < dataArray.length(); i++) {
                returned[i] = resultCreator(dataArray, i);
            }
        } catch (JSONException e) {
            Log.e("JSONParser", "JSONException Occured while trying to Parse Data  : " + e.getMessage());
        } catch (ParseException e) {
            Log.e("JSONParser", "ParseException Occured while trying to Parse Data  : " + e.getMessage());
        }

        return returned;
    }

    public String[] resultsDetailsCreator(JSONArray dataArray) {
        //return list of strings for the available trials
        String[] returned = new String[dataArray.length()];
        try {
            for (int i = 0; i < dataArray.length(); i++) {
                returned[i] = resultDetailsCreator(dataArray, i);
            }
        } catch (JSONException e) {
            Log.e("JSONParser", "JSONException Occurred while trying to Parse Data  : " + e.getMessage());
        } catch (ParseException e) {
            Log.e("JSONParser", "ParseException Occurred while trying to Parse Data  : " + e.getMessage());
        }

        return returned;
    }

    public String resultreviewsCreator(JSONArray dataArray, int position) throws JSONException, ParseException {
        //create a review for a specific postion from an array
        JSONObject oneResult = new JSONObject(dataArray.get(position).toString());
        return oneResult.getString("content");
    }

    public String[] resultsreviewsCreator(JSONArray dataArray) {
        //create an array of reviews for a specific movie
        String[] returned = new String[dataArray.length()];
        try {
            for (int i = 0; i < dataArray.length(); i++) {
                returned[i] = resultreviewsCreator(dataArray, i);
            }
        } catch (JSONException e) {
            Log.e("JSONParser", "JSONException Occured while trying to Parse Data  : " + e.getMessage());
        } catch (ParseException e) {
            Log.e("JSONParser", "ParseException Occured while trying to Parse Data  : " + e.getMessage());
        }

        return returned;
    }

}
