/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.comercial;

import Bean.F_UPBean;
import Bean.F_UP_HistBean;
import DAO.F_UPDAO;
import DAO.F_UP_HistDAO;
import DAO.ProcessosServicoDAO;
import Methods.Dates;
import Methods.Numeros;
import Methods.Telas;
import Methods.Valores;
import View.Geral.ProcurarCliente;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class AdicionarOPFUP extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdicionarOPFUP
     */
    int idcriado;
    static ProcessosServicoDAO psd = new ProcessosServicoDAO();

    public AdicionarOPFUP() {
        initComponents();
        readProcessosServico();
    }
    
    public static void readProcessosServico() {
        psd.read().forEach(psb -> {
            cbprocesso.addItem(psb.getNome());
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtdav = new javax.swing.JTextField();
        txtop = new javax.swing.JTextField();
        txtmaterial = new javax.swing.JTextField();
        cbprocesso = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        dateentrega = new com.toedter.calendar.JDateChooser();
        cbnivel = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtvalor = new javax.swing.JTextField();
        txtObs = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtcliente = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Adicionar OP no Follow Up");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel1.setText("DAV");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Material");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("Data de Entrega");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("OP");
        jLabel4.setName("jLabel4"); // NOI18N

        txtdav.setName("txtdav"); // NOI18N

        txtop.setName("txtop"); // NOI18N

        txtmaterial.setName("txtmaterial"); // NOI18N

        cbprocesso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rascunho" }));
        cbprocesso.setName("cbprocesso"); // NOI18N

        jLabel5.setText("Primeiro Processo");
        jLabel5.setName("jLabel5"); // NOI18N

        dateentrega.setDateFormatString("dd/MM/yyyy");
        dateentrega.setName("dateentrega"); // NOI18N

        cbnivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "1 (Muito Urgente)", "2 (Urgente)", "3 (Normal)", "4 (Não Urgente)", "5 (Nada Urgente)" }));
        cbnivel.setName("cbnivel"); // NOI18N

        jLabel6.setText("Nível");
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText("Valor R$");
        jLabel7.setName("jLabel7"); // NOI18N

        txtvalor.setName("txtvalor"); // NOI18N
        txtvalor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtvalorFocusLost(evt);
            }
        });

        txtObs.setName("txtObs"); // NOI18N

        jLabel8.setText("Observação");
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setText("Cliente");
        jLabel9.setName("jLabel9"); // NOI18N

        txtcliente.setEnabled(false);
        txtcliente.setName("txtcliente"); // NOI18N

        jButton2.setText("Procurar");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtmaterial))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtdav, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtop, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbnivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtvalor))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dateentrega, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbprocesso, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtObs))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtdav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtmaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbprocesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel5))
                    .addComponent(dateentrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbnivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(txtvalor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtObs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)))
        );

        jButton1.setText("Salvar");
        jButton1.setName("jButton1"); // NOI18N
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtcliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Adicione um cliente.");
            ProcurarCliente pc = new ProcurarCliente(this.getClass().getSimpleName());
            Telas.AparecerTela(pc);
        } else if (txtdav.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um número de DAV.");
            txtdav.requestFocus();
        } else if (txtop.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um número de OP.");
            txtop.requestFocus();
        } else if (txtmaterial.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite o material da OP.");
            txtmaterial.requestFocus();
        } else if (dateentrega.getCalendar() == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma data.");
        } else if (cbnivel.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Selecione um nível.");
            cbnivel.showPopup();
        } else if (txtvalor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um valor primeiro.");
            txtvalor.requestFocus();
        } else {
            String datacriacao = Dates.CriarDataCompletaParaDB();

            F_UPDAO fd = new F_UPDAO();
            F_UPBean fb2 = new F_UPBean();

            fb2.setDav(txtdav.getText());
            fb2.setOp(txtop.getText());
            fb2.setDataentrega(Dates.CriarDataCurtaDBJDateChooser(dateentrega.getDate()));
            fb2.setMaterial(txtmaterial.getText());
            fb2.setProcesso(cbprocesso.getSelectedItem().toString());
            fb2.setDatacriacao(datacriacao);
            fb2.setNivel(cbnivel.getSelectedIndex());
            fb2.setValor(Numeros.TransformarNumeroEmDouble(txtvalor.getText()));
            fb2.setObservacao(txtObs.getText());
            fb2.setCliente(txtcliente.getText());

            //dav, op, dataentrega, material, processo, datacriacao, nivel, valor, observacao, cliente
            fd.create(fb2);

            fd.readcreated(datacriacao).forEach(fb -> {
                idcriado = fb.getId();
            });

            F_UP_HistDAO fhd = new F_UP_HistDAO();
            F_UP_HistBean fhb = new F_UP_HistBean();

            fhb.setIdfup(idcriado);
            fhb.setProcesso(cbprocesso.getSelectedItem().toString());

            //idfup, processo, funcionario, data
            fhd.create(fhb);

            F_UP.readops();

            dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ProcurarCliente pc = new ProcurarCliente(this.getClass().getSimpleName());
        Telas.AparecerTela(pc);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtvalorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtvalorFocusLost
        Valores.SetarTxtNumeroEmDinheiro(txtvalor);
    }//GEN-LAST:event_txtvalorFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> cbnivel;
    public static javax.swing.JComboBox<String> cbprocesso;
    public com.toedter.calendar.JDateChooser dateentrega;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JTextField txtObs;
    public static javax.swing.JTextField txtcliente;
    public javax.swing.JTextField txtdav;
    public javax.swing.JTextField txtmaterial;
    public javax.swing.JTextField txtop;
    public static javax.swing.JTextField txtvalor;
    // End of variables declaration//GEN-END:variables
}
