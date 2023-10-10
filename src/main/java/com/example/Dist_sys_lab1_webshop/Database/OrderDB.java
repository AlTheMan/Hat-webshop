package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.Item.Category;
import com.example.Dist_sys_lab1_webshop.Model.Order.Order;
import com.example.Dist_sys_lab1_webshop.Model.Order.OrderItem;
import com.example.Dist_sys_lab1_webshop.Model.Order.OrderStatus;
import com.example.Dist_sys_lab1_webshop.Model.User.ShoppingCart;
import com.example.Dist_sys_lab1_webshop.Model.User.User;

import java.sql.*;
import java.time.LocalDate;
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

    public static Collection<Order> getDBOrderAll2() {
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

                Statement orderItemsStatement = con.createStatement();
                ResultSet orderItemsResultSet = orderItemsStatement.executeQuery("SELECT * FROM order_items WHERE order_id = " + orderId);

                ArrayList<OrderItem> orderItems = new ArrayList<>();
                int quantity=-1;
                while (orderItemsResultSet.next()) { //tabell order_items
                    int itemId = orderItemsResultSet.getInt("item_id");
                    quantity = orderItemsResultSet.getInt("quantity");

                    Statement itemStatement = con.createStatement();
                    ResultSet itemResultSet = itemStatement.executeQuery("SELECT item.*, categories.category AS category FROM item JOIN categories ON item.categoryId = categories.id WHERE item.id =" + itemId);

                    ItemDB itemDB = null;
                    if (itemResultSet.next()) { //tabell item
                        String itemName = itemResultSet.getString("name");
                        String description = itemResultSet.getString("description");
                        double price = itemResultSet.getDouble("price");
                        int itemQuantity = itemResultSet.getInt("quantity");
                        String imagesrc = itemResultSet.getString("imagesrc");
                        int categoryid = itemResultSet.getInt("categoryid");
                        String categoryName = itemResultSet.getString("category");
                        Category category = new Category(categoryName, categoryid);
                        itemDB= new ItemDB(itemId, itemName, description, price, itemQuantity, imagesrc, category);


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


    /**
     * Inserts an order for a user in the database
     * 1) it inserts the order in the database.
     * 2) it fetches the order id for that order (auto incremented in the database)
     * 3) it inserts the ordered items in the order_items table with that order id.
     * 4)  it updates the item quantity in the item table
     * @param user the user that added the order
     * @return true if success
     */

    public static boolean addOrder(User user) {
        ShoppingCart shoppingcart = user.getShoppingcart();
        Connection con = DBManager.getConnection();

        // Updates the item quantity
        String itemSql = "UPDATE item SET quantity = quantity - ? WHERE id = ?;";
        // Inserts the order into the order table
        String ordersSql = "INSERT INTO orders (customer_name, order_date, status, shipping_address, user_id) VALUES (?, ?, ?, ?, ?)";
        // Inserts item into the order_items table
        String order_itemsSql = "INSERT INTO order_items (order_id, item_id, quantity) VALUES (?, ?, ?)";
        // Gets the order_id to be inserted into the order_items table
        String getOrderIdSql = "SELECT order_id from orders where customer_name = ?";

        try {
            PreparedStatement itemStatement = con.prepareStatement(itemSql);
            PreparedStatement order_itemsStatement = con.prepareStatement(order_itemsSql);
            PreparedStatement orderStatement = con.prepareStatement(ordersSql);
            PreparedStatement getOrderId = con.prepareStatement(getOrderIdSql);

            con.setAutoCommit(false);  // Start transaction
            // Inserts into the order table
            orderStatement.setString(1, user.getUserName());
            orderStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            orderStatement.setString(3, String.valueOf(OrderStatus.PENDING));
            orderStatement.setString(4, user.getAddress());
            orderStatement.setInt(5, user.getId());
            orderStatement.executeUpdate();

            // Gets the order-id for order_items table insert
            getOrderId.setString(1, user.getUserName());
            ResultSet rs = getOrderId.executeQuery();
            String order_id = null;
            while(rs.next()) {
                order_id = rs.getString(1);
            }

            for(int i=0; i<shoppingcart.getItems().size(); i++){
                int itemId = shoppingcart.getItems().get(i).getItem().getId();
                int itemQuantity = shoppingcart.getItems().get(i).getNrOfItems();

                // Inserts the item in the order_items table
                order_itemsStatement.setInt(1, Integer.parseInt(order_id));
                order_itemsStatement.setInt(2, itemId);
                order_itemsStatement.setInt(3, itemQuantity);
                order_itemsStatement.executeUpdate();

                // Updates the quantity in the item table
                itemStatement.setInt(1, itemQuantity);
                itemStatement.setInt(2, itemId);
                itemStatement.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();

            try {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Rollback!");
            } catch (SQLException ex) {
                e.printStackTrace();
            }
            return false;
        }

        finally {
            try {
                con.setAutoCommit(true);  // End the transaction and set the connection back to default mode
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true; //Kolla om detta returnerar true endast vid success.
    }



}
