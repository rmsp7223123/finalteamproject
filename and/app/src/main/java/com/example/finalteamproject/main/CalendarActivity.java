package com.example.finalteamproject.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RadioButton;

import com.example.finalteamproject.ChangeStatusBar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityCalendarBinding;
import com.example.finalteamproject.databinding.DialogAddScheduleBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;

public class CalendarActivity extends AppCompatActivity {
    ActivityCalendarBinding binding;
    private String selectedDate = "";

    String importance = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewCalendar();
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
        dialogBinding.dateText.setText(selectedDate);
        dialogBinding.saveScheduleBtn.setOnClickListener(v -> {
            for(int i= 0 ; i< dialogBinding.radioGroup.getChildCount(); i ++){
                RadioButton btn = (RadioButton) dialogBinding.radioGroup.getChildAt(i);
                if(btn.isChecked() == true){
                   importance = btn.getText().toString();
                   break;
                }
            }
            CommonConn conn = new CommonConn(this, "main/addSchedule");
            conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            conn.addParamMap("calendar_content", dialogBinding.content.getText().toString());
            conn.addParamMap("calendar_date", dialogBinding.dateText.getText().toString());
            conn.addParamMap("calendar_importance", importance);
            conn.onExcute((isResult, data) -> {
                dialog.dismiss();
            });
        });
        dialogBinding.cancelDialogBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void viewCalendar() {
        CommonConn conn = new CommonConn(this, "main.viewCalendarList");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {

        });
    }
}