package com.example.Dist_sys_lab1_webshop.Model.Item;

import java.util.Date;

public class Orders {
    private int orderID;
    private OrderItem orderItem;
    private String customerName;
    private Date orderDate;
    private String shippingaddress;
    private OrderStatus orderStatus;

    public Orders(int orderID, OrderItem orderItem, String customerName, Date orderDate, String shippingaddress, OrderStatus orderStatus) {
        this.orderID = orderID;
        this.orderItem = orderItem;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.shippingaddress = shippingaddress;
        this.orderStatus = orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderID() {
        return orderID;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getShippingaddress() {
        return shippingaddress;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
