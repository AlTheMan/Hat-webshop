<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.Privilege" %><%--
  Created by IntelliJ IDEA.
  User: emilw
  Date: 2023-10-08
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>Enter information:</h3>

<form action="login" method="post">
    <label>Enter username: <input type="text" name="username" value=""></label>
    <br>
    <label>Enter password: <input type="password" name="password" value=""></label>
    <br><label>Enter email: <input type="text" name="userEmail" value=""></label>
    <br><label>Enter address: <input type="text" name="userAddress" value=""></label>
    <br><input type="submit" value="Add User">
    <input type="hidden" name="userPrivilege" value=<%=Privilege.CUSTOMER%>>
    <input type="hidden" name="action" value="userCreation">
</form>



</body>
</html>
