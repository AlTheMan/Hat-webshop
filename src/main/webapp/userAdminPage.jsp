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
  <link rel="stylesheet" type="text/css" href="css/userAdmin.css">
</head>
<body>


<div class="table">
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



<div class="form">
  <h3>User ID to manage:
  <% String id = request.getParameter("userId");
    if (id == null)  { %> Please select ID
  <% } else { %> <%=id%> <%}%> </h3>
<form method="post" action="editUser">
  <%--@declare id="dropdown"--%><label for="dropdown">Update privilege:</label>
    <input name="userId" type="hidden" value=<%=id%>>
  <select id="dropdown" name="userPrivilege">
    <option value="NOVALUE"><------></option>
    <option value="ADMIN">Admin</option>
  <option value="STAFF">Staff</option>
  <option value="CUSTOMER">Customer</option></select>
    <br><label>Update email: <input type="text" value="" name="userEmail"></label>
  <br><input type="submit" value="Update User" name="userSubmit"> <input type="submit" value="Delete User" name="userDelete">
</form>
</div>



</body>
</html>
