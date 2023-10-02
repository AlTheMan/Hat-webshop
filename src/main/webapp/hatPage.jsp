<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User" %><%--
  Created by IntelliJ IDEA.
  User: emilw
  Date: 2023-10-01
  Time: 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a>Köp en hatt</a>
<br>
<img src="images/victoriantophat.jpg" alt="viktoriansk hatt 1000 kr" width="150" height="150">

<%
    System.out.println("Här kan man lägga Kod");
    User user = (User) request.getAttribute("User");
%>

<tr>
    <td>name</td> <td><%= user.getUserName() %></td>
    <td>password</td> <td> <%= user.getPassword() %> </td>


</tr>



</body>
</html>
