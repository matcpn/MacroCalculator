package com.example.macrocalculator;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by Matt on 1/27/2015.
 */
class FoodDatabase{

    // http://api.data.gov/usda/ndb/nutrients/?ndbno={USDA_FOOD_NUMBER}&format=json&api_key={API_KEY}&nutrients=203&nutrients=204&nutrients=205&nutrients=208

    private String apiKey = "Y6uH9DT59rzs5C2iBmGffchYkmTJL71CJcaZM8FU";
    private String searchUrl = "api.data.gov/usda/ndb/search/";
    private String nutrientUrl = "api.data.gov/usda/ndb/nutrients/";

    private String proteinNum = "203";
    private String fatNum = "204";
    private String carbsNum = "2053";
    private String caloriesNum = "208";

    private RequestQueue queue = VolleyApplication.getInstance().getRequestQueue();

    protected void search(String searchTerm) {
        try {
            String url = "http://" + searchUrl + "?format=json&q=" + URLEncoder.encode(searchTerm, "UTF-8") + "&api_key=" + apiKey;
            StringRequest searchRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                public void onResponse(String response) {
                    try {
                        JSONObject searchResults = new JSONObject(response);
                        Log.d("results", searchResults.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            queue.add(searchRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}