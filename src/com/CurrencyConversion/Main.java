package com.CurrencyConversion;
import java.util.Scanner;


public class Main {
    private static Scanner input = new Scanner(System.in);
    private static Currency currency = new Currency();

    public static void main(String[] args) {
        System.out.println("\n\n*********CURRENCY CONVERTER*********");
        mainMenu();
    }

    private static void mainMenu() {
        System.out.println("\nMain Menu: ");
        System.out.println("1 - Convert a currency pair");
        System.out.println("2 - Add/Modify a currency conversion");
        System.out.println("3 - Remove a currency conversion");
        System.out.println("4 - Exit");
        String selection = input.nextLine();

        switch (selection) {
            case "1":
                convertPair();
                break;
            case "2":
                addCurrencies();
                break;
            case "3":
                removeCurrencies();
                break;
            case "4":
                System.out.println("\nGoodbye!");
                System.exit(0);
            case "5":  //secret troubleshooting menu
                xmlReadWrite.printValidCurrencies();
                xmlReadWrite.printAllConversionRates();
                mainMenu();
                break;
            default:
                System.out.println("\nInvalid selection, try again");
                mainMenu();
                break;
        }
    }

    private static void convertPair() {
        System.out.print("\nValid Currencies: ");
        xmlReadWrite.printValidCurrencies();
        System.out.print("\nPlease currency to be converted from: ");
        currency.setFromCurrency(input.nextLine());
        if (!xmlReadWrite.verifyCurrency(currency.getFromCurrency())){
            System.out.println("Invalid Currency. Please try again.");
            convertPair();
            return;
        }

        System.out.print("Please enter currency to: ");
        currency.setToCurrency(input.nextLine());
        if (!xmlReadWrite.verifyCurrency(currency.getFromCurrency())){
            System.out.println("Invalid Currency. Please try again.");
            convertPair();
            return;
        }

        System.out.print("You are converting from " + currency.getFromCurrency()
                            + " to " + currency.getToCurrency()+", correct? y/n: ");
        if (input.nextLine().equalsIgnoreCase("n")){
            convertPair();
            return;
        }

        System.out.print("Please enter the amount to convert, no commas: ");
        currency.setAmountToConvert(input.nextDouble()); input.nextLine(); //clear buffer
        if (currency.getAmountToConvert() < 0) {
            System.out.println("Invalid amount. Please try again.");
            convertPair();
            return;
        }

        Double convertedAmt = currency.convertCurrency();
        System.out.println("Your converted amount is: "
                +formatOutputStr(convertedAmt)+" "+currency.getToCurrency());
        mainMenu();
    }

    private static void addCurrencies() {
        System.out.println("\nEntering currency conversion to USD");
        System.out.print("Enter 3 letter currency abbreviation: ");
        currency.setFromCurrency(input.nextLine());

        currency.setToCurrency("USD");

        System.out.print("Adding/editing "+currency.getFromCurrency()+" to "
                +currency.getToCurrency()+", correct? y/n: ");

        if (input.nextLine().equalsIgnoreCase("N")) {
            addCurrencies();
            return;
        }

        System.out.print("Please enter conversion rate to USD, no commas: ");
        currency.setAmountToConvert(input.nextDouble());
        input.nextLine();  //clear buffer

        //TODO - Implement in XML
        //currency.addCurrencyPair();
        mainMenu();
    }

    private static void removeCurrencies() {
        System.out.println("\nRemoving a currency conversion");
        System.out.print("Enter currency to remove: ");
        currency.setFromCurrency(input.nextLine());

        System.out.print("Removing "+currency.getFromCurrency()+", correct? y/n: ");
        if (input.nextLine().equalsIgnoreCase("N")) {
            removeCurrencies();
            return;
        }
        //TODO Implement in XML
        //currency.removeCurrencyPair();
        System.out.println("Currency pair removed");
        mainMenu();
    }

    private static String formatOutputStr(Double number) {
        String output = number.toString();
        String[] splitString = output.split("\\.");

        String front = splitString[0];
        String back = "";

        if (splitString[1].length() > 1) {
            back = splitString[1].substring(0, 2);
        }
        else if (splitString[1].length() == 1) {
            back = splitString[1]+"0";
        }

        if (front.length() > 6) {
            String front1 =  front.substring(0, front.length()-6);
            String front2 =  front.substring(front.length()-6, front.length()-3);
            String front3 =  front.substring(front.length()-3);
            return front1+","+front2+","+front3+"."+back;
        }

        else if (front.length() > 3 && front.length() <= 6) {
            String front1 = front.substring(0,front.length()-3);
            String front2 = front.substring(front.length()-3);
            return front1+","+front2+"."+back;
        }
        return front+"."+back;
    }
}
