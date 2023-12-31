package com.example.finalteamproject.chat;

import java.io.Serializable;

public class MessageDTO implements Serializable {
    private int imgRes;
    private String nickname, content, time, member_id;

    private boolean isCheck;

    public MessageDTO( ) {
    }

    public MessageDTO(int imgRes, String nickname, String content, String time, String member_id, boolean isCheck) {
        this.imgRes = imgRes;
        this.nickname = nickname;
        this.content = content;
        this.time = time;
        this.member_id = member_id;
        this.isCheck = isCheck;
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

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
}
