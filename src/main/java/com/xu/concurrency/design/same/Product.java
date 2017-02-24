package com.xu.concurrency.design.same;


public final class Product {
    private final String no;
    private final String name;
    private final String price;

    public Product(String no, String name, String price) {
        this.no = no;
        this.name = name;
        this.price = price;
    }

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

}
