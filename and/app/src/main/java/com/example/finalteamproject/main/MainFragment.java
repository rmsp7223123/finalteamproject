package com.example.finalteamproject.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.FragmentMainBinding;
import com.example.finalteamproject.setting.ChangeProfileActivity;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;

    int[] images = new int[]{
            R.drawable.haerin2,
            R.drawable.minji10,
            R.drawable.minji12,
            R.drawable.danielle11,
            R.drawable.hanni9,
            R.drawable.hyein11
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container,false);
        Viewpager_main_adapter adapter = new Viewpager_main_adapter(getContext(), images);
        binding.imgViewpager.setAdapter(adapter);
        binding.imgViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            int currentState = 0;
            int currentPos = 0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(currentState == ViewPager2.SCROLL_STATE_DRAGGING && currentPos == position) {
                    if(currentPos == 0) {
                        binding.imgViewpager.setCurrentItem(images.length);
                    }
                    else if(currentPos == 2) {
                        binding.imgViewpager.setCurrentItem(0);
                    }
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                currentState = state;
                super.onPageScrollStateChanged(state);
            }
        });

        binding.imgvAlarmHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MainAlarmHistoryActivity.class);
            startActivity(intent);
        });

        binding.imgvSmallProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChangeProfileActivity.class);
            startActivity(intent);
        });


        return binding.getRoot();
    }
}