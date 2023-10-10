package com.example.Dist_sys_lab1_webshop.Model.Item;

import com.example.Dist_sys_lab1_webshop.Database.CategoryDB;

import java.util.ArrayList;

public class CategoryHandler {

    
    public static void addCategory(String category){
        CategoryDB.addCategory(category);
    }

    public static void removeCategoryById(int id){
        CategoryDB.removeCategoryById(id);
    }
    public static ArrayList<Category> getCategories() {
        return CategoryDB.getAllCategories(); //TODO: returnera en kopia
    }

}
