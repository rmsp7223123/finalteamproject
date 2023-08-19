package com.example.finalteamproject.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.FragmentFriendListBinding;
import com.example.finalteamproject.main.FriendVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FriendListFragment extends Fragment {
    FragmentFriendListBinding binding;
    FriendListAdapter adapter;

    private List<FriendVO> originalList; // 전체 친구 목록
    private ArrayList<FriendVO> filteredList; // 필터링된 친구 목록

    public ArrayList<FriendVO> test;

    ArrayList<FriendVO> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFriendListBinding.inflate(inflater, container, false);
        binding.searchView.setIconifiedByDefault(false);
        originalList = getList();
        filteredList = new ArrayList<>(originalList);
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
        friendList();
//        binding.containerFrameProfile.setVisibility(View.GONE);
        return binding.getRoot();
    }

    public ArrayList<FriendVO> getList() {
        ArrayList<FriendVO> list = new ArrayList<>();
        return list;
    }

    private void filterList(String query) {
        filteredList.clear();
        String queryInitialSound = HangulUtils.convertToHangulInitialSound(query);

        for (int i = 0; i < list.size(); i++) {
            FriendVO friend = list.get(i);
            String nicknameInitialSound = HangulUtils.convertToHangulInitialSound(friend.getMember_nickname());
            if (nicknameInitialSound.toLowerCase().contains(queryInitialSound.toLowerCase())) {
                filteredList.add(friend);
            }
        }

        adapter.list = filteredList;
        adapter.notifyDataSetChanged();
    }


    public void friendList() {
        CommonConn conn = new CommonConn(getContext(), "main/viewFriendList");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {
            list = new Gson().fromJson(data, new TypeToken<ArrayList<FriendVO>>() {
            }.getType());
            adapter = new FriendListAdapter(list, getContext());
            binding.recvFriendList.setAdapter(adapter);
            binding.recvFriendList.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.notifyDataSetChanged();
        });
    }

}