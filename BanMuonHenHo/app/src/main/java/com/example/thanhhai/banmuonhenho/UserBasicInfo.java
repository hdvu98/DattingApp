package com.example.thanhhai.banmuonhenho;

public class UserBasicInfo {
    String name;
    String email;
    Birthday birthday;
    Boolean gender;
    Account account;
    String phone;
    String facebook;


    public UserBasicInfo() {
        this.name = "";
        this.email = "";
        this.birthday = new Birthday();
        this.gender = false;
        this.account = new Account();
    }

    public UserBasicInfo(String name, String email, Birthday birthday, Boolean gender, Account acount) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.account = acount;
    }



}
