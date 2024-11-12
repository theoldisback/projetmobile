package com.example.projetmobile.View.Userview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetmobile.Model.User;
import com.example.projetmobile.R;
import com.example.projetmobile.Service.UserService;

import java.util.ArrayList;
import java.util.List;

public class DashboarduserFragment extends Fragment {

    private RecyclerView userRecyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;

    public DashboarduserFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("DashboarduserFragment", "onCreateView called");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dashboarduser_layout, container, false);

        // Initialize RecyclerView and set it up
        userRecyclerView = view.findViewById(R.id.userRecyclerView);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Dummy data - replace with real user data
        userList = new ArrayList<>();
        UserService u = new UserService(getActivity());
        userList = u.getAllUsers();
        userAdapter = new UserAdapter(userList, getActivity());
        userRecyclerView.setAdapter(userAdapter);

        return view;
    }


}
