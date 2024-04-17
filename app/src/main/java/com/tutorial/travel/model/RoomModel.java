package com.tutorial.travel.model;

public class RoomModel {
    private int id;
    private String roomName;
    private double price;
    private String roomImage;
    private String roomStatus;
    private String description;
    private int hotelId;
    private int roomTypeId;

    public RoomModel() {}

    public RoomModel(int id, String roomName, double price, String roomImage, String roomStatus, int hotelId, int roomTypeId, String description) {
        this.id = id;
        this.roomName = roomName;
        this.price = price;
        this.roomImage = roomImage;
        this.roomStatus = roomStatus;
        this.description = description;
        this.hotelId = hotelId;
        this.roomTypeId = roomTypeId;
    }

    public RoomModel(String roomName, double price, String roomImage, String description) {
        this.roomName = roomName;
        this.price = price;
        this.roomImage = roomImage;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(String roomImage) {
        this.roomImage = roomImage;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RoomModel{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                ", price=" + price +
                ", roomImage='" + roomImage + '\'' +
                ", roomStatus='" + roomStatus + '\'' +
                ", description='" + description + '\'' +
                ", hotelId=" + hotelId +
                ", roomTypeId=" + roomTypeId +
                '}';
    }
}
