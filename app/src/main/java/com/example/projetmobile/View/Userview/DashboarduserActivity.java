package com.example.projetmobile.View.Userview;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetmobile.Model.User;
import com.example.projetmobile.R;
import com.example.projetmobile.Service.UserService;

import java.util.ArrayList;
import java.util.List;

public class DashboarduserActivity extends AppCompatActivity {

    private RecyclerView userRecyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboarduser_layout);

        userRecyclerView = findViewById(R.id.userRecyclerView);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data - replace with real user data
        userList = new ArrayList<>();
        UserService u = new UserService(this);
        userList = u.getAllUsers();
        userAdapter = new UserAdapter(userList, this);
        userRecyclerView.setAdapter(userAdapter);
    }
}
