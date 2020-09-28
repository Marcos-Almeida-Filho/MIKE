/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Geral;

import DAO.InsumosDAO;
import View.vendas.VM;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class ProcurarMateriaPrima extends javax.swing.JInternalFrame {

    /**
     * Creates new form ProcuraMaterialVenda
     */
    private String origem;

    public ProcurarMateriaPrima(String origin) {
        initComponents();
        readtablemateriais();
        origem = origin;
    }
    
    public static void readtablemateriais() {
        DefaultTableModel model = (DefaultTableModel) tablemateriais.getModel();
        model.setNumRows(0);
        
        InsumosDAO id = new InsumosDAO();
        
        id.read().forEach(ib -> {
            model.addRow(new Object[]{
                ib.getId(),
                ib.getCodigo(),
                ib.getDescricao()
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablemateriais = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtpesquisa = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Procurar Matéria-Prima");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablemateriais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Código", "Descrição"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablemateriais.setName("tablemateriais"); // NOI18N
        tablemateriais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablemateriaisMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablemateriais);
        if (tablemateriais.getColumnModel().getColumnCount() > 0) {
            tablemateriais.getColumnModel().getColumn(0).setMinWidth(0);
            tablemateriais.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablemateriais.getColumnModel().getColumn(0).setMaxWidth(0);
            tablemateriais.getColumnModel().getColumn(1).setMinWidth(150);
            tablemateriais.getColumnModel().getColumn(1).setPreferredWidth(150);
            tablemateriais.getColumnModel().getColumn(1).setMaxWidth(150);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel2.setName("jPanel2"); // NOI18N

        txtpesquisa.setName("txtpesquisa"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtpesquisa)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtpesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void tablemateriaisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablemateriaisMouseClicked
        if (evt.getClickCount() == 2) {
            switch (origem) {
                case "VendasMaterial":
                    VM.txtmaterialdeorigem.setText(tablemateriais.getValueAt(tablemateriais.getSelectedRow(), 1).toString());
                    dispose();
                    break;
            }
        }
    }//GEN-LAST:event_tablemateriaisMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tablemateriais;
    public javax.swing.JTextField txtpesquisa;
    // End of variables declaration//GEN-END:variables
}
