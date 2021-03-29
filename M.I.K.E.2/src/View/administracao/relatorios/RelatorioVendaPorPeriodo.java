/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.administracao.relatorios;

import DAO.VendasPedidoItensDAO;
import Methods.Dates;
import Methods.ExcelMethods;
import Methods.Valores;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class RelatorioVendaPorPeriodo extends javax.swing.JInternalFrame {

    VendasPedidoItensDAO vpid = new VendasPedidoItensDAO();

    /**
     * Creates new form RelatorioVendaPorCliente
     */
    public RelatorioVendaPorPeriodo() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        dateInicio = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        dateFinal = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVendas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Relatório de Vendas Por Período");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel2.setText("Início");
        jLabel2.setName("jLabel2"); // NOI18N

        dateInicio.setDateFormatString("dd/MM/yyyy");
        dateInicio.setName("dateInicio"); // NOI18N

        jLabel3.setText("Final");
        jLabel3.setName("jLabel3"); // NOI18N

        dateFinal.setDateFormatString("dd/MM/yyyy");
        dateFinal.setName("dateFinal"); // NOI18N

        jButton2.setText("OK");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Pedido", "Cliente", "Código", "Descrição", "Vr. Unitário", "Qtde", "NF", "Pedido Cliente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableVendas.setName("tableVendas"); // NOI18N
        jScrollPane1.setViewportView(tableVendas);
        if (tableVendas.getColumnModel().getColumnCount() > 0) {
            tableVendas.getColumnModel().getColumn(0).setMinWidth(100);
            tableVendas.getColumnModel().getColumn(0).setPreferredWidth(100);
            tableVendas.getColumnModel().getColumn(0).setMaxWidth(100);
            tableVendas.getColumnModel().getColumn(1).setMinWidth(100);
            tableVendas.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableVendas.getColumnModel().getColumn(1).setMaxWidth(100);
            tableVendas.getColumnModel().getColumn(2).setMinWidth(150);
            tableVendas.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableVendas.getColumnModel().getColumn(2).setMaxWidth(150);
            tableVendas.getColumnModel().getColumn(5).setMinWidth(80);
            tableVendas.getColumnModel().getColumn(5).setPreferredWidth(80);
            tableVendas.getColumnModel().getColumn(5).setMaxWidth(80);
            tableVendas.getColumnModel().getColumn(6).setMinWidth(60);
            tableVendas.getColumnModel().getColumn(6).setPreferredWidth(60);
            tableVendas.getColumnModel().getColumn(6).setMaxWidth(60);
            tableVendas.getColumnModel().getColumn(7).setMinWidth(80);
            tableVendas.getColumnModel().getColumn(7).setPreferredWidth(80);
            tableVendas.getColumnModel().getColumn(7).setMaxWidth(80);
            tableVendas.getColumnModel().getColumn(8).setMinWidth(100);
            tableVendas.getColumnModel().getColumn(8).setPreferredWidth(100);
            tableVendas.getColumnModel().getColumn(8).setMaxWidth(100);
        }

        jButton1.setText("Exportar em Excel");
        jButton1.setName("jButton1"); // NOI18N
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1009, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (dateInicio.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Escolha uma data de início.");
        } else if (dateFinal.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Escolha uma data final.");
        } else {
            DefaultTableModel model = (DefaultTableModel) tableVendas.getModel();
            model.setNumRows(0);

            vpid.readItensVendasPorPeriodo(Dates.CriarDataCurtaDBJDateChooser(dateInicio.getDate()), Dates.CriarDataCurtaDBJDateChooser(dateFinal.getDate())).forEach(v -> {
                model.addRow(new Object[]{
                    Dates.TransformarDataCurtaDoDB(v.getData()),
                    v.getDav(),
                    v.getCliente(),
                    v.getCodigo(),
                    v.getDescricao(),
                    Valores.TransformarDoubleDBemDinheiroComLocal(v.getValorUnitario()),
                    v.getQtd(),
                    v.getNf(),
                    v.getPedido()
                });
            });
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ExcelMethods.exportTable(tableVendas, "Relatorio Vendas Geral", 0);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser dateFinal;
    private com.toedter.calendar.JDateChooser dateInicio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableVendas;
    // End of variables declaration//GEN-END:variables
}
