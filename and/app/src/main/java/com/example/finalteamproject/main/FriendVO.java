package com.example.finalteamproject.main;

import java.io.Serializable;

public class FriendVO implements Serializable {
    String member_id, friend_id, member_nickname, member_profileimg, time, content;
    boolean isCheck;

    public FriendVO() {

    }

    public FriendVO(String member_id, String friend_id, String member_nickname, String member_profileimg, String time, String content, boolean isCheck) {
        this.member_id = member_id;
        this.friend_id = friend_id;
        this.member_nickname = member_nickname;
        this.member_profileimg = member_profileimg;
        this.time = time;
        this.content = content;
        this.isCheck = isCheck;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }

    public String getMember_profileimg() {
        return member_profileimg;
    }

    public void setMember_profileimg(String member_profileimg) {
        this.member_profileimg = member_profileimg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
