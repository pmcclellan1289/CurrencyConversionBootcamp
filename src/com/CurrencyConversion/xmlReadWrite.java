package com.CurrencyConversion;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;

class xmlReadWrite implements iReadWrite {
    private static final String fileLocation = "/home/patrick/Documents/Intellij Projects/" +
                                               "CurrencyConversionBootcamp/CurrencyRates.xml";
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

    static boolean verifyCurrency (String currency, String tagName) {
        Document document = initializeDocument();
        NodeList nList = document.getElementsByTagName(tagName);

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
        NodeList nodeList = document.getElementsByTagName("currencyPair");
        System.out.println("\nConversion Rates: ");  //spacer
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.hasAttributes()) {
                NamedNodeMap nodeMap = node.getAttributes();
                for (int j = 0; j < nodeMap.getLength(); j++) {
                    Node tempNode = nodeMap.item(j);
                    System.out.println(tempNode.getNodeValue() + " " + node.getTextContent());
                }
            }
        }
    }

    static void addValidCurrency (String currencyToAdd) {
        Document document = initializeDocument();
        Element root = document.getDocumentElement();

        //create and add child element
        Node newNode = createNode(document, currencyToAdd);
        root.appendChild(newNode);

        //WRITE
        writeToXML(document);
    }

    static void addCurrencyPair (String currencyPair, double rate) {
        Document document = initializeDocument();
        Element root = document.getDocumentElement();

        //create and add child element
        Node newNode = createNode(document, currencyPair, rate);
        root.appendChild(newNode);

        writeToXML(document);
    }

    static void removeCurrency (String currencyToDelete, String tagName) {
        try {
            Document document = initializeDocument();
            NodeList nodeList = document.getElementsByTagName(tagName);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && node.hasAttributes()) {
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int j = 0; j < nodeMap.getLength(); j++) {
                        Node tempNode = nodeMap.item(j);
                        if (currencyToDelete.equalsIgnoreCase(tempNode.getNodeValue())) {
                            Element tempElement = (Element)document.getElementsByTagName(tagName).item(i);
                            Node parent = tempElement.getParentNode();
                            parent.removeChild(tempElement);
                            parent.normalize();
                        }
                    }
                }
            }   writeToXML(document);
        } catch (Exception e) {
            System.out.println("xmlReadWrite.RemoveCurrency error: "+e);
        }
    }

    private static void writeToXML(Document doc) {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            tFactory.setAttribute("indent-number", 4);
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            Result result = new StreamResult(new FileOutputStream("CurrencyRates.xml"));

            transformer.transform(new DOMSource(doc), result);
        } catch (Exception e) {
            System.out.println("Write error: "+e);
        }
    }
    private static Node createNode(Document document, String strCurrencyPair, double rate) {

        Element rateToAdd = document.createElement("rate");
        rateToAdd.appendChild(document.createTextNode(Double.toString(rate)));

        Element newNode = document.createElement("currencyPair");
        Attr idAttribute = document.createAttribute("id");
        idAttribute.setValue(strCurrencyPair);

        newNode.setAttributeNode(idAttribute);
        newNode.appendChild(rateToAdd);

        return newNode;
    }
    private static Node createNode(Document document, String currencyToAdd) {

        Element newNode = document.createElement("currency");
        Attr idAttribute = document.createAttribute("id");

        idAttribute.setValue(currencyToAdd);
        newNode.setAttributeNode(idAttribute);

        return newNode;
    }
    private static Document initializeDocument() {
        try {
            File fileName = new File(fileLocation);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fileName);
            document.getDocumentElement();
            document.normalizeDocument();
            return document;
        } catch (Exception e){
            System.out.println("Initialize Document error: "+e);
        } return null;
    }
}