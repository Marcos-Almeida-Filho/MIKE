/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.servicos;

import Bean.OSInspecaoBean;
import Bean.OSProcessosBean;
import DAO.OSInspecaoDAO;
import DAO.OSProcessosDAO;
import Methods.SoNumeros;
import View.TelaPrincipal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class ProcessoOS extends javax.swing.JInternalFrame {

    /**
     * Creates new form ProcessoOS
     */
    public ProcessoOS() {
        initComponents();
        camposnumeros();
        txtinicial.setVisible(false);
    }

    public static void readprocesso() {
        OSProcessosDAO opd = new OSProcessosDAO();

        for (OSProcessosBean opb : opd.readprocesso(Integer.parseInt(txtid.getText()))) {
            txtprocesso.setText(opb.getProcesso());
            txtinicio.setText(opb.getInicio());
            txttermino.setText(opb.getTermino());
            txtok.setText(String.valueOf(opb.getQtdok()));
            txtnaook.setText(String.valueOf(opb.getQtdnaook()));
            txtusuario.setText(opb.getUsuario());
            txtobservacao.setText(opb.getObservacao());
            txtmotivo.setText(opb.getMotivo());
        }
    }
    
    public static void readinspecao() {
        DefaultTableModel model = (DefaultTableModel) tableinspecao.getModel();
        model.setNumRows(0);
        
        OSInspecaoDAO oid = new OSInspecaoDAO();
        
        for(OSInspecaoBean oib : oid.read(txtid.getText())) {
            model.addRow(new Object[]{
                oib.getMedida(),
                oib.getMedidamaior(),
                oib.getMedidamenor(),
                oib.getInstrumento()
            });
        }
    }

    public static void camposnumeros() {
        txtok.setDocument(new SoNumeros());
        txtnaook.setDocument(new SoNumeros());
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        txtprocesso = new javax.swing.JTextField();
        txtinicio = new javax.swing.JTextField();
        txttermino = new javax.swing.JTextField();
        txtok = new javax.swing.JTextField();
        txtnaook = new javax.swing.JTextField();
        txtusuario = new javax.swing.JTextField();
        txtinicial = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableinspecao = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtobservacao = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtmotivo = new javax.swing.JTextArea();

        setClosable(true);
        setTitle("Processo");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Processo"));

        jLabel1.setText("ID");

        jLabel2.setText("Processo");

        jLabel3.setText("Início");

        jLabel4.setText("Término");

        jLabel5.setText("Quantidade Conforme");

        jLabel6.setText("Quantidade Não Conforme");

        jLabel7.setText("Usuário");

        txtid.setEditable(false);
        txtid.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtprocesso.setEditable(false);
        txtprocesso.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtinicio.setEditable(false);
        txtinicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txttermino.setEditable(false);
        txttermino.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtok.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtok.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtokFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtokFocusLost(evt);
            }
        });

        txtnaook.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtnaook.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtnaookFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtnaookFocusLost(evt);
            }
        });

        txtusuario.setEditable(false);
        txtusuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtinicial.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprocesso, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttermino, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtok, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnaook, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtinicial, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtinicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtprocesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtinicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txttermino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(txtok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnaook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Inspeções"));

        tableinspecao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Medida Inspecionada", "Maior Valor do Lote", "Menor Valor do Lote", "Instrumento de Medição"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableinspecao);
        if (tableinspecao.getColumnModel().getColumnCount() > 0) {
            tableinspecao.getColumnModel().getColumn(1).setMinWidth(115);
            tableinspecao.getColumnModel().getColumn(1).setPreferredWidth(115);
            tableinspecao.getColumnModel().getColumn(1).setMaxWidth(115);
            tableinspecao.getColumnModel().getColumn(2).setMinWidth(120);
            tableinspecao.getColumnModel().getColumn(2).setPreferredWidth(120);
            tableinspecao.getColumnModel().getColumn(2).setMaxWidth(120);
        }

        jButton2.setText("Adicionar Medição");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Observações"));

        txtobservacao.setColumns(20);
        txtobservacao.setLineWrap(true);
        txtobservacao.setRows(5);
        txtobservacao.setWrapStyleWord(true);
        txtobservacao.setAutoscrolls(false);
        jScrollPane3.setViewportView(txtobservacao);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Motivo das Peças Não Conformes"));

        txtmotivo.setEditable(false);
        txtmotivo.setColumns(20);
        txtmotivo.setLineWrap(true);
        txtmotivo.setRows(5);
        txtmotivo.setWrapStyleWord(true);
        txtmotivo.setAutoscrolls(false);
        jScrollPane2.setViewportView(txtmotivo);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        if (tableinspecao.getRowCount() == 0) {
            int resp = JOptionPane.showConfirmDialog(rootPane, "Não existem medições! Está correto?", "Sem medições", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                OSProcessosDAO opd = new OSProcessosDAO();
                OSProcessosBean opb = new OSProcessosBean();

                Calendar c = Calendar.getInstance();
                String pattern = "dd/MM/yyyy HH:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                opb.setTermino(simpleDateFormat.format(c.getTime()));
                opb.setQtdok(Integer.parseInt(txtok.getText()));
                opb.setQtdnaook(Integer.parseInt(txtnaook.getText()));
                opb.setObservacao(txtobservacao.getText());
                opb.setMotivo(txtmotivo.getText());
                opb.setId(Integer.parseInt(txtid.getText()));

                //termino = ?, qtdok = ?, qtdnaook = ?, observacao = ?, motivo = ? WHERE id = ?
                opd.update(opb);
            }
        } else {
            OSProcessosDAO opd = new OSProcessosDAO();
            OSProcessosBean opb = new OSProcessosBean();

            Calendar c = Calendar.getInstance();
            String pattern = "dd/MM/yyyy HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            opb.setTermino(simpleDateFormat.format(c.getTime()));
            opb.setQtdok(Integer.parseInt(txtok.getText()));
            opb.setQtdnaook(Integer.parseInt(txtnaook.getText()));
            opb.setObservacao(txtobservacao.getText());
            opb.setMotivo(txtmotivo.getText());
            opb.setId(Integer.parseInt(txtid.getText()));

            //termino = ?, qtdok = ?, qtdnaook = ?, observacao = ?, motivo = ? WHERE id = ?
            opd.update(opb);

            OSInspecaoDAO oid = new OSInspecaoDAO();
            OSInspecaoBean oib = new OSInspecaoBean();

            for (int i = 0; i < tableinspecao.getRowCount(); i++) {
                oib.setIdos(OS.txtnumeroos.getText());
                oib.setIdprocesso(txtid.getText());
                oib.setMedida(tableinspecao.getValueAt(i, 0).toString());
                oib.setMedidamaior(tableinspecao.getValueAt(i, 1).toString());
                oib.setMedidamenor(tableinspecao.getValueAt(i, 2).toString());
                oib.setFuncionario(TelaPrincipal.lblapelido.getText());
                oib.setInstrumento(tableinspecao.getValueAt(i, 3).toString());

                //idos, idprocesso, medida, medidamaior, medidamenor, funcionario, instrumento
                oid.create(oib);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtnaookFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnaookFocusLost
        int ok = Integer.parseInt(txtok.getText());
        int naook = Integer.parseInt(txtnaook.getText());
        int inicial = Integer.parseInt(txtinicial.getText());
        if (ok + naook > inicial) {
            JOptionPane.showMessageDialog(rootPane, "Quantidade aprovada + Quantidade reprovada maior que a quantidade da OS.\nPor favor insira uma quantidade aceitável.");
            txtok.requestFocus();
            txtok.selectAll();
        } else if (naook > 0) {
            if (txtmotivo.getText().equals("")) {
                String motivo = JOptionPane.showInputDialog(rootPane, "Qual o motivo das peças não estarem aprovadas?", "Peças reprovadas", JOptionPane.YES_OPTION);
                txtmotivo.setText(motivo);
            } else {
                int resp = JOptionPane.showConfirmDialog(rootPane, "Deseja mudar o motivo das peças estarem reprovadas?", "Alterar motivo", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    String motivo = JOptionPane.showInputDialog(rootPane, "Qual o motivo das peças não estarem aprovadas?", "Peças reprovadas", JOptionPane.YES_OPTION);
                    txtmotivo.setText(motivo);
                }
            }
        } else {
            txtmotivo.setText("");
        }
    }//GEN-LAST:event_txtnaookFocusLost

    private void txtokFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtokFocusLost
        int ok = Integer.parseInt(txtok.getText());
        int naook = Integer.parseInt(txtnaook.getText());
        int inicial = Integer.parseInt(txtinicial.getText());
        if (ok + naook > inicial) {
            JOptionPane.showMessageDialog(rootPane, "Quantidade aprovada + Quantidade reprovada maior que a quantidade da OS.\nPor favor insira uma quantidade aceitável.");
            txtok.requestFocus();
            txtok.selectAll();
        }
    }//GEN-LAST:event_txtokFocusLost

    private void txtokFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtokFocusGained
        txtok.selectAll();
    }//GEN-LAST:event_txtokFocusGained

    private void txtnaookFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnaookFocusGained
        txtnaook.selectAll();
    }//GEN-LAST:event_txtnaookFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTable tableinspecao;
    public static javax.swing.JTextField txtid;
    public static javax.swing.JTextField txtinicial;
    public static javax.swing.JTextField txtinicio;
    public static javax.swing.JTextArea txtmotivo;
    public static javax.swing.JTextField txtnaook;
    public static javax.swing.JTextArea txtobservacao;
    public static javax.swing.JTextField txtok;
    public static javax.swing.JTextField txtprocesso;
    public static javax.swing.JTextField txttermino;
    public static javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}
