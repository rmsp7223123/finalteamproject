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
                    ArrayList<AlarmVO> list2 = new Gson().fromJson(data, new TypeToken<ArrayList<AlarmVO>>() {
                    }.getType());
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("친구 추가 요청");
                    builder.setMessage(list2.get(holder.getAdapterPosition()).getMember_id() + "님의 친구 추가 요청을 수락하시겠습니까?");
                    builder.setPositiveButton("거절", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteAlarm(idx);
                            list.remove(idx);
                            updateAlarm();
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("수락", (dialog, which) -> {
                        CommonConn conn1 = new CommonConn(context, "main/addFriend");
                        conn1.addParamMap("member_id", list2.get(idx).getMember_id());
                        conn1.addParamMap("friend_id", CommonVar.logininfo.getMember_id());
                        conn1.onExcute((isResult1, data1) -> {
                            deleteAlarm(idx);
                            list.remove(idx);
                            updateAlarm();
                            notifyDataSetChanged();
                        });


                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                });
            } else if (alarm.getAlarm_content().contains("메시지")) {
                CommonConn conn = new CommonConn(context, "main/viewAlarm");
                conn.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
                conn.onExcute((isResult, data) -> {
                    ArrayList<AlarmVO> alarm_list = new Gson().fromJson(data, new TypeToken<ArrayList<AlarmVO>>() {
                    }.getType());
                    if(alarm_list.get(idx).alarm_content.contains("메시지")) {
                        CommonConn conn1 = new CommonConn(context, "main/viewFriendList");
                        conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                        conn1.onExcute((isResult1, data1) -> {
                            ArrayList<FriendVO> friendList = new Gson().fromJson(data1, new TypeToken<ArrayList<FriendVO>>(){}.getType());
                            friendList.get(idx).setMember_id(CommonVar.logininfo.getMember_id());
                            Intent intent = new Intent(context, MessageChatActivity.class);
                            intent.putExtra("vo", friendList.get(idx));
                            CommonConn conn2 = new CommonConn(context, "main/deleteAlarm");
                            conn2.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
                            conn2.onExcute((isResult2, data2) -> {
                                list = new ArrayList<>();
                                CommonConn conn3 = new CommonConn(context,"main/viewAlarm");
                                conn3.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
                                conn3.onExcute((isResult3, data3) -> {
                                    list = new Gson().fromJson(data3, new TypeToken<ArrayList<AlarmVO>>(){}.getType());
                                    context.startActivity(intent);
                                    updateAlarm();
                                    notifyDataSetChanged();
                                });
                            });
                        });
                    }
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
        CommonConn conn = new CommonConn(context, "main/deleteOneAlarm");
        conn.addParamMap("alarm_id", list.get(position).getAlarm_id());
        conn.onExcute((isResult, data) -> {

        });
    }
    private void updateAlarm() {
        CommonConn conn1 = new CommonConn(context, "main/viewAlarmCnt");
        conn1.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
        conn1.onExcute((isResult1, data1) -> {
            Intent intent = new Intent("update-alarm-count");
            intent.putExtra("alarmCount", data1);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        });
    }
}
