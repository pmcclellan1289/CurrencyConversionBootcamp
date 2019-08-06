package com.CurrencyConversion;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

class CurrWebScraper {
    private static String jsonString;

    static JSONArray populateDB() {
        try {
            URL url = new URL("https://api.exchangeratesapi.io/latest?base=USD");
            URLConnection conn = url.openConnection();
            InputStream iStream = conn.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
            jsonString = "["+bReader.readLine()+"]";  //get string to do JSON stuff to later
        } catch(IOException e) {
            System.out.println("getValues() exception: (probably offline) " + e);
        }

        JSONArray jsonArray = new JSONArray(jsonString);
        JSONObject thisObject = jsonArray.getJSONObject(0);
        Object rates = thisObject.get("rates");
        String rateString = "["+rates.toString()+"]";

        return new JSONArray(rateString);
    }

//    public Currency loadCurrency(String abbrev) {
//        try {
//            URL url = new URL("https://api.exchangeratesapi.io/latest?base=USD");
//            URLConnection conn = url.openConnection();
//            InputStream iStream = conn.getInputStream();
//            BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
//            jsonString = "["+bReader.readLine()+"]";  //get string to do JSON stuff to later
//        }catch(IOException e){
//            System.out.println("getValues() exception: (probably offline) " + e);
//        }
//
//        JSONArray jsonArray = new JSONArray(jsonString);
//        JSONObject thisObject = jsonArray.getJSONObject(0);
//        Object rates = thisObject.get("rates");
//        String rateString = "["+rates.toString()+"]";
//
//        //have to do this twice since it's a nested json
//        JSONArray desiredArray = new JSONArray(rateString);
//        JSONObject desiredObject = desiredArray.getJSONObject(0);
//        Object actualRates = desiredObject.get(abbrev.toUpperCase().replaceAll("\\s", ""));
//
//        Double rate = (Double) actualRates;
//        Currency returnCurrency = new Currency();
//        returnCurrency.setAbbrev(abbrev);
//        returnCurrency.setRate(rate);
//        return returnCurrency;
//    }
}
