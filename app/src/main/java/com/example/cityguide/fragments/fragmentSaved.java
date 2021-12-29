package com.example.cityguide.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityguide.R;
import com.example.cityguide.adapters.CustomAdapter;
import com.example.cityguide.database.locDbHelper;

import java.util.ArrayList;

public class fragmentSaved extends Fragment {

    ImageView empty_imageview;
    TextView no_data;

    RecyclerView recyclerView;

    locDbHelper myDB;
    ArrayList<String> loc_id, loc_title, loc_address,loc_mapurl,loc_imageurl;
    CustomAdapter customAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      View  view = inflater.inflate(R.layout.fragement_saved, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        empty_imageview = view.findViewById(R.id.empty_imageview);
        no_data = view.findViewById(R.id.no_data);
        setHasOptionsMenu(true);

        myDB = new locDbHelper(getContext());
        loc_id = new ArrayList<>();
        loc_title = new ArrayList<>();
        loc_address = new ArrayList<>();

        loc_mapurl = new ArrayList<>();
        loc_imageurl = new ArrayList<>();

        storeDataInArrays();


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            getActivity().recreate();
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        MenuInflater inflater1 = getActivity().getMenuInflater();
        inflater1.inflate(R.menu.my_menu, menu);
        super.onCreateOptionsMenu(menu, inflater1);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return true;
    }


    public  void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                loc_id.add(cursor.getString(0));
                loc_title.add(cursor.getString(1));
                loc_address.add(cursor.getString(2));
               loc_mapurl.add(cursor.getString(3));
                loc_imageurl.add(cursor.getString(4));

            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }

        customAdapter = new CustomAdapter(getActivity(),getContext(), loc_id, loc_title, loc_address,loc_mapurl,loc_imageurl);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }





    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                locDbHelper myDB = new locDbHelper(getContext());
                myDB.deleteAllData();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}
