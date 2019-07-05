/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.compras;

import Bean.ComprasSolicitacaoBean;
import DAO.ComprasSolicitacaoDAO;
import View.TelaPrincipal;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class ComprasSolicitacao extends javax.swing.JInternalFrame {

    /**
     * Creates new form ComprasSolicitacao
     */
    public ComprasSolicitacao() {
        initComponents();
        readsolicitacoes();
    }
    
    public static void readsolicitacoes() {
        //DAO para método
        ComprasSolicitacaoDAO csd = new ComprasSolicitacaoDAO();
        
        //Zerar linhas na Tablesolicitacao
        DefaultTableModel model = (DefaultTableModel) tablesolicitacao.getModel();
        model.setNumRows(0);
        
        //Ler dados
        for (ComprasSolicitacaoBean csb: csd.read()) {
            model.addRow(new Object[]{
                csb.getId(),
                false,
                csb.getIdtela(),
                csb.getTipo(),
                csb.getSolicitante(),
                csb.getStatus()
            });
        }
    }
    
    public static void travacampos() {
        if (txtstatus.getText().equals("Aprovado")) {
            cbtipo.setEnabled(false);
            txtnotes.setEditable(false);
        }
        if (txtstatus.getText().equals("Cancelado")) {
            cbtipo.setEnabled(false);
            txtnotes.setEditable(false);
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

        tabsolicitacao = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtpesquisa = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        cbstatus = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablesolicitacao = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtnumerosolicitacao = new javax.swing.JTextField();
        txtstatus = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtsolicitante = new javax.swing.JTextField();
        cbtipo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        tabsolicitacaointerna = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtnotes = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablesolicitacaoitens = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txtdata = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Solicitação de Compra");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));

        txtpesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtpesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtpesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));

        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Aberto", "Aprovado", "Cancelado", "Todos" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablesolicitacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Solicitação", "Tipo de Solicitação", "Solicitante", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablesolicitacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablesolicitacaoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablesolicitacao);
        if (tablesolicitacao.getColumnModel().getColumnCount() > 0) {
            tablesolicitacao.getColumnModel().getColumn(0).setMinWidth(0);
            tablesolicitacao.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablesolicitacao.getColumnModel().getColumn(0).setMaxWidth(0);
            tablesolicitacao.getColumnModel().getColumn(1).setMinWidth(40);
            tablesolicitacao.getColumnModel().getColumn(1).setPreferredWidth(40);
            tablesolicitacao.getColumnModel().getColumn(1).setMaxWidth(40);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabsolicitacao.addTab("Lista de Solicitações", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Nº");

        txtnumerosolicitacao.setEditable(false);
        txtnumerosolicitacao.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtstatus.setEditable(false);
        txtstatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setText("Status");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jLabel3.setText("Solicitante");

        txtsolicitante.setEditable(false);

        cbtipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Matéria-prima", "Rebolo", "Material de Escritório", "Material de Limpeza", "Manutenção" }));

        jLabel4.setText("Tipo de Material a Comprar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtsolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtsolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Novo");

        txtnotes.setColumns(20);
        txtnotes.setRows(5);
        jScrollPane2.setViewportView(txtnotes);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 999, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabsolicitacaointerna.addTab("Observações", jPanel6);

        tablesolicitacaoitens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Item", "Quantidade", "Observação", "Pedido de Compra"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablesolicitacaoitens);
        if (tablesolicitacaoitens.getColumnModel().getColumnCount() > 0) {
            tablesolicitacaoitens.getColumnModel().getColumn(0).setMinWidth(0);
            tablesolicitacaoitens.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablesolicitacaoitens.getColumnModel().getColumn(0).setMaxWidth(0);
            tablesolicitacaoitens.getColumnModel().getColumn(1).setMinWidth(40);
            tablesolicitacaoitens.getColumnModel().getColumn(1).setPreferredWidth(40);
            tablesolicitacaoitens.getColumnModel().getColumn(1).setMaxWidth(40);
            tablesolicitacaoitens.getColumnModel().getColumn(2).setMinWidth(250);
            tablesolicitacaoitens.getColumnModel().getColumn(2).setPreferredWidth(250);
            tablesolicitacaoitens.getColumnModel().getColumn(2).setMaxWidth(250);
            tablesolicitacaoitens.getColumnModel().getColumn(3).setMinWidth(70);
            tablesolicitacaoitens.getColumnModel().getColumn(3).setPreferredWidth(70);
            tablesolicitacaoitens.getColumnModel().getColumn(3).setMaxWidth(70);
            tablesolicitacaoitens.getColumnModel().getColumn(5).setMinWidth(110);
            tablesolicitacaoitens.getColumnModel().getColumn(5).setPreferredWidth(110);
            tablesolicitacaoitens.getColumnModel().getColumn(5).setMaxWidth(110);
        }

        jButton3.setText("Incluir");

        jButton4.setText("Excluir");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 999, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        tabsolicitacaointerna.addTab("Itens", jPanel7);

        txtdata.setEditable(false);
        txtdata.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setText("Data de Criação");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabsolicitacaointerna)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnumerosolicitacao, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdata, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtnumerosolicitacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtdata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabsolicitacaointerna)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        tabsolicitacao.addTab("Solicitação", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsolicitacao)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsolicitacao)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablesolicitacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablesolicitacaoMouseClicked
        if (evt.getClickCount() == 2) {
            //Mudar tabsolicitacao
            tabsolicitacao.setSelectedIndex(1);
            
            //Colocar a tabnotes na frente caso não estivesse
            tabsolicitacaointerna.setSelectedIndex(0);
            
            //Colocar o número da solicitação no txt
            txtnumerosolicitacao.setText(tablesolicitacao.getValueAt(tablesolicitacao.getSelectedRow(), 2).toString());
            
            //DAO para leitura dos dados
            ComprasSolicitacaoDAO csd = new ComprasSolicitacaoDAO();
            
            //Ler dados e colocar nos campos
            for (ComprasSolicitacaoBean csb: csd.click(txtnumerosolicitacao.getText())) {
                txtdata.setText(csb.getData());
                txtstatus.setText(csb.getStatus());
                txtsolicitante.setText(csb.getSolicitante());
                cbtipo.setSelectedItem(csb.getTipo());
                txtnotes.setText(csb.getNotes());
            }
            
            //Ver status e travar campos se necessário
            travacampos();
        }
    }//GEN-LAST:event_tablesolicitacaoMouseClicked

    private void txtpesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpesquisaKeyReleased
        //DAO para método
        ComprasSolicitacaoDAO csd = new ComprasSolicitacaoDAO();
        
        //Zerar linhas na tablesolicitacao
        DefaultTableModel model = (DefaultTableModel) tablesolicitacao.getModel();
        model.setNumRows(0);
        
        //Colocar itens com os dados digitados na pesquisa
        for (ComprasSolicitacaoBean csb: csd.pesquisa(txtpesquisa.getText())) {
            model.addRow(new Object[]{
                csb.getId(),
                false,
                csb.getIdtela(),
                csb.getTipo(),
                csb.getSolicitante(),
                csb.getStatus()
            });
        }
    }//GEN-LAST:event_txtpesquisaKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtnumerosolicitacao.getText().equals("")) { //Se for uma solicitação nova
            //DAO e Bean para salvar nova solicitação
            ComprasSolicitacaoDAO csd = new ComprasSolicitacaoDAO();
            ComprasSolicitacaoBean csb = new ComprasSolicitacaoBean();
            
            //Dados para criar nova solicitação
            csb.setIdtela(title);
            csb.setData(title);
            csb.setSolicitante(TelaPrincipal.lbllogin.getText());
            csb.setTipo(String.valueOf(cbtipo.getSelectedItem()));
            csb.setNotes(txtnotes.getText());
            csb.setStatus("Ativo");
            
            //Criar nova solicitação
            //idtela, data, solicitante, tipo, notes, status
            csd.create(csb);
            
            //Ler solicitação criada
        } else { //Se a solicitação já existir
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> cbstatus;
    public static javax.swing.JComboBox<String> cbtipo;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTable tablesolicitacao;
    public static javax.swing.JTable tablesolicitacaoitens;
    public javax.swing.JTabbedPane tabsolicitacao;
    public javax.swing.JTabbedPane tabsolicitacaointerna;
    public static javax.swing.JTextField txtdata;
    public static javax.swing.JTextArea txtnotes;
    public static javax.swing.JTextField txtnumerosolicitacao;
    public static javax.swing.JTextField txtpesquisa;
    public static javax.swing.JTextField txtsolicitante;
    public static javax.swing.JTextField txtstatus;
    // End of variables declaration//GEN-END:variables
}
