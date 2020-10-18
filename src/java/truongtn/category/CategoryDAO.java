/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.category;

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
public class CategoryDAO implements Serializable{
    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;
    
    private void closeConnection() throws SQLException{
        if(rs != null){
            rs.close();
        }
        if(pstm != null){
            pstm.close();
        }
        if(rs != null){
            rs.close();
        }
    }
    
    public List<CategoryDTO> getCategoryList() throws NamingException, SQLException{
        List<CategoryDTO> categoryList = null;
        
        try {
            con = DBHelper.makeConnection();
            if(con != null){
                String sql = "SELECT categoryId, categoryName FROM TblCategory";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while(rs.next()){
                    if(categoryList == null){
                        categoryList = new ArrayList<>();
                    }
                    int categoryId = rs.getInt("categoryId");
                    String categoryName = rs.getString("categoryName");
                    CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryName);
                    categoryList.add(categoryDTO);
                }
            }
        } finally {
            closeConnection();
        }
        return categoryList;
    } 
    
    public int getCategoryId(String categoryName) throws NamingException, SQLException{
        int categoryId = -1;
        try {
            con = DBHelper.makeConnection();
            if(con != null){
                String sql = "SELECT categoryId FROM TblCategory WHERE upper(categoryName) = upper(?)";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, categoryName);
                rs = pstm.executeQuery();
                if(rs.next()){
                    categoryId = rs.getInt("categoryId");
                }
            }
        } finally {
            closeConnection();
        }
        return categoryId;
    }
    
    public boolean createCategory(String categoryName) throws NamingException, SQLException{
        try {
            con = DBHelper.makeConnection();
            if(con != null){
                String insertSql = "INSERT INTO TblCategory(categoryName) VALUES (?)";
                pstm = con.prepareStatement(insertSql);
                pstm.setString(1, categoryName);
                
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