package com.kareem.moviesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by kareem on 7/31/2016.
 */

public class settings extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    public void onCreate(Bundle b){
        super.onCreate(b);
        addPreferencesFromResource(R.xml.preferneces);
        addlistenertopreference(findPreference("sorttype"));
    }
    public void addlistenertopreference(Preference preference){
        //set listener to each preference
        preference.setOnPreferenceChangeListener(this);
        //call it for once to set the sumary
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(),""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if(preference instanceof ListPreference){
            ListPreference lp = (ListPreference)preference;
            lp.setSummary("Sorted by "+o.toString()+" movies");
            Intent i = new Intent();
            i.putExtra("changed",true);
            setResult(1,i);
        }
        return true;
    }
}
