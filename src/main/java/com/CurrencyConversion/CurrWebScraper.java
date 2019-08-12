package com.CurrencyConversion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class CurrWebScraper implements CurrencyInterface {
    private static String jsonString;
    private static List<String> removedCurrList;
    private static Map<String, Double> addedModifiedCurrencies;
    private static final Logger log = Logger.getLogger("WebScraper");

    CurrWebScraper() {
        removedCurrList = new ArrayList<>();
        addedModifiedCurrencies = new HashMap<>();
        setupLogger();
    }

    public String listCurrencies() {
        initializeJSON();
        JSONArray thisArray = new JSONArray(getRateString());
        JSONObject desiredObject = thisArray.getJSONObject(0);
        Iterator<String> keyList = desiredObject.keys();

        String result = "";
        while (keyList.hasNext()) {
            String abbrev = keyList.next();
            // not removed and not modified currencies
            if (!removedCurrList.contains(abbrev) && !addedModifiedCurrencies.containsKey(abbrev)) {
                result += abbrev + " ";
            }
        }

        for (Map.Entry<String, Double> entry : addedModifiedCurrencies.entrySet()) {
            result += entry.getKey() + " ";
        }
        return result;
    }

    public void saveCurrency(Currency currency) {
        addedModifiedCurrencies.put(currency.getAbbrev(), currency.getRate());
    }
    public Currency loadCurrency(String abbrev) {
        initializeJSON();
        JSONArray desiredArray = new JSONArray(getRateString());
        JSONObject desiredObject = desiredArray.getJSONObject(0);

        if (addedModifiedCurrencies.containsKey(abbrev)) {
            Double rate = addedModifiedCurrencies.get(abbrev);
            rate = 1/rate;
            return new Currency (abbrev, rate);
        }
        else if (!removedCurrList.contains(abbrev)) {
            try {
                Object actualRates = desiredObject.get(abbrev.toUpperCase().replaceAll("\\s", ""));
                String rateString = actualRates.toString();
                Double rate = Double.parseDouble(rateString);
                rate = 1/rate;
                return new Currency(abbrev, rate);
            } catch (Exception ex) {
                System.out.println("No curr found");
            }
        }
        return null;
    }
    public void removeCurrency(Currency currency) {
        removedCurrList.add(currency.getAbbrev());
    }

    public void update() {
        initializeJSON();


    }
    private static void initializeJSON() {
        try {
            URL url = new URL("https://api.exchangeratesapi.io/latest?base=USD");
            URLConnection conn = url.openConnection();
            log.info("connection established");
            InputStream iStream = conn.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
            jsonString = "["+bReader.readLine()+"]";  //get string to do JSON stuff to later
        }catch(IOException e){
            System.out.println("getValues() exception: (probably offline) \n" + e);
        }
    }
    private static String getRateString() {
        JSONArray jsonArray = new JSONArray(jsonString);
        JSONObject thisObject = jsonArray.getJSONObject(0);
        Object rates = thisObject.get("rates");
        return "["+rates.toString()+"]";
    }
    static void populateDB() {  //This is for the database implementation
        initializeJSON();
        JSONArray thisArray = new JSONArray(getRateString());
        JSONObject desiredObject = thisArray.getJSONObject(0);
        Iterator<String> keyList = desiredObject.keys();

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
    private void setupLogger() {
        log.setLevel(Level.ALL);
        FileHandler fh;

        try {
            fh = new FileHandler("webscraper.log");
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            log.info("logger set up");
        } catch (Exception e) {
            System.out.println("Logger error: " + e);
        }
    }
}
