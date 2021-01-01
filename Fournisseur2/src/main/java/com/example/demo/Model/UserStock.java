package com.example.demo.Model;

import java.util.List;

public class UserStock {
    private List<Fournisseur> userStock;

    public List<Fournisseur> getUserStock() {
        return userStock;
    }

    public void setUserStock(List<Fournisseur> userStock) {
        this.userStock = userStock;
    }
}
