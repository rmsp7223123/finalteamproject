package com.example.finalteamproject.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.finalteamproject.ChangeStatusBar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityCalendarBinding;
import com.example.finalteamproject.databinding.DialogAddScheduleBinding;
import com.google.android.gms.common.internal.service.Common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.intellij.lang.annotations.JdkConstants;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;

public class CalendarActivity extends AppCompatActivity {
    ActivityCalendarBinding binding;
    private String selectedDate = "";

    String importance = "";

    CalendarAdapter adapter;

    ArrayList<CalendarVO> calendarList = new ArrayList<>();

    Dialog dialog;

    DialogAddScheduleBinding dialogBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new CalendarAdapter(calendarList, this);
        viewCalendar();
        new ChangeStatusBar().changeStatusBarColor(this);
        binding.calendarView.setSelectedDate(CalendarDay.today());
        selectItem(CalendarDay.today());
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

        binding.calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectItem(date);
            }

        });

    }

    private void showScheduleDialog() {
        dialog = new Dialog(this);
        dialogBinding = DialogAddScheduleBinding.inflate(LayoutInflater.from(this));
        dialog.setContentView(dialogBinding.getRoot());
        dialogBinding.dateText.setText(selectedDate);
        dialogBinding.saveScheduleBtn.setOnClickListener(v -> {
            if (dialogBinding.content.getText().toString().isEmpty()) {
                Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else if (dialogBinding.radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "중요도를 체크해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < dialogBinding.radioGroup.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) dialogBinding.radioGroup.getChildAt(i);
                    if (btn.isChecked() == true && btn.getText().toString().equals("적음")) {
                        importance = "1";
                        break;
                    } else if(btn.isChecked() == true && btn.getText().toString().equals("중간")) {
                        importance = "2";
                        break;
                    } else {
                        importance = "3";
                        break;
                    }
                }
                CommonConn conn = new CommonConn(this, "main/addSchedule");
                conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                conn.addParamMap("calendar_content", dialogBinding.content.getText().toString());
                conn.addParamMap("calendar_date", dialogBinding.dateText.getText().toString());
                conn.addParamMap("calendar_importance", importance);
                conn.onExcute((isResult, data) -> {
                    CommonConn conn1 = new CommonConn(this, "main/viewScheduleOne");
                    conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                    conn1.addParamMap("calendar_date", selectedDate);
                    conn1.onExcute((isResult1, data1) -> {
                        ArrayList<CalendarVO> list = new Gson().fromJson(data1, new TypeToken<ArrayList<CalendarVO>>(){}.getType());
                        adapter.calendarList = list;
                        if(list.size() == 0) {
                            binding.emptyText.setVisibility(View.VISIBLE);
                        } else {
                            binding.emptyText.setVisibility(View.GONE);
                        }
                        viewCalendar();
                        dialog.dismiss();
                    });
                });
                adapter.notifyDataSetChanged();
            }
        });
        dialogBinding.cancelDialogBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void viewCalendar() {
        HashSet<CalendarDay> set = new HashSet<>();
        CommonConn conn = new CommonConn(this, "main/viewCalendarList");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {
            ArrayList<CalendarVO> calendarList2 = new Gson().fromJson(data, new TypeToken<ArrayList<CalendarVO>>() {
            }.getType());
            calendarList = calendarList2;
            binding.recvSchedule.setAdapter(adapter);
            binding.recvSchedule.setLayoutManager(new LinearLayoutManager(this));

            if (calendarList2.size() != 0) {
                for (int i = 0; i < calendarList2.size(); i++) {
                    String[] tempDate = calendarList2.get(i).getCalendar_date().split("-");
                    CalendarDay day = CalendarDay.from(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]) - 1, Integer.parseInt(tempDate[2]));
                    set.add(day);

                }
            } else {
            }

            binding.calendarView.addDecorator(new DateDecorator(Color.RED, set));
        });
    }

    public void selectItem(CalendarDay date) {
        Calendar selectedCalendar = date.getCalendar();
        int year = selectedCalendar.get(Calendar.YEAR);
        int month = selectedCalendar.get(Calendar.MONTH  )+1;
        int day = selectedCalendar.get(Calendar.DAY_OF_MONTH);
        String modifiedDate = year + "-" + month + "-" + day;
        selectedDate = modifiedDate;
        adapter.calendarList.clear();
        CommonConn conn = new CommonConn(getApplicationContext(), "main/viewScheduleOne");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.addParamMap("calendar_date", modifiedDate);
        conn.onExcute((isResult, data) -> {
            ArrayList<CalendarVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<CalendarVO>>(){}.getType());
            adapter.calendarList = list;
            if(list.size() == 0) {
                binding.emptyText.setVisibility(View.VISIBLE);
            } else {
                binding.emptyText.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        });

    }



}