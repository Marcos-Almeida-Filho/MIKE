/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.vendas;

import Connection.Session;
import DAO.VendasCotacaoDAO;
import DAO.VendasCotacaoDocsDAO;
import DAO.VendasCotacaoItensDAO;
import DAO.VendasCotacaoObsDAO;
import Methods.Dates;
import Methods.Telas;
import Methods.Valores;
import View.Geral.AdicionarObs;
import View.Geral.ItemCotacao;
import View.Geral.ProcurarCliente;
import View.Geral.ProcurarCondicaoDePagamento;
import View.Geral.ProcurarDocumento;
import View.Geral.ProcurarRepresentante;
import View.Geral.ProcurarVendedor;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class CotacaoVenda extends javax.swing.JInternalFrame {

    static int idCotacao = 0;

    static VendasCotacaoDAO vcd = new VendasCotacaoDAO();
    static VendasCotacaoItensDAO vcid = new VendasCotacaoItensDAO();
    static VendasCotacaoDocsDAO vcdd = new VendasCotacaoDocsDAO();
    static VendasCotacaoObsDAO vcod = new VendasCotacaoObsDAO();

    static public boolean cotacaoAtualizada, cotacaoCriada, itensCriados, docsCriados, obsCriadas;

    /**
     * Creates new form CotacaoVenda
     */
    public CotacaoVenda() {
        initComponents();
        status();
        lerCotacoesAbertas();
    }

    public static void lerCotacoesAbertas() {
        DefaultTableModel modelTableCotacoes = (DefaultTableModel) tableCotacoes.getModel();
        
        modelTableCotacoes.setNumRows(0);
        
        vcd.readCotacoesAbertas().forEach(vcb -> {
            modelTableCotacoes.addRow(new Object[]{
                vcb.getId(),
                false,
                vcb.getCotacao(),
                vcb.getCliente(),
                vcb.getStatus()
            });
        });
    }
    
    public static void lerCotacao(String cotacao) {
        vcd.readCotacao(cotacao).forEach(vcb -> {
            txtDataAbertura.setText(Dates.TransformarDataCurtaDoDB(vcb.getData_abertura()));
            txtCliente.setText(vcb.getCliente());
            txtStatus.setText(vcb.getStatus());
            if (vcb.isCadastrado()) {
                radioCadastrado.setSelected(true);
            } else {
                radioNaoCadastrado.setSelected(true);
            }
            txtVendedor.setText(vcb.getVendedor());
            txtRep.setText(vcb.getRepresentante());
            txtCondPag.setText(vcb.getCondicao());
        });
    }

    public static void lerItensCotacao(String cotacao) {
        DefaultTableModel modelItens = (DefaultTableModel) tableItens.getModel();
        modelItens.setNumRows(0);

        vcid = new VendasCotacaoItensDAO();

        vcid.readItens(cotacao).forEach(vcib -> {
            modelItens.addRow(new Object[]{
                vcib.getId(),
                false,
                vcib.getCodigo(),
                vcib.getDescricao(),
                Valores.TransformarDoubleDBemString(vcib.getQtd()),
                Valores.TransformarDoubleDBemString(vcib.getValorunitario()),
                Valores.TransformarDoubleDBemString(vcib.getValortotal()),
                vcib.getPrazo(),
                vcib.getPedido(),
                vcib.getDav()
            });
        });
    }
    
    public static void lerDocumentosCotacao(String cotacao) {
        DefaultTableModel tableDocumentos = (DefaultTableModel) tableDocs.getModel();
        tableDocumentos.setNumRows(0);
        
        vcdd = new VendasCotacaoDocsDAO();
        
        vcdd.readDocs(cotacao).forEach(vcdb -> {
            tableDocumentos.addRow(new Object[]{
                vcdb.getId(),
                false,
                vcdb.getDescricao(),
                vcdb.getLocal(),
                ""
            });
        });
    }
    
    public static void lerObsCotacao(String cotacao) {
        DefaultTableModel tableObservacao = (DefaultTableModel) tableObs.getModel();
        tableObservacao.setNumRows(0);
        
        vcod = new VendasCotacaoObsDAO();
        
        vcod.readObs(cotacao).forEach(vcob -> {
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
        cotacaoAtualizada = false;
        cotacaoCriada = false;
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
        tabCotacoes = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableCotacoes = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        txtDataAbertura = new javax.swing.JTextField();
        btnProcurarCliente = new javax.swing.JButton();
        radioCadastrado = new javax.swing.JRadioButton();
        radioNaoCadastrado = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        txtCotacao = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCondPag = new javax.swing.JTextField();
        txtRep = new javax.swing.JTextField();
        txtVendedor = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        tabItens = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableObs = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDocs = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableItens = new javax.swing.JTable();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        txtValorTotal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Cotações de Venda");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        tabCotacoes.setName("tabCotacoes"); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        tableCotacoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Orçamento", "Cliente", "Status"
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
        tableCotacoes.setName("tableCotacoes"); // NOI18N
        tableCotacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCotacoesMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableCotacoes);
        if (tableCotacoes.getColumnModel().getColumnCount() > 0) {
            tableCotacoes.getColumnModel().getColumn(0).setMinWidth(0);
            tableCotacoes.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableCotacoes.getColumnModel().getColumn(0).setMaxWidth(0);
            tableCotacoes.getColumnModel().getColumn(1).setMinWidth(35);
            tableCotacoes.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableCotacoes.getColumnModel().getColumn(1).setMaxWidth(35);
        }

        jButton7.setText("Novo Orçamento");
        jButton7.setName("jButton7"); // NOI18N

        jButton15.setText("Desativar Orçamento");
        jButton15.setName("jButton15"); // NOI18N

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel9.setName("jPanel9"); // NOI18N

        txtPesquisa.setName("txtPesquisa"); // NOI18N

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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Aberto", "Fechado", "Desativado", "Todos" }));
        jComboBox1.setName("jComboBox1"); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jComboBox1, 0, 128, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1046, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton15))
                .addContainerGap())
        );

        tabCotacoes.addTab("Cotações", jPanel2);

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

        buttonGroup1.add(radioCadastrado);
        radioCadastrado.setSelected(true);
        radioCadastrado.setText("Cadastrado");
        radioCadastrado.setName("radioCadastrado"); // NOI18N
        radioCadastrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioCadastradoActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioNaoCadastrado);
        radioNaoCadastrado.setText("Não Cadastrado");
        radioNaoCadastrado.setName("radioNaoCadastrado"); // NOI18N
        radioNaoCadastrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioNaoCadastradoActionPerformed(evt);
            }
        });

        jLabel8.setText("Cotação");
        jLabel8.setName("jLabel8"); // NOI18N

        txtCotacao.setEditable(false);
        txtCotacao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCotacao.setName("txtCotacao"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCotacao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(radioCadastrado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioNaoCadastrado)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtDataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtCotacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioCadastrado)
                    .addComponent(radioNaoCadastrado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

        jButton4.setText("Procurar");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Procurar");
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Procurar");
        jButton6.setName("jButton6"); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
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
                        .addComponent(jButton5))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtVendedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6))))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCondPag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCondPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtRep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(txtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabItens.setName("tabItens"); // NOI18N

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

        jButton8.setText("Adicionar Observação");
        jButton8.setName("jButton8"); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Excluir Observação");
        jButton9.setName("jButton9"); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1034, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addContainerGap())
        );

        tabItens.addTab("Observações", jPanel6);

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

        jButton10.setText("Adicionar Documento");
        jButton10.setName("jButton10"); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Excluir Documento");
        jButton11.setName("jButton11"); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1034, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton11))
                .addContainerGap())
        );

        tabItens.addTab("Documentos", jPanel7);

        jPanel8.setName("jPanel8"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tableItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Código", "Descrição", "Qtd", "Valor Unitário", "Valor Total", "Prazo de Entrega", "Pedido do Cliente", "DAV"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false, false
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
        }

        jButton12.setText("Adicionar Item");
        jButton12.setName("jButton12"); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("Excluir Item");
        jButton13.setName("jButton13"); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        txtValorTotal.setEditable(false);
        txtValorTotal.setName("txtValorTotal"); // NOI18N

        jLabel7.setText("Total: R$");
        jLabel7.setName("jLabel7"); // NOI18N

        jButton16.setText("Marcar Todos");
        jButton16.setName("jButton16"); // NOI18N
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1034, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton13)
                    .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jButton16))
                .addContainerGap())
        );

        tabItens.addTab("Itens", jPanel8);

        jButton1.setText("Salvar");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Novo");
        jButton2.setName("jButton2"); // NOI18N

        jButton3.setText("Enviar Orçamento");
        jButton3.setName("jButton3"); // NOI18N

        jButton14.setText("Alterações");
        jButton14.setName("jButton14"); // NOI18N

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
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
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
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton14))
                .addContainerGap())
        );

        tabCotacoes.addTab("Cotação", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabCotacoes)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabCotacoes)
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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        ProcurarCondicaoDePagamento pcp = new ProcurarCondicaoDePagamento(this.getClass().getSimpleName());
        Telas.AparecerTela(pcp);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ProcurarRepresentante pr = new ProcurarRepresentante(this.getClass().getSimpleName());
        Telas.AparecerTela(pr);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        ProcurarVendedor pv = new ProcurarVendedor(this.getClass().getSimpleName());
        Telas.AparecerTela(pv);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnProcurarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurarClienteActionPerformed
        ProcurarCliente pc = new ProcurarCliente(this.getClass().getSimpleName());
        Telas.AparecerTela(pc);
    }//GEN-LAST:event_btnProcurarClienteActionPerformed

    private void radioCadastradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioCadastradoActionPerformed
        btnProcurarCliente.setEnabled(true);
        txtCliente.setEditable(false);
    }//GEN-LAST:event_radioCadastradoActionPerformed

    private void radioNaoCadastradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNaoCadastradoActionPerformed
        btnProcurarCliente.setEnabled(false);
        txtCliente.setEditable(true);
        txtCliente.requestFocus();
    }//GEN-LAST:event_radioNaoCadastradoActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        ItemCotacao ic = new ItemCotacao(this.getClass().getSimpleName());
        ic.setTitle("Item Cotação de Venda");
        Telas.AparecerTela(ic);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void tableItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableItensMouseClicked
        int row = tableItens.getSelectedRow();
        if (evt.getButton() == 1) {
            if (evt.getClickCount() == 2) {
                ItemCotacao ic = new ItemCotacao(this.getClass().getSimpleName());

                if (tableItens.getValueAt(row, 0).equals("")) {
                    ic.idItemCotacao = 0;
                } else {
                    ic.idItemCotacao = Integer.parseInt(tableItens.getValueAt(row, 0).toString());
                }

                ItemCotacao.txtcodigo.setText(tableItens.getValueAt(row, 2).toString());
                ItemCotacao.txtdesc.setText(tableItens.getValueAt(row, 3).toString());
                ItemCotacao.txtqtd.setText(tableItens.getValueAt(row, 4).toString());
                ItemCotacao.txtvalor.setText(tableItens.getValueAt(row, 5).toString());
                ItemCotacao.txtpedido.setText(tableItens.getValueAt(row, 7).toString());
                ItemCotacao.txtprazo.setText(tableItens.getValueAt(row, 8).toString().replace(" dias", ""));

                Telas.AparecerTela(ic);
            }
        } else if (evt.getButton() == 3) {
            JOptionPane.showMessageDialog(null, "Botão direito");
        }
    }//GEN-LAST:event_tableItensMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente selecionado.");
        } else if (txtVendedor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum vendedor selecionado.");
        } else if (txtRep.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum representante selecionado.");
        } else if (txtCondPag.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhuma condição de pagamento selecionada.");
        } else if (idCotacao == 0) {
            String cotacao = vcd.cotacaoAtual();

            //Criar nova cotação
            vcd.create(cotacao, Dates.CriarDataCurtaDBSemDataExistente(), txtCliente.getText(), radioCadastrado.isSelected(), "Ativo", txtVendedor.getText(), txtRep.getText(), txtCondPag.getText());

            txtCotacao.setText(cotacao);
            txtStatus.setText("Ativo");
            
            //Criar itens da cotação
            for (int i = 0; i < tableItens.getRowCount(); i++) {
                vcid.create(cotacao, tableItens.getValueAt(i, 2).toString(), tableItens.getValueAt(i, 3).toString(), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 4).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 5).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 6).toString()), tableItens.getValueAt(i, 7).toString(), tableItens.getValueAt(i, 8).toString(), radioCadastrado.isSelected());
            }

            //Criar documentos da cotação
            for (int i = 0; i < tableDocs.getRowCount(); i++) {
                vcdd.create(cotacao, tableDocs.getValueAt(i, 2).toString(), tableDocs.getValueAt(i, 3).toString());
            }

            //Criar observações da cotação
            for (int i = 0; i < tableObs.getRowCount(); i++) {
                vcod.create(cotacao, Dates.CriarDataCurtaDBComDataExistente(tableObs.getValueAt(i, 2).toString()), tableObs.getValueAt(i, 3).toString(), tableObs.getValueAt(i, 4).toString());
            }

            if (cotacaoCriada && itensCriados && docsCriados && obsCriadas) {
                JOptionPane.showMessageDialog(null, "Cotação criada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Ops...algo deu errado. Favor entrar em contato com suporte.");
            }

            status();
        } else {
            String cotacao = txtCotacao.getText();

            //Atualizar cotação
            vcd.update(cotacao, txtCliente.getText(), radioCadastrado.isSelected(), txtVendedor.getText(), txtRep.getText(), txtCondPag.getText());

            //Criar itens da cotação que não existiam
            for (int i = 0; i < tableItens.getRowCount(); i++) {
                if (tableItens.getValueAt(i, 0).equals("")) {
                    vcid.create(cotacao, tableItens.getValueAt(i, 2).toString(), tableItens.getValueAt(i, 3).toString(), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 4).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 5).toString()), Valores.TransformarDinheiroEmValorDouble(tableItens.getValueAt(i, 6).toString()), tableItens.getValueAt(i, 7).toString(), tableItens.getValueAt(i, 8).toString(), radioCadastrado.isSelected());
                } else {
                    vcid.update(tableItens.getValueAt(i, 2).toString(), tableItens.getValueAt(i, 3).toString(), Double.parseDouble(Valores.TransformarStringDinheiroEmStringDouble(tableItens.getValueAt(i, 4).toString())), Double.parseDouble(Valores.TransformarStringDinheiroEmStringDouble(tableItens.getValueAt(i, 5).toString())), Double.parseDouble(Valores.TransformarStringDinheiroEmStringDouble(tableItens.getValueAt(i, 6).toString())), tableItens.getValueAt(i, 7).toString(), radioCadastrado.isSelected(), Integer.parseInt(tableItens.getValueAt(i, 0).toString()));
                }
            }

            //Criar documentos da cotação que não existiam
            for (int i = 0; i < tableDocs.getRowCount(); i++) {
                if (tableDocs.getValueAt(i, 0).equals("")) {
                    vcdd.create(cotacao, tableDocs.getValueAt(i, 2).toString(), tableDocs.getValueAt(i, 3).toString());
                }
            }

            //Criar observações da cotação
            for (int i = 0; i < tableObs.getRowCount(); i++) {
                if (tableObs.getValueAt(i, 0).equals("")) {
                    vcod.create(cotacao, Dates.InverterDataCurta(tableObs.getValueAt(i, 2).toString()), tableObs.getValueAt(i, 3).toString(), tableObs.getValueAt(i, 4).toString());
                }
            }

            if (cotacaoAtualizada && itensCriados && docsCriados && obsCriadas) {
                JOptionPane.showMessageDialog(null, "Cotação atualizada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Ops...algo deu errado. Favor entrar em contato com suporte.");
            }

            status();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tableCotacoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCotacoesMouseClicked
        if (evt.getClickCount() == 2) {
            idCotacao = Integer.parseInt(tableCotacoes.getValueAt(tableCotacoes.getSelectedRow(), 0).toString());

            tabCotacoes.setSelectedIndex(1);

            txtCotacao.setText(tableCotacoes.getValueAt(tableCotacoes.getSelectedRow(), 2).toString());

            String cotacao = txtCotacao.getText();
            
            //Pegar dados da Cotação no DB
            lerCotacao(cotacao);

            //Pegar itens da Cotação no DB
            lerItensCotacao(cotacao);

            //Pegar Documentos da Cotação no DB
            lerDocumentosCotacao(cotacao);
            
            //Pegar Observações da Cotação no DB
            lerObsCotacao(cotacao);
            
            txtTotal();
        }
    }//GEN-LAST:event_tableCotacoesMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        AdicionarObs ao = new AdicionarObs(this.getClass().getSimpleName());
        Telas.AparecerTela(ao);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int numTrue = 0;
        for (int i = 0; i < tableObs.getRowCount(); i++) {
            if (tableObs.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
            if (numTrue == 0) {
                JOptionPane.showMessageDialog(null, "Nenhuma observação selecionada.");
            } else {

                int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir a observação selecionada?", "Excluir observação", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    vcod.delete(Integer.parseInt(tableObs.getValueAt(i, 0).toString()));
                }
            }
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void tableObsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableObsMouseClicked
        if (tableObs.getSelectedColumn() == 1) {
            String user = tableObs.getValueAt(tableObs.getSelectedRow(), 3).toString();
            if (!user.equals(Session.nome)) {
                JOptionPane.showMessageDialog(null, "Usuário não é dono da observação.");
                tableObs.setValueAt(false, tableObs.getSelectedRow(), 1);
            }
        }
    }//GEN-LAST:event_tableObsMouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        ProcurarDocumento pd = new ProcurarDocumento(this.getClass().getSimpleName());
        Telas.AparecerTela(pd);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
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
            for (int i = 0; i < tableItens.getRowCount(); i++) {
                if (resp == 0) {
                    if (tableItens.getValueAt(i, 1).equals(true)) {
                        vcid.delete(Integer.parseInt(tableObs.getValueAt(i, 0).toString()));
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
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
            for (int i = 0; i < tableItens.getRowCount(); i++) {
                if (resp == 0) {
                    if (tableItens.getValueAt(i, 1).equals(true)) {
                        vcdd.delete(Integer.parseInt(tableObs.getValueAt(i, 0).toString()), tableObs.getValueAt(i, 3).toString());
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        if (jButton16.getText().equals("Marcar Todos")) {
            for (int i = 0; i < tableItens.getRowCount(); i++) {
                tableItens.setValueAt(true, i, 1);
            }
            jButton16.setText("Desmarcar Todos");
        } else {
            for (int i = 0; i < tableItens.getRowCount(); i++) {
                tableItens.setValueAt(false, i, 1);
            }
            jButton16.setText("Marcar Todos");
        }
    }//GEN-LAST:event_jButton16ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnProcurarCliente;
    public javax.swing.ButtonGroup buttonGroup1;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton10;
    public javax.swing.JButton jButton11;
    public javax.swing.JButton jButton12;
    public javax.swing.JButton jButton13;
    public javax.swing.JButton jButton14;
    public javax.swing.JButton jButton15;
    public javax.swing.JButton jButton16;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton5;
    public javax.swing.JButton jButton6;
    public javax.swing.JButton jButton7;
    public javax.swing.JButton jButton8;
    public javax.swing.JButton jButton9;
    public javax.swing.JComboBox<String> jComboBox1;
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
    public static javax.swing.JRadioButton radioCadastrado;
    public static javax.swing.JRadioButton radioNaoCadastrado;
    public javax.swing.JTabbedPane tabCotacoes;
    public javax.swing.JTabbedPane tabItens;
    public static javax.swing.JTable tableCotacoes;
    public static javax.swing.JTable tableDocs;
    public static javax.swing.JTable tableItens;
    public static javax.swing.JTable tableObs;
    public static javax.swing.JTextField txtCliente;
    public static javax.swing.JTextField txtCondPag;
    public static javax.swing.JTextField txtCotacao;
    public static javax.swing.JTextField txtDataAbertura;
    public javax.swing.JTextField txtPesquisa;
    public static javax.swing.JTextField txtRep;
    public static javax.swing.JTextField txtStatus;
    public static javax.swing.JTextField txtValorTotal;
    public static javax.swing.JTextField txtVendedor;
    // End of variables declaration//GEN-END:variables
}
