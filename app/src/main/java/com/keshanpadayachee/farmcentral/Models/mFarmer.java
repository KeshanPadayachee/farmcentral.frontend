package com.keshanpadayachee.farmcentral.Models;

import java.io.Serializable;

public class mFarmer implements Serializable {

    // Attributes of a Farmer
    private String farmerId;
    private String firstName;
    private String lastName;
    private String passwordSalt;
    private String passwordHash;

    public mFarmer() {
        // EMPTY CONSTRUCTOR DO NOT REMOVE
    }

    // Farmer constructor that accepts all parameters
    public mFarmer(String farmerId, String firstName, String lastName, String passwordSalt, String passwordHash) {
        this.farmerId = farmerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordSalt = passwordSalt;
        this.passwordHash = passwordHash;
    }

    // Getters and setters
    public String getFarmerID() {
        return farmerId;
    }

    public void setFarmerID(String farmerID) {
        this.farmerId = farmerId;
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
