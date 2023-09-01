package com.example.finalteamproject.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.finalteamproject.ChangeStatusBar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityCalendarBinding;
import com.example.finalteamproject.databinding.DialogAddScheduleBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;

public class CalendarActivity extends AppCompatActivity {
    ActivityCalendarBinding binding;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new ChangeStatusBar().changeStatusBarColor(this);
        binding.calendarView.setSelectedDate(CalendarDay.today());
        int year = binding.calendarView.getSelectedDate().getYear();
        int month = binding.calendarView.getSelectedDate().getMonth() + 1;
        int day = binding.calendarView.getSelectedDate().getDay();
        selectedDate = year + "-" + month + "-" + day;






        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        binding.addScheduleBtn.setOnClickListener(v -> {
            showScheduleDialog();
        });

    }

    private void showScheduleDialog() {
        Dialog dialog = new Dialog(this);
        DialogAddScheduleBinding dialogBinding = DialogAddScheduleBinding.inflate(LayoutInflater.from(this));
        dialog.setContentView(dialogBinding.getRoot());
        dialogBinding.saveScheduleBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialogBinding.cancelDialogBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }
}