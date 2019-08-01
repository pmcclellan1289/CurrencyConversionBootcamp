package com.CurrencyConversion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class DatabaseConnection implements CurrencyInterface{  //TODO implements some interface
    //Connection Constants
    private static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Currencies";
    private static final String USER = "root";
    private static final String PASS = "";
    private static Statement stmt;
    private static Connection conn;

    DatabaseConnection() {
        setupConnection();
    }
    private static void setupConnection() {
        try {
            Class.forName(JDBC_Driver);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (Exception ex) {
            System.out.println("");
        }
    }

    public String listCurrencies(){
        String result = "";
        try {
            String sqlQuery = "SELECT * FROM Currencies.currencies;";
            ResultSet resultSet = stmt.executeQuery(sqlQuery);

            while(resultSet.next()) {
                String abbrev = resultSet.getString("abbrev");
                result += abbrev + " ";
            }

            conn.close();
            return result;
        } catch (Exception e) {
            System.out.println("list currencies exception: "+e);
        }
        return "";
    }

    public void saveCurrency(Currency currency) {
        //if already in db, call 'removeCurrency()' then proceed to add
        try {
            String sqlQuery = "";

        } catch (Exception e) {
            System.out.println("save exception: " + e);
        }
    }

    @Override
    public Currency loadCurrency(String currencyName) {
        try {


        } catch (Exception e) {
            System.out.println("load exception: " + e);
        }
        return null;
    }

    @Override
    public void removeCurrency(Currency currency) {
        try {

        } catch (Exception e) {
            System.out.println("remove exception: " + e);
        }

    }
}
