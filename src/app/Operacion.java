package app;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.awt.*;

public class Operacion extends JFrame implements ActionListener, ItemListener {
    private JTextField txf1;
    private double n1;
    private JTextField txf2;
    private double n2;
    private JLabel lblSimbolo;
    private JLabel lblRdo;
    private double rdo;
    private JRadioButton rdbSuma;
    private JRadioButton rdbResta;
    private JRadioButton rdbMultiplicacion;
    private JRadioButton rdbDivision;
    private ButtonGroup grpOperaciones;
    private JButton btnOperacion;
    private JLabel lblError;
    private JComboBox<Integer> cmbDecimal;
    private JLabel lblDec;
    private String home = System.getProperty("user.home");
    private File archivo = new File(home+"/.operacion.txt");
    private boolean operacionCorrecta = true;
    private boolean conversionCorrecta = true;
    private SimboloOperacion op = new SimboloOperacion();


    public Operacion() {
        super("Calculator 3000");
        setLayout(null);

        txf1 = new JTextField(9);
        txf1.setSize(txf1.getPreferredSize());
        txf1.setLocation(20, 20);
        add(txf1);

        txf2 = new JTextField(9);
        txf2.setSize(txf2.getPreferredSize());
        txf2.setLocation(180, 20);
        add(txf2);

        lblSimbolo = new JLabel("+");
        lblSimbolo.setSize(lblSimbolo.getPreferredSize());
        lblSimbolo.setLocation(150, 20);
        add(lblSimbolo);

        lblRdo = new JLabel("=");
        lblRdo.setSize(lblRdo.getPreferredSize());
        lblRdo.setLocation(300, 20);
        add(lblRdo);

        rdbSuma = new JRadioButton("Suma");
        rdbSuma.setSize(rdbSuma.getPreferredSize());
        rdbSuma.setLocation(20, 50);
        rdbSuma.addItemListener(this);
        add(rdbSuma);

        rdbResta = new JRadioButton("Resta");
        rdbResta.setSize(rdbResta.getPreferredSize());
        rdbResta.setLocation(90, 50);
        rdbResta.addItemListener(this);
        add(rdbResta);

        rdbMultiplicacion = new JRadioButton("Multiplicación");
        rdbMultiplicacion.setSize(rdbMultiplicacion.getPreferredSize());
        rdbMultiplicacion.setLocation(170, 50);
        rdbMultiplicacion.addItemListener(this);
        add(rdbMultiplicacion);

        rdbDivision = new JRadioButton("División");
        rdbDivision.setSize(rdbDivision.getPreferredSize());
        rdbDivision.setLocation(300, 50);
        rdbDivision.addItemListener(this);
        add(rdbDivision);

        grpOperaciones = new ButtonGroup();
        grpOperaciones.add(rdbSuma);
        grpOperaciones.add(rdbResta);
        grpOperaciones.add(rdbMultiplicacion);
        grpOperaciones.add(rdbDivision);

        rdbSuma.setSelected(true);

        btnOperacion = new JButton("Operación");
        btnOperacion.setSize(btnOperacion.getPreferredSize());
        btnOperacion.setLocation(400, 50);
        btnOperacion.addActionListener(this);
        add(btnOperacion);

        lblError = new JLabel();
        lblError.setSize(lblError.getPreferredSize());
        lblError.setLocation(550, 55);
        lblError.setForeground(Color.red);
        add(lblError);

        cmbDecimal = new JComboBox<Integer>();
        for (int i = 0; i <= 5; i++) {
            cmbDecimal.addItem(i);
        }
        cmbDecimal.setSize(cmbDecimal.getPreferredSize());
        cmbDecimal.setLocation(200, 100);
        cmbDecimal.setSelectedIndex(2);
        add(cmbDecimal);
        
        lblDec = new JLabel("Número de decimais");
        lblDec.setSize(lblDec.getPreferredSize());
        lblDec.setLocation(50, 105);
        add(lblDec);

        this.addWindowListener(new CierreVentana());
        valoresIniciales();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        lblError.setText("");
        int decimal = Integer.parseInt(cmbDecimal.getSelectedItem().toString());
        
        if (conversion()) {
            operacionCorrecta = true;
            switch(lblSimbolo.getText()){
                case "+": rdo = n1 + n2;
                    break;
                case "-": rdo = n1 - n2;
                    break;
                case "x": rdo = n1 * n2;
                    break;
                case "÷": 
                    if (n2 != 0) {
                        rdo = n1 / n2;
                    } else {
                        operacionCorrecta = false;
                        lblError.setText("Non se pode dividir entre 0");
                        lblError.setSize(lblError.getPreferredSize());
                    }
            }
            if(operacionCorrecta){
                lblRdo.setText(String.format("= %." + decimal + "f", rdo));
                lblRdo.setSize(lblRdo.getPreferredSize());
            }
        }
    }
    
    public void valoresIniciales(){
        if(archivo.exists()){
            try(Scanner sc = new Scanner(archivo)){
                while(sc.hasNext()){
                    txf1.setText(sc.nextLine());
                    lblSimbolo.setText(sc.nextLine());
                    switch(lblSimbolo.getText()){
                        case "+": rdbSuma.setSelected(true);
                            break;
                        case "-": rdbResta.setSelected(true);
                            break;
                        case "x": rdbMultiplicacion.setSelected(true);
                            break;
                        case "÷": rdbDivision.setSelected(true);
                    }
                    txf2.setText(sc.nextLine());
                    conversion();
                }
            }catch(IOException e){
                JOptionPane.showMessageDialog(null, "Non se poden ler os datos"+e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public boolean conversion(){
        boolean correcto = true;
        try {
            conversionCorrecta = true;
            n1 = Double.parseDouble(txf1.getText());
            n2 = Double.parseDouble(txf2.getText());
        } catch (NumberFormatException excep) {
            conversionCorrecta = false;
            lblRdo.setText("");
            lblError.setText("Debes introducir números");
            lblError.setSize(lblError.getPreferredSize());
        }
        return correcto;
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            lblSimbolo.setText(op.simbolo(((JRadioButton)e.getSource())));
            lblRdo.setText("=");
        }
    }

    class CierreVentana extends WindowAdapter {
        int num1;
        int num2;

        @Override
        public void windowClosing(WindowEvent e) {
            boolean correcto = true;
            try{
                correcto = true;
                num1 = (int)n1;
                num2 = (int)n2;                
            } catch (NumberFormatException excep){
                correcto = false;
                JOptionPane.showMessageDialog(null, "Datos non válidos: "+excep.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
            if(correcto && conversionCorrecta){
                try (PrintWriter f = new PrintWriter(new FileWriter(archivo, false))) {
                    f.println(num1);
                    f.println(lblSimbolo.getText());
                    f.println(num2);
                } catch (IOException excep) {
                    JOptionPane.showMessageDialog(null, "Non se poideron gardar aos datos: "+excep.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}