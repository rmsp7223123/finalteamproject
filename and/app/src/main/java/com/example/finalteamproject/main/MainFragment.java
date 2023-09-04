package com.example.finalteamproject.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.Login.LoginInfoActivity;
import com.example.finalteamproject.Login.LoginProfileActivity;
import com.example.finalteamproject.Login.ProgressDialog;
import com.example.finalteamproject.R;
import com.example.finalteamproject.board.BoardCommonVar;
import com.example.finalteamproject.chat.MessageChatActivity;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.MemberVO;
import com.example.finalteamproject.databinding.FragmentMainBinding;
import com.example.finalteamproject.databinding.ItemMainFriendBinding;
import com.example.finalteamproject.setting.ChangeProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.chip.Chip;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.Cipher;

public class MainFragment extends Fragment{

    FragmentMainBinding binding;

    public static ArrayList<MemberVO> list;


    //Viewpager_main_adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);


        binding.imgvMenu.setOnClickListener(v-> {
            boardEvent();

            BoardCommonVar.board = true;
            binding.lnBoard.setVisibility(View.VISIBLE);

        });

        binding.tvMenu.setOnClickListener(v->{
            boardEvent();

            BoardCommonVar.board = true;
            binding.lnBoard.setVisibility(View.VISIBLE);
        });
        binding.imgvAlaram.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MainAlarmHistoryActivity.class);
            startActivity(intent);
        });
        binding.imgvCalendar.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CalendarActivity.class);
            startActivity(intent);
        });
        selectMainSlider();
        selectAlarmCount();

