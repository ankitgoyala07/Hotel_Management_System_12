package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * [Test 3 of 5] JUnit test for `staff` table.
 * Exact record: StaffID="H-101", Name="Ritesh Chand", Phone="9845698562", Email="ritesh@gmail.com", Address="Dillibazar", Role="Chef", Shift="Day"
 */
public class StaffTableTest {

    @Test
    public void testStaffTableRecord() {
        StaffManagementModel staff = new StaffManagementModel( "H-101", "Ritesh Chand", "9845698562", "ritesh@gmail.com",
                "Dillibazar", "Chef", "Day");

        assertEquals("Staff ID must match staff table record", "H-101", staff.getStaffId());
        assertEquals("Name must match staff table record", "Ritesh Chand", staff.getName());
        assertEquals("Phone must match staff table record", "9845698562", staff.getPhone());
        assertEquals("Email must match staff table record", "ritesh@gmail.com", staff.getEmail());
        assertEquals("Address must match staff table record", "Dillibazar", staff.getAddress());
        assertEquals("Role must match staff table record", "Chef", staff.getRole());
        assertEquals("Shift must match staff table record", "Day", staff.getShift());
    }
}
