package com.example.finalteamproject.chat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.R;
import com.example.finalteamproject.board.BoardContextFragment;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.FragmentFriendDetailBinding;
import com.example.finalteamproject.main.FriendVO;

public class FriendDetailFragment extends Fragment {

    FragmentFriendDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFriendDetailBinding.inflate(inflater,container,false);
        binding.cvDetailProfile.bringToFront();
        Bundle bundle = getArguments();
        FriendVO friendVO = (FriendVO) bundle.getSerializable("friend_detail");
        if(bundle != null) {
            Glide.with(getContext()).load(friendVO.getMember_profileimg()).into(binding.imgvProfileImg);
            binding.tvDetailNickname.setText(friendVO.getMember_nickname());
        }
        binding.imgvDetailClose.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        });
        binding.containerFrameMessage1.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MessageChatActivity.class);
            friendVO.setMember_id(CommonVar.logininfo.getMember_id());
            intent.putExtra("vo",friendVO);
            startActivity(intent);
        });

        binding.containerFrameMessage2.setOnClickListener(v -> {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
            builder.setTitle("친구를 삭제하시겠습니까?");
            builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CommonConn conn = new CommonConn(getContext(), "main/deleteFriend");
                    conn.addParamMap("friend_id", friendVO.getFriend_id());
                    conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                    conn.onExcute((isResult, data) -> {
                        if(data.equals("성공")){
                            Toast.makeText(getContext(), "친구 삭제 성공", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.container_frame, new FriendListFragment());
                            ft.commit();
                        }else {
                            Toast.makeText(getContext(), "친구 삭제 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            android.app.AlertDialog dialog = builder.create();
            dialog.show();
        });

        return binding.getRoot();
    }
}