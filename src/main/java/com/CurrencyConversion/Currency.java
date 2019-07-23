package com.CurrencyConversion;

public class Currency {
    //  == FIELDS ==  //
    private String abbrev;
    private Double rate;

    //  == CONSTRUCTORS ==  //
    public Currency() { }

    public Currency(String abbrev) {
        this.abbrev = abbrev;
    }

    //  == METHODS ==  //
    public Double convert (Double amt, Currency currencyTO){
        return amt * getRate() / currencyTO.getRate();
    }

    public void remove() {
        xmlReadWrite.removeCurrency(getAbbrev());
    }

    public void addToXML (){
        if (xmlReadWrite.verifyCurrency(this.abbrev)) {
            //currency previously present, remove old info then re-add
            xmlReadWrite.removeCurrency(this.abbrev);
            xmlReadWrite.addCurrency(this.abbrev, this.rate);
        } else {  //new currency, just add entry
            xmlReadWrite.addCurrency(this.abbrev, this.rate);
        }
    }

    //   == GETTERS/SETTERS ==  //
    public void setAbbrev(String inputStr) {
        //uppercase and remove whitespace
        this.abbrev = inputStr.toUpperCase().replaceAll("\\s", "");
    }
    public String getAbbrev() {
        return this.abbrev;
    }
    private Double getRate () {
        this.rate = xmlReadWrite.getConversionRate(this.abbrev);
        return rate;
    }
    public void setRate(Double newRate) {
        this.rate = newRate;
    }
}

