/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.servicos;

import Bean.OSBean;
import Bean.ServicoMateriaisBean;
import Bean.ServicoMateriaisMovimentacaoBean;
import Connection.Session;
import DAO.OSDAO;
import DAO.ServicoMateriaisDAO;
import DAO.ServicoMateriaisMovimentacaoDAO;
import static View.servicos.OS.txtcodigo;
import static View.servicos.OS.txtfinal;
import static View.servicos.OS.txtnumeroos;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class MudarStatus extends javax.swing.JInternalFrame {

    /**
     * Creates new form MudarStatusEmLote
     */
    public MudarStatus() {
        initComponents();
    }

    public static void fillcb() {
        if (OS.txtstatus.getText().equals("Rascunho")) {
            cbstatus.addItem("Ativo");
            cbstatus.addItem("Cancelado");
            cbstatus.addItem("Fechado");
        } else {
            cbstatus.addItem("Cancelado");
            cbstatus.addItem("Fechado");
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
        jLabel1 = new javax.swing.JLabel();
        cbstatus = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Mudar Status Em Lote");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Mudar Status Para:");

        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cbstatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbstatusMouseEntered(evt);
            }
        });

        jButton1.setText("Alterar Status");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbstatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 69, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
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
        if (cbstatus.getSelectedItem().equals("Selecione")) {
            JOptionPane.showMessageDialog(rootPane, "Selecione um status primeiro!");
        } else if (cbstatus.getSelectedItem().equals("Fechado")) {
            //DAO e Bean para alterar OS
            OSDAO od = new OSDAO();
            OSBean ob = new OSBean();

            //DAO e Bean para criar movimentação
            ServicoMateriaisMovimentacaoDAO smmd = new ServicoMateriaisMovimentacaoDAO();
            ServicoMateriaisMovimentacaoBean smmb = new ServicoMateriaisMovimentacaoBean();

            //DAO para id do material
            ServicoMateriaisDAO smd = new ServicoMateriaisDAO();
            ServicoMateriaisBean smb = new ServicoMateriaisBean();

            //Alterar status
            ob.setStatus(cbstatus.getSelectedItem().toString());
            OS.txtstatus.setText(cbstatus.getSelectedItem().toString());
            ob.setIdtela(OS.txtnumeroos.getText());

            od.updatestatus(ob);

            //Pegar id do material
            int idmaterial = 0;
            for (ServicoMateriaisBean smb2 : smd.readid(txtcodigo.getText())) {
                idmaterial = smb2.getId();
            }

            //Pegar saldo atual do produto
            int saldoatual = 0;
            for (ServicoMateriaisBean smb2 : smd.readestoque(idmaterial)) {
                saldoatual = smb2.getEstoque();
            }

            //Pegar data para gravar
            Calendar c = Calendar.getInstance();
            String pattern = "dd/MM/yyyy HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            //Criar movimentação do material
            smmb.setIdmaterial(idmaterial);
            smmb.setInicial(saldoatual);
            smmb.setMovimentada(Integer.parseInt(txtfinal.getText()));
            smmb.setTipo("OS " + txtnumeroos.getText());
            smmb.setSaldo(saldoatual + Integer.parseInt(txtfinal.getText()));
            smmb.setData(simpleDateFormat.format(c.getTime()));
            smmb.setUsuario(Session.nome);

            //idmaterial, inicial, movimentada, tipo, saldo, data, usuario
            smmd.create(smmb);

            //Alterar estoque do material
            smb.setEstoque(saldoatual + Integer.parseInt(txtfinal.getText()));
            smb.setId(idmaterial);

            //estoque = ? WHERE id = ?
            smd.updateestoque(smb);

            //Atualizar OS e travar campos de acordo com o status
            OS.reados();
            OS.travarcampos();

            //Fechar tela
            dispose();
        } else {
            //DAO e Bean para alterar OS
            OSDAO od = new OSDAO();
            OSBean ob = new OSBean();

            //Alterar status
            ob.setStatus(cbstatus.getSelectedItem().toString());
            OS.txtstatus.setText(cbstatus.getSelectedItem().toString());
            ob.setIdtela(OS.txtnumeroos.getText());

            od.updatestatus(ob);

            //Atualizar OS e travar campos de acordo com o status
            OS.reados();
            OS.travarcampos();

            //Fechar tela
            dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbstatusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbstatusMouseEntered
        if (cbstatus.getItemCount() == 1) {
            fillcb();
        }
    }//GEN-LAST:event_cbstatusMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> cbstatus;
    public javax.swing.JButton jButton1;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
