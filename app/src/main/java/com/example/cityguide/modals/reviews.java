package com.example.cityguide.modals;

public class reviews {

String imagrurl;
String review;
String author_name;
double rating;


    public reviews(String imagrurl, String review, String author_name, double rating) {
        this.imagrurl = imagrurl;
        this.review = review;
        this.author_name = author_name;
        this.rating = rating;
    }

    public String getImagrurl() {
        return imagrurl;
    }

    public String getReview() {
        return review;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public double getRating() {
        return  rating;
    }
}
