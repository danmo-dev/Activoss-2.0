package com.datacenter.asset.domain.asset;

import com.datacenter.asset.domain.exception.InvalidAssetException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount, "Money amount is required");
        Objects.requireNonNull(currency, "Money currency is required");
        if (amount.signum() < 0) {
            throw new InvalidAssetException("Money amount cannot be negative");
        }
    }
}