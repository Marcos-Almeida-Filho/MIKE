/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JTextField;

/**
 *
 * @author Marcos Filho
 */
public class Valores {

    public static String TransformarValorFloatEmDinheiro(String valor) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String valorf;

        if (valor.equals("0.0")) {
            valorf = "0,00";
        } else {
            BigDecimal valortotal = new BigDecimal(valor);
            valorf = formatter.format(valortotal);
        }

        return valorf;
    }

    public static double TransformarDinheiroEmValorDouble(String valor) {
        String v, valor2, valor3;

        double vp;

        if (valor.contains(".")) {
            v = valor.replace(".", "");
            valor2 = v.replace(",", ".");
        } else {
            valor2 = valor.replace(",", ".");
        }

        vp = Float.parseFloat(valor2);
        
        return vp;
    }

    public static String TransformarStringDinheiroEmStringDouble(String valor) {
        String replace1, replace2;

        if (valor.contains("R$")) {
            String valor2 = valor.replace("R$ ", "");
            replace1 = valor2.replace(".", "");
        } else {
            replace1 = valor.replace(".", "");
        }

        replace2 = replace1.replace(",", ".");

        return replace2;
    }
    
    public static double TransformarStringDinheiroEmDouble(String valor) {
        String replace1;
        double replace2;

        if (valor.contains("R$")) {
            String valor2 = valor.replace("R$ ", "");
            replace1 = valor2.replace(".", "");
        } else {
            replace1 = valor.replace(".", "");
        }

        replace2 = Double.parseDouble(replace1.replace(",", "."));

        return replace2;
    }

    public static void SetarTxtNumeroEmDinheiro(JTextField text) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String vv = text.getText().replace(".", "");
        String v = formatter.format(Float.parseFloat(vv.replace(",", ".")));
        text.setText(v);
    }

    public static String TransformarDoubleDBemString(double valordb) {
        String valor;

        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        valor = formatter.format(valordb);

        return valor;
    }

    public static String TransformarDoubleDBemDinheiroComLocal(double valordb) {
        Locale locale = new Locale("pt", "BR");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        return currencyFormatter.format(valordb);
    }
}
