package com.example.Dist_sys_lab1_webshop.Model.Item;

import com.example.Dist_sys_lab1_webshop.Database.CategoryDB;

import java.util.ArrayList;

public class CategoryHandler {

    
    public static void addCategory(String category){
        if (category != null) {
            CategoryDB.addCategory(category);
        }
    }

    public static void removeCategoryById(String categoryId){
        if (categoryId == null) return;

        int id = Integer.parseInt(categoryId);
        CategoryDB.removeCategoryById(id);
    }
    public static ArrayList<Category> getCategories() {
        return CategoryDB.getAllCategories(); //TODO: returnera en kopia
    }

}
