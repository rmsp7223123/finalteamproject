package com.example.finalteamproject.chat;

public class MessageDTO {
    private int imgRes;
    private String nickname, content, time;

    public MessageDTO(int imgRes, String nickname, String content, String time) {
        this.imgRes = imgRes;
        this.nickname = nickname;
        this.content = content;
        this.time = time;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
