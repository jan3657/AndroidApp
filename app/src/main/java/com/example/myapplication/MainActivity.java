package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private TextView naprave;
    private String url = "https://smartify-dev.azurewebsites.net/api/DeviceApi";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        naprave = (TextView) findViewById(R.id.naprave);
    }

    public  void prikaziNaprave(View view){
        if (view != null){
            JsonArrayRequest request = new JsonArrayRequest(url, jsonArrayListener, errorListener);
            requestQueue.add(request);
        }
    }

    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response){
            ArrayList<String> data = new ArrayList<>();

            for (int i = 0; i < response.length(); i++){
                try {
                    JSONObject object = response.getJSONObject(i);
                    String deviceID = object.getString("deviceID");
                    String areaID = object.getString("areaID");
                    String deviceTypeID = object.getString("deviceTypeID");
                    String autoMessageID = object.getString("autoMessageID");
                    String deviceParameters = object.getString("deviceParameters");
                    String currentStatus = object.getString("currentStatus");
                    String timeActivated = object.getString("timeActivated");
                    String timeDeactivated = object.getString("timeDeactivated");

                    data.add(deviceID + " " + areaID + " " + deviceTypeID + " " + autoMessageID + " " +
                            deviceParameters + " " + currentStatus + " " + timeActivated + " " + timeDeactivated );

                } catch (JSONException e){
                    e.printStackTrace();
                    return;

                }
            }

            naprave.setText("");


            for (String row: data){
                String currentText = naprave.getText().toString();
                naprave.setText(currentText + "\n\n" + row);
            }

        }

    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };


}