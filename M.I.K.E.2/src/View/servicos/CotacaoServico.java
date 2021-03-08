/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.servicos;

import Bean.ServicoOrcamentoItensBean;
import Bean.ServicoOrcamentoBean;
import Bean.ServicoOrcamentoDocumentosBean;
import Bean.ServicoPedidoBean;
import Bean.ServicoPedidoDocumentosBean;
import Bean.ServicoPedidoItensBean;
import Connection.ConnectionFactory;
import DAO.ServicoOrcamentoItensDAO;
import DAO.ServicoOrcamentoDAO;
import DAO.ServicoOrcamentoDocumentosDAO;
import DAO.ServicoPedidoDAO;
import DAO.ServicoPedidoDocumentosDAO;
import DAO.ServicoPedidoItensDAO;
import Methods.Dates;
import Methods.SendEmail;
import Methods.Telas;
import View.Geral.ItemCotacao;
import View.Geral.ProcurarCliente;
import View.Geral.ProcurarCondicaoDePagamento;
import View.Geral.ProcurarDocumento;
import View.Geral.ProcurarRepresentante;
import View.Geral.ProcurarVendedor;
import View.TelaPrincipal;
import static View.TelaPrincipal.jDesktopPane1;
import static View.servicos.PedidoServico.txtnumeropedido;
import java.awt.AWTException;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Marcos Filho
 */
public class CotacaoServico extends javax.swing.JInternalFrame {

    static ServicoOrcamentoItensDAO iosd = new ServicoOrcamentoItensDAO();
    static ServicoOrcamentoDocumentosDAO sodd = new ServicoOrcamentoDocumentosDAO();
    static ServicoOrcamentoDAO sod = new ServicoOrcamentoDAO();

    /**
     * Creates new form OrcamentoServico
     */
    public CotacaoServico() {
        initComponents();
        filltableorcamentoservico();
        tableitens();
        txtvalor();
        centertable();
    }

    public void lerCotacao(String cotacao) {
        txtnumeroorcamento.setText(cotacao);

        sod.click(cotacao).forEach(sob -> {
            if (sob.getClientecadastro().equals("true")) {
                radioc.setSelected(true);
            } else {
                radionc.setSelected(true);
            }
            txtnomecliente.setText(sob.getCliente());
            txtcondicao.setText(sob.getCondicao());
            txtrepresentante.setText(sob.getRepresentante());
            txtvendedor.setText(sob.getVendedor());
            txtstatus.setText(sob.getStatus());
            txtnotes.setText(sob.getNotes());
            txtdata.setText(sob.getData());
        });
        if (radionc.isSelected()) {
            btncliente.setEnabled(false);
            txtnomecliente.setEditable(true);
        }

        lerItensCotacao(cotacao);
        
        lerDocsCotacao(cotacao);

        txtvalor();

        checkstatus();
    }
    
    public void lerDocsCotacao(String cotacao) {
        DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
        modeldoc.setNumRows(0);

        sodd.readitens(cotacao).forEach(sodb -> {
            modeldoc.addRow(new Object[]{
                sodb.getId(),
                false,
                sodb.getDescricao(),
                sodb.getLocal()
            });
        });
    }

    public static void filltableorcamentoservico() {
        int cb = cbstatus.getSelectedIndex();
        switch (cb) {
            case 0:
                DefaultTableModel model = (DefaultTableModel) tableorcamentoservico.getModel();
                model.setNumRows(0);
                sod = new ServicoOrcamentoDAO();

                sod.reademaberto().forEach((sob) -> {
                    model.addRow(new Object[]{
                        sob.getIdtela(),
                        sob.getCliente(),
                        sob.getStatus()
                    });
                });
                break;

            case 5:
                DefaultTableModel model5 = (DefaultTableModel) tableorcamentoservico.getModel();
                model5.setNumRows(0);
                sod = new ServicoOrcamentoDAO();

                sod.read().forEach((sob) -> {
                    model5.addRow(new Object[]{
                        sob.getIdtela(),
                        sob.getCliente(),
                        sob.getStatus()
                    });
                });
                break;

            default:
                DefaultTableModel modeld = (DefaultTableModel) tableorcamentoservico.getModel();
                modeld.setNumRows(0);
                sod = new ServicoOrcamentoDAO();

                sod.readstatus(cbstatus.getSelectedItem().toString()).forEach((sob) -> {
                    modeld.addRow(new Object[]{
                        sob.getIdtela(),
                        sob.getCliente(),
                        sob.getStatus()
                    });
                });

        }
    }

    public static void tableitens() {
        DefaultTableModel modelti = (DefaultTableModel) tableitens.getModel();
        modelti.setNumRows(0);
    }

    public static void txtvalor() {
        if (tableitens.getRowCount() < 1) {
            txttotal.setText("");
        } else {
            float vt = 0;

            for (int i = 0; i < tableitens.getRowCount(); i++) {
                String v = tableitens.getValueAt(i, 6).toString().replace(".", "");
                String valor = v.replace(",", ".");
                float vp = Float.parseFloat(valor);
                vt = vt + vp;
            }
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            String valorf = formatter.format(vt);
            txttotal.setText(String.valueOf(valorf));
        }
    }

    public static void tableitensatualizar() {
        DefaultTableModel model = (DefaultTableModel) tableitens.getModel();
        model.setNumRows(0);
        iosd = new ServicoOrcamentoItensDAO();

        iosd.readitens(txtnumeroorcamento.getText()).forEach((iosb) -> {
            model.addRow(new Object[]{
                false,
                iosb.getId(),
                iosb.getCodigo(),
                iosb.getDesc(),
                iosb.getQtd(),
                iosb.getValor(),
                iosb.getTotal(),
                iosb.getPrazo(),
                iosb.getDas()
            });
        });
    }

    public static void tabledocumentosatualizar() {
        DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
        modeldoc.setNumRows(0);
        sodd = new ServicoOrcamentoDocumentosDAO();

        sodd.readitens(txtnumeroorcamento.getText()).forEach((sodb) -> {
            modeldoc.addRow(new Object[]{
                false,
                sodb.getId(),
                sodb.getDescricao(),
                sodb.getLocal()
            });
        });
    }

