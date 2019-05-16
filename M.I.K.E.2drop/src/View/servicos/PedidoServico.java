/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.servicos;

import Bean.OSBean;
import Bean.OSDocumentosBean;
import Bean.OSProcessosBean;
import Bean.ServicoGrupoDeProcessosBean;
import Bean.ServicoGrupoDeProcessosItensBean;
import Bean.ServicoMateriaisBean;
import Bean.ServicoMateriaisDocumentosBean;
import Bean.ServicoMateriaisMovimentacaoBean;
import Bean.ServicoPedidoBean;
import Bean.ServicoPedidoDocumentosBean;
import Bean.ServicoPedidoItensBean;
import Bean.ServicoPedidoItensNFBean;
import DAO.OSDAO;
import DAO.OSDocumentosDAO;
import DAO.OSProcessosDAO;
import DAO.ServicoGrupoDeProcessosDAO;
import DAO.ServicoGrupoDeProcessosItensDAO;
import DAO.ServicoMateriaisDAO;
import DAO.ServicoMateriaisDocumentosDAO;
import DAO.ServicoMateriaisMovimentacaoDAO;
import DAO.ServicoPedidoDAO;
import DAO.ServicoPedidoDocumentosDAO;
import DAO.ServicoPedidoItensDAO;
import DAO.ServicoPedidoItensNFDAO;
import Methods.SendEmail;
import View.TelaPrincipal;
import static View.TelaPrincipal.jDesktopPane1;
import static View.servicos.OS.radioreconstrucao;
import static View.servicos.OS.radiotopo;
import static View.servicos.OS.txtcodigo;
import static View.servicos.OS.txtdesc;
import static View.servicos.OS.txtfinal;
import static View.servicos.OS.txtfrontal;
import static View.servicos.OS.txtinicial;
import static View.servicos.OS.txtmortas;
import static View.servicos.OS.txtnumeroos;
import static View.servicos.OS.txtraio;
import static View.servicos.OS.txtstatus;
import java.awt.AWTException;
import java.awt.Desktop;
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
import java.text.DecimalFormat;
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
public class PedidoServico extends javax.swing.JInternalFrame {

    /**
     * Creates new form PedidoServico
     */
    public PedidoServico() {
        initComponents();
        filltablepedidoorcamento();
    }

    public static void filltablepedidoorcamento() {
        DefaultTableModel model = (DefaultTableModel) tablepedidoservico.getModel();
        model.setNumRows(0);
        ServicoPedidoDAO spd = new ServicoPedidoDAO();

        for (ServicoPedidoBean spb : spd.read()) {
            model.addRow(new Object[]{
                spb.getIdtela(),
                spb.getCliente(),
                spb.getStatus_retorno(),
                spb.getStatus_cobranca()
            });
        }
    }

    public static void txtvalorcobranca() {
        if (tableitensorcamento.getRowCount() < 1) {
            txttotal.setText("");
        } else {
            float vt = 0;

            for (int i = 0; i < tableitensorcamento.getRowCount(); i++) {
                String v = tableitensorcamento.getValueAt(i, 6).toString().replace(".", "");
                String valor = v.replace(",", ".");
                float vp = Float.parseFloat(valor);
                vt = vt + vp;
            }
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            String valorf = formatter.format(vt);
            txttotal.setText(String.valueOf(valorf));
        }
    }

    public static void txtvalorretorno() {
        if (tableitensnota.getRowCount() < 1) {
            txttotalretorno.setText("");
        } else {
            float vt = 0;

            for (int i = 0; i < tableitensnota.getRowCount(); i++) {
                String v = tableitensnota.getValueAt(i, 6).toString().replace(".", "");
                String valor = v.replace(",", ".");
                float vp = Float.parseFloat(valor);
                vt = vt + vp;
            }
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            String valorf = formatter.format(vt);
            txttotalretorno.setText(String.valueOf(valorf));
        }
    }

    public static void camposeditaveis() {
        if (!txtorcamento.getText().equals("")) {
            txtorcamento.setEditable(false);
        }

        if (!txtnfcliente.getText().equals("")) {
            txtnfcliente.setEditable(false);
        }
    }

