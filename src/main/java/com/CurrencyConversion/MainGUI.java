package com.CurrencyConversion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static javax.swing.JOptionPane.showMessageDialog;

public class MainGUI extends JPanel implements ActionListener {
    private static CurrencyInterface currInterface = new DatabaseConnection();
    //    ==   class-wide variables   ==    //
    private static JFrame jFrame;
    private static JComboBox<String> currFromConv;
    private static JComboBox<String> currToConv;
    private static JTextField amtToConvert;
    private static JTextField convertedAmt;
    private static JTextField currNewRate;
    private static JTextField currToEdit;
    private static JComboBox<String> currToRemove;
    private static String[] currList;
    private static JLabel currEditList;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    private MainGUI() {
        super(new GridLayout(1, 1));
        JTabbedPane jTabbedPane = new JTabbedPane();

        Component convertTab = setUpConvertTab();
        Component addEditTab = setUpAddEditTab();
        Component removeTab  = setUpRemoveTab();

        jTabbedPane.add("Convert", convertTab);
        jTabbedPane.add("Add/Edit",addEditTab);
        jTabbedPane.add("Remove",  removeTab);

        add(jTabbedPane);
        jTabbedPane.setVisible(true);
    }
    private static void createAndShowGUI() {
        jFrame = new JFrame("Currency Converter");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(new MainGUI(), BorderLayout.CENTER);

        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setSize(400, 300);
    }

    private JPanel setUpConvertTab() {
        //  -Buttons-
        JButton convertButton = new JButton("Click to convert values");
        convertButton.addActionListener(this);
        convertButton.setActionCommand("convert");

        JButton updateButton = new JButton("Update DB from Web");
        updateButton.addActionListener(this);
        updateButton.setActionCommand("update");

        currList = new String[40];
        currList = (currInterface.listCurrencies().split(" "));

        //  -Input labels and dropdowns-
        JLabel currFromLabel= new JLabel("Currency Abbreviation From:");
        JLabel currToLabel  = new JLabel("Currency Abbreviation To:     ");
        JLabel amtToConvLbl = new JLabel("Amount to convert:                ");
        JLabel convertedLbl = new JLabel("Converted amount:                ");
        currFromConv = new JComboBox<>(currList);
        currToConv = new JComboBox<>(currList);
        convertedAmt = new JTextField(10);

        //this block restricts input to numbers only
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat df = (DecimalFormat) nf;
        df.setGroupingUsed(false);
        amtToConvert = new JFormattedTextField(df);
        amtToConvert.setColumns(10);

        JPanel convPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        convPanel.add(currFromLabel);
        convPanel.add(currFromConv);
        convPanel.add(currToLabel);
        convPanel.add(currToConv);
        convPanel.add(amtToConvLbl);
        convPanel.add(amtToConvert);
        convPanel.add(convertedLbl);
        convPanel.add(convertedAmt);
        convPanel.add(convertButton);
        //convPanel.add(updateButton);

        return convPanel;
    }
    private JPanel setUpAddEditTab() {
        //  -Buttons-
        JButton addEditButton = new JButton("Click to add/edit currency");
        addEditButton.addActionListener(this);
        addEditButton.setActionCommand("addEdit");
        //  -Input fields-
        JLabel currToEditLbl  = new JLabel("Currency to add/edit:     ");
        JLabel currNewRateLbl = new JLabel("Conversion rate to USD: ");
        currEditList = new JLabel();
        currEditList = new JLabel(String.format("<html><div style=\"width:%dpx;\">%s</div></html>",
                290, currInterface.listCurrencies()));
        currToEdit  = new JTextField(10);

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat df = (DecimalFormat) nf;
        df.setGroupingUsed(false);
        currNewRate = new JFormattedTextField(df);
        currNewRate.setColumns(10);

        JPanel addEditPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addEditPanel.add(currEditList);
        addEditPanel.add(currToEditLbl);
        addEditPanel.add(currToEdit);
        addEditPanel.add(currNewRateLbl);
        addEditPanel.add(currNewRate);
        addEditPanel.add(addEditButton);

        return addEditPanel;
    }
    private JPanel setUpRemoveTab() {
        JButton removeButton = new JButton("Click to remove currency");
        removeButton.addActionListener(this);
        removeButton.setActionCommand("remove");

        //  -Input fields-
        JLabel currToRemoveLbl  = new JLabel("Currency to remove: ");

        currToRemove  = new JComboBox<>(currList);
        JPanel removePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        removePanel.add(currToRemoveLbl);
        removePanel.add(currToRemove);
        removePanel.add(removeButton);

        return removePanel;
    }

    private void convertCurrency(){
        Currency currFrom = currInterface.loadCurrency(currFromConv.getSelectedItem().toString());
        Currency currTo   = currInterface.loadCurrency(currToConv.getSelectedItem().toString());

        if(currFrom == null || currTo == null) {
            System.out.println("ConvertCurrency() Null detected");
            System.out.println("currFrom : "+currFrom);
            System.out.println("currTo   : "+currTo);
            return;
        }

        double amtToConv = Double.parseDouble(amtToConvert.getText());
        Double result = currFrom.convert(amtToConv, currTo);
        convertedAmt.setText(Main.formatOutputStr(result));
        jFrame.revalidate();
    }
    private void addEditCurrency() {
        if (currToEdit.getText().length() != 3) {
            showMessageDialog(null, "Invalid Length");
            return;
        }
        Currency cToEdit = currInterface.loadCurrency(currToEdit.getText());

        if (cToEdit != null) {
            cToEdit.setRate(Double.parseDouble(currNewRate.getText()));
            currInterface.saveCurrency(cToEdit);
        }
        if (cToEdit == null) {
            cToEdit = new Currency(); //get out of null status
            cToEdit.setAbbrev(currToEdit.getText());
            cToEdit.setRate(Double.parseDouble(currNewRate.getText()));
            currInterface.saveCurrency(cToEdit);
        }
        showMessageDialog(null, cToEdit.getAbbrev()+" added");
    }
    private void removeCurrency() {
        Currency currTORemove = currInterface.loadCurrency(currToRemove.getSelectedItem().toString());
        if (currTORemove == null) {
            System.out.println("RemoveCurrency() null detected");
            showMessageDialog(null, "Currency not available to remove");
            return;
        }
        currInterface.removeCurrency(currTORemove);
        showMessageDialog(null, currTORemove.getAbbrev()+" removed");
    }

    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "convert":
                convertCurrency();
                break;
            case "addEdit":
                addEditCurrency();
                refreshScreen();
                break;
            case "remove":
                removeCurrency();
                refreshScreen();
                break;
            case "update":
                currInterface.update();
                refreshScreen();
                break;
                default:
                    System.out.println("oops!");
        }
    }
    private void refreshScreen() {
        // get updated info
        currList = (currInterface.listCurrencies().split(" "));
        currEditList.setText(String.format("<html><div style=\"width:%dpx;\">%s</div></html>",
                290, currInterface.listCurrencies()));

        // clear all dropdowns
        currFromConv.removeAllItems();
        currToConv.removeAllItems();
        currToRemove.removeAllItems();

        // repopulate dropdowns
        for (int i = 0; i < currList.length; i++) {
            currFromConv.addItem(currList[i]);
            currToConv.addItem(currList[i]);
            currToRemove.addItem(currList[i]);
        }
        jFrame.revalidate();
        jFrame.repaint();
    }
}