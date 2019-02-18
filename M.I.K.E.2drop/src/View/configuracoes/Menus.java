/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.configuracoes;

import Bean.MenusBean;
import DAO.MenusDAO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class Menus extends javax.swing.JInternalFrame {

    /**
     * Creates new form Menus
     */
    public Menus() {
        initComponents();
        readtablemenus();
    }

    public static void readtablemenus() {
        DefaultTableModel modelo = (DefaultTableModel) tablemenus.getModel();
        modelo.setNumRows(0);
        MenusDAO md = new MenusDAO();

        md.read().forEach((c) -> {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getNome()
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

        jLabel1 = new javax.swing.JLabel();
        txtpesquisa = new javax.swing.JTextField();
        btnsalvar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablemenus = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        btnnovo = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Cadastro de Menus");

        jLabel1.setText("Nome:");

        btnsalvar.setText("Salvar");
        btnsalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalvarActionPerformed(evt);
            }
        });

        tablemenus.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablemenus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablemenusMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablemenus);
        if (tablemenus.getColumnModel().getColumnCount() > 0) {
            tablemenus.getColumnModel().getColumn(0).setMinWidth(35);
            tablemenus.getColumnModel().getColumn(0).setPreferredWidth(35);
            tablemenus.getColumnModel().getColumn(0).setMaxWidth(35);
            tablemenus.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel2.setText("ID");

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(255, 255, 255));

        btnnovo.setText("Novo");
        btnnovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnovoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpesquisa))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnnovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnsalvar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtpesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsalvar)
                    .addComponent(btnnovo))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablemenusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablemenusMouseClicked
        if (evt.getClickCount() == 2) {
            txtid.setText(tablemenus.getValueAt(tablemenus.getSelectedRow(), 0).toString());
            txtpesquisa.setText(tablemenus.getValueAt(tablemenus.getSelectedRow(), 1).toString());
        }
    }//GEN-LAST:event_tablemenusMouseClicked

    private void btnsalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalvarActionPerformed
        if (txtid.getText().equals("")) {
            MenusBean mb = new MenusBean();
            MenusDAO udao = new MenusDAO();

            mb.setNome(txtpesquisa.getText());
            udao.create(mb);
            txtpesquisa.setText("");
            txtpesquisa.requestFocus();
            readtablemenus();
        } else {
            MenusBean mb = new MenusBean();
            MenusDAO udao = new MenusDAO();

            mb.setNome(txtpesquisa.getText());
            mb.setId(Integer.parseInt(txtid.getText()));
            udao.update(mb);
            txtid.setText("");
            txtpesquisa.setText("");
            txtpesquisa.requestFocus();
            readtablemenus();
        }
    }//GEN-LAST:event_btnsalvarActionPerformed

    private void btnnovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnovoActionPerformed
        int i = JOptionPane.showConfirmDialog(rootPane, "Gostaria de cadastrar um novo menu?", "Novo Menu", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            txtid.setText("");
            txtpesquisa.setText("");
            txtpesquisa.requestFocus();
        }
    }//GEN-LAST:event_btnnovoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnnovo;
    public javax.swing.JButton btnsalvar;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tablemenus;
    public javax.swing.JTextField txtid;
    public static javax.swing.JTextField txtpesquisa;
    // End of variables declaration//GEN-END:variables
}
