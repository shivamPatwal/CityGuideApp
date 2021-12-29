package com.example.cityguide.modals;

import android.location.Location;
import android.widget.Toast;

import com.example.cityguide.activity.MainActivity;
import com.google.android.gms.maps.GoogleMap;


import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;

import static java.lang.String.format;

public class details_categories {

String name;
String address;
double lat;
double lon;
String photorefrence;
String photoid;
double rating;

    public details_categories(String name, String address, double lat, double lon, String photorefrence, String photoid, double rating) {
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.photorefrence = photorefrence;
        this.photoid = photoid;
        this.rating = rating;
    }

    public String getName() {

        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getPhotorefrence() {
    String u="";
        if(photorefrence.equals("null")){   u="ss";    }
        else{
   u="https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&photo_reference="+photorefrence+"&key=AIzaSyAxrj_oW36wimhP-0Y2kvQSkK2CG6mTmKc";
        }
    return  u;
    }

    public String getPhotoid() {
        return photoid;
    }

    public String getRating() {
        return String.valueOf(rating);
    }


   public String getDistance(){
       MainActivity a=new MainActivity();
       double lat1= Double.parseDouble(a.getLatitude());
       double lon1= Double.parseDouble(a.getLongitude());
       double lat2=lat;
       double lon2=lon;


           LatLng sydney = new LatLng(lat1, lon1);
           LatLng Brisbane = new LatLng(lat2, lon2);
           Double distance;

               distance = SphericalUtil.computeDistanceBetween(sydney, Brisbane);

       return String.format("%.2f", distance / 1000) + " km";
   }




}

