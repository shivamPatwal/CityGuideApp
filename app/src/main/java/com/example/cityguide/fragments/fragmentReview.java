package com.example.cityguide.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.cityguide.adapters.reviewAdapter;
import com.example.cityguide.modals.reviews;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class fragmentReview extends Fragment {

    RecyclerView recyclerView;

    ArrayList<reviews> arrayList = new ArrayList<>();
    String placeId;
    String apiKey = "AIzaSyAxrj_oW36wimhP-0Y2kvQSkK2CG6mTmKc";

    RecyclerView.LayoutManager RecyclerViewLayoutManager;


    reviewAdapter adapter;


    LinearLayoutManager HorizontalLayout;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_review, container, false);
        data();
        recyclerView
                = (RecyclerView) view.findViewById(R.id.recycleView);
        RecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fetchdata();


        return view;

    }

    private void fetchdata() {

        String url = "https://maps.googleapis.com/maps/api/place/details/json?";
        Uri baseUrl = Uri.parse(url);
        Uri.Builder uriBuilder = baseUrl.buildUpon();
        uriBuilder.appendQueryParameter("place_id", placeId);
        uriBuilder.appendQueryParameter("key", apiKey);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, uriBuilder.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("result");
                    JSONArray jsonArray = jsonObject.getJSONArray("reviews");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String authorName = "na";
                        if (jsonObject1.has("author_name")) {
                            authorName = jsonObject1.getString("author_name");

                        }

                        String profilepic = "na";
                        if (jsonObject1.has("profile_photo_url")) {
                            profilepic = jsonObject1.getString("profile_photo_url");

                        }

                        String text = "na";
                        if (jsonObject1.has("text")) {
                            text = jsonObject1.getString("text");

                        }

                        double rating=0.0;

                        if (jsonObject1.has("rating")) {
                            rating = jsonObject1.getDouble("rating");

                        }

                        reviews review=new reviews(profilepic,text,authorName,rating);
                        arrayList.add(review);


                    }


                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage() + "ss", Toast.LENGTH_SHORT).show();
                }

                adapter = new reviewAdapter(getContext(), arrayList);

                HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(HorizontalLayout);

                recyclerView.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Failed:Check Internet", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

    }


    private void data() {
        categoryAdapter a = new categoryAdapter();
        Log.d("st", a.getphotoid());
        placeId = a.getphotoid();

    }

}