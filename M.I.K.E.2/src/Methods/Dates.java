/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import Bean.OSBean;
import DAO.OSDAO;
import View.logistica.RastreamentoDocumentos;
import com.toedter.calendar.JDateChooser;
import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author marco
 */
public class Dates {

    static String data;
    static String datanormal = "";
    static Calendar date;
    static Date d = new Date();

    public static String CriarDataCompletaParaDB() {
        date = Calendar.getInstance();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        data = simpleDateFormat.format(date.getTime());

        return data;
    }
    
    public static String CriarDataCompletaParaDBInicio(String data) {
        datanormal = "";
        String dia = data.substring(0, 2);
        String mes = data.substring(3, 5);
        String ano = data.substring(6, 10);
        datanormal = ano + "-" + mes + "-" + dia + " 00:00:00";

        return datanormal;
    }
    
    public static String CriarDataCompletaParaDBFim(String data) {
        datanormal = "";
        String dia = data.substring(0, 2);
        String mes = data.substring(3, 5);
        String ano = data.substring(6, 10);
        datanormal = ano + "-" + mes + "-" + dia + " 23:59:59";

        return datanormal;
    }

    public static String CriarDataCurta() {
        date = Calendar.getInstance();
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        data = simpleDateFormat.format(date.getTime());

        return data;
    }

    public static String CriarDataCurtaDBSemDataExistente() {
        date = Calendar.getInstance();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        data = simpleDateFormat.format(date.getTime());

        return data;
    }

    public static String CriarDataCurtaDBSemDataExistenteComPrazo(int days) {
        date = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        for (int i = 1; i <= days; i++) {
            date2.add(Calendar.DAY_OF_MONTH, 1);
            if (date2.get(Calendar.DAY_OF_WEEK) == 1 || date2.get(Calendar.DAY_OF_WEEK) == 7) {
                days++;
            }
        }

        date.add(Calendar.DAY_OF_MONTH, days);
        data = simpleDateFormat.format(date.getTime());

        return data;
    }

