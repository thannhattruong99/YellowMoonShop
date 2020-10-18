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
public class CreateProductError implements Serializable {

    private String productNameLengErr;
    private String productNameDuplicateErr;
    private String categoryLengthErr;
    private String priceTypeErr;
    private String quantityTypeErr;
    private String descriptionLengthErr;
    private String createDateErr;
    private String expirationDateErr;

    public CreateProductError() {
    }

    public CreateProductError(String productNameLengErr, String priceTypeErr, String quantityTypeErr, String descriptionLengthErr) {
        this.productNameLengErr = productNameLengErr;
        this.priceTypeErr = priceTypeErr;
        this.quantityTypeErr = quantityTypeErr;
        this.descriptionLengthErr = descriptionLengthErr;
    }

    public String getProductNameLengErr() {
        return productNameLengErr;
    }

    public void setProductNameLengErr(String productNameLengErr) {
        this.productNameLengErr = productNameLengErr;
    }

    public String getPriceTypeErr() {
        return priceTypeErr;
    }

    public void setPriceTypeErr(String priceTypeErr) {
        this.priceTypeErr = priceTypeErr;
    }

    public String getQuantityTypeErr() {
        return quantityTypeErr;
    }

    public void setQuantityTypeErr(String quantityTypeErr) {
        this.quantityTypeErr = quantityTypeErr;
    }

    public String getDescriptionLengthErr() {
        return descriptionLengthErr;
    }

    public void setDescriptionLengthErr(String descriptionLengthErr) {
        this.descriptionLengthErr = descriptionLengthErr;
    }

    public String getCreateDateErr() {
        return createDateErr;
    }

    public void setCreateDateErr(String createDateErr) {
        this.createDateErr = createDateErr;
    }

    public String getExpirationDateErr() {
        return expirationDateErr;
    }

    public void setExpirationDateErr(String expirationDateErr) {
        this.expirationDateErr = expirationDateErr;
    }

    public String getCategoryLengthErr() {
        return categoryLengthErr;
    }

    public void setCategoryLengthErr(String categoryLengthErr) {
        this.categoryLengthErr = categoryLengthErr;
    }

    public String getProductNameDuplicateErr() {
        return productNameDuplicateErr;
    }

    public void setProductNameDuplicateErr(String productNameDuplicateErr) {
        this.productNameDuplicateErr = productNameDuplicateErr;
    }

}
