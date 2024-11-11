package com.example.projetmobile.View.Userview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetmobile.R;
import com.example.projetmobile.Service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    ImageView imageView;
    Button editbutton;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_layout);
       UserService userService = new UserService(this);
        // Retrieve the SharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        // Get the user details from SharedPreferences
        int userId = sharedPreferences.getInt("USER_ID", -1); // Default is -1 if not found username = sharedPreferences.getString("USERNAME", ""); // Default is an empty string if not found
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy"); // Adjust format if necessary

        String username = sharedPreferences.getString("USERNAME", ""); // Default is an empty string if not found
        String email = sharedPreferences.getString("EMAIL", "");
        String firstname = sharedPreferences.getString("FIRSTNAME", "");
        String lastname = sharedPreferences.getString("LASTNAME", "");
        String address = sharedPreferences.getString("ADDRESS", "");
        String birthdate = sharedPreferences.getString("DATE", "");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Parse the date string into a Date object
            Date date = inputFormat.parse(birthdate);

            // Format the date to "yyyy-MM-dd"
             formattedDate = outputFormat.format(date);

            // Use the formatted date as needed
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Find TextViews to display the data
        TextView firstandlastTextView = findViewById(R.id.firstnamelastname);
        editbutton = findViewById(R.id.editimagebutton);
        TextView usernameTextView = findViewById(R.id.profileusername);
        TextView emailTextView = findViewById(R.id.profileemail);
        TextView firstnameTextView = findViewById(R.id.profileufirstname);
        TextView lastnameTextView = findViewById(R.id.profileulastname);
        TextView addressTextView = findViewById(R.id.profileadress);
        TextView birthdateTextView = findViewById(R.id.profilebirthday);
        byte[] imageBytes = userService.getUserImage(userId);
        Bitmap userImage = byteArrayToBitmap(imageBytes);
        ImageView imageView = findViewById(R.id.imageviewuser);  // Replace with your actual ImageView ID
        if (userImage != null) {
            imageView.setImageBitmap(userImage);
        } else {
            // Handle the case when no image is found, e.g., display a default image
            imageView.setImageResource(R.drawable.user);  // Replace with your default image
        }

        // Set the retrieved values to the respective TextViews
        usernameTextView.setText(username);
        emailTextView.setText(email);
        firstnameTextView.setText(firstname);
        lastnameTextView.setText(lastname);
        addressTextView.setText(address);
        birthdateTextView.setText(formattedDate);
        firstandlastTextView.setText(firstname+"  "+lastname);

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a new activity
                Intent intent = new Intent(ProfileActivity.this, EditprofileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public Bitmap byteArrayToBitmap(byte[] imageBytes) {
        if (imageBytes != null) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }
        return null;
    }
}
