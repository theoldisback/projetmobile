package com.example.projetmobile.Service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.projetmobile.Database.DatabaseHelper;
import com.example.projetmobile.Model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserService {

    private DatabaseHelper dbHelper;

    public UserService(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Add User
    public void addUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", md5Hash(user.getPassword()));
        values.put("firstname", user.getFirstname());
        values.put("lastname", user.getLastname());
        values.put("adress", user.getAdress());
        values.put("birthdate", dateToString(user.getBirthdate())); // Store date as a string
        values.put("email", user.getEmail());
        if (user.getImage() != null) {
            values.put("image", user.getImage());
        }

        db.insert("User", null, values);
        db.close();
    }

    // Update User
    public void updateUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("firstname", user.getFirstname());
        values.put("lastname", user.getLastname());
        values.put("adress", user.getAdress());
        values.put("birthdate", dateToString(user.getBirthdate()));
        values.put("email", user.getEmail());

        db.update("User", values, "id = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    // Delete User
    public void deleteUser(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("User", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Get User by ID
    @SuppressLint("Range")
    public User getUserById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("User", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setFirstname(cursor.getString(cursor.getColumnIndex("firstname")));
            user.setLastname(cursor.getString(cursor.getColumnIndex("lastname")));
            user.setAdress(cursor.getString(cursor.getColumnIndex("adress")));
            user.setBirthdate(stringToDate(cursor.getString(cursor.getColumnIndex("birthdate"))));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            cursor.close();
            return user;
        }
        return null;
    }

    // Helper method to convert Date to String
    private String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }

    // Helper method to convert String to Date
    private Date stringToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String md5Hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            Log.d("Tag is password", "password");

            Log.d("Tag is password", "password");
            Log.d("Tag is password", "password");
            Log.d("Tag is password", "password");
            Log.d("Tag is password", "password");

            Log.d("md5Hash", sb.toString());
            Log.d("this is password", "password");
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public User getUserByUsernameAndPassword(String username, String hashedPassword) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USER,  // Table name
                null,                         // Select all columns
                DatabaseHelper.COLUMN_USER_USERNAME + "=? AND " + DatabaseHelper.COLUMN_USER_PASSWORD + "=?", // WHERE clause
                new String[]{username, hashedPassword}, // WHERE arguments
                null, null, null);             // Group by, having, order by

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();

            // Retrieve column indices based on column names
            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID);
            int usernameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_USERNAME);
            int passwordIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_PASSWORD);
            int firstnameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_FIRSTNAME);
            int lastnameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_LASTNAME);
            int adressIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ADDRESS);
            int birthdateIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_BIRTHDATE);
            int emailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_EMAIL);

            // Populate user fields from cursor data
            if (idIndex != -1) {
                user.setId(cursor.getInt(idIndex)); // Assuming 'id' is an integer
            }
            if (usernameIndex != -1) {
                user.setUsername(cursor.getString(usernameIndex)); // Assuming 'username' is a String
            }
            if (passwordIndex != -1) {
                user.setPassword(cursor.getString(passwordIndex)); // Assuming 'password' is a String
            }
            if (firstnameIndex != -1) {
                user.setFirstname(cursor.getString(firstnameIndex)); // Assuming 'firstname' is a String
            }
            if (lastnameIndex != -1) {
                user.setLastname(cursor.getString(lastnameIndex)); // Assuming 'lastname' is a String
            }
            if (adressIndex != -1) {
                user.setAdress(cursor.getString(adressIndex)); // Assuming 'adress' is a String
            }
            if (birthdateIndex != -1) {
                // Convert birthdate from String to Date (assuming birthdate is stored as a String in database)
                String birthdateString = cursor.getString(birthdateIndex);
                try {
                    Date birthdate = new SimpleDateFormat("yyyy-MM-dd").parse(birthdateString); // Adjust the date format as needed
                    user.setBirthdate(birthdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (emailIndex != -1) {
                user.setEmail(cursor.getString(emailIndex)); // Assuming 'email' is a String
            }

            cursor.close();
            return user;
        }
        return null;  // If no matching user is found
    }


    public User loginUser(String username, String password) {
        // Hash the entered password
        String hashedPassword = md5Hash(password);
        // Check if the user exists in the database with this hashed password
        User user = getUserByUsernameAndPassword(username, hashedPassword);
        Log.d("USER", user.toString());
        return user;
    }

    public byte[] getUserImage(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Ensure column names and the query are correct
        Cursor cursor = db.query("User", new String[]{"image"}, "id=?",
                new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Ensure the column exists
            int imageColumnIndex = cursor.getColumnIndex("image");
            if (imageColumnIndex != -1) {
                byte[] image = cursor.getBlob(imageColumnIndex);
                cursor.close();
                return image;
            } else {
                Log.e("DatabaseError", "Column 'image' not found in the database.");
                cursor.close();
                return null;
            }
        } else {
            Log.e("DatabaseError", "No data found for the given user ID.");
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }

    @SuppressLint("Range")
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Exclude the 'image' column from this query
        Cursor cursor = db.query("User", new String[]{"id", "username", "firstname", "lastname", "adress", "birthdate", "email"}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                user.setFirstname(cursor.getString(cursor.getColumnIndex("firstname")));
                user.setLastname(cursor.getString(cursor.getColumnIndex("lastname")));
                user.setAdress(cursor.getString(cursor.getColumnIndex("adress")));
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));

                String birthdateString = cursor.getString(cursor.getColumnIndex("birthdate"));
                user.setBirthdate(stringToDate(birthdateString));

                // Add user to the list without the image
                userList.add(user);
            }
            cursor.close();
        }
        db.close();
        return userList;
    }

    @SuppressLint("Range")
    public byte[] getUsereImage(int userIde) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("User", new String[]{"image"}, "id=?", new String[]{String.valueOf(userIde)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
            cursor.close();
            return image;
        }
        return null;
    }
}
