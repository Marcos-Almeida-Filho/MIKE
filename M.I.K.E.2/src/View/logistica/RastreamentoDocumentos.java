/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.logistica;

import View.Geral.ProcuraXML;
import Bean.RastreamentoDocumentosBean;
import Bean.RastreamentoDocumentosDocBean;
import Connection.Session;
import DAO.RastreamentoDocumentosDAO;
import DAO.RastreamentoDocumentosDocDAO;
import Methods.Dates;
import Methods.SendEmail;
import View.Geral.ProcurarCliente;
import View.Geral.ProcurarFornecedor;
import static View.TelaPrincipal.jDesktopPane1;
import View.servicos.DocumentosOrcamentoServico;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class RastreamentoDocumentos extends javax.swing.JInternalFrame {

    /**
     * Creates new form RastreamentoDocumentos
     */
    //DAOs
    static RastreamentoDocumentosDAO rdd = new RastreamentoDocumentosDAO();
    static RastreamentoDocumentosDocDAO rddd = new RastreamentoDocumentosDocDAO();

    //Beans
    static RastreamentoDocumentosBean rdb = new RastreamentoDocumentosBean();
    static RastreamentoDocumentosDocBean rddb = new RastreamentoDocumentosDocBean();

    public String userentrada;
    public int idcriado;

    public RastreamentoDocumentos() {
        initComponents();
        readtabledocumentos();
        invisible();
    }

    public void salvarrastreamento() {
        //Variáveis
        String datacompleta = Dates.CriarDataCompletaParaDB();
        String datacurta = Dates.CriarDataCurtaDBSemDataExistente();

        if (txtid.getText().equals("")) {
//////////////Criar documento
            //Itens Bean
            rdb.setCliente(radiocliente.isSelected());
            rdb.setFornecedor(radiofornecedor.isSelected());
            rdb.setOutros(radiooutros.isSelected());
            rdb.setEmitente(txtemitente.getText());
            rdb.setNumero(txtnumero.getText());
            rdb.setEmissao(Dates.CriarDataCurtaDBJDateChooser(txtemissao.getDate()));
            rdb.setNf(radionf.isSelected());
            rdb.setConta(radioconta.isSelected());
            rdb.setOutrostipo(radiooutrostipo.isSelected());
            rdb.setOutrosdesc(txtoutros.getText());
            rdb.setData(datacompleta);
            rdb.setCap(checkpagamento.isSelected());
            rdb.setEntradauser(Session.nome);
            rdb.setEntradadata(datacurta);
            rdb.setStatus("Ativo");

            //cliente, fornecedor, outros, emitente, numero, emissao, nf, conta, outrostipo, outrosdesc, data, cap, entradauser, entradadata, status
            rdd.create(rdb);

//////////////Pegar id criado
            rdd.readcreated(datacompleta).forEach(rdb -> {
                idcriado = rdb.getId();
                txtid.setText(String.valueOf(idcriado));
            });

//////////////Criar Entrada no histórico
            int resp2 = JOptionPane.showConfirmDialog(null, "Quem recebeu o documento é o mesmo funcionário que está dando entrada?", "Recebimento de Documento", JOptionPane.YES_NO_OPTION);
            if (resp2 == 0) {
                //Itens Bean Recebimento
                rdb.setRecebimentouser(Session.nome);
                rdb.setRecebimentodata(datacurta);
                rdb.setId(idcriado);

                //recebimentouser = ?, recebimentodata = ? WHERE id = ?
                rdd.createrecebimento(rdb);
            } else {
//                //Solicitar Funcionário
//                JOptionPane.showMessageDialog(null, "Selecione o funcionário responsável pelo recebimento do documento.");
//                ProcurarUser pv = new ProcurarUser("Rastreamento");
//                JDesktopPane desk = this.getDesktopPane();
//                desk.add(pv);
//                Dimension desktopsize = jDesktopPane1.getSize();
//                Dimension jinternalframesize = pv.getSize();
//                pv.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
//                pv.setVisible(true);
//
//                //Itens Bean Recebimento
//                rdb.setId(idcriado);
//                rdb.setRecebimentouser(userentrada);
//                rdb.setEntradadata(Dates.verificadata());
//
//                //iddoc, recebimentouser, recebimentodata
//                rdd.createrecebimento(rdb);
            }

            if (!checkpagamento.isSelected()) {
                rdb.setCapuser("Sem pagamento");
                rdb.setData("0000-00-00");
                rdb.setStatus("Fechado");
                rdb.setId(idcriado);

                //capuser = ?, capdata = ?, status = ? WHERE id = ?
                rdd.createcap(rdb);
            }

//////////////Criar Documentos do Documento
            //Se houver arquivo xml
            if (!txtxml.getText().equals("")) {
                File fileoriginal = new File(txtxml.getText());
                File folder = new File("Q:/MIKE_ERP/ras_doc_arq/" + idcriado);
                File filecopy = new File(folder + "/" + fileoriginal.getName());

                folder.mkdirs();
                try {
                    Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                } catch (IOException ex) {
                    Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");
                    try {
                        SendEmail.EnviarErro(ex.toString());
                    } catch (HeadlessException hex) {
                        JOptionPane.showMessageDialog(rootPane, "Erro!\n" + hex);
                    } catch (AWTException | IOException ex1) {
                        Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
                //Itens Bean
                rdb.setXml(filecopy.toString());
                rdb.setId(idcriado);

                //xml = ? WHERE id = ?
                rdd.updatexml(rdb);
            }
            //Loop para número de itens na tabledocumentos
            for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
                File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                File folder = new File("Q:/MIKE_ERP/ras_doc_arq/" + idcriado);
                File filecopy = new File(folder + "/" + fileoriginal.getName());

                folder.mkdirs();
                try {
                    Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                } catch (IOException ex) {
                    Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Erro ao criar arquivo do Documento!\n" + ex + "\nEnviando e-mail para suporte.");
                    try {
                        SendEmail.EnviarErro(ex.toString());
                    } catch (HeadlessException hex) {
                        JOptionPane.showMessageDialog(null, "Erro!\n" + hex);
                    } catch (AWTException | IOException ex1) {
                        Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
                //Itens Bean
                rddb.setIddoc(idcriado);
                rddb.setDescricao(tabledocumentos.getValueAt(i, 2).toString());
                rddb.setLocal(filecopy.toString());

                //iddoc, descricao, local
                rddd.create(rddb);
            }
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } else {//Se o item já tem ID
            //Variável ID
            int id = Integer.parseInt(txtid.getText());

//////////////Atualizar documento
            //Itens Bean
            rdb.setCliente(radiocliente.isSelected());
            rdb.setFornecedor(radiofornecedor.isSelected());
            rdb.setOutros(radiooutros.isSelected());
            rdb.setEmitente(txtemitente.getText());
            rdb.setNumero(txtnumero.getText());
            rdb.setEmissao(Dates.CriarDataCurtaDBJDateChooser(txtemissao.getDate()));
            rdb.setNf(radionf.isSelected());
            rdb.setConta(radioconta.isSelected());
            rdb.setOutrostipo(radiooutrostipo.isSelected());
            rdb.setOutrosdesc(txtoutros.getText());
            rdb.setCap(checkpagamento.isSelected());
            rdb.setId(id);

            //cliente = ?, fornecedor = ?, outros = ?, emitente = ?, numero = ?, emissao = ?, nf = ?, conta = ?, outrostipo = ?, outrosdesc = ?, cap = ? WHERE id = ?
            rdd.update(rdb);

            if (!checkpagamento.isSelected()) {
                rdb.setCapuser("Sem pagamento");
                rdb.setData("0000-00-00");
                rdb.setStatus("Fechado");
                rdb.setId(Integer.parseInt(txtid.getText()));

                //capuser = ?, capdata = ?, status = ? WHERE id = ?
                rdd.createcap(rdb);
            }

//////////////Criar Documentos do Documento
            //Loop para número de itens na tabledocumentos
            for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
                if (tabledocumentos.getValueAt(i, 0).equals("")) {
                    File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                    File folder = new File("Q:/MIKE_ERP/ras_doc_arq/" + id);
                    File filecopy = new File(folder + "/" + fileoriginal.getName());

                    folder.mkdirs();
                    try {
                        Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                    } catch (IOException ex) {
                        Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Erro ao criar arquivo do Documento!\n" + ex + "\nEnviando e-mail para suporte.");
                        try {
                            SendEmail.EnviarErro(ex.toString());
                        } catch (HeadlessException hex) {
                            JOptionPane.showMessageDialog(null, "Erro!\n" + hex);
                        } catch (AWTException | IOException ex1) {
                            Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                    //Itens Bean
                    rddb.setIddoc(id);
                    rddb.setDescricao(tabledocumentos.getValueAt(i, 2).toString());
                    rddb.setLocal(filecopy.toString());

                    //iddoc, descricao, local
                    rddd.create(rddb);
                }
            }
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        }
    }

    public static void zeracampos() {
        //Arrumar radios
        radiocliente.setSelected(true);
        radionf.setSelected(false);
        radioconta.setSelected(false);
        radiooutrostipo.setSelected(false);
        radiovazio.setSelected(true);

        //habilitar buttonpesquisa
        btnpesquisa.setEnabled(true);

        //Apagar textos dos txts
        txtid.setText("");
        txtemitente.setText("");
        txtemitente.setEditable(false);
        txtemissao.setCalendar(null);
        txtoutros.setText("");
        txtnumero.setText("");
        txtxml.setText("");

        //desmarcar check
        checkpagamento.setSelected(false);

        //Zerar linhas da tabledocumentos
        DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
        modeldoc.setNumRows(0);

        //DefaultTable
        DefaultTableModel modelhist = (DefaultTableModel) tablehistorico.getModel();

        //Retirar valores selecionados anteriormente
        for (int i = 0; i < modelhist.getRowCount(); i++) {
            modelhist.setValueAt("", i, 1);
            modelhist.setValueAt("", i, 2);
        }
    }

    public static void readtabledocumentos() {
        int s = cbstatus.getSelectedIndex();

        //DefaultTable para modificar tabledocs
        DefaultTableModel model = (DefaultTableModel) tabledocs.getModel();
        model.setNumRows(0);

        switch (s) {
            case 2:
                rdd.readtable().forEach((rdb) -> {
                    model.addRow(new Object[]{
                        rdb.getId(),
                        rdb.getNumero(),
                        rdb.getEmitente()
                    });
                });
                break;
            default:
                rdd.readtablestatus(cbstatus.getSelectedItem().toString()).forEach((rdb) -> {
                    model.addRow(new Object[]{
                        rdb.getId(),
                        rdb.getNumero(),
                        rdb.getEmitente()
                    });
                });
                break;
        }
    }

    public static void readhist() {
        //DefaultTable
        DefaultTableModel modelhist = (DefaultTableModel) tablehistorico.getModel();

        if (!txtid.getText().equals("")) {
            //Retirar valores selecionados anteriormente
            for (int i = 0; i < modelhist.getRowCount(); i++) {
                modelhist.setValueAt("", i, 1);
                modelhist.setValueAt("", i, 2);
            }

            //Colocar valores do Documento selecionado
            rdd.click(Integer.parseInt(txtid.getText())).forEach((rdb) -> {
                if (rdb.getRecebimentouser() != null) {
                    modelhist.setValueAt(rdb.getRecebimentouser(), 0, 1);
                    modelhist.setValueAt(Dates.TransformarDataCurtaDoDB(rdb.getRecebimentodata()), 0, 2);
                }
                if (rdb.getEntradauser() != null) {
                    modelhist.setValueAt(rdb.getEntradauser(), 1, 1);
                    modelhist.setValueAt(Dates.TransformarDataCurtaDoDB(rdb.getEntradadata()), 1, 2);
                }
                if (rdb.getAprovacaouser() != null) {
                    modelhist.setValueAt(rdb.getAprovacaouser(), 2, 1);
                    modelhist.setValueAt(Dates.TransformarDataCurtaDoDB(rdb.getAprovacaodata()), 2, 2);
                    btnaprovar.setEnabled(false);

                } else if (Session.nivel.equals("Administrador")) {
                    btnaprovar.setEnabled(true);
                }
                if (rdb.getCapuser() != null) {
                    if (rdb.getCapuser().equals("Sem pagamento")) {
                        modelhist.setValueAt(rdb.getCapuser(), 3, 1);
                    } else {
                        modelhist.setValueAt(rdb.getCapuser(), 3, 1);
                        modelhist.setValueAt(Dates.TransformarDataCurtaDoDB(rdb.getCapdata()), 3, 2);
                    }
                }
            });
        } else {
            for (int i = 0; i < tablehistorico.getRowCount(); i++) {
                modelhist.setValueAt("", i, 1);
                modelhist.setValueAt("", i, 2);
            }
        }
    }

    public static void readdocumentosdodocumento() {
        DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();

        modeldoc.setNumRows(0);

        rddd.read(Integer.parseInt(txtid.getText())).forEach((rddb) -> {
            modeldoc.addRow(new Object[]{
                rddb.getId(),
                false,
                rddb.getDescricao(),
                rddb.getLocal(),
                ""
            });
        });
    }

    public static void invisible() {
        radiovazio.setVisible(false);
    }

    public static void radios() {
        if (radiocliente.isSelected()) {
            txtemitente.setEditable(false);
            btnpesquisa.setEnabled(true);
        }
        if (radiofornecedor.isSelected()) {
            txtemitente.setEditable(false);
            btnpesquisa.setEnabled(true);
        }
        if (radiooutros.isSelected()) {
            txtemitente.setEditable(true);
            btnpesquisa.setEnabled(false);
        }
        if (radionf.isSelected()) {
            btnxml.setEnabled(true);
            txtoutros.setEditable(false);
//            txtemissao.setEditable(false);
            txtnumero.setEditable(false);
        }
        if (radioconta.isSelected()) {
            btnxml.setEnabled(false);
            txtoutros.setEditable(false);
//            txtemissao.setEditable(true);
            txtnumero.setEditable(true);
        }
        if (radiooutrostipo.isSelected()) {
            txtoutros.setEditable(true);
            txtoutros.requestFocus();
            btnxml.setEnabled(false);
//            txtemissao.setEditable(true);
            txtnumero.setEditable(true);
        }
        if (radiovazio.isSelected()) {
            btnxml.setEnabled(false);
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

        GroupEmitente = new javax.swing.ButtonGroup();
        GroupTipo = new javax.swing.ButtonGroup();
        tabdocs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtpesquisa = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabledocs = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        cbstatus = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtemitente = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        radionf = new javax.swing.JRadioButton();
        radioconta = new javax.swing.JRadioButton();
        radiooutrostipo = new javax.swing.JRadioButton();
        txtoutros = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtnumero = new javax.swing.JTextField();
        txtid = new javax.swing.JTextField();
        btnpesquisa = new javax.swing.JButton();
        radiocliente = new javax.swing.JRadioButton();
        radiofornecedor = new javax.swing.JRadioButton();
        radiooutros = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        radiovazio = new javax.swing.JRadioButton();
        checkpagamento = new javax.swing.JCheckBox();
        txtemissao = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnxml = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablehistorico = new javax.swing.JTable();
        btnaprovar = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabledocumentos = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtxml = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Rastreamento de Documentos - FOR-95");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));

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

        tabledocs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Número Do Documento", "Emitente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabledocs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabledocsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabledocs);
        if (tabledocs.getColumnModel().getColumnCount() > 0) {
            tabledocs.getColumnModel().getColumn(0).setMinWidth(0);
            tabledocs.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabledocs.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));

        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Fechado", "Todos" }));
        cbstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbstatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbstatus, 0, 118, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabdocs.addTab("Documentos", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Documento"));

        txtemitente.setEditable(false);

        jLabel1.setText("Emitente");

        jLabel2.setText("Emissão");

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo"));

        GroupTipo.add(radionf);
        radionf.setText("Nota Fiscal");
        radionf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radionfActionPerformed(evt);
            }
        });

        GroupTipo.add(radioconta);
        radioconta.setText("Conta");
        radioconta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiocontaActionPerformed(evt);
            }
        });

        GroupTipo.add(radiooutrostipo);
        radiooutrostipo.setText("Outros");
        radiooutrostipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiooutrostipoActionPerformed(evt);
            }
        });

        txtoutros.setEditable(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(radionf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioconta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radiooutrostipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtoutros))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radiooutrostipo)
                        .addComponent(radioconta)
                        .addComponent(radionf))
                    .addComponent(txtoutros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jLabel3.setText("Número");

        txtid.setEditable(false);

        btnpesquisa.setText("Pesquisar");
        btnpesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpesquisaActionPerformed(evt);
            }
        });

        GroupEmitente.add(radiocliente);
        radiocliente.setSelected(true);
        radiocliente.setText("Cliente");
        radiocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioclienteActionPerformed(evt);
            }
        });

        GroupEmitente.add(radiofornecedor);
        radiofornecedor.setText("Fornecedor");
        radiofornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiofornecedorActionPerformed(evt);
            }
        });

        GroupEmitente.add(radiooutros);
        radiooutros.setText("Outros");
        radiooutros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiooutrosActionPerformed(evt);
            }
        });

        jLabel5.setText("ID");

        GroupTipo.add(radiovazio);
        radiovazio.setText("Vazio");
        radiovazio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiovazioActionPerformed(evt);
            }
        });

        checkpagamento.setText("Documento com pagamento");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(radiocliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radiofornecedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radiooutros)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtemitente, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnpesquisa)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtemissao, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkpagamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radiovazio)
                .addContainerGap())
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radiocliente)
                    .addComponent(radiofornecedor)
                    .addComponent(radiooutros)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtemitente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnpesquisa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(radiovazio)
                        .addComponent(checkpagamento))
                    .addComponent(txtemissao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Novo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnxml.setText("Arquivo XML");
        btnxml.setEnabled(false);
        btnxml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxmlActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Histórico"));

        tablehistorico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Recebimento", null, null},
                {"Entrada", null, null},
                {"Aprovação", null, null},
                {"Lançamento CAP", null, null}
            },
            new String [] {
                "Ação", "Usuário", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablehistorico);
        if (tablehistorico.getColumnModel().getColumnCount() > 0) {
            tablehistorico.getColumnModel().getColumn(0).setMinWidth(110);
            tablehistorico.getColumnModel().getColumn(0).setPreferredWidth(110);
            tablehistorico.getColumnModel().getColumn(0).setMaxWidth(110);
            tablehistorico.getColumnModel().getColumn(2).setMinWidth(110);
            tablehistorico.getColumnModel().getColumn(2).setPreferredWidth(110);
            tablehistorico.getColumnModel().getColumn(2).setMaxWidth(110);
        }

        btnaprovar.setText("Aprovar Documento");
        btnaprovar.setEnabled(false);
        btnaprovar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaprovarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnaprovar)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnaprovar))
        );

        jTabbedPane2.addTab("Histórico", jPanel7);

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
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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
            tabledocumentos.getColumnModel().getColumn(1).setMinWidth(40);
            tabledocumentos.getColumnModel().getColumn(1).setPreferredWidth(40);
            tabledocumentos.getColumnModel().getColumn(1).setMaxWidth(40);
            tabledocumentos.getColumnModel().getColumn(4).setMinWidth(0);
            tabledocumentos.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabledocumentos.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jButton5.setText("Adicionar Documento");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Excluir Documento");

        jLabel4.setText("Arquivo XML:");

        txtxml.setEditable(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtxml)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtxml, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Documentos", jPanel8);

        jButton4.setText("Histórico de Alterações");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnxml)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(btnxml)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        tabdocs.addTab("Detalhes do Documento", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabdocs, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabdocs)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnxmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxmlActionPerformed
        ProcuraXML p = new ProcuraXML("Rastreamento");
        jDesktopPane1.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_btnxmlActionPerformed

    private void radiooutrostipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiooutrostipoActionPerformed
        txtoutros.setEditable(true);
        txtoutros.requestFocus();
        btnxml.setEnabled(false);
