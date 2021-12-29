package com.example.cityguide.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cityguide.R;
import com.example.cityguide.activity.viewActivity;
import com.example.cityguide.modals.PostItem;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
   private ArrayList<PostItem> postitem;
    private Context context;
   public static String url;
    public  PostAdapter(){}

   public PostAdapter(Context context,ArrayList<PostItem>postitem){
       this.context=context;
       this.postitem=postitem;
   }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.fragment_photo_itemlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostItem post=postitem.get(position);
        Glide.with(holder.postimageView.getContext()).load(post.getImage()).into(holder.postimageView);

        holder.postimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url=post.getImage();
                Log.d("s2",url);
                Intent intent=new Intent(context, viewActivity.class);

                context.startActivity(intent);
            }
        });

   }

    @Override
    public int getItemCount() {
        return postitem.size();
    }


    public class PostViewHolder extends RecyclerView.ViewHolder {
       RoundedImageView postimageView;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postimageView=itemView.findViewById(R.id.imagePost);
        }



   }


   public  String getUrl(){
       return  url;
   }
}
