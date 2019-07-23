package com.CurrencyConversion;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

import static com.CurrencyConversion.xmlReadWrite.FILE_LOCATION;

public class CurrMarshaller {
    private static final File file = new File(FILE_LOCATION);

    static void marshallToXML(Currency currency) {
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(Currency.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(currency, file);
            jaxbMarshaller.marshal(currency, System.out);
        } catch (Exception e) {
            System.out.println("marshallToXML() error: "+e);
        }
    }

    static Currency unMarshalFromXML() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Currency.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Currency currency = (Currency) jaxbUnmarshaller.unmarshal(file);
            return currency;
        } catch(Exception e) {
            System.out.println("marshallToXML() error: "+e);
        }
        System.out.println("unsucessful unmarshal");
        return null;
    }
}
