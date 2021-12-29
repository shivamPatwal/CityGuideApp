package com.example.cityguide.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cityguide.MySingleton;
import com.example.cityguide.R;
import com.example.cityguide.activity.categoryActivity;
import com.example.cityguide.adapters.categoryAdapter;
import com.example.cityguide.modals.details_categories;

import org.json.JSONArray;
import org.json.JSONObject;

public class fragmentHome extends Fragment {

    ImageView breakfast,lunch,dinner,cofee,nightlife,things;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_home, container, false);
        breakfast=view.findViewById(R.id.one);


        lunch=view.findViewById(R.id.two);
        dinner=view.findViewById(R.id.three);
        cofee=view.findViewById(R.id.four);
        nightlife=view.findViewById(R.id.five);
        things=view.findViewById(R.id.six);

    lunch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent i=new Intent(getContext(), categoryActivity.class);
            i.putExtra("query","lunch");
            startActivity(i);

        }
    });


        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), categoryActivity.class);

                i.putExtra("query","breakfast");
                startActivity(i);
            }
        });


        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), categoryActivity.class);

                i.putExtra("query","dhaba");
                startActivity(i);
            }
        });


        cofee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), categoryActivity.class);

                i.putExtra("query","coffee");
                // i.putExtra("url","https://api.foursquare.com/v2/venues/explore");
                startActivity(i);
            }
        });


        nightlife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getContext(), categoryActivity.class);
                i.putExtra("query","nightlife");
                startActivity(i);
            }
        });


        things.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i=new Intent(getContext(), categoryActivity.class);
                i.putExtra("query","things to do");
                startActivity(i);

            }
        });




        return view;

    }






}


