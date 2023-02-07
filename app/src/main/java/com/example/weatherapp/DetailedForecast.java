package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.ForecastRecycler;
import adapter.RecyclerViewAdapter;

public class DetailedForecast extends AppCompatActivity {

    RecyclerView recyclerView;
    ForecastRecycler forecastRecycler;
    ArrayList<ForecastStructure> forecast = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_forecast);

        recyclerView = findViewById(R.id.hourForecastData);
        forecastRecycler = new ForecastRecycler(getApplicationContext(),forecast);

        Intent intent = getIntent();
        String forecastAPI = intent.getStringExtra("forecastAPI");
        int position = intent.getIntExtra("counter",0);

        setDataFromAPI(forecastAPI,position);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(forecastRecycler);
    }

    public void setDataFromAPI(String api,int position){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.GET, api, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray hour = response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(position).getJSONArray("hour");

                    for(int i=0;i<hour.length();i++){
                        JSONObject conditionObj = hour.getJSONObject(i).getJSONObject("condition");

                        String datetime = hour.getJSONObject(i).getString("time");
                        String time = datetime.split(" ")[1];

                        String condition = conditionObj.getString("text");
                        String conditionImg = conditionObj.getString("icon");
                        String temp = hour.getJSONObject(i).getString("temp_c");

                        forecast.add(new ForecastStructure(condition,conditionImg,temp,time));
                        recyclerView.setAdapter(forecastRecycler);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast toast = Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );

        requestQueue.add(jsonObjectRequest3);
    }
}
