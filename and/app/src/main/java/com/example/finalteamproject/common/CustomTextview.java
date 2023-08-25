package com.example.finalteamproject.common;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CustomTextview extends AppCompatTextView {

    public CustomTextview(@NonNull Context context) {
        super(context);
    }

    public CustomTextview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
<<<<<<< HEAD
        this.setTextSize(plusTextSize);
=======
>>>>>>> parent of 9b527d3 (커스텀 텍스트뷰 기능 추가)
    }

    public CustomTextview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
