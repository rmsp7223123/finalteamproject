package com.example.finalteamproject.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.FragmentFriendListBinding;

import java.util.ArrayList;
import java.util.List;

public class FriendListFragment extends Fragment {
    FragmentFriendListBinding binding;
    FriendListAdapter adapter;

    private List<MessageDTO> originalList; // 전체 친구 목록
    private ArrayList<MessageDTO> filteredList; // 필터링된 친구 목록


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFriendListBinding.inflate(inflater,container,false);
        adapter = new FriendListAdapter(getList(),getContext());
        binding.recvFriendList.setAdapter(adapter);
        binding.recvFriendList.setLayoutManager(new LinearLayoutManager(getContext()));
        originalList = getList();
        filteredList = new ArrayList<>(originalList);
        binding.searchView.setIconifiedByDefault(false);
        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });
        return binding.getRoot();
    }

    public ArrayList<MessageDTO> getList() {
        ArrayList<MessageDTO> list = new ArrayList<>();
        list.add(new MessageDTO(R.drawable.haerin2,"해린","내용1","12:34",false));
        list.add(new MessageDTO(R.drawable.hanni9,"하니","내용2","11:34",false));
        list.add(new MessageDTO(R.drawable.minji10,"민지","내용3","10:34",false));
        list.add(new MessageDTO(R.drawable.hyein11,"혜인","내용4","14:34",false));
        list.add(new MessageDTO(R.drawable.danielle11,"다니엘","내용5","15:34",false));
        return list;
    }
    private void filterList(String query) {
        filteredList.clear();
        String queryInitialSound = HangulUtils.convertToHangulInitialSound(query);

        for (int i = 0; i < originalList.size(); i++) {
            MessageDTO friend = originalList.get(i);
            String nicknameInitialSound = HangulUtils.convertToHangulInitialSound(friend.getNickname());

            if (nicknameInitialSound.toLowerCase().contains(queryInitialSound.toLowerCase())) {
                filteredList.add(friend);
            }
        }
        adapter.list =  filteredList;
        adapter.notifyDataSetChanged();
    }
}