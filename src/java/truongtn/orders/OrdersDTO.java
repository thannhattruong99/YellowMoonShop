/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.orders;

import java.io.Serializable;

/**
 *
 * @author truongtn
 */
public class OrdersDTO implements Serializable {

    private String orderId;
    private String orderDate;
    private String shippingAddress;
    private long totalPrice;
    private String userId;
    private String paymentId;
    private int statusId;
    private boolean cashed;

    public OrdersDTO() {
    }

    public OrdersDTO(String orderId, String orderDate, String shippingAddress, long totalPrice, String userId, String paymentId, int statusId, boolean cashed) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.paymentId = paymentId;
        this.statusId = statusId;
        this.cashed = cashed;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public boolean isCashed() {
        return cashed;
    }

    public void setCashed(boolean cashed) {
        this.cashed = cashed;
    }

}
