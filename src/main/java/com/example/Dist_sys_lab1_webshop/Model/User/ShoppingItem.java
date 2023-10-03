package com.example.Dist_sys_lab1_webshop.Model.User;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;

public class ShoppingItem {
    private Item item;
    private int nrOfItems;
    public ShoppingItem() {
        this.item = null;
        this.nrOfItems = 0;
    }

    public ShoppingItem(Item item, int nrOfItems) {
        this.item = item;
        this.nrOfItems = nrOfItems;
    }

    public Item getItem() {
        return item;
    }

    public int getNrOfItems() {
        return nrOfItems;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setNrOfItems(int nrOfItems) {
        this.nrOfItems = nrOfItems;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" +
                "item=" + item +
                ", nrOfItems=" + nrOfItems +
                '}';
    }
}
