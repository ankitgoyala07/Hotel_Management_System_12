package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * [Test 1 of 5] JUnit test for `users` table.
 * Exact record: Username="Manager", Email="manager@gmail.com", Phone="9845745230", Password="1234", Role="Manager", SecurityQuestion="soft"
 */
public class UsersTableTest {

    @Test
    public void testUsersTableRecord() {
        signupModel user = new signupModel("Manager", "manager@gmail.com", "9845745230", "1234", "Manager", "soft");

        assertEquals("Username must match users table record", "Manager", user.getUsername());
        assertEquals("Email must match users table record", "manager@gmail.com", user.getEmail());
        assertEquals("Phone must match users table record", "9845745230", user.getPhone());
        assertEquals("Password must match users table record", "1234", user.getPassword());
        assertEquals("Role must match users table record", "Manager", user.getRole());
        assertEquals("Security question must match users table record", "soft", user.getSecurityQuestion());
    }
}
