package com.example.fypfoodtruck.fypfoodtruck;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

public class Item {

    private String documentId;
    private String product;
    private String price;
    private String description;
    private String duration;


    public Item() {
        //public no-arg constructor needed
    }

    public Item(String product, String price, String description, String duration) {
        this.product = product;
        this.price = price;
        this.description = description;
        this.duration = duration;
    }


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getProduct() {
        return product;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() { return duration; }
}

