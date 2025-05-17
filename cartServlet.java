package com.resturent.controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/CartServlet")
public class cartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        if ("add".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int price = Integer.parseInt(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            boolean found = false;
            for (Map<String, Object> item : cart) {
                if (((Integer)item.get("id")) == id) {
                    item.put("quantity", ((Integer)item.get("quantity")) + quantity);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Map<String, Object> newItem = new HashMap<>();
                newItem.put("id", id);
                newItem.put("name", name);
                newItem.put("price", price);
                newItem.put("quantity", quantity);
                cart.add(newItem);
            }
            session.setAttribute("cart", cart);
            response.sendRedirect("menu.jsp");
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            for (Map<String, Object> item : cart) {
                if (((Integer)item.get("id")) == id) {
                    item.put("quantity", quantity);
                    break;
                }
            }
            session.setAttribute("cart", cart);
            response.sendRedirect("cart.jsp");
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            cart.removeIf(item -> ((Integer)item.get("id")) == id);
            session.setAttribute("cart", cart);
            response.sendRedirect("cart.jsp");
        } else if ("placeOrder".equals(action)) {
            response.sendRedirect("cart.jsp");
        }
        else if ("applyDiscount".equals(action)) {
            int discountValue = Integer.parseInt(request.getParameter("discountValue"));
            String discountType = request.getParameter("discountType");
            session.setAttribute("discountValue", discountValue);
            session.setAttribute("discountType", discountType);
            response.sendRedirect("cart.jsp");
        }

    }
}