    public static void checkstatus() {
        if (txtstatus.getText().equals("Fechado")) {
            radioc.setEnabled(false);
            radionc.setEnabled(false);
            txtnomecliente.setEnabled(false);
            btncliente.setEnabled(false);
            txtcondicao.setEnabled(false);
            btncondicao.setEnabled(false);
            txtrepresentante.setEnabled(false);
            btnrepresentante.setEnabled(false);
            txtvendedor.setEnabled(false);
            btnvendedor.setEnabled(false);
            txtnotes.setEnabled(false);
//            tabledocumentos.setEnabled(false);
            btnincluirdoc.setEnabled(false);
            btnexcluirdoc.setEnabled(false);
            btnincluiritem.setEnabled(false);
            btnexcluiritem.setEnabled(false);
            btnsalvarorcamento.setEnabled(false);
            btncriarpedido.setEnabled(false);
            btncancelarorcamento.setEnabled(false);
        } else {
            radioc.setEnabled(true);
            radionc.setEnabled(true);
            txtnomecliente.setEnabled(true);
            btncliente.setEnabled(true);
            txtcondicao.setEnabled(true);
            btncondicao.setEnabled(true);
            txtrepresentante.setEnabled(true);
            btnrepresentante.setEnabled(true);
            txtvendedor.setEnabled(true);
            btnvendedor.setEnabled(true);
            txtnotes.setEnabled(true);
            tabledocumentos.setEnabled(true);
            btnincluirdoc.setEnabled(true);
            btnexcluirdoc.setEnabled(true);
            btnincluiritem.setEnabled(true);
            btnexcluiritem.setEnabled(true);
            btnsalvarorcamento.setEnabled(true);
            btncriarpedido.setEnabled(true);
            btncancelarorcamento.setEnabled(true);
        }
    }

    public static void centertable() {
        int cc = tableitens.getColumnCount();
        for (int i = 0; i < cc - 1; i++) {
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            tableitens.getColumnModel().getColumn(i + 1).setCellRenderer(centerRenderer);
        }
//        for (int i = 0; i < cc; i++) {
//            DefaultTableCellHeaderRenderer centerHeader = new DefaultTableCellHeaderRenderer();
//            centerHeader.setHorizontalAlignment(SwingConstants.CENTER);
//            tableitens.getTableHeader().setDefaultRenderer(centerHeader);
//        }

//        To center all columns with String data you can do:
//
//        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//        table.setDefaultRenderer(String.class, centerRenderer);
    }

    public static void clienteif() {
        Container c = txtnomecliente.getParent().getParent();
        Point p = txtnomecliente.getLocation();
        ProcuraCliente t = new ProcuraCliente("ServiçoCotação");
        BasicInternalFrameUI bi = (BasicInternalFrameUI) t.getUI();
        bi.setNorthPane(null);
        t.setSize(txtnomecliente.getWidth(), t.getHeight());
        t.setLocation(p.x + 7, p.y + txtnomecliente.getHeight() + 7);
        c.add(t);
        t.setVisible(false);
        c.setComponentZOrder(t, 0);
    }

