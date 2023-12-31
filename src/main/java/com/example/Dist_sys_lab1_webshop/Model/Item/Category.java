package com.example.Dist_sys_lab1_webshop.Model.Item;

public class Category {
    private String category;
    private int categoryId;

    public Category(String category, int categoryId) {
        this.category = category;
        this.categoryId = categoryId;
    }

    protected Category(){

    }

    public static Category getCategoryCopy(Category category){
	    return new Category(category.category, category.categoryId);
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
