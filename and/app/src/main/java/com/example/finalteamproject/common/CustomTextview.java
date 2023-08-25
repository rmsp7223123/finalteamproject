package com.example.finalteamproject.common;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CustomTextview extends AppCompatTextView {
        public static float plusTextSize = 50;//-4? -8  // 20 -4-8
    public CustomTextview(@NonNull Context context) {
        super(context);

        this.setTextSize(this.getTextSize() -plusTextSize);
    }

    public CustomTextview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTextSize(this.getTextSize() -plusTextSize);

    }

}
