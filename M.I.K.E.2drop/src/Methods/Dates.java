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
 * @author marco
 */
public class Dates {

    static String data = "";
    static String datanormal = "";

    public static String criardatadb() {
        Calendar date = Calendar.getInstance();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        data = simpleDateFormat.format(date.getTime());

        return data;
    }

    public static String datanormal(String datadb) {
        String dia = datadb.substring(8, 10);
        String mes = datadb.substring(5, 7);
        String ano = datadb.substring(0, 4);
        String hora = datadb.substring(11, 19);
        datanormal = dia + "/" + mes + "/" + ano + " " + hora;

        return datanormal;
    }
}
