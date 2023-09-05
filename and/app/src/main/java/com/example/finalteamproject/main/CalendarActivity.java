package com.example.finalteamproject.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.finalteamproject.CalendarWidget;
import com.example.finalteamproject.CalendarWidgetList;
import com.example.finalteamproject.ChangeStatusBar;
import com.example.finalteamproject.Login.CustomProgressDialog;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityCalendarBinding;
import com.example.finalteamproject.databinding.DialogAddScheduleBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    ActivityCalendarBinding binding;
    private String selectedDate = "";

    String importance = "";

    CalendarAdapter adapter;

    ArrayList<CalendarVO> calendarList = new ArrayList<>();

    Dialog dialog;

    DialogAddScheduleBinding dialogBinding;
    ArrayList<CalendarVO> calendarList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new CalendarAdapter(calendarList, this, this);
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
                    if (dialogBinding.lessBtn.isChecked()) {
                        importance = "1";
                        break;
                    } else if (dialogBinding.middleBtn.isChecked()) {
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

                        ArrayList<CalendarVO> list = new Gson().fromJson(data1, new TypeToken<ArrayList<CalendarVO>>() {
                        }.getType());
                        adapter.calendarList = list;

                        setRemoteView();
//                        views.setTextViewText(R.id.tv_today, "abcd");

                        if (list.size() == 0) {
                            binding.emptyText.setVisibility(View.VISIBLE);
                        } else {
                            binding.emptyText.setVisibility(View.GONE);
                        }
                        viewCalendar();
                        dialog.dismiss();
                    });
                });

//                AppWidgetManager appWidgetManager =
//                        getSystemService(AppWidgetManager.class);
//                ComponentName myProvider =
//                        new ComponentName(this, CalendarWidget.class);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    if (appWidgetManager.isRequestPinAppWidgetSupported()) {
//                        // Create the PendingIntent object only if your app needs to be notified
//                        // that the user allowed the widget to be pinned. Note that, if the pinning
//                        // operation fails, your app isn't notified.
//                        Intent pinnedWidgetCallbackIntent = new Intent(this, CalendarWidget.class );
//
//                        // Configure the intent so that your app's broadcast receiver gets
//                        // the callback successfully. This callback receives the ID of the
//                        // newly-pinned widget (EXTRA_APPWIDGET_ID).
//                        PendingIntent successCallback = PendingIntent.getBroadcast(this, 0,
//                                pinnedWidgetCallbackIntent, PendingIntent.FLAG_MUTABLE);
//
//                        appWidgetManager.requestPinAppWidget(myProvider, null, successCallback);
//                    }
//                }


                adapter.notifyDataSetChanged();
            }
        });
        dialogBinding.cancelDialogBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    public void setRemoteView() {
        Log.d("package", "setRemoteView: "+getPackageName());
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.calendar_widget);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(date);
        views.setTextViewText(R.id.tv_today, getTime);

        ArrayList<CalendarWidgetList> arr = new ArrayList<>();
        arr.add(new CalendarWidgetList(R.id.ln_1, R.id.imgv_1, R.id.tv_1, R.id.tv_day1));
        arr.add(new CalendarWidgetList(R.id.ln_2, R.id.imgv_2, R.id.tv_2, R.id.tv_day2));
        arr.add(new CalendarWidgetList(R.id.ln_3, R.id.imgv_3, R.id.tv_3, R.id.tv_day3));

        try {

            CommonConn conn = new CommonConn(this, "main/widgetSchedule");
            conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            conn.onExcute((isResult, data) -> {
                List<CalendarVO> list = new Gson().fromJson(data, new TypeToken<List<CalendarVO>>() {}.getType());

                if (list.size() == 0) {
                    views.setViewVisibility(R.id.rl, View.VISIBLE);
                    views.setViewVisibility(R.id.ln, View.GONE);
                } else {
                    views.setViewVisibility(R.id.rl, View.GONE);
                    views.setViewVisibility(R.id.ln, View.VISIBLE);
                    views.setViewVisibility(R.id.ln_1, View.VISIBLE);
                    views.setViewVisibility(R.id.ln_2, View.VISIBLE);
                    views.setViewVisibility(R.id.ln_3, View.VISIBLE);
                    for (int i = 0; i < list.size(); i++) {
                        if(list.get(i).getCalendar_importance().equals("3")){
                            views.setImageViewResource(arr.get(i).getImgv(), R.drawable.importance1);
                        }else if(list.get(i).getCalendar_importance().equals("2")){
                            views.setImageViewResource(arr.get(i).getImgv(), R.drawable.importance2);
                        }else {
                            views.setImageViewResource(arr.get(i).getImgv(), R.drawable.importance3);
                        }
                        if(list.get(i).getCalendar_content().length()>6){
                            views.setTextViewText(arr.get(i).getTv_content(), list.get(i).getCalendar_content().substring(0, 6)+"...");
                        }else {
                            views.setTextViewText(arr.get(i).getTv_content(), list.get(i).getCalendar_content());
                        }
                        views.setTextViewText(arr.get(i).getTv_day(), list.get(i).getCalendar_date());

                        if(list.size()==2){
                            views.setViewVisibility(R.id.fl2, View.GONE);
                            views.setViewVisibility(R.id.ln_3, View.GONE);
                        }else if(list.size()==1){
                            views.setViewVisibility(R.id.fl1, View.GONE);
                            views.setViewVisibility(R.id.ln_2, View.GONE);
                            views.setViewVisibility(R.id.fl2, View.GONE);
                            views.setViewVisibility(R.id.ln_3, View.GONE);
                        }

                        Intent intent = new Intent(this, CalendarActivity.class);
                        intent.putExtra("member_id", CommonVar.logininfo.getMember_id());
                        intent.putExtra("calendar_id", list.get(i).getCalendar_id());
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);

                        views.setOnClickPendingIntent(arr.get(i).getLn(), pendingIntent);
                    }
                }
                ComponentName componentname = new ComponentName(this, CalendarWidget.class);
                AppWidgetManager appwidgetmanager = AppWidgetManager.getInstance(this);
                appwidgetmanager.updateAppWidget(componentname, views);

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewCalendar() {

        CustomProgressDialog dialog = new CustomProgressDialog(this);
        dialog.show();

        HashSet<CalendarDay> set = new HashSet<>();
        CommonConn conn = new CommonConn(this, "main/viewCalendarList");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {
             calendarList2 = new Gson().fromJson(data, new TypeToken<ArrayList<CalendarVO>>() {
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

            if (set.size() > 0) {
                binding.calendarView.addDecorator(new DateDecorator(Color.RED, set));
            } else {
                binding.calendarView.addDecorator(new DateDecorator(Color.TRANSPARENT, set));
            }

            dialog.dismiss();
        });
    }

    public void selectItem(CalendarDay date) {
        Calendar selectedCalendar = date.getCalendar();
        int year = selectedCalendar.get(Calendar.YEAR);
        int month = selectedCalendar.get(Calendar.MONTH) + 1;
        int day = selectedCalendar.get(Calendar.DAY_OF_MONTH);
        String modifiedDate = year + "-" + month + "-" + day;
        selectedDate = modifiedDate;
        adapter.calendarList.clear();
        CommonConn conn = new CommonConn(getApplicationContext(), "main/viewScheduleOne");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.addParamMap("calendar_date", modifiedDate);
        conn.onExcute((isResult, data) -> {
            ArrayList<CalendarVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<CalendarVO>>() {
            }.getType());
            adapter.calendarList = list;
            if (list.size() == 0) {
                binding.emptyText.setVisibility(View.VISIBLE);
            } else {
                binding.emptyText.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        });

    }

    public void calendarTextVisibility(int visible) {
        binding.emptyText.setVisibility(visible);
    }

    public void updateCalendarDecorators(HashSet<CalendarDay> set) {
        binding.calendarView.removeDecorators();
        if (set.size() > 0) {
            binding.calendarView.addDecorator(new DateDecorator(Color.RED, set));
        } else {
            binding.calendarView.addDecorator(new DateDecorator(Color.TRANSPARENT, set));
        }
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.calendar_widget);

    }


}