/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.TI;

import Connection.Session;
import DAO.TicketsDAO;
import DAO.UsuariosDAO;
import Methods.Dates;
import Methods.SendEmail;
import View.TelaPrincipal;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class Tickets extends javax.swing.JInternalFrame {

    TicketsDAO td = new TicketsDAO();
    UsuariosDAO ud = new UsuariosDAO();

    static int idTicket;

    /**
     * Creates new form Tickets
     */
    public Tickets() {
        initComponents();
        idTicket = 0;
        lerTickets();
        txtSolicitante.setText(Session.nome);
        tabs();
    }

    public static void tabs() {
        if (Session.nivel.equals("TI")) {
            txtResposta.setEditable(true);
        } else {
            txtResposta.setEditable(false);
        }
    }

    public void lerTickets() {
        DefaultTableModel model = (DefaultTableModel) tableTickets.getModel();
        model.setNumRows(0);

        String status = cbStatus.getSelectedItem().toString();
        String pesquisa = txtPesquisa.getText();

        if (pesquisa.equals("")) {
            switch (status) {
                case "Todos":
                    td.readTodos().forEach(tb -> {
                        model.addRow(new Object[]{
                            tb.getId(),
                            false,
                            tb.getTicket(),
                            tb.getAssunto(),
                            tb.getSolicitante(),
                            tb.getNivel(),
                            tb.getStatus()
                        });
                    });
                    break;
                default:
                    td.readStatus(status).forEach(tb -> {
                        model.addRow(new Object[]{
                            tb.getId(),
                            false,
                            tb.getTicket(),
                            tb.getAssunto(),
                            tb.getSolicitante(),
                            tb.getNivel(),
                            tb.getStatus()
                        });
                    });
                    break;
            }
        } else {
            switch (status) {
                case "Todos":
                    td.readTodosPesquisa(pesquisa).forEach(tb -> {
                        model.addRow(new Object[]{
                            tb.getId(),
                            false,
                            tb.getTicket(),
                            tb.getAssunto(),
                            tb.getSolicitante(),
                            tb.getNivel(),
                            tb.getStatus()
                        });
                    });
                    break;
                default:
                    td.readStatusPesquisa(status, pesquisa).forEach(tb -> {
                        model.addRow(new Object[]{
                            tb.getId(),
                            false,
                            tb.getTicket(),
                            tb.getAssunto(),
                            tb.getSolicitante(),
                            tb.getNivel(),
                            tb.getStatus()
                        });
                    });
                    break;
            }
        }
    }

    public void lerTicket(int idTicket) {
        td.readTicket(idTicket).forEach(tb -> {
            txtNumTicket.setText(tb.getTicket());
            txtAbertura.setText(Dates.TransformarDataCurtaDoDB(tb.getDataAbertura()));
            if (tb.getDataEncerramento() != null) {
                txtEncerramento.setText(Dates.TransformarDataCurtaDoDB(tb.getDataEncerramento()));
            }
            txtStatus.setText(tb.getStatus());
            txtSolicitante.setText(tb.getSolicitante());
            txtAssunto.setText(tb.getAssunto());
            cbNivel.setSelectedIndex(tb.getNivel());
            txtDescricao.setText(tb.getDescricao());
            txtResposta.setText(tb.getResposta());
        });

        camposPorStatus(txtStatus.getText());
    }

    public void zerarCampos() {
        idTicket = 0;

        txtNumTicket.setText("");
        txtAbertura.setText("");
        txtEncerramento.setText("");
        txtStatus.setText("");
        txtAssunto.setText("");
        txtDescricao.setText("");
        cbNivel.setSelectedIndex(0);

        camposPorStatus(txtStatus.getText());
    }

    public void camposPorStatus(String status) {
        if (Session.nivel.equals("TI")) {
            switch (status) {
                case "Fazendo":
                    txtAssunto.setEditable(false);
                    txtDescricao.setEditable(false);
                    cbNivel.setEnabled(false);
                    btnAlterarStatus.setEnabled(true);
                    btnSalvar.setEnabled(true);
                    txtResposta.setEditable(true);
                    break;
                case "Fechado":
                    txtAssunto.setEditable(false);
                    txtDescricao.setEditable(false);
                    cbNivel.setEnabled(false);
                    btnAlterarStatus.setEnabled(false);
                    btnSalvar.setEnabled(false);
                    txtResposta.setEditable(false);
                    break;
                default:
                    txtAssunto.setEditable(true);
                    txtDescricao.setEditable(true);
                    cbNivel.setEnabled(true);
                    btnAlterarStatus.setEnabled(true);
                    btnSalvar.setEnabled(true);
                    txtResposta.setEditable(true);
                    break;
            }
        } else {
            switch (status) {
                case "Fazendo":
                    txtAssunto.setEditable(false);
                    txtDescricao.setEditable(false);
                    cbNivel.setEnabled(false);
                    btnAlterarStatus.setEnabled(true);
                    btnSalvar.setEnabled(false);
                    break;
                case "Fechado":
                    txtAssunto.setEditable(false);
                    txtDescricao.setEditable(false);
                    cbNivel.setEnabled(false);
                    btnAlterarStatus.setEnabled(false);
                    btnSalvar.setEnabled(false);
                    break;
                default:
                    txtAssunto.setEditable(true);
                    txtDescricao.setEditable(true);
                    cbNivel.setEnabled(true);
                    btnAlterarStatus.setEnabled(true);
                    btnSalvar.setEnabled(true);
                    break;
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

        jPanel2 = new javax.swing.JPanel();
        tabTickets = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTickets = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        cbStatus = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNumTicket = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        txtAbertura = new javax.swing.JTextField();
        txtEncerramento = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        btnAlterarStatus = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        tabTicket = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        txtSolicitante = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtAssunto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        cbNivel = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtResposta = new javax.swing.JTextArea();

        setClosable(true);
        setTitle("Tickets");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setName("jPanel2"); // NOI18N

        tabTickets.setName("tabTickets"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableTickets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Ticket", "Assunto", "Solicitante", "Nível", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableTickets.setName("tableTickets"); // NOI18N
        tableTickets.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTicketsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableTickets);
        if (tableTickets.getColumnModel().getColumnCount() > 0) {
            tableTickets.getColumnModel().getColumn(0).setMinWidth(0);
            tableTickets.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableTickets.getColumnModel().getColumn(0).setMaxWidth(0);
            tableTickets.getColumnModel().getColumn(1).setMinWidth(35);
            tableTickets.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableTickets.getColumnModel().getColumn(1).setMaxWidth(35);
            tableTickets.getColumnModel().getColumn(2).setMinWidth(80);
            tableTickets.getColumnModel().getColumn(2).setPreferredWidth(80);
            tableTickets.getColumnModel().getColumn(2).setMaxWidth(80);
            tableTickets.getColumnModel().getColumn(5).setMinWidth(100);
            tableTickets.getColumnModel().getColumn(5).setPreferredWidth(100);
            tableTickets.getColumnModel().getColumn(5).setMaxWidth(100);
            tableTickets.getColumnModel().getColumn(6).setMinWidth(120);
            tableTickets.getColumnModel().getColumn(6).setPreferredWidth(120);
            tableTickets.getColumnModel().getColumn(6).setMaxWidth(120);
        }

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));
        jPanel4.setName("jPanel4"); // NOI18N

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pendente", "Fazendo", "Fechado", "Todos" }));
        cbStatus.setName("cbStatus"); // NOI18N
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbStatus, 0, 192, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel5.setName("jPanel5"); // NOI18N

        txtPesquisa.setName("txtPesquisa"); // NOI18N
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 407, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabTickets.addTab("Tickets", jPanel1);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setName("jPanel3"); // NOI18N

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));
        jPanel7.setName("jPanel7"); // NOI18N

        jLabel1.setText("Ticket");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Data Abertura");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("Data Encerramento");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("Status");
        jLabel4.setName("jLabel4"); // NOI18N

        txtNumTicket.setEditable(false);
        txtNumTicket.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumTicket.setName("txtNumTicket"); // NOI18N

        txtStatus.setEditable(false);
        txtStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStatus.setName("txtStatus"); // NOI18N

        txtAbertura.setEditable(false);
        txtAbertura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAbertura.setName("txtAbertura"); // NOI18N

        txtEncerramento.setEditable(false);
        txtEncerramento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEncerramento.setName("txtEncerramento"); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEncerramento, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(jLabel2)
                .addComponent(jLabel3)
                .addComponent(jLabel4)
                .addComponent(txtNumTicket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtEncerramento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnSalvar.setText("Salvar");
        btnSalvar.setName("btnSalvar"); // NOI18N
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnAlterarStatus.setText("Alterar Status");
        btnAlterarStatus.setName("btnAlterarStatus"); // NOI18N
        btnAlterarStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarStatusActionPerformed(evt);
            }
        });

        jButton3.setText("Novo");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tabTicket.setName("tabTicket"); // NOI18N

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel8.setName("jPanel8"); // NOI18N

        txtSolicitante.setEditable(false);
        txtSolicitante.setName("txtSolicitante"); // NOI18N

        jLabel5.setText("Solicitante");
        jLabel5.setName("jLabel5"); // NOI18N

        txtAssunto.setName("txtAssunto"); // NOI18N

        jLabel6.setText("Assunto");
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText("Descrição");
        jLabel7.setName("jLabel7"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        txtDescricao.setColumns(20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setRows(10);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setName("txtDescricao"); // NOI18N
        jScrollPane2.setViewportView(txtDescricao);

        jLabel8.setText("Nível");
        jLabel8.setName("jLabel8"); // NOI18N

        cbNivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "1 (Muito Urgente)", "2 (Urgente)", "3 (Normal)", "4 (Não Urgente)", "5 (Nada Urgente)" }));
        cbNivel.setName("cbNivel"); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAssunto, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 397, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(cbNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabTicket.addTab("Solicitação", jPanel8);

        jPanel6.setName("jPanel6"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        txtResposta.setColumns(20);
        txtResposta.setLineWrap(true);
        txtResposta.setRows(5);
        txtResposta.setWrapStyleWord(true);
        txtResposta.setName("txtResposta"); // NOI18N
        jScrollPane3.setViewportView(txtResposta);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabTicket.addTab("Resposta", jPanel6);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAlterarStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(tabTicket, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabTicket)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnAlterarStatus)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        tabTickets.addTab("Ticket - Detalhes", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabTickets)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabTickets, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        lerTickets();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void tableTicketsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTicketsMouseClicked
        if (evt.getClickCount() == 2) {
            tabTickets.setSelectedIndex(1);

            idTicket = Integer.parseInt(tableTickets.getValueAt(tableTickets.getSelectedRow(), 0).toString());

            lerTicket(idTicket);
        }
    }//GEN-LAST:event_tableTicketsMouseClicked

    private void btnAlterarStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarStatusActionPerformed
        if (Session.nivel.equals("TI")) {
            if (idTicket == 0) {
                JOptionPane.showMessageDialog(null, "Selecione um Ticket primeiro.");
            } else {
                String[] options = new String[2];
                options[0] = "Fazendo";
                options[1] = "Fechado";

                int escolha = JOptionPane.showInternalOptionDialog(TelaPrincipal.jDesktopPane1, "Qual o novo Status?", "Escolher Status", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, iconable);

                if (escolha >= 0) {
                    if (escolha == 0) {
                        td.ticketFazendo(idTicket);
                    } else {
                        td.encerrarTicket(idTicket, Dates.CriarDataCurtaDBSemDataExistente());
                    }

                    String email = ud.getEmail(txtSolicitante.getText());

                    new Thread() {
                        @Override
                        public void run() {
                            SendEmail.EnviarAviso(email, "Alteração de Status de Ticket", "Seu Ticket " + txtNumTicket.getText() + " teve o status alterado para " + options[escolha] + "\nAssunto:\n" + txtAssunto.getText() + "\nDescrição: \n" + txtDescricao.getText() + "\n\nResposta do Desenvolvedor:\n" + txtResposta.getText());
                        }
                    }.start();

                    lerTicket(idTicket);

                    lerTickets();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Sem acesso.");
        }
    }//GEN-LAST:event_btnAlterarStatusActionPerformed

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        lerTickets();
    }//GEN-LAST:event_cbStatusActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (cbNivel.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Escolha um nível primeiro.");
            cbNivel.showPopup();
        } else {
            if (idTicket == 0) {
                String ticket = td.ticketAtual();

                td.create(ticket, Dates.CriarDataCurtaDBSemDataExistente(), txtSolicitante.getText(), txtAssunto.getText(), cbNivel.getSelectedIndex(), txtDescricao.getText());

                idTicket = td.ticketId(ticket);

                new Thread() {
                    @Override
                    public void run() {
                        SendEmail.EnviarAviso("financeiro@speedcut.com.br", "Novo Ticket Criado - " + txtAssunto.getText(), "Ticket " + ticket + " criado por " + Session.nome + "\nAssunto:\n" + txtAssunto.getText() + "\nDescrição:\n" + txtDescricao.getText());
                    }
                }.start();
            } else {
                td.updateTicket(txtAssunto.getText(), cbNivel.getSelectedIndex(), txtDescricao.getText(), txtResposta.getText(), idTicket);
            }

            lerTicket(idTicket);

            lerTickets();
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        zerarCampos();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAlterarStatus;
    public javax.swing.JButton btnSalvar;
    public javax.swing.JComboBox<String> cbNivel;
    public static javax.swing.JComboBox<String> cbStatus;
    public javax.swing.JButton jButton3;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JPanel jPanel8;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTabbedPane tabTicket;
    public javax.swing.JTabbedPane tabTickets;
    public javax.swing.JTable tableTickets;
    public javax.swing.JTextField txtAbertura;
    public javax.swing.JTextField txtAssunto;
    public javax.swing.JTextArea txtDescricao;
    public javax.swing.JTextField txtEncerramento;
    public static javax.swing.JTextField txtNumTicket;
    public static javax.swing.JTextField txtPesquisa;
    public static javax.swing.JTextArea txtResposta;
    public javax.swing.JTextField txtSolicitante;
    public static javax.swing.JTextField txtStatus;
    // End of variables declaration//GEN-END:variables
}
