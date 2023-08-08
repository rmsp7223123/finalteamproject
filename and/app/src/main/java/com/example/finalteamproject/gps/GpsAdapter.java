package com.example.finalteamproject.gps;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.databinding.ItemGpsBinding;

import java.util.ArrayList;

//내 주변 경로당 아이템
public class GpsAdapter extends RecyclerView.Adapter<GpsAdapter.ViewHolder> {
    ItemGpsBinding binding;
    ArrayList<GpsVO> list;
    GpsVO vo;
    Context context;

    public GpsAdapter(ArrayList<GpsVO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemGpsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        //경로당 전화번호(안보임)
        h.binding.seniorPhone.setText(list.get(i).getSenior_call()+"");
        h.binding.seniorPhone.setVisibility(View.GONE);
        if(list.get(i).getSenior_call() == null){
            h.binding.seniorPhone.setText("전화번호 정보 없음");
        }
        //경로당 이름
        h.binding.seniorName.setText(list.get(i).getSenior_name()+"");
        //경로당 주소
        h.binding.seniorRoadaddress.setText(list.get(i).getSenior_roadaddress()+"");
        if (list.get(i).getSenior_roadaddress() == null){
            h.binding.seniorRoadaddress.setText("주소 정보 없음");
        }
        //좋아요 수
        h.binding.seniorLike.setText(list.get(i).getSenior_like_num()+"");

        h.binding.itemSenior.setOnClickListener(v -> {
            Intent intent = new Intent(context, GpsDetailActivity.class);
            intent.putExtra("senior_name", h.binding.seniorName.getText().toString());
            intent.putExtra("senior_address", h.binding.seniorRoadaddress.getText().toString());
            intent.putExtra("senior_phone", h.binding.seniorPhone.getText().toString());
            intent.putExtra("senior_like", h.binding.seniorLike.getText().toString());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemGpsBinding binding;

        public ViewHolder(ItemGpsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
