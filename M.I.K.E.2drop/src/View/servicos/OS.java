/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.servicos;

import Bean.OSBean;
import Bean.OSDocumentosBean;
import Bean.OSInspecaoBean;
import Bean.OSProcessosBean;
import Bean.ServicoMateriaisBean;
import Bean.ServicoMateriaisMovimentacaoBean;
import Bean.ServicoPedidoBean;
import Bean.ServicoPedidoDocumentosBean;
import Bean.ServicoPedidoItensBean;
import Connection.Session;
import DAO.OSDAO;
import DAO.OSDocumentosDAO;
import DAO.OSInspecaoDAO;
import DAO.OSProcessosDAO;
import DAO.ServicoMateriaisDAO;
import DAO.ServicoMateriaisMovimentacaoDAO;
import DAO.ServicoPedidoDAO;
import DAO.ServicoPedidoDocumentosDAO;
import DAO.ServicoPedidoItensDAO;
import Methods.SendEmail;
import Methods.SoNumeros;
import View.TelaPrincipal;
import static View.TelaPrincipal.jDesktopPane1;
import static View.servicos.PedidoServico.txtnumeropedido;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class OS extends javax.swing.JInternalFrame {

    /**
     * Creates new form OS
     */
    public OS() {
        initComponents();
        camposnumeros();
        radiovazio.setVisible(false);
        reados();
//        jButton2.addActionListener(new PrintOS());
        lbldirectory.setVisible(false);
    }

    public static void reados() {
        DefaultTableModel model = (DefaultTableModel) tableos.getModel();
        model.setNumRows(0);
        OSDAO od = new OSDAO();

        int index = cbstatus.getSelectedIndex();

        switch (index) {
            case 1:
                for (OSBean ob : od.readstatus(cbstatus.getSelectedItem().toString())) {
                    model.addRow(new Object[]{
                        false,
                        ob.getIdtela(),
                        ob.getCliente(),
                        ob.getCodigo(),
                        ob.getStatus()
                    });
                }
                break;
            case 2:
                for (OSBean ob : od.readstatus(cbstatus.getSelectedItem().toString())) {
                    model.addRow(new Object[]{
                        false,
                        ob.getIdtela(),
                        ob.getCliente(),
                        ob.getCodigo(),
                        ob.getStatus()
                    });
                }
                break;
            case 3:
                for (OSBean ob : od.readstatus(cbstatus.getSelectedItem().toString())) {
                    model.addRow(new Object[]{
                        false,
                        ob.getIdtela(),
                        ob.getCliente(),
                        ob.getCodigo(),
                        ob.getStatus()
                    });
                }
                break;
            case 4:
                for (OSBean ob : od.readstatus(cbstatus.getSelectedItem().toString())) {
                    model.addRow(new Object[]{
                        false,
                        ob.getIdtela(),
                        ob.getCliente(),
                        ob.getCodigo(),
                        ob.getStatus()
                    });
                }
                break;
            case 5:
                for (OSBean ob : od.read()) {
                    model.addRow(new Object[]{
                        false,
                        ob.getIdtela(),
                        ob.getCliente(),
                        ob.getCodigo(),
                        ob.getStatus()
                    });
                }
                break;
            default:
                for (OSBean ob : od.reademaberto()) {
                    model.addRow(new Object[]{
                        false,
                        ob.getIdtela(),
                        ob.getCliente(),
                        ob.getCodigo(),
                        ob.getStatus()
                    });
                }
                break;
        }

    }

    public static void camposembranco() {
        txtnumeroos.setText("");
        txtabertura.setText("");
        txtprevisao.setText("");
        txtstatus.setText("");
        txtcliente.setText("");
        txtdas.setText("");
        txtcodigo.setText("");
        txtdesc.setText("");
        txtinicial.setText("");
        txtfinal.setText("");
        txtmortas.setText("");
        txtnotes.setText("");
        radiovazio.setSelected(true);
        checkraio.setSelected(false);
        txtraio.setText("");
        txtraio.setEditable(false);
        checkfrontal.setSelected(false);
        txtfrontal.setText("");
        txtfrontal.setEditable(false);
        DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
        DefaultTableModel modelproc = (DefaultTableModel) tableprocessos.getModel();
        modeldoc.setNumRows(0);
        modelproc.setNumRows(0);
    }

    public static void dadosos() {
        OSDAO od = new OSDAO();

        for (OSBean ob : od.click(txtnumeroos.getText())) {
            txtabertura.setText(ob.getDataabertura());
            txtprevisao.setText(ob.getDataprevisao());
            txtstatus.setText(ob.getStatus());
            txtdas.setText(ob.getDas());
            txtcliente.setText(ob.getCliente());
            txtdas.setText(ob.getDas());
            txtcodigo.setText(ob.getCodigo());
            txtdesc.setText(ob.getDescricao());
            txtinicial.setText(String.valueOf(ob.getQtdinicial()));
            txtfinal.setText(String.valueOf(ob.getQtdok()));
            txtmortas.setText(String.valueOf(ob.getQtdnaook()));
            txtnotes.setText(ob.getNotes());
            if (ob.getTopo().equals("true")) {
                radiotopo.setSelected(true);
            } else if (ob.getReconstrucao().equals("true")) {
                radioreconstrucao.setSelected(true);
            } else if (ob.getCompleta().equals("true")) {
                radiocompleta.setSelected(true);
            } else if (ob.getDesenho().equals("true")) {
                radiodesenho.setSelected(true);
            } else {
                radiovazio.setSelected(true);
            }
            txtraio.setText(ob.getRaio());
            if (!txtraio.getText().equals("")) {
                checkraio.setSelected(true);
            } else {
                checkraio.setSelected(false);
            }
            txtfrontal.setText(ob.getFrontal());
            if (!txtfrontal.getText().equals("")) {
                checkfrontal.setSelected(true);
            } else {
                checkfrontal.setSelected(false);
            }
        }
    }

    public static void readdocs() {
        DefaultTableModel model = (DefaultTableModel) tabledocumentos.getModel();
        model.setNumRows(0);
        OSDocumentosDAO odd = new OSDocumentosDAO();

        for (OSDocumentosBean odb : odd.readitens(txtnumeroos.getText())) {
            model.addRow(new Object[]{
                false,
                odb.getId(),
                odb.getDesc(),
                odb.getLocal()
            });
        }
    }

    public static void readprocessos() {
        DefaultTableModel model = (DefaultTableModel) tableprocessos.getModel();
        model.setNumRows(0);
        OSProcessosDAO opd = new OSProcessosDAO();

        for (OSProcessosBean opb : opd.read(txtnumeroos.getText())) {
            model.addRow(new Object[]{
                false,
                opb.getId(),
                opb.getProcesso(),
                opb.getInicio(),
                opb.getTermino(),
                opb.getQtdok(),
                opb.getQtdnaook(),
                opb.getUsuario(),
                opb.getOrdem(),
                opb.getDisponivel()
            });
        }
    }

    public static void camposnumeros() {
        txtinicial.setDocument(new SoNumeros());
        txtraio.setDocument(new SoNumeros());
        txtfrontal.setDocument(new SoNumeros());
    }

    public static void travarcampos() {
        if (txtstatus.getText().equals("Ativo")) {
            //Desabilitar botões
            btnprocurarcliente.setEnabled(false);
            btnprocurarmaterial.setEnabled(false);
            btnmudarprocesso.setEnabled(false);

            //Desabilitar txts
            txtinicial.setEditable(false);

            //Desabilitar checks
            checkraio.setEnabled(false);
            checkfrontal.setEnabled(false);

            //Desabilitar radios
            if (radiotopo.isSelected()) {
                radiotopo.setSelected(true);
                radioreconstrucao.setEnabled(false);
                radiocompleta.setEnabled(false);
                radiodesenho.setEnabled(false);
            }
            if (radioreconstrucao.isSelected()) {
                radiotopo.setEnabled(false);
                radioreconstrucao.setSelected(true);
                radiocompleta.setEnabled(false);
                radiodesenho.setEnabled(false);
            }
            if (radiocompleta.isSelected()) {
                radiotopo.setEnabled(false);
                radioreconstrucao.setEnabled(false);
                radiocompleta.setSelected(true);
                radiodesenho.setEnabled(false);
            }
            if (radiodesenho.isSelected()) {
                radiotopo.setEnabled(false);
                radioreconstrucao.setEnabled(false);
                radiocompleta.setEnabled(false);
                radiodesenho.setSelected(true);
            }
        } else if (txtstatus.getText().equals("Cancelado") || txtstatus.getText().equals("Fechado")) {
            //Desabilitar botões
            btnprocurarcliente.setEnabled(false);
            btnprocurarmaterial.setEnabled(false);
            btnmudarprocesso.setEnabled(false);
            btnadddoc.setEnabled(false);
            btndeldoc.setEnabled(false);
            btnalterarstatus.setEnabled(false);
            btnsalvaros.setEnabled(false);

            //Desabilitar txts
            txtinicial.setEditable(false);
            txtnotes.setEditable(false);

            //Desabilitar checks
            checkraio.setEnabled(false);
            checkfrontal.setEnabled(false);

            //Desabilitar radios
            if (radiotopo.isSelected()) {
                radiotopo.setSelected(true);
                radioreconstrucao.setEnabled(false);
                radiocompleta.setEnabled(false);
                radiodesenho.setEnabled(false);
            }
            if (radioreconstrucao.isSelected()) {
                radiotopo.setEnabled(false);
                radioreconstrucao.setSelected(true);
                radiocompleta.setEnabled(false);
                radiodesenho.setEnabled(false);
            }
            if (radiocompleta.isSelected()) {
                radiotopo.setEnabled(false);
                radioreconstrucao.setEnabled(false);
                radiocompleta.setSelected(true);
                radiodesenho.setEnabled(false);
            }
            if (radiodesenho.isSelected()) {
                radiotopo.setEnabled(false);
                radioreconstrucao.setEnabled(false);
                radiocompleta.setEnabled(false);
                radiodesenho.setSelected(true);
            }
        } else {
            //Habilitar botões
            btnprocurarcliente.setEnabled(true);
            btnprocurarmaterial.setEnabled(true);
            btnmudarprocesso.setEnabled(true);
            btnalterarstatus.setEnabled(true);

            //Habilitar txts
            txtinicial.setEditable(true);

            //Habilitar checks
            checkraio.setEnabled(true);
            checkfrontal.setEnabled(true);

            //Habilitar radios
            radioreconstrucao.setEnabled(true);
            radiotopo.setEnabled(true);

            if (!txtraio.getText().equals("")) {
                checkraio.setSelected(true);
                txtraio.setEditable(true);
            }
            if (!txtfrontal.getText().equals("")) {
                checkfrontal.setSelected(true);
                txtfrontal.setEditable(true);
            }
        }
    }

    public static void readinspecoes() {
        DefaultTableModel model = (DefaultTableModel) tableinspecoes.getModel();
        model.setNumRows(0);

        OSInspecaoDAO oid = new OSInspecaoDAO();
        for (OSInspecaoBean oib : oid.reados(txtnumeroos.getText())) {
            model.addRow(new Object[]{
                oib.getId(),
                oib.getProcesso(),
                oib.getMedida(),
                oib.getMedidamaior(),
                oib.getMedidamenor(),
                oib.getFuncionario(),
                oib.getInstrumento()
            });
        }
    }

    public static void qtdok() {
        int qtdnaook = 0;
        int qtdinicial = Integer.parseInt(OS.txtinicial.getText());
        for (int i = 0; i < OS.tableprocessos.getRowCount(); i++) {
            qtdnaook = qtdnaook + Integer.parseInt(tableprocessos.getValueAt(i, 6).toString());
        }

        OS.txtmortas.setText(String.valueOf(qtdnaook));
        OS.txtfinal.setText(String.valueOf(qtdinicial - qtdnaook));
        OSDAO od = new OSDAO();
        OSBean ob = new OSBean();

        ob.setQtdok(qtdinicial - qtdnaook);
        ob.setQtdnaook(qtdnaook);
        ob.setIdtela(OS.txtnumeroos.getText());

        //qtdok = ?, qtdnaook = ? WHERE idtela = ?
        od.updateqtd(ob);
    }

    public static void radios() {
        if (!txtstatus.getText().equals("Rascunho")) {
            if (radiotopo.isSelected()) {
                radioreconstrucao.setEnabled(false);
            } else {
                radiotopo.setEnabled(false);
            }
        }
    }

    public static void encerraop() {
        //DAO e Bean para alteração da OS
        OSDAO od = new OSDAO();
        OSBean ob = new OSBean();

        //DAO para id do material
        ServicoMateriaisDAO smd = new ServicoMateriaisDAO();
        ServicoMateriaisBean smb = new ServicoMateriaisBean();

        //DAO e Bean para movimentação do produto
        ServicoMateriaisMovimentacaoDAO smmd = new ServicoMateriaisMovimentacaoDAO();
        ServicoMateriaisMovimentacaoBean smmb = new ServicoMateriaisMovimentacaoBean();

        //Pegar data para gravar
        Calendar c = Calendar.getInstance();
        String pattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        //Pegar id do material
        int idmaterial = 0;
        for (ServicoMateriaisBean smb2 : smd.readid(txtcodigo.getText())) {
            idmaterial = smb2.getId();
        }

        //Número de processos encerrados e rowcount da table de processos
        int procencerrado = 0;
        int rc = tableprocessos.getRowCount();

        //Checagem de quantos processos estão encerrados
        for (int i = 0; i < rc; i++) {
            if (!tableprocessos.getValueAt(i, 4).equals("")) {
                procencerrado++;
            }
        }

        //Pegar saldo atual do produto
        int saldoatual = 0;
        for (ServicoMateriaisBean smb2 : smd.readestoque(idmaterial)) {
            saldoatual = smb2.getEstoque();
        }

        //Checagem se todos os processos estão encerrados para alterar status da OP
        if (rc == procencerrado) {
            int resp = JOptionPane.showConfirmDialog(null, "Último processo está sendo encerrado.\n Deseja encerrar a OS?", "Último processo", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                ob.setStatus("Fechado");
                txtstatus.setText("Fechado");
                ob.setIdtela(txtnumeroos.getText());

                //status = ? WHERE idtela = ?
                od.updatestatus(ob);

                //Criar movimentação do material
                smmb.setIdmaterial(idmaterial);
                smmb.setInicial(saldoatual);
                smmb.setMovimentada(Integer.parseInt(txtfinal.getText()));
                smmb.setTipo(txtnumeroos.getText());
                smmb.setSaldo(saldoatual + Integer.parseInt(txtfinal.getText()));
                smmb.setData(simpleDateFormat.format(c.getTime()));
                smmb.setUsuario(Session.nome);

                //idmaterial, inicial, movimentada, tipo, saldo, data, usuario
                smmd.create(smmb);

                //Alterar estoque do material
                smb.setEstoque(saldoatual + Integer.parseInt(txtfinal.getText()));
                smb.setId(idmaterial);

                //estoque = ? WHERE id = ?
                smd.updateestoque(smb);
            }
        }

        //Atualizar tabela de OS
        reados();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        tabos = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtpesquisa = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cbstatus = new javax.swing.JComboBox<>();
        jButton12 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtnumeroos = new javax.swing.JTextField();
        txtstatus = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtprevisao = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtabertura = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtcliente = new javax.swing.JTextField();
        txtdas = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnprocurarcliente = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtcodigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtdesc = new javax.swing.JTextField();
        btnprocurarmaterial = new javax.swing.JButton();
        btnsalvaros = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        radiotopo = new javax.swing.JRadioButton();
        radioreconstrucao = new javax.swing.JRadioButton();
        checkraio = new javax.swing.JCheckBox();
        txtraio = new javax.swing.JTextField();
        txtfrontal = new javax.swing.JTextField();
        checkfrontal = new javax.swing.JCheckBox();
        radiovazio = new javax.swing.JRadioButton();
        radiocompleta = new javax.swing.JRadioButton();
        radiodesenho = new javax.swing.JRadioButton();
        tabadp = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtnotes = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabledocumentos = new javax.swing.JTable();
        btnadddoc = new javax.swing.JButton();
        btndeldoc = new javax.swing.JButton();
        lbldirectory = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableprocessos = new javax.swing.JTable();
        btnmudarprocesso = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableinspecoes = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        txtfinal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtmortas = new javax.swing.JTextField();
        txtinicial = new javax.swing.JTextField();
        btnalterarstatus = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        setClosable(true);
        setMaximizable(true);
        setTitle("OS");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tableos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "#", "Cliente", "Código", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableos);
        if (tableos.getColumnModel().getColumnCount() > 0) {
            tableos.getColumnModel().getColumn(0).setMinWidth(40);
            tableos.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableos.getColumnModel().getColumn(0).setMaxWidth(40);
            tableos.getColumnModel().getColumn(1).setMinWidth(80);
            tableos.getColumnModel().getColumn(1).setPreferredWidth(80);
            tableos.getColumnModel().getColumn(1).setMaxWidth(80);
            tableos.getColumnModel().getColumn(4).setMinWidth(110);
            tableos.getColumnModel().getColumn(4).setPreferredWidth(110);
            tableos.getColumnModel().getColumn(4).setMaxWidth(110);
        }

        jButton1.setText("Novo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));

        jLabel8.setText("Pesquisa:");

        txtpesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtpesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro"));

        jLabel9.setText("Status");

        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Aberto", "Rascunho", "Ativo", "Cancelado", "Fechado", "Todas" }));
        cbstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbstatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbstatus, 0, 151, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton12.setText("Alterar Status em Lote");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 373, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton12))
                .addContainerGap())
        );

        tabos.addTab("Lista de OS", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("OS"));

        jLabel1.setText("Nº");

        txtnumeroos.setEditable(false);
        txtnumeroos.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtstatus.setEditable(false);
        txtstatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setText("Status");

        txtprevisao.setEditable(false);

        jLabel10.setText("Data de Previsão");

        txtabertura.setEditable(false);

        jLabel11.setText("Data de Abertura");

        jButton4.setText("<");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText(">");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnumeroos, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtabertura, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtprevisao, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtnumeroos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtprevisao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtabertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        jLabel3.setText("Nome:");

        txtcliente.setEditable(false);

        txtdas.setEditable(false);
        txtdas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtdas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtdasMouseClicked(evt);
            }
        });

        jLabel4.setText("DAS de origem:");

        btnprocurarcliente.setText("Procurar");
        btnprocurarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprocurarclienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnprocurarcliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdas, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(btnprocurarcliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Material"));

        jLabel5.setText("Código:");

        txtcodigo.setEditable(false);

        jLabel7.setText("Descrição:");

        txtdesc.setEditable(false);

        btnprocurarmaterial.setText("Procurar");
        btnprocurarmaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprocurarmaterialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnprocurarmaterial)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnprocurarmaterial))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnsalvaros.setText("Salvar");
        btnsalvaros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalvarosActionPerformed(evt);
            }
        });

        jButton3.setText("Novo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Serviço"));

        buttonGroup1.add(radiotopo);
        radiotopo.setText("Topo");

        buttonGroup1.add(radioreconstrucao);
        radioreconstrucao.setText("Reconstrução");

        checkraio.setText("Raio");
        checkraio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkraioActionPerformed(evt);
            }
        });

        txtraio.setEditable(false);
        txtraio.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtfrontal.setEditable(false);
        txtfrontal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        checkfrontal.setText("Frontal");
        checkfrontal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkfrontalActionPerformed(evt);
            }
        });

        buttonGroup1.add(radiovazio);
        radiovazio.setText("Vazio");

        buttonGroup1.add(radiocompleta);
        radiocompleta.setText("Completa");

        buttonGroup1.add(radiodesenho);
        radiodesenho.setText("Desenho");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(checkraio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtraio, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(checkfrontal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfrontal, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(radiocompleta)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(radiodesenho))
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(radiotopo)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(radioreconstrucao)))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(radiovazio)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radiotopo)
                    .addComponent(radioreconstrucao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radiocompleta)
                    .addComponent(radiodesenho))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtraio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkraio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfrontal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkfrontal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(radiovazio)
                .addContainerGap())
        );

        txtnotes.setColumns(20);
        txtnotes.setRows(5);
        jScrollPane2.setViewportView(txtnotes);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabadp.addTab("Anotações", jPanel7);

        tabledocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Descrição", "Local", "Local Original"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabledocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabledocumentosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabledocumentos);
        if (tabledocumentos.getColumnModel().getColumnCount() > 0) {
            tabledocumentos.getColumnModel().getColumn(0).setMinWidth(40);
            tabledocumentos.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabledocumentos.getColumnModel().getColumn(0).setMaxWidth(40);
            tabledocumentos.getColumnModel().getColumn(1).setMinWidth(0);
            tabledocumentos.getColumnModel().getColumn(1).setPreferredWidth(0);
            tabledocumentos.getColumnModel().getColumn(1).setMaxWidth(0);
            tabledocumentos.getColumnModel().getColumn(3).setMinWidth(0);
            tabledocumentos.getColumnModel().getColumn(3).setPreferredWidth(0);
            tabledocumentos.getColumnModel().getColumn(3).setMaxWidth(0);
            tabledocumentos.getColumnModel().getColumn(4).setMinWidth(0);
            tabledocumentos.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabledocumentos.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        btnadddoc.setText("Incluir");
        btnadddoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnadddocActionPerformed(evt);
            }
        });

        btndeldoc.setText("Excluir");
        btndeldoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeldocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbldirectory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btndeldoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnadddoc)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnadddoc)
                    .addComponent(btndeldoc)
                    .addComponent(lbldirectory))
                .addContainerGap())
        );

        tabadp.addTab("Documentos", jPanel8);

        tableprocessos.setAutoCreateRowSorter(true);
        tableprocessos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Processo", "Início", "Término", "Qtde Conforme", "Qtde Não Conforme", "Usuário", "Ordem", "Disponivel"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableprocessos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableprocessosMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tableprocessos);
        if (tableprocessos.getColumnModel().getColumnCount() > 0) {
            tableprocessos.getColumnModel().getColumn(0).setMinWidth(40);
            tableprocessos.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableprocessos.getColumnModel().getColumn(0).setMaxWidth(40);
            tableprocessos.getColumnModel().getColumn(1).setMinWidth(0);
            tableprocessos.getColumnModel().getColumn(1).setPreferredWidth(0);
            tableprocessos.getColumnModel().getColumn(1).setMaxWidth(0);
            tableprocessos.getColumnModel().getColumn(8).setMinWidth(0);
            tableprocessos.getColumnModel().getColumn(8).setPreferredWidth(0);
            tableprocessos.getColumnModel().getColumn(8).setMaxWidth(0);
            tableprocessos.getColumnModel().getColumn(9).setMinWidth(0);
            tableprocessos.getColumnModel().getColumn(9).setPreferredWidth(0);
            tableprocessos.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        btnmudarprocesso.setText("Mudar Processos");
        btnmudarprocesso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmudarprocessoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnmudarprocesso)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnmudarprocesso)
                .addContainerGap())
        );

        tabadp.addTab("Processos", jPanel10);

        tableinspecoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Processo", "Medida", "Maior Valor do Lote", "Menor Valor do Lote", "Funcionário", "Instrumento de Medição"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tableinspecoes);
        if (tableinspecoes.getColumnModel().getColumnCount() > 0) {
            tableinspecoes.getColumnModel().getColumn(0).setMinWidth(0);
            tableinspecoes.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableinspecoes.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabadp.addTab("Inspeção", jPanel14);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Quantidade"));

        txtfinal.setEditable(false);
        txtfinal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel13.setText("Inicial");

        jLabel14.setText("Final");

        jLabel15.setText("Mortas");

        txtmortas.setEditable(false);
        txtmortas.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtinicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtinicial, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfinal, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmortas, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(txtmortas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtinicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnalterarstatus.setText("Alterar Status da OS");
        btnalterarstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnalterarstatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnalterarstatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnsalvaros))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(tabadp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabadp, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsalvaros)
                    .addComponent(jButton3)
                    .addComponent(btnalterarstatus))
                .addContainerGap())
        );

        tabos.addTab("Detalhes OS", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabos)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabos)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkraioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkraioActionPerformed
        if (checkraio.isSelected()) {
            txtraio.setEditable(true);
            txtraio.requestFocus();
        } else {
            txtraio.setEditable(false);
        }
    }//GEN-LAST:event_checkraioActionPerformed

    private void checkfrontalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkfrontalActionPerformed
        if (checkfrontal.isSelected()) {
            txtfrontal.setEditable(true);
            txtfrontal.requestFocus();
        } else {
            txtfrontal.setEditable(false);
        }
    }//GEN-LAST:event_checkfrontalActionPerformed

    private void txtdasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtdasMouseClicked
        if (evt.getButton() == 3) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem das = new JMenuItem("Abrir DAS");

            das.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String DAS = txtdas.getText();
                    if (DAS.equals("")) {
                        JOptionPane.showMessageDialog(rootPane, "OS sem DAS");
                    } else {
                        PedidoServico p = new PedidoServico();
                        jDesktopPane1.add(p);
                        p.setVisible(true);
                        try {
                            p.setMaximum(true);
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        PedidoServico.txtnumeropedido.setText(DAS);
                        PedidoServico.tabpedidos.setSelectedIndex(1);

                        ServicoPedidoDAO spd = new ServicoPedidoDAO();

                        for (ServicoPedidoBean spb : spd.click(txtnumeropedido.getText())) {
                            PedidoServico.txtclientepedido.setText(spb.getCliente());
                            PedidoServico.txtcondicao.setText(spb.getCondicao());
                            PedidoServico.txtrepresentante.setText(spb.getRepresentante());
                            PedidoServico.txtvendedor.setText(spb.getVendedor());
                            PedidoServico.txtstatusretorno.setText(spb.getStatus_retorno());
                            PedidoServico.txtnotes.setText(spb.getNotes());
                            PedidoServico.txtorcamento.setText(String.valueOf(spb.getIdorcamento()));
                        }

                        DefaultTableModel model = (DefaultTableModel) PedidoServico.tableitensorcamento.getModel();
                        model.setNumRows(0);
                        ServicoPedidoItensDAO spid = new ServicoPedidoItensDAO();

                        for (ServicoPedidoItensBean spib : spid.readitens(txtnumeropedido.getText())) {
                            model.addRow(new Object[]{
                                false,
                                spib.getId(),
                                spib.getCodigo(),
                                spib.getDescricao(),
                                spib.getQtde(),
                                spib.getValor(),
                                spib.getTotal(),
                                spib.getPrazo(),
                                spib.getPedidocliente(),
                                spib.getOs(),
                                spib.getNf()
                            });
                        }

                        DefaultTableModel modeldoc = (DefaultTableModel) PedidoServico.tabledocumentos.getModel();
                        modeldoc.setNumRows(0);
                        ServicoPedidoDocumentosDAO spdd = new ServicoPedidoDocumentosDAO();

                        for (ServicoPedidoDocumentosBean spdb : spdd.readitens(PedidoServico.txtnumeropedido.getText())) {
                            modeldoc.addRow(new Object[]{
                                false,
                                spdb.getId(),
                                spdb.getDescricao(),
                                spdb.getLocal()});
                        }
                        PedidoServico.txtvalorcobranca();
                    }
                }
            });

            menu.add(das);

            menu.show(evt.getComponent(), evt.getPoint().x, evt.getPoint().y);
        }
    }//GEN-LAST:event_txtdasMouseClicked

    private void btnsalvarosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalvarosActionPerformed
        if (txtnumeroos.getText().equals("")) {
            if (txtcliente.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Escolha um cliente primeiro!");
            } else if (txtcodigo.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Selecione um produto primeiro!");
            } else if (txtinicial.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Coloque uma quantidade!");
                txtinicial.requestFocus();
            } else if (!radiotopo.isSelected() && !radioreconstrucao.isSelected() && !radiocompleta.isSelected() && !radiodesenho.isSelected()) {
                JOptionPane.showMessageDialog(rootPane, "Escolha um serviço a ser executado.");
            } else {
                //Criar OS
                OSDAO od = new OSDAO();
                OSBean ob = new OSBean();

                //Dados para método DAO
                try { //Tentar achar primeira OP do ano para poder dar nome
                    if (od.readnome() == false) {
                        Calendar ca = Calendar.getInstance();
                        String patterny = "yy";
                        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                        String year = simpleDateFormaty.format(ca.getTime());
                        String idtela = year + "-0001S";
                        ob.setIdtela(idtela);
                        txtnumeroos.setText(idtela);
                    } else {
                        Calendar ca = Calendar.getInstance();
                        String patterny = "yy";
                        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                        String year = simpleDateFormaty.format(ca.getTime());
                        String hua = "";
                        for (OSBean sob2 : od.read()) {
                            hua = String.valueOf(sob2.getIdtela());
                        }
                        int yearint = Integer.parseInt(hua.replace(year + "-", ""));
                        int yearnovo = yearint + 1;
                        String idtela = year + "-" + String.format("%04d", yearnovo) + "S";
                        ob.setIdtela(idtela);
                        txtnumeroos.setText(idtela);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CotacaoServico.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Criar data do dia para data de criação e prazo de entrega
                Calendar c = Calendar.getInstance();
                String pattern = "dd/MM/yyyy HH:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                txtabertura.setText(simpleDateFormat.format(c.getTime()));
                ob.setDataabertura(simpleDateFormat.format(c.getTime()));

                //Perguntar prazo para fazer a data de entrega
                int days = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "Quantos dias de prazo?", "Prazo", JOptionPane.YES_NO_OPTION));
                c.add(Calendar.DAY_OF_MONTH, days);
                txtprevisao.setText(simpleDateFormat.format(c.getTime()));
                ob.setDataprevisao(simpleDateFormat.format(c.getTime()));

                //Resto dos dados para o método DAO
                txtstatus.setText("Rascunho");
                ob.setStatus("Rascunho");
                ob.setCliente(txtcliente.getText());
                ob.setDas("");
                ob.setCodigo(txtcodigo.getText());
                ob.setDescricao(txtdesc.getText());
                ob.setQtdinicial(Integer.parseInt(txtinicial.getText()));
                ob.setQtdok(Integer.parseInt(txtinicial.getText()));
                ob.setQtdnaook(0);
                ob.setNotes(txtnotes.getText());
                String topo = "false";
                String rec = "false";
                String com = "false";
                String des = "false";
                if (radiotopo.isSelected()) {
                    topo = "true";
                }
                if (radioreconstrucao.isSelected()) {
                    rec = "true";
                }
                if (radiocompleta.isSelected()) {
                    com = "true";
                }
                if (radiodesenho.isSelected()) {
                    des = "true";
                }
                ob.setTopo(topo);
                ob.setReconstrucao(rec);
                ob.setCompleta(com);
                ob.setDesenho(des);
                ob.setRaio(txtraio.getText());
                txtraio.setEditable(false);
                checkraio.setEnabled(false);
                ob.setFrontal(txtfrontal.getText());
                txtfrontal.setEditable(false);
                checkfrontal.setEnabled(false);
                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, descricao, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, completa, desenho, raio, frontal
                od.create(ob);

                //Criar Processos
                OSProcessosDAO opd = new OSProcessosDAO();
                OSProcessosBean opb = new OSProcessosBean();

                int rcproc = tableprocessos.getRowCount();
                for (int i = 0; i < rcproc; i++) {
                    opb.setIdos(txtnumeroos.getText());
                    opb.setProcesso(tableprocessos.getValueAt(i, 2).toString());
                    opb.setInicio(tableprocessos.getValueAt(i, 3).toString());
                    opb.setTermino(tableprocessos.getValueAt(i, 4).toString());
                    opb.setQtdok(Integer.parseInt(tableprocessos.getValueAt(i, 5).toString()));
                    opb.setQtdnaook(Integer.parseInt(tableprocessos.getValueAt(i, 6).toString()));
                    opb.setUsuario(tableprocessos.getValueAt(i, 7).toString());
                    opb.setOrdem(Integer.parseInt(tableprocessos.getValueAt(i, 8).toString()));
                    opb.setDisponivel(txtinicial.getText());

                    //idos, processo, inicio, termino, qtdok, qtdnaook, usuario, ordem, disponivel
                    opd.create(opb);
                }

                //Criar Documentos da OS
                OSDocumentosDAO odd = new OSDocumentosDAO();
                OSDocumentosBean odb = new OSDocumentosBean();

                int rcdoc = tabledocumentos.getRowCount();
                for (int i = 0; i < rcdoc; i++) {
                    File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                    File folder = new File("Q:/MIKE_ERP/os_arq/" + txtnumeroos.getText());
                    File filecopy = new File(folder + "/" + fileoriginal.getName());

                    folder.mkdirs();
                    try {
                        Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                    } catch (IOException ex) {
                        Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");
                        try {
                            SendEmail.EnviarErro(ex.toString());
                            JOptionPane.showMessageDialog(rootPane, "E-mail com erro enviado com sucesso!");
                        } catch (HeadlessException hex) {
                            JOptionPane.showMessageDialog(rootPane, "Erro!\n" + hex);
                        } catch (AWTException | IOException ex1) {
                            Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }

                    odb.setIdos(txtnumeroos.getText());
                    odb.setDesc(tabledocumentos.getValueAt(i, 2).toString());
                    odb.setLocal(filecopy.toString());

                    //idos, descricao, local
                    odd.create(odb);
                }
                reados();
                readdocs();
                readprocessos();
                radios();
                JOptionPane.showMessageDialog(rootPane, "Salvo com sucesso!");
            }
        } else {
            //Atualizar OS
            OSDAO od = new OSDAO();
            OSBean ob = new OSBean();

            ob.setDataabertura(txtabertura.getText());
            ob.setDataprevisao(txtprevisao.getText());
            ob.setStatus(txtstatus.getText());
            ob.setCliente(txtcliente.getText());
            ob.setDas(txtdas.getText());
            ob.setCodigo(txtcodigo.getText());
            ob.setDescricao(txtdesc.getText());
            ob.setQtdinicial(Integer.parseInt(txtinicial.getText()));
            ob.setQtdok(Integer.parseInt(txtfinal.getText()));
            ob.setQtdnaook(Integer.parseInt(txtmortas.getText()));
            ob.setNotes(txtnotes.getText());
            String topo = "false";
            String rec = "false";
            String com = "false";
            String des = "false";
            if (radiotopo.isSelected()) {
                topo = "true";
                if (!txtstatus.getText().equals("Rascunho")) {
                    radioreconstrucao.setEnabled(false);
                    radiocompleta.setEnabled(false);
                    radiodesenho.setEnabled(false);
                }
            }
            if (radioreconstrucao.isSelected()) {
                rec = "true";
                if (!txtstatus.getText().equals("Rascunho")) {
                    radiotopo.setEnabled(false);
                    radiocompleta.setEnabled(false);
                    radiodesenho.setEnabled(false);
                }
            }
            if (radiocompleta.isSelected()) {
                com = "true";
                if (!txtstatus.getText().equals("Rascunho")) {
                    radiotopo.setEnabled(false);
                    radioreconstrucao.setEnabled(false);
                    radiodesenho.setEnabled(false);
                }
            }
            if (radiodesenho.isSelected()) {
                des = "true";
                if (!txtstatus.getText().equals("Rascunho")) {
                    radiotopo.setEnabled(false);
                    radioreconstrucao.setEnabled(false);
                    radiocompleta.setEnabled(false);
                }
            }
            ob.setTopo(topo);
            ob.setReconstrucao(rec);
            ob.setCompleta(com);
            ob.setDesenho(des);
            ob.setRaio(txtraio.getText());
//            txtraio.setEditable(false);
//            checkraio.setEnabled(false);
            ob.setFrontal(txtfrontal.getText());
//            txtfrontal.setEditable(false);
//            checkfrontal.setEnabled(false);
            ob.setIdtela(txtnumeroos.getText());
            //dataabertura = ?, dataprevisao = ?, status = ?, cliente = ?, das = ?, codigo = ?, descricao = ?, qtdinicial = ?, qtdok = ?, qtdnaook = ?, notes = ?, topo = ?, reconstrucao = ?, completa = ?, desenho = ?, raio = ?, frontal = ? WHERE idtela = ?
            od.update(ob);

            //Atualizar Processos
            OSProcessosDAO opd = new OSProcessosDAO();
            OSProcessosBean opb = new OSProcessosBean();

            int rcproc = tableprocessos.getRowCount();
            for (int i = 0; i < rcproc; i++) {
                opb.setIdos(txtnumeroos.getText());
                opb.setProcesso(tableprocessos.getValueAt(i, 2).toString());
                opb.setInicio(tableprocessos.getValueAt(i, 3).toString());
                opb.setTermino(tableprocessos.getValueAt(i, 4).toString());
                opb.setQtdok(Integer.parseInt(tableprocessos.getValueAt(i, 5).toString()));
                opb.setQtdnaook(Integer.parseInt(tableprocessos.getValueAt(i, 6).toString()));
                opb.setUsuario(tableprocessos.getValueAt(i, 7).toString());
                opb.setOrdem(Integer.parseInt(tableprocessos.getValueAt(i, 8).toString()));
                opb.setDisponivel(tableprocessos.getValueAt(i, 9).toString());
                opb.setId(Integer.parseInt(tableprocessos.getValueAt(i, 1).toString()));

                //idos = ?, processo = ?, inicio = ?, termino = ?, qtdok = ?, qtdnaook = ?, usuario = ?, ordem = ?, disponivel = ? WHERE id = ?
                opd.updatetotal(opb);
            }

            //Atualizar Documentos da OS
            OSDocumentosDAO odd = new OSDocumentosDAO();
            OSDocumentosBean odb = new OSDocumentosBean();

            int rcdoc = tabledocumentos.getRowCount();
            for (int i = 0; i < rcdoc; i++) {
                if (tabledocumentos.getValueAt(i, 1).equals("")) {
                    File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                    File folder = new File("Q:/MIKE_ERP/os_arq/" + txtnumeroos.getText());
                    File filecopy = new File(folder + "/" + fileoriginal.getName());

                    folder.mkdirs();
                    try {
                        Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                    } catch (IOException ex) {
                        Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");
                        try {
                            SendEmail.EnviarErro(ex.toString());
                            JOptionPane.showMessageDialog(rootPane, "E-mail com erro enviado com sucesso!");
                        } catch (HeadlessException hex) {
                            JOptionPane.showMessageDialog(rootPane, "Erro!\n" + hex);
                        } catch (AWTException | IOException ex1) {
                            Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }

                    odb.setIdos(txtnumeroos.getText());
                    odb.setDesc(tabledocumentos.getValueAt(i, 2).toString());
                    odb.setLocal(filecopy.toString());

                    //idos, descricao, local
                    odd.create(odb);
                }
            }
            reados();
            readdocs();
            readprocessos();
            JOptionPane.showMessageDialog(rootPane, "Salvo com sucesso!");
        }
    }//GEN-LAST:event_btnsalvarosActionPerformed

    private void btnprocurarmaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprocurarmaterialActionPerformed
        ProcuraItemOS p = new ProcuraItemOS();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_btnprocurarmaterialActionPerformed

    private void btnprocurarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprocurarclienteActionPerformed
        ProcurarClienteOS p = new ProcurarClienteOS();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_btnprocurarclienteActionPerformed

    private void btnmudarprocessoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmudarprocessoActionPerformed
        ProcuraGrupoDeProcessosOS p = new ProcuraGrupoDeProcessosOS();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_btnmudarprocessoActionPerformed

    private void tableprocessosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableprocessosMouseClicked
        if (evt.getClickCount() == 2) {
            if (txtstatus.getText().equals("Rascunho")) {
                JOptionPane.showMessageDialog(rootPane, "OS em rascunho!");
            } else {
                if (tableprocessos.getValueAt(tableprocessos.getSelectedRow(), 3).equals("")) {
                    int resp = JOptionPane.showConfirmDialog(rootPane, "Deseja iniciar o processo " + tableprocessos.getValueAt(tableprocessos.getSelectedRow(), 2) + "?", "Início de Processo", JOptionPane.YES_NO_OPTION);
                    if (resp == 0) {
                        OSProcessosDAO opd = new OSProcessosDAO();
                        OSProcessosBean opb = new OSProcessosBean();

                        Calendar c = Calendar.getInstance();
                        String pattern = "dd/MM/yyyy HH:mm:ss";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        opb.setInicio(simpleDateFormat.format(c.getTime()));
                        opb.setUsuario(Session.nome);
                        opb.setId(Integer.parseInt(tableprocessos.getValueAt(tableprocessos.getSelectedRow(), 1).toString()));

                        //inicio = ?, usuario = ? WHERE id = ?
                        opd.updateinicio(opb);

                        readprocessos();
                    }
                } else {
                    String processo = tableprocessos.getValueAt(tableprocessos.getSelectedRow(), 2).toString();
                    int naookprocesso = 0;
                    int okprocesso = 0;
                    int naookos = 0;
                    for (int i = 0; i < tableprocessos.getRowCount(); i++) {
                        if (tableprocessos.getValueAt(i, 2).equals(processo)) {
                            naookprocesso = naookprocesso + Integer.parseInt(tableprocessos.getValueAt(i, 6).toString());
                        }
                        if (tableprocessos.getValueAt(i, 2).equals(processo)) {
                            okprocesso = okprocesso + Integer.parseInt(tableprocessos.getValueAt(i, 5).toString());
                        }
                        naookos = naookos + Integer.parseInt(tableprocessos.getValueAt(i, 6).toString());
                    }
                    ProcessoOS p = new ProcessoOS();
                    JDesktopPane desk = this.getDesktopPane();
                    desk.add(p);
                    Dimension desktopsize = jDesktopPane1.getSize();
                    Dimension jinternalframesize = p.getSize();
                    p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
                    p.setVisible(true);
                    ProcessoOS.txtidprocesso.setText(tableprocessos.getValueAt(tableprocessos.getSelectedRow(), 1).toString());
                    ProcessoOS.txtdisponivel.setText(txtfinal.getText());
                    ProcessoOS.txtnaookprocesso.setText(String.valueOf(naookprocesso));
                    ProcessoOS.txtokprocesso.setText(String.valueOf(okprocesso));
                    ProcessoOS.txtnaookos.setText(String.valueOf(naookos));
                    ProcessoOS.txtrow.setText(String.valueOf(tableprocessos.getSelectedRow()));
                    ProcessoOS.txtdispprocesso.setText(tableprocessos.getValueAt(tableprocessos.getSelectedRow(), 9).toString());
                    ProcessoOS.readprocesso();
                    ProcessoOS.readinspecao();
                    ProcessoOS.travarcampos();
                }
            }
        }
    }//GEN-LAST:event_tableprocessosMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int resp = JOptionPane.showConfirmDialog(rootPane, "Deseja criar uma nova OS?", "Nova OS", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            OS.camposembranco();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnadddocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnadddocActionPerformed
        File dir = new File(lbldirectory.getText());
        if (lbldirectory.getText().equals("")) {
            DocumentosOS p = new DocumentosOS();
            JDesktopPane desk = this.getDesktopPane();
            desk.add(p);
            Dimension desktopsize = jDesktopPane1.getSize();
            Dimension jinternalframesize = p.getSize();
            p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
            p.setVisible(true);
        } else {
            DocumentosOS p = new DocumentosOS();
            JDesktopPane desk = this.getDesktopPane();
            desk.add(p);
            Dimension desktopsize = jDesktopPane1.getSize();
            Dimension jinternalframesize = p.getSize();
            p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
            DocumentosOS.chooser.setCurrentDirectory(dir);
            p.setVisible(true);
        }
    }//GEN-LAST:event_btnadddocActionPerformed

    private void btndeldocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeldocActionPerformed
        int numerotrue = 0;
        for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
            if (tabledocumentos.getValueAt(i, 0).equals(true)) {
                numerotrue++;
            }
        }
        DefaultTableModel model = (DefaultTableModel) tabledocumentos.getModel();
        if (tabledocumentos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Não existem itens para excluir");
        } else if (numerotrue == 0) {
            JOptionPane.showMessageDialog(rootPane, "Selecione um item para excluir!");
        } else {
            int resp = JOptionPane.showConfirmDialog(rootPane, "Excluir documentos selecionados?", "Excluir documentos", JOptionPane.OK_CANCEL_OPTION);
            if (resp == 0) {
                int nr = tabledocumentos.getRowCount();
                for (int i = 0; i < nr; i++) {
                    if (tabledocumentos.getValueAt(i, 0).equals(true)) {
                        File file = new File((String) tabledocumentos.getValueAt(i, 3));
                        try {
                            Files.delete(file.toPath());
                        } catch (IOException ex) {
                            Logger.getLogger(CotacaoServico.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(rootPane, "Erro!\n" + ex);
                        }

                        OSDocumentosBean spdb = new OSDocumentosBean();
                        OSDocumentosDAO spdd = new OSDocumentosDAO();

                        spdb.setId(Integer.parseInt(tabledocumentos.getValueAt(i, 1).toString()));
                        //id

                        spdd.delete(spdb);

                        model.removeRow(i);
                    }
                }
            }
        }
    }//GEN-LAST:event_btndeldocActionPerformed

    private void tableosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableosMouseClicked
        if (evt.getClickCount() == 2) {
            tabos.setSelectedIndex(1);
            txtnumeroos.setText(tableos.getValueAt(tableos.getSelectedRow(), 1).toString());

            tabadp.setSelectedIndex(0);

            //Pegar dados da OS
            OS.dadosos();

            //Pegar documentos
            readdocs();

            //Pegar processos
            readprocessos();

            //Travar campos de acordo com status da op
            travarcampos();

            //Pegar inspeções
            readinspecoes();

            //Atualizar quantidade final
            qtdok();
        }
    }//GEN-LAST:event_tableosMouseClicked

    private void cbstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbstatusActionPerformed
        reados();
    }//GEN-LAST:event_cbstatusActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int rascunho = 0;
        int ativo = 0;
        int cancelado = 0;
        int fechado = 0;
        int numerotrue = 0;
        for (int i = 0; i < tableos.getRowCount(); i++) {
            if (tableos.getValueAt(i, 0).equals(true)) {
                numerotrue++;
            }
            if (tableos.getValueAt(i, 4).equals("Rascunho") && tableos.getValueAt(i, 0).equals(true)) {
                rascunho++;
            }
            if (tableos.getValueAt(i, 4).equals("Ativo") && tableos.getValueAt(i, 0).equals(true)) {
                ativo++;
            }
            if (tableos.getValueAt(i, 4).equals("Cancelado") && tableos.getValueAt(i, 0).equals(true)) {
                cancelado++;
            }
            if (tableos.getValueAt(i, 4).equals("Fechado") && tableos.getValueAt(i, 0).equals(true)) {
                fechado++;
            }
        }
        if (numerotrue == 0) {
            JOptionPane.showMessageDialog(rootPane, "Não existem OS's selecionadas.");
        } else if (rascunho > 0 && ativo > 0) {
            JOptionPane.showMessageDialog(rootPane, "Mais de um status de OS's selecionado.");
        } else if (rascunho > 0 && cancelado > 0) {
            JOptionPane.showMessageDialog(rootPane, "Mais de um status de OS's selecionado.");
        } else if (rascunho > 0 && fechado > 0) {
            JOptionPane.showMessageDialog(rootPane, "Mais de um status de OS's selecionado.");
        } else if (ativo > 0 && cancelado > 0) {
            JOptionPane.showMessageDialog(rootPane, "Mais de um status de OS's selecionado.");
        } else if (ativo > 0 && fechado > 0) {
            JOptionPane.showMessageDialog(rootPane, "Mais de um status de OS's selecionado.");
        } else if (cancelado > 0 && fechado > 0) {
            JOptionPane.showMessageDialog(rootPane, "Mais de um status de OS's selecionado.");
        } else if (cancelado > 0) {
            JOptionPane.showMessageDialog(rootPane, "OS's canceladas. Status não pode ser alterado.");
        } else if (fechado > 0) {
            JOptionPane.showMessageDialog(rootPane, "OS's fechadas. Status não pode ser alterado.");
        } else {
            MudarStatusEmLote p = new MudarStatusEmLote();
            JDesktopPane desk = this.getDesktopPane();
            desk.add(p);
            Dimension desktopsize = jDesktopPane1.getSize();
            Dimension jinternalframesize = p.getSize();
            p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
            p.setVisible(true);
            if (rascunho > 0) {
                MudarStatusEmLote.txtrascunho.setText(String.valueOf(rascunho));
            }
            if (ativo > 0) {
                MudarStatusEmLote.txtativo.setText(String.valueOf(ativo));
            }
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void btnalterarstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnalterarstatusActionPerformed
        MudarStatus p = new MudarStatus();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_btnalterarstatusActionPerformed

    private void tabledocumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabledocumentosMouseClicked
        if (evt.getClickCount() == 2) {
            Desktop desk = Desktop.getDesktop();
            try {
                desk.open(new File((String) tabledocumentos.getValueAt(tabledocumentos.getSelectedRow(), 3)));

            } catch (IOException ex) {
                Logger.getLogger(DocumentosOrcamentoServico.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_tabledocumentosMouseClicked

    private void txtpesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpesquisaKeyReleased
        DefaultTableModel model = (DefaultTableModel) tableos.getModel();
        model.setNumRows(0);

        OSDAO od = new OSDAO();
        for (OSBean ob : od.pesquisa(txtpesquisa.getText(), cbstatus.getSelectedItem().toString())) {
            model.addRow(new Object[]{
                false,
                ob.getIdtela(),
                ob.getCliente(),
                ob.getCodigo(),
                ob.getStatus()
            });
        }
    }//GEN-LAST:event_txtpesquisaKeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (txtnumeroos.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Selecione uma OP primeiro!");
        } else {
            //Pegar OS atual
            String osoriginal = txtnumeroos.getText();

            //Zerar campos
            OS.camposembranco();
            
            //Transformar na próxima OS
            int numero = Integer.parseInt(osoriginal.replace("OS19-", "")) + 1;

            //Colocar novo número de OS
            txtnumeroos.setText("OS19-" + String.format("%04d", numero));

            //Pegar dados da OS
            OS.dadosos();

            //Pegar documentos
            readdocs();

            //Pegar processos
            readprocessos();

            //Travar campos de acordo com status da op
            travarcampos();

            //Pegar inspeções
            readinspecoes();

            //Atualizar quantidade final
            qtdok();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (txtnumeroos.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Selecione uma OP primeiro!");
        } else {
            //Pegar OS atual
            String osoriginal = txtnumeroos.getText();

            //Zerar campos
            OS.camposembranco();

            //Transformar na próxima OS
            int numero = Integer.parseInt(osoriginal.replace("OS19-", "")) - 1;

            //Colocar novo número de OS
            txtnumeroos.setText("OS19-" + String.format("%04d", numero));

            //Pegar dados da OS
            OS.dadosos();

            //Pegar documentos
            readdocs();

            //Pegar processos
            readprocessos();

            //Travar campos de acordo com status da op
            travarcampos();

            //Pegar inspeções
            readinspecoes();

            //Atualizar quantidade final
            qtdok();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int resp = JOptionPane.showConfirmDialog(rootPane, "Deseja criar uma nova OS?", "Nova OS", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            OS.camposembranco();
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnadddoc;
    public static javax.swing.JButton btnalterarstatus;
    public static javax.swing.JButton btndeldoc;
    public static javax.swing.JButton btnmudarprocesso;
    public static javax.swing.JButton btnprocurarcliente;
    public static javax.swing.JButton btnprocurarmaterial;
    public static javax.swing.JButton btnsalvaros;
    public javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JComboBox<String> cbstatus;
    public static javax.swing.JCheckBox checkfrontal;
    public static javax.swing.JCheckBox checkraio;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton12;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public static javax.swing.JButton jButton5;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel10;
    public javax.swing.JPanel jPanel11;
    public javax.swing.JPanel jPanel12;
    public javax.swing.JPanel jPanel13;
    public javax.swing.JPanel jPanel14;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JPanel jPanel8;
    public javax.swing.JPanel jPanel9;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JScrollPane jScrollPane6;
    public javax.swing.JScrollPane jScrollPane7;
    public javax.swing.JTable jTable1;
    public static javax.swing.JLabel lbldirectory;
    public static javax.swing.JRadioButton radiocompleta;
    public static javax.swing.JRadioButton radiodesenho;
    public static javax.swing.JRadioButton radioreconstrucao;
    public static javax.swing.JRadioButton radiotopo;
    public static javax.swing.JRadioButton radiovazio;
    public static javax.swing.JTabbedPane tabadp;
    public static javax.swing.JTable tabledocumentos;
    public static javax.swing.JTable tableinspecoes;
    public static javax.swing.JTable tableos;
    public static javax.swing.JTable tableprocessos;
    public javax.swing.JTabbedPane tabos;
    public static javax.swing.JTextField txtabertura;
    public static javax.swing.JTextField txtcliente;
    public static javax.swing.JTextField txtcodigo;
    public static javax.swing.JTextField txtdas;
    public static javax.swing.JTextField txtdesc;
    public static javax.swing.JTextField txtfinal;
    public static javax.swing.JTextField txtfrontal;
    public static javax.swing.JTextField txtinicial;
    public static javax.swing.JTextField txtmortas;
    public static javax.swing.JTextArea txtnotes;
    public static javax.swing.JTextField txtnumeroos;
    public javax.swing.JTextField txtpesquisa;
    public static javax.swing.JTextField txtprevisao;
    public static javax.swing.JTextField txtraio;
    public static javax.swing.JTextField txtstatus;
    // End of variables declaration//GEN-END:variables
}
