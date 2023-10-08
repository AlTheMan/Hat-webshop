package com.example.Dist_sys_lab1_webshop.Model.Item;

import com.example.Dist_sys_lab1_webshop.Database.CategoryDB;

import java.util.ArrayList;

public class CategoryHandler {
    ArrayList<Category> categories;

    public CategoryHandler() {
        this.categories = new ArrayList<>();
    }

    public CategoryHandler(ArrayList<Category> categories) {
        this.categories = categories;
    }


    public static ArrayList<Category> getCategories() {
        return CategoryDB.getAllCategories(); //TODO: returnera en kopia
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
