/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.vendas;

import Bean.VendasPedidoItensBean;
import Connection.Session;
import DAO.NotaFiscalDAO;
import DAO.NotaFiscalItensDAO;
import DAO.VendasMateriaisDAO;
import DAO.VendasMateriaisMovDAO;
import DAO.VendasPedidoDAO;
import DAO.VendasPedidoItensDAO;
import Methods.Dates;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class AddNF extends javax.swing.JInternalFrame {

    static NotaFiscalDAO nfd = new NotaFiscalDAO();
    NotaFiscalItensDAO nfid = new NotaFiscalItensDAO();
    VendasPedidoDAO vpd = new VendasPedidoDAO();
    VendasPedidoItensDAO vpid = new VendasPedidoItensDAO();
    VendasMateriaisDAO vmd = new VendasMateriaisDAO();
    VendasMateriaisMovDAO vmmd = new VendasMateriaisMovDAO();

    int idMaterial, idItemPedido;

    VendasPedidoItensBean vpib;

    String nf;

    /**
     * Creates new form AddNF
     *
     * @param idMaterial
     * @param idItemPedido
     * @param vpib
     */
    public AddNF(int idMaterial, int idItemPedido, VendasPedidoItensBean vpib) {
        initComponents();
        lerNotas();
        this.idMaterial = idMaterial;
        this.idItemPedido = idItemPedido;
        this.vpib = vpib;
    }

    public static void lerNotas() {
        DefaultTableModel model = (DefaultTableModel) tableNF.getModel();
        model.setNumRows(0);

        nfd.readNotas().forEach(nfb -> {
            model.addRow(new Object[]{
                nfb.getId(),
                nfb.getNumero(),
                nfb.getDestinatario()
            });
        });
    }

    public void lerItensNF(int numero) {
        DefaultTableModel model = (DefaultTableModel) tableItens.getModel();
        model.setNumRows(0);

        nfid.readItensSemPedido(numero).forEach(nfib -> {
            model.addRow(new Object[]{
                nfib.getId(),
                nfib.getCodigo(),
                nfib.getDescricao(),
                nfib.getQtd()
            });
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
        txtPesquisa = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableNF = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableItens = new javax.swing.JTable();

        setClosable(true);
        setTitle("Adicionar Nota Fiscal");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel2.setName("jPanel2"); // NOI18N

        txtPesquisa.setName("txtPesquisa"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableNF.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nota Fiscal", "Destinatário"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableNF.setName("tableNF"); // NOI18N
        tableNF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableNFMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableNF);
        if (tableNF.getColumnModel().getColumnCount() > 0) {
            tableNF.getColumnModel().getColumn(0).setMinWidth(0);
            tableNF.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableNF.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tableItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Código", "Descrição", "Quantidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableItens.setName("tableItens"); // NOI18N
        tableItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableItensMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableItens);
        if (tableItens.getColumnModel().getColumnCount() > 0) {
            tableItens.getColumnModel().getColumn(0).setMinWidth(0);
            tableItens.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableItens.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void tableNFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableNFMouseClicked
        lerItensNF(Integer.parseInt(tableNF.getValueAt(tableNF.getSelectedRow(), 1).toString()));
        nf = tableNF.getValueAt(tableNF.getSelectedRow(), 1).toString();
    }//GEN-LAST:event_tableNFMouseClicked

    private void tableItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableItensMouseClicked
        if (evt.getClickCount() == 2) {
            int idItem = Integer.parseInt(tableItens.getValueAt(tableItens.getSelectedRow(), 0).toString());

            double estoqueAtual = vmd.readEstoque(idMaterial);
            double qtd = Double.parseDouble(tableItens.getValueAt(tableItens.getSelectedRow(), 3).toString());
            if (qtd > vpib.getQtd()) {
                JOptionPane.showMessageDialog(null, "Quantidade da nota fiscal é maior que a quantidade no pedido.");
            } else if (qtd > estoqueAtual) {
                JOptionPane.showMessageDialog(null, "Não há estoque suficiente para dar baixa.");
            } else {
                if (qtd < vpib.getQtd()) {
                    double qtd2 = vpib.getQtd() - qtd;
                    vpid.update(vpib.getIdMaterial(), vpib.getCodigo(), vpib.getDescricao(), qtd, vpib.getValorunitario(), vpib.getValortotal(), vpib.getPrazo(), vpib.getPedido(), vpib.getId());

                    vpid.create(PedidoVenda.txtPedido.getText(), 0, vpib.getCodigo(), vpib.getDescricao(), qtd2, vpib.getValorunitario(), vpib.getValortotal(), vpib.getPrazo(), vpib.getPedido(), "", vpib.getOp());
                } else {
                    vpid.updateNotaFiscal(nf, idItemPedido);
                }
                double estoque = estoqueAtual - qtd;

                nfid.updateIdMaterial(idMaterial, idItem);

                vmd.updateEstoque(estoque, idMaterial);

                vmmd.create(idMaterial, estoqueAtual, qtd, estoque, "Nota Fiscal " + nf, Dates.CriarDataCurtaDBSemDataExistente(), Session.nome);

                PedidoVenda.lerItensPedido(PedidoVenda.txtPedido.getText());
                int numNota = 0;
                for (int i = 0; i < PedidoVenda.tableItens.getRowCount(); i++) {
                    if (!PedidoVenda.tableItens.getValueAt(i, 10).equals("")) {
                        numNota++;
                    }
                }

                String pedido = PedidoVenda.txtPedido.getText();
                if (numNota == PedidoVenda.tableItens.getRowCount()) {
                    vpd.updateStatus(pedido, "Faturado");
                } else {
                    vpd.updateStatus(pedido, "Parcialmente Faturado");
                }

                PedidoVenda.lerPedido(pedido);
                PedidoVenda.lerPedidosAbertos();
                dispose();
            }
        }
    }//GEN-LAST:event_tableItensMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable tableItens;
    public static javax.swing.JTable tableNF;
    public javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
