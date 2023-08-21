package com.example.finalteamproject.gps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ItemGpsBinding;

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
            TextView tv = new TextView(parent.getContext());
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

        //안보일 정보(키, 전화번호)
        h.binding.seniorKey.setText(list.get(i).getKey()+"");
        h.binding.seniorKey.setVisibility(View.GONE);
        h.binding.seniorPhone.setText(list.get(i).getSenior_call()+"");
        h.binding.seniorPhone.setVisibility(View.GONE);
        if(list.get(i).getSenior_call() == null){
            h.binding.seniorPhone.setText("전화번호 정보 없음");
        }
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

        //상세페이지(프래그먼트)
        h.binding.itemSenior.setOnClickListener(v -> {
            gpsFragment.binding.lnDetail.setVisibility(View.VISIBLE);
            CommonConn conn = new CommonConn(v.getContext(), "gps/detail");
            conn.addParamMap("key", list.get(i).getKey());
            gpsFragment.binding.seniorName.setText(list.get(i).getSenior_name()+"");
            if (list.get(i).getSenior_roadaddress() == null){
                gpsFragment.binding.seniorAddress.setText("주소 정보 없음");
            }else {
                gpsFragment.binding.seniorAddress.setText(list.get(i).getSenior_roadaddress()+"");
            }
            if(list.get(i).getSenior_call() == null){
                gpsFragment.binding.phoneNumber.setText("전화번호 정보 없음");
            }else {
                gpsFragment.binding.phoneNumber.setText(list.get(i).getSenior_call()+"");
            }
            gpsFragment.binding.seniorLike.setText("좋아요 "+list.get(i).getSenior_like_num()+"");

            //좋아요 버튼 활성/비활성화
            gpsFragment.binding.like.setVisibility(View.INVISIBLE);
            CommonConn connlike = new CommonConn(v.getContext(), "gps/likeyet");
            connlike.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            connlike.addParamMap("key", list.get(i).getKey());
            connlike.onExcute((isResult, data) -> {
                if (data.equals(list.get(i).getKey()+"")){
                    gpsFragment.binding.like.setVisibility(View.VISIBLE);
                }
            });

            //좋아요 : 회색버튼 누르면 빨간색으로 변함
            gpsFragment.binding.unlike.setOnClickListener(v1 -> {
                CommonConn connl = new CommonConn(v1.getContext(), "gps/likebtn");
                connl.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                connl.addParamMap("key", list.get(i).getKey());
                connl.onExcute((isResult, data) -> {
                    gpsFragment.binding.unlike.setVisibility(View.GONE);
                    gpsFragment.binding.like.setVisibility(View.VISIBLE);
                    gpsFragment.likelist();
                });
            });

            //좋아요 취소 : 빨간 버튼 누르면 회색으로 변함
            gpsFragment.binding.like.setOnClickListener(v1 -> {
                CommonConn connul = new CommonConn(v1.getContext(), "gps/unlikebtn");
                connul.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                connul.addParamMap("key", list.get(i).getKey());
                connul.onExcute((isResult, data) -> {
                    gpsFragment.binding.like.setVisibility(View.GONE);
                    gpsFragment.binding.unlike.setVisibility(View.VISIBLE);
                    gpsFragment.likelist();
                });
            });


            gpsFragment.moveCamera(list.get(i).getSenior_latitude() , list.get(i).getSenior_longitude());


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
