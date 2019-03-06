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
import DAO.ServicoOrcamentoItensDAO;
import DAO.ServicoOrcamentoDAO;
import DAO.ServicoOrcamentoDocumentosDAO;
import DAO.ServicoPedidoDAO;
import DAO.ServicoPedidoDocumentosDAO;
import DAO.ServicoPedidoItensDAO;
import Methods.SendEmail;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Marcos Filho
 */
public class OrcamentoServico extends javax.swing.JInternalFrame {

    /**
     * Creates new form OrcamentoServico
     */
    public OrcamentoServico() {
        initComponents();
        filltableorcamentoservico();
        tableitens();
        txtvalor();
    }

    public static void filltableorcamentoservico() {
        DefaultTableModel model = (DefaultTableModel) tableorcamentoservico.getModel();
        model.setNumRows(0);
        ServicoOrcamentoDAO sod = new ServicoOrcamentoDAO();

        for (ServicoOrcamentoBean sob : sod.read()) {
            model.addRow(new Object[]{
                sob.getIdtela(),
                sob.getCliente(),
                sob.getStatus()
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
        ServicoOrcamentoItensDAO iosd = new ServicoOrcamentoItensDAO();

        for (ServicoOrcamentoItensBean iosb : iosd.readitens(txtnumeroorcamento.getText())) {
            model.addRow(new Object[]{
                false,
                iosb.getId(),
                iosb.getCodigo(),
                iosb.getDesc(),
                iosb.getQtd(),
                iosb.getValor(),
                iosb.getTotal(),
                iosb.getPrazo(),
                iosb.getPedido(),
                iosb.getDas()
            });
        }
    }

    public static void tabledocumentosatualizar() {
        DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
        modeldoc.setNumRows(0);
        ServicoOrcamentoDocumentosDAO sodd = new ServicoOrcamentoDocumentosDAO();

        for (ServicoOrcamentoDocumentosBean sodb : sodd.readitens(txtnumeroorcamento.getText())) {
            modeldoc.addRow(new Object[]{
                false,
                sodb.getId(),
                sodb.getDescricao(),
                sodb.getLocal(),});
        }
    }

    public static void checkstatus() {
        TableModel modelne = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };
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
            tabledocumentos.setEnabled(false);
            btnincluirdoc.setEnabled(false);
            btnexcluirdoc.setEnabled(false);
            tableitens = new JTable(modelne);
            tableitens.setEnabled(false);
            btnincluiritem.setEnabled(false);
            btnexcluiritem.setEnabled(false);
            btnsalvarorcamento.setEnabled(false);
            btncriarpedido.setEnabled(false);
            btncancelarorcamento.setEnabled(false);
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
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton11 = new javax.swing.JButton();
        PanelOrcamentoServico = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
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
        setTitle("Orçamento de Serviço");

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
            tableorcamentoservico.getColumnModel().getColumn(0).setMinWidth(60);
            tableorcamentoservico.getColumnModel().getColumn(0).setPreferredWidth(60);
            tableorcamentoservico.getColumnModel().getColumn(0).setMaxWidth(60);
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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Aberto", "Pedido Parcial", "Fechado", "Cancelado", "Todos" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
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
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11)
                .addContainerGap())
        );

        taborcamentos.addTab("Lista de Orçamentos", jPanel1);

        PanelOrcamentoServico.setBackground(new java.awt.Color(255, 255, 255));
        PanelOrcamentoServico.setPreferredSize(new java.awt.Dimension(836, 600));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        jLabel3.setText("Cliente");

