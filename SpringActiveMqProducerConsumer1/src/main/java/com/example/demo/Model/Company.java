package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Company.class)
public class Company {
    private String name;

    private List<Product> products;

    public Company(){
    }

    public Company(String name, List<Product> products){
        this.name = name;
        this.products = products;
    }

    // name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // products
    public void setProducts(List<Product> products){
        this.products = products;
    }

    public List<Product> getProducts(){
        return this.products;
    }
}
