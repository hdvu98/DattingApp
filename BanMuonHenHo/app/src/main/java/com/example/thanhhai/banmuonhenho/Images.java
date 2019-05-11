package com.example.thanhhai.banmuonhenho;

import java.util.ArrayList;

public class Images {
    String avatar;
    ArrayList<String> linkImages;

    public Images(String avatar, ArrayList<String> linkImages) {
        this.avatar = avatar;
        this.linkImages = linkImages;
    }

    public Images() {
        avatar = "";
        linkImages = new ArrayList<>();
    }
}
