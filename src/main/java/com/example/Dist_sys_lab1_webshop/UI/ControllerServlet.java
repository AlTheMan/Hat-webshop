package com.example.Dist_sys_lab1_webshop.UI;

import java.io.*;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;

import com.example.Dist_sys_lab1_webshop.Model.Item.CategoryHandler;
import com.example.Dist_sys_lab1_webshop.Model.Item.ImageHelper;
import com.example.Dist_sys_lab1_webshop.Model.Item.Item;
import com.example.Dist_sys_lab1_webshop.Model.Item.ItemHandler;
import com.example.Dist_sys_lab1_webshop.Model.Order.OrderHandler;
import com.example.Dist_sys_lab1_webshop.Model.Order.OrderStatus;
import com.example.Dist_sys_lab1_webshop.Model.User.Privilege;
import com.example.Dist_sys_lab1_webshop.Model.User.User;
import com.example.Dist_sys_lab1_webshop.Model.User.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


/**
 * All interaktion med JSP-sidorna ska gå via denna
 *
 */




@WebServlet(name = "ControllerServlet", value = {

        "/wares",
        "/hatPage",
        "/login",
        "/userAdmin",
        "/itemAdmin",
        "/addItemToShoppingCartFromIndex",
        "/buyItems",
        "/removeItemFromShoppingCartFromIndex",
        "/shoppingCart",
        "/addItemToShoppingCartFromShoppingcartPage",
        "/removeItemFromShoppingCartFromShoppingcartPage",
        "/ordersPage",
        "/packOrder"})



public class ControllerServlet extends HttpServlet {

    private final static String USERID = "userId";

    public void init() {


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //super.doPost(request, response);
        response.setContentType("text/html");

        String path = request.getServletPath(); // Hämta sökvägen för den aktuella begäran

        System.out.println(path);
        switch (path){
            case "/shoppingBasket":
                response.sendRedirect("shoppingBasket.jsp");
                break;
            case "/wares":
                request.setAttribute("items", ItemHandler.getAllItemsFromDb());
                request.getRequestDispatcher("itemPage.jsp").forward(request, response);
                break;
            case "/hatPage":
                response.sendRedirect("hatPage.jsp");
                break;
            case "/login": handleLogin(request, response);
                break;
            case "/userAdmin":
                handleAdmin(request, response);
                break;
            case "/itemAdmin": handleItemAdmin(request, response);
                break;
            case "/addItemToShoppingCartFromIndex":
                handleAddItemToShoppingCartFromIndex(request,response);
                break;
            case "/buyItems":
                handleBuyItems(request,response);
                break;
            case "/removeItemFromShoppingCartFromIndex":
                handleRemoveItemFromShoppingCartFromIndex(request,response);
                break;
            case "/addItemToShoppingCartFromShoppingcartPage":
                handleAddItemToShoppingCartFromShoppingCartPage(request,response);
                break;
            case "/removeItemFromShoppingCartFromShoppingcartPage":
                handleRemoveItemFromShoppingCartFromShoppingCartPage(request,response);
                break;
            case "/shoppingCart":
                handleGoToShoppingCart(request,response);
                break;
            case "/ordersPage":
                handleOrdersPage(request,response);
                break;
            case "/packOrder":
                handlePackOrder(request,response);
                break;
            default:break;
        }
    }

