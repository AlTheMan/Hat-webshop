<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Webshop</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>

<br/>
<div class="header">
    <h1><%= "Hattshopp" %> </h1>
</div>
<div class="main-content">
<br>
<h3>Köp Hatt</h3>
<br>
<img src="images/transp_hat.png" alt="viktoriansk hatt 1000 kr" width="198" height="150">
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