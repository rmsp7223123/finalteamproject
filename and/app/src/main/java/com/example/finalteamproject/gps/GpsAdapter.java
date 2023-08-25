package com.example.finalteamproject.gps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.com.example.finalteamproject.common.CustomTextview;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ItemGpsBinding;
import com.naver.maps.map.NaverMap;

import java.util.ArrayList;

//내 주변 경로당 아이템
public class GpsAdapter extends RecyclerView.Adapter<GpsAdapter.ViewHolder> {
    ItemGpsBinding binding;
    ArrayList<GpsVO> list;
    Context context;
    GpsFragment gpsFragment;

    public GpsAdapter(ArrayList<GpsVO> list, GpsFragment gpsFragment) {
        this.list = list;
        this.gpsFragment = gpsFragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(list == null || list.size() == 0){
            com.example.finalteamproject.common.CustomTextview tv = new com.example.finalteamproject.common.CustomTextview(parent.getContext());
            tv.setText("검색 결과가 없습니다.");
            return new ViewHolder(tv);
        }else{
            binding = ItemGpsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            context = parent.getContext();
            return new ViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {

        if(list == null || list.size() == 0 || h.binding == null){

        }else{


        //경로당 이름
        h.binding.seniorName.setText(list.get(i).getSenior_name()+"");
        //경로당 주소
        if (list.get(i).getSenior_roadaddress() == null){
            h.binding.seniorRoadaddress.setText("주소 정보 없음");
        }else {
            h.binding.seniorRoadaddress.setText(list.get(i).getSenior_roadaddress()+"");
        }
        //좋아요 수
        h.binding.seniorLike.setText(list.get(i).getSenior_like_num()+"");

            //상세정보 페이지(프래그먼트)
            h.binding.itemSenior.setOnClickListener(v -> {
               gpsFragment.selectDetail(list.get(i));
            });
        }
    }

    @Override
    public int getItemCount() {
        if(list == null || list.size() == 0) {
            return 1;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemGpsBinding binding;

        public ViewHolder(ItemGpsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public ViewHolder(View binding) {
            super(binding);
        }
    }


}
