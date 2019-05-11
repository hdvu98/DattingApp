package com.example.thanhhai.banmuonhenho;

public class Room2 {
    private String roomID;
    private String date;

    public Room2(String roomID, String date) {
        this.roomID = roomID;
        this.date = date;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
