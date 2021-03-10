package com.example.fypfoodtruck.fypfoodtruck;

import com.google.firebase.firestore.Exclude;
public class Business {
    private String documentId;
    private String name;
    private String category;
    private String address;
    private String county;
    private String number;
    private String website;
    private String userId;
    public Business() {
        //public no-arg constructor needed
    }
    @Exclude
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    public Business(String name, String category, String address, String county, String number, String website, String userId) {
        this.name = name;
        this.category = category;
        this.address = address;
        this.county = county;
        this.number = number;
        this.website = website;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }


    public String getCategory() {
        return category;
    }


    public String getAddress() {
        return address;
    }


    public String getCounty() {
        return county;
    }

    public String getNumber() {
        return number;
    }

    public String getWebsite() {
        return website;
    }

    public String getUserId() {
        return userId;
    }
}
