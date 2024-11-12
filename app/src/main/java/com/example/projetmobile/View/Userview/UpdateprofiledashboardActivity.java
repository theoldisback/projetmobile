package com.example.projetmobile.View.Userview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetmobile.Model.User;
import com.example.projetmobile.R;
import com.example.projetmobile.Service.UserService;

public class UpdateprofiledashboardActivity extends AppCompatActivity {

Button updatebutton;
    String selectedRole; // Variable to hold the selected role
    UserService userService;
    String currentrole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            userService = new UserService(this);
        setContentView(R.layout.updateuserdashboard_layout);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("USER_ID", -1);
        Log.d("UserId", "useeerid"+String.valueOf(userId));

        updatebutton = findViewById(R.id.updatedashboarrole);
        User user= userService.getUserById(userId);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        currentrole= user.getRole();
        int position = adapter.getPosition(currentrole);
        spinner.setSelection(position);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = parentView.getItemAtPosition(position).toString();
                Toast.makeText(UpdateprofiledashboardActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                currentrole= selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case when nothing is selected
            }
        });



        updatebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                User user = new User();
                user.setId(userId);
                user.setRole(currentrole);
                userService.updateUserrole(user);
                Intent intent = new Intent(UpdateprofiledashboardActivity.this, DashboarduserFragment.class);
                startActivity(intent);
            }
        });
    }


}
