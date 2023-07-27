package com.example.finalteamproject.Login;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LoginFavorVO {

    boolean name;
    LinearLayout ln;
    ImageView imgv;

    int drawable, drawableSelect;

    public Boolean getName() {
        return name;
    }

    public void setName(Boolean name) {
        this.name = name;
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

    public LoginFavorVO(Boolean name, LinearLayout ln, ImageView imgv, int drawable, int drawableSelect) {
        this.name = name;
        this.ln = ln;
        this.imgv = imgv;
        this.drawable = drawable;
        this.drawableSelect = drawableSelect;
    }
}
