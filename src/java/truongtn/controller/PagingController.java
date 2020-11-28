/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import truongtn.product.ProductDAO;
import truongtn.product.ProductDTO;
import truongtn.search.SearchObject;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
public class PagingController extends HttpServlet {
    private static final Logger log = Logger.getLogger(PagingController.class.getName());
    
    private final int PAGE_SIZE = 10;
    private final int MONEY_RANGE = 20000;

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
        String numberPageStr = request.getParameter("txtNumberPage");
        String txtNotify = request.getParameter("txtNotify");
        try {
            HttpSession session = request.getSession();
            String searchValue = null;
            long minPrice = -1;
            long maxPrice = -1;
            int numberPage = 0;
            int categoryId = -1;
            SearchObject searchObject = new SearchObject();

            if (session.getAttribute("SEARCH_VALUE") != null) {
                searchObject = (SearchObject) session.getAttribute("SEARCH_VALUE");
                if (searchObject.getCategoryId() != 0) {
                    categoryId = searchObject.getCategoryId();
                }
                if (searchObject.getMinPrice() != 0L) {
                    minPrice = searchObject.getMinPrice();
                }
            }

            ProductDAO productDAO = new ProductDAO();

            List<ProductDTO> productList = null;
            if (!MyToys.isNullOrEmpty(searchValue)) {
                searchObject.setSearchValue(searchValue);
            }

            //search object
            if (minPrice != -1) {
                maxPrice = minPrice + MONEY_RANGE;
            }

            int totalOfRecord = productDAO.countRecordForSearching(searchValue, categoryId, minPrice, maxPrice);

            //if have record with search value
            if (totalOfRecord > 0) {

                if (!MyToys.isNullOrEmpty(numberPageStr)) {
                    numberPage = Integer.parseInt(numberPageStr) - 1;
                }

                productList = productDAO.searchProduct(searchValue, categoryId, minPrice, maxPrice, numberPage * PAGE_SIZE, PAGE_SIZE);
                int totalOfPage = (int) Math.ceil((float) totalOfRecord / PAGE_SIZE);
                request.setAttribute("TOTAL_PAGE", totalOfPage);
            }
            
            if(!MyToys.isNullOrEmpty(txtNotify)){
                request.setAttribute("NOTIFY", txtNotify);
            }

            //set values into search object
            searchObject.setNumberPage(numberPage);
            session.setAttribute("SEARCH_VALUE", searchObject);
            session.setAttribute("PRODUCTS", productList);

        } catch (NamingException | SQLException | ParseException ex) {
            log.error("Error at PagingController: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
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
