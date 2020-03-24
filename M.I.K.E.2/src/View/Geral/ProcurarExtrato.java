/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Geral;

import Methods.ExcelMethods;
import View.financeiro.Extratos;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Marcos Filho
 */
public class ProcurarExtrato extends javax.swing.JInternalFrame {

    /**
     * Creates new form Documentos
     */
    String origem;

    public ProcurarExtrato(String origin) {
        initComponents();
        origem = origin;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xls", "xlsx");
        chooser.setFileFilter(filter);
        File home = FileSystemView.getFileSystemView().getHomeDirectory();
        chooser.setCurrentDirectory(home.getAbsoluteFile());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chooser = new javax.swing.JFileChooser();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Escolha de Documento");

        chooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chooser, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooserActionPerformed
        if (evt.getActionCommand().equals("ApproveSelection")) {
            File fileoriginal = chooser.getSelectedFile();
            String filestring = fileoriginal.toString();
            switch (origem) {
                case "Extratos":
                    ExcelMethods excel = new ExcelMethods();
                    switch (Extratos.txtbanco.getText()) {
                        case "Bradesco":
                            excel.readExtrato(filestring, Extratos.tableextratobradesco);
                            break;
                        case "Itaú":
                            excel.readExtrato(filestring, Extratos.tableextratoitau);
                            break;
                    }
                    dispose();
                    break;
                default:
                    throw new AssertionError();
            }
        }
        dispose();
    }//GEN-LAST:event_chooserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JFileChooser chooser;
    // End of variables declaration//GEN-END:variables
}
