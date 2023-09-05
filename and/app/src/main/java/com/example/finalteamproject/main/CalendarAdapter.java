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

    public void setRemoteView(){
        RemoteViews views = new RemoteViews("com.example.finalteamproject", R.layout.calendar_widget);
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

            CommonConn conn = new CommonConn(context, "main/widgetSchedule");
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

                        Intent intent = new Intent(context, CalendarActivity.class);
                        intent.putExtra("member_id", CommonVar.logininfo.getMember_id());
                        intent.putExtra("calendar_id", list.get(i).getCalendar_id());
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);

                        views.setOnClickPendingIntent(arr.get(i).getLn(), pendingIntent);
                    }
                }
                ComponentName componentname = new ComponentName(context, CalendarWidget.class);
                AppWidgetManager appwidgetmanager = AppWidgetManager.getInstance(context);
                appwidgetmanager.updateAppWidget(componentname, views);

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        setRemoteView();
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
                    setRemoteView();
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
