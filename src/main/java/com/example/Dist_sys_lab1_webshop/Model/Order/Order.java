package com.example.Dist_sys_lab1_webshop.Model.Order;

import com.example.Dist_sys_lab1_webshop.Database.OrderDB;
import com.example.Dist_sys_lab1_webshop.Model.User.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Order {
    private int orderID;
    private ArrayList<OrderItem> orderItems;
    private String customerName;
    private Date orderDate;
    private String shippingAddress;
    private OrderStatus orderStatus;

    public Order(int orderID, ArrayList<OrderItem> orderItems, String customerName, Date orderDate, String shippingAddress, OrderStatus orderStatus) {
        this.orderID = orderID;
        this.orderItems = orderItems;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.orderStatus = orderStatus;
    }

    public int getOrderID() {
        return orderID;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public ArrayList<OrderItem> getCopyOfOrderItems(ArrayList<OrderItem> orderItems) {
        ArrayList<OrderItem> copy = new ArrayList<>();

        for (OrderItem oi : orderItems) {
            OrderItem itemCpy = OrderItem.getCopy(oi);
            copy.add(itemCpy);
        }

        return copy;
    }

    public static Collection<Order> getDBOrdersAll() {
        Collection<Order> ordersDB = OrderDB.getDBOrderAll2();
        Collection<Order> orders = new ArrayList<>();
        for (Order o: ordersDB){
            orders.add(new Order(o.getOrderID(), o.getCopyOfOrderItems(o.orderItems), o.customerName, o.orderDate, o.shippingAddress, o.orderStatus));
        }
        return orders;
    }

    public static void updateStatusOfOrder(OrderStatus orderStatus, Order order) {
        OrderDB.updateStatusOfOrder(orderStatus,order);
    }
    public static void updateStatusOfOrder(OrderStatus orderStatus, int orderID) {
        OrderDB.updateStatusOfOrder(orderStatus,orderID);
    }

    public String getCustomerName() {
        return customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public static boolean addOrder(User user) {
        return OrderDB.addOrder(user);
    }


}
