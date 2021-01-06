/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Geral;

import DAO.InsumosDAO;
import DAO.OPMPDAO;
import Methods.SendEmail;
import View.vendas.EscolherMP;
import View.vendas.OP;
import View.vendas.VM;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class ProcurarMateriaPrima extends javax.swing.JInternalFrame {

    static OPMPDAO ompd = new OPMPDAO();
    static InsumosDAO id = new InsumosDAO();

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

        if (txtPesquisa.getText().length() > 0) {
            id.readPesquisa(txtPesquisa.getText()).forEach(ib -> {
                model.addRow(new Object[]{
                    ib.getId(),
                    ib.getCodigo(),
                    ib.getDescricao()
                });
            });
        } else {
            id.read().forEach(ib -> {
                model.addRow(new Object[]{
                    ib.getId(),
                    ib.getCodigo(),
                    ib.getDescricao()
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
        tablemateriais = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();

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
            String codigo = tablemateriais.getValueAt(tablemateriais.getSelectedRow(), 1).toString();
            String desc = tablemateriais.getValueAt(tablemateriais.getSelectedRow(), 2).toString();
            switch (origem) {
                case "VendasMaterial":
                    VM.txtmaterialdeorigem.setText(codigo);
                    break;
                case "OP":
                    String op = OP.txtNumOP.getText();
                    try {
                        ompd.create(op, codigo, desc, 0, true);
                    } catch (SQLException e) {
                        String msg = "Erro ao criar matéria prima da OP.";
                        JOptionPane.showMessageDialog(null, msg);

                        new Thread() {
                            @Override
                            public void run() {
                                SendEmail.EnviarErro2(msg, e);
                            }
                        }.start();
                    }
                    OP.lerMP(op);
                    break;
                case "EscolherMP":
                    EscolherMP.txtCodigo.setText(codigo);
                    EscolherMP.txtDesc.setText(desc);
                    break;
            }
            dispose();
        }
    }//GEN-LAST:event_tablemateriaisMouseClicked

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        readtablemateriais();
    }//GEN-LAST:event_txtPesquisaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tablemateriais;
    public static javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
