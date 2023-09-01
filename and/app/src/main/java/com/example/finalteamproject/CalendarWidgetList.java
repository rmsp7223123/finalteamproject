package com.example.finalteamproject;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CalendarWidgetList {

    int ln, imgv, tv_content, tv_day;

    public int getLn() {
        return ln;
    }

    public void setLn(int ln) {
        this.ln = ln;
    }

    public int getImgv() {
        return imgv;
    }

    public void setImgv(int imgv) {
        this.imgv = imgv;
    }

    public int getTv_content() {
        return tv_content;
    }

    public void setTv_content(int tv_content) {
        this.tv_content = tv_content;
    }

    public int getTv_day() {
        return tv_day;
    }

    public void setTv_day(int tv_day) {
        this.tv_day = tv_day;
    }

    public CalendarWidgetList(int ln, int imgv, int tv_content, int tv_day) {
        this.ln = ln;
        this.imgv = imgv;
        this.tv_content = tv_content;
        this.tv_day = tv_day;
    }
}
