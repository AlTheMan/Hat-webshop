package com.example.Dist_sys_lab1_webshop.Model.Order;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class OrderHandler {

    public static ArrayList<Order> getAllOrders()  {
        Collection<Order> itemCollection = Order.getDBItemsAll();
        return new ArrayList<>(itemCollection);
    }

    public static void updateStatusOfOrder(OrderStatus orderStatus, Order order) {
        Order.updateStatusOfOrder(orderStatus, order);
        order.setOrderStatus(orderStatus);
    }
}
