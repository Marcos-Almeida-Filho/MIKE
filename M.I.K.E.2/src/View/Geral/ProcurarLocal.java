/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Geral;

import DAO.LocalDAO;
import View.vendas.VM;
import View.vendas.VM1;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class ProcurarLocal extends javax.swing.JInternalFrame {

    private final String origem;
    private static final LocalDAO LD = new LocalDAO();

    /**
     * Creates new form ProcuraMaterialVenda
     *
     * @param origin
     */
    public ProcurarLocal(String origin) {
        initComponents();
        readLocal();
        origem = origin;
    }

    public static void readLocal() {
        DefaultTableModel modelLocal = (DefaultTableModel) tableLocal.getModel();
        modelLocal.setNumRows(0);

        if (txtPesquisa.getText().length() == 0) {
            LD.readAtivo().forEach(lb -> {
                modelLocal.addRow(new Object[]{
                    lb.getId(),
                    lb.getNome()
                });
            });
        } else {
            LD.readPesquisaAtivo(txtPesquisa.getText()).forEach(lb -> {
                modelLocal.addRow(new Object[]{
                    lb.getId(),
                    lb.getNome()
                });
            });
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableLocal = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Procurar Material");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableLocal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Código"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableLocal.setName("tableLocal"); // NOI18N
        tableLocal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableLocalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableLocal);
        if (tableLocal.getColumnModel().getColumnCount() > 0) {
            tableLocal.getColumnModel().getColumn(0).setMinWidth(0);
            tableLocal.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableLocal.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel2.setName("jPanel2"); // NOI18N

        txtPesquisa.setName("txtPesquisa"); // NOI18N
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
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

    private void tableLocalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableLocalMouseClicked
        if (evt.getClickCount() == 2) {
            String escolha = tableLocal.getValueAt(tableLocal.getSelectedRow(), 1).toString();
            switch (origem) {
                case "VendasMaterial":
                    break;
                case "VM":
                    VM.txtLocal.setText(escolha);
                    break;
                case "VM1":
                    VM1.txtLocal.setText(escolha);
                    break;
            }
            dispose();
        }
    }//GEN-LAST:event_tableLocalMouseClicked

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        readLocal();
    }//GEN-LAST:event_txtPesquisaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tableLocal;
    public static javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
