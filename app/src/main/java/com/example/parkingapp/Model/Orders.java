package com.example.parkingapp.Model;

public class Orders {

    private String bookingdate, category, noofhours, orderid, parkingaddress, parkingname, totalamount, username, userphonenumber, vehicleno;

    public Orders() {

    }

    public Orders(String bookingdate, String category, String noofhours, String orderid, String parkingaddress, String parkingname, String totalamount, String username, String userphonenumber, String vehicleno) {

        this.bookingdate = bookingdate;
        this.category = category;
        this.noofhours = noofhours;
        this.orderid = orderid;
        this.parkingaddress = parkingaddress;
        this.parkingname = parkingname;
        this.totalamount = totalamount;
        this.username = username;
        this.userphonenumber = userphonenumber;
        this.vehicleno = vehicleno;

    }

    public String getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        this.bookingdate = bookingdate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNoofhours() {
        return noofhours;
    }

    public void setNoofhours(String noofhours) {
        this.noofhours = noofhours;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getParkingaddress() {
        return parkingaddress;
    }

    public void setParkingaddress(String parkingaddress) {
        this.parkingaddress = parkingaddress;
    }

    public String getParkingname() {
        return parkingname;
    }

    public void setParkingname(String parkingname) {
        this.parkingname = parkingname;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphonenumber() {
        return userphonenumber;
    }

    public void setUserphonenumber(String userphonenumber) {
        this.userphonenumber = userphonenumber;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }
}
