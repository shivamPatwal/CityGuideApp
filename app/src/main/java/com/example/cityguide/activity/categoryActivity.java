package com.example.cityguide.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cityguide.MySingleton;
import com.example.cityguide.R;
import com.example.cityguide.adapters.categoryAdapter;
import com.example.cityguide.modals.details_categories;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class categoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String longitute, latitude;
categoryAdapter adapter;
    ArrayList<details_categories> arrayList = new ArrayList<>();
    String query;

    String apiKey="AIzaSyAxrj_oW36wimhP-0Y2kvQSkK2CG6mTmKc";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.category_recycle);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Intent i=getIntent();
        query=i.getStringExtra("query");
        fetchData();
    }




    private void fetchData() {

        MainActivity a = new MainActivity();
        latitude = a.getLatitude();
        longitute = a.getLongitude();
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
        String lat=a.getLatitude()+","+a.getLongitude();
        Uri baseUrl= Uri.parse(url);
        Uri.Builder uriBuilder = baseUrl.buildUpon();
        uriBuilder.appendQueryParameter("ll",lat);
        uriBuilder.appendQueryParameter("query",query);
      uriBuilder.appendQueryParameter("key",apiKey);
        Log.d("urlll",uriBuilder.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, uriBuilder.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++){
                     JSONObject jsonObject=jsonArray.getJSONObject(i);
                     String address="null";
                     if(jsonObject.has("formatted_address")) {
                         address = jsonObject.getString("formatted_address");
                     }
                    double lat=0,lon=0;
                     if(jsonObject.has("geometry") ) {
                         JSONObject jsonObject1 = jsonObject.getJSONObject("geometry");
                         JSONObject jsonObject2 = jsonObject1.getJSONObject("location");
                          lat = jsonObject2.getDouble("lat");
                         lon = jsonObject2.getDouble("lng");
                     }
                        String resname=null;
                     if(jsonObject.has("name")) {
                         resname = jsonObject.getString("name");
                     }
                        String placeid=null;
                     if(jsonObject.has("place_id")) {
                         placeid = jsonObject.getString("place_id");
                     }
                        double rating=0;
                     if(jsonObject.has("rating")) {
                        rating = jsonObject.getDouble("rating");
                     }
                        String photoref="null";
                        if(jsonObject.has("photos")) {
                            JSONArray jsonArray1 = jsonObject.getJSONArray("photos");

                            JSONObject jsonObject3 = jsonArray1.getJSONObject(0);
                            photoref = jsonObject3.getString("photo_reference");
                        }

                    Log.d("S",resname+"/"+address+"/"+lat+"/"+lon+"/"+photoref+"/"+placeid+"/"+rating);

                //      fetchPhoto(resname,address,lat,lon,photoref,placeid,rating);
details_categories details=new details_categories(resname,address,lat,lon,photoref,placeid,rating);
                        arrayList.add(details);

                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage()+"ss", Toast.LENGTH_SHORT).show();
                }


                adapter = new categoryAdapter(categoryActivity.this, arrayList);
               recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Failed:Check Internet", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }




}