package com.example.latepay;

public class ListElementDebt {
    public long debt_id;
    public String product;
    public String price;
    public String paid;
    public String buy_date;
    public String pay_date;
    public long customer_id;
    public String color;

    public ListElementDebt(long debt_id, String product, String price, String paid, String buy_date, String pay_date, long customer_id, String color) {
        this.debt_id = debt_id;
        this.product = product;
        this.price = price;
        this.paid = paid;
        this.buy_date = buy_date;
        this.pay_date = pay_date;
        this.customer_id = customer_id;
        this.color = color;
    }

    public ListElementDebt() {

    }

    public long getDebt_id() {
        return debt_id;
    }

    public void setDebt_id(long debt_id) {
        this.debt_id = debt_id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String isPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getBuy_date() {
        return buy_date;
    }

    public void setBuy_date(String buy_date) {
        this.buy_date = buy_date;
    }

    public String getPay_date() {
        return pay_date;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
