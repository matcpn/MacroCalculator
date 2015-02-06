package com.example.macrocalculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

/**
 * Created by Brendan on 2/6/15.
 */
public class SearchResults extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_food_search_result);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainActivity.PlaceholderFragment()).commit();
        }
    }

    public void displayResult() {

    }

}
