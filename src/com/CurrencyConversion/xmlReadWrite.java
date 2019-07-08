package com.CurrencyConversion;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

class xmlReadWrite {
    xmlReadWrite() { }

    static void printValidCurrencies() {
        try {
            File fileName = new File("/home/patrick/Desktop/Desktop" +
                    "/CurrencyConversionBootcamp/src/com/CurrencyConversion/CurrencyRates.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fileName);
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("currency");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.print(eElement.getAttribute("id")+ " ");
                }
            }

        } catch (Exception e) {
            System.out.println("ACHTUNG! " + e);
        }
    }

    static boolean verifyCurrency (String currency) {
        try {
            File fileName = new File("/home/patrick/Desktop/Desktop" +
                    "/CurrencyConversionBootcamp/src/com/CurrencyConversion/CurrencyRates.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fileName);
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("currency");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (currency.equalsIgnoreCase(eElement.getAttribute("id"))) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ACHTUNG! " + e);
        }
        return false;
    }

    static double getConversionRate(String conversion) {
        try {
            File fileName = new File("/home/patrick/Desktop/Desktop" +
                    "/CurrencyConversionBootcamp/src/com/CurrencyConversion/CurrencyRates.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fileName);
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("currencyPair");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (conversion.equalsIgnoreCase(eElement.getAttribute("id"))) {
                        System.out.println("Double return value: " + Double.parseDouble(eElement.getNodeValue()));
                        return Double.parseDouble(eElement.getNodeValue());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ACHTUNG! " + e);
        } return -1;
    }

    static void printAllConversionRates() {
        try {
            File fileName = new File("/home/patrick/Desktop/Desktop" +
                    "/CurrencyConversionBootcamp/src/com/CurrencyConversion/CurrencyRates.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fileName);
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("currencyPair");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.print(eElement.getAttribute("rate") + " ");
                }
            }

        } catch (Exception e) {
            System.out.println("ACHTUNG! " + e);
        }
    }
}