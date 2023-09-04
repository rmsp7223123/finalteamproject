package com.example.finalteamproject.main;

import android.content.Context;

import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;

public class ChatStatus {

    public static void changeStatus(Context context) {
        CommonConn conn = new CommonConn(context, "main/changeChatStatus");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {

        });
    }
}
