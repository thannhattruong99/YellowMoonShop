/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.orders;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.naming.NamingException;
import truongtn.cart.CartObject;
import truongtn.product.ProductDTO;
import truongtn.utils.DBHelper;

/**
 *
 * @author truongtn
 */
public class OrdersDAO implements Serializable {

    private Connection con = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public boolean isDuplicateOrderId(String orderId) throws NamingException, SQLException {
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT (orderId) AS RECORDS FROM TblOrders WHERE orderId = ?";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, orderId);
                rs = pstm.executeQuery();
                int record = 0;
                if (rs.next()) {
                    record = rs.getInt("RECORDS");
                }
                if (record > 0) {
                    return true;
                }

            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean checkoutOrder(OrdersDTO ordersDTO, CartObject cartObject) throws SQLException {
        PreparedStatement pstmOrderDetail;
        PreparedStatement pstmProduct;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                con.setAutoCommit(false);

                String orderSql = "INSERT INTO TblOrders(orderId, orderDate, shippingAddress, totalPrice, userId, paymentId, statusId, cashed) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                String orderDetailSql = "INSERT INTO TblOrderDetail(quantity, price, orderId, productId, totalPrice) VALUES (?, ?, ?, ?, ?)";
                String productSql = "UPDATE TblProduct set quantity = (SELECT quantity From TblProduct WHERE productId = ? ) - ? WHERE productId = ?";

                pstm = con.prepareStatement(orderSql);
                pstmOrderDetail = con.prepareStatement(orderDetailSql);
                pstmProduct = con.prepareStatement(productSql);

                pstm.setString(1, ordersDTO.getOrderId());
                pstm.setString(2, ordersDTO.getOrderDate());
                pstm.setString(3, ordersDTO.getShippingAddress());
                pstm.setLong(4, cartObject.getTotalPrice());
                pstm.setString(5, ordersDTO.getUserId());
                pstm.setString(6, ordersDTO.getPaymentId());
                pstm.setInt(7, ordersDTO.getStatusId());
                pstm.setBoolean(8, ordersDTO.isCashed());

                int rows = pstm.executeUpdate();
                if (rows > 0) {
                    for (Entry<String, ProductDTO> entry : cartObject.getItems().entrySet()) {
                        pstmProduct.setString(1, entry.getKey());
                        pstmProduct.setLong(2, entry.getValue().getQuantity());
                        pstmProduct.setString(3, entry.getKey());
                        rows = pstmProduct.executeUpdate();
                        if (rows <= 0) {
                            throw new Exception();
                        }

                        pstmOrderDetail.setInt(1, entry.getValue().getQuantity());
                        pstmOrderDetail.setLong(2, entry.getValue().getPrice());
                        pstmOrderDetail.setString(3, ordersDTO.getOrderId());
                        pstmOrderDetail.setString(4, entry.getKey());
                        pstmOrderDetail.setLong(5, entry.getValue().getTotal());
                        rows = pstmOrderDetail.executeUpdate();
                        if (rows <= 0) {
                            throw new Exception();
                        }
                    }
                }
                con.commit();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error at OrderDAO: " + e.getMessage());
            if (con != null) {
                con.rollback();
            }
        } finally {
            closeConnection();
        }

        return false;
    }

    public List<OrdersDTO> getOrdersByUserUserId(String userId) throws NamingException, SQLException {
        List<OrdersDTO> ordersDTOs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT orderId, orderDate, shippingAddress, totalPrice, paymentId, statusId, cashed "
                        + "FROM TblOrders Where userID = ? ORDER BY orderDate  DESC";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, userId);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    if (ordersDTOs == null) {
                        ordersDTOs = new ArrayList<>();
                    }

                    String orderId = rs.getString("orderId");
                    String orderDate = rs.getString("orderDate");
                    String address = rs.getString("shippingAddress");
                    long totalPrice = rs.getLong("totalPrice");
                    String paymentId = rs.getString("paymentId");
                    int statusId = rs.getInt("statusId");
                    boolean cashed = rs.getBoolean("cashed");

                    OrdersDTO ordersDTO = new OrdersDTO(orderId, orderDate, address, totalPrice, userId, paymentId, statusId, cashed);
                    ordersDTOs.add(ordersDTO);
                }
                return ordersDTOs;
            }
            return ordersDTOs;
        } finally {
            closeConnection();
        }
    }

    public List<OrdersDTO> searchById(String orderIdIn, String userId) throws SQLException, NamingException {
        List<OrdersDTO> ordersDTOs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT DISTINCT O.orderId, O.shippingAddress, O.orderDate, O.totalPrice, O.statusId, O.paymentId, O.cashed FROM TblAccount A "
                        + "INNER JOIN TblOrders O "
                        + "ON A.userId = O.userId "
                        + "AND A.userId = ? "
                        + "INNER JOIN TblOrderDetail OD "
                        + "ON O.orderId = OD.orderId "
                        + "WHERE O.orderId LIKE ?";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, userId);
                pstm.setString(2, "%" + orderIdIn + "%");
                
                rs = pstm.executeQuery();
                while (rs.next()) {                    
                    if(ordersDTOs == null){
                        ordersDTOs = new ArrayList<>();
                    }
                    String orderId = rs.getString("orderId");
                    String shippingAddr = rs.getString("shippingAddress");
                    String orderDate = rs.getString("orderDate");
                    long totalPrice = rs.getLong("totalPrice");
                    int statusId = rs.getInt("statusId");
                    String paymentId = rs.getString("paymentId");
                    boolean cashed = rs.getBoolean("cashed");
                    OrdersDTO ordersDTO = new OrdersDTO(orderId, orderDate, shippingAddr, totalPrice, userId, paymentId, statusId, cashed);
                    ordersDTOs.add(ordersDTO);
                }
            }
        } finally {
            closeConnection();
        }
        return ordersDTOs;
    }

}
