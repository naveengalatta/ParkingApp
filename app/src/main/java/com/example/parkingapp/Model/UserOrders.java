package com.example.parkingapp.Model;

public class UserOrders {

    private String orderID;

    public UserOrders() {
    }

    public UserOrders(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
