/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.qualidade;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class DocumentosInstrumentos extends javax.swing.JInternalFrame {

    /**
     * Creates new form Documentos
     */
    public DocumentosInstrumentos() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        chooser = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtdescarquivo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtdata = new javax.swing.JFormattedTextField();

        jTextField1.setText("jTextField1");

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Escolha de Documento");

        chooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserActionPerformed(evt);
            }
        });

        jLabel2.setText("Certificado Nº");

        jLabel1.setText("Data de Calibração");

        txtdata.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdescarquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdata, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtdescarquivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtdata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chooser, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooserActionPerformed
        if (txtdescarquivo.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Coloque um número de certificado.");
            txtdescarquivo.requestFocus();
        } else if (txtdata.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Coloque uma data de calibração.");
            txtdata.requestFocus();
        } else {
            int days = Integer.parseInt(InstrumentosMedicao.txtperiodicidade.getText());
            Calendar cal = Calendar.getInstance();
            String validade = "";
            try {
                String data = txtdata.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                cal.setTime(sdf.parse(data));
                cal.add(Calendar.DAY_OF_MONTH, days);
                validade = sdf.format(cal.getTime());
            } catch (ParseException e) {
            }

            File fileoriginal = chooser.getSelectedFile();
            if (evt.getActionCommand().equals("ApproveSelection")) {
                DefaultTableModel model = (DefaultTableModel) InstrumentosMedicao.tablecertificados.getModel();
                model.addRow(new Object[]{
                    "",
                    txtdescarquivo.getText(),
                    txtdata.getText(),
                    validade,
                    "",
                    fileoriginal.toString()
                });
                dispose();
                JOptionPane.showMessageDialog(null, "Incluído com sucesso!");
            }
            if (evt.getActionCommand().equals("CancelSelection")) {
                dispose();
            }
        }

    }//GEN-LAST:event_chooserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JFileChooser chooser;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JTextField jTextField1;
    public static javax.swing.JFormattedTextField txtdata;
    public javax.swing.JTextField txtdescarquivo;
    // End of variables declaration//GEN-END:variables
}
