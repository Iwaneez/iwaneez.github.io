package com.iwaneez.stuffer.core.exchange.bo;

import org.knowm.xchange.currency.Currency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WalletBalance {
    private Currency balanceCurrency;
    private List<WalletBalanceItem> balanceItems;
    private BigDecimal totalPrice;

    public WalletBalance(Currency balanceCurrency) {
        this.balanceCurrency = balanceCurrency;
        this.balanceItems = new ArrayList<>();
        this.totalPrice = BigDecimal.ZERO;
    }

    public Currency getBalanceCurrency() {
        return balanceCurrency;
    }

    public List<WalletBalanceItem> getBalanceItems() {
        return balanceItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void addBalanceItem(WalletBalanceItem balanceItem) {
        balanceItems.add(balanceItem);
        totalPrice = totalPrice.add(balanceItem.getPrice());
    }

}
