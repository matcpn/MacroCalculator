package com.example.macrocalculator.RequestResponseListener;

import android.util.Log;

import com.example.macrocalculator.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Brendan on 2/6/15.
 */
public class SearchRequestListener implements RequestResponseListener {

    public void onRequestCompleted(JSONObject searchResults) {
        JSONParser parser = new JSONParser(searchResults);

        JSONArray items = parser.getSearchResultsItems();
        for(int i = 0; i < items.length(); i++) {
            try {
                Log.d("item", items.getJSONObject(i).getString("name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onRequestFailed() {

    }
}
