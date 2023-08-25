package com.example.finalteamproject.common;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CustomTextview extends AppCompatTextView {
        public static float plusTextSize = 30;//-4? -8  // 20 -4-8
    public CustomTextview(@NonNull Context context) {
        super(context);
        this.setTextSize(plusTextSize);
    }

    public CustomTextview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTextSize(plusTextSize);
    }

}
