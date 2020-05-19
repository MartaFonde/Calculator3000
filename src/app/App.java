package app;

import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        Operacion op = new Operacion();
        op.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        op.setSize(850,400);
        op.setVisible(true);
    }
}