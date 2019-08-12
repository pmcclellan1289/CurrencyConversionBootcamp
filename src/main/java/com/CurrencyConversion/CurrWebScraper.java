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
        initializeJSON();
        JSONArray desiredArray = new JSONArray(getRateString());
        JSONObject desiredObject = desiredArray.getJSONObject(0);
        Object actualRates = desiredObject.get(abbrev.toUpperCase().replaceAll("\\s", ""));

        Double rate = (Double) actualRates;
        return new Currency(abbrev,rate);
    }
    
    static void populateDB() {  //This is for the database implementation
        initializeJSON();
        JSONArray thisArray = new JSONArray(getRateString());
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



    public String listCurrencies() {
        initializeJSON();

        return null;
    }

    public void removeCurrency(Currency currency) {
        initializeJSON();

    }

    public void update() {
        initializeJSON();


    }

    private static void initializeJSON() {
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
    private static String getRateString() {
        JSONArray jsonArray = new JSONArray(jsonString);
        JSONObject thisObject = jsonArray.getJSONObject(0);
        Object rates = thisObject.get("rates");
        return "["+rates.toString()+"]";
    }
}
