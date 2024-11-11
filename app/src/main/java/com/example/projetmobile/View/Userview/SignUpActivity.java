package com.example.projetmobile.View.Userview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projetmobile.Model.User;
import com.example.projetmobile.R;
import com.example.projetmobile.Service.UserService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_IMAGE = 1001;
    private static final int REQUEST_CODE_PERMISSION = 1002;
    private EditText usernameEditText, passwordEditText, firstnameEditText, lastnameEditText, addressEditText, emailEditText;
    private Button signUpButton,selectImageButton;
    private UserService userService;
    DatePicker datePicker;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri = null;  // To hold the URI of the selected image
    private ImageView selectedImageView;
    Bitmap bitmapimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        datePicker = findViewById(R.id.date_picker);
        selectImageButton = findViewById(R.id.selectImageButton);
        selectedImageView = findViewById(R.id.selectedImageView);

        // Initialize UserService
        userService = new UserService(this);

        // Link UI elements
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        firstnameEditText = findViewById(R.id.firstnameEditText);
        lastnameEditText = findViewById(R.id.lastnameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        emailEditText = findViewById(R.id.emailadresstext);

        signUpButton = findViewById(R.id.signUpButton);

        selectImageButton.setOnClickListener(v -> openImagePicker());

        // Set onClickListener for Sign-Up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                // Finish SignUpActivity so user can't go back to it by pressing back button
                finish();
            }
        });
    }


    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            // Display the selected image in the ImageView
            if (selectedImageUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    selectedImageView.setImageBitmap(bitmap);
                    bitmapimage= bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void handleSignUp() {
        // Get input values
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String firstname = firstnameEditText.getText().toString();
        String lastname = lastnameEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String email = emailEditText.getText().toString();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap resizedBitmap = resizeImage(bitmapimage, 800, 600); // Resize to a reasonable size
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream); // Compress the image to 80% quality
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byte[] imageBytes =  byteArrayOutputStream.toByteArray();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        // Validate input (basic validation example)
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create User object and save to database
        User newUser = new User(username, password, firstname, lastname, address, new Date(day,month,year), email,imageBytes);
        userService.addUser(newUser);

        // Notify user of success
        Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show();

        // Clear fields or navigate to login screen
        clearFields();
    }

    private void clearFields() {
        usernameEditText.setText("");
        passwordEditText.setText("");
        firstnameEditText.setText("");
        lastnameEditText.setText("");
        addressEditText.setText("");
        emailEditText.setText("");
    }

    public byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public Bitmap resizeImage(Bitmap originalImage, int maxWidth, int maxHeight) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        float ratioBitmap = (float) width / (float) height;
        float ratioMax = (float) maxWidth / (float) maxHeight;

        int finalWidth = maxWidth;
        int finalHeight = maxHeight;

        if (ratioMax > ratioBitmap) {
            finalWidth = (int) (maxHeight * ratioBitmap);
        } else {
            finalHeight = (int) (maxWidth / ratioBitmap);
        }

        return Bitmap.createScaledBitmap(originalImage, finalWidth, finalHeight, true);
    }

}
