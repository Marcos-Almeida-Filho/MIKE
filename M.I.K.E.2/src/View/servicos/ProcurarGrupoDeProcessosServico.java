/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.servicos;

import Bean.ServicoGrupoDeProcessosBean;
import Bean.UsuariosBean;
import DAO.ServicoGrupoDeProcessosDAO;
import DAO.UsuariosDAO;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class ProcurarGrupoDeProcessosServico extends javax.swing.JInternalFrame {

    /**
     * Creates new form ProcurarCliente
     */
    public ProcurarGrupoDeProcessosServico() {
        initComponents();
        readtablegrupo();
    }

    public static void readtablegrupo() {
        DefaultTableModel model = (DefaultTableModel) tablegrupo.getModel();
        model.setNumRows(0);
        ServicoGrupoDeProcessosDAO sgdpd = new ServicoGrupoDeProcessosDAO();

        for (ServicoGrupoDeProcessosBean sgdpb : sgdpd.read()) {
            model.addRow(new Object[]{
                sgdpb.getId(),
                sgdpb.getNome()
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
        tablegrupo = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtpesquisa = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Procurar Grupo de Processos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tablegrupo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablegrupo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablegrupoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablegrupo);
        if (tablegrupo.getColumnModel().getColumnCount() > 0) {
            tablegrupo.getColumnModel().getColumn(0).setMinWidth(30);
            tablegrupo.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablegrupo.getColumnModel().getColumn(0).setMaxWidth(30);
            tablegrupo.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Grupo de Processos"));

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
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
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

    private void tablegrupoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablegrupoMouseClicked
        if (evt.getClickCount() == 2) {
            ServicoMateriais.txtgrupo.setText(tablegrupo.getValueAt(tablegrupo.getSelectedRow(),1).toString());
            dispose();
        }
    }//GEN-LAST:event_tablegrupoMouseClicked

    private void txtpesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpesquisaKeyReleased
        if (txtpesquisa.getText().equals("")) {
            DefaultTableModel model = (DefaultTableModel) tablegrupo.getModel();
            model.setNumRows(0);
            ServicoGrupoDeProcessosDAO ud = new ServicoGrupoDeProcessosDAO();

            for (ServicoGrupoDeProcessosBean ub : ud.pesquisa(txtpesquisa.getText())) {
                model.addRow(new Object[]{
                    ub.getId(),
                    ub.getNome()
                });
            }
        } else {
            DefaultTableModel model = (DefaultTableModel) tablegrupo.getModel();
            model.setNumRows(0);
            ServicoGrupoDeProcessosDAO ud = new ServicoGrupoDeProcessosDAO();

            for (ServicoGrupoDeProcessosBean ub : ud.pesquisa(txtpesquisa.getText())) {
                model.addRow(new Object[]{
                    ub.getId(),
                    ub.getNome()
                });
            }
        }
    }//GEN-LAST:event_txtpesquisaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tablegrupo;
    public static javax.swing.JTextField txtpesquisa;
    // End of variables declaration//GEN-END:variables
}