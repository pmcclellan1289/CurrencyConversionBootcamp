package com.CurrencyConversion;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

class xmlReadWrite {
    private static final String fileLocation = "/home/patrick/Desktop/Desktop" +
            "/CurrencyConversionBootcamp/src/com/CurrencyConversion/CurrencyRates.xml";

    xmlReadWrite() { }
    static void printValidCurrencies() {
        Document document = initializeDocument();
        NodeList nList = document.getElementsByTagName("currency");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.print(eElement.getAttribute("id")+ " ");
            }
        }
    }

    static boolean verifyCurrency (String currency) {
        Document document = initializeDocument();
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
        return false;
    }

    static double getConversionRate(String conversion) { //this works
        Document document = initializeDocument();
        NodeList nodeList = document.getElementsByTagName("currencyPair");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.hasAttributes()) {
                NamedNodeMap nodeMap = node.getAttributes();
                for (int j = 0; j < nodeMap.getLength(); j++) {
                    Node tempNode = nodeMap.item(j);
                    if (conversion.equalsIgnoreCase(tempNode.getNodeValue())) {
                        return Double.parseDouble(node.getTextContent());
                    }
                }
            }
        }
        return -1;
    }

    static void printAllConversionRates() {
        Document document = initializeDocument();
        NodeList nList = document.getElementsByTagName("currencyPair");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.print(eElement.getAttribute("rate") + " ");
            }
        }
    }//working on this, needed?



    private static Document initializeDocument() {
        try {
            File fileName = new File(fileLocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fileName);
            document.getDocumentElement().normalize();
            return document;
        } catch (Exception e){
            System.out.println("Initialize Document error: "+e);
        } return null;
    }
}