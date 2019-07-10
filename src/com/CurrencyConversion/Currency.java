package com.CurrencyConversion;

class Currency {
    private String fromCurrency;
    private String toCurrency;
    private Double amountToConvert;

    Currency() { }

    double convert() {
        String convFrom = fromCurrency + "_TO_USD";
        String convTo   = toCurrency   + "_TO_USD";
        return getAmountToConvert() * xmlReadWrite.getConversionRate(convFrom)
                                    / xmlReadWrite.getConversionRate(convTo);
    }

    void addEdit() {
        if (!xmlReadWrite.verifyCurrency(getFromCurrency(), "currency")) {
             xmlReadWrite.addValidCurrency(getFromCurrency());
        }

        String pairToAdd = getFromCurrency() + "_TO_USD";
        if (!xmlReadWrite.verifyCurrency(pairToAdd, "currencyPair")) {
            xmlReadWrite.addCurrencyPair(pairToAdd, getAmountToConvert());
        }
        else { //currency pair present, delete then re-add w/ new info
            xmlReadWrite.removeCurrency(pairToAdd, "currencyPair");
            xmlReadWrite.addCurrencyPair(pairToAdd, getAmountToConvert());
        }

        clearData();
    }

    void remove() {
        xmlReadWrite.removeCurrency(getFromCurrency(),"currency");

        String stringToRemove = getFromCurrency() + "_TO_USD";
        if (xmlReadWrite.verifyCurrency(stringToRemove, "currencyPair")) {
            xmlReadWrite.removeCurrency(stringToRemove, "currencyPair");
        }
        clearData();
    }

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
