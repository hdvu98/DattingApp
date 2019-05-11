package com.example.thanhhai.banmuonhenho;

public class SaveLogin {
    String idPhone;
    String idUser;
    boolean isOnLogin;

    public SaveLogin(String idPhone, String idUser, boolean isOnLogin) {
        this.idPhone = idPhone;
        this.idUser = idUser;
        this.isOnLogin = isOnLogin;
    }

    public SaveLogin() {
    }
}
