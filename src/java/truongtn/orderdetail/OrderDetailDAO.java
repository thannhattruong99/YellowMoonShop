/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.orderdetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import truongtn.utils.DBHelper;

/**
 *
 * @author truongtn
 */
public class OrderDetailDAO implements Serializable {

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

    public List<OrderDetailDTO> getListOrderDetailById(String ordersId) throws NamingException, SQLException {
        List<OrderDetailDTO> orderDetailDTOs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT P.productName, O.quantity, O.price, O.totalPrice FROM TblOrderDetail O "
                        + "INNER JOIN TblProduct P "
                        + "ON O.productId = P.productId "
                        + "WHERE orderId = ?";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, ordersId);
                rs = pstm.executeQuery();
                while (rs.next()) {                    
                    if(orderDetailDTOs == null){
                        orderDetailDTOs = new ArrayList<>();
                    }
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    long price = rs.getLong("price");
                    long totalPrice = rs.getLong("totalPrice");
                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO(quantity, price, productName, totalPrice);
                    orderDetailDTOs.add(orderDetailDTO);
                }
            }  
        } finally {
            closeConnection();
        }
        return orderDetailDTOs;
    }
}
