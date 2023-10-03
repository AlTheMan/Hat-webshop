<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.User" %>
<%@ page import="java.util.Iterator" %><%--
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
    // Vet inte hur man kan se hur en session har en user satt eller inte
	session = request.getSession();
	try {
        User user = (User) session.getAttribute("user"); %>
        <p><%=user.getUserName()%></p>
        <p><%=user.getEmail()%></p>
        <p><%=user.getPrivilege()%></p><%
    } catch (Exception e) {
        System.out.println("User not set");
    }
%>





</body>
</html>
