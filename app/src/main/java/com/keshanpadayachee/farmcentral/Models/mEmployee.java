package com.keshanpadayachee.farmcentral.Models;

import java.io.Serializable;

public class mEmployee implements Serializable {

    // Attributes of a Employee
    private String employeeId;
    private String firstName;
    private String lastName;
    private String passwordSalt;
    private String passwordHash;

    public mEmployee() {
        // EMPTY CONSTRUCTOR DO NOT REMOVE
    }

    // Employee constructor that accepts parameters
    public mEmployee(String employeeId, String firstName, String lastName, String passwordSalt, String passwordHash) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordSalt = passwordSalt;
        this.passwordHash = passwordHash;
    }


    // Getters and Setters
    public String getEmployeeID() {
        return employeeId;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
