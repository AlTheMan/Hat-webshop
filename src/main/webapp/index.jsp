<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Webshop</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>

</h1>
<br/>
<div class="header">
    <h1><%= "Hattshopp" %>
</div>
<div class="main-content">


<a href="hello-servlet">Hello Servlet</a>

<br>
<br>
<br>
<br>
<a>Köp Hatt</a>
<br>
<img src="images/victoriantophat.jpg" alt="viktoriansk hatt 1000 kr" width="150" height="150">
<br>
<a href="add-item-servlet">Kundkorg</a>
<br>
<a href="remove-item-servlet">Här finns en hatt</a>
<br>
<br>
    <div class="container">
        <form action="remove-item-servlet" method="post">
            Name:<input type="text" name="name"><br>
            Password:<input type="password" name="password"><br>
            <input type="submit" value="login">
        </form>
    </div>
</div>
<div class ="footer">
    <p>Dagens datum: <%= new java.util.Date() %></p>
</div>
</body>
</html>