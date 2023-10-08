package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.Order.Order;
import com.example.Dist_sys_lab1_webshop.Model.Order.OrderItem;
import com.example.Dist_sys_lab1_webshop.Model.Order.OrderStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class OrderDB extends Order {
    public OrderDB(int orderID, ArrayList<OrderItem> orderItems, String customerName, Date orderDate, String shippingaddress, OrderStatus orderStatus) {
        super(orderID, orderItems, customerName, orderDate, shippingaddress, orderStatus);
    }

    public static void updateStatusOfOrder(OrderStatus orderStatus, Order order) {
        Connection con = DBManager.getConnection();
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, orderStatus.toString());
            statement.setInt(2,order.getOrderID());
            statement.executeUpdate();
        } catch (SQLException e) {
        e.printStackTrace();
    }
        return;
    }

    public static void updateStatusOfOrder(OrderStatus orderStatus, int orderID) {
        Connection con = DBManager.getConnection();
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, orderStatus.toString());
            statement.setInt(2, orderID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Collection<Order> getDBOrderAll() {
        Connection con = DBManager.getConnection();
        Collection<Order> orderCollection = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet orderResultSet = statement.executeQuery("SELECT * FROM orders");

            while (orderResultSet.next()) {

                int orderId = orderResultSet.getInt("order_id");
                String customerName = orderResultSet.getString("customer_name");
                java.sql.Date date = orderResultSet.getDate("order_date");
                String status = orderResultSet.getString("status");
                OrderStatus orderStatus = OrderStatus.valueOf(status);
                String shippingAddress = orderResultSet.getString("shipping_address");

                System.out.println("Order ID: " + orderId);
                System.out.println("Customer Name: " + customerName);

                Statement orderItemsStatement = con.createStatement();
                ResultSet orderItemsResultSet = orderItemsStatement.executeQuery("SELECT * FROM order_items WHERE order_id = " + orderId);

                ArrayList<OrderItem> orderItems = new ArrayList<>();
                int quantity=-1;
                while (orderItemsResultSet.next()) { //tabell order_items
                    int itemId = orderItemsResultSet.getInt("item_id");
                    quantity = orderItemsResultSet.getInt("quantity");

                    System.out.println("\tItem ID: " + itemId);
                    System.out.println("\tQuantity: " + quantity);

                    Statement itemStatement = con.createStatement();
                    ResultSet itemResultSet = itemStatement.executeQuery("SELECT * FROM item WHERE id = " + itemId);

                    ItemDB itemDB = null;
                    if (itemResultSet.next()) { //tabell item
                        String itemName = itemResultSet.getString("name");
                        String description = itemResultSet.getString("description");
                        double price = itemResultSet.getDouble("price");
                        int itemQuantity = itemResultSet.getInt("quantity");
                        String imagesrc = itemResultSet.getString("imagesrc");
                        String category = itemResultSet.getString("category");
                        itemDB= new ItemDB(itemId, itemName, description, price, itemQuantity, imagesrc, category);

                        System.out.println("\t\tItem Name: " + itemName);
                    }
                    orderItems.add(new OrderItem(itemDB,quantity));

                    itemStatement.close();
                }

                orderCollection.add(new Order(orderId,orderItems,customerName,date,shippingAddress,orderStatus));

                orderItemsStatement.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderCollection;
    }


    public static OrderDB getOrdersWithOrderItems(int orderId) {




        return null;
    }



}
