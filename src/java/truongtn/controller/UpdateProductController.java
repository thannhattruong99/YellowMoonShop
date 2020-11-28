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
import java.text.ParseException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.log4j.Logger;
import truongtn.product.ProductDAO;
import truongtn.product.ProductDTO;
import truongtn.updaterecord.UpdateRecordDTO;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
@WebServlet("/updateProduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)
public class UpdateProductController extends HttpServlet {
    private static final Logger log = Logger.getLogger(UpdateProductController.class.getName());
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

        String userId = request.getParameter("userId");
        String productId = request.getParameter("txtProductId");
        String productName = request.getParameter("txtProductName");
        String categoryStr = request.getParameter("txtCategory");
        String priceStr = request.getParameter("txtPrice");
        String quantityStr = request.getParameter("txtQuantity");
        String description = request.getParameter("txtDescription");
        String createDate = request.getParameter("txtCreatedDate");
        String expirationDate = request.getParameter("txtExpirationDate");
        String statusIdStr = request.getParameter("txtStatusId");
        Part filePart = request.getPart("image");

        boolean foundErr = false;
        try {
            HttpSession session = request.getSession();
            ProductDAO productDAO = new ProductDAO();
            String proID = productDAO.getProductIdByName(productName);

            //checkName duplicate
            if (proID != null) {
                if (!proID.equals(productId)) {
                    foundErr = true;
                }
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
                

                int categoryId = Integer.parseInt(categoryStr);

                long price = Long.parseLong(priceStr);
                int quantity = Integer.parseInt(quantityStr);
                int statusId = Integer.parseInt(statusIdStr);

                ProductDTO productDTO = new ProductDTO(productId, productName, imageLink, price, quantity, categoryId, description, createDate, expirationDate, statusId);

                String currentDate = MyToys.getCurrentDateStr("yyyy-MM-dd HH:mm:ss");
                UpdateRecordDTO updateRecordDTO = new UpdateRecordDTO(currentDate, userId, productId);
                productDAO.updateProduct(productDTO, updateRecordDTO);
                  
                filePart.write(filePathAbsolute);

            }

        } catch (NamingException | SQLException | ParseException ex) {
            log.error("Error at UpdateProductController: " + ex.getMessage());
        } finally {
            response.sendRedirect("viewAllProduct");
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
