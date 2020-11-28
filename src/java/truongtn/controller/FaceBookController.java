/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.controller;

import account.AccountDAO;
import account.AccountDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import truongtn.api.APIWrapper;
import truongtn.dto.UserInfo;

/**
 *
 * @author truongtn
 */
public class FaceBookController extends HttpServlet {
    private static final Logger log = Logger.getLogger(AddToCartController.class.getName());
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
        try {
            String code = request.getParameter("code");


            //Tu code da lay, chuyen thanh access token
            APIWrapper wrapper = new APIWrapper();
            String accessToken = wrapper.getAccessToken(code);
            wrapper.setAccessToken(accessToken);

            //Truy cap thong tin user
            UserInfo userInfo = wrapper.getUserInfo();
            
            AccountDAO accountDAO = new AccountDAO();
            AccountDTO result = accountDAO.getAccountByUserId(userInfo.getFacebookID());
            
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setUserId(userInfo.getId());
            accountDTO.setFullname(userInfo.getName());
            accountDTO.setRoleId(1);
            accountDTO.setStatusId(2);
            if(result == null){
                accountDAO.createAccountByFaceBook(accountDTO);
            }

            HttpSession session = request.getSession();
            session.setAttribute("USER", accountDTO);

            
        } catch (NamingException | SQLException ex) {
            log.error("ERROR at FaceBookController: " + ex.getMessage());
        } finally {
            response.sendRedirect("StartApplication");
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
