package com.example.latepay.entidades;

public class Cliente {
    private long customer_id;
    private String first_name;
    private String last_name;
    private String phone;
    private String address;
    private String created_date;
    private double total_debt;

    public Cliente(long customer_id, String first_name, String last_name, String phone, String address, String created_date, double total_debt) {
        this.customer_id = customer_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.address = address;
        this.created_date = created_date;
        this.total_debt = total_debt;
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

    public double getTotal_debt() {
        return total_debt;
    }

    public void setTotal_debt(double total_debt) {
        this.total_debt = total_debt;
    }
}
