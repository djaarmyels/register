package com.example.cashier_app;

import android.widget.TextView;

public class Product {
    String name;
    String amount;
    String price;

    public Product(String name,String price,String amount) {
        this.name=name;
        this.price=price;
        this.amount=amount;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
