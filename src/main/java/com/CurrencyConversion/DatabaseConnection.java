package com.CurrencyConversion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
    //Connection Constants
    private static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Currencies";
    private static final String USER = "root";
    private static final String PASS = "";

    public DatabaseConnection() {
        ResultSet resultSet = getConnection();
        System.out.println("result set: "+resultSet);
    }

    public  ResultSet getConnection(){
        try {
            Class.forName(JDBC_Driver);
            System.out.println("first");
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("second");
            Statement stmt = conn.createStatement();
            System.out.println("third");
            String sql = "SELECT abbrev FROM Currencies.currencies;";
            System.out.println("fourth");
            ResultSet resultSet = stmt.executeQuery(sql);
            System.out.println("fifth");
            conn.close();
            return resultSet;
        } catch (Exception e) {
            System.out.println("DBException "+e);
        }
        return null;
    }
}
