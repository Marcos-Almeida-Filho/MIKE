/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.financeiro;

import Bean.CAPBean;
import DAO.CAPDAO;
import DAO.UsuariosDAO;
import Methods.Dates;
import Methods.Valores;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class PagamentoRH extends javax.swing.JInternalFrame {

    /**
     * Creates new form PagamentoRH
     */
    
    double salario = 0.0;
    
    public PagamentoRH() {
        initComponents();
        readtable();
    }
    
    
    public static void readtable() {
        UsuariosDAO ud = new UsuariosDAO();
        
        DefaultTableModel model = (DefaultTableModel) tablepagamento.getModel();
        model.setNumRows(0);
        
        ud.readtabelausuariosativo().forEach(ub -> {
            model.addRow(new Object[]{
                ub.getNome(),
                ""
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
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablepagamento = new javax.swing.JTable();
        cbtipo = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txtoutros = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtnumero = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtparcela = new javax.swing.JTextField();
        datevencimento = new com.toedter.calendar.JDateChooser();

        setClosable(true);
        setTitle("Lançar Pagamentos do RH");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jButton1.setText("Lançar");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablepagamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablepagamento.setName("tablepagamento"); // NOI18N
        jScrollPane1.setViewportView(tablepagamento);
        if (tablepagamento.getColumnModel().getColumnCount() > 0) {
            tablepagamento.getColumnModel().getColumn(1).setMinWidth(130);
            tablepagamento.getColumnModel().getColumn(1).setPreferredWidth(130);
            tablepagamento.getColumnModel().getColumn(1).setMaxWidth(130);
        }

        cbtipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Salário", "Adiantamento", "Férias", "Outros" }));
        cbtipo.setName("cbtipo"); // NOI18N
        cbtipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbtipoActionPerformed(evt);
            }
        });

        jLabel1.setText("Tipo");
        jLabel1.setName("jLabel1"); // NOI18N

        txtoutros.setEnabled(false);
        txtoutros.setName("txtoutros"); // NOI18N

        jLabel2.setText("Número");
        jLabel2.setName("jLabel2"); // NOI18N

        txtnumero.setName("txtnumero"); // NOI18N

        jLabel3.setText("Parcela");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("Vencimento");
        jLabel4.setName("jLabel4"); // NOI18N

        txtparcela.setName("txtparcela"); // NOI18N

        datevencimento.setDateFormatString("dd/MM/yyyy");
        datevencimento.setName("datevencimento"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cbtipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtoutros, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtnumero, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3)
                                    .addComponent(txtparcela, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(datevencimento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel1))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtoutros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtparcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datevencimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
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

    private void cbtipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtipoActionPerformed
        switch (cbtipo.getSelectedItem().toString()) {
            case "Outros":
                txtoutros.setEnabled(true);
                for (int i = 0; i < tablepagamento.getRowCount(); i++) {
                    tablepagamento.setValueAt("", i, 1);
                }
                break;
            case "Adiantamento":
                txtoutros.setText("");
                txtoutros.setEnabled(false);
                for (int i = 0; i < tablepagamento.getRowCount(); i++) {
                    UsuariosDAO ud = new UsuariosDAO();
                    
                    String nome = tablepagamento.getValueAt(i, 0).toString();
                    
                    ud.readsalario(nome).forEach(ub -> {
                        salario = ub.getSalario() * 0.4;
                    });
                    
                    tablepagamento.setValueAt(Valores.TransformarDoubleDBemString(salario), i, 1);
                }
                break;
            default:
                txtoutros.setText("");
                txtoutros.setEnabled(false);
                for (int i = 0; i < tablepagamento.getRowCount(); i++) {
                    tablepagamento.setValueAt("", i, 1);
                }
                break;
        }
    }//GEN-LAST:event_cbtipoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int numVazio = 0;
        
        for (int i = 0; i < tablepagamento.getRowCount(); i++) {
            if (tablepagamento.getValueAt(i, 1).equals("")) {
                numVazio++;
            }
        }
        
        if (numVazio == tablepagamento.getRowCount()) {
            JOptionPane.showMessageDialog(null, "Não foi digitado valor algum.");
        } else if (cbtipo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Selecione um tipo de pagamento.");
            cbtipo.showPopup();
        } else if (cbtipo.getSelectedItem().equals("Outros") && txtoutros.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Descreva o tipo.");
            txtoutros.requestFocus();
        } else if (txtnumero.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Coloque um número de identificação.");
            txtnumero.requestFocus();
        } else if (txtparcela.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Coloque uma parcela.");
            txtparcela.requestFocus();
        } else if (datevencimento.getCalendar() == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma data de vencimento.");
        } else {
            CAPDAO cd = new CAPDAO();
            CAPBean cb = new CAPBean();
            
            Date dia = new Date();
            
            String tipo;
            
            if (cbtipo.getSelectedItem().equals("Outros")) {
                tipo = txtoutros.getText();
            } else {
                tipo = cbtipo.getSelectedItem().toString();
            }
            
            for (int i = 0; i < tablepagamento.getRowCount(); i++) {
                if (!tablepagamento.getValueAt(i, 1).equals("")) {
                    cb.setDatalancamento(Dates.CriarDataCurtaDBJDateChooser(dia));
                    cb.setFornecedor(tipo + " - " + tablepagamento.getValueAt(i, 0).toString());
                    cb.setNumero(txtnumero.getText());
                    cb.setDataemissao(Dates.CriarDataCurtaDBJDateChooser(dia));
                    cb.setTotal(tablepagamento.getValueAt(i, 1).toString());
                    cb.setParcela(txtparcela.getText());
                    cb.setValorparcela(tablepagamento.getValueAt(i, 1).toString());
                    cb.setDataparcela(Dates.CriarDataCurtaDBJDateChooser(datevencimento.getDate()));
                    cb.setStatus("Ativo");

                    //datalancamento, fornecedor, notafiscal, dataemissao, total, parcela, valorparcela, dataparcela, status
                    cd.create(cb);
                }
            }
            
            JOptionPane.showMessageDialog(null, "Lançado com sucesso!");
            dispose();
            
            ContasPagar.readtablecap();
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox<String> cbtipo;
    public com.toedter.calendar.JDateChooser datevencimento;
    public javax.swing.JButton jButton1;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tablepagamento;
    public javax.swing.JTextField txtnumero;
    public javax.swing.JTextField txtoutros;
    public javax.swing.JTextField txtparcela;
    // End of variables declaration//GEN-END:variables
}
