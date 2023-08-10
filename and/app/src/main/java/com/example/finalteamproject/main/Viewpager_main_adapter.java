package com.example.finalteamproject.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.MemberVO;
import com.example.finalteamproject.databinding.ViewpagerMainSliderBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Viewpager_main_adapter extends RecyclerView.Adapter<Viewpager_main_adapter.ViewHolder> {

    ViewpagerMainSliderBinding binding;

    Context context;
    ArrayList<MemberVO> list;

    public Viewpager_main_adapter(Context context, ArrayList<MemberVO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewpagerMainSliderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(list.get(position).getMember_profileimg() == null || list.get(position).getMember_profileimg().length() < 1){
            Glide.with(context).load(R.drawable.test).apply(new RequestOptions().circleCrop()).into(holder.binding.imageSlider);
        }else{
            Glide.with(context).load(list.get(position).getMember_profileimg()).apply(new RequestOptions().circleCrop()).into(holder.binding.imageSlider);
        }
        //CommonConn conn = new CommonConn(context, "main/viewpager");
        //conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());

//        conn.onExcute((isResult, data) -> {
//            list = new Gson().fromJson(data, new TypeToken<ArrayList<MemberVO>>() {
//            }.getType());
//            if (data.) {
//                Glide.with(context).load(R.drawable.test).apply(new RequestOptions().circleCrop()).into(holder.binding.imageSlider);
//            } else {
//                Glide.with(context).load(list.get(position).getMember_profileimg()).apply(new RequestOptions().circleCrop()).into(holder.binding.imageSlider);
//            }
//
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewpagerMainSliderBinding binding;

        public ViewHolder(@NonNull ViewpagerMainSliderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}
