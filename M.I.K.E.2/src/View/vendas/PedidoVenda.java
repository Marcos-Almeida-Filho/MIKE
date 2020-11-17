/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.vendas;

import Bean.F_UPBean;
import Bean.VendasPedidoItensBean;
import Connection.Session;
import DAO.AltDAO;
import DAO.F_UPDAO;
import DAO.F_UP_HistDAO;
import DAO.OPDAO;
import DAO.OPDocDAO;
import DAO.OPProcessosDAO;
import DAO.VendasMateriaisDAO;
import DAO.VendasMateriaisDocDAO;
import DAO.VendasPedidoDAO;
import DAO.VendasPedidoDocsDAO;
import DAO.VendasPedidoItensDAO;
import DAO.VendasPedidoObsDAO;
import Methods.Dates;
import Methods.SendEmail;
import Methods.Telas;
import Methods.Valores;
import View.Geral.AdicionarObs;
import View.Geral.HistoricoAlteracao;
import View.Geral.ItemPedido;
import View.Geral.ProcurarCliente;
import View.Geral.ProcurarCondicaoDePagamento;
import View.Geral.ProcurarDocumento;
import View.Geral.ProcurarRepresentante;
import View.Geral.ProcurarVendedor;
import View.comercial.DocumentosFornecedores;
import View.fiscal.NotasFiscais;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class PedidoVenda extends javax.swing.JInternalFrame {

    static int idCotacao = 0;

    static VendasPedidoDAO vpd = new VendasPedidoDAO();
    static VendasPedidoItensDAO vpid = new VendasPedidoItensDAO();
    static VendasPedidoDocsDAO vpdd = new VendasPedidoDocsDAO();
    static VendasPedidoObsDAO vpod = new VendasPedidoObsDAO();
    static AltDAO ad = new AltDAO();
    static OPDAO od = new OPDAO();
    static OPDocDAO odd = new OPDocDAO();
    static OPProcessosDAO opd = new OPProcessosDAO();
    static VendasMateriaisDAO vmd = new VendasMateriaisDAO();
    static VendasMateriaisDocDAO vmdd = new VendasMateriaisDocDAO();
    static F_UPDAO fud = new F_UPDAO();
    static F_UP_HistDAO fuhd = new F_UP_HistDAO();

    static public boolean pedidoAtualizado, pedidoCriado, itensCriados, docsCriados, obsCriadas;

    public String clienteOriginal, condicaoOriginal, representanteOriginal, vendedorOriginal;
    public int numDocsOriginal, numObsOriginal, numItensOriginal;

    /**
     * Creates new form CotacaoVenda
     */
    public PedidoVenda() {
        initComponents();
        status();
        lerPedidosAbertos();
        pedidoDesativado();
    }

    public static void lerPedidosAbertos() {
        DefaultTableModel modelTablePedidos = (DefaultTableModel) tablePedidos.getModel();

        modelTablePedidos.setNumRows(0);

        String status = cbStatus.getSelectedItem().toString();

        if (txtPesquisa.getText().equals("")) {
            switch (status) {
                case "Em Aberto":
                    vpd.readPedidosAbertos().forEach(vpb -> {
                        modelTablePedidos.addRow(new Object[]{
                            vpb.getId(),
                            false,
                            vpb.getPedido(),
                            vpb.getCliente(),
                            vpb.getStatus()
                        });
                    });
                    break;
                case "Parcialmente Faturado":
                    vpd.readPedidosStatus(status).forEach(vpb -> {
                        modelTablePedidos.addRow(new Object[]{
                            vpb.getId(),
                            false,
                            vpb.getPedido(),
                            vpb.getCliente(),
                            vpb.getStatus()
                        });
                    });
                    break;
                case "Desativado":
                    vpd.readPedidosStatus(status).forEach(vpb -> {
                        modelTablePedidos.addRow(new Object[]{
                            vpb.getId(),
                            false,
                            vpb.getPedido(),
                            vpb.getCliente(),
                            vpb.getStatus()
                        });
                    });
                    break;
                case "Fechado":
                    vpd.readPedidosStatus(status).forEach(vpb -> {
                        modelTablePedidos.addRow(new Object[]{
                            vpb.getId(),
                            false,
                            vpb.getPedido(),
                            vpb.getCliente(),
                            vpb.getStatus()
                        });
                    });
                    break;
                case "Todos":
                    vpd.readPedidos().forEach(vpb -> {
                        modelTablePedidos.addRow(new Object[]{
                            vpb.getId(),
                            false,
                            vpb.getPedido(),
                            vpb.getCliente(),
                            vpb.getStatus()
                        });
                    });
                    break;
            }
        } else {

        }
    }

    public static void lerPedido(String pedido) {
        vpd.readPedido(pedido).forEach(vpb -> {
            txtDataAbertura.setText(Dates.TransformarDataCurtaDoDB(vpb.getData_abertura()));
            txtCliente.setText(vpb.getCliente());
            txtStatus.setText(vpb.getStatus());
            txtVendedor.setText(vpb.getVendedor());
            txtRep.setText(vpb.getRepresentante());
            txtCondPag.setText(vpb.getCondicao());
        });
    }

    public static void lerItensPedido(String pedido) {
        DefaultTableModel modelItens = (DefaultTableModel) tableItens.getModel();
        modelItens.setNumRows(0);

        vpid = new VendasPedidoItensDAO();

        vpid.readItens(pedido).forEach(vpib -> {
            modelItens.addRow(new Object[]{
                vpib.getId(),
                false,
                vpib.getCodigo(),
                vpib.getDescricao(),
                Valores.TransformarDoubleDBemString(vpib.getQtd()),
                Valores.TransformarDoubleDBemString(vpib.getValorunitario()),
                Valores.TransformarDoubleDBemString(vpib.getValortotal()),
                vpib.getPrazo(),
                vpib.getPedido(),
                vpib.getOp(),
                vpib.getNf(),
                vpib.getIdMaterial()
            });
        });
    }

    public static void lerDocumentosPedido(String pedido) {
        DefaultTableModel tableDocumentos = (DefaultTableModel) tableDocs.getModel();
        tableDocumentos.setNumRows(0);

        vpdd = new VendasPedidoDocsDAO();

        vpdd.readDocs(pedido).forEach(vpdb -> {
            tableDocumentos.addRow(new Object[]{
                vpdb.getId(),
                false,
                vpdb.getDescricao(),
                vpdb.getLocal(),
                ""
            });
        });
    }

    public static void lerObsPedido(String pedido) {
        DefaultTableModel tableObservacao = (DefaultTableModel) tableObs.getModel();
        tableObservacao.setNumRows(0);

        vpod = new VendasPedidoObsDAO();

        vpod.readObs(pedido).forEach(vcob -> {
            tableObservacao.addRow(new Object[]{
                vcob.getId(),
                false,
                Dates.TransformarDataCurtaDoDB(vcob.getData()),
                vcob.getUsuario(),
                vcob.getObs()
            });
        });
    }

    public static void status() {
        pedidoAtualizado = false;
        pedidoCriado = false;
        itensCriados = false;
        docsCriados = false;
        obsCriadas = false;
    }

    public static void txtTotal() {
        double valorItem, valorTotal = 0;
        for (int i = 0; i < tableItens.getRowCount(); i++) {
            String valor = tableItens.getValueAt(i, 6).toString().replace(".", "");
            String replace = valor.replace(",", ".");
            valorItem = Double.parseDouble(replace);
            valorTotal += valorItem;
            String valorTotalS = Valores.TransformarDoubleDBemString(valorTotal);
            txtValorTotal.setText(valorTotalS);
        }
    }

    public static void pedidoDesativado() {
        if (txtStatus.getText().equals("Desativado")) {
            btnProcurarCliente.setEnabled(false);
            btnCondPag.setEnabled(false);
            btnRep.setEnabled(false);
            btnVendedor.setEnabled(false);
            btnMotivo.setVisible(true);
            btnAddObs.setEnabled(false);
            btnDelObs.setEnabled(false);
            btnAddDoc.setEnabled(false);
            btnDelDoc.setEnabled(false);
            btnAddItem.setEnabled(false);
            btnDelItem.setEnabled(false);
            btnNF.setEnabled(false);
            btnOpenOP.setEnabled(false);
            btnSalvar.setEnabled(false);
            btnSendPedido.setEnabled(false);
            btnMarcarTodos.setEnabled(false);
        } else {
            btnProcurarCliente.setEnabled(true);
            btnCondPag.setEnabled(true);
            btnRep.setEnabled(true);
            btnVendedor.setEnabled(true);
            btnMotivo.setVisible(false);
            btnAddObs.setEnabled(true);
            btnDelObs.setEnabled(true);
            btnAddDoc.setEnabled(true);
            btnDelDoc.setEnabled(true);
            btnAddItem.setEnabled(true);
            btnDelItem.setEnabled(true);
            btnNF.setEnabled(true);
            btnOpenOP.setEnabled(true);
            btnSalvar.setEnabled(true);
            btnSendPedido.setEnabled(true);
            btnMarcarTodos.setEnabled(true);
        }
    }

    public static void zerarCampos() {
        txtPedido.setText("");
        txtDataAbertura.setText("");
        txtStatus.setText("");
        txtCliente.setText("");
        txtCondPag.setText("");
        txtRep.setText("");
        txtVendedor.setText("");
        DefaultTableModel modelObs = (DefaultTableModel) tableObs.getModel();
        modelObs.setNumRows(0);
        DefaultTableModel modelDoc = (DefaultTableModel) tableDocs.getModel();
        modelDoc.setNumRows(0);
        DefaultTableModel modelItens = (DefaultTableModel) tableItens.getModel();
        modelItens.setNumRows(0);
        pedidoDesativado();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        tabPedidos = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablePedidos = new javax.swing.JTable();
        jButton15 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        cbStatus = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        txtDataAbertura = new javax.swing.JTextField();
        btnProcurarCliente = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtPedido = new javax.swing.JTextField();
        btnMotivo = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCondPag = new javax.swing.JTextField();
        txtRep = new javax.swing.JTextField();
        txtVendedor = new javax.swing.JTextField();
        btnCondPag = new javax.swing.JButton();
        btnRep = new javax.swing.JButton();
        btnVendedor = new javax.swing.JButton();
        tabItens = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableObs = new javax.swing.JTable();
        btnAddObs = new javax.swing.JButton();
        btnDelObs = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDocs = new javax.swing.JTable();
        btnAddDoc = new javax.swing.JButton();
        btnDelDoc = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableItens = new javax.swing.JTable();
        btnAddItem = new javax.swing.JButton();
        btnDelItem = new javax.swing.JButton();
        txtValorTotal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnMarcarTodos = new javax.swing.JButton();
        btnOpenOP = new javax.swing.JButton();
        btnNF = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnSendPedido = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Pedidos de Venda");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        tabPedidos.setName("tabPedidos"); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        tablePedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Pedido", "Cliente", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

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
        jScrollPane4.setViewportView(tablePedidos);
        if (tablePedidos.getColumnModel().getColumnCount() > 0) {
            tablePedidos.getColumnModel().getColumn(0).setMinWidth(0);
            tablePedidos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablePedidos.getColumnModel().getColumn(0).setMaxWidth(0);
            tablePedidos.getColumnModel().getColumn(1).setMinWidth(35);
            tablePedidos.getColumnModel().getColumn(1).setPreferredWidth(35);
            tablePedidos.getColumnModel().getColumn(1).setMaxWidth(35);
        }

        jButton15.setText("Desativar Pedido");
        jButton15.setName("jButton15"); // NOI18N
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel9.setName("jPanel9"); // NOI18N

        txtPesquisa.setName("txtPesquisa"); // NOI18N
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));
        jPanel10.setName("jPanel10"); // NOI18N

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Aberto", "Parcialmente Faturado", "Faturado", "Desativado", "Todos" }));
        cbStatus.setName("cbStatus"); // NOI18N
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbStatus, 0, 128, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1111, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton15)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton15)
                .addContainerGap())
        );

        tabPedidos.addTab("Pedidos", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setName("jPanel3"); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));
        jPanel4.setName("jPanel4"); // NOI18N

        jLabel4.setText("Cliente");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText("Data de Abertura");
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText("Status");
        jLabel6.setName("jLabel6"); // NOI18N

        txtCliente.setEditable(false);
        txtCliente.setName("txtCliente"); // NOI18N

        txtStatus.setEditable(false);
        txtStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStatus.setName("txtStatus"); // NOI18N

        txtDataAbertura.setEditable(false);
        txtDataAbertura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataAbertura.setName("txtDataAbertura"); // NOI18N

        btnProcurarCliente.setText("Procurar");
        btnProcurarCliente.setName("btnProcurarCliente"); // NOI18N
        btnProcurarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcurarClienteActionPerformed(evt);
            }
        });

        jLabel8.setText("Pedido");
        jLabel8.setName("jLabel8"); // NOI18N

        txtPedido.setEditable(false);
        txtPedido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPedido.setName("txtPedido"); // NOI18N

        btnMotivo.setText("Motivo");
        btnMotivo.setName("btnMotivo"); // NOI18N
        btnMotivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMotivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMotivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProcurarCliente))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtDataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMotivo))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProcurarCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Financeiro"));
        jPanel5.setName("jPanel5"); // NOI18N

        jLabel1.setText("Condição de Pagamento");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Representante");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("Vendedor");
        jLabel3.setName("jLabel3"); // NOI18N

        txtCondPag.setEditable(false);
        txtCondPag.setName("txtCondPag"); // NOI18N

        txtRep.setEditable(false);
        txtRep.setName("txtRep"); // NOI18N

        txtVendedor.setEditable(false);
        txtVendedor.setName("txtVendedor"); // NOI18N

        btnCondPag.setText("Procurar");
        btnCondPag.setName("btnCondPag"); // NOI18N
        btnCondPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCondPagActionPerformed(evt);
            }
        });

        btnRep.setText("Procurar");
        btnRep.setName("btnRep"); // NOI18N
        btnRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRepActionPerformed(evt);
            }
        });

        btnVendedor.setText("Procurar");
        btnVendedor.setName("btnVendedor"); // NOI18N
        btnVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVendedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(61, 61, 61)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtRep, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRep))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtVendedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVendedor))))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCondPag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCondPag))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCondPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCondPag))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtRep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVendedor)
                    .addComponent(txtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabItens.setName("tabItens"); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setName("jPanel6"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableObs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Data", "Usuário", "Observação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableObs.setName("tableObs"); // NOI18N
        tableObs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableObsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableObs);
        if (tableObs.getColumnModel().getColumnCount() > 0) {
            tableObs.getColumnModel().getColumn(0).setMinWidth(0);
            tableObs.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableObs.getColumnModel().getColumn(0).setMaxWidth(0);
            tableObs.getColumnModel().getColumn(1).setMinWidth(35);
            tableObs.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableObs.getColumnModel().getColumn(1).setMaxWidth(35);
            tableObs.getColumnModel().getColumn(2).setMinWidth(90);
            tableObs.getColumnModel().getColumn(2).setPreferredWidth(90);
            tableObs.getColumnModel().getColumn(2).setMaxWidth(90);
            tableObs.getColumnModel().getColumn(3).setMinWidth(250);
            tableObs.getColumnModel().getColumn(3).setPreferredWidth(250);
            tableObs.getColumnModel().getColumn(3).setMaxWidth(250);
        }

        btnAddObs.setText("Adicionar Observação");
        btnAddObs.setName("btnAddObs"); // NOI18N
        btnAddObs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddObsActionPerformed(evt);
            }
        });

        btnDelObs.setText("Excluir Observação");
        btnDelObs.setName("btnDelObs"); // NOI18N
        btnDelObs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelObsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(818, Short.MAX_VALUE)
                .addComponent(btnDelObs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddObs)
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddObs)
                    .addComponent(btnDelObs))
                .addContainerGap())
        );

        tabItens.addTab("Observações", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setName("jPanel7"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tableDocs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Descrição", "Local", "Local Original"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDocs.setName("tableDocs"); // NOI18N
        jScrollPane2.setViewportView(tableDocs);
        if (tableDocs.getColumnModel().getColumnCount() > 0) {
            tableDocs.getColumnModel().getColumn(0).setMinWidth(0);
            tableDocs.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableDocs.getColumnModel().getColumn(0).setMaxWidth(0);
            tableDocs.getColumnModel().getColumn(1).setMinWidth(35);
            tableDocs.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableDocs.getColumnModel().getColumn(1).setMaxWidth(35);
            tableDocs.getColumnModel().getColumn(4).setMinWidth(0);
            tableDocs.getColumnModel().getColumn(4).setPreferredWidth(0);
            tableDocs.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        btnAddDoc.setText("Adicionar Documento");
        btnAddDoc.setName("btnAddDoc"); // NOI18N
        btnAddDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDocActionPerformed(evt);
            }
        });

        btnDelDoc.setText("Excluir Documento");
        btnDelDoc.setName("btnDelDoc"); // NOI18N
        btnDelDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelDocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(822, Short.MAX_VALUE)
                .addComponent(btnDelDoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddDoc)
                .addContainerGap())
            .addComponent(jScrollPane2)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddDoc)
                    .addComponent(btnDelDoc))
                .addContainerGap())
        );

        tabItens.addTab("Documentos", jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setName("jPanel8"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tableItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Código", "Descrição", "Qtd", "Valor Unitário", "Valor Total", "Prazo de Entrega", "Pedido do Cliente", "OP", "NF", "IdMaterial"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableItens.setName("tableItens"); // NOI18N
        tableItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableItensMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableItensMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tableItens);
        if (tableItens.getColumnModel().getColumnCount() > 0) {
            tableItens.getColumnModel().getColumn(0).setMinWidth(0);
            tableItens.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableItens.getColumnModel().getColumn(0).setMaxWidth(0);
            tableItens.getColumnModel().getColumn(1).setMinWidth(35);
            tableItens.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableItens.getColumnModel().getColumn(1).setMaxWidth(35);
            tableItens.getColumnModel().getColumn(2).setMinWidth(150);
            tableItens.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableItens.getColumnModel().getColumn(2).setMaxWidth(150);
            tableItens.getColumnModel().getColumn(4).setMinWidth(60);
            tableItens.getColumnModel().getColumn(4).setPreferredWidth(60);
            tableItens.getColumnModel().getColumn(4).setMaxWidth(60);
            tableItens.getColumnModel().getColumn(5).setMinWidth(100);
            tableItens.getColumnModel().getColumn(5).setPreferredWidth(100);
            tableItens.getColumnModel().getColumn(5).setMaxWidth(100);
            tableItens.getColumnModel().getColumn(6).setMinWidth(100);
            tableItens.getColumnModel().getColumn(6).setPreferredWidth(100);
            tableItens.getColumnModel().getColumn(6).setMaxWidth(100);
            tableItens.getColumnModel().getColumn(7).setMinWidth(110);
            tableItens.getColumnModel().getColumn(7).setPreferredWidth(110);
            tableItens.getColumnModel().getColumn(7).setMaxWidth(110);
            tableItens.getColumnModel().getColumn(8).setMinWidth(110);
            tableItens.getColumnModel().getColumn(8).setPreferredWidth(110);
            tableItens.getColumnModel().getColumn(8).setMaxWidth(110);
            tableItens.getColumnModel().getColumn(9).setMinWidth(80);
            tableItens.getColumnModel().getColumn(9).setPreferredWidth(80);
            tableItens.getColumnModel().getColumn(9).setMaxWidth(80);
            tableItens.getColumnModel().getColumn(10).setMinWidth(80);
            tableItens.getColumnModel().getColumn(10).setPreferredWidth(80);
            tableItens.getColumnModel().getColumn(10).setMaxWidth(80);
            tableItens.getColumnModel().getColumn(11).setMinWidth(0);
            tableItens.getColumnModel().getColumn(11).setPreferredWidth(0);
            tableItens.getColumnModel().getColumn(11).setMaxWidth(0);
        }

        btnAddItem.setText("Adicionar Item");
        btnAddItem.setName("btnAddItem"); // NOI18N
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });

        btnDelItem.setText("Excluir Item");
        btnDelItem.setName("btnDelItem"); // NOI18N
        btnDelItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelItemActionPerformed(evt);
            }
        });

        txtValorTotal.setEditable(false);
        txtValorTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorTotal.setName("txtValorTotal"); // NOI18N

        jLabel7.setText("Total: R$");
        jLabel7.setName("jLabel7"); // NOI18N

        btnMarcarTodos.setText("Marcar Todos");
        btnMarcarTodos.setName("btnMarcarTodos"); // NOI18N
        btnMarcarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcarTodosActionPerformed(evt);
            }
        });

        btnOpenOP.setText("Abrir OP's");
        btnOpenOP.setName("btnOpenOP"); // NOI18N
        btnOpenOP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenOPActionPerformed(evt);
            }
        });

        btnNF.setText("Lançar NF");
        btnNF.setName("btnNF"); // NOI18N
        btnNF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(btnMarcarTodos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 476, Short.MAX_VALUE)
                .addComponent(btnNF)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOpenOP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane3)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddItem)
                    .addComponent(btnDelItem)
                    .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(btnMarcarTodos)
                    .addComponent(btnOpenOP)
                    .addComponent(btnNF))
                .addContainerGap())
        );

        tabItens.addTab("Itens", jPanel8);

        btnSalvar.setText("Salvar");
        btnSalvar.setName("btnSalvar"); // NOI18N
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jButton2.setText("Novo");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnSendPedido.setText("Enviar Pedido");
        btnSendPedido.setName("btnSendPedido"); // NOI18N
        btnSendPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendPedidoActionPerformed(evt);
            }
        });

        jButton14.setText("Alterações");
        jButton14.setName("jButton14"); // NOI18N
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabItens)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSendPedido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabItens)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(jButton2)
                    .addComponent(btnSendPedido)
                    .addComponent(jButton14))
                .addContainerGap())
        );

        tabPedidos.addTab("Pedido", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPedidos)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPedidos)
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

    private void btnCondPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCondPagActionPerformed
        ProcurarCondicaoDePagamento pcp = new ProcurarCondicaoDePagamento(this.getClass().getSimpleName());
        Telas.AparecerTela(pcp);
    }//GEN-LAST:event_btnCondPagActionPerformed

    private void btnRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepActionPerformed
        ProcurarRepresentante pr = new ProcurarRepresentante(this.getClass().getSimpleName());
        Telas.AparecerTela(pr);
    }//GEN-LAST:event_btnRepActionPerformed

    private void btnVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVendedorActionPerformed
        ProcurarVendedor pv = new ProcurarVendedor(this.getClass().getSimpleName());
        Telas.AparecerTela(pv);
    }//GEN-LAST:event_btnVendedorActionPerformed

    private void btnProcurarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurarClienteActionPerformed
        ProcurarCliente pc = new ProcurarCliente(this.getClass().getSimpleName());
        Telas.AparecerTela(pc);
    }//GEN-LAST:event_btnProcurarClienteActionPerformed

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
        ItemPedido ip = new ItemPedido(this.getClass().getSimpleName());
        ip.setTitle("Item Pedido de Venda");
        Telas.AparecerTela(ip);
    }//GEN-LAST:event_btnAddItemActionPerformed

    private void tableItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableItensMouseClicked
        int row = tableItens.getSelectedRow();
        if (evt.getButton() == 1) {
            if (evt.getClickCount() == 2) {
                ItemPedido ip = new ItemPedido(this.getClass().getSimpleName());

                if (tableItens.getValueAt(row, 0).equals("")) {
                    ip.idItemCotacao = 0;
                } else {
                    ip.idItemCotacao = Integer.parseInt(tableItens.getValueAt(row, 0).toString());
                }

                ItemPedido.txtcodigo.setText(tableItens.getValueAt(row, 2).toString());
                ItemPedido.txtdesc.setText(tableItens.getValueAt(row, 3).toString());
                ItemPedido.txtqtd.setText(tableItens.getValueAt(row, 4).toString());
                ItemPedido.txtvalor.setText(tableItens.getValueAt(row, 5).toString());
                ItemPedido.txtpedido.setText(tableItens.getValueAt(row, 8).toString());
                ItemPedido.txtprazo.setText(tableItens.getValueAt(row, 7).toString().replace(" dias úteis", ""));

                Telas.AparecerTela(ip);
            }
        } else if (evt.getButton() == 3) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem abrirOP = new JMenuItem("Abrir OP");
            JMenuItem abrirNF = new JMenuItem("Abrir NF");
            JMenuItem lancarNF = new JMenuItem("Lançar NF");

            abrirOP.addActionListener((ActionEvent e) -> {
                String op = tableItens.getValueAt(tableItens.getSelectedRow(), 9).toString();
                if (op.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Sem OP no produto.");
                } else {
                    OP opjif = new OP();
                    OP.txtNumOP.setText(op);
                    OP.tabOPS.setSelectedIndex(1);
                    OP.lerOP(op);
                    OP.lerDocs(op);
                    OP.lerInspecoes(op);
                    OP.lerMP(op);
                    OP.lerMedidasMaterial(Integer.parseInt(tableItens.getValueAt(row, 11).toString()));
                    OP.lerObs(op);
                    OP.lerProcessos(op);
                    Telas.AparecerTelaAumentada(opjif);
                }
            });

            abrirNF.addActionListener((ActionEvent ae) -> {
                String nf = tableItens.getValueAt(tableItens.getSelectedRow(), 10).toString();
                NotasFiscais nfv = new NotasFiscais();
                Telas.AparecerTela(nfv);
                nfv.tabNF.setSelectedIndex(1);
                nfv.readNF(Integer.parseInt(nf));
            });

            lancarNF.addActionListener((ActionEvent ae) -> {
                int idMaterial = Integer.parseInt(tableItens.getValueAt(row, 11).toString());
                int idItemPedido = Integer.parseInt(tableItens.getValueAt(row, 0).toString());

                VendasPedidoItensBean vpib = new VendasPedidoItensBean();
                vpib.setId(Integer.parseInt(tableItens.getValueAt(row, 0).toString()));
                vpib.setPedido(txtPedido.getText());
                vpib.setIdMaterial(Integer.parseInt(tableItens.getValueAt(row, 11).toString()));
                vpib.setCodigo(tableItens.getValueAt(row, 2).toString());
                vpib.setDescricao(tableItens.getValueAt(row, 3).toString());
                vpib.setQtd(Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(row, 4).toString()));
                vpib.setValorunitario(Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(row, 5).toString()));
                vpib.setValortotal(Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(row, 6).toString()));
                vpib.setPrazo(tableItens.getValueAt(row, 7).toString());
                vpib.setPedido(tableItens.getValueAt(row, 8).toString());
                vpib.setOp(tableItens.getValueAt(row, 9).toString());

                AddNF anf = new AddNF(idMaterial, idItemPedido, vpib);
                Telas.AparecerTela(anf);

            });

            menu.add(abrirOP);
            if (tableItens.getValueAt(tableItens.getSelectedRow(), 10).equals("")) {
                menu.add(lancarNF);
            } else {
                menu.add(abrirNF);
            }

            menu.show(evt.getComponent(), evt.getPoint().x, evt.getPoint().y);
        }
    }//GEN-LAST:event_tableItensMouseClicked

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (txtCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente selecionado.");
        } else if (txtVendedor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum vendedor selecionado.");
        } else if (txtRep.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum representante selecionado.");
        } else if (txtCondPag.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhuma condição de pagamento selecionada.");
        } else if (idCotacao == 0) {
            String pedido = vpd.pedidoAtual();

            //Criar novo pedido
            vpd.create(pedido, Dates.CriarDataCurtaDBSemDataExistente(), txtCliente.getText(), "Ativo", txtVendedor.getText(), txtRep.getText(), txtCondPag.getText());

            txtPedido.setText(pedido);

            //Criar itens do Pedido
            for (int i = 0; i < tableItens.getRowCount(); i++) {
                vpid.create(pedido, Integer.parseInt(tableItens.getValueAt(i, 11).toString()), tableItens.getValueAt(i, 2).toString(), tableItens.getValueAt(i, 3).toString(), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 4).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 5).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 6).toString()), tableItens.getValueAt(i, 7).toString(), tableItens.getValueAt(i, 8).toString(), "", "");
            }

            //Criar documentos da cotação
            for (int i = 0; i < tableDocs.getRowCount(); i++) {
                //Localicação do documento original
                File fileoriginal = new File(tableDocs.getValueAt(i, 4).toString());
                //Pasta que será colocar o documento
                File folder = new File("Q:/MIKE_ERP/cot_ven_arq/" + String.valueOf(pedido));
                //Documento copiado do original
                File filecopy = new File(folder + "/" + fileoriginal.getName());

                //Criar pasta no caso de já não existir
                folder.mkdirs();
                try {
                    //Criar o documento copiado na pasta
                    Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                } catch (IOException ex) {
                    Logger.getLogger(DocumentosFornecedores.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");

                    new Thread() {
                        @Override
                        public void run() {
                            SendEmail.EnviarErro2(ex.toString());
                        }
                    }.start();
                }

                vpdd.create(pedido, tableDocs.getValueAt(i, 2).toString(), tableDocs.getValueAt(i, 3).toString());
            }

            //Criar observações da cotação
            for (int i = 0; i < tableObs.getRowCount(); i++) {
                vpod.create(pedido, Dates.CriarDataCurtaDBComDataExistente(tableObs.getValueAt(i, 2).toString()), tableObs.getValueAt(i, 3).toString(), tableObs.getValueAt(i, 4).toString());
            }

            if (pedidoCriado && itensCriados && docsCriados && obsCriadas) {
                JOptionPane.showMessageDialog(null, "Cotação criada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Ops...algo deu errado. Favor entrar em contato com suporte.");
            }

            status();
        } else {
            String pedido = txtPedido.getText();

            //Atualizar cotação
            vpd.update(pedido, txtCliente.getText(), txtVendedor.getText(), txtRep.getText(), txtCondPag.getText());

            //Criar itens da cotação que não existiam
            for (int i = 0; i < tableItens.getRowCount(); i++) {
                if (tableItens.getValueAt(i, 0).equals("")) {
                    vpid.create(pedido, Integer.parseInt(tableItens.getValueAt(i, 11).toString()), tableItens.getValueAt(i, 2).toString(), tableItens.getValueAt(i, 3).toString(), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 4).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 5).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 6).toString()), tableItens.getValueAt(i, 7).toString(), tableItens.getValueAt(i, 8).toString(), "", "");
                } else {
                    vpid.update(Integer.parseInt(tableItens.getValueAt(i, 11).toString()), tableItens.getValueAt(i, 2).toString(), tableItens.getValueAt(i, 3).toString(), Double.parseDouble(Valores.TransformarStringDinheiroEmStringDouble(tableItens.getValueAt(i, 4).toString())), Double.parseDouble(Valores.TransformarStringDinheiroEmStringDouble(tableItens.getValueAt(i, 5).toString())), Double.parseDouble(Valores.TransformarStringDinheiroEmStringDouble(tableItens.getValueAt(i, 6).toString())), tableItens.getValueAt(i, 7).toString(), tableItens.getValueAt(i, 8).toString(), Integer.parseInt(tableItens.getValueAt(i, 0).toString()));
                }
            }

            //Criar documentos do pedido que não existiam
            for (int i = 0; i < tableDocs.getRowCount(); i++) {
                if (tableDocs.getValueAt(i, 0).equals("")) {
                    vpdd.create(pedido, tableDocs.getValueAt(i, 2).toString(), tableDocs.getValueAt(i, 3).toString());
                }
            }

            //Criar observações do pedido
            for (int i = 0; i < tableObs.getRowCount(); i++) {
                if (tableObs.getValueAt(i, 0).equals("")) {
                    vpod.create(pedido, Dates.InverterDataCurta(tableObs.getValueAt(i, 2).toString()), tableObs.getValueAt(i, 3).toString(), tableObs.getValueAt(i, 4).toString());
                }
            }

            status();

            //Verificar alterações
            String id = txtPedido.getText();
            String tipo = this.getClass().getSimpleName();
            String data = Dates.CriarDataCompletaParaDB();
            String user = Session.nome;
            if (!txtCliente.getText().equals(clienteOriginal)) {
                ad.create(id, tipo, data, user, "Cliente", clienteOriginal, txtCliente.getText());
            }
            if (!txtCondPag.getText().equals(condicaoOriginal)) {
                ad.create(id, tipo, data, user, "Condição de Pagamento", condicaoOriginal, txtCondPag.getText());
            }
            if (!txtRep.getText().equals(representanteOriginal)) {
                ad.create(id, tipo, data, user, "Representante", representanteOriginal, txtRep.getText());
            }
            if (!txtVendedor.getText().equals(condicaoOriginal)) {
                ad.create(id, tipo, data, user, "Vendedor", vendedorOriginal, txtVendedor.getText());
            }
            if (numDocsOriginal != tableDocs.getRowCount()) {
                ad.create(id, tipo, data, user, "Número de Documentos", String.valueOf(numDocsOriginal), String.valueOf(tableDocs.getRowCount()));
            }
            if (numObsOriginal != tableObs.getRowCount()) {
                ad.create(id, tipo, data, user, "Número de Observações", String.valueOf(numObsOriginal), String.valueOf(tableObs.getRowCount()));
            }
            if (numItensOriginal != tableItens.getRowCount()) {
                ad.create(id, tipo, data, user, "Número de Itens", String.valueOf(numItensOriginal), String.valueOf(tableItens.getRowCount()));
            }

            lerPedidosAbertos();
        }
        lerPedido(txtPedido.getText());
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void tablePedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePedidosMouseClicked
        if (evt.getClickCount() == 2) {
            idCotacao = Integer.parseInt(tablePedidos.getValueAt(tablePedidos.getSelectedRow(), 0).toString());

            tabPedidos.setSelectedIndex(1);

            txtPedido.setText(tablePedidos.getValueAt(tablePedidos.getSelectedRow(), 2).toString());

            String cotacao = txtPedido.getText();

            //Pegar dados da Cotação no DB
            lerPedido(cotacao);

            //Pegar itens da Cotação no DB
            lerItensPedido(cotacao);

            //Pegar Documentos da Cotação no DB
            lerDocumentosPedido(cotacao);

            //Pegar Observações da Cotação no DB
            lerObsPedido(cotacao);

            txtTotal();

            pedidoDesativado();

            clienteOriginal = txtCliente.getText();
            condicaoOriginal = txtCondPag.getText();
            representanteOriginal = txtRep.getText();
            vendedorOriginal = txtVendedor.getText();
            numObsOriginal = tableObs.getRowCount();
            numDocsOriginal = tableDocs.getRowCount();
            numItensOriginal = tableItens.getRowCount();
        }
    }//GEN-LAST:event_tablePedidosMouseClicked

    private void btnAddObsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddObsActionPerformed
        AdicionarObs ao = new AdicionarObs(this.getClass().getSimpleName());
        Telas.AparecerTela(ao);
    }//GEN-LAST:event_btnAddObsActionPerformed

    private void btnDelObsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelObsActionPerformed
        int numTrue = 0;
        for (int i = 0; i < tableObs.getRowCount(); i++) {
            if (tableObs.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma observação selecionada.");
        } else {

            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir a observação selecionada?", "Excluir observação", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                for (int i = 0; i < tableObs.getRowCount(); i++) {
                    if (tableObs.getValueAt(i, 1).equals(true)) {
                        vpod.delete(Integer.parseInt(tableObs.getValueAt(i, 0).toString()));
                    }
                }
                lerObsPedido(txtPedido.getText());
            }
        }
    }//GEN-LAST:event_btnDelObsActionPerformed

    private void tableObsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableObsMouseClicked
        if (tableObs.getSelectedColumn() == 1) {
            String user = tableObs.getValueAt(tableObs.getSelectedRow(), 3).toString();
            if (!user.equals(Session.nome)) {
                JOptionPane.showMessageDialog(null, "Usuário não é dono da observação.");
                tableObs.setValueAt(false, tableObs.getSelectedRow(), 1);
            }
        }
    }//GEN-LAST:event_tableObsMouseClicked

    private void btnAddDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDocActionPerformed
        ProcurarDocumento pd = new ProcurarDocumento(this.getClass().getSimpleName());
        Telas.AparecerTela(pd);
    }//GEN-LAST:event_btnAddDocActionPerformed

    private void btnDelItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelItemActionPerformed
        int numTrue = 0;
        for (int i = 0; i < tableItens.getRowCount(); i++) {
            if (tableItens.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir o(s) item(ns) selecionado(s)?", "Excluir Item", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                for (int i = 0; i < tableItens.getRowCount(); i++) {
                    if (tableItens.getValueAt(i, 1).equals(true)) {
                        vpid.delete(Integer.parseInt(tableItens.getValueAt(i, 0).toString()));
                    }
                }
                //Pegar itens da Cotação no DB
                lerItensPedido(txtPedido.getText());

                txtTotal();
            }
        }
    }//GEN-LAST:event_btnDelItemActionPerformed

    private void btnDelDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelDocActionPerformed
        int numTrue = 0;
        for (int i = 0; i < tableDocs.getRowCount(); i++) {
            if (tableDocs.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir o(s) item(ns) selecionado(s)?", "Excluir Item", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                for (int i = 0; i < tableDocs.getRowCount(); i++) {
                    if (tableDocs.getValueAt(i, 1).equals(true)) {
                        vpdd.delete(Integer.parseInt(tableDocs.getValueAt(i, 0).toString()), tableDocs.getValueAt(i, 3).toString());
                    }
                }
                //Pegar Observações da Cotação no DB
                lerDocumentosPedido(txtPedido.getText());
            }
        }
    }//GEN-LAST:event_btnDelDocActionPerformed

    private void btnMarcarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcarTodosActionPerformed
        if (btnMarcarTodos.getText().equals("Marcar Todos")) {
            for (int i = 0; i < tableItens.getRowCount(); i++) {
                tableItens.setValueAt(true, i, 1);
            }
            btnMarcarTodos.setText("Desmarcar Todos");
        } else {
            for (int i = 0; i < tableItens.getRowCount(); i++) {
                tableItens.setValueAt(false, i, 1);
            }
            btnMarcarTodos.setText("Marcar Todos");
        }
    }//GEN-LAST:event_btnMarcarTodosActionPerformed

    private void btnSendPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendPedidoActionPerformed
        JOptionPane.showMessageDialog(null, "Em Breve!");
    }//GEN-LAST:event_btnSendPedidoActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        if (txtPedido.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione ou salve uma cotação primeiro.");
        } else {
            HistoricoAlteracao ha = new HistoricoAlteracao(this.getClass().getSimpleName());
            Telas.AparecerTela(ha);
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void btnOpenOPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenOPActionPerformed
        int numTrue = 0, numOp = 0;
        for (int i = 0; i < tableItens.getRowCount(); i++) {
            if (tableItens.getValueAt(i, 1).equals(true)) {
                numTrue++;
                if (!tableItens.getValueAt(i, 9).equals("")) {
                    numOp++;
                }
            }
        }
        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado.");
        } else if (numOp > 0) {
            JOptionPane.showMessageDialog(null, "Itens com OP selecionados.");
        } else if (!txtCliente.getText().equals(clienteOriginal) || !txtCondPag.getText().equals(condicaoOriginal) || !txtRep.getText().equals(representanteOriginal) || !txtVendedor.getText().equals(vendedorOriginal) || numDocsOriginal != tableDocs.getRowCount() || numObsOriginal != tableObs.getRowCount() || numItensOriginal != tableItens.getRowCount()) {
            JOptionPane.showMessageDialog(null, "Salve o Pedido para que as alterações entrem em vigor primeiro.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja abrir OPs para os itens selecionados?", "Criar OPs", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                for (int i = 0; i < tableItens.getRowCount(); i++) {
                    if (tableItens.getValueAt(i, 1).equals(true)) {
                        String op = od.opAtual();
                        double qtd = Double.parseDouble(tableItens.getValueAt(i, 4).toString().replace(".", "").replace(",", "."));
                        int idProduto = vmd.idProduto(tableItens.getValueAt(i, 2).toString());
                        String dataEntrega = Dates.CriarDataCurtaDBSemDataExistenteComPrazo(Integer.parseInt(tableItens.getValueAt(i, 7).toString().replace(" dias úteis", "")));
                        int idMaterial = Integer.parseInt(tableItens.getValueAt(i, 11).toString());
                        String material = tableItens.getValueAt(i, 2).toString();
                        String dataCriacao = Dates.CriarDataCurtaDBSemDataExistente();

                        od.create(op, dataCriacao, dataEntrega, txtCliente.getText(), txtPedido.getText(), idMaterial, material, tableItens.getValueAt(i, 3).toString(), qtd, qtd, "Rascunho");

                        F_UPBean fub = new F_UPBean();

                        fub.setDav(txtPedido.getText());
                        fub.setOp(op);
                        fub.setDataentrega(dataEntrega);
                        fub.setMaterial(material);
                        fub.setProcesso("Separação de Material");
                        fub.setDatacriacao(dataCriacao);
                        fub.setNivel(5);
                        fub.setValor(Double.parseDouble(tableItens.getValueAt(i, 6).toString().replace(".", "").replace(",", ".")));//
                        fub.setObservacao("");
                        fub.setCliente(txtCliente.getText());

                        //dav, op, dataentrega, material, processo, datacriacao, nivel, valor, observacao, cliente
                        fud.create(fub);

                        vpid.updateOP(op, Integer.parseInt(tableItens.getValueAt(i, 0).toString()));

                        vmdd.read(idProduto).forEach(vmdb -> {
                            //Localicação do documento original
                            File fileoriginal = new File(vmdb.getLocal());
                            //Pasta que será colocar o documento
                            File folder = new File("Q:/MIKE_ERP/op_arq/" + String.valueOf(op));
                            //Documento copiado do original
                            File filecopy = new File(folder + "/" + fileoriginal.getName());

                            //Criar pasta no caso de já não existir
                            folder.mkdirs();
                            try {
                                //Criar o documento copiado na pasta
                                Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                            } catch (IOException ex) {
                                Logger.getLogger(DocumentosFornecedores.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");

                                new Thread() {
                                    @Override
                                    public void run() {
                                        SendEmail.EnviarErro2(ex.toString());
                                    }
                                }.start();
                            }
                            odd.create(op, vmdb.getDescricao(), filecopy.toString().replace("//", "////"));
                        });
                    }
                }
                lerItensPedido(txtPedido.getText());
            }
        }
    }//GEN-LAST:event_btnOpenOPActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int resp = JOptionPane.showConfirmDialog(null, "Deseja lançar um novo pedido?", "Novo Pedido", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            zerarCampos();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        int numTrue = 0, desativados = 0;
        for (int i = 0; i < tablePedidos.getRowCount(); i++) {
            if (tablePedidos.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
            if (tablePedidos.getValueAt(i, 4).equals("Desativado")) {
                desativados++;
            }
        }
        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum Pedido selecionado.");
        } else if (desativados > 0) {
            JOptionPane.showMessageDialog(null, "Pedido desativado selecionado.");
        } else {
            if (numTrue == 1) {
                int resp = JOptionPane.showConfirmDialog(null, "Deseja desativar o Pedido de Venda selecionado?", "Desativar Pedido", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    for (int i = 0; i < tablePedidos.getRowCount(); i++) {
                        if (tablePedidos.getValueAt(i, 1).equals(true)) {
                            String pedido = tablePedidos.getValueAt(i, 2).toString();
                            String motivo = JOptionPane.showInputDialog(null, "Qual o motivo para desativar o Pedido " + pedido + "?", "Motivo", JOptionPane.YES_NO_OPTION);
                            vpd.desativarPedido(pedido, motivo);
                            ad.create(tablePedidos.getValueAt(i, 2).toString(), this.getClass().getSimpleName(), Dates.CriarDataCurtaDBSemDataExistente(), Session.nome, "Status", tablePedidos.getValueAt(i, 4).toString(), "Desativado");
                        }
                    }
                }
            } else {
                int resp = JOptionPane.showConfirmDialog(null, "Deseja desativar os Pedidos de Venda selecionados?", "Desativar Pedidos", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    for (int i = 0; i < tablePedidos.getRowCount(); i++) {
                        if (tablePedidos.getValueAt(i, 1).equals(true)) {
                            String pedido = tablePedidos.getValueAt(i, 2).toString();
                            String motivo = JOptionPane.showInputDialog(null, "Qual o motivo para desativar o Pedido " + pedido + "?", "Motivo", JOptionPane.YES_NO_OPTION);
                            vpd.desativarPedido(pedido, motivo);
                            ad.create(tablePedidos.getValueAt(i, 2).toString(), this.getClass().getSimpleName(), Dates.CriarDataCurtaDBSemDataExistente(), Session.nome, "Status", tablePedidos.getValueAt(i, 4).toString(), "Desativado");
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void btnMotivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMotivoActionPerformed
        JOptionPane.showMessageDialog(null, vpd.readMotivo(txtPedido.getText()));
    }//GEN-LAST:event_btnMotivoActionPerformed

    private void btnNFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNFActionPerformed
        int numTrue = 0, notas = 0;
        for (int i = 0; i < tableItens.getRowCount(); i++) {
            if (tableItens.getValueAt(i, 2).equals(true)) {
                numTrue++;
            }
            if (!tableItens.getValueAt(i, 10).equals("")) {
                notas++;
            }
        }
        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado.");
        } else if (notas > 0) {
            JOptionPane.showMessageDialog(null, "Item com Nota Fiscal selecionado.");
        } else {
            String nota = JOptionPane.showInputDialog(null, "Qual a Nota Fiscal?", "Lançar Nota Fiscal", JOptionPane.YES_NO_OPTION);
            if (nota.length() == 0) {
                JOptionPane.showMessageDialog(null, "Nenhuma nota lançada.");
            } else {
                for (int i = 0; i < tableItens.getRowCount(); i++) {
                    if (tableItens.getValueAt(i, 1).equals(true)) {
                        vpid.updateNotaFiscal(nota, Integer.parseInt(tableItens.getValueAt(i, 0).toString()));
                    }
                }
            }
        }
    }//GEN-LAST:event_btnNFActionPerformed

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        lerPedidosAbertos();
    }//GEN-LAST:event_cbStatusActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        lerPedidosAbertos();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void tableItensMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableItensMouseReleased
        int r = tableItens.rowAtPoint(evt.getPoint());
        if (r >= 0 && r < tableItens.getRowCount()) {
            tableItens.setRowSelectionInterval(r, r);
        } else {
            tableItens.clearSelection();
        }
    }//GEN-LAST:event_tableItensMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAddDoc;
    public static javax.swing.JButton btnAddItem;
    public static javax.swing.JButton btnAddObs;
    public static javax.swing.JButton btnCondPag;
    public static javax.swing.JButton btnDelDoc;
    public static javax.swing.JButton btnDelItem;
    public static javax.swing.JButton btnDelObs;
    public static javax.swing.JButton btnMarcarTodos;
    public static javax.swing.JButton btnMotivo;
    public static javax.swing.JButton btnNF;
    public static javax.swing.JButton btnOpenOP;
    public static javax.swing.JButton btnProcurarCliente;
    public static javax.swing.JButton btnRep;
    public static javax.swing.JButton btnSalvar;
    public static javax.swing.JButton btnSendPedido;
    public static javax.swing.JButton btnVendedor;
    public javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JComboBox<String> cbStatus;
    public javax.swing.JButton jButton14;
    public javax.swing.JButton jButton15;
    public javax.swing.JButton jButton2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel10;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JPanel jPanel8;
    public javax.swing.JPanel jPanel9;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane4;
    public static javax.swing.JTabbedPane tabItens;
    public static javax.swing.JTabbedPane tabPedidos;
    public static javax.swing.JTable tableDocs;
    public static javax.swing.JTable tableItens;
    public static javax.swing.JTable tableObs;
    public static javax.swing.JTable tablePedidos;
    public static javax.swing.JTextField txtCliente;
    public static javax.swing.JTextField txtCondPag;
    public static javax.swing.JTextField txtDataAbertura;
    public static javax.swing.JTextField txtPedido;
    public static javax.swing.JTextField txtPesquisa;
    public static javax.swing.JTextField txtRep;
    public static javax.swing.JTextField txtStatus;
    public static javax.swing.JTextField txtValorTotal;
    public static javax.swing.JTextField txtVendedor;
    // End of variables declaration//GEN-END:variables
}
