package com.example.fypfoodtruck.fypfoodtruck;

public class OrderItem {
    String orderId;
    String itemName;
    String item;
    String quantity;

    public OrderItem(String orderId, String itemName, String item, String quantity) {
        this.orderId = orderId;
        this.itemName = itemName;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
