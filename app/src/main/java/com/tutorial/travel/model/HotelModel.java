package com.tutorial.travel.model;

import java.io.Serializable;

public class HotelModel implements Serializable {
    private int id;
    private String hotelName;
    private String location;
    private int starRating;
    private String image;
    private double minRoomPrice;

    public HotelModel(int id, String hotelName, int starRating, String image) {
        this.id = id;
        this.hotelName = hotelName;
        this.starRating = starRating;
        this.image = image;
    }

    public HotelModel(int id, String hotelName, String location, int starRating, String image, double minRoomPrice) {
        this.id = id;
        this.hotelName = hotelName;
        this.location = location;
        this.starRating = starRating;
        this.image = image;
        this.minRoomPrice = minRoomPrice;
    }

    public HotelModel(int id, String hotelName, String location) {
        this.id = id;
        this.hotelName = hotelName;
        this.location = location;
    }

    public HotelModel(String hotelName, String location, int starRating, String image) {
        this.hotelName = hotelName;
        this.location = location;
        this.starRating = starRating;
        this.image = image;
    }

    public HotelModel() {

    }
//
    public HotelModel(int hotelId, String hotelName, String location, int starRating, String imageUrl) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getMinRoomPrice() {
        return minRoomPrice;
    }

    public void setMinRoomPrice(double minRoomPrice) {
        this.minRoomPrice = minRoomPrice;
    }
}