//        txtemissao.setEditable(true);
        txtnumero.setEditable(true);
    }//GEN-LAST:event_radiooutrostipoActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        DocumentosRastreamentoDocumentos p = new DocumentosRastreamentoDocumentos();
        jDesktopPane1.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int resp = JOptionPane.showConfirmDialog(null, "Deseja lançar um novo documento?", "Novo Documento", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            zeracampos();
            readhist();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void radionfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radionfActionPerformed
        btnxml.setEnabled(true);
        txtoutros.setEditable(false);
    }//GEN-LAST:event_radionfActionPerformed

    private void radiocontaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiocontaActionPerformed
        btnxml.setEnabled(false);
        txtoutros.setEditable(false);
        txtnumero.setEditable(true);
    }//GEN-LAST:event_radiocontaActionPerformed

    private void txtpesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpesquisaKeyReleased
        //DefaultTable para filtrar resultados na tabledocs
        DefaultTableModel model = (DefaultTableModel) tabledocs.getModel();
        model.setNumRows(0);

        rdd.pesquisa(txtpesquisa.getText()).forEach((rdb) -> {
            model.addRow(new Object[]{
                rdb.getId(),
                rdb.getNumero(),
                rdb.getEmitente()
            });
        });
    }//GEN-LAST:event_txtpesquisaKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtemitente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Escolha ou digite um emitente!");
        } else if (txtemissao.getCalendar() == null) {
            JOptionPane.showMessageDialog(null, "Coloque uma data de emissão!");
            txtemissao.requestFocus();
        } else if (!radionf.isSelected() && !radioconta.isSelected() && !radiooutrostipo.isSelected()) {
            JOptionPane.showMessageDialog(null, "Selecione pelo menos um tipo de documento!");
        } else if (tabledocumentos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Inclua o PDF do Documento!");
        } else if (rdd.readduplicate(txtemitente.getText(), txtnumero.getText()) && txtid.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Documento já lançado anteriormente!");
        } else if (!checkpagamento.isSelected()) {
            int resp = JOptionPane.showConfirmDialog(null, "O Documento não está marcado para pagamento. Está correto?", "Sem pagamento", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                salvarrastreamento();
            } else {
                checkpagamento.setSelected(true);
                salvarrastreamento();
            }
        } else {
            salvarrastreamento();
        }
        readhist();
        readdocumentosdodocumento();
        readtabledocumentos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void radioclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioclienteActionPerformed
        txtemitente.setEditable(false);
        txtemitente.setText("");
        btnpesquisa.setEnabled(true);
    }//GEN-LAST:event_radioclienteActionPerformed

    private void radiofornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiofornecedorActionPerformed
        txtemitente.setEditable(false);
        txtemitente.setText("");
        btnpesquisa.setEnabled(true);
    }//GEN-LAST:event_radiofornecedorActionPerformed

    private void radiooutrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiooutrosActionPerformed
        txtemitente.setEditable(true);
        txtemitente.setText("");
        btnpesquisa.setEnabled(false);
    }//GEN-LAST:event_radiooutrosActionPerformed

    private void tabledocsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabledocsMouseClicked
        if (evt.getClickCount() == 2) {
            //Colocar valor do ID
            txtid.setText(tabledocs.getValueAt(tabledocs.getSelectedRow(), 0).toString());

            //Mudar tab para ver as Informações
            tabdocs.setSelectedIndex(1);

            //Buscar dados e colocar nos radios e txts
            rdd.click(Integer.parseInt(txtid.getText())).forEach((rdb) -> {
                radiocliente.setSelected(rdb.isCliente());
                radiofornecedor.setSelected(rdb.isFornecedor());
                radiooutros.setSelected(rdb.isOutros());
                txtemitente.setText(rdb.getEmitente());
                txtnumero.setText(rdb.getNumero());
                Dates.SetarDataJDateChooser(txtemissao, rdb.getEmissao());
                radionf.setSelected(rdb.isNf());
                radioconta.setSelected(rdb.isConta());
                radiooutros.setSelected(rdb.isOutrostipo());
                txtoutros.setText(rdb.getOutrosdesc());
                checkpagamento.setSelected(rdb.isCap());
                txtxml.setText(rdb.getXml());
                readhist();
            });

            //Buscar Documentos do Documento
            readdocumentosdodocumento();

            radios();
        }
    }//GEN-LAST:event_tabledocsMouseClicked

    private void btnpesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpesquisaActionPerformed
        if (radiocliente.isSelected()) {
            ProcurarCliente p = new ProcurarCliente("Rastreamento");
            JDesktopPane desk = this.getDesktopPane();
            desk.add(p);
            Dimension desktopsize = jDesktopPane1.getSize();
            Dimension jinternalframesize = p.getSize();
            p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
            p.setVisible(true);
        }
        if (radiofornecedor.isSelected()) {
            ProcurarFornecedor p = new ProcurarFornecedor("Rastreamento");
            JDesktopPane desk = this.getDesktopPane();
            desk.add(p);
            Dimension desktopsize = jDesktopPane1.getSize();
            Dimension jinternalframesize = p.getSize();
            p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
            p.setVisible(true);
        }
    }//GEN-LAST:event_btnpesquisaActionPerformed

    private void tabledocumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabledocumentosMouseClicked
        if (evt.getClickCount() == 2) {
            Desktop desk = Desktop.getDesktop();
            try {
                desk.open(new File((String) tabledocumentos.getValueAt(tabledocumentos.getSelectedRow(), 3)));
            } catch (IOException ex) {
                Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_tabledocumentosMouseClicked

    private void btnaprovarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaprovarActionPerformed
        int resp = JOptionPane.showConfirmDialog(null, "Deseja aprovar o documento?", "Aprovar Documento", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            rdb.setAprovacaouser(Session.nome);
            rdb.setAprovacaodata(Dates.CriarDataCompletaParaDB());
            rdb.setId(Integer.parseInt(txtid.getText()));

            //aprovacaouser = ?, aprovacaodata = ? WHERE iddoc = ?
            rdd.createaprovacao(rdb);

            JOptionPane.showMessageDialog(null, "Aprovado com sucesso!");

            readdocumentosdodocumento();
            readhist();
            readtabledocumentos();
        }
    }//GEN-LAST:event_btnaprovarActionPerformed

    private void radiovazioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiovazioActionPerformed
        btnxml.setEnabled(false);
    }//GEN-LAST:event_radiovazioActionPerformed

    private void cbstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbstatusActionPerformed
        readtabledocumentos();
    }//GEN-LAST:event_cbstatusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GroupEmitente;
    private javax.swing.ButtonGroup GroupTipo;
    public static javax.swing.JButton btnaprovar;
    public static javax.swing.JButton btnpesquisa;
    public static javax.swing.JButton btnxml;
    private static javax.swing.JComboBox<String> cbstatus;
    public static javax.swing.JCheckBox checkpagamento;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    public static javax.swing.JRadioButton radiocliente;
    public static javax.swing.JRadioButton radioconta;
    public static javax.swing.JRadioButton radiofornecedor;
    public static javax.swing.JRadioButton radionf;
    public static javax.swing.JRadioButton radiooutros;
    public static javax.swing.JRadioButton radiooutrostipo;
    private static javax.swing.JRadioButton radiovazio;
    private javax.swing.JTabbedPane tabdocs;
    public static javax.swing.JTable tabledocs;
    public static javax.swing.JTable tabledocumentos;
    public static javax.swing.JTable tablehistorico;
    public static com.toedter.calendar.JDateChooser txtemissao;
    public static javax.swing.JTextField txtemitente;
    private static javax.swing.JTextField txtid;
    public static javax.swing.JTextField txtnumero;
    public static javax.swing.JTextField txtoutros;
    private javax.swing.JTextField txtpesquisa;
    public static javax.swing.JTextField txtxml;
    // End of variables declaration//GEN-END:variables
}
