/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import javax.swing.JTextField;

/**
 *
 * @author Marcos Filho
 */
public class Numeros {

    static String valorSemPonto, valorTrocaVirgula;

    public static int TransformarNumeroEmInt(String valor) {
        int numeroint;

        valorSemPonto = valor.replace(".", "");
        numeroint = Integer.parseInt(valorSemPonto);

        return numeroint;
    }

    public static double TransformarNumeroEmDouble(String valor) {
        double numerodouble;

        if (valor.contains(".")) {
            valorSemPonto = valor.replace(".", "");
            valorTrocaVirgula = valorSemPonto.replace(",", ".");
        } else {
            valorTrocaVirgula = valor.replace(",", ".");
        }

        numerodouble = Double.parseDouble(valorTrocaVirgula);

        return numerodouble;
    }

    public static void SetarTextoNumeroEmFloat(String valor, JTextField txt) {
        if (!valor.equals("")) {
            if (!valor.contains(",")) {
                float numerofloat;

                numerofloat = Float.parseFloat(valor);
                String strDouble = String.format("%.1f", numerofloat);

                txt.setText(strDouble);
            }
        }
    }
    
    public static void SetarTextoNumeroEmDinheiro(String valor, JTextField txt) {
        if (!valor.equals("")) {
            if (!valor.contains(",")) {
                float numerofloat;

                numerofloat = Float.parseFloat(valor);
                String strDouble = String.format("%.2f", numerofloat);

                txt.setText(strDouble);
            }
        }
    }
}