        txtnomecliente.setEditable(false);
        txtnomecliente.setBackground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(radioc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radionc))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncliente)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioc)
                    .addComponent(radionc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtnomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Orçamento"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Nº");

        txtnumeroorcamento.setEditable(false);
        txtnumeroorcamento.setBackground(new java.awt.Color(255, 255, 255));
        txtnumeroorcamento.setSelectionColor(new java.awt.Color(255, 255, 255));

        txtstatus.setEditable(false);
        txtstatus.setBackground(new java.awt.Color(255, 255, 255));
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
        txtdata.setBackground(new java.awt.Color(255, 255, 255));
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
                        .addComponent(txtnumeroorcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        txtvendedor.setBackground(new java.awt.Color(255, 255, 255));

        txtrepresentante.setEditable(false);
        txtrepresentante.setBackground(new java.awt.Color(255, 255, 255));

        txtcondicao.setEditable(false);
        txtcondicao.setBackground(new java.awt.Color(255, 255, 255));

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
                "", "ID", "Código", "Descrição", "Qtde", "Valor Unitário R$", "Total R$", "Prazo de Entrega", "Pedido do Cliente", "DAS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
        }

        btnincluiritem.setText("Incluir");
        btnincluiritem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnincluiritemActionPerformed(evt);
            }
        });

        txttotal.setEditable(false);
        txttotal.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setText("Total: R$");

        btnexcluiritem.setText("Excluir");
        btnexcluiritem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexcluiritemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnexcluiritem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnincluiritem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
                    .addComponent(btnexcluiritem)))
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
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1035, Short.MAX_VALUE)
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
                "", "Id", "Descrição", "Local", "Local Original"
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
        jScrollPane6.setViewportView(tabledocumentos);
        if (tabledocumentos.getColumnModel().getColumnCount() > 0) {
            tabledocumentos.getColumnModel().getColumn(0).setMinWidth(40);
            tabledocumentos.getColumnModel().getColumn(0).setMaxWidth(40);
            tabledocumentos.getColumnModel().getColumn(1).setMinWidth(0);
            tabledocumentos.getColumnModel().getColumn(1).setPreferredWidth(0);
            tabledocumentos.getColumnModel().getColumn(1).setMaxWidth(0);
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
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1035, Short.MAX_VALUE)
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

        btncancelarorcamento.setText("Cancelar Orçamento");

        btnnovoorcamento.setText("Novo Orçamento");
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(PanelOrcamentoServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsalvarorcamento)
                    .addComponent(btncriarpedido)
                    .addComponent(btncancelar)
                    .addComponent(btncancelarorcamento)
                    .addComponent(btnnovoorcamento))
                .addContainerGap())
        );

        taborcamentos.addTab("Detalhes do Orçamento", PanelOrcamentoServico);

        jScrollPane3.setViewportView(taborcamentos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1087, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
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
        ProcurarClienteOrcamentoServico p = new ProcurarClienteOrcamentoServico();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_btnclienteActionPerformed

    private void btnincluiritemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnincluiritemActionPerformed
        ItemOrcamentoServico ios = new ItemOrcamentoServico();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(ios);
        Dimension i = ios.getSize();
        Dimension d = desk.getSize();
        ios.setLocation((d.width - i.width) / 2, (d.height - i.height) / 2);
        ios.setVisible(true);
    }//GEN-LAST:event_btnincluiritemActionPerformed

    private void btncondicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncondicaoActionPerformed
        ProcurarCondicaoDePagamentoOrcamentoServico p = new ProcurarCondicaoDePagamentoOrcamentoServico();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_btncondicaoActionPerformed

    private void btnrepresentanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrepresentanteActionPerformed
        ProcurarRepresentanteOrcamentoServico p = new ProcurarRepresentanteOrcamentoServico();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_btnrepresentanteActionPerformed

    private void btnvendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnvendedorActionPerformed
        ProcurarVendedorOrcamentoServico p = new ProcurarVendedorOrcamentoServico();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
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
            ServicoOrcamentoDAO sod = new ServicoOrcamentoDAO();
            String c;
            if (radioc.isSelected()) {
                c = "true";
            } else {
                c = "false";
            }
            try {
                if (sod.readnome() == false) {
                    Calendar ca = Calendar.getInstance();
                    String patterny = "yy";
                    SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                    String year = simpleDateFormaty.format(ca.getTime());
                    String idtela = year + "-0001";
                    sob.setIdtela(idtela);
                } else {
                    Calendar ca = Calendar.getInstance();
                    String patterny = "yy";
                    SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                    String year = simpleDateFormaty.format(ca.getTime());
                    String hua = "";
                    for (ServicoOrcamentoBean sob2 : sod.read()) {
                        hua = String.valueOf(sob2.getIdtela());
                    }
                    int yearint = Integer.parseInt(hua.replace(year + "-", ""));
                    int yearnovo = yearint + 1;
                    String idtela = year + "-" + String.format("%04d", yearnovo);
                    sob.setIdtela(idtela);
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
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
                soib.setPedido(tableitens.getValueAt(i, 8).toString());
                soib.setDas(tableitens.getValueAt(i, 9).toString());
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
                    ServicoOrcamentoDocumentosBean sodb = new ServicoOrcamentoDocumentosBean();
                    ServicoOrcamentoDocumentosDAO sodd = new ServicoOrcamentoDocumentosDAO();

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
            ServicoOrcamentoDAO sod = new ServicoOrcamentoDAO();

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
            sob.setId(Integer.parseInt(txtnumeroorcamento.getText()));
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
                    soib.setPedido(tableitens.getValueAt(i, 8).toString());
                    soib.setDas(tableitens.getValueAt(i, 9).toString());
                    //idorcamento, codigo, desc, qtd, valor, total, prazo, pedido, das

                    soid.create(soib);
                } else {
                    soib.setCodigo(tableitens.getValueAt(i, 2).toString());
                    soib.setDesc(tableitens.getValueAt(i, 3).toString());
                    soib.setQtd(tableitens.getValueAt(i, 4).toString());
                    soib.setValor(tableitens.getValueAt(i, 5).toString());
                    soib.setTotal(tableitens.getValueAt(i, 6).toString());
                    soib.setPrazo(tableitens.getValueAt(i, 7).toString());
                    soib.setPedido(tableitens.getValueAt(i, 8).toString());
                    soib.setDas(tableitens.getValueAt(i, 9).toString());
                    soib.setId(Integer.parseInt(tableitens.getValueAt(i, 1).toString()));
                    //codigo, descricao , qtd , valor , total , prazo , pedido , das, id

                    soid.update(soib);
                }
            }
            int rctd = tabledocumentos.getRowCount();
            if (rctd != 0) {
                for (int i = 0; i < rctd; i++) {
                    if (tabledocumentos.getValueAt(i, 1).equals("")) {
                        File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                        File folder = new File("Q:/MIKE_ERP/orc_ser_arq/" + txtnumeroorcamento.getText());
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
                        ServicoOrcamentoDocumentosBean sodb = new ServicoOrcamentoDocumentosBean();
                        ServicoOrcamentoDocumentosDAO sodd = new ServicoOrcamentoDocumentosDAO();

                        sodb.setIdorcamento(txtnumeroorcamento.getText());
                        sodb.setDescricao(tabledocumentos.getValueAt(i, 2).toString());
                        sodb.setLocal(String.valueOf(filecopy));
                        //idorcamento, descricao, local

                        sodd.create(sodb);
                    } else {
                        ServicoOrcamentoDocumentosBean sodb = new ServicoOrcamentoDocumentosBean();
                        ServicoOrcamentoDocumentosDAO sodd = new ServicoOrcamentoDocumentosDAO();

                        sodb.setIdorcamento(txtnumeroorcamento.getText());
                        sodb.setDescricao(tabledocumentos.getValueAt(i, 2).toString());
                        sodb.setLocal(tabledocumentos.getValueAt(i, 3).toString());
                        sodb.setId(Integer.parseInt(tabledocumentos.getValueAt(i, 1).toString()));
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
        int i = JOptionPane.showConfirmDialog(rootPane, "Deseja excluir os itens selecionados?", "Excluir itens", JOptionPane.YES_NO_OPTION);
        DefaultTableModel model = (DefaultTableModel) tableitens.getModel();
        if (tableitens.getRowCount() < 1) {
            JOptionPane.showMessageDialog(rootPane, "Não existem itens para excluir.");
        } else if (i == 0) {
            for (int j = 0; j < tableitens.getRowCount(); j++) {
                String v = tableitens.getValueAt(j, 0).toString();
                if (v.equals("true")) {
                    model.removeRow(j);
                }
            }
        }
        txtvalor();
    }//GEN-LAST:event_btnexcluiritemActionPerformed

    private void btnincluirdocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnincluirdocActionPerformed
//        if (txtnumeroorcamento.getText().equals("")) {
//            JOptionPane.showMessageDialog(rootPane, "Selecione um orçamento antes!");
//        } else {
        DocumentosOrcamentoServico d = new DocumentosOrcamentoServico();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(d);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = d.getSize();
        d.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        d.setVisible(true);
//        }
    }//GEN-LAST:event_btnincluirdocActionPerformed

    private void tableitensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableitensMouseClicked
        if (evt.getButton() == 1) {
            if (evt.getClickCount() == 2) {
                ItemOrcamentoServico ios = new ItemOrcamentoServico();
                JDesktopPane desk = this.getDesktopPane();
                desk.add(ios);
                Dimension i = ios.getSize();
                Dimension d = desk.getSize();
                ios.setLocation((d.width - i.width) / 2, (d.height - i.height) / 2);
                ios.setVisible(true);
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
                ItemOrcamentoServico.txtpedido.setText(tableitens.getValueAt(tableitens.getSelectedRow(), 8).toString());
            }
        }
        if (evt.getButton() == 3) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem das = new JMenuItem("Abrir DAS");

            das.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String DAS = tableitens.getValueAt(tableitens.getSelectedRow(), 9).toString();
                    if (DAS.equals("")) {
                        JOptionPane.showMessageDialog(rootPane, "Produto sem DAS");
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
                            PedidoServico.txtnomecliente.setText(spb.getCliente());
                            PedidoServico.txtcondicao.setText(spb.getCondicao());
                            PedidoServico.txtrepresentante.setText(spb.getRepresentante());
                            PedidoServico.txtvendedor.setText(spb.getVendedor());
                            PedidoServico.txtstatus.setText(spb.getStatus());
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
                                spib.getNf()
                            });
                        }

                        txtvalor();

                        DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
                        modeldoc.setNumRows(0);
                        ServicoPedidoDocumentosDAO spdd = new ServicoPedidoDocumentosDAO();

                        for (ServicoPedidoDocumentosBean spdb : spdd.readitens(txtnumeroorcamento.getText())) {
                            modeldoc.addRow(new Object[]{
                                false,
                                spdb.getId(),
                                spdb.getDescricao(),
                                spdb.getLocal(),});
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
            txtnumeroorcamento.setText(tableorcamentoservico.getValueAt(tableorcamentoservico.getSelectedRow(), 0).toString());

            ServicoOrcamentoDAO sod = new ServicoOrcamentoDAO();

            for (ServicoOrcamentoBean sob : sod.click(txtnumeroorcamento.getText())) {
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
            }
            if (radionc.isSelected()) {
                btncliente.setEnabled(false);
                txtnomecliente.setEditable(true);
            }

            DefaultTableModel model = (DefaultTableModel) tableitens.getModel();
            model.setNumRows(0);
            ServicoOrcamentoItensDAO iosd = new ServicoOrcamentoItensDAO();

            for (ServicoOrcamentoItensBean iosb : iosd.readitens(txtnumeroorcamento.getText())) {
                model.addRow(new Object[]{
                    false,
                    iosb.getId(),
                    iosb.getCodigo(),
                    iosb.getDesc(),
                    iosb.getQtd(),
                    iosb.getValor(),
                    iosb.getTotal(),
                    iosb.getPrazo(),
                    iosb.getPedido(),
                    iosb.getDas()
                });
            }

            txtvalor();

            DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
            modeldoc.setNumRows(0);
            ServicoOrcamentoDocumentosDAO sodd = new ServicoOrcamentoDocumentosDAO();

            for (ServicoOrcamentoDocumentosBean sodb : sodd.readitens(txtnumeroorcamento.getText())) {
                modeldoc.addRow(new Object[]{
                    false,
                    sodb.getId(),
                    sodb.getDescricao(),
                    sodb.getLocal(),});
            }
            checkstatus();
        }
    }//GEN-LAST:event_tableorcamentoservicoMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Em breve!");
