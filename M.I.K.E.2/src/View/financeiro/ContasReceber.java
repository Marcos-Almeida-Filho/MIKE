/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.financeiro;

import DAO.CARDAO;
import DAO.CARDocumentosDAO;
import Methods.Dates;
import Methods.Valores;
import View.Geral.MudarStatus;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JDesktopPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class ContasReceber extends javax.swing.JInternalFrame {

    /**
     * Creates new form ContasPagar
     */
    private static JTable getNewRenderedTable(final JTable table, int coluna) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                String status = (String) table.getModel().getValueAt(row, coluna);
                switch (status) {
                    case "Lançado":
                        setBackground(Color.BLUE);
                        setForeground(Color.BLACK);
                        break;
                    case "Aprovado":
                        setBackground(Color.GREEN);
                        setForeground(Color.BLACK);
                        break;
                    case "Automático":
                        setBackground(Color.ORANGE);
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

    public ContasReceber() {
        initComponents();
        readtablecar();
        getNewRenderedTable(tablecar, 8);
    }

    public static void readtablecar() {
        DefaultTableModel model = (DefaultTableModel) tablecar.getModel();
        model.setNumRows(0);

        CARDAO card = new CARDAO();

        switch (cbstatus.getSelectedIndex()) {
            case 0:
                card.readaberto().forEach((carb) -> {
                    String datapagamento;
                    if (carb.getDatarecebimento() == null) {
                        datapagamento = carb.getDatarecebimento();
                    } else {
                        datapagamento = Dates.TransformarDataCurtaDoDB(carb.getDatarecebimento());
                    }
                    model.addRow(new Object[]{
                        false,
                        carb.getId(),
                        carb.getNotafiscal(),
                        carb.getCliente(),
                        carb.getParcela(),
                        Valores.TransformarValorFloatEmDinheiro(String.valueOf(carb.getValorparcela())),
                        Dates.TransformarDataCurtaDoDB(carb.getDataparcela()),
                        datapagamento,
                        carb.getStatus()
                    });
                });
                break;
            case 6:
                card.readtodos().forEach((carb) -> {
                    String datapagamento;
                    if (carb.getDatarecebimento() == null) {
                        datapagamento = carb.getDatarecebimento();
                    } else {
                        datapagamento = Dates.TransformarDataCurtaDoDB(carb.getDatarecebimento());
                    }
                    model.addRow(new Object[]{
                        false,
                        carb.getId(),
                        carb.getNotafiscal(),
                        carb.getCliente(),
                        carb.getParcela(),
                        carb.getValorparcela(),
                        Dates.TransformarDataCurtaDoDB(carb.getDataparcela()),
                        datapagamento,
                        carb.getStatus()
                    });
                });
                break;
            default:
                card.readstatus(cbstatus.getSelectedItem().toString()).forEach((carb) -> {
                    String datapagamento;
                    if (carb.getDatarecebimento() == null) {
                        datapagamento = carb.getDatarecebimento();
                    } else {
                        datapagamento = Dates.TransformarDataCurtaDoDB(carb.getDatarecebimento());
                    }
                    model.addRow(new Object[]{
                        false,
                        carb.getId(),
                        carb.getNotafiscal(),
                        carb.getCliente(),
                        carb.getParcela(),
                        carb.getValorparcela(),
                        Dates.TransformarDataCurtaDoDB(carb.getDataparcela()),
                        datapagamento,
                        carb.getStatus()
                    });
                });
                break;
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablecar = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtpesquisa = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        cbstatus = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

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
        setTitle("Contas a Receber");
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

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablecar.setAutoCreateRowSorter(true);
        tablecar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Nota Fiscal", "Cliente", "Parcela", "Valor Parcela", "Data de Vencimento", "Data de Recebimento", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
        tablecar.setName("tablecar"); // NOI18N
        tablecar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablecarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablecar);
        if (tablecar.getColumnModel().getColumnCount() > 0) {
            tablecar.getColumnModel().getColumn(0).setMinWidth(30);
            tablecar.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablecar.getColumnModel().getColumn(0).setMaxWidth(30);
            tablecar.getColumnModel().getColumn(1).setMinWidth(0);
            tablecar.getColumnModel().getColumn(1).setPreferredWidth(0);
            tablecar.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel1.setText("Pesquisa");
        jLabel1.setName("jLabel1"); // NOI18N

        txtpesquisa.setName("txtpesquisa"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpesquisa)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtpesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton3.setText("Receber em Lote");
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
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
        AdicionarContasAReceber cp = new AdicionarContasAReceber();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(cp);
        Dimension jif = cp.getSize();
        Dimension d = desk.getSize();
        cp.setLocation((d.width - jif.width) / 2, (d.height - jif.height) / 2);
        cp.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbstatusActionPerformed
        readtablecar();
    }//GEN-LAST:event_cbstatusActionPerformed

    private void tablecarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablecarMouseClicked
        if (evt.getClickCount() == 2) {
            ContaReceber acp = new ContaReceber();
            JDesktopPane desk = this.getDesktopPane();
            desk.add(acp);
            ContaReceber.txtid.setText(tablecar.getValueAt(tablecar.getSelectedRow(), 1).toString());

            //DAOs para pesquisa
            CARDAO card = new CARDAO();
            CARDocumentosDAO cdd = new CARDocumentosDAO();

            //int ID
            int id = Integer.parseInt(ContaReceber.txtid.getText());

            card.click(id).forEach(cb -> {
                ContaReceber.txtdatalancamento.setText(cb.getDatalancamento());
                ContaReceber.txtfornecedor.setText(cb.getCliente());
                ContaReceber.txtnf.setText(String.valueOf(cb.getNotafiscal()));
                ContaReceber.txtemissao.setText(Dates.TransformarDataCurtaDoDB(cb.getDataemissao()));
                ContaReceber.txttotal.setText(Valores.TransformarValorFloatEmDinheiro(String.valueOf(cb.getTotal())));
                ContaReceber.txtparcela.setText(cb.getParcela());
                ContaReceber.txtvalorparcela.setText(Valores.TransformarValorFloatEmDinheiro(String.valueOf(cb.getValorparcela())));
                ContaReceber.txtvencimento.setText(Dates.TransformarDataCurtaDoDB(cb.getDataparcela()));
                ContaReceber.txtpagamento.setText(cb.getDatarecebimento());
            });

            //DefaultTableModel para adicionar linhas
            DefaultTableModel modeldoc = (DefaultTableModel) ContaReceber.tabledocs.getModel();

            cdd.readitens(id).forEach(cdb -> {
                modeldoc.addRow(new Object[]{
                    cdb.getId(),
                    false,
                    cdb.getDescricao(),
                    cdb.getLocal()
                });
            });

            Dimension jif = acp.getSize();
            Dimension d = desk.getSize();
            acp.setLocation((d.width - jif.width) / 2, (d.height - jif.height) / 2);
            acp.setVisible(true);
            ContaReceber.travacampos();
        }
    }//GEN-LAST:event_tablecarMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        PagarEmLote pel = new PagarEmLote();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(pel);
        Dimension jif = pel.getSize();
        Dimension d = desk.getSize();
        pel.setLocation((d.width - jif.width) / 2, (d.height - jif.height) / 2);
        pel.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String[] ops = new String[4];
        ops[0] = "Selecione";
        ops[1] = "Lançado";
        ops[2] = "Aprovado";
        ops[3] = "Automático";
        MudarStatus sel = new MudarStatus(ops, "Mudar Status CAR", "CAR");
        JDesktopPane desk = this.getDesktopPane();
        desk.add(sel);
        Dimension jif = sel.getSize();
        Dimension d = desk.getSize();
        sel.setLocation((d.width - jif.width) / 2, (d.height - jif.height) / 2);
        sel.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> cbstatus;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tablecar;
    public javax.swing.JTextField txtpesquisa;
    // End of variables declaration//GEN-END:variables
}
