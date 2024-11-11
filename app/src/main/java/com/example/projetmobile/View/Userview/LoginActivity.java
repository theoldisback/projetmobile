package com.example.projetmobile.View.Userview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetmobile.MainActivity;
import com.example.projetmobile.Model.User;
import com.example.projetmobile.R;
import com.example.projetmobile.Service.UserService;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        usernameEditText = findViewById(R.id.usernamelogin);
        passwordEditText = findViewById(R.id.passwordlogin);
        loginButton = findViewById(R.id.signInButton);
        userService = new UserService(this);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (userService.loginUser(username, password) != null) {
                    // Login successful
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    // Redirect to main page or dashboard
                    User user = userService.loginUser(username, password);

                    saveUserSession(user);
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    // Finish SignUpActivity so user can't go back to it by pressing back button
                    finish();
                    return;
                } else {
                    // Login failed
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

                }
            }
        });
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
