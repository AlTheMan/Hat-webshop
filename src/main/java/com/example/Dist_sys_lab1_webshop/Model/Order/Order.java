package com.example.Dist_sys_lab1_webshop.Model.Order;

import com.example.Dist_sys_lab1_webshop.Database.OrderDB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Order {
    private int orderID;
    private ArrayList<OrderItem> orderItems;
    private String customerName;
    private Date orderDate;
    private String shippingaddress;
    private OrderStatus orderStatus;

    public Order(int orderID, ArrayList<OrderItem> orderItems, String customerName, Date orderDate, String shippingaddress, OrderStatus orderStatus) {
        this.orderID = orderID;
        this.orderItems = orderItems;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.shippingaddress = shippingaddress;
        this.orderStatus = orderStatus;
    }

    public int getOrderID() {
        return orderID;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public static Collection<Order> getDBOrdersAll() {
        return OrderDB.getDBOrderAll();
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
        return shippingaddress;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
