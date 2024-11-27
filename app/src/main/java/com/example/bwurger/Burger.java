package com.example.bwurger;

import java.io.Serializable;
import java.util.ArrayList;

public class Burger implements Serializable {
    private String name;
    private ArrayList<String> ingredients;
    private int imageResId; // ID resource untuk gambar burger

    public Burger() { } // Diperlukan untuk Firebase

    public Burger(String name, ArrayList<String> ingredients, int imageResId) {
        this.name = name;
        this.ingredients = ingredients;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public int getImageResId() {
        return imageResId; // Getter untuk ID resource gambar
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}