    public static void getInitItems(HttpServletRequest request){
        request.setAttribute("items", ItemHandler.getAllItemsFromDb());
    }
    private void handlePackOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderHandler.updateStatusOfOrder(OrderStatus.SHIPPED,orderId);
        request.setAttribute("orders", OrderHandler.getAllOrders());
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }

    private void handleOrdersPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("orders", OrderHandler.getAllOrders());
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }

    private void handleGoToShoppingCart(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = getUserSession(request);
        if (user != null) {
            System.out.println(user.getAddress());
            System.out.println(user.getId());
            request.setAttribute("user", user);
            request.getRequestDispatcher("shoppingcart.jsp").forward(request, response);
        }
    }

    private void handleAddItemToShoppingCartFromIndex(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleAddItemToShoppingCartDefault(request, response);
        request.setAttribute("items", ItemHandler.getAllItemsFromDb());
        response.sendRedirect("index.jsp");
        //request.getRequestDispatcher("index.jsp").forward(request, response);  // After adding the item, redirect back to index.jsp
    }
    private void handleAddItemToShoppingCartFromShoppingCartPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleAddItemToShoppingCartDefault(request, response);
        User user = getUserSession(request);
        request.setAttribute("user", user);
        //response.sendRedirect("shoppingcart.jsp");
        request.getRequestDispatcher("shoppingcart.jsp").forward(request, response);  // After adding the item, redirect back to shoppingcart.jsp
    }
    private void handleAddItemToShoppingCartDefault(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = getUserSession(request);
        if (user != null) {
            String stringItemId = request.getParameter("itemId");
            int itemId = Integer.parseInt(stringItemId);
            Item item = ItemHandler.getItemByID(itemId);
            user.getShoppingcart().addItems(item, 1);
            System.out.println(user.getShoppingcart().toString());
        }
    }


    private void handleRemoveItemFromShoppingCartFromShoppingCartPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleRemoveItemFromShoppingCartDefault(request,response);
        User user = getUserSession(request);
        request.setAttribute("user", user);
        request.getRequestDispatcher("shoppingcart.jsp").forward(request, response);  // After adding the item, redirect back to shoppingcart.jsp

    }
    private void handleRemoveItemFromShoppingCartFromIndex(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleRemoveItemFromShoppingCartDefault(request,response);
        request.setAttribute("items", ItemHandler.getAllItemsFromDb());
        request.getRequestDispatcher("index.jsp").forward(request, response); // After adding the item, redirect back to index.jsp
    }
    private void handleRemoveItemFromShoppingCartDefault(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = getUserSession(request);
        if (user != null) {
            String stringItemId = request.getParameter("itemId");
            int itemId = Integer.parseInt(stringItemId);
            Item item = ItemHandler.getItemByID(itemId);
            user.getShoppingcart().removeItems(item,1);
            System.out.println(user.getShoppingcart().toString());
        }
    }


    private void handleBuyItems(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = getUserSession(request);
        if (user != null) {
            try{
                if(UserHandler.buyItems(user)){
                    user.getShoppingcart().emptyCart();
                    System.out.println("Purchase was sucessful!");
                }
            } catch (SQLException e) {
                System.out.println("Purchase was not successful");
                user.getShoppingcart().emptyCart();
                //throw new RuntimeException(e);
            }

        }
        // After adding the item, redirect back to shoppingcart.jsp
        request.setAttribute("user", user);
        request.getRequestDispatcher("shoppingcart.jsp").forward(request, response);
    }



    private void handleAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action != null){
            System.out.println(action);
            switch (action) {
                case "addUser": addUser(request); break;
                case "editUser": editUser(request); break;
                case "deleteUser": deleteUser(request); break;
                default: break;
            }
        }
        if (checkAdmin(getUserSession(request))) {
            request.setAttribute("users", UserHandler.getAllUsers());
            request.getRequestDispatcher("userAdminPage.jsp").forward(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }
    private void addUser(HttpServletRequest request){
        HashMap<String, String> values = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            System.out.println(name + " " + value);
            values.put(name, value);
        }
        for (String v : values.values()){ // tittar att inga fält är tomma
            if (v.compareTo("") == 0) return;
        }
        UserHandler.addUser(values);
    }


    private void deleteUser(HttpServletRequest request) {
        System.out.println("Delete User");
        String id = request.getParameter(USERID);
        if (id == null) {
            System.out.println("id was null");
            return;
        }
        UserHandler.deleteUser(Integer.parseInt(id));
    }

    private void editUser(HttpServletRequest request) {
        String id = request.getParameter(USERID);
        if (id == null) {
            System.out.println("id was null");
            return;
        }
        HashMap<String, String> values = mapAllParameterValues(request);
        UserHandler.updateUser(values);
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        System.out.println(action);
        if (action != null) {
            switch (action){
                case "loginUser": loginUser(request, response); break;
                case "createUser": createUser(request, response); break;
                case "logoutUser": logoutUser(request, response); break;
                case "userCreation": userCreation(request, response); break;
            }



        }


    }

    private void userCreation(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HashMap<String, String> values = mapAllParameterValues(request);
        UserHandler.addUser(values);

        loginUser(request, response);
    }

    private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        UserHandler.setInitUser();
        response.sendRedirect("index.jsp");
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = getUserSession(request);

        if (user == null) {
            System.out.println("Create user");
            request.getRequestDispatcher("createAccount.jsp").forward(request, response);
        } else {
          response.sendRedirect("index.jsp");
        }


    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //response.setHeader("Refresh", "1; index.jsp");
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(name + " " + password);
        User user = UserHandler.authenticateUser(name, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        }
        response.sendRedirect("index.jsp");
    }


    /**
     * This method handles the request from the item admin page.
     * The form needs to pass a parameter with the name action that holds the case to handle.
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */

    private void handleItemAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Handle Item");
        String action = request.getParameter("action");
        System.out.println(action);
        // Action is null first time accessing the page so this will skip.
        if (action != null) {
            switch (action) {
                case "updateItem": updateItem(request); break;
                case "addItem": addItem(request); break;
                case "removeItem": removeItemById(request); break;
                case "addCategory": addCategory(request); break;
                case "removeCategory": removeCategory(request); break;
                default: break;
            }
        }
        if (checkAdmin(getUserSession(request))) {
            request.setAttribute("categories", CategoryHandler.getCategories());
            request.setAttribute("images", ImageHelper.getImageNames());
            request.setAttribute("items", ItemHandler.getAllItemsFromDb());
            request.getRequestDispatcher("itemAdminPage.jsp").forward(request, response);
       } else {
            response.sendRedirect("index.jsp");
        }
    }

    private void removeCategory(HttpServletRequest request) {
        String categoryId = request.getParameter("categoryId");
        if (categoryId == null) return;
        int id = Integer.parseInt(categoryId);
        CategoryHandler.removeCategoryById(id);
    }

    private void addCategory(HttpServletRequest request) {
        String categoryName = request.getParameter("categoryName");
        if (categoryName == null) return;
        CategoryHandler.addCategory(categoryName);

    }

    /**
     * This method loops through all the parameter names and
     * maps them with the parameter values. It only adds the names and params
     * if the value is not an empty string.
     * Then it calls the proper itemhandler method and passes the values.
     * @param request the request from the jsp-page
     */

    private void updateItem(HttpServletRequest request) {
        HashMap<String, String> values = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String value = request.getParameter(name);
            if (!value.isEmpty())
                values.put(name, value);
        }
        ItemHandler.updateItem(values);
    }

    /**
     * This method loops through all the parameter names and
     * maps them with the parameter values. If any of the names has
     * an empty value it returns. Can only add an item with all fields.
     * If all fields are present, it calls the proper Itemhandler method.
     * @param request
     */
    private void addItem(HttpServletRequest request) {
        HashMap<String, String> values = mapAllParameterValues(request);
        for (String value : values.values()) {
            if (value.isEmpty()) return;
            System.out.println(value.toString());
        }

        ItemHandler.addItemToDb(values);

    }

    /**
     * This method handles the removal of an item if that item exists in the database.
     * @param request the request from the jsp-page
     */
    private void removeItemById(HttpServletRequest request) {
        String stringId = request.getParameter("itemId");
        if (stringId == null) return;
        int id = Integer.parseInt(stringId);
        ItemHandler.removeItem(id);
    }




    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       doPost(request, response);
    }

    public void destroy() {
    }

    /**
     * returns the current user session
     * @param request the HttpServletRequest
     * @return a session user.
     */
    private User getUserSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (User) session.getAttribute("user");
    }

    /**
     * Checks if the user passed is of ADMIN-privilege
     * @param user the user to check
     * @return true if admin, false if not
     */
    private boolean checkAdmin(User user) {
        if (user == null) return false;
	    return user.getPrivilege() == Privilege.ADMIN;
    }

    /**
     *
     * @param request the incoming servlet request
     * @return a hashmap with parameter names mapped to values
     */
    private HashMap<String, String> mapAllParameterValues(HttpServletRequest request) {
        HashMap<String, String> values = new HashMap<>();
        Enumeration<String> strings = request.getParameterNames();
        while (strings.hasMoreElements()) {
            String name = strings.nextElement();
            String value = request.getParameter(name);
            values.put(name, value);
        }
        return values;
    }

}

