<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.User" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.User.Privilege" %><%--
  Created by IntelliJ IDEA.
  User: emilw
  Date: 2023-10-03
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="css/userAdmin.css">
  <title></title>
</head>
<body>


<div class="table">
<table id="users">
  <%

    @SuppressWarnings("unchecked")
    ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
  %>
  <tr>
    <th>ID</th>
    <th>Username</th>
    <th>Privilege</th>
    <th>Email</th>
    <th>Address</th>
    <th></th>
  </tr>
  <% for (int i = 0; i < users.size(); i++) {%>
  <tr>
    <td><%=users.get(i).getId()%></td>
    <td><%=users.get(i).getUserName()%></td>
    <td><%=users.get(i).getPrivilege()%></td>
    <td><%=users.get(i).getEmail()%></td>
    <td><%=users.get(i).getAddress()%></td>
    <td>
      <form>
        <!-- Use a hidden input field to capture the user ID -->
        <input type="hidden" name="userId" value="<%= users.get(i).getId() %>">
        <input type="submit" value="Select ID">
      </form>
    </td>
  </tr>
  <%}%>
</table>
</div>


<%
  String id = request.getParameter("userId");

  if (id != null) {
  int userId = Integer.parseInt(id);
  User user = User.getUserFromId(users, userId);
  if (user != null) {
%>

<div class="form">
  <h3>User ID to manage: <%=userId%> </h3>
<form method="post" action="userAdmin">
  <%--@declare id="dropdown"--%><label for="dropdown">Update privilege:</label>
    <input name="userId" type="hidden" value=<%=id%>>
    <input name="action" type="hidden" value="editUser">
  <select id="dropdown" name="userPrivilege">

    <option style="left: auto" value="<%=user.getPrivilege()%>">Current: <%=user.getPrivilege()%></option>

    <option value="<%=Privilege.ADMIN%>">Admin</option>
  <option value="<%=Privilege.STAFF%>">Staff</option>
  <option value="<%=Privilege.CUSTOMER%>">Customer</option></select>
    <br><label>Update email: <input type="text" value="<%=user.getEmail()%>" name="userEmail"></label>
    <br><label>Update address: <input type="text" value="<%=user.getAddress()%>" name="userAddress"></label>
  <br><input type="submit" value="Update User">
</form>

  <br>
  <form method="post" action="userAdmin">
    <input type="hidden" value="deleteUser" name = "action">
    <input name="userId" type="hidden" value=<%=id%>>
    <input type="submit" value="Delete User">
  </form>
  <%}} %>

  <h3>Add user to database</h3>

  <form method="post" action="userAdmin">

    <label>Enter username: <input type="text" name="username" value=""></label>
    <br>
    <label>Enter password: <input type="password" name="password" value=""></label>

    <%--@declare id="dropdown"--%><label for="dropdown"><br>Select privilege:</label>
    <input name="action" type="hidden" value="addUser">
    <select id="dropdown" name="userPrivilege">
      <option value="NOVALUE"><------></option>
      <option value="ADMIN">Admin</option>
      <option value="STAFF">Staff</option>
      <option value="CUSTOMER">Customer</option></select>
    <br><label>Enter email: <input type="text" name="userEmail" value=""></label>
    <br><label>Enter address: <input type="text" name="userAddress" value=""></label>
    <br><input type="submit" value="Add User">
  </form>




</div>



</body>
</html>
