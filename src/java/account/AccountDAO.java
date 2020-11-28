/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import truongtn.utils.DBHelper;

/**
 *
 * @author truongtn
 */
public class AccountDAO implements Serializable {

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

    public AccountDTO checkLogin(AccountDTO accountDTO) throws NamingException, SQLException {
        AccountDTO resultDTO = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Select fullname, phone, address, roleId From TblAccount WHERE userId = ? AND password = ? AND statusId = ?";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, accountDTO.getUserId());
                pstm.setString(2, accountDTO.getPassword());
                pstm.setInt(3, accountDTO.getStatusId());

                rs = pstm.executeQuery();
                if (rs.next()) {
                    String fullname = rs.getString("fullname");
                    String address = rs.getString("address");
                    int roleId = rs.getInt("roleId");
                    
                    if (resultDTO == null) {
                        resultDTO = new AccountDTO();
                    }
                    resultDTO.setUserId(accountDTO.getUserId());
                    resultDTO.setFullname(fullname);
                    resultDTO.setAddress(address);
                    resultDTO.setRoleId(roleId);
                    
                }
            }
        } finally {
            closeConnection();
        }
        return resultDTO;
    }

    public boolean createAccount(AccountDTO accountDTO) throws SQLException, NamingException {
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Insert into TblAccount (userId, password, fullname, roleId, statusId) values (?, ?, ?, ?, ?)";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, accountDTO.getUserId());
                pstm.setString(2, accountDTO.getPassword());
                pstm.setString(3, accountDTO.getFullname());
                pstm.setInt(4, accountDTO.getRoleId());
                pstm.setInt(5, accountDTO.getStatusId());

                int row = pstm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
    public AccountDTO getAccountByUserId(String userId) throws NamingException, SQLException{
        AccountDTO accountDTO = null;
        try {
            con = DBHelper.makeConnection();
            if(con != null){
                String sql = "SELECT fullname, roleId, statusId FROM TblAccount WHERE userId = ?";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, userId);
                
                rs = pstm.executeQuery();
                
                
                if(rs.next()){
                    String fullname = rs.getString("fullname");
                    int roleId = rs.getInt("roleId");
                    int statusId = rs.getInt("statusId");
                    accountDTO = new AccountDTO();
                    accountDTO.setUserId(userId);
                    accountDTO.setFullname(fullname);
                    accountDTO.setRoleId(roleId);
                    accountDTO.setStatusId(statusId);
                }               
            }
        } finally {
            closeConnection();
        }
        return accountDTO;
    }
    
    public boolean updateAccount(String userId, String fullname) throws NamingException, SQLException{
        try {
            con = DBHelper.makeConnection();
            if(con != null){
                String sql = "Update TblAccount set fullname = ? WHERE userId = ?";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, fullname);
                pstm.setString(2, userId);
                
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

    public boolean createAccountByNumberPhone(AccountDTO accountDTO) throws NamingException, SQLException{
        try {
            con = DBHelper.makeConnection();
            if(con != null){
                String sql = "INSERT INTO TblAccount(userId, fullname, phone, address, roleId, statusId) VALUES (?, ?, ?, ?, ?, ?)";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, accountDTO.getUserId());
                pstm.setString(2, accountDTO.getFullname());
                pstm.setString(3, accountDTO.getPhone());
                pstm.setString(4, accountDTO.getAddress());
                pstm.setInt(5, accountDTO.getRoleId());
                pstm.setInt(6, accountDTO.getStatusId());
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
    
    public boolean createAccountByFaceBook(AccountDTO accountDTO) throws NamingException, SQLException{
        try {
            con = DBHelper.makeConnection();
            if(con != null){
                String sql = "INSERT INTO TblAccount(userId, fullname, roleId, statusId) VALUES (?, ?, ?, ?)";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, accountDTO.getUserId());
                pstm.setString(2, accountDTO.getFullname());
                pstm.setInt(3, accountDTO.getRoleId());
                pstm.setInt(4, accountDTO.getStatusId());
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
    
    public boolean updateAccountRole(AccountDTO accountDTO) throws NamingException, SQLException {
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Update TblAccount set statusId = ? Where userId = ?";
                pstm = con.prepareStatement(sql);
                pstm.setInt(1, accountDTO.getStatusId());
                pstm.setString(2, accountDTO.getUserId());

                int row = pstm.executeUpdate();

                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

}
