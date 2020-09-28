/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.comercial;

import DAO.F_UPDAO;
import DAO.F_UP_HistDAO;
import DAO.ProcessosServicoDAO;
import DAO.ServicoGrupoDeProcessosDAO;
import Methods.Dates;
import Methods.ExcelMethods;
import Methods.Telas;
import Methods.Valores;
import View.financeiro.ContasPagar;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class F_UP extends javax.swing.JInternalFrame {

    /**
     * Creates new form F_UP
     */
    static F_UPDAO fud = new F_UPDAO();
    static F_UP_HistDAO fhd = new F_UP_HistDAO();

    static ProcessosServicoDAO psd = new ProcessosServicoDAO();
    static ServicoGrupoDeProcessosDAO sgdpd = new ServicoGrupoDeProcessosDAO();

    public F_UP() {
        initComponents();
        readops();
        statusOS();
        readOSs();
    }

    public static void tableHist() {
        DefaultTableModel modelhist = (DefaultTableModel) OPF_UP.tablehist.getModel();
        modelhist.setNumRows(0);

        fhd.click(Integer.parseInt(OPF_UP.txtid.getText())).forEach(fhb -> {
            String funcionario = "", data = "";
            if (fhb.getFuncionario() != null) {
                funcionario = fhb.getFuncionario();
            }
            if (fhb.getData() != null) {
                data = Dates.TransformarDataCurtaDoDB(fhb.getData());
            }
            modelhist.addRow(new Object[]{
                fhb.getProcesso(),
                funcionario,
                data
            });
        });
    }

    public static void click(JTable jt) {
        OPF_UP of = new OPF_UP();
        Telas.AparecerTela(of);

        OPF_UP.txtid.setText(jt.getValueAt(jt.getSelectedRow(), 0).toString());

        fud.click(Integer.parseInt(OPF_UP.txtid.getText())).forEach(fb -> {
            OPF_UP.txtdav.setText(String.valueOf(fb.getDav()));
            OPF_UP.txtop.setText(String.valueOf(fb.getOp()));
            OPF_UP.txtmaterial.setText(fb.getMaterial());
            Dates.SetarDataJDateChooser(OPF_UP.dateentrega, fb.getDataentrega());
            OPF_UP.txtcliente.setText(fb.getCliente());
            OPF_UP.cbnivel.setSelectedIndex(fb.getNivel());
            OPF_UP.txtvalor.setText(Valores.TransformarValorFloatEmDinheiro(String.valueOf(fb.getValor())));
            OPF_UP.txtobs.setText(fb.getObservacao());
        });

        DefaultTableModel modelhist = (DefaultTableModel) OPF_UP.tablehist.getModel();
        modelhist.setNumRows(0);

        fhd.click(Integer.parseInt(OPF_UP.txtid.getText())).forEach(fhb -> {
            String funcionario = "", data = "";
            if (fhb.getFuncionario() != null) {
                funcionario = fhb.getFuncionario();
            }
            if (fhb.getData() != null) {
                data = Dates.TransformarDataCurtaDoDB(fhb.getData());
            }
            modelhist.addRow(new Object[]{
                fhb.getProcesso(),
                funcionario,
                data
            });
        });

        OPF_UP.btnAlterarProcesso();
    }

    public static void readops() {
        DefaultTableModel model = (DefaultTableModel) tablefup.getModel();
        model.setNumRows(0);

        String processo = cbfiltro.getSelectedItem().toString();

        if (txtpesquisa.getText().equals("")) {
            switch (processo) {
                case "Em Aberto":
                    fud.readOPEmAberto().forEach(fub -> {
                        model.addRow(new Object[]{
                            fub.getId(),
                            fub.getDav(),
                            fub.getMaterial(),
                            Dates.TransformarDataCurtaDoDB(fub.getDataentrega()),
                            fub.getOp(),
                            fub.getNivel(),
                            fub.getProcesso(),
                            fub.getObservacao()
                        });
                    });
                    break;
                case "Todos":
                    fud.readOPTodos().forEach(fub -> {
                        model.addRow(new Object[]{
                            fub.getId(),
                            fub.getDav(),
                            fub.getMaterial(),
                            Dates.TransformarDataCurtaDoDB(fub.getDataentrega()),
                            fub.getOp(),
                            fub.getNivel(),
                            fub.getProcesso(),
                            fub.getObservacao()
                        });
                    });
                    break;
                default:
                    fud.readOPPorProcesso(processo).forEach(fub -> {
                        model.addRow(new Object[]{
                            fub.getId(),
                            fub.getDav(),
                            fub.getMaterial(),
                            Dates.TransformarDataCurtaDoDB(fub.getDataentrega()),
                            fub.getOp(),
                            fub.getNivel(),
                            fub.getProcesso(),
                            fub.getObservacao()
                        });
                    });
                    break;
            }
        } else {
            String pesquisa = txtpesquisa.getText();
            switch (processo) {
                case "Em Aberto":
                    fud.readOPEmAbertoPesquisa(pesquisa).forEach(fub -> {
                        model.addRow(new Object[]{
                            fub.getId(),
                            fub.getDav(),
                            fub.getMaterial(),
                            Dates.TransformarDataCurtaDoDB(fub.getDataentrega()),
                            fub.getOp(),
                            fub.getNivel(),
                            fub.getProcesso(),
                            fub.getObservacao()
                        });
                    });
                    break;
                case "Todos":
                    fud.readOPTodosPesquisa(pesquisa).forEach(fub -> {
                        model.addRow(new Object[]{
                            fub.getId(),
                            fub.getDav(),
                            fub.getMaterial(),
                            Dates.TransformarDataCurtaDoDB(fub.getDataentrega()),
                            fub.getOp(),
                            fub.getNivel(),
                            fub.getProcesso(),
                            fub.getObservacao()
                        });
                    });
                    break;
                default:
                    fud.readOPProcessoPesquisa(processo, pesquisa).forEach(fub -> {
                        model.addRow(new Object[]{
                            fub.getId(),
                            fub.getDav(),
                            fub.getMaterial(),
                            Dates.TransformarDataCurtaDoDB(fub.getDataentrega()),
                            fub.getOp(),
                            fub.getNivel(),
                            fub.getProcesso(),
                            fub.getObservacao()
                        });
                    });
                    break;
            }
        }
    }

    public static void readOSs() {
        DefaultTableModel model = (DefaultTableModel) tableFUPOS.getModel();
        model.setNumRows(0);

        String processo = cbStatusOS.getSelectedItem().toString();

        if (txtPesquisaOS.getText().equals("")) {
            switch (processo) {
                case "Em Aberto":
                    fud.readOSEmAberto().forEach(fub -> {
                        model.addRow(new Object[]{
                            fub.getId(),
                            fub.getDav(),
                            fub.getMaterial(),
                            Dates.TransformarDataCurtaDoDB(fub.getDataentrega()),
                            fub.getOp(),
                            fub.getNivel(),
                            fub.getProcesso(),
                            fub.getObservacao()
                        });
                    });
                    break;
                case "Todos":
                    fud.readOSTodos().forEach(fub -> {
                        model.addRow(new Object[]{
                            fub.getId(),
                            fub.getDav(),
                            fub.getMaterial(),
                            Dates.TransformarDataCurtaDoDB(fub.getDataentrega()),
                            fub.getOp(),
                            fub.getNivel(),
                            fub.getProcesso(),
                            fub.getObservacao()
                        });
                    });
                    break;
                default:
                    fud.readOSPorProcesso(processo).forEach(fub -> {
                        model.addRow(new Object[]{
                            fub.getId(),
                            fub.getDav(),
                            fub.getMaterial(),
                            Dates.TransformarDataCurtaDoDB(fub.getDataentrega()),
                            fub.getOp(),
                            fub.getNivel(),
                            fub.getProcesso(),
                            fub.getObservacao()
                        });
                    });
                    break;
            }
        } else {
            String pesquisa = txtPesquisaOS.getText();
            switch (processo) {
                case "Em Aberto":
                    fud.readOSEmAbertoPesquisa(pesquisa).forEach(fub -> {
                        model.addRow(new Object[]{
                            fub.getId(),
                            fub.getDav(),
                            fub.getMaterial(),
                            Dates.TransformarDataCurtaDoDB(fub.getDataentrega()),
                            fub.getOp(),
                            fub.getNivel(),
                            fub.getProcesso(),
                            fub.getObservacao()
                        });
                    });
                    break;
                case "Todos":
                    fud.readOSTodosPesquisa(pesquisa).forEach(fub -> {
                        model.addRow(new Object[]{
                            fub.getId(),
                            fub.getDav(),
                            fub.getMaterial(),
                            Dates.TransformarDataCurtaDoDB(fub.getDataentrega()),
                            fub.getOp(),
                            fub.getNivel(),
                            fub.getProcesso(),
                            fub.getObservacao()
                        });
                    });
                    break;
                default:
                    fud.readOSProcessoPesquisa(processo, pesquisa).forEach(fub -> {
                        model.addRow(new Object[]{
                            fub.getId(),
                            fub.getDav(),
                            fub.getMaterial(),
                            Dates.TransformarDataCurtaDoDB(fub.getDataentrega()),
                            fub.getOp(),
                            fub.getNivel(),
                            fub.getProcesso(),
                            fub.getObservacao()
                        });
                    });
                    break;
            }
        }
    }

    public static void statusOS() {
        psd.read().forEach(sgdpd -> {
            cbStatusOS.addItem(sgdpd.getNome());
        });
        cbStatusOS.addItem("Encerrado");
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
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtpesquisa = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        cbfiltro = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablefup = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        txtPesquisaOS = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        cbStatusOS = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableFUPOS = new javax.swing.JTable();

        setClosable(true);
        setTitle("Follow Up");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setName("jPanel4"); // NOI18N

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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro"));
        jPanel2.setName("jPanel2"); // NOI18N

        cbfiltro.setMaximumRowCount(10);
        cbfiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Aberto", "Todos", "Rascunho", "Separação de Material", "Corte", "Para Canulação", "Em Canulação", "Para Retífica", "Em Retífica", "Ponta", "Desbaste", "Acabamento", "Canal", "Ticar", "CNC", "Para Revestimento", "Em Revestimento", "Terceiros", "Gravação", "Inspeção", "Etiquetagem", "Faturamento", "Encerrado" }));
        cbfiltro.setName("cbfiltro"); // NOI18N
        cbfiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbfiltroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbfiltro, javax.swing.GroupLayout.Alignment.TRAILING, 0, 216, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbfiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablefup.setAutoCreateRowSorter(true);
        tablefup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DAV", "Material", "Data de Entrega", "OP", "Nível", "Processo Atual", "Observação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablefup.setName("tablefup"); // NOI18N
        tablefup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablefupMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablefup);
        if (tablefup.getColumnModel().getColumnCount() > 0) {
            tablefup.getColumnModel().getColumn(0).setMinWidth(0);
            tablefup.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablefup.getColumnModel().getColumn(0).setMaxWidth(0);
            tablefup.getColumnModel().getColumn(1).setMinWidth(80);
            tablefup.getColumnModel().getColumn(1).setPreferredWidth(80);
            tablefup.getColumnModel().getColumn(1).setMaxWidth(80);
            tablefup.getColumnModel().getColumn(3).setMinWidth(100);
            tablefup.getColumnModel().getColumn(3).setPreferredWidth(100);
            tablefup.getColumnModel().getColumn(3).setMaxWidth(100);
            tablefup.getColumnModel().getColumn(4).setMinWidth(80);
            tablefup.getColumnModel().getColumn(4).setPreferredWidth(80);
            tablefup.getColumnModel().getColumn(4).setMaxWidth(80);
            tablefup.getColumnModel().getColumn(5).setMinWidth(45);
            tablefup.getColumnModel().getColumn(5).setPreferredWidth(45);
            tablefup.getColumnModel().getColumn(5).setMaxWidth(45);
        }

        jButton1.setText("Adicionar OP");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Exportar para Excel");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1029, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Produção", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setName("jPanel5"); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel6.setName("jPanel6"); // NOI18N

        txtPesquisaOS.setName("txtPesquisaOS"); // NOI18N
        txtPesquisaOS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaOSKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisaOS)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisaOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro"));
        jPanel7.setName("jPanel7"); // NOI18N

        cbStatusOS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Aberto", "Todos" }));
        cbStatusOS.setName("cbStatusOS"); // NOI18N
        cbStatusOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbStatusOS, 0, 222, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbStatusOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tableFUPOS.setAutoCreateRowSorter(true);
        tableFUPOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DAV", "Material", "Data de Entrega", "OS", "Nível", "Processo Atual", "Observação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableFUPOS.setName("tableFUPOS"); // NOI18N
        tableFUPOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableFUPOSMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableFUPOS);
        if (tableFUPOS.getColumnModel().getColumnCount() > 0) {
            tableFUPOS.getColumnModel().getColumn(0).setMinWidth(0);
            tableFUPOS.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableFUPOS.getColumnModel().getColumn(0).setMaxWidth(0);
            tableFUPOS.getColumnModel().getColumn(1).setMinWidth(80);
            tableFUPOS.getColumnModel().getColumn(1).setPreferredWidth(80);
            tableFUPOS.getColumnModel().getColumn(1).setMaxWidth(80);
            tableFUPOS.getColumnModel().getColumn(3).setMinWidth(100);
            tableFUPOS.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableFUPOS.getColumnModel().getColumn(3).setMaxWidth(100);
            tableFUPOS.getColumnModel().getColumn(4).setMinWidth(80);
            tableFUPOS.getColumnModel().getColumn(4).setPreferredWidth(80);
            tableFUPOS.getColumnModel().getColumn(4).setMaxWidth(80);
            tableFUPOS.getColumnModel().getColumn(5).setMinWidth(45);
            tableFUPOS.getColumnModel().getColumn(5).setPreferredWidth(45);
            tableFUPOS.getColumnModel().getColumn(5).setMaxWidth(45);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1029, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Serviço", jPanel5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AdicionarOPFUP aof = new AdicionarOPFUP();
        Telas.AparecerTela(aof);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tablefupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablefupMouseClicked
        if (evt.getClickCount() == 2) {
            JTable jt = (JTable) evt.getSource();
            click(jt);
        }
    }//GEN-LAST:event_tablefupMouseClicked

    private void cbfiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbfiltroActionPerformed
        //txtpesquisa.setText("");
        readops();
    }//GEN-LAST:event_cbfiltroActionPerformed

    private void txtpesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpesquisaKeyReleased
        readops();
    }//GEN-LAST:event_txtpesquisaKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            ExcelMethods.exportTable(tablefup, new File("/f-up.xls"), 1);
        } catch (IOException ex) {
            Logger.getLogger(ContasPagar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtPesquisaOSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaOSKeyReleased
        readOSs();
    }//GEN-LAST:event_txtPesquisaOSKeyReleased

    private void cbStatusOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusOSActionPerformed
        readOSs();
    }//GEN-LAST:event_cbStatusOSActionPerformed

    private void tableFUPOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableFUPOSMouseClicked
        if (evt.getClickCount() == 2) {
            JTable jt = (JTable) evt.getSource();
            click(jt);
        }
    }//GEN-LAST:event_tableFUPOSMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> cbStatusOS;
    public static javax.swing.JComboBox<String> cbfiltro;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JTable tableFUPOS;
    public static javax.swing.JTable tablefup;
    public static javax.swing.JTextField txtPesquisaOS;
    public static javax.swing.JTextField txtpesquisa;
    // End of variables declaration//GEN-END:variables
}
