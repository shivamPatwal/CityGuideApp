package com.example.cityguide.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cityguide.activity.detailActivity;
import com.example.cityguide.modals.details_categories;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cityguide.R;

import java.util.ArrayList;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.categoryHolder> {
    private Context context;
    private ArrayList<details_categories> arrayList = new ArrayList<>();
    public  static  String photoid;
    public  static  String photourl;
    public  static  String placename;

    public categoryAdapter(Context context, ArrayList<details_categories> rrayList) {
        this.context = context;
        arrayList = rrayList;
    }

    public   categoryAdapter(){

    }

    @NonNull
    @Override
    public categoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_category, parent, false);

        return new categoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryHolder holder, int position) {
        details_categories detail=arrayList.get(position);



            holder.resname.setText(detail.getName());

        if(detail.getRating().equals("0")){
           holder.resrating.setText("");
        }
        else {
            holder.resrating.setText(detail.getRating());
        }

if(detail.getAddress().equals("null")) {
    holder.resaddress.setText(""); }
else{
    holder.resaddress.setText(detail.getAddress());
}
String distance= detail.getDistance();
holder.resdistance.setText(distance);

if(!detail.getPhotorefrence().equals("ss")){
    Glide.with(holder.itemView.getContext()).load(detail.getPhotorefrence()).into(holder.imageView);

    Log.d("yes",detail.getPhotorefrence());
}else{
    Glide.with(holder.itemView.getContext()).load(R.drawable.noimage).into(holder.imageView);

}

holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      photoid=detail.getPhotoid();
      photourl=detail.getPhotorefrence();
      placename=detail.getName();
        Intent i=new Intent(context, detailActivity.class);
        context.startActivity(i);
    }
});

    }


    @Override
    public int getItemCount() {

        return arrayList.size();
    }


    public class categoryHolder extends RecyclerView.ViewHolder {
        TextView resname, resrating, resaddress, resdistance;
        ImageView imageView;

        public categoryHolder(@NonNull View itemView) {
            super(itemView);
            resname = itemView.findViewById(R.id.textView3);
            resrating = itemView.findViewById(R.id.textView6);
            resaddress = itemView.findViewById(R.id.textView4);
            imageView=itemView.findViewById(R.id.imageView3);

            resdistance = itemView.findViewById(R.id.textView5);


        }
    }


    public  String getphotoid(){
        return photoid;
    }
    public  String getphotoiurl(){
        return photourl;
    }
    public  String getpplacename(){
        return placename;
    }

}

