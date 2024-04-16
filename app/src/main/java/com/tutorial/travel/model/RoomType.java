package com.tutorial.travel.model;

public class RoomType {
    private int id;
    private String type;
    private String price;
    private int quantity;
    private String image;

    private int hotel_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public RoomType(int id, String type, String price, int quantity, String image, int hotel_id) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.hotel_id = hotel_id;
    }

    public RoomType(){

    }


}
