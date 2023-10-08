<%--
  Created by IntelliJ IDEA.
  User: algot
  Date: 2023-10-05
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.Shoppingcart" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.ShoppingItem" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.ShoppingItem" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.Shoppingcart" %>
<html>
<head>
    <title>Checkout</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%
    Shoppingcart shoppingCart = (Shoppingcart) request.getAttribute("shoppingcart");
%>

    <h2>
        <img src="images/basket.png" alt="Kundkorg" width="auto" height="70">
        Your Shopping Cart
    </h2>
<br>
<div class="grid-container">
    <%
    if (shoppingCart != null) {
        ArrayList<ShoppingItem> shoppingItem = shoppingCart.getItems();
        if (shoppingItem != null && !shoppingItem.isEmpty()) {
            for (ShoppingItem s : shoppingItem) {
                if(s.getNrOfItems()!=0){
                %>
                    <div class="item-card">
                        <img src="images/hat/<%= s.getItem().getImagesrc() %>" alt="<%= s.getItem().getName() %> Image">
                        <h3><%= s.getItem().getName() %></h3>
                        <p><%= s.getItem().getDescription() %></p>
                        <p>Price: <%= s.getItem().getPrice() %></p>
                        <p>Quantity: <%= s.getNrOfItems() %></p>
                        <form action="addItemToShoppingCartFromShoppingcartPage" method="post">
                            <input type="hidden" name="itemId" value="<%= s.getItem().getId() %>">
                            <input type="submit" value="+">
                        </form>
                        <form action="removeItemFromShoppingCartFromShoppingcartPage" method="post">
                            <input type="hidden" name="itemId" value="<%= s.getItem().getId() %>">
                            <input type="submit" value="-">
                        </form>
                    </div>
                    <%
                }
            }
        }
    }
    %>
</div>
<form method="post" action="buyItems">
    <input type="submit" value="Purchase">
</form>


</body>
</html>
