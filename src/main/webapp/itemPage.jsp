<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.Item.Item" %><%--
  Created by IntelliJ IDEA.
  User: emilw
  Date: 2023-10-02
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    @SuppressWarnings("unchecked")
    ArrayList<Item> shopItems = (ArrayList<Item>) request.getAttribute("items");
%>

<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Quantity</th>
    </tr>
    <% for (Item item : shopItems) { %>
    <tr>
        <td><%= item.getId() %></td>
        <td><%= item.getName() %></td>
        <td><%= item.getDescription() %></td>
        <td><%= item.getPrice() %></td>
        <td><%= item.getQuantity() %></td>
        <form action="addItemToShoppingCart" method="post">
            <input type="hidden" name="itemId" value="<%= item.getId() %>">
            <input type="submit" value="+">
        </form>
        <form action="removeItemFromShoppingCart" method="post">
            <input type="hidden" name="itemId" value="<%= item.getId() %>">
            <input type="submit" value="-">
        </form>

    </tr>
    <% } %>

</table>
<form method="post" action="buyItems">
    <input type="submit" value="Purchase">
</form>
</body>

</html>
