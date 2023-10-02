package com.example.Dist_sys_lab1_webshop.Model.Item;

public class Item {

	private int id;
	private String name;
	private String description;
	private double price;
	private int quantity;


	public Item(int id, String name, String description, double price, int quantity) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

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
}