//        try {
//            new JasperOrcamentoServico().gerar("Reports/orcamento_servico2.jrxml");
//        } catch (ClassNotFoundException | SQLException e) {
//            JOptionPane.showMessageDialog(rootPane, "Erro ao mostrar relatório!\n" + e);
//        } catch (JRException ex) {
//            Logger.getLogger(OrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
                desk.open(new File((String) tabledocumentos.getValueAt(tabledocumentos.getSelectedRow(), 3)));
            } catch (IOException ex) {
                Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(OrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(rootPane, "Erro!\n" + ex);
                        }

                        ServicoOrcamentoDocumentosBean sodb = new ServicoOrcamentoDocumentosBean();
                        ServicoOrcamentoDocumentosDAO sodd = new ServicoOrcamentoDocumentosDAO();

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
                        String idtela = year + "-0001";
                        spb.setIdtela(idtela);
                    } else {
                        Calendar ca = Calendar.getInstance();
                        String patterny = "yy";
                        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                        String year = simpleDateFormaty.format(ca.getTime());
                        String hua = "";
                        for (ServicoPedidoBean spb2 : spd.read()) {
                            hua = String.valueOf(spb2.getIdtela());
                        }
                        int yearint = Integer.parseInt(hua.replace(year + "-", ""));
                        int yearnovo = yearint + 1;
                        String idtelanovo = year + "-" + String.format("%04d", yearnovo);
                        spb.setIdtela(idtelanovo);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(OrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
                }
                spb.setIdorcamento(txtnumeroorcamento.getText());
                spb.setCliente(txtnomecliente.getText());
                spb.setCondicao(txtcondicao.getText());
                spb.setRepresentante(txtrepresentante.getText());
                spb.setVendedor(txtvendedor.getText());
                spb.setNotes(txtnotes.getText());
                spb.setStatus("Aberto");
                spb.setNfcliente("");

                Calendar date = Calendar.getInstance();
                String pattern = "dd/MM/yyyy HH:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String data = simpleDateFormat.format(date.getTime());

                spb.setData(data);
//              idtela, idorcamento, cliente, condicao, representante, vendedor, notes, status, nfcliente, data

                spd.create(spb);

                String numpedido = "";
                ServicoPedidoDAO spdd = new ServicoPedidoDAO();
                for (ServicoPedidoBean spbb : spdd.readcreated(txtnomecliente.getText(), data)) {
                    numpedido = spbb.getIdtela();
                }

                for (int i = 0; i < numerotrue; i++) {
                    if ((Boolean) tableitens.getValueAt(i, 0) == true) {
                        ServicoPedidoItensBean spib = new ServicoPedidoItensBean();
                        ServicoPedidoItensDAO spid = new ServicoPedidoItensDAO();

                        spib.setIdpedido(numpedido);
                        spib.setCodigo(tableitens.getValueAt(i, 2).toString());
                        spib.setDescricao(tableitens.getValueAt(i, 3).toString());
                        spib.setQtde(tableitens.getValueAt(i, 4).toString());
                        spib.setValor(tableitens.getValueAt(i, 5).toString());
                        spib.setTotal(tableitens.getValueAt(i, 6).toString());
                        spib.setPrazo(tableitens.getValueAt(i, 7).toString());
                        spib.setPedidocliente(tableitens.getValueAt(i, 8).toString());
                        spib.setNf("");
//                      idpedido, codigo, descricao, qtde, valor, total, prazo, pedidocliente, nf

                        spid.create(spib);

                        ServicoOrcamentoItensBean soib = new ServicoOrcamentoItensBean();
                        ServicoOrcamentoItensDAO soid = new ServicoOrcamentoItensDAO();

                        soib.setCodigo(tableitens.getValueAt(i, 2).toString());
                        soib.setDesc(tableitens.getValueAt(i, 3).toString());
                        soib.setQtd(tableitens.getValueAt(i, 4).toString());
                        soib.setValor(tableitens.getValueAt(i, 5).toString());
                        soib.setTotal(tableitens.getValueAt(i, 6).toString());
                        soib.setPrazo(tableitens.getValueAt(i, 7).toString());
                        soib.setPedido(tableitens.getValueAt(i, 8).toString());
                        soib.setDas(numpedido);
                        soib.setId(Integer.parseInt(tableitens.getValueAt(i, 1).toString()));
                        //codigo, descricao , qtd , valor , total , prazo , pedido , das, id

                        soid.update(soib);

                        tableitensatualizar();
                    }
                }
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
                                Logger.getLogger(DocumentosPedidoServico.class.getName()).log(Level.SEVERE, null, ex);
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
                    if (!tableitens.getValueAt(i, 9).equals("")) {
                        numerodas++;
                    }
                }
                if (tableitens.getRowCount() == numerodas) {
                    ServicoOrcamentoBean sob = new ServicoOrcamentoBean();
                    ServicoOrcamentoDAO sod = new ServicoOrcamentoDAO();

                    sob.setStatus("Fechado");
                    sob.setIdtela(txtnumeroorcamento.getText());
                    //status, id

                    sod.updatestatus(sob);
                } else {
                    ServicoOrcamentoBean sob = new ServicoOrcamentoBean();
                    ServicoOrcamentoDAO sod = new ServicoOrcamentoDAO();

                    sob.setStatus("Pedido Parcial");
                    sob.setIdtela(txtnumeroorcamento.getText());
                    //status, id

                    sod.updatestatus(sob);
                }
                ServicoOrcamentoDAO sod = new ServicoOrcamentoDAO();
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
        ServicoOrcamentoDAO sod = new ServicoOrcamentoDAO();

        for (ServicoOrcamentoBean sob : sod.pesquisa(txtpesquisa.getText())) {
            model.addRow(new Object[]{
                sob.getIdtela(),
                sob.getCliente(),
                sob.getStatus()
            });
        }
    }//GEN-LAST:event_txtpesquisaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelOrcamentoServico;
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
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JPanel jPanel2;
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
