/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.comercial;

import Bean.PlanejamentoBean;
import DAO.F_UPDAO;
import DAO.PlanejamentoDAO;
import DAO.VendasPedidoDAO;
import DAO.VendasPedidoItensDAO;
import Methods.Dates;
import Methods.Valores;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class PlanejamentoFaturamento extends javax.swing.JInternalFrame {

    CellEditorListener ChangeNotification = new CellEditorListener() {
        @Override
        public void editingCanceled(ChangeEvent e) {
            System.out.println("The user canceled editing.");
        }

        @Override
        public void editingStopped(ChangeEvent e) {
            System.out.println("The user stopped editing successfully.");
        }
    };

    PlanejamentoDAO pd = new PlanejamentoDAO();
    PlanejamentoBean pb = new PlanejamentoBean();
    F_UPDAO fd = new F_UPDAO();
    VendasPedidoItensDAO vpid = new VendasPedidoItensDAO();
    VendasPedidoDAO vpd = new VendasPedidoDAO();

    /**
     * Creates new form PlanejamentoQuinzenal
     */
    public PlanejamentoFaturamento() {
        initComponents();
        tableFill();
        txtAtrasado();
        tableFillFaturamento();
        tableFaturamento.getDefaultEditor(String.class).addCellEditorListener(ChangeNotification);
    }

    public void tableFillFaturamento() {
        DefaultTableModel model = (DefaultTableModel) tableFaturamento.getModel();

        pd.read().forEach(pb -> {
            model.setValueAt("R$ " + Valores.TransformarValorFloatEmDinheiro(String.valueOf(pb.getPlanejamento())), 0, 1);
            model.setValueAt("R$ " + Valores.TransformarValorFloatEmDinheiro(String.valueOf(pb.getPlanejamento() / 3)), 1, 1);
            model.setValueAt("R$ " + Valores.TransformarValorFloatEmDinheiro(String.valueOf(pb.getPlanejamento() / 3)), 2, 1);
            model.setValueAt("R$ " + Valores.TransformarValorFloatEmDinheiro(String.valueOf(pb.getPlanejamento() / 3)), 3, 1);
            model.setValueAt("R$ " + Valores.TransformarValorFloatEmDinheiro(String.valueOf(pb.getAtingidomensal())), 0, 2);
            model.setValueAt("R$ " + Valores.TransformarValorFloatEmDinheiro(String.valueOf(pb.getAtingido1())), 1, 2);
            model.setValueAt("R$ " + Valores.TransformarValorFloatEmDinheiro(String.valueOf(pb.getAtingido2())), 2, 2);
            model.setValueAt("R$ " + Valores.TransformarValorFloatEmDinheiro(String.valueOf(pb.getAtingido3())), 3, 2);
        });
    }

    public void tableFill() {
        DefaultTableModel model = (DefaultTableModel) tablePlanejamento.getModel();
        model.setNumRows(0);

        vpid.readItensSemNF().forEach(vpib -> {
            model.addRow(new Object[]{
                vpib.getId(),
                vpib.getDav(),
                "",
                vpib.getCodigo(),
                vpib.getOp(),
                "",
                Dates.TransformarDataCurtaDoDB(vpib.getPrazo()),
                Valores.TransformarValorFloatEmDinheiro(String.valueOf(vpib.getValorunitario())),
                Valores.TransformarValorFloatEmDinheiro(String.valueOf(vpib.getValortotal()))
            });
        });

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < tablePlanejamento.getRowCount(); i++) {
                    String pedido = tablePlanejamento.getValueAt(i, 1).toString();
                    String cliente = vpd.readCliente(pedido);
                    tablePlanejamento.setValueAt(cliente, i, 2);
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < tablePlanejamento.getRowCount(); i++) {
                    if (!tablePlanejamento.getValueAt(i, 4).toString().equals("")) {
                        String op = tablePlanejamento.getValueAt(i, 4).toString();
                        String processo = fd.getProcesso(op);
                        tablePlanejamento.setValueAt(processo, i, 5);
                    }
                }
            }
        }.start();
    }

    public void txtAtrasado() {
        double valorAtrasado = 0, valorParcial;

        for (int i = 0; i < tablePlanejamento.getRowCount(); i++) {
            String dataNormal = tablePlanejamento.getValueAt(i, 6).toString();
            Date dataDate = new Date(), dataHoje = new Date();
            try {
                dataDate = new SimpleDateFormat("dd/MM/yyyy").parse(dataNormal);
            } catch (ParseException ex) {
                Logger.getLogger(PlanejamentoFaturamento.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (dataDate.compareTo(dataHoje) < 0) {
                valorParcial = Valores.TransformarDinheiroEmValorDouble(tablePlanejamento.getValueAt(i, 8).toString());
                valorAtrasado += valorParcial;
            }
        }

        txtatrasado.setText("R$ " + Valores.TransformarValorFloatEmDinheiro(String.valueOf(valorAtrasado)));
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePlanejamento = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableFaturamento = new javax.swing.JTable();
        btnLancarPlanejamento = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtatrasado = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Planejamento De Faturamento");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablePlanejamento.setAutoCreateRowSorter(true);
        tablePlanejamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DAV", "Cliente", "Código", "OP", "Processo", "Data de Entrega", "Valor R$", "Valor Unitário R$"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePlanejamento.setName("tablePlanejamento"); // NOI18N
        jScrollPane1.setViewportView(tablePlanejamento);
        if (tablePlanejamento.getColumnModel().getColumnCount() > 0) {
            tablePlanejamento.getColumnModel().getColumn(0).setMinWidth(0);
            tablePlanejamento.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablePlanejamento.getColumnModel().getColumn(0).setMaxWidth(0);
            tablePlanejamento.getColumnModel().getColumn(1).setMinWidth(80);
            tablePlanejamento.getColumnModel().getColumn(1).setPreferredWidth(80);
            tablePlanejamento.getColumnModel().getColumn(1).setMaxWidth(80);
            tablePlanejamento.getColumnModel().getColumn(4).setMinWidth(80);
            tablePlanejamento.getColumnModel().getColumn(4).setPreferredWidth(80);
            tablePlanejamento.getColumnModel().getColumn(4).setMaxWidth(80);
            tablePlanejamento.getColumnModel().getColumn(6).setMinWidth(105);
            tablePlanejamento.getColumnModel().getColumn(6).setPreferredWidth(105);
            tablePlanejamento.getColumnModel().getColumn(6).setMaxWidth(105);
            tablePlanejamento.getColumnModel().getColumn(7).setMinWidth(85);
            tablePlanejamento.getColumnModel().getColumn(7).setPreferredWidth(85);
            tablePlanejamento.getColumnModel().getColumn(7).setMaxWidth(85);
        }

        jPanel4.setName("jPanel4"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tableFaturamento.setAutoCreateRowSorter(true);
        tableFaturamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Faturamento Mensal", null, null},
                {"Faturamento 1ª Dezena", null, null},
                {"Faturamento 2ª Dezena", null, null},
                {"Faturamento 3ª Dezena", null, null}
            },
            new String [] {
                "", "Planejado", "Atingido R$"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableFaturamento.setName("tableFaturamento"); // NOI18N
        tableFaturamento.setShowGrid(true);
        jScrollPane2.setViewportView(tableFaturamento);
        if (tableFaturamento.getColumnModel().getColumnCount() > 0) {
            tableFaturamento.getColumnModel().getColumn(1).setMinWidth(95);
            tableFaturamento.getColumnModel().getColumn(1).setPreferredWidth(95);
            tableFaturamento.getColumnModel().getColumn(1).setMaxWidth(95);
            tableFaturamento.getColumnModel().getColumn(2).setMinWidth(90);
            tableFaturamento.getColumnModel().getColumn(2).setPreferredWidth(90);
            tableFaturamento.getColumnModel().getColumn(2).setMaxWidth(90);
        }

        btnLancarPlanejamento.setText("Lançar Planejamento");
        btnLancarPlanejamento.setName("btnLancarPlanejamento"); // NOI18N
        btnLancarPlanejamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLancarPlanejamentoActionPerformed(evt);
            }
        });

        jLabel1.setText("Atrasado");
        jLabel1.setName("jLabel1"); // NOI18N

        txtatrasado.setEditable(false);
        txtatrasado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtatrasado.setName("txtatrasado"); // NOI18N

        jButton1.setText("Salvar Atingido");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Zerar Atingido");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtatrasado))
                    .addComponent(btnLancarPlanejamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtatrasado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLancarPlanejamento)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1064, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
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

    private void btnLancarPlanejamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLancarPlanejamentoActionPerformed
        double valorProjetado = Double.parseDouble(JOptionPane.showInputDialog(null, "Qual o Faturamento Planejado?", "Faturamento Planejado", JOptionPane.YES_NO_OPTION));

        pb.setPlanejamento(valorProjetado);
        //planejamento = ? WHERE id = 1
        pd.update(pb);

        tableFillFaturamento();
    }//GEN-LAST:event_btnLancarPlanejamentoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        double atingidoMensal, atingido1 = 0, atingido2 = 0, atingido3 = 0;

        if (tableFaturamento.getValueAt(1, 2) != null) {
            atingido1 = Valores.TransformarDinheiroEmValorDouble(tableFaturamento.getValueAt(1, 2).toString().replace("R$ ", ""));
        }
        if (tableFaturamento.getValueAt(2, 2) != null) {
            atingido2 = Valores.TransformarDinheiroEmValorDouble(tableFaturamento.getValueAt(2, 2).toString().replace("R$ ", ""));
        }
        if (tableFaturamento.getValueAt(3, 2) != null) {
            atingido3 = Valores.TransformarDinheiroEmValorDouble(tableFaturamento.getValueAt(3, 2).toString().replace("R$ ", ""));
        }

        atingidoMensal = atingido1 + atingido2 + atingido3;

        pb.setAtingidomensal(atingidoMensal);
        pb.setAtingido1(atingido1);
        pb.setAtingido2(atingido2);
        pb.setAtingido3(atingido3);

        //atingidomensal = ?, atingido1 = ?, atingido2 = ?, atingido3 = ? WHERE id = 1
        pd.updateAtingido(pb);

        tableFillFaturamento();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int resp = JOptionPane.showConfirmDialog(null, "Deseja zerar os valores atingidos?", "Zerar Valores", JOptionPane.YES_NO_OPTION);

        if (resp == 0) {
            pb.setAtingidomensal(0);
            pb.setAtingido1(0);
            pb.setAtingido2(0);
            pb.setAtingido3(0);

            //atingidomensal = ?, atingido1 = ?, atingido2 = ?, atingido3 = ? WHERE id = 1
            pd.updateAtingido(pb);

            tableFillFaturamento();
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnLancarPlanejamento;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable tableFaturamento;
    public static javax.swing.JTable tablePlanejamento;
    public static javax.swing.JTextField txtatrasado;
    // End of variables declaration//GEN-END:variables
}
