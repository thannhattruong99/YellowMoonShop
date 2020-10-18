/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.search;

import java.io.Serializable;

/**
 *
 * @author truongtn
 */
public class SearchObject implements Serializable {

    private String searchValue;
    private long minPrice;
    private int categoryId;
    private int numberPage;

    public SearchObject() {
        this.minPrice = -1;
        this.numberPage = 0;
        this.categoryId = -1;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

}
