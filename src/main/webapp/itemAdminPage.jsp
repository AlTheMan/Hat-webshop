<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.Item.Item" %>
<%@ page import="com.example.Dist_sys_lab1_webshop.Model.Item.Category" %><%--
  Created by IntelliJ IDEA.
  User: emilw
  Date: 2023-10-04
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%  ArrayList<String> images = (ArrayList<String>) request.getAttribute("images");
    ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
%>
<h2>Add Item</h2>
<form>
    <label>Name: <input name="itemName" value="" type="text"></label>
    <label>Description: <input name="descriptionName" value="" type="text"></label>
    <label>Price: <input name="itemPrice" value="" type="number" min="0"></label>
    <label>Quantity: <input name="itemQuantity" value="" type="number" min="0"></label>

    <% if (categories != null) { %> <%--@declare id="dropdown"--%> <label for="dropdown">Category</label>
    <select name="itemCategoryId" id="dropdown">
        <% for (Category c : categories) { %>
        <option value="<%=c.getCategoryId()%>"> <%=c.getCategory()%> </option>
        <%}%>
    </select>
    <%}%>

    <% if (images != null) { %> <%--@declare id="dropdown"--%> <label for="dropdown">Image</label>
    <select name="itemIMG" id="dropdown">
        <% for (String image : images) { %>
        <option value="<%=image%>"><%=image%></option>
        <%}%>
    </select>
    <%}%>
    <input name="action" type="hidden" value="addItem">
    <input type="submit" value="Add Item">
</form>

<% ArrayList<Item> items = (ArrayList<Item>) request.getAttribute("items");
if (items != null) { %>

<h2>Select an item to edit</h2>
<div class="grid-container">
    <% for (Item item : items) { %>
    <div class="item-card">
        <img src="images/hat/<%= item.getImageSrc() %>" alt="<%= item.getName() %> Image">
        <h3><%= item.getName() %></h3>
        <p><%= item.getDescription() %></p>
        <p>Price: <%= item.getPrice() %></p>
        <p>Quantity: <%= item.getQuantity() %></p>
        <p>Item id: <%=item.getId()%></p>
        <p>Category: <%=item.getCategoryName()%></p>
        <form>
            <input type="hidden" value="<%=item.getId()%>" name="itemId">
            <input type="submit" value="Select">
        </form>
    </div>
    <% }
    }%>
</div>

<h2>Edit Category</h2>
<form method="post">
    <label>Add Category: <input type=text name="categoryName" value=""></label>
    <input type="hidden" value="addCategory" name="action">
    <input type="submit" value="Submit">
</form>
<form method="post">
    <% if (categories != null) { %> <%--@declare id="dropdown"--%> <label for="dropdown">Remove Category</label>
    <select name="categoryId" id="dropdown">
        <option value="">Choose</option>
        <% for (Category c : categories) { %>
        <option value="<%=c.getCategoryId()%>"> <%=c.getCategory()%> </option>
        <%}%>
    </select>
    <%}%>
    <input type="hidden" value="removeCategory" name="action">
    <input type="submit" value="Remove">
</form>


<% String itemId = request.getParameter("itemId");
if (itemId != null) {

%>


<h2>Update existing item</h2>
<h3>Selected item: <%=itemId%>

</h3>
<form method="post">
    <label>Edit name: <input type=text name="itemName" value=""></label>
    <label>Edit description: <input name="descriptionName" value="" type="text"></label>
    <label>Edit price: <input type="number" min="0" name="itemPrice" value=""></label>
    <label>Edit quantity: <input type="number" min="0" name="itemQuantity" value=""></label>

    <% if (categories != null) { %> <%--@declare id="dropdown"--%> <label for="dropdown">Category</label>
    <select name="itemCategoryId" id="dropdown">
        <option value="">Choose</option>
        <% for (Category c : categories) { %>
        <option value="<%=c.getCategoryId()%>"> <%=c.getCategory()%> </option>
        <%}%>
    </select>
    <%}%>

	<% if (images != null) { %> <%--@declare id="dropdown"--%> <label for="dropdown">Edit image</label>
    <select name="itemIMG" id="dropdown">
        <option value="">Choose</option>
        <% for (String image : images) { %>
        <option value="<%=image%>"><%=image%></option>
        <%}%>
    </select>
    <input type="hidden" name="action" value="updateItem">
    <input type="hidden" name="itemId" value="<%=itemId%>">
    <input type="submit" value="Update Item">
</form>
<form method="post">
    <input type="submit" value="Remove Item">
    <input type="hidden" value="<%=itemId%>" name="itemId" >
    <input type="hidden" value="removeItem" name="action">
</form>

<%}}%>



</body>
</html>
