/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.orderdetail;

import java.io.Serializable;

/**
 *
 * @author truongtn
 */
public class OrderDetailDTO implements Serializable {

    private String orderDetailId;
    private int quantity;
    private long price;
    private String orderId;
    private String productId;
    private String productName;
    private long totalPrice;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(int quantity, long price, String productName, long totalPrice) {
        this.quantity = quantity;
        this.price = price;
        this.productName = productName;
        this.totalPrice = totalPrice;
    }

    
    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
