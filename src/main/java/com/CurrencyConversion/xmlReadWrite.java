/* Keeping this around for archival purposes */



//package com.CurrencyConversion;
//
//import org.w3c.dom.*;
//import org.xml.sax.InputSource;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Result;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.StringReader;
//import java.io.StringWriter;
//
//class xmlReadWrite { //Utility class to interact with xml
//    public  static final String FILE_LOCATION = "CurrencyRates.xml";
//    private static final String TAG_NAME = "currency";
//
//    static boolean verifyCurrency (String currency) {
//        Document document = initializeDocument();
//        NodeList nList = document.getElementsByTagName(TAG_NAME);
//
//        for (int i = 0; i < nList.getLength(); i++) {
//            Node nNode = nList.item(i);
//            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element eElement = (Element) nNode;
//                if (currency.equalsIgnoreCase(eElement.getAttribute("abbrev"))) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    static Double getConversionRate(String conversion) { //this works
//        Document document = initializeDocument();
//        NodeList nodeList = document.getElementsByTagName("currency");
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i);
//            if (node.getNodeType() == Node.ELEMENT_NODE && node.hasAttributes()) {
//                NamedNodeMap nodeMap = node.getAttributes();
//                for (int j = 0; j < nodeMap.getLength(); j++) {
//                    Node tempNode = nodeMap.item(j);
//                    if (conversion.equalsIgnoreCase(tempNode.getNodeValue())) {
//                        return Double.parseDouble(node.getTextContent());
//                    }
//                }
//            }
//        }
//        return -1.0;
//    }
//
//    static void printValidCurrencies() {
//        Document document = initializeDocument();
//        NodeList nList = document.getElementsByTagName("currency");
//
//        for (int i = 0; i < nList.getLength(); i++) {
//            Node node = nList.item(i);
//            if (node.getNodeType() == Node.ELEMENT_NODE && node.hasAttributes()) {
//                NamedNodeMap nodeMap = node.getAttributes();
//                for (int j = 0; j < nodeMap.getLength(); j++) {
//                    Node tempNode = nodeMap.item(j);
//                    System.out.print(tempNode.getNodeValue() + " ");
//                }
//            }
//        }
//    }
//
//    static void addCurrency(String abbr, double rate) {
//        Document document = initializeDocument();
//        Element root = document.getDocumentElement();
//
//        //create and add child element
//        Node newNode = createNode(document, abbr, rate);
//        root.appendChild(newNode);
//
//        writeToXML(document);
//    }
//
//    static void removeCurrency (String currencyToDelete) {
//        try {
//            Document document = initializeDocument();
//            NodeList nodeList = document.getElementsByTagName(TAG_NAME);
//
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE && node.hasAttributes()) {
//                    NamedNodeMap nodeMap = node.getAttributes();
//                    for (int j = 0; j < nodeMap.getLength(); j++) {
//                        Node tempNode = nodeMap.item(j);
//                        if (currencyToDelete.equalsIgnoreCase(tempNode.getNodeValue())) {
//                            Element tempElement = (Element)document.getElementsByTagName(TAG_NAME).item(i);
//                            Node parent = tempElement.getParentNode();
//                            parent.removeChild(tempElement);
//                        }
//                    }
//                }
//            }   writeToXML(document);
//        } catch (Exception e) {
//            System.out.println("xmlReadWrite.RemoveCurrency error: "+e);
//        }
//    }
//    private static void writeToXML(Document doc) {
//        try { //Format to remove whitespaces
//            String docString = toString(doc);
//            doc = toDocument(docString);
//
//            TransformerFactory tFactory = TransformerFactory.newInstance();
//            tFactory.setAttribute("indent-number", 4);
//
//            Transformer transformer = tFactory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//
//            Result result = new StreamResult(new FileOutputStream(FILE_LOCATION));
//
//            transformer.transform(new DOMSource(doc), result);
//        } catch (Exception e) {
//            System.out.println("Write error: "+e);
//        }
//    }
//    private static Node createNode(Document document, String strCurrencyPair, double rate) {
//        //used for currency pairs and rate
//        Element rateToAdd = document.createElement("rate");
//        rateToAdd.appendChild(document.createTextNode(Double.toString(rate)));
//
//        Element newNode = document.createElement("currency");
//        Attr idAttribute = document.createAttribute("abbrev");
//        idAttribute.setValue(strCurrencyPair);
//
//        newNode.setAttributeNode(idAttribute);
//        newNode.appendChild(rateToAdd);
//
//        return newNode;
//    }
//    private static Document initializeDocument() {
//        try {
//            File fileName = new File(FILE_LOCATION);
//
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            dbFactory.setIgnoringElementContentWhitespace(true);
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document document = dBuilder.parse(fileName);
//            document.getDocumentElement();
//            document.normalizeDocument();
//            return document;
//
//        } catch (Exception e){
//            System.out.println("Initialize Document error: "+e);
//        } return null;
//    }
//    private static String toString(Document doc) {
//        try {
//            StringWriter sw = new StringWriter();
//            TransformerFactory tf = TransformerFactory.newInstance();
//            Transformer transformer = tf.newTransformer();
//            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
//            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//
//            transformer.transform(new DOMSource(doc), new StreamResult(sw));
//            return sw.toString();
//        } catch (Exception e) {
//            throw new RuntimeException("Error converting to String", e);
//        }
//    }
//    private static Document toDocument(String docString) {
//        docString = docString.replaceAll("(?!>\\s+<//)(>\\s+<)", "> <");
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder;
//
//        try {
//            builder = factory.newDocumentBuilder();
//            Document doc = builder.parse(new InputSource(new StringReader(docString)));
//            return doc;
//
//        } catch (Exception e){
//            System.out.println("exception in toDocument() "+e);
//        }
//        return null;
//    }
//    static void printAllConversionRates() {
//        Document document = initializeDocument();
//        NodeList nodeList = document.getElementsByTagName("currency");
//
//        System.out.println("\n");
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i);
//            if (node.getNodeType() == Node.ELEMENT_NODE && node.hasAttributes()) {
//                NamedNodeMap nodeMap = node.getAttributes();
//                for (int j = 0; j < nodeMap.getLength(); j++) {
//                    Node tempNode = nodeMap.item(j);
//                    System.out.println(tempNode.getNodeValue() + " " + node.getTextContent());
//                    //currency abbr                 //double value
//                }
//            }
//        }
//    }
//}