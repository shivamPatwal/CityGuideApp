package com.example.cityguide.modals;

public class Users {
    String username="";
    String imageURL="";

Users(){

}
    Users(String profileName, String imageUrl){
        username=profileName;
        imageURL=imageUrl;

    }

    public String getUserName() {
        return username;
    }




    public String getImageUrl() {
        return imageURL;
    }


}
