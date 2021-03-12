/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Geral;

import DAO.VendasCotacaoItensDAO;
import Methods.SendEmail;
import Methods.SoNumeros;
import Methods.Telas;
import View.servicos.*;
import View.vendas.CotacaoVenda;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class ItemCotacao extends javax.swing.JInternalFrame {

    private final String origin;
    public int idItemCotacao = 0;
    public static int idMaterial;

    VendasCotacaoItensDAO vcid = new VendasCotacaoItensDAO();

    /**
     * Creates new form ItemOrcamentoServico
     *
     * @param origem
     */
    public ItemCotacao(String origem) {
        initComponents();
        origin = origem;
        txtprazo.setDocument(new SoNumeros());
    }

    public void readItemDB() {
        if (idItemCotacao != 0) {
            switch (origin) {
                case "CotacaoVenda":
                    vcid.readItem(idItemCotacao).forEach(vcib -> {
                        if (vcib.isCadastrado()) {
                            radioCadastrado.setSelected(true);
                        } else {
                            radioNaoCadastrado.setSelected(true);
                        }
                        txtcodigo.setText(vcib.getCodigo());
                        txtdesc.setText(vcib.getDescricao());
                        txtqtd.setText(String.valueOf(vcib.getQtd()));
                        txtvalor.setText(String.valueOf(vcib.getValorunitario()));
                        txtprazo.setText(vcib.getPrazo().replace(" dias", "").replace(" úteis", ""));
                    });
                    break;
                default:
                    throw new AssertionError();
            }
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

        jLabel1 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        radioCadastrado = new javax.swing.JRadioButton();
        radioNaoCadastrado = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        txtdesc = new javax.swing.JTextField();
        btnprocurar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtqtd = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtvalor = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        txtprazo = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtcodigo = new javax.swing.JTextField();

        jLabel1.setText("jLabel1");

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Item Cotação");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Produto"));

        buttonGroup1.add(radioCadastrado);
        radioCadastrado.setSelected(true);
        radioCadastrado.setText("Produto Cadastrado");
        radioCadastrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioCadastradoActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioNaoCadastrado);
        radioNaoCadastrado.setText("Produto Não Cadastrado");
        radioNaoCadastrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioNaoCadastradoActionPerformed(evt);
            }
        });

        jLabel2.setText("Descrição");

        txtdesc.setEditable(false);

        btnprocurar.setText("Procurar");
        btnprocurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprocurarActionPerformed(evt);
            }
        });

        jLabel3.setText("Quantidade");

        jLabel4.setText("Valor Unitário R$");

        txtvalor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));

        jLabel6.setText("Prazo de Entrega");

        txtprazo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprazoKeyReleased(evt);
            }
        });

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Código");

        txtcodigo.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtdesc, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(radioCadastrado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radioNaoCadastrado)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnprocurar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtqtd, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtvalor, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprazo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioCadastrado)
                    .addComponent(radioNaoCadastrado))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnprocurar)
                    .addComponent(jLabel8)
                    .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtqtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtvalor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtprazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void radioCadastradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioCadastradoActionPerformed
        txtcodigo.setEditable(false);
        txtcodigo.setText("");
        txtdesc.setEditable(false);
        txtdesc.setText("");
        btnprocurar.setEnabled(true);
    }//GEN-LAST:event_radioCadastradoActionPerformed

    private void radioNaoCadastradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNaoCadastradoActionPerformed
        txtcodigo.setEditable(true);
        txtdesc.setEditable(true);
        btnprocurar.setEnabled(false);
        txtcodigo.requestFocus();
        idMaterial = 0;
    }//GEN-LAST:event_radioNaoCadastradoActionPerformed

    private void btnprocurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprocurarActionPerformed
        ProcurarMaterial pm = new ProcurarMaterial(origin);
        Telas.AparecerTela(pm);
    }//GEN-LAST:event_btnprocurarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (idItemCotacao == 0) {
            if (txtdesc.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Escolha um item primeiro!");
                txtdesc.requestFocus();
            } else if (txtqtd.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Coloque uma quantidade primeiro!");
                txtqtd.requestFocus();
            } else if (txtvalor.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Coloque um valor unitário primeiro!");
                txtvalor.requestFocus();
            } else {
                DecimalFormat formatter = new DecimalFormat("#,###.00");
                DecimalFormat formatterq = new DecimalFormat("#,###");
                int qtd = Integer.parseInt(txtqtd.getText());
                String v = txtvalor.getText().replace(".", "");
                float valor = Float.parseFloat(v.replace(",", "."));
                float tot = qtd * valor;
                String totf = formatter.format(tot);
                String qtdf = formatterq.format(qtd);

                switch (origin) {
                    case "CotacaoServico":
                        DefaultTableModel modelServico = (DefaultTableModel) CotacaoServico.tableitens.getModel();
                        modelServico.addRow(new Object[]{
                            false,
                            "",
                            txtcodigo.getText(),
                            txtdesc.getText(),
                            qtdf,
                            txtvalor.getText(),
                            totf,
                            txtprazo.getText() + " dias úteis",
                            ""
                        });
                        CotacaoServico.txtvalor();
                        break;
                    case "CotacaoVenda":
                        DefaultTableModel modelVenda = (DefaultTableModel) CotacaoVenda.tableItens.getModel();
                        modelVenda.addRow(new Object[]{
                            "",
                            radioCadastrado.isSelected(),
                            false,
                            txtcodigo.getText(),
                            txtdesc.getText(),
                            txtqtd.getText(),
                            txtvalor.getText(),
                            totf,
                            txtprazo.getText() + " dias úteis",
                            "",
                            idMaterial
                        });
                        break;
                    default:
                        throw new AssertionError();
                }
                dispose();
            }
        } else {
            double qtd = Double.parseDouble(txtqtd.getText().replace(",", "."));
            String v = txtvalor.getText().replace(".", "");
            double valor = Double.parseDouble(v.replace(",", "."));
            double tot = qtd * valor;
            String nprazo = txtprazo.getText() + " dias úteis";

            switch (origin) {
                case "CotacaoServico":

                    break;
                case "CotacaoVenda":
                    try {
                        vcid.update(idMaterial, txtcodigo.getText(), txtdesc.getText(), qtd, valor, tot, nprazo, radioCadastrado.isSelected(), idItemCotacao);
                        JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
                        CotacaoVenda.lerItensCotacao(CotacaoVenda.txtCotacao.getText());
                    } catch (SQLException e) {
                        String msg = "Erro ao atualizar item da Cotação de Vendas.";
                        JOptionPane.showMessageDialog(null, msg);

                        new Thread() {
                            @Override
                            public void run() {
                                SendEmail.EnviarErro2(msg, e);
                            }
                        }.start();
                    }
                    break;
            }

            dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtprazoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprazoKeyReleased
        SoNumeros.verificarNumeros(txtprazo);
    }//GEN-LAST:event_txtprazoKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnprocurar;
    public javax.swing.ButtonGroup buttonGroup1;
    public javax.swing.JButton jButton1;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public static javax.swing.JRadioButton radioCadastrado;
    public static javax.swing.JRadioButton radioNaoCadastrado;
    public static javax.swing.JTextField txtcodigo;
    public static javax.swing.JTextField txtdesc;
    public static javax.swing.JTextField txtprazo;
    public static javax.swing.JTextField txtqtd;
    public static javax.swing.JFormattedTextField txtvalor;
    // End of variables declaration//GEN-END:variables
}
