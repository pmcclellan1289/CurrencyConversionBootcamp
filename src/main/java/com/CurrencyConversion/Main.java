package com.CurrencyConversion;

import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    //Changelog: Added this interface and its class implementation
    private static CurrencyInterface currInterface = new DatabaseConnection();

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

            switch (input.nextLine()) {
                case "1":
                    convertCurrency();
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
                case "5"://secret troubleshooting/testing menu
                    testFunction();
                    break;
                default:
                    System.out.println("\nInvalid selection, try again");
                    break;
            }
        }
    }

    //   ==  Functions  ==   //
    private static void convertCurrency() {
        System.out.println("\nConverting currencies\n\nValid Currencies: ");
        System.out.println(currInterface.listCurrencies());

        //  FROM  //
        System.out.print("\n\nPlease currency to be converted from: ");
        String inputStr = input.nextLine();
        if(verifyQuitNo(inputStr)){
            return;
        }

        Currency currencyFrom = currInterface.loadCurrency(inputStr);
        if (currencyFrom == null) {
            System.out.println("Invalid selection, returning to main menu");
            return;
        }

        //   TO    //
        System.out.print("Please enter currency to: ");
        inputStr = input.nextLine();
        if(verifyQuitNo(inputStr)){
            return;
        }

        Currency currencyTO = currInterface.loadCurrency(inputStr);
        if (currencyTO == null) {
            System.out.println("Invalid selection, returning to main menu");
            return;
        }

        // CONFIRM //
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

        //  OUTPUT  //
        Double convertedAmt = currencyFrom.convert(amt, currencyTO);
        System.out.println("Your converted amount is: "
                +formatOutputStr(convertedAmt)+" "+ currencyTO.getAbbrev());
    }

    private static void addEditCurrency() {
        System.out.println("\nAdding/Editing currency\n\nCurrencies on file: ");
        System.out.println(currInterface.listCurrencies());

        System.out.print("\n\nEnter a 3 letter currency abbreviation: ");
        String currAddEdit = input.nextLine();
        if(verifyQuitNo(currAddEdit)) {
            return;
        }
        if (currAddEdit.length() != 3) {
            System.out.println("Invalid currency abbreviation. ");
            return;
        }

        Currency currencyToAdd = new Currency();
        currencyToAdd.setAbbrev(currAddEdit);

        System.out.print("Adding/editing "+ currencyToAdd.getAbbrev()
                +" to USD, correct? y/n: ");
        if(verifyQuitNo(input.nextLine())) {
            return;
        }

        System.out.print("Please enter conversion rate to USD, no commas: ");
        Double newRate = input.nextDouble();
        currencyToAdd.setRate(newRate);
        input.nextLine();  //clear buffer

        currInterface.saveCurrency(currencyToAdd);
        System.out.println(currencyToAdd.getAbbrev()+" at "
                + currencyToAdd.getRate() +" to USD, has been added \n");
    }

    private static void removeCurrency() {
        System.out.println("Currencies on file: ");
        System.out.println(currInterface.listCurrencies());

        System.out.print("\n\nSelect currency to remove: ");
        Currency currencyToRemove = currInterface.loadCurrency(input.nextLine());

        if (currencyToRemove == null) {
            System.out.println("Invalid selection. ");
            return;
        }

        System.out.print("Removing "+currencyToRemove.getAbbrev()+", correct? y/n: ");
        if (verifyQuitNo(input.nextLine())) {
            return;
        }

        currInterface.removeCurrency(currencyToRemove);
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
    static String formatOutputStr(Double number) {
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

    private static void testFunction() {

    }
}