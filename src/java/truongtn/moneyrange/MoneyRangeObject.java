/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.moneyrange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author truongtn
 */
public class MoneyRangeObject implements Serializable {

    private final static int MONEY_RANGE = 20000;
    private long minPrice;
    private long maxPrice;
    private List<MoneyRangeObject> moneyRange;

    public MoneyRangeObject() {
    }

    public MoneyRangeObject(long minPrice, long maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public MoneyRangeObject(long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    }

    public long getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<MoneyRangeObject> getMoneyRange() {
        moneyRange = new ArrayList<>();
        int totalOfRange = (int) Math.ceil((float) maxPrice / MONEY_RANGE);
        for (int i = 0; i < totalOfRange;) {
            int min = i * MONEY_RANGE;
            int max = (++i) * MONEY_RANGE;
            moneyRange.add(new MoneyRangeObject(min, max));
        }
        return moneyRange;
    }

    public void setMoneyRange(List<MoneyRangeObject> moneyRange) {
        this.moneyRange = moneyRange;
    }

}
