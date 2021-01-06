/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.compras;

import Connection.Session;
import DAO.InsumosDAO;
import DAO.InsumosDocDAO;
import DAO.InsumosMovDAO;
import DAO.InsumosObsDAO;
import DAO.TiposInsumoDAO;
import Methods.Dates;
import Methods.Docs;
import Methods.SendEmail;
import Methods.Telas;
import View.Geral.AdicionarObs;
import View.Geral.ProcurarDocumento;
import View.Geral.ProcurarUnidades;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class Insumos extends javax.swing.JInternalFrame {

    //DAOs para salvar
    static InsumosDAO idao = new InsumosDAO();
    InsumosDocDAO idd = new InsumosDocDAO();
    InsumosMovDAO imd = new InsumosMovDAO();
    InsumosObsDAO iod = new InsumosObsDAO();
    static TiposInsumoDAO tid = new TiposInsumoDAO();

    static int idInsumo = 0;

    /**
     * Creates new form Insumos
     */
    public Insumos() {
        initComponents();
        readtableinsumos(cbstatus.getSelectedItem().toString());
        readTipos();
    }

    public static void readtableinsumos(String status) {
        DefaultTableModel modelinsumos = (DefaultTableModel) tableInsumos.getModel();
        modelinsumos.setRowCount(0);

        String pesquisa = txtpesquisa.getText();

        if (pesquisa.length() == 0) {
            switch (status) {
                case "Todos":
                    idao.read().forEach(ib -> {
                        modelinsumos.addRow(new Object[]{
                            ib.getId(),
                            false,
                            ib.getCodigo(),
                            ib.getDescricao(),
                            ib.getStatus()
                        });
                    });
                    break;
                default:
                    idao.readPorStatus(status).forEach(ib -> {
                        modelinsumos.addRow(new Object[]{
                            ib.getId(),
                            false,
                            ib.getCodigo(),
                            ib.getDescricao(),
                            ib.getStatus()
                        });
                    });
                    break;
            }
        } else {
            switch (status) {
                case "Todos":
                    idao.readPesquisa(pesquisa).forEach(ib -> {
                        modelinsumos.addRow(new Object[]{
                            ib.getId(),
                            false,
                            ib.getCodigo(),
                            ib.getDescricao(),
                            ib.getStatus()
                        });
                    });
                    break;
                default:
                    idao.readPorStatusEPesquisa(status, pesquisa).forEach(ib -> {
                        modelinsumos.addRow(new Object[]{
                            ib.getId(),
                            false,
                            ib.getCodigo(),
                            ib.getDescricao(),
                            ib.getStatus()
                        });
                    });
                    break;
            }
        }
    }

    public static void readTipos() {
        tid.read().forEach(tib -> {
            cbtipo.addItem(tib.getNome());
        });
    }

    public static void zeracampos() {
        txtid.setText("");
        txtcodigo.setText("");
        txtdescricao.setText("");
        txtstatus.setText("");
        cbtipo.setSelectedIndex(0);
        txtun.setText("");

        DefaultTableModel modelobs = (DefaultTableModel) tableobs.getModel();
        DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
        DefaultTableModel modelest = (DefaultTableModel) tableestoque.getModel();

        modelobs.setRowCount(0);
        modeldoc.setRowCount(0);
        modelest.setRowCount(0);

        idInsumo = 0;
    }

    public static void travarCampos() {
        if (txtstatus.getText().equals("Desativado")) {
            txtcodigo.setEditable(false);
            txtdescricao.setEditable(false);
            cbtipo.setEnabled(false);
            btnProcurarUnidade.setEnabled(false);
            btnAddObs.setEnabled(false);
        } else {

        }
    }

    public void criarDocumentos() {
        for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
            if (tabledocumentos.getValueAt(i, 0).equals("")) {
                File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                File folder = new File("Q:/MIKE_ERP/ins_arq/" + idInsumo);
                File filecopy = new File(folder + "/" + fileoriginal.getName());

                folder.mkdirs();
                try {
                    Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                } catch (IOException e) {
                    String msg = "Erro ao criar documento em rede.";
                    JOptionPane.showMessageDialog(null, msg);

                    new Thread() {
                        @Override
                        public void run() {
                            SendEmail.EnviarErro2(msg, e);
                        }
                    }.start();
                }

                String descricaoDoc = tabledocumentos.getValueAt(i, 2).toString();
                idd.create(idInsumo, descricaoDoc, filecopy.toString().replace("\\", "\\\\"));
            }
        }
    }

    public void lerInsumo(int idInsumo) {
        idao.click(idInsumo).forEach(ib -> {
            txtid.setText(String.valueOf(ib.getId()));
            txtstatus.setText(ib.getStatus());
            txtcodigo.setText(ib.getCodigo());
            txtdescricao.setText(ib.getDescricao());
            txtun.setText(ib.getUnidade());
            cbtipo.setSelectedItem(ib.getTipo());
        });

        lerObs(idInsumo);

        lerDocs(idInsumo);

        lerMov(idInsumo);
    }

    public void lerObs(int idInsumo) {
        DefaultTableModel model = (DefaultTableModel) tableobs.getModel();
        model.setNumRows(0);

        iod.read(idInsumo).forEach(iob -> {
            model.addRow(new Object[]{
                iob.getId(),
                false,
                Dates.TransformarDataCurtaDoDB(iob.getData()),
                iob.getFuncionario(),
                iob.getObs()
            });
        });
    }

    public void lerDocs(int idInsumo) {
        DefaultTableModel model = (DefaultTableModel) tabledocumentos.getModel();
        model.setNumRows(0);

        idd.readDocs(idInsumo).forEach(idb -> {
            model.addRow(new Object[]{
                idb.getId(),
                false,
                idb.getDescricao(),
                idb.getLocal()
            });
        });
    }

    public void lerMov(int idInsumo) {
        DefaultTableModel model = (DefaultTableModel) tableestoque.getModel();
        model.setNumRows(0);

        imd.read(idInsumo).forEach(imb -> {
            model.addRow(new Object[]{
                Dates.TransformarDataCurtaDoDB(imb.getData()),
                imb.getTipomov(),
                imb.getQtdinicial(),
                imb.getQtdmov(),
                imb.getQtdfinal(),
                imb.getFuncionario()
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

        tabInsumos = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableInsumos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtpesquisa = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        cbstatus = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableobs = new javax.swing.JTable();
        btnAddObs = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabledocumentos = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableestoque = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtcodigo = new javax.swing.JTextField();
        txtdescricao = new javax.swing.JTextField();
        txtstatus = new javax.swing.JTextField();
        cbtipo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtun = new javax.swing.JTextField();
        btnProcurarUnidade = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Cadastro de Insumos");

        tabInsumos.setName("tabInsumos"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableInsumos.setAutoCreateRowSorter(true);
        tableInsumos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Código", "Descrição", "Status"
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
        tableInsumos.setName("tableInsumos"); // NOI18N
        tableInsumos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableInsumosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableInsumos);
        if (tableInsumos.getColumnModel().getColumnCount() > 0) {
            tableInsumos.getColumnModel().getColumn(0).setMinWidth(0);
            tableInsumos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableInsumos.getColumnModel().getColumn(0).setMaxWidth(0);
            tableInsumos.getColumnModel().getColumn(1).setMinWidth(30);
            tableInsumos.getColumnModel().getColumn(1).setPreferredWidth(30);
            tableInsumos.getColumnModel().getColumn(1).setMaxWidth(30);
            tableInsumos.getColumnModel().getColumn(2).setMinWidth(200);
            tableInsumos.getColumnModel().getColumn(2).setPreferredWidth(200);
            tableInsumos.getColumnModel().getColumn(2).setMaxWidth(200);
            tableInsumos.getColumnModel().getColumn(4).setMinWidth(150);
            tableInsumos.getColumnModel().getColumn(4).setPreferredWidth(150);
            tableInsumos.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel3.setName("jPanel3"); // NOI18N

        txtpesquisa.setName("txtpesquisa"); // NOI18N
        txtpesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtpesquisa)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtpesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));
        jPanel4.setName("jPanel4"); // NOI18N

        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Desativado", "Todos" }));
        cbstatus.setName("cbstatus"); // NOI18N
        cbstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbstatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbstatus, javax.swing.GroupLayout.Alignment.TRAILING, 0, 169, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jButton7.setText("Novo");
        jButton7.setName("jButton7"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton7)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap())
        );

        tabInsumos.addTab("Insumos", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setName("jPanel2"); // NOI18N

        jTabbedPane2.setName("jTabbedPane2"); // NOI18N

        jPanel5.setName("jPanel5"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tableobs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Data", "Funcionário", "Observação"
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
        tableobs.setName("tableobs"); // NOI18N
        jScrollPane2.setViewportView(tableobs);
        if (tableobs.getColumnModel().getColumnCount() > 0) {
            tableobs.getColumnModel().getColumn(0).setMinWidth(0);
            tableobs.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableobs.getColumnModel().getColumn(0).setMaxWidth(0);
            tableobs.getColumnModel().getColumn(1).setMinWidth(35);
            tableobs.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableobs.getColumnModel().getColumn(1).setMaxWidth(35);
            tableobs.getColumnModel().getColumn(2).setMinWidth(75);
            tableobs.getColumnModel().getColumn(2).setPreferredWidth(75);
            tableobs.getColumnModel().getColumn(2).setMaxWidth(75);
            tableobs.getColumnModel().getColumn(3).setMinWidth(200);
            tableobs.getColumnModel().getColumn(3).setPreferredWidth(200);
            tableobs.getColumnModel().getColumn(3).setMaxWidth(200);
        }

        btnAddObs.setText("Adicionar Observação");
        btnAddObs.setName("btnAddObs"); // NOI18N
        btnAddObs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddObsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddObs)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddObs)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Observações", jPanel5);

        jPanel6.setName("jPanel6"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tabledocumentos.setModel(new javax.swing.table.DefaultTableModel(
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
        tabledocumentos.setName("tabledocumentos"); // NOI18N
        tabledocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabledocumentosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabledocumentos);
        if (tabledocumentos.getColumnModel().getColumnCount() > 0) {
            tabledocumentos.getColumnModel().getColumn(0).setMinWidth(0);
            tabledocumentos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabledocumentos.getColumnModel().getColumn(0).setMaxWidth(0);
            tabledocumentos.getColumnModel().getColumn(1).setMinWidth(30);
            tabledocumentos.getColumnModel().getColumn(1).setPreferredWidth(30);
            tabledocumentos.getColumnModel().getColumn(1).setMaxWidth(30);
            tabledocumentos.getColumnModel().getColumn(4).setMinWidth(0);
            tabledocumentos.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabledocumentos.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jButton4.setText("Adicionar Documento");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Excluir Documento");
        jButton5.setName("jButton5"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Documentos", jPanel6);

        jPanel8.setName("jPanel8"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        tableestoque.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Tipo de Movimentação", "Qtd Inicial", "Qtd Movimentada", "Qtd Final", "Funcionário"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableestoque.setName("tableestoque"); // NOI18N
        jScrollPane4.setViewportView(tableestoque);

        jButton6.setText("Lançar Contagem Manual");
        jButton6.setName("jButton6"); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton6)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Estoque", jPanel8);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Insumo"));
        jPanel7.setName("jPanel7"); // NOI18N

        jLabel1.setText("Código");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Descrição");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("Status");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("Tipo");
        jLabel4.setName("jLabel4"); // NOI18N

        txtcodigo.setName("txtcodigo"); // NOI18N

        txtdescricao.setName("txtdescricao"); // NOI18N

        txtstatus.setEditable(false);
        txtstatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtstatus.setName("txtstatus"); // NOI18N

        cbtipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cbtipo.setName("cbtipo"); // NOI18N
        cbtipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbtipoActionPerformed(evt);
            }
        });

        jLabel5.setText("ID");
        jLabel5.setName("jLabel5"); // NOI18N

        txtid.setEditable(false);
        txtid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtid.setName("txtid"); // NOI18N

        jLabel6.setText("UN");
        jLabel6.setName("jLabel6"); // NOI18N

        txtun.setEditable(false);
        txtun.setName("txtun"); // NOI18N

        btnProcurarUnidade.setText("Procurar Unidade");
        btnProcurarUnidade.setName("btnProcurarUnidade"); // NOI18N
        btnProcurarUnidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcurarUnidadeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtun, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProcurarUnidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtdescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProcurarUnidade))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Salvar");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Novo");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Desativar Insumo");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane2)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        tabInsumos.addTab("Detalhes do Insumo", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabInsumos)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabInsumos)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbtipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtipoActionPerformed

    }//GEN-LAST:event_cbtipoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtcodigo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um código.");
            txtcodigo.requestFocus();
        } else if (txtdescricao.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite uma descrição.");
            txtdescricao.requestFocus();
        } else if (txtun.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Coloque uma unidade.");
        } else if (cbtipo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Escolha um tipo de insumo.");
            cbtipo.showPopup();
        } else {//Se tudo estiver correto.
            String codigo = txtcodigo.getText();
            String descricao = txtdescricao.getText();
            String unidade = txtun.getText();
            String tipo = cbtipo.getSelectedItem().toString();
            String dataCriacao = Dates.CriarDataCompletaParaDB();
            if (idInsumo == 0) {//Se for um item novo
////////////////Criar Insumo
                double estoque = Double.parseDouble(JOptionPane.showInputDialog(null, "Qual o estoque inicial do Insumo?", "Estoque Inicial", JOptionPane.YES_NO_OPTION));

                idao.create(codigo, descricao, unidade, tipo, estoque, dataCriacao);

                idInsumo = idao.idCreated(descricao);

                txtid.setText(String.valueOf(idInsumo));

                idao.click(idInsumo).forEach(ib -> {
                    txtstatus.setText(ib.getStatus());
                });

////////////////Criar Documentos do Insumo
                criarDocumentos();

////////////////Criar Observação do Insumo
                if (tableobs.getRowCount() > 0) {
                    for (int i = 0; i < tableobs.getRowCount(); i++) {
                        String data = Dates.CriarDataCurtaDBComDataExistente(tableobs.getValueAt(i, 2).toString());
                        String funcionario = tableobs.getValueAt(i, 3).toString();
                        String obs = tableobs.getValueAt(i, 4).toString();
                        iod.create(idInsumo, data, funcionario, obs);
                    }
                }

////////////////Criar Movimentação do Insumo
                try {
                    imd.create(idInsumo, 0, estoque, estoque, Dates.CriarDataCurtaDBSemDataExistente(), "Criação", Session.nome);
                } catch (SQLException e) {
                    String msg = "Erro ao criar movimentação do Insumo.";
                    JOptionPane.showMessageDialog(null, msg);

                    new Thread() {
                        @Override
                        public void run() {
                            SendEmail.EnviarErro2(msg, e);
                        }
                    }.start();
                }

                JOptionPane.showMessageDialog(null, "Criado com sucesso.");
            } else {//Se for um item já cadastrado
////////////////Atualizar Insumo
                idao.update(codigo, descricao, tipo, idInsumo);

////////////////Criar Documentos do Insumo
                if (tabledocumentos.getRowCount() > 0) {
                    for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
                        if (tabledocumentos.getValueAt(i, 0).equals("")) {
                            idd.create(idInsumo, descricao, tipo);
                        }
                    }
                }

////////////////Criar Observação do Insumo
                criarDocumentos();

                JOptionPane.showMessageDialog(null, "Atualizado com sucesso.");
            }
            lerInsumo(idInsumo);
            readtableinsumos(cbstatus.getSelectedItem().toString());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAddObsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddObsActionPerformed
        AdicionarObs ao = new AdicionarObs("Insumos");
        Telas.AparecerTela(ao);
    }//GEN-LAST:event_btnAddObsActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (txtid.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione um insumo.");
            tabInsumos.setSelectedIndex(0);
        } else if (idInsumo == 0) {
            JOptionPane.showMessageDialog(null, "Selecione ou salve um Insumo primeiro.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja desativar o insumo " + txtcodigo.getText() + "?", "Desativar Insumo", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                idao.desativar(idInsumo);

                lerInsumo(idInsumo);
                readtableinsumos(cbstatus.getSelectedItem().toString());
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        ProcurarDocumento pd = new ProcurarDocumento("Insumos");
        Telas.AparecerTela(pd);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnProcurarUnidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurarUnidadeActionPerformed
        ProcurarUnidades pu = new ProcurarUnidades(this.getClass().getSimpleName());
        Telas.AparecerTela(pu);
    }//GEN-LAST:event_btnProcurarUnidadeActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int resp = JOptionPane.showConfirmDialog(null, "Deseja criar um novo insumo?", "Criar novo", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            zeracampos();
            tabInsumos.setSelectedIndex(1);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tableInsumosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInsumosMouseClicked
        if (evt.getClickCount() == 2) {
            idInsumo = Integer.parseInt(tableInsumos.getValueAt(tableInsumos.getSelectedRow(), 0).toString());

            tabInsumos.setSelectedIndex(1);

            lerInsumo(idInsumo);
        }
    }//GEN-LAST:event_tableInsumosMouseClicked

    private void tabledocumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabledocumentosMouseClicked
        if (evt.getClickCount() == 2) {
            Docs.open(tabledocumentos.getValueAt(tabledocumentos.getSelectedRow(), 3).toString());
        }
    }//GEN-LAST:event_tabledocumentosMouseClicked

    private void cbstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbstatusActionPerformed
        readtableinsumos(cbstatus.getSelectedItem().toString());
    }//GEN-LAST:event_cbstatusActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (idInsumo == 0) {
            JOptionPane.showMessageDialog(null, "Selecione um insumo antes.");
        } else {
            double qtd, estoqueAtual;
            estoqueAtual = idao.readEstoque(idInsumo);
            try {
                qtd = Double.parseDouble(JOptionPane.showInputDialog(null, "Qual a quantidade movimentada?", "Lançar Contagem", JOptionPane.YES_NO_OPTION));

                double estoqueFinal = estoqueAtual + qtd;

                idao.updateEstoque(estoqueFinal, idInsumo);
                imd.create(idInsumo, estoqueAtual, qtd, estoqueFinal, Dates.CriarDataCurtaDBSemDataExistente(), "Contagem Manual", Session.nome);

                lerInsumo(idInsumo);
            } catch (Exception e) {
                String msg = "Erro.\n" + e;
                JOptionPane.showMessageDialog(null, msg);

                new Thread() {
                    @Override
                    public void run() {
                        SendEmail.EnviarErro2(msg, e);
                    }
                }.start();
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtpesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpesquisaKeyReleased
        readtableinsumos(cbstatus.getSelectedItem().toString());
    }//GEN-LAST:event_txtpesquisaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAddObs;
    public static javax.swing.JButton btnProcurarUnidade;
    public javax.swing.JComboBox<String> cbstatus;
    public static javax.swing.JComboBox<String> cbtipo;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton5;
    public javax.swing.JButton jButton6;
    public javax.swing.JButton jButton7;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
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
    public javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JTabbedPane jTabbedPane2;
    public static javax.swing.JTabbedPane tabInsumos;
    public static javax.swing.JTable tableInsumos;
    public static javax.swing.JTable tabledocumentos;
    public static javax.swing.JTable tableestoque;
    public static javax.swing.JTable tableobs;
    public static javax.swing.JTextField txtcodigo;
    public static javax.swing.JTextField txtdescricao;
    public static javax.swing.JTextField txtid;
    public static javax.swing.JTextField txtpesquisa;
    public static javax.swing.JTextField txtstatus;
    public static javax.swing.JTextField txtun;
    // End of variables declaration//GEN-END:variables
}
