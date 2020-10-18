/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import account.AccountDAO;
import account.AccountDTO;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
public class LoginController extends HttpServlet {

    private static final Logger log = Logger.getLogger(LoginController.class.getName());

    private final String FAIL = "login.jsp";
    private final String SUCCESS = "StartApplication";

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
        String url = FAIL;
        HttpSession session = request.getSession();
        try {
            String username = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            String passwordHash = MyToys.toHexString(MyToys.getSHA(password));
            AccountDAO accountDAO = new AccountDAO();
            AccountDTO accountDTO = new AccountDTO(username, passwordHash, 2);
            AccountDTO resultDTO = accountDAO.checkLogin(accountDTO);

            if (resultDTO != null) {
                session.setAttribute("USER", resultDTO);
                url = SUCCESS;
            } else {
                session.setAttribute("ERROR", "Username or password is invalid.Please try again!!");
            }
        } catch (NamingException | SQLException | NoSuchAlgorithmException ex) {
            log.error("Error at LoginController: " + ex.getMessage());
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
