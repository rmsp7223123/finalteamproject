package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityChangeFontBinding;

public class ChangeFontActivity extends AppCompatActivity {

    ActivityChangeFontBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeFontBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        binding.tvFontColor.setOnClickListener(v -> {
            String[] dialog_item = {"검은색", "파란색", "빨간색", "노란색", "초록색"};
            int[] colors = {Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("글씨 색상 변경");

            FontColorArrayAdapter adapter = new FontColorArrayAdapter(this,dialog_item, colors);
            builder.setSingleChoiceItems(adapter, 3, (dialog, i) -> {
                binding.tvFontColor.setText(dialog_item[i]);
            });
            builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    binding.tvFontColor.setText(dialog_item[3]);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
        // 글씨 크기 조절, 글씨 색깔 변경 dialog로 띄워서 처리 하기
    }
}