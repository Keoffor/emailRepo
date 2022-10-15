package com.example.emailSendGrid;

public class EmployeeResponse {
    private int id;
    private String firstName;
    private String email;

    public EmployeeResponse() {
    }

    public EmployeeResponse(int id, String firstName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.email = email;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
