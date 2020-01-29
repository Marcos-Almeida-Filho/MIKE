/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Methods;

/**
 *
 * @author Marcos Filho
 */
public class NumerosInt {
    
    public static int TransformarNumeroEmInt (String valor) {
        int numeroint;
        
        String valorSemPonto = valor.replace(".","");
        numeroint = Integer.parseInt(valorSemPonto);
        
        return numeroint;
    }
}
