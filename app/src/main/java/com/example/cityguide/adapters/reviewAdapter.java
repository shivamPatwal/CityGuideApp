package com.example.cityguide.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cityguide.R;
import com.example.cityguide.activity.detailActivity;
import com.example.cityguide.modals.details_categories;
import com.example.cityguide.modals.reviews;

import java.util.ArrayList;
import java.util.List;

public class reviewAdapter extends RecyclerView.Adapter<reviewAdapter.reviewHolder> {private Context context;
    private ArrayList<reviews> arrayList = new ArrayList<>();


    public reviewAdapter(Context context, ArrayList<reviews> rrayList) {
        this.context = context;
        arrayList = rrayList;
    }

    public   reviewAdapter(){

    }

    @NonNull
    @Override
    public reviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.featured_card_design, parent, false);

        return new reviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewHolder holder, int position) {
        reviews detail=arrayList.get(position);
if(!detail.getAuthor_name().equals("na")){ holder.authorName.setText(detail.getAuthor_name()); }
else{  holder.authorName.setText("");  }


        if(detail.getRating()!=0){ holder.ratingBar.setRating((float)detail.getRating()); }
        else{  holder.ratingBar.setRating(0);}

        if(detail.getImagrurl().equals("na")){ Glide.with(holder.itemView.getContext()).load(R.drawable.noimage).into(holder.profilepic); }
        else {
            Glide.with(holder.itemView.getContext()).load(detail.getImagrurl()).into(holder.profilepic);
        }

holder.desc.setText(detail.getReview());

    }


    @Override
    public int getItemCount() {

        return arrayList.size();
    }


    public class reviewHolder extends RecyclerView.ViewHolder {
ImageView profilepic;

        TextView authorName,desc;
        RatingBar ratingBar;

        public reviewHolder(@NonNull View itemView) {
            super(itemView);
profilepic=itemView.findViewById(R.id.mv_image);
            authorName=itemView.findViewById(R.id.textvie);
ratingBar=itemView.findViewById(R.id.mv_rating);
desc=itemView.findViewById(R.id.mv_desc);
        }
    }


}
