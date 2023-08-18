package com.example.finalteamproject.chat;

import java.io.Serializable;

public class ChatPhotoGalleryDTO implements Serializable {

    String img;

    String[] imgSubs;

    public ChatPhotoGalleryDTO(String img) {
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
