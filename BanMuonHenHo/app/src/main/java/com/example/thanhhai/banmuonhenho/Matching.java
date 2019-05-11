package com.example.thanhhai.banmuonhenho;

public class Matching {
    String id;
    String user1;
    String user2;
    boolean isUser1Agree;
    boolean isUser2Agree;
    int status;

    public Matching(String id, String user1, String user2, int status) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.status = status;
    }

    public Matching() {
    }
}


