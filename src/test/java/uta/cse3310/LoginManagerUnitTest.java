package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.List;
import java.util.ArrayList;

public class LoginManagerUnitTest extends TestCase {
    private LoginManager loginManager;

    public LoginManagerUnitTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(LoginManagerUnitTest.class);
    }

    @Override
    public void setUp() {
        loginManager = new LoginManager(); // Initialize LoginManager before each test
    }

    public void testRegisterUser() {
        // Test registering a new user
        assertTrue(loginManager.registerUser("User1"));
        assertTrue(loginManager.registerUser("User2"));

        // Test registering a duplicate user
        assertFalse(loginManager.registerUser("User1"));
    }

    public void testGetRegisteredUsers() {
        // Test getting registered users
        loginManager.registerUser("User1");
        loginManager.registerUser("User2");

        List<String> registeredUsers = loginManager.getRegisteredUsers();
        assertEquals(2, registeredUsers.size());
        assertTrue(registeredUsers.contains("User1"));
        assertTrue(registeredUsers.contains("User2"));
    }

    public void testRemoveUser() {
         // Initialize LoginManager
     LoginManager loginManager = new LoginManager();
        // Test removing a registered user
        loginManager.registerUser("User1");
        loginManager.registerUser("User2");

        assertTrue(loginManager.removeUser("User1"));
        assertFalse(loginManager.removeUser("User1")); // Ensure removing the same user again fails
        assertEquals(1, loginManager.currentGameSize); // Check if the game size has been decremented
        // Attempt to remove the same user again (should fail)
        assertFalse(loginManager.removeUser("user1"));

        // Test removing a non-existent user
        assertFalse(loginManager.removeUser("NonExistentUser"));
    }
}
