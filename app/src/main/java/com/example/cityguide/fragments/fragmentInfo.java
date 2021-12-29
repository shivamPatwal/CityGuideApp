package com.example.cityguide.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cityguide.MySingleton;
import com.example.cityguide.R;
import com.example.cityguide.activity.MainActivity;
import com.example.cityguide.adapters.categoryAdapter;
import com.example.cityguide.database.locDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

public class fragmentInfo extends Fragment {
    FloatingActionButton f1, f2, f3, f4;
    private Boolean clicked = false;
    String placeId;
    String apiKey = "AIzaSyAxrj_oW36wimhP-0Y2kvQSkK2CG6mTmKc";
    String mapurl;
    String  locname,locadd,imageurl;
    String lat,lon;

    TextView address, textProgress, category1, category2;
    ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_info, container, false);
        data();
        f1 = view.findViewById(R.id.floatingActionButton1);
        f2 = view.findViewById(R.id.floatingActionButton2);
        f3 = view.findViewById(R.id.floatingActionButton3);
        f4 = view.findViewById(R.id.floatingActionButton4);
        address = view.findViewById(R.id.address);
        progressBar = view.findViewById(R.id.progress_bar);
        textProgress = view.findViewById(R.id.text_view_progress);
        category1 = view.findViewById(R.id.t1);
        category2 = view.findViewById(R.id.t2);

        categoryAdapter a=new categoryAdapter();
        imageurl=a.getphotoiurl();
        fetchData();

        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });

        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(mapurl));
                startActivity(intent);

            }
        });

        f3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            route();

            }
        });
        f4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               locDbHelper myDb=new locDbHelper(getContext());
               myDb.addLoc(locname,locadd,mapurl,imageurl);
             //   myDb.addLoc(locname,mapurl);
            }
        });


        return view;


    }

    


    private void route() {

        MainActivity a=new MainActivity();
        String lat1=a.getLatitude();
        String lon1=a.getLongitude();
try {
    Uri uri=Uri.parse("https://www.google.co.in/maps/dir/"+lat1+","+lon1+"/"+lat+","+lon);
    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
    intent.setPackage("com.google.android.apps.maps");
intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
startActivity(intent);
}catch (ActivityNotFoundException e){

    Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
Intent intent=new Intent(Intent.ACTION_VIEW,uri);
intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
startActivity(intent);
}

    }

    private void fetchData() {

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
 locadd=jsonObject.getString("formatted_address");
                    address.setText(locadd);
                    double rating = 0.0;
                    locname=jsonObject.getString("name");

                    if (jsonObject.has("url")) {
                        mapurl = jsonObject.getString("url");
                    }
                    String rate = "NA";
                    if (jsonObject.has("rating")) {
                        rating = jsonObject.getDouble("rating") * 20;
                        rate = String.valueOf(jsonObject.getDouble("rating"));
                    }
                    progressBar.setProgress((int) rating);
                    textProgress.setText(rate);
                    JSONArray jsonArray = jsonObject.getJSONArray("types");

                    category1.setText(jsonArray.getString(0));
                    category2.setText(jsonArray.getString(1));

                    JSONObject jsonObject1=jsonObject.getJSONObject("geometry");
                    JSONObject jsonObject2=jsonObject1.getJSONObject("location");
                    lat= String.valueOf(jsonObject2.getDouble("lat"));
                    lon= String.valueOf(jsonObject2.getDouble("lng"));


                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage() + "ss", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Failed:Check Internet", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);


    }


    private void onAddButtonClicked() {
        setVisibility(clicked);

        setClickable(clicked);
        setAnimation(clicked);
        if (!clicked) clicked = true;
        else clicked = false;
    }


    private void setClickable(Boolean click) {
        if (!click) {
            f2.setClickable(true);
            f3.setClickable(true);
            f4.setClickable(true);
        } else {
            f2.setClickable(false);
            f3.setClickable(false);
            f4.setClickable(false);

        }

    }

    private void setVisibility(Boolean click) {
        if (!click) {
            f2.setVisibility(View.VISIBLE);
            f3.setVisibility(View.VISIBLE);
            f4.setVisibility(View.VISIBLE);
        } else {
            f2.setVisibility(View.INVISIBLE);
            f3.setVisibility(View.INVISIBLE);
            f4.setVisibility(View.INVISIBLE);
        }

    }

    private void setAnimation(Boolean click) {
        if (!click) {
            f2.startAnimation(fromBottom());
            f3.startAnimation(fromBottom());
            f4.startAnimation(fromBottom());
            f1.startAnimation(rotateOpen());
        } else {
            f2.startAnimation(toBottom());
            f3.startAnimation(toBottom());
            f4.startAnimation(toBottom());
            f1.startAnimation(rotateClose());

        }

    }


    private void data() {
        categoryAdapter a = new categoryAdapter();
        Log.d("st", a.getphotoid());
        placeId = a.getphotoid();

    }

    private Animation rotateOpen() {
        return AnimationUtils.loadAnimation(getContext(), R.anim.rotate_open_anim);

    }


    private Animation rotateClose() {
        return AnimationUtils.loadAnimation(getContext(), R.anim.rotate_close_anim);

    }


    private Animation fromBottom() {
        return AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_anim);

    }

    private Animation toBottom() {
        return AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom_anim);

    }

}
