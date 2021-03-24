/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.vendas;

import Bean.ClientesContatosBean;
import Connection.ConnectionFactory;
import Connection.Session;
import DAO.AltDAO;
import DAO.ClientesContatosDAO;
import DAO.ClientesDAO;
import DAO.VendasCotacaoDAO;
import DAO.VendasCotacaoDocsDAO;
import DAO.VendasCotacaoItensDAO;
import DAO.VendasCotacaoObsDAO;
import DAO.VendasPedidoDAO;
import DAO.VendasPedidoItensDAO;
import DAO.VendasPedidoObsDAO;
import Methods.Dates;
import Methods.Docs;
import Methods.SendEmail;
import Methods.Telas;
import Methods.Valores;
import View.Geral.AdicionarObs;
import View.Geral.EscolherPedido;
import View.Geral.HistoricoAlteracao;
import View.Geral.ItemCotacao;
import View.Geral.ProcurarCliente;
import View.Geral.ProcurarCondicaoDePagamento;
import View.Geral.ProcurarDocumento;
import View.Geral.ProcurarRepresentante;
import View.Geral.ProcurarVendedor;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author Marcos Filho
 */
public class CotacaoVenda extends javax.swing.JInternalFrame {

    static int idCotacao;

    static VendasCotacaoDAO vcd = new VendasCotacaoDAO();
    static VendasCotacaoItensDAO vcid = new VendasCotacaoItensDAO();
    static VendasCotacaoDocsDAO vcdd = new VendasCotacaoDocsDAO();
    static VendasCotacaoObsDAO vcod = new VendasCotacaoObsDAO();
    static VendasPedidoDAO vpd = new VendasPedidoDAO();
    static VendasPedidoItensDAO vpid = new VendasPedidoItensDAO();
    static VendasPedidoObsDAO vpod = new VendasPedidoObsDAO();
    static AltDAO ad = new AltDAO();
    ClientesContatosDAO ccd = new ClientesContatosDAO();
    ClientesDAO cd = new ClientesDAO();

    public static String clienteOriginal, condicaoOriginal, representanteOriginal, vendedorOriginal;
    public static int numDocsOriginal, numObsOriginal, numItensOriginal;

    static double totalCotacao;

    /**
     * Creates new form CotacaoVenda
     */
    public CotacaoVenda() {
        initComponents();
        lerCotacoes();
        btnMotivo.setVisible(false);
        idCotacao = 0;
    }

    public static void valoresOriginais() {
        clienteOriginal = txtCliente.getText();
        condicaoOriginal = txtCondPag.getText();
        representanteOriginal = txtRep.getText();
        vendedorOriginal = txtVendedor.getText();
        numObsOriginal = tableObs.getRowCount();
        numDocsOriginal = tableDocs.getRowCount();
        numItensOriginal = tableItens.getRowCount();
    }

    public static void criarPedidoVenda(String pedido) {
        String dav;
        if (pedido.length() > 0) {
            dav = pedido;
        } else {
            dav = vpd.pedidoAtual();

            try {
                vpd.create(dav, Dates.CriarDataCurtaDBSemDataExistente(), txtCliente.getText(), "Ativo", txtVendedor.getText(), txtRep.getText(), txtCondPag.getText(), Valores.TransformarDinheiroEmValorDouble(txtFrete.getText()), "");
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

        try {
            for (int i = 0; i < tableItens.getRowCount(); i++) {
                if (tableItens.getValueAt(i, 2).equals(true)) {
                    vcid.updateDAV(dav, Integer.parseInt(tableItens.getValueAt(i, 0).toString()));

                    String dataEntrega = Dates.CriarDataCurtaDBSemDataExistenteComPrazo(Integer.parseInt(tableItens.getValueAt(i, 8).toString().replace(" dias úteis", "")));

                    vpid.create(dav, Integer.parseInt(tableItens.getValueAt(i, 10).toString()), tableItens.getValueAt(i, 3).toString(), tableItens.getValueAt(i, 4).toString(), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 5).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 6).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 7).toString()), dataEntrega, "", "");
                }
            }

            for (int i = 0; i < tableObs.getRowCount(); i++) {
                vpod.create(dav, Dates.CriarDataCurtaDBComDataExistente(tableObs.getValueAt(i, 2).toString()), tableObs.getValueAt(i, 3).toString(), tableObs.getValueAt(i, 4).toString());
            }

