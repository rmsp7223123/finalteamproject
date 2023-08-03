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

    private List<FriendListDTO> originalList; // 전체 친구 목록
    private List<FriendListDTO> filteredList; // 필터링된 친구 목록


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFriendListBinding.inflate(inflater,container,false);
        adapter = new FriendListAdapter(getList(),getContext());
        binding.recvFriendList.setAdapter(adapter);
        binding.recvFriendList.setLayoutManager(new LinearLayoutManager(getContext()));
        originalList = getList();
        filteredList = new ArrayList<>(originalList);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    public ArrayList<FriendListDTO> getList() {
        ArrayList<FriendListDTO> list = new ArrayList<>();
        list.add(new FriendListDTO(R.drawable.haerin2, "닉네임1"));
        list.add(new FriendListDTO(R.drawable.minji10, "닉네임2"));
        list.add(new FriendListDTO(R.drawable.hanni9, "닉네임3"));
        list.add(new FriendListDTO(R.drawable.danielle11, "닉네임4"));
        list.add(new FriendListDTO(R.drawable.hyein11, "5닉네임"));
        return list;
    }
    private void filterList(String query) {
        filteredList.clear();
        for (int i = 0; i < originalList.size(); i++) {
            FriendListDTO friend = originalList.get(i);
            if (friend.getNickname().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(friend);
            }
        }
        adapter.notifyDataSetChanged();
    }
}