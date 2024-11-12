package com.example.projetmobile.View.Userview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projetmobile.R;
import com.example.projetmobile.Service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfilerFragment  extends Fragment {

    private ImageView imageView;
    private Button editButton;
    private String formattedDate;

    public ProfilerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_layout, container, false);

        UserService userService = new UserService(getActivity());

        // Retrieve the SharedPreferences object
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSession", getActivity().MODE_PRIVATE);

        // Get the user details from SharedPreferences
        int userId = sharedPreferences.getInt("USER_ID", -1); // Default is -1 if not found
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

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Find TextViews to display the data
        TextView firstAndLastTextView = view.findViewById(R.id.firstnamelastname);
        editButton = view.findViewById(R.id.editimagebutton);
        TextView usernameTextView = view.findViewById(R.id.profileusername);
        TextView emailTextView = view.findViewById(R.id.profileemail);
        TextView firstnameTextView = view.findViewById(R.id.profileufirstname);
        TextView lastnameTextView = view.findViewById(R.id.profileulastname);
        TextView addressTextView = view.findViewById(R.id.profileadress);
        TextView birthdateTextView = view.findViewById(R.id.profilebirthday);

        byte[] imageBytes = userService.getUserImage(userId);
        Bitmap userImage = byteArrayToBitmap(imageBytes);
        imageView = view.findViewById(R.id.imageviewuser);  // Replace with your actual ImageView ID

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
        firstAndLastTextView.setText(firstname + " " + lastname);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a new activity
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, editProfileFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    public Bitmap byteArrayToBitmap(byte[] imageBytes) {
        if (imageBytes != null) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }
        return null;
    }
}