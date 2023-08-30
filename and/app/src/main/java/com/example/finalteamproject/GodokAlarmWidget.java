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

    private static final String ACTION_WIDGET_CLICKED = "com.example.finalteamproject.WIDGET_CLICKED";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setImageViewResource(R.id.widgetImageView, R.drawable.logo);

            // 액션을 설정한 PendingIntent 생성
            Intent clickIntent = new Intent(context, GodokAlarmWidget.class);
            clickIntent.setAction(ACTION_WIDGET_CLICKED);
            clickIntent.putExtra("member_id", CommonVar.logininfo.getMember_id());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_IMMUTABLE);

            // 위젯 뷰에 클릭 이벤트와 PendingIntent 연결
            views.setOnClickPendingIntent(R.id.widgetImageView, pendingIntent);

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
        String member_id = intent.getStringExtra("member_id");
        Toast.makeText(context, "확인용ㅇㅇㅇㅇㅇ", Toast.LENGTH_SHORT).show();
        if (intent.getAction() != null && intent.getAction().equals(ACTION_WIDGET_CLICKED) && member_id != null) {
            if (CommonVar.logininfo != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("안부문자 보내기")
                        .setMessage("안부문자를 보내시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CommonConn conn = new CommonConn(context, "godok/sendOneGodokMsg");
                                conn.addParamMap("member_id", member_id);
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
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(context, "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}