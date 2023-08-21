package com.example.finalteamproject.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.chat.MessageChatActivity;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.MemberVO;
import com.example.finalteamproject.databinding.ItemAlarmHistoryBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainAlarmHistoryAdapter extends RecyclerView.Adapter<MainAlarmHistoryAdapter.ViewHolder> {

    ItemAlarmHistoryBinding binding;
    ArrayList<AlarmVO> list;

    Context context;

    ArrayList<AlarmVO> list2;
    ArrayList<FriendVO> friendList;

    public MainAlarmHistoryAdapter(ArrayList<AlarmVO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemAlarmHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlarmVO alarm = list.get(position);
        holder.binding.tvText.setText(list.get(position).getAlarm_content());
        final int idx = position;
        holder.binding.cvAlarm.setOnClickListener(v -> {
            if (alarm.getAlarm_content().contains("친구신청")) {
                CommonConn conn = new CommonConn(context, "main/viewAlarm");
                conn.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
                conn.onExcute((isResult, data) -> {
                    list2 = new Gson().fromJson(data, new TypeToken<ArrayList<AlarmVO>>() {
                    }.getType());
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("친구 추가 요청");
                    builder.setMessage(list2.get(holder.getAdapterPosition()).getMember_id() + "님의 친구 추가 요청을 수락하시겠습니까?");
                    builder.setPositiveButton("거절", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteAlarm(idx);
                           // selectAlarmList();//?
                           // updateAlarm();//?
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("수락", (dialog, which) -> {
                        CommonConn conn1 = new CommonConn(context, "main/addFriend");
                        conn1.addParamMap("member_id", list2.get(idx).getMember_id());
                        conn1.addParamMap("friend_id", CommonVar.logininfo.getMember_id());
                        conn1.onExcute((isResult1, data1) -> {
                            deleteAlarm(idx);
                          //  selectAlarmList();
                          //  updateAlarm();
                           // notifyDataSetChanged();
                        });


                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                });
            } else if (alarm.getAlarm_content().contains("메시지")) {
                Intent intent = new Intent(context, MessageChatActivity.class);
                CommonConn conn2 = new CommonConn(context, "main/deleteAlarm");
                conn2.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
                conn2.addParamMap("nickname", list.get(idx).getAlarm_content().substring(0, list.get(idx).getAlarm_content().indexOf("님")));
                conn2.addParamMap("alarm_content2", "메시지를");
                CommonConn conn = new CommonConn(context, "main/detail");
                conn.addParamMap("member_id", list.get(idx).getMember_id());
                conn.onExcute((isResult, data) -> {
                    ArrayList<MemberVO> member_list = new Gson().fromJson(data, new TypeToken<ArrayList<MemberVO>>(){}.getType());
                    FriendVO vo =new FriendVO(CommonVar.logininfo.getMember_id(), list.get(idx).getMember_id(), member_list.get(0).getMember_nickname(),member_list.get(0).getMember_profileimg(),"","",false);
                    intent.putExtra("vo", vo);
                    conn2.onExcute((isResult2, data2) -> {
                       // selectAlarmList();
                        notifyDataSetChanged();
                        context.startActivity(intent);
                    });
                });
            } else {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemAlarmHistoryBinding binding;

        public ViewHolder(@NonNull ItemAlarmHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void deleteAlarm(int position) {
        CommonConn conn = new CommonConn(context, "main/deleteAlarm");
        conn.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
        conn.addParamMap("nickname", list.get(position).getAlarm_content().substring(0, list.get(position).getAlarm_content().indexOf("님")));
        conn.addParamMap("alarm_content2", "친구신청을");
        conn.onExcute((isResult, data) -> {
            list = new Gson().fromJson(data, new TypeToken<ArrayList<AlarmVO>>(){}.getType());
            Intent intent = new Intent("update-alarm-count");
            intent.putExtra("alarmCount", list.size());
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            notifyDataSetChanged();
        });
    }

//    private void updateAlarm() {
//        CommonConn conn1 = new CommonConn(context, "main/viewAlarmCnt");
//        conn1.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
//        conn1.onExcute((isResult1, data1) -> {
//            Intent intent = new Intent("update-alarm-count");
//            intent.putExtra("alarmCount", data1);
//            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//        });
//    }

//    private void selectAlarmList() {
//        CommonConn conn = new CommonConn(context, "main/viewAlarm");
//        conn.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
//        conn.onExcute((isResult, data) -> {
//            list = new Gson().fromJson(data, new TypeToken<ArrayList<AlarmVO>>(){}.getType());
//        });
//    }
}
