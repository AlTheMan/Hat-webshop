<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.User" %><%--
  Created by IntelliJ IDEA.
  User: emilw
  Date: 2023-10-03
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
      #users {
        font-family: Arial, Helvetica, sans-serif;
        border-collapse: collapse;
        width: 100%;
      }

      #users td, #users th {
        border: 1px solid #ddd;
        padding: 8px;
      }

      #users tr:nth-child(even){background-color: #f2f2f2;}

      #users tr:hover {background-color: #ddd;}

      #users th {
        padding-top: 12px;
        padding-bottom: 12px;
        text-align: left;
        background-color: #04AA6D;
        color: white;
      }
    </style>
</head>
<body>

<h1>User table</h1>
<table id="users">
  <%

    @SuppressWarnings("unchecked")
    ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
    User userToEdit = null; %>
  <tr>
    <th>ID</th>
    <th>Username</th>
    <th>Privilege</th>
    <th>Email</th>
    <th></th>
  </tr>
  <% for (int i = 0; i < users.size(); i++) {%>
  <tr>
    <td><%=users.get(i).getId()%></td>
    <td><%=users.get(i).getUserName()%></td>
    <td><%=users.get(i).getPrivilege()%></td>
    <td><%=users.get(i).getEmail()%></td>
    <td>
      <form method="post" action="editUser">
        <!-- Use a hidden input field to capture the user ID -->
        <input type="hidden" name="userId" value="<%= users.get(i).getId() %>">
        <input type="submit" value="Manage">
      </form>
    </td>
  </tr>
  <%}%>
</table>

<%
if(userToEdit != null) { %>

<form method="post" action="editUser">
  User ID to manage: <%=userToEdit.getId()%>
  <label><input type="text" name="username" ></label>
</form>

<%}%>

</body>
</html>
