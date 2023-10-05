package com.example.Dist_sys_lab1_webshop.Model.Order;

import java.util.ArrayList;
import java.util.Collection;

public class OrderHandler {

    public static ArrayList<Order> getAllOrders()  {
        Collection<Order> itemCollection = Order.getDBOrdersAll();
        return new ArrayList<>(itemCollection);
    }

    public static void updateStatusOfOrder(OrderStatus orderStatus, Order order) {
        Order.updateStatusOfOrder(orderStatus, order);
        order.setOrderStatus(orderStatus); //TODO: borde kolla om statusen ändras i databsen.
    }
}
