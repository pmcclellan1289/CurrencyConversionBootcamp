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
    //class-wide variables
    private static CurrencyInterface currInterface = new DatabaseConnection();
    private static JFrame jFrame;
    private static JTabbedPane jTabbedPane;
    private static JTextField currFromConv;
    private static JTextField currToConv;
    private static JTextField amtToConvert;
    private static JTextField convertedAmt;
    private static JTextField currToEdit;
    private static JTextField currNewRate;
    private static JTextField currToRemove;
    private static JLabel convCurrList;
    private static JLabel addEditCurrList;
    private static JLabel removeCurrList;
    private static String labelText;
    private static final int labelWidth = 290;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private MainGUI() {
        super(new GridLayout(1, 1));
        jTabbedPane = new JTabbedPane();

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
        //  -Button-
        JButton convertButton = new JButton("Click to convert values");
        convertButton.addActionListener(this);
        convertButton.setActionCommand("convert");
        //  -label-
        convCurrList = new JLabel();
        labelText = ("Valid Currencies: "+currInterface.listCurrencies());
        convCurrList.setText(String.format("<html><div style=\"width:%dpx;\">%s</div></html>",labelWidth, labelText));
        //  -Input fields-
        JLabel currFromLabel= new JLabel("Currency Abbreviation From:");
        JLabel currToLabel  = new JLabel("Currency Abbreviation To:     ");
        JLabel amtToConvLbl = new JLabel("Amount to convert:                ");
        JLabel convertedLbl = new JLabel("Converted amount:                ");
        currFromConv = new JTextField(10);
        currToConv = new JTextField(10);
        convertedAmt = new JTextField(10);

        //this block restricts input to numbers only
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat df = (DecimalFormat) nf;
        df.setGroupingUsed(false);
        amtToConvert = new JFormattedTextField(df);
        amtToConvert.setColumns(10);

        JPanel convPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        convPanel.add(convCurrList);
        convPanel.add(currFromLabel);
        convPanel.add(currFromConv);
        convPanel.add(currToLabel);
        convPanel.add(currToConv);
        convPanel.add(amtToConvLbl);
        convPanel.add(amtToConvert);
        convPanel.add(convertedLbl);
        convPanel.add(convertedAmt);
        convPanel.add(convertButton);

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
        currToEdit  = new JTextField(10);

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat df = (DecimalFormat) nf;
        df.setGroupingUsed(false);
        currNewRate = new JFormattedTextField(df);
        currNewRate.setColumns(10);
        addEditCurrList = new JLabel();
        addEditCurrList.setText(String.format("<html><div style=\"width:%dpx;\">%s</div></html>",labelWidth,labelText));

        JPanel addEditPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addEditPanel.add(addEditCurrList);
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

        currToRemove  = new JTextField(10);
        removeCurrList = new JLabel();
        removeCurrList.setText(String.format("<html><div style=\"width:%dpx;\">%s</div></html>",290,labelText));

        JPanel removePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        removePanel.add(removeCurrList);
        removePanel.add(currToRemoveLbl);
        removePanel.add(currToRemove);
        removePanel.add(removeButton);

        return removePanel;
    }

    private void convertCurrency(){
        Currency currFrom = currInterface.loadCurrency(currFromConv.getText());
        Currency currTo   = currInterface.loadCurrency(currToConv.getText());

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

        jFrame.revalidate();
    }
    private void removeCurrency() {
        Currency currTORemove = currInterface.loadCurrency(currToRemove.getText());
        if (currTORemove == null) {
            System.out.println("RemoveCurrency() null detected");
            showMessageDialog(null, "Currency not available to remove");
            return;
        }
        currInterface.removeCurrency(currTORemove);
        showMessageDialog(null, currTORemove.getAbbrev()+" removed");
        jTabbedPane.revalidate();
        jTabbedPane.repaint();
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
                default:
                    System.out.println("oops!");
        }
    }

    private void refreshScreen() {
        labelText = ("Valid Currencies: "+currInterface.listCurrencies());

        convCurrList.setText(String.format("<html><div style=\"width:%dpx;\">%s</div></html>",290, labelText));
        addEditCurrList.setText(String.format("<html><div style=\"width:%dpx;\">%s</div></html>",290,labelText));
        removeCurrList.setText(String.format("<html><div style=\"width:%dpx;\">%s</div></html>",290,labelText));

        jFrame.revalidate();
        jFrame.repaint();
    }
}