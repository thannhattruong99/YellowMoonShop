/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.product;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.naming.NamingException;
import truongtn.cart.CartObject;
import truongtn.utils.DBHelper;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
public class ProductDAO implements Serializable {

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

    public List<ProductDTO> loadProductInit() throws NamingException, SQLException, ParseException {
        List<ProductDTO> productList = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT TOP 10 P.productId, P.productName, P.image, C.categoryName, P.price, P.quantity, P.description, P.createdDate, P.expirationDate "
                        + "FROM TblProduct P "
                        + "INNER JOIN TblCategory C "
                        + "ON P.categoryId = C.categoryId "
                        + "WHERE P.statusId = 2 AND P.quantity > 0 AND P.expirationDate >= ? ORDER BY P.createdDate DESC ";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, MyToys.getCurrentDateStr("yyyy-MM-dd HH:mm:ss"));
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String productId = rs.getString("productId");
                    String name = rs.getString("productName");
                    String image = rs.getString("image");
                    long price = rs.getLong("price");
                    int quantity = rs.getInt("quantity");
                    String description = rs.getString("description");
                    String createdDate = rs.getString("createdDate");
                    String expirationDate = rs.getString("expirationDate");
                    String categpryStr = rs.getString("categoryName");
                    ProductDTO productDTO = new ProductDTO(productId, name, categpryStr, image, price, quantity, description, createdDate, expirationDate);
                    if (productList == null) {
                        productList = new ArrayList<>();
                    }
                    productList.add(productDTO);
                }

            }
        } finally {
            closeConnection();
        }
        return productList;
    }

    public long getMaxPrice() throws NamingException, SQLException, ParseException {
        long maxPrice = 0;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT MAX(price) AS MAX_PRICE FROM TblProduct "
                        + "WHERE statusId = 2 AND quantity > 0 AND expirationDate >= ? ";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, MyToys.getCurrentDateStr("yyyy-MM-dd HH:mm:ss"));
                rs = pstm.executeQuery();
                if (rs.next()) {
                    maxPrice = rs.getLong("MAX_PRICE");
                }

            }
        } finally {
            closeConnection();
        }
        return maxPrice;
    }

    public int countRecordForSearching(String productName, int categoryId, long minPrice, long maxPrice) throws NamingException, SQLException, ParseException {
        int result = 0;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT count(P.productId) AS TOTAL "
                        + "FROM TblProduct P "
                        + "WHERE P.productName LIKE ? "
                        + " AND P.statusId = 2 AND P.quantity > 0 AND P.expirationDate >= ? ";

                if (minPrice == -1) {
                    sql += " AND 0 = 0 ";
                } else {
                    sql += " AND (P.price BETWEEN " + minPrice + " AND " + maxPrice + " ) ";
                }
                if (categoryId == -1) {
                    sql += " AND 0 = 0 ";
                } else {
                    sql += " AND P.categoryId = " + categoryId;
                }
                pstm = con.prepareStatement(sql);
                if (MyToys.isNullOrEmpty(productName)) {
                    pstm.setString(1, "%%");
                } else {
                    pstm.setString(1, "%" + productName + "%");
                }
                pstm.setString(2, MyToys.getCurrentDateStr("yyyy-MM-dd HH:mm:ss"));
                rs = pstm.executeQuery();

                if (rs.next()) {
                    result = rs.getInt("TOTAL");
                }
            }
        } finally {
            closeConnection();
        }

        return result;
    }

    public List<ProductDTO> searchProduct(String productName, int categoryId, long minPrice, long maxPrice, int offSet, int size) throws SQLException, NamingException, ParseException {
        List<ProductDTO> productList = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT P.productId, P.productName, P.image, C.categoryName, P.price, P.quantity, P.description, P.createdDate, P.expirationDate "
                        + "FROM TblProduct P "
                        + "INNER JOIN TblCategory C "
                        + "ON P.categoryId = C.categoryId "
                        + "WHERE P.productName LIKE ? "
                        + "AND P.statusId = 2 AND P.quantity > 0 AND P.expirationDate >= ? ";

                if (minPrice == -1) {
                    sql += " AND 0 = 0 ";
                } else {
                    sql += " AND (P.price BETWEEN " + minPrice + " AND " + maxPrice + " ) ";
                }
                if (categoryId == -1) {
                    sql += " AND 0 = 0 ";
                } else {
                    sql += " AND P.categoryId = " + categoryId;
                }
                
                sql += " ORDER BY P.createdDate DESC "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                pstm = con.prepareStatement(sql);
                if (MyToys.isNullOrEmpty(productName)) {
                    pstm.setString(1, "%%");
                } else {
                    pstm.setString(1, "%" + productName + "%");
                }
                pstm.setString(2, MyToys.getCurrentDateStr("yyyy-MM-dd HH:mm:ss"));
                pstm.setInt(3, offSet);
                pstm.setInt(4, size);
                
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String productId = rs.getString("productId");
                    String name = rs.getString("productName");
                    String image = rs.getString("image");
                    long price = rs.getLong("price");
                    int quantity = rs.getInt("quantity");
                    String description = rs.getString("description");
                    String createdDate = rs.getString("createdDate");
                    String expirationDate = rs.getString("expirationDate");
                    String categpryStr = rs.getString("categoryName");
                    ProductDTO productDTO = new ProductDTO(productId, name, categpryStr, image, price, quantity, description, createdDate, expirationDate);
                    if (productList == null) {
                        productList = new ArrayList<>();
                    }
                    productList.add(productDTO);
                }

            }
        } finally {
            closeConnection();
        }
        return productList;

    }
    
    public List<ProductDTO> searchProductByAdmin(String productName, int categoryIdIn, long minPrice, long maxPrice, int offSet, int size) throws SQLException, NamingException, ParseException {
        List<ProductDTO> productList = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT P.productId, P.productName, P.statusId, P.image, P.categoryId, P.price, P.quantity, P.description, P.createdDate, P.expirationDate, P.userId "
                        + "FROM TblProduct P "
                        + "WHERE P.productName LIKE ? ";

                if (minPrice == -1) {
                    sql += " AND 0 = 0 ";
                } else {
                    sql += " AND (P.price BETWEEN " + minPrice + " AND " + maxPrice + " ) ";
                }
                if (categoryIdIn == -1) {
                    sql += " AND 0 = 0 ";
                } else {
                    sql += " AND P.categoryId = " + categoryIdIn;
                }
                
                sql += " ORDER BY P.createdDate DESC "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                pstm = con.prepareStatement(sql);
                if (MyToys.isNullOrEmpty(productName)) {
                    pstm.setString(1, "%%");
                } else {
                    pstm.setString(1, "%" + productName + "%");
                }
                pstm.setInt(2, offSet);
                pstm.setInt(3, size);
                
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String productId = rs.getString("productId");
                    String name = rs.getString("productName");
                    String image = rs.getString("image");
                    int statusId = rs.getInt("statusId");
                    long price = rs.getLong("price");
                    int quantity = rs.getInt("quantity");
                    String description = rs.getString("description");
                    String createdDate = rs.getString("createdDate");
                    String expirationDate = rs.getString("expirationDate");
                    int categoryId = rs.getInt("categoryId");
                    String userId = rs.getString("userId");
                    ProductDTO productDTO = new ProductDTO(productId, productName, image, price, quantity, categoryId, description, createdDate, expirationDate, statusId, userId);
                    if (productList == null) {
                        productList = new ArrayList<>();
                    }
                    productList.add(productDTO);
                }

            }
        } finally {
            closeConnection();
        }
        return productList;

    }
    
    public CartObject getProductListByID(CartObject cartObject) throws NamingException, SQLException, ParseException{
        CartObject result = null;
        try {
            con = DBHelper.makeConnection();
            if(con != null){
                String sql = "SELECT price, quantity FROM TblProduct WHERE productId = ? "
                        + "AND statusId = 2 AND quantity > 0 AND expirationDate >= ? ";
                pstm = con.prepareStatement(sql);
                for (Entry<String, ProductDTO> entry : cartObject.getItems().entrySet()) {
                    pstm.setString(1, entry.getKey());
                    pstm.setString(2, MyToys.getCurrentDateStr("yyyy-MM-dd HH:mm:ss"));
                    rs = pstm.executeQuery();
                    if(rs.next()){
                        long price = rs.getLong("price");
                        int quantity = rs.getInt("quantity");
                        if(result == null){
                            result = new CartObject();
                        }
                        ProductDTO productDTO = new ProductDTO();
                        productDTO.setPrice(price);
                        productDTO.setQuantity(quantity);

                        result.addItemsToCart(entry.getKey(), productDTO);
                    }
                }
            }
        } finally{
            closeConnection();
        }
        return result;
    }
    
    public boolean isDuplicateProductName(String productName) throws NamingException, SQLException{
        try {
            con = DBHelper.makeConnection();
            if(con != null){
                String sql = "SELECT productID FROM TblProduct WHERE upper(productName) = upper(?) AND statusId = 2";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, productName);
                
                rs = pstm.executeQuery();
                if(rs.next()){
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        
        return false;
    }
    
    public boolean createProduct(ProductDTO productDTO) throws NamingException, SQLException{
        try {
            con = DBHelper.makeConnection();
            if(con != null){
                String sql = "INSERT INTO TblProduct(productName, image, price, quantity, categoryId, description, createdDate, expirationDate, statusId, userId) VALUES(?,?,?,?,?,?,?,?,?,?)";
                pstm = con.prepareStatement(sql);
                
                pstm.setString(1, productDTO.getProductName());
                pstm.setString(2, productDTO.getImage());
                pstm.setLong(3, productDTO.getPrice());
                pstm.setInt(4, productDTO.getQuantity());
                pstm.setInt(5, productDTO.getCategoryId());
                pstm.setString(6, productDTO.getDescription());
                pstm.setString(7, productDTO.getCreatedDate());
                pstm.setString(8, productDTO.getExpirationDate());
                pstm.setInt(9, productDTO.getStatusId());
                pstm.setString(10, productDTO.getUserId());
                
                int rows = pstm.executeUpdate();
                if(rows > 0){
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        
        return false;
    }

}
