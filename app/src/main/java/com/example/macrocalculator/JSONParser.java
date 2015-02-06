package com.example.macrocalculator;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Brendan on 2/6/15.
 */
public class JSONParser {

    JSONObject jObject;

    public JSONParser(JSONObject jObject) {
        this.jObject = jObject;
    }

    public JSONArray getSearchResultsItems() {
        try {
            return this.jObject.getJSONObject("list").getJSONArray("item");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Error", "Could not parse items");
        return new JSONArray();
    }

}
