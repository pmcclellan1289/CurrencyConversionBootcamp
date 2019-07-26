package com.CurrencyConversion;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class CurrMarshaller {

    static void marshallToXML(Currency currency) {
        try{
            File file = new File("xmlObjects/"+currency.getAbbrev()+".xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Currency.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(currency, file);
        } catch (Exception e) {
            System.out.print("");
        }
    }

    static Currency unMarshalFromXML(String currencyName) {
        try {
            currencyName = currencyName.toUpperCase().replaceAll("\\s", "");
            File file = new File("xmlObjects/"+currencyName+".xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Currency.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Currency currency = (Currency) jaxbUnmarshaller.unmarshal(file);
            return currency;
        } catch(Exception e) {
            System.out.print("");
        }
        return null; //null return addressed in Main
    }

    static String listOfCurrencies() {
        File folder = new File("xmlObjects");
        File[] listOfFiles = folder.listFiles();
        String result = "";

        for (int i = 0; i < listOfFiles.length; i++) {
            //get i'th item, separate on the '.'
            String[] tempArray = listOfFiles[i].toString().split("\\.");
            //get the part preceeding the '.', substring the currency name
            String toPrint = tempArray[0].substring(11);
            result += (toPrint + " ");
        }
        return result;
    }

    static void removeCurrency(Currency currencyToRemove) {
        File file = new File ("xmlObjects/"+currencyToRemove.getAbbrev()+".xml");
        file.delete();
    }
}
