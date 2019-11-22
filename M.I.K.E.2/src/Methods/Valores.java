/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.swing.JTextField;

/**
 *
 * @author Marcos Filho
 */
public class Valores {

    public static String TransformarValorFloatEmDinheiro(String valor) {
        BigDecimal valortotal = new BigDecimal(valor);

        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String valorf = formatter.format(valortotal);

        return valorf;
    }

    public static double TransformarDinheiroEmValorDouble(String valor) {
        String v = valor.replace(".", "");
        String valor2 = v.replace(",", ".");
        double vp = Float.parseFloat(valor2);

        return vp;
    }

    public static String TransformarStringDinheiroEmStringDouble(String valor) {
        String replace1 = valor.replace(".", "");
        String replace2 = replace1.replace(",", ".");

        return replace2;
    }
    
    public static void SetarTxtNumeroEmDinheiro(JTextField text) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String vv = text.getText().replace(".", "");
        String v = formatter.format(Float.parseFloat(vv.replace(",", ".")));
        text.setText(v);
    }
    
    public static String TransformarDoubleDBemString (double valordb) {
        String valor;
        
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        valor = formatter.format(valordb);
        
        return valor;
    }
}
