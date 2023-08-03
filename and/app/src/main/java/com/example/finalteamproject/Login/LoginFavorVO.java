package com.example.finalteamproject.Login;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LoginFavorVO {

    String name;
    boolean bl;
    LinearLayout ln;
    ImageView imgv;

    int drawable, drawableSelect;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBl() {
        return bl;
    }

    public void setBl(boolean bl) {
        this.bl = bl;
    }

    public LinearLayout getLn() {
        return ln;
    }

    public void setLn(LinearLayout ln) {
        this.ln = ln;
    }

    public ImageView getImgv() {
        return imgv;
    }

    public void setImgv(ImageView imgv) {
        this.imgv = imgv;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getDrawableSelect() {
        return drawableSelect;
    }

    public void setDrawableSelect(int drawableSelect) {
        this.drawableSelect = drawableSelect;
    }

    public LoginFavorVO(String name, boolean bl, LinearLayout ln, ImageView imgv, int drawable, int drawableSelect) {
        this.name = name;
        this.bl = bl;
        this.ln = ln;
        this.imgv = imgv;
        this.drawable = drawable;
        this.drawableSelect = drawableSelect;
    }
}
