package controller;

/**
 * Factory class to instantiate the appropriate UserRole based on role name.
 */
public class RoleFactory {
    /**
     * Resolves the string role to a polymorphic UserRole implementation.
     *
     * @param roleName Name of the role (e.g. Admin, Manager, Guest, Frontdesk)
     * @return the resolved UserRole instance
     */
    public static UserRole getRole(String roleName) {
        if (roleName != null) {
            String roleTrimmed = roleName.trim().toLowerCase();
            if (roleTrimmed.equals("manager") || roleTrimmed.equals("admin")) {
                return new AdminRole();
            } else if (roleTrimmed.equals("guest")) {
                return new GuestRole();
            }
        }
        // Default fallback role is Frontdesk
        return new FrontdeskRole();
    }
}
