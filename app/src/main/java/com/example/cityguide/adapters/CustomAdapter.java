package com.example.cityguide.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cityguide.R;
import com.example.cityguide.database.locDbHelper;
import com.example.cityguide.fragments.fragmentSaved;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList loc_id, loc_title, loc_address,loc_map,loc_image;

    public CustomAdapter(Activity activity, Context context, ArrayList loc_id, ArrayList loc_title, ArrayList loc_address,ArrayList loc_map,ArrayList loc_image){
        this.activity = activity;
        this.context = context;
        this.loc_id = loc_id;
        this.loc_title = loc_title;
        this.loc_address = loc_address;
        this.loc_map = loc_map;
        this.loc_image=loc_image;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_todo, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
     //   holder.loc_id_txt.setText(String.valueOf(loc_id.get(position)));
        holder.loc_title_txt.setText(String.valueOf(loc_title.get(position)));
        holder.loc_address_txt.setText(String.valueOf(loc_address.get(position)));
        Glide.with(context).load( loc_image.get(position)  ).into(holder.photo);


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(String.valueOf(loc_map.get(position))));
                activity.startActivity(intent);

            }
        });

       holder.mainLayout.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {
               confirmDialog(position);
             return false;
          }});

    }

    private void confirmDialog(int position) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete ?");
        builder.setMessage("Are you sure you want to delete ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                locDbHelper myDB = new locDbHelper(context);
                myDB.deleteOneRow(String.valueOf(loc_id.get(position)));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }


    @Override
    public int getItemCount() {
        return loc_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  loc_title_txt, loc_address_txt;
        LinearLayout mainLayout;
        ImageView photo;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            loc_title_txt = itemView.findViewById(R.id.t1);
            loc_address_txt = itemView.findViewById(R.id.t2);
           photo = itemView.findViewById(R.id.i1);

           mainLayout = itemView.findViewById(R.id.mainLayout);

        }

    }

}