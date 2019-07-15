package com.CurrencyConversion;

public interface iReadWrite {

    void printValidCurrencies();

    boolean verifyCurrency (String currency, String tagName);

    double getConversionRate(String conversion);

    void printAllConversionRates();

    void addValidCurrency (String currencyToAdd);

    void addCurrencyPair (String currencyPair, double rate);

    void removeCurrency (String currencyToDelete, String tagName);
}

