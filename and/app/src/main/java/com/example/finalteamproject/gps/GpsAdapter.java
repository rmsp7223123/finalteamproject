package com.example.finalteamproject.gps;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.example.finalteamproject.databinding.ItemGpsBinding;

import java.util.ArrayList;

//내 주변 경로당 아이템
public class GpsAdapter extends RecyclerView.Adapter<GpsAdapter.ViewHolder> {
    ItemGpsBinding binding;
    ArrayList<GpsVO> list;
    Context context;

    public GpsAdapter(ArrayList<GpsVO> list) {
        this.list = list;
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

        //상세페이지로 전달할 값
        h.binding.itemSenior.setOnClickListener(v -> {
            Intent intent = new Intent(context, GpsDetailActivity.class);
            intent.putExtra("senior_key", list.get(i).getKey());
            intent.putExtra("senior_name", h.binding.seniorName.getText().toString());
            intent.putExtra("senior_address", h.binding.seniorRoadaddress.getText().toString());
            intent.putExtra("senior_phone", h.binding.seniorPhone.getText().toString());
            intent.putExtra("senior_like", h.binding.seniorLike.getText().toString());
            context.startActivity(intent);
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
