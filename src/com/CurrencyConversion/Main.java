package com.CurrencyConversion;
import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);
    private static Currency currency = new Currency();  //new Currency() or CurrencyDB();

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
                addEditCurrency();
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
        System.out.println("\nConverting currencies\n\nValid Currencies: ");
        xmlReadWrite.printValidCurrencies();

        System.out.print("\n\nPlease currency to be converted from: ");
        currency.setFromCurrency(input.nextLine());
        //verify inputs
        if (currency.getFromCurrency().equalsIgnoreCase("quit")) {
            mainMenu(); return;
        }
        if (!xmlReadWrite.verifyCurrency(currency.getFromCurrency(), "currency")){
            System.out.println("Invalid Currency. Please try again.");
            convertPair(); return;
        }

        System.out.print("Please enter currency to: ");
        currency.setToCurrency(input.nextLine());
        //verify inputs
        if (currency.getToCurrency().equalsIgnoreCase("quit")) {
            mainMenu(); return;
        }
        if (!xmlReadWrite.verifyCurrency(currency.getToCurrency(), "currency")){
            System.out.println("Invalid Currency. Please try again.");
            convertPair(); return;
        }

        //confirm selection
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

        Double convertedAmt = currency.convert();
        System.out.println("Your converted amount is: "
                +formatOutputStr(convertedAmt)+" "+currency.getToCurrency());
        mainMenu();
    }

    private static void addEditCurrency() {
        System.out.println("\nAdding/Editing currency ");
        System.out.println("\nCurrencies on file: ");
        xmlReadWrite.printValidCurrencies();
        System.out.print("\n\nEnter 3 letter currency abbreviation: ");
        currency.setFromCurrency(input.nextLine());

        if (currency.getFromCurrency().equalsIgnoreCase("quit")) {
            mainMenu(); return;
        }
        if (currency.getFromCurrency().length() != 3) {
            System.out.println("Invalid currency abbreviation. ");
            addEditCurrency(); return;
        }

        System.out.print("Adding/editing "+currency.getFromCurrency()
                        +" to USD, correct? y/n: ");
        if (input.nextLine().equalsIgnoreCase("n")) {
            addEditCurrency();
            return;
        }

        System.out.print("Please enter conversion rate to USD, no commas: ");
        currency.setAmountToConvert(input.nextDouble());
        input.nextLine();  //clear buffer

        currency.addEdit();
        mainMenu();
    }

    private static void removeCurrencies() {
        System.out.println("Currencies on file: ");
        xmlReadWrite.printValidCurrencies();

        System.out.print("\n\nSelect currency to remove: ");
        currency.setFromCurrency(input.nextLine());
        if (currency.getFromCurrency().equalsIgnoreCase("quit")) {
            mainMenu(); return;
        }
        if (!xmlReadWrite.verifyCurrency(currency.getFromCurrency(), "currency")) {
            System.out.println("Invalid selection, try again! ");
            removeCurrencies(); return;
        }

        System.out.print("Removing "+currency.getFromCurrency()+", correct? y/n: ");
        if (currency.getFromCurrency().equalsIgnoreCase("quit")) {
            mainMenu(); return;
        }
        if (input.nextLine().equalsIgnoreCase("N")) {
            removeCurrencies();
            return;
        }

        currency.remove();
        System.out.println("Currency removed");
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