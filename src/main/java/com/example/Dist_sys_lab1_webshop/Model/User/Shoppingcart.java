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
        for(int i =0; i<items.size(); i++){
            if(items.get(i).getItem().getId()==item.getId()){ //if item already exist, just increment the nrofitems of that item
                items.get(i).addNrOfItems(nrOfItems);
                return;
            }
        }
        System.out.println("item not found");
        items.add(new ShoppingItem(item, nrOfItems));

    }
    public void removeItems(Item item, int nrOfItemsToRemove) {
        for(int i =0; i<items.size(); i++){
            if(items.get(i).getItem().getId()==item.getId()){ //if item already exist, just increment the nrofitems of that item
                int newNrOfItems = items.get(i).getNrOfItems() - nrOfItemsToRemove;
                if(newNrOfItems<0) newNrOfItems=0;
                items.set(i,new ShoppingItem(item,newNrOfItems));
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "Shoppingcart: " + items;
    }
}
