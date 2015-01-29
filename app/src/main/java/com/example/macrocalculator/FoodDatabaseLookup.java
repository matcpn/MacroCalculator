package com.example.macrocalculator;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Matt on 1/27/2015.
 */
class FoodDatabaseLookup extends AsyncTask<String, Void, NodeList> {

    private Exception exception;

    protected NodeList doInBackground(String... urlString) {
        try {
            Log.d("url being passed", urlString[0]);
            HttpClient client = new DefaultHttpClient();
            HttpGet req = new HttpGet(urlString[0]);
            HttpResponse response;
            response = client.execute(req);
            HttpEntity entity = response.getEntity();
            String result = null;

            if (entity != null) {
                // A Simple JSON Response Read
                result = EntityUtils.toString(entity);
            }
            Log.d("result", result);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            InputSource is;
            builder = factory.newDocumentBuilder();
            is = new InputSource(new StringReader(result));
            Document doc = builder.parse(is);
            NodeList list = doc.getElementsByTagName("name");

            return list;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(NodeList o) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}