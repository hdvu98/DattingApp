package com.example.thanhhai.banmuonhenho;

public class Account {
    String id;
    String username;
    String password;
    boolean isUser;

    public Account() {

    }

    public Account(String id, String username, String password, boolean isUser) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isUser = isUser;
    }
}
