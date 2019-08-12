package com.CurrencyConversion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

class CurrWebScraper implements CurrencyInterface { //TODO in progress
    private static String jsonString;

    public CurrWebScraper() { }

    public void saveCurrency(Currency currency) {

    }

    public Currency loadCurrency(String abbrev) {
        initializeJSONString();

        JSONArray jsonArray = new JSONArray(jsonString);
        JSONObject thisObject = jsonArray.getJSONObject(0);
        Object rates = thisObject.get("rates");
        String rateString = "["+rates.toString()+"]";

        //have to do this twice since it's a nested json
        JSONArray desiredArray = new JSONArray(rateString);
        JSONObject desiredObject = desiredArray.getJSONObject(0);
        Object actualRates = desiredObject.get(abbrev.toUpperCase().replaceAll("\\s", ""));

        Double rate = (Double) actualRates;
        Currency returnCurrency = new Currency();
        returnCurrency.setAbbrev(abbrev);
        returnCurrency.setRate(rate);
        return returnCurrency;
    }

    public String listCurrencies() {
        return null;
    }

    public void removeCurrency(Currency currency) {

    }

    public void update() { }
    
    static void populateDB() {  //This is for the database implementation
        initializeJSONString();

        JSONArray jsonArray = new JSONArray(jsonString);
        JSONObject thisObject = jsonArray.getJSONObject(0);
        Object rates = thisObject.get("rates");
        String rateString = "["+rates.toString()+"]";

        //have to do this twice, the JSON is nested
        JSONArray thisArray = new JSONArray(rateString);
        JSONObject desiredObject = thisArray.getJSONObject(0);
        Iterator keyList = desiredObject.keys();
        CurrencyInterface dbConn = new DatabaseConnection();

        while (keyList.hasNext()) {
            String abbrev = keyList.next().toString();
            Object actualRates = desiredObject.get(abbrev);
            String actualRateStr = actualRates.toString();
            double rate = Double.parseDouble(actualRateStr);
            rate = 1/rate;
            // ^ actualRates produces an Integer object, can't cast directly to a Double
            //   Also inverting value since values are given as USD/x where my calculations are x/USD.
            dbConn.saveCurrency(new Currency(abbrev, rate));
        }
    }

    private static void initializeJSONString() {
        try {
            URL url = new URL("https://api.exchangeratesapi.io/latest?base=USD");
            URLConnection conn = url.openConnection();
            InputStream iStream = conn.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
            jsonString = "["+bReader.readLine()+"]";  //get string to do JSON stuff to later
        }catch(IOException e){
            System.out.println("getValues() exception: (probably offline) " + e);
        }
    }
}
