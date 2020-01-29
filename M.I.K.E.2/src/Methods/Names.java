/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Marcos Filho
 */
public class Names {

    public static String CriarIdTela(boolean checkid, String abrev, String ultimo) {
        String idtela;

        Calendar ca = Calendar.getInstance();
        String patterny = "yy";
        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
        String year = simpleDateFormaty.format(ca.getTime());

        if (checkid) {
            int num = Integer.parseInt(ultimo.substring(ultimo.length() - 4, ultimo.length()));
            idtela = abrev + year + "-" + String.format("%04d", num + 1);
        } else {
            idtela = abrev + year + "-0001";
        }

        return idtela;
    }
}
