package com.CurrencyConversion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Currency {
    private List<String> validCurrencies = new ArrayList<>();
    Map<String, Double> currConversions = new HashMap<>();
    private String fromCurrency;
    private String toCurrency;
    private double amountToConvert;

    Currency() {
        validCurrencies.add("USD");
        validCurrencies.add("CAD");
        validCurrencies.add("ISK");
        currConversions.put("CAD_TO_USD", 0.7634);
        currConversions.put("ISK_TO_USD", 0.0079);
        currConversions.put("USD_TO_USD", 1.0000);
    }

    boolean verifyCurrency(String input) {
        return (validCurrencies.contains(input));
    }

    double convertCurrency() {
        String convFrom = fromCurrency  + "_TO_USD";
        String convTo   = toCurrency    + "_TO_USD";
        return amountToConvert * currConversions.get(convFrom)/currConversions.get(convTo);
    }

    void addCurrencyPair() {
        if (!validCurrencies.contains(getFromCurrency())) {
            validCurrencies.add(getFromCurrency());
        }
        if (!validCurrencies.contains(getToCurrency())) {
            validCurrencies.add(getToCurrency());
        }
        String pairToAdd = getFromCurrency() + "_TO_" + getToCurrency();
        currConversions.put(pairToAdd, getAmountToConvert());

        //addMissingConversions();  //stretch goal here
        clearData();
    }

    void removeCurrencyPair() {
        if (verifyCurrency(getFromCurrency())) {
            validCurrencies.remove(getFromCurrency());
        }

        String stringToRemove = getFromCurrency() + "_TO_USD";
        currConversions.remove(stringToRemove);

        clearData();
    }

    private void clearData() {
        setFromCurrency("");
        setToCurrency("");
        setAmountToConvert(0);
    }

//**************************GETTERS AND SETTERS**************************
    String getValidCurrencies() {
        String result = "";
        for (String i : validCurrencies) {
            result = result + i + " ";
        }
        return result;
    }

    String getFromCurrency() {
        return fromCurrency;
    }

    String getToCurrency() {
        return toCurrency;
    }

    double getAmountToConvert() {
        return amountToConvert;
    }

    void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency.toUpperCase();
    }

    void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency.toUpperCase();
    }

    void setAmountToConvert(double amountToConvert) {
        this.amountToConvert = amountToConvert;
    }
}
