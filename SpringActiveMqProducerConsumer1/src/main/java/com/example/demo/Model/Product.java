package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Product.class)
public class Product {
    private String name;
    private String type;

    private Company company;

    public Product(){
    }

    public Product(String name, String type){
        this.name = name;
        this.type = type;
    }

    public Product(String name, Company company){
        this.name = name;
        this.company = company;
    }

    // name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    // products
    public void setCompany(Company company){
        this.company = company;
    }

    public Company getCompany(){
        return this.company;
    }

    @Override
    public String toString() {
        String info = String.format("[name = %s, type = %s]", this.name, this.type);
        return info;
    }
}
