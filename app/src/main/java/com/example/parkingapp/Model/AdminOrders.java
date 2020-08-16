package com.example.parkingapp.Model;

public class AdminOrders {

    //private String AdminBookingID;

    public AdminOrders() {
    }

   /* public AdminOrders(String adminBookingID) {

        this.AdminBookingID = adminBookingID;

    }

    public String getAdminBookingID() {
        return AdminBookingID;
    }

    public void setAdminBookingID(String adminBookingID) {
        AdminBookingID = adminBookingID;
    }*/

    private String orderID;

    public AdminOrders(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
