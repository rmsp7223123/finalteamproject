package com.example.finalteamproject.gps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ItemGpslikeBinding;

import java.util.ArrayList;

//자주가는 경로당 아이템(좋아요 버튼 외)
public class GpsLikeAdapter extends RecyclerView.Adapter<GpsLikeAdapter.ViewHolder> {
    ItemGpslikeBinding binding;
    ArrayList<GpsVO> list;

    GpsFragment gpsFragment;

    public GpsLikeAdapter(ArrayList<GpsVO> list, GpsFragment gpsFragment) {
        this.list = list;
        this.gpsFragment = gpsFragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding=ItemGpslikeBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        h.binding.seniorName.setText(list.get(i).getSenior_name()+"");
        h.binding.seniorAddress.setText(list.get(i).getSenior_roadaddress()+"");

        h.binding.unlike.setVisibility(View.GONE);

        h.binding.like.setOnClickListener(v -> {
            CommonConn conn = new CommonConn(v.getContext(), "gps/unlikebtn");
            conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            conn.addParamMap("key", list.get(i).getKey());
            conn.onExcute((isResult, data) -> {
                h.binding.like.setVisibility(View.GONE);
                h.binding.unlike.setVisibility(View.VISIBLE);
                Toast.makeText(v.getContext(), "자주가는 경로당이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                gpsFragment.likelist();
            });
        });

        h.binding.unlike.setOnClickListener(v -> {
            CommonConn conn = new CommonConn(v.getContext(), "gps/likebtn");
            conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            conn.addParamMap("key", list.get(i).getKey());
            conn.onExcute((isResult, data) -> {
                h.binding.unlike.setVisibility(View.GONE);
                h.binding.like.setVisibility(View.VISIBLE);
                Toast.makeText(v.getContext(), "자주가는 경로당이 추가되었습니다", Toast.LENGTH_SHORT).show();
                gpsFragment.likelist();
            });
        });

        h.binding.senoirLikeinfo.setOnClickListener(v -> {
            gpsFragment.selectDetail(list.get(i));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemGpslikeBinding binding;

        public ViewHolder(ItemGpslikeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
