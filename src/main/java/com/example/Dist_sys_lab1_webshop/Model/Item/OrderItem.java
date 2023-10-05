package com.example.Dist_sys_lab1_webshop.Model.Item;

public class OrderItem {
    private Item item;
    private int nrOfItems;

    public OrderItem(Item item, int nrOfItems) {
        this.item = item;
        this.nrOfItems = nrOfItems;
    }

    public Item getItem() {
        return item;
    }

    public int getNrOfItems() {
        return nrOfItems;
    }
}
