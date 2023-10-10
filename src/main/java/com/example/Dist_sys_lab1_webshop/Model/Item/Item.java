package com.example.Dist_sys_lab1_webshop.Model.Item;

import com.example.Dist_sys_lab1_webshop.Database.ItemDB;

import java.util.ArrayList;
import java.util.Collection;

public class Item {

	private int id;
	private String name;
	private String description;
	private double price;
	private int quantity;
	private String imagesrc;
	//private String category;
	//private int categoryId;

	private Category category;


	protected Item(int id, String name, String description, double price, int quantity, String imagesrc, Category category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.imagesrc=imagesrc;
		this.category=category;

	}




	protected Item(String name, String description, double price, int quantity, String imagesrc, Category category) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.imagesrc = imagesrc;
		this.category=category;
	}

	protected Item() {
		this.category = new Category();
	}

	public int getCategoryId() {
		return category.getCategoryId();
	}

	public void setCategoryId(int categoryId) {
		this.category.setCategoryId(categoryId);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setImagesrc(String imagesrc) {
		this.imagesrc = imagesrc;
	}

	public static Collection<Item> getDBItemsWithCategory(String category) throws NoSuchMethodException {
		throw new NoSuchMethodException();
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public String getCategoryName() {
		return category.getCategory();
	}

	public static ArrayList<Item> getDBItemsAll() {

		return ItemDB.getDBItemsAll();
	}

	public int getId() {
		return id;
	}

	public static Item getDBItemByID(int id){
		return ItemDB.getDBItemByID(id);
	}

	public String getName() {
		return name;
	}

	protected static void updateItemById(Item updateItem) {
		ItemDB.updateItemByIdPrepared(updateItem);
	}

	protected static void addItemToDB(Item item) {
		ItemDB.addItemToDB(item);
	}

	protected static void removeItemById(int id) {
		ItemDB.removeItemById(id);
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getImagesrc() {
		return imagesrc;
	}



}
