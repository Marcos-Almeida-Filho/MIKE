/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.fiscal;

import Connection.Session;
import DAO.NotaFiscalDAO;
import DAO.NotaFiscalItensDAO;
import DAO.NotaFiscalDuplicatasDAO;
import DAO.CARDAO;
import DAO.VendasMateriaisDAO;
import DAO.VendasMateriaisMovDAO;
import Methods.Dates;
import Methods.SendEmail;
import Methods.Telas;
import Methods.Valores;
import View.Geral.ProcuraXML;
import java.io.File;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class NotasFiscais extends javax.swing.JInternalFrame {

    static NotaFiscalDAO nfd = new NotaFiscalDAO();
    static NotaFiscalItensDAO nfid = new NotaFiscalItensDAO();
    static NotaFiscalDuplicatasDAO nfdd = new NotaFiscalDuplicatasDAO();
    static CARDAO cd = new CARDAO();
    static VendasMateriaisDAO vmd = new VendasMateriaisDAO();
    static VendasMateriaisMovDAO vmmd = new VendasMateriaisMovDAO();

    public static String pasta = "";

    /**
     * Creates new form notasFiscais
     */
    public NotasFiscais() {
        initComponents();
        lerNotasFiscais();
    }

    public static void lerNotasFiscais() {
        DefaultTableModel model = (DefaultTableModel) tableNF.getModel();
        model.setNumRows(0);

        String pesquisa = txtpesquisa.getText();

        if (pesquisa.equals("")) {
            nfd.readNotas().forEach(nfb -> {
                model.addRow(new Object[]{
                    nfb.getId(),
                    false,
                    nfb.getNumero(),
                    nfb.getDestinatario(),
                    nfb.getNatureza(),
                    Dates.TransformarDataCompletaDoDB(nfb.getDataEmissao()),
                    nfb.getStatus()
                });
            });
        } else {
            nfd.readNotasPesquisa(pesquisa).forEach(nfb -> {
                model.addRow(new Object[]{
                    nfb.getId(),
                    false,
                    nfb.getNumero(),
                    nfb.getDestinatario(),
                    nfb.getNatureza(),
                    Dates.TransformarDataCompletaDoDB(nfb.getDataEmissao()),
                    nfb.getStatus()
                });
            });
        }

    }

    public void readNF(int numero) {
        nfd.readNota(numero).forEach(nfb -> {
            //Dados Nota Fiscal
            txtNumero.setText(String.valueOf(numero));
            txtDataEmissao.setText(Dates.TransformarDataCompletaDoDB(nfb.getDataEmissao()));
            txtStatus.setText(nfb.getStatus());
            txtNatureza.setText(nfb.getNatureza());
            txtObs.setText(nfb.getObs());
            checkVenda.setSelected(nfb.getVenda());

            //Dados Destinatário
            txtRazaoD.setText(nfb.getDestinatario());
            txtCNPJD.setText(nfb.getCnpjD());
            txtIED.setText(nfb.getIeD());
            txtLogradouroD.setText(nfb.getLogradouroD());
            txtNumD.setText(nfb.getNumeroD());
            txtComplementoD.setText(nfb.getComplementoD());
            txtBairroD.setText(nfb.getBairroD());
            txtCidadeD.setText(nfb.getCidadeD());
            txtUFD.setText(nfb.getUfD());
            txtCEPD.setText(nfb.getCepD());

            //Dados Transportadora
            txtRazaoT.setText(nfb.getTransportadora());
            txtCNPJT.setText(nfb.getCnpjT());
            txtIET.setText(nfb.getIeT());
            txtEnderecoT.setText(nfb.getEnderecoT());
            txtCidadeT.setText(nfb.getCidadeT());
            txtUFT.setText(nfb.getUfT());

            //Valores
            txtBaseICMS.setText(Valores.TransformarDoubleDBemDinheiroComLocal(nfb.getBaseIcms()));
            txtICMS.setText(Valores.TransformarDoubleDBemDinheiroComLocal(nfb.getValorIcms()));
            txtBaseICMSST.setText(Valores.TransformarDoubleDBemDinheiroComLocal(nfb.getBaseIcmsSt()));
            txtICMSST.setText(Valores.TransformarDoubleDBemDinheiroComLocal(nfb.getValorIcmsSt()));
            txtPIS.setText(Valores.TransformarDoubleDBemDinheiroComLocal(nfb.getValorPis()));
            txtCofins.setText(Valores.TransformarDoubleDBemDinheiroComLocal(nfb.getValorCofins()));
            txtIPI.setText(Valores.TransformarDoubleDBemDinheiroComLocal(nfb.getValorIpi()));
            txtFrete.setText(Valores.TransformarDoubleDBemDinheiroComLocal(nfb.getValorFrete()));
            txtTotalProdutos.setText(Valores.TransformarDoubleDBemDinheiroComLocal(nfb.getValorProdutos()));
            txtTotalNF.setText(Valores.TransformarDoubleDBemDinheiroComLocal(nfb.getValorTotalNotaFiscal()));
        });

        readItensNF(numero);
    }

    public void readItensNF(int numero) {
        DefaultTableModel model = (DefaultTableModel) tableItens.getModel();
        model.setNumRows(0);

        nfid.readItens(numero).forEach(nfib -> {
            model.addRow(new Object[]{
                nfib.getId(),
                nfib.getIdMaterial(),
                nfib.getCodigo(),
                nfib.getDescricao(),
                nfib.getQtd(),
                nfib.getValorUnitario(),
                nfib.getValorTotal()
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

        tabNF = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableNF = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtpesquisa = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        txtDataEmissao = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        txtNatureza = new javax.swing.JTextField();
        checkVenda = new javax.swing.JCheckBox();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableItens = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtBaseICMS = new javax.swing.JTextField();
        txtICMS = new javax.swing.JTextField();
        txtBaseICMSST = new javax.swing.JTextField();
        txtICMSST = new javax.swing.JTextField();
        txtPIS = new javax.swing.JTextField();
        txtCofins = new javax.swing.JTextField();
        txtIPI = new javax.swing.JTextField();
        txtFrete = new javax.swing.JTextField();
        txtTotalProdutos = new javax.swing.JTextField();
        txtTotalNF = new javax.swing.JTextField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtRazaoD = new javax.swing.JTextField();
        txtCNPJD = new javax.swing.JTextField();
        txtIED = new javax.swing.JTextField();
        txtLogradouroD = new javax.swing.JTextField();
        txtNumD = new javax.swing.JTextField();
        txtComplementoD = new javax.swing.JTextField();
        txtBairroD = new javax.swing.JTextField();
        txtCidadeD = new javax.swing.JTextField();
        txtUFD = new javax.swing.JTextField();
        txtCEPD = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        txtRazaoT = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtCNPJT = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtIET = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtEnderecoT = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtCidadeT = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtUFT = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Notas Fiscais");

        tabNF.setBackground(new java.awt.Color(255, 255, 255));
        tabNF.setName("tabNF"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tableNF.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Número", "Destinatário", "Natureza de Operação", "Data de Emissão", "Status"
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
        tableNF.setName("tableNF"); // NOI18N
        tableNF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableNFMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableNF);
        if (tableNF.getColumnModel().getColumnCount() > 0) {
            tableNF.getColumnModel().getColumn(0).setMinWidth(0);
            tableNF.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableNF.getColumnModel().getColumn(0).setMaxWidth(0);
            tableNF.getColumnModel().getColumn(1).setMinWidth(35);
            tableNF.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableNF.getColumnModel().getColumn(1).setMaxWidth(35);
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

        jButton1.setText("Cancelar Nota Fiscal");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Lançar por XML");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        tabNF.addTab("Notas Fiscais", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setName("jPanel2"); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Nota Fiscal"));
        jPanel4.setName("jPanel4"); // NOI18N

        jLabel1.setText("Número");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel3.setText("Status");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("Data de Emissão");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText("Natureza de Operação");
        jLabel5.setName("jLabel5"); // NOI18N

        txtNumero.setEditable(false);
        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumero.setName("txtNumero"); // NOI18N

        txtDataEmissao.setEditable(false);
        txtDataEmissao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataEmissao.setName("txtDataEmissao"); // NOI18N

        txtStatus.setEditable(false);
        txtStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStatus.setName("txtStatus"); // NOI18N

        txtNatureza.setEditable(false);
        txtNatureza.setName("txtNatureza"); // NOI18N

        checkVenda.setText("Venda");
        checkVenda.setName("checkVenda"); // NOI18N
        checkVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkVendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNatureza, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkVenda))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNatureza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkVenda)))
        );

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setName("jPanel5"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tableItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "IDMaterial", "Código", "Descrição", "Quantidade", "Valor Unitário", "Valor Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableItens.setName("tableItens"); // NOI18N
        jScrollPane2.setViewportView(tableItens);
        if (tableItens.getColumnModel().getColumnCount() > 0) {
            tableItens.getColumnModel().getColumn(0).setMinWidth(0);
            tableItens.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableItens.getColumnModel().getColumn(0).setMaxWidth(0);
            tableItens.getColumnModel().getColumn(1).setMinWidth(0);
            tableItens.getColumnModel().getColumn(1).setPreferredWidth(0);
            tableItens.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Itens", jPanel5);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setName("jPanel7"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        txtObs.setColumns(20);
        txtObs.setRows(5);
        txtObs.setName("txtObs"); // NOI18N
        jScrollPane3.setViewportView(txtObs);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Observações", jPanel7);

        jPanel8.setName("jPanel8"); // NOI18N

        jLabel2.setText("Base ICMS");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel20.setText("Valor ICMS");
        jLabel20.setName("jLabel20"); // NOI18N

        jLabel21.setText("Base ICMS ST");
        jLabel21.setName("jLabel21"); // NOI18N

        jLabel24.setText("Valor ICMS ST");
        jLabel24.setName("jLabel24"); // NOI18N

        jLabel25.setText("Valor PIS");
        jLabel25.setName("jLabel25"); // NOI18N

        jLabel26.setText("Valor Cofins");
        jLabel26.setName("jLabel26"); // NOI18N

        jLabel27.setText("Valor IPI");
        jLabel27.setName("jLabel27"); // NOI18N

        jLabel28.setText("Valor Frete");
        jLabel28.setName("jLabel28"); // NOI18N

        jLabel29.setText("Valor Produtos");
        jLabel29.setName("jLabel29"); // NOI18N

        jLabel30.setText("Valor Nota Fiscal");
        jLabel30.setName("jLabel30"); // NOI18N

        txtBaseICMS.setEditable(false);
        txtBaseICMS.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBaseICMS.setName("txtBaseICMS"); // NOI18N

        txtICMS.setEditable(false);
        txtICMS.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtICMS.setName("txtICMS"); // NOI18N

        txtBaseICMSST.setEditable(false);
        txtBaseICMSST.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBaseICMSST.setName("txtBaseICMSST"); // NOI18N

        txtICMSST.setEditable(false);
        txtICMSST.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtICMSST.setName("txtICMSST"); // NOI18N

        txtPIS.setEditable(false);
        txtPIS.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPIS.setName("txtPIS"); // NOI18N

        txtCofins.setEditable(false);
        txtCofins.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCofins.setName("txtCofins"); // NOI18N

        txtIPI.setEditable(false);
        txtIPI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIPI.setName("txtIPI"); // NOI18N

        txtFrete.setEditable(false);
        txtFrete.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFrete.setName("txtFrete"); // NOI18N

        txtTotalProdutos.setEditable(false);
        txtTotalProdutos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalProdutos.setName("txtTotalProdutos"); // NOI18N

        txtTotalNF.setEditable(false);
        txtTotalNF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalNF.setName("txtTotalNF"); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPIS, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIPI, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBaseICMS, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBaseICMSST, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(94, 94, 94)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalNF, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFrete, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCofins, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtICMSST, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtICMS, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(380, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel20)
                    .addComponent(txtBaseICMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtICMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel24)
                    .addComponent(txtBaseICMSST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtICMSST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(txtPIS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCofins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(txtIPI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(txtTotalProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalNF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        jTabbedPane1.addTab("Valores", jPanel8);

        jTabbedPane2.setName("jTabbedPane2"); // NOI18N

        jPanel9.setName("jPanel9"); // NOI18N

        jLabel6.setText("Razão Social");
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText("CNPJ");
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText("I.E.");
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setText("Logradouro");
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel10.setText("Número");
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel11.setText("Complemento");
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel12.setText("Cidade");
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel13.setText("UF");
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setText("CEP");
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel15.setText("Bairro");
        jLabel15.setName("jLabel15"); // NOI18N

        txtRazaoD.setEditable(false);
        txtRazaoD.setName("txtRazaoD"); // NOI18N

        txtCNPJD.setEditable(false);
        txtCNPJD.setName("txtCNPJD"); // NOI18N

        txtIED.setEditable(false);
        txtIED.setName("txtIED"); // NOI18N

        txtLogradouroD.setEditable(false);
        txtLogradouroD.setName("txtLogradouroD"); // NOI18N

        txtNumD.setEditable(false);
        txtNumD.setName("txtNumD"); // NOI18N

        txtComplementoD.setEditable(false);
        txtComplementoD.setName("txtComplementoD"); // NOI18N

        txtBairroD.setEditable(false);
        txtBairroD.setName("txtBairroD"); // NOI18N

        txtCidadeD.setEditable(false);
        txtCidadeD.setName("txtCidadeD"); // NOI18N

        txtUFD.setEditable(false);
        txtUFD.setName("txtUFD"); // NOI18N

        txtCEPD.setEditable(false);
        txtCEPD.setName("txtCEPD"); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBairroD, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCidadeD, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUFD, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCEPD, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLogradouroD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumD, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtComplementoD, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRazaoD, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCNPJD, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIED, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtRazaoD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCNPJD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtIED, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtLogradouroD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtNumD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtComplementoD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(txtBairroD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCidadeD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUFD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCEPD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Destinatário", jPanel9);

        jPanel10.setName("jPanel10"); // NOI18N

        txtRazaoT.setEditable(false);
        txtRazaoT.setName("txtRazaoT"); // NOI18N

        jLabel16.setText("Razão Social");
        jLabel16.setName("jLabel16"); // NOI18N

        txtCNPJT.setEditable(false);
        txtCNPJT.setName("txtCNPJT"); // NOI18N

        jLabel17.setText("CNPJ");
        jLabel17.setName("jLabel17"); // NOI18N

        txtIET.setEditable(false);
        txtIET.setName("txtIET"); // NOI18N

        jLabel18.setText("I.E.");
        jLabel18.setName("jLabel18"); // NOI18N

        txtEnderecoT.setEditable(false);
        txtEnderecoT.setName("txtEnderecoT"); // NOI18N

        jLabel19.setText("Endereço");
        jLabel19.setName("jLabel19"); // NOI18N

        jLabel22.setText("Cidade");
        jLabel22.setName("jLabel22"); // NOI18N

        txtCidadeT.setEditable(false);
        txtCidadeT.setName("txtCidadeT"); // NOI18N

        jLabel23.setText("UF");
        jLabel23.setName("jLabel23"); // NOI18N

        txtUFT.setEditable(false);
        txtUFT.setName("txtUFT"); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCidadeT, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUFT, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 150, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEnderecoT, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRazaoT, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCNPJT, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIET, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtRazaoT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtCNPJT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtIET, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtEnderecoT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(txtCidadeT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUFT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Transportadora", jPanel10);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jTabbedPane2))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        tabNF.addTab("Nota Fiscal", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabNF)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabNF)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ProcuraXML pxml = new ProcuraXML("NotasFiscais");
        File dir = new File(pasta);
        pxml.chooser.setCurrentDirectory(dir);
        Telas.AparecerTela(pxml);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int numTrue = 0;
        for (int i = 0; i < tableNF.getRowCount(); i++) {
            if (tableNF.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma nota selecionada.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja cancelar as Notas Fiscais selecionadas?", "Cancelar Nota Fiscal", JOptionPane.YES_NO_OPTION);

            if (resp == 0) {
                for (int i = 0; i < tableNF.getRowCount(); i++) {
                    if (tableNF.getValueAt(i, 1).equals(true)) {
                        int numero = Integer.parseInt(tableNF.getValueAt(i, 2).toString());
                        nfd.cancelarNota(numero);
                        nfid.readItens(numero).forEach(nfib -> {
                            if (nfib.getIdMaterial() != 0) {
                                double estoqueAtual = vmd.readEstoque(nfib.getIdMaterial());
                                double qtdItem = nfib.getQtd();
                                double estoque = estoqueAtual + qtdItem;
                                vmd.updateEstoque(estoque, nfib.getIdMaterial());

                                try {
                                    vmmd.create(nfib.getIdMaterial(), estoqueAtual, qtdItem, estoque, "Nota Fiscal " + numero + " Cancelada", Dates.CriarDataCurtaDBSemDataExistente(), Session.nome);
                                } catch (SQLException e) {
                                    String msg = "Erro ao criar movimentação do Material de Venda.";
                                    JOptionPane.showMessageDialog(null, msg);

                                    new Thread() {
                                        @Override
                                        public void run() {
                                            SendEmail.EnviarErro2(msg, e);
                                        }
                                    }.start();
                                }
                            }
                        });
                        cd.cancelarConta(numero);
                    }
                }
                lerNotasFiscais();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tableNFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableNFMouseClicked
        if (evt.getClickCount() == 2) {
            tabNF.setSelectedIndex(1);
            readNF(Integer.parseInt(tableNF.getValueAt(tableNF.getSelectedRow(), 2).toString()));
        }
    }//GEN-LAST:event_tableNFMouseClicked

    private void txtpesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpesquisaKeyReleased
        lerNotasFiscais();
    }//GEN-LAST:event_txtpesquisaKeyReleased

    private void checkVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkVendaActionPerformed
        if (txtNumero.getText().length() > 0) {
            try {
                nfd.updateVendas(Integer.parseInt(txtNumero.getText()), checkVenda.isSelected());
                
                JOptionPane.showMessageDialog(null, "Status de Venda alterado com sucesso!");
            } catch (SQLException e) {
                String msg = "Erro.";
                
                JOptionPane.showMessageDialog(null, msg + "\n" + e);
                
                new Thread() {
                    @Override
                    public void run() {
                        SendEmail.EnviarErro2(msg, e);
                    }
                }.start();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma nota selecionada.");
            checkVenda.setSelected(false);
        }
    }//GEN-LAST:event_checkVendaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBox checkVenda;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    public javax.swing.JLabel jLabel17;
    public javax.swing.JLabel jLabel18;
    public javax.swing.JLabel jLabel19;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel20;
    public javax.swing.JLabel jLabel21;
    public javax.swing.JLabel jLabel22;
    public javax.swing.JLabel jLabel23;
    public javax.swing.JLabel jLabel24;
    public javax.swing.JLabel jLabel25;
    public javax.swing.JLabel jLabel26;
    public javax.swing.JLabel jLabel27;
    public javax.swing.JLabel jLabel28;
    public javax.swing.JLabel jLabel29;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel30;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel10;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JPanel jPanel8;
    public javax.swing.JPanel jPanel9;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTabbedPane jTabbedPane2;
    public javax.swing.JTabbedPane tabNF;
    public static javax.swing.JTable tableItens;
    public static javax.swing.JTable tableNF;
    public javax.swing.JTextField txtBairroD;
    public static javax.swing.JTextField txtBaseICMS;
    public static javax.swing.JTextField txtBaseICMSST;
    public javax.swing.JTextField txtCEPD;
    public javax.swing.JTextField txtCNPJD;
    public javax.swing.JTextField txtCNPJT;
    public javax.swing.JTextField txtCidadeD;
    public javax.swing.JTextField txtCidadeT;
    public static javax.swing.JTextField txtCofins;
    public javax.swing.JTextField txtComplementoD;
    public javax.swing.JTextField txtDataEmissao;
    public javax.swing.JTextField txtEnderecoT;
    public static javax.swing.JTextField txtFrete;
    public static javax.swing.JTextField txtICMS;
    public static javax.swing.JTextField txtICMSST;
    public javax.swing.JTextField txtIED;
    public javax.swing.JTextField txtIET;
    public static javax.swing.JTextField txtIPI;
    public javax.swing.JTextField txtLogradouroD;
    public javax.swing.JTextField txtNatureza;
    public javax.swing.JTextField txtNumD;
    public javax.swing.JTextField txtNumero;
    public javax.swing.JTextArea txtObs;
    public static javax.swing.JTextField txtPIS;
    public javax.swing.JTextField txtRazaoD;
    public javax.swing.JTextField txtRazaoT;
    public javax.swing.JTextField txtStatus;
    public static javax.swing.JTextField txtTotalNF;
    public static javax.swing.JTextField txtTotalProdutos;
    public javax.swing.JTextField txtUFD;
    public javax.swing.JTextField txtUFT;
    public static javax.swing.JTextField txtpesquisa;
    // End of variables declaration//GEN-END:variables
}
