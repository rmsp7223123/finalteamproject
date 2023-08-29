package com.example.finalteamproject;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.main.MainActivity;
import com.example.finalteamproject.main.OptionVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class GodokAlarmWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Log.d("TAG", "onUpdate: " + "확인ㅇㅇㅇㅇㅇ"+appWidgetIds);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setImageViewResource(R.id.widgetImageView, R.drawable.logo);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if ("widgetImageClicked".equals(intent.getAction())) {
            // 다이얼로그 생성
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("안부문자 보내기")
                    .setMessage("안부문자를 보내시겠습니까?")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CommonConn conn = new CommonConn(context, "godok/sendOneGodokMsg");
                            conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                            conn.onExcute((isResult, data) -> {
                            });
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            // 다이얼로그 보이기
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

}