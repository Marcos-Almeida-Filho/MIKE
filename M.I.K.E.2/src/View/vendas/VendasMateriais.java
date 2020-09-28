/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.vendas;

import Bean.AltBean;
import Bean.VendasMateriaisBean;
import Bean.VendasMateriaisCodigoClienteBean;
import Bean.VendasMateriaisDocBean;
import Bean.VendasMateriaisMovBean;
import Bean.VendasMateriaisObsBean;
import Connection.Session;
import DAO.AltDAO;
import DAO.VendasMateriaisCodigoClienteDAO;
import DAO.VendasMateriaisDAO;
import DAO.VendasMateriaisDocDAO;
import DAO.VendasMateriaisMovDAO;
import DAO.VendasMateriaisObsDAO;
import Methods.Dates;
import Methods.SendEmail;
import Methods.Telas;
import ValoresOriginais.VendasMateriaisValoresOriginais;
import View.Geral.AdicionarObs;
import View.Geral.HistoricoAlteracao;
import View.Geral.ProcurarDocumento;
import View.Geral.ProcurarLocal;
import View.servicos.DocumentosOrcamentoServico;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class VendasMateriais extends javax.swing.JInternalFrame {

    /**
     * Creates new form Materiais
     */
    static VendasMateriaisDAO vmd = new VendasMateriaisDAO();
    VendasMateriaisDocDAO vmdd = new VendasMateriaisDocDAO();
    VendasMateriaisObsDAO vmod = new VendasMateriaisObsDAO();
    VendasMateriaisCodigoClienteDAO vmccd = new VendasMateriaisCodigoClienteDAO();
    VendasMateriaisMovDAO vmmd = new VendasMateriaisMovDAO();
    AltDAO ad = new AltDAO();

    VendasMateriaisBean vmb = new VendasMateriaisBean();
    VendasMateriaisDocBean vmdb = new VendasMateriaisDocBean();
    VendasMateriaisObsBean vmob = new VendasMateriaisObsBean();
    VendasMateriaisCodigoClienteBean vmccb = new VendasMateriaisCodigoClienteBean();
    VendasMateriaisMovBean vmmb = new VendasMateriaisMovBean();
    AltBean ab = new AltBean();

    public static int id = 0;

    public VendasMateriais() {
        initComponents();
        vanish();
        readMateriais();
    }

    private void vanish() {
        btnExcluirObs.setVisible(false);
    }

    private void salvarMaterial() {
        String codigo = txtCodigo.getText();
        String descricao = txtDesc.getText();
        Double estoque = 0.0;
        String local = txtLocal.getText();
        String tipo = this.getClass().getSimpleName();
        String user = Session.nome;

        if (id == 0) {//Se o produto não tem ID, criar um novo
            //Criar um novo procuto
            //vmd.create(codigo, descricao, estoque, "Ativo", local);

            //Recuperar seu ID
            int created = vmd.readcreated();
            id = created;

            //Criar Movimentação Inicial
            vmmd.create(created, 0, 0, 0, "Criação do Material", Dates.CriarDataCurtaDBSemDataExistente(), Session.nome);

            //Criar Alteração Inicial
            String data = Dates.CriarDataCompletaParaDB();
            ad.create(id, tipo, data, user, "Criação do Registro", "", "");

            //Criar Observações, caso existam
            for (int i = 0; i < tableObs.getRowCount(); i++) {
                vmod.create(created, Dates.CriarDataCurtaDBComDataExistente(tableObs.getValueAt(i, 2).toString()), tableObs.getValueAt(i, 3).toString(), tableObs.getValueAt(i, 4).toString());
            }

            //Criar Documentos, caso existam
            for (int i = 0; i < tableDocumentos.getRowCount(); i++) {
                File fileoriginal = new File(tableDocumentos.getValueAt(i, 4).toString());
                File folder = new File("Q:/MIKE_ERP/mat_ven_arq/" + created);
                File filecopy = new File(folder + "/" + fileoriginal.getName());

                folder.mkdirs();
                try {
                    Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                } catch (IOException ex) {
                    Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");
                    try {
                        SendEmail.EnviarErro(ex.toString());
                        JOptionPane.showMessageDialog(rootPane, "E-mail com erro enviado com sucesso!");
                    } catch (HeadlessException hex) {
                        JOptionPane.showMessageDialog(rootPane, "Erro!\n" + hex);
                    } catch (AWTException | IOException ex1) {
                        Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }

                vmdd.create(created, tableDocumentos.getValueAt(i, 2).toString(), filecopy.toString());
            }
            //Criar Códigos por Cliente, caso existam
            for (int i = 0; i < tableCodigoPorCliente.getRowCount(); i++) {
                vmccd.create(created, tableCodigoPorCliente.getValueAt(i, 2).toString(), tableCodigoPorCliente.getValueAt(i, 3).toString(), tableCodigoPorCliente.getValueAt(i, 4).toString());
            }
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } else {//O produto tem ID, atualizar produto
            //Atualizar produto
//            vmd.update(codigo, descricao, local, id);

            //Criar Observações, caso existam
            for (int i = 0; i < tableObs.getRowCount(); i++) {
                if (tableObs.getValueAt(i, 0).equals("")) {
                    vmod.create(id, Dates.CriarDataCurtaDBComDataExistente(tableObs.getValueAt(i, 2).toString()), tableObs.getValueAt(i, 3).toString(), tableObs.getValueAt(i, 4).toString());
                }
            }

            //Criar Documentos, caso existam
            for (int i = 0; i < tableDocumentos.getRowCount(); i++) {
                if (tableDocumentos.getValueAt(i, 0).equals("")) {
                    File fileoriginal = new File(tableDocumentos.getValueAt(i, 4).toString());
                    File folder = new File("Q:/MIKE_ERP/mat_ven_arq/" + id);
                    File filecopy = new File(folder + "/" + fileoriginal.getName());

                    folder.mkdirs();
                    try {
                        Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                    } catch (IOException ex) {
                        Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");
                        try {
                            SendEmail.EnviarErro(ex.toString());
                            JOptionPane.showMessageDialog(rootPane, "E-mail com erro enviado com sucesso!");
                        } catch (HeadlessException hex) {
                            JOptionPane.showMessageDialog(rootPane, "Erro!\n" + hex);
                        } catch (AWTException | IOException ex1) {
                            Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }

                    vmdd.create(id, tableDocumentos.getValueAt(i, 2).toString(), filecopy.toString());
                }
            }
            //Criar Códigos por Cliente, caso existam
            for (int i = 0; i < tableCodigoPorCliente.getRowCount(); i++) {
                if (tableCodigoPorCliente.getValueAt(i, 0).equals("")) {
                    vmccd.create(id, tableCodigoPorCliente.getValueAt(i, 2).toString(), tableCodigoPorCliente.getValueAt(i, 3).toString(), tableCodigoPorCliente.getValueAt(i, 4).toString());
                }
            }
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");

            String data = Dates.CriarDataCompletaParaDB();

            if (!VendasMateriaisValoresOriginais.codigo.equals(txtCodigo.getText())) {
                ad.create(id, tipo, data, user, "Código", VendasMateriaisValoresOriginais.codigo, txtCodigo.getText());
            }
            if (!VendasMateriaisValoresOriginais.descricao.equals(txtDesc.getText())) {
                ad.create(id, tipo, data, user, "Descrição", VendasMateriaisValoresOriginais.descricao, txtDesc.getText());
            }
            if (!VendasMateriaisValoresOriginais.local.equals(txtLocal.getText())) {
                ad.create(id, tipo, data, user, "Local de Armazenagem", VendasMateriaisValoresOriginais.local, txtLocal.getText());
            }
            if (VendasMateriaisValoresOriginais.docs != tableDocumentos.getRowCount()) {
                ad.create(id, tipo, data, user, "Número de Documentos", String.valueOf(VendasMateriaisValoresOriginais.docs), String.valueOf(tableDocumentos.getRowCount()));
            }
            if (VendasMateriaisValoresOriginais.codclis != tableCodigoPorCliente.getRowCount()) {
                ad.create(id, tipo, data, user, "Número de Códigos por Cliente", String.valueOf(VendasMateriaisValoresOriginais.codclis), String.valueOf(tableCodigoPorCliente.getRowCount()));
            }
        }
        readMateriais();
    }

    public static void readMateriais() {
        DefaultTableModel modelMat = (DefaultTableModel) tableMateriais.getModel();
        modelMat.setNumRows(0);

        vmd.readtodos().forEach(vmb -> {
            modelMat.addRow(new Object[]{
                vmb.getId(),
                false,
                vmb.getCodigo(),
                vmb.getDescricao()
            });
        });
    }

    public void readClick() {
        vmd.click(id).forEach(vmb -> {
            txtCodigo.setText(vmb.getCodigo());
            txtDesc.setText(vmb.getDescricao());
            txtLocal.setText(vmb.getLocal());
            txtEstoque.setText(String.valueOf(vmb.getEstoque()));
        });

        DefaultTableModel modelObs = (DefaultTableModel) tableObs.getModel();
        modelObs.setNumRows(0);

        vmod.read(id).forEach(vmob -> {
            modelObs.addRow(new Object[]{
                vmob.getId(),
                false,
                Dates.TransformarDataCurtaDoDB(vmob.getData()),
                vmob.getUsuario(),
                vmob.getObs()
            });
        });

        DefaultTableModel modelMov = (DefaultTableModel) tableMov.getModel();
        modelMov.setNumRows(0);

        vmmd.read(id).forEach(vmmb -> {
            modelMov.addRow(new Object[]{
                vmmb.getId(),
                vmmb.getQtdInicial(),
                vmmb.getQtdMovimentada(),
                vmmb.getTipo(),
                vmmb.getSaldo(),
                Dates.TransformarDataCurtaDoDB(vmmb.getData()),
                vmmb.getUsuario()
            });
        });

        DefaultTableModel modelDoc = (DefaultTableModel) tableDocumentos.getModel();
        modelDoc.setNumRows(0);

        vmdd.read(id).forEach(vmdb -> {
            modelDoc.addRow(new Object[]{
                vmdb.getId(),
                false,
                vmdb.getDescricao(),
                vmdb.getLocal(),
                ""
            });
        });

        DefaultTableModel modelCod = (DefaultTableModel) tableCodigoPorCliente.getModel();
        modelCod.setNumRows(0);

        vmccd.read(id).forEach(vmccb -> {
            modelCod.addRow(new Object[]{
                vmccb.getId(),
                false,
                vmccb.getCliente(),
                vmccb.getCodigo(),
                vmccb.getDescricao()
            });
        });

        VendasMateriaisValoresOriginais.codigo = txtCodigo.getText();
        VendasMateriaisValoresOriginais.descricao = txtDesc.getText();
        VendasMateriaisValoresOriginais.local = txtLocal.getText();
        VendasMateriaisValoresOriginais.docs = tableDocumentos.getRowCount();
        VendasMateriaisValoresOriginais.codclis = tableCodigoPorCliente.getRowCount();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabMateriais = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMateriais = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        cbStatus = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        tabInfoMateriais = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableObs = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        btnExcluirObs = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableMov = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        txtLocal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtEstoque = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableDocumentos = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableCodigoPorCliente = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtDesc = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Materiais de Venda");

        tabMateriais.setName("tabMateriais"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableMateriais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Código", "Descrição"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableMateriais.setName("tableMateriais"); // NOI18N
        tableMateriais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMateriaisMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableMateriais);
        if (tableMateriais.getColumnModel().getColumnCount() > 0) {
            tableMateriais.getColumnModel().getColumn(0).setMinWidth(0);
            tableMateriais.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableMateriais.getColumnModel().getColumn(0).setMaxWidth(0);
            tableMateriais.getColumnModel().getColumn(1).setMinWidth(35);
            tableMateriais.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableMateriais.getColumnModel().getColumn(1).setMaxWidth(35);
        }

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel3.setName("jPanel3"); // NOI18N

        txtPesquisa.setName("txtPesquisa"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));
        jPanel4.setName("jPanel4"); // NOI18N

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Desativado", "Todos" }));
        cbStatus.setName("cbStatus"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(cbStatus, 0, 156, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 974, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabMateriais.addTab("Cadastro", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setName("jPanel2"); // NOI18N

        tabInfoMateriais.setName("tabInfoMateriais"); // NOI18N

        jPanel5.setName("jPanel5"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

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
        jScrollPane2.setViewportView(tableObs);
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

        jButton3.setText("Adicionar Observação");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnExcluirObs.setText("Excluir Observação");
        btnExcluirObs.setName("btnExcluirObs"); // NOI18N
        btnExcluirObs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirObsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExcluirObs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 962, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(btnExcluirObs))
                .addContainerGap())
        );

        tabInfoMateriais.addTab("Observações", jPanel5);

        jPanel7.setName("jPanel7"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tableMov.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Qtd Inicial", "Qtd Movimentada", "Tipo de Movimentação", "Saldo", "Data", "Usuário"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableMov.setName("tableMov"); // NOI18N
        jScrollPane3.setViewportView(tableMov);
        if (tableMov.getColumnModel().getColumnCount() > 0) {
            tableMov.getColumnModel().getColumn(0).setMinWidth(0);
            tableMov.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableMov.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jPanel10.setName("jPanel10"); // NOI18N

        jButton9.setText("Procurar Local");
        jButton9.setName("jButton9"); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        txtLocal.setEditable(false);
        txtLocal.setName("txtLocal"); // NOI18N

        jLabel5.setText("Local");
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel4.setText("Estoque");
        jLabel4.setName("jLabel4"); // NOI18N

        txtEstoque.setEditable(false);
        txtEstoque.setName("txtEstoque"); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLocal))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEstoque))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 809, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabInfoMateriais.addTab("Movimentação", jPanel7);

        jPanel8.setName("jPanel8"); // NOI18N

        jButton5.setText("Adicionar Documento");
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Excluir Documento");
        jButton6.setName("jButton6"); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        tableDocumentos.setModel(new javax.swing.table.DefaultTableModel(
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
        tableDocumentos.setName("tableDocumentos"); // NOI18N
        tableDocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDocumentosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableDocumentos);
        if (tableDocumentos.getColumnModel().getColumnCount() > 0) {
            tableDocumentos.getColumnModel().getColumn(0).setMinWidth(0);
            tableDocumentos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableDocumentos.getColumnModel().getColumn(0).setMaxWidth(0);
            tableDocumentos.getColumnModel().getColumn(1).setMinWidth(35);
            tableDocumentos.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableDocumentos.getColumnModel().getColumn(1).setMaxWidth(35);
            tableDocumentos.getColumnModel().getColumn(4).setMinWidth(0);
            tableDocumentos.getColumnModel().getColumn(4).setPreferredWidth(0);
            tableDocumentos.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 962, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        tabInfoMateriais.addTab("Documentos", jPanel8);

        jPanel9.setName("jPanel9"); // NOI18N

        jButton7.setText("Adicionar Código");
        jButton7.setName("jButton7"); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Excluir Código");
        jButton8.setName("jButton8"); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        tableCodigoPorCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Cliente", "Código", "Descrição"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCodigoPorCliente.setName("tableCodigoPorCliente"); // NOI18N
        jScrollPane5.setViewportView(tableCodigoPorCliente);
        if (tableCodigoPorCliente.getColumnModel().getColumnCount() > 0) {
            tableCodigoPorCliente.getColumnModel().getColumn(0).setMinWidth(0);
            tableCodigoPorCliente.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableCodigoPorCliente.getColumnModel().getColumn(0).setMaxWidth(0);
            tableCodigoPorCliente.getColumnModel().getColumn(1).setMinWidth(35);
            tableCodigoPorCliente.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableCodigoPorCliente.getColumnModel().getColumn(1).setMaxWidth(35);
            tableCodigoPorCliente.getColumnModel().getColumn(4).setMinWidth(450);
            tableCodigoPorCliente.getColumnModel().getColumn(4).setPreferredWidth(450);
            tableCodigoPorCliente.getColumnModel().getColumn(4).setMaxWidth(450);
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 962, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addContainerGap())
        );

        tabInfoMateriais.addTab("Código Por Cliente", jPanel9);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));
        jPanel6.setName("jPanel6"); // NOI18N

        jLabel1.setText("Código");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Descrição");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("Status");
        jLabel3.setName("jLabel3"); // NOI18N

        txtStatus.setEditable(false);
        txtStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStatus.setFocusable(false);
        txtStatus.setName("txtStatus"); // NOI18N

        txtCodigo.setName("txtCodigo"); // NOI18N

        txtDesc.setName("txtDesc"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
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

        jButton10.setText("Alterações");
        jButton10.setName("jButton10"); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabInfoMateriais)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton10)
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
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabInfoMateriais)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton10))
                .addContainerGap())
        );

        tabMateriais.addTab("Material", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabMateriais)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabMateriais)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ProcurarDocumento pd = new ProcurarDocumento(this.getClass().getSimpleName());
        Telas.AparecerTela(pd);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int numtrue = 0;
        for (int i = 0; i < tableDocumentos.getRowCount(); i++) {
            if (tableDocumentos.getValueAt(i, 1).equals(true)) {
                numtrue++;
            }
        }
        if (numtrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum documento selecionado.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir o(s) documento(s) selecionado(s)?", "Excluir Documento", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                for (int i = 0; i < tableDocumentos.getRowCount(); i++) {
                    if (tableDocumentos.getValueAt(i, 1).equals(true)) {
                        vmdd.delete(Integer.parseInt(tableDocumentos.getValueAt(i, 0).toString()));
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        AdicionarObs ao = new AdicionarObs(this.getClass().getSimpleName());
        Telas.AparecerTela(ao);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnExcluirObsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirObsActionPerformed
        int numtrue = 0;
        for (int i = 0; i < tableObs.getRowCount(); i++) {
            if (tableObs.getValueAt(i, 1).equals(true)) {
                numtrue++;
            }
        }
        if (numtrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma observação selecionada.");
        } else {
            for (int i = 0; i < tableObs.getRowCount(); i++) {
                if (tableObs.getValueAt(i, 1).equals(true) && !Session.nome.equals(tableObs.getValueAt(i, 2))) {
                    JOptionPane.showMessageDialog(null, "Observação escrita por outro usuário.\nObservação não será excluída.");
                } else {
                    int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir esta observação?", "Excluir Observação", JOptionPane.YES_NO_OPTION);
                    if (resp == 0) {
                        vmod.delete(Integer.parseInt(tableObs.getValueAt(i, 0).toString()));
                    }
                }
            }
        }
    }//GEN-LAST:event_btnExcluirObsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtCodigo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um código.");
            txtCodigo.requestFocus();
        } else if (txtDesc.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite uma descrição.");
            txtDesc.requestFocus();
        } else {
            salvarMaterial();
            readClick();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        ProcurarLocal pl = new ProcurarLocal(this.getClass().getSimpleName());
        Telas.AparecerTela(pl);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        CodigoPorCliente cpc = new CodigoPorCliente();
        Telas.AparecerTela(cpc);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int numtrue = 0;

        for (int i = 0; i < tableCodigoPorCliente.getRowCount(); i++) {
            if (tableCodigoPorCliente.getValueAt(i, 1).equals(true)) {
                numtrue++;
            }
        }

        if (numtrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum código selecionado.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir o(s) código(s) selecionado(s)?", "Excluir Código por Cliente", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                for (int i = 0; i < tableCodigoPorCliente.getRowCount(); i++) {
                    if (tableCodigoPorCliente.getValueAt(i, 1).equals(true)) {
                        vmccd.delete(Integer.parseInt(tableCodigoPorCliente.getValueAt(i, 0).toString()));
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int resp = JOptionPane.showConfirmDialog(null, "Cadastrar novo material de venda?", "Novo Material de Venda", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            //Tirar texto dos Txts
            txtCodigo.setText("");
            txtCodigo.requestFocus();
            txtDesc.setText("");
            txtStatus.setText("");
            txtEstoque.setText("");
            txtLocal.setText("");

            //Tirar linhas das Tables
            DefaultTableModel modelObs = (DefaultTableModel) tableObs.getModel();
            modelObs.setNumRows(0);
            DefaultTableModel modelMov = (DefaultTableModel) tableMov.getModel();
            modelMov.setNumRows(0);
            DefaultTableModel modelDoc = (DefaultTableModel) tableDocumentos.getModel();
            modelDoc.setNumRows(0);
            DefaultTableModel modelCod = (DefaultTableModel) tableCodigoPorCliente.getModel();
            modelCod.setNumRows(0);

            //Zerar ID
            id = 0;
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (id != 0) {
            HistoricoAlteracao ha = new HistoricoAlteracao(this.getClass().getSimpleName());
            Telas.AparecerTela(ha);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void tableMateriaisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMateriaisMouseClicked
        if (evt.getClickCount() == 2) {
            id = Integer.parseInt(tableMateriais.getValueAt(tableMateriais.getSelectedRow(), 0).toString());

            tabMateriais.setSelectedIndex(1);

            readClick();
        }
    }//GEN-LAST:event_tableMateriaisMouseClicked

    private void tableDocumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDocumentosMouseClicked
        if (evt.getClickCount() == 2) {
            Desktop desk = Desktop.getDesktop();
            if (tableDocumentos.getValueAt(tableDocumentos.getSelectedRow(), 3).equals("")) {
                try {
                    desk.open(new File((String) tableDocumentos.getValueAt(tableDocumentos.getSelectedRow(), 4)));
                } catch (IOException ex) {
                    Logger.getLogger(DocumentosOrcamentoServico.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    desk.open(new File((String) tableDocumentos.getValueAt(tableDocumentos.getSelectedRow(), 3)));
                } catch (IOException ex) {
                    Logger.getLogger(DocumentosOrcamentoServico.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_tableDocumentosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnExcluirObs;
    public static javax.swing.JComboBox<String> cbStatus;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton10;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton5;
    public javax.swing.JButton jButton6;
    public javax.swing.JButton jButton7;
    public javax.swing.JButton jButton8;
    public javax.swing.JButton jButton9;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
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
    public javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JTabbedPane tabInfoMateriais;
    public javax.swing.JTabbedPane tabMateriais;
    public static javax.swing.JTable tableCodigoPorCliente;
    public static javax.swing.JTable tableDocumentos;
    public static javax.swing.JTable tableMateriais;
    public static javax.swing.JTable tableMov;
    public static javax.swing.JTable tableObs;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtDesc;
    public javax.swing.JTextField txtEstoque;
    public static javax.swing.JTextField txtLocal;
    public static javax.swing.JTextField txtPesquisa;
    public javax.swing.JTextField txtStatus;
    // End of variables declaration//GEN-END:variables
}
