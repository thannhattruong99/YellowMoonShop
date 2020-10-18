/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import truongtn.category.CategoryDAO;
import truongtn.product.CreateProductError;
import truongtn.product.ProductDAO;
import truongtn.product.ProductDTO;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
@WebServlet("/createCake")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)
public class CreateCakeController extends HttpServlet {

    private final String FAIL = "createCake.jsp";
    private final String SUCCESS = "StartApplicationController";
    private static final String SAVE_PATH = "images/";

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

        String userId = request.getParameter("userId");
        String productName = request.getParameter("txtProductName");
        String categoryStr = request.getParameter("txtCategory");
        String priceStr = request.getParameter("txtPrice");
        String quantityStr = request.getParameter("txtQuantity");
        String description = request.getParameter("txtDescription");
        String createDate = request.getParameter("txtCreatedDate");
        String expirationDate = request.getParameter("txtExpirationDate");
        Part filePart = request.getPart("image");

        CreateProductError createProductError = new CreateProductError();
        boolean foundErr = false;
        try {
            System.out.println("productName: " + productName);
            if ( MyToys.isNullOrEmpty(productName)|| !MyToys.checkStringLength(productName, 6, 200)) {
                foundErr = true;
                createProductError.setProductNameLengErr("Product name is inputed from 6 to 200 characters");
            }

            if (!MyToys.checkStringLength(categoryStr, 3, 20)) {
                foundErr = true;
                createProductError.setCategoryLengthErr("Categorry is inputted from 3 to 20 characters");
            }

            if (!MyToys.isNumeric(priceStr)) {
                foundErr = true;
                createProductError.setPriceTypeErr("Price is integer number");
            }

            if (!MyToys.isNumeric(quantityStr)) {
                foundErr = true;
                createProductError.setQuantityTypeErr("Quantity is integer number");
            }

            if (!MyToys.checkStringLength(description, 6, 500)) {
                foundErr = true;
                createProductError.setDescriptionLengthErr("Description is inputted from 6 to 500 characters");
            }

            if (MyToys.isNullOrEmpty(createDate)) {
                foundErr = true;
                createProductError.setCreateDateErr("Create date is not empty");
            }

            if (MyToys.isNullOrEmpty(expirationDate)) {
                foundErr = true;
                createProductError.setExpirationDateErr("Expiration date is not empty");
            }

            ProductDAO productDAO = new ProductDAO();
            if (productDAO.isDuplicateProductName(productName)) {
                foundErr = true;
                createProductError.setProductNameDuplicateErr("Product name is existed");
            }

            if (!foundErr) {
                String imageName = getFileName(filePart);
                String appPath = request.getServletContext().getRealPath("");
                int indexOfBuild = appPath.indexOf("build");
                appPath = appPath.substring(0, indexOfBuild) + appPath.substring(indexOfBuild + 6);
                String fullSavePath;
                if (appPath.endsWith("/")) {
                    fullSavePath = appPath + SAVE_PATH;
                } else {
                    fullSavePath = appPath + "/" + SAVE_PATH;
                }
                File fileSave = new File(fullSavePath);
                if (!fileSave.exists()) {
                    fileSave.mkdir();
                }
                String imageLink = SAVE_PATH + imageName;
                String filePathAbsolute = fullSavePath + "/" + imageName;
                filePart.write(filePathAbsolute);

                CategoryDAO categoryDAO = new CategoryDAO();
                int categoryId = categoryDAO.getCategoryId(categoryStr);
                if (categoryId == -1) {
                    if (categoryDAO.createCategory(categoryStr)) {
                        categoryId = categoryDAO.getCategoryId(categoryStr);
                    }
                }
                
                long price = Long.parseLong(priceStr);
                int quantity = Integer.parseInt(quantityStr);
                
                ProductDTO productDTO = new ProductDTO(productName, imageLink, price, quantity, categoryId, description, createDate, expirationDate, 2, userId);
                if(productDAO.createProduct(productDTO)){
                   request.setAttribute("SUCCESS", "Created product succesfully");
                   url = SUCCESS; 
                }
            }
            
            if(foundErr){
                request.setAttribute("CREATE_ERROR", createProductError);
            }

        } catch (NamingException | SQLException ex) {
            System.out.println("Error at CreateCakeController: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
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
