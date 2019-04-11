/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.servicos;

import Bean.OSBean;
import Bean.OSDocumentosBean;
import Bean.OSProcessosBean;
import Bean.ServicoPedidoBean;
import Bean.ServicoPedidoDocumentosBean;
import Bean.ServicoPedidoItensBean;
import DAO.OSDAO;
import DAO.OSDocumentosDAO;
import DAO.OSProcessosDAO;
import DAO.ServicoPedidoDAO;
import DAO.ServicoPedidoDocumentosDAO;
import DAO.ServicoPedidoItensDAO;
import Methods.SendEmail;
import Methods.SoNumeros;
import View.TelaPrincipal;
import static View.TelaPrincipal.jDesktopPane1;
import static View.servicos.OrcamentoServico.tabledocumentos;
import static View.servicos.OrcamentoServico.txtnumeroorcamento;
import static View.servicos.OrcamentoServico.txtvalor;
import static View.servicos.PedidoServico.txtnumeropedido;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class OS extends javax.swing.JInternalFrame {

    /**
     * Creates new form OS
     */
    public OS() {
        initComponents();
        camposnumeros();
        radiovazio.setVisible(false);
        reados();
    }

    public static void reados() {
        DefaultTableModel model = (DefaultTableModel) tableos.getModel();
        model.setNumRows(0);
        OSDAO od = new OSDAO();

        int index = cbstatus.getSelectedIndex();

        switch (index) {
            case 1:
                for (OSBean ob : od.readstatus(cbstatus.getSelectedItem().toString())) {
                    model.addRow(new Object[]{
                        false,
                        ob.getIdtela(),
                        ob.getCliente(),
                        ob.getCodigo(),
                        ob.getStatus()
                    });
                }
                break;
            case 2:
                for (OSBean ob : od.readstatus(cbstatus.getSelectedItem().toString())) {
                    model.addRow(new Object[]{
                        false,
                        ob.getIdtela(),
                        ob.getCliente(),
                        ob.getCodigo(),
                        ob.getStatus()
                    });
                }
                break;
            case 3:
                for (OSBean ob : od.readstatus(cbstatus.getSelectedItem().toString())) {
                    model.addRow(new Object[]{
                        false,
                        ob.getIdtela(),
                        ob.getCliente(),
                        ob.getCodigo(),
                        ob.getStatus()
                    });
                }
                break;
            case 4:
                for (OSBean ob : od.readstatus(cbstatus.getSelectedItem().toString())) {
                    model.addRow(new Object[]{
                        false,
                        ob.getIdtela(),
                        ob.getCliente(),
                        ob.getCodigo(),
                        ob.getStatus()
                    });
                }
                break;
            case 5:
                for (OSBean ob : od.read()) {
                    model.addRow(new Object[]{
                        false,
                        ob.getIdtela(),
                        ob.getCliente(),
                        ob.getCodigo(),
                        ob.getStatus()
                    });
                }
                break;
            default:
                for (OSBean ob : od.reademaberto()) {
                    model.addRow(new Object[]{
                        false,
                        ob.getIdtela(),
                        ob.getCliente(),
                        ob.getCodigo(),
                        ob.getStatus()
                    });
                }
                break;
        }

    }

    public static void readdocs() {
        DefaultTableModel model = (DefaultTableModel) tabledoc.getModel();
        model.setNumRows(0);
        OSDocumentosDAO odd = new OSDocumentosDAO();

        for (OSDocumentosBean odb : odd.readitens(txtnumeroos.getText())) {
            model.addRow(new Object[]{
                false,
                odb.getId(),
                odb.getDesc(),
                odb.getLocal()
            });
        }
    }

    public static void readprocessos() {
        DefaultTableModel model = (DefaultTableModel) tableprocessos.getModel();
        model.setNumRows(0);
        OSProcessosDAO opd = new OSProcessosDAO();

        for (OSProcessosBean opb : opd.read(txtnumeroos.getText())) {
            model.addRow(new Object[]{
                false,
                opb.getId(),
                opb.getProcesso(),
                opb.getInicio(),
                opb.getTermino(),
                opb.getQtdok(),
                opb.getQtdnaook(),
                opb.getUsuario(),
                opb.getOrdem()
            });
        }
    }

    public static void camposnumeros() {
        txtinicial.setDocument(new SoNumeros());
        txtraio.setDocument(new SoNumeros());
        txtfrontal.setDocument(new SoNumeros());
    }

    public static void travarcampos() {
        if (txtstatus.getText().equals("Ativo") || txtstatus.getText().equals("Cancelado") || txtstatus.getText().equals("Fechado")) {
            //Desabilitar botões
            btnprocurarcliente.setEnabled(false);
            btnprocurarmaterial.setEnabled(false);
            btnincluirprocesso.setEnabled(false);
            btnexcluirprocesso.setEnabled(false);
            btnmudarprocesso.setEnabled(false);
            btnalterarstatus.setEnabled(false);

            //Desabilitar txts
            txtinicial.setEditable(false);

            //Desabilitar checks
            checkraio.setEnabled(false);
            checkfrontal.setEnabled(false);

            //Desabilitar radios
            if (radiotopo.isSelected()) {
                radiotopo.setSelected(true);
                radioreconstrucao.setEnabled(false);
            } else {
                radioreconstrucao.setSelected(true);
                radiotopo.setEnabled(false);
            }
        } else {
            //Habilitar botões
            btnprocurarcliente.setEnabled(true);
            btnprocurarmaterial.setEnabled(true);
            btnincluirprocesso.setEnabled(true);
            btnexcluirprocesso.setEnabled(true);
            btnmudarprocesso.setEnabled(true);
            btnalterarstatus.setEnabled(true);

            //Habilitar txts
            txtinicial.setEditable(true);

            //Habilitar checks
            checkraio.setEnabled(true);
            checkfrontal.setEnabled(true);

            //Habilitar radios
            radioreconstrucao.setEnabled(true);
            radiotopo.setEnabled(true);

            if (!txtraio.getText().equals("")) {
                checkraio.setSelected(true);
                txtraio.setEditable(true);
            }
            if (!txtfrontal.getText().equals("")) {
                checkfrontal.setSelected(true);
                txtfrontal.setEditable(true);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonGroup2 = new javax.swing.ButtonGroup();
        tabos = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtpesquisa = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cbstatus = new javax.swing.JComboBox<>();
        jButton12 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtnumeroos = new javax.swing.JTextField();
        txtstatus = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtprevisao = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtabertura = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtcliente = new javax.swing.JTextField();
        txtdas = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnprocurarcliente = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtcodigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtdesc = new javax.swing.JTextField();
        btnprocurarmaterial = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        radiotopo = new javax.swing.JRadioButton();
        radioreconstrucao = new javax.swing.JRadioButton();
        checkraio = new javax.swing.JCheckBox();
        txtraio = new javax.swing.JTextField();
        txtfrontal = new javax.swing.JTextField();
        checkfrontal = new javax.swing.JCheckBox();
        radiovazio = new javax.swing.JRadioButton();
        tabadp = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtnotes = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabledoc = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableprocessos = new javax.swing.JTable();
        btnincluirprocesso = new javax.swing.JButton();
        btnexcluirprocesso = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        btnmudarprocesso = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        txtfinal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtmortas = new javax.swing.JTextField();
        txtinicial = new javax.swing.JTextField();
        btnalterarstatus = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        setClosable(true);
        setMaximizable(true);
        setTitle("OS");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tableos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "#", "Cliente", "Código", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableos);
        if (tableos.getColumnModel().getColumnCount() > 0) {
            tableos.getColumnModel().getColumn(0).setMinWidth(40);
            tableos.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableos.getColumnModel().getColumn(0).setMaxWidth(40);
            tableos.getColumnModel().getColumn(1).setMinWidth(70);
            tableos.getColumnModel().getColumn(1).setPreferredWidth(70);
            tableos.getColumnModel().getColumn(1).setMaxWidth(70);
            tableos.getColumnModel().getColumn(4).setMinWidth(110);
            tableos.getColumnModel().getColumn(4).setPreferredWidth(110);
            tableos.getColumnModel().getColumn(4).setMaxWidth(110);
        }

        jButton1.setText("Novo");

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));

        jLabel8.setText("Pesquisa:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtpesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro"));

        jLabel9.setText("Status");

        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Aberto", "Rascunho", "Ativo", "Cancelado", "Fechado", "Todas" }));
        cbstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbstatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbstatus, 0, 151, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton12.setText("Alterar Status em Lote");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 373, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton12))
                .addContainerGap())
        );

        tabos.addTab("Lista de OS", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("OS"));

        jLabel1.setText("Nº");

        txtnumeroos.setEditable(false);
        txtnumeroos.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtstatus.setEditable(false);
        txtstatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setText("Status");

        txtprevisao.setEditable(false);

        jLabel10.setText("Data de Previsão");

        txtabertura.setEditable(false);

        jLabel11.setText("Data de Abertura");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnumeroos, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtabertura, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtprevisao, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtnumeroos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtprevisao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtabertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        jLabel3.setText("Nome:");

        txtcliente.setEditable(false);

        txtdas.setEditable(false);
        txtdas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtdas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtdasMouseClicked(evt);
            }
        });

        jLabel4.setText("DAS de origem:");

        btnprocurarcliente.setText("Procurar");
        btnprocurarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprocurarclienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnprocurarcliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdas, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(btnprocurarcliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Material"));

        jLabel5.setText("Código:");

        txtcodigo.setEditable(false);

        jLabel7.setText("Descrição:");

        txtdesc.setEditable(false);

        btnprocurarmaterial.setText("Procurar");
        btnprocurarmaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprocurarmaterialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnprocurarmaterial)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnprocurarmaterial))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton2.setText("Salvar");
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

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Serviço"));

        buttonGroup1.add(radiotopo);
        radiotopo.setText("Topo");

        buttonGroup1.add(radioreconstrucao);
        radioreconstrucao.setText("Reconstrução");

        checkraio.setText("Raio");
        checkraio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkraioActionPerformed(evt);
            }
        });

        txtraio.setEditable(false);
        txtraio.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtfrontal.setEditable(false);
        txtfrontal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        checkfrontal.setText("Frontal");
        checkfrontal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkfrontalActionPerformed(evt);
            }
        });

        buttonGroup1.add(radiovazio);
        radiovazio.setText("Vazio");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(radiotopo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioreconstrucao))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(checkraio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtraio, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(checkfrontal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfrontal, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radiovazio)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radiotopo)
                    .addComponent(radioreconstrucao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtraio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkraio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfrontal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkfrontal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(radiovazio)
                .addContainerGap())
        );

        txtnotes.setColumns(20);
        txtnotes.setRows(5);
        jScrollPane2.setViewportView(txtnotes);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabadp.addTab("Anotações", jPanel7);

        tabledoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Descrição", "Local", "Local Original"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
        jScrollPane5.setViewportView(tabledoc);
        if (tabledoc.getColumnModel().getColumnCount() > 0) {
            tabledoc.getColumnModel().getColumn(0).setMinWidth(40);
            tabledoc.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabledoc.getColumnModel().getColumn(0).setMaxWidth(40);
            tabledoc.getColumnModel().getColumn(1).setMinWidth(0);
            tabledoc.getColumnModel().getColumn(1).setPreferredWidth(0);
            tabledoc.getColumnModel().getColumn(1).setMaxWidth(0);
            tabledoc.getColumnModel().getColumn(3).setMinWidth(0);
            tabledoc.getColumnModel().getColumn(3).setPreferredWidth(0);
            tabledoc.getColumnModel().getColumn(3).setMaxWidth(0);
            tabledoc.getColumnModel().getColumn(4).setMinWidth(0);
            tabledoc.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabledoc.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jButton8.setText("Incluir");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Excluir");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addContainerGap())
        );

        tabadp.addTab("Documentos", jPanel8);

        tableprocessos.setAutoCreateRowSorter(true);
        tableprocessos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Processo", "Início", "Término", "Qtde Conforme", "Qtde Não Conforme", "Usuário", "Ordem"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableprocessos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableprocessosMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tableprocessos);
        if (tableprocessos.getColumnModel().getColumnCount() > 0) {
            tableprocessos.getColumnModel().getColumn(0).setMinWidth(40);
            tableprocessos.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableprocessos.getColumnModel().getColumn(0).setMaxWidth(40);
            tableprocessos.getColumnModel().getColumn(1).setMinWidth(0);
            tableprocessos.getColumnModel().getColumn(1).setPreferredWidth(0);
            tableprocessos.getColumnModel().getColumn(1).setMaxWidth(0);
            tableprocessos.getColumnModel().getColumn(8).setMinWidth(0);
            tableprocessos.getColumnModel().getColumn(8).setPreferredWidth(0);
            tableprocessos.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        btnincluirprocesso.setText("Incluir");
        btnincluirprocesso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnincluirprocessoActionPerformed(evt);
            }
        });

        btnexcluirprocesso.setText("Excluir");

        jButton6.setText("Teste");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        btnmudarprocesso.setText("Mudar Processos");
        btnmudarprocesso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmudarprocessoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnmudarprocesso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnexcluirprocesso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnincluirprocesso))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnincluirprocesso)
                    .addComponent(btnexcluirprocesso)
                    .addComponent(jButton6)
                    .addComponent(btnmudarprocesso))
                .addContainerGap())
        );

        tabadp.addTab("Processos", jPanel10);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Medida", "Maior Valor do Lote", "Menor Valor do Lote", "Funcionário", "Instrumento de Medição"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMinWidth(0);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(0);
            jTable2.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabadp.addTab("Inspeção", jPanel14);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Quantidade"));

        txtfinal.setEditable(false);
        txtfinal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel13.setText("Inicial");

        jLabel14.setText("Final");

        jLabel15.setText("Mortas");

        txtmortas.setEditable(false);
        txtmortas.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtinicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtinicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtinicialKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtinicial, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfinal, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmortas, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(txtmortas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtinicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnalterarstatus.setText("Alterar Status da OS");
        btnalterarstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnalterarstatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnalterarstatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(tabadp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabadp)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(btnalterarstatus))
                .addContainerGap())
        );

        tabos.addTab("Detalhes OS", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabos)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabos)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkraioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkraioActionPerformed
        if (checkraio.isSelected()) {
            txtraio.setEditable(true);
            txtraio.requestFocus();
        } else {
            txtraio.setEditable(false);
        }
    }//GEN-LAST:event_checkraioActionPerformed

    private void checkfrontalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkfrontalActionPerformed
        if (checkfrontal.isSelected()) {
            txtfrontal.setEditable(true);
            txtfrontal.requestFocus();
        } else {
            txtfrontal.setEditable(false);
        }
    }//GEN-LAST:event_checkfrontalActionPerformed

    private void txtdasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtdasMouseClicked
        if (evt.getButton() == 3) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem das = new JMenuItem("Abrir DAS");

            das.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String DAS = txtdas.getText();
                    if (DAS.equals("")) {
                        JOptionPane.showMessageDialog(rootPane, "OS sem DAS");
                    } else {
                        PedidoServico p = new PedidoServico();
                        jDesktopPane1.add(p);
                        p.setVisible(true);
                        try {
                            p.setMaximum(true);
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        PedidoServico.txtnumeropedido.setText(DAS);
                        PedidoServico.tabpedidos.setSelectedIndex(1);

                        ServicoPedidoDAO spd = new ServicoPedidoDAO();

                        for (ServicoPedidoBean spb : spd.click(txtnumeropedido.getText())) {
                            PedidoServico.txtcliente.setText(spb.getCliente());
                            PedidoServico.txtcondicao.setText(spb.getCondicao());
                            PedidoServico.txtrepresentante.setText(spb.getRepresentante());
                            PedidoServico.txtvendedor.setText(spb.getVendedor());
                            PedidoServico.txtstatusretorno.setText(spb.getStatus_retorno());
                            PedidoServico.txtnotes.setText(spb.getNotes());
                            PedidoServico.txtorcamento.setText(String.valueOf(spb.getIdorcamento()));
                        }

                        DefaultTableModel model = (DefaultTableModel) PedidoServico.tableitensorcamento.getModel();
                        model.setNumRows(0);
                        ServicoPedidoItensDAO spid = new ServicoPedidoItensDAO();

                        for (ServicoPedidoItensBean spib : spid.readitens(txtnumeropedido.getText())) {
                            model.addRow(new Object[]{
                                false,
                                spib.getId(),
                                spib.getCodigo(),
                                spib.getDescricao(),
                                spib.getQtde(),
                                spib.getValor(),
                                spib.getTotal(),
                                spib.getPrazo(),
                                spib.getPedidocliente(),
                                spib.getNf()
                            });
                        }

                        txtvalor();

                        DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
                        modeldoc.setNumRows(0);
                        ServicoPedidoDocumentosDAO spdd = new ServicoPedidoDocumentosDAO();

                        for (ServicoPedidoDocumentosBean spdb : spdd.readitens(txtnumeroorcamento.getText())) {
                            modeldoc.addRow(new Object[]{
                                false,
                                spdb.getId(),
                                spdb.getDescricao(),
                                spdb.getLocal(),});
                        }
                    }
                }
            });

            menu.add(das);

            menu.show(evt.getComponent(), evt.getPoint().x, evt.getPoint().y);
        }
    }//GEN-LAST:event_txtdasMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (txtnumeroos.getText().equals("")) {
            if (txtcliente.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Escolha um cliente primeiro!");
            } else if (txtcodigo.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Selecione um produto primeiro!");
            } else if (txtinicial.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Coloque uma quantidade!");
                txtinicial.requestFocus();
            } else if (!radiotopo.isSelected() && !radioreconstrucao.isSelected()) {
                JOptionPane.showMessageDialog(rootPane, "Escolha um serviço a ser executado.");
            } else {
                //Criar OS
                OSDAO od = new OSDAO();
                OSBean ob = new OSBean();

                try {
                    if (od.readnome() == false) {
                        Calendar ca = Calendar.getInstance();
                        String patterny = "yy";
                        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                        String year = simpleDateFormaty.format(ca.getTime());
                        String idtela = year + "-0001";
                        ob.setIdtela(idtela);
                        txtnumeroos.setText(idtela);
                    } else {
                        Calendar ca = Calendar.getInstance();
                        String patterny = "yy";
                        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                        String year = simpleDateFormaty.format(ca.getTime());
                        String hua = "";
                        for (OSBean sob2 : od.read()) {
                            hua = String.valueOf(sob2.getIdtela());
                        }
                        int yearint = Integer.parseInt(hua.replace(year + "-", ""));
                        int yearnovo = yearint + 1;
                        String idtela = year + "-" + String.format("%04d", yearnovo);
                        ob.setIdtela(idtela);
                        txtnumeroos.setText(idtela);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(OrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
                }
                Calendar c = Calendar.getInstance();
                String pattern = "dd/MM/yyyy HH:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                txtabertura.setText(simpleDateFormat.format(c.getTime()));
                ob.setDataabertura(simpleDateFormat.format(c.getTime()));
                int days = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "Quantos dias de prazo?", "Prazo", JOptionPane.YES_NO_OPTION));
                c.add(Calendar.DAY_OF_MONTH, days);
                txtprevisao.setText(simpleDateFormat.format(c.getTime()));
                ob.setDataprevisao(simpleDateFormat.format(c.getTime()));
                txtstatus.setText("Rascunho");
                ob.setStatus("Rascunho");
                ob.setCliente(txtcliente.getText());
                ob.setDas("");
                ob.setCodigo(txtcodigo.getText());
                ob.setDescricao(txtdesc.getText());
                ob.setQtdinicial(Integer.parseInt(txtinicial.getText()));
                ob.setQtdok(Integer.parseInt(txtinicial.getText()));
                ob.setQtdnaook(0);
                ob.setNotes(txtnotes.getText());
                String topo = "false";
                String rec = "false";
                if (radiotopo.isSelected()) {
                    topo = "true";
                    radioreconstrucao.setEnabled(false);
                } else {
                    rec = "true";
                    radiotopo.setEnabled(false);
                }
                ob.setTopo(topo);
                ob.setReconstrucao(rec);
                ob.setRaio(txtraio.getText());
                txtraio.setEditable(false);
                checkraio.setEnabled(false);
                ob.setFrontal(txtfrontal.getText());
                txtfrontal.setEditable(false);
                checkfrontal.setEnabled(false);
                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal
                od.create(ob);

                //Criar Processos
                OSProcessosDAO opd = new OSProcessosDAO();
                OSProcessosBean opb = new OSProcessosBean();

                int rcproc = tableprocessos.getRowCount();
                if (rcproc > 0) {
                    for (int i = 0; i < rcproc; i++) {
                        opb.setIdos(txtnumeroos.getText());
                        opb.setProcesso(tableprocessos.getValueAt(i, 2).toString());
                        opb.setInicio(tableprocessos.getValueAt(i, 3).toString());
                        opb.setTermino(tableprocessos.getValueAt(i, 4).toString());
                        opb.setQtdok(Integer.parseInt(tableprocessos.getValueAt(i, 5).toString()));
                        opb.setQtdnaook(Integer.parseInt(tableprocessos.getValueAt(i, 6).toString()));
                        opb.setUsuario(tableprocessos.getValueAt(i, 7).toString());
                        opb.setOrdem(Integer.parseInt(tableprocessos.getValueAt(i, 8).toString()));

                        //idos, processo, inicio, termino, qtdok, qtdnaook, usuario, ordem
                        opd.create(opb);
                    }
                }

                //Criar Documentos da OS
                OSDocumentosDAO odd = new OSDocumentosDAO();
                OSDocumentosBean odb = new OSDocumentosBean();

                int rcdoc = tabledoc.getRowCount();
                if (rcdoc > 0) {
                    for (int i = 0; i < rcdoc; i++) {
                        File fileoriginal = new File(tabledoc.getValueAt(i, 4).toString());
                        File folder = new File("Q:/MIKE_ERP/os_arq/" + txtnumeroos.getText());
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

                        odb.setIdos(txtnumeroos.getText());
                        odb.setDesc(tabledoc.getValueAt(i, 2).toString());
                        odb.setLocal(filecopy.toString());
                    }
                }
                //idos, descricao, local
                odd.create(odb);
            }
            reados();
            readdocs();
            readprocessos();
            JOptionPane.showMessageDialog(rootPane, "Salvo com sucesso!");
        } else {

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int row = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "Qual número de row?", "Número de row", JOptionPane.YES_OPTION));
        DefaultTableModel model = (DefaultTableModel) tableprocessos.getModel();
        tableprocessos.setModel(model);
        model.insertRow(row, new Object[]{
            false,
            "ID" + row,
            "Processo" + row,
            "Início" + row,
            "Término" + row,
            "Conforme" + row,
            "Não conforme" + row,
            "Usuário" + row
        });
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnprocurarmaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprocurarmaterialActionPerformed
        ProcuraItemOS p = new ProcuraItemOS();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_btnprocurarmaterialActionPerformed

    private void btnprocurarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprocurarclienteActionPerformed
        ProcurarClienteOS p = new ProcurarClienteOS();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_btnprocurarclienteActionPerformed

    private void btnmudarprocessoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmudarprocessoActionPerformed

    }//GEN-LAST:event_btnmudarprocessoActionPerformed

    private void tableprocessosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableprocessosMouseClicked
        if (evt.getClickCount() == 2) {
            if (txtstatus.getText().equals("Rascunho")) {
                JOptionPane.showMessageDialog(rootPane, "OS em rascunho!");
            } else {
                if (tableprocessos.getValueAt(tableprocessos.getSelectedRow(), 3).equals("")) {
                    int resp = JOptionPane.showConfirmDialog(rootPane, "Deseja iniciar o processo " + tableprocessos.getValueAt(tableprocessos.getSelectedRow(), 2) + "?", "Início de Processo", JOptionPane.YES_NO_OPTION);
                    if (resp == 0) {
                        OSProcessosDAO opd = new OSProcessosDAO();
                        OSProcessosBean opb = new OSProcessosBean();

                        Calendar c = Calendar.getInstance();
                        String pattern = "dd/MM/yyyy HH:mm:ss";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        opb.setInicio(simpleDateFormat.format(c.getTime()));
                        opb.setUsuario(TelaPrincipal.lblapelido.getText());
                        opb.setId(Integer.parseInt(tableprocessos.getValueAt(tableprocessos.getSelectedRow(), 1).toString()));

                        //inicio = ?, usuario = ? WHERE id = ?
                        opd.updateinicio(opb);

                        readprocessos();
                    }
                } else {
                    ProcessoOS p = new ProcessoOS();
                    JDesktopPane desk = this.getDesktopPane();
                    desk.add(p);
                    Dimension desktopsize = jDesktopPane1.getSize();
                    Dimension jinternalframesize = p.getSize();
                    p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
                    p.setVisible(true);
                    ProcessoOS.txtid.setText(tableprocessos.getValueAt(tableprocessos.getSelectedRow(), 1).toString());
                    ProcessoOS.txtinicial.setText(txtinicial.getText());
                    ProcessoOS.readprocesso();
                    ProcessoOS.readinspecao();
                }
            }
        }
    }//GEN-LAST:event_tableprocessosMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int resp = JOptionPane.showConfirmDialog(rootPane, "Deseja criar uma nova OS?", "Nova OS", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            txtnumeroos.setText("");
            txtabertura.setText("");
            txtprevisao.setText("");
            txtstatus.setText("");
            txtcliente.setText("");
            txtdas.setText("");
            txtcodigo.setText("");
            txtdesc.setText("");
            txtinicial.setText("");
            txtfinal.setText("");
            txtmortas.setText("");
            txtnotes.setText("");
            radiovazio.setSelected(true);
            checkraio.setSelected(false);
            txtraio.setText("");
            txtraio.setEditable(false);
            checkfrontal.setSelected(false);
            txtfrontal.setText("");
            txtfrontal.setEditable(false);
            DefaultTableModel modeldoc = (DefaultTableModel) tabledoc.getModel();
            DefaultTableModel modelproc = (DefaultTableModel) tableprocessos.getModel();
            modeldoc.setNumRows(0);
            modelproc.setNumRows(0);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnincluirprocessoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnincluirprocessoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnincluirprocessoActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void tableosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableosMouseClicked
        if (evt.getClickCount() == 2) {
            tabos.setSelectedIndex(1);
            txtnumeroos.setText(tableos.getValueAt(tableos.getSelectedRow(), 1).toString());

            //Pegar dados da OS
            OSDAO od = new OSDAO();

            for (OSBean ob : od.click(txtnumeroos.getText())) {
                txtabertura.setText(ob.getDataabertura());
                txtprevisao.setText(ob.getDataprevisao());
                txtstatus.setText(ob.getStatus());
                txtcliente.setText(ob.getCliente());
                txtcodigo.setText(ob.getCodigo());
                txtdesc.setText(ob.getDescricao());
                txtinicial.setText(String.valueOf(ob.getQtdinicial()));
                txtfinal.setText(String.valueOf(ob.getQtdok()));
                txtmortas.setText(String.valueOf(ob.getQtdnaook()));
                txtnotes.setText(ob.getNotes());
                if (ob.getTopo().equals("true")) {
                    radiotopo.setSelected(true);
                } else {
                    radioreconstrucao.setSelected(true);
                }
                txtraio.setText(ob.getRaio());
                txtfrontal.setText(ob.getFrontal());

            }

            //Pegar documentos
            readdocs();

            //Pegar processos
            readprocessos();

            //Travar campos de acordo com status da op
            travarcampos();
        }
    }//GEN-LAST:event_tableosMouseClicked

    private void txtinicialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinicialKeyReleased
        txtfinal.setText(txtinicial.getText());
        txtmortas.setText("0");
        for (int i = 0; i < tableprocessos.getRowCount(); i++) {
            tableprocessos.setValueAt(txtfinal.getText(), i, 5);
            tableprocessos.setValueAt(txtmortas.getText(), i, 6);
        }
    }//GEN-LAST:event_txtinicialKeyReleased

    private void cbstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbstatusActionPerformed
        reados();
    }//GEN-LAST:event_cbstatusActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int rascunho = 0;
        int ativo = 0;
        int cancelado = 0;
        int fechado = 0;
        int numerotrue = 0;
        for (int i = 0; i < tableos.getRowCount(); i++) {
            if (tableos.getValueAt(i, 0).equals(true)) {
                numerotrue++;
            }
            if (tableos.getValueAt(i, 4).equals("Rascunho") && tableos.getValueAt(i, 0).equals(true)) {
                rascunho++;
            }
            if (tableos.getValueAt(i, 4).equals("Ativo") && tableos.getValueAt(i, 0).equals(true)) {
                ativo++;
            }
            if (tableos.getValueAt(i, 4).equals("Cancelado") && tableos.getValueAt(i, 0).equals(true)) {
                cancelado++;
            }
            if (tableos.getValueAt(i, 4).equals("Fechado") && tableos.getValueAt(i, 0).equals(true)) {
                fechado++;
            }
        }
        if (numerotrue == 0) {
            JOptionPane.showMessageDialog(rootPane, "Não existem OS's selecionadas.");
        } else if (rascunho > 0 && ativo > 0) {
            JOptionPane.showMessageDialog(rootPane, "Mais de um status de OS's selecionado.");
        } else if (rascunho > 0 && cancelado > 0) {
            JOptionPane.showMessageDialog(rootPane, "Mais de um status de OS's selecionado.");
        } else if (rascunho > 0 && fechado > 0) {
            JOptionPane.showMessageDialog(rootPane, "Mais de um status de OS's selecionado.");
        } else if (ativo > 0 && cancelado > 0) {
            JOptionPane.showMessageDialog(rootPane, "Mais de um status de OS's selecionado.");
        } else if (ativo > 0 && fechado > 0) {
            JOptionPane.showMessageDialog(rootPane, "Mais de um status de OS's selecionado.");
        } else if (cancelado > 0 && fechado > 0) {
            JOptionPane.showMessageDialog(rootPane, "Mais de um status de OS's selecionado.");
        } else if (cancelado > 0) {
            JOptionPane.showMessageDialog(rootPane, "OS's canceladas. Status não pode ser alterado.");
        } else if (fechado > 0) {
            JOptionPane.showMessageDialog(rootPane, "OS's fechadas. Status não pode ser alterado.");
        } else {
            MudarStatusEmLote p = new MudarStatusEmLote();
            JDesktopPane desk = this.getDesktopPane();
            desk.add(p);
            Dimension desktopsize = jDesktopPane1.getSize();
            Dimension jinternalframesize = p.getSize();
            p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
            p.setVisible(true);
            if (rascunho > 0) {
                MudarStatusEmLote.txtrascunho.setText(String.valueOf(rascunho));
            }
            if (ativo > 0) {
                MudarStatusEmLote.txtativo.setText(String.valueOf(ativo));
            }
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void btnalterarstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnalterarstatusActionPerformed
        MudarStatus p = new MudarStatus();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_btnalterarstatusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnalterarstatus;
    public static javax.swing.JButton btnexcluirprocesso;
    public static javax.swing.JButton btnincluirprocesso;
    public static javax.swing.JButton btnmudarprocesso;
    public static javax.swing.JButton btnprocurarcliente;
    public static javax.swing.JButton btnprocurarmaterial;
    public javax.swing.ButtonGroup buttonGroup1;
    public javax.swing.ButtonGroup buttonGroup2;
    public static javax.swing.JComboBox<String> cbstatus;
    public static javax.swing.JCheckBox checkfrontal;
    public static javax.swing.JCheckBox checkraio;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton12;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton6;
    public javax.swing.JButton jButton8;
    public javax.swing.JButton jButton9;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel10;
    public javax.swing.JPanel jPanel11;
    public javax.swing.JPanel jPanel12;
    public javax.swing.JPanel jPanel13;
    public javax.swing.JPanel jPanel14;
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
    public javax.swing.JScrollPane jScrollPane6;
    public javax.swing.JScrollPane jScrollPane7;
    public javax.swing.JTable jTable1;
    public javax.swing.JTable jTable2;
    public static javax.swing.JRadioButton radioreconstrucao;
    public static javax.swing.JRadioButton radiotopo;
    public javax.swing.JRadioButton radiovazio;
    public static javax.swing.JTabbedPane tabadp;
    public static javax.swing.JTable tabledoc;
    public static javax.swing.JTable tableos;
    public static javax.swing.JTable tableprocessos;
    public javax.swing.JTabbedPane tabos;
    public javax.swing.JTextField txtabertura;
    public static javax.swing.JTextField txtcliente;
    public static javax.swing.JTextField txtcodigo;
    public javax.swing.JTextField txtdas;
    public static javax.swing.JTextField txtdesc;
    public static javax.swing.JTextField txtfinal;
    public static javax.swing.JTextField txtfrontal;
    public static javax.swing.JTextField txtinicial;
    public static javax.swing.JTextField txtmortas;
    public javax.swing.JTextArea txtnotes;
    public static javax.swing.JTextField txtnumeroos;
    public javax.swing.JTextField txtpesquisa;
    public javax.swing.JTextField txtprevisao;
    public static javax.swing.JTextField txtraio;
    public static javax.swing.JTextField txtstatus;
    // End of variables declaration//GEN-END:variables
}
