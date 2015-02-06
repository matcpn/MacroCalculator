package com.example.macrocalculator.RequestResponseListener;

import org.json.JSONObject;

/**
 * Created by Brendan on 2/6/15.
 */
public interface RequestResponseListener {

    public void onRequestCompleted(JSONObject searchResults);
    public void onRequestFailed();

}
