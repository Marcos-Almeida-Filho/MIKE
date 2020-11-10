/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.vendas;

import Connection.Session;
import DAO.F_UPDAO;
import DAO.F_UP_HistDAO;
import DAO.OPDAO;
import DAO.OPDocDAO;
import DAO.OPMPDAO;
import DAO.OPMedicoesDAO;
import DAO.OPObsDAO;
import DAO.OPProcessosDAO;
import DAO.ProcessosServicoDAO;
import DAO.VendasMateriaisDAO;
import DAO.VendasMateriaisMovDAO;
import Methods.Dates;
import Methods.SendEmail;
import Methods.Telas;
import View.Geral.ProcuraMaterial;
import View.Geral.ProcurarMateriaPrima;
import View.Geral.ProcurarPedido;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class OP extends javax.swing.JInternalFrame {

    static OPDAO od = new OPDAO();
    static OPProcessosDAO opd = new OPProcessosDAO();
    static OPMPDAO omd = new OPMPDAO();
    static OPDocDAO odd = new OPDocDAO();
    static OPObsDAO ood = new OPObsDAO();
    static OPMedicoesDAO omed = new OPMedicoesDAO();
    static F_UP_HistDAO fuhd = new F_UP_HistDAO();
    static VendasMateriaisDAO vmd = new VendasMateriaisDAO();
    static VendasMateriaisMovDAO vmmd = new VendasMateriaisMovDAO();
    static ProcessosServicoDAO psd = new ProcessosServicoDAO();
    static F_UPDAO fud = new F_UPDAO();

    static int idMaterial = 0;
    static int iP = 0;

    /**
     * Creates new form OPs
     */
    public OP() {
        initComponents();
        readOPs();
    }

    public static void readOPs() {
        String status = cbStatus.getSelectedItem().toString();
        DefaultTableModel modelOP = (DefaultTableModel) tableOP.getModel();
        modelOP.setNumRows(0);
        if (txtPesquisa.getText().equals("")) {
            switch (status) {
                case "Todos":
                    od.readTodasOPs().forEach(ob -> {
                        modelOP.addRow(new Object[]{
                            ob.getId(),
                            false,
                            ob.getOp(),
                            ob.getCliente(),
                            ob.getCodigo(),
                            Dates.TransformarDataCurtaDoDB(ob.getDataprevista()),
                            ob.getStatus()
                        });
                    });
                    break;
                default:
                    od.readOPPorStatus(status).forEach(ob -> {
                        modelOP.addRow(new Object[]{
                            ob.getId(),
                            false,
                            ob.getOp(),
                            ob.getCliente(),
                            ob.getCodigo(),
                            Dates.TransformarDataCurtaDoDB(ob.getDataprevista()),
                            ob.getStatus()
                        });
                    });
                    break;
            }
        } else {
        }
    }

    public static void lerOP(String OP) {
        od.readOP(OP).forEach(ob -> {
            idMaterial = ob.getIdmaterial();
            txtDav.setText(ob.getDav());
            TxtCliente.setText(ob.getCliente());
            TxtCodigo.setText(ob.getCodigo());
            Dates.SetarDataJDateChooser(txtDataEntrega, ob.getDataprevista());
            txtStatus.setText(ob.getStatus());
            TxtDescricao.setText(ob.getDescricao());
            TxtQtde.setText(String.valueOf(ob.getQtd()).replace(".", ","));
            txtOk.setText(String.valueOf(ob.getQtdok()).replace(".", ","));
            txtMortas.setText(String.valueOf(ob.getQtdnaook()).replace(".", ","));
        });
    }

    public static void lerProcessos(String op) {
        DefaultTableModel modelProcessos = (DefaultTableModel) tableProcessos.getModel();
        modelProcessos.setNumRows(0);

        opd.readProcessos(op).forEach(opb -> {
            modelProcessos.addRow(new Object[]{
                opb.getId(),
                false,
                opb.getProcesso(),
                Dates.TransformarDataCompletaDoDB(opb.getDatainicio()),
                Dates.TransformarDataCompletaDoDB(opb.getDatafim()),
                opb.getQtdok(),
                opb.getQtdnaook(),
                opb.getUser(),
                opb.getOrdem(),
                opb.getQtddisponivel()
            });
        });
    }

    public static void lerMedidasMaterial(int idMaterial) {
        vmd.click(idMaterial).forEach(vmb -> {
            txthelice.setText(vmb.getHelice());
            txtnucleo.setText(vmb.getNucleo());
            txtconcavidade.setText(vmb.getConcavidade());
            txtaliviotopo1.setText(vmb.getTopo1());
            txtaliviotopo2.setText(vmb.getTopo2());
            txtalivio1.setText(vmb.getAlivio1());
            txtalivio2.setText(vmb.getAlivio2());
            txtespfilete.setText(vmb.getFilete());
            txtagressividade.setText(vmb.getAgressividade());
            txtfrontal.setText(vmb.getFrontal());
        });
    }

    public static void lerMP(String op) {
        DefaultTableModel modelMP = (DefaultTableModel) tableMP.getModel();

        modelMP.setNumRows(0);

        omd.readMP(op).forEach(omb -> {
            modelMP.addRow(new Object[]{
                omb.getId(),
                false,
                omb.getCodigo(),
                omb.getDescricao(),
                omb.getQtd(),
                omb.isBaixa()
            });
        });
    }

    public static void lerObs(String op) {
        DefaultTableModel modelObs = (DefaultTableModel) tableObs.getModel();

        modelObs.setNumRows(0);

        ood.readObs(op).forEach(oob -> {
            modelObs.addRow(new Object[]{
                oob.getId(),
                false,
                Dates.TransformarDataCurtaDoDB(oob.getData()),
                oob.getUsuario(),
                oob.getObs()
            });
        });
    }

    public static void lerInspecoes(String op) {
        DefaultTableModel modelIns = (DefaultTableModel) tableInspecao.getModel();

        modelIns.setNumRows(0);

        for (int i = 0; i < tableProcessos.getRowCount(); i++) {
            final String Processo = tableProcessos.getValueAt(i, 3).toString();
            omed.readMedicoes(Integer.parseInt(tableProcessos.getValueAt(i, 0).toString())).forEach(omeb -> {
                modelIns.addRow(new Object[]{
                    Processo,
                    omeb.getMedida(),
                    omeb.getMaior(),
                    omeb.getMenor(),
                    omeb.getInstrumento()
                });
            });
        }
    }

    public static void lerDocs(String op) {
        DefaultTableModel modelDoc = (DefaultTableModel) tableDocs.getModel();

        modelDoc.setNumRows(0);

        odd.read(op).forEach(odb -> {
            modelDoc.addRow(new Object[]{
                odb.getId(),
                false,
                odb.getDescricao(),
                odb.getLocal()
            });
        });
    }

    public static void camposPorStatus() {
        String status = txtStatus.getText();

        switch (status) {
            case "Rascunho":
                TxtQtde.setEditable(true);
                btnProcurarCliente.setEnabled(true);
                btnProcurarProduto.setEnabled(true);
                btnBaixaMP.setEnabled(true);
                break;
            default:
                TxtQtde.setEditable(false);
                btnProcurarCliente.setEnabled(false);
                btnProcurarProduto.setEnabled(false);
                btnBaixaMP.setEnabled(false);
                break;
        }
    }

    public static void criarOP() {
        String op = od.opAtual();
        idMaterial = vmd.idProduto(TxtCodigo.getText());

        od.create(op, Dates.CriarDataCurtaDBSemDataExistente(), Dates.CriarDataCurtaDBJDateChooser(txtDataEntrega.getDate()), TxtCliente.getText(), txtDav.getText(), idMaterial, TxtCodigo.getText(), TxtDescricao.getText(), Double.parseDouble(TxtQtde.getText()), Double.parseDouble(TxtQtde.getText()), "Rascunho");

        TxtNumOP.setText(op);
    }

    public static void updateOP(String op) {
        idMaterial = vmd.idProduto(TxtCodigo.getText());

        od.updateOP(op, TxtCliente.getText(), idMaterial, TxtCodigo.getText(), TxtDescricao.getText(), Double.parseDouble(TxtQtde.getText()));
    }

    public static void salvarDocs(String op) {
        for (int i = 0; i < tableDocs.getRowCount(); i++) {
            if (tableDocs.getValueAt(i, 0).equals("")) {
                //Localicação do documento original
                File fileoriginal = new File(tableDocs.getValueAt(i, 4).toString());
                //Pasta que será colocar o documento
                File folder = new File("Q:/MIKE_ERP/op_arq/" + op);
                //Documento copiado do original
                File filecopy = new File(folder + "/" + fileoriginal.getName());

                //Criar pasta no caso de já não existir
                folder.mkdirs();
                try {
                    //Criar o documento copiado na pasta
                    Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                } catch (IOException ex) {
                    Logger.getLogger(OP.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");

                    new Thread() {
                        @Override
                        public void run() {
                            SendEmail.EnviarErro2(ex.toString());
                        }
                    }.start();
                }

                odd.create(op, tableDocs.getValueAt(i, 2).toString(), filecopy.toString().replace("//", "////"));
            }
        }
    }

    public static void salvarObs(String op) {
        for (int i = 0; i < tableObs.getRowCount(); i++) {
            if (tableObs.getValueAt(i, 0).equals("")) {
                ood.create(op, tableObs.getValueAt(i, 2).toString(), tableObs.getValueAt(i, 3).toString(), tableObs.getValueAt(i, 4).toString());
            }
        }
    }

    public static void salvarMP(String op) {
        for (int i = 0; i < tableMP.getRowCount(); i++) {
            if (tableMP.getValueAt(i, 0).equals("")) {
                omd.create(op, tableMP.getValueAt(i, 2).toString(), tableMP.getValueAt(i, 3).toString(), Double.parseDouble(tableMP.getValueAt(i, 4).toString()));
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

        jScrollPane9 = new javax.swing.JScrollPane();
        tabOPS = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableOP = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        cbStatus = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        TxtNumOP = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TxtCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDav = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnProcurarCliente = new javax.swing.JButton();
        txtDataEntrega = new com.toedter.calendar.JDateChooser();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        TxtCodigo = new javax.swing.JTextField();
        TxtDescricao = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnProcurarProduto = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableObs = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableProcessos = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        paneldadostxt = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txthelice = new javax.swing.JTextField();
        txtnucleo = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtconcavidade = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtaliviotopo1 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtaliviotopo2 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtalivio1 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txtalivio2 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtespfilete = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtagressividade = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtfrontal = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        lblicon = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableInspecao = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableDocs = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableMP = new javax.swing.JTable();
        btnBaixaMP = new javax.swing.JButton();
        btnAddMP = new javax.swing.JButton();
        btnExcluirMP = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        TxtQtde = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOk = new javax.swing.JTextField();
        txtMortas = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("OPs");
        setAutoscrolls(true);
        setPreferredSize(new java.awt.Dimension(970, 734));

        jScrollPane9.setPreferredSize(new java.awt.Dimension(1000, 600));

        tabOPS.setMinimumSize(new java.awt.Dimension(43, 65));
        tabOPS.setPreferredSize(new java.awt.Dimension(943, 600));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(43, 65));
        jPanel1.setPreferredSize(new java.awt.Dimension(943, 600));

        tableOP.setAutoCreateRowSorter(true);
        tableOP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "OP", "Cliente", "Código", "Data de Entrega", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableOP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableOPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableOP);
        if (tableOP.getColumnModel().getColumnCount() > 0) {
            tableOP.getColumnModel().getColumn(0).setMinWidth(0);
            tableOP.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableOP.getColumnModel().getColumn(0).setMaxWidth(0);
            tableOP.getColumnModel().getColumn(1).setMinWidth(35);
            tableOP.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableOP.getColumnModel().getColumn(1).setMaxWidth(35);
        }

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rascunho", "Ativo", "Cancelado", "Finalizado", "Todos" }));
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbStatus, 0, 152, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
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
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabOPS.addTab("Lista", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(43, 65));
        jPanel2.setPreferredSize(new java.awt.Dimension(943, 600));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("OP"));

        TxtNumOP.setEditable(false);
        TxtNumOP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TxtNumOP.setFocusable(false);

        jLabel1.setText("OP");

        txtStatus.setEditable(false);
        txtStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStatus.setFocusable(false);

        jLabel2.setText("Status");

        jLabel3.setText("Cliente:");

        TxtCliente.setEditable(false);

        jLabel4.setText("Data de Entrega:");

        txtDav.setEditable(false);
        txtDav.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDavMouseClicked(evt);
            }
        });

        jLabel10.setText("DAV");

        btnProcurarCliente.setText("Procurar");

        txtDataEntrega.setDateFormatString("dd'/'MM'/'yyyy");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtNumOP, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDav, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProcurarCliente)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(TxtNumOP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel2)
                        .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtDataEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(TxtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnProcurarCliente))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Produto"));

        jLabel5.setText("Código:");

        TxtCodigo.setEditable(false);

        TxtDescricao.setEditable(false);
        TxtDescricao.setFocusable(false);

        jLabel6.setText("Descrição:");

        btnProcurarProduto.setText("Procurar");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtDescricao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProcurarProduto))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 419, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(TxtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProcurarProduto))
                .addGap(14, 14, 14))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

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

        jButton6.setText("Adicionar Observação");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Excluir Observação");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Observações", jPanel4);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        tableProcessos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Processo", "Início", "Término", "Qtde Conforme", "Qtde Não Conforme", "Usuário", "Ordem", "Qtde Disponível"
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
        tableProcessos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProcessosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableProcessos);
        if (tableProcessos.getColumnModel().getColumnCount() > 0) {
            tableProcessos.getColumnModel().getColumn(0).setMinWidth(0);
            tableProcessos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableProcessos.getColumnModel().getColumn(0).setMaxWidth(0);
            tableProcessos.getColumnModel().getColumn(1).setMinWidth(35);
            tableProcessos.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableProcessos.getColumnModel().getColumn(1).setMaxWidth(35);
            tableProcessos.getColumnModel().getColumn(8).setMinWidth(0);
            tableProcessos.getColumnModel().getColumn(8).setPreferredWidth(0);
            tableProcessos.getColumnModel().getColumn(8).setMaxWidth(0);
            tableProcessos.getColumnModel().getColumn(9).setMinWidth(0);
            tableProcessos.getColumnModel().getColumn(9).setPreferredWidth(0);
            tableProcessos.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Processos", jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados para Construção"));

        jLabel14.setText("Hélice");

        txthelice.setEditable(false);
        txthelice.setName("fresa-broca-fe-be"); // NOI18N
        txthelice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtheliceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtheliceFocusLost(evt);
            }
        });

        txtnucleo.setEditable(false);
        txtnucleo.setName("fresa-broca-fe-be"); // NOI18N
        txtnucleo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtnucleoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtnucleoFocusLost(evt);
            }
        });

        jLabel25.setText("Núcleo");

        txtconcavidade.setEditable(false);
        txtconcavidade.setName("fresa-fe"); // NOI18N
        txtconcavidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtconcavidadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtconcavidadeFocusLost(evt);
            }
        });

        jLabel36.setText("Concavidade");

        txtaliviotopo1.setEditable(false);
        txtaliviotopo1.setName("fresa-fe"); // NOI18N
        txtaliviotopo1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtaliviotopo1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtaliviotopo1FocusLost(evt);
            }
        });

        jLabel37.setText("Alívio de Topo Primário");

        txtaliviotopo2.setEditable(false);
        txtaliviotopo2.setName("fresa-fe"); // NOI18N
        txtaliviotopo2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtaliviotopo2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtaliviotopo2FocusLost(evt);
            }
        });

        jLabel38.setText("Alívio de Topo Secundário");

        txtalivio1.setEditable(false);
        txtalivio1.setName("fresa-fe"); // NOI18N
        txtalivio1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtalivio1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtalivio1FocusLost(evt);
            }
        });

        jLabel39.setText("Alívio Primário");

        txtalivio2.setEditable(false);
        txtalivio2.setName("fresa-fe"); // NOI18N
        txtalivio2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtalivio2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtalivio2FocusLost(evt);
            }
        });

        jLabel40.setText("Alívio Secundário");

        txtespfilete.setEditable(false);
        txtespfilete.setName("fresa-fe"); // NOI18N
        txtespfilete.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtespfileteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtespfileteFocusLost(evt);
            }
        });

        jLabel41.setText("Espessura de Filete");

        txtagressividade.setEditable(false);
        txtagressividade.setName("fresa-broca-fe-be"); // NOI18N
        txtagressividade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtagressividadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtagressividadeFocusLost(evt);
            }
        });

        jLabel42.setText("Agressividade");

        txtfrontal.setEditable(false);
        txtfrontal.setName("broca-be"); // NOI18N
        txtfrontal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtfrontalFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtfrontalFocusLost(evt);
            }
        });

        jLabel16.setText("Frontal");

        javax.swing.GroupLayout paneldadostxtLayout = new javax.swing.GroupLayout(paneldadostxt);
        paneldadostxt.setLayout(paneldadostxtLayout);
        paneldadostxtLayout.setHorizontalGroup(
            paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldadostxtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel25)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txthelice, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(txtagressividade)
                    .addComponent(txtespfilete)
                    .addComponent(txtalivio2)
                    .addComponent(txtalivio1)
                    .addComponent(txtaliviotopo2)
                    .addComponent(txtaliviotopo1)
                    .addComponent(txtconcavidade, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtnucleo, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(txtfrontal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paneldadostxtLayout.setVerticalGroup(
            paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldadostxtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txthelice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnucleo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtconcavidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtaliviotopo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtaliviotopo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txtalivio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txtalivio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtespfilete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtagressividade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfrontal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane6.setViewportView(paneldadostxt);

        lblicon.setBackground(new java.awt.Color(255, 255, 255));
        lblicon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblicon.setOpaque(true);
        jScrollPane10.setViewportView(lblicon);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
            .addComponent(jScrollPane10)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Informações do Produto", jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        tableInspecao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Processo", "Medida", "Valor Maior", "Valor Menor", "Instrumento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tableInspecao);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Inspeção", jPanel10);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jButton3.setText("Adicionar Arquivo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tableDocs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Descrição", "Local"
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
        jScrollPane8.setViewportView(tableDocs);
        if (tableDocs.getColumnModel().getColumnCount() > 0) {
            tableDocs.getColumnModel().getColumn(0).setMinWidth(0);
            tableDocs.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableDocs.getColumnModel().getColumn(0).setMaxWidth(0);
            tableDocs.getColumnModel().getColumn(1).setMinWidth(35);
            tableDocs.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableDocs.getColumnModel().getColumn(1).setMaxWidth(35);
        }

        jButton8.setText("Excluir Arquivo");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3))
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton8)))
        );

        jTabbedPane3.addTab("Arquivos", jPanel11);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        tableMP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Código", "Descrição", "Quantidade", "Baixa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableMP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableMP);
        if (tableMP.getColumnModel().getColumnCount() > 0) {
            tableMP.getColumnModel().getColumn(0).setMinWidth(0);
            tableMP.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableMP.getColumnModel().getColumn(0).setMaxWidth(0);
            tableMP.getColumnModel().getColumn(1).setMinWidth(35);
            tableMP.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableMP.getColumnModel().getColumn(1).setMaxWidth(35);
            tableMP.getColumnModel().getColumn(4).setMinWidth(100);
            tableMP.getColumnModel().getColumn(4).setPreferredWidth(100);
            tableMP.getColumnModel().getColumn(4).setMaxWidth(100);
            tableMP.getColumnModel().getColumn(5).setMinWidth(60);
            tableMP.getColumnModel().getColumn(5).setPreferredWidth(60);
            tableMP.getColumnModel().getColumn(5).setMaxWidth(60);
        }

        btnBaixaMP.setText("Separar");
        btnBaixaMP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaixaMPActionPerformed(evt);
            }
        });

        btnAddMP.setText("Adicionar");
        btnAddMP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMPActionPerformed(evt);
            }
        });

        btnExcluirMP.setText("Excluir");
        btnExcluirMP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirMPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExcluirMP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddMP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBaixaMP)
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBaixaMP)
                    .addComponent(btnAddMP)
                    .addComponent(btnExcluirMP))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Matéria Prima", jPanel15);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Quantidades"));

        TxtQtde.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setText("Total");

        txtOk.setEditable(false);
        txtOk.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtMortas.setEditable(false);
        txtMortas.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setText("OK");

        jLabel9.setText("Mortas");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtOk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(TxtQtde, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMortas)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(TxtQtde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMortas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)))
        );

        jButton2.setText("Salvar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Nova OP");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        tabOPS.addTab("OP", jPanel2);

        jScrollPane9.setViewportView(tabOPS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        TxtNumOP.setText("");
        TxtCliente.setText("");
        TxtCliente.setEditable(true);
        TxtCliente.setFocusable(true);
        txtDataEntrega.setDate(null);
        txtDataEntrega.setEnabled(true);
        txtStatus.setText("");
        TxtCodigo.setText("");
        TxtCodigo.setEditable(true);
        TxtCodigo.setFocusable(true);
        TxtDescricao.setText("");
        TxtQtde.setText("");
        TxtQtde.setEditable(true);
        TxtQtde.setFocusable(true);
        TxtCliente.requestFocus();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (TxtNumOP.getText().equals("")) {
            criarOP();

            String op = TxtNumOP.getText();

            salvarDocs(op);

            salvarMP(op);

            salvarObs(op);
        } else {
            String op = TxtNumOP.getText();

            updateOP(op);

            salvarDocs(op);

            salvarMP(op);

            salvarObs(op);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tableOPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOPMouseClicked
        if (evt.getClickCount() == 2) {
            tabOPS.setSelectedIndex(1);
            TxtNumOP.setText(tableOP.getValueAt(tableOP.rowAtPoint(evt.getPoint()), 2).toString());
            
            String op = TxtNumOP.getText();
            lerOP(op);

            lerProcessos(op);
            
            lerDocs(op);
            
            lerObs(op);
            
            lerInspecoes(op);

            lerMedidasMaterial(idMaterial);
        }
    }//GEN-LAST:event_tableOPMouseClicked

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        readOPs();
    }//GEN-LAST:event_cbStatusActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

    }//GEN-LAST:event_jButton6ActionPerformed

    private void tableProcessosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProcessosMouseClicked
        if (evt.getClickCount() == 2) {
            if (txtStatus.getText().equals("Rascunho")) {
                JOptionPane.showMessageDialog(null, "OP em Rascunho. Favor colocar em produção primeiro.");
            } else if (tableProcessos.getValueAt(tableProcessos.getSelectedRow(), 3).equals("")) {
                int resp = JOptionPane.showConfirmDialog(null, "Deseja iniciar o processo " + tableProcessos.getValueAt(tableProcessos.getSelectedRow(), 2).toString() + "?", "Iniciar Processo", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    opd.inicioProcesso(Integer.parseInt(tableProcessos.getValueAt(tableProcessos.getSelectedRow(), 0).toString()), Dates.CriarDataCompletaParaDB(), Session.nome);

                    lerProcessos(TxtNumOP.getText());
                }
            } else {
                int id = Integer.parseInt(tableProcessos.getValueAt(tableProcessos.getSelectedRow(), 0).toString());
                double qtdTotalProcesso = Double.parseDouble(tableProcessos.getValueAt(tableProcessos.getSelectedRow(), 9).toString());
                ProcessoOP po = new ProcessoOP(id, qtdTotalProcesso);
                Telas.AparecerTela(po);
                ProcessoOP.readProcesso(id);
            }
        }
    }//GEN-LAST:event_tableProcessosMouseClicked

    private void txtheliceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtheliceFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/helice.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtheliceFocusGained

    private void txtheliceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtheliceFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtheliceFocusLost

    private void txtnucleoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnucleoFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/nucleo.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtnucleoFocusGained

    private void txtnucleoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnucleoFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtnucleoFocusLost

    private void txtconcavidadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtconcavidadeFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/concavidade.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtconcavidadeFocusGained

    private void txtconcavidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtconcavidadeFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtconcavidadeFocusLost

    private void txtaliviotopo1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo1FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/aliviotopo1.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtaliviotopo1FocusGained

    private void txtaliviotopo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo1FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtaliviotopo1FocusLost

    private void txtaliviotopo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo2FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/aliviotopo2.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtaliviotopo2FocusGained

    private void txtaliviotopo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo2FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtaliviotopo2FocusLost

    private void txtalivio1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtalivio1FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/alivio1.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtalivio1FocusGained

    private void txtalivio1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtalivio1FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtalivio1FocusLost

    private void txtalivio2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtalivio2FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/alivio2.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtalivio2FocusGained

    private void txtalivio2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtalivio2FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtalivio2FocusLost

    private void txtespfileteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtespfileteFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/espfilete.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtespfileteFocusGained

    private void txtespfileteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtespfileteFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtespfileteFocusLost

    private void txtagressividadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtagressividadeFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/agressividade.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtagressividadeFocusGained

    private void txtagressividadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtagressividadeFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtagressividadeFocusLost

    private void txtfrontalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfrontalFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/frontal.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtfrontalFocusGained

    private void txtfrontalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfrontalFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtfrontalFocusLost

    private void txtDavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDavMouseClicked
        if (evt.getButton() == 3) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem novaDav = new JMenuItem("Colocar DAV");
            JMenuItem dav = new JMenuItem("Abrir DAV");

            novaDav.addActionListener((ActionEvent e) -> {
                String cliente = TxtCliente.getText();
                String codigo = TxtCodigo.getText();
                if (cliente.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Sem cliente na OP.");
                } else if (codigo.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Sem produto na OP.");
                } else {
                    ProcurarPedido pp = new ProcurarPedido(this.getClass().getSimpleName(), cliente, codigo);
                    Telas.AparecerTela(pp);
                }

                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates
            });

            dav.addActionListener((ActionEvent ae) -> {
                String DAV = txtDav.getText();
                if (DAV.equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Produto sem DAV.");
                } else {
                    PedidoVenda p = new PedidoVenda();
                    Telas.AparecerTelaAumentada(p);
                    PedidoVenda.txtPedido.setText(DAV);
                    PedidoVenda.tabPedidos.setSelectedIndex(1);
                    PedidoVenda.lerPedido(DAV);
                    PedidoVenda.lerObsPedido(DAV);
                    PedidoVenda.lerItensPedido(DAV);
                    PedidoVenda.lerDocumentosPedido(DAV);
                }
            });

            if (txtStatus.getText().equals("Rascunho")) {
                menu.add(novaDav);
            }
            menu.add(dav);

            menu.show(evt.getComponent(), evt.getPoint().x, evt.getPoint().y);
        }
    }//GEN-LAST:event_txtDavMouseClicked

    private void btnExcluirMPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirMPActionPerformed
        int numTrue = 0, baixa = 0;
        for (int i = 0; i < tableMP.getRowCount(); i++) {
            if (tableMP.getValueAt(i, 1).equals(true)) {
                numTrue++;
                if (tableMP.getValueAt(i, 5).equals(true)) {
                    baixa++;
                }
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma Matéria Prima selecionada.");
        } else if (baixa > 0) {
            JOptionPane.showMessageDialog(null, "Produtos com baixa de estoque selecionados.");
        } else {

        }
    }//GEN-LAST:event_btnExcluirMPActionPerformed

    private void btnAddMPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMPActionPerformed
        String[] options = new String[2];
        options[0] = "Produto";
        options[1] = "Matéria-prima";

        int escolha;

        escolha = JOptionPane.showInternalOptionDialog(null, "Qual origem?", "Escolher Origem", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, iconable);

        if (escolha == 0) {
            ProcuraMaterial pmv = new ProcuraMaterial(this.getClass().getSimpleName());
            Telas.AparecerTela(pmv);
        } else {
            ProcurarMateriaPrima pmp = new ProcurarMateriaPrima(this.getClass().getSimpleName());
            Telas.AparecerTela(pmp);
        }
    }//GEN-LAST:event_btnAddMPActionPerformed

    private void btnBaixaMPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaixaMPActionPerformed
        int rows = tableMP.getRowCount(), numBaixa = 0;
        for (int i = 0; i < rows; i++) {
            if (tableMP.getValueAt(i, 5).equals(true)) {
                numBaixa++;
            }
        }
        if (rows == 0 || numBaixa == rows) {
            JOptionPane.showMessageDialog(null, "Nenhum item para dar baixa.");
        } else if (TxtNumOP.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione ou salve uma OP primeiro.");
        } else {
            for (int i = 0; i < rows; i++) {
                if (tableMP.getValueAt(i, 5).equals(false)) {
                    String material = tableMP.getValueAt(i, 2).toString();
                    int idmaterial = vmd.idProduto(material);
                    double estoqueAtual = vmd.readEstoque(idmaterial);
                    double qtd = Double.parseDouble(tableMP.getValueAt(i, 4).toString());
                    if (qtd > estoqueAtual) {
                        JOptionPane.showMessageDialog(null, "Estoque do item " + material + " é inferior ao selecionado para dar baixa.");
                    } else {
                        double estoque = estoqueAtual - qtd;

                        vmd.updateEstoque(estoque, idmaterial);

                        vmmd.create(idmaterial, estoqueAtual, qtd, estoque, TxtNumOP.getText() + " - baixa de MP", Dates.CriarDataCurtaDBSemDataExistente(), Session.nome);

                        String op = TxtNumOP.getText();

                        od.updateStatus(op, "Ativo");

                        omd.updateBaixa(idmaterial);

                        lerOP(op);

                    }
                }
            }
        }
    }//GEN-LAST:event_btnBaixaMPActionPerformed

    private void tableMPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMPMouseClicked
        if (tableMP.getValueAt(tableMP.getSelectedRow(), 5).equals(true) && tableMP.getSelectedColumn() == 4) {
            JOptionPane.showMessageDialog(null, "Produto já dado baixa de estoque.");
            btnAddMP.requestFocus();
        }
    }//GEN-LAST:event_tableMPMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextField TxtCliente;
    private static javax.swing.JTextField TxtCodigo;
    private static javax.swing.JTextField TxtDescricao;
    public static javax.swing.JTextField TxtNumOP;
    public static javax.swing.JTextField TxtQtde;
    private javax.swing.JButton btnAddMP;
    private static javax.swing.JButton btnBaixaMP;
    private javax.swing.JButton btnExcluirMP;
    private static javax.swing.JButton btnProcurarCliente;
    private static javax.swing.JButton btnProcurarProduto;
    private static javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lblicon;
    public static javax.swing.JPanel paneldadostxt;
    public static javax.swing.JTabbedPane tabOPS;
    private static javax.swing.JTable tableDocs;
    private static javax.swing.JTable tableInspecao;
    public static javax.swing.JTable tableMP;
    private static javax.swing.JTable tableOP;
    private static javax.swing.JTable tableObs;
    public static javax.swing.JTable tableProcessos;
    private static com.toedter.calendar.JDateChooser txtDataEntrega;
    private static javax.swing.JTextField txtDav;
    private static javax.swing.JTextField txtMortas;
    private static javax.swing.JTextField txtOk;
    private static javax.swing.JTextField txtPesquisa;
    private static javax.swing.JTextField txtStatus;
    public static javax.swing.JTextField txtagressividade;
    public static javax.swing.JTextField txtalivio1;
    public static javax.swing.JTextField txtalivio2;
    public static javax.swing.JTextField txtaliviotopo1;
    public static javax.swing.JTextField txtaliviotopo2;
    public static javax.swing.JTextField txtconcavidade;
    public static javax.swing.JTextField txtespfilete;
    public static javax.swing.JTextField txtfrontal;
    public static javax.swing.JTextField txthelice;
    public static javax.swing.JTextField txtnucleo;
    // End of variables declaration//GEN-END:variables
}
