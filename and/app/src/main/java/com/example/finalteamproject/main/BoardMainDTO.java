package com.example.finalteamproject.main;

import android.widget.ImageView;
import android.widget.com.example.finalteamproject.common.CustomTextview;

public class BoardMainDTO {

    int imgv_icon, imgv_move;
    String tv_board_name;

    public BoardMainDTO(int imgv_icon, int imgv_move, String tv_board_name) {
        this.imgv_icon = imgv_icon;
        this.imgv_move = imgv_move;
        this.tv_board_name = tv_board_name;
    }

    public int getImgv_icon() {
        return imgv_icon;
    }

    public void setImgv_icon(int imgv_icon) {
        this.imgv_icon = imgv_icon;
    }

    public int getImgv_move() {
        return imgv_move;
    }

    public void setImgv_move(int imgv_move) {
        this.imgv_move = imgv_move;
    }

    public String getTv_board_name() {
        return tv_board_name;
    }

    public void setTv_board_name(String tv_board_name) {
        this.tv_board_name = tv_board_name;
    }
}
