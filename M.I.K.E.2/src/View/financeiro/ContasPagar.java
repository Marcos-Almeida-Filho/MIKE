/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.financeiro;

import Bean.CAPBean;
import DAO.CAPDAO;
import Methods.Colors;
import Methods.Dates;
import Methods.ExcelMethods;
import Methods.Telas;
import Methods.Valores;
import View.Geral.MudarStatus;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class ContasPagar extends javax.swing.JInternalFrame {

    /**
     * Creates new form ContasPagar
     */
    public ContasPagar() {
        initComponents();
        datasJDateChooser();
        readtablecap();
        getNewRenderedTable(tablecap, 9);
        valores();
    }
    
    private static JTable getNewRenderedTable(final JTable table, int coluna) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                String status = (String) table.getModel().getValueAt(row, coluna);
                switch (status) {
                    case "Lançado":
                        setBackground(Colors.blue);
                        setForeground(Color.BLACK);
                        break;
                    case "Aprovado":
                        setBackground(Colors.green);
                        setForeground(Color.BLACK);
                        break;
                    case "Automático":
                        setBackground(Colors.orange);
                        setForeground(Color.BLACK);
                        break;
                    default:
                        setBackground(table.getBackground());
                        setForeground(table.getForeground());
                        break;
                }
                return this;
            }
        });
        return table;
    }
    
    public static void datasJDateChooser() {
        Dates.SetarDataJDateChooser(jdateinicio, Dates.primeiroDiaDoMes());
        Dates.SetarDataJDateChooser(jdatefim, Dates.ultimoDiaDoMes());
    }

    public static void valores() {
        String total, pago, apagar;
        double totald, pagod = 0, apagard = 0;

        for (int i = 0; i < tablecap.getRowCount(); i++) {
            if (tablecap.getValueAt(i, 9).equals("Pago")) {
                pagod += Valores.TransformarDinheiroEmValorDouble(tablecap.getValueAt(i, 6).toString());
            } else {
                apagard += Valores.TransformarDinheiroEmValorDouble(tablecap.getValueAt(i, 6).toString());
            }
        }

        totald = pagod + apagard;

        total = "R$ " + Valores.TransformarValorFloatEmDinheiro(String.valueOf(totald));
        pago = "R$ " + Valores.TransformarValorFloatEmDinheiro(String.valueOf(pagod));
        apagar = "R$ " + Valores.TransformarValorFloatEmDinheiro(String.valueOf(apagard));

        txtpago.setText(pago);
        txtapagar.setText(apagar);
        txttotal.setText(total);
    }

    public static void readtablecap() {
        DefaultTableModel model = (DefaultTableModel) tablecap.getModel();
        model.setNumRows(0);

        CAPDAO capd = new CAPDAO();

        if (txtPesquisa.getText().length() == 0) {
            switch (cbstatus.getSelectedIndex()) {
                case 0:
                    capd.readaberto(Dates.CriarDataCurtaDBJDateChooser(jdateinicio.getDate()), Dates.CriarDataCurtaDBJDateChooser(jdatefim.getDate())).forEach((capb) -> {
                        String datapagamento;

                        if (capb.getDatapagamento() == null) {
                            datapagamento = capb.getDatapagamento();
                        } else {
                            datapagamento = Dates.TransformarDataCurtaDoDB(capb.getDatapagamento());
                        }
                        model.addRow(new Object[]{
                            false,
                            capb.getId(),
                            capb.isObs(),
                            capb.getNumero(),
                            capb.getFornecedor(),
                            capb.getParcela(),
                            capb.getValorparcela(),
                            Dates.TransformarDataCurtaDoDB(capb.getDataparcela()),
                            datapagamento,
                            capb.getStatus()
                        });
                    });
                    break;
                case 6:
                    capd.readtodos(Dates.CriarDataCurtaDBJDateChooser(jdateinicio.getDate()), Dates.CriarDataCurtaDBJDateChooser(jdatefim.getDate())).forEach((capb) -> {

                        String datapagamento;

                        if (capb.getDatapagamento() == null) {
                            datapagamento = capb.getDatapagamento();
                        } else {
                            datapagamento = Dates.TransformarDataCurtaDoDB(capb.getDatapagamento());
                        }
                        model.addRow(new Object[]{
                            false,
                            capb.getId(),
                            capb.isObs(),
                            capb.getNumero(),
                            capb.getFornecedor(),
                            capb.getParcela(),
                            capb.getValorparcela(),
                            Dates.TransformarDataCurtaDoDB(capb.getDataparcela()),
                            datapagamento,
                            capb.getStatus()
                        });
                    });
                    break;
                default:
                    capd.readstatus(cbstatus.getSelectedItem().toString(), Dates.CriarDataCurtaDBJDateChooser(jdateinicio.getDate()), Dates.CriarDataCurtaDBJDateChooser(jdatefim.getDate())).forEach((capb) -> {

                        String datapagamento;

                        if (capb.getDatapagamento() == null) {
                            datapagamento = capb.getDatapagamento();
                        } else {
                            datapagamento = Dates.TransformarDataCurtaDoDB(capb.getDatapagamento());
                        }
                        model.addRow(new Object[]{
                            false,
                            capb.getId(),
                            capb.isObs(),
                            capb.getNumero(),
                            capb.getFornecedor(),
                            capb.getParcela(),
                            capb.getValorparcela(),
                            Dates.TransformarDataCurtaDoDB(capb.getDataparcela()),
                            datapagamento,
                            capb.getStatus()
                        });
                    });
                    break;
            }
        } else {
            switch (cbstatus.getSelectedIndex()) {
                case 0:
                    capd.readAbertoPesquisa(Dates.CriarDataCurtaDBJDateChooser(jdateinicio.getDate()), Dates.CriarDataCurtaDBJDateChooser(jdatefim.getDate()), txtPesquisa.getText()).forEach((capb) -> {
                        String datapagamento;

                        if (capb.getDatapagamento() == null) {
                            datapagamento = capb.getDatapagamento();
                        } else {
                            datapagamento = Dates.TransformarDataCurtaDoDB(capb.getDatapagamento());
                        }
                        model.addRow(new Object[]{
                            false,
                            capb.getId(),
                            capb.isObs(),
                            capb.getNumero(),
                            capb.getFornecedor(),
                            capb.getParcela(),
                            capb.getValorparcela(),
                            Dates.TransformarDataCurtaDoDB(capb.getDataparcela()),
                            datapagamento,
                            capb.getStatus()
                        });
                    });
                    break;
                case 6:
                    capd.readTodosPesquisa(Dates.CriarDataCurtaDBJDateChooser(jdateinicio.getDate()), Dates.CriarDataCurtaDBJDateChooser(jdatefim.getDate()), txtPesquisa.getText()).forEach((capb) -> {

                        String datapagamento;

                        if (capb.getDatapagamento() == null) {
                            datapagamento = capb.getDatapagamento();
                        } else {
                            datapagamento = Dates.TransformarDataCurtaDoDB(capb.getDatapagamento());
                        }
                        model.addRow(new Object[]{
                            false,
                            capb.getId(),
                            capb.isObs(),
                            capb.getNumero(),
                            capb.getFornecedor(),
                            capb.getParcela(),
                            capb.getValorparcela(),
                            Dates.TransformarDataCurtaDoDB(capb.getDataparcela()),
                            datapagamento,
                            capb.getStatus()
                        });
                    });
                    break;
                default:
                    capd.readStatusPesquisa(cbstatus.getSelectedItem().toString(), Dates.CriarDataCurtaDBJDateChooser(jdateinicio.getDate()), Dates.CriarDataCurtaDBJDateChooser(jdatefim.getDate()), txtPesquisa.getText()).forEach((capb) -> {

                        String datapagamento;

                        if (capb.getDatapagamento() == null) {
                            datapagamento = capb.getDatapagamento();
                        } else {
                            datapagamento = Dates.TransformarDataCurtaDoDB(capb.getDatapagamento());
                        }
                        model.addRow(new Object[]{
                            false,
                            capb.getId(),
                            capb.isObs(),
                            capb.getNumero(),
                            capb.getFornecedor(),
                            capb.getParcela(),
                            capb.getValorparcela(),
                            Dates.TransformarDataCurtaDoDB(capb.getDataparcela()),
                            datapagamento,
                            capb.getStatus()
                        });
                    });
                    break;
            }
        }
        valores();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablecap = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        cbstatus = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jdatefim = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jdateinicio = new com.toedter.calendar.JDateChooser();
        jButton6 = new javax.swing.JButton();
        txttotal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtapagar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtpago = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();

        jPanel2.setName("jPanel2"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setClosable(true);
        setTitle("Contas a Pagar");
        setName("Form"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jButton1.setText("Incluir");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Excluir");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablecap.setAutoCreateRowSorter(true);
        tablecap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Obs", "Nota Fiscal", "Fornecedor", "Parcela", "Valor Parcela", "Data de Vencimento", "Data de Pagamento", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
        tablecap.setName("tablecap"); // NOI18N
        tablecap.setShowHorizontalLines(true);
        tablecap.setShowVerticalLines(true);
        tablecap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablecapMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablecap);
        if (tablecap.getColumnModel().getColumnCount() > 0) {
            tablecap.getColumnModel().getColumn(0).setMinWidth(30);
            tablecap.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablecap.getColumnModel().getColumn(0).setMaxWidth(30);
            tablecap.getColumnModel().getColumn(1).setMinWidth(0);
            tablecap.getColumnModel().getColumn(1).setPreferredWidth(0);
            tablecap.getColumnModel().getColumn(1).setMaxWidth(0);
            tablecap.getColumnModel().getColumn(2).setMinWidth(35);
            tablecap.getColumnModel().getColumn(2).setPreferredWidth(35);
            tablecap.getColumnModel().getColumn(2).setMaxWidth(35);
            tablecap.getColumnModel().getColumn(3).setMinWidth(120);
            tablecap.getColumnModel().getColumn(3).setPreferredWidth(120);
            tablecap.getColumnModel().getColumn(3).setMaxWidth(120);
            tablecap.getColumnModel().getColumn(5).setMinWidth(60);
            tablecap.getColumnModel().getColumn(5).setPreferredWidth(60);
            tablecap.getColumnModel().getColumn(5).setMaxWidth(60);
            tablecap.getColumnModel().getColumn(6).setMinWidth(85);
            tablecap.getColumnModel().getColumn(6).setPreferredWidth(85);
            tablecap.getColumnModel().getColumn(6).setMaxWidth(85);
            tablecap.getColumnModel().getColumn(7).setMinWidth(125);
            tablecap.getColumnModel().getColumn(7).setPreferredWidth(125);
            tablecap.getColumnModel().getColumn(7).setMaxWidth(125);
            tablecap.getColumnModel().getColumn(8).setMinWidth(125);
            tablecap.getColumnModel().getColumn(8).setPreferredWidth(125);
            tablecap.getColumnModel().getColumn(8).setMaxWidth(125);
            tablecap.getColumnModel().getColumn(9).setMinWidth(75);
            tablecap.getColumnModel().getColumn(9).setPreferredWidth(75);
            tablecap.getColumnModel().getColumn(9).setMaxWidth(75);
        }

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel1.setText("Pesquisa");
        jLabel1.setName("jLabel1"); // NOI18N

        txtPesquisa.setName("txtPesquisa"); // NOI18N
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro Status"));
        jPanel4.setName("jPanel4"); // NOI18N

        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Aberto", "Ativo", "Lançado", "Aprovado", "Automático", "Pago", "Todos" }));
        cbstatus.setName("cbstatus"); // NOI18N
        cbstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbstatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton3.setText("Pagar em Lote");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Alterar Status");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Lançar Pagamentos RH");
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro Data"));
        jPanel5.setName("jPanel5"); // NOI18N

        jdatefim.setDateFormatString("dd/MM/yyyy");
        jdatefim.setName("jdatefim"); // NOI18N

        jLabel2.setText("Final");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("Início");
        jLabel3.setName("jLabel3"); // NOI18N

        jdateinicio.setDateFormatString("dd/MM/yyyy");
        jdateinicio.setName("jdateinicio"); // NOI18N

        jButton6.setText("Atualizar");
        jButton6.setName("jButton6"); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jdateinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jdatefim, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton6)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jdateinicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jdatefim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txttotal.setEditable(false);
        txttotal.setName("txttotal"); // NOI18N

        jLabel4.setText("Total");
        jLabel4.setName("jLabel4"); // NOI18N

        txtapagar.setEditable(false);
        txtapagar.setName("txtapagar"); // NOI18N

        jLabel5.setText("A Pagar");
        jLabel5.setName("jLabel5"); // NOI18N

        txtpago.setEditable(false);
        txtpago.setName("txtpago"); // NOI18N

        jLabel6.setText("Pago");
        jLabel6.setName("jLabel6"); // NOI18N

        jButton7.setText("Exportar para Excel");
        jButton7.setName("jButton7"); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtpago, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtapagar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtapagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtpago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton7))
                .addContainerGap())
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AdicionarContasAPagar cp = new AdicionarContasAPagar();
        Telas.AparecerTela(cp);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbstatusActionPerformed
        readtablecap();
    }//GEN-LAST:event_cbstatusActionPerformed

    private void tablecapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablecapMouseClicked
        if (evt.getClickCount() == 2) {
            ContaPagar acp = new ContaPagar(Integer.parseInt(tablecap.getValueAt(tablecap.getSelectedRow(), 1).toString()));
            Telas.AparecerTela(acp);
            ContaPagar.travacampos();
        }
    }//GEN-LAST:event_tablecapMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int pago = 0, seltrue = 0;
        for (int i = 0; i < tablecap.getRowCount(); i++) {
            if (tablecap.getValueAt(i, 0).equals(true)) {
                seltrue++;
                if (tablecap.getValueAt(i, 9).equals("Pago")) {
                    pago++;
                }
            }
        }
        if (seltrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum pagamento selecionado.");
        } else if (pago > 0) {
            JOptionPane.showMessageDialog(null, "Pagamentos selecionados já estão pagos.");
        } else {
            PagarEmLote pel = new PagarEmLote();
            Telas.AparecerTela(pel);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int lancado = 0, aprovado = 0, ativo = 0, pago = 0, seltrue = 0;
        String[] ops;
        String status;
        for (int i = 0; i < tablecap.getRowCount(); i++) {
            if (tablecap.getValueAt(i, 0).equals(true)) {
                seltrue++;
                status = tablecap.getValueAt(i, 9).toString();
                switch (status) {
                    case "Lançado":
                        lancado++;
                        break;
                    case "Ativo":
                        ativo++;
                        break;
                    case "Aprovado":
                        aprovado++;
                        break;
                    case "Pago":
                        pago++;
                        break;
                }
            }
        }
        if (seltrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum pagamento selecionado.");
        } else if ((lancado > 0 && (ativo > 0 | aprovado > 0 | pago > 0)) || (ativo > 0 && (aprovado > 0 | pago > 0)) || (aprovado > 0 && pago > 0)) {
            JOptionPane.showMessageDialog(null, "Foi escolhido mais de um status!");
        } else {
            if (lancado > 0) {
                ops = new String[3];
                ops[0] = "Selecione";
                ops[1] = "Aprovado";
                ops[2] = "Automático";
                MudarStatus sel = new MudarStatus(ops, "Mudar Status CAP", "CAP");
                Telas.AparecerTela(sel);
            }
            if (ativo > 0) {
                ops = new String[4];
                ops[0] = "Selecione";
                ops[1] = "Lançado";
                ops[2] = "Aprovado";
                ops[3] = "Automático";
                MudarStatus sel = new MudarStatus(ops, "Mudar Status CAP", "CAP");
                Telas.AparecerTela(sel);
            }
            if (aprovado > 0) {
                JOptionPane.showMessageDialog(null, "Pagamentos selecionados estão aprovados. Favor usar botão Pagar em Lote.");
            }
            if (pago > 0) {
                int resp = JOptionPane.showConfirmDialog(null, "Pagamentos já pagos.\nDeseja realmente alterar os status?", "Title", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    ops = new String[5];
                    ops[0] = "Selecione";
                    ops[1] = "Ativo";
                    ops[2] = "Lançado";
                    ops[3] = "Aprovado";
                    ops[4] = "Automático";
                    MudarStatus sel = new MudarStatus(ops, "Mudar Status CAP", "CAP");
                    Telas.AparecerTela(sel);
                }
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        PagamentoRH prh = new PagamentoRH();
        Telas.AparecerTela(prh);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int numtrue = 0, numpago = 0;
        for (int i = 0; i < tablecap.getRowCount(); i++) {
            if (tablecap.getValueAt(i, 0).equals(true)) {
                numtrue++;
            }
            if (tablecap.getValueAt(i, 9).equals("Pago")) {
                numpago++;
            }
        }
        if (numtrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum registro selecionado.");
        } else if (numpago > 0) {
            JOptionPane.showMessageDialog(null, "Registros pagos selecionados.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir os registros selecionados?", "Excluir Registros", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                CAPDAO cd = new CAPDAO();
                CAPBean cb = new CAPBean();

                for (int i = 0; i < tablecap.getRowCount(); i++) {
                    if (tablecap.getValueAt(i, 0).equals(true)) {
                        cb.setId(Integer.parseInt(tablecap.getValueAt(i, 1).toString()));

                        //id = ?
                        cd.delete(cb);
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Registro(s) excluído(s) com sucesso!");
        readtablecap();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        readtablecap();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        readtablecap();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            ExcelMethods.exportTable(tablecap, new File("/cap.xls"), 3);
        } catch (IOException ex) {
            Logger.getLogger(ContasPagar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> cbstatus;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton5;
    public javax.swing.JButton jButton6;
    public javax.swing.JButton jButton7;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JScrollPane jScrollPane1;
    public static com.toedter.calendar.JDateChooser jdatefim;
    public static com.toedter.calendar.JDateChooser jdateinicio;
    public static javax.swing.JTable tablecap;
    public static javax.swing.JTextField txtPesquisa;
    public static javax.swing.JTextField txtapagar;
    public static javax.swing.JTextField txtpago;
    public static javax.swing.JTextField txttotal;
    // End of variables declaration//GEN-END:variables
}
