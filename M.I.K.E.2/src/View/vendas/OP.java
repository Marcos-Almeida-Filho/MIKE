/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.vendas;

import Bean.F_UPBean;
import Bean.F_UP_HistBean;
import Connection.Session;
import DAO.F_UPDAO;
import DAO.F_UP_HistDAO;
import DAO.InsumosDAO;
import DAO.InsumosMovDAO;
import DAO.OPDAO;
import DAO.OPDocDAO;
import DAO.OPMPDAO;
import DAO.OPMedicoesDAO;
import DAO.OPObsDAO;
import DAO.OPProcessosDAO;
import DAO.ProcessosServicoDAO;
import DAO.ProcessosVendasDAO;
import DAO.ProcessosVendasMedicoesDAO;
import DAO.VendasMateriaisDAO;
import DAO.VendasMateriaisMovDAO;
import DAO.VendasPedidoItensDAO;
import Methods.Arquivos;
import Methods.Dates;
import Methods.SendEmail;
import Methods.Telas;
import View.Geral.AdicionarObs;
import View.Geral.ProcurarMaterial;
import View.Geral.ProcurarCliente;
import View.Geral.ProcurarDocumento;
import View.Geral.ProcurarPedido;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class OP extends javax.swing.JInternalFrame {

    static OPDAO od = new OPDAO();
    static OPProcessosDAO opd = new OPProcessosDAO();
    static OPMPDAO omd = new OPMPDAO();
    static OPDocDAO odd = new OPDocDAO();
    static OPObsDAO ood = new OPObsDAO();
    static OPMedicoesDAO omed = new OPMedicoesDAO();
    static F_UP_HistDAO fuhd = new F_UP_HistDAO();
    static VendasMateriaisDAO vmd = new VendasMateriaisDAO();
    static VendasMateriaisMovDAO vmmd = new VendasMateriaisMovDAO();
    static ProcessosServicoDAO psd = new ProcessosServicoDAO();
    static ProcessosVendasDAO pvd = new ProcessosVendasDAO();
    static ProcessosVendasMedicoesDAO pvmd = new ProcessosVendasMedicoesDAO();
    static F_UPDAO fud = new F_UPDAO();
    static InsumosDAO idao = new InsumosDAO();
    static InsumosMovDAO imdao = new InsumosMovDAO();
    static VendasPedidoItensDAO vpid = new VendasPedidoItensDAO();

    static boolean raio, importado, weldon, desbaste, ri, cs11, detalonado;

    static int idMaterial = 0;
    static int iP = 0;

    /**
     * Creates new form OPs
     */
    public OP() {
        initComponents();
        readOPs();
    }

    public static void readOPs() {
        String status = cbStatus.getSelectedItem().toString();
        DefaultTableModel modelOP = (DefaultTableModel) tableOP.getModel();
        modelOP.setNumRows(0);
        if (txtPesquisa.getText().equals("")) {
            switch (status) {
                case "Em Aberto":
                    od.readTodasOPsEmAberto().forEach(ob -> {
                        modelOP.addRow(new Object[]{
                            ob.getId(),
                            false,
                            ob.getOp(),
                            ob.getCliente(),
                            ob.getCodigo(),
                            Dates.TransformarDataCurtaDoDB(ob.getDataprevista()),
                            ob.getStatus()
                        });
                    });
                    break;
                case "Todos":
                    od.readTodasOPs().forEach(ob -> {
                        modelOP.addRow(new Object[]{
                            ob.getId(),
                            false,
                            ob.getOp(),
                            ob.getCliente(),
                            ob.getCodigo(),
                            Dates.TransformarDataCurtaDoDB(ob.getDataprevista()),
                            ob.getStatus()
                        });
                    });
                    break;
                default:
                    od.readOPPorStatus(status).forEach(ob -> {
                        modelOP.addRow(new Object[]{
                            ob.getId(),
                            false,
                            ob.getOp(),
                            ob.getCliente(),
                            ob.getCodigo(),
                            Dates.TransformarDataCurtaDoDB(ob.getDataprevista()),
                            ob.getStatus()
                        });
                    });
                    break;
            }
        } else {
            String pesquisa = txtPesquisa.getText();
            switch (status) {
                case "Em Aberto":
                    od.readTodasOPsEmAbertoPesquisa(pesquisa).forEach(ob -> {
                        modelOP.addRow(new Object[]{
                            ob.getId(),
                            false,
                            ob.getOp(),
                            ob.getCliente(),
                            ob.getCodigo(),
                            Dates.TransformarDataCurtaDoDB(ob.getDataprevista()),
                            ob.getStatus()
                        });
                    });
                    break;
                case "Todos":
                    od.readTodasOPsPesquisa(pesquisa).forEach(ob -> {
                        modelOP.addRow(new Object[]{
                            ob.getId(),
                            false,
                            ob.getOp(),
                            ob.getCliente(),
                            ob.getCodigo(),
                            Dates.TransformarDataCurtaDoDB(ob.getDataprevista()),
                            ob.getStatus()
                        });
                    });
                    break;
                default:
                    od.readOPPorStatusPesquisa(status, pesquisa).forEach(ob -> {
                        modelOP.addRow(new Object[]{
                            ob.getId(),
                            false,
                            ob.getOp(),
                            ob.getCliente(),
                            ob.getCodigo(),
                            Dates.TransformarDataCurtaDoDB(ob.getDataprevista()),
                            ob.getStatus()
                        });
                    });
                    break;
            }
        }
    }

    public static void escolherPrimeiroProcesso() {
        double qtdTotal = Double.parseDouble(OP.TxtQtde.getText().replace(",", "."));

        JComboBox cbProcessos = new JComboBox();

        pvd.readTodos().forEach(psb -> {
            cbProcessos.addItem(psb.getNome());
            iP++;
        });

        JOptionPane.showMessageDialog(null, cbProcessos, "Selecione o Primeiro Processo", JOptionPane.QUESTION_MESSAGE);

        for (int i = 0; i < OP.tableProcessos.getRowCount(); i++) {
            qtdTotal -= Double.parseDouble(OP.tableProcessos.getValueAt(i, 6).toString());
        }

        String processo = cbProcessos.getSelectedItem().toString();

        opd.create(OP.txtNumOP.getText(), processo, qtdTotal);

        int id = opd.idUltimoProcesso(OP.txtNumOP.getText(), processo);

        int id2 = pvd.idProcesso(processo);
        pvmd.readMedidas(id2).forEach(pvmb -> {
            omed.create(id, pvmb.getMedida(), "", "", "");
        });

        F_UPBean fub = new F_UPBean();
        fub.setProcesso(processo);
        fub.setOp(OP.txtNumOP.getText());

        //processo = ? WHERE op = ?
        fud.updateProcessoByOs(fub);

        //Criar novo histórico no F-UP
        F_UP_HistBean fuhb = new F_UP_HistBean();
        fuhb.setIdfup(fud.getId(OP.txtNumOP.getText()));
        fuhb.setProcesso(cbProcessos.getSelectedItem().toString());

        //idfup, processo
        fuhd.create(fuhb);
    }

    public static void lerOP(String op) {
        od.readOP(op).forEach(ob -> {
            idMaterial = ob.getIdmaterial();
            txtDav.setText(ob.getDav());
            txtCliente.setText(ob.getCliente());
            TxtCodigo.setText(ob.getCodigo());
            Dates.SetarDataJDateChooser(txtDataEntrega, ob.getDataprevista());
            txtStatus.setText(ob.getStatus());
            TxtDescricao.setText(ob.getDescricao());
            TxtQtde.setText(String.valueOf(ob.getQtd()).replace(".", ","));
            txtOk.setText(String.valueOf(ob.getQtdok()).replace(".", ","));
            txtMortas.setText(String.valueOf(ob.getQtdnaook()).replace(".", ","));
        });

        lerProcessos(op);

        lerDocs(op);

        lerObs(op);

        lerInspecoes(op);

        lerMP(op);

        lerMedidasMaterial(idMaterial);

        camposPorStatus();
    }

    public static void lerProcessos(String op) {
        DefaultTableModel modelProcessos = (DefaultTableModel) tableProcessos.getModel();
        modelProcessos.setNumRows(0);

        opd.readProcessos(op).forEach(opb -> {
            modelProcessos.addRow(new Object[]{
                opb.getId(),
                false,
                opb.getProcesso(),
                Dates.TransformarDataCompletaDoDB(opb.getDatainicio()),
                Dates.TransformarDataCompletaDoDB(opb.getDatafim()),
                opb.getQtdok(),
                opb.getQtdnaook(),
                opb.getUser(),
                opb.getOrdem(),
                opb.getQtddisponivel()
            });
        });
    }

    public static void lerMedidasMaterial(int idMaterial) {
        vmd.click(idMaterial).forEach(vmb -> {
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
            txtTolD1.setText(vmb.getTolD1());
            txtTolD2.setText(vmb.getTolD2());
            txtHelice.setText(vmb.getHelice());
            txtnucleo.setText(vmb.getNucleo());
            txtconcavidade.setText(vmb.getConcavidade());
            txtaliviotopo1.setText(vmb.getTopo1());
            txtaliviotopo2.setText(vmb.getTopo2());
            txtalivio1.setText(vmb.getAlivio1());
            txtalivio2.setText(vmb.getAlivio2());
            txtespfilete.setText(vmb.getFilete());
            txtagressividade.setText(vmb.getAgressividade());
            txtMPDados.setText(vmb.getMpDados());
            txtConicidade.setText(vmb.getConicidade());
            txtTipoFilete.setText(vmb.getTipoFilete());
            txtCostela.setText(vmb.getAlturaCostela());
            txtTipoRaio.setText(vmb.getTipoRaio());
            txtAnguloFrontal.setText(vmb.getAnguloFrontal());
            txtTipoFrontal.setText(vmb.getTipoFrontal());

            if (!vmb.getRaio().equals("")) {
                checkraio.setSelected(true);
                raio = true;
                txtraio.setText(vmb.getRaio());
            } else {
                checkraio.setSelected(false);
                raio = false;
                txtraio.setText("");
            }
            checkimportado.setSelected(vmb.isImportada());
            importado = vmb.isImportada();
            checkweldon.setSelected(vmb.isWeldon());
            weldon = vmb.isWeldon();
            checkri.setSelected(vmb.isRi());
            ri = vmb.isRi();
            checkDesbaste.setSelected(vmb.isDesbaste());
            desbaste = vmb.isDesbaste();
            checkCS11.setSelected(vmb.isCs11());
            cs11 = vmb.isCs11();
            checkDetalonado.setSelected(vmb.isDetalonado());
            detalonado = vmb.isDetalonado();
        });
    }

    public void checks() {
        checkraio.setSelected(raio);
        checkimportado.setSelected(importado);
        checkweldon.setSelected(weldon);
        checkri.setSelected(ri);
        checkDesbaste.setSelected(desbaste);
        checkCS11.setSelected(cs11);
        checkDetalonado.setSelected(detalonado);
    }

    public static void lerMP(String op) {
        DefaultTableModel modelMP = (DefaultTableModel) tableMP.getModel();

        modelMP.setNumRows(0);

        omd.readMP(op).forEach(omb -> {
            modelMP.addRow(new Object[]{
                omb.getId(),
                false,
                omb.getCodigo(),
                omb.getDescricao(),
                omb.getLote(),
                omb.getQtd(),
                omb.isBaixa(),
                omb.isInsumo(),
                omb.getIdInsumo()
            });
        });
    }

    public static void lerObs(String op) {
        DefaultTableModel modelObs = (DefaultTableModel) tableObs.getModel();

        modelObs.setNumRows(0);

        ood.readObs(op).forEach(oob -> {
            modelObs.addRow(new Object[]{
                oob.getId(),
                false,
                Dates.TransformarDataCurtaDoDB(oob.getData()),
                oob.getUsuario(),
                oob.getObs()
            });
        });
    }

    public static void lerInspecoes(String op) {
        DefaultTableModel modelIns = (DefaultTableModel) tableInspecao.getModel();

        modelIns.setNumRows(0);

        for (int i = 0; i < tableProcessos.getRowCount(); i++) {
            final String Processo = tableProcessos.getValueAt(i, 2).toString();
            omed.readMedicoes(Integer.parseInt(tableProcessos.getValueAt(i, 0).toString())).forEach(omeb -> {
                modelIns.addRow(new Object[]{
                    Processo,
                    omeb.getMedida(),
                    omeb.getMaior(),
                    omeb.getMenor(),
                    omeb.getInstrumento()
                });
            });
        }
    }

    public static void lerDocs(String op) {
        DefaultTableModel modelDoc = (DefaultTableModel) tableDocs.getModel();

        modelDoc.setNumRows(0);

        odd.read(op).forEach(odb -> {
            modelDoc.addRow(new Object[]{
                odb.getId(),
                false,
                odb.getDescricao(),
                odb.getLocal()
            });
        });
    }

    public static void camposPorStatus() {
        String status = txtStatus.getText();

        switch (status) {
            case "Rascunho":
                TxtQtde.setEditable(true);
                btnProcurarCliente.setEnabled(true);
                btnProcurarProduto.setEnabled(true);
                btnBaixaMP.setEnabled(false);
                txtDataEntrega.setEnabled(true);
                btnAddMP.setEnabled(false);
                btnExcluirMP.setEnabled(false);
                btnAddDoc.setEnabled(true);
                btnDelDoc.setEnabled(true);
                btnSalvar.setEnabled(true);
                break;
            case "Ativo":
                TxtQtde.setEditable(false);
                btnProcurarCliente.setEnabled(false);
                btnProcurarProduto.setEnabled(false);
                btnBaixaMP.setEnabled(false);
                txtDataEntrega.setEnabled(false);
                btnAddMP.setEnabled(false);
                btnExcluirMP.setEnabled(false);
                btnAddDoc.setEnabled(true);
                btnDelDoc.setEnabled(true);
                btnSalvar.setEnabled(false);
                break;
            default:
                TxtQtde.setEditable(false);
                btnProcurarCliente.setEnabled(false);
                btnProcurarProduto.setEnabled(false);
                btnBaixaMP.setEnabled(false);
                txtDataEntrega.setEnabled(false);
                btnAddMP.setEnabled(false);
                btnExcluirMP.setEnabled(false);
                btnAddDoc.setEnabled(false);
                btnDelDoc.setEnabled(false);
                btnSalvar.setEnabled(false);
                break;
        }

        if (Session.alterarMPOP) {
            switch (status) {
                case "Rascunho":
                    btnBaixaMP.setEnabled(true);
                    btnAddMP.setEnabled(true);
                    btnExcluirMP.setEnabled(true);
                    btnProcurarProduto.setEnabled(true);
                    btnSalvar.setEnabled(true);
                    break;
                case "Ativo":
                    btnBaixaMP.setEnabled(true);
                    btnAddMP.setEnabled(true);
                    btnExcluirMP.setEnabled(true);
                    btnProcurarProduto.setEnabled(true);
                    btnSalvar.setEnabled(true);
                    break;
            }
        }
    }

    public static void criarOP() throws SQLException {
        String op = od.opAtual();
        idMaterial = vmd.idProduto(TxtCodigo.getText());

        od.create(op, Dates.CriarDataCurtaDBSemDataExistente(), Dates.CriarDataCurtaDBJDateChooser(txtDataEntrega.getDate()), txtCliente.getText(), txtDav.getText(), idMaterial, TxtCodigo.getText(), TxtDescricao.getText(), Double.parseDouble(TxtQtde.getText()), Double.parseDouble(TxtQtde.getText()), "Rascunho");

        F_UPBean fub = new F_UPBean();

        fub.setDav(txtDav.getText());
        fub.setOp(op);
        fub.setDataentrega(Dates.CriarDataCurtaDBJDateChooser(txtDataEntrega.getDate()));
        fub.setMaterial(TxtCodigo.getText());
        fub.setProcesso("Rascunho");
        fub.setDatacriacao(Dates.CriarDataCurtaDBSemDataExistente());
        fub.setNivel(5);
        fub.setValor(0);
        fub.setObservacao("");
        fub.setCliente(txtCliente.getText());

        //dav, op, dataentrega, material, processo, datacriacao, nivel, valor, observacao, cliente
        fud.create(fub);

        txtNumOP.setText(op);

        salvarDocs(op);

        salvarMP(op);

        salvarObs(op);
    }

    public static void updateOP(String op) throws SQLException {
        idMaterial = vmd.idProduto(TxtCodigo.getText());

        String dataPrevista = Dates.CriarDataCurtaDBJDateChooser(txtDataEntrega.getDate());

        od.updateOP(op, txtCliente.getText(), idMaterial, TxtCodigo.getText(), TxtDescricao.getText(), Double.parseDouble(TxtQtde.getText().replace(",", ".")), Double.parseDouble(TxtQtde.getText().replace(",", ".")), dataPrevista);

        vpid.updateDataEntrega(op, dataPrevista);

        if (fud.existeFUP(op)) {
            fud.updateDataEntrega(op, dataPrevista, txtCliente.getText());
        } else {
            F_UPBean fub = new F_UPBean();

            fub.setDav(txtDav.getText());
            fub.setOp(op);
            fub.setDataentrega(Dates.CriarDataCurtaDBJDateChooser(txtDataEntrega.getDate()));
            fub.setMaterial(TxtCodigo.getText());
            fub.setProcesso("Rascunho");
            fub.setDatacriacao(Dates.CriarDataCurtaDBSemDataExistente());
            fub.setNivel(5);
            fub.setValor(0);
            fub.setObservacao("");
            fub.setCliente(txtCliente.getText());

            //dav, op, dataentrega, material, processo, datacriacao, nivel, valor, observacao, cliente
            fud.create(fub);
        }

    }

    public static void salvarDocs(String op) throws SQLException {
        for (int i = 0; i < tableDocs.getRowCount(); i++) {
            if (tableDocs.getValueAt(i, 0).equals("")) {
                //Localicação do documento original
                File fileoriginal = new File(tableDocs.getValueAt(i, 4).toString());
                //Pasta que será colocar o documento
                File folder = new File("Q:/MIKE_ERP/op_arq/" + op);
                //Documento copiado do original
                File filecopy = new File(folder + "/" + fileoriginal.getName());

                //Criar pasta no caso de já não existir
                folder.mkdirs();
                try {
                    //Criar o documento copiado na pasta
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

                odd.create(op, tableDocs.getValueAt(i, 2).toString(), filecopy.toString());
            }
        }
    }

    public static void salvarObs(String op) throws SQLException {
        for (int i = 0; i < tableObs.getRowCount(); i++) {
            if (tableObs.getValueAt(i, 0).equals("")) {
                ood.create(op, Dates.CriarDataCurtaDBComDataExistente(tableObs.getValueAt(i, 2).toString()), tableObs.getValueAt(i, 3).toString(), tableObs.getValueAt(i, 4).toString());
            }
        }
    }

    public static void salvarMP(String op) throws SQLException {
        for (int i = 0; i < tableMP.getRowCount(); i++) {
            if (tableMP.getValueAt(i, 0).equals("")) {
                omd.create(op, tableMP.getValueAt(i, 2).toString(), tableMP.getValueAt(i, 3).toString(), Double.parseDouble(tableMP.getValueAt(i, 5).toString()), Boolean.parseBoolean(tableMP.getValueAt(i, 7).toString()), tableMP.getValueAt(i, 4).toString(), Integer.parseInt(tableMP.getValueAt(i, 8).toString()));
            } else {
                omd.updateMP(Double.parseDouble(tableMP.getValueAt(i, 5).toString().replace(",", ".")), Integer.parseInt(tableMP.getValueAt(i, 0).toString()));
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane9 = new javax.swing.JScrollPane();
        tabOPS = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableOP = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        cbStatus = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        txtNumOP = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDav = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnProcurarCliente = new javax.swing.JButton();
        txtDataEntrega = new com.toedter.calendar.JDateChooser();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        TxtCodigo = new javax.swing.JTextField();
        TxtDescricao = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnProcurarProduto = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableObs = new javax.swing.JTable();
        btnAddObs = new javax.swing.JButton();
        btnDelObs = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableProcessos = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        lblicon = new javax.swing.JLabel();
        paneldiam = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtd1 = new javax.swing.JTextField();
        txtd2 = new javax.swing.JTextField();
        txtd3 = new javax.swing.JTextField();
        txtd4 = new javax.swing.JTextField();
        txtd5 = new javax.swing.JTextField();
        panelcomp = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtl1 = new javax.swing.JTextField();
        txtl2 = new javax.swing.JTextField();
        txtl3 = new javax.swing.JTextField();
        txtl4 = new javax.swing.JTextField();
        txtl5 = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        checkraio = new javax.swing.JCheckBox();
        txtraio = new javax.swing.JTextField();
        checkri = new javax.swing.JCheckBox();
        checkweldon = new javax.swing.JCheckBox();
        checkimportado = new javax.swing.JCheckBox();
        checkDesbaste = new javax.swing.JCheckBox();
        checkCS11 = new javax.swing.JCheckBox();
        checkDetalonado = new javax.swing.JCheckBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        paneldadostxt = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtHelice = new javax.swing.JTextField();
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
        txtTolD1 = new javax.swing.JTextField();
        txtTolD2 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtMPDados = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtConicidade = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtTipoFilete = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtCostela = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtTipoRaio = new javax.swing.JTextField();
        txtAnguloFrontal = new javax.swing.JTextField();
        txtTipoFrontal = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableInspecao = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        btnAddDoc = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableDocs = new javax.swing.JTable();
        btnDelDoc = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableMP = new javax.swing.JTable();
        btnBaixaMP = new javax.swing.JButton();
        btnAddMP = new javax.swing.JButton();
        btnExcluirMP = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        TxtQtde = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOk = new javax.swing.JTextField();
        txtMortas = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("OPs");
        setAutoscrolls(true);
        setPreferredSize(new java.awt.Dimension(970, 734));

        jScrollPane9.setPreferredSize(new java.awt.Dimension(1000, 600));

        tabOPS.setMinimumSize(new java.awt.Dimension(43, 65));
        tabOPS.setPreferredSize(new java.awt.Dimension(943, 600));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(43, 65));
        jPanel1.setPreferredSize(new java.awt.Dimension(943, 600));

        tableOP.setAutoCreateRowSorter(true);
        tableOP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "OP", "Cliente", "Código", "Data de Entrega", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableOP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableOPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableOP);
        if (tableOP.getColumnModel().getColumnCount() > 0) {
            tableOP.getColumnModel().getColumn(0).setMinWidth(0);
            tableOP.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableOP.getColumnModel().getColumn(0).setMaxWidth(0);
            tableOP.getColumnModel().getColumn(1).setMinWidth(35);
            tableOP.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableOP.getColumnModel().getColumn(1).setMaxWidth(35);
        }

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));

        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Aberto", "Rascunho", "Ativo", "Cancelado", "Finalizado", "Todos" }));
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbStatus, 0, 152, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton1.setText("Cancelar OP");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 326, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        tabOPS.addTab("Lista", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(43, 65));
        jPanel2.setPreferredSize(new java.awt.Dimension(943, 600));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("OP"));

        txtNumOP.setEditable(false);
        txtNumOP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumOP.setFocusable(false);

        jLabel1.setText("OP");

        txtStatus.setEditable(false);
        txtStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStatus.setFocusable(false);

        jLabel2.setText("Status");

        jLabel3.setText("Cliente:");

        txtCliente.setEditable(false);

        jLabel4.setText("Data de Entrega:");

        txtDav.setEditable(false);
        txtDav.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDavMouseClicked(evt);
            }
        });

        jLabel10.setText("DAV");

        btnProcurarCliente.setText("Procurar");
        btnProcurarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcurarClienteActionPerformed(evt);
            }
        });

        txtDataEntrega.setDateFormatString("dd'/'MM'/'yyyy");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumOP, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDav, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProcurarCliente)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtNumOP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel2)
                        .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtDataEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnProcurarCliente))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Produto"));

        jLabel5.setText("Código:");

        TxtCodigo.setEditable(false);

        TxtDescricao.setEditable(false);
        TxtDescricao.setFocusable(false);

        jLabel6.setText("Descrição:");

        btnProcurarProduto.setText("Procurar");
        btnProcurarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcurarProdutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProcurarProduto))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(TxtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProcurarProduto))
                .addGap(14, 14, 14))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        tableObs.setModel(new javax.swing.table.DefaultTableModel(
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
        tableObs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableObsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableObs);
        if (tableObs.getColumnModel().getColumnCount() > 0) {
            tableObs.getColumnModel().getColumn(0).setMinWidth(0);
            tableObs.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableObs.getColumnModel().getColumn(0).setMaxWidth(0);
            tableObs.getColumnModel().getColumn(1).setMinWidth(35);
            tableObs.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableObs.getColumnModel().getColumn(1).setMaxWidth(35);
            tableObs.getColumnModel().getColumn(2).setMinWidth(90);
            tableObs.getColumnModel().getColumn(2).setPreferredWidth(90);
            tableObs.getColumnModel().getColumn(2).setMaxWidth(90);
            tableObs.getColumnModel().getColumn(3).setMinWidth(250);
            tableObs.getColumnModel().getColumn(3).setPreferredWidth(250);
            tableObs.getColumnModel().getColumn(3).setMaxWidth(250);
        }

        btnAddObs.setText("Adicionar Observação");
        btnAddObs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddObsActionPerformed(evt);
            }
        });

        btnDelObs.setText("Excluir Observação");
        btnDelObs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelObsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDelObs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddObs))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddObs)
                    .addComponent(btnDelObs))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Observações", jPanel4);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        tableProcessos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Processo", "Início", "Término", "Qtde Conforme", "Qtde Não Conforme", "Usuário", "Ordem", "Qtde Disponível"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableProcessos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProcessosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableProcessos);
        if (tableProcessos.getColumnModel().getColumnCount() > 0) {
            tableProcessos.getColumnModel().getColumn(0).setMinWidth(0);
            tableProcessos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableProcessos.getColumnModel().getColumn(0).setMaxWidth(0);
            tableProcessos.getColumnModel().getColumn(1).setMinWidth(35);
            tableProcessos.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableProcessos.getColumnModel().getColumn(1).setMaxWidth(35);
            tableProcessos.getColumnModel().getColumn(8).setMinWidth(0);
            tableProcessos.getColumnModel().getColumn(8).setPreferredWidth(0);
            tableProcessos.getColumnModel().getColumn(8).setMaxWidth(0);
            tableProcessos.getColumnModel().getColumn(9).setMinWidth(0);
            tableProcessos.getColumnModel().getColumn(9).setPreferredWidth(0);
            tableProcessos.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Processos", jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados para Construção"));

        lblicon.setBackground(new java.awt.Color(255, 255, 255));
        lblicon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblicon.setOpaque(true);
        jScrollPane10.setViewportView(lblicon);

        paneldiam.setBorder(javax.swing.BorderFactory.createTitledBorder("Diâmetro"));

        jLabel11.setText("D1");

        jLabel12.setText("D2");

        jLabel13.setText("D3");

        jLabel15.setText("D4");

        jLabel17.setText("D5");

        txtd1.setEditable(false);
        txtd1.setName("fresa-broca-fe-be"); // NOI18N

        txtd2.setEditable(false);
        txtd2.setName("fresa-broca-fe-be"); // NOI18N

        txtd3.setEditable(false);
        txtd3.setName("fe-be"); // NOI18N

        txtd4.setEditable(false);
        txtd4.setName("fe-be"); // NOI18N

        txtd5.setEditable(false);
        txtd5.setName("fe-be"); // NOI18N

        javax.swing.GroupLayout paneldiamLayout = new javax.swing.GroupLayout(paneldiam);
        paneldiam.setLayout(paneldiamLayout);
        paneldiamLayout.setHorizontalGroup(
            paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldiamLayout.createSequentialGroup()
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd5))
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd4))
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd3))
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paneldiamLayout.setVerticalGroup(
            paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldiamLayout.createSequentialGroup()
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtd3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtd4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtd5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelcomp.setBorder(javax.swing.BorderFactory.createTitledBorder("Comprimento"));

        jLabel18.setText("L1");

        jLabel19.setText("L2");

        jLabel20.setText("L3");

        jLabel21.setText("L4");

        jLabel22.setText("L5");

        txtl1.setEditable(false);
        txtl1.setName("fresa-broca-fe-be"); // NOI18N

        txtl2.setEditable(false);
        txtl2.setName("fresa-broca-fe-be"); // NOI18N

        txtl3.setEditable(false);
        txtl3.setName("fe-be"); // NOI18N

        txtl4.setEditable(false);
        txtl4.setName("fe-be"); // NOI18N

        txtl5.setEditable(false);
        txtl5.setName("fe-be"); // NOI18N

        javax.swing.GroupLayout panelcompLayout = new javax.swing.GroupLayout(panelcomp);
        panelcomp.setLayout(panelcompLayout);
        panelcompLayout.setHorizontalGroup(
            panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcompLayout.createSequentialGroup()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl5))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcompLayout.createSequentialGroup()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl4))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcompLayout.createSequentialGroup()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl3))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcompLayout.createSequentialGroup()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl2))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcompLayout.createSequentialGroup()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelcompLayout.setVerticalGroup(
            panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcompLayout.createSequentialGroup()
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtl3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtl4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtl5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Especificações"));

        checkraio.setText("Raio");
        checkraio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkraioActionPerformed(evt);
            }
        });

        txtraio.setEditable(false);

        checkri.setText("Refrigeração Interna");
        checkri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkriActionPerformed(evt);
            }
        });

        checkweldon.setText("Weldon");
        checkweldon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkweldonActionPerformed(evt);
            }
        });

        checkimportado.setText("Importado");
        checkimportado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkimportadoActionPerformed(evt);
            }
        });

        checkDesbaste.setText("Desbaste");
        checkDesbaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkDesbasteActionPerformed(evt);
            }
        });

        checkCS11.setText("CS11");
        checkCS11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCS11ActionPerformed(evt);
            }
        });

        checkDetalonado.setText("Detalonado");
        checkDetalonado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkDetalonadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(checkraio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtraio, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(checkweldon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkDesbaste))
            .addComponent(checkri)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(checkCS11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkDetalonado))
            .addComponent(checkimportado)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkraio)
                    .addComponent(txtraio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkweldon)
                    .addComponent(checkDesbaste))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkri)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkCS11)
                    .addComponent(checkDetalonado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkimportado)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel14.setText("Hélice");

        txtHelice.setEditable(false);
        txtHelice.setName("fresa-broca-fe-be"); // NOI18N
        txtHelice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHeliceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHeliceFocusLost(evt);
            }
        });

        txtnucleo.setEditable(false);
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

        txtconcavidade.setEditable(false);
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

        txtaliviotopo1.setEditable(false);
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

        txtaliviotopo2.setEditable(false);
        txtaliviotopo2.setName("fresa-fe"); // NOI18N
        txtaliviotopo2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtaliviotopo2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtaliviotopo2FocusLost(evt);
            }
        });
        txtaliviotopo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtaliviotopo2ActionPerformed(evt);
            }
        });

        jLabel38.setText("Alívio de Topo Secundário");

        txtalivio1.setEditable(false);
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

        txtalivio2.setEditable(false);
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

        txtespfilete.setEditable(false);
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

        txtagressividade.setEditable(false);
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

        txtTolD1.setEditable(false);

        txtTolD2.setEditable(false);

        jLabel30.setText("Tolerância D1");

        jLabel31.setText("Tolerância D2");

        txtMPDados.setEditable(false);

        jLabel28.setText("Matéria Prima");

        txtConicidade.setEditable(false);

        jLabel29.setText("Conicidade do Canal");

        txtTipoFilete.setEditable(false);

        jLabel32.setText("Filete (Tipo)");

        txtCostela.setEditable(false);

        jLabel33.setText("Altura da Costela");

        txtTipoRaio.setEditable(false);

        txtAnguloFrontal.setEditable(false);

        txtTipoFrontal.setEditable(false);

        jLabel34.setText("Raio (Tipo)");

        jLabel35.setText("Ângulo do Frontal");

        jLabel43.setText("Frontal (Tipo)");

        javax.swing.GroupLayout paneldadostxtLayout = new javax.swing.GroupLayout(paneldadostxt);
        paneldadostxt.setLayout(paneldadostxtLayout);
        paneldadostxtLayout.setHorizontalGroup(
            paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldadostxtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel28)
                    .addGroup(paneldadostxtLayout.createSequentialGroup()
                        .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel25)
                            .addComponent(jLabel29)
                            .addComponent(jLabel42)
                            .addComponent(jLabel32)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtnucleo, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtConicidade, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHelice, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTolD2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTolD1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMPDados, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtagressividade, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipoFilete, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCostela, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneldadostxtLayout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtaliviotopo2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paneldadostxtLayout.createSequentialGroup()
                        .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel41)
                            .addComponent(jLabel37)
                            .addComponent(jLabel34)
                            .addComponent(jLabel35)
                            .addComponent(jLabel43)
                            .addComponent(jLabel39)
                            .addComponent(jLabel40))
                        .addGap(22, 22, 22)
                        .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtconcavidade, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtaliviotopo1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtespfilete, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipoFrontal, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAnguloFrontal, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtalivio1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtalivio2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipoRaio, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paneldadostxtLayout.setVerticalGroup(
            paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldadostxtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMPDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel41)
                    .addComponent(txtespfilete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(paneldadostxtLayout.createSequentialGroup()
                        .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTolD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtconcavidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTolD2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31)
                            .addComponent(jLabel37)
                            .addComponent(txtaliviotopo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14))
                    .addGroup(paneldadostxtLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(txtHelice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtaliviotopo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtnucleo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25)
                        .addComponent(jLabel34))
                    .addComponent(txtTipoRaio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneldadostxtLayout.createSequentialGroup()
                        .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtConicidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtagressividade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42)
                            .addComponent(jLabel43))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTipoFilete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32)
                            .addComponent(jLabel39))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCostela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33)
                            .addComponent(jLabel40)))
                    .addGroup(paneldadostxtLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAnguloFrontal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTipoFrontal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtalivio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtalivio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(paneldadostxt);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(paneldiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelcomp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(paneldiam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelcomp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 83, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Informações do Produto", jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        tableInspecao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Processo", "Medida", "Valor Maior", "Valor Menor", "Instrumento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tableInspecao);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Inspeção", jPanel10);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        btnAddDoc.setText("Adicionar Arquivo");
        btnAddDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDocActionPerformed(evt);
            }
        });

        tableDocs.setModel(new javax.swing.table.DefaultTableModel(
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
        tableDocs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDocsMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tableDocs);
        if (tableDocs.getColumnModel().getColumnCount() > 0) {
            tableDocs.getColumnModel().getColumn(0).setMinWidth(0);
            tableDocs.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableDocs.getColumnModel().getColumn(0).setMaxWidth(0);
            tableDocs.getColumnModel().getColumn(1).setMinWidth(35);
            tableDocs.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableDocs.getColumnModel().getColumn(1).setMaxWidth(35);
            tableDocs.getColumnModel().getColumn(4).setMinWidth(0);
            tableDocs.getColumnModel().getColumn(4).setPreferredWidth(0);
            tableDocs.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        btnDelDoc.setText("Excluir Arquivo");
        btnDelDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelDocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDelDoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddDoc))
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddDoc)
                    .addComponent(btnDelDoc)))
        );

        jTabbedPane3.addTab("Arquivos", jPanel11);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        tableMP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Código", "Descrição", "Lote", "Quantidade", "Baixa", "Insumo", "idInsumo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableMP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableMP);
        if (tableMP.getColumnModel().getColumnCount() > 0) {
            tableMP.getColumnModel().getColumn(0).setMinWidth(0);
            tableMP.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableMP.getColumnModel().getColumn(0).setMaxWidth(0);
            tableMP.getColumnModel().getColumn(1).setMinWidth(35);
            tableMP.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableMP.getColumnModel().getColumn(1).setMaxWidth(35);
            tableMP.getColumnModel().getColumn(4).setMinWidth(100);
            tableMP.getColumnModel().getColumn(4).setPreferredWidth(100);
            tableMP.getColumnModel().getColumn(4).setMaxWidth(100);
            tableMP.getColumnModel().getColumn(5).setMinWidth(100);
            tableMP.getColumnModel().getColumn(5).setPreferredWidth(100);
            tableMP.getColumnModel().getColumn(5).setMaxWidth(100);
            tableMP.getColumnModel().getColumn(6).setMinWidth(60);
            tableMP.getColumnModel().getColumn(6).setPreferredWidth(60);
            tableMP.getColumnModel().getColumn(6).setMaxWidth(60);
            tableMP.getColumnModel().getColumn(7).setMinWidth(0);
            tableMP.getColumnModel().getColumn(7).setPreferredWidth(0);
            tableMP.getColumnModel().getColumn(7).setMaxWidth(0);
            tableMP.getColumnModel().getColumn(8).setMinWidth(0);
            tableMP.getColumnModel().getColumn(8).setPreferredWidth(0);
            tableMP.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        btnBaixaMP.setText("Separar");
        btnBaixaMP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaixaMPActionPerformed(evt);
            }
        });

        btnAddMP.setText("Adicionar");
        btnAddMP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMPActionPerformed(evt);
            }
        });

        btnExcluirMP.setText("Excluir");
        btnExcluirMP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirMPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExcluirMP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddMP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBaixaMP)
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBaixaMP)
                    .addComponent(btnAddMP)
                    .addComponent(btnExcluirMP))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Matéria Prima", jPanel15);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Quantidades"));

        TxtQtde.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setText("Total");

        txtOk.setEditable(false);
        txtOk.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtMortas.setEditable(false);
        txtMortas.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setText("OK");

        jLabel9.setText("Mortas");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtOk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(TxtQtde, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMortas)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(TxtQtde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMortas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)))
        );

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jButton4.setText("Nova OP");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        tabOPS.addTab("OP", jPanel2);

        jScrollPane9.setViewportView(tabOPS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1272, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        txtNumOP.setText("");
        txtCliente.setText("");
        txtCliente.setEditable(true);
        txtCliente.setFocusable(true);
        txtDataEntrega.setDate(null);
        txtDataEntrega.setEnabled(true);
        txtStatus.setText("");
        TxtCodigo.setText("");
        TxtCodigo.setEditable(true);
        TxtCodigo.setFocusable(true);
        TxtDescricao.setText("");
        TxtQtde.setText("");
        TxtQtde.setEditable(true);
        TxtQtde.setFocusable(true);
        txtCliente.requestFocus();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (txtCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Escolha uma cliente antes.");
        } else if (TxtCodigo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Escolha um produto antes.");
        } else if (TxtQtde.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Coloque uma quantidade.");
            TxtQtde.requestFocus();
        } else if (txtDataEntrega.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Escolha uma data de entrega.");
        } else if (TxtQtde.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Digite uma quantidade maior que 0.");
            TxtQtde.requestFocus();
        } else if (vmd.qtdMinimaOP(TxtCodigo.getText()) > Double.parseDouble(TxtQtde.getText().replace(",", "."))) {
            JOptionPane.showMessageDialog(null, "Quantidade mínima para o produto não atingida.\nQuantidade Mínima: " + vmd.qtdMinimaOP(TxtCodigo.getText()));
            TxtQtde.requestFocus();
        } else {
            if (txtNumOP.getText().equals("")) {
                try {
                    criarOP();

                    JOptionPane.showMessageDialog(null, "OP criada com sucesso.");
                } catch (SQLException e) {
                    String msg = "Erro ao criar OP.";

                    JOptionPane.showMessageDialog(null, msg);

                    new Thread() {
                        @Override
                        public void run() {
                            SendEmail.EnviarErro2(msg, e);
                        }
                    }.start();
                }
            } else {
                String op = txtNumOP.getText();

                try {
                    updateOP(op);

                    salvarDocs(op);

                    salvarMP(op);

                    salvarObs(op);

                    JOptionPane.showMessageDialog(null, "OP atualizada com sucesso.");
                } catch (SQLException e) {
                    String msg = "Erro ao criar OP.";

                    JOptionPane.showMessageDialog(null, msg);

                    new Thread() {
                        @Override
                        public void run() {
                            SendEmail.EnviarErro2(msg, e);
                        }
                    }.start();
                }
            }

            String op = txtNumOP.getText();

            lerOP(op);
            lerDocs(op);
            lerInspecoes(op);
            lerMP(op);
            lerObs(op);
            lerProcessos(op);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void tableOPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOPMouseClicked
        if (evt.getClickCount() == 2) {
            tabOPS.setSelectedIndex(1);
            txtNumOP.setText(tableOP.getValueAt(tableOP.rowAtPoint(evt.getPoint()), 2).toString());

            String op = txtNumOP.getText();
            lerOP(op);
        }
    }//GEN-LAST:event_tableOPMouseClicked

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        readOPs();
    }//GEN-LAST:event_cbStatusActionPerformed

    private void btnAddObsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddObsActionPerformed
        AdicionarObs ao = new AdicionarObs(this.getClass().getSimpleName());
        Telas.AparecerTela(ao);
    }//GEN-LAST:event_btnAddObsActionPerformed

    private void tableProcessosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProcessosMouseClicked
        if (evt.getClickCount() == 2) {
            if (txtStatus.getText().equals("Rascunho")) {
                JOptionPane.showMessageDialog(null, "OP em Rascunho. Favor colocar em produção primeiro.");
            } else if (tableProcessos.getValueAt(tableProcessos.getSelectedRow(), 3).equals("")) {
                int resp = JOptionPane.showConfirmDialog(null, "Deseja iniciar o processo " + tableProcessos.getValueAt(tableProcessos.getSelectedRow(), 2).toString() + "?", "Iniciar Processo", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    opd.inicioProcesso(Integer.parseInt(tableProcessos.getValueAt(tableProcessos.getSelectedRow(), 0).toString()), Dates.CriarDataCompletaParaDB(), Session.nome);

                    lerProcessos(txtNumOP.getText());
                }
            } else {
                String user = tableProcessos.getValueAt(tableProcessos.getSelectedRow(), 7).toString();
                if (user.equals(Session.nome)) {
                    int id = Integer.parseInt(tableProcessos.getValueAt(tableProcessos.getSelectedRow(), 0).toString());
                    double qtdTotalProcesso = Double.parseDouble(tableProcessos.getValueAt(tableProcessos.getSelectedRow(), 9).toString());
                    ProcessoOP po = new ProcessoOP(id, qtdTotalProcesso);
                    Telas.AparecerTela(po);
                    ProcessoOP.readProcesso(id);
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário logado diferente do usuário do processo.");
                }
            }
        }
    }//GEN-LAST:event_tableProcessosMouseClicked

    private void txtDavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDavMouseClicked
        if (evt.getButton() == 3) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem novaDav = new JMenuItem("Colocar DAV");
            JMenuItem dav = new JMenuItem("Abrir DAV");

            novaDav.addActionListener((ActionEvent e) -> {
                String cliente = txtCliente.getText();
                String codigo = TxtCodigo.getText();
                if (cliente.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Sem cliente na OP.");
                } else if (codigo.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Sem produto na OP.");
                } else {
                    ProcurarPedido pp = new ProcurarPedido(this.getClass().getSimpleName(), cliente, codigo);
                    Telas.AparecerTela(pp);
                }

                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates
            });

            dav.addActionListener((ActionEvent ae) -> {
                String DAV = txtDav.getText();
                if (DAV.equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Produto sem DAV.");
                } else {
                    PedidoVenda p = new PedidoVenda();
                    Telas.AparecerTelaAumentada(p);
                    PedidoVenda.txtPedido.setText(DAV);
                    PedidoVenda.tabPedidos.setSelectedIndex(1);
                    PedidoVenda.lerPedido(DAV);
                    PedidoVenda.lerObsPedido(DAV);
                    PedidoVenda.lerItensPedido(DAV);
                    PedidoVenda.lerDocumentosPedido(DAV);
                }
            });

            if (txtStatus.getText().equals("Rascunho")) {
                menu.add(novaDav);
            }
            menu.add(dav);

            menu.show(evt.getComponent(), evt.getPoint().x, evt.getPoint().y);
        }
    }//GEN-LAST:event_txtDavMouseClicked

    private void btnExcluirMPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirMPActionPerformed
        int numTrue = 0, baixa = 0;
        for (int i = 0; i < tableMP.getRowCount(); i++) {
            if (tableMP.getValueAt(i, 1).equals(true)) {
                numTrue++;
                if (tableMP.getValueAt(i, 5).equals(true)) {
                    baixa++;
                }
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma Matéria Prima selecionada.");
        } else if (baixa > 0) {
            JOptionPane.showMessageDialog(null, "Produtos com baixa de estoque selecionados.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir as Matérias Primas selecionadas?", "Excluir Matéria Prima", JOptionPane.YES_NO_OPTION);

            if (resp == 0) {
                for (int i = 0; i < tableMP.getRowCount(); i++) {
                    if (tableMP.getValueAt(i, 1).equals(true)) {
                        int id = Integer.parseInt(tableMP.getValueAt(i, 0).toString());
                        omd.delete(id);
                        if (tableMP.getValueAt(i, 6).equals(true)) {
                            double qtd = Double.parseDouble(tableMP.getValueAt(i, 5).toString());
                            int idInsumo = Integer.parseInt(tableMP.getValueAt(i, 8).toString());
                            if (tableMP.getValueAt(i, 7).equals(true)) {//Insumo
                                double estoqueAtual = idao.readEstoque(idInsumo);
                                double qtdFinal = estoqueAtual + qtd;

                                idao.updateEstoque(qtdFinal, idInsumo);
                                try {
                                    imdao.create(idInsumo, estoqueAtual, qtd, qtdFinal, Dates.CriarDataCurtaDBSemDataExistente(), txtNumOP.getText() + " - Estorno MP", Session.nome);
                                } catch (SQLException e) {
                                    String msg = "Erro.";

                                    JOptionPane.showMessageDialog(null, msg + "\n" + e);

                                    new Thread() {
                                        @Override
                                        public void run() {
                                            SendEmail.EnviarErro2(msg, e);
                                        }
                                    }.start();
                                }
                            } else {
                                double estoqueAtual = vmd.readEstoque(idInsumo);
                                double qtdFinal = estoqueAtual + qtd;

                                vmd.updateEstoque(qtdFinal, idInsumo);
                                try {
                                    vmmd.create(idInsumo, estoqueAtual, qtd, qtdFinal, txtNumOP.getText() + " - Estorno MP", Dates.CriarDataCurtaDBSemDataExistente(), Session.nome);
                                } catch (SQLException e) {
                                    String msg = "Erro.";

                                    JOptionPane.showMessageDialog(null, msg + "\n" + e);

                                    new Thread() {
                                        @Override
                                        public void run() {
                                            SendEmail.EnviarErro2(msg, e);
                                        }
                                    }.start();
                                }
                            }
                        }
                    }
                }
                lerMP(txtNumOP.getText());
            }
        }
    }//GEN-LAST:event_btnExcluirMPActionPerformed

    private void btnAddMPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMPActionPerformed
        EscolherMP em = new EscolherMP();
        Telas.AparecerTela(em);
    }//GEN-LAST:event_btnAddMPActionPerformed

    private void btnBaixaMPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaixaMPActionPerformed
        int rows = tableMP.getRowCount(), numBaixa = 0, numZero = 0;
        for (int i = 0; i < rows; i++) {
            if (tableMP.getValueAt(i, 6).equals(true)) {
                numBaixa++;
            }
            if (Double.parseDouble(tableMP.getValueAt(i, 5).toString()) == 0) {
                numZero++;
            }
        }
        double qtdMinima = vmd.qtdMinimaOP(TxtCodigo.getText());
        double qtdOP = Double.parseDouble(TxtQtde.getText().replace(",", "."));

        if (qtdOP < qtdMinima) {
            JOptionPane.showMessageDialog(null, "Quantidade da OP menor que a mínima do item.\nQuantidade mínima: " + qtdMinima);
        } else if (rows == 0 || numBaixa == rows) {
            JOptionPane.showMessageDialog(null, "Nenhum item para dar baixa.");
        } else if (txtNumOP.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione ou salve uma OP primeiro.");
        } else if (numZero > 0) {
            JOptionPane.showMessageDialog(null, "Um ou mais produtos com quantidade 0 para dar baixa de estoque.");
        } else {
            int qtdNaoOk = 0;

            for (int i = 0; i < rows; i++) {
                if (tableMP.getValueAt(i, 6).equals(false)) {
                    String material = tableMP.getValueAt(i, 2).toString();
                    if (tableMP.getValueAt(i, 7).equals(true)) {
                        int idmaterial = idao.idCreated(material);
                        double estoqueAtual = idao.readEstoque(idmaterial);
                        double qtd = Double.parseDouble(tableMP.getValueAt(i, 5).toString().replace(",", "."));
                        if (qtd > estoqueAtual) {
                            JOptionPane.showMessageDialog(null, "Estoque do insumo " + material + " é inferior ao selecionado para dar baixa.");
                            qtdNaoOk++;
                        }
                    } else {
                        int idmaterial = vmd.idProduto(material);
                        double estoqueAtual = vmd.readEstoque(idmaterial);
                        double qtd = Double.parseDouble(tableMP.getValueAt(i, 5).toString().replace(",", "."));
                        if (qtd > estoqueAtual) {
                            JOptionPane.showMessageDialog(null, "Estoque do material " + material + " é inferior ao selecionado para dar baixa.");
                            qtdNaoOk++;
                        }
                    }
                }
            }

            if (qtdNaoOk == 0) {
                for (int i = 0; i < rows; i++) {
                    if (tableMP.getValueAt(i, 6).equals(false)) {
                        String material = tableMP.getValueAt(i, 2).toString();
                        if (tableMP.getValueAt(i, 7).equals(true)) {
                            int idmaterial = idao.idCreated(material);
                            double estoqueAtual = idao.readEstoque(idmaterial);
                            double qtd = Double.parseDouble(tableMP.getValueAt(i, 5).toString().replace(",", "."));

                            int idmp = Integer.parseInt(tableMP.getValueAt(i, 0).toString());
                            omd.updateBaixa(idmp);

                            qtd = qtd * (-1);
                            double estoque = estoqueAtual + qtd;

                            idao.updateEstoque(estoque, idmaterial);

                            try {
                                imdao.create(idmaterial, estoqueAtual, qtd, estoque, Dates.CriarDataCurtaDBSemDataExistente(), txtNumOP.getText() + " - baixa de MP", Session.nome);
                            } catch (SQLException ex) {
                                Logger.getLogger(OP.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            int idmaterial = vmd.idProduto(material);
                            double estoqueAtual = vmd.readEstoque(idmaterial);
                            double qtd = Double.parseDouble(tableMP.getValueAt(i, 5).toString().replace(",", "."));

                            int idmp = Integer.parseInt(tableMP.getValueAt(i, 0).toString());
                            omd.updateBaixa(idmp);

                            qtd = qtd * (-1);
                            double estoque = estoqueAtual + qtd;

                            vmd.updateEstoque(estoque, idmaterial);

                            try {
                                vmmd.create(idmaterial, estoqueAtual, qtd, estoque, txtNumOP.getText() + " - baixa de MP", Dates.CriarDataCurtaDBSemDataExistente(), Session.nome);
                            } catch (SQLException e) {
                                String msg = "Erro ao criar movimentação do Material de Venda.";
                                JOptionPane.showMessageDialog(null, msg);

                                new Thread() {
                                    @Override
                                    public void run() {
                                        SendEmail.EnviarErro2(msg, e);
                                    }
                                }.start();
                            }
                        }
                    }
                }
                if (txtStatus.getText().equals("Rascunho")) {
                    escolherPrimeiroProcesso();
                }

                String op = txtNumOP.getText();

                od.updateStatus(op, "Ativo");

                lerOP(op);

                lerProcessos(op);

                lerMP(op);
            }
        }
    }//GEN-LAST:event_btnBaixaMPActionPerformed

    private void tableMPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMPMouseClicked
        if (tableMP.getValueAt(tableMP.getSelectedRow(), 5).equals(true) && tableMP.getSelectedColumn() == 4) {
            JOptionPane.showMessageDialog(null, "Produto já dado baixa de estoque.");
        } else {

        }
    }//GEN-LAST:event_tableMPMouseClicked

    private void btnDelDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelDocActionPerformed
        int numTrue = 0;

        for (int i = 0; i < tableDocs.getRowCount(); i++) {
            if (tableDocs.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum documento selecionado.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir os documentos selecionados?", "Excluir Documentos", JOptionPane.YES_NO_OPTION);

            if (resp == 0) {
                for (int i = 0; i < tableDocs.getRowCount(); i++) {
                    if (tableDocs.getValueAt(i, 1).equals(true)) {
                        int idDoc = Integer.parseInt(tableDocs.getValueAt(i, 0).toString());
                        odd.delete(idDoc);
                    }
                }
                lerDocs(txtNumOP.getText());
            }
        }
    }//GEN-LAST:event_btnDelDocActionPerformed

    private void btnAddDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDocActionPerformed
        ProcurarDocumento pd = new ProcurarDocumento(this.getClass().getSimpleName());
        Telas.AparecerTela(pd);
    }//GEN-LAST:event_btnAddDocActionPerformed

    private void btnDelObsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelObsActionPerformed
        int numTrue = 0;

        for (int i = 0; i < tableObs.getRowCount(); i++) {
            if (tableObs.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma observação selecionada.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir as observações selecionadas?", "Excluir Observação", JOptionPane.YES_NO_OPTION);

            if (resp == 0) {
                for (int i = 0; i < tableObs.getRowCount(); i++) {
                    if (tableObs.getValueAt(i, 1).equals(true)) {
                        ood.delete(Integer.parseInt(tableObs.getValueAt(i, 0).toString()));
                    }
                }
                lerObs(txtNumOP.getText());
            }
        }
    }//GEN-LAST:event_btnDelObsActionPerformed

    private void tableObsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableObsMouseClicked
        if (tableObs.getSelectedColumn() == 1) {
            String usuarioObs = tableObs.getValueAt(tableObs.getSelectedRow(), 3).toString();
            if (!usuarioObs.equals(Session.nome)) {
                JOptionPane.showMessageDialog(null, "Observação feita por outro usuário. Não é possível selecionar.");
                tableObs.setValueAt(false, tableObs.getSelectedRow(), 1);
            }
        }
    }//GEN-LAST:event_tableObsMouseClicked

    private void btnProcurarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurarClienteActionPerformed
        ProcurarCliente pc = new ProcurarCliente(this.getClass().getSimpleName());
        Telas.AparecerTela(pc);
    }//GEN-LAST:event_btnProcurarClienteActionPerformed

    private void btnProcurarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurarProdutoActionPerformed
        ProcurarMaterial pm = new ProcurarMaterial(this.getClass().getSimpleName());
        Telas.AparecerTela(pm);
    }//GEN-LAST:event_btnProcurarProdutoActionPerformed

    private void tableDocsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDocsMouseClicked
        if (evt.getClickCount() == 2) {
            Arquivos.abrirArquivo(tableDocs.getValueAt(tableDocs.getSelectedRow(), 3).toString());
        }
    }//GEN-LAST:event_tableDocsMouseClicked

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        readOPs();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int numTrue = 0;

        for (int i = 0; i < tableOP.getRowCount(); i++) {
            if (tableOP.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma OP selecionada.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja cancelar as OPs selecionadas?", "Cancelar OPs", JOptionPane.YES_NO_OPTION);

            if (resp == 0) {
                for (int i = 0; i < tableOP.getRowCount(); i++) {
                    if (tableOP.getValueAt(i, 1).equals(true)) {
                        String op = tableOP.getValueAt(i, 2).toString();
                        od.updateStatus(op, "Cancelado");

                        F_UPBean fb = new F_UPBean();

                        fb.setProcesso("Cancelado");
                        fb.setOp(op);

                        //processo = ? WHERE op = ?
                        fud.updateProcessoByOs(fb);
                    }
                }
                readOPs();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void checkraioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkraioActionPerformed
        checks();
    }//GEN-LAST:event_checkraioActionPerformed

    private void checkimportadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkimportadoActionPerformed
        checks();
    }//GEN-LAST:event_checkimportadoActionPerformed

    private void checkweldonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkweldonActionPerformed
        checks();
    }//GEN-LAST:event_checkweldonActionPerformed

    private void checkDesbasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkDesbasteActionPerformed
        checks();
    }//GEN-LAST:event_checkDesbasteActionPerformed

    private void checkriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkriActionPerformed
        checks();
    }//GEN-LAST:event_checkriActionPerformed

    private void checkCS11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCS11ActionPerformed
        checks();
    }//GEN-LAST:event_checkCS11ActionPerformed

    private void checkDetalonadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkDetalonadoActionPerformed
        checks();
    }//GEN-LAST:event_checkDetalonadoActionPerformed

    private void txtHeliceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHeliceFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/helice.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtHeliceFocusGained

    private void txtHeliceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHeliceFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtHeliceFocusLost

    private void txtnucleoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnucleoFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/nucleo.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtnucleoFocusGained

    private void txtnucleoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnucleoFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtnucleoFocusLost

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

    private void txtaliviotopo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo1FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtaliviotopo1FocusLost

    private void txtaliviotopo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo2FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/aliviotopo2.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtaliviotopo2FocusGained

    private void txtaliviotopo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo2FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtaliviotopo2FocusLost

    private void txtalivio1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtalivio1FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/alivio1.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtalivio1FocusGained

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

    private void txtaliviotopo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtaliviotopo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtaliviotopo2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextField TxtCodigo;
    public static javax.swing.JTextField TxtDescricao;
    public static javax.swing.JTextField TxtQtde;
    private static javax.swing.JButton btnAddDoc;
    private static javax.swing.JButton btnAddMP;
    private static javax.swing.JButton btnAddObs;
    private static javax.swing.JButton btnBaixaMP;
    private static javax.swing.JButton btnDelDoc;
    private static javax.swing.JButton btnDelObs;
    private static javax.swing.JButton btnExcluirMP;
    private static javax.swing.JButton btnProcurarCliente;
    private static javax.swing.JButton btnProcurarProduto;
    private static javax.swing.JButton btnSalvar;
    private static javax.swing.JComboBox<String> cbStatus;
    private static javax.swing.JCheckBox checkCS11;
    public static javax.swing.JCheckBox checkDesbaste;
    private static javax.swing.JCheckBox checkDetalonado;
    public static javax.swing.JCheckBox checkimportado;
    public static javax.swing.JCheckBox checkraio;
    public static javax.swing.JCheckBox checkri;
    public static javax.swing.JCheckBox checkweldon;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lblicon;
    public static javax.swing.JPanel panelcomp;
    public static javax.swing.JPanel paneldadostxt;
    public static javax.swing.JPanel paneldiam;
    public static javax.swing.JTabbedPane tabOPS;
    public static javax.swing.JTable tableDocs;
    private static javax.swing.JTable tableInspecao;
    public static javax.swing.JTable tableMP;
    private static javax.swing.JTable tableOP;
    public static javax.swing.JTable tableObs;
    public static javax.swing.JTable tableProcessos;
    private static javax.swing.JTextField txtAnguloFrontal;
    public static javax.swing.JTextField txtCliente;
    private static javax.swing.JTextField txtConicidade;
    private static javax.swing.JTextField txtCostela;
    private static com.toedter.calendar.JDateChooser txtDataEntrega;
    private static javax.swing.JTextField txtDav;
    public static javax.swing.JTextField txtHelice;
    private static javax.swing.JTextField txtMPDados;
    public static javax.swing.JTextField txtMortas;
    public static javax.swing.JTextField txtNumOP;
    public static javax.swing.JTextField txtOk;
    private static javax.swing.JTextField txtPesquisa;
    public static javax.swing.JTextField txtStatus;
    private static javax.swing.JTextField txtTipoFilete;
    private static javax.swing.JTextField txtTipoFrontal;
    private static javax.swing.JTextField txtTipoRaio;
    public static javax.swing.JTextField txtTolD1;
    public static javax.swing.JTextField txtTolD2;
    public static javax.swing.JTextField txtagressividade;
    public static javax.swing.JTextField txtalivio1;
    public static javax.swing.JTextField txtalivio2;
    public static javax.swing.JTextField txtaliviotopo1;
    public static javax.swing.JTextField txtaliviotopo2;
    public static javax.swing.JTextField txtconcavidade;
    public static javax.swing.JTextField txtd1;
    public static javax.swing.JTextField txtd2;
    public static javax.swing.JTextField txtd3;
    public static javax.swing.JTextField txtd4;
    public static javax.swing.JTextField txtd5;
    public static javax.swing.JTextField txtespfilete;
    public static javax.swing.JTextField txtl1;
    public static javax.swing.JTextField txtl2;
    public static javax.swing.JTextField txtl3;
    public static javax.swing.JTextField txtl4;
    public static javax.swing.JTextField txtl5;
    public static javax.swing.JTextField txtnucleo;
    public static javax.swing.JTextField txtraio;
    // End of variables declaration//GEN-END:variables
}
