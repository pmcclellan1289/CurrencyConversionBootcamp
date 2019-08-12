package com.CurrencyConversion;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class Currency {
    //  == FIELDS ==  //
    private String abbrev;
    private Double rate;

    //  == CONSTRUCTORS ==  //
    Currency() { }
    Currency(String abbrev, Double rate) {
        this.abbrev = abbrev.toUpperCase();
        this.rate = rate;
    }

    //  == METHODS ==  //
    Double convert (Double amt, Currency currencyTO){
        return amt * this.getRate() / currencyTO.getRate();
    }

    //   == GETTERS/SETTERS ==  //
    public void setAbbrev(String inputStr) {
        //uppercase and remove whitespace
        this.abbrev = inputStr.toUpperCase().replaceAll("\\s", "");
    }
    @XmlAttribute
    public String getAbbrev() {
        return this.abbrev;
    }
    @XmlElement
    public  Double getRate () {
        return rate;
    }
    public void setRate(Double newRate) {
        this.rate = newRate;
    }
}