//        //ym 주석 2023 08 29
        return binding.getRoot();
    }

    private void boardEvent(){
        BoardMainAdapter adapter2 = new BoardMainAdapter(this, getList(), getActivity());
        binding.recvBoard.setAdapter(adapter2);
        binding.recvBoard.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //
        binding.imgvMenu.setOnClickListener(v -> {

        });

        binding.imgvClose.setOnClickListener(v -> {
            BoardCommonVar.board = false;
            binding.lnBoard.setVisibility(View.INVISIBLE);
        });
        //게시판에서 뒤로갔을 때 게시판 메뉴 보여주기
        if (BoardCommonVar.board) {
            binding.lnBoard.setVisibility(View.VISIBLE);
        } else {
            binding.lnBoard.setVisibility(View.GONE);
        }
        //
    }

    private ArrayList<BoardMainDTO> getList() {
        ArrayList<BoardMainDTO> list = new ArrayList<>();
        list.add(new BoardMainDTO(R.drawable.tv_select, R.drawable.mini_arrow, "TV"));
        list.add(new BoardMainDTO(R.drawable.music_select, R.drawable.mini_arrow, "음악"));
        list.add(new BoardMainDTO(R.drawable.movie_select, R.drawable.mini_arrow, "영화"));
        list.add(new BoardMainDTO(R.drawable.fashion_select, R.drawable.mini_arrow, "패션"));
        list.add(new BoardMainDTO(R.drawable.animal_select, R.drawable.mini_arrow, "동물"));
        list.add(new BoardMainDTO(R.drawable.news_select, R.drawable.mini_arrow, "뉴스"));
        list.add(new BoardMainDTO(R.drawable.car_select, R.drawable.mini_arrow, "자동차"));
        list.add(new BoardMainDTO(R.drawable.sports_select, R.drawable.mini_arrow, "운동"));
        list.add(new BoardMainDTO(R.drawable.game_select, R.drawable.mini_arrow, "게임"));
        return list;
    }
    private void selectMainSlider() {

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.show();

        CommonConn conn =new CommonConn(getContext() , "main/viewpager");
        conn.addParamMap("member_id",CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {
            list = new Gson().fromJson(data, new TypeToken<ArrayList<MemberVO>>() {
            }.getType());
                    SwipeStackAdapter adapter = new SwipeStackAdapter(getLayoutInflater(),list,getContext());
                    adapter.setFragment(this);


           CardStackLayoutManager manager = new CardStackLayoutManager(getContext(), new CardStackListener() {
               @Override
               public void onCardDragging(Direction direction, float ratio) {

               }

               @Override
               public void onCardSwiped(Direction direction) {

               }

               @Override
               public void onCardRewound() {

               }

               @Override
               public void onCardCanceled() {

               }

               @Override
               public void onCardAppeared(View view, int position) {
                   Log.d("TAG", "onCardAppeared: ");
               }

               @Override
               public void onCardDisappeared(View view, int position) {
                   Log.d("TAG", "onCardAppeared: ");
                   if(position+1 == list.size()){
                       selectMainSlider();
                   }
               }
           });
            manager.setStackFrom(StackFrom.Top);
            manager.setVisibleCount(3);
            RewindAnimationSetting.Builder setting = new  RewindAnimationSetting.Builder();
            setting.setDirection(Direction.Bottom);
            setting.setInterpolator(input -> {
                Log.d("TAG", "selectMainSlider: "+ ""+input);
                return input;
            });

            setting.setDuration(Duration.Normal.duration);
            manager.setTranslationInterval(8.0f);
            manager.setVisibleCount(3);
            manager.setRewindAnimationSetting(setting.build());
                    binding.swipeStack.setLayoutManager(manager);
                    binding.swipeStack.setAdapter(adapter);




            dialog.dismiss();

        });





    }

    @Override
    public void onResume() {
        super.onResume();
        selectAlarmCount();
    }


    public void dialog_message(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        CommonConn conn1 = new CommonConn(getContext(), "main/viewpager");
        conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());

        builder.setTitle("메시지 보내기");
        builder.setMessage(list.get(position).getMember_nickname() + "님에게 메시지 보내기");
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FriendVO vo = new FriendVO(CommonVar.logininfo.getMember_id(), list.get(position).getMember_id(), list.get(position).getMember_nickname(), list.get(position).getMember_profileimg(), "", "", false);
                Intent intent = new Intent(getContext(), MessageChatActivity.class);
                intent.putExtra("vo", vo);
                getContext().startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void dialog_friend(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        CommonConn conn1 = new CommonConn(getContext(), "main/viewpager");
        conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn1.onExcute((isResult, data) -> {
            list = new Gson().fromJson(data, new TypeToken<ArrayList<MemberVO>>() {
            }.getType());
            builder.setTitle("친구추가");
            builder.setMessage(list.get(position).getMember_nickname() + "님에게 친구추가 보내기");
            builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(), list.get(position).getMember_nickname() + "님에게 친구추가를 보냈습니다.", Toast.LENGTH_SHORT).show();
                    CommonConn conn = new CommonConn(getContext(), "main/addAlarm");
                    conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                    conn.addParamMap("alarm_content", CommonVar.logininfo.getMember_nickname() + "님이 친구신청을 보냈습니다.");
                    conn.addParamMap("alarm_time", new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                    conn.addParamMap("receive_id", list.get(position).getMember_id());
                    conn.onExcute((isResult1, data1) -> {
                        if (isResult1) {
                            Log.d("TAG", "onClick: " + "확인용");
                        }
                    });
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }





    //친구 추가 메소드 ( 하트버튼 누르면 )

    // 알람을 받았을때 알람개수가 바로 늘어나게 추가
    private BroadcastReceiver updateAlarmCountReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("update-alarm-count".equals(intent.getAction())) {
                String alarmCount = intent.getStringExtra("alarmCount");
                updateAlarmCount(alarmCount);
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IntentFilter filter = new IntentFilter("update-alarm-count");
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(updateAlarmCountReceiver, filter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(updateAlarmCountReceiver);
        binding = null;
    }
    @SuppressLint("UnsafeOptInUsageError")
    public void updateAlarmCount(String alarmCount) {
        if (alarmCount.equals("0")) {
            BadgeUtils.attachBadgeDrawable(getBadge(alarmCount) , binding.imgvAlaram );
           // binding.cvAlarmCnt.setVisibility(View.GONE);
        } else {
            BadgeUtils.attachBadgeDrawable(getBadge(alarmCount) , binding.imgvAlaram );
           // binding.cvAlarmCnt.setVisibility(View.VISIBLE);
           // binding.tvAlarmCnt.setText(alarmCount);
        }
    }




    //알람개수조회
    @SuppressLint("UnsafeOptInUsageError")
    public void selectAlarmCount(){
        CommonConn conn1 = new CommonConn(getContext(), "main/viewAlarmCnt");
        conn1.addParamMap("receive_id" , CommonVar.logininfo.getMember_id());
        conn1.onExcute((isResult, data) -> {
            if(data.equals("0")) {
                BadgeUtils.attachBadgeDrawable(getBadge(data) , binding.imgvAlaram );
                //binding.cvAlarmCnt.setVisibility(View.GONE);
            } else {
                BadgeUtils.attachBadgeDrawable(getBadge(data) , binding.imgvAlaram );
             //   binding.cvAlarmCnt.setVisibility(View.VISIBLE);
              //  binding.tvAlarmCnt.setText(data);
            }
        });

    }


    BadgeDrawable getBadge(String data){
        BadgeDrawable badge =  BadgeDrawable.create(getContext());
        badge.setBackgroundColor(Color.parseColor("#990000"));
        badge.setBadgeTextColor(Color.parseColor("#FFFFFF"));
        badge.setBadgeGravity(BadgeDrawable.TOP_START);
        badge.setNumber(Integer.parseInt(data));
        return badge;
    }
}


