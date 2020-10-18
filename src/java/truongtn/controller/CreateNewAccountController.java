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
import java.util.Random;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import account.AccountCreateError;
import account.AccountDAO;
import account.AccountDTO;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
public class CreateNewAccountController extends HttpServlet {
    private static final Logger log = Logger.getLogger(CreateNewAccountController.class.getName());

    private final String ERROR_PAGE = "create.jsp";
    private final String VERIFY_PAGE = "verify.jsp";

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
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullname");
//        String address = request.getParameter("txtAddress");

        AccountCreateError errors = new AccountCreateError();
        boolean foundErr = false;

        String url = ERROR_PAGE;

        try {

            if (email.trim().length() < 6 || email.trim().length() > 50) {
                foundErr = true;
                errors.setEmailLengthErr("Email is required inputted from 6 to 50 characters");
            } else if (!MyToys.validate(email)) {
                foundErr = true;
                errors.setEmailTypeErr("Email format is wrong.");
            }
            if (password.trim().length() < 6 || password.trim().length() > 20) {
                foundErr = true;
                errors.setPasswordLengthErr("Password is required inputted from 6 to 20 characters");
            } else if (!confirm.trim().equals(password.trim())) {
                foundErr = true;
                errors.setConfirmNotMatched("Confirm must match password");
            }
            if (fullname.trim().length() < 2 || fullname.trim().length() > 50) {
                foundErr = true;
                errors.setFullnameLengthErr("Full name is required inputted from 2 to 50 characters");
            }
            if (!foundErr && !MyToys.validate(email.trim())) {
                foundErr = true;
                errors.setEmailTypeErr("Email is uncomfortable");
            }
            if (!foundErr) {
                String passwordHash = MyToys.toHexString(MyToys.getSHA(password));
                AccountDTO dto = new AccountDTO(email, passwordHash, fullname, 1, 1);
                AccountDAO dao = new AccountDAO();
                boolean result = dao.createAccount(dto);
                if (result) {
                    Random random = new Random();
                    //0.0 <= random_dub <= 1.0
                    double random_dub = random.nextDouble();
                    String vertifyCode = String.valueOf((int) (random_dub * 10000));
                    if (MyToys.sendEmail(email, vertifyCode)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("VERIFYCODE", vertifyCode);
                        url = VERIFY_PAGE;
                    }
                }
            }
            if (foundErr) {
                request.setAttribute("CREATEERRORS", errors);
            }
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            if (msg.contains("duplicate")) {
                errors.setEmailIsExisted(email + " is existed");
                request.setAttribute("CREATEERRORS", errors);
            }
            log.error("Error at CreateNewAccountController: " + ex.getMessage());
        } catch (NamingException | NoSuchAlgorithmException ex) {
            log.error("Error at CreateNewAccountController: " + ex.getMessage());
        } catch (MessagingException ex) {
            String msg = ex.getMessage();
            if (msg.contains("Invalid Addresses")) {
                errors.setEmailIsNotAvailability("Email is not avaibility");
            }
            log.error("Error at CreateNewAccountController: " + ex.getMessage());
        } finally {
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
