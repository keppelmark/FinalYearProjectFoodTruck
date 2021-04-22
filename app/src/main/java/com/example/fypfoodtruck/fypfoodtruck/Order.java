package com.example.fypfoodtruck.fypfoodtruck;

public class Order {
    String customerId;
    String businessId;
    String date;
    int status;

    public Order() {
        //public no-arg constructor needed
    }

    public Order(String customerId, String businessId, String date, int status) {
        this.customerId = customerId;
        this.businessId = businessId;
        this.date = date;
        this.status = status;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}


