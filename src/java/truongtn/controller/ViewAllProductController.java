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
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import truongtn.category.CategoryDAO;
import truongtn.category.CategoryDTO;
import truongtn.product.ProductDAO;
import truongtn.product.ProductDTO;
import truongtn.search.SearchObject;
import truongtn.status.StatusDAO;
import truongtn.status.StatusDTO;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
public class ViewAllProductController extends HttpServlet {

    private static final Logger log = Logger.getLogger(ViewAllProductController.class.getName());
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
        String searchValue = request.getParameter("txtSearchValue");
        String minPriceStr = request.getParameter("txtMinPrice");
        String category = request.getParameter("txtCategoryId");
        try {

            ProductDAO productDAO = new ProductDAO();
            SearchObject searchObject = new SearchObject();
            HttpSession session = request.getSession();
            List<ProductDTO> productList = null;
            if (!MyToys.isNullOrEmpty(searchValue)) {
                searchObject.setSearchValue(searchValue);
            }

            //search values
            long minPirce = -1;
            long maxPrice = -1;
            int numberPage = 0;

            if (!MyToys.isNullOrEmpty(minPriceStr)) {
                minPirce = Long.parseLong(minPriceStr);
                maxPrice = minPirce + MONEY_RANGE;
            }
            int categoryId = -1;
            if (!MyToys.isNullOrEmpty(category)) {
                categoryId = Integer.parseInt(category);
            }

            int totalOfRecord = productDAO.countRecordForSearchingByAdmin(searchValue, categoryId, minPirce, maxPrice);

            //if have record with search value
            if (totalOfRecord > 0) {

                productList = productDAO.searchProductByAdmin(searchValue, categoryId, minPirce, maxPrice, numberPage * PAGE_SIZE, PAGE_SIZE);
                int totalOfPage = (int) Math.ceil((float) totalOfRecord / PAGE_SIZE);
                request.setAttribute("TOTAL_PAGE", totalOfPage);
            }
            
            CategoryDAO categoryDAO = new CategoryDAO();
            List<CategoryDTO> categoryDTOs = categoryDAO.getCategoryList();
            if(categoryDTOs != null){
                session.setAttribute("CATEGORIES", categoryDTOs);
            }
            StatusDAO statusDAO = new StatusDAO();
            List<StatusDTO> statusDTOs = statusDAO.getStatusOfCake();
            if(statusDTOs != null){
                session.setAttribute("STATUS", statusDTOs);
            }
            

            //set values into search object
            searchObject.setMinPrice(minPirce);
            searchObject.setCategoryId(categoryId);
            searchObject.setNumberPage(numberPage);
            searchObject.setSearchValue(searchValue);

            session.setAttribute("SEARCH_VALUE", searchObject);
            session.setAttribute("PRODUCTS", productList);

        } catch (NamingException | SQLException | ParseException ex) {
            log.error("Error at ViewAllProductController: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher("viewAllCake.jsp");
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
