package com.example.Dist_sys_lab1_webshop.UI;

import java.io.*;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;

import com.example.Dist_sys_lab1_webshop.Database.DBManager;
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
        "/goToShoppingcart",
        "/addItemToShoppingCartFromShoppingcartPage",
        "/removeItemFromShoppingCartFromShoppingcartPage",
        "/ordersPage",
        "/packOrder"})



public class ControllerServlet extends HttpServlet {

    private final static String USERID = "userId";

    public void init() {

        DBManager.setInitUser(); //initerar användaren till read-only
        //request.setAttribute("items", ItemHandler.getAllItems());
        //request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //super.doPost(request, response);
        response.setContentType("text/html");

        String path = request.getServletPath(); // Hämta sökvägen för den aktuella begäran
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
            case "/login":
                handleLoginServlet(request, response);
                break;
            case "/userAdmin":
                handleAdminServlet(request, response);
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
            case "/goToShoppingcart":
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

    public static void getInitUsers(HttpServletRequest request){
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
            request.setAttribute("shoppingcart", user.getShoppingcart());
            request.getRequestDispatcher("shoppingcart.jsp").forward(request, response);
        }
    }

    private void handleAddItemToShoppingCartFromIndex(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleAddItemToShoppingCartDefault(request, response);
        request.setAttribute("items", ItemHandler.getAllItemsFromDb());
        request.getRequestDispatcher("index.jsp").forward(request, response);  // After adding the item, redirect back to index.jsp
    }
    private void handleAddItemToShoppingCartFromShoppingCartPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleAddItemToShoppingCartDefault(request, response);
        User user = getUserSession(request);
        request.setAttribute("shoppingcart", user.getShoppingcart());
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
        request.setAttribute("shoppingcart", user.getShoppingcart());
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
                if(UserHandler.buyItems(user.getShoppingcart())){
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
        request.setAttribute("shoppingcart", user.getShoppingcart());
        request.getRequestDispatcher("shoppingcart.jsp").forward(request, response);
    }



    private void handleAdminServlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
        User user = getUserSession(request);
        if (user != null) {
            if (user.getPrivilege() == Privilege.ADMIN){
                request.setAttribute("users", UserHandler.getAllUsers());
                request.getRequestDispatcher("userAdminPage.jsp").forward(request, response);
            }
        }
    }
    private void addUser(HttpServletRequest request){
        System.out.println("Add user");
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

    private void handleLoginServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        User user = UserHandler.authenticateUser(name, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        }
        response.sendRedirect("index.jsp");
    }


    private void handleItemAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Handle Item");
        String action = request.getParameter("action");
        System.out.println(action);
        if (action != null) {
            switch (action) {
                case "updateItem": updateItem(request); break;
                case "addItem": addItem(request); break;
                case "removeItem": removeItemById(request); break;
                default: break;
            }
        }

        if (checkAdmin(getUserSession(request))) {
            request.setAttribute("images", ImageHelper.getImageNames());
            request.setAttribute("items", ItemHandler.getAllItemsFromDb());
            request.getRequestDispatcher("itemAdminPage.jsp").forward(request, response);
       }
    }

    private void updateItem(HttpServletRequest request) { //TODO: skriv om till en klass i itemhandler
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

    private void addItem(HttpServletRequest request) {
        HashMap<String, String> values = mapAllParameterValues(request);
        for (String value : values.values()) {
            if (value.isEmpty()) return;
        }

        ItemHandler.addItemToDb(values);

    }

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

    private User getUserSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (User) session.getAttribute("user");
    }


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

