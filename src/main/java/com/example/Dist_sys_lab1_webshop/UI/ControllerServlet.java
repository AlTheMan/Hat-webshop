package com.example.Dist_sys_lab1_webshop.UI;

import java.io.*;

import com.example.Dist_sys_lab1_webshop.Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


/**
 * All interaktion med JSP-sidorna ska gå via denna
 *
 */




@WebServlet(name = "helloServlet", value = {
        "/hello-servlet",
        "/add-item-servlet",
        "/remove-item-servlet"})
public class ControllerServlet extends HttpServlet {
    private String message;
    private String message_buy_item;

    public void init() {
        message = "Hello World!";
        message_buy_item="Successfully bought item";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //super.doPost(request, response);
        response.setContentType("text/html");

        String path = request.getServletPath(); // Hämta sökvägen för den aktuella begäran

        //PrintWriter out = response.getWriter(); //denna används inte

        switch (path){
            case "/hello-servlet":
                hello_servlet(response);
                System.out.println("Hej");
                break;
            case "/add-item-servlet":
                response.sendRedirect("shoppingBasket.jsp");
                break;
            case "/remove-item-servlet":
                String name = request.getParameter("name");
                String password = request.getParameter("password");
                request.setAttribute("User", new User(name, password));
                //response.sendRedirect("shoppingBasket.jsp");
                request.getRequestDispatcher("hatPage.jsp").forward(request, response);

                break;
            default: break;

        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       doPost(request, response);
    }

    public void destroy() {
    }

    private void hello_servlet(HttpServletResponse response) throws IOException {
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<h1>" + message_buy_item + "</h1>");
        out.println("</body></html>");
    }
}