    public static String CriarIdOS() {
        OSDAO od = new OSDAO();

        Calendar ca = Calendar.getInstance();
        String patterny = "yy";
        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
        String year = simpleDateFormaty.format(ca.getTime());

        String idos = "OS" + year + "-0001";

        try { //Tentar achar primeira OS do ano para poder dar nome
            if (od.readnome()) {
                String hua = "";
                for (OSBean sob2 : od.read()) {
                    hua = String.valueOf(sob2.getIdtela());
                }
                int yearint = Integer.parseInt(hua.replace("OS" + year + "-", ""));
                int yearnovo = yearint + 1;
                String idtela = "OS" + year + "-" + String.format("%04d", yearnovo);
                idos = idtela;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OSDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                SendEmail.EnviarErro(ex.toString());
            } catch (AWTException | IOException ex1) {
                Logger.getLogger(Dates.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return idos;
    }

    public static String TransformarDataXML(String data) {
        datanormal = data.substring(0, 10) + " " + data.substring(12, 19);
        return datanormal;
    }

    public static String CriarDataCurtaDBComDataExistente(String data) {
        datanormal = "";
        String dia = data.substring(0, 2);
        String mes = data.substring(3, 5);
        String ano = data.substring(6, 10);
        datanormal = ano + "-" + mes + "-" + dia;

        return datanormal;
    }

    public static String TransformarDataCompletaDoDB(String datadb) {
        datanormal = "";
        if (datadb != null) {
            String dia = datadb.substring(8, 10);
            String mes = datadb.substring(5, 7);
            String ano = datadb.substring(0, 4);
            String hora = datadb.substring(11, 19);
            datanormal = dia + "/" + mes + "/" + ano + " " + hora;
        }

        return datanormal;
    }

    public static String TransformarDataCurtaDoDB(String datadb) {
        datanormal = "";
        String dia = datadb.substring(8, 10);
        String mes = datadb.substring(5, 7);
        String ano = datadb.substring(0, 4);
        datanormal = dia + "/" + mes + "/" + ano;

        return datanormal;
    }

    public static String dataxml(String dataxml) {
        datanormal = "";
        String dia = dataxml.substring(8, 10);
        String mes = dataxml.substring(5, 7);
        String ano = dataxml.substring(0, 4);

        datanormal = dia + "/" + mes + "/" + ano;

        return datanormal;
    }

    public static String verificadata1() {
        //Variáveis
        String dia, mes, anos, datafinal;

        //Loop infinito até que seja feito uma data válida
        for (;;) {
            data:
            {
                //Valor da data
                datafinal = JOptionPane.showInputDialog(null, "Qual a data?", "Data", JOptionPane.YES_NO_OPTION);

                if (!datafinal.equals("")) {
                    if (datafinal.matches("\\d{2}[/]{1}\\d{2}[/]{1}\\d{4}")) {
                        //Separar dia/mês/ano da data
                        dia = datafinal.substring(0, 2);
                        mes = datafinal.substring(3, 5);
                        anos = datafinal.substring(6, 10);

                        //Variável ano em int
                        int ano = Integer.parseInt(anos);

                        switch (mes) {
                            case "01":
                                if (dia.substring(0, 1).matches("[0123]{1}")) {
                                    switch (dia.substring(0, 1)) {
                                        case "3":
                                            if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                        default:
                                            if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                    break data;
                                }
                            case "02":
                                //Se o ano for bissexto
                                if ((ano % 4 == 0 && ano % 100 != 0) | (ano % 400 == 0)) {
                                    if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        return datafinal;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                        break data;
                                    }
                                } else {//Se não for bissexto
                                    switch (dia.substring(0, 1)) {
                                        case "2":
                                            if (datafinal.matches("[2]{1}[0-8]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                        default:
                                            if (datafinal.matches("[01]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                    }
                                }
                            case "03":
                                if (dia.substring(0, 1).matches("[0123]{1}")) {
                                    switch (dia.substring(0, 1)) {
                                        case "3":
                                            if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                        default:
                                            if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                    break data;
                                }
                            case "04":
                                if (dia.substring(0, 1).matches("[0123]{1}")) {
                                    switch (dia.substring(0, 1)) {
                                        case "3":
                                            if (datafinal.matches("[3]{1}[0]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                        default:
                                            if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                    break data;
                                }
                            case "05":
                                if (dia.substring(0, 1).matches("[0123]{1}")) {
                                    switch (dia.substring(0, 1)) {
                                        case "3":
                                            if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                        default:
                                            if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                    break data;
                                }
                            case "06":
                                if (dia.substring(0, 1).matches("[0123]{1}")) {
                                    switch (dia.substring(0, 1)) {
                                        case "3":
                                            if (datafinal.matches("[3]{1}[0]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                        default:
                                            if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                    break data;
                                }
                            case "07":
                                if (dia.substring(0, 1).matches("[0123]{1}")) {
                                    switch (dia.substring(0, 1)) {
                                        case "3":
                                            if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                        default:
                                            if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                    break data;
                                }
                            case "08":
                                if (dia.substring(0, 1).matches("[0123]{1}")) {
                                    switch (dia.substring(0, 1)) {
                                        case "3":
                                            if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                        default:
                                            if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                    break data;
                                }
                            case "09":
                                if (dia.substring(0, 1).matches("[0123]{1}")) {
                                    switch (dia.substring(0, 1)) {
                                        case "3":
                                            if (datafinal.matches("[3]{1}[0]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                        default:
                                            if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                    break data;
                                }
                            case "10":
                                if (dia.substring(0, 1).matches("[0123]{1}")) {
                                    switch (dia.substring(0, 1)) {
                                        case "3":
                                            if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                        default:
                                            if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                    break data;
                                }
                            case "11":
                                if (dia.substring(0, 1).matches("[0123]{1}")) {
                                    switch (dia.substring(0, 1)) {
                                        case "3":
                                            if (datafinal.matches("[3]{1}[0]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                        default:
                                            if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                    break data;
                                }
                            case "12":
                                if (dia.substring(0, 1).matches("[0123]{1}")) {
                                    switch (dia.substring(0, 1)) {
                                        case "3":
                                            if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                        default:
                                            if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                                return datafinal;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                                break data;
                                            }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Data inválida!\nValor digitado: " + datafinal);
                                    break data;
                                }
                            default:
                                JOptionPane.showMessageDialog(null, "Data incorreta!\nPadrão 'dd/mm/aaaa'");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Data incorreta!\nPadrão 'dd/mm/aaaa'");
                    }
                }
                break;
            }
        }
        return datafinal;
    }

    public static boolean verificadata(String data) {
        //Variáveis
        String dia, mes, anos, datafinal;
        boolean b = false;

        //Valor da data
        datafinal = data;

        if (!datafinal.equals("")) {
            if (datafinal.matches("\\d{2}[/]{1}\\d{2}[/]{1}\\d{4}")) {
                //Separar dia/mês/ano da data
                dia = datafinal.substring(0, 2);
                mes = datafinal.substring(3, 5);
                anos = datafinal.substring(6, 10);

                //Variável ano em int
                int ano = Integer.parseInt(anos);

                switch (mes) {
                    case "01":
                        if (dia.substring(0, 1).matches("[0123]{1}")) {
                            switch (dia.substring(0, 1)) {
                                case "3":
                                    if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida1!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                                default:
                                    if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida2!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Data inválida3!\nValor digitado: " + datafinal);
                            b = false;
                            break;
                        }
                        break;
                    case "02":
                        //Se o ano for bissexto
                        if ((ano % 4 == 0 && ano % 100 != 0) | (ano % 400 == 0)) {
                            if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                b = true;
                                break;
                            } else {
                                JOptionPane.showMessageDialog(null, "Data inválida4!\nValor digitado: " + datafinal);
                                b = false;
                                break;
                            }
                        } else {//Se não for bissexto
                            switch (dia.substring(0, 1)) {
                                case "2":
                                    if (datafinal.matches("[2]{1}[0-8]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida5!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                                default:
                                    if (datafinal.matches("[01]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida6!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                            }
                        }
                        break;
                    case "03":
                        if (dia.substring(0, 1).matches("[0123]{1}")) {
                            switch (dia.substring(0, 1)) {
                                case "3":
                                    if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida7!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                                default:
                                    if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida8!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Data inválida9!\nValor digitado: " + datafinal);
                            b = false;
                            break;
                        }
                        break;
                    case "04":
                        if (dia.substring(0, 1).matches("[0123]{1}")) {
                            switch (dia.substring(0, 1)) {
                                case "3":
                                    if (datafinal.matches("[3]{1}[0]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida10!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                                default:
                                    if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida11!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Data inválida12!\nValor digitado: " + datafinal);
                            b = false;
                            break;
                        }
                        break;
                    case "05":
                        if (dia.substring(0, 1).matches("[0123]{1}")) {
                            switch (dia.substring(0, 1)) {
                                case "3":
                                    if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida13!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                                default:
                                    if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida14!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Data inválida15!\nValor digitado: " + datafinal);
                            b = false;
                            break;
                        }
                        break;
                    case "06":
                        if (dia.substring(0, 1).matches("[0123]{1}")) {
                            switch (dia.substring(0, 1)) {
                                case "3":
                                    if (datafinal.matches("[3]{1}[0]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida16!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                                default:
                                    if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida17!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Data inválida18!\nValor digitado: " + datafinal);
                            b = false;
                            break;
                        }
                        break;
                    case "07":
                        if (dia.substring(0, 1).matches("[0123]{1}")) {
                            switch (dia.substring(0, 1)) {
                                case "3":
                                    if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida19!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                                default:
                                    if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida20!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Data inválida21!\nValor digitado: " + datafinal);
                            b = false;
                            break;
                        }
                        break;
                    case "08":
                        if (dia.substring(0, 1).matches("[0123]{1}")) {
                            switch (dia.substring(0, 1)) {
                                case "3":
                                    if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida22!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                                default:
                                    if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida23!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Data inválida24!\nValor digitado: " + datafinal);
                            b = false;
                            break;
                        }
                        break;
                    case "09":
                        if (dia.substring(0, 1).matches("[0123]{1}")) {
                            switch (dia.substring(0, 1)) {
                                case "3":
                                    if (datafinal.matches("[3]{1}[0]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida25!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                                default:
                                    if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida26!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Data inválida27!\nValor digitado: " + datafinal);
                            b = false;
                            break;
                        }
                        break;
                    case "10":
                        if (dia.substring(0, 1).matches("[0123]{1}")) {
                            switch (dia.substring(0, 1)) {
                                case "3":
                                    if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida28!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                                default:
                                    if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida29!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Data inválida30!\nValor digitado: " + datafinal);
                            b = false;
                            break;
                        }
                        break;
                    case "11":
                        if (dia.substring(0, 1).matches("[0123]{1}")) {
                            switch (dia.substring(0, 1)) {
                                case "3":
                                    if (datafinal.matches("[3]{1}[0]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida31!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                                default:
                                    if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida32!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Data inválida33!\nValor digitado: " + datafinal);
                            b = false;
                            break;
                        }
                        break;
                    case "12":
                        if (dia.substring(0, 1).matches("[0123]{1}")) {
                            switch (dia.substring(0, 1)) {
                                case "3":
                                    if (datafinal.matches("[3]{1}[0-1]{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida34!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                                default:
                                    if (datafinal.matches("[012]{1}\\d{1}[/]{1}[01]{1}\\d{1}[/]{1}\\d{4}")) {
                                        b = true;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Data inválida35!\nValor digitado: " + datafinal);
                                        b = false;
                                        break;
                                    }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Data inválida36!\nValor digitado: " + datafinal);
                            b = false;
                            break;
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Data incorreta!\nPadrão 'dd/mm/aaaa'");
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Data incorreta38!\nPadrão 'dd/mm/aaaa'");
            }
        }
        return b;
    }

    public static String InverterDataCurta(String data) {
        String datafinalinvertida;

        String dia = data.substring(0, 2);
        String mes = data.substring(3, 5);
        String ano = data.substring(6, 10);
        datafinalinvertida = ano + "/" + mes + "/" + dia;

        return datafinalinvertida;
    }

    public static String CriarDataCurtaDBJDateChooser(Date data) {
        datanormal = "";
        if (data == null) {
            datanormal = null;

            return datanormal;
        } else {
            String datafinal;

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            datafinal = df.format(data);
            String dia = datafinal.substring(0, 2);
            String mes = datafinal.substring(3, 5);
            String ano = datafinal.substring(6, 10);
            datanormal = ano + "-" + mes + "-" + dia;

            return datanormal;
        }
    }

    public static String CriarDataCurtaJDateChooser(Date data) {
        datanormal = "";
        if (data == null) {
            datanormal = null;
        } else {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            datanormal = df.format(data);
        }
        
        return datanormal;
    }

    public static void SetarDataJDateChooser(JDateChooser chooser, String datadb) {
        if (datadb != null) {
            try {
                chooser.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(Dates.TransformarDataCurtaDoDB(datadb)));
            } catch (ParseException ex) {
                Logger.getLogger(RastreamentoDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String primeiroDiaDoMes() {
        String primeiroDia;

        LocalDate today = LocalDate.now();

        primeiroDia = today.withDayOfMonth(1).toString();

        return primeiroDia;
    }

    public static String ultimoDiaDoMes() {
        String primeiroDia;

        LocalDate today = LocalDate.now();

        primeiroDia = today.withDayOfMonth(today.lengthOfMonth()).toString();

        return primeiroDia;
    }
}
