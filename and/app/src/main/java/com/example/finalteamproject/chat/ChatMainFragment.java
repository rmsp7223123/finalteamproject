package com.example.finalteamproject.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.FragmentChatBinding;

public class ChatMainFragment extends Fragment {
    FragmentChatBinding binding;
    private boolean isRailVisible = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        setupNavigationRail();
        binding.navigationRail.setSelectedItemId(R.id.friend_list);
        binding.containerLinearOpen.setOnClickListener(view -> {
            binding.containerLinearOpen.setVisibility(View.INVISIBLE);
            showNavigationRail();
        });
        return binding.getRoot();

    }

    private void setupNavigationRail() {
        binding.navigationRail.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.close) {
                binding.containerLinearOpen.setVisibility(View.VISIBLE);
                hideNavigationRail();
            } else {
                showNavigationRail();
            }
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