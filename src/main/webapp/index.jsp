<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Webshop</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h1><%= "Hattshopp" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<br>
<br>
<br>
<br>
<a>Köp Hatt</a>
<br>
<img src="images/victoriantophat.jpg" alt="viktoriansk hatt 1000 kr" width="150" height="150">
<a href="add-item-servlet">+</a>
<a href="remove-item-servlet">-</a>
<br>
<p>Dagens datum: <%= new java.util.Date() %></p>
</body>
</html>