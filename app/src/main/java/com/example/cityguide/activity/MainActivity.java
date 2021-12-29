package com.example.cityguide.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import com.example.cityguide.R;
import com.example.cityguide.circle;
import com.example.cityguide.fragments.fragmentCategory;
import com.example.cityguide.fragments.fragmentAbout;
import com.example.cityguide.fragments.fragmentMap;
import com.example.cityguide.fragments.fragmentHome;
import com.example.cityguide.fragments.fragmentLocation;
import com.example.cityguide.fragments.fragmentProfile;
import com.example.cityguide.fragments.fragmentSaved;
import com.example.cityguide.modals.Users;
import com.example.cityguide.sign.SignInActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imagevew;
    TextView name;
    DatabaseReference rootref;
    FirebaseAuth mauth;
    FirebaseUser firebaseUser;
    public static Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mauth = FirebaseAuth.getInstance();
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("MY PLACE");

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        imagevew = (ImageView) hView.findViewById(R.id.imageView);
        name = (TextView) hView.findViewById(R.id.menu_header_name);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //showing default fragment
        displaySelectedFragment(R.id.nav_home);
        getRef();
        currentlocation();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    void getRef() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rootref = FirebaseDatabase.getInstance().getReference("Users");
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users user = dataSnapshot.getValue(Users.class);
                    Glide.with(getApplicationContext()).load(user.getImageUrl().toString()).transform(new circle(getApplicationContext())).into(imagevew);

                    name.setText(user.getUserName());
                    Log.d("name", firebaseUser.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        // item id is being passed into the method here
        displaySelectedFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setScreenTitle(int item_id) {
        String title = "";
        switch (item_id) {
            case R.id.nav_home:
                title = "Home";
                break;
            case R.id.nav_category:
                title = "Category";
                break;

            case R.id.nav_Location:
                title = "Location";
                break;

            case R.id.nav_save:
                title = "Saved Location";
                break;
            case R.id.nav_profile:
                title = "Profile";
                break;

            case R.id.nav_logout:
                title = "Three";
                break;


            case R.id.nav_map:
                title = "MAP";
                break;

            case R.id.nav_about:
                title = "About";
                break;
        }

        toolbar.setTitle(title);
    }

    public void displaySelectedFragment(int item_id) {


        Fragment fragment = null;

        switch (item_id) {

            case R.id.nav_home:
                fragment = new fragmentHome();
                navigationView.getMenu().getItem(0).setChecked(true);
                break;

            case R.id.nav_category:
                fragment = new fragmentCategory();
                navigationView.getMenu().getItem(1).setChecked(true);
                break;

            case R.id.nav_Location:
                fragment = new fragmentLocation();
                navigationView.getMenu().getItem(2).setChecked(true);
                break;
            case R.id.nav_save:
                fragment = new fragmentSaved();
                navigationView.getMenu().getItem(3).setChecked(true);
                break;
            case R.id.nav_profile:
                fragment = new fragmentProfile();
                navigationView.getMenu().getItem(4).setChecked(true);
                break;
            case R.id.nav_logout:
                logOut();


                navigationView.getMenu().getItem(5).setChecked(true);
                break;

            case R.id.nav_map:
                fragment = new fragmentMap();

                break;

            case R.id.nav_about:
                fragment = new fragmentAbout();

                break;

        }
        if (fragment != null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //this is where the id of the FrameLayout is being mentioned. Hence the fragment would be loaded into the framelayout
            ft.replace(R.id.container, fragment);
            ft.commit();
        }

        /** setting title to the screen **/
        setScreenTitle(item_id);

    }

    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to Log Out ?");
        builder.setTitle("LOG OUT");

        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                 outofScreen();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void outofScreen() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);

    }



    void currentlocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Log.d("lan", String.valueOf(currentLocation.getLatitude()));

                    Log.d("lot", String.valueOf(currentLocation.getLongitude()));

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    currentlocation();
                }
                break;

        }
    }

   public  String getLatitude(){

        return String.valueOf(currentLocation.getLatitude());
    }
    public  String getLongitude(){

        return String.valueOf(currentLocation.getLongitude());
    }


    public  Location getLoction(){

        return  currentLocation;
    }






}




