/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Geral;

import Bean.F_UPBean;
import Bean.F_UP_HistBean;
import DAO.F_UPDAO;
import DAO.F_UP_HistDAO;
import DAO.ProcessosServicoDAO;
import View.servicos.OS;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class EscolherProximoProcesso extends javax.swing.JInternalFrame {

    /**
     * Creates new form EscolherProximoProcesso
     *
     * @param origin
     */
    static String origem;

    public EscolherProximoProcesso(String origin) {
        initComponents();
        origem = origin;
        pegarProcessos();
    }
    
    public static void pegarProcessos() {
        switch (origem) {
            case "OS":
                ProcessosServicoDAO psd = new ProcessosServicoDAO();
                
                psd.read().forEach(psb -> {
                    cbprocesso.addItem(psb.getNome());
                });
                break;
            default:
                throw new AssertionError();
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
        cbprocesso = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setTitle("Escolher Próximo Processo");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        cbprocesso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cbprocesso.setName("cbprocesso"); // NOI18N

        jButton1.setText("Selecionar");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Escolha qual será o próximo Processo.");
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbprocesso, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbprocesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (cbprocesso.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Selecione um processo.");
            cbprocesso.showPopup();
        } else {
            switch (origem) {
                case "OS":
                    int ordem = OS.tableprocessos.getRowCount();
                    DefaultTableModel model = (DefaultTableModel) OS.tableprocessos.getModel();
                    model.addRow(new Object[]{
                        false,
                        "",
                        cbprocesso.getSelectedItem(),
                        "",
                        "",
                        0,
                        0,
                        "",
                        ordem,
                        0
                    });
                    OS.salvarOS();
                    
                    F_UPDAO fud = new F_UPDAO();
                    F_UPBean fub = new F_UPBean();
                    
                    fub.setProcesso(cbprocesso.getSelectedItem().toString());
                    fub.setOp(OS.txtnumeroos.getText());
                    
                    //SET processo = ? WHERE op = ?
                    fud.updateProcessoByOs(fub);
                    
                    F_UP_HistDAO fuhd = new F_UP_HistDAO();
                    F_UP_HistBean fuhb = new F_UP_HistBean();
                    
                    fuhb.setIdfup(fud.getId(OS.txtnumeroos.getText()));
                    fuhb.setProcesso(OS.tableprocessos.getValueAt(OS.tableprocessos.getRowCount() - 1, 2).toString());
                    
                    //idfup, processo, funcionario, data
                    fuhd.create(fuhb);
                    break;
            }
            this.dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> cbprocesso;
    public javax.swing.JButton jButton1;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
