package com.example.cityguide;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static com.example.cityguide.MySingleton instance;
    private RequestQueue requestQueue;
    private Context ctx;


    private MySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();


    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {

            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }


    public static synchronized com.example.cityguide.MySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new com.example.cityguide.MySingleton(context);
        }
        return instance;
    }


    public <T> void addToRequestQueue(Request<T> req) {

        getRequestQueue().add(req);
    }
}