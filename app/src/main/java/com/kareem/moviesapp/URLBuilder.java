package com.kareem.moviesapp;


import android.net.Uri;

/**
 * Created by kareem on 7/31/2016.
 */

public class URLBuilder {
    String type ,show, sort_by,api_key;
    int page;
    public URLBuilder(String type,String show,String sort_by,String api_key){
        this.type = type;
        this.show = show;
        this.sort_by = sort_by;
        this.api_key = api_key;
        page = 1;

    }
    public String uriBuilder(){
        //create url for the main url
        Uri.Builder builder = new Uri.Builder();
        return builder.scheme("http").authority("api.themoviedb.org").appendPath("3").appendPath(type).appendPath(show).appendQueryParameter("sort_by",sort_by).appendQueryParameter("page",page+"").appendQueryParameter("api_key",api_key).toString();

    }

    public String trials(){
        //create url for trials and reviews
        Uri.Builder builder = new Uri.Builder();
        return builder.scheme("http").authority("api.themoviedb.org").appendPath("3").appendPath(type).appendPath(show).appendPath(sort_by).appendQueryParameter("api_key",api_key).toString();

    }

public String nextPage(){
    //to create url for the next page
    page++;
    Uri.Builder builder = new Uri.Builder();
    builder.scheme("http").authority("api.themoviedb.org").appendPath("3").appendPath(type).appendPath(show).appendQueryParameter("sort_by",sort_by).appendQueryParameter("page",page+"").appendQueryParameter("api_key",api_key);
    return builder.toString();
}
    public String previousPage(){
        //to create url for the previous page
        page--;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http").authority("api.themoviedb.org").appendPath("3").appendPath(type).appendPath(show).appendQueryParameter("sort_by",sort_by).appendQueryParameter("page",page+"").appendQueryParameter("api_key",api_key);
        return builder.toString();
    }

}
