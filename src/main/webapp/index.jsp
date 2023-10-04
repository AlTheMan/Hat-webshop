<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.User" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.Item.Item" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.UI.ControllerServlet" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Webshop</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>



<br/>
<div class="header">
    <h1>
        <img src="images/hat/victoriantophat.jpg" alt="HatImage" width="30px">
        <%= "Hattshopp" %>
    </h1>
</div>
<div class="main-content">
    <%

        ControllerServlet.getInitUsers(request);

        @SuppressWarnings("unchecked")
        ArrayList<Item> shopItems = (ArrayList<Item>) request.getAttribute("items");


    %>
    <div class="grid-container">
        <% for (Item item : shopItems) { %>
        <div class="item-card">
            <img src="images/hat/<%= item.getImagesrc() %>" alt="<%= item.getName() %> Image">
            <h3><%= item.getName() %></h3>
            <p><%= item.getDescription() %></p>
            <p>Price: <%= item.getPrice() %></p>
            <p>Quantity: <%= item.getQuantity() %></p>
        </div>
        <% } %>
    </div>

    <br>
    <br>


<img src="images/hat/transp_hat.png" alt="viktoriansk hatt 1000 kr" width="198" height="150">

    <br>
    <p>Button has been clicked: <%= request.getAttribute("count") %> times.</p>
    <a href="increaseCounterForItem">Increase Counter!</a>
    <br>
    <form method="post" action="addItem">
        <input type="submit" name="action" value="submit">
        <input type="submit" value="+">
    </form>
    <br>
    <br>
    <form method="post" action="hatPage">
        <input type="hidden" name="action" value="remove">
        <input type="submit" value="Här finns en hatt">
    </form>
    <br>
    <form method="post" action="wares">
        <input type="hidden" name="action" value="remove">
        <input type="submit" value="Här är lagret">
    </form>
    <br>
    <form method="post" action="shoppingBasket">
        <input type="hidden" name="action" value="remove">
        <input type="submit" value="Shopping basket">
    </form>
    <br>
    <br>
    <form method="post" action="userAdmin">
        <input type="submit" value="Handle users">
    </form>
    <br>
    <div class="container">
        <form method= "post" action="login">
            Username:<label><input type="text" name="name"></label><br>
            Password:<label><input type="password" name="password"></label><br>
            <input type="submit" value="login">
        </form>
    </div>
</div>
<div class ="footer">
    <% User user = (User) session.getAttribute("user");
     if (user != null) { %>
    <p> Logged in as: <%= user.getUserName()%>!</p>
    <%}%>

    <p>Dagens datum: <%= new java.util.Date() %></p>
</div>
</body>
</html>