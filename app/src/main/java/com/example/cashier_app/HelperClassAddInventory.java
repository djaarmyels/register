package com.example.cashier_app;


public class HelperClassAddInventory {
    String name;
    Double price;
    Double priceb;




    public HelperClassAddInventory(String name,  Double price,Double priceb){
        this.name=name;



        this.price =price;
        this.priceb =priceb;


    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getPriceb() {
        return priceb;
    }
    public void setPriceb(Double priceb) {
        this.priceb = priceb;
    }





}
