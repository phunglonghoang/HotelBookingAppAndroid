package com.tutorial.travel.model;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewModel extends RecyclerView.Adapter {
    public int id;
    public String reviewDetail;
    public Double rating;
    public int hotel_id;
    public int user_id;

    public ReviewModel(){

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public ReviewModel(int id, String reviewDetail, Double rating, int hotel_id, int user_id) {
        this.id = id;
        this.reviewDetail = reviewDetail;
        this.rating = rating;
        this.hotel_id = hotel_id;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReviewDetail() {
        return reviewDetail;
    }

    public void setReviewDetail(String reviewDetail) {
        this.reviewDetail = reviewDetail;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    @Override
    public String toString() {

        return "ReviewModel{" +
                "id=" + id +
                ", rvDetail='" + reviewDetail + '\'' +
                ", rating=" + rating +

                ", hotel_id=" + hotel_id +
                ", userId=" + user_id +
                '}';
    }
}
