package com.example.finalteamproject.main;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.MemberVO;
import com.example.finalteamproject.databinding.ItemMainFriendBinding;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class SwipeStackAdapter extends RecyclerView.Adapter<SwipeStackAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<MemberVO> list;
    private Context context;

    private MainFragment fragment;

    private MainActivity activity;

    public MainFragment getFragment() {
        return fragment;
    }

    public void setFragment(MainFragment fragment) {
        this.fragment = fragment;
    }

    public SwipeStackAdapter(LayoutInflater inflater, List<MemberVO> list, Context context, Activity activity) {
        this.inflater = inflater;
        this.list = list;
        this.context = context;
        this.activity = (MainActivity)activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMainFriendBinding.inflate(inflater , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        if(list.get(position).getMember_profileimg() == null || list.get(position).getMember_profileimg().length() < 1){
                h.binding.imgvProfile.setImageResource(R.drawable.test);
         //   Glide.with(context).load(R.drawable.test).into(h.binding.imgvProfile);
        }else{
           Glide.with(context).load(list.get(position).getMember_profileimg()).into(h.binding.imgvProfile);
        }
        h.binding.tvName.setText(list.get(position).getMember_nickname() + " (" + list.get(position).getMember_gender() + ")");
//        + " , " + list.get(position).getMember_manner_score()

            CommonConn conn = new CommonConn(inflater.getContext(), "main/favor");
            if (list.size() == 0 || list.size() < position) {

            } else {
                conn.addParamMap("member_id", list.get(position).getMember_id());
                conn.onExcute((isResult1, data1) -> {
                    ArrayList<FavorVO> list1 = new Gson().fromJson(data1, new TypeToken<ArrayList<FavorVO>>() {
                    }.getType());
                    h.binding.chipgroup.removeAllViews();
                    for (int i = 0; i < list1.size(); i++) {
                        Chip chip = (Chip) inflater.inflate(R.layout.item_chip , h.binding.chipgroup,false);
                        chip.setText(getMainDTO(list1.get(i).favor).getTv_board_name());
                        chip.setChipIconEnabled(true);
                        chip.setChipIcon(context.getResources().getDrawable(getMainDTO(list1.get(i).favor).getImgv_icon()));
                        h.binding.chipgroup.addView(chip);

                        int i1 = i;
                        chip.setOnClickListener(v -> {
                            activity.changeFragment(fragment, activity, list1.get(i1).getFavor()-1);
                        });

                    }
                });
            }


            h.binding.cvAdd.setOnClickListener(v->{
                fragment.dialog_friend(position);
            });
            h.binding.cvMessage.setOnClickListener(v->{
                fragment.dialog_message(position);
            });


    }




    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMainFriendBinding binding;

        public ViewHolder(@NonNull ItemMainFriendBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



    private BoardMainDTO getMainDTO(int i) {


        if(i==1){
           return new BoardMainDTO(R.drawable.tv_select, R.drawable.mini_arrow, "TV");
        }else if(i==2){
           return new BoardMainDTO(R.drawable.music_select, R.drawable.mini_arrow, "음악");
        }else if(i==3){
            return new BoardMainDTO(R.drawable.movie_select, R.drawable.mini_arrow, "영화");
        }else if(i==4){
            return new BoardMainDTO(R.drawable.fashion_select, R.drawable.mini_arrow, "패션");
        }else if(i==5){
            return new BoardMainDTO(R.drawable.animal_select, R.drawable.mini_arrow, "동물");
        }else if(i==6){
            return new BoardMainDTO(R.drawable.news_select, R.drawable.mini_arrow, "뉴스");
        }else if(i==7){
            return new BoardMainDTO(R.drawable.car_select, R.drawable.mini_arrow, "자동차");
        }else if(i==8){
            return new BoardMainDTO(R.drawable.sports_select, R.drawable.mini_arrow, "운동");
        }else if(i==9){
            return  new BoardMainDTO(R.drawable.game_select, R.drawable.mini_arrow, "게임");
        }else{
            return null;
        }

    }




}