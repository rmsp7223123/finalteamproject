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
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityChangeFontBinding;
import com.example.finalteamproject.main.OptionVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ChangeFontActivity extends AppCompatActivity {

    ActivityChangeFontBinding binding;

    int savedItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeFontBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        changeFontSize();
        binding.tvFontSize.setOnClickListener(view -> {
            String[] dialog_item = {"작게", "중간", "크게"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("글씨 크기 변경");
            builder.setSingleChoiceItems(dialog_item, -1, (dialog, i) -> {
                binding.tvFontSize.setText(dialog_item[i]);
                savedItem = i;

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
                    CommonConn conn = new CommonConn(ChangeFontActivity.this, "setting/updateFont");
                    conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                    conn.addParamMap("option_font_size", dialog_item[savedItem]);
                    conn.onExcute((isResult, data) -> {
                        if(savedItem == 0) {
                            // 작게
                        } else if (savedItem == 1) {
                            // 중간
                        } else {
                            // 크게
                        }
                        recreate();
                        dialog.dismiss();
                        changeFontSize();
                    });
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
        // 글씨 크기 조절, 글씨 색깔 변경 dialog로 띄워서 처리 하기
    }

    public void changeFontSize() {
        CommonConn conn1 = new CommonConn(this, "setting/viewOption");
        conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn1.onExcute((isResult, data) -> {
            ArrayList<OptionVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<OptionVO>>(){}.getType());
            binding.tvFontSize.setText(list.get(0).getOption_font_size());
        });
    }

}