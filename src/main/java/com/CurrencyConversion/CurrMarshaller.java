package com.CurrencyConversion;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;

public class CurrMarshaller {

    static void marshallToXML(Currency currency) {
        try{
            File file = new File("xmlObjects/"+currency.getAbbrev()+".xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Currency.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(currency, file);
            //jaxbMarshaller.marshal(currency, System.out);
        } catch (Exception e) {
            System.out.println("marshallToXML() error: "+e);
        }
    }

//    static Currency unMarshalFromXML() {
//        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(Currency.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            Currency currency = (Currency) jaxbUnmarshaller.unmarshal(file);
//            return currency;
//        } catch(Exception e) {
//            System.out.println("marshallToXML() error: "+e);
//        }
//        System.out.println("unsucessful unmarshal");
//        return null;
//    }
}
