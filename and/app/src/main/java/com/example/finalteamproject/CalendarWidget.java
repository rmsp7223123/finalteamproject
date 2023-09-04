package com.example.finalteamproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.view.View;

import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.main.CalendarActivity;
import com.example.finalteamproject.main.CalendarVO;
import com.example.finalteamproject.main.OptionVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarWidget extends AppWidgetProvider {

     public static final String ACTION_WIDGET_CLICKED = "com.example.finalteamproject.WIDGET_CLICKED";
    private static final long UPDATE_INTERVAL = 5000; // 5 second

    private final Handler handler = new Handler();
    private Runnable updateTask = null;

//    private ComponentName componentName = null;

    int[] appWidgetIds = null;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        this.appWidgetIds = appWidgetIds;
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
//        Log.d("update", "onEnabled: test");
//        scheduleUpdateTask(context);
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.calendar_widget);
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

                    for (int i = 0; i < list.size(); i++) {
                        if(list.get(i).getCalendar_importance().equals("3")){
                            views.setImageViewResource(arr.get(i).imgv, R.drawable.importance1);
                        }else if(list.get(i).getCalendar_importance().equals("2")){
                            views.setImageViewResource(arr.get(i).imgv, R.drawable.importance2);
                        }else {
                            views.setImageViewResource(arr.get(i).imgv, R.drawable.importance3);
                        }
                        if(list.get(i).getCalendar_content().length()>5){
                            views.setTextViewText(arr.get(i).tv_content, list.get(i).getCalendar_content().substring(0, 5)+"...");
                        }else {
                            views.setTextViewText(arr.get(i).tv_content, list.get(i).getCalendar_content());
                        }
                        views.setTextViewText(arr.get(i).tv_day, list.get(i).getCalendar_date());

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
                        intent.setAction(ACTION_WIDGET_CLICKED);
                        intent.putExtra("member_id", CommonVar.logininfo.getMember_id());
                        intent.putExtra("calendar_id", list.get(i).getCalendar_id());
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);

                        views.setOnClickPendingIntent(arr.get(i).ln, pendingIntent);
                    }
                }

                appWidgetManager.updateAppWidget(appWidgetId, views);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnabled(Context context) {
//        Log.d("update", "onEnabled: test");
//        scheduleUpdateTask(context);
    }

    @Override
    public void onDisabled(Context context) {
        // Remove the update task when the last widget is disabled
//        cancelUpdateTask();
    }

//    private void scheduleUpdateTask(Context context) {
//        if (updateTask == null) {
//            updateTask = new Runnable() {
//                @Override
//                public void run() {
//                    // Update the widget
//                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
////                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, CalendarWidget.class));
//                    for (int appWidgetId : appWidgetIds) {
//                        updateWidget(context, appWidgetManager, appWidgetId);
//                    }
//
//
//                    // Reschedule the task
//                    handler.postDelayed(this, UPDATE_INTERVAL);
//                }
//            };
//        }
//
//        handler.post(updateTask);
//    }

//    private void cancelUpdateTask() {
//        if (updateTask != null) {
//            handler.removeCallbacks(updateTask);
//            updateTask = null;
//        }
//    }
}
