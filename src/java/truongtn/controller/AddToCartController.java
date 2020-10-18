/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import truongtn.cart.CartObject;
import truongtn.product.ProductDTO;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
public class AddToCartController extends HttpServlet {

    private final String CART_PAGE = "cart.jsp";
    private final String PAGING_ACTION = "paging";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url = PAGING_ACTION;

        String btnAction = request.getParameter("btnAction");
        String txtProductId = request.getParameter("txtProductId");
        String txtProductName = request.getParameter("txtProductName");
        String txtQuantity = request.getParameter("txtQuantity");
        String txtPrice = request.getParameter("txtPrice");

        HttpSession session = request.getSession();
        try {
            if (!MyToys.isNullOrEmpty(txtQuantity) && !MyToys.isNullOrEmpty(txtProductId) && !MyToys.isNullOrEmpty(txtProductName)) {

                int quantity = Integer.parseInt(txtQuantity);
                long price = Long.parseLong(txtPrice);
                if (quantity <= 0 && quantity > 1000) {
                    return;
                }

                CartObject cartObject = (CartObject) session.getAttribute("CART");
                if (cartObject != null) {
                    switch (btnAction) {
                        case "Add to cart":
                            cartObject.addItemsToCart(txtProductId, new ProductDTO(txtProductName, price, quantity));
                            break;
                        case "Remove":
                            url = CART_PAGE;
                            cartObject.removeItem(txtProductId);
                            break;
                        case "Update":
                            url = CART_PAGE;
                            cartObject.updateQuantityItemInCart(txtProductId, new ProductDTO(txtProductName, price, quantity));
                            break;
                    }
                } else {
                    cartObject = new CartObject();

                    cartObject.addItemsToCart(txtProductId, new ProductDTO(txtProductName, price, quantity));
                }
                
                session.setAttribute("CART", cartObject);
            }
        } catch (Exception ex) {
            System.out.println("Error at AddToCartController: " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
