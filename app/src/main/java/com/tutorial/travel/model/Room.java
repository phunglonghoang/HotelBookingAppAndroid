package com.tutorial.travel.model;

public class Room {
    private String id;
    private String roomName;
    private String roomType;
    private String roomRate;
    private String price;
    private String image;

    public Room(String id, String roomName, String roomType, String roomRate) {
        this.id = id;
        this.roomName = roomName;
        this.roomType = roomType;
        this.roomRate = roomRate;
    }

    public Room(String id, String roomName, String roomType, String roomRate, String price, String image) {
        this.id = id;
        this.roomName = roomName;
        this.roomType = roomType;
        this.roomRate = roomRate;
        this.price = price;
        this.image = image;
    }

    public Room(String id, String roomName, String roomType, String roomRate, String price) {
        this.id = id;
        this.roomName = roomName;
        this.roomType = roomType;
        this.roomRate = roomRate;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Room(int roomId, String roomName, String type, int price, String imageUrl) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(String roomRate) {
        this.roomRate = roomRate;
    }
}
