package com.CurrencyConversion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class DatabaseConnection implements CurrencyInterface {
    //Connection Constants
    private static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Currencies";
    private static final String USER = "root";
    private static final String PASS = "";
    private static Statement stmt;
    private static Connection conn;

    DatabaseConnection() { }

    public String listCurrencies(){
        String result = "";
        try {
            setupConnection();
            String sqlQuery = "SELECT * FROM Currencies.currencies;";
            ResultSet resultSet = stmt.executeQuery(sqlQuery);

            while(resultSet.next()) {
                String abbrev = resultSet.getString("abbrev");
                result += abbrev + " ";
            }
            conn.close();
            return result;
        } catch (Exception e) {
            System.out.println("list currencies exception: " + e);
        }
        return "";
    }

    public void saveCurrency(Currency currency) {
        //call 'removeCurrency()' then proceed to add
        removeCurrency(currency);
        try {
            setupConnection();
            String sqlQuery = "INSERT INTO Currencies.currencies" +
                              "(ID, abbrev, rate) VALUES" +
                              "(null, '"+currency.getAbbrev()+"', '"
                              +currency.getRate()+"');";
            stmt.executeUpdate(sqlQuery);
            conn.close();
        } catch (Exception e) {
            System.out.println("save exception: " + e);
        }
    }

    public Currency loadCurrency(String abbrev) {
        try {
            setupConnection();
            abbrev = abbrev.toUpperCase().replaceAll("\\s", "");

            String sqlQuery = "SELECT * FROM Currencies.currencies " +
                              "WHERE abbrev = '"+abbrev+"';";
            ResultSet resultSet = stmt.executeQuery(sqlQuery);

            if (resultSet.next()) {
                Currency currency = new Currency();
                currency.setAbbrev(resultSet.getString("abbrev"));
                currency.setRate(resultSet.getDouble("rate"));
                return currency;
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("load exception: " + e);
        }
        return null;
    }

    public void removeCurrency(Currency currency) {
        try {
            setupConnection();
            String sqlQuery = "DELETE FROM Currencies.currencies " +
                              "WHERE abbrev = '"+currency.getAbbrev()+"';";
            stmt.executeUpdate(sqlQuery);
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
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
}
