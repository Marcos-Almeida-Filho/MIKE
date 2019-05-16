/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.servicos;

import Bean.ClientesBean;
import DAO.ClientesDAO;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class ProcurarClienteOrcamentoServico extends javax.swing.JInternalFrame {

    /**
     * Creates new form ProcurarCliente
     */
    public ProcurarClienteOrcamentoServico() {
        initComponents();
        filltableclientes();
    }

    public static void filltableclientes() {
        DefaultTableModel model = (DefaultTableModel) tableclientes.getModel();
        model.setNumRows(0);
        ClientesDAO cd = new ClientesDAO();

        for (ClientesBean cb : cd.read()) {
            model.addRow(new Object[]{
                cb.getId(),
                cb.getNome(),
                cb.getRazaosocial()
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableclientes = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtpesquisa = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Procurar Cliente");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tableclientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Razão Social"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableclientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableclientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableclientes);
        if (tableclientes.getColumnModel().getColumnCount() > 0) {
            tableclientes.getColumnModel().getColumn(0).setMinWidth(40);
            tableclientes.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableclientes.getColumnModel().getColumn(0).setMaxWidth(40);
            tableclientes.getColumnModel().getColumn(1).setResizable(false);
            tableclientes.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        jLabel1.setText("Pesquisa");

        txtpesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpesquisa)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtpesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(168, 168, 168))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
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

    private void tableclientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableclientesMouseClicked
        if (evt.getClickCount() == 2) {
            CotacaoServico.txtnomecliente.setText(tableclientes.getValueAt(tableclientes.getSelectedRow(), 1).toString());

            ClientesDAO cd = new ClientesDAO();

            for (ClientesBean cb : cd.readcondicoes(CotacaoServico.txtnomecliente.getText())) {
                CotacaoServico.txtcondicao.setText(cb.getCondicaodepagamento());
                CotacaoServico.txtvendedor.setText(cb.getVendedor());
                CotacaoServico.txtrepresentante.setText(cb.getRepresentante());
            }
            dispose();
        }
    }//GEN-LAST:event_tableclientesMouseClicked

    private void txtpesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpesquisaKeyReleased
        DefaultTableModel model = (DefaultTableModel) tableclientes.getModel();
        model.setNumRows(0);
        ClientesDAO cd = new ClientesDAO();

        for (ClientesBean cb : cd.pesquisa(txtpesquisa.getText())) {
            model.addRow(new Object[]{
                cb.getId(),
                cb.getNome(),
                cb.getRazaosocial()
            });
        }
    }//GEN-LAST:event_txtpesquisaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tableclientes;
    public static javax.swing.JTextField txtpesquisa;
    // End of variables declaration//GEN-END:variables
}
