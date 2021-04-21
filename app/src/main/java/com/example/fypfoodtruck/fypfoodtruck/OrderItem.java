package com.example.fypfoodtruck.fypfoodtruck;

import java.util.ArrayList;

public class OrderItem {
    /*String orderId;*/
    String item;
    int quantity;

    public OrderItem(String item, int quantity) {
        /*this.orderId = orderId;*/
        this.item = item;
        this.quantity = quantity;
    }

   /* public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }*/

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
