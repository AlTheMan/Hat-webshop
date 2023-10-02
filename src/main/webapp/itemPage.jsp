<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.Item.Item" %><%--
  Created by IntelliJ IDEA.
  User: emilw
  Date: 2023-10-02
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <% ArrayList<Item> items = (ArrayList<Item>) request.getAttribute("items");

  %>

  <tr>
      <td>name</td> <td><%= items.get(0).getName() %></td>
      <td>id</td> <td> <%= items.get(0).getId() %> </td>


  </tr>
</body>
</html>
