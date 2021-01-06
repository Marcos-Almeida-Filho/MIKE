/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.vendas;

import Connection.Session;
import DAO.VendasMateriaisCodigoClienteDAO;
import DAO.VendasMateriaisDAO;
import DAO.VendasMateriaisDocDAO;
import DAO.VendasMateriaisMovDAO;
import DAO.VendasMateriaisObsDAO;
import Methods.Dates;
import Methods.Numeros;
import Methods.SendEmail;
import Methods.Telas;
import View.Geral.AdicionarObs;
import View.Geral.ProcurarMaterial;
import View.Geral.ProcurarDocumento;
import View.Geral.ProcurarLocal;
import View.Geral.ProcurarMateriaPrima;
import View.servicos.DocumentosPedidoServico;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class VM extends javax.swing.JInternalFrame {

    String codigo, desc, descFerr, ferr, tipotopo, cortes, cortesdesc, revchar, revtipo, raio, ichar, idesc, richar, ritipo, weldonchar, weldondesc, extra, comptotal, diamfinal, tipocanal, tipoCanalChar, materiaPrima, materiaPrimaChar, desbaste;

    String[] riarray = new String[2];
    String[] weldonarray = new String[2];
    String[] iarray = new String[2];

    static int idmaterial = 0;

    static VendasMateriaisDAO vmd = new VendasMateriaisDAO();

    static VendasMateriaisDocDAO vmdd = new VendasMateriaisDocDAO();

    static VendasMateriaisObsDAO vmod = new VendasMateriaisObsDAO();

    static VendasMateriaisMovDAO vmmd = new VendasMateriaisMovDAO();

    static VendasMateriaisCodigoClienteDAO vmccd = new VendasMateriaisCodigoClienteDAO();

    /**
     * Creates new form Produtos
     */
    //Variáveis para criar código
    public VM() {
        initComponents();
        lblcodigoerro.setVisible(false);
        lbldescricaoerro.setVisible(false);
        radioVazio.setVisible(false);
        readProdutos();
        idmaterial = 0;
    }

    public void lerMaterial(int idmaterial) {
        tabmateriais.setSelectedIndex(1);

        vmd.click(idmaterial).forEach(vmb -> {
            txtcodigo.setText(vmb.getCodigo());
            txtdescricao.setText(vmb.getDescricao());
            txtestoque.setText(String.valueOf(vmb.getEstoque()));
            txtestoqueminimo.setText(String.valueOf(vmb.getEstoqueMinimo()));
            txtQtdOp.setText(String.valueOf(vmb.getQtdMinimaOP()));
            txtstatus.setText(vmb.getStatus());
            txtLocal.setText(vmb.getLocal());
            txtd1.setText(vmb.getD1());
            txtd2.setText(vmb.getD2());
            txtd3.setText(vmb.getD3());
            txtd4.setText(vmb.getD4());
            txtd5.setText(vmb.getD5());
            txtl1.setText(vmb.getL1());
            txtl2.setText(vmb.getL2());
            txtl3.setText(vmb.getL3());
            txtl4.setText(vmb.getL4());
            txtl5.setText(vmb.getL5());
            txtmaterialdeorigem.setText(vmb.getMaterialOrigem());
            if (!vmb.getRev().equals("Selecione")) {
                checkrevestimento.setSelected(true);
                cbrevestimento.setSelectedItem(vmb.getRev());
            } else {
                checkrevestimento.setSelected(false);
                cbrevestimento.setSelectedIndex(0);
            }
            if (!vmb.getRaio().equals("")) {
                checkraio.setSelected(true);
                txtraio.setText(vmb.getRaio());
            } else {
                checkraio.setSelected(false);
                txtraio.setText("");
            }
            checkimportado.setSelected(vmb.isImportada());
            checkweldon.setSelected(vmb.isWeldon());
            checkri.setSelected(vmb.isRi());
            radioMD.setSelected(vmb.isMd());
            radioHSS.setSelected(vmb.isHss());
            cbtipo.setSelectedItem(vmb.getTipo());
            cbfamilia.setSelectedItem(vmb.getFamilia());
            cbtamanho.setSelectedItem(vmb.getTamanho());
            txtcortes.setText(vmb.getCortes());
            cbtopo.setSelectedItem(vmb.getTopo());
            cbcanal.setSelectedItem(vmb.getCanal());
            txtextra.setText(vmb.getExtra());
            txthelice.setText(vmb.getHelice());
            txtnucleo.setText(vmb.getNucleo());
            txtconcavidade.setText(vmb.getConcavidade());
            txtaliviotopo1.setText(vmb.getTopo1());
            txtaliviotopo2.setText(vmb.getTopo2());
            txtalivio1.setText(vmb.getAlivio1());
            txtalivio2.setText(vmb.getAlivio2());
            txtespfilete.setText(vmb.getFilete());
            txtagressividade.setText(vmb.getAgressividade());
            txtfrontal.setText(vmb.getFrontal());
        });

        readObs(idmaterial);

        readDocs(idmaterial);

        readDescCli(idmaterial);

        readMov(idmaterial);

        revestimento();

        raio();
    }

    public static void readMov(int idmaterial) {
        DefaultTableModel model = (DefaultTableModel) tableMovimentacao.getModel();
        model.setNumRows(0);

        vmmd.read(idmaterial).forEach(vmmb -> {
            model.addRow(new Object[]{
                Dates.TransformarDataCurtaDoDB(vmmb.getData()),
                vmmb.getTipo(),
                vmmb.getQtdInicial(),
                vmmb.getQtdMovimentada(),
                vmmb.getSaldo(),
                vmmb.getUsuario()
            });
        });
    }

    public static void readDescCli(int idmaterial) {
        DefaultTableModel model = (DefaultTableModel) tabledesccli.getModel();
        model.setNumRows(0);

        vmccd.read(idmaterial).forEach(vmccb -> {
            model.addRow(new Object[]{
                vmccb.getId(),
                false,
                vmccb.getCliente(),
                vmccb.getCodigo(),
                vmccb.getDescricao()
            });
        });
    }

    public static void readObs(int idmaterial) {
        DefaultTableModel model = (DefaultTableModel) tableobs.getModel();
        model.setNumRows(0);

        vmod.read(idmaterial).forEach(vmob -> {
            model.addRow(new Object[]{
                vmob.getId(),
                false,
                Dates.TransformarDataCurtaDoDB(vmob.getData()),
                vmob.getUsuario(),
                vmob.getObs()
            });
        });
    }

    public static void readDocs(int idmaterial) {
        DefaultTableModel model = (DefaultTableModel) tabledocumentos.getModel();
        model.setNumRows(0);

        vmdd.read(idmaterial).forEach(vmdb -> {
            model.addRow(new Object[]{
                vmdb.getId(),
                false,
                vmdb.getDescricao(),
                vmdb.getLocal()
            });
        });
    }

    public static void readProdutos() {
        DefaultTableModel modelProdutos = (DefaultTableModel) tablemateriaisvendas.getModel();
        modelProdutos.setNumRows(0);

        int cbindex = cbstatus.getSelectedIndex();

        switch (cbindex) {
            case 2:
                if (txtpesquisa.getText().equals("")) {
                    vmd.readtodos().forEach(vmb -> {
                        modelProdutos.addRow(new Object[]{
                            vmb.getId(),
                            vmb.getCodigo(),
                            vmb.getDescricao(),
                            vmb.getEstoque(),
                            vmb.getLocal(),
                            vmb.getStatus()
                        });
                    });
                } else {
                    vmd.readTodosPesquisa(txtpesquisa.getText()).forEach(vmb -> {
                        modelProdutos.addRow(new Object[]{
                            vmb.getId(),
                            vmb.getCodigo(),
                            vmb.getDescricao(),
                            vmb.getEstoque(),
                            vmb.getLocal(),
                            vmb.getStatus()
                        });
                    });
                }
                break;
            default:
                if (txtpesquisa.getText().equals("")) {
                    vmd.readStatus(cbstatus.getSelectedItem().toString()).forEach(vmb -> {
                        modelProdutos.addRow(new Object[]{
                            vmb.getId(),
                            vmb.getCodigo(),
                            vmb.getDescricao(),
                            vmb.getEstoque(),
                            vmb.getLocal(),
                            vmb.getStatus()
                        });
                    });
                } else {
                    vmd.readStatusPesquisa(cbstatus.getSelectedItem().toString(), txtpesquisa.getText()).forEach(vmb -> {
                        modelProdutos.addRow(new Object[]{
                            vmb.getId(),
                            vmb.getCodigo(),
                            vmb.getDescricao(),
                            vmb.getEstoque(),
                            vmb.getLocal(),
                            vmb.getStatus()
                        });
                    });
                }
                break;
        }
    }

    public static Double multiply(double a, double b) {
        return a * b;
    }

    public static void cbexcluir() {
        cbfamilia.removeAllItems();
        cbfamilia.addItem("Selecione");

        cbtamanho.removeAllItems();
        cbtamanho.addItem("Selecione");

        cbcanal.removeAllItems();
        cbcanal.addItem("Selecione");
    }

    public static void cbfamiliaincluir() {
        int selection = cbtipo.getSelectedIndex();
        switch (selection) {
            case 1://Fresa
                cbfamilia.addItem("12");
                cbfamilia.addItem("13");
                cbfamilia.addItem("14");
                cbfamilia.addItem("14.2");
                cbfamilia.addItem("14.3");
                cbfamilia.addItem("15");
                cbfamilia.addItem("16");
                cbfamilia.addItem("17");
                break;
            case 2://Fresa Especial
                cbfamilia.addItem("12");
                cbfamilia.addItem("13");
                cbfamilia.addItem("14");
                cbfamilia.addItem("14.2");
                cbfamilia.addItem("14.3");
                cbfamilia.addItem("15");
                cbfamilia.addItem("16");
                cbfamilia.addItem("17");
                break;
            case 3://Broca
                cbfamilia.addItem("6539");
                cbfamilia.addItem("338N");
                cbfamilia.addItem("340N");
                cbfamilia.addItem("6537KRE");
                cbfamilia.addItem("6537KRI");
                cbfamilia.addItem("6537LRE");
                cbfamilia.addItem("6537LRI");
                break;
            case 4://Broca Especial
                cbfamilia.addItem("6539");
                cbfamilia.addItem("338N");
                cbfamilia.addItem("340N");
                cbfamilia.addItem("6537KRE");
                cbfamilia.addItem("6537KRI");
                cbfamilia.addItem("6537LRE");
                cbfamilia.addItem("6537LRI");
                break;
            case 5://Escareador
                cbfamilia.addItem("510-60");
                cbfamilia.addItem("510-90");
                break;
            case 6://Escareador Especial
                cbfamilia.addItem("510");
                break;
            case 7://Alargador
                cbfamilia.addItem("2111");
                cbfamilia.addItem("212");
                break;
            case 8://Alargador Especial
                cbfamilia.addItem("2111");
                cbfamilia.addItem("212");
                break;
            case 9://Lima
                cbfamilia.addItem("SA");
                cbfamilia.addItem("SB");
                cbfamilia.addItem("SC");
                cbfamilia.addItem("SD");
                cbfamilia.addItem("SE");
                cbfamilia.addItem("SF");
                cbfamilia.addItem("SG");
                cbfamilia.addItem("SH");
                cbfamilia.addItem("SJ");
                cbfamilia.addItem("SK");
                cbfamilia.addItem("SL");
                cbfamilia.addItem("SM");
                cbfamilia.addItem("SN");
                cbfamilia.addItem("SGR");
                break;
            case 10://Lima Especial
                cbfamilia.addItem("SA");
                cbfamilia.addItem("SB");
                cbfamilia.addItem("SC");
                cbfamilia.addItem("SD");
                cbfamilia.addItem("SE");
                cbfamilia.addItem("SF");
                cbfamilia.addItem("SG");
                cbfamilia.addItem("SH");
                cbfamilia.addItem("SJ");
                cbfamilia.addItem("SK");
                cbfamilia.addItem("SL");
                cbfamilia.addItem("SM");
                cbfamilia.addItem("SN");
                cbfamilia.addItem("SGR");
                break;
            case 11://Ferramenta Especial
                break;
            default:
                break;
        }
    }

    public String checarrevestimento() {
        //Identificar se tem revestimento e qual é
        if (checkrevestimento.isSelected()) {
            revtipo = " Com Revestimento"/* + cbrevestimento.getSelectedItem().toString()*/;
            int selection = cbrevestimento.getSelectedIndex();
            switch (selection) {
                case 1:
                    revchar = " T";
                    break;
                case 2:
                    revchar = " A";
                    break;
                case 3:
                    revchar = " AC";
                    break;
                default:
                    break;
            }
        } else {
            revtipo = "";
            revchar = "";
        }

        return revtipo;
    }

    public String checarraio() {
        //Identificar se tem raio e qual medida
        if (checkraio.isSelected()) {
            raio = " R" + txtraio.getText();
        } else {
            raio = "";
        }

        return raio;
    }

    public String[] checarimportada() {
        //Identificar se é importada
        if (checkimportado.isSelected()) {
            ichar = " I";
            idesc = " Importada";
        } else {
            ichar = "";
            idesc = "";
        }

        iarray[0] = ichar;
        iarray[1] = idesc;

        return iarray;
    }

    public String[] checarri() {
        //Identificar se tem refrigeração interna
        if (checkri.isSelected()) {
            richar = " RI";
            ritipo = " Com Refrigeração Interna";
        } else {
            richar = "";
            ritipo = "";
        }

        riarray[0] = richar;
        riarray[1] = ritipo;

        return riarray;
    }

    public String checarDesbaste() {
        //Identificar se tem raio e qual medida
        if (checkDesbaste.isSelected()) {
            desbaste = " Para Desbaste";
        } else {
            desbaste = "";
        }

        return desbaste;
    }

    public String[] checarweldon() {
        if (checkweldon.isSelected()) {
            weldonchar = " W";
            weldondesc = " Com Weldon";
        } else {
            weldonchar = "";
            weldondesc = "";
        }

        weldonarray[0] = weldonchar;
        weldonarray[1] = weldondesc;

        return weldonarray;
    }

    public void checarextra() {
        if (!txtextra.getText().equals("")) {
            extra = " - " + txtextra.getText();
        } else {
            extra = "";
        }
    }

    public void checarComprimentoTotal() {
        if (!txtl5.getText().equals("")) {
            comptotal = txtl5.getText();
        } else if (!txtl4.getText().equals("")) {
            comptotal = txtl4.getText();
        } else if (!txtl3.getText().equals("")) {
            comptotal = txtl3.getText();
        } else {
            comptotal = txtl2.getText();
        }
    }

    public void checarDiamFinal() {
        if (!txtd5.getText().equals("")) {
            diamfinal = txtd5.getText();
        } else if (!txtd4.getText().equals("")) {
            diamfinal = txtd4.getText();
        } else if (!txtd3.getText().equals("")) {
            diamfinal = txtd3.getText();
        } else {
            diamfinal = txtd2.getText();
        }
    }

    public void checarTipoCanal() {
        String canalSelecionado = cbcanal.getSelectedItem().toString();

        switch (canalSelecionado) {
            case "Reto":
                tipoCanalChar = "R";
                tipocanal = " Canal Reto";
                break;
            case "Helicoidal":
                tipoCanalChar = "H";
                tipocanal = " Canal Helicoidal";
                break;
            case "DC":
                tipoCanalChar = "DC ";
                tipocanal = " Corte Duplo";
                break;
            case "FC":
                tipoCanalChar = "FC ";
                tipocanal = " Corte Fino";
                break;
            default:
                tipocanal = "";
                tipoCanalChar = "";
                break;
        }
    }

    public void checarMateriaPrima() {
        if (radioMD.isSelected()) {
            materiaPrima = "Metal Duro ";
            materiaPrimaChar = "MD";
        } else {
            materiaPrima = "HSS ";
            materiaPrimaChar = "HSS";
        }
    }

    public void checagemGeral() {
        checarrevestimento();

        checarraio();

        checarimportada();

        checarri();

        checarDesbaste();

        checarweldon();

        checarextra();

        checarComprimentoTotal();

        checarDiamFinal();

        checarTipoCanal();

        checarMateriaPrima();
    }

    public void gerarcodigofresa() {
        checagemGeral();

        ferr = "Fresa Topo ";

        //Identificar família da fresa e criar descrição
//        int selection = cbfamilia.getSelectedIndex();
        String sel = cbfamilia.getSelectedItem().toString();
        switch (sel) {
            case "12":
                tipotopo = "Reto " + materiaPrima;
                cortes = "2 Cortes ";
                break;
            case "13":
                tipotopo = "Reto " + materiaPrima;
                cortes = "3 Cortes ";
                break;
            case "14":
                tipotopo = "Reto " + materiaPrima;
                cortes = "4 Cortes ";
                break;
            case "15":
                tipotopo = "Esférico " + materiaPrima;
                cortes = "2 Cortes ";
                break;
            case "16":
                tipotopo = "Esférico " + materiaPrima;
                cortes = "4 Cortes ";
                break;
            case "17":
                tipotopo = "Esférico " + materiaPrima;
                cortes = "3 Cortes ";
                break;
            case "14.2":
                tipotopo = "Reto " + materiaPrima;
                cortes = "4 Cortes ";
                break;
            case "14.3":
                tipotopo = "Reto " + materiaPrima;
                cortes = "4 Cortes ";
                break;
        }

        //Criar código/descrição
        codigo = cbfamilia.getSelectedItem().toString() + "-" + txtd1.getText() + cbtamanho.getSelectedItem().toString() + materiaPrimaChar + raio + revchar + riarray[0] + weldonarray[0] + iarray[0];
        desc = ferr + tipotopo + cortes + txtd1.getText() + "x" + txtl1.getText() + "x" + txtl2.getText() + "x" + txtd2.getText() + raio + revtipo + riarray[1] + desbaste + weldonarray[1] + iarray[1];

        //Colocar código e descrição nos txt's
        txtcodigo.setText(codigo);
        txtdescricao.setText(desc);
    }

    public void gerarcodigofresaespecial() {
        checagemGeral();

        //Identificar se a fresa tem família ou número de cortes e criar descrição
        if (cbfamilia.getSelectedIndex() != 0) {
            int selection = cbfamilia.getSelectedIndex();
            switch (selection) {
                case 1:
                    ferr = "Fresa Topo ";
                    tipotopo = "Reto " + materiaPrima;
                    cortes = "2 Cortes ";
                    break;
                case 2:
                    ferr = "Fresa Topo ";
                    tipotopo = "Reto " + materiaPrima;
                    cortes = "3 Cortes ";
                    break;
                case 3:
                    ferr = "Fresa Topo ";
                    tipotopo = "Reto " + materiaPrima;
                    cortes = "4 Cortes ";
                    break;
                case 4:
                    ferr = "Fresa Topo ";
                    tipotopo = "Esférico " + materiaPrima;
                    cortes = "2 Cortes ";
                    break;
                case 5:
                    ferr = "Fresa Topo ";
                    tipotopo = "Esférico " + materiaPrima;
                    cortes = "4 Cortes ";
                    break;
                case 6:
                    ferr = "Fresa Topo ";
                    tipotopo = "Esférico " + materiaPrima;
                    cortes = "3 Cortes ";
                    break;
                default:
                    break;
            }
        } else {
            ferr = "Fresa Topo ";
            tipotopo = cbtopo.getSelectedItem().toString() + " Metal Duro ";
            cortes = txtcortes.getText() + " Cortes ";
        }

        //Criar código/descrição
        if (!txtcortes.getText().equals("")) {
            codigo = cbtipo.getSelectedItem().toString() + " " + txtcortes.getText() + "C " + txtd1.getText() + "x" + txtl1.getText() + "x" + comptotal + "x" + diamfinal + materiaPrimaChar + raio + revchar + riarray[0] + weldonarray[0] + iarray[0] + extra;
            desc = ferr + tipotopo + cortes + txtd1.getText() + "x" + txtl1.getText() + "x" + comptotal + "x" + diamfinal + raio + revtipo + riarray[1] + desbaste + weldonarray[1] + extra;
        } else {
            codigo = cbtipo.getSelectedItem().toString() + " " + cbfamilia.getSelectedItem().toString() + "-" + txtd1.getText() + "x" + txtl1.getText() + "x" + comptotal + "x" + diamfinal + raio + revchar + riarray[0] + weldonarray[0] + ichar + extra;
            desc = ferr + tipotopo + cortes + txtd1.getText() + "x" + txtl1.getText() + "x" + comptotal + "x" + diamfinal + raio + revtipo + riarray[1] + weldonarray[1] + iarray[1] + extra;
        }

        //Colocar código e descrição nos txt's
        txtcodigo.setText(codigo);
        txtdescricao.setText(desc);
    }

    public void gerarcodigobroca() {
        checagemGeral();

        //Identificar família da broca e criar descrição
        int selection = cbfamilia.getSelectedIndex();
        switch (selection) {
            case 1:
                ferr = "6539 Diam. ";
                descFerr = "6539 " + materiaPrima + "Diam. ";
                break;
            case 2:
                ferr = "338N Diam. ";
                descFerr = "338N " + materiaPrima + "Diam. ";
                break;
            case 3:
                ferr = "340N Diam. ";
                descFerr = "340N " + materiaPrima + "Diam. ";
                break;
            case 4:
                ferr = "6537KRE Diam. ";
                descFerr = "6537KRE " + materiaPrima + "Diam. ";
                break;
            case 5:
                ferr = "6537KRI Diam. ";
                descFerr = "6537KRI " + materiaPrima + "Diam. ";
                break;
            case 6:
                ferr = "6537LRE Diam. ";
                descFerr = "6537LRE " + materiaPrima + "Diam. ";
                break;
            case 7:
                ferr = "6537LRI Diam. ";
                descFerr = "6537LRI " + materiaPrima + "Diam. ";
                break;
            default:
                break;
        }

        //Criar código/descrição
        codigo = ferr + txtd1.getText() + riarray[0] + materiaPrimaChar + raio + revchar + weldonarray[0] + iarray[0];
        desc = descFerr + txtd1.getText() + "x" + txtl1.getText() + "x" + txtl2.getText() + "x" + txtd2.getText() + riarray[1] + desbaste + raio + revtipo + weldonarray[1] + iarray[1];

        //Colocar código e descrição nos txt's
        txtcodigo.setText(codigo);
        txtdescricao.setText(desc);
    }

    public void gerarCodigoBrocaEspecial() {
        checagemGeral();

        if (!txtcortes.getText().equals("")) {
            cortes = txtcortes.getText() + "C";
            cortesdesc = txtcortes.getText() + " Cortes ";
        } else {
            cortes = "";
            cortesdesc = "";
        }

        //Criar código/descrição
        codigo = "B" + materiaPrimaChar + " Diam. " + txtd1.getText() + " " + cortes + riarray[0] + materiaPrimaChar + raio + revchar + weldonarray[0] + iarray[0] + extra;
        desc = "Broca " + materiaPrima + "Diam. " + txtd1.getText() + "x" + txtl1.getText() + "x" + comptotal + "x" + diamfinal + " " + cortesdesc + tipocanal + riarray[1] + desbaste + raio + revtipo + weldonarray[1] + iarray[1] + extra;

        //Colocar código e descrição nos txt's
        txtcodigo.setText(codigo);
        txtdescricao.setText(desc);
    }

    public void gerarCodigoEscareador() {
        checagemGeral();

        //Criar código/descrição
        codigo = cbfamilia.getSelectedItem().toString() + "-" + txtd1.getText() + riarray[0] + raio + revchar + weldonarray[0] + iarray[0];
        desc = cbtipo.getSelectedItem().toString() + " " + materiaPrima + "Diam. " + txtd1.getText() + "x" + txtl1.getText() + " Frontal " + cbfamilia.getSelectedItem().toString().substring(4, 6) + "º " + riarray[1] + desbaste + raio + revtipo + weldonarray[1] + iarray[1];

        //Colocar código e descrição nos txt's
        txtcodigo.setText(codigo);
        txtdescricao.setText(desc);
    }

    public void gerarCodigoEscareadorEspecial() {
        checagemGeral();

        //Criar código/descrição
        codigo = cbfamilia.getSelectedItem().toString() + "-" + txtfrontal.getText() + "-" + txtd1.getText() + riarray[0] + raio + revchar + weldonarray[0] + iarray[0];
        desc = cbtipo.getSelectedItem().toString() + " " + materiaPrima + "Diam. " + txtd1.getText() + "x" + txtl1.getText() + " Frontal " + txtfrontal.getText() + "º " + riarray[1] + desbaste + raio + revtipo + weldonarray[1] + iarray[1];

        //Colocar código e descrição nos txt's
        txtcodigo.setText(codigo);
        txtdescricao.setText(desc);
    }

    public void gerarcodigoalargador() {
        checagemGeral();

        //Identificar família da fresa e criar descrição
        int selection = cbfamilia.getSelectedIndex();
        switch (selection) {
            case 1://2111
                ferr = "Alargador 2111 ";
                break;
            case 2://212
                ferr = "Alargador 212 ";
                break;
            default:
                break;
        }

        //Criar código/descrição
        codigo = cbfamilia.getSelectedItem().toString() + "-" + txtd1.getText() + tipoCanalChar + riarray[0] + materiaPrimaChar + raio + revchar + weldonarray[0] + iarray[0] + extra;
        desc = ferr + materiaPrima + txtd1.getText() + "x" + txtl1.getText() + "x" + txtl2.getText() + "x" + txtd2.getText() + tipocanal + raio + revtipo + riarray[1] + desbaste + weldonarray[1] + iarray[1];

        //Colocar código e descrição nos txt's
        txtcodigo.setText(codigo);
        txtdescricao.setText(desc);
    }

    public void gerarcodigoalargadorespecial() {
        checagemGeral();

        //Identificar família da fresa e criar descrição
        int selection = cbfamilia.getSelectedIndex();
        switch (selection) {
            case 1://2111
                ferr = "Alargador 2111 ";
                break;
            case 2://212
                ferr = "Alargador 212 ";
                break;
            default:
                ferr = "Alargador ";
                break;
        }

        //Criar código/descrição
        codigo = cbfamilia.getSelectedItem().toString() + "-" + txtd1.getText() + tipoCanalChar + riarray[0] + materiaPrimaChar + raio + revchar + weldonarray[0] + iarray[0] + extra;
        desc = ferr + materiaPrima + txtd1.getText() + "x" + txtl1.getText() + "x" + comptotal + "x" + diamfinal + tipocanal + raio + revtipo + riarray[1] + desbaste + weldonarray[1] + iarray[1];

        //Colocar código e descrição nos txt's
        txtcodigo.setText(codigo);
        txtdescricao.setText(desc);
    }

    public void gerarcodigolima() {
        checagemGeral();

        //Identificar família da fresa e criar descrição
        int selection = cbfamilia.getSelectedIndex();
        switch (selection) {
            case 14:
                ferr = "Rebarbador " + materiaPrima + cbfamilia.getSelectedItem().toString() + "-";
                break;
            default:
                ferr = "Lima Rotativa " + materiaPrima + cbfamilia.getSelectedItem().toString() + "-";
                break;
        }

        String codigo = "";

        switch (cbfamilia.getSelectedIndex()) {
            case 1:
                codigo = "Cilíndrica";
                break;
            case 2:
                codigo = "Cilíndrica Corte Frontal";
                break;
            case 3:
                codigo = "Cilíndrica Esférica";
                break;
            case 4:
                codigo = "Esférica";
                break;
            case 5:
                codigo = "Oval";
                break;
            case 6:
                codigo = "Árvore Esférica";
                break;
            case 7:
                codigo = "Árvore Aguda";
                break;
            case 8:
                codigo = "Labareda";
                break;
            case 9:
                codigo = "Cônica 60º";
                break;
            case 10:
                codigo = "Cônica 90º";
                break;
            case 11:
                codigo = "Cônica Esférica";
                break;
            case 12:
                codigo = "Cônica";
                break;
            case 13:
                codigo = "Cônica Invertida";
                break;
            case 14:
                codigo = "Rebarbador";
                break;
        }

        //Criar código/descrição
        codigo = codigo + " " + txtd1.getText() + "M " + tipoCanalChar + riarray[0] + materiaPrimaChar + raio + revchar + weldonarray[0] + iarray[0] + extra;
        desc = ferr + txtd1.getText() + "x" + txtl1.getText() + "x" + txtd2.getText() + "x" + txtl2.getText() + tipocanal + raio + revtipo + riarray[1] + desbaste + weldonarray[1] + iarray[1];

        //Colocar código e descrição nos txt's
        txtcodigo.setText(codigo);
        txtdescricao.setText(desc);
    }

    public void gerarcodigolimaespecial() {
        checagemGeral();

        //Identificar família da fresa e criar descrição
        int selection = cbfamilia.getSelectedIndex();
        switch (selection) {
            case 14://2111
                ferr = "Rebarbador " + materiaPrima + cbfamilia.getSelectedItem().toString();
                break;
            default:
                ferr = "Lima Rotativa " + materiaPrima + cbfamilia.getSelectedItem().toString();
                break;
        }

        //Criar código/descrição
        codigo = cbfamilia.getSelectedItem().toString() + "-" + txtd1.getText() + tipoCanalChar + riarray[0] + materiaPrimaChar + raio + revchar + weldonarray[0] + iarray[0] + extra;
        desc = ferr + txtd1.getText() + "x" + txtl1.getText() + "x" + diamfinal + "x" + comptotal + tipocanal + raio + revtipo + riarray[1] + desbaste + weldonarray[1] + iarray[1];

        //Colocar código e descrição nos txt's
        txtcodigo.setText(codigo);
        txtdescricao.setText(desc);
    }

    public static void transformarDiam(String diam, JTextField txt) {
        if (!diam.equals("")) {
            if (!diam.contains(",")) {
                Numeros.SetarTextoNumeroEmFloat(diam, txt);
            }
        }
    }

    public static void checarCaracteres() {
        int codigo = txtcodigo.getText().length();
        int descricao = txtdescricao.getText().length();

        if (codigo > 60) {
            lblcodigoerro.setVisible(true);
        } else {
            lblcodigoerro.setVisible(false);
        }

        if (descricao > 110) {
            lbldescricaoerro.setVisible(true);
        } else {
            lbldescricaoerro.setVisible(false);
        }
    }

    public static void travartxt() {
//        for (int i = 0; i < paneldiam.getComponentCount(); i++) {
//            Component c = paneldiam.getComponent(i);
//            if (c instanceof JTextField) {
//                c.setEnabled(false);
//            }
//        }
    }

    public static void txtfresa() {
//        //String para procura de texto no nome
//        String nome = "fresa";
//
//        //Abrir campos do paneldiam
//        for (int i = 0; i < 10; i++) {
//            Component c = paneldiam.getComponent(i);
//            String name = c.getName();
//            if (name != null && name.contains(nome)) {
//                c.setEnabled(true);
//            }
//        }
//
//        //Abrir campos do panelcomp
//        for (int i = 0; i < 10; i++) {
//            Component c = panelcomp.getComponent(i);
//            String name = c.getName();
//            if (name != null && name.contains(nome)) {
//                c.setEnabled(true);
//            }
//        }
//
//        //Abrir campos do paneltxtdados
//        for (int i = 0; i < paneldadostxt.getComponentCount(); i++) {
//            Component c = paneldadostxt.getComponent(i);
//            String name = c.getName();
//            if (name != null && name.contains(nome)) {
//                c.setEnabled(true);
//            }
//        }
    }

    public static void txtfresaespecial() {
//        //String para procura de texto no nome
//        String nome = "fe";
//
//        //Abrir campos do paneldiam
//        for (int i = 0; i < 10; i++) {
//            Component c = paneldiam.getComponent(i);
//            String name = c.getName();
//            if (name != null && name.contains(nome)) {
//                c.setEnabled(true);
//            }
//        }
//
//        //Abrir campos do panelcomp
//        for (int i = 0; i < 10; i++) {
//            Component c = panelcomp.getComponent(i);
//            String name = c.getName();
//            if (name != null && name.contains(nome)) {
//                c.setEnabled(true);
//            }
//        }
//
//        //Abrir campos do paneltxtdados
//        for (int i = 0; i < paneldadostxt.getComponentCount(); i++) {
//            Component c = paneldadostxt.getComponent(i);
//            String name = c.getName();
//            if (name != null && name.contains(nome)) {
//                c.setEnabled(true);
//            }
//        }
    }

    public static void txtbroca() {
//        //String para procura de texto no nome
//        String nome = "broca";
//
//        //Abrir campos do paneldiam
//        for (int i = 0; i < 10; i++) {
//            Component c = paneldiam.getComponent(i);
//            String name = c.getName();
//            if (name != null && name.contains(nome)) {
//                c.setEnabled(true);
//            }
//        }
//
//        //Abrir campos do panelcomp
//        for (int i = 0; i < 10; i++) {
//            Component c = panelcomp.getComponent(i);
//            String name = c.getName();
//            if (name != null && name.contains(nome)) {
//                c.setEnabled(true);
//            }
//        }
//
//        //Abrir campos do paneltxtdados
//        for (int i = 0; i < paneldadostxt.getComponentCount(); i++) {
//            Component c = paneldadostxt.getComponent(i);
//            String name = c.getName();
//            if (name != null && name.contains(nome)) {
//                c.setEnabled(true);
//            }
//        }
    }

    public static void txtbrocaespecial() {
//        //String para procura de texto no nome
//        String nome = "be";
//
//        //Abrir campos do paneldiam
//        for (int i = 0; i < 10; i++) {
//            Component c = paneldiam.getComponent(i);
//            String name = c.getName();
//            if (name != null && name.contains(nome)) {
//                c.setEnabled(true);
//            }
//        }
//
//        //Abrir campos do panelcomp
//        for (int i = 0; i < 10; i++) {
//            Component c = panelcomp.getComponent(i);
//            String name = c.getName();
//            if (name != null && name.contains(nome)) {
//                c.setEnabled(true);
//            }
//        }
//
//        //Abrir campos do paneltxtdados
//        for (int i = 0; i < paneldadostxt.getComponentCount(); i++) {
//            Component c = paneldadostxt.getComponent(i);
//            String name = c.getName();
//            if (name != null && name.contains(nome)) {
//                c.setEnabled(true);
//            }
//        }
    }

    public static void zeraCampos() {
        idmaterial = 0;

        txtcodigo.setText("");
        lblcodigoerro.setVisible(false);
        txtdescricao.setText("");
        lbldescricaoerro.setVisible(false);

        DefaultTableModel modelObs = (DefaultTableModel) tableobs.getModel();
        modelObs.setNumRows(0);

        DefaultTableModel modelDoc = (DefaultTableModel) tabledocumentos.getModel();
        modelDoc.setNumRows(0);

        DefaultTableModel modelDesc = (DefaultTableModel) tabledesccli.getModel();
        modelDesc.setNumRows(0);

        DefaultTableModel modelMov = (DefaultTableModel) tableMovimentacao.getModel();
        modelMov.setNumRows(0);

        txtestoque.setText("");
        txtLocal.setText("");
        txtestoqueminimo.setText("");
        txtQtdOp.setText("");

        cbtipo.setSelectedIndex(0);
        cbfamilia.setSelectedIndex(0);
        cbtamanho.setSelectedIndex(0);
        txtcortes.setText("");
        cbtopo.setSelectedIndex(0);
        cbcanal.setSelectedIndex(0);
        txtextra.setText("");

        txtd1.setText("");
        txtd2.setText("");
        txtd3.setText("");
        txtd4.setText("");
        txtd5.setText("");
        txtl1.setText("");
        txtl2.setText("");
        txtl3.setText("");
        txtl4.setText("");
        txtl5.setText("");

        txtmaterialdeorigem.setText("");

        checkrevestimento.setSelected(false);
        cbrevestimento.setSelectedIndex(0);
        checkraio.setSelected(false);
        txtraio.setText("");
        checkimportado.setSelected(false);
        checkweldon.setSelected(false);
        checkri.setSelected(false);
        checkDesbaste.setSelected(false);
        radioVazio.setSelected(true);

        txtTolD1.setText("");
        txtTolD2.setText("");
        txtTolD3.setText("");
        txtTolD4.setText("");
        txtTolD5.setText("");
        txthelice.setText("");
        txtnucleo.setText("");
        txtconcavidade.setText("");
        txtaliviotopo1.setText("");
        txtaliviotopo2.setText("");
        txtalivio1.setText("");
        txtalivio2.setText("");
        txtespfilete.setText("");
        txtagressividade.setText("");
        txtfrontal.setText("");
    }

    public static void revestimento() {
        if (checkrevestimento.isSelected()) {
            cbrevestimento.setEnabled(true);
        } else {
            cbrevestimento.setEnabled(false);
            cbrevestimento.setSelectedIndex(0);
        }
    }

    public static void raio() {
        if (checkraio.isSelected()) {
            txtraio.setEnabled(true);
        } else {
            txtraio.setEnabled(false);
            txtraio.setText("");
        }
    }

    private void gerarCodigo() {
        int selection = cbtipo.getSelectedIndex();

        switch (selection) {
            case 1://Fresa
                gerarcodigofresa();
                break;
            case 2://Fresa Especial
                gerarcodigofresaespecial();
                break;
            case 3://Broca
                gerarcodigobroca();
                break;
            case 4://Broca Especial
                gerarCodigoBrocaEspecial();
                break;
            case 5://Escareador
                gerarCodigoEscareador();
                break;
            case 6://Escareador Especial
                gerarCodigoEscareadorEspecial();
                break;
            case 7://Alargador
                gerarcodigoalargador();
                break;
            case 8://Alargador Especial
                gerarcodigoalargadorespecial();
                break;
            case 9://Lima
                gerarcodigolima();
                break;
            case 10://Lima Especial
                gerarcodigolimaespecial();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Escolha um tipo de ferramenta primeiro!");
                break;
        }

        checarCaracteres();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GroupRevestimento = new javax.swing.ButtonGroup();
        GroupTamanho = new javax.swing.ButtonGroup();
        GroupMateriaPrima = new javax.swing.ButtonGroup();
        tabmateriais = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        txtpesquisa = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        cbstatus = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablemateriaisvendas = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtcodigo = new javax.swing.JTextField();
        txtdescricao = new javax.swing.JTextField();
        txtstatus = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        lblcodigoerro = new javax.swing.JLabel();
        lbldescricaoerro = new javax.swing.JLabel();
        tabmaterialinfo = new javax.swing.JTabbedPane();
        panelobs = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableobs = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        paneldados = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbtipo = new javax.swing.JComboBox<>();
        paneldiam = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtd1 = new javax.swing.JTextField();
        txtd2 = new javax.swing.JTextField();
        txtd3 = new javax.swing.JTextField();
        txtd4 = new javax.swing.JTextField();
        txtd5 = new javax.swing.JTextField();
        panelcomp = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtl1 = new javax.swing.JTextField();
        txtl2 = new javax.swing.JTextField();
        txtl3 = new javax.swing.JTextField();
        txtl4 = new javax.swing.JTextField();
        txtl5 = new javax.swing.JTextField();
        cbfamilia = new javax.swing.JComboBox<>();
        lblfamilia = new javax.swing.JLabel();
        lbltamanho = new javax.swing.JLabel();
        cbtamanho = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        checkrevestimento = new javax.swing.JCheckBox();
        cbrevestimento = new javax.swing.JComboBox<>();
        checkraio = new javax.swing.JCheckBox();
        txtraio = new javax.swing.JTextField();
        checkri = new javax.swing.JCheckBox();
        checkweldon = new javax.swing.JCheckBox();
        checkimportado = new javax.swing.JCheckBox();
        radioMD = new javax.swing.JRadioButton();
        radioHSS = new javax.swing.JRadioButton();
        radioVazio = new javax.swing.JRadioButton();
        checkDesbaste = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        paneldadostxt = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txthelice = new javax.swing.JTextField();
        txtnucleo = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtconcavidade = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtaliviotopo1 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtaliviotopo2 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtalivio1 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txtalivio2 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtespfilete = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtagressividade = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtfrontal = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtTolD1 = new javax.swing.JTextField();
        txtTolD2 = new javax.swing.JTextField();
        txtTolD3 = new javax.swing.JTextField();
        txtTolD4 = new javax.swing.JTextField();
        txtTolD5 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        lblicon = new javax.swing.JLabel();
        lblcortes = new javax.swing.JLabel();
        txtcortes = new javax.swing.JTextField();
        lbltopo = new javax.swing.JLabel();
        cbtopo = new javax.swing.JComboBox<>();
        panelorigem = new javax.swing.JPanel();
        txtmaterialdeorigem = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        cbcanal = new javax.swing.JComboBox<>();
        lblextra = new javax.swing.JLabel();
        txtextra = new javax.swing.JTextField();
        paneldocs = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabledocumentos = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        paneldesc = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabledesccli = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        panelmov = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableMovimentacao = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtestoque = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtestoqueminimo = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtLocal = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtQtdOp = new javax.swing.JTextField();
        btnMovManual = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Materiais de Venda");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));

        txtpesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtpesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtpesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));

        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Desativado", "Todos" }));
        cbstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbstatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbstatus, 0, 170, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        tablemateriaisvendas.setAutoCreateRowSorter(true);
        tablemateriaisvendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Código", "Descrição", "Estoque", "Local", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablemateriaisvendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablemateriaisvendasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablemateriaisvendas);
        if (tablemateriaisvendas.getColumnModel().getColumnCount() > 0) {
            tablemateriaisvendas.getColumnModel().getColumn(0).setMinWidth(0);
            tablemateriaisvendas.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablemateriaisvendas.getColumnModel().getColumn(0).setMaxWidth(0);
            tablemateriaisvendas.getColumnModel().getColumn(1).setMinWidth(180);
            tablemateriaisvendas.getColumnModel().getColumn(1).setPreferredWidth(180);
            tablemateriaisvendas.getColumnModel().getColumn(1).setMaxWidth(180);
            tablemateriaisvendas.getColumnModel().getColumn(3).setMinWidth(70);
            tablemateriaisvendas.getColumnModel().getColumn(3).setPreferredWidth(70);
            tablemateriaisvendas.getColumnModel().getColumn(3).setMaxWidth(70);
            tablemateriaisvendas.getColumnModel().getColumn(4).setMinWidth(110);
            tablemateriaisvendas.getColumnModel().getColumn(4).setPreferredWidth(110);
            tablemateriaisvendas.getColumnModel().getColumn(4).setMaxWidth(110);
            tablemateriaisvendas.getColumnModel().getColumn(5).setMinWidth(150);
            tablemateriaisvendas.getColumnModel().getColumn(5).setPreferredWidth(150);
            tablemateriaisvendas.getColumnModel().getColumn(5).setMaxWidth(150);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 503, Short.MAX_VALUE)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        tabmateriais.addTab("Materiais Cadastrados", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Material"));

        jLabel1.setText("Código");

        jLabel2.setText("Descrição");

        txtcodigo.setEditable(false);
        txtcodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcodigoKeyReleased(evt);
            }
        });

        txtdescricao.setEditable(false);
        txtdescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdescricaoKeyReleased(evt);
            }
        });

        txtstatus.setEditable(false);

        jLabel15.setText("Status");

        lblcodigoerro.setForeground(new java.awt.Color(255, 51, 51));
        lblcodigoerro.setText("Código muito longo!");

        lbldescricaoerro.setForeground(new java.awt.Color(255, 51, 51));
        lbldescricaoerro.setText("Descrição muito longa!");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 724, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbldescricaoerro)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblcodigoerro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(lblcodigoerro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtdescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbldescricaoerro))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableobs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Data", "Usuário", "Observação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableobs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableobsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableobs);
        if (tableobs.getColumnModel().getColumnCount() > 0) {
            tableobs.getColumnModel().getColumn(0).setMinWidth(0);
            tableobs.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableobs.getColumnModel().getColumn(0).setMaxWidth(0);
            tableobs.getColumnModel().getColumn(1).setMinWidth(35);
            tableobs.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableobs.getColumnModel().getColumn(1).setMaxWidth(35);
            tableobs.getColumnModel().getColumn(2).setMinWidth(100);
            tableobs.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableobs.getColumnModel().getColumn(2).setMaxWidth(100);
            tableobs.getColumnModel().getColumn(3).setMinWidth(200);
            tableobs.getColumnModel().getColumn(3).setPreferredWidth(200);
            tableobs.getColumnModel().getColumn(3).setMaxWidth(200);
        }

        jButton1.setText("Adicionar Observação");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton10.setText("Excluir Observação");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelobsLayout = new javax.swing.GroupLayout(panelobs);
        panelobs.setLayout(panelobsLayout);
        panelobsLayout.setHorizontalGroup(
            panelobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1285, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelobsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        panelobsLayout.setVerticalGroup(
            panelobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelobsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton10))
                .addContainerGap())
        );

        tabmaterialinfo.addTab("Observações", panelobs);

        jLabel3.setText("Tipo");

        cbtipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Fresa", "Fresa Especial", "Broca", "Broca Especial", "Escareador", "Escareador Especial", "Alargador", "Alargador Especial", "Lima", "Lima Especial", "Ferramenta Especial" }));
        cbtipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbtipoActionPerformed(evt);
            }
        });

        paneldiam.setBackground(new java.awt.Color(255, 255, 255));
        paneldiam.setBorder(javax.swing.BorderFactory.createTitledBorder("Diâmetro"));

        jLabel4.setText("D1");

        jLabel5.setText("D2");

        jLabel6.setText("D3");

        jLabel7.setText("D4");

        jLabel8.setText("D5");

        txtd1.setEnabled(false);
        txtd1.setName("fresa-broca-fe-be"); // NOI18N
        txtd1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtd1FocusLost(evt);
            }
        });

        txtd2.setEnabled(false);
        txtd2.setName("fresa-broca-fe-be"); // NOI18N
        txtd2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtd2FocusLost(evt);
            }
        });

        txtd3.setEnabled(false);
        txtd3.setName("fe-be"); // NOI18N
        txtd3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtd3FocusLost(evt);
            }
        });

        txtd4.setEnabled(false);
        txtd4.setName("fe-be"); // NOI18N
        txtd4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtd4FocusLost(evt);
            }
        });

        txtd5.setEnabled(false);
        txtd5.setName("fe-be"); // NOI18N
        txtd5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtd5FocusLost(evt);
            }
        });

        javax.swing.GroupLayout paneldiamLayout = new javax.swing.GroupLayout(paneldiam);
        paneldiam.setLayout(paneldiamLayout);
        paneldiamLayout.setHorizontalGroup(
            paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldiamLayout.createSequentialGroup()
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd5))
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd4))
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd3))
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paneldiamLayout.setVerticalGroup(
            paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldiamLayout.createSequentialGroup()
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtd3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtd4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtd5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelcomp.setBackground(new java.awt.Color(255, 255, 255));
        panelcomp.setBorder(javax.swing.BorderFactory.createTitledBorder("Comprimento"));

        jLabel9.setText("L1");

        jLabel10.setText("L2");

        jLabel11.setText("L3");

        jLabel12.setText("L4");

        jLabel13.setText("L5");

        txtl1.setEnabled(false);
        txtl1.setName("fresa-broca-fe-be"); // NOI18N
        txtl1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtl1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtl1FocusLost(evt);
            }
        });

        txtl2.setEnabled(false);
        txtl2.setName("fresa-broca-fe-be"); // NOI18N

        txtl3.setEnabled(false);
        txtl3.setName("fe-be"); // NOI18N

        txtl4.setEnabled(false);
        txtl4.setName("fe-be"); // NOI18N

        txtl5.setEnabled(false);
        txtl5.setName("fe-be"); // NOI18N

        javax.swing.GroupLayout panelcompLayout = new javax.swing.GroupLayout(panelcomp);
        panelcomp.setLayout(panelcompLayout);
        panelcompLayout.setHorizontalGroup(
            panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcompLayout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl5))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcompLayout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl4))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcompLayout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl3))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcompLayout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl2))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcompLayout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelcompLayout.setVerticalGroup(
            panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcompLayout.createSequentialGroup()
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtl3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtl4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtl5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        cbfamilia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cbfamilia.setEnabled(false);
        cbfamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbfamiliaActionPerformed(evt);
            }
        });

        lblfamilia.setText("Família");

        lbltamanho.setText("Tamanho");

        cbtamanho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cbtamanho.setEnabled(false);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Especificações"));

        checkrevestimento.setBackground(new java.awt.Color(255, 255, 255));
        checkrevestimento.setText("Revestimento");
        checkrevestimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkrevestimentoActionPerformed(evt);
            }
        });

        cbrevestimento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "TIN", "TIALN", "ALCRONA" }));
        cbrevestimento.setEnabled(false);

        checkraio.setText("Raio");
        checkraio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkraioActionPerformed(evt);
            }
        });

        txtraio.setEnabled(false);
        txtraio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtraioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtraioFocusLost(evt);
            }
        });

        checkri.setText("Refrigeração Interna");

        checkweldon.setText("Weldon");

        checkimportado.setText("Importado");

        GroupMateriaPrima.add(radioMD);
        radioMD.setText("Metal Duro");

        GroupMateriaPrima.add(radioHSS);
        radioHSS.setText("HSS");

        GroupMateriaPrima.add(radioVazio);
        radioVazio.setText("Vazio");

        checkDesbaste.setText("Desbaste");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(checkrevestimento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbrevestimento, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(checkraio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtraio, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkimportado))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(checkweldon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkri))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(radioMD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioHSS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioVazio))
                    .addComponent(checkDesbaste))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkrevestimento)
                    .addComponent(cbrevestimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkraio)
                    .addComponent(txtraio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkimportado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkweldon)
                    .addComponent(checkri))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkDesbaste)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioMD)
                    .addComponent(radioHSS)
                    .addComponent(radioVazio))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados para Construção"));

        jLabel14.setText("Hélice");

        txthelice.setName("fresa-broca-fe-be"); // NOI18N
        txthelice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtheliceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtheliceFocusLost(evt);
            }
        });

        txtnucleo.setName("fresa-broca-fe-be"); // NOI18N
        txtnucleo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtnucleoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtnucleoFocusLost(evt);
            }
        });

        jLabel25.setText("Núcleo");

        txtconcavidade.setName("fresa-fe"); // NOI18N
        txtconcavidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtconcavidadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtconcavidadeFocusLost(evt);
            }
        });

        jLabel36.setText("Concavidade");

        txtaliviotopo1.setName("fresa-fe"); // NOI18N
        txtaliviotopo1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtaliviotopo1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtaliviotopo1FocusLost(evt);
            }
        });

        jLabel37.setText("Alívio de Topo Primário");

        txtaliviotopo2.setName("fresa-fe"); // NOI18N
        txtaliviotopo2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtaliviotopo2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtaliviotopo2FocusLost(evt);
            }
        });

        jLabel38.setText("Alívio de Topo Secundário");

        txtalivio1.setName("fresa-fe"); // NOI18N
        txtalivio1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtalivio1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtalivio1FocusLost(evt);
            }
        });

        jLabel39.setText("Alívio Primário");

        txtalivio2.setName("fresa-fe"); // NOI18N
        txtalivio2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtalivio2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtalivio2FocusLost(evt);
            }
        });

        jLabel40.setText("Alívio Secundário");

        txtespfilete.setName("fresa-fe"); // NOI18N
        txtespfilete.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtespfileteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtespfileteFocusLost(evt);
            }
        });

        jLabel41.setText("Espessura de Filete");

        txtagressividade.setName("fresa-broca-fe-be"); // NOI18N
        txtagressividade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtagressividadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtagressividadeFocusLost(evt);
            }
        });

        jLabel42.setText("Agressividade");

        txtfrontal.setName("broca-be"); // NOI18N
        txtfrontal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtfrontalFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtfrontalFocusLost(evt);
            }
        });

        jLabel16.setText("Frontal");

        jLabel21.setText("Tolerância D1");

        jLabel22.setText("Tolerância D2");

        jLabel23.setText("Tolerância D3");

        jLabel24.setText("Tolerância D4");

        jLabel26.setText("Tolerância D5");

        javax.swing.GroupLayout paneldadostxtLayout = new javax.swing.GroupLayout(paneldadostxt);
        paneldadostxt.setLayout(paneldadostxtLayout);
        paneldadostxtLayout.setHorizontalGroup(
            paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldadostxtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel25)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42)
                    .addComponent(jLabel16)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTolD5, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(txtTolD4)
                    .addComponent(txtTolD3)
                    .addComponent(txtTolD2)
                    .addComponent(txtTolD1)
                    .addGroup(paneldadostxtLayout.createSequentialGroup()
                        .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txthelice, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(txtagressividade)
                            .addComponent(txtespfilete)
                            .addComponent(txtalivio2)
                            .addComponent(txtalivio1)
                            .addComponent(txtaliviotopo2)
                            .addComponent(txtaliviotopo1)
                            .addComponent(txtconcavidade, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtnucleo, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(txtfrontal))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        paneldadostxtLayout.setVerticalGroup(
            paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneldadostxtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTolD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTolD2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTolD3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTolD4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTolD5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txthelice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnucleo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtconcavidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtaliviotopo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtaliviotopo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txtalivio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txtalivio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtespfilete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtagressividade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfrontal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane6.setViewportView(paneldadostxt);

        lblicon.setBackground(new java.awt.Color(255, 255, 255));
        lblicon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblicon.setOpaque(true);
        jScrollPane7.setViewportView(lblicon);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane7)
        );

        lblcortes.setText("Cortes");

        txtcortes.setEnabled(false);
        txtcortes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcortesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcortesFocusLost(evt);
            }
        });
        txtcortes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcortesKeyReleased(evt);
            }
        });

        lbltopo.setText("Topo");

        cbtopo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Reto", "Esférico", "Toroidal" }));
        cbtopo.setEnabled(false);
        cbtopo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbtopoActionPerformed(evt);
            }
        });

        panelorigem.setBackground(new java.awt.Color(255, 255, 255));
        panelorigem.setBorder(javax.swing.BorderFactory.createTitledBorder("Material de Origem"));

        txtmaterialdeorigem.setEditable(false);

        jButton8.setText("Procurar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelorigemLayout = new javax.swing.GroupLayout(panelorigem);
        panelorigem.setLayout(panelorigemLayout);
        panelorigemLayout.setHorizontalGroup(
            panelorigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelorigemLayout.createSequentialGroup()
                .addGroup(panelorigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtmaterialdeorigem)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelorigemLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8)))
                .addContainerGap())
        );
        panelorigemLayout.setVerticalGroup(
            panelorigemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelorigemLayout.createSequentialGroup()
                .addComponent(txtmaterialdeorigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addContainerGap())
        );

        jLabel19.setText("Canal");

        cbcanal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cbcanal.setEnabled(false);

        lblextra.setText("Identificação Extra");

        txtextra.setEnabled(false);

        javax.swing.GroupLayout paneldadosLayout = new javax.swing.GroupLayout(paneldados);
        paneldados.setLayout(paneldadosLayout);
        paneldadosLayout.setHorizontalGroup(
            paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneldadosLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblfamilia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbfamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbltamanho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbtamanho, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblcortes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcortes, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbltopo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbtopo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbcanal, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblextra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtextra, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(paneldadosLayout.createSequentialGroup()
                        .addGroup(paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(paneldadosLayout.createSequentialGroup()
                                .addComponent(paneldiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelcomp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(panelorigem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        paneldadosLayout.setVerticalGroup(
            paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbfamilia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblfamilia)
                    .addComponent(lbltamanho)
                    .addComponent(cbtamanho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblcortes)
                    .addComponent(txtcortes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltopo)
                    .addComponent(cbtopo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(cbcanal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblextra)
                    .addComponent(txtextra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(paneldadosLayout.createSequentialGroup()
                        .addGroup(paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paneldadosLayout.createSequentialGroup()
                                .addGroup(paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(paneldiam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(panelcomp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelorigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 28, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tabmaterialinfo.addTab("Dados do Material", paneldados);

        tabledocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Descrição", "Local", "Local Original"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabledocumentos);
        if (tabledocumentos.getColumnModel().getColumnCount() > 0) {
            tabledocumentos.getColumnModel().getColumn(0).setMinWidth(0);
            tabledocumentos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabledocumentos.getColumnModel().getColumn(0).setMaxWidth(0);
            tabledocumentos.getColumnModel().getColumn(1).setMinWidth(40);
            tabledocumentos.getColumnModel().getColumn(1).setPreferredWidth(40);
            tabledocumentos.getColumnModel().getColumn(1).setMaxWidth(40);
            tabledocumentos.getColumnModel().getColumn(4).setMinWidth(0);
            tabledocumentos.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabledocumentos.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jButton2.setText("Adicionar Documento");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Excluir Documento");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneldocsLayout = new javax.swing.GroupLayout(paneldocs);
        paneldocs.setLayout(paneldocsLayout);
        paneldocsLayout.setHorizontalGroup(
            paneldocsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldocsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldocsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1285, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneldocsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        paneldocsLayout.setVerticalGroup(
            paneldocsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldocsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldocsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        tabmaterialinfo.addTab("Documentos", paneldocs);

        tabledesccli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Cliente", "Código", "Descrição"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabledesccli);
        if (tabledesccli.getColumnModel().getColumnCount() > 0) {
            tabledesccli.getColumnModel().getColumn(0).setMinWidth(0);
            tabledesccli.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabledesccli.getColumnModel().getColumn(0).setMaxWidth(0);
            tabledesccli.getColumnModel().getColumn(1).setMinWidth(40);
            tabledesccli.getColumnModel().getColumn(1).setPreferredWidth(40);
            tabledesccli.getColumnModel().getColumn(1).setMaxWidth(40);
            tabledesccli.getColumnModel().getColumn(2).setMinWidth(150);
            tabledesccli.getColumnModel().getColumn(2).setPreferredWidth(150);
            tabledesccli.getColumnModel().getColumn(2).setMaxWidth(150);
            tabledesccli.getColumnModel().getColumn(3).setMinWidth(150);
            tabledesccli.getColumnModel().getColumn(3).setPreferredWidth(150);
            tabledesccli.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        jButton4.setText("Adicionar Descrição");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Excluir Descrição");

        javax.swing.GroupLayout paneldescLayout = new javax.swing.GroupLayout(paneldesc);
        paneldesc.setLayout(paneldescLayout);
        paneldescLayout.setHorizontalGroup(
            paneldescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldescLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1285, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneldescLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        paneldescLayout.setVerticalGroup(
            paneldescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldescLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap())
        );

        tabmaterialinfo.addTab("Descrição Por Cliente", paneldesc);

        panelmov.setBackground(new java.awt.Color(255, 255, 255));

        tableMovimentacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Tipo de Movimentação", "Estoque Anterior", "Qtde Movimentada", "Novo Estoque", "Usuário"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tableMovimentacao);
        if (tableMovimentacao.getColumnModel().getColumnCount() > 0) {
            tableMovimentacao.getColumnModel().getColumn(0).setMinWidth(100);
            tableMovimentacao.getColumnModel().getColumn(0).setPreferredWidth(100);
            tableMovimentacao.getColumnModel().getColumn(0).setMaxWidth(100);
            tableMovimentacao.getColumnModel().getColumn(1).setMinWidth(150);
            tableMovimentacao.getColumnModel().getColumn(1).setPreferredWidth(150);
            tableMovimentacao.getColumnModel().getColumn(1).setMaxWidth(150);
            tableMovimentacao.getColumnModel().getColumn(2).setMinWidth(150);
            tableMovimentacao.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableMovimentacao.getColumnModel().getColumn(2).setMaxWidth(150);
            tableMovimentacao.getColumnModel().getColumn(3).setMinWidth(150);
            tableMovimentacao.getColumnModel().getColumn(3).setPreferredWidth(150);
            tableMovimentacao.getColumnModel().getColumn(3).setMaxWidth(150);
            tableMovimentacao.getColumnModel().getColumn(4).setMinWidth(150);
            tableMovimentacao.getColumnModel().getColumn(4).setPreferredWidth(150);
            tableMovimentacao.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Estoque"));

        jLabel17.setText("Atual");

        txtestoque.setEnabled(false);

        jLabel18.setText("Mínimo");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtestoqueminimo)
                    .addComponent(txtestoque, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtestoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtestoqueminimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Local"));

        txtLocal.setEditable(false);

        jButton7.setText("Procurar Local");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtLocal)
            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txtLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("OP"));

        jLabel20.setText("Quantidade Mínima");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel20)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtQtdOp)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQtdOp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnMovManual.setText("Lançar Contagem Manual");
        btnMovManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovManualActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelmovLayout = new javax.swing.GroupLayout(panelmov);
        panelmov.setLayout(panelmovLayout);
        panelmovLayout.setHorizontalGroup(
            panelmovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelmovLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelmovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelmovLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnMovManual))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1135, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelmovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelmovLayout.setVerticalGroup(
            panelmovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelmovLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelmovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelmovLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 48, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMovManual))
        );

        tabmaterialinfo.addTab("Movimentação", panelmov);

        jButton9.setText("Salvar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton6.setText("Novo Produto");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabmaterialinfo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabmaterialinfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        tabmateriais.addTab("Material", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabmateriais)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabmateriais)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbtipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtipoActionPerformed
        int selection = cbtipo.getSelectedIndex();

        cbexcluir();
        cbfamiliaincluir();

        switch (selection) {
            case 1://Fresa

                //Itens para habilitar/desabilitar
                cbfamilia.setEnabled(true);
                cbtamanho.setEnabled(true);
                cbtopo.setEnabled(false);
                cbcanal.setEnabled(false);
                txtcortes.setEnabled(false);
                txtcodigo.setEditable(false);
                txtdescricao.setEditable(false);
                txtextra.setEnabled(false);

                //Habilitar/desabilitar diam.
                txtd1.setEnabled(true);
                txtd2.setEnabled(true);
                txtd3.setEnabled(false);
                txtd4.setEnabled(false);
                txtd5.setEnabled(false);

                //habilitar/desabilitar comp.
                txtl1.setEnabled(true);
                txtl2.setEnabled(true);
                txtl3.setEnabled(false);
                txtl4.setEnabled(false);
                txtl5.setEnabled(false);

                cbtamanho.addItem("C");
                cbtamanho.addItem("S");
                cbtamanho.addItem("L");
                cbtamanho.addItem("X");

                break;
            case 2://Fresa Especial

                //Itens para habilitar/desabilitar
                cbfamilia.setEnabled(true);
                cbtamanho.setEnabled(false);
                cbtopo.setEnabled(true);
                cbcanal.setEnabled(true);
                txtcortes.setEnabled(true);
                txtcodigo.setEditable(false);
                txtdescricao.setEditable(false);
                txtextra.setEnabled(true);

                //Habilitar txts diam/comp
                for (int i = 0; i < paneldiam.getComponentCount(); i++) {
                    paneldiam.getComponent(i).setEnabled(true);
                }
                for (int i = 0; i < panelcomp.getComponentCount(); i++) {
                    panelcomp.getComponent(i).setEnabled(true);
                }

                cbtamanho.addItem("C");
                cbtamanho.addItem("S");
                cbtamanho.addItem("L");
                cbtamanho.addItem("X");
                cbcanal.addItem("À Esquerda");
                cbcanal.addItem("Reto");

                break;
            case 3://Broca

                //Itens para habilitar/desabilitar
                cbfamilia.setEnabled(true);
                cbtamanho.setEnabled(false);
                cbtopo.setEnabled(false);
                cbcanal.setEnabled(false);
                txtcortes.setEnabled(false);
                txtcodigo.setEditable(false);
                txtdescricao.setEditable(false);
                txtextra.setEnabled(false);

                //Habilitar/desabilitar diam.
                txtd1.setEnabled(true);
                txtd2.setEnabled(true);
                txtd3.setEnabled(false);
                txtd4.setEnabled(false);
                txtd5.setEnabled(false);

                //habilitar/desabilitar comp.
                txtl1.setEnabled(true);
                txtl2.setEnabled(true);
                txtl3.setEnabled(false);
                txtl4.setEnabled(false);
                txtl5.setEnabled(false);

                break;
            case 4://Broca Especial

                //Itens para habilitar/desabilitar
                cbfamilia.setEnabled(true);
                cbtamanho.setEnabled(false);
                cbtopo.setEnabled(false);
                cbcanal.setEnabled(true);
                txtcortes.setEnabled(true);
                txtcodigo.setEditable(false);
                txtdescricao.setEditable(false);
                txtextra.setEnabled(true);

                //Habilitar txts diam/comp
                for (int i = 0; i < paneldiam.getComponentCount(); i++) {
                    paneldiam.getComponent(i).setEnabled(true);
                }
                for (int i = 0; i < panelcomp.getComponentCount(); i++) {
                    panelcomp.getComponent(i).setEnabled(true);
                }

                cbcanal.addItem("À Esquerda");
                cbcanal.addItem("Reto");

                break;
            case 5://Escareador
                //Itens para habilitar/desabilitar
                cbfamilia.setEnabled(true);
                cbtamanho.setEnabled(false);
                cbtopo.setEnabled(false);
                cbcanal.setEnabled(true);
                txtcortes.setEnabled(false);
                txtcodigo.setEditable(false);
                txtdescricao.setEditable(false);
                txtextra.setEnabled(false);

                //Habilitar/desabilitar diam.
                txtd1.setEnabled(true);
                txtd2.setEnabled(false);
                txtd3.setEnabled(false);
                txtd4.setEnabled(false);
                txtd5.setEnabled(false);

                //habilitar/desabilitar comp.
                txtl1.setEnabled(true);
                txtl2.setEnabled(false);
                txtl3.setEnabled(false);
                txtl4.setEnabled(false);
                txtl5.setEnabled(false);

                cbcanal.addItem("À Esquerda");
                cbcanal.addItem("Reto");

                break;
            case 6://Escareador Especial
                //Itens para habilitar/desabilitar
                cbfamilia.setEnabled(true);
                cbtamanho.setEnabled(false);
                cbtopo.setEnabled(false);
                cbcanal.setEnabled(true);
                txtcortes.setEnabled(true);
                txtcodigo.setEditable(false);
                txtdescricao.setEditable(false);
                txtextra.setEnabled(true);

                //Habilitar txts diam/comp
                for (int i = 0; i < paneldiam.getComponentCount(); i++) {
                    paneldiam.getComponent(i).setEnabled(true);
                }
                for (int i = 0; i < panelcomp.getComponentCount(); i++) {
                    panelcomp.getComponent(i).setEnabled(true);
                }

                cbcanal.addItem("Helicoidal");
                cbcanal.addItem("Reto");

                break;
            case 7://Alargador
                //Itens para habilitar/desabilitar
                cbfamilia.setEnabled(true);
                cbtamanho.setEnabled(false);
                cbtopo.setEnabled(false);
                cbcanal.setEnabled(true);
                txtcortes.setEnabled(false);
                txtcodigo.setEditable(false);
                txtdescricao.setEditable(false);
                txtextra.setEnabled(false);

                //Habilitar/desabilitar diam.
                txtd1.setEnabled(true);
                txtd2.setEnabled(true);
                txtd3.setEnabled(false);
                txtd4.setEnabled(false);
                txtd5.setEnabled(false);

                //habilitar/desabilitar comp.
                txtl1.setEnabled(true);
                txtl2.setEnabled(true);
                txtl3.setEnabled(false);
                txtl4.setEnabled(false);
                txtl5.setEnabled(false);

                cbcanal.addItem("À Esquerda");
                cbcanal.addItem("Reto");

                break;
            case 8://Alargador Especial

                //Itens para habilitar/desabilitar
                cbfamilia.setEnabled(true);
                cbtamanho.setEnabled(false);
                cbtopo.setEnabled(false);
                cbcanal.setEnabled(true);
                txtcortes.setEnabled(true);
                txtcodigo.setEditable(false);
                txtdescricao.setEditable(false);
                txtextra.setEnabled(true);

                //Habilitar txts diam/comp
                for (int i = 0; i < paneldiam.getComponentCount(); i++) {
                    paneldiam.getComponent(i).setEnabled(true);
                }
                for (int i = 0; i < panelcomp.getComponentCount(); i++) {
                    panelcomp.getComponent(i).setEnabled(true);
                }

                cbcanal.addItem("À Esquerda");
                cbcanal.addItem("Reto");

                break;
            case 9://Lima

                //Itens para habilitar/desabilitar
                cbfamilia.setEnabled(true);
                cbtamanho.setEnabled(true);
                cbtopo.setEnabled(false);
                cbcanal.setEnabled(true);
                txtcortes.setEnabled(false);
                txtcodigo.setEditable(false);
                txtdescricao.setEditable(false);
                txtextra.setEnabled(false);

                //Habilitar/desabilitar diam.
                txtd1.setEnabled(true);
                txtd2.setEnabled(true);
                txtd3.setEnabled(false);
                txtd4.setEnabled(false);
                txtd5.setEnabled(false);

                //habilitar/desabilitar comp.
                txtl1.setEnabled(true);
                txtl2.setEnabled(true);
                txtl3.setEnabled(false);
                txtl4.setEnabled(false);
                txtl5.setEnabled(false);

                cbtamanho.addItem("L3");
                cbtamanho.addItem("L6");
                cbcanal.addItem("DC");
                cbcanal.addItem("FC");

                break;
            case 10://Lima Especial

                //Itens para habilitar/desabilitar
                cbfamilia.setEnabled(true);
                cbtamanho.setEnabled(false);
                cbtopo.setEnabled(false);
                cbcanal.setEnabled(true);
                txtcortes.setEnabled(false);
                txtcodigo.setEditable(false);
                txtdescricao.setEditable(false);
                txtextra.setEnabled(true);

                //Habilitar txts diam/comp
                for (int i = 0; i < paneldiam.getComponentCount(); i++) {
                    paneldiam.getComponent(i).setEnabled(true);
                }
                for (int i = 0; i < panelcomp.getComponentCount(); i++) {
                    panelcomp.getComponent(i).setEnabled(true);
                }

                cbtamanho.addItem("L3");
                cbtamanho.addItem("L6");
                cbcanal.addItem("DC");
                cbcanal.addItem("FC");

                break;
            case 11://Ferramenta Especial

                //Itens para habilitar/desabilitar
                cbfamilia.setEnabled(true);
                cbtamanho.setEnabled(false);
                cbtopo.setEnabled(false);
                cbcanal.setEnabled(true);
                txtcortes.setEnabled(false);
                txtcodigo.setEditable(true);
                txtdescricao.setEditable(true);
                txtextra.setEnabled(true);

                //Habilitar txts diam/comp
                for (int i = 0; i < paneldiam.getComponentCount(); i++) {
                    paneldiam.getComponent(i).setEnabled(true);
                }
                for (int i = 0; i < panelcomp.getComponentCount(); i++) {
                    panelcomp.getComponent(i).setEnabled(true);
                }

                txtcodigo.requestFocus();

                cbcanal.addItem("À Esquerda");
                cbcanal.addItem("Reto");

                break;
            default:
                //Itens para habilitar/desabilitar
                cbfamilia.setEnabled(false);
                cbtamanho.setEnabled(false);
                cbtopo.setEnabled(false);
                cbcanal.setEnabled(false);
                txtcortes.setEnabled(false);
                txtcodigo.setEditable(false);
                txtdescricao.setEditable(false);
                txtextra.setEnabled(false);

                //Habilitar txts diam/comp
                for (int i = 0; i < paneldiam.getComponentCount(); i++) {
                    paneldiam.getComponent(i).setEnabled(false);
                }
                for (int i = 0; i < panelcomp.getComponentCount(); i++) {
                    panelcomp.getComponent(i).setEnabled(false);
                }
                break;
        }
    }//GEN-LAST:event_cbtipoActionPerformed

    private void checkrevestimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkrevestimentoActionPerformed
        revestimento();
    }//GEN-LAST:event_checkrevestimentoActionPerformed

    private void checkraioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkraioActionPerformed
        raio();
    }//GEN-LAST:event_checkraioActionPerformed

    private void cbfamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbfamiliaActionPerformed
        if (cbfamilia.getSelectedIndex() != 0) {
            txtcortes.setEnabled(false);
            txtcortes.setText("");
            cbtopo.setSelectedIndex(0);
            cbtopo.setEnabled(false);
        } else {
            txtcortes.setEnabled(true);
            cbtopo.setEnabled(true);
        }
    }//GEN-LAST:event_cbfamiliaActionPerformed

    private void cbtopoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtopoActionPerformed
        if (cbtopo.getSelectedIndex() != 0) {
            cbfamilia.setSelectedIndex(0);
            txtcortes.setEnabled(true);
        }
    }//GEN-LAST:event_cbtopoActionPerformed

    private void txtconcavidadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtconcavidadeFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/concavidade.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtconcavidadeFocusGained

    private void txtconcavidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtconcavidadeFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtconcavidadeFocusLost

    private void txtaliviotopo1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo1FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/aliviotopo1.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtaliviotopo1FocusGained

    private void txtaliviotopo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo2FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/aliviotopo2.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtaliviotopo2FocusGained

    private void txtaliviotopo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo1FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtaliviotopo1FocusLost

    private void txtaliviotopo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo2FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtaliviotopo2FocusLost

    private void txtnucleoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnucleoFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/nucleo.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtnucleoFocusGained

    private void txtnucleoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnucleoFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtnucleoFocusLost

    private void txtheliceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtheliceFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/helice.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtheliceFocusGained

    private void txtalivio1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtalivio1FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/alivio1.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtalivio1FocusGained

    private void txtheliceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtheliceFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtheliceFocusLost

    private void txtalivio1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtalivio1FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtalivio1FocusLost

    private void txtalivio2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtalivio2FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/alivio2.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtalivio2FocusGained

    private void txtalivio2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtalivio2FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtalivio2FocusLost

    private void txtespfileteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtespfileteFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/espfilete.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtespfileteFocusGained

    private void txtespfileteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtespfileteFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtespfileteFocusLost

    private void txtagressividadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtagressividadeFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/agressividade.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtagressividadeFocusGained

    private void txtagressividadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtagressividadeFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtagressividadeFocusLost

    private void txtcortesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcortesFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/numcortes.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtcortesFocusGained

    private void txtcortesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcortesFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtcortesFocusLost

    private void txtraioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtraioFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/raio.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtraioFocusGained

    private void txtraioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtraioFocusLost
        lblicon.setIcon(null);
        Numeros.SetarTextoNumeroEmFloat(txtraio.getText(), txtraio);
    }//GEN-LAST:event_txtraioFocusLost

    private void txtl1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtl1FocusGained
        String resource;
        int ind = cbtipo.getSelectedIndex();
        switch (ind) {
            case 1:
                resource = "/Images/l1.png";
                break;
            case 2:
                resource = "/Images/l1.png";
                break;
            case 3:
                resource = "/Images/l1broca.png";
                break;
            case 4:
                resource = "/Images/l1broca.png";
                break;
            default:
                resource = "/Images/l1.png";
                break;
        }
        ImageIcon i = new ImageIcon(getClass().getResource(resource));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtl1FocusGained

    private void txtl1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtl1FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtl1FocusLost

    private void txtfrontalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfrontalFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/frontal.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtfrontalFocusGained

    private void txtfrontalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfrontalFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtfrontalFocusLost

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        String[] options = new String[2];
        options[0] = "Produto";
        options[1] = "Matéria-prima";

        int escolha;

        escolha = JOptionPane.showInternalOptionDialog(null, "Qual origem?", "Escolher Origem", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, iconable);

        if (escolha == 0) {
            ProcurarMaterial pmv = new ProcurarMaterial(this.getClass().getSimpleName());
            Telas.AparecerTela(pmv);
        } else {
            ProcurarMateriaPrima pmp = new ProcurarMateriaPrima(this.getClass().getSimpleName());
            Telas.AparecerTela(pmp);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtcodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigoKeyReleased
        checarCaracteres();
    }//GEN-LAST:event_txtcodigoKeyReleased

    private void txtdescricaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescricaoKeyReleased
        checarCaracteres();
    }//GEN-LAST:event_txtdescricaoKeyReleased

    private void txtd1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtd1FocusLost
        transformarDiam(txtd1.getText(), txtd1);
    }//GEN-LAST:event_txtd1FocusLost

    private void txtd2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtd2FocusLost
        transformarDiam(txtd2.getText(), txtd2);
    }//GEN-LAST:event_txtd2FocusLost

    private void txtd3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtd3FocusLost
        transformarDiam(txtd3.getText(), txtd3);
    }//GEN-LAST:event_txtd3FocusLost

    private void txtd4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtd4FocusLost
        transformarDiam(txtd4.getText(), txtd4);
    }//GEN-LAST:event_txtd4FocusLost

    private void txtd5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtd5FocusLost
        transformarDiam(txtd5.getText(), txtd5);
    }//GEN-LAST:event_txtd5FocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AdicionarObs ao = new AdicionarObs(this.getClass().getSimpleName());
        Telas.AparecerTela(ao);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int indexTipo = cbtipo.getSelectedIndex();
        if (indexTipo == 1 && (cbfamilia.getSelectedIndex() == 0 || cbtamanho.getSelectedIndex() == 0)) {
            JOptionPane.showMessageDialog(null, "Escolha uma família e um tamanho.");
        } else if ((indexTipo == 2 || indexTipo == 4 || indexTipo == 6 || indexTipo == 8) && (cbfamilia.getSelectedIndex() == 0 && txtcortes.getText().equals(""))) {
            JOptionPane.showMessageDialog(null, "Escolha uma família ou coloque um número de cortes.");
        } else if ((indexTipo == 3 || indexTipo == 5 || indexTipo == 9 || indexTipo == 10) && cbfamilia.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Escolha uma família.");
            cbfamilia.showPopup();
        } else if (indexTipo == 7 && (cbfamilia.getSelectedIndex() == 0 || cbcanal.getSelectedIndex() == 0)) {
            JOptionPane.showMessageDialog(null, "Escolha uma família e um tipo de canal.");
        } else if (indexTipo == 8 && cbcanal.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Escolha um tipo de canal.");
            cbcanal.showPopup();
        } else if (indexTipo == 11 && (txtcodigo.getText().equals("") || txtdescricao.getText().equals(""))) {
            JOptionPane.showMessageDialog(null, "Digite um código e uma descrição.");
        } else {
            gerarCodigo();

            if (lblcodigoerro.isVisible()) {
                JOptionPane.showMessageDialog(null, "Código muito longo.");
            } else if (lbldescricaoerro.isVisible()) {
                JOptionPane.showMessageDialog(null, "Descrição muito longa.");
            } else if (txtLocal.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Nenhum local de armazenagem selecionado.");

                tabmaterialinfo.setSelectedIndex(4);

                ProcurarLocal pl = new ProcurarLocal(this.getClass().getSimpleName());
                Telas.AparecerTela(pl);
            } else if (txtestoqueminimo.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Sem valor de estoque mínimo.");

                tabmaterialinfo.setSelectedIndex(4);

                txtestoqueminimo.requestFocus();
                tabmaterialinfo.setSelectedIndex(4);
            } else if (txtQtdOp.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Sem valor de Quantidade Mínima na OP.");

                tabmaterialinfo.setSelectedIndex(4);

                txtQtdOp.requestFocus();
            } else if (!radioMD.isSelected() && !radioHSS.isSelected()) {
                JOptionPane.showMessageDialog(null, "Escolha se é Metal Duro ou HSS.");

                tabmaterialinfo.setSelectedIndex(1);

                tabmaterialinfo.setSelectedIndex(1);
            } else if (cbtipo.getSelectedIndex() == 6 && txtfrontal.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Digite um valor para o frontal.");

                tabmaterialinfo.setSelectedIndex(1);

                txtfrontal.requestFocus();
            } else {
                if (idmaterial == 0) {
                    if (vmd.produtoCadastrado(txtcodigo.getText())) {
                        JOptionPane.showMessageDialog(null, "Produto já cadastrado.");
                    } else {
                        try {
                            //Criar material
                            vmd.create(
                                    txtcodigo.getText(),
                                    txtdescricao.getText(),
                                    0,
                                    Double.parseDouble(txtestoqueminimo.getText()),
                                    "Ativo",
                                    txtLocal.getText(),
                                    txtd1.getText(),
                                    txtd2.getText(),
                                    txtd3.getText(),
                                    txtd4.getText(),
                                    txtd5.getText(),
                                    txtl1.getText(),
                                    txtl2.getText(),
                                    txtl3.getText(),
                                    txtl4.getText(),
                                    txtl5.getText(),
                                    txtmaterialdeorigem.getText(),
                                    cbrevestimento.getSelectedItem().toString(),
                                    txtraio.getText(),
                                    checkimportado.isSelected(),
                                    checkweldon.isSelected(),
                                    checkri.isSelected(),
                                    radioMD.isSelected(),
                                    radioHSS.isSelected(),
                                    cbtipo.getSelectedItem().toString(),
                                    cbfamilia.getSelectedItem().toString(),
                                    cbtamanho.getSelectedItem().toString(),
                                    txtcortes.getText(),
                                    cbtopo.getSelectedItem().toString(),
                                    cbcanal.getSelectedItem().toString(),
                                    txtextra.getText(),
                                    txthelice.getText(),
                                    txtnucleo.getText(),
                                    txtconcavidade.getText(),
                                    txtaliviotopo1.getText(),
                                    txtaliviotopo2.getText(),
                                    txtalivio1.getText(),
                                    txtalivio2.getText(),
                                    txtespfilete.getText(),
                                    txtagressividade.getText(),
                                    txtfrontal.getText(),
                                    Double.parseDouble(txtQtdOp.getText()),
                                    checkDesbaste.isSelected(),
                                    txtTolD1.getText(),
                                    txtTolD2.getText(),
                                    txtTolD3.getText(),
                                    txtTolD4.getText(),
                                    txtTolD5.getText()
                            );

                            //Recuperar id do material
                            idmaterial = vmd.readcreated();

                            //Criar observações
                            for (int i = 0; i < tableobs.getRowCount(); i++) {
                                vmod.create(idmaterial, Dates.CriarDataCurtaDBComDataExistente(tableobs.getValueAt(i, 2).toString()), tableobs.getValueAt(i, 3).toString(), tableobs.getValueAt(i, 4).toString());
                            }

                            //Criar documentos
                            for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
                                File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                                File folder = new File("Q:/MIKE_ERP/mat_ven_arq/" + idmaterial);
                                File filecopy = new File(folder + "/" + fileoriginal.getName());

                                folder.mkdirs();
                                try {
                                    Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                                } catch (IOException e) {
                                    String msg = "Erro ao criar documento em rede.";
                                    JOptionPane.showMessageDialog(null, msg);

                                    new Thread() {
                                        @Override
                                        public void run() {
                                            SendEmail.EnviarErro2(msg, e);
                                        }
                                    }.start();
                                }
                                vmdd.create(idmaterial, tabledocumentos.getValueAt(i, 2).toString(), filecopy.toString());
                            }

                            //Criar códigos por clientes
                            for (int i = 0; i < tabledesccli.getRowCount(); i++) {
                                vmccd.create(idmaterial, tabledesccli.getValueAt(i, 2).toString(), tabledesccli.getValueAt(i, 3).toString(), tabledesccli.getValueAt(i, 4).toString());
                            }

                            //Criar movimentação do material
                            vmmd.create(idmaterial, 0, 0, 0, "Criação", Dates.CriarDataCurtaDBSemDataExistente(), Session.nome);

                            JOptionPane.showMessageDialog(null, "Material de Venda criado com sucesso!");
                        } catch (SQLException e) {
                            String msg = "Erro ao criar Material de Venda.";
                            JOptionPane.showMessageDialog(null, msg);

                            new Thread() {
                                @Override
                                public void run() {
                                    SendEmail.EnviarErro2(msg, e);
                                }
                            }.start();
                        }
                    }
                } else {
                    try {
                        //Atualizar material
                        vmd.update(
                                txtcodigo.getText(),
                                txtdescricao.getText(),
                                Double.parseDouble(txtestoqueminimo.getText()),
                                txtLocal.getText(),
                                txtd1.getText(),
                                txtd2.getText(),
                                txtd3.getText(),
                                txtd4.getText(),
                                txtd5.getText(),
                                txtl1.getText(),
                                txtl2.getText(),
                                txtl3.getText(),
                                txtl4.getText(),
                                txtl5.getText(),
                                txtmaterialdeorigem.getText(),
                                cbrevestimento.getSelectedItem().toString(),
                                txtraio.getText(),
                                checkimportado.isSelected(),
                                checkweldon.isSelected(),
                                checkri.isSelected(),
                                radioMD.isSelected(),
                                radioHSS.isSelected(),
                                cbtipo.getSelectedItem().toString(),
                                cbfamilia.getSelectedItem().toString(),
                                cbtamanho.getSelectedItem().toString(),
                                txtcortes.getText(),
                                cbtopo.getSelectedItem().toString(),
                                cbcanal.getSelectedItem().toString(),
                                txtextra.getText(),
                                txthelice.getText(),
                                txtnucleo.getText(),
                                txtconcavidade.getText(),
                                txtaliviotopo1.getText(),
                                txtaliviotopo2.getText(),
                                txtalivio1.getText(),
                                txtalivio2.getText(),
                                txtespfilete.getText(),
                                txtagressividade.getText(),
                                txtfrontal.getText(),
                                Double.parseDouble(txtQtdOp.getText()),
                                checkDesbaste.isSelected(),
                                txtTolD1.getText(),
                                txtTolD2.getText(),
                                txtTolD3.getText(),
                                txtTolD4.getText(),
                                txtTolD5.getText(),
                                idmaterial
                        );

                        //Criar observações que não existiam antes
                        for (int i = 0; i < tableobs.getRowCount(); i++) {
                            if (tableobs.getValueAt(i, 0).equals("")) {
                                vmod.create(idmaterial, Dates.CriarDataCurtaDBComDataExistente(tableobs.getValueAt(i, 2).toString()), tableobs.getValueAt(i, 3).toString(), tableobs.getValueAt(i, 4).toString());
                            }
                        }

                        //Criar documentos que não existiam antes
                        for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
                            if (tabledocumentos.getValueAt(i, 0).equals("")) {
                                File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                                File folder = new File("Q:/MIKE_ERP/mat_ven_arq/" + idmaterial);
                                File filecopy = new File(folder + "/" + fileoriginal.getName());

                                folder.mkdirs();
                                try {
                                    Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                                } catch (IOException ex) {
                                    Logger.getLogger(VM.class.getName()).log(Level.SEVERE, null, ex);
                                    JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");
                                    try {
                                        SendEmail.EnviarErro(ex.toString());
                                        JOptionPane.showMessageDialog(rootPane, "E-mail com erro enviado com sucesso!");
                                    } catch (HeadlessException hex) {
                                        JOptionPane.showMessageDialog(rootPane, "Erro!\n" + hex);
                                    } catch (AWTException | IOException ex1) {
                                        Logger.getLogger(DocumentosPedidoServico.class.getName()).log(Level.SEVERE, null, ex1);
                                    }
                                }
                                vmdd.create(idmaterial, tabledocumentos.getValueAt(i, 2).toString(), filecopy.toString());
                            }
                        }

                        //Criar códigos por clientes que não existiam antes
                        for (int i = 0; i < tabledesccli.getRowCount(); i++) {
                            if (tabledesccli.getValueAt(i, 0).equals("")) {
                                vmccd.create(idmaterial, tabledesccli.getValueAt(i, 2).toString(), tabledesccli.getValueAt(i, 3).toString(), tabledesccli.getValueAt(i, 4).toString());
                            }
                        }

                        JOptionPane.showMessageDialog(null, "Material de Venda atualizado com sucesso.");
                    } catch (SQLException e) {
                        String frase = "Erro ao atualizar Material de Venda.";
                        JOptionPane.showMessageDialog(null, frase);

                        new Thread() {
                            @Override
                            public void run() {
                                SendEmail.EnviarErro2(frase, e);
                            }
                        }.start();
                    }
                }
                lerMaterial(idmaterial);
            }
            readProdutos();
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void tablemateriaisvendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablemateriaisvendasMouseClicked
        if (evt.getClickCount() == 2) {
            idmaterial = Integer.parseInt(tablemateriaisvendas.getValueAt(tablemateriaisvendas.getSelectedRow(), 0).toString());

            lerMaterial(idmaterial);
        }
    }//GEN-LAST:event_tablemateriaisvendasMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ProcurarDocumento pd = new ProcurarDocumento(this.getClass().getSimpleName());
        Telas.AparecerTela(pd);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int numtrue = 0;

        for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
            if (tabledocumentos.getValueAt(i, 1).equals(true)) {
                numtrue++;
            }
        }
        if (numtrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum documento selecionado.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir o(s) documento(s) selecionado(s)?", "Excluir Documento", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
                    if (tabledocumentos.getValueAt(i, 1).equals(true)) {
                        vmdd.delete(Integer.parseInt(tabledocumentos.getValueAt(i, 0).toString()));
                    }
                }
                readDocs(idmaterial);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        CodigoPorCliente cpc = new CodigoPorCliente();
        Telas.AparecerTela(cpc);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int resp = JOptionPane.showConfirmDialog(null, "Deseja cadastrar um novo produto?", "Cadastrar Novo", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            zeraCampos();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        ProcurarLocal pl = new ProcurarLocal(this.getClass().getSimpleName());
        Telas.AparecerTela(pl);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int numTrue = 0;
        for (int i = 0; i < tableobs.getRowCount(); i++) {
            if (tableobs.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum comentário selecionado.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir as observações selecionadas?", "Excluir Observações", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                for (int i = 0; i < tableobs.getRowCount(); i++) {
                    if (tableobs.getValueAt(i, 1).equals(true)) {
                        vmod.delete(Integer.parseInt(tableobs.getValueAt(i, 0).toString()));
                    }
                }
            }
            readObs(idmaterial);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void tableobsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableobsMouseClicked
        if (tableobs.getSelectedColumn() == 1) {
            if (!tableobs.getValueAt(tableobs.getSelectedRow(), 3).equals(Session.nome)) {
                JOptionPane.showMessageDialog(null, "Usuário logado diferente do usuário que fez a observação.");
                tableobs.setValueAt(false, tableobs.getSelectedRow(), 1);
            }
        }
    }//GEN-LAST:event_tableobsMouseClicked

    private void btnMovManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovManualActionPerformed
        if (idmaterial == 0) {
            JOptionPane.showMessageDialog(null, "Selecione um produto primeiro.");
        } else {
            double estoqueAtual = vmd.readEstoque(idmaterial);
            double qtdMovimentada = 0;
            try {
                qtdMovimentada = Double.parseDouble(JOptionPane.showInputDialog(null, "Qual a quantidade a ser colocada no estoque?", "Quantidade Movimentada", JOptionPane.YES_NO_OPTION));
            } catch (NullPointerException e) {
                String msg = "Erro.";
                JOptionPane.showMessageDialog(null, msg);

                new Thread() {
                    @Override
                    public void run() {
                        SendEmail.EnviarErro2(msg, e);
                    }
                }.start();
            }

            double saldo = estoqueAtual + qtdMovimentada;

            try {
                vmd.updateEstoque(saldo, idmaterial);
                vmmd.create(idmaterial, estoqueAtual, qtdMovimentada, saldo, "Contagem Manual", Dates.CriarDataCurtaDBSemDataExistente(), Session.nome);

                JOptionPane.showMessageDialog(null, "Movimentação criada com sucesso.");

                lerMaterial(idmaterial);
            } catch (SQLException e) {
                String msg = "Erro ao criar movimentação do Material.";
                JOptionPane.showMessageDialog(null, msg);

                new Thread() {
                    @Override
                    public void run() {
                        SendEmail.EnviarErro2(msg, e);
                    }
                }.start();
            }
        }
    }//GEN-LAST:event_btnMovManualActionPerformed

    private void txtpesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpesquisaKeyReleased
        readProdutos();
    }//GEN-LAST:event_txtpesquisaKeyReleased

    private void cbstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbstatusActionPerformed
        readProdutos();
    }//GEN-LAST:event_cbstatusActionPerformed

    private void txtcortesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcortesKeyReleased
        if (txtcortes.getText().length() > 0) {
            cbfamilia.setEnabled(false);
        } else {
            cbfamilia.setEnabled(true);
        }
    }//GEN-LAST:event_txtcortesKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.ButtonGroup GroupMateriaPrima;
    public javax.swing.ButtonGroup GroupRevestimento;
    public javax.swing.ButtonGroup GroupTamanho;
    public javax.swing.JButton btnMovManual;
    public static javax.swing.JComboBox<String> cbcanal;
    public static javax.swing.JComboBox<String> cbfamilia;
    public static javax.swing.JComboBox<String> cbrevestimento;
    public static javax.swing.JComboBox<String> cbstatus;
    public static javax.swing.JComboBox<String> cbtamanho;
    public static javax.swing.JComboBox<String> cbtipo;
    public static javax.swing.JComboBox<String> cbtopo;
    public static javax.swing.JCheckBox checkDesbaste;
    public static javax.swing.JCheckBox checkimportado;
    public static javax.swing.JCheckBox checkraio;
    public static javax.swing.JCheckBox checkrevestimento;
    public static javax.swing.JCheckBox checkri;
    public static javax.swing.JCheckBox checkweldon;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton10;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton5;
    public javax.swing.JButton jButton6;
    public javax.swing.JButton jButton7;
    public javax.swing.JButton jButton8;
    public javax.swing.JButton jButton9;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    public javax.swing.JLabel jLabel17;
    public javax.swing.JLabel jLabel18;
    public javax.swing.JLabel jLabel19;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel20;
    public javax.swing.JLabel jLabel21;
    public javax.swing.JLabel jLabel22;
    public javax.swing.JLabel jLabel23;
    public javax.swing.JLabel jLabel24;
    public javax.swing.JLabel jLabel25;
    public javax.swing.JLabel jLabel26;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel36;
    public javax.swing.JLabel jLabel37;
    public javax.swing.JLabel jLabel38;
    public javax.swing.JLabel jLabel39;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel40;
    public javax.swing.JLabel jLabel41;
    public javax.swing.JLabel jLabel42;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel11;
    public javax.swing.JPanel jPanel12;
    public javax.swing.JPanel jPanel18;
    public javax.swing.JPanel jPanel19;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JScrollPane jScrollPane6;
    public javax.swing.JScrollPane jScrollPane7;
    public static javax.swing.JLabel lblcodigoerro;
    public static javax.swing.JLabel lblcortes;
    public static javax.swing.JLabel lbldescricaoerro;
    public javax.swing.JLabel lblextra;
    public static javax.swing.JLabel lblfamilia;
    public javax.swing.JLabel lblicon;
    public static javax.swing.JLabel lbltamanho;
    public static javax.swing.JLabel lbltopo;
    public static javax.swing.JPanel panelcomp;
    public javax.swing.JPanel paneldados;
    public static javax.swing.JPanel paneldadostxt;
    public javax.swing.JPanel paneldesc;
    public static javax.swing.JPanel paneldiam;
    public javax.swing.JPanel paneldocs;
    public javax.swing.JPanel panelmov;
    public javax.swing.JPanel panelobs;
    public javax.swing.JPanel panelorigem;
    public static javax.swing.JRadioButton radioHSS;
    public static javax.swing.JRadioButton radioMD;
    public static javax.swing.JRadioButton radioVazio;
    public static javax.swing.JTable tableMovimentacao;
    public static javax.swing.JTable tabledesccli;
    public static javax.swing.JTable tabledocumentos;
    public static javax.swing.JTable tablemateriaisvendas;
    public static javax.swing.JTable tableobs;
    public static javax.swing.JTabbedPane tabmateriais;
    public static javax.swing.JTabbedPane tabmaterialinfo;
    public static javax.swing.JTextField txtLocal;
    public static javax.swing.JTextField txtQtdOp;
    public static javax.swing.JTextField txtTolD1;
    public static javax.swing.JTextField txtTolD2;
    public static javax.swing.JTextField txtTolD3;
    public static javax.swing.JTextField txtTolD4;
    public static javax.swing.JTextField txtTolD5;
    public static javax.swing.JTextField txtagressividade;
    public static javax.swing.JTextField txtalivio1;
    public static javax.swing.JTextField txtalivio2;
    public static javax.swing.JTextField txtaliviotopo1;
    public static javax.swing.JTextField txtaliviotopo2;
    public static javax.swing.JTextField txtcodigo;
    public static javax.swing.JTextField txtconcavidade;
    public static javax.swing.JTextField txtcortes;
    public static javax.swing.JTextField txtd1;
    public static javax.swing.JTextField txtd2;
    public static javax.swing.JTextField txtd3;
    public static javax.swing.JTextField txtd4;
    public static javax.swing.JTextField txtd5;
    public static javax.swing.JTextField txtdescricao;
    public static javax.swing.JTextField txtespfilete;
    public static javax.swing.JTextField txtestoque;
    public static javax.swing.JTextField txtestoqueminimo;
    public static javax.swing.JTextField txtextra;
    public static javax.swing.JTextField txtfrontal;
    public static javax.swing.JTextField txthelice;
    public static javax.swing.JTextField txtl1;
    public static javax.swing.JTextField txtl2;
    public static javax.swing.JTextField txtl3;
    public static javax.swing.JTextField txtl4;
    public static javax.swing.JTextField txtl5;
    public static javax.swing.JTextField txtmaterialdeorigem;
    public static javax.swing.JTextField txtnucleo;
    public static javax.swing.JTextField txtpesquisa;
    public static javax.swing.JTextField txtraio;
    public javax.swing.JTextField txtstatus;
    // End of variables declaration//GEN-END:variables
}
