package com.CurrencyConversion;

public interface CurrencyInterface {

    void saveCurrency(Currency currency);

    Currency loadCurrency(String currencyName);

    String listCurrencies();

    void removeCurrency(Currency currency);

    void update();
}
