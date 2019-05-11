package com.example.thanhhai.banmuonhenho;

import java.util.ArrayList;

public class User{
    String id;
    UserBasicInfo userBasicInfo;
    Images images;
    UserIntro userIntro;
    UserLooking userLooking;
    DatingStatus datingStatus;


    public User() {
        id = "0";
        userBasicInfo = new UserBasicInfo();



    }

    public User(String id, UserBasicInfo userBasicInfo, Images images, UserIntro userIntro, UserLooking userLooking) {
        this.id = id;
        this.userBasicInfo = userBasicInfo;
        this.images = images;
        this.userIntro = userIntro;
        this.userLooking = userLooking;
        this.datingStatus = new DatingStatus();
    }
}

