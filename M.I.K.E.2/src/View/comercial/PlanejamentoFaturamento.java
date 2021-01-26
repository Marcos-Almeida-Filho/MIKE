/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.comercial;

import Bean.PlanejamentoBean;
import DAO.F_UPDAO;
import DAO.PlanejamentoDAO;
import DAO.ServicoPedidoDAO;
import DAO.ServicoPedidoItensDAO;
import DAO.VendasMateriaisDAO;
import DAO.VendasPedidoDAO;
import DAO.VendasPedidoItensDAO;
import Methods.Colors;
import Methods.Dates;
import Methods.Telas;
import Methods.Valores;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

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

    static PlanejamentoDAO pd = new PlanejamentoDAO();
    static PlanejamentoBean pb = new PlanejamentoBean();
    static F_UPDAO fd = new F_UPDAO();
    static VendasPedidoItensDAO vpid = new VendasPedidoItensDAO();
    static VendasPedidoDAO vpd = new VendasPedidoDAO();
    static VendasMateriaisDAO vmd = new VendasMateriaisDAO();
    static ServicoPedidoItensDAO spid = new ServicoPedidoItensDAO();
    static ServicoPedidoDAO spd = new ServicoPedidoDAO();

    /**
     * Creates new form PlanejamentoQuinzenal
     */
    public PlanejamentoFaturamento() {
        initComponents();
        datasJDateChooser();
        tableFill();
    }

    private static JTable getNewRenderedTable(final JTable table, int colunaPedido, int colunaEstoque) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                double qtdPedido = Valores.TransformarDinheiroEmValorDouble(table.getModel().getValueAt(row, colunaPedido).toString());
                double qtdEstoque = Valores.TransformarDinheiroEmValorDouble(table.getModel().getValueAt(row, colunaEstoque).toString());
                if (qtdEstoque >= qtdPedido) {
                    setBackground(Colors.green);
                    setForeground(Color.BLACK);
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
                return this;
            }
        });
        return table;
    }

    public static void datasJDateChooser() {
        Dates.SetarDataJDateChooser(jdateinicio, Dates.primeiroDiaDoMes());
        Dates.SetarDataJDateChooser(jdatefim, Dates.ultimoDiaDoMes());
    }

    public static void tableFill() {
        DefaultTableModel model = (DefaultTableModel) tablePlanejamento.getModel();
        model.setNumRows(0);

        DefaultTableModel model1 = (DefaultTableModel) tablePlanejamento1.getModel();
        model1.setNumRows(0);

        String ordem = "";

        String dataInicio = Dates.CriarDataCurtaDBJDateChooser(jdateinicio.getDate());
        String dataFim = Dates.CriarDataCurtaDBJDateChooser(jdatefim.getDate());

        switch (cbOrdem.getSelectedItem().toString()) {
            case "DAV":
                ordem = "dav";
                break;
            case "Data de Entrega":
                ordem = "prazo";
                break;
        }

        vpid.readItensSemNF(ordem, dataInicio, dataFim).forEach(vpib -> {
            model.addRow(new Object[]{
                vpib.getId(),
                false,
                vpib.getDav(),
                "",
                vpib.getCodigo(),
                Valores.TransformarDoubleDBemString(vpib.getQtd()),
                0,
                vpib.getOp(),
                "",
                Dates.TransformarDataCurtaDoDB(vpib.getPrazo()),
                Valores.TransformarValorFloatEmDinheiro(String.valueOf(vpib.getValortotal())),
                vpib.getIdMaterial(),
                vpib.getSeparado()
            });
        });
        
        txtValores();

        spid.readitensSemNota().forEach(spib -> {
            model1.addRow(new Object[]{
                spib.getId(),
                spib.getIdpedido(),
                "",
                spib.getCodigo(),
                spib.getQtde(),
                spib.getOs(),
                ""
            });
        });

        JTableHeader th = tablePlanejamento.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();

        //Ler estoque do item
        new Thread() {
            @Override
            public void run() {
                int rowIdMaterial = tcm.getColumnIndex("IDMaterial");
                int rowEstoque = tcm.getColumnIndex("Qtde Estoque");
                int rowQtdPedido = tcm.getColumnIndex("Qtde Pedido");
                for (int i = 0; i < tablePlanejamento.getRowCount(); i++) {
                    int idMaterial = Integer.parseInt(tablePlanejamento.getValueAt(i, rowIdMaterial).toString());
                    double qtd = vmd.readEstoque(idMaterial);
                    tablePlanejamento.setValueAt(Valores.TransformarDoubleDBemString(qtd), i, rowEstoque);
                    getNewRenderedTable(tablePlanejamento, rowQtdPedido, rowEstoque);
                }
            }
        }.start();

        //Ler Cliente do Pedido Vendas
        new Thread() {
            @Override
            public void run() {
                int rowPedido = tcm.getColumnIndex("DAV");
                int rowCliente = tcm.getColumnIndex("Cliente");
                for (int i = 0; i < tablePlanejamento.getRowCount(); i++) {
                    String pedido = tablePlanejamento.getValueAt(i, rowPedido).toString();
                    String cliente = vpd.readCliente(pedido);
                    tablePlanejamento.setValueAt(cliente, i, rowCliente);
                }
            }
        }.start();

        //Ler Processo da OP
        new Thread() {
            @Override
            public void run() {
                int rowOP = tcm.getColumnIndex("OP");
                int rowProcesso = tcm.getColumnIndex("Processo");
                for (int i = 0; i < tablePlanejamento.getRowCount(); i++) {
                    if (!tablePlanejamento.getValueAt(i, rowOP).toString().equals("")) {
                        String op = tablePlanejamento.getValueAt(i, rowOP).toString();
                        String processo = fd.getProcesso(op);
                        tablePlanejamento.setValueAt(processo, i, rowProcesso);
                    }
                }

                for (int i = 0; i < tablePlanejamento1.getRowCount(); i++) {
                    if (!tablePlanejamento1.getValueAt(i, 5).toString().equals("")) {
                        String op = tablePlanejamento1.getValueAt(i, 5).toString();
                        String processo = fd.getProcesso(op);
                        tablePlanejamento1.setValueAt(processo, i, 6);
                    }
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < tablePlanejamento1.getRowCount(); i++) {
                    String dav = tablePlanejamento1.getValueAt(i, 1).toString();
                    String cliente = spd.getCliente(dav);
                    tablePlanejamento1.setValueAt(cliente, i, 2);
                }
            }
        }.start();
    }

    public static void txtValores() {
        double valorAtrasado = 0, valorParcial, valorTotal = 0;
        Date dataDate = new Date(), dataHoje = new Date();

        JTableHeader th = tablePlanejamento.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        int rowDataEntrega = tcm.getColumnIndex("Data de Entrega");
        int rowValor = tcm.getColumnIndex("Valor R$");

        for (int i = 0; i < tablePlanejamento.getRowCount(); i++) {
            valorParcial = Valores.TransformarDinheiroEmValorDouble(tablePlanejamento.getValueAt(i, rowValor).toString());
            valorTotal += valorParcial;
            String dataNormal = tablePlanejamento.getValueAt(i, rowDataEntrega).toString();
            try {
                dataDate = new SimpleDateFormat("dd/MM/yyyy").parse(dataNormal);
            } catch (ParseException ex) {
                Logger.getLogger(PlanejamentoFaturamento.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (dataDate.compareTo(dataHoje) < 0) {
                valorAtrasado += valorParcial;
            }
        }

        txtatrasado.setText("R$ " + Valores.TransformarValorFloatEmDinheiro(String.valueOf(valorAtrasado)));
        txtTotal.setText(Valores.TransformarDoubleDBemDinheiro(valorTotal));
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtatrasado = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePlanejamento = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        cbOrdem = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jdatefim = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jdateinicio = new com.toedter.calendar.JDateChooser();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablePlanejamento1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        cbOrdem1 = new javax.swing.JComboBox<>();

        setClosable(true);
        setTitle("Planejamento De Faturamento");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setName("jPanel3"); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Valores"));
        jPanel4.setName("jPanel4"); // NOI18N

        jLabel1.setText("Atrasado");
        jLabel1.setName("jLabel1"); // NOI18N

        txtatrasado.setEditable(false);
        txtatrasado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtatrasado.setName("txtatrasado"); // NOI18N

        jLabel2.setText("Total");
        jLabel2.setName("jLabel2"); // NOI18N

        txtTotal.setEditable(false);
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setName("txtTotal"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtatrasado, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(txtatrasado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablePlanejamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "DAV", "Cliente", "Código", "Qtde Pedido", "Qtde Estoque", "OP", "Processo", "Data de Entrega", "Valor R$", "IDMaterial", "Separado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePlanejamento.setName("tablePlanejamento"); // NOI18N
        tablePlanejamento.getTableHeader().setReorderingAllowed(false);
        tablePlanejamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePlanejamentoMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablePlanejamentoMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tablePlanejamento);
        if (tablePlanejamento.getColumnModel().getColumnCount() > 0) {
            tablePlanejamento.getColumnModel().getColumn(0).setMinWidth(0);
            tablePlanejamento.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablePlanejamento.getColumnModel().getColumn(0).setMaxWidth(0);
            tablePlanejamento.getColumnModel().getColumn(1).setMinWidth(35);
            tablePlanejamento.getColumnModel().getColumn(1).setPreferredWidth(35);
            tablePlanejamento.getColumnModel().getColumn(1).setMaxWidth(35);
            tablePlanejamento.getColumnModel().getColumn(2).setMinWidth(80);
            tablePlanejamento.getColumnModel().getColumn(2).setPreferredWidth(80);
            tablePlanejamento.getColumnModel().getColumn(2).setMaxWidth(80);
            tablePlanejamento.getColumnModel().getColumn(5).setMinWidth(90);
            tablePlanejamento.getColumnModel().getColumn(5).setPreferredWidth(90);
            tablePlanejamento.getColumnModel().getColumn(5).setMaxWidth(90);
            tablePlanejamento.getColumnModel().getColumn(6).setMinWidth(90);
            tablePlanejamento.getColumnModel().getColumn(6).setPreferredWidth(90);
            tablePlanejamento.getColumnModel().getColumn(6).setMaxWidth(90);
            tablePlanejamento.getColumnModel().getColumn(7).setMinWidth(80);
            tablePlanejamento.getColumnModel().getColumn(7).setPreferredWidth(80);
            tablePlanejamento.getColumnModel().getColumn(7).setMaxWidth(80);
            tablePlanejamento.getColumnModel().getColumn(9).setMinWidth(105);
            tablePlanejamento.getColumnModel().getColumn(9).setPreferredWidth(105);
            tablePlanejamento.getColumnModel().getColumn(9).setMaxWidth(105);
            tablePlanejamento.getColumnModel().getColumn(10).setMinWidth(85);
            tablePlanejamento.getColumnModel().getColumn(10).setPreferredWidth(85);
            tablePlanejamento.getColumnModel().getColumn(10).setMaxWidth(85);
            tablePlanejamento.getColumnModel().getColumn(11).setMinWidth(0);
            tablePlanejamento.getColumnModel().getColumn(11).setPreferredWidth(0);
            tablePlanejamento.getColumnModel().getColumn(11).setMaxWidth(0);
            tablePlanejamento.getColumnModel().getColumn(12).setMinWidth(70);
            tablePlanejamento.getColumnModel().getColumn(12).setPreferredWidth(70);
            tablePlanejamento.getColumnModel().getColumn(12).setMaxWidth(70);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordenar Por"));
        jPanel2.setName("jPanel2"); // NOI18N

        cbOrdem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DAV", "Data de Entrega" }));
        cbOrdem.setName("cbOrdem"); // NOI18N
        cbOrdem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbOrdemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbOrdem, 0, 173, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbOrdem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro Data"));
        jPanel7.setName("jPanel7"); // NOI18N

        jdatefim.setDateFormatString("dd/MM/yyyy");
        jdatefim.setName("jdatefim"); // NOI18N

        jLabel3.setText("Final");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("Início");
        jLabel4.setName("jLabel4"); // NOI18N

        jdateinicio.setDateFormatString("dd/MM/yyyy");
        jdateinicio.setName("jdateinicio"); // NOI18N

        jButton6.setText("Atualizar");
        jButton6.setName("jButton6"); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jdateinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jdatefim, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jdateinicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jdatefim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(4, Short.MAX_VALUE))
        );

        jButton1.setText("Separar Materiais");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Produção", jPanel3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setName("jPanel5"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tablePlanejamento1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DAV", "Cliente", "Código", "Qtde Pedido", "OP", "Processo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePlanejamento1.setName("tablePlanejamento1"); // NOI18N
        jScrollPane3.setViewportView(tablePlanejamento1);
        if (tablePlanejamento1.getColumnModel().getColumnCount() > 0) {
            tablePlanejamento1.getColumnModel().getColumn(0).setMinWidth(0);
            tablePlanejamento1.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablePlanejamento1.getColumnModel().getColumn(0).setMaxWidth(0);
            tablePlanejamento1.getColumnModel().getColumn(1).setMinWidth(80);
            tablePlanejamento1.getColumnModel().getColumn(1).setPreferredWidth(80);
            tablePlanejamento1.getColumnModel().getColumn(1).setMaxWidth(80);
            tablePlanejamento1.getColumnModel().getColumn(4).setMinWidth(90);
            tablePlanejamento1.getColumnModel().getColumn(4).setPreferredWidth(90);
            tablePlanejamento1.getColumnModel().getColumn(4).setMaxWidth(90);
            tablePlanejamento1.getColumnModel().getColumn(5).setMinWidth(80);
            tablePlanejamento1.getColumnModel().getColumn(5).setPreferredWidth(80);
            tablePlanejamento1.getColumnModel().getColumn(5).setMaxWidth(80);
        }

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordenar Por"));
        jPanel6.setName("jPanel6"); // NOI18N

        cbOrdem1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DAV", "Data de Entrega" }));
        cbOrdem1.setName("cbOrdem1"); // NOI18N
        cbOrdem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbOrdem1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbOrdem1, 0, 173, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(cbOrdem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1162, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Serviço", jPanel5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
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

    private void cbOrdemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOrdemActionPerformed
        tableFill();
    }//GEN-LAST:event_cbOrdemActionPerformed

    private void cbOrdem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOrdem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbOrdem1ActionPerformed

    private void tablePlanejamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePlanejamentoMouseClicked
        if (evt.getButton() == 3) {
            if (tablePlanejamento.getValueAt(tablePlanejamento.getSelectedRow(), 7).toString().equals("")) {
                JPopupMenu menu = new JPopupMenu();
                JMenuItem lancarOP = new JMenuItem("Lançar OP");

                lancarOP.addActionListener((ActionEvent e) -> {
                    int idMaterialPedido = Integer.parseInt(tablePlanejamento.getValueAt(tablePlanejamento.getSelectedRow(), 0).toString());
                    int idMaterial = Integer.parseInt(tablePlanejamento.getValueAt(tablePlanejamento.getSelectedRow(), 11).toString());

                    AcharOP ao = new AcharOP();
                    Telas.AparecerTela(ao);
                    AcharOP.idMaterialPedido = idMaterialPedido;
                    AcharOP.idMaterial = idMaterial;
                });

                menu.add(lancarOP);

                menu.show(evt.getComponent(), evt.getPoint().x, evt.getPoint().y);
            }
        }
    }//GEN-LAST:event_tablePlanejamentoMouseClicked

    private void tablePlanejamentoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePlanejamentoMouseReleased
        int r = tablePlanejamento.rowAtPoint(evt.getPoint());
        if (r >= 0 && r < tablePlanejamento.getRowCount()) {
            tablePlanejamento.setRowSelectionInterval(r, r);
        } else {
            tablePlanejamento.clearSelection();
        }
    }//GEN-LAST:event_tablePlanejamentoMouseReleased

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        tableFill();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int numTrue = 0;

        for (int i = 0; i < tablePlanejamento.getRowCount(); i++) {
            if (tablePlanejamento.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum registro selecionado.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Confirma que os materiais selecionados estão separados?", "Separar Materiais", JOptionPane.YES_NO_OPTION);

            if (resp == 0) {
                for (int i = 0; i < tablePlanejamento.getRowCount(); i++) {
                    if (tablePlanejamento.getValueAt(i, 1).equals(true)) {
                        int idItemMaterial = Integer.parseInt(tablePlanejamento.getValueAt(i, 0).toString());
                        vpid.updateSeparado(idItemMaterial);
                    }
                }
                tableFill();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> cbOrdem;
    public javax.swing.JComboBox<String> cbOrdem1;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton6;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTabbedPane jTabbedPane1;
    public static com.toedter.calendar.JDateChooser jdatefim;
    public static com.toedter.calendar.JDateChooser jdateinicio;
    public static javax.swing.JTable tablePlanejamento;
    public static javax.swing.JTable tablePlanejamento1;
    public static javax.swing.JTextField txtTotal;
    public static javax.swing.JTextField txtatrasado;
    // End of variables declaration//GEN-END:variables
}