    public static void readitenscobranca() {
        DefaultTableModel model = (DefaultTableModel) tableitensorcamento.getModel();
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
                spib.getOs(),
                spib.getNf()
            });
        }
    }

    public static void readitensretorno() {
        DefaultTableModel model = (DefaultTableModel) tableitensnota.getModel();
        model.setNumRows(0);
        ServicoPedidoItensNFDAO spid = new ServicoPedidoItensNFDAO();

        for (ServicoPedidoItensNFBean spib : spid.readitens(txtnumeropedido.getText())) {
            model.addRow(new Object[]{
                false,
                spib.getId(),
                spib.getCodigo(),
                spib.getDescricao(),
                spib.getQtde(),
                spib.getValor(),
                spib.getTotal(),
                spib.getNfretorno()
            });
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

        tabpedidos = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablepedidoservico = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtclientepedido = new javax.swing.JTextField();
        BtnProcurar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtvendedor = new javax.swing.JTextField();
        txtrepresentante = new javax.swing.JTextField();
        txtcondicao = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        BtnCondicao = new javax.swing.JButton();
        BtnRepresentante = new javax.swing.JButton();
        BtnVendedor = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtnumeropedido = new javax.swing.JTextField();
        txtstatusretorno = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtorcamento = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtnfcliente = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtstatuscobranca = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtnotes = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabledocumentos = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableitensorcamento = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        txttotal = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableitensnota = new javax.swing.JTable();
        txttotalretorno = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Pedido de Serviço");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Procura"));

        jLabel4.setText("Pesquisa");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablepedidoservico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Status Retorno", "Status Cobrança"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablepedidoservico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablepedidoservicoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablepedidoservico);
        if (tablepedidoservico.getColumnModel().getColumnCount() > 0) {
            tablepedidoservico.getColumnModel().getColumn(0).setMinWidth(80);
            tablepedidoservico.getColumnModel().getColumn(0).setPreferredWidth(80);
            tablepedidoservico.getColumnModel().getColumn(0).setMaxWidth(80);
        }

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Aberto", "Ativo", "Parcialmente Faturado", "Faturado", "Cancelado", "Todos" }));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, 0, 169, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 562, Short.MAX_VALUE)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabpedidos.addTab("Lista de Pedidos", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        jLabel3.setText("Cliente");

        txtclientepedido.setEditable(false);

        BtnProcurar.setText("Procurar");
        BtnProcurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnProcurarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtclientepedido, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnProcurar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtclientepedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnProcurar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Comercial"));

        txtvendedor.setEditable(false);

        txtrepresentante.setEditable(false);

        txtcondicao.setEditable(false);

        jLabel5.setText("Vendedor");

        jLabel6.setText("Representante");

        jLabel7.setText("Condição de Pagamento");

        BtnCondicao.setText("Procurar");
        BtnCondicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCondicaoActionPerformed(evt);
            }
        });

        BtnRepresentante.setText("Procurar");
        BtnRepresentante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRepresentanteActionPerformed(evt);
            }
        });

        BtnVendedor.setText("Procurar");
        BtnVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVendedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtvendedor, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                    .addComponent(txtrepresentante)
                    .addComponent(txtcondicao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnCondicao)
                    .addComponent(BtnRepresentante)
                    .addComponent(BtnVendedor))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcondicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(BtnCondicao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtrepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(BtnRepresentante))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(BtnVendedor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Pedido"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Nº");

        txtnumeropedido.setEditable(false);
        txtnumeropedido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtnumeropedido.setSelectionColor(new java.awt.Color(255, 255, 255));

        txtstatusretorno.setEditable(false);
        txtstatusretorno.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setText("Retorno");

        jButton4.setText("Imprimir");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel8.setText("Orçamento");

        txtorcamento.setEditable(false);
        txtorcamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel9.setText("NF Cliente");

        txtnfcliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel12.setText("Cobrança");

        txtstatuscobranca.setEditable(false);
        txtstatuscobranca.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnfcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnumeropedido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtstatusretorno)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtstatuscobranca, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(txtorcamento))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtorcamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtnumeropedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtstatusretorno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel12)
                    .addComponent(txtstatuscobranca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtnfcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtnotes.setColumns(20);
        txtnotes.setRows(5);
        jScrollPane5.setViewportView(txtnotes);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1218, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Anotações", jPanel9);

        jPanel10.setPreferredSize(new java.awt.Dimension(1056, 144));

        jScrollPane4.setPreferredSize(new java.awt.Dimension(166, 96));

        tabledocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Id", "Descrição", "Local", "Local Original"
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
        tabledocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabledocumentosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabledocumentos);
        if (tabledocumentos.getColumnModel().getColumnCount() > 0) {
            tabledocumentos.getColumnModel().getColumn(0).setMinWidth(40);
            tabledocumentos.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabledocumentos.getColumnModel().getColumn(0).setMaxWidth(40);
            tabledocumentos.getColumnModel().getColumn(1).setMinWidth(0);
            tabledocumentos.getColumnModel().getColumn(1).setPreferredWidth(0);
            tabledocumentos.getColumnModel().getColumn(1).setMaxWidth(0);
            tabledocumentos.getColumnModel().getColumn(2).setMinWidth(500);
            tabledocumentos.getColumnModel().getColumn(2).setPreferredWidth(500);
            tabledocumentos.getColumnModel().getColumn(2).setMaxWidth(500);
        }

        jButton7.setText("Incluir");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Excluir");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1218, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addGap(313, 313, 313))
        );

        jTabbedPane2.addTab("Documentos", jPanel10);

        tableitensorcamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Código", "Descrição", "Qtde", "Valor Unitário", "Total", "Prazo de Entrega", "Pedido do Cliente", "OS", "NF Cobrança"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableitensorcamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableitensorcamentoMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableitensorcamentoMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tableitensorcamento);
        if (tableitensorcamento.getColumnModel().getColumnCount() > 0) {
            tableitensorcamento.getColumnModel().getColumn(0).setMinWidth(40);
            tableitensorcamento.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableitensorcamento.getColumnModel().getColumn(0).setMaxWidth(40);
            tableitensorcamento.getColumnModel().getColumn(1).setMinWidth(0);
            tableitensorcamento.getColumnModel().getColumn(1).setPreferredWidth(0);
            tableitensorcamento.getColumnModel().getColumn(1).setMaxWidth(0);
            tableitensorcamento.getColumnModel().getColumn(2).setMinWidth(150);
            tableitensorcamento.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableitensorcamento.getColumnModel().getColumn(2).setMaxWidth(150);
            tableitensorcamento.getColumnModel().getColumn(4).setMinWidth(60);
            tableitensorcamento.getColumnModel().getColumn(4).setPreferredWidth(60);
            tableitensorcamento.getColumnModel().getColumn(4).setMaxWidth(60);
            tableitensorcamento.getColumnModel().getColumn(5).setMinWidth(90);
            tableitensorcamento.getColumnModel().getColumn(5).setPreferredWidth(90);
            tableitensorcamento.getColumnModel().getColumn(5).setMaxWidth(90);
            tableitensorcamento.getColumnModel().getColumn(6).setMinWidth(90);
            tableitensorcamento.getColumnModel().getColumn(6).setPreferredWidth(90);
            tableitensorcamento.getColumnModel().getColumn(6).setMaxWidth(90);
            tableitensorcamento.getColumnModel().getColumn(7).setMinWidth(110);
            tableitensorcamento.getColumnModel().getColumn(7).setPreferredWidth(110);
            tableitensorcamento.getColumnModel().getColumn(7).setMaxWidth(110);
            tableitensorcamento.getColumnModel().getColumn(8).setMinWidth(110);
            tableitensorcamento.getColumnModel().getColumn(8).setPreferredWidth(110);
            tableitensorcamento.getColumnModel().getColumn(8).setMaxWidth(110);
            tableitensorcamento.getColumnModel().getColumn(9).setMinWidth(80);
            tableitensorcamento.getColumnModel().getColumn(9).setPreferredWidth(80);
            tableitensorcamento.getColumnModel().getColumn(9).setMaxWidth(80);
            tableitensorcamento.getColumnModel().getColumn(10).setMinWidth(90);
            tableitensorcamento.getColumnModel().getColumn(10).setPreferredWidth(90);
            tableitensorcamento.getColumnModel().getColumn(10).setMaxWidth(90);
        }

        jButton2.setText("Incluir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setText("Excluir");

        jButton6.setText("Criar OS's");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        txttotal.setEditable(false);
        txttotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel10.setText("Total: R$");

        jButton11.setText("Lançar Nota de Cobrança");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton13.setText("Teste");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1218, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jButton11)
                    .addComponent(jButton13))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Itens de Cobrança", jPanel7);

        tableitensnota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Código", "Descrição", "Qtde", "Valor Unitário", "Total", "NF Retorno"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableitensnota);
        if (tableitensnota.getColumnModel().getColumnCount() > 0) {
            tableitensnota.getColumnModel().getColumn(0).setMinWidth(40);
            tableitensnota.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableitensnota.getColumnModel().getColumn(0).setMaxWidth(40);
            tableitensnota.getColumnModel().getColumn(1).setMinWidth(0);
            tableitensnota.getColumnModel().getColumn(1).setPreferredWidth(0);
            tableitensnota.getColumnModel().getColumn(1).setMaxWidth(0);
            tableitensnota.getColumnModel().getColumn(2).setMinWidth(150);
            tableitensnota.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableitensnota.getColumnModel().getColumn(2).setMaxWidth(150);
            tableitensnota.getColumnModel().getColumn(4).setMinWidth(60);
            tableitensnota.getColumnModel().getColumn(4).setPreferredWidth(60);
            tableitensnota.getColumnModel().getColumn(4).setMaxWidth(60);
            tableitensnota.getColumnModel().getColumn(5).setMinWidth(80);
            tableitensnota.getColumnModel().getColumn(5).setPreferredWidth(80);
            tableitensnota.getColumnModel().getColumn(5).setMaxWidth(80);
            tableitensnota.getColumnModel().getColumn(6).setMinWidth(90);
            tableitensnota.getColumnModel().getColumn(6).setPreferredWidth(90);
            tableitensnota.getColumnModel().getColumn(6).setMaxWidth(90);
            tableitensnota.getColumnModel().getColumn(7).setMinWidth(90);
            tableitensnota.getColumnModel().getColumn(7).setPreferredWidth(90);
            tableitensnota.getColumnModel().getColumn(7).setMaxWidth(90);
        }

        jLabel11.setText("Total: R$");

        jButton9.setText("Incluir");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Excluir");

        jButton12.setText("Lançar Nota Fiscal de Retorno");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1218, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttotalretorno, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttotalretorno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jButton12))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Itens de Retorno", jPanel6);

        jButton3.setText("Novo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        tabpedidos.addTab("Detalhes do Pedido", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabpedidos)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabpedidos)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnProcurarActionPerformed
        ProcurarClientePedidoServico p = new ProcurarClientePedidoServico();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_BtnProcurarActionPerformed

    private void BtnCondicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCondicaoActionPerformed
        ProcurarCondicaoDePagamentoPedidoServico p = new ProcurarCondicaoDePagamentoPedidoServico();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_BtnCondicaoActionPerformed

    private void BtnRepresentanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRepresentanteActionPerformed
        ProcurarRepresentantePedidoServico p = new ProcurarRepresentantePedidoServico();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_BtnRepresentanteActionPerformed

    private void BtnVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVendedorActionPerformed
        ProcurarVendedorPedidoServico p = new ProcurarVendedorPedidoServico();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_BtnVendedorActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ItemPedidoServico p = new ItemPedidoServico();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

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

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        DocumentosPedidoServico d = new DocumentosPedidoServico();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(d);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = d.getSize();
        d.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        d.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int numerotrue = 0;
        for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
            if (tabledocumentos.getValueAt(i, 0).equals(true)) {
                numerotrue++;
            }
        }
        DefaultTableModel model = (DefaultTableModel) tabledocumentos.getModel();
        if (tabledocumentos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Não existem itens para excluir");
        } else if (numerotrue == 0) {
            JOptionPane.showMessageDialog(rootPane, "Selecione um item para excluir!");
        } else {
            int resp = JOptionPane.showConfirmDialog(rootPane, "Excluir documentos selecionados?", "Excluir documentos", JOptionPane.OK_CANCEL_OPTION);
            if (resp == 0) {
                int nr = tabledocumentos.getRowCount();
                for (int i = 0; i < nr; i++) {
                    if (tabledocumentos.getValueAt(i, 0).equals(true)) {
                        File file = new File((String) tabledocumentos.getValueAt(i, 3));
                        try {
                            Files.delete(file.toPath());
                        } catch (IOException ex) {
                            Logger.getLogger(CotacaoServico.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(rootPane, "Erro!\n" + ex);
                        }

                        ServicoPedidoDocumentosBean spdb = new ServicoPedidoDocumentosBean();
                        ServicoPedidoDocumentosDAO spdd = new ServicoPedidoDocumentosDAO();

                        spdb.setId(Integer.parseInt(tabledocumentos.getValueAt(i, 1).toString()));
                        //id

                        spdd.delete(spdb);

                        model.removeRow(i);
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtclientepedido.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Selecione um cliente primeiro!");
        } else if (txtcondicao.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Selecione uma condição de pagamento primeiro!");
        } else if (txtrepresentante.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Selecione um representante primeiro!");
        } else if (txtvendedor.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Selecione um vendedor primeiro!");
        } else if (tableitensorcamento.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Coloque pelo menos um item no pedido!");
        } else if (txtnumeropedido.getText().equals("")) {
            //Criar pedido novo
            ServicoPedidoDAO spd = new ServicoPedidoDAO();
            ServicoPedidoBean spb = new ServicoPedidoBean();

            try {
                if (spd.readnome() == false) {
                    Calendar ca = Calendar.getInstance();
                    String patterny = "yy";
                    SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                    String year = simpleDateFormaty.format(ca.getTime());
                    String idtela = "PS" + year + "-0001";
                    spb.setIdtela(idtela);
                    txtnumeropedido.setText(idtela);
                } else {
                    Calendar ca = Calendar.getInstance();
                    String patterny = "yy";
                    SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                    String year = simpleDateFormaty.format(ca.getTime());
                    String hua = "";
                    for (ServicoPedidoBean spb2 : spd.read()) {
                        hua = String.valueOf(spb2.getIdtela());
                    }
                    int yearint = Integer.parseInt(hua.replace(year + "-", ""));
                    int yearnovo = yearint + 1;
                    String idtela = "PS" + year + "-" + String.format("%04d", yearnovo);
                    spb.setIdtela(idtela);
                    txtnumeropedido.setText(idtela);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CotacaoServico.class.getName()).log(Level.SEVERE, null, ex);
            }
            spb.setIdorcamento(txtorcamento.getText());
            spb.setCliente(txtclientepedido.getText());
            spb.setCondicao(txtcondicao.getText());
            spb.setRepresentante(txtrepresentante.getText());
            spb.setVendedor(txtvendedor.getText());
            spb.setNotes(txtnotes.getText());
            spb.setStatus_retorno("Ativo");
            spb.setStatus_cobranca("Ativo");
            txtstatusretorno.setText("Ativo");
            spb.setNfcliente(txtnfcliente.getText());
            Calendar date = Calendar.getInstance();
            String pattern = "dd/MM/yyyy HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            spb.setData(simpleDateFormat.format(date.getTime()));

            //idtela, idorcamento, cliente, condicao, representante, vendedor, notes, status_retorno, status_cobranca, nfcliente, data
            spd.create(spb);

            //Criar itens do pedido (Orçamento)
            ServicoPedidoItensDAO spid = new ServicoPedidoItensDAO();
            ServicoPedidoItensBean spib = new ServicoPedidoItensBean();

            for (int i = 0; i < tableitensorcamento.getRowCount(); i++) {
                spib.setIdpedido(txtnumeropedido.getText());
                spib.setCodigo(tableitensorcamento.getValueAt(i, 2).toString());
                spib.setDescricao(tableitensorcamento.getValueAt(i, 3).toString());
                spib.setQtde(tableitensorcamento.getValueAt(i, 4).toString());
                spib.setValor(tableitensorcamento.getValueAt(i, 5).toString());
                spib.setTotal(tableitensorcamento.getValueAt(i, 6).toString());
                spib.setPrazo(tableitensorcamento.getValueAt(i, 7).toString());
                spib.setPedidocliente(tableitensorcamento.getValueAt(i, 8).toString());
                spib.setOs(tableitensorcamento.getValueAt(i, 9).toString());
                spib.setNf(tableitensorcamento.getValueAt(i, 10).toString());

                //idpedido, codigo, descricao, qtde, valor, total, prazo, pedidocliente, nf
                spid.create(spib);
            }

            //Criar itens do pedido (Nota Fiscal)
            ServicoPedidoItensNFDAO spinfd = new ServicoPedidoItensNFDAO();
            ServicoPedidoItensNFBean spinfb = new ServicoPedidoItensNFBean();

            if (tableitensnota.getRowCount() > 0) {
                for (int i = 0; i < tableitensnota.getRowCount(); i++) {
                    spinfb.setIdpedido(txtnumeropedido.getText());
                    spinfb.setCodigo(tableitensnota.getValueAt(i, 2).toString());
                    spinfb.setDescricao(tableitensnota.getValueAt(i, 3).toString());
                    spinfb.setQtde(tableitensnota.getValueAt(i, 4).toString());
                    spinfb.setValor(tableitensnota.getValueAt(i, 5).toString());
                    spinfb.setTotal(tableitensnota.getValueAt(i, 6).toString());
                    spinfb.setNfretorno(tableitensnota.getValueAt(i, 7).toString());

                    //idpedido, codigo, descricao, qtde, valor, total, nfretorno
                    spinfd.create(spinfb);
                }
            }

            //Criar documentos do pedido
            ServicoPedidoDocumentosDAO spdd = new ServicoPedidoDocumentosDAO();
            ServicoPedidoDocumentosBean spdb = new ServicoPedidoDocumentosBean();

            if (tabledocumentos.getRowCount() > 0) {
                for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
                    File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                    File folder = new File("Q:/MIKE_ERP/ped_ser_arq/" + txtnumeropedido.getText());
                    File filecopy = new File(folder + "/" + fileoriginal.getName());

                    folder.mkdirs();
                    try {
                        Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                    } catch (IOException ex) {
                        Logger.getLogger(DocumentosPedidoServico.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");
                        try {
                            SendEmail.EnviarErro(ex.toString());
                            JOptionPane.showMessageDialog(rootPane, "E-mail com erro enviado com sucesso!");
                        } catch (HeadlessException hex) {
                            JOptionPane.showMessageDialog(rootPane, "Erro!\n" + hex);
                        } catch (AWTException | IOException ex1) {
                            Logger.getLogger(DocumentosPedidoServico.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }

                    //Salvar arquivos no DB
                    spdb.setIdpedido(txtnumeropedido.getText());
                    spdb.setDescricao(tabledocumentos.getValueAt(i, 2).toString());
                    spdb.setLocal(filecopy.toString());
                    //idorcamento, descricao, local

                    spdd.create(spdb);
                }
            }
            JOptionPane.showMessageDialog(rootPane, "Salvo com sucesso!");
            filltablepedidoorcamento();
            camposeditaveis();
            readitenscobranca();
            readitensretorno();
        } else {
            //Atualizar pedido
            ServicoPedidoDAO spd = new ServicoPedidoDAO();
            ServicoPedidoBean spb = new ServicoPedidoBean();

            spb.setCliente(txtclientepedido.getText());
            spb.setCondicao(txtcondicao.getText());
            spb.setRepresentante(txtrepresentante.getText());
            spb.setVendedor(txtvendedor.getText());
            spb.setNotes(txtnotes.getText());
            spb.setStatus_retorno(txtstatusretorno.getText());
            spb.setNfcliente(txtnfcliente.getText());
            spb.setIdtela(txtnumeropedido.getText());

            //cliente = ?, condicao = ?, representante = ?, vendedor = ?, notes = ?, nfcliente = ? WHERE idtela = ?
            spd.update(spb);

            //Criar itens do pedido (Orçamento)
            ServicoPedidoItensDAO spid = new ServicoPedidoItensDAO();
            ServicoPedidoItensBean spib = new ServicoPedidoItensBean();

            for (int i = 0; i < tableitensorcamento.getRowCount(); i++) {
                if (tableitensorcamento.getValueAt(i, 1).equals("")) {
                    spib.setIdpedido(txtnumeropedido.getText());
                    spib.setCodigo(tableitensorcamento.getValueAt(i, 2).toString());
                    spib.setDescricao(tableitensorcamento.getValueAt(i, 3).toString());
                    spib.setQtde(tableitensorcamento.getValueAt(i, 4).toString());
                    spib.setValor(tableitensorcamento.getValueAt(i, 5).toString());
                    spib.setTotal(tableitensorcamento.getValueAt(i, 6).toString());
                    spib.setPrazo(tableitensorcamento.getValueAt(i, 7).toString());
                    spib.setPedidocliente(tableitensorcamento.getValueAt(i, 8).toString());
                    spib.setOs(tableitensorcamento.getValueAt(i, 9).toString());
                    spib.setNf(tableitensorcamento.getValueAt(i, 10).toString());

                    //idpedido, codigo, descricao, qtde, valor, total, prazo, pedidocliente, nf
                    spid.create(spib);
                } else {
                    spib.setIdpedido(txtnumeropedido.getText());
                    spib.setCodigo(tableitensorcamento.getValueAt(i, 2).toString());
                    spib.setDescricao(tableitensorcamento.getValueAt(i, 3).toString());
                    spib.setQtde(tableitensorcamento.getValueAt(i, 4).toString());
                    spib.setValor(tableitensorcamento.getValueAt(i, 5).toString());
                    spib.setTotal(tableitensorcamento.getValueAt(i, 6).toString());
                    spib.setPrazo(tableitensorcamento.getValueAt(i, 7).toString());
                    spib.setPedidocliente(tableitensorcamento.getValueAt(i, 8).toString());
                    spib.setOs(tableitensorcamento.getValueAt(i, 9).toString());
                    spib.setNf(tableitensorcamento.getValueAt(i, 10).toString());
                    spib.setId(Integer.parseInt(tableitensorcamento.getValueAt(i, 1).toString()));

                    //idpedido = ?, codigo = ?, descricao = ?, qtde = ?, valor = ?, total = ?, prazo = ?, pedidocliente = ?, os = ?, nf = ? WHERE id = ?
                    spid.update(spib);
                }

            }

            //Criar itens do pedido (Nota Fiscal)
            ServicoPedidoItensNFDAO spinfd = new ServicoPedidoItensNFDAO();
            ServicoPedidoItensNFBean spinfb = new ServicoPedidoItensNFBean();

            if (tableitensnota.getRowCount() > 0) {
                for (int i = 0; i < tableitensnota.getRowCount(); i++) {
                    if (tableitensnota.getValueAt(i, 1).equals("")) {
                        spinfb.setIdpedido(txtnumeropedido.getText());
                        spinfb.setCodigo(tableitensnota.getValueAt(i, 2).toString());
                        spinfb.setDescricao(tableitensnota.getValueAt(i, 3).toString());
                        spinfb.setQtde(tableitensnota.getValueAt(i, 4).toString());
                        spinfb.setValor(tableitensnota.getValueAt(i, 5).toString());
                        spinfb.setTotal(tableitensnota.getValueAt(i, 6).toString());
                        spinfb.setNfretorno(tableitensnota.getValueAt(i, 7).toString());

                        //idpedido, codigo, descricao, qtde, valor, total, nfretorno
                        spinfd.create(spinfb);
                    } else {
                        spinfb.setIdpedido(txtnumeropedido.getText());
                        spinfb.setCodigo(tableitensnota.getValueAt(i, 2).toString());
                        spinfb.setDescricao(tableitensnota.getValueAt(i, 3).toString());
                        spinfb.setQtde(tableitensnota.getValueAt(i, 4).toString());
                        spinfb.setValor(tableitensnota.getValueAt(i, 5).toString());
                        spinfb.setTotal(tableitensnota.getValueAt(i, 6).toString());
                        spinfb.setNfretorno(tableitensnota.getValueAt(i, 7).toString());
                        spinfb.setId(Integer.parseInt(tableitensnota.getValueAt(i, 1).toString()));

                        //idpedido = ?, codigo = ?, descricao = ?, qtde = ?, valor = ?, total = ?,  nfretorno = ? WHERE id = ?
                        spinfd.update(spinfb);
                    }
                }
            }

            //Criar documentos do pedido
            ServicoPedidoDocumentosDAO spdd = new ServicoPedidoDocumentosDAO();
            ServicoPedidoDocumentosBean spdb = new ServicoPedidoDocumentosBean();

            if (tabledocumentos.getRowCount() > 0) {
                for (int i = 0; i < tabledocumentos.getRowCount(); i++) {
                    if (tabledocumentos.getValueAt(i, 1).equals("")) {
                        File fileoriginal = new File(tabledocumentos.getValueAt(i, 4).toString());
                        File folder = new File("Q:/MIKE_ERP/ped_ser_arq/" + txtnumeropedido.getText());
                        File filecopy = new File(folder + "/" + fileoriginal.getName());

                        folder.mkdirs();
                        try {
                            Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
                        } catch (IOException ex) {
                            Logger.getLogger(DocumentosPedidoServico.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");
                            try {
                                SendEmail.EnviarErro(ex.toString());
                                JOptionPane.showMessageDialog(rootPane, "E-mail com erro enviado com sucesso!");
                            } catch (HeadlessException hex) {
                                JOptionPane.showMessageDialog(rootPane, "Erro!\n" + hex);
                            } catch (AWTException | IOException ex1) {
                                Logger.getLogger(DocumentosPedidoServico.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        }

                        //Salvar arquivos no DB
                        spdb.setIdpedido(txtnumeropedido.getText());
                        spdb.setDescricao(tabledocumentos.getValueAt(i, 2).toString());
                        spdb.setLocal(filecopy.toString());
                        //idorcamento, descricao, local

                        spdd.create(spdb);
                    }
                }
            }
            JOptionPane.showMessageDialog(rootPane, "Atualizado com sucesso!");
            filltablepedidoorcamento();
            camposeditaveis();
            readitenscobranca();
            readitensretorno();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tablepedidoservicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablepedidoservicoMouseClicked
        if (evt.getClickCount() == 2) {
            tabpedidos.setSelectedIndex(1);
            txtnumeropedido.setText(tablepedidoservico.getValueAt(tablepedidoservico.getSelectedRow(), 0).toString());

            ServicoPedidoDAO spd = new ServicoPedidoDAO();

            for (ServicoPedidoBean spb : spd.click(txtnumeropedido.getText())) {
                txtclientepedido.setText(spb.getCliente());
                txtcondicao.setText(spb.getCondicao());
                txtrepresentante.setText(spb.getRepresentante());
                txtvendedor.setText(spb.getVendedor());
                txtstatusretorno.setText(spb.getStatus_retorno());
                txtstatuscobranca.setText(spb.getStatus_cobranca());
                txtnotes.setText(spb.getNotes());
                txtorcamento.setText(String.valueOf(spb.getIdorcamento()));
                txtnfcliente.setText(spb.getNfcliente());
            }

            camposeditaveis();

            readitenscobranca();

            txtvalorcobranca();

            readitensretorno();

            txtvalorretorno();

            DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
            modeldoc.setNumRows(0);
            ServicoPedidoDocumentosDAO spdd = new ServicoPedidoDocumentosDAO();

            for (ServicoPedidoDocumentosBean spdb : spdd.readitens(txtnumeropedido.getText())) {
                modeldoc.addRow(new Object[]{
                    false,
                    spdb.getId(),
                    spdb.getDescricao(),
                    spdb.getLocal(),});
            }
        }
    }//GEN-LAST:event_tablepedidoservicoMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Em breve!");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int resp = JOptionPane.showConfirmDialog(rootPane, "Deseja criar um novo pedido?", "Novo pedido", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            txtclientepedido.setText("");
            txtcondicao.setText("");
            txtrepresentante.setText("");
            txtvendedor.setText("");
            txtnumeropedido.setText("");
            txtstatusretorno.setText("");
            txtorcamento.setText("");
            txtnfcliente.setText("");
            txttotal.setText("");
            txtnotes.setText("");
            DefaultTableModel modelo = (DefaultTableModel) tableitensorcamento.getModel();
            DefaultTableModel modeln = (DefaultTableModel) tableitensnota.getModel();
            DefaultTableModel modeld = (DefaultTableModel) tabledocumentos.getModel();
            modelo.setNumRows(0);
            modeln.setNumRows(0);
            modeld.setNumRows(0);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        ItemRetornoServico p = new ItemRetornoServico();
        JDesktopPane desk = this.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        //DAO e Bean para atualizar estoque do item
        ServicoMateriaisDAO smd = new ServicoMateriaisDAO();
        ServicoMateriaisBean smb = new ServicoMateriaisBean();

        //DAO e Bean para atualizar itens do pedido
        ServicoPedidoItensDAO spid = new ServicoPedidoItensDAO();
        ServicoPedidoItensBean spib = new ServicoPedidoItensBean();

        //DAO e Bean para alterar pedido
        ServicoPedidoDAO spd = new ServicoPedidoDAO();
        ServicoPedidoBean spb = new ServicoPedidoBean();

        //DAO e Bean para criar movimentação do material
        ServicoMateriaisMovimentacaoDAO smmd = new ServicoMateriaisMovimentacaoDAO();
        ServicoMateriaisMovimentacaoBean smmb = new ServicoMateriaisMovimentacaoBean();

        //Número de linhas na table para fazer os métodos na table inteira
        int rc = tableitensorcamento.getRowCount();
        //Número de linhas selecionadas
        int numerotrue = 0;
        //Número de itens com nota lançados antes de clicar no botão
        int numeronota = 0;
        //Número de itens com nota lançados depois de clicar no botão
        int numeronota2 = 0;

        for (int i = 0; i < rc; i++) {
            //Pegar número de linhas selecionadas
            if (tableitensorcamento.getValueAt(i, 0).equals(true)) {
                numerotrue++;
            }
            //Pegar número de notas lançadas antes de clicar no botão
            if (tableitensorcamento.getValueAt(i, 0).equals(true) && !tableitensorcamento.getValueAt(i, 10).equals("")) {
                numeronota++;
            }
        }
        if (numerotrue == 0) { //Verificar se existem itens selecionados
            JOptionPane.showMessageDialog(rootPane, "Escolha um item primeiro!");
        } else if (numeronota > 0) { //Verificar se existem itens selecionados com nota fiscal já lançada
            JOptionPane.showMessageDialog(rootPane, "Item selecionado já com nota fiscal.");
        } else {
            //Saldo atual do item selecionado
            int saldoatual = 0;

            //Id do material
            int idmaterial = 0;

            //Pegar número da nota para atualizar itens selecionados
            String nota = JOptionPane.showInputDialog(rootPane, "Qual o número da nota de cobrança?", "Nota de Cobrança", JOptionPane.YES_NO_OPTION);

            //Atualizar itens com valor da nota anteriormente digitada
            for (int i = 0; i < rc; i++) {
                //Quantidade do item
                int qtditem = Integer.parseInt(tableitensorcamento.getValueAt(i, 4).toString());
                
                //Pegar id do material
                for (ServicoMateriaisBean smb2 : smd.readid(tableitensorcamento.getValueAt(i, 2).toString())) {
                    idmaterial = smb2.getId();
                }
                //Pegar saldo atual do material
                for (ServicoMateriaisBean smb3 : smd.readestoque(idmaterial)) {
                    saldoatual = smb3.getEstoque();
                }
                if (tableitensorcamento.getValueAt(i, 0).equals(true)) { //Verificar se a linha está selecionada
                    if (saldoatual >= qtditem) { //Verificar se o saldo atual é maior ou igual à quantidade do pedido 
                        //Dados para o método chamado
                        spib.setNf(nota);
                        spib.setId(Integer.parseInt(tableitensorcamento.getValueAt(i, 1).toString()));

                        //nf = ? WHERE id = ?
                        spid.updatenotacobranca(spib);

                        //Pegar data para gravar
                        Calendar c = Calendar.getInstance();
                        String pattern = "dd/MM/yyyy HH:mm:ss";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                        //Criar movimentação do material
                        smmb.setIdmaterial(idmaterial);
                        smmb.setInicial(saldoatual);
                        smmb.setMovimentada(qtditem);
                        smmb.setTipo(txtnumeropedido.getText());
                        smmb.setSaldo(saldoatual - qtditem);
                        smmb.setData(simpleDateFormat.format(c.getTime()));
                        smmb.setUsuario(TelaPrincipal.lblapelido.getText());

                        //idmaterial, inicial, movimentada, tipo, saldo, data, usuario
                        smmd.create(smmb);

                        //Alterar estoque do material
                        smb.setEstoque(saldoatual - qtditem);
                        smb.setId(idmaterial);

                        //estoque = ? WHERE id = ?
                        smd.updateestoque(smb);
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "O item " + tableitensorcamento.getValueAt(i, 2) + " não possui saldo suficiente.\n Favor verificar informações e refazer processo.");
                    }
                }
            }
            //Atualizar table com os itens
            readitenscobranca();
            //Pegar número de linhas com nota lançada depois de clicar no botão
            for (int i = 0; i < rc; i++) {
                if (!tableitensorcamento.getValueAt(i, 10).equals("")) {
                    numeronota2++;
                }
            }
            //Alterar status do pedido de acordo com o número de linhas com notas lançadas
            if (numeronota2 < rc) {
                spb.setStatus_cobranca("Parcial");
                spb.setIdtela(txtnumeropedido.getText());

                //status_cobranca = ? WHERE idtela = ?
                spd.updatestatuscobranca(spb);
            } else {
                spb.setStatus_cobranca("Faturado");
                spb.setIdtela(txtnumeropedido.getText());

                //status_cobranca = ? WHERE idtela = ?
                spd.updatestatuscobranca(spb);
            }
            //Atualizar table de pedidos para ver status novos
            filltablepedidoorcamento();
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void tableitensorcamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableitensorcamentoMouseClicked
        if (evt.getButton() == 1) {
            if (evt.getClickCount() == 2) {
                ItemPedidoServico p = new ItemPedidoServico();
                JDesktopPane desk = this.getDesktopPane();

                desk.add(p);
                Dimension desktopsize = jDesktopPane1.getSize();
                Dimension jinternalframesize = p.getSize();
                p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
                p.setVisible(true);

                int row = tableitensorcamento.getSelectedRow();
                ItemPedidoServico.txtrow.setText(String.valueOf(row));
                ItemPedidoServico.txtid.setText(tableitensorcamento.getValueAt(row, 1).toString());
                ItemPedidoServico.txtcodigo.setText(tableitensorcamento.getValueAt(row, 2).toString());
                ItemPedidoServico.txtdesc.setText(tableitensorcamento.getValueAt(row, 3).toString());
                ItemPedidoServico.txtqtd.setText(tableitensorcamento.getValueAt(row, 4).toString());
                ItemPedidoServico.txtvalor.setText(tableitensorcamento.getValueAt(row, 5).toString());
                ItemPedidoServico.txtprazo.setText(tableitensorcamento.getValueAt(row, 7).toString());
                ItemPedidoServico.txtpedido.setText(tableitensorcamento.getValueAt(row, 8).toString());
            }
        }
        if (evt.getButton() == 3) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem das = new JMenuItem("Abrir OS");

            das.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String OS = tableitensorcamento.getValueAt(tableitensorcamento.getSelectedRow(), 9).toString();
                    if (OS.equals("")) {
                        JOptionPane.showMessageDialog(rootPane, "Produto sem OS");
                    } else {
                        OS p = new OS();
                        jDesktopPane1.add(p);
                        p.setVisible(true);
                        try {
                            p.setMaximum(true);
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        p.txtnumeroos.setText(OS);
                        p.tabos.setSelectedIndex(1);

                        OSDAO od = new OSDAO();

                        for (OSBean ob : od.click(txtnumeroos.getText())) {
                            p.txtabertura.setText(ob.getDataabertura());
                            p.txtprevisao.setText(ob.getDataprevisao());
                            txtstatus.setText(ob.getStatus());
                            p.txtcliente.setText(ob.getCliente());
                            p.txtdas.setText(ob.getDas());
                            txtcodigo.setText(ob.getCodigo());
                            txtdesc.setText(ob.getDescricao());
                            txtinicial.setText(String.valueOf(ob.getQtdinicial()));
                            txtfinal.setText(String.valueOf(ob.getQtdok()));
                            txtmortas.setText(String.valueOf(ob.getQtdnaook()));
                            txtnotes.setText(ob.getNotes());
                            if (ob.getTopo().equals("true")) {
                                radiotopo.setSelected(true);
                            }
                            if (ob.getReconstrucao().equals("true")){
                                radioreconstrucao.setSelected(true);
                            }
                            if (ob.getCompleta().equals("true")) {
                                p.radiocompleta.setSelected(true);
                            }
                            txtraio.setText(ob.getRaio());
                            txtfrontal.setText(ob.getFrontal());

                        }

                        //Pegar documentos
                        p.readdocs();

                        //Pegar processos
                        p.readprocessos();

                        //Travar campos de acordo com status da op
                        p.travarcampos();
                    }
                }
            });

            menu.add(das);

            menu.show(evt.getComponent(), evt.getPoint().x, evt.getPoint().y);
        }
    }//GEN-LAST:event_tableitensorcamentoMouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int rc = tableitensnota.getRowCount();
        int numerotrue = 0;
        int numeronota = 0;
        int numeronota2 = 0;
        for (int i = 0; i < rc; i++) {
            if (tableitensnota.getValueAt(i, 0).equals(true)) {
                numerotrue++;
            }
            if (tableitensnota.getValueAt(i, 0).equals(true) && !tableitensnota.getValueAt(i, 7).equals("")) {
                numeronota++;
            }
        }
        if (numerotrue == 0) {
            JOptionPane.showMessageDialog(rootPane, "Escolha um item primeiro!");
        } else if (numeronota > 0) {
            JOptionPane.showMessageDialog(rootPane, "Item selecionado já com nota fiscal.");
        } else {
            ServicoPedidoDAO spd = new ServicoPedidoDAO();
            ServicoPedidoBean spb = new ServicoPedidoBean();

            String nota = JOptionPane.showInputDialog(rootPane, "Qual o número da nota de retorno?", "Nota de Retorno", JOptionPane.YES_NO_OPTION);
            for (int i = 0; i < rc; i++) {
                if (tableitensnota.getValueAt(i, 0).equals(true)) {
                    ServicoPedidoItensNFDAO spid = new ServicoPedidoItensNFDAO();
                    ServicoPedidoItensNFBean spib = new ServicoPedidoItensNFBean();

                    spib.setNfretorno(nota);
                    spib.setId(Integer.parseInt(tableitensnota.getValueAt(i, 1).toString()));

                    //nf = ? WHERE id = ?
                    spid.updatenotaretorno(spib);
                }
            }
            readitensretorno();
            for (int i = 0; i < rc; i++) {
                if (!tableitensnota.getValueAt(i, 7).equals("")) {
                    numeronota2++;
                }
            }
            if (numeronota2 < rc) {
                spb.setStatus_retorno("Parcial");
                spb.setIdtela(txtnumeropedido.getText());

                //status_retorno = ? WHERE idtela = ?
                spd.updatestatusretorno(spb);
            } else {
                spb.setStatus_retorno("Faturado");
                spb.setIdtela(txtnumeropedido.getText());

                //status_retorno = ? WHERE idtela = ?
                spd.updatestatusretorno(spb);
            }
            filltablepedidoorcamento();
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int numerotrue = 0;
        int numerooss = 0;
        for (int i = 0; i < tableitensorcamento.getRowCount(); i++) {
            if (tableitensorcamento.getValueAt(i, 0).equals(true)) {
                numerotrue++;
            }
            if (tableitensorcamento.getValueAt(i, 0).equals(true) && !tableitensorcamento.getValueAt(i, 9).equals("")) {
                numerooss++;
            }
        }
        if (numerotrue == 0) {
            JOptionPane.showMessageDialog(rootPane, "Não foram selecionados itens para abrir OS.");
        } else if (numerooss > 0) {
            JOptionPane.showMessageDialog(rootPane, "Item(ns) com OS selecionado(s).");
        } else {
            OSDAO od = new OSDAO();
            OSBean ob = new OSBean();

            //Criar OS
            ServicoPedidoItensDAO spid = new ServicoPedidoItensDAO();
            ServicoPedidoItensBean spib = new ServicoPedidoItensBean();
            for (int i = 0; i < tableitensorcamento.getRowCount(); i++) {
                if (tableitensorcamento.getValueAt(i, 0).equals(true)) {
                    try {
                        if (od.readnome() == false) {
                            Calendar ca = Calendar.getInstance();
                            String patterny = "yy";
                            SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                            String year = simpleDateFormaty.format(ca.getTime());
                            String idtela = "OS" + year + "-0001";
                            ob.setIdtela(idtela);
                        } else {
                            Calendar ca = Calendar.getInstance();
                            String patterny = "yy";
                            SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
                            String year = simpleDateFormaty.format(ca.getTime());
                            String hua = "";
                            for (OSBean sob2 : od.read()) {
                                hua = String.valueOf(sob2.getIdtela());
                            }
                            int yearint = Integer.parseInt(hua.replace("OS" + year + "-", ""));
                            int yearnovo = yearint + 1;
                            String idtela = "OS" + year + "-" + String.format("%04d", yearnovo);
                            ob.setIdtela(idtela);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(CotacaoServico.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Calendar c = Calendar.getInstance();
                    String pattern = "dd/MM/yyyy HH:mm:ss";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    String data = simpleDateFormat.format(c.getTime());
                    ob.setDataabertura(data);
                    int days = Integer.parseInt(tableitensorcamento.getValueAt(i, 7).toString().replace(" dias", ""));
                    c.add(Calendar.DAY_OF_MONTH, days);
                    ob.setDataprevisao(simpleDateFormat.format(c.getTime()));
                    ob.setStatus("Rascunho");
                    ob.setCliente(txtclientepedido.getText());
                    ob.setDas(txtnumeropedido.getText());
                    ob.setCodigo(tableitensorcamento.getValueAt(i, 2).toString());
                    ob.setDescricao(tableitensorcamento.getValueAt(i, 3).toString());
                    ob.setQtdinicial(Integer.parseInt(tableitensorcamento.getValueAt(i, 4).toString()));
                    ob.setQtdok(Integer.parseInt(tableitensorcamento.getValueAt(i, 4).toString()));
                    ob.setQtdnaook(0);
                    ob.setNotes("");
                    ob.setTopo("false");
                    ob.setReconstrucao("false");
                    ob.setCompleta("false");
                    ob.setRaio("");
                    ob.setFrontal("");

                    //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, descricao, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, completa, raio, frontal
                    od.create(ob);

                    String oscriada = "";
                    String n = tableitensorcamento.getValueAt(i, 10).toString();

                    for (OSBean osb : od.readcreated(tableitensorcamento.getValueAt(i, 2).toString(), data)) {
                        oscriada = osb.getIdtela();
                    }

                    spib.setIdpedido(txtnumeropedido.getText());
                    spib.setCodigo(tableitensorcamento.getValueAt(i, 2).toString());
                    spib.setDescricao(tableitensorcamento.getValueAt(i, 3).toString());
                    spib.setQtde(tableitensorcamento.getValueAt(i, 4).toString());
                    spib.setValor(tableitensorcamento.getValueAt(i, 5).toString());
                    spib.setTotal(tableitensorcamento.getValueAt(i, 6).toString());
                    spib.setPrazo(tableitensorcamento.getValueAt(i, 7).toString());
                    spib.setPedidocliente(tableitensorcamento.getValueAt(i, 8).toString());
                    spib.setOs(oscriada);
                    if (n.isEmpty()) {
                        spib.setNf("");
                    } else {
                        spib.setNf(n);
                    }

                    spib.setId(Integer.parseInt(tableitensorcamento.getValueAt(i, 1).toString()));

                    //idpedido = ?, codigo = ?, descricao = ?, qtde = ?, valor = ?, total = ?, prazo = ?, pedidocliente = ?, os = ?, nf = ? WHERE id = ?
                    spid.update(spib);

                    //Documentos do Produto na OS
                    ServicoMateriaisDAO smd = new ServicoMateriaisDAO();

                    int idmaterial = 0;

                    for (ServicoMateriaisBean smbb : smd.readid(tableitensorcamento.getValueAt(i, 2).toString())) {
                        idmaterial = smbb.getId();
                    }

                    OSDocumentosDAO odd = new OSDocumentosDAO();
                    OSDocumentosBean odb = new OSDocumentosBean();

                    ServicoMateriaisDocumentosDAO smdd = new ServicoMateriaisDocumentosDAO();

                    for (ServicoMateriaisDocumentosBean smdb : smdd.read(idmaterial)) {
                        File fileoriginal = new File(smdb.getLocal());
                        File folder = new File("Q:/MIKE_ERP/os_arq/" + oscriada);
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

                        odb.setIdos(oscriada);
                        odb.setDesc(smdb.getDescricao());
                        odb.setLocal(filecopy.toString());

                        //idos, descricao, local
                        odd.create(odb);
                    }

                    //Descobrir grupo e colocar processos do grupo na OS
                    String grupo = "";
                    for (ServicoMateriaisBean smb : smd.readgrupo(tableitensorcamento.getValueAt(i, 2).toString())) {
                        grupo = smb.getGrupo_de_processos();
                    }

                    ServicoGrupoDeProcessosDAO sgpd = new ServicoGrupoDeProcessosDAO();

                    int idgrupo = 0;
                    for (ServicoGrupoDeProcessosBean sgpb : sgpd.readidgrupo(grupo)) {
                        idgrupo = sgpb.getId();
                    }

                    ServicoGrupoDeProcessosItensDAO sgpid = new ServicoGrupoDeProcessosItensDAO();

                    int ordem = 0;

                    OSProcessosDAO opd = new OSProcessosDAO();
                    OSProcessosBean opb = new OSProcessosBean();

                    for (ServicoGrupoDeProcessosItensBean sgpib : sgpid.read(idgrupo)) {

                        opb.setIdos(oscriada);
                        opb.setProcesso(sgpib.getProcesso());
                        opb.setInicio("");
                        opb.setTermino("");
                        opb.setQtdok(0);
                        opb.setQtdnaook(0);
                        opb.setUsuario("");
                        opb.setOrdem(ordem);
                        opb.setDisponivel(tableitensorcamento.getValueAt(i, 4).toString());

                        //idos, processo, inicio, termino, qtdok, qtdnaook, usuario, ordem, disponivel
                        opd.create(opb);

                        ordem++;
                    }
                }
            }
            readitenscobranca();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tableitensorcamentoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableitensorcamentoMouseReleased
        int r = tableitensorcamento.rowAtPoint(evt.getPoint());
        if (r >= 0 && r < tableitensorcamento.getRowCount()) {
            tableitensorcamento.setRowSelectionInterval(r, r);
        } else {
            tableitensorcamento.clearSelection();
        }
    }//GEN-LAST:event_tableitensorcamentoMouseReleased

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        String n = tableitensorcamento.getValueAt(0, 10).toString();
        if (n.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "null");
        } else {
            JOptionPane.showMessageDialog(null, n);
        }
    }//GEN-LAST:event_jButton13ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCondicao;
    private javax.swing.JButton BtnProcurar;
    private javax.swing.JButton BtnRepresentante;
    private javax.swing.JButton BtnVendedor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
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
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextField jTextField3;
    public static javax.swing.JTable tabledocumentos;
    public static javax.swing.JTable tableitensnota;
    public static javax.swing.JTable tableitensorcamento;
    public static javax.swing.JTable tablepedidoservico;
    public static javax.swing.JTabbedPane tabpedidos;
    public static javax.swing.JTextField txtclientepedido;
    public static javax.swing.JTextField txtcondicao;
    public static javax.swing.JTextField txtnfcliente;
    public static javax.swing.JTextArea txtnotes;
    public static javax.swing.JTextField txtnumeropedido;
    public static javax.swing.JTextField txtorcamento;
    public static javax.swing.JTextField txtrepresentante;
    public static javax.swing.JTextField txtstatuscobranca;
    public static javax.swing.JTextField txtstatusretorno;
    public static javax.swing.JTextField txttotal;
    public static javax.swing.JTextField txttotalretorno;
    public static javax.swing.JTextField txtvendedor;
    // End of variables declaration//GEN-END:variables
}
