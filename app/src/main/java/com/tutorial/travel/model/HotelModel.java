package com.tutorial.travel.model;

public class HotelModel {
    private int id;
    private String hotelName;
    private String location;
    private int starRating;
    private String image;


    public HotelModel(int id, String hotelName, String location, int starRating, String image) {
        this.id = id;
        this.hotelName = hotelName;
        this.location = location;
        this.starRating = starRating;
        this.image = image;
    }

    public Hotel(int id, String hotelName, String location) {
        this.id = id;
        this.hotelName = hotelName;
        this.location = location;
    }

    public Hotel(String hotelName, String location, int starRating, String image) {
        this.hotelName = hotelName;
        this.location = location;
        this.starRating = starRating;
        this.image = image;
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

////<<<<<<< HEAD:app/src/main/java/com/tutorial/travel/model/Hotel.java
//    @Override
//    public String toString() {
//        return "Hotel{" +
//                "hotelID='" + id + '\'' +
//                "hotelName='" + hotelName + '\'' +
//                ", hotelLocation='" + location + '\'' +
//                ", hotelManager='" + starRating + '\'' +
//                ", image ='" + image + '\'' +
//                '}';
//    }
//
//    public Hotel(int id, String hotelName, String location, int starRating, String image) {
//        this.id = id;
//        this.hotelName = hotelName;
//        this.location = location;
//        this.starRating = starRating;
//        this.image = image;
//    }

//=======
//>>>>>>> main:app/src/main/java/com/tutorial/travel/model/HotelModel.java
}
