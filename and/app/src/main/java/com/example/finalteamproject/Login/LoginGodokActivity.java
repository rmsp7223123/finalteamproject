package com.example.finalteamproject.Login;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityLoginGodokBinding;
import com.google.android.play.core.integrity.b;

import java.util.regex.Pattern;

public class LoginGodokActivity extends AppCompatActivity {

    ActivityLoginGodokBinding binding;

    String[] list = {"아들", "딸", "며느리", "사위", "손자", "손녀", "친구", "이웃", "기타"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginGodokBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new HideActionBar().hideActionBar(this);

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", binding.edtPhone.getText().toString())){
                    binding.tvWrong.setVisibility(View.VISIBLE);
                }else {
                    binding.tvWrong.setVisibility(View.INVISIBLE);
                }
            }
        });

        Spinner relationSpinner = binding.spnRelation;
        ArrayAdapter relationAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        relationSpinner.setAdapter(relationAdapter);
        binding.spnRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int i, long id) {
                if(list[i].equals("기타")){
                    binding.edtRelation.setVisibility(View.VISIBLE);
                }else {
                    binding.edtRelation.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.cvDone.setOnClickListener(v -> {
                Intent intent = new Intent(this, LoginDoneActivity.class);
                startActivity(intent);
        });


    }
}