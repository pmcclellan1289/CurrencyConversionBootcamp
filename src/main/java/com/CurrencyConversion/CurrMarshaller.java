package com.CurrencyConversion;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

class CurrMarshaller implements CurrencyInterface {  //TODO implements some interface

    public void saveCurrency(Currency currency) {
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

    public Currency loadCurrency(String currencyName) {
        try {
            currencyName = currencyName.toUpperCase().replaceAll("\\s", "");
            File file = new File("xmlObjects/"+currencyName+".xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Currency.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Currency) jaxbUnmarshaller.unmarshal(file);
        } catch(Exception e) {
            System.out.print("");
        }
        return null; //null return addressed in Main
    }

    public String listCurrencies() {
        File folder = new File("xmlObjects");
        File[] listOfFiles = folder.listFiles();
        String result = "";

        for (File listOfFile : listOfFiles) {
            //get i'th item, separate on the '.'
            String[] tempArray = listOfFile.toString().split("\\.");
            //get the part preceding the '.', substring the currency name
            String toPrint = tempArray[0].substring(11);
            result += (toPrint + " ");
        }
        return result;
    }

    public void removeCurrency(Currency currencyToRemove) {
        File file = new File("xmlObjects/" + currencyToRemove.getAbbrev() + ".xml");
        file.delete();
    }
}
