package com.example.finalteamproject.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.FragmentChatBinding;
import com.google.android.material.navigation.NavigationBarView;

public class ChatMainFragment extends Fragment {
    FragmentChatBinding binding;
    private boolean isRailVisible = true;
    FragmentManager manager;

    Fragment fragment = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        manager = getActivity().getSupportFragmentManager();
        setupNavigationRail();
        binding.navigationRail.setSelectedItemId(R.id.friend_list);
        binding.containerLinearOpen.setOnClickListener(view -> {
            binding.containerLinearOpen.setVisibility(View.INVISIBLE);
            showNavigationRail();
        });
//        binding.navigationRail.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment fragment = null;
//                if(item.getItemId() == R.id.friend_list) {
//                    fragment = new FriendListFragment();
//                } else {
//                    return false;
//                }
//                manager.beginTransaction().replace(R.id.container_frame_call_msg, fragment).commit();
//                return true;
//            }
//        });
        return binding.getRoot();

    }

    private void setupNavigationRail() {
        binding.navigationRail.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.close) {
                binding.containerLinearOpen.setVisibility(View.VISIBLE);
                hideNavigationRail();
            } else if(item.getItemId() == R.id.friend_list){
                fragment = new FriendListFragment();
            }else {
                showNavigationRail();
            }
            manager.beginTransaction().replace(R.id.container_frame_call_msg,fragment).commit();
            return true;
        });
    }

    private void hideNavigationRail() {
        if (isRailVisible) {
            Animation slideOut = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_to_left);
            slideOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.navigationRail.setVisibility(View.GONE);
                    isRailVisible = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            binding.navigationRail.startAnimation(slideOut);
        }
    }

    private void showNavigationRail() {
        if (!isRailVisible) {
            Animation slideIn = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_from_left);
            slideIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    binding.navigationRail.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isRailVisible = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            binding.navigationRail.startAnimation(slideIn);
        }
    }
}