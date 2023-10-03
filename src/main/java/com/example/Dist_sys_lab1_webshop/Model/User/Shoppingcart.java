package com.example.Dist_sys_lab1_webshop.Model.User;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;

import java.util.ArrayList;

public class Shoppingcart {
    private ArrayList<ShoppingItem> items;

    public Shoppingcart() {
        this.items = new ArrayList<>();
    }

    public ArrayList<ShoppingItem> getItems() {
        return items;
    }

    public void emptyCart(){
        items.clear();
    }

    public void addItems(Item item, int nrOfItems) {
        int index = items.indexOf(item);
        if(index!=-1){                  //found matching object
            int newNrOfItems = nrOfItems + items.get(index).getNrOfItems();
            items.set(index,new ShoppingItem(item,newNrOfItems));
        }
        items.add(new ShoppingItem(item, nrOfItems));
    }

}
