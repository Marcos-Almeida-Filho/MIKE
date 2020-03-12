/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.comercial;

import Bean.F_UPBean;
import Bean.F_UP_HistBean;
import Connection.Session;
import DAO.F_UPDAO;
import DAO.F_UP_HistDAO;
import Methods.Dates;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class AlterarProcesso extends javax.swing.JInternalFrame {

    /**
     * Creates new form AlterarProcesso
     */
    
    String processo = "";
    
    public AlterarProcesso(String proc) {
        initComponents();
        processo = proc;
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
        jLabel1 = new javax.swing.JLabel();
        cbprocesso = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Alterar Processo");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Processo"));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText("Próximo Processo");
        jLabel1.setName("jLabel1"); // NOI18N

        cbprocesso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Corte", "Para Canulação", "Em Canulação", "Para Retífica", "Em Retífica", "Ponta", "Desbaste", "Acabamento", "Canal", "Ticar", "CNC", "Para Revestimento", "Em Revestimento", "Terceiros", "Gravação", "Inspeção", "Etiquetagem", "Faturamento", "Encerrado" }));
        cbprocesso.setName("cbprocesso"); // NOI18N

        jButton1.setText("Alterar");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbprocesso, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbprocesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
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
        F_UPDAO fud = new F_UPDAO();
        F_UPBean fub = new F_UPBean();
        F_UP_HistDAO fuhd = new F_UP_HistDAO();
        F_UP_HistBean fuhb = new F_UP_HistBean();
        
        int id = Integer.parseInt(OPF_UP.txtid.getText());
        
        fub.setProcesso(cbprocesso.getSelectedItem().toString());
        fub.setId(id);
        
        //processo = ? WHERE id = ?
        fud.updateprocesso(fub);
        
        fuhb.setFuncionario(Session.nome);
        fuhb.setData(Dates.CriarDataCurtaDBSemDataExistente());
        
        //funcionario = ?, data = ? WHERE idfup = ? AND processo = ?
        fuhd.update(fuhb, id, processo);
        
        fuhb.setIdfup(id);
        fuhb.setProcesso(cbprocesso.getSelectedItem().toString());
        fuhb.setFuncionario(null);
        fuhb.setData(null);
        
        //idfup, processo, funcionario, data
        fuhd.create(fuhb);
        
        F_UP.readops();
        
        F_UP.tableHist();
        
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JOptionPane.showMessageDialog(null, processo);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> cbprocesso;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
