package com.khalifacomputer.speechtosign;

/**
 * Created by hany on 10/08/2015.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONVParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String jsonv = "";

    // constructor
    public JSONVParser() {

    }

    public JSONObject getJSONVFromUrl(String url,String valueIWantToSend) {

        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            // create a list to store HTTP variables and their values
            List nameValuePairs = new ArrayList();
            Log.i("oneword---->",valueIWantToSend);
            // add an HTTP variable and value pair
            nameValuePairs.add(new BasicNameValuePair("oneword", valueIWantToSend));
            // nameValuePairs.add(new BasicNameValuePair("key", "fawzy"));
            nameValuePairs.add(new BasicNameValuePair("prevword", "شرفنطح"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            // send the variable and value, in other words post, to the URL
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            Log.e("1 --->","UnsupportedEncodingException");
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("2 --->","ClientProtocolException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("3 --->","IOException");
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"));
            StringBuilder sb = new StringBuilder();

            int c;
            while ((c = reader.read()) !=-1) {


                sb.append((char)c);
            }
            is.close();
            Log.i("first word---->",sb.toString());
            jsonv = sb.toString().replaceAll("﻿","");
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result v------> " + e.toString());
        }

        // try parse the string to a JSONV object
        try {
            jObj = new JSONObject(jsonv);
        } catch (JSONException e) {
            Log.e("JSONV Parser", "Error parsing data v---->" + e.toString());
        }

        // return JSONV String
        return jObj;

    }
}


