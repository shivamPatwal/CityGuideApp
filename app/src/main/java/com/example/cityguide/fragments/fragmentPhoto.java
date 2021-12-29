package com.example.cityguide.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cityguide.MySingleton;
import com.example.cityguide.R;
import com.example.cityguide.activity.categoryActivity;
import com.example.cityguide.adapters.PostAdapter;
import com.example.cityguide.adapters.categoryAdapter;
import com.example.cityguide.modals.PostItem;
import com.example.cityguide.modals.details_categories;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class fragmentPhoto extends Fragment {
RecyclerView recyclerView;
    ImageView empty_imageview;
    TextView no_data;
    String placeId;
    String apiKey = "AIzaSyAxrj_oW36wimhP-0Y2kvQSkK2CG6mTmKc";

    ArrayList<PostItem> arrayList = new ArrayList<>();
    PostAdapter adapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_photorecycler, container, false);
        data();
        empty_imageview = view.findViewById(R.id.empty_imageview);
        no_data = view.findViewById(R.id.no_data);
    recyclerView=view.findViewById(R.id.recyclevie);
    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        fetchData();
       return  view;
    }





    private void fetchData() {
        empty_imageview.setVisibility(View.GONE);
        no_data.setVisibility(View.GONE);
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
                    JSONArray jsonArray=jsonObject.getJSONArray("photos");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                       String photoref=jsonObject1.getString("photo_reference");
                        PostItem postItem=new PostItem(photoref);
                        arrayList.add(postItem);
                    }
                } catch (Exception e) {
                    empty_imageview.setVisibility(View.VISIBLE);
                    no_data.setVisibility(View.VISIBLE);



                }

                adapter= new PostAdapter(getContext(), arrayList);
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
