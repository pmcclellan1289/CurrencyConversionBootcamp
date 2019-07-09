package com.CurrencyConversion;

class Currency {
    private String fromCurrency;
    private String toCurrency;
    private Double amountToConvert;

    Currency() { }

    double convertCurrency() {
        String convFrom = fromCurrency + "_TO_USD";
        String convTo   = toCurrency   + "_TO_USD";
        return getAmountToConvert() * xmlReadWrite.getConversionRate(convFrom)
                                    / xmlReadWrite.getConversionRate(convTo);
    }

    void addCurrencyPair() {
        if (!xmlReadWrite.verifyCurrency(getFromCurrency())) {
             xmlReadWrite.addValidCurrency(getFromCurrency());
        }

        String pairToAdd = getFromCurrency() + "_TO_" + getToCurrency();
        xmlReadWrite.addCurrencyPair(pairToAdd, getAmountToConvert());
        clearData();
    }  //todo

    void removeCurrencyPair() {
        if (xmlReadWrite.verifyCurrency(getFromCurrency())) {
            //TODO - REMOVE FROM XML FILE
            //validCurrencies.remove(getFromCurrency());
        }

        String stringToRemove = getFromCurrency() + "_TO_USD";

        //TODO - change to XML - edit
        //currConversions.remove(stringToRemove);

        clearData();
    }  //todo




//**************************GETTERS, SETTERS, HELPERS**************************
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
    private void clearData() {
        setFromCurrency("");
        setToCurrency("");
        setAmountToConvert(0);
    }
}
