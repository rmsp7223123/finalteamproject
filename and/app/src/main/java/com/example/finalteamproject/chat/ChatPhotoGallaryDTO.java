package com.example.finalteamproject.chat;

import java.io.Serializable;

public class ChatPhotoGallaryDTO implements Serializable {

    String img;

    String[] imgSubs;

    public ChatPhotoGallaryDTO(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String[] getImgSubs() {
        return imgSubs;
    }

    public void setImgSubs(String[] imgSubs) {
        this.imgSubs = imgSubs;
    }
}
