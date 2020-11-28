/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import truongtn.product.ProductDTO;

/**
 *
 * @author truongtn
 */
public class CartObject implements Serializable {

    private Map<String, ProductDTO> items;
    private long totalPrice;

    public CartObject() {
    }

    public void addItemsToCart(String productId, ProductDTO productDTO) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        if (this.items.containsKey(productId)) {
            ProductDTO tmp = this.items.get(productId);
            tmp.setQuantity(tmp.getQuantity() + productDTO.getQuantity());
            this.items.replace(productId, tmp);
        } else {
            this.items.put(productId, productDTO);
        }
    }
    
    public void updateQuantityItemInCart(String productId, ProductDTO productDTO){
        if (this.items == null) {
            return;
        }
        if (this.items.containsKey(productId)) {
            if(productDTO.getQuantity() <= 0){
                removeItem(productId);
            }else{
                this.items.replace(productId, productDTO);
            }
        }
    }

    public void removeItem(String productId) {
        if (this.items == null) {
            return;
        }
        if (this.items.containsKey(productId)) {
            this.items.remove(productId);
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }

    public Map<String, ProductDTO> getItems() {
        return items;
    }

    public void totalPrice() {
        this.totalPrice = 0;
        if(this.items == null){
            return;
        }
        for (Entry<String, ProductDTO> entry : this.items.entrySet()) {
            totalPrice += entry.getValue().getTotal();
        }
    }

    public long getTotalPrice() {
        totalPrice();
        return totalPrice;
    }

}