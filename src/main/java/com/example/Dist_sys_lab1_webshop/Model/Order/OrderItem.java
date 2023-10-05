package com.example.Dist_sys_lab1_webshop.Model.Order;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;

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

    @Override
    public String toString() {
        return "itemName:" + item.getName() +
                ", itemID:" + item.getId() +
                ", nrOfItems=" + nrOfItems;
    }
}
