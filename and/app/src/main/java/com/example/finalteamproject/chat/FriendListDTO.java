package com.example.finalteamproject.chat;

public class FriendListDTO {
    private int imgRes;
    private String nickname;

    public FriendListDTO(int imgRes, String nickname) {
        this.imgRes = imgRes;
        this.nickname = nickname;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
