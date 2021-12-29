package com.example.cityguide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.cityguide.R;
import com.example.cityguide.adapters.PostAdapter;
import com.example.cityguide.adapters.categoryAdapter;

public class viewActivity extends AppCompatActivity {

ImageView viewphoto;
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_viewphoto);

           viewphoto=findViewById(R.id.i2);
       PostAdapter a=new PostAdapter();
        String url=a.getUrl();

    Glide.with(getApplicationContext()).load(url).into(viewphoto);
    }



}
