package com.example.finalteamproject.main;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.DialogAddScheduleBinding;
import com.example.finalteamproject.databinding.ItemCalendarBinding;
import com.example.finalteamproject.databinding.ItemCalendarListBinding;
import com.example.finalteamproject.databinding.ItemMessageBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder>{

    ItemCalendarListBinding binding;

    ArrayList<CalendarVO> calendarList;

    Context context;

    String importance = "";

    public CalendarAdapter(ArrayList<CalendarVO> calendarList, Context context) {
        this.calendarList = calendarList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemCalendarListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    //이거는 머하는거여????
    // 그 달력 내용 변경하는건데 스케쥴 이게 좀 이상해요
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvContent.setText(calendarList.get(position).calendar_content);
        holder.binding.tvContent.setOnClickListener(v -> {
                Dialog dialog = new Dialog(context);
                ItemCalendarBinding dialogBinding = ItemCalendarBinding.inflate(LayoutInflater.from(context));
                dialog.setContentView(dialogBinding.getRoot());
                dialogBinding.dateText.setText(calendarList.get(position).calendar_date);
                dialogBinding.content.setText(calendarList.get(position).calendar_content);
                try{
                    RadioButton btn = (RadioButton) dialogBinding.radioGroup.getChildAt(3-Integer.parseInt(calendarList.get(position).calendar_importance));
                    btn.setChecked(true);
                }catch (Exception e){
                    Log.d("TAG", "onBindViewHolder: 중요도 없음.");
                }
                dialogBinding.saveScheduleBtn.setOnClickListener(view -> {
                    for (int i = 0; i < dialogBinding.radioGroup.getChildCount(); i++) {
                        RadioButton btn = (RadioButton) dialogBinding.radioGroup.getChildAt(i);
                        if(btn.isChecked()) {
                            importance = 3 - (i) + "";
                            break;
                        }
                    }
                    CommonConn conn1 = new CommonConn(context, "main/updateSchedule");
                    conn1.addParamMap("calendar_content", dialogBinding.content.getText().toString());
                    conn1.addParamMap("calendar_importance", importance);
                    conn1.addParamMap("calendar_id", calendarList.get(position).calendar_id);
                    conn1.onExcute((isResult1, data1) -> {
                        CalendarVO vo = new Gson().fromJson(data1,CalendarVO.class);
                        if(vo !=null){
                            calendarList.set(position,vo);
                            notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    });
                });
                dialogBinding.cancelDialogBtn.setOnClickListener(view -> {
                    CommonConn conn1 = new CommonConn(context, "main/deleteScheduleOne");
                    conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                    conn1.addParamMap("calendar_id", calendarList.get(position).calendar_id);
                    conn1.onExcute((isResult1, data1) -> {
                        calendarList.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    });
                });
                dialog.show();
        });
    }



    @Override
    public int getItemCount() {
        if(calendarList == null) return 0;
        return calendarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemCalendarListBinding binding;

        public ViewHolder(@NonNull ItemCalendarListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
