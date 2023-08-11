package com.example.finalteamproject.common;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;

import com.example.finalteamproject.R;

public class ProgressDialog extends Dialog {

    public ProgressDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 다이얼로그 제목 안 보이게
        setContentView(R.layout.loading_dialog);
    }

}
