package app;

import javax.swing.JRadioButton;

public class SimboloOperacion extends JRadioButton {
    String operacion = "?"; 

    public String simbolo(JRadioButton rdb){
        if(rdb.getText().contains("Suma")){
            operacion = "+";
        } 
        if(rdb.getText().contains("Resta")){
            operacion = "-";
        } 
        if(rdb.getText().contains("Multiplicación")){
            operacion = "x";
        } 
        if(rdb.getText().contains("División")) {
            operacion = "÷";
        }
        return operacion;
    }
}