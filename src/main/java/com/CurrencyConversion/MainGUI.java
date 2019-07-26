package com.CurrencyConversion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainGUI extends JPanel implements ActionListener {
    //class-wide variables
    //I may have to put everything up here....
    private static JTabbedPane jTabbedPane;
    private static JTextField currFromConv;
    private static JTextField currToConv;
    private static JTextField amtToConvert;
    private static JTextField convertedAmt;
    private static JTextField currToEdit;
    private static JTextField currNewRate;
    private static JTextField currToRemove;

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
        jTabbedPane.add("Convert",  setUpConvertTab());
        jTabbedPane.add("Add/Edit", setUpAddEditTab());
        jTabbedPane.add("Remove",   setUpRemoveTab());

        add(jTabbedPane);
        jTabbedPane.setSize(800, 500);
        jTabbedPane.setVisible(true);
    }

    private static void createAndShowGUI() {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(new MainGUI(), BorderLayout.CENTER);

        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        //cheated a bit here with resizable
        jFrame.setSize(400, 600);
    }

    private JPanel setUpConvertTab() {
        //  -Button-
        JButton convertButton = new JButton("Click to convert values");
        convertButton.addActionListener(this);
        convertButton.setActionCommand("convert");
        //  -Input fields-
        JLabel listCurrencies=new JLabel("Valid Currencies: "+CurrMarshaller.listOfCurrencies());
        JLabel currFromLabel= new JLabel("Currency Abbreviation From:");
        JLabel currToLabel  = new JLabel("Currency Abbreviation To:     ");
        JLabel amtToConvLbl = new JLabel("Amount to convert:                ");
        JLabel convertedLbl = new JLabel("Converted amount:                ");

        currFromConv = new JTextField(10);
        currToConv = new JTextField(10);
        convertedAmt = new JTextField(10);

        //this chunk restricts input to numbers only
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat df = (DecimalFormat) nf;
        df.setGroupingUsed(false);
        amtToConvert = new JFormattedTextField(df);
        amtToConvert.setColumns(10);

        JPanel convPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        convPanel.add(listCurrencies);
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
    private void convertCurrency(){
        Currency currFrom = CurrMarshaller.unMarshalFromXML(currFromConv.getText());
        Currency currTo   = CurrMarshaller.unMarshalFromXML(currToConv.getText());
        double amtToConv = Double.parseDouble(amtToConvert.getText());

        Double result = currFrom.convert(amtToConv, currTo);
        convertedAmt.setText(Main.formatOutputStr(result));
    }

    private JPanel setUpAddEditTab() {
        //  -Buttons-
        JButton addEditButton = new JButton("Click to edit currency");
        addEditButton.addActionListener(this);
        addEditButton.setActionCommand("addEdit");
        //  -Input fields-
        JLabel currToEditLbl  = new JLabel("Currency to edit:             ");
        JLabel currNewRateLbl = new JLabel("Conversion rate to USD: ");

        currToEdit  = new JTextField(10);
        currNewRate = new JTextField(10);

        JPanel addEditPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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

        JPanel addEditPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addEditPanel.add(currToRemoveLbl);
        addEditPanel.add(currToRemove);
        addEditPanel.add(removeButton);

        return addEditPanel;
    }

    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "convert":
                System.out.println("Convert() triggered");
                convertCurrency();
                break;
            case "addEdit":
                System.out.println("AddEdit() triggered");
                break;
            case "remove":
                System.out.println("Remove() triggered");
                break;
                default:
                    System.out.println("oops!");
        }
    }
}
