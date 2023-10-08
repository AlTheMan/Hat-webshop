package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.Item.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryDB extends Category {

    public CategoryDB(String category, int categoryId) {
        super(category, categoryId);
    }

    public static ArrayList<Category> getAllCategories() {
        Connection con = DBManager.getConnection();
        ArrayList<Category> categoryCollection = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            String query = "select * from categories;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String category = resultSet.getString("category");
                categoryCollection.add(new Category(category, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryCollection;
    }
}
