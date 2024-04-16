package com.tutorial.travel.model;

public class Room {
    private String id;
    private String roomName;
    private String roomType;
    private String roomRate;

    public Room(String id, String roomName, String roomType, String roomRate) {
        this.id = id;
        this.roomName = roomName;
        this.roomType = roomType;
        this.roomRate = roomRate;
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
