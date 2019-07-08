package com.CurrencyConversion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Currency {
    private static List<String> validCurrencies = new ArrayList<>();
    static Map<String, Double> currConversions = new HashMap<>();
    private static String fromCurrency;
    private static String toCurrency;
    private static Double amountToConvert;

    Currency() {
//        validCurrencies.add("USD");
//        validCurrencies.add("CAD");
//        validCurrencies.add("ISK");
//        currConversions.put("CAD_TO_USD", 0.7634);
//        currConversions.put("ISK_TO_USD", 0.0079);
//        currConversions.put("USD_TO_USD", 1.0000);

    }

    double convertCurrency() {
        String convFrom = fromCurrency  + "_TO_USD";
        String convTo   = toCurrency    + "_TO_USD";
        //TODO - change currConversions.get to XML lookups
        return getAmountToConvert() * xmlReadWrite.getConversionRate(convFrom)/xmlReadWrite.getConversionRate(convTo);
        //return amountToConvert * currConversions.get(convFrom)/currConversions.get(convTo);
    }

    void addCurrencyPair() {
        if (!validCurrencies.contains(getFromCurrency())) {
            validCurrencies.add(getFromCurrency());
        }

        String pairToAdd = getFromCurrency() + "_TO_" + getToCurrency();
        //TODO - change to xml write
        currConversions.put(pairToAdd, getAmountToConvert());
        clearData();
    }

    void removeCurrencyPair() {
        if (xmlReadWrite.verifyCurrency(getFromCurrency())) {
            //TODO - REMOVE FROM XML 
            //validCurrencies.remove(getFromCurrency());
        }

        String stringToRemove = getFromCurrency() + "_TO_USD";

        //TODO - change to XML edit
        //currConversions.remove(stringToRemove);

        clearData();
    }

    private void clearData() {
        setFromCurrency("");
        setToCurrency("");
        setAmountToConvert(0);
    }

//**************************GETTERS AND SETTERS**************************
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
