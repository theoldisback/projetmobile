package com.example.projetmobile;
import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

import com.example.projetmobile.Database.DatabaseHelper;
import com.example.projetmobile.Model.User;
import com.example.projetmobile.Service.UserService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class UserServiceTest {

    private UserService userService;
    private DatabaseHelper dbHelper;
    private Context context;

    @Before
    public void setUp() {
        // Initialize context, dbHelper, and userService
        context = ApplicationProvider.getApplicationContext();
        dbHelper = new DatabaseHelper(context);
        userService = new UserService(context);

        // Clear database before each test
        dbHelper.getWritableDatabase().execSQL("DELETE FROM " + DatabaseHelper.TABLE_USER);
    }

    @After
    public void tearDown() {
        dbHelper.close();
    }

    @Test
    public void testAddUser() {
        User user = new User("testuser", "password", "John", "Doe", "123 Street", null, "john@example.com");
        userService.addUser(user);

        User retrievedUser = userService.getUserById(1);
        assertNotNull(retrievedUser);
        assertEquals("testuser", retrievedUser.getUsername());
    }

    @Test
    public void testUpdateUser() {
        User user = new User("testuser", "password", "John", "Doe", "123 Street", null, "john@example.com");
        userService.addUser(user);

        User retrievedUser = userService.getUserById(1);
        retrievedUser.setLastname("Smith");
        userService.updateUser(retrievedUser);

        User updatedUser = userService.getUserById(1);
        assertEquals("Smith", updatedUser.getLastname());
    }

    @Test
    public void testDeleteUser() {
        User user = new User("testuser", "password", "John", "Doe", "123 Street", null, "john@example.com");
        userService.addUser(user);

        userService.deleteUser(1);
        User deletedUser = userService.getUserById(1);
        assertNull(deletedUser);
    }

    @Test
    public void testGetUserById() {
        User user = new User("testuser", "password", "John", "Doe", "123 Street", null, "john@example.com");
        userService.addUser(user);

        User retrievedUser = userService.getUserById(1);
        assertNotNull(retrievedUser);
        assertEquals("john@example.com", retrievedUser.getEmail());
    }
}
