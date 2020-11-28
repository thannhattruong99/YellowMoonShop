/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.status;

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
public class StatusDAO implements Serializable {

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

    public List<StatusDTO> getStatusOfCake() throws NamingException, SQLException {
        List<StatusDTO> statusDTOs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT statusId, statusName FROM TblStatus WHERE upper(statusName) = 'ACTIVE' OR upper(statusName) = 'INACTIVE'";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    if (statusDTOs == null) {
                        statusDTOs = new ArrayList<>();
                    }
                    int statusId = rs.getInt("statusId");
                    String statusName = rs.getString("statusName");
                    statusDTOs.add(new StatusDTO(statusId, statusName));
                }
            }
        } finally {
            closeConnection();
        }
        return statusDTOs;
    }
}
