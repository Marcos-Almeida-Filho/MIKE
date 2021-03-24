/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Geral;

import DAO.ServicoPedidoDAO;
import DAO.VendasCotacaoItensDAO;
import DAO.VendasPedidoDAO;
import DAO.VendasPedidoItensDAO;
import View.servicos.CotacaoServico;
import View.vendas.CotacaoVenda;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class EscolherPedido extends javax.swing.JInternalFrame {

    VendasPedidoDAO vpd = new VendasPedidoDAO();
    VendasPedidoItensDAO vpid = new VendasPedidoItensDAO();
    VendasCotacaoItensDAO vcid = new VendasCotacaoItensDAO();

    ServicoPedidoDAO spd = new ServicoPedidoDAO();

    private final String origem, cliente;

    /**
     * Creates new form EscolherPedido
     *
     * @param origem
     * @param cliente
     */
    public EscolherPedido(String origem, String cliente) {
        initComponents();
        this.origem = origem;
        this.cliente = cliente;
        lerPedidos();
    }

    private void lerPedidos() {
        DefaultTableModel model = (DefaultTableModel) tablePedidos.getModel();
        model.setNumRows(0);

        switch (origem) {
            case "CotacaoVenda":
                vpd.readPedidosAbertosPorCliente(cliente).forEach(vpb -> {
                    model.addRow(new Object[]{
                        vpb.getPedido(),
                        vpb.getCliente()
                    });
                });
                break;
            case "CotacaoServico":
                spd.readEmAbertoPorCliente(cliente).forEach(spb -> {
                    model.addRow(new Object[]{
                        spb.getIdtela(),
                        spb.getCliente()
                    });
                });
                break;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePedidos = new javax.swing.JTable();

        setClosable(true);
        setTitle("Escolher Pedido");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablePedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pedido", "Cliente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePedidos.setName("tablePedidos"); // NOI18N
        tablePedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePedidosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablePedidos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
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

    private void tablePedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePedidosMouseClicked
        if (evt.getClickCount() == 2) {
            String pedido = tablePedidos.getValueAt(tablePedidos.getSelectedRow(), 0).toString();

            switch (origem) {
                case "CotacaoVenda":
                    CotacaoVenda.criarPedidoVenda(pedido);
                    break;
                case "CotacaoServico":
                    CotacaoServico.criarPedido(pedido);
                    break;
            }
            dispose();
        }
    }//GEN-LAST:event_tablePedidosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablePedidos;
    // End of variables declaration//GEN-END:variables
}
