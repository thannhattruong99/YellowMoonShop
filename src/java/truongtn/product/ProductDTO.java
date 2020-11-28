/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.product;

import java.io.Serializable;

/**
 *
 * @author truongtn
 */
public class ProductDTO implements Serializable {

    private String productId;
    private String productName;
    private String image;
    private long price;
    private int quantity;
    private int categoryId;
    private String categoryStr;
    private String description;
    private String createdDate;
    private String expirationDate;
    private int statusId;
    private long total;
    private String userId;

    public ProductDTO() {
    }

    public ProductDTO(String productName, long price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductDTO(String productName, String image, long price, int quantity, int categoryId, String description, String createdDate, String expirationDate, int statusId, String userId) {
        this.productName = productName;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.description = description;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
        this.statusId = statusId;
        this.userId = userId;
    }

    public ProductDTO(String productId, String productName, String categoryStr, String image, long price, int quantity, String description, String createdDate, String expirationDate) {
        this.productId = productId;
        this.productName = productName;
        this.categoryStr = categoryStr;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
    }

    public ProductDTO(String productId, String productName, String image, long price, int quantity, int categoryId, String description, String createdDate, String expirationDate, int statusId, String userId) {
        this.productId = productId;
        this.productName = productName;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.description = description;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
        this.statusId = statusId;
        this.userId = userId;
    }

    public ProductDTO(String productId, String productName, String image, long price, int quantity, int categoryId, String description, String createdDate, String expirationDate, int statusId) {
        this.productId = productId;
        this.productName = productName;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.description = description;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
        this.statusId = statusId;
    }
    
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getCategoryStr() {
        return categoryStr;
    }

    public void setCategoryStr(String categoryStr) {
        this.categoryStr = categoryStr;
    }

    public long getTotal() {
        this.total = this.quantity * this.price;
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
