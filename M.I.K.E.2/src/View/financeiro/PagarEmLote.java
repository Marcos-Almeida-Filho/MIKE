/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.financeiro;

import Bean.CAPBean;
import DAO.BancosDAO;
import DAO.CAPDAO;
import Methods.Dates;
import static View.financeiro.ContasPagar.readtablecap;
import static View.financeiro.ContasPagar.tablecap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class PagarEmLote extends javax.swing.JInternalFrame {

    /**
     * Creates new form PagarEmLote
     */
    
    static BancosDAO bd = new BancosDAO();
    
    public PagarEmLote() {
        initComponents();
        readcbbancos();
        campos();
    }

    public static void readcbbancos() {
        bd.read().forEach((bb) -> {
            cbbanco.addItem(bb.getBanco());
        });
    }

    public static void campos() {
        String metodo = cbmetodo.getSelectedItem().toString();
        switch (metodo) {
            case "Selecione":
                lblbanco.setVisible(false);
                cbbanco.setVisible(false);
                lblcheque.setVisible(false);
                txtcheque.setVisible(false);
                break;
            case "Boleto":
                lblbanco.setVisible(true);
                cbbanco.setVisible(true);
                lblcheque.setVisible(false);
                txtcheque.setVisible(false);
                break;
            case "Depósito":
                lblbanco.setVisible(false);
                cbbanco.setVisible(false);
                lblcheque.setVisible(false);
                txtcheque.setVisible(false);
                break;
            case "Transferência":
                lblbanco.setVisible(true);
                cbbanco.setVisible(true);
                lblcheque.setVisible(false);
                txtcheque.setVisible(false);
                break;
            case "Cartão de Crédito":
                lblbanco.setVisible(false);
                cbbanco.setVisible(false);
                lblcheque.setVisible(false);
                txtcheque.setVisible(false);
                break;
            case "Cheque":
                lblbanco.setVisible(false);
                cbbanco.setVisible(false);
                lblcheque.setVisible(true);
                txtcheque.setVisible(true);
                break;
            case "Dinheiro":
                lblbanco.setVisible(false);
                cbbanco.setVisible(false);
                lblcheque.setVisible(false);
                txtcheque.setVisible(false);
                break;
            case "Débito Automático":
                lblbanco.setVisible(true);
                cbbanco.setVisible(true);
                lblcheque.setVisible(false);
                txtcheque.setVisible(false);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblbanco = new javax.swing.JLabel();
        cbbanco = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cbmetodo = new javax.swing.JComboBox<>();
        lblcheque = new javax.swing.JLabel();
        txtcheque = new javax.swing.JTextField();
        txtpagamento = new com.toedter.calendar.JDateChooser();
        btnsalvar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Pagar em Lote");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Pagamento"));

        jLabel10.setText("Data de Pagamento");

        lblbanco.setText("Banco");

        cbbanco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));

        jLabel12.setText("Método");

        cbmetodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Boleto", "Depósito", "Transferência", "Cartão de Crédito", "Cheque", "Dinheiro", "Débito Automático" }));
        cbmetodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbmetodoActionPerformed(evt);
            }
        });

        lblcheque.setText("Número do Cheque");

        txtpagamento.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbmetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblbanco)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblcheque)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcheque)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(cbmetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtpagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblbanco)
                        .addComponent(cbbanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblcheque))
                    .addComponent(txtcheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnsalvar.setText("Salvar");
        btnsalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnsalvar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnsalvar)
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

    private void btnsalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalvarActionPerformed
        DefaultTableModel model = (DefaultTableModel) tablecap.getModel();
        for (int j = 0; j < tablecap.getRowCount(); j++) {
            String v = tablecap.getValueAt(j, 0).toString();
            if (v.equals("true")) {
                CAPBean capb = new CAPBean();
                CAPDAO capd = new CAPDAO();

                capb.setDatapagamento(Dates.CriarDataCurtaDBJDateChooser(txtpagamento.getDate()));
                capb.setBanco(cbbanco.getSelectedItem().toString());
                capb.setMetodo(cbmetodo.getSelectedItem().toString());
                capb.setStatus("Pago");
                capb.setId(Integer.parseInt(tablecap.getValueAt(j, 1).toString()));
                //datapagamento = ?, banco = ?, metodo = ?, status = ? WHERE id = ?
                capd.updatepagamento(capb);
            }
        }

        ContasPagar.cbstatus.setSelectedIndex(0);
        readtablecap();
        JOptionPane.showMessageDialog(null, "Pago com sucesso!");
        dispose();
    }//GEN-LAST:event_btnsalvarActionPerformed

    private void cbmetodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbmetodoActionPerformed
        campos();
    }//GEN-LAST:event_cbmetodoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnsalvar;
    public static javax.swing.JComboBox<String> cbbanco;
    public static javax.swing.JComboBox<String> cbmetodo;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel4;
    public static javax.swing.JLabel lblbanco;
    public static javax.swing.JLabel lblcheque;
    public static javax.swing.JTextField txtcheque;
    public com.toedter.calendar.JDateChooser txtpagamento;
    // End of variables declaration//GEN-END:variables
}
