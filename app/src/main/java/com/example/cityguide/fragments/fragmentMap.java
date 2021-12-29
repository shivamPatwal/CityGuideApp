package com.example.cityguide.fragments;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cityguide.activity.MainActivity;
import com.example.cityguide.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class fragmentMap extends Fragment implements OnMapReadyCallback {
Button currLoc;
    Location currentLocation;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_map, container, false);

        fetchLocation();
        currLoc=view.findViewById(R.id.myloc);
        currLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            fetchLocation();
            }
        });
        return view;
    }
    private void fetchLocation() {
                    MainActivity a=new MainActivity();
                    currentLocation = a.getLoction();
                   Toast.makeText(getContext(), currentLocation.getLatitude() + "," + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.myMap);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(fragmentMap.this);

            }


    @Override
    public void onMapReady(GoogleMap googleMap) {
            Log.d("ss", String.valueOf(currentLocation.getLatitude())) ;

        Log.d("sp", String.valueOf(currentLocation.getLongitude())) ;

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        float zoomLevel = 14.0f;
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

        // googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));

        googleMap.addMarker(markerOptions); }


}