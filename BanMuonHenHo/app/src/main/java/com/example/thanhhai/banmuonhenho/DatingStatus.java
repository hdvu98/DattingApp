package com.example.thanhhai.banmuonhenho;

public class DatingStatus {
    String currentMatching;
    String currentRoom;
    String currentLover;

    public DatingStatus(String currentMatching, String currentRoom) {
        this.currentMatching = currentMatching;
        this.currentRoom = currentRoom;
    }

    public DatingStatus() {
        currentRoom = "";
        currentMatching="";
        currentLover="";
    }
}