            JOptionPane.showMessageDialog(null, "Pedido criado com sucesso.");
        } catch (SQLException e) {
            String msg = "Erro ao criar Pedido de Venda.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        }

        String cotacao = txtCotacao.getText();

        lerItensCotacao(cotacao);

        alterarStatus(cotacao);

        lerCotacao(cotacao);
    }

    public static void camposPorStatus() {
        if (txtStatus.getText().equals("Desativado") || txtStatus.getText().equals("Fechado") || txtStatus.getText().equals("Perdido")) {
            txtCliente.setEditable(false);
            btnProcurarCliente.setEnabled(false);
            btnCondPag.setEnabled(false);
            btnRep.setEnabled(false);
            btnVendedor.setEnabled(false);
            btnAddObs.setEnabled(false);
            btnDelObs.setEnabled(false);
            btnAddDoc.setEnabled(false);
            btnDelDoc.setEnabled(false);
            btnAddItem.setEnabled(false);
            btnDelItem.setEnabled(false);
            btnAddPedido.setEnabled(false);
            btnSalvar.setEnabled(false);
            btnMarcarTodos.setEnabled(false);
            radioCadastrado.setEnabled(false);
            radioNaoCadastrado.setEnabled(false);
            tableObs.setEnabled(false);
            tableDocs.setEnabled(false);
            tableItens.setEnabled(false);
            txtFrete.setEditable(false);
            btnItensPerdidos.setEnabled(false);
        } else {
            txtCliente.setEditable(true);
            btnProcurarCliente.setEnabled(true);
            btnCondPag.setEnabled(true);
            btnRep.setEnabled(true);
            btnVendedor.setEnabled(true);
            btnAddObs.setEnabled(true);
            btnDelObs.setEnabled(true);
            btnAddDoc.setEnabled(true);
            btnDelDoc.setEnabled(true);
            btnAddItem.setEnabled(true);
            btnDelItem.setEnabled(true);
            btnAddPedido.setEnabled(true);
            btnSalvar.setEnabled(true);
            btnMarcarTodos.setEnabled(true);
            radioCadastrado.setEnabled(true);
            radioNaoCadastrado.setEnabled(true);
            tableObs.setEnabled(true);
            tableDocs.setEnabled(true);
            tableItens.setEnabled(true);
            txtFrete.setEditable(false);
            btnItensPerdidos.setEnabled(true);
        }
    }

    public static void lerCotacoes() {
        DefaultTableModel modelTableCotacoes = (DefaultTableModel) tableCotacoes.getModel();

        modelTableCotacoes.setNumRows(0);

        String status = cbStatus.getSelectedItem().toString();

        if (txtPesquisa.getText().equals("")) {
            switch (status) {
                case "Todos":
                    vcd.readCotacoes().forEach(vcb -> {
                        modelTableCotacoes.addRow(new Object[]{
                            vcb.getId(),
                            false,
                            vcb.getCotacao(),
                            vcb.getCliente(),
                            vcb.getVendedor(),
                            vcb.getStatus()
                        });
                    });
                    break;
                default:
                    vcd.readCotacoesStatus(status).forEach(vcb -> {
                        modelTableCotacoes.addRow(new Object[]{
                            vcb.getId(),
                            false,
                            vcb.getCotacao(),
                            vcb.getCliente(),
                            vcb.getVendedor(),
                            vcb.getStatus()
                        });
                    });
                    break;
            }
        } else {
            String pesquisa = txtPesquisa.getText();
            switch (status) {
                case "Todos":
                    vcd.readCotacoesPesquisa(pesquisa).forEach(vcb -> {
                        modelTableCotacoes.addRow(new Object[]{
                            vcb.getId(),
                            false,
                            vcb.getCotacao(),
                            vcb.getCliente(),
                            vcb.getVendedor(),
                            vcb.getStatus()
                        });
                    });
                    break;
                default:
                    vcd.readCotacoesStatusPesquisa(status, pesquisa).forEach(vcb -> {
                        modelTableCotacoes.addRow(new Object[]{
                            vcb.getId(),
                            false,
                            vcb.getCotacao(),
                            vcb.getCliente(),
                            vcb.getVendedor(),
                            vcb.getStatus()
                        });
                    });
                    break;
            }
        }
    }

    public static void lerCotacao(String cotacao) {
        vcd.readCotacao(cotacao).forEach(vcb -> {
            idCotacao = vcb.getId();
            txtCotacao.setText(vcb.getCotacao());
            txtDataAbertura.setText(Dates.TransformarDataCurtaDoDB(vcb.getData_abertura()));
            txtCliente.setText(vcb.getCliente());
            txtStatus.setText(vcb.getStatus());
            if (vcb.isCadastrado()) {
                radioCadastrado.setSelected(true);
            } else {
                radioNaoCadastrado.setSelected(true);
            }
            txtVendedor.setText(vcb.getVendedor());
            txtRep.setText(vcb.getRepresentante());
            txtCondPag.setText(vcb.getCondicao());
            txtFrete.setText(Valores.TransformarDoubleDBemString(vcb.getFrete()));
        });

        if (vcd.checkMotivo(cotacao)) {
            btnMotivo.setVisible(true);
        }

        lerItensCotacao(cotacao);

        lerDocumentosCotacao(cotacao);

        lerObsCotacao(cotacao);

        clienteCadastrado();

        valoresOriginais();

        camposPorStatus();
    }

    public static void lerItensCotacao(String cotacao) {
        DefaultTableModel modelItens = (DefaultTableModel) tableItens.getModel();
        modelItens.setNumRows(0);

        vcid = new VendasCotacaoItensDAO();

        totalCotacao = Double.parseDouble(Valores.TransformarStringDinheiroEmStringDouble(txtFrete.getText().replace("R$ ", "")));

        vcid.readItens(cotacao).forEach(vcib -> {
            totalCotacao += vcib.getValortotal();

            modelItens.addRow(new Object[]{
                vcib.getId(),
                vcib.isCadastrado(),
                false,
                vcib.getCodigo(),
                vcib.getDescricao(),
                Valores.TransformarDoubleDBemString(vcib.getQtd()),
                Valores.TransformarDoubleDBemString(vcib.getValorunitario()),
                Valores.TransformarDoubleDBemString(vcib.getValortotal()),
                vcib.getPrazo(),
                vcib.getDav(),
                vcib.getIdmaterial()
            });
        });

        txtValorTotal.setText(Valores.TransformarDoubleDBemString(totalCotacao));
    }

    public static void lerDocumentosCotacao(String cotacao) {
        DefaultTableModel tableDocumentos = (DefaultTableModel) tableDocs.getModel();
        tableDocumentos.setNumRows(0);

        vcdd = new VendasCotacaoDocsDAO();

        vcdd.readDocs(cotacao).forEach(vcdb -> {
            tableDocumentos.addRow(new Object[]{
                vcdb.getId(),
                false,
                vcdb.getDescricao(),
                vcdb.getLocal(),
                ""
            });
        });
    }

    public static void lerObsCotacao(String cotacao) {
        DefaultTableModel tableObservacao = (DefaultTableModel) tableObs.getModel();
        tableObservacao.setNumRows(0);

        vcod = new VendasCotacaoObsDAO();

        vcod.readObs(cotacao).forEach(vcob -> {
            tableObservacao.addRow(new Object[]{
                vcob.getId(),
                false,
                Dates.TransformarDataCurtaDoDB(vcob.getData()),
                vcob.getUsuario(),
                vcob.getObs()
            });
        });
    }

    public static void clienteCadastrado() {
        if (radioCadastrado.isSelected()) {
            btnProcurarCliente.setEnabled(true);
            txtCliente.setEditable(false);
        } else {
            btnProcurarCliente.setEnabled(false);
            txtCliente.setEditable(true);
            txtCliente.requestFocus();
        }
    }

    public static void zerarCampos() {
        idCotacao = 0;
        txtCotacao.setText("");
        txtDataAbertura.setText("");
        txtStatus.setText("");
        txtCliente.setText("");
        radioCadastrado.setSelected(true);
        txtCondPag.setText("");
        txtVendedor.setText("");
        txtRep.setText("");
        DefaultTableModel modelObs = (DefaultTableModel) tableObs.getModel();
        modelObs.setNumRows(0);
        DefaultTableModel modelDocs = (DefaultTableModel) tableDocs.getModel();
        modelDocs.setNumRows(0);
        DefaultTableModel modelItens = (DefaultTableModel) tableItens.getModel();
        modelItens.setNumRows(0);
    }

    public static void criarDocumentosCotacao(String cotacao) {

    }

    public static void alterarStatus(String cotacao) {
        int numPedido = 0;
        for (int i = 0; i < tableItens.getRowCount(); i++) {
            if (!tableItens.getValueAt(i, 9).equals("")) {
                numPedido++;
            }
        }
        if (numPedido == tableItens.getRowCount()) {
            vcd.updateStatus(cotacao, "Fechado");
        } else {
            vcd.updateStatus(cotacao, "Parcialmente Fechado");
        }

        lerCotacoes();
    }

    public void updateTotal(String cotacao) {
        vcd.updateTotal(cotacao, Valores.TransformarDinheiroEmValorDouble(txtValorTotal.getText()));
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
        jPanel1 = new javax.swing.JPanel();
        tabCotacoes = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableCotacoes = new javax.swing.JTable();
        jButton15 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        cbStatus = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        txtDataAbertura = new javax.swing.JTextField();
        btnProcurarCliente = new javax.swing.JButton();
        radioCadastrado = new javax.swing.JRadioButton();
        radioNaoCadastrado = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        txtCotacao = new javax.swing.JTextField();
        btnMotivo = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCondPag = new javax.swing.JTextField();
        txtRep = new javax.swing.JTextField();
        txtVendedor = new javax.swing.JTextField();
        btnCondPag = new javax.swing.JButton();
        btnRep = new javax.swing.JButton();
        btnVendedor = new javax.swing.JButton();
        tabItens = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableObs = new javax.swing.JTable();
        btnAddObs = new javax.swing.JButton();
        btnDelObs = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDocs = new javax.swing.JTable();
        btnAddDoc = new javax.swing.JButton();
        btnDelDoc = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableItens = new javax.swing.JTable();
        btnAddItem = new javax.swing.JButton();
        btnDelItem = new javax.swing.JButton();
        txtValorTotal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnMarcarTodos = new javax.swing.JButton();
        btnAddPedido = new javax.swing.JButton();
        btnItensPerdidos = new javax.swing.JButton();
        txtFrete = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnSendCotacao = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Cotações de Venda");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        tabCotacoes.setName("tabCotacoes"); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        tableCotacoes.setAutoCreateRowSorter(true);
        tableCotacoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Orçamento", "Cliente", "Vendedor", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCotacoes.setName("tableCotacoes"); // NOI18N
        tableCotacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCotacoesMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableCotacoes);
        if (tableCotacoes.getColumnModel().getColumnCount() > 0) {
            tableCotacoes.getColumnModel().getColumn(0).setMinWidth(0);
            tableCotacoes.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableCotacoes.getColumnModel().getColumn(0).setMaxWidth(0);
            tableCotacoes.getColumnModel().getColumn(1).setMinWidth(35);
            tableCotacoes.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableCotacoes.getColumnModel().getColumn(1).setMaxWidth(35);
        }

        jButton15.setText("Desativar Cotação");
        jButton15.setName("jButton15"); // NOI18N
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel9.setName("jPanel9"); // NOI18N

        txtPesquisa.setName("txtPesquisa"); // NOI18N
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));
        jPanel10.setName("jPanel10"); // NOI18N

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Parcialmente Fechado", "Fechado", "Perdido", "Todos" }));
        cbStatus.setName("cbStatus"); // NOI18N
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbStatus, 0, 128, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1192, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton15)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton15)
                .addContainerGap())
        );

        tabCotacoes.addTab("Cotações", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setName("jPanel3"); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));
        jPanel4.setName("jPanel4"); // NOI18N

        jLabel4.setText("Cliente");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText("Data de Abertura");
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText("Status");
        jLabel6.setName("jLabel6"); // NOI18N

        txtCliente.setEditable(false);
        txtCliente.setName("txtCliente"); // NOI18N

        txtStatus.setEditable(false);
        txtStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStatus.setName("txtStatus"); // NOI18N

        txtDataAbertura.setEditable(false);
        txtDataAbertura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataAbertura.setName("txtDataAbertura"); // NOI18N

        btnProcurarCliente.setText("Procurar");
        btnProcurarCliente.setName("btnProcurarCliente"); // NOI18N
        btnProcurarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcurarClienteActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioCadastrado);
        radioCadastrado.setSelected(true);
        radioCadastrado.setText("Cadastrado");
        radioCadastrado.setName("radioCadastrado"); // NOI18N
        radioCadastrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioCadastradoActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioNaoCadastrado);
        radioNaoCadastrado.setText("Não Cadastrado");
        radioNaoCadastrado.setName("radioNaoCadastrado"); // NOI18N
        radioNaoCadastrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioNaoCadastradoActionPerformed(evt);
            }
        });

        jLabel8.setText("Cotação");
        jLabel8.setName("jLabel8"); // NOI18N

        txtCotacao.setEditable(false);
        txtCotacao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCotacao.setName("txtCotacao"); // NOI18N

        btnMotivo.setText("Motivo");
        btnMotivo.setName("btnMotivo"); // NOI18N
        btnMotivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMotivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCotacao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMotivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProcurarCliente))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(radioCadastrado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioNaoCadastrado)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtDataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtCotacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMotivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioCadastrado)
                    .addComponent(radioNaoCadastrado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProcurarCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Financeiro"));
        jPanel5.setName("jPanel5"); // NOI18N

        jLabel1.setText("Condição de Pagamento");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Representante");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("Vendedor");
        jLabel3.setName("jLabel3"); // NOI18N

        txtCondPag.setEditable(false);
        txtCondPag.setName("txtCondPag"); // NOI18N

        txtRep.setEditable(false);
        txtRep.setName("txtRep"); // NOI18N

        txtVendedor.setEditable(false);
        txtVendedor.setName("txtVendedor"); // NOI18N

        btnCondPag.setText("Procurar");
        btnCondPag.setName("btnCondPag"); // NOI18N
        btnCondPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCondPagActionPerformed(evt);
            }
        });

        btnRep.setText("Procurar");
        btnRep.setName("btnRep"); // NOI18N
        btnRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRepActionPerformed(evt);
            }
        });

        btnVendedor.setText("Procurar");
        btnVendedor.setName("btnVendedor"); // NOI18N
        btnVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVendedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(61, 61, 61)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtRep, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRep))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtVendedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVendedor))))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCondPag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCondPag))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCondPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCondPag))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtRep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVendedor)
                    .addComponent(txtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabItens.setName("tabItens"); // NOI18N

        jPanel6.setName("jPanel6"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

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
        tableObs.setName("tableObs"); // NOI18N
        tableObs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableObsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableObs);
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
        btnAddObs.setName("btnAddObs"); // NOI18N
        btnAddObs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddObsActionPerformed(evt);
            }
        });

        btnDelObs.setText("Excluir Observação");
        btnDelObs.setName("btnDelObs"); // NOI18N
        btnDelObs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelObsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDelObs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddObs))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddObs)
                    .addComponent(btnDelObs))
                .addContainerGap())
        );

        tabItens.addTab("Observações", jPanel6);

        jPanel7.setName("jPanel7"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

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
        tableDocs.setName("tableDocs"); // NOI18N
        tableDocs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDocsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableDocs);
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

        btnAddDoc.setText("Adicionar Documento");
        btnAddDoc.setName("btnAddDoc"); // NOI18N
        btnAddDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDocActionPerformed(evt);
            }
        });

        btnDelDoc.setText("Excluir Documento");
        btnDelDoc.setName("btnDelDoc"); // NOI18N
        btnDelDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelDocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDelDoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddDoc))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddDoc)
                    .addComponent(btnDelDoc))
                .addContainerGap())
        );

        tabItens.addTab("Documentos", jPanel7);

        jPanel8.setName("jPanel8"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tableItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cadastrado", "", "Código", "Descrição", "Qtd", "Valor Unitário", "Valor Total", "Prazo de Entrega", "DAV", "idMaterial"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableItens.setName("tableItens"); // NOI18N
        tableItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableItensMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableItensMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tableItens);
        if (tableItens.getColumnModel().getColumnCount() > 0) {
            tableItens.getColumnModel().getColumn(0).setMinWidth(0);
            tableItens.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableItens.getColumnModel().getColumn(0).setMaxWidth(0);
            tableItens.getColumnModel().getColumn(1).setMinWidth(0);
            tableItens.getColumnModel().getColumn(1).setPreferredWidth(0);
            tableItens.getColumnModel().getColumn(1).setMaxWidth(0);
            tableItens.getColumnModel().getColumn(2).setMinWidth(35);
            tableItens.getColumnModel().getColumn(2).setPreferredWidth(35);
            tableItens.getColumnModel().getColumn(2).setMaxWidth(35);
            tableItens.getColumnModel().getColumn(3).setMinWidth(150);
            tableItens.getColumnModel().getColumn(3).setPreferredWidth(150);
            tableItens.getColumnModel().getColumn(3).setMaxWidth(150);
            tableItens.getColumnModel().getColumn(5).setMinWidth(60);
            tableItens.getColumnModel().getColumn(5).setPreferredWidth(60);
            tableItens.getColumnModel().getColumn(5).setMaxWidth(60);
            tableItens.getColumnModel().getColumn(6).setMinWidth(100);
            tableItens.getColumnModel().getColumn(6).setPreferredWidth(100);
            tableItens.getColumnModel().getColumn(6).setMaxWidth(100);
            tableItens.getColumnModel().getColumn(7).setMinWidth(100);
            tableItens.getColumnModel().getColumn(7).setPreferredWidth(100);
            tableItens.getColumnModel().getColumn(7).setMaxWidth(100);
            tableItens.getColumnModel().getColumn(8).setMinWidth(110);
            tableItens.getColumnModel().getColumn(8).setPreferredWidth(110);
            tableItens.getColumnModel().getColumn(8).setMaxWidth(110);
            tableItens.getColumnModel().getColumn(9).setMinWidth(80);
            tableItens.getColumnModel().getColumn(9).setPreferredWidth(80);
            tableItens.getColumnModel().getColumn(9).setMaxWidth(80);
            tableItens.getColumnModel().getColumn(10).setMinWidth(0);
            tableItens.getColumnModel().getColumn(10).setPreferredWidth(0);
            tableItens.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        btnAddItem.setText("Adicionar Item");
        btnAddItem.setName("btnAddItem"); // NOI18N
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });

        btnDelItem.setText("Excluir Item");
        btnDelItem.setName("btnDelItem"); // NOI18N
        btnDelItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelItemActionPerformed(evt);
            }
        });

        txtValorTotal.setEditable(false);
        txtValorTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorTotal.setName("txtValorTotal"); // NOI18N

        jLabel7.setText("Total:");
        jLabel7.setName("jLabel7"); // NOI18N

        btnMarcarTodos.setText("Marcar Todos");
        btnMarcarTodos.setName("btnMarcarTodos"); // NOI18N
        btnMarcarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcarTodosActionPerformed(evt);
            }
        });

        btnAddPedido.setText("Criar Pedido de Venda");
        btnAddPedido.setName("btnAddPedido"); // NOI18N
        btnAddPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPedidoActionPerformed(evt);
            }
        });

        btnItensPerdidos.setBackground(new java.awt.Color(255, 51, 51));
        btnItensPerdidos.setText("Lançar Itens Perdidos");
        btnItensPerdidos.setName("btnItensPerdidos"); // NOI18N
        btnItensPerdidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItensPerdidosActionPerformed(evt);
            }
        });

        txtFrete.setName("txtFrete"); // NOI18N

        jLabel9.setText("Frete: R$");
        jLabel9.setName("jLabel9"); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnMarcarTodos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnItensPerdidos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddPedido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFrete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddItem)
                    .addComponent(btnDelItem)
                    .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(btnMarcarTodos)
                    .addComponent(btnAddPedido)
                    .addComponent(btnItensPerdidos)
                    .addComponent(txtFrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap())
        );

        tabItens.addTab("Itens", jPanel8);

        btnSalvar.setText("Salvar");
        btnSalvar.setName("btnSalvar"); // NOI18N
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jButton2.setText("Novo");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnSendCotacao.setText("Enviar Cotação");
        btnSendCotacao.setName("btnSendCotacao"); // NOI18N
        btnSendCotacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendCotacaoActionPerformed(evt);
            }
        });

        jButton14.setText("Alterações");
        jButton14.setName("jButton14"); // NOI18N
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabItens)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSendCotacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabItens)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(jButton2)
                    .addComponent(btnSendCotacao)
                    .addComponent(jButton14))
                .addContainerGap())
        );

        tabCotacoes.addTab("Cotação", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabCotacoes)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabCotacoes)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCondPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCondPagActionPerformed
        ProcurarCondicaoDePagamento pcp = new ProcurarCondicaoDePagamento(this.getClass().getSimpleName());
        Telas.AparecerTela(pcp);
    }//GEN-LAST:event_btnCondPagActionPerformed

    private void btnRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepActionPerformed
        ProcurarRepresentante pr = new ProcurarRepresentante(this.getClass().getSimpleName());
        Telas.AparecerTela(pr);
    }//GEN-LAST:event_btnRepActionPerformed

    private void btnVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVendedorActionPerformed
        ProcurarVendedor pv = new ProcurarVendedor(this.getClass().getSimpleName());
        Telas.AparecerTela(pv);
    }//GEN-LAST:event_btnVendedorActionPerformed

    private void btnProcurarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurarClienteActionPerformed
        ProcurarCliente pc = new ProcurarCliente(this.getClass().getSimpleName());
        Telas.AparecerTela(pc);
    }//GEN-LAST:event_btnProcurarClienteActionPerformed

    private void radioCadastradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioCadastradoActionPerformed
        clienteCadastrado();
        txtCliente.setText("");
    }//GEN-LAST:event_radioCadastradoActionPerformed

    private void radioNaoCadastradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNaoCadastradoActionPerformed
        clienteCadastrado();
        txtCliente.setText("");
    }//GEN-LAST:event_radioNaoCadastradoActionPerformed

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
        ItemCotacao ic = new ItemCotacao(this.getClass().getSimpleName());
        ic.setTitle("Item Cotação de Venda");
        Telas.AparecerTela(ic);
    }//GEN-LAST:event_btnAddItemActionPerformed

    private void tableItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableItensMouseClicked
        int row = tableItens.getSelectedRow();
        if (evt.getButton() == 1) {
            if (evt.getClickCount() == 2) {
                ItemCotacao ic = new ItemCotacao(this.getClass().getSimpleName());

                if (tableItens.getValueAt(row, 0).equals("")) {
                    ic.idItemCotacao = 0;
                } else {
                    ic.idItemCotacao = Integer.parseInt(tableItens.getValueAt(row, 0).toString());
                }

                ItemCotacao.txtcodigo.setText(tableItens.getValueAt(row, 3).toString());
                ItemCotacao.txtdesc.setText(tableItens.getValueAt(row, 4).toString());
                ItemCotacao.txtqtd.setText(tableItens.getValueAt(row, 5).toString());
                ItemCotacao.txtvalor.setText(tableItens.getValueAt(row, 6).toString());
                ItemCotacao.txtprazo.setText(tableItens.getValueAt(row, 8).toString().replace(" dias úteis", ""));

                Telas.AparecerTela(ic);
            }
        } else if (evt.getButton() == 3) {
            String pedido = tableItens.getValueAt(tableItens.getSelectedRow(), 9).toString();

            if (pedido.length() > 0) {
                JPopupMenu menu = new JPopupMenu();
                JMenuItem abrirPedido = new JMenuItem("Abrir Pedido");

                abrirPedido.addActionListener((ActionEvent e) -> {
                    PedidoVenda pv = new PedidoVenda();
                    PedidoVenda.idPedido = vpd.readIdPedido(pedido);
                    PedidoVenda.tabPedidos.setSelectedIndex(1);
                    PedidoVenda.lerPedido(pedido);

                    Telas.AparecerTelaAumentada(pv);
                });

                menu.add(abrirPedido);

                menu.show(evt.getComponent(), evt.getPoint().x, evt.getPoint().y);
            }
        }
    }//GEN-LAST:event_tableItensMouseClicked

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (txtCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente selecionado.");
        } else if (txtVendedor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum vendedor selecionado.");
        } else if (txtRep.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum representante selecionado.");
        } else if (txtCondPag.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhuma condição de pagamento selecionada.");
        } else {
            if (txtFrete.getText().length() == 0) {
                txtFrete.setText("0,00");
            }
            if (idCotacao == 0) {
                String cotacao = vcd.cotacaoAtual();

                try {
                    //Criar nova cotação
                    vcd.create(cotacao, Dates.CriarDataCurtaDBSemDataExistente(), txtCliente.getText(), radioCadastrado.isSelected(), "Ativo", txtVendedor.getText(), txtRep.getText(), txtCondPag.getText(), Valores.TransformarDinheiroEmValorDouble(txtFrete.getText()));

                    idCotacao = vcd.idCotacao(cotacao);

                    //Criar itens da cotação
                    for (int i = 0; i < tableItens.getRowCount(); i++) {
                        vcid.create(cotacao, Integer.parseInt(tableItens.getValueAt(i, 10).toString()), tableItens.getValueAt(i, 3).toString(), tableItens.getValueAt(i, 4).toString(), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 5).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 6).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 7).toString()), tableItens.getValueAt(i, 8).toString(), Boolean.valueOf(tableItens.getValueAt(i, 1).toString()));
                    }

                    //Criar documentos da cotação
                    for (int i = 0; i < tableDocs.getRowCount(); i++) {
                        if (tableDocs.getValueAt(i, 0).equals("")) {
                            //Localicação do documento original
                            File fileoriginal = new File(tableDocs.getValueAt(i, 4).toString());
                            //Pasta que será colocar o documento
                            File folder = new File("Q:/MIKE_ERP/cot_ven_arq/" + String.valueOf(cotacao));
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

                            vcdd.create(cotacao, tableDocs.getValueAt(i, 2).toString(), filecopy.toString());
                        }
                    }

                    //Criar observações da cotação
                    for (int i = 0; i < tableObs.getRowCount(); i++) {
                        vcod.create(cotacao, Dates.CriarDataCurtaDBComDataExistente(tableObs.getValueAt(i, 2).toString()), tableObs.getValueAt(i, 3).toString(), tableObs.getValueAt(i, 4).toString());
                    }

                    JOptionPane.showMessageDialog(null, "Cotação de Venda criada com sucesso!");
                } catch (SQLException e) {
                    String msg = "Erro ao criar Cotação de Venda.";
                    JOptionPane.showMessageDialog(null, msg);

                    new Thread() {
                        @Override
                        public void run() {
                            SendEmail.EnviarErro2(msg, e);
                        }
                    }.start();
                }

                lerCotacao(cotacao);

                updateTotal(cotacao);
            } else {
                String cotacao = txtCotacao.getText();

                try {
                    //Atualizar cotação
                    vcd.update(cotacao, txtCliente.getText(), radioCadastrado.isSelected(), txtVendedor.getText(), txtRep.getText(), txtCondPag.getText(), Valores.TransformarDinheiroEmValorDouble(txtFrete.getText()));

                    //Criar itens da cotação que não existiam
                    for (int i = 0; i < tableItens.getRowCount(); i++) {
                        if (tableItens.getValueAt(i, 0).equals("")) {
                            vcid.create(cotacao, Integer.parseInt(tableItens.getValueAt(i, 10).toString()), tableItens.getValueAt(i, 3).toString(), tableItens.getValueAt(i, 4).toString(), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 5).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 6).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 7).toString()), tableItens.getValueAt(i, 8).toString(), Boolean.valueOf(tableItens.getValueAt(i, 1).toString()));
                        } else {
                            vcid.update(Integer.parseInt(tableItens.getValueAt(i, 10).toString()), tableItens.getValueAt(i, 3).toString(), tableItens.getValueAt(i, 4).toString(), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 5).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 6).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 7).toString()), tableItens.getValueAt(i, 8).toString(), Boolean.valueOf(tableItens.getValueAt(i, 1).toString()), Integer.parseInt(tableItens.getValueAt(i, 0).toString()));
                        }
                    }

                    //Criar documentos da cotação que não existiam
                    for (int i = 0; i < tableDocs.getRowCount(); i++) {
                        if (tableDocs.getValueAt(i, 0).equals("")) {
                            //Localicação do documento original
                            File fileoriginal = new File(tableDocs.getValueAt(i, 4).toString());
                            //Pasta que será colocar o documento
                            File folder = new File("Q:/MIKE_ERP/cot_ven_arq/" + String.valueOf(cotacao));
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

                            vcdd.create(cotacao, tableDocs.getValueAt(i, 2).toString(), filecopy.toString());
                        }
                    }

                    //Criar observações da cotação
                    for (int i = 0; i < tableObs.getRowCount(); i++) {
                        if (tableObs.getValueAt(i, 0).equals("")) {
                            vcod.create(cotacao, Dates.InverterDataCurta(tableObs.getValueAt(i, 2).toString()), tableObs.getValueAt(i, 3).toString(), tableObs.getValueAt(i, 4).toString());
                        }
                    }

                    JOptionPane.showMessageDialog(null, "Cotação de Venda atualizada com sucesso.");
                } catch (SQLException e) {
                    String msg = "Erro ao atualizar Cotação de Vendas.";
                    JOptionPane.showMessageDialog(null, msg);

                    new Thread() {
                        @Override
                        public void run() {
                            SendEmail.EnviarErro2(msg, e);
                        }
                    }.start();
                }

                //Verificar alterações
                String id = txtCotacao.getText();
                String tipo = this.getClass().getSimpleName();
                String data = Dates.CriarDataCompletaParaDB();
                String user = Session.nome;
                if (!txtCliente.getText().equals(clienteOriginal)) {
                    ad.create(id, tipo, data, user, "Cliente", clienteOriginal, txtCliente.getText());
                }
                if (!txtCondPag.getText().equals(condicaoOriginal)) {
                    ad.create(id, tipo, data, user, "Condição de Pagamento", condicaoOriginal, txtCondPag.getText());
                }
                if (!txtRep.getText().equals(representanteOriginal)) {
                    ad.create(id, tipo, data, user, "Representante", representanteOriginal, txtRep.getText());
                }
                if (!txtVendedor.getText().equals(vendedorOriginal)) {
                    ad.create(id, tipo, data, user, "Vendedor", vendedorOriginal, txtVendedor.getText());
                }
                if (numDocsOriginal != tableDocs.getRowCount()) {
                    ad.create(id, tipo, data, user, "Número de Documentos", String.valueOf(numDocsOriginal), String.valueOf(tableDocs.getRowCount()));
                }
                if (numObsOriginal != tableObs.getRowCount()) {
                    ad.create(id, tipo, data, user, "Número de Observações", String.valueOf(numObsOriginal), String.valueOf(tableObs.getRowCount()));
                }
                if (numItensOriginal != tableItens.getRowCount()) {
                    ad.create(id, tipo, data, user, "Número de Itens", String.valueOf(numItensOriginal), String.valueOf(tableItens.getRowCount()));
                }

                lerCotacao(cotacao);

                updateTotal(cotacao);
            }
            lerCotacoes();
        }

        valoresOriginais();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void tableCotacoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCotacoesMouseClicked
        if (evt.getClickCount() == 2) {
            idCotacao = Integer.parseInt(tableCotacoes.getValueAt(tableCotacoes.getSelectedRow(), 0).toString());

            tabCotacoes.setSelectedIndex(1);

            String cotacao = tableCotacoes.getValueAt(tableCotacoes.getSelectedRow(), 2).toString();

            //Pegar dados da Cotação no DB
            lerCotacao(cotacao);
        }
    }//GEN-LAST:event_tableCotacoesMouseClicked

    private void btnAddObsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddObsActionPerformed
        AdicionarObs ao = new AdicionarObs(this.getClass().getSimpleName());
        Telas.AparecerTela(ao);
    }//GEN-LAST:event_btnAddObsActionPerformed

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

            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir a observação selecionada?", "Excluir observação", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                for (int i = 0; i < tableObs.getRowCount(); i++) {
                    if (tableObs.getValueAt(i, 1).equals(true)) {
                        vcod.delete(Integer.parseInt(tableObs.getValueAt(i, 0).toString()));
                    }
                }
                lerObsCotacao(txtCotacao.getText());
            }
        }
    }//GEN-LAST:event_btnDelObsActionPerformed

    private void tableObsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableObsMouseClicked
        if (tableObs.getSelectedColumn() == 1) {
            String user = tableObs.getValueAt(tableObs.getSelectedRow(), 3).toString();
            if (!user.equals(Session.nome)) {
                JOptionPane.showMessageDialog(null, "Usuário não é dono da observação.");
                tableObs.setValueAt(false, tableObs.getSelectedRow(), 1);
            }
        }
    }//GEN-LAST:event_tableObsMouseClicked

    private void btnAddDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDocActionPerformed
        ProcurarDocumento pd = new ProcurarDocumento(this.getClass().getSimpleName());
        Telas.AparecerTela(pd);
    }//GEN-LAST:event_btnAddDocActionPerformed

    private void btnDelItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelItemActionPerformed
        int numTrue = 0;
        for (int i = 0; i < tableItens.getRowCount(); i++) {
            if (tableItens.getValueAt(i, 2).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir o(s) item(ns) selecionado(s)?", "Excluir Item", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                for (int i = 0; i < tableItens.getRowCount(); i++) {
                    if (tableItens.getValueAt(i, 2).equals(true)) {
                        vcid.delete(Integer.parseInt(tableItens.getValueAt(i, 0).toString()));
                    }
                }

                String id = txtCotacao.getText();
                String tipo = this.getClass().getSimpleName();
                String data = Dates.CriarDataCompletaParaDB();
                String user = Session.nome;

                String cotacao = txtCotacao.getText();

                //Pegar itens da Cotação no DB
                lerItensCotacao(cotacao);

                ad.create(id, tipo, data, user, "Número de Itens", String.valueOf(numItensOriginal), String.valueOf(tableItens.getRowCount()));

                lerCotacao(cotacao);

                valoresOriginais();
            }
        }
    }//GEN-LAST:event_btnDelItemActionPerformed

    private void btnDelDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelDocActionPerformed
        int numTrue = 0;
        for (int i = 0; i < tableDocs.getRowCount(); i++) {
            if (tableDocs.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir o(s) item(ns) selecionado(s)?", "Excluir Item", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                for (int i = 0; i < tableDocs.getRowCount(); i++) {
                    if (tableDocs.getValueAt(i, 1).equals(true)) {
                        vcdd.delete(Integer.parseInt(tableDocs.getValueAt(i, 0).toString()), tableDocs.getValueAt(i, 3).toString());
                    }
                }
                //Pegar Observações da Cotação no DB
                lerDocumentosCotacao(txtCotacao.getText());
            }
        }
    }//GEN-LAST:event_btnDelDocActionPerformed

    private void btnMarcarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcarTodosActionPerformed
        if (btnMarcarTodos.getText().equals("Marcar Todos")) {
            for (int i = 0; i < tableItens.getRowCount(); i++) {
                tableItens.setValueAt(true, i, 2);
            }
            btnMarcarTodos.setText("Desmarcar Todos");
        } else {
            for (int i = 0; i < tableItens.getRowCount(); i++) {
                tableItens.setValueAt(false, i, 2);
            }
            btnMarcarTodos.setText("Marcar Todos");
        }
    }//GEN-LAST:event_btnMarcarTodosActionPerformed

    private void btnSendCotacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendCotacaoActionPerformed
        if (txtCotacao.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione uma cotação primeiro.");
        } else {
            updateTotal(txtCotacao.getText());

            JOptionPane.showMessageDialog(null, "Enviando orçamento.");
            try {
                Connection con = ConnectionFactory.getConnection();

                HashMap<String, Object> para = new HashMap<>();
                para.put("cotacao", txtCotacao.getText());

//                InputStream is = getClass().getResourceAsStream("/Reports/orcamento.jrxml");
                InputStream is = getClass().getResourceAsStream("/Reports/primeiro_exemplo.jrxml");
                if (is == null) {
                    JOptionPane.showMessageDialog(null, "Report não foi encontrado.");
                } else {
//                    String file = "Q:\\MIKE_ERP\\reports\\orcamento.jrxml";
//                    String file = "Q:\\MIKE_ERP\\reports\\primeiro_exemplo.jrxml";
                    JasperReport jr = JasperCompileManager.compileReport(is);

                    JasperPrint j = JasperFillManager.fillReport(jr, para, con);

//                    JasperViewer.viewReport(j, false);
                    //Pasta que será colocar o documento
                    File folder = new File("Q:/MIKE_ERP/cot_ven_arq/" + txtCotacao.getText());

                    //Criar pasta no caso de já não existir
                    folder.mkdirs();

                    File f = new File("Q:\\MIKE_ERP\\cot_ven_arq\\" + txtCotacao.getText() + "\\orcamento.pdf");

                    JasperExportManager.exportReportToPdfFile(j, f.getPath());

                    int idCliente = cd.getIdNome(txtCliente.getText());
                    List<ClientesContatosBean> destinatarios = ccd.readDestinatariosOrcamento(idCliente);

                    SendEmail.EnviarOrcamento(f, Session.emailfabrica);

                    if (destinatarios.size() > 0) {
                        destinatarios.forEach((destinatario) -> {
                            SendEmail.EnviarOrcamento(f, destinatario.getEmail());
                        });

                        JOptionPane.showMessageDialog(null, "Orçamento enviado com sucesso!");
                    }
                }
            } catch (Exception e) {
                String msg = "Erro ao gerar relatório.\n" + e.getLocalizedMessage() + "\n" + Arrays.toString(e.getStackTrace());
                JOptionPane.showMessageDialog(null, msg);

                new Thread() {
                    @Override
                    public void run() {
                        SendEmail.EnviarErro2(msg, e);
                    }
                }.start();
            }
        }
    }//GEN-LAST:event_btnSendCotacaoActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        if (txtCotacao.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione ou salve uma cotação primeiro.");
        } else {
            HistoricoAlteracao ha = new HistoricoAlteracao(this.getClass().getSimpleName());
            Telas.AparecerTela(ha);
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void btnAddPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPedidoActionPerformed
        int numTrue = 0, numNaoCadastrado = 0, numPedido = 0;
        for (int i = 0; i < tableItens.getRowCount(); i++) {
            if (tableItens.getValueAt(i, 2).equals(true)) {
                numTrue++;
                if (tableItens.getValueAt(i, 1).equals(false)) {
                    numNaoCadastrado++;
                }
                if (!tableItens.getValueAt(i, 9).equals("")) {
                    numPedido++;
                }
            }
        }
        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado.");
        } else if (radioNaoCadastrado.isSelected()) {
            JOptionPane.showMessageDialog(null, "Não é possível criar pedidos de clientes não cadastrados.");
        } else if (numNaoCadastrado > 0) {
            JOptionPane.showMessageDialog(null, "Não é possível criar pedidos com produtos não cadastrados.");
        } else if (!txtCliente.getText().equals(clienteOriginal) || !txtCondPag.getText().equals(condicaoOriginal) || !txtRep.getText().equals(representanteOriginal) || !txtVendedor.getText().equals(vendedorOriginal) || numDocsOriginal != tableDocs.getRowCount() || numObsOriginal != tableObs.getRowCount() || numItensOriginal != tableItens.getRowCount()) {
            JOptionPane.showMessageDialog(null, "Salve a cotação para que as alterações entrem em vigor primeiro.");
        } else if (numPedido > 0) {
            JOptionPane.showMessageDialog(null, "Itens com Pedido selecionados.");
        } else {
            String[] opt = {"Novo Pedido", "Pedido já existente"};
            int resp = JOptionPane.showOptionDialog(null, "Incluir itens em um:", "Pedido de Venda", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, frameIcon, opt, iconable);
            switch (resp) {
                case 0:
                    criarPedidoVenda("");
                    break;
                case 1:
                    EscolherPedido ep = new EscolherPedido(this.getClass().getSimpleName(), txtCliente.getText());
                    Telas.AparecerTela(ep);
                    break;
            }
        }
    }//GEN-LAST:event_btnAddPedidoActionPerformed

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        lerCotacoes();
    }//GEN-LAST:event_cbStatusActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        zerarCampos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        int numTrue = 0, desativados = 0;
        for (int i = 0; i < tableCotacoes.getRowCount(); i++) {
            if (tableCotacoes.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
            if (tableCotacoes.getValueAt(i, 4).equals("Desativado")) {
                desativados++;
            }
        }
        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma Cotação selecionada.");
        } else if (desativados > 0) {
            JOptionPane.showMessageDialog(null, "Cotações desativadas selecionadas.");
        } else {
            String tipo = this.getClass().getSimpleName();
            String data = Dates.CriarDataCompletaParaDB();
            String user = Session.nome;
            if (numTrue == 1) {
                int resp = JOptionPane.showConfirmDialog(null, "Quer mesmo desativar a Cotação de Venda selecionada?", "Desativar Cotação", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    for (int i = 0; i < tableCotacoes.getRowCount(); i++) {
                        if (tableCotacoes.getValueAt(i, 1).equals(true)) {
                            String id = tableCotacoes.getValueAt(i, 2).toString();
                            String motivo = JOptionPane.showInputDialog(null, "Qual o motivo para a desativação da Cotação " + id + "?", "Motivo", JOptionPane.YES_NO_OPTION);
                            if (motivo.length() > 0) {
                                vcd.desativarCotacao(tableCotacoes.getValueAt(i, 2).toString(), motivo);
                                ad.create(id, tipo, data, user, "Status", tableCotacoes.getValueAt(i, 4).toString(), "Desativado");
                            } else {
                                JOptionPane.showMessageDialog(null, "Sem motivo digitado.");
                            }
                        }
                    }
                }
            } else {
                int resp = JOptionPane.showConfirmDialog(null, "Quer mesmo desativar as Cotações de Venda selecionadas?", "Desativar Cotações", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    for (int i = 0; i < tableCotacoes.getRowCount(); i++) {
                        if (tableCotacoes.getValueAt(i, 1).equals(true)) {
                            String id = tableCotacoes.getValueAt(i, 2).toString();
                            String motivo = JOptionPane.showInputDialog(null, "Qual o motivo para a desativação da Cotação " + id + "?", "Motivo", JOptionPane.YES_NO_OPTION);
                            if (motivo.length() > 0) {
                                vcd.desativarCotacao(tableCotacoes.getValueAt(i, 2).toString(), motivo);
                                ad.create(id, tipo, data, user, "Status", tableCotacoes.getValueAt(i, 4).toString(), "Desativado");
                            } else {
                                JOptionPane.showMessageDialog(null, "Sem motivo digitado.");
                            }
                        }
                    }
                }
            }
        }
        lerCotacoes();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void btnMotivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMotivoActionPerformed
        JOptionPane.showMessageDialog(null, vcd.readMotivo(txtCotacao.getText()));
    }//GEN-LAST:event_btnMotivoActionPerformed

    private void tableDocsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDocsMouseClicked
        if (evt.getClickCount() == 2) {
            Docs.open(tableDocs.getValueAt(tableDocs.getSelectedRow(), 3).toString());
        }
    }//GEN-LAST:event_tableDocsMouseClicked

    private void tableItensMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableItensMouseReleased
        int r = tableItens.rowAtPoint(evt.getPoint());
        if (r >= 0 && r < tableItens.getRowCount()) {
            tableItens.setRowSelectionInterval(r, r);
        } else {
            tableItens.clearSelection();
        }
    }//GEN-LAST:event_tableItensMouseReleased

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        lerCotacoes();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void btnItensPerdidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItensPerdidosActionPerformed
        int numTrue = 0;

        for (int i = 0; i < tableItens.getRowCount(); i++) {
            if (tableItens.getValueAt(i, 2).equals(true)) {
                numTrue++;
            }
        }

        if (txtCotacao.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione uma Cotação primeiro.");
        } else if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Confirma que os itens foram perdidos?", "Itens Perdidos", JOptionPane.YES_NO_OPTION);

            if (resp == 0) {
                String cotacaoAtual = txtCotacao.getText();
                String cotacao = cotacaoAtual + "P";
                String motivo = JOptionPane.showInputDialog(null, "Qual o motivo para ter perdido os itens?", "Motivo", JOptionPane.YES_NO_OPTION);

                try {
                    if (!vcd.checkCotacao(cotacao)) {
                        vcd.create(cotacao, Dates.CriarDataCurtaDBSemDataExistente(), txtCliente.getText(), radioCadastrado.isSelected(), "Perdido", txtVendedor.getText(), txtRep.getText(), txtCondPag.getText(), Valores.TransformarDinheiroEmValorDouble(txtFrete.getText()));
                        JOptionPane.showMessageDialog(null, "Cotação Perdida lançada com sucesso.\nLançando itens na Cotação.");
                    }

                    vcd.lancarMotivoPerdido(cotacao, motivo);
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

                for (int i = 0; i < tableItens.getRowCount(); i++) {
                    if (tableItens.getValueAt(i, 2).equals(true)) {
                        try {
                            vcid.create(cotacao, Integer.parseInt(tableItens.getValueAt(i, 10).toString()), tableItens.getValueAt(i, 3).toString(), tableItens.getValueAt(i, 4).toString(), Valores.TransformarStringDinheiroEmDouble(tableItens.getValueAt(i, 5).toString()), Valores.TransformarStringDinheiroEmDouble(tableItens.getValueAt(i, 6).toString()), Valores.TransformarStringDinheiroEmDouble(tableItens.getValueAt(i, 7).toString()), tableItens.getValueAt(i, 8).toString(), Boolean.valueOf(tableItens.getValueAt(i, 1).toString()));

                            vcid.delete(Integer.parseInt(tableItens.getValueAt(i, 0).toString()));

                            JOptionPane.showMessageDialog(null, "Itens lançados com sucesso.");
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

                if (tableItens.getRowCount() == numTrue) {
                    vcd.deletarCotacao(cotacaoAtual);
                } else {
                    alterarStatus(cotacaoAtual);
                }
                lerCotacao(cotacao);
            }
        }
    }//GEN-LAST:event_btnItensPerdidosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAddDoc;
    public static javax.swing.JButton btnAddItem;
    public static javax.swing.JButton btnAddObs;
    public static javax.swing.JButton btnAddPedido;
    public static javax.swing.JButton btnCondPag;
    public static javax.swing.JButton btnDelDoc;
    public static javax.swing.JButton btnDelItem;
    public static javax.swing.JButton btnDelObs;
    public static javax.swing.JButton btnItensPerdidos;
    public static javax.swing.JButton btnMarcarTodos;
    public static javax.swing.JButton btnMotivo;
    public static javax.swing.JButton btnProcurarCliente;
    public static javax.swing.JButton btnRep;
    public static javax.swing.JButton btnSalvar;
    public static javax.swing.JButton btnSendCotacao;
    public static javax.swing.JButton btnVendedor;
    public javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JComboBox<String> cbStatus;
    public javax.swing.JButton jButton14;
    public javax.swing.JButton jButton15;
    public javax.swing.JButton jButton2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel10;
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
    public static javax.swing.JRadioButton radioCadastrado;
    public static javax.swing.JRadioButton radioNaoCadastrado;
    public javax.swing.JTabbedPane tabCotacoes;
    public javax.swing.JTabbedPane tabItens;
    public static javax.swing.JTable tableCotacoes;
    public static javax.swing.JTable tableDocs;
    public static javax.swing.JTable tableItens;
    public static javax.swing.JTable tableObs;
    public static javax.swing.JTextField txtCliente;
    public static javax.swing.JTextField txtCondPag;
    public static javax.swing.JTextField txtCotacao;
    public static javax.swing.JTextField txtDataAbertura;
    public static javax.swing.JTextField txtFrete;
    public static javax.swing.JTextField txtPesquisa;
    public static javax.swing.JTextField txtRep;
    public static javax.swing.JTextField txtStatus;
    public static javax.swing.JTextField txtValorTotal;
    public static javax.swing.JTextField txtVendedor;
    // End of variables declaration//GEN-END:variables
}
