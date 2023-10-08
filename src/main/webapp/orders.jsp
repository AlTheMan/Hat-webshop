<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.Item.Item" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.Order.Order" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.Order.OrderItem" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.Order.OrderStatus" %>

<html>
<head>
    <title>Orders</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%
    @SuppressWarnings("unchecked")
    ArrayList<Order> orders = ( ArrayList<Order>) request.getAttribute("orders");
%>

<div class="grid-container">

    <% for (Order o : orders) { %>
    <%if (o.getOrderStatus().equals(OrderStatus.PENDING)){ %>
        <div class="item-card-orders">
            <h3><%= o.getOrderID() %></h3>
            <p><%= o.getCustomerName() %></p>
            <p><%= o.getOrderDate() %></p>
            <p><%= o.getOrderStatus() %></p>
            <p><%= o.getShippingAddress() %></p>
            <% ArrayList<OrderItem> orderitems = o.getOrderItems();
                for(OrderItem i: orderitems){ %>
            <p> id: <%= i.getItem().getId() %>, <%= i.getItem().getName() %>, Amount Bought:<%= i.getNrOfItems() %></p>
            <%  } %>
            <form action="packOrder" method="post">
                <input type="hidden" name="orderId" value="<%= o.getOrderID() %>">
                <input type="submit" value="send order">
            </form>
        </div>
        <%
        }
    } %>
</div>

</body>
</html>
