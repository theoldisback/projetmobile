package com.example.projetmobile.View.Userview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetmobile.Model.User;
import com.example.projetmobile.R;
import com.example.projetmobile.Service.UserService;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditprofileActivity extends AppCompatActivity {
     Button editprofilebutton ;
    DatePicker birthdateTextView;
    EditText usernameTextView,emailTextView,firstnameTextView,lastnameTextView,addressTextView;
    private UserService userService;
    int userId;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.editprofile_layout);
        UserService userService = new UserService(this);
        // Retrieve the SharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        // Get the user details from SharedPreferences
         userId = sharedPreferences.getInt("USER_ID", -1); // Default is -1 if not found username = sharedPreferences.getString("USERNAME", ""); // Default is an empty string if not found

        String username = sharedPreferences.getString("USERNAME", ""); // Default is an empty string if not found
        String email = sharedPreferences.getString("EMAIL", "");
        String firstname = sharedPreferences.getString("FIRSTNAME", "");
        String lastname = sharedPreferences.getString("LASTNAME", "");
        String address = sharedPreferences.getString("ADDRESS", "");
        String birthdate = sharedPreferences.getString("DATE", "");

        // Find TextViews to display the data

         usernameTextView = findViewById(R.id.usernameEditprofile);
         emailTextView = findViewById(R.id.emailEditprofile);
         firstnameTextView = findViewById(R.id.firstnameEditprofile);
         lastnameTextView = findViewById(R.id.lastnameEditprofile);
         addressTextView = findViewById(R.id.addressEditprofile);
         birthdateTextView = findViewById(R.id.date_pickerEditprofile);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        editprofilebutton  = findViewById(R.id.EditprofileButton);

        // Set the retrieved values to the respective TextViews
        usernameTextView.setText(username);
        lastnameTextView.setText(lastname);
        firstnameTextView.setText(firstname);
        addressTextView.setText(address);
        emailTextView.setText(email);
        try {
            // Parse the date string into a Date object
            Date date = sdf.parse(birthdate);

            // Create a Calendar instance and set the date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // Extract year, month, and day from the Calendar
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH); // Note: Month is zero-based in Calendar
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Set the date in DatePicker
            birthdateTextView.updateDate(year, month, day);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        editprofilebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                handleeditprofile();

                Toast.makeText(EditprofileActivity.this, "Edit successfully!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditprofileActivity.this, ProfileActivity.class);
                startActivity(intent);
                // Finish SignUpActivity so user can't go back to it by pressing back button
                finish();
            }
        });

    }








    private void handleeditprofile() {
        // Get input values
        String username = usernameTextView.getText().toString();
        String firstname = firstnameTextView.getText().toString();
        String lastname = lastnameTextView.getText().toString();
        String address = addressTextView.getText().toString();
        String email = emailTextView.getText().toString();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int day = birthdateTextView.getDayOfMonth();
        int month = birthdateTextView.getMonth();
        int year = birthdateTextView.getYear();

        // Validate input (basic validation example)


        // Create User object and save to database
        User newUser = new User(username, "", firstname, lastname, address, new Date(day,month,year), email);
        newUser.setId(userId);
        Log.d("User ID","this is user id"+ String.valueOf(userId));
        userService = new UserService(this);

        userService.updateUser(newUser);
        saveUserSession(newUser);
        // Notify user of success
        Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show();

        // Clear fields or navigate to login screen
    }

    public void saveUserSession(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("USER_ID", user.getId());
        editor.putString("USERNAME", user.getUsername());
        editor.putString("PASSWORD", user.getPassword());
        editor.putString("FIRSTNAME", user.getFirstname());
        editor.putString("LASTNAME", user.getLastname());
        editor.putString("ADDRESS", user.getAdress());
        editor.putString("EMAIL", user.getEmail());
        editor.putString("DATE",user.getBirthdate().toString());

        editor.apply(); // Save changes
    }

}
