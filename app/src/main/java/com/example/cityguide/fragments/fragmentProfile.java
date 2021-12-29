package com.example.cityguide.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.cityguide.R;
import com.example.cityguide.circle;
import com.example.cityguide.modals.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragmentProfile extends Fragment {
   ImageView imageView;
   TextView textView1;
   TextView textView2;
    DatabaseReference rootref;
   FirebaseUser fuser;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_profile, container, false);


        imageView=view.findViewById(R.id.imageView5);
        textView1=view.findViewById(R.id.displayName);
        textView2=view.findViewById(R.id.displayemail);

      setDetails();

        return view;




    }

    private void setDetails() {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        rootref= FirebaseDatabase.getInstance().getReference("Users");
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users user=dataSnapshot.getValue(Users.class);
                    Glide.with(getContext()).load(user.getImageUrl().toString()).transform(new circle(getContext())).into(imageView);

                    textView1.setText(user.getUserName());
                    textView2.setText(fuser.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
