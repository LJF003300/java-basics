package com.ljf.learning.review.week01;

public class Product {
    private final String name;
    private double price;
    private int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        setPrice(price);
        setStock(stock);
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public boolean setPrice(double price) {
        if(price > 0){
            this.price = price;
            return true;
        }
        return false;
    }

    public boolean setStock(int stock) {
        if(stock >= 0){
            this.stock = stock;
            return  true;
        }
        return false;
    }

    public double calculateTotalValue(){
        return price * stock;
    }
}

