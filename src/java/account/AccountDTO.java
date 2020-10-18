/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import java.io.Serializable;

/**
 *
 * @author truongtn
 */
public class AccountDTO implements Serializable {

    private String userId;
    private String password;
    private String fullname;
    private String phone;
    private String address;
    private int roleId;
    private int statusId;

    public AccountDTO() {
    }

    public AccountDTO(String fullname) {
        this.fullname = fullname;
    }

    public AccountDTO(String userId, String fullname) {
        this.userId = userId;
        this.fullname = fullname;
    }

    public AccountDTO(String userId, int roleId, int statusId) {
        this.userId = userId;
        this.roleId = roleId;
        this.statusId = statusId;
    }

    public AccountDTO(String userId, String password, int statusId) {
        this.userId = userId;
        this.password = password;
        this.statusId = statusId;
    }

    public AccountDTO(String userId, int statusId) {
        this.userId = userId;
        this.statusId = statusId;
    }

    public AccountDTO(String userId, String fullname, String phone, String address, int roleId, int statusId) {
        this.userId = userId;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.roleId = roleId;
        this.statusId = statusId;
    }

    public AccountDTO(String userId, String password, String fullname, int roleId, int statusId) {
        this.userId = userId;
        this.password = password;
        this.fullname = fullname;
        this.roleId = roleId;
        this.statusId = statusId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
