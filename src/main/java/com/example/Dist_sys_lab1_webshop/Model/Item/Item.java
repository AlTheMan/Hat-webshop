package com.example.Dist_sys_lab1_webshop.Model.Item;

import com.example.Dist_sys_lab1_webshop.Database.ItemDB;

import java.util.Collection;

public class Item {

	private int id;
	private String name;
	private String description;
	private double price;
	private int quantity;
	private String imagesrc;


	protected Item(int id, String name, String description, double price, int quantity, String imagesrc) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.imagesrc=imagesrc;
	}

	public static Collection<Item> getDBItemsWithCategory(String category) throws NoSuchMethodException {
		throw new NoSuchMethodException();
	}

	public static Collection<Item> getDBItemsAll() {
		return ItemDB.getDBItemsAll();
	}

	public int getId() {
		return id;
	}

	public static Item getDBItemByID(int id){return ItemDB.getDBItemByID(id);} //TODO: skicka tillbaka en kopia istället?

	public String getName() {
		return name;
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
