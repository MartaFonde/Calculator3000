package app;

import javax.swing.JRadioButton;

public class RdbSimbol extends JRadioButton {
    String operacion; 

    public RdbSimbol(String text, String operacion){
        super(text);
        this.operacion = operacion;
    }
}