package com.example.latepay;

import java.io.Serializable;

public class ListElementCustomer implements Serializable {
    public long customer_id;
    public String first_name;
    public String last_name;
    public String phone;
    public String email;
    public String address;
    public String created_date;
    public String total_debt;
    public String color;

    public ListElementCustomer(long customer_id, String first_name, String last_name, String phone, String email, String address, String created_date, String total_debt, String color) {
        this.customer_id = customer_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.created_date = created_date;
        this.total_debt = total_debt;
        this.color = color;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getTotal_debt() {
        return total_debt;
    }

    public void setTotal_debt(String total_debt) {
        this.total_debt = total_debt;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
