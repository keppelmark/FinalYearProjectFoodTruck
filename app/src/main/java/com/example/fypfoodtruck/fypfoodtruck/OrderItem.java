package com.example.fypfoodtruck.fypfoodtruck;

import java.util.ArrayList;

public class OrderItem {
    String orderId;
    String item;
    String quantity;

    public OrderItem(String orderId, String item, String quantity) {
        this.orderId = orderId;
        this.item = item;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
