package com.tutorial.travel.model;

public class RoomTypeModel {
    private int id;
    private String roomTypeName;
    private String descriptions;

    public RoomTypeModel() {}

    public RoomTypeModel(int id, String roomTypeName, String descriptions) {
        this.id = id;
        this.roomTypeName = roomTypeName;
        this.descriptions = descriptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String toString() {
        return "RoomTypeModel{" +
                "id=" + id +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", descriptions='" + descriptions + '\'' +
                '}';
    }
}
