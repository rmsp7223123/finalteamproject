package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityChangeFontBinding;

public class ChangeFontActivity extends AppCompatActivity {

    ActivityChangeFontBinding binding;

    int themeResId = R.style.FontSmall; // 기본값은 작게

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeFontBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        binding.tvFontSize.setOnClickListener(view -> {
            String[] dialog_item = {"작게", "중간", "크게"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("글씨 크기 변경");
            builder.setSingleChoiceItems(dialog_item, -1, (dialog, i) -> {
                binding.tvFontSize.setText(dialog_item[i]);
                // 글씨 크기 변경 추가하기
                if (i == 0) {
                    themeResId = R.style.FontSmall;
                } else if (i == 1) {
                    themeResId = R.style.FontMiddle;
                } else if (i == 2) {
                    themeResId = R.style.FontLarge;
                }

            });
            builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 테마 변경 및 액티비티 재시작
                    setTheme(themeResId);
                    recreate();
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
        // 글씨 크기 조절, 글씨 색깔 변경 dialog로 띄워서 처리 하기
    }

}