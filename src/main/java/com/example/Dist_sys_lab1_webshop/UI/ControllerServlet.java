package com.example.Dist_sys_lab1_webshop.UI;

import java.io.*;

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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String path = request.getServletPath(); // Hämta sökvägen för den aktuella begäran
        PrintWriter out = response.getWriter(); //denna används inte

        switch (path){
            case "/hello-servlet":
                hello_servlet(response);
                break;
            case "/add-item-servlet":
                out.println("<h1>Item Added!</h1>");
                break;
            case "/remove-item-servlet":
                out.println("<h1>Item Removed!</h1>");
                break;
            default: break;

        }

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

