package com.example.fypfoodtruck.fypfoodtruck;

import com.google.firebase.firestore.Exclude;

public class Customer {
    private String documentId;

    private String fullName;
    private String phoneNumber;
    private String userEmail;
    private String userId;

    public Customer() {
        //public no-arg constructor needed
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Customer(String fullName, String phoneNumber, String userEmail, String userId) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getUserEmail() {
        return userEmail;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}


