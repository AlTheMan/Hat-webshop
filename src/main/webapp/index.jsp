<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.User" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.Item.Item" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.UI.ControllerServlet" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.Privilege" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.ShoppingCart" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Webshop</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%
    ControllerServlet.getInitItems(request);
    @SuppressWarnings("unchecked")
    ArrayList<Item> shopItems = (ArrayList<Item>) request.getAttribute("items");
    User user = (User) session.getAttribute("user");
	ShoppingCart shoppingCart = null;
	if (user != null) {
		shoppingCart = user.getShoppingcart();
    }
%>


<br/>
<div class="header">
    <h1>
        <img src="images/hat/victoriantophat.jpg" alt="HatImage" width="30px">
        <%= "Hattshopp" %>
    </h1>
</div>
<div class="grid-container-header">
    <div class = "image-header">
        <form method="post" action="shoppingCart">
            <input type="image" src="images/basket.png"  width="100px" value="Shoppingcart">
        </form>
    </div>
</div>


<div class="main-content">

    <div class="grid-container">

        <% int noOfItems = 0; int itemDiff = 0; for (Item item : shopItems) { %>
        <div class="item-card">
            <img src="images/hat/<%= item.getImageSrc() %>" alt="<%= item.getName() %> Image">
            <h3><%= item.getName() %></h3>
            <p><%= item.getDescription() %></p>
            <p>Price: <%= item.getPrice() %></p>
            <% if (shoppingCart == null) { %>
            <p>Quantity: <%= item.getQuantity() %></p>
            <% } else {%>
            <p>Quantity:
                <% noOfItems = shoppingCart.getQuantityFromItemId(item.getId()); itemDiff = item.getQuantity() - noOfItems; %> <%=itemDiff%></p>
            <% } %>
            <% if (user != null) { %>
            <form action="addItemToShoppingCartFromIndex" method="post">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <% if (itemDiff > 0) { %>
                <input type="submit" value="+"> <% } %>
            </form>
            <form action="removeItemFromShoppingCartFromIndex" method="post">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <% if (noOfItems > 0) { %>
                <input type="submit" value="-"> <% } %>
            </form>
            <% } %>
        </div>
        <% } %>
    </div>

    <br>
    <br>
    <div class="item-card">
    <form method="post" action="wares">
        <input type="hidden" name="action" value="remove">
        <input type="submit" value="Lager">
    </form>
        <br>
    <% if (user != null) {
        System.out.println(user.getPrivilege());
		if (user.getPrivilege() != Privilege.CUSTOMER) { %>
    <form method="post" action="ordersPage">
        <input type="hidden" name="action" value="remove">
        <label>Admin/Staff only: <br>
            <input type="submit" value="Manage orders"></label>
    </form>
        <br>
    <% }
		if (user.getPrivilege() == Privilege.ADMIN) { %>

    <form method="post" action="userAdmin">
        <label>Admin only: <br>
            <input type="submit" value="Manage users">
        </label>
    </form>

    <form method="post" action="itemAdmin">
        <input type="submit" value="Manage items">
    </form>
    </div>
       <% }
    }
    %>

    <br>
    <div class="container">
        <% if (user == null) {  %>
        <form method= "post" action="login">
            Username:<label><input type="text" name="username"></label><br>
            Password:<label><input type="password" name="password"></label><br>
            <input type="hidden" value="loginUser" name="action">
            <input type="submit" value="Login">
        </form>
        <form method="post" action="login">
            <input type="submit" value="Create user">
            <input type="hidden" value="createUser" name="action">
        </form>
        <% } else { %>
        <p> Logged in as: <%= user.getUserName()%>, <%=user.getPrivilege()%>.</p>
        <form method="post" action="login">
            <input type="submit" value="Logout">
            <input type="hidden" value="logoutUser" name="action">
        </form>

        <% }%>

    </div>
</div>
<div class ="footer">
    <p>Dagens datum: <%= new java.util.Date() %></p>
</div>
</body>
</html>