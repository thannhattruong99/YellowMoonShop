/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.controller;

import account.AccountDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import truongtn.orders.OrdersDAO;
import truongtn.orders.OrdersDTO;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
public class ViewOrdersController extends HttpServlet {

    private static final Logger log = Logger.getLogger(ViewOrderDetailController.class.getName());
    private final String FAIL = "login.jsp";
    private final String SUCCESS = "orders.jsp";
    private final String VIEW_ORDER_DETAIIL = "ViewOrderDetail?txtOrderId=";

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

        String orderId = request.getParameter("txtOrderId");
        String url = FAIL;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                AccountDTO accountDTO = (AccountDTO) session.getAttribute("USER");
                if (accountDTO != null && accountDTO.getRoleId() == 1) {
                    String userId = accountDTO.getUserId();
                    OrdersDAO ordersDAO = new OrdersDAO();
                    List<OrdersDTO> ordersDTOs = ordersDAO.getOrdersByUserUserId(userId);
                    if (ordersDTOs != null) {
                        session.setAttribute("ORDERS", ordersDTOs);
                        url = SUCCESS;
                        if (!MyToys.isNullOrEmpty(orderId)) {
                            url = VIEW_ORDER_DETAIIL + orderId;
                        }
                    } else {
                        request.setAttribute("ERROR", "You have no orders");
                    }
                }
            }
        } catch (NamingException | SQLException ex) {
            log.error("Error at ViewOrderController: " + ex.getMessage());
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
