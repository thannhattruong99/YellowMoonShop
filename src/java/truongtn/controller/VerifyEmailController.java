/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
public class VerifyEmailController extends HttpServlet {
    private static final Logger log = Logger.getLogger(VerifyEmailController.class.getName());
    
    private final String SUCCESS = "login.html";
    private final String FAIL = "verify.jsp";

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
        try {
            String txtVerifyCode = request.getParameter("txtVerifyCode");
            String txtEmail = request.getParameter("txtEmail");
            HttpSession session = request.getSession(false);
            if(session != null){
                String verifyCode = (String) session.getAttribute("VERIFYCODE");
                if(!MyToys.isNullOrEmpty(txtVerifyCode) && !MyToys.isNullOrEmpty(url) && txtVerifyCode.trim().equals(verifyCode.trim())){
                    AccountDAO dao = new AccountDAO();
                    AccountDTO dto = new AccountDTO(txtEmail, 2);
                    dao.updateAccountRole(dto);
                    url = SUCCESS;    
                }else{
                    request.setAttribute("ERROR", "Verify code is not matched.");
                }
            }
        } catch (NamingException | SQLException ex) {
            log.error("Error at VerifyEmailController: " + ex.getMessage());
        }finally{
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
