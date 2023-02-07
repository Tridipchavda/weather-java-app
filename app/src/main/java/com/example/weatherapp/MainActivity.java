package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import adapter.RecyclerViewAdapter;


public class MainActivity extends AppCompatActivity {

    TextView hmd, tmp, prs, region, dir, type;
    AutoCompleteTextView cname;
    ImageButton search;
    ImageView imageView;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    FusedLocationProviderClient LocationProvider;
    private String key = "fb629833558d4f4292e134701221009";
    public String url = "https://api.weatherapi.com/v1/current.json?key="+key+"&q=London&aqi=no";
    public String forecastApi = "https://api.weatherapi.com/v1/forecast.json?key="+key+"&q=London&days=10&aqi=no&alerts=no";

    ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationProvider = LocationServices.getFusedLocationProviderClient(this);

        hmd = findViewById(R.id.hmd);
        prs = findViewById(R.id.prs);
        tmp = findViewById(R.id.tmp);
        region = findViewById(R.id.region);
        cname = (AutoCompleteTextView) findViewById(R.id.cityname);
        dir = findViewById(R.id.dir);
        search = findViewById(R.id.search);
        imageView = findViewById(R.id.imageView);
        type = findViewById(R.id.type);
        recyclerView = findViewById(R.id.recycler);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cname.getText().toString() == ""){
                    url = "https://api.weatherapi.com/v1/current.json?key="+key+"&q=London&aqi=no";
                    forecastApi = "https://api.weatherapi.com/v1/forecast.json?key="+key+"&q=London&days=10&aqi=no&alerts=no";
                }
                else {
                    url = "https://api.weatherapi.com/v1/current.json?key="+key+"&q=" + cname.getText() + "&aqi=no";
                    forecastApi = "https://api.weatherapi.com/v1/forecast.json?key="+key+"&q="+ cname.getText() +"&days=10&aqi=no&alerts=no";
                }
                fetchApis();
            }
        });

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                search.performClick();
            }
        },1000);



    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationProvider.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> ad = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        System.out.println(ad.get(0).getLocality());
                        cname.setText(ad.get(0).getLocality());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void fetchApis(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject sys1 = response.getJSONObject("location");
                    JSONObject sys2 = response.getJSONObject("current");
                    JSONObject sys3 = sys2.getJSONObject("condition");

                    Picasso.get().load("https://".concat(sys3.getString("icon"))).into(imageView);
                    type.setText(sys3.getString("text"));

//                    region.setText(sys1.getString("region"));

                    prs.setText("Pressure : " + sys2.getString("pressure_mb"));
                    hmd.setText("HMD : " + sys2.getString("humidity"));
                    dir.setText("Wind Dir : " + sys2.getString("wind_dir"));
                    tmp.setText(sys2.getString("temp_c") + "\u00B0 C");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast toast = Toast.makeText(getApplicationContext(), "No Such City Found", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );

        contacts.removeAll(contacts);

        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(),contacts,forecastApi);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, forecastApi, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject sys1 = response.getJSONObject("forecast");
                    JSONArray sys2 = sys1.getJSONArray("forecastday");

                    for(int i=0;i<sys2.length();i++){
                        JSONObject sys3 = sys2.getJSONObject(i);
                        JSONObject forTemp = sys3.getJSONObject("day");
                        JSONObject forCondition = forTemp.getJSONObject("condition");

                        String date = sys3.getString("date");
                        if(i==0){
                            date = "Today";
                        }
                        if(i==1){
                            date= "Tomorrow";
                        }

                        String min = forTemp.getString("mintemp_c");
                        String max = forTemp.getString("maxtemp_c");
                        String img = forCondition.getString("icon");
                        String condition = forCondition.getString("text");

                        contacts.add(new Contact(date,min,max,img,condition));

                        recyclerView.setAdapter(recyclerViewAdapter);

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

        requestQueue.add(jsonObjectRequest);
        requestQueue.add(jsonObjectRequest2);


    }
}