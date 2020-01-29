/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.financeiro;

import Bean.CAPBean;
import DAO.BancosDAO;
import DAO.CAPDAO;
import DAO.CAPDocumentosDAO;
import Methods.Dates;
import Methods.Docs;
import Methods.Telas;
import View.Geral.ProcurarFornecedor;
import static View.financeiro.ContasPagar.readtablecap;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class ContaPagar extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdicionarContasPagar
     */
    public static int id;

    public ContaPagar(int idi) {
        initComponents();
        id = idi;
        readdados();
        data();
        readcbbancos();
        travacampos();
        camposcbmetodo();
    }

    public static void readdados() {
        //DAOs para pesquisa
        CAPDAO capd = new CAPDAO();
        CAPDocumentosDAO cdd = new CAPDocumentosDAO();

        txtid.setText(String.valueOf(id));

        capd.click(id).forEach(cb -> {
            txtdatalancamento.setText(cb.getDatalancamento());
            txtfornecedor.setText(cb.getFornecedor());
            txtnf.setText(cb.getNumero());
            Dates.SetarDataJDateChooser(dateemissao, cb.getDataemissao());
            txttotal.setText(cb.getTotal());
            txtparcela.setText(cb.getParcela());
            txtvalorparcela.setText(cb.getValorparcela());
            Dates.SetarDataJDateChooser(datevencimento, cb.getDataparcela());
            if (cb.getDatapagamento() != null) {
                Dates.SetarDataJDateChooser(datepagamento, cb.getDatapagamento());
            }
            if (cb.getBanco() != null) {
                cbbanco.addItem(cb.getBanco());
                cbbanco.setSelectedItem(cb.getBanco());
            }
            if (cb.getMetodo() != null) {
                cbmetodo.setSelectedItem(cb.getMetodo());
            }
            if (cb.getCheque() != null) {
                txtcheque.setText(cb.getCheque());
            }
        });

        //DefaultTableModel para adicionar linhas
        DefaultTableModel modeldoc = (DefaultTableModel) ContaPagar.tabledocs.getModel();

        cdd.readitens(id).forEach(cdb -> {
            modeldoc.addRow(new Object[]{
                cdb.getId(),
                false,
                cdb.getDescricao(),
                cdb.getLocal()
            });
        });
    }

    public static void camposcbmetodo() {
        String metodo = cbmetodo.getSelectedItem().toString();
        switch (metodo) {
            case "Selecione":
                lblbanco.setVisible(false);
                cbbanco.setVisible(false);
                lblcheque.setVisible(false);
                txtcheque.setVisible(false);
                break;
            case "Boleto":
                lblbanco.setVisible(true);
                cbbanco.setVisible(true);
                lblcheque.setVisible(false);
                txtcheque.setVisible(false);
                break;
            case "Depósito":
                lblbanco.setVisible(false);
                cbbanco.setVisible(false);
                lblcheque.setVisible(false);
                txtcheque.setVisible(false);
                break;
            case "Transferência":
                lblbanco.setVisible(true);
                cbbanco.setVisible(true);
                lblcheque.setVisible(false);
                txtcheque.setVisible(false);
                break;
            case "Cartão de Crédito":
                lblbanco.setVisible(false);
                cbbanco.setVisible(false);
                lblcheque.setVisible(false);
                txtcheque.setVisible(false);
                break;
            case "Cheque":
                lblbanco.setVisible(false);
                cbbanco.setVisible(false);
                lblcheque.setVisible(true);
                txtcheque.setVisible(true);
                break;
            case "Dinheiro":
                lblbanco.setVisible(false);
                cbbanco.setVisible(false);
                lblcheque.setVisible(false);
                txtcheque.setVisible(false);
                break;
        }
    }

    public static void data() {
        Calendar c = Calendar.getInstance();
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        txtdatalancamento.setText(simpleDateFormat.format(c.getTime()));
    }

    public static void readcbbancos() {
        BancosDAO bd = new BancosDAO();

        bd.read().forEach(bb -> {
            cbbanco.addItem(bb.getBanco());
        });
    }

    public static void travacampos() {
        if (datepagamento.getCalendar() != null) {
            txtfornecedor.setEditable(false);
            btnprocurar.setEnabled(false);
            jButton1.setEnabled(false);
            txtnf.setEditable(false);
            dateemissao.setEnabled(false);
            txttotal.setEditable(false);
            txtparcela.setEditable(false);
            txtvalorparcela.setEditable(false);
            datevencimento.setEnabled(false);
            datepagamento.setEnabled(false);
            cbbanco.setEnabled(false);
            cbmetodo.setEnabled(false);
        }
    }

    public static void zeracampos() {
        txtid.setText("");
        txtfornecedor.setText("");
        txtnf.setText("");
        dateemissao.setCalendar(null);
        txttotal.setText("");
        txtparcela.setText("");
        txtvalorparcela.setText("");
        datevencimento.setCalendar(null);
        datepagamento.setCalendar(null);
        cbbanco.setSelectedIndex(0);
        cbmetodo.setSelectedIndex(0);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtdatalancamento = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtnf = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtfornecedor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txttotal = new javax.swing.JTextField();
        btnprocurar = new javax.swing.JButton();
        dateemissao = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtparcela = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtvalorparcela = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        datevencimento = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbmetodo = new javax.swing.JComboBox<>();
        lblcheque = new javax.swing.JLabel();
        txtcheque = new javax.swing.JTextField();
        datepagamento = new com.toedter.calendar.JDateChooser();
        lblbanco = new javax.swing.JLabel();
        cbbanco = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabledocs = new javax.swing.JTable();

        setClosable(true);
        setResizable(true);
        setTitle("Conta A Pagar");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("ID");

        jLabel2.setText("Data de Lançamento");

        txtid.setEditable(false);

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtdatalancamento.setEditable(false);
        txtdatalancamento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        txtdatalancamento.setToolTipText("dd/mm/aaaa");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jLabel3.setText("Nota Fiscal");

        txtnf.setEditable(false);
        txtnf.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel4.setText("Fornecedor");

        txtfornecedor.setEditable(false);

        jLabel5.setText("Data de Emissão");

        jLabel6.setText("Valor Total R$");

        txttotal.setEditable(false);
        txttotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txttotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txttotalFocusLost(evt);
            }
        });

        btnprocurar.setText("Procurar");
        btnprocurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprocurarActionPerformed(evt);
            }
        });

        dateemissao.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnf, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateemissao, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnprocurar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtfornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnprocurar))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtnf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateemissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Contas A Pagar"));

        jLabel7.setText("Parcela");

        txtparcela.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtparcela.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtparcelaFocusLost(evt);
            }
        });

        jLabel8.setText("Valor de Parcela R$");

        txtvalorparcela.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtvalorparcela.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtvalorparcelaFocusLost(evt);
            }
        });

        jLabel9.setText("Data de Vencimento");

        datevencimento.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtparcela, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtvalorparcela, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datevencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtparcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtvalorparcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(datevencimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Pagamento"));

        jLabel10.setText("Data de Pagamento");

        jLabel12.setText("Método");

        cbmetodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Boleto", "Depósito", "Transferência", "Cartão de Crédito", "Cheque", "Dinheiro" }));
        cbmetodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbmetodoActionPerformed(evt);
            }
        });

        lblcheque.setText("Número do Cheque");

        datepagamento.setDateFormatString("dd/MM/yyyy");

        lblbanco.setText("Banco");

        cbbanco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datepagamento, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbmetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblbanco)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblcheque)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcheque)
                        .addGap(41, 41, 41)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(datepagamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbmetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(jLabel10)))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblbanco)
                        .addComponent(cbbanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblcheque)
                        .addComponent(txtcheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Documentos"));

        tabledocs.setModel(new javax.swing.table.DefaultTableModel(
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
            tabledocs.getColumnModel().getColumn(1).setMinWidth(30);
            tabledocs.getColumnModel().getColumn(1).setPreferredWidth(30);
            tabledocs.getColumnModel().getColumn(1).setMaxWidth(30);
            tabledocs.getColumnModel().getColumn(4).setMinWidth(0);
            tabledocs.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabledocs.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdatalancamento, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtdatalancamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
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
        CAPBean capb = new CAPBean();
        CAPDAO capd = new CAPDAO();

        if (txtid.getText().equals("")) {
            capb.setDatalancamento(txtdatalancamento.getText());
            capb.setFornecedor(txtfornecedor.getText());
            capb.setNumero(txtnf.getText());
            capb.setDataemissao(Dates.CriarDataCurtaDBJDateChooser(dateemissao.getDate()));
            capb.setTotal(txttotal.getText());
            capb.setParcela(txtparcela.getText());
            capb.setValorparcela(txtvalorparcela.getText());
            capb.setDataparcela(Dates.CriarDataCurtaDBJDateChooser(datevencimento.getDate()));
            capb.setDatapagamento(Dates.CriarDataCurtaDBJDateChooser(datepagamento.getDate()));
            capb.setBanco(cbbanco.getSelectedItem().toString());
            capb.setMetodo(cbmetodo.getSelectedItem().toString());
            if (datepagamento.getCalendar() == null) {
                capb.setStatus("Em aberto");
            } else {
                capb.setStatus("Pago");
            }

            //datalancamento, fornecedor, notafiscal, dataemissao, total, parcela, valorparcela, dataparcela, datapagamento, banco, metodo, status
            capd.create(capb);

            ContasPagar.cbstatus.setSelectedIndex(0);

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");

            readtablecap();
        } else {
            capb.setFornecedor(txtfornecedor.getText());
            capb.setNumero(txtnf.getText());
            capb.setDataemissao(Dates.CriarDataCurtaDBJDateChooser(dateemissao.getDate()));
            capb.setTotal(txttotal.getText());
            capb.setParcela(txtparcela.getText());
            capb.setValorparcela(txtvalorparcela.getText());
            capb.setDataparcela(Dates.CriarDataCurtaDBJDateChooser(datevencimento.getDate()));
            if (datepagamento.getCalendar() == null) {
                capb.setDatapagamento(null);
            } else {
                capb.setDatapagamento(Dates.CriarDataCurtaDBJDateChooser(datepagamento.getDate()));
            }
            capb.setBanco(cbbanco.getSelectedItem().toString());
            capb.setMetodo(cbmetodo.getSelectedItem().toString());
            capb.setCheque(txtcheque.getText());
            if (datepagamento.getCalendar() == null) {
                capb.setStatus("Ativo");
            } else {
                capb.setStatus("Pago");
            }
            capb.setId(Integer.parseInt(txtid.getText()));

            //fornecedor = ?, notafiscal = ?, dataemissao = ?, total = ?, parcela = ?, valorparcela = ?, dataparcela = ?, datapagamento = ?, banco = ?, metodo = ?, cheque = ?, status = ? WHERE id = ?
            capd.update(capb);

            ContasPagar.cbstatus.setSelectedIndex(0);

            readtablecap();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        }
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txttotalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txttotalFocusLost
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String vv = txttotal.getText().replace(".", "");
        String v = formatter.format(Float.parseFloat(vv.replace(",", ".")));
        txttotal.setText(v);
    }//GEN-LAST:event_txttotalFocusLost

    private void txtvalorparcelaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtvalorparcelaFocusLost
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String v = formatter.format(Float.parseFloat(txtvalorparcela.getText().replace(",", ".")));
        txtvalorparcela.setText(v);
    }//GEN-LAST:event_txtvalorparcelaFocusLost

    private void btnprocurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprocurarActionPerformed
        ProcurarFornecedor pf = new ProcurarFornecedor("ContaPagar");
        Telas.AparecerTela(pf);
    }//GEN-LAST:event_btnprocurarActionPerformed

    private void txtparcelaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtparcelaFocusLost
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String t = txttotal.getText().replace(".", "");
        float tot = Float.parseFloat(t.replace(",", "."));
        switch (txtparcela.getText().length()) {
            case 3: {
                String v = txtparcela.getText().substring(2, 3);
                int p = Integer.parseInt(v);
                float vp = tot / p;
                txtvalorparcela.setText(String.valueOf(formatter.format(vp)));
                break;
            }
            case 4: {
                String v = txtparcela.getText().substring(2, 4);
                int p = Integer.parseInt(v);
                float vp = tot / p;
                txtvalorparcela.setText(String.valueOf(formatter.format(vp)));
                break;
            }
            default: {
                String v = txtparcela.getText().substring(3, 5);
                int p = Integer.parseInt(v);
                float vp = tot / p;
                txtvalorparcela.setText(String.valueOf(formatter.format(vp)));
                break;
            }
        }
    }//GEN-LAST:event_txtparcelaFocusLost

    private void cbmetodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbmetodoActionPerformed
        camposcbmetodo();
    }//GEN-LAST:event_cbmetodoActionPerformed

    private void tabledocsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabledocsMouseClicked
        if (evt.getClickCount() == 2) {
            Docs.open(tabledocs.getValueAt(tabledocs.getSelectedRow(), 3).toString());
        }
    }//GEN-LAST:event_tabledocsMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnprocurar;
    public javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JComboBox<String> cbbanco;
    public static javax.swing.JComboBox<String> cbmetodo;
    public static com.toedter.calendar.JDateChooser dateemissao;
    public static com.toedter.calendar.JDateChooser datepagamento;
    public static com.toedter.calendar.JDateChooser datevencimento;
    public static javax.swing.JButton jButton1;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lblbanco;
    public static javax.swing.JLabel lblcheque;
    public static javax.swing.JTable tabledocs;
    public static javax.swing.JTextField txtcheque;
    public static javax.swing.JFormattedTextField txtdatalancamento;
    public static javax.swing.JTextField txtfornecedor;
    public static javax.swing.JTextField txtid;
    public static javax.swing.JTextField txtnf;
    public static javax.swing.JTextField txtparcela;
    public static javax.swing.JTextField txttotal;
    public static javax.swing.JTextField txtvalorparcela;
    // End of variables declaration//GEN-END:variables
}
