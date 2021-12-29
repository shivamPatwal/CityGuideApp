package com.example.cityguide.modals;

public class PostItem {
    String image;
    String apiKey = "AIzaSyAxrj_oW36wimhP-0Y2kvQSkK2CG6mTmKc";

    public PostItem(String image) {
        this.image = image;
    }

    public String getImage() {
        String photourl="https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&photo_reference="+image+"&key="+apiKey;

        return photourl;
    }



}
