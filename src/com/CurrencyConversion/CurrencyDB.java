package com.CurrencyConversion;

class CurrencyDB extends Currency{
    //same thing as "Currency" class, but points to dbReadWrite to pass info back to Main

    @Override
    double convert() {
        String convFrom = getFromCurrency() + "_TO_USD";
        String convTo   = getToCurrency()   + "_TO_USD";
        return getAmountToConvert() * dbReadWrite.getConversionRate(convFrom)
                                    / dbReadWrite.getConversionRate(convTo);
    }

    @Override
    void addEdit() {
        if (!dbReadWrite.verifyCurrency(getFromCurrency(), "currency")) {
            dbReadWrite.addValidCurrency(getFromCurrency());
        }

        String pairToAdd = getFromCurrency() + "_TO_USD";
        if (!dbReadWrite.verifyCurrency(pairToAdd, "currencyPair")) {
            dbReadWrite.addCurrencyPair(pairToAdd, getAmountToConvert());
        }
        else { //currency pair present, delete then re-add w/ new info
            dbReadWrite.removeCurrency(pairToAdd, "currencyPair");
            dbReadWrite.addCurrencyPair(pairToAdd, getAmountToConvert());
        }
        clearData();
    }

    @Override
    void remove() {
        dbReadWrite.removeCurrency(getFromCurrency(),"currency");

        String stringToRemove = getFromCurrency() + "_TO_USD";
        if (xmlReadWrite.verifyCurrency(stringToRemove, "currencyPair")) {
            xmlReadWrite.removeCurrency(stringToRemove, "currencyPair");
        }
        clearData();

    }
}
