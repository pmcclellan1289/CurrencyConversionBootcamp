package com.CurrencyConversion;

import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n\n*********CURRENCY CONVERTER*********");
        mainMenu();
    }

    //   ==  Primary Loop  ==   //
    private static void mainMenu() {
        while (true){
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
                    addEditCurrency();
                    break;
                case "3":
                    removeCurrency();
                    break;
                case "4":
                    System.out.println("\nGoodbye!\n");
                    System.exit(0);
                case "5":  //secret troubleshooting menu
                    xmlReadWrite.printValidCurrencies();
                    xmlReadWrite.printAllConversionRates();
                    break;
                default:
                    System.out.println("\nInvalid selection, try again");
                    break;
            }
        }
    }

    private static void convertPair() {
        System.out.println("\nConverting currencies\n\nValid Currencies: ");
        xmlReadWrite.printValidCurrencies();

        System.out.print("\n\nPlease currency to be converted from: ");
        String inputStr = input.nextLine();

        Currency currencyFrom = new Currency();
        currencyFrom.setAbbrev(inputStr);
        if(verifyQuitNo(inputStr)){
            return;
        }
        if(!isValidCurrency(currencyFrom)) {
            System.out.println("Invalid input, please try again\n");
            return;
        }

        System.out.print("Please enter currency to: ");
        inputStr = input.nextLine();

        Currency currencyTO = new Currency();
        currencyTO.setAbbrev(inputStr);

        if(verifyQuitNo(inputStr)){
            return;
        }
        if(!isValidCurrency(currencyTO)) {
            System.out.println("Invalid input, please try again\n");
            return;
        }

        System.out.print("You are converting from " + currencyFrom.getAbbrev()
                + " to " + currencyTO.getAbbrev()+", correct? y/n: ");
        if(verifyQuitNo(input.nextLine())){
            return;
        }

        System.out.print("Please enter the amount to convert: ");
        Double amt = input.nextDouble(); input.nextLine(); //clear buffer
        if (amt < 0) {
            System.out.println("Invalid amount. Please try again.");
            return;
        }

        Double convertedAmt = currencyFrom.convert(amt, currencyTO);
        System.out.println("Your converted amount is: "
                +formatOutputStr(convertedAmt)+" "+ currencyTO.getAbbrev());
    }

    private static void addEditCurrency() {
        System.out.println("\nAdding/Editing currency ");
        System.out.println("\nCurrencies on file: ");
        xmlReadWrite.printValidCurrencies();

        System.out.print("\n\nEnter a 3 letter currency abbreviation: ");
        Currency currencyToAdd = new Currency();
        currencyToAdd.setAbbrev(input.nextLine());  //formatting in setter

        if(verifyQuitNo(currencyToAdd.getAbbrev())) {
            return;
        }
        if (currencyToAdd.getAbbrev().length() != 3) {
            System.out.println("Invalid currency abbreviation. ");
            return;
        }

        System.out.print("Adding/editing "+ currencyToAdd.getAbbrev()
                +" to USD, correct? y/n: ");
        if(verifyQuitNo(input.nextLine())) {
            return;
        }

        System.out.print("Please enter conversion rate to USD, no commas: ");
        Double newRate = input.nextDouble();
        currencyToAdd.setRate(newRate);
        input.nextLine();  //clear buffer

        currencyToAdd.addToXML();
        System.out.println(currencyToAdd.getAbbrev()+" at "
                + newRate +" to USD, has been added \n");
    }

    private static void removeCurrency() {
        System.out.println("Currencies on file: ");
        xmlReadWrite.printValidCurrencies();

        System.out.print("\n\nSelect currency to remove: ");
        Currency currencyToRemove = new Currency();
        currencyToRemove.setAbbrev(input.nextLine());

        if (!isValidCurrency(currencyToRemove)
                ||currencyToRemove.getAbbrev().equalsIgnoreCase("USD")){
            System.out.println("Invalid selection. ");
            return;
        }

        System.out.print("Removing "+currencyToRemove.getAbbrev()+", correct? y/n: ");
        if (verifyQuitNo(input.nextLine())) {
            return;
        }

        currencyToRemove.remove();
        System.out.println("Entry removed \n");
    }

    //  ===  HELPER METHODS  ===  //
    private static boolean verifyQuitNo(String inputStr) {
        if ((inputStr.equalsIgnoreCase("quit"))
                ||(inputStr.equalsIgnoreCase("n"))){
            System.out.println("Returning to main menu");
            return true;
        }   return false;
    }
    private static boolean isValidCurrency(Currency currency) {
        return xmlReadWrite.verifyCurrency(currency.getAbbrev());
    }
    private static String formatOutputStr(Double number) {
        String output = number.toString();
        String[] splitString = output.split("\\.");
        // ^ split on decimal
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