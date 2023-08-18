package com.example.finalteamproject.chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.R;
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
        binding.imgvMessage.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MessageChatActivity.class);
            friendVO.setMember_id(CommonVar.logininfo.getMember_id());
            intent.putExtra("vo",friendVO);
            startActivity(intent);
        });

        return binding.getRoot();
    }
}