package com.example.finalteamproject.main;

import android.app.Dialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.CalendarWidget;
import com.example.finalteamproject.CalendarWidgetList;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ItemCalendarBinding;
import com.example.finalteamproject.databinding.ItemCalendarListBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    ItemCalendarListBinding binding;

    ArrayList<CalendarVO> calendarList;

    Context context;
    CalendarActivity activity;

    String importance = "";

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public CalendarAdapter(ArrayList<CalendarVO> calendarList, Context context, CalendarActivity activity) {
        this.calendarList = calendarList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemCalendarListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(calendarList.get(position).getCalendar_importance().equals("3")){
            holder.binding.imgvImportance.setImageResource(R.drawable.importance1);
        }else if(calendarList.get(position).getCalendar_importance().equals("2")){
            holder.binding.imgvImportance.setImageResource(R.drawable.importance2);
        }else {
            holder.binding.imgvImportance.setImageResource(R.drawable.importance3);
        }
        holder.binding.tvContent.setText(calendarList.get(position).getCalendar_content());
        holder.binding.tvContent.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context);
            ItemCalendarBinding dialogBinding = ItemCalendarBinding.inflate(LayoutInflater.from(context));
            dialog.setContentView(dialogBinding.getRoot());
            dialogBinding.dateText.setText(calendarList.get(position).getCalendar_date());
            dialogBinding.content.setText(calendarList.get(position).getCalendar_content());
            try {
                RadioButton btn = (RadioButton) dialogBinding.radioGroup.getChildAt(3 - Integer.parseInt(calendarList.get(position).getCalendar_importance()));
                btn.setChecked(true);
            } catch (Exception e) {
                Log.d("TAG", "onBindViewHolder: 중요도 없음.");
            }
            dialogBinding.saveScheduleBtn.setOnClickListener(view -> {
                for (int i = 0; i < dialogBinding.radioGroup.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) dialogBinding.radioGroup.getChildAt(i);
                    if (btn.isChecked()) {
                        importance = 3 - (i) + "";
                        break;
                    }
                }
                CommonConn conn1 = new CommonConn(context, "main/updateSchedule");
                conn1.addParamMap("calendar_content", dialogBinding.content.getText().toString());
                conn1.addParamMap("calendar_importance", importance);
                conn1.addParamMap("calendar_id", calendarList.get(position).getCalendar_id());
                conn1.onExcute((isResult1, data1) -> {
                    CalendarVO vo = new Gson().fromJson(data1, CalendarVO.class);
                    if (vo != null) {
                        calendarList.set(position, vo);
                        notifyDataSetChanged();
                    }
                    dialog.dismiss();
                });
            });
            dialogBinding.cancelDialogBtn.setOnClickListener(view -> {
                CommonConn conn1 = new CommonConn(context, "main/deleteScheduleOne");
                conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                conn1.addParamMap("calendar_id", calendarList.get(position).getCalendar_id());
                conn1.onExcute((isResult1, data1) -> {
                    CalendarActivity activity = (CalendarActivity) context;
                    activity.viewCalendar();
                    HashSet<CalendarDay> updatedSet = new HashSet<>();
                    CommonConn conn = new CommonConn(context, "main/viewCalendarList");
                    conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());

                    conn.onExcute((isResult, data) -> {
                        ArrayList<CalendarVO> calendarList2 = new Gson().fromJson(data, new TypeToken<ArrayList<CalendarVO>>() {
                        }.getType());
                        if (calendarList2.size() != 0) {
                            for (int i = 0; i < calendarList2.size(); i++) {
                                String[] tempDate = calendarList2.get(i).getCalendar_date().split("-");
                                CalendarDay day = CalendarDay.from(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]) - 1, Integer.parseInt(tempDate[2]));
                                updatedSet.add(day);
                            }
                        }
                        calendarList.remove(position);
                        if (calendarList.size() == 0) {
                            changeVisibility(View.VISIBLE);
                        } else {
                            changeVisibility(View.GONE);
                        }
                    });
                    activity.updateCalendarDecorators(updatedSet);
                    notifyDataSetChanged();
                    dialog.dismiss();



                });
            });
            dialog.show();
        });



    }


    @Override
    public int getItemCount() {
        if (calendarList == null) return 0;
        return calendarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemCalendarListBinding binding;

        public ViewHolder(@NonNull ItemCalendarListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void changeVisibility(int visible) {
        CalendarActivity activity = (CalendarActivity) context;
        activity.calendarTextVisibility(visible);
    }



}
