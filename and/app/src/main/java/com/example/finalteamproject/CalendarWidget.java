package com.example.finalteamproject;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.example.finalteamproject.board.FavorBoardVO;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.main.CalendarActivity;
import com.example.finalteamproject.main.CalendarVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class CalendarWidget extends AppWidgetProvider {

    private static final String ACTION_WIDGET_CLICKED = "com.example.finalteamproject.WIDGET_CLICKED";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
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

            CommonConn conn = new CommonConn(context, "main/widgetSchedule");
            conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            conn.onExcute((isResult, data) -> {
                List<CalendarVO> list = new Gson().fromJson(data, new TypeToken<List<CalendarVO>>(){}.getType());
                if(list.size()==0){
                    views.setViewVisibility(R.id.rl, View.VISIBLE);
                    views.setViewVisibility(R.id.ln, View.GONE);
                }else {
                    views.setViewVisibility(R.id.rl, View.GONE);
                    views.setViewVisibility(R.id.ln, View.VISIBLE);

                    for (int i = 0; i < list.size(); i++) {
                        if(list.get(i).getCalendar_importance().equals("1")){
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
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE);

                        views.setOnClickPendingIntent(arr.get(i).ln, pendingIntent);

                    }




                }


                appWidgetManager.updateAppWidget(appWidgetId, views);

            });




        }
    }

//    private List<CalendarVO> getList(Context context){
//        List<CalendarVO> list = null;
//        CommonConn conn = new CommonConn(context, "main/widgetSchedule");
//        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
//        conn.onExcute((isResult, data) -> {
//            list = new Gson().fromJson(data, new TypeToken<List<CalendarVO>>(){}.getType());
//        });
//        return list;
//    }


    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}