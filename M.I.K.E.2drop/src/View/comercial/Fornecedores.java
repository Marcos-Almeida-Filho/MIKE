/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.comercial;

import Bean.FornecedoresAltBean;
import Bean.FornecedoresBean;
import Bean.FornecedoresContatosBean;
import Bean.FornecedoresDocBean;
import Bean.FornecedoresTipoBean;
import Connection.Session;
import DAO.FornecedoresAltDAO;
import DAO.FornecedoresContatosDAO;
import DAO.FornecedoresDAO;
import DAO.FornecedoresDocDAO;
import DAO.FornecedoresTipoDAO;
import Methods.Dates;
import Methods.SendEmail;
import ValoresOriginais.FornecedoresValoresOriginais;
import br.com.correios.EnderecoERP;
import br.com.correios.SQLException_Exception;
import br.com.correios.SigepClienteException;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class Fornecedores extends javax.swing.JInternalFrame {

    /**
     * Creates new form TelaCadastroCliente
     */
    public Fornecedores() {
        initComponents();
        readtablefornecedores();
        tabletipo();
    }

    public void zeracampos() {
        //Apagar txt's
        txtidfornecedor.setText("");
        txtnomefornecedor.setText("");
        txtrazao.setText("");
        txtcnpj.setText("");
        txtie.setText("");
        txttelefone.setText("");
        txtlogradouro.setText("");
        txtnumero.setText("");
        txtcomplemento.setText("");
        txtbairro.setText("");
        txtcidade.setText("");
        cbuf.setSelectedIndex(0);
        txtcep.setText("");
        txtnomefornecedor.requestFocus();

        //Zerar número de linhas das tables
        DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
        DefaultTableModel modelcont = (DefaultTableModel) tablecontatos.getModel();
        modeldoc.setNumRows(0);
        modelcont.setNumRows(0);

        //Desmarcar todas as linhas da tabletipo
        tabletipo();
    }

    public static void readtablefornecedores() {
        DefaultTableModel model = (DefaultTableModel) tableofornecedores.getModel();
        model.setNumRows(0);
        FornecedoresDAO fd = new FornecedoresDAO();

        for (FornecedoresBean fb : fd.preenchertabelafornecedores()) {
            model.addRow(new Object[]{
                fb.getId(),
                fb.getNome(),
                fb.getRazaosocial()
            });
        }
    }

    public static void tabletipo() {
        for (int i = 0; i < tabletipo.getRowCount(); i++) {
            tabletipo.setValueAt(false, i, 0);
        }
    }

    public static void orgnome() {
        FornecedoresValoresOriginais.nome = txtnomefornecedor.getText();
    }

    public static void orgrazao() {
        FornecedoresValoresOriginais.razao = txtrazao.getText();
    }

    public static void orgcnpj() {
        FornecedoresValoresOriginais.cnpj = txtcnpj.getText();
    }

    public static void orgie() {
        FornecedoresValoresOriginais.ie = txtie.getText();
    }

    public static void orgtel() {
        FornecedoresValoresOriginais.tel = txttelefone.getText();
    }

    public static void orgnfe() {
        FornecedoresValoresOriginais.nfe = txtnfe.getText();
    }

    public static void orgrua() {
        FornecedoresValoresOriginais.rua = txtlogradouro.getText();
    }

    public static void orgnum() {
        FornecedoresValoresOriginais.num = txtnumero.getText();
    }

    public static void orgcomp() {
        FornecedoresValoresOriginais.comp = txtcomplemento.getText();
    }

    public static void orgbairro() {
        FornecedoresValoresOriginais.bairro = txtbairro.getText();
    }

    public static void orgcidade() {
        FornecedoresValoresOriginais.cidade = txtcidade.getText();
    }

    public static void orguf() {
        FornecedoresValoresOriginais.uf = cbuf.getSelectedItem().toString();
    }

    public static void orgcep() {
        FornecedoresValoresOriginais.cep = txtcep.getText();
    }

    public static void orgmp() {
        FornecedoresValoresOriginais.mp = (boolean) tabletipo.getValueAt(0, 0);
    }

    public static void orgferr() {
        FornecedoresValoresOriginais.ferr = (boolean) tabletipo.getValueAt(1, 0);
    }

    public static void orgreb() {
        FornecedoresValoresOriginais.reb = (boolean) tabletipo.getValueAt(2, 0);
    }

    public static void orgoleo() {
        FornecedoresValoresOriginais.oleo = (boolean) tabletipo.getValueAt(3, 0);
    }

    public static void orggrav() {
        FornecedoresValoresOriginais.grav = (boolean) tabletipo.getValueAt(4, 0);
    }

    public static void orgemb() {
        FornecedoresValoresOriginais.emb = (boolean) tabletipo.getValueAt(5, 0);
    }

    public static void orgcal() {
        FornecedoresValoresOriginais.cal = (boolean) tabletipo.getValueAt(6, 0);
    }

    public static void orgman() {
        FornecedoresValoresOriginais.man = (boolean) tabletipo.getValueAt(7, 0);
    }

    public static void orgesc() {
        FornecedoresValoresOriginais.esc = (boolean) tabletipo.getValueAt(8, 0);
    }

    public static void orglim() {
        FornecedoresValoresOriginais.lim = (boolean) tabletipo.getValueAt(9, 0);
    }

    public static void orgrev() {
        FornecedoresValoresOriginais.rev = (boolean) tabletipo.getValueAt(10, 0);
    }

    public static void orgret() {
        FornecedoresValoresOriginais.ret = (boolean) tabletipo.getValueAt(11, 0);
    }

    public static void orgdoc() {
        FornecedoresValoresOriginais.rcdoc = tabledocumentos.getRowCount();
    }

    public static void orgcont() {
        FornecedoresValoresOriginais.rccont = tablecontatos.getRowCount();
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
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tabfornecedores = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableofornecedores = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnsalvar = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtidfornecedor = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtnomefornecedor = new javax.swing.JTextField();
        txtrazao = new javax.swing.JTextField();
        txtie = new javax.swing.JTextField();
        txtcnpj = new javax.swing.JFormattedTextField();
        txttelefone = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        txtnfe = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtlogradouro = new javax.swing.JTextField();
        txtnumero = new javax.swing.JTextField();
        txtcomplemento = new javax.swing.JTextField();
        txtbairro = new javax.swing.JTextField();
        txtcidade = new javax.swing.JTextField();
        txtcep = new javax.swing.JFormattedTextField();
        cbuf = new javax.swing.JComboBox<>();
        jButton8 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabletipo = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabledocumentos = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablecontatos = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jLabel6.setText("jLabel6");

        setClosable(true);
        setTitle("Cadastro de Fornecedores");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tableofornecedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Razão Social"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableofornecedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableofornecedoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableofornecedores);
        if (tableofornecedores.getColumnModel().getColumnCount() > 0) {
            tableofornecedores.getColumnModel().getColumn(0).setMinWidth(40);
            tableofornecedores.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableofornecedores.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jButton3.setText("Novo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 871, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );

        tabfornecedores.addTab("Fornecedores", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnsalvar.setText("Salvar");
        btnsalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalvarActionPerformed(evt);
            }
        });

        jButton6.setText("Novo");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("ID");

        txtidfornecedor.setEditable(false);
        txtidfornecedor.setBackground(new java.awt.Color(255, 255, 255));
        txtidfornecedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Fornecedor"));

        jLabel1.setText("Nome");

        jLabel2.setText("Razão Social");

        jLabel3.setText("CNPJ");

        jLabel4.setText("Ins. Est.");

        jLabel5.setText("Telefone");

        try {
            txtcnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            txttelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel14.setText("E-mail Nfe");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnfe, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcnpj))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnomefornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtrazao))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtie, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 185, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtnomefornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtrazao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtcnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txttelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtnfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereço"));

        jLabel7.setText("Logradouro");

        jLabel8.setText("Nº");

        jLabel9.setText("Complemento");

        jLabel10.setText("Bairro");

        jLabel11.setText("Cidade");

        jLabel12.setText("UF");

        jLabel13.setText("CEP");

        try {
            txtcep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        cbuf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO" }));

        jButton8.setText("Procurar CEP");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtlogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbairro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcidade, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcomplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbuf, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcep, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(txtlogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcomplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(txtcep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton8))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtbairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(txtcidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(cbuf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de Fornecedor"));

        tabletipo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "Matéria Prima"},
                {null, "Ferramentas"},
                {null, "Rebolos"},
                {null, "Óleo"},
                {null, "Gravação"},
                {null, "Embalagem"},
                {null, "Calibração"},
                {null, "Manutenção Nível II"},
                {null, "Material de Escritório"},
                {null, "Material de Limpeza"},
                {null, "Revestimento"},
                {null, "Retífica"}
            },
            new String [] {
                "", "Tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabletipo);
        if (tabletipo.getColumnModel().getColumnCount() > 0) {
            tabletipo.getColumnModel().getColumn(0).setMinWidth(40);
            tabletipo.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabletipo.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dados", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

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
        jScrollPane2.setViewportView(tabledocumentos);
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

        jButton4.setText("Incluir");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Excluir");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Documentos", jPanel7);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        tablecontatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Nome", "Cargo", "Telefone", "E-mail"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablecontatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablecontatosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablecontatos);
        if (tablecontatos.getColumnModel().getColumnCount() > 0) {
            tablecontatos.getColumnModel().getColumn(0).setMinWidth(0);
            tablecontatos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablecontatos.getColumnModel().getColumn(0).setMaxWidth(0);
            tablecontatos.getColumnModel().getColumn(1).setMinWidth(40);
            tablecontatos.getColumnModel().getColumn(1).setPreferredWidth(40);
            tablecontatos.getColumnModel().getColumn(1).setMaxWidth(40);
        }

        jButton7.setText("Incluir");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton9.setText("Excluir");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton9))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Contatos", jPanel9);

        jButton10.setText("Histórico de Alterações");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidfornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnsalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtidfornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnsalvar)
                    .addComponent(jButton6)
                    .addComponent(jButton10)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        tabfornecedores.addTab("Dados do Fornecedor", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabfornecedores)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabfornecedores, javax.swing.GroupLayout.Alignment.TRAILING)
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
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnsalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalvarActionPerformed
        //DAO e Bean para criar/alterar novo Fornecedor
        FornecedoresBean fb = new FornecedoresBean();
        FornecedoresDAO fd = new FornecedoresDAO();

        //DAO e Bean para criar documentos do Fornecedor
        FornecedoresDocDAO fdd = new FornecedoresDocDAO();
        FornecedoresDocBean fdb = new FornecedoresDocBean();

        //DAO e Bean para criar/alterar contatos do Fornecedor
        FornecedoresContatosDAO fcd = new FornecedoresContatosDAO();
        FornecedoresContatosBean fcb = new FornecedoresContatosBean();

        //DAO e Bean para criar/alterar tipo do Fonecedor
        FornecedoresTipoDAO ftd = new FornecedoresTipoDAO();
        FornecedoresTipoBean ftb = new FornecedoresTipoBean();

        //DAO e Bean para criar alterações do Fornecedor
        FornecedoresAltDAO fad = new FornecedoresAltDAO();
        FornecedoresAltBean fab = new FornecedoresAltBean();

        //Criar data para criação/alteração
        String data = Dates.criardatadb();

        if (txtidfornecedor.getText().equals("")) {//Se o ID está vazio
            //Dados para criar novo Fornecedor
            fb.setNome(txtnomefornecedor.getText());
            fb.setRazaosocial(txtrazao.getText());
            fb.setCnpj(txtcnpj.getText());
            fb.setIe(txtie.getText());
            fb.setTelefone(txttelefone.getText());
            fb.setLogradouro(txtlogradouro.getText());
            fb.setNumero(txtnumero.getText());
            fb.setComplemento(txtcomplemento.getText());
            fb.setBairro(txtbairro.getText());
            fb.setCidade(txtcidade.getText());
            fb.setUf(cbuf.getSelectedItem().toString());
            fb.setCep(txtcep.getText());
            fb.setData(data);
            fb.setEmailnfe(txtnfe.getText());

            //nome, razaosocial, cnpj, ie, telefone, logradouro, numero, complemento, bairro, cidade, uf, cep, data, emailnfe
            fd.create(fb);

            //Retornar ID do fornecedor criado
            int idcriado = 0;
            for (FornecedoresBean fb2 : fd.readcreated(data)) {
                idcriado = fb2.getId();
            }

            //Salvar tipo de Fornecedor
            ftb.setIdfornecedor(idcriado);
            ftb.setMp((boolean) tabletipo.getValueAt(0, 0));
            ftb.setFerramentas((boolean) tabletipo.getValueAt(1, 0));
            ftb.setRebolo((boolean) tabletipo.getValueAt(2, 0));
            ftb.setOleo((boolean) tabletipo.getValueAt(3, 0));
            ftb.setGravacao((boolean) tabletipo.getValueAt(4, 0));
            ftb.setEmbalagem((boolean) tabletipo.getValueAt(5, 0));
            ftb.setCalibracao((boolean) tabletipo.getValueAt(6, 0));
            ftb.setManutencao((boolean) tabletipo.getValueAt(7, 0));
            ftb.setEscritorio((boolean) tabletipo.getValueAt(8, 0));
            ftb.setLimpeza((boolean) tabletipo.getValueAt(9, 0));
            ftb.setRevestimento((boolean) tabletipo.getValueAt(10, 0));
            ftb.setRetifica((boolean) tabletipo.getValueAt(11, 0));

            //idfornecedor, mp, ferramentas, rebolo, oleo, gravacao, embalagem, calibracao, manutencao, escritorio, limpeza, revestimento, retifica
            ftd.create(ftb);

            //Salvar documentos do Fornecedor
            if (tabledocumentos.getRowCount() > 0) {//Se existem documentos
                for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
                    //Localicação do documento original
                    File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                    //Pasta que será colocar o documento
                    File folder = new File("Q:/MIKE_ERP/fornecedor_arq/" + String.valueOf(idcriado));
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
                        try {
                            SendEmail.EnviarErro(ex.toString());
                            JOptionPane.showMessageDialog(rootPane, "E-mail com erro enviado com sucesso!");
                        } catch (HeadlessException hex) {
                            JOptionPane.showMessageDialog(rootPane, "Erro ao enviar e-mail para suporte!\n" + hex);
                        } catch (AWTException | IOException ex1) {
                            Logger.getLogger(DocumentosFornecedores.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }

                    //Dados do documento para salvar no DB
                    fdb.setIdfornecedor(idcriado);
                    fdb.setDescricao(tabledocumentos.getValueAt(i, 2).toString());
                    fdb.setLocal(filecopy.toString());

                    //idorcamento, descricao, local
                    fdd.create(fdb);
                }
            }

            //Salvar contatos do Fornecedor
            if (tablecontatos.getRowCount() > 0) {//Se existem contatos
                //Dados para criar contato
                for (int i = 0; i < tablecontatos.getRowCount(); i++) {
                    fcb.setIdfornecedor(idcriado);
                    fcb.setNome(tablecontatos.getValueAt(i, 2).toString());
                    fcb.setCargo(tablecontatos.getValueAt(i, 3).toString());
                    fcb.setTelefone(tablecontatos.getValueAt(i, 4).toString());
                    fcb.setEmail(tablecontatos.getValueAt(i, 5).toString());

                    //idfornecedor, nome, cargo, telefone, email
                    fcd.create(fcb);
                }
            }

            //Salvar criação do Fornecedor
            fab.setIdfornecedor(idcriado);
            fab.setData(data);
            fab.setUser(Session.nome);
            fab.setValor("Criação do registro");
            fab.setValoranterior("");
            fab.setValornovo(data);

            //idfornecedor, data, user, valor, valoranterior, valornovo
            fad.create(fab);

            //Zerar campos
            zeracampos();

            //Voltar para primeira tela de Fornecedores
            tabfornecedores.setSelectedIndex(0);

            //Atualizar lista com fonecedores cadastrados
            readtablefornecedores();
        } else {//Se o ID não está vazio
            //Dados para atualizar Fornecedor
            fb.setNome(txtnomefornecedor.getText());
            fb.setRazaosocial(txtrazao.getText());
            fb.setCnpj(txtcnpj.getText());
            fb.setIe(txtie.getText());
            fb.setTelefone(txttelefone.getText());
            fb.setLogradouro(txtlogradouro.getText());
            fb.setNumero(txtnumero.getText());
            fb.setComplemento(txtcomplemento.getText());
            fb.setBairro(txtbairro.getText());
            fb.setCidade(txtcidade.getText());
            fb.setUf(cbuf.getSelectedItem().toString());
            fb.setCep(txtcep.getText());
            fb.setEmailnfe(txtnfe.getText());
            fb.setId(Integer.parseInt(txtidfornecedor.getText()));

            //nome = ?, razaosocial = ?, cnpj = ?, ie = ?, telefone = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, cep = ?, emailnfe = ? WHERE id = ?
            fd.update(fb);

            //Update do tipo de Fornecedor
            JOptionPane.showMessageDialog(null, ftb.isMp());
            ftb.setMp((boolean) tabletipo.getValueAt(0, 0));
            JOptionPane.showMessageDialog(null, (boolean) tabletipo.getValueAt(0, 0));
            JOptionPane.showMessageDialog(null, ftb.isMp());
            ftb.setFerramentas((boolean) tabletipo.getValueAt(1, 0));
            ftb.setRebolo((boolean) tabletipo.getValueAt(2, 0));
            ftb.setOleo((boolean) tabletipo.getValueAt(3, 0));
            ftb.setGravacao((boolean) tabletipo.getValueAt(4, 0));
            ftb.setEmbalagem((boolean) tabletipo.getValueAt(5, 0));
            ftb.setCalibracao((boolean) tabletipo.getValueAt(6, 0));
            ftb.setManutencao((boolean) tabletipo.getValueAt(7, 0));
            ftb.setEscritorio((boolean) tabletipo.getValueAt(8, 0));
            ftb.setLimpeza((boolean) tabletipo.getValueAt(9, 0));
            ftb.setRevestimento((boolean) tabletipo.getValueAt(10, 0));
            ftb.setRetifica((boolean) tabletipo.getValueAt(11, 0));
            ftb.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));

            //mp = ?, ferramentas = ?, rebolo = ?, oleo = ?, gravacao = ?, embalagem = ?, calibracao = ?, manutencao = ?, escritorio = ?, limpeza = ?, revestimento = ?, retifica = ? WHERE idfornecedor = ?
            ftd.update(ftb);

            //Salvar documentos do Fornecedor
            if (tabledocumentos.getRowCount() > 0) {//Se existem documentos
                for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
                    if (tabledocumentos.getValueAt(i, 0).equals("")) {
                        //Localicação do documento original
                        File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                        //Pasta que será colocar o documento
                        File folder = new File("Q:/MIKE_ERP/fornecedor_arq/" + txtidfornecedor.getText());
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
                            try {
                                SendEmail.EnviarErro(ex.toString());
                                JOptionPane.showMessageDialog(rootPane, "E-mail com erro enviado com sucesso!");
                            } catch (HeadlessException hex) {
                                JOptionPane.showMessageDialog(rootPane, "Erro ao enviar e-mail para suporte!\n" + hex);
                            } catch (AWTException | IOException ex1) {
                                Logger.getLogger(DocumentosFornecedores.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        }

                        //Dados do documento para salvar no DB
                        fdb.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                        fdb.setDescricao(tabledocumentos.getValueAt(i, 2).toString());
                        fdb.setLocal(filecopy.toString());

                        //idorcamento, descricao, local
                        fdd.create(fdb);
                    }
                }
            }

            //Salvar contatos do Fornecedor
            if (tablecontatos.getRowCount() > 0) {//Se existem contatos
                //Dados para criar contato
                for (int i = 0; i < tablecontatos.getRowCount(); i++) {
                    if (tablecontatos.getValueAt(i, 0).equals("")) {//Se não tem id, criar
                        fcb.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                        fcb.setNome(tablecontatos.getValueAt(i, 2).toString());
                        fcb.setCargo(tablecontatos.getValueAt(i, 3).toString());
                        fcb.setTelefone(tablecontatos.getValueAt(i, 4).toString());
                        fcb.setEmail(tablecontatos.getValueAt(i, 5).toString());

                        //idfornecedor, nome, cargo, telefone, email
                        fcd.create(fcb);
                    } else {//Se tem id, atualizar
                        fcb.setNome(tablecontatos.getValueAt(i, 2).toString());
                        fcb.setCargo(tablecontatos.getValueAt(i, 3).toString());
                        fcb.setTelefone(tablecontatos.getValueAt(i, 4).toString());
                        fcb.setEmail(tablecontatos.getValueAt(i, 5).toString());
                        fcb.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));

                        //nome = ?, cargo = ?, telefone = ?, email = ? WHERE idfornecedor = ?
                        fcd.update(fcb);
                    }
                }
            }

            //Verificar alterações e salvar caso seja necessário
            if (!txtnomefornecedor.getText().equals(FornecedoresValoresOriginais.nome)) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Nome");
                fab.setValoranterior(FornecedoresValoresOriginais.nome);
                fab.setValornovo(txtnomefornecedor.getText());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (!txtrazao.getText().equals(FornecedoresValoresOriginais.razao)) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Razão Social");
                fab.setValoranterior(FornecedoresValoresOriginais.razao);
                fab.setValornovo(txtrazao.getText());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (!txtcnpj.getText().equals(FornecedoresValoresOriginais.cnpj)) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("CNPJ");
                fab.setValoranterior(FornecedoresValoresOriginais.cnpj);
                fab.setValornovo(txtcnpj.getText());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (!txtie.getText().equals(FornecedoresValoresOriginais.ie)) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Inscrição Estadual");
                fab.setValoranterior(FornecedoresValoresOriginais.ie);
                fab.setValornovo(txtie.getText());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (!txttelefone.getText().equals(FornecedoresValoresOriginais.tel)) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Telefone");
                fab.setValoranterior(FornecedoresValoresOriginais.tel);
                fab.setValornovo(txttelefone.getText());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (!txtnfe.getText().equals(FornecedoresValoresOriginais.nfe)) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("E-mail de nfe");
                fab.setValoranterior(FornecedoresValoresOriginais.nfe);
                fab.setValornovo(txtnfe.getText());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (!txtlogradouro.getText().equals(FornecedoresValoresOriginais.rua)) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Logradouro");
                fab.setValoranterior(FornecedoresValoresOriginais.rua);
                fab.setValornovo(txtlogradouro.getText());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (!txtnumero.getText().equals(FornecedoresValoresOriginais.num)) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Número");
                fab.setValoranterior(FornecedoresValoresOriginais.num);
                fab.setValornovo(txtnumero.getText());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (!txtcomplemento.getText().equals(FornecedoresValoresOriginais.comp)) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Complemento");
                fab.setValoranterior(FornecedoresValoresOriginais.comp);
                fab.setValornovo(txtcomplemento.getText());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (!txtbairro.getText().equals(FornecedoresValoresOriginais.bairro)) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData("Bairro");
                fab.setUser(Session.nome);
                fab.setValoranterior(FornecedoresValoresOriginais.bairro);
                fab.setValornovo(txtbairro.getText());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (!txtcidade.getText().equals(FornecedoresValoresOriginais.cidade)) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Cidade");
                fab.setValoranterior(FornecedoresValoresOriginais.cidade);
                fab.setValornovo(txtcidade.getText());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (!cbuf.getSelectedItem().toString().equals(FornecedoresValoresOriginais.uf)) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Estado");
                fab.setValoranterior(FornecedoresValoresOriginais.uf);
                fab.setValornovo(cbuf.getSelectedItem().toString());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (!txtcep.getText().equals(FornecedoresValoresOriginais.cep)) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("CEP");
                fab.setValoranterior(FornecedoresValoresOriginais.cep);
                fab.setValornovo(txtcep.getText());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            boolean mpn = (boolean) tabletipo.getValueAt(0, 0);
            if (mpn != FornecedoresValoresOriginais.mp) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Fornecedor de Matéria Prima");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.mp));
                fab.setValornovo(String.valueOf(mpn));

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            boolean ferrn = (boolean) tabletipo.getValueAt(1, 0);
            if (ferrn != FornecedoresValoresOriginais.ferr) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Fornecedor de Ferramentas");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.ferr));
                fab.setValornovo(String.valueOf(ferrn));

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            boolean rebolon = (boolean) tabletipo.getValueAt(2, 0);
            if (rebolon != FornecedoresValoresOriginais.reb) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Fornecedor de Rebolo");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.reb));
                fab.setValornovo(txtrazao.getText());

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            boolean oleon = (boolean) tabletipo.getValueAt(3, 0);
            if (oleon != FornecedoresValoresOriginais.oleo) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Fornecedor de Óleo");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.oleo));
                fab.setValornovo(String.valueOf(oleon));

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            boolean gravn = (boolean) tabletipo.getValueAt(4, 0);
            if (gravn != FornecedoresValoresOriginais.grav) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Fornecedor de Material para Gravação");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.grav));
                fab.setValornovo(String.valueOf(gravn));

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            boolean embn = (boolean) tabletipo.getValueAt(5, 0);
            if (embn != FornecedoresValoresOriginais.emb) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Fornecedor de Embalagem");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.emb));
                fab.setValornovo(String.valueOf(embn));

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            boolean caln = (boolean) tabletipo.getValueAt(6, 0);
            if (caln != FornecedoresValoresOriginais.cal) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Fornecedor de Calibração");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.cal));
                fab.setValornovo(String.valueOf(caln));

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            boolean manutencaon = (boolean) tabletipo.getValueAt(7, 0);
            if (manutencaon != FornecedoresValoresOriginais.man) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Fornecedor de Manutenção");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.man));
                fab.setValornovo(String.valueOf(manutencaon));

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            boolean escritorion = (boolean) tabletipo.getValueAt(8, 0);
            if (escritorion != FornecedoresValoresOriginais.esc) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Fornecedor de Material para Escritório");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.esc));
                fab.setValornovo(String.valueOf(escritorion));

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            boolean limpezan = (boolean) tabletipo.getValueAt(9, 0);
            if (limpezan != FornecedoresValoresOriginais.lim) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Fornecedor de Material de Limpeza");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.lim));
                fab.setValornovo(String.valueOf(limpezan));

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            boolean revestn = (boolean) tabletipo.getValueAt(10, 0);
            if (revestn != FornecedoresValoresOriginais.rev) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Fornecedor de Revestimento");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.rev));
                fab.setValornovo(String.valueOf(revestn));

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            boolean retn = (boolean) tabletipo.getValueAt(11, 0);
            if (retn != FornecedoresValoresOriginais.ret) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Fornecedor de Retífica");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.ret));
                fab.setValornovo(String.valueOf(retn));

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (tabledocumentos.getRowCount() != FornecedoresValoresOriginais.rcdoc) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Número de Documentos");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.rcdoc));
                fab.setValornovo(String.valueOf(tabledocumentos.getRowCount()));

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            if (tablecontatos.getRowCount() != FornecedoresValoresOriginais.rccont) {
                fab.setIdfornecedor(Integer.parseInt(txtidfornecedor.getText()));
                fab.setData(data);
                fab.setUser(Session.nome);
                fab.setValor("Número de Contatos");
                fab.setValoranterior(String.valueOf(FornecedoresValoresOriginais.rccont));
                fab.setValornovo(String.valueOf(tablecontatos.getRowCount()));

                //idfornecedor, data, user, valor, valoranterior, valornovo
                fad.create(fab);
            }
            JOptionPane.showMessageDialog(null, "Fornecedor atualizado com sucesso!");

            //Atualizar lista com fonecedores cadastrados
            readtablefornecedores();

        }
    }//GEN-LAST:event_btnsalvarActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int i = JOptionPane.showConfirmDialog(rootPane, "Deseja cadastrar um novo fornecedor?", "Novo Cadastro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (i == 0) {
            zeracampos();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tableofornecedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableofornecedoresMouseClicked
        //Verificar número de cliques
        if (evt.getClickCount() == 2) {//Se forem 2 cliques
            //Colocar o ID no txt
            txtidfornecedor.setText(tableofornecedores.getValueAt(tableofornecedores.getSelectedRow(), 0).toString());
            //Mudar a aba para os dados do Fornecedor
            tabfornecedores.setSelectedIndex(1);
            //Variável de ID
            int idfornecedor = Integer.parseInt(txtidfornecedor.getText());

            //DAO para buscar dados
            FornecedoresDAO fd = new FornecedoresDAO();
            FornecedoresTipoDAO ftd = new FornecedoresTipoDAO();
            FornecedoresContatosDAO fcd = new FornecedoresContatosDAO();
            FornecedoresDocDAO fdd = new FornecedoresDocDAO();

            //Colocar dados do Fornecedor de acordo com ID nos txt's
            for (FornecedoresBean fb : fd.click(idfornecedor)) {
                txtnomefornecedor.setText(fb.getNome());
                txtrazao.setText(fb.getRazaosocial());
                txtcnpj.setText(fb.getCnpj());
                txtie.setText(fb.getIe());
                txttelefone.setText(fb.getTelefone());
                txtlogradouro.setText(fb.getLogradouro());
                txtnumero.setText(fb.getNumero());
                txtcomplemento.setText(fb.getComplemento());
                txtbairro.setText(fb.getBairro());
                txtcidade.setText(fb.getCidade());
                cbuf.setSelectedItem(fb.getUf());
                txtcep.setText(fb.getCep());
                txtnfe.setText(fb.getEmailnfe());
            }

            //Colocar tabelas em Default
            DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
            DefaultTableModel modelcont = (DefaultTableModel) tablecontatos.getModel();

            //Colocar Tipos do Fornecedor de acordo com ID
            for (FornecedoresTipoBean ftb : ftd.read(idfornecedor)) {
                tabletipo.setValueAt(ftb.isMp(), 0, 0);
                tabletipo.setValueAt(ftb.isFerramentas(), 1, 0);
                tabletipo.setValueAt(ftb.isRebolo(), 2, 0);
                tabletipo.setValueAt(ftb.isOleo(), 3, 0);
                tabletipo.setValueAt(ftb.isGravacao(), 4, 0);
                tabletipo.setValueAt(ftb.isEmbalagem(), 5, 0);
                tabletipo.setValueAt(ftb.isCalibracao(), 6, 0);
                tabletipo.setValueAt(ftb.isManutencao(), 7, 0);
                tabletipo.setValueAt(ftb.isEscritorio(), 8, 0);
                tabletipo.setValueAt(ftb.isLimpeza(), 9, 0);
                tabletipo.setValueAt(ftb.isRevestimento(), 10, 0);
                tabletipo.setValueAt(ftb.isRetifica(), 11, 0);
            }

            //Colocar Documentos do Fornecedor de acordo com ID
            modeldoc.setNumRows(0);
            for (FornecedoresDocBean fdb : fdd.read(idfornecedor)) {
                modeldoc.addRow(new Object[]{
                    fdb.getId(),
                    false,
                    fdb.getDescricao(),
                    fdb.getLocal(),
                    ""
                });
            }

            //Colocar Contatos do Fornecedor de acordo com ID
            modelcont.setNumRows(0);
            for (FornecedoresContatosBean fcb : fcd.read(idfornecedor)) {
                modelcont.addRow(new Object[]{
                    fcb.getId(),
                    false,
                    fcb.getNome(),
                    fcb.getCargo(),
                    fcb.getTelefone(),
                    fcb.getEmail()
                });
            }

            //Pegar valores originais de cada campo monitorado
            orgnome();
            orgrazao();
            orgcnpj();
            orgie();
            orgtel();
            orgnfe();
            orgrua();
            orgnum();
            orgcomp();
            orgbairro();
            orgcidade();
            orguf();
            orgcep();
            orgmp();
            orgferr();
            orgreb();
            orgoleo();
            orggrav();
            orgemb();
            orgcal();
            orgman();
            orgesc();
            orglim();
            orgrev();
            orgret();
            orgdoc();
            orgcont();
        }
    }//GEN-LAST:event_tableofornecedoresMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        //        if (txtcep.getText().equals("")) {
        //            JOptionPane.showMessageDialog(rootPane, "Coloque um CEP!");
        //        } else {
        //            WebServiceCep w = WebServiceCep.searchCep(txtcep.getText());
        //            if (w.wasSuccessful()) {
        //                txtlogradouro.setText(w.getLogradouroFull());
        //                txtbairro.setText(w.getBairro());
        //                txtcidade.setText(w.getCidade());
        //                cbuf.setSelectedItem(w.getUf());
        //                txtnumero.requestFocus();
        //            } else {
        //                JOptionPane.showMessageDialog(rootPane, "CEP incorreto!");
        //            }
        //        }
        if (txtcep.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Coloque um CEP!");
        } else {
            try {
                consultaCEP(txtcep.getText());
                if (!consultaCEP(txtcep.getText()).getEnd().equals("")) {
                    txtlogradouro.setText(consultaCEP(txtcep.getText()).getEnd());
                    txtbairro.setText(consultaCEP(txtcep.getText()).getBairro());
                    txtcidade.setText(consultaCEP(txtcep.getText()).getCidade());
                    cbuf.setSelectedItem(consultaCEP(txtcep.getText()).getUf());
                    txtnumero.requestFocus();
                }
            } catch (SigepClienteException ex) {
                Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(rootPane, "Erro ao consultar! \n" + ex);
            } catch (SQLException_Exception ex) {
                Logger.getLogger(Fornecedores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        DocumentosFornecedores p = new DocumentosFornecedores();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension jif = p.getSize();
        Dimension d = desk.getSize();
        p.setLocation((d.width - jif.width) / 2, (d.height - jif.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (txtidfornecedor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum fornecedor selecionado.");
        } else {
            FornecedoresHistorico p = new FornecedoresHistorico();
            JDesktopPane desk = this.getDesktopPane();
            desk.add(p);
            Dimension jif = p.getSize();
            Dimension d = desk.getSize();
            p.setLocation((d.width - jif.width) / 2, (d.height - jif.height) / 2);
            p.setVisible(true);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        FornecedoresContatoAdd p = new FornecedoresContatoAdd();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension jif = p.getSize();
        Dimension d = desk.getSize();
        p.setLocation((d.width - jif.width) / 2, (d.height - jif.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tablecontatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablecontatosMouseClicked
        //Verificar quantidade de cliques para abrir tela com dados do contato
        if (evt.getClickCount() == 2) {
            FornecedoresContatoAdd p = new FornecedoresContatoAdd();
            JDesktopPane desk = this.getDesktopPane();
            desk.add(p);
            Dimension jif = p.getSize();
            Dimension d = desk.getSize();
            p.setLocation((d.width - jif.width) / 2, (d.height - jif.height) / 2);
            FornecedoresContatoAdd.lbllinha.setText(String.valueOf(tablecontatos.getSelectedRow()));
            FornecedoresContatoAdd.txtnome.setText(String.valueOf(tablecontatos.getValueAt(tablecontatos.getSelectedRow(), 2)));
            FornecedoresContatoAdd.txtcargo.setText(String.valueOf(tablecontatos.getValueAt(tablecontatos.getSelectedRow(), 3)));
            FornecedoresContatoAdd.txttel.setText(String.valueOf(tablecontatos.getValueAt(tablecontatos.getSelectedRow(), 4)));
            FornecedoresContatoAdd.txtemail.setText(String.valueOf(tablecontatos.getValueAt(tablecontatos.getSelectedRow(), 5)));
            p.setVisible(true);
        }
    }//GEN-LAST:event_tablecontatosMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        for (int i = 0; i < tabletipo.getRowCount(); i++) {
            JOptionPane.showMessageDialog(null, tabletipo.getValueAt(i, 0));
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnsalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JComboBox<String> cbuf;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane tabfornecedores;
    public static javax.swing.JTable tablecontatos;
    public static javax.swing.JTable tabledocumentos;
    public static javax.swing.JTable tableofornecedores;
    public static javax.swing.JTable tabletipo;
    public static javax.swing.JTextField txtbairro;
    public static javax.swing.JFormattedTextField txtcep;
    public static javax.swing.JTextField txtcidade;
    public static javax.swing.JFormattedTextField txtcnpj;
    public static javax.swing.JTextField txtcomplemento;
    public static javax.swing.JTextField txtidfornecedor;
    public static javax.swing.JTextField txtie;
    public static javax.swing.JTextField txtlogradouro;
    public static javax.swing.JTextField txtnfe;
    public static javax.swing.JTextField txtnomefornecedor;
    public static javax.swing.JTextField txtnumero;
    public static javax.swing.JTextField txtrazao;
    public static javax.swing.JFormattedTextField txttelefone;
    // End of variables declaration//GEN-END:variables
    private static EnderecoERP consultaCEP(java.lang.String cep) throws SigepClienteException, SQLException_Exception {
        br.com.correios.AtendeClienteService service = new br.com.correios.AtendeClienteService();
        br.com.correios.AtendeCliente port = service.getAtendeClientePort();
        return port.consultaCEP(cep);
    }

}
