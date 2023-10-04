<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.Item.Item" %><%--

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>HattShopp</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>

<div class="header">
    <h1>
        <%= "HATTSHOPP" %>
        <img src="images/hat/victoriantophat.jpg" alt="hat1" width="50" height="auto">
    </h1>
</div>
<div class="main-content">
    <img src="images/basket.png" alt="shoppingcartImage" width="100" height="auto">
    <br>
    <div class="image-row">
        <%for (int i = 1; i <= 20; i++) { %>
            <img src="images/hat/hat<%=i%>.jpg" alt="hat <%= i %>" width="200" height="auto" margin-right: 50px>
            <form action="addItemToShoppingCart" method="post">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <input type="submit" value="+">
            </form>
        <%}%>
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