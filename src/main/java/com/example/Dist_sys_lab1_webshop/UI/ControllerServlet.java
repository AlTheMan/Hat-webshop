package com.example.Dist_sys_lab1_webshop.UI;

import java.io.*;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;

import com.example.Dist_sys_lab1_webshop.Database.DBManager;
import com.example.Dist_sys_lab1_webshop.Model.Item.Item;
import com.example.Dist_sys_lab1_webshop.Model.Item.ItemHandler;
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
        "/shoppingBasket",
        "/wares",
        "/hatPage",
        "/login",
        "/userAdmin",
        "/addItemToShoppingCart",
        "/buyItems",
        "/removeItemFromShoppingCart"})



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
                request.setAttribute("items", ItemHandler.getAllItems());
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
            case "/addItemToShoppingCart":
                handleAddItemToShoppingCart(request,response);
                break;
            case "/buyItems":
                handleBuyItems(request,response);
                break;
            case "/removeItemFromShoppingCart":
                handleRemoveItemFromShoppingCart(request,response);
                break;
            default:break;
        }
    }

    public static void getInitUsers(HttpServletRequest request){
        request.setAttribute("items", ItemHandler.getAllItems());
    }

    private void handleAddItemToShoppingCart(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = getUserSession(request);
        if (user != null) {
            String stringItemId = request.getParameter("itemId");
            int itemId = Integer.parseInt(stringItemId);
            Item item = ItemHandler.getItemByID(itemId);
            user.getShoppingcart().addItems(item,1);
            System.out.println(user.getShoppingcart().toString());
            //System.out.println("nr of items in index 0: "+user.getShoppingcart().getItems().get(0).getNrOfItems());
        }
        // After adding the item, redirect back to itemPage.jsp
        request.setAttribute("items", ItemHandler.getAllItems());
        request.getRequestDispatcher("itemPage.jsp").forward(request, response);

    }

    private void handleRemoveItemFromShoppingCart(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = getUserSession(request);
        if (user != null) {
            String stringItemId = request.getParameter("itemId");
            int itemId = Integer.parseInt(stringItemId);
            Item item = ItemHandler.getItemByID(itemId);
            user.getShoppingcart().removeItems(item,1);
            System.out.println(user.getShoppingcart().toString());
            //System.out.println("nr of items in index 0: "+user.getShoppingcart().getItems().get(0).getNrOfItems());
        }
        // After adding the item, redirect back to itemPage.jsp
        request.setAttribute("items", ItemHandler.getAllItems());
        request.getRequestDispatcher("itemPage.jsp").forward(request, response);

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
        // After adding the item, redirect back to itemPage.jsp
        request.setAttribute("items", ItemHandler.getAllItems());
        request.getRequestDispatcher("itemPage.jsp").forward(request, response);

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

        /*String username = request.getParameter("username");
        String privilege = request.getParameter("userPrivilege");
        String email = request.getParameter("email");*/



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
        System.out.println("EditUsers");

        String id = request.getParameter(USERID);
        if (id == null) {
            System.out.println("id was null");
            return;
        }

        HashMap<String, String> values = new HashMap<>();
        Enumeration<String> strings = request.getParameterNames();
        while (strings.hasMoreElements()) {
            String name = strings.nextElement();
            String value = request.getParameter(name);
            System.out.println("Name: " + name + ", Value: " + value);
            values.put(name, value);
        }
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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       doPost(request, response);
    }

    public void destroy() {
    }

    private User getUserSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (User) session.getAttribute("user");
    }

}

