package com.example.projetmobile.View.Userview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projetmobile.Model.User;
import com.example.projetmobile.R;
import com.example.projetmobile.Service.UserService;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditProfileFragment extends Fragment {
    private Button editprofilebutton;
    private DatePicker birthdateTextView;
    private EditText usernameTextView, emailTextView, firstnameTextView, lastnameTextView, addressTextView;
    private UserService userService;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.editprofile_layout, container, false);

        userService = new UserService(getActivity());

        // Retrieve the SharedPreferences object
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSession", getActivity().MODE_PRIVATE);

        // Get the user details from SharedPreferences
        userId = sharedPreferences.getInt("USER_ID", -1); // Default is -1 if not found

        String username = sharedPreferences.getString("USERNAME", "");
        String email = sharedPreferences.getString("EMAIL", "");
        String firstname = sharedPreferences.getString("FIRSTNAME", "");
        String lastname = sharedPreferences.getString("LASTNAME", "");
        String address = sharedPreferences.getString("ADDRESS", "");
        String birthdate = sharedPreferences.getString("DATE", "");

        // Initialize views
        usernameTextView = rootView.findViewById(R.id.usernameEditprofile);
        emailTextView = rootView.findViewById(R.id.emailEditprofile);
        firstnameTextView = rootView.findViewById(R.id.firstnameEditprofile);
        lastnameTextView = rootView.findViewById(R.id.lastnameEditprofile);
        addressTextView = rootView.findViewById(R.id.addressEditprofile);
        birthdateTextView = rootView.findViewById(R.id.date_pickerEditprofile);
        editprofilebutton = rootView.findViewById(R.id.EditprofileButton);

        // Set the retrieved values to the respective TextViews
        usernameTextView.setText(username);
        lastnameTextView.setText(lastname);
        firstnameTextView.setText(firstname);
        addressTextView.setText(address);
        emailTextView.setText(email);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Parse the date string into a Date object
            Date date = sdf.parse(birthdate);

            // Create a Calendar instance and set the date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // Extract year, month, and day from the Calendar
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH); // Month is zero-based
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Set the date in DatePicker
            birthdateTextView.updateDate(year, month, day);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Set OnClickListener for the Edit Profile button
        editprofilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEditProfile();
                DashboarduserFragment dashboarduserFragment = new DashboarduserFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, dashboarduserFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                Toast.makeText(getActivity(), "Edit successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void handleEditProfile() {
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
        User newUser = new User(username, "", firstname, lastname, address, new Date(day, month, year), email);
        newUser.setId(userId);
        Log.d("User ID", "this is user id" + String.valueOf(userId));
        userService.updateUser(newUser);
        saveUserSession(newUser);

        // Notify user of success
        Toast.makeText(getActivity(), "User registered successfully!", Toast.LENGTH_SHORT).show();
    }

    public void saveUserSession(User user) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSession", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("USER_ID", user.getId());
        editor.putString("USERNAME", user.getUsername());
        editor.putString("PASSWORD", user.getPassword());
        editor.putString("FIRSTNAME", user.getFirstname());
        editor.putString("LASTNAME", user.getLastname());
        editor.putString("ADDRESS", user.getAdress());
        editor.putString("EMAIL", user.getEmail());
        editor.putString("DATE", user.getBirthdate().toString());

        editor.apply(); // Save changes
    }
}