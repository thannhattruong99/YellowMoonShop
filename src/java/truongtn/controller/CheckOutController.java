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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import truongtn.cart.CartObject;
import truongtn.orders.OrdersDAO;
import truongtn.orders.OrdersDTO;
import truongtn.product.ProductDAO;
import truongtn.product.ProductDTO;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
public class CheckOutController extends HttpServlet {

    private final String SUCCESS = "ViewOrders?txtOrderId=";
    private final String FAIL = "cart.jsp";

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

        HttpSession session = request.getSession();

        String btnAction = request.getParameter("btnAciton");
        String userId = request.getParameter("txtUserId");
        String fullname = request.getParameter("txtFullname");
        String shippingAddr = request.getParameter("txtShippingAddr");

        String url = FAIL;
        List<String> errorList = null;

        try {

            errorList = checkInput(userId, shippingAddr, fullname);

            if (errorList == null) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setUserId(userId);
                accountDTO.setFullname(fullname);
                accountDTO.setAddress(shippingAddr);
                accountDTO.setRoleId(1);

                session.setAttribute("USER", accountDTO);

                CartObject cartObject = (CartObject) session.getAttribute("CART");
                if (cartObject != null && cartObject.getItems().size() > 0) {
                    ProductDAO productDAO = new ProductDAO();
                    CartObject wareHouse = productDAO.getProductListByID(cartObject);
                    if (wareHouse != null) {
                        errorList = checkoutWareHoue(cartObject, wareHouse);
                        if (errorList == null || errorList.isEmpty()) {
                            String orderId = checkOut(userId, shippingAddr, cartObject);
                            if (orderId != null) {
                                session.removeAttribute("ERRORS");
                                session.removeAttribute("CART");
                                url = SUCCESS + orderId;
                            }
                        }
                    }
                }
            }

            session.setAttribute("ERRORS", errorList);

        } catch (NamingException | SQLException | ParseException ex) {
            if (errorList == null) {
                errorList = new ArrayList<>();
                errorList.add("Some errors is occured. We apolize");
                session.setAttribute("ERRORS", errorList);
            }
            url = FAIL;

            System.out.println("Error at CheckOutController: " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
            out.close();
        }
    }

    private List<String> checkoutWareHoue(CartObject userCart, CartObject wareHouse) {
        List<String> result = null;

        if (userCart.getItems().size() != wareHouse.getItems().size()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add("Some product is unavaible.");
        }

        for (Entry<String, ProductDTO> entry : userCart.getItems().entrySet()) {
            String productId = entry.getKey();
            ProductDTO productOfUser = entry.getValue();
            ProductDTO productOfShop = wareHouse.getItems().get(productId);
            if (productOfShop != null) {
                String errorMsg = compareProduct(productOfUser, productOfShop);
                if (!MyToys.isNullOrEmpty(errorMsg)) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(errorMsg);
                }
            }
        }

        return result;
    }

    private String compareProduct(ProductDTO productOfUser, ProductDTO productOfShop) {
        String msg = "";

        if (productOfUser.getQuantity() > productOfShop.getQuantity()) {
            msg += productOfUser.getProductName() + " have " + productOfShop.getQuantity() + " items remaining.";
        }
        if (productOfUser.getPrice() != productOfShop.getPrice()) {
            msg += productOfUser.getProductName() + " have just updated price te be " + productOfShop.getPrice();
        }
        return msg;
    }

    private List<String> checkInput(String userId, String shippingAddr, String fullname) throws NamingException, SQLException {
        List<String> errorList = null;
        if (MyToys.isNullOrEmpty(userId)) {
            if (errorList == null) {
                errorList = new ArrayList<>();
            }
            errorList.add("You must login or input number phone to check out.");
        }
        if (MyToys.isNullOrEmpty(shippingAddr)) {
            if (errorList == null) {
                errorList = new ArrayList<>();
            }
            errorList.add("Please input your shipping address.");
        }

        if (MyToys.isNullOrEmpty(fullname)) {
            if (errorList == null) {
                errorList = new ArrayList<>();
            }
            errorList.add("Please input your name.");
        }

        if (errorList != null) {
            return errorList;
        }

        AccountDAO accountDAO = new AccountDAO();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(userId);
        accountDTO.setFullname(fullname);
        accountDTO.setPhone(userId);
        accountDTO.setAddress(shippingAddr);
        accountDTO.setRoleId(1);
        accountDTO.setStatusId(2);

        AccountDTO result = accountDAO.getAccountByUserId(userId);
        if (result == null) {
            accountDAO.createAccountByNumberPhone(accountDTO);
        } else {
            if (result.getRoleId() != 1 || result.getStatusId() != 2) {
                if (errorList == null) {
                    errorList = new ArrayList<>();
                }
                errorList.add("Your action is denied with this account.");
            } else {
                accountDAO.updateAccount(userId, fullname);
            }
        }

        return errorList;
    }

    private String checkOut(String userId, String shippingAddr, CartObject cartObject) throws NamingException, SQLException, ParseException {
        OrdersDAO ordersDAO = new OrdersDAO();

        String orderId = MyToys.getRandomUID();
        while (ordersDAO.isDuplicateOrderId(orderId)) {
            orderId = MyToys.getRandomUID();
        }

        OrdersDTO ordersDTO = new OrdersDTO(orderId, MyToys.getCurrentDateStr("yyyy-MM-dd HH:mm:ss"), shippingAddr, cartObject.getTotalPrice(), userId, null, 4, true);

        if (ordersDAO.checkoutOrder(ordersDTO, cartObject)) {
            return orderId;
        }
        return null;
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