    public static void lerItensCotacao(String cotacao) {
        DefaultTableModel model = (DefaultTableModel) tableitens.getModel();
        model.setNumRows(0);

        iosd.readitens(cotacao).forEach(iosb -> {
            model.addRow(new Object[]{
                false,
                iosb.getId(),
                iosb.getCodigo(),
                iosb.getDesc(),
                iosb.getQtd(),
                iosb.getValor(),
                iosb.getTotal(),
                iosb.getPrazo(),
                iosb.getDas()
            });
        });
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jButton7 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        taborcamentos = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableorcamentoservico = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtpesquisa = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        cbstatus = new javax.swing.JComboBox<>();
        jButton11 = new javax.swing.JButton();
        PanelOrcamentoServico = new javax.swing.JPanel();
        panel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtnomecliente = new javax.swing.JTextField();
        btncliente = new javax.swing.JButton();
        radioc = new javax.swing.JRadioButton();
        radionc = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtnumeroorcamento = new javax.swing.JTextField();
        txtstatus = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtdata = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtvendedor = new javax.swing.JTextField();
        txtrepresentante = new javax.swing.JTextField();
        txtcondicao = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btncondicao = new javax.swing.JButton();
        btnrepresentante = new javax.swing.JButton();
        btnvendedor = new javax.swing.JButton();
        btnsalvarorcamento = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableitens = new javax.swing.JTable();
        btnincluiritem = new javax.swing.JButton();
        txttotal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnexcluiritem = new javax.swing.JButton();
        btnAll = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtnotes = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabledocumentos = new javax.swing.JTable();
        btnincluirdoc = new javax.swing.JButton();
        btnexcluirdoc = new javax.swing.JButton();
        btncriarpedido = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        btncancelarorcamento = new javax.swing.JButton();
        btnnovoorcamento = new javax.swing.JButton();

        jButton7.setText("jButton7");

        setClosable(true);
        setTitle("Cotação de Serviço");

        taborcamentos.setPreferredSize(new java.awt.Dimension(900, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1007, 600));

        tableorcamentoservico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableorcamentoservico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableorcamentoservicoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableorcamentoservico);
        if (tableorcamentoservico.getColumnModel().getColumnCount() > 0) {
            tableorcamentoservico.getColumnModel().getColumn(0).setMinWidth(80);
            tableorcamentoservico.getColumnModel().getColumn(0).setPreferredWidth(80);
            tableorcamentoservico.getColumnModel().getColumn(0).setMaxWidth(80);
            tableorcamentoservico.getColumnModel().getColumn(1).setResizable(false);
            tableorcamentoservico.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Procura"));

        jLabel4.setText("Pesquisa");

        txtpesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtpesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));

        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Aberto", "Ativo", "Pedido Parcial", "Fechado", "Cancelado", "Todos" }));
        cbstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbstatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton11.setText("Novo");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1116, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton11)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11)
                .addContainerGap())
        );

        taborcamentos.addTab("Lista de Cotações", jPanel1);

        PanelOrcamentoServico.setBackground(new java.awt.Color(255, 255, 255));
        PanelOrcamentoServico.setPreferredSize(new java.awt.Dimension(836, 600));

        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        jLabel3.setText("Cliente");

        txtnomecliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnomeclienteKeyReleased(evt);
            }
        });

        btncliente.setText("Procurar");
        btncliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclienteActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioc);
        radioc.setSelected(true);
        radioc.setText("Cadastrado");
        radioc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiocActionPerformed(evt);
            }
        });

        buttonGroup1.add(radionc);
        radionc.setText("Não Cadastrado");
        radionc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioncActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(radioc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radionc))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncliente)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioc)
                    .addComponent(radionc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtnomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Orçamento"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Nº");

        txtnumeroorcamento.setEditable(false);
        txtnumeroorcamento.setSelectionColor(new java.awt.Color(255, 255, 255));

        txtstatus.setEditable(false);
        txtstatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setText("Status");

        jButton4.setText("Imprimir");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel9.setText("Data");

        txtdata.setEditable(false);
        txtdata.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnumeroorcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 47, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdata)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtnumeroorcamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtdata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Comercial"));

        txtvendedor.setEditable(false);

        txtrepresentante.setEditable(false);

        txtcondicao.setEditable(false);

        jLabel5.setText("Vendedor");

        jLabel6.setText("Representante");

        jLabel7.setText("Condição de Pagamento");

        btncondicao.setText("Procurar");
        btncondicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncondicaoActionPerformed(evt);
            }
        });

        btnrepresentante.setText("Procurar");
        btnrepresentante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrepresentanteActionPerformed(evt);
            }
        });

        btnvendedor.setText("Procurar");
        btnvendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnvendedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtrepresentante, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                    .addComponent(txtvendedor)
                    .addComponent(txtcondicao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btncondicao)
                    .addComponent(btnrepresentante)
                    .addComponent(btnvendedor))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcondicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(btncondicao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtrepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(btnrepresentante))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnvendedor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnsalvarorcamento.setText("Salvar");
        btnsalvarorcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalvarorcamentoActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Itens"));

        tableitens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Código", "Descrição", "Qtde", "Valor Unitário R$", "Total R$", "Prazo de Entrega", "DAS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableitens.getTableHeader().setReorderingAllowed(false);
        tableitens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableitensMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableitensMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tableitens);
        if (tableitens.getColumnModel().getColumnCount() > 0) {
            tableitens.getColumnModel().getColumn(0).setMinWidth(30);
            tableitens.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableitens.getColumnModel().getColumn(0).setMaxWidth(30);
            tableitens.getColumnModel().getColumn(1).setMinWidth(0);
            tableitens.getColumnModel().getColumn(1).setPreferredWidth(0);
            tableitens.getColumnModel().getColumn(1).setMaxWidth(0);
            tableitens.getColumnModel().getColumn(2).setMinWidth(90);
            tableitens.getColumnModel().getColumn(2).setPreferredWidth(90);
            tableitens.getColumnModel().getColumn(2).setMaxWidth(90);
            tableitens.getColumnModel().getColumn(4).setMinWidth(60);
            tableitens.getColumnModel().getColumn(4).setPreferredWidth(60);
            tableitens.getColumnModel().getColumn(4).setMaxWidth(60);
            tableitens.getColumnModel().getColumn(5).setMinWidth(110);
            tableitens.getColumnModel().getColumn(5).setPreferredWidth(110);
            tableitens.getColumnModel().getColumn(5).setMaxWidth(110);
            tableitens.getColumnModel().getColumn(6).setMinWidth(80);
            tableitens.getColumnModel().getColumn(6).setPreferredWidth(80);
            tableitens.getColumnModel().getColumn(6).setMaxWidth(80);
            tableitens.getColumnModel().getColumn(7).setMinWidth(110);
            tableitens.getColumnModel().getColumn(7).setPreferredWidth(110);
            tableitens.getColumnModel().getColumn(7).setMaxWidth(110);
            tableitens.getColumnModel().getColumn(8).setMinWidth(80);
            tableitens.getColumnModel().getColumn(8).setPreferredWidth(80);
            tableitens.getColumnModel().getColumn(8).setMaxWidth(80);
        }

        btnincluiritem.setText("Incluir");
        btnincluiritem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnincluiritemActionPerformed(evt);
            }
        });

        txttotal.setEditable(false);
        txttotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setText("Total: R$");

        btnexcluiritem.setText("Excluir");
        btnexcluiritem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexcluiritemActionPerformed(evt);
            }
        });

        btnAll.setText("Selecionar Todos");
        btnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(btnAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnexcluiritem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnincluiritem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnincluiritem)
                    .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(btnexcluiritem)
                    .addComponent(btnAll)))
        );

        txtnotes.setColumns(20);
        txtnotes.setRows(5);
        jScrollPane7.setViewportView(txtnotes);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1104, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Anotações", jPanel7);

        tabledocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "", "Descrição", "Local", "Local Original"
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
        tabledocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabledocumentosMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tabledocumentos);
        if (tabledocumentos.getColumnModel().getColumnCount() > 0) {
            tabledocumentos.getColumnModel().getColumn(0).setMinWidth(0);
            tabledocumentos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabledocumentos.getColumnModel().getColumn(0).setMaxWidth(0);
            tabledocumentos.getColumnModel().getColumn(1).setMinWidth(40);
            tabledocumentos.getColumnModel().getColumn(1).setMaxWidth(40);
            tabledocumentos.getColumnModel().getColumn(2).setMinWidth(500);
            tabledocumentos.getColumnModel().getColumn(2).setPreferredWidth(500);
            tabledocumentos.getColumnModel().getColumn(2).setMaxWidth(500);
            tabledocumentos.getColumnModel().getColumn(4).setMinWidth(0);
            tabledocumentos.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabledocumentos.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        btnincluirdoc.setText("Incluir");
        btnincluirdoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnincluirdocActionPerformed(evt);
            }
        });

        btnexcluirdoc.setText("Excluir");
        btnexcluirdoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexcluirdocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1104, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnexcluirdoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnincluirdoc)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnincluirdoc)
                    .addComponent(btnexcluirdoc))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Documentos", jPanel10);

        btncriarpedido.setText("Criar Pedido");
        btncriarpedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncriarpedidoActionPerformed(evt);
            }
        });

        btncancelar.setText("Cancelar");
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });

        btncancelarorcamento.setText("Cancelar Cotação");
        btncancelarorcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarorcamentoActionPerformed(evt);
            }
        });

        btnnovoorcamento.setText("Nova Cotação");
        btnnovoorcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnovoorcamentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelOrcamentoServicoLayout = new javax.swing.GroupLayout(PanelOrcamentoServico);
        PanelOrcamentoServico.setLayout(PanelOrcamentoServicoLayout);
        PanelOrcamentoServicoLayout.setHorizontalGroup(
            PanelOrcamentoServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelOrcamentoServicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelOrcamentoServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelOrcamentoServicoLayout.createSequentialGroup()
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelOrcamentoServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelOrcamentoServicoLayout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelOrcamentoServicoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnnovoorcamento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncancelarorcamento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncriarpedido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnsalvarorcamento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncancelar)))))
                .addContainerGap())
        );
        PanelOrcamentoServicoLayout.setVerticalGroup(
            PanelOrcamentoServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelOrcamentoServicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelOrcamentoServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(PanelOrcamentoServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsalvarorcamento)
                    .addComponent(btncriarpedido)
                    .addComponent(btncancelar)
                    .addComponent(btncancelarorcamento)
                    .addComponent(btnnovoorcamento))
                .addContainerGap())
        );

        taborcamentos.addTab("Detalhes da Cotação", PanelOrcamentoServico);

        jScrollPane3.setViewportView(taborcamentos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1134, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void radioncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioncActionPerformed
        btncliente.setEnabled(false);
        txtnomecliente.setText("");
        txtnomecliente.setEditable(true);
        txtnomecliente.requestFocus();
        txtcondicao.setText("");
        txtrepresentante.setText("");
        txtvendedor.setText("");
    }//GEN-LAST:event_radioncActionPerformed

    private void radiocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiocActionPerformed
        btncliente.setEnabled(true);
        txtnomecliente.setText("");
    }//GEN-LAST:event_radiocActionPerformed

    private void btnclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclienteActionPerformed
        ProcurarCliente p = new ProcurarCliente("ServiçoCotação");
        Telas.AparecerTela(p);
    }//GEN-LAST:event_btnclienteActionPerformed

    private void btnincluiritemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnincluiritemActionPerformed
        ItemCotacao ic = new ItemCotacao(this.getClass().getSimpleName());
        ic.setTitle("Item Cotação de Serviço");
        Telas.AparecerTela(ic);
    }//GEN-LAST:event_btnincluiritemActionPerformed

    private void btncondicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncondicaoActionPerformed
        ProcurarCondicaoDePagamento p = new ProcurarCondicaoDePagamento("ServiçoCotação");
        Telas.AparecerTela(p);
    }//GEN-LAST:event_btncondicaoActionPerformed

    private void btnrepresentanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrepresentanteActionPerformed
        ProcurarRepresentante p = new ProcurarRepresentante("ServiçoCotação");
        Telas.AparecerTela(p);
    }//GEN-LAST:event_btnrepresentanteActionPerformed

    private void btnvendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnvendedorActionPerformed
        ProcurarVendedor p = new ProcurarVendedor("ServiçoCotação");
        Telas.AparecerTela(p);
    }//GEN-LAST:event_btnvendedorActionPerformed

    private void btnsalvarorcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalvarorcamentoActionPerformed
        if (txtnomecliente.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Selecione um cliente primeiro!");
        } else if (txtcondicao.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Selecione uma condição de pagamento primeiro!");
        } else if (txtrepresentante.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Selecione um representante primeiro!");
        } else if (txtvendedor.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Selecione um vendedor primeiro!");
        } else if (tableitens.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Coloque pelo menos um item no orçamento!");
        } else if (txtnumeroorcamento.getText().equals("")) {
            ServicoOrcamentoBean sob = new ServicoOrcamentoBean();

            String c;

            if (radioc.isSelected()) {
                c = "true";
            } else {
                c = "false";
            }
            try {
                Calendar ca = Calendar.getInstance();
                String patterny = "yy";
                SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                String year = simpleDateFormaty.format(ca.getTime());

                if (sod.readnome() == false) {
                    String idtela = "CS" + year + "-0001";
                    sob.setIdtela(idtela);
                } else {
                    String hua = "";
                    for (ServicoOrcamentoBean sob2 : sod.read()) {
                        hua = String.valueOf(sob2.getIdtela());
                    }
                    int yearint = Integer.parseInt(hua.replace("CS" + year + "-", ""));
                    int yearnovo = yearint + 1;
                    String idtela = "CS" + year + "-" + String.format("%04d", yearnovo);
                    sob.setIdtela(idtela);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CotacaoServico.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            sob.setCliente(txtnomecliente.getText());
            sob.setCondicao(txtcondicao.getText());
            sob.setRepresentante(txtrepresentante.getText());
            sob.setVendedor(txtvendedor.getText());
            sob.setNotes(txtnotes.getText());
            sob.setStatus("Ativo");
            sob.setClientecadastro(c);
            Calendar date = Calendar.getInstance();
            String pattern = "dd/MM/yyyy HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            sob.setData(simpleDateFormat.format(date.getTime()));

            //idtela, cliente, condicao, representante, vendedor, notes, status, clientecadastro, data
            sod.create(sob);

            for (ServicoOrcamentoBean sob2 : sod.read()) {
                txtnumeroorcamento.setText(String.valueOf(sob2.getIdtela()));
                txtstatus.setText(String.valueOf(sob2.getStatus()));
                txtdata.setText(String.valueOf(sob2.getData()));
            }
            int nr = tableitens.getRowCount();
            for (int i = 0; i < nr; i++) {
                ServicoOrcamentoItensBean soib = new ServicoOrcamentoItensBean();
                ServicoOrcamentoItensDAO soid = new ServicoOrcamentoItensDAO();

                soib.setIdorcamento(txtnumeroorcamento.getText());
                soib.setCodigo(tableitens.getValueAt(i, 2).toString());
                soib.setDesc(tableitens.getValueAt(i, 3).toString());
                soib.setQtd(tableitens.getValueAt(i, 4).toString());
                soib.setValor(tableitens.getValueAt(i, 5).toString());
                soib.setTotal(tableitens.getValueAt(i, 6).toString());
                soib.setPrazo(tableitens.getValueAt(i, 7).toString());
                soib.setDas(tableitens.getValueAt(i, 8).toString());
                //idorcamento, codigo, desc, qtd, valor, total, prazo, pedido, das

                soid.create(soib);
            }
            if (tabledocumentos.getRowCount() > 0) {
                int rc = tabledocumentos.getRowCount();
                for (int i = 0; i < rc; i++) {
                    File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                    File folder = new File("Q:/MIKE_ERP/orc_ser_arq/" + txtnumeroorcamento.getText());
                    File filecopy = new File(folder + "/" + fileoriginal.getName());

                    folder.mkdirs();
                    try {
                        Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                    } catch (IOException ex) {
                        Logger.getLogger(DocumentosOrcamentoServico.class
                                .getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");
                        try {
                            SendEmail.EnviarErro(ex.toString());
                            JOptionPane.showMessageDialog(rootPane, "E-mail com erro enviado com sucesso!");
                        } catch (HeadlessException hex) {
                            JOptionPane.showMessageDialog(rootPane, "Erro!\n" + hex);
                        } catch (AWTException | IOException ex1) {
                            Logger.getLogger(DocumentosOrcamentoServico.class
                                    .getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                    ServicoOrcamentoDocumentosBean sodb = new ServicoOrcamentoDocumentosBean();

                    sodb.setIdorcamento(txtnumeroorcamento.getText());
                    sodb.setDescricao(tabledocumentos.getValueAt(i, 2).toString());
                    sodb.setLocal(filecopy.toString());
                    //idorcamento, descricao, local

                    sodd.create(sodb);
                }
            }
            filltableorcamentoservico();
            tableitensatualizar();
            tabledocumentosatualizar();
            JOptionPane.showMessageDialog(rootPane, "Salvo com sucesso!");
        } else {
            ServicoOrcamentoBean sob = new ServicoOrcamentoBean();

            String st = txtstatus.getText();

            String c;
            if (radioc.isSelected()) {
                c = "true";
            } else {
                c = "false";
            }

            sob.setCliente(txtnomecliente.getText());
            sob.setCondicao(txtcondicao.getText());
            sob.setRepresentante(txtrepresentante.getText());
            sob.setVendedor(txtvendedor.getText());
            sob.setNotes(txtnotes.getText());
            sob.setStatus(st);
            sob.setClientecadastro(c);
            sob.setIdtela(txtnumeroorcamento.getText());
            //cliente, condicao, representante, vendedor, notes, status, clientecadastro, id

            sod.update(sob);

            int nr = tableitens.getRowCount();
            for (int i = 0; i < nr; i++) {
                ServicoOrcamentoItensBean soib = new ServicoOrcamentoItensBean();
                ServicoOrcamentoItensDAO soid = new ServicoOrcamentoItensDAO();

                if (tableitens.getValueAt(i, 1).equals("")) {
                    soib.setIdorcamento(txtnumeroorcamento.getText());
                    soib.setCodigo(tableitens.getValueAt(i, 2).toString());
                    soib.setDesc(tableitens.getValueAt(i, 3).toString());
                    soib.setQtd(tableitens.getValueAt(i, 4).toString());
                    soib.setValor(tableitens.getValueAt(i, 5).toString());
                    soib.setTotal(tableitens.getValueAt(i, 6).toString());
                    soib.setPrazo(tableitens.getValueAt(i, 7).toString());
                    soib.setDas(tableitens.getValueAt(i, 8).toString());
                    //idorcamento, codigo, desc, qtd, valor, total, prazo, pedido, das

                    soid.create(soib);
                } else {
                    soib.setCodigo(tableitens.getValueAt(i, 2).toString());
                    soib.setDesc(tableitens.getValueAt(i, 3).toString());
                    soib.setQtd(tableitens.getValueAt(i, 4).toString());
                    soib.setValor(tableitens.getValueAt(i, 5).toString());
                    soib.setTotal(tableitens.getValueAt(i, 6).toString());
                    soib.setPrazo(tableitens.getValueAt(i, 7).toString());
                    soib.setDas(tableitens.getValueAt(i, 8).toString());
                    soib.setId(Integer.parseInt(tableitens.getValueAt(i, 1).toString()));
                    //codigo, descricao , qtd , valor , total , prazo , pedido , das, id

                    soid.update(soib);
                }
            }
            int rctd = tabledocumentos.getRowCount();
            if (rctd != 0) {
                for (int i = 0; i < rctd; i++) {
                    if (tabledocumentos.getValueAt(i, 0).equals("")) {
                        File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                        File folder = new File("Q:/MIKE_ERP/orc_ser_arq/" + txtnumeroorcamento.getText());
                        File filecopy = new File(folder + "/" + fileoriginal.getName());

                        folder.mkdirs();
                        try {
                            Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                        } catch (IOException ex) {
                            Logger.getLogger(DocumentosOrcamentoServico.class
                                    .getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");
                            try {
                                SendEmail.EnviarErro(ex.toString());
                                JOptionPane.showMessageDialog(rootPane, "E-mail com erro enviado com sucesso!");
                            } catch (HeadlessException hex) {
                                JOptionPane.showMessageDialog(rootPane, "Erro!\n" + hex);
                            } catch (AWTException | IOException ex1) {
                                Logger.getLogger(DocumentosOrcamentoServico.class
                                        .getName()).log(Level.SEVERE, null, ex1);
                            }
                        }
                        ServicoOrcamentoDocumentosBean sodb = new ServicoOrcamentoDocumentosBean();

                        sodb.setIdorcamento(txtnumeroorcamento.getText());
                        sodb.setDescricao(tabledocumentos.getValueAt(i, 2).toString());
                        sodb.setLocal(String.valueOf(filecopy));
                        //idorcamento, descricao, local

                        sodd.create(sodb);
                    } else {
                        ServicoOrcamentoDocumentosBean sodb = new ServicoOrcamentoDocumentosBean();

                        sodb.setIdorcamento(txtnumeroorcamento.getText());
                        sodb.setDescricao(tabledocumentos.getValueAt(i, 2).toString());
                        sodb.setLocal(tabledocumentos.getValueAt(i, 3).toString());
                        sodb.setId(Integer.parseInt(tabledocumentos.getValueAt(i, 0).toString()));
                        //idorcamento, descricao, local, id

                        sodd.update(sodb);
                    }
                }
            }
            filltableorcamentoservico();
            tableitensatualizar();
            tabledocumentosatualizar();
            JOptionPane.showMessageDialog(rootPane, "Atualizado com sucesso!");
        }
    }//GEN-LAST:event_btnsalvarorcamentoActionPerformed

    private void btnexcluiritemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexcluiritemActionPerformed
        //Descobrir número de linhas na tableitens
        int rc = tableitens.getRowCount();

        //Descobrir número de itens selecionados para excluir
        int numerotrue = 0;
        for (int i = 0; i < rc; i++) {
            if (tableitens.getValueAt(i, 0).equals(true)) {
                numerotrue++;
            }
        }

        if (rc < 1) {//Se o número de linhas na tableitens for menor que 1
            JOptionPane.showMessageDialog(rootPane, "Não existem itens para excluir.");
        } else if (numerotrue == 0) {//Se não existem itens selecionados para exclusão
            JOptionPane.showMessageDialog(rootPane, "Não foram selecionados itens para excluir.");
        } else {//Se existem itens na tableitens e pelo menos 1 item foi selecionado para exclusão

            //Mensagem para confirmação da exclusão dos itens
            int i = JOptionPane.showConfirmDialog(rootPane, "Deseja excluir os itens selecionados?", "Excluir itens", JOptionPane.YES_NO_OPTION);

            if (i == 0) {//Se a resposta foi afirmativa para a exclusão

                //Transformar a tableitens em Default para excluir os itens
                DefaultTableModel model = (DefaultTableModel) tableitens.getModel();

                for (int j = 0; j < tableitens.getRowCount(); j++) {
                    if (tableitens.getValueAt(j, 0).equals(true)) {//Se a linha estiver selecionada

                        //DAO e Bean para exclusão dos itens selecionados
                        ServicoOrcamentoItensBean soib = new ServicoOrcamentoItensBean();
                        ServicoOrcamentoItensDAO soid = new ServicoOrcamentoItensDAO();

                        soib.setId(Integer.parseInt(tableitens.getValueAt(j, 1).toString()));

                        //id = ?
                        soid.delete(soib);

                        //tableitens removendo o item da tabela
                        model.removeRow(j);
                    }
                }
            }
        }
        //Colocar o valor total do orçamento
        txtvalor();
    }//GEN-LAST:event_btnexcluiritemActionPerformed

    private void btnincluirdocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnincluirdocActionPerformed
//        DocumentosOrcamentoServico d = new DocumentosOrcamentoServico();
//        Telas.AparecerTela(d);
        ProcurarDocumento pd = new ProcurarDocumento(this.getClass().getSimpleName());
        Telas.AparecerTela(pd);
    }//GEN-LAST:event_btnincluirdocActionPerformed

    private void tableitensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableitensMouseClicked
        int rowclicked = tableitens.getSelectedRow();
        if (evt.getButton() == 1) {
            if (tableitens.getSelectedColumn() == 0) {
                if (!tableitens.getValueAt(rowclicked, 8).toString().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Item incluído em DAS! Não pode ser selecionado novamente.");
                    tableitens.setValueAt(false, rowclicked, 0);
                }
            }
            if (evt.getClickCount() == 2) {
                if (!tableitens.getValueAt(tableitens.getSelectedRow(), 8).toString().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Item incluído em DAS! Não pode ser alterado.");
                } else {
                    ItemOrcamentoServico ios = new ItemOrcamentoServico();
                    Telas.AparecerTela(ios);

                    if (tableitens.getValueAt(tableitens.getSelectedRow(), 2).equals("")) {
                        ItemOrcamentoServico.jRadioButton2.setSelected(true);
                        ItemOrcamentoServico.btnprocurar.setEnabled(false);
                        ItemOrcamentoServico.txtdesc.setEditable(true);
                    }
                    ItemOrcamentoServico.txtrow.setText(String.valueOf(tableitens.getSelectedRow()));
                    ItemOrcamentoServico.txtid.setText(tableitens.getValueAt(tableitens.getSelectedRow(), 1).toString());
                    ItemOrcamentoServico.txtcodigo.setText(tableitens.getValueAt(tableitens.getSelectedRow(), 2).toString());
                    ItemOrcamentoServico.txtdesc.setText(tableitens.getValueAt(tableitens.getSelectedRow(), 3).toString());
                    ItemOrcamentoServico.txtqtd.setText(tableitens.getValueAt(tableitens.getSelectedRow(), 4).toString());
                    ItemOrcamentoServico.txtvalor.setText(tableitens.getValueAt(tableitens.getSelectedRow(), 5).toString());
                    ItemOrcamentoServico.txtprazo.setText(tableitens.getValueAt(tableitens.getSelectedRow(), 7).toString());
                }
            }
        }
        if (evt.getButton() == 3) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem das = new JMenuItem("Abrir DAS");

            das.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String DAS = tableitens.getValueAt(tableitens.getSelectedRow(), 8).toString();
                    if (DAS.equals("")) {
                        JOptionPane.showMessageDialog(rootPane, "Produto sem DAS");
                    } else {
                        PedidoServico p = new PedidoServico();
                        jDesktopPane1.add(p);
                        p.setVisible(true);
                        try {
                            p.setMaximum(true);
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(TelaPrincipal.class
                                    .getName()).log(Level.SEVERE, null, ex);
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
                                spib.getNf()
                            });
                        }

                        txtvalor();
                        PedidoServico.txtvalorcobranca();
                        PedidoServico.txtvalorretorno();

                        DefaultTableModel modeldoc = (DefaultTableModel) PedidoServico.tabledocumentos.getModel();
                        modeldoc.setNumRows(0);
                        ServicoPedidoDocumentosDAO spdd = new ServicoPedidoDocumentosDAO();

                        for (ServicoPedidoDocumentosBean spdb : spdd.readitens(txtnumeropedido.getText())) {
                            modeldoc.addRow(new Object[]{
                                false,
                                spdb.getId(),
                                spdb.getDescricao(),
                                spdb.getLocal()
                            });
                        }
                    }
                }
            });

            menu.add(das);

            menu.show(evt.getComponent(), evt.getPoint().x, evt.getPoint().y);
        }
    }//GEN-LAST:event_tableitensMouseClicked

    private void tableorcamentoservicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableorcamentoservicoMouseClicked
        if (evt.getClickCount() == 2) {
            taborcamentos.setSelectedIndex(1);
            String cotacao = tableorcamentoservico.getValueAt(tableorcamentoservico.getSelectedRow(), 0).toString();

            lerCotacao(cotacao);
        }
    }//GEN-LAST:event_tableorcamentoservicoMouseClicked

    @SuppressWarnings("unchecked")
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //Conexão com o banco de dados
        Connection con = ConnectionFactory.getConnection();
        HashMap parametros = new HashMap();
        //o nome do parâmetro e o valor é passado ao HashMap
        String numorc = txtnumeroorcamento.getText();
        parametros.put("idtela", numorc);
        //pega o caminho físico até o arquivo .jasper
        InputStream stream = CotacaoServico.class
                .getResourceAsStream("/Reports/CotacaoServico.jasper");
        //chama fillreport
        try {
            @SuppressWarnings("unchecked")
            JasperPrint jp = JasperFillManager.fillReport(stream, parametros, con);
            //exibe o relatório com viewReport
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(rootPane, "Erro ao gerar relatório!\n" + e);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tableitensMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableitensMouseReleased
        int r = tableitens.rowAtPoint(evt.getPoint());
        if (r >= 0 && r < tableitens.getRowCount()) {
            tableitens.setRowSelectionInterval(r, r);
        } else {
            tableitens.clearSelection();
        }
    }//GEN-LAST:event_tableitensMouseReleased

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btncancelarActionPerformed

    private void tabledocumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabledocumentosMouseClicked
        if (evt.getClickCount() == 2) {
            Desktop desk = Desktop.getDesktop();
            try {
                if (tabledocumentos.getValueAt(tabledocumentos.getSelectedRow(), 3).equals("")) {
                    desk.open(new File((String) tabledocumentos.getValueAt(tabledocumentos.getSelectedRow(), 4)));
                } else {
                    desk.open(new File((String) tabledocumentos.getValueAt(tabledocumentos.getSelectedRow(), 3)));
                }
            } catch (IOException ex) {
                Logger.getLogger(DocumentosOrcamentoServico.class
                        .getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro ao abrir arquivo.\n" + ex);
                try {
                    SendEmail.EnviarErro(ex.toString());
                } catch (AWTException | IOException ex1) {
                    Logger.getLogger(CotacaoServico.class
                            .getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }//GEN-LAST:event_tabledocumentosMouseClicked

    private void btnexcluirdocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexcluirdocActionPerformed
        DefaultTableModel model = (DefaultTableModel) tabledocumentos.getModel();
        if (tabledocumentos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Não existem itens para excluir");
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
                            Logger.getLogger(CotacaoServico.class
                                    .getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(rootPane, "Erro!\n" + ex);
                        }

                        ServicoOrcamentoDocumentosBean sodb = new ServicoOrcamentoDocumentosBean();

                        sodb.setId(Integer.parseInt(tabledocumentos.getValueAt(i, 1).toString()));
                        //id

                        sodd.delete(sodb);

                        model.removeRow(i);
                    }
                }
            }
        }
    }//GEN-LAST:event_btnexcluirdocActionPerformed

    private void btncriarpedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncriarpedidoActionPerformed
        int numerotrue = 0;
        int numeronaocadastrado = 0;
        int nri = tableitens.getRowCount();
        for (int i = 0; i < nri; i++) {
            if (tableitens.getValueAt(i, 0).equals(true)) {
                numerotrue++;
            }
        }
        for (int j = 0; j < nri; j++) {
            if (tableitens.getValueAt(j, 2).equals("") && (Boolean) tableitens.getValueAt(j, 0) == true) {
                numeronaocadastrado++;
            }
        }
        if (txtnumeroorcamento.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Escolha um orçamento ou salve este primeiro!");
        } else if (radionc.isSelected()) {
            JOptionPane.showMessageDialog(rootPane, "Cadastre o cliente primeiro!");
        } else if (numerotrue == 0) {
            JOptionPane.showMessageDialog(rootPane, "Escolha pelo menos um item!");
        } else if (numeronaocadastrado > 0) {
            JOptionPane.showMessageDialog(rootPane, "Cadastre todos os itens selecionados primeiro!");
        } else {
            int resp = JOptionPane.showConfirmDialog(rootPane, "Deseja gerar um pedido com os dados deste orçamento?", "Gerar pedido", JOptionPane.OK_CANCEL_OPTION);
            if (resp == 0) {
                ServicoPedidoBean spb = new ServicoPedidoBean();
                ServicoPedidoDAO spd = new ServicoPedidoDAO();

                try {
                    if (spd.readnome() == false) {
                        Calendar ca = Calendar.getInstance();
                        String patterny = "yy";
                        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                        String year = simpleDateFormaty.format(ca.getTime());
                        String idtela = "PS" + year + "-0001";
                        spb.setIdtela(idtela);
                    } else {
                        Calendar ca = Calendar.getInstance();
                        String patterny = "yy";
                        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                        String year = simpleDateFormaty.format(ca.getTime());
                        String hua = "";
                        for (ServicoPedidoBean spb2 : spd.readTodos()) {
                            hua = String.valueOf(spb2.getIdtela());
                        }
                        int yearint = Integer.parseInt(hua.replace("PS" + year + "-", ""));
                        int yearnovo = yearint + 1;
                        String idtelanovo = "PS" + year + "-" + String.format("%04d", yearnovo);
                        spb.setIdtela(idtelanovo);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CotacaoServico.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                spb.setIdorcamento(txtnumeroorcamento.getText());
                spb.setCliente(txtnomecliente.getText());
                spb.setCondicao(txtcondicao.getText());
                spb.setRepresentante(txtrepresentante.getText());
                spb.setVendedor(txtvendedor.getText());
                spb.setNotes(txtnotes.getText());
                spb.setStatus_retorno("Ativo");
                spb.setStatus_cobranca("Ativo");
                spb.setNfcliente("");

                Calendar date = Calendar.getInstance();
                String pattern = "dd/MM/yyyy HH:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String data = simpleDateFormat.format(date.getTime());

                spb.setData(data);
//              idtela, idorcamento, cliente, condicao, representante, vendedor, notes, status_retorno, status_cobranca, nfcliente, data

                spd.create(spb);

                String numpedido = "";
                ServicoPedidoDAO spdd = new ServicoPedidoDAO();
                for (ServicoPedidoBean spbb : spdd.readcreated(txtnomecliente.getText(), data)) {
                    numpedido = spbb.getIdtela();
                }

                ServicoPedidoItensBean spib = new ServicoPedidoItensBean();
                ServicoPedidoItensDAO spid = new ServicoPedidoItensDAO();

                ServicoOrcamentoItensBean soib = new ServicoOrcamentoItensBean();
                ServicoOrcamentoItensDAO soid = new ServicoOrcamentoItensDAO();

                for (int i = 0; i < tableitens.getRowCount(); i++) {
                    if (tableitens.getValueAt(i, 0).equals(true)) {
                        int dias = Integer.parseInt(tableitens.getValueAt(i, 7).toString().replace(" dias úteis", ""));
                        String datePrazo = Dates.CriarDataCurtaDBSemDataExistenteComPrazo(dias);
                        
                        spib.setIdpedido(numpedido);
                        spib.setCodigo(tableitens.getValueAt(i, 2).toString());
                        spib.setDescricao(tableitens.getValueAt(i, 3).toString());
                        spib.setQtde(tableitens.getValueAt(i, 4).toString());
                        spib.setValor(tableitens.getValueAt(i, 5).toString());
                        spib.setTotal(tableitens.getValueAt(i, 6).toString());
                        spib.setPrazo(tableitens.getValueAt(i, 7).toString());
                        spib.setPrazoDate(datePrazo);
                        spib.setOs("");
                        spib.setNf("");
//                      idpedido, codigo, descricao, qtde, valor, total, prazo, os, nf

                        spid.create(spib);

                        soib.setCodigo(tableitens.getValueAt(i, 2).toString());
                        soib.setDesc(tableitens.getValueAt(i, 3).toString());
                        soib.setQtd(tableitens.getValueAt(i, 4).toString());
                        soib.setValor(tableitens.getValueAt(i, 5).toString());
                        soib.setTotal(tableitens.getValueAt(i, 6).toString());
                        soib.setPrazo(tableitens.getValueAt(i, 7).toString());
                        soib.setDas(numpedido);
                        soib.setId(Integer.parseInt(tableitens.getValueAt(i, 1).toString()));
                        //codigo, descricao , qtd , valor , total , prazo , pedido , das, id

                        soid.update(soib);
                    }
                }
                tableitensatualizar();
                if (tabledocumentos.getRowCount() > 0) {
                    int respdoc = JOptionPane.showConfirmDialog(rootPane, "Deseja enviar os documentos deste orçamento para o pedido?", "Gerar pedido com documentos", JOptionPane.OK_CANCEL_OPTION);
                    if (respdoc == 0) {
                        int rcd = tabledocumentos.getRowCount();
                        for (int i = 0; i < rcd; i++) {
                            File fileoriginal = new File(tabledocumentos.getValueAt(i, 3).toString());
                            File folder = new File("Q:/MIKE_ERP/ped_ser_arq/" + numpedido);
                            File filecopy = new File(folder + "/" + fileoriginal.getName());
                            folder.mkdirs();
                            try {
                                Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                            } catch (IOException ex) {
                                Logger.getLogger(DocumentosPedidoServico.class
                                        .getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");
                                try {
                                    SendEmail.EnviarErro(ex.toString());
                                    JOptionPane.showMessageDialog(rootPane, "E-mail com erro enviado com sucesso!");
                                } catch (HeadlessException hex) {
                                    JOptionPane.showMessageDialog(rootPane, "Erro!\n" + hex);
                                } catch (AWTException | IOException ex1) {
                                    Logger.getLogger(DocumentosPedidoServico.class
                                            .getName()).log(Level.SEVERE, null, ex1);
                                }
                            }

                            //Salvar arquivos no DB
                            ServicoPedidoDocumentosBean spdb = new ServicoPedidoDocumentosBean();
                            ServicoPedidoDocumentosDAO spdod = new ServicoPedidoDocumentosDAO();

                            spdb.setIdpedido(numpedido);
                            spdb.setDescricao(tabledocumentos.getValueAt(i, 2).toString());
                            spdb.setLocal(filecopy.toString());
                            //idorcamento, descricao, local

                            spdod.create(spdb);
                        }
                    }
                }
                int numerodas = 0;
                for (int i = 0; i < tableitens.getRowCount(); i++) {
                    if (!tableitens.getValueAt(i, 8).equals("")) {
                        numerodas++;
                    }
                }
                if (tableitens.getRowCount() == numerodas) {
                    ServicoOrcamentoBean sob = new ServicoOrcamentoBean();

                    sob.setStatus("Fechado");
                    sob.setIdtela(txtnumeroorcamento.getText());
                    //status, id

                    sod.updatestatus(sob);
                } else {
                    ServicoOrcamentoBean sob = new ServicoOrcamentoBean();

                    sob.setStatus("Pedido Parcial");
                    sob.setIdtela(txtnumeroorcamento.getText());
                    //status, id

                    sod.updatestatus(sob);
                }

                for (ServicoOrcamentoBean sob : sod.click(txtnumeroorcamento.getText())) {
                    txtstatus.setText(String.valueOf(sob.getStatus()));
                }
                JOptionPane.showMessageDialog(rootPane, "Pedido criado com sucesso!");
                filltableorcamentoservico();
                checkstatus();
            }
        }
    }//GEN-LAST:event_btncriarpedidoActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        int resp = JOptionPane.showConfirmDialog(rootPane, "Deseja criar um novo orçamento?", "Novo orçamento", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            taborcamentos.setSelectedIndex(1);
            radioc.setSelected(true);
            txtnomecliente.setText("");
            txtcondicao.setText("");
            txtrepresentante.setText("");
            txtvendedor.setText("");
            txtnumeroorcamento.setText("");
            txtdata.setText("");
            txtstatus.setText("");
            txtnotes.setText("");
            DefaultTableModel modelitens = (DefaultTableModel) tableitens.getModel();
            DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
            modelitens.setNumRows(0);
            modeldoc.setNumRows(0);
            txttotal.setText("");
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void btnnovoorcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnovoorcamentoActionPerformed
        int resp = JOptionPane.showConfirmDialog(rootPane, "Deseja criar um novo orçamento?", "Novo orçamento", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            taborcamentos.setSelectedIndex(1);
            radioc.setSelected(true);
            txtnomecliente.setText("");
            txtcondicao.setText("");
            txtrepresentante.setText("");
            txtvendedor.setText("");
            txtnumeroorcamento.setText("");
            txtdata.setText("");
            txtstatus.setText("");
            txtnotes.setText("");
            DefaultTableModel modelitens = (DefaultTableModel) tableitens.getModel();
            DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
            modelitens.setNumRows(0);
            modeldoc.setNumRows(0);
            txttotal.setText("");
        }
    }//GEN-LAST:event_btnnovoorcamentoActionPerformed

    private void txtpesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpesquisaKeyReleased
        DefaultTableModel model = (DefaultTableModel) tableorcamentoservico.getModel();
        model.setNumRows(0);

        for (ServicoOrcamentoBean sob : sod.pesquisa(txtpesquisa.getText())) {
            model.addRow(new Object[]{
                sob.getIdtela(),
                sob.getCliente(),
                sob.getStatus()
            });
        }
    }//GEN-LAST:event_txtpesquisaKeyReleased

    private void cbstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbstatusActionPerformed
        int cb = cbstatus.getSelectedIndex();
        switch (cb) {
            case 0:
                DefaultTableModel model = (DefaultTableModel) tableorcamentoservico.getModel();
                model.setNumRows(0);

                for (ServicoOrcamentoBean sob : sod.reademaberto()) {
                    model.addRow(new Object[]{
                        sob.getIdtela(),
                        sob.getCliente(),
                        sob.getStatus()
                    });
                }
                break;
            case 5:
                DefaultTableModel model5 = (DefaultTableModel) tableorcamentoservico.getModel();
                model5.setNumRows(0);

                for (ServicoOrcamentoBean sob : sod.read()) {
                    model5.addRow(new Object[]{
                        sob.getIdtela(),
                        sob.getCliente(),
                        sob.getStatus()
                    });
                }
                break;
            default:
                DefaultTableModel modeld = (DefaultTableModel) tableorcamentoservico.getModel();
                modeld.setNumRows(0);

                sod.readstatus(cbstatus.getSelectedItem().toString()).forEach((sob) -> {
                    modeld.addRow(new Object[]{
                        sob.getIdtela(),
                        sob.getCliente(),
                        sob.getStatus()
                    });
                });
        }
    }//GEN-LAST:event_cbstatusActionPerformed

    private void txtnomeclienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomeclienteKeyReleased
        /*if (radioc.isSelected()) {
            InternalFrameProcura.procuraCliente(txtnomecliente, "ServiçoCotação");
        }*/
    }//GEN-LAST:event_txtnomeclienteKeyReleased

    private void btnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllActionPerformed
        if (btnAll.getText().equals("Selecionar Todos")) {
            for (int i = 0; i < tableitens.getRowCount(); i++) {
                tableitens.setValueAt(true, i, 0);
            }
            btnAll.setText("Desmarcar Todos");
        } else {
            for (int i = 0; i < tableitens.getRowCount(); i++) {
                tableitens.setValueAt(false, i, 0);
            }
            btnAll.setText("Selecionar Todos");
        }

    }//GEN-LAST:event_btnAllActionPerformed

    private void btncancelarorcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarorcamentoActionPerformed
        int resp = JOptionPane.showConfirmDialog(null, "Deseja cancelar a cotação?", "Cancelar Cotação", JOptionPane.YES_NO_OPTION);

        if (resp == 0) {
            ServicoOrcamentoBean sob = new ServicoOrcamentoBean();

            sob.setStatus("Cancelado");
            sob.setIdtela(txtnumeroorcamento.getText());

            //status = ? WHERE idtela = ?
            sod.updatestatus(sob);

            lerCotacao(txtnumeroorcamento.getText());
            
            filltableorcamentoservico();
        }
    }//GEN-LAST:event_btncancelarorcamentoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelOrcamentoServico;
    private javax.swing.JButton btnAll;
    public static javax.swing.JButton btncancelar;
    public static javax.swing.JButton btncancelarorcamento;
    public static javax.swing.JButton btncliente;
    public static javax.swing.JButton btncondicao;
    public static javax.swing.JButton btncriarpedido;
    public static javax.swing.JButton btnexcluirdoc;
    public static javax.swing.JButton btnexcluiritem;
    public static javax.swing.JButton btnincluirdoc;
    public static javax.swing.JButton btnincluiritem;
    private javax.swing.JButton btnnovoorcamento;
    public static javax.swing.JButton btnrepresentante;
    public static javax.swing.JButton btnsalvarorcamento;
    public static javax.swing.JButton btnvendedor;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    public static javax.swing.JComboBox<String> cbstatus;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JPanel panel2;
    public static javax.swing.JRadioButton radioc;
    public static javax.swing.JRadioButton radionc;
    public static javax.swing.JTable tabledocumentos;
    public static javax.swing.JTable tableitens;
    public static javax.swing.JTable tableorcamentoservico;
    private javax.swing.JTabbedPane taborcamentos;
    public static javax.swing.JTextField txtcondicao;
    public static javax.swing.JTextField txtdata;
    public static javax.swing.JTextField txtnomecliente;
    public static javax.swing.JTextArea txtnotes;
    public static javax.swing.JTextField txtnumeroorcamento;
    private javax.swing.JTextField txtpesquisa;
    public static javax.swing.JTextField txtrepresentante;
    public static javax.swing.JTextField txtstatus;
    public static javax.swing.JTextField txttotal;
    public static javax.swing.JTextField txtvendedor;
    // End of variables declaration//GEN-END:variables
}
