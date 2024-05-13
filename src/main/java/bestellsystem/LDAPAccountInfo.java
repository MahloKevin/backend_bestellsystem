package bestellsystem;

import org.jetbrains.annotations.NotNull;

public class LDAPAccountInfo {

    private final String userName;
    private final String firstName;
    private final String lastName;
    private final String department;

    public LDAPAccountInfo(String userName, String firstName, String lastName, String department) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    @NotNull
    public String getUserName() {
        return this.userName;
    }

    @NotNull
    public String getFirstName() {
        return this.firstName;
    }

    @NotNull
    public String getLastName() {
        return this.lastName;
    }

    @NotNull
    public String getDepartment() {
        return this.department;
    }

    @Override
    public String toString() {
        return this.userName + " (" + this.firstName + " " + this.lastName + "): " + this.department;
    }

}