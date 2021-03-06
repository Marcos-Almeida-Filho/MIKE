/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.servicos;

import Bean.F_UPBean;
import Bean.F_UP_HistBean;
import Bean.OSBean;
import Bean.OSDocumentosBean;
import Bean.OSProcessosBean;
import Bean.ServicoMateriaisBean;
import Bean.ServicoMateriaisDocumentosBean;
import Bean.ServicoMateriaisMovimentacaoBean;
import Bean.ServicoPedidoBean;
import Bean.ServicoPedidoDocumentosBean;
import Bean.ServicoPedidoItensBean;
import Bean.ServicoPedidoItensNFBean;
import Connection.Session;
import DAO.F_UPDAO;
import DAO.F_UP_HistDAO;
import DAO.OSDAO;
import DAO.OSDocumentosDAO;
import DAO.OSProcessosDAO;
import DAO.ServicoMateriaisDAO;
import DAO.ServicoMateriaisDocumentosDAO;
import DAO.ServicoMateriaisMovimentacaoDAO;
import DAO.ServicoPedidoDAO;
import DAO.ServicoPedidoDocumentosDAO;
import DAO.ServicoPedidoItensDAO;
import DAO.ServicoPedidoItensNFDAO;
import Methods.Dates;
import Methods.Numeros;
import Methods.SendEmail;
import Methods.Telas;
import Methods.Valores;
import View.Geral.ProcurarCliente;
import View.Geral.ProcurarCondicaoDePagamento;
import View.Geral.ProcurarRepresentante;
import View.Geral.ProcurarVendedor;
import View.TelaPrincipal;
import static View.TelaPrincipal.jDesktopPane1;
import static View.servicos.OS.txtnumeroos;
import java.awt.AWTException;
import java.awt.Desktop;
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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class PedidoServico extends javax.swing.JInternalFrame {

    OSDAO od = new OSDAO();
    OSBean ob = new OSBean();

    //Criar OS
    ServicoPedidoItensDAO spid = new ServicoPedidoItensDAO();
    ServicoPedidoItensBean spib = new ServicoPedidoItensBean();

    //DAO e Bean para atualizar estoque do item
    ServicoMateriaisDAO smd = new ServicoMateriaisDAO();
    ServicoMateriaisBean smb = new ServicoMateriaisBean();

    //DAO e Bean para alterar pedido
    static ServicoPedidoDAO spd = new ServicoPedidoDAO();
    ServicoPedidoBean spb = new ServicoPedidoBean();

    //DAO e Bean para criar movimentação do material
    ServicoMateriaisMovimentacaoDAO smmd = new ServicoMateriaisMovimentacaoDAO();
    ServicoMateriaisMovimentacaoBean smmb = new ServicoMateriaisMovimentacaoBean();

    static ServicoPedidoItensNFDAO spinfd = new ServicoPedidoItensNFDAO();

    ServicoPedidoDocumentosDAO spdd = new ServicoPedidoDocumentosDAO();

    ServicoPedidoItensNFBean spinfb;

    F_UPDAO fud = new F_UPDAO();

    public static int vezes = 0;

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

        String status = cbStatus.getSelectedItem().toString();

        if (txtPesquisa.getText().equals("")) {
            switch (status) {
                case "Em Aberto":
                    spd.readEmAberto().forEach(spb -> {
                        model.addRow(new Object[]{
                            spb.getIdtela(),
                            spb.getCliente(),
                            spb.getNfcliente(),
                            spb.getPedidocliente(),
                            spb.getStatus_retorno(),
                            spb.getStatus_cobranca()
                        });
                    });
                    break;
                case "Todos":
                    spd.readTodos().forEach(spb -> {
                        model.addRow(new Object[]{
                            spb.getIdtela(),
                            spb.getCliente(),
                            spb.getNfcliente(),
                            spb.getPedidocliente(),
                            spb.getStatus_retorno(),
                            spb.getStatus_cobranca()
                        });
                    });
                    break;
                default:
                    spd.readStatus(status).forEach(spb -> {
                        model.addRow(new Object[]{
                            spb.getIdtela(),
                            spb.getCliente(),
                            spb.getNfcliente(),
                            spb.getPedidocliente(),
                            spb.getStatus_retorno(),
                            spb.getStatus_cobranca()
                        });
                    });
                    break;
            }
        } else {
            String pesquisa = txtPesquisa.getText();
            switch (status) {
                case "Em Aberto":
                    spd.readEmAbertoPesquisa(pesquisa).forEach(spb -> {
                        model.addRow(new Object[]{
                            spb.getIdtela(),
                            spb.getCliente(),
                            spb.getNfcliente(),
                            spb.getPedidocliente(),
                            spb.getStatus_retorno(),
                            spb.getStatus_cobranca()
                        });
                    });
                    break;
                case "Todos":
                    spd.readTodosPesquisa(pesquisa).forEach(spb -> {
                        model.addRow(new Object[]{
                            spb.getIdtela(),
                            spb.getCliente(),
                            spb.getNfcliente(),
                            spb.getPedidocliente(),
                            spb.getStatus_retorno(),
                            spb.getStatus_cobranca()
                        });
                    });
                    break;
                default:
                    spd.readStatusPesquisa(status, pesquisa).forEach(spb -> {
                        model.addRow(new Object[]{
                            spb.getIdtela(),
                            spb.getCliente(),
                            spb.getNfcliente(),
                            spb.getPedidocliente(),
                            spb.getStatus_retorno(),
                            spb.getStatus_cobranca()
                        });
                    });
                    break;
            }
        }
    }

    public void checkStatusRetorno() {
        int numeronota = 0;
        for (int i = 0; i < tableitensnota.getRowCount(); i++) {
            if (!tableitensnota.getValueAt(i, 7).equals("")) {
                numeronota++;
            }
        }
        if (numeronota < tableitensnota.getRowCount()) {
            spb.setStatus_retorno("Parcialmente Faturado");
            spb.setIdtela(txtnumeropedido.getText());

            //status_retorno = ? WHERE idtela = ?
            spd.updatestatusretorno(spb);
        } else {
            spb.setStatus_retorno("Faturado");
            spb.setIdtela(txtnumeropedido.getText());

            //status_retorno = ? WHERE idtela = ?
            spd.updatestatusretorno(spb);
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

    public void lerDocs(String pedido) {
        DefaultTableModel modeldoc = (DefaultTableModel) tabledocumentos.getModel();
        modeldoc.setNumRows(0);

        spdd.readitens(pedido).forEach((spdb) -> {
            modeldoc.addRow(new Object[]{
                false,
                spdb.getId(),
                spdb.getDescricao(),
                spdb.getLocal(),});
        });
    }

    public void lerPedido(String pedido) {
        txtnumeropedido.setText(pedido);

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
            txtPedidoCliente.setText(spb.getPedidocliente());
        }

        camposeditaveis();

        readitenscobranca();

        readitensretorno();

        lerDocs(pedido);
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
                Dates.TransformarDataCurtaDoDB(spib.getPrazoDate()),
                spib.getOs(),
                spib.getNf()
            });
        }
        
        txtvalorcobranca();
    }

    public static void readitensretorno() {
        DefaultTableModel model = (DefaultTableModel) tableitensnota.getModel();
        model.setNumRows(0);

        spinfd.readitens(txtnumeropedido.getText()).forEach(spib -> {
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
        });
        
        txtvalorretorno();
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
        txtPesquisa = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablepedidoservico = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        cbStatus = new javax.swing.JComboBox<>();
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
        jLabel8 = new javax.swing.JLabel();
        txtorcamento = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtnfcliente = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtstatuscobranca = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtPedidoCliente = new javax.swing.JTextField();
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
        btnAll = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableitensnota = new javax.swing.JTable();
        txttotalretorno = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        btnSelecionarItensRetorno = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Pedido de Serviço");

        tabpedidos.setName("tabpedidos"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Procura"));

        jLabel4.setText("Pesquisa");

        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablepedidoservico.setAutoCreateRowSorter(true);
        tablepedidoservico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Nota Fiscal Cliente", "Pedido Cliente", "Status Retorno", "Status Cobrança"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Aberto", "Ativo", "Parcialmente Faturado", "Faturado", "Cancelado", "Todos" }));
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbStatus, 0, 169, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 563, Short.MAX_VALUE)
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
        txtclientepedido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtclientepedidoKeyReleased(evt);
            }
        });

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

        jLabel8.setText("Orçamento");

        txtorcamento.setEditable(false);
        txtorcamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel9.setText("NF Cliente");

        txtnfcliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel12.setText("Cobrança");

        txtstatuscobranca.setEditable(false);
        txtstatuscobranca.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel13.setText("Pedido do Cliente");

        txtPedidoCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
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
                            .addComponent(txtorcamento)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnfcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPedidoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtnfcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtPedidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1261, Short.MAX_VALUE)
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
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1261, Short.MAX_VALUE)
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
                "", "ID", "Código", "Descrição", "Qtde", "Valor Unitário", "Total", "Prazo de Entrega", "OS", "NF Cobrança"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false
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
            tableitensorcamento.getColumnModel().getColumn(8).setMinWidth(95);
            tableitensorcamento.getColumnModel().getColumn(8).setPreferredWidth(95);
            tableitensorcamento.getColumnModel().getColumn(8).setMaxWidth(95);
            tableitensorcamento.getColumnModel().getColumn(9).setMinWidth(90);
            tableitensorcamento.getColumnModel().getColumn(9).setPreferredWidth(90);
            tableitensorcamento.getColumnModel().getColumn(9).setMaxWidth(90);
        }

        jButton2.setText("Incluir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setText("Excluir");
        jButton5.setName("btnexcluir"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

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

        btnAll.setText("Selecionar Todos");
        btnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllActionPerformed(evt);
            }
        });

        jButton13.setText("Duplicar itens para Retorno");
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
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1261, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 400, Short.MAX_VALUE)
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
                    .addComponent(btnAll)
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
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton12.setText("Lançar Nota Fiscal de Retorno");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        btnSelecionarItensRetorno.setText("Selecionar Todos");
        btnSelecionarItensRetorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarItensRetornoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1261, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnSelecionarItensRetorno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 655, Short.MAX_VALUE)
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
                    .addComponent(jButton12)
                    .addComponent(btnSelecionarItensRetorno))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Itens de Retorno", jPanel6);

        jButton3.setText("Novo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton14.setText("Cancelar Pedido");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
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
                        .addComponent(jButton14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(jButton3)
                    .addComponent(jButton14))
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
        ProcurarCliente p = new ProcurarCliente("ServiçoPedido");
        Telas.AparecerTela(p);
    }//GEN-LAST:event_BtnProcurarActionPerformed

    private void BtnCondicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCondicaoActionPerformed
        ProcurarCondicaoDePagamento p = new ProcurarCondicaoDePagamento("ServiçoPedido");
        Telas.AparecerTela(p);
    }//GEN-LAST:event_BtnCondicaoActionPerformed

    private void BtnRepresentanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRepresentanteActionPerformed
        ProcurarRepresentante p = new ProcurarRepresentante("ServiçoPedido");
        Telas.AparecerTela(p);
    }//GEN-LAST:event_BtnRepresentanteActionPerformed

    private void BtnVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVendedorActionPerformed
        ProcurarVendedor p = new ProcurarVendedor("ServiçoPedido");
        Telas.AparecerTela(p);
    }//GEN-LAST:event_BtnVendedorActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ItemPedidoServico p = new ItemPedidoServico();
        Telas.AparecerTela(p);
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
        Telas.AparecerTela(d);
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
                    for (ServicoPedidoBean spb2 : spd.readTodos()) {
                        hua = String.valueOf(spb2.getIdtela());
                    }
                    int yearint = Integer.parseInt(hua.replace("PS" + year + "-", ""));
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
            spb.setPedidocliente(txtPedidoCliente.getText());
            Calendar date = Calendar.getInstance();
            String pattern = "dd/MM/yyyy HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            spb.setData(simpleDateFormat.format(date.getTime()));

            //idtela, idorcamento, cliente, condicao, representante, vendedor, notes, status_retorno, status_cobranca, nfcliente, data
            spd.create(spb);

            for (int i = 0; i < tableitensorcamento.getRowCount(); i++) {
                spib.setIdpedido(txtnumeropedido.getText());
                spib.setCodigo(tableitensorcamento.getValueAt(i, 2).toString());
                spib.setDescricao(tableitensorcamento.getValueAt(i, 3).toString());
                spib.setQtde(tableitensorcamento.getValueAt(i, 4).toString());
                spib.setValor(tableitensorcamento.getValueAt(i, 5).toString());
                spib.setTotal(tableitensorcamento.getValueAt(i, 6).toString());
                spib.setPrazo(tableitensorcamento.getValueAt(i, 7).toString());
                spib.setOs(tableitensorcamento.getValueAt(i, 8).toString());
                spib.setNf(tableitensorcamento.getValueAt(i, 9).toString());
                spib.setPrazoDate(Dates.CriarDataCurtaDBComDataExistente(tableitensorcamento.getValueAt(i, 7).toString()));

                //idpedido, codigo, descricao, qtde, valor, total, prazo, pedidocliente, nf
                spid.create(spib);
            }

            //Criar itens do pedido (Nota Fiscal)
            spinfb = new ServicoPedidoItensNFBean();

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
            spdd = new ServicoPedidoDocumentosDAO();
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
            spb.setCliente(txtclientepedido.getText());
            spb.setCondicao(txtcondicao.getText());
            spb.setRepresentante(txtrepresentante.getText());
            spb.setVendedor(txtvendedor.getText());
            spb.setNotes(txtnotes.getText());
            spb.setStatus_retorno(txtstatusretorno.getText());
            spb.setNfcliente(txtnfcliente.getText());
            spb.setPedidocliente(txtPedidoCliente.getText());
            spb.setIdtela(txtnumeropedido.getText());

            //cliente = ?, condicao = ?, representante = ?, vendedor = ?, notes = ?, nfcliente = ? WHERE idtela = ?
            spd.update(spb);

            for (int i = 0; i < tableitensorcamento.getRowCount(); i++) {
                if (tableitensorcamento.getValueAt(i, 1).equals("")) {
                    spib = new ServicoPedidoItensBean();

                    spib.setIdpedido(txtnumeropedido.getText());
                    spib.setCodigo(tableitensorcamento.getValueAt(i, 2).toString());
                    spib.setDescricao(tableitensorcamento.getValueAt(i, 3).toString());
                    spib.setQtde(tableitensorcamento.getValueAt(i, 4).toString());
                    spib.setValor(tableitensorcamento.getValueAt(i, 5).toString());
                    spib.setTotal(tableitensorcamento.getValueAt(i, 6).toString());
                    spib.setPrazo(tableitensorcamento.getValueAt(i, 7).toString());
                    spib.setOs(tableitensorcamento.getValueAt(i, 8).toString());
                    spib.setNf(tableitensorcamento.getValueAt(i, 9).toString());
                    spib.setPrazoDate(Dates.CriarDataCurtaDBComDataExistente(tableitensorcamento.getValueAt(i, 7).toString()));

                    //idpedido, codigo, descricao, qtde, valor, total, prazo, pedidocliente, nf
                    spid.create(spib);
                } else {
                    spib.setCodigo(tableitensorcamento.getValueAt(i, 2).toString());
                    spib.setDescricao(tableitensorcamento.getValueAt(i, 3).toString());
                    spib.setQtde(tableitensorcamento.getValueAt(i, 4).toString());
                    spib.setValor(tableitensorcamento.getValueAt(i, 5).toString());
                    spib.setTotal(tableitensorcamento.getValueAt(i, 6).toString());
                    spib.setPrazo(tableitensorcamento.getValueAt(i, 7).toString());
                    spib.setId(Integer.parseInt(tableitensorcamento.getValueAt(i, 1).toString()));
                    spib.setPrazoDate(Dates.CriarDataCurtaDBComDataExistente(tableitensorcamento.getValueAt(i, 7).toString()));

                    //codigo = ?, descricao = ?, qtde = ?, valor = ?, total = ?, prazo = ? WHERE id = ?
                    spid.update(spib);
                }

            }

            //Criar itens do pedido (Nota Fiscal)
            spinfb = new ServicoPedidoItensNFBean();

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

            lerPedido(tablepedidoservico.getValueAt(tablepedidoservico.getSelectedRow(), 0).toString());
        }
    }//GEN-LAST:event_tablepedidoservicoMouseClicked

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
        Telas.AparecerTela(p);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
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
            if (tableitensorcamento.getValueAt(i, 0).equals(true) && !tableitensorcamento.getValueAt(i, 9).equals("")) {
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

            if (!nota.equals(null)) {
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
                        //if (saldoatual >= qtditem) { //Verificar se o saldo atual é maior ou igual à quantidade do pedido 
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
                        smmb.setUsuario(Session.nome);

                        //idmaterial, inicial, movimentada, tipo, saldo, data, usuario
                        smmd.create(smmb);

                        //Alterar estoque do material
                        smb.setEstoque(saldoatual - qtditem);
                        smb.setId(idmaterial);

                        //estoque = ? WHERE id = ?
                        smd.updateestoque(smb);
                        /*} else {
                        JOptionPane.showMessageDialog(rootPane, "O item " + tableitensorcamento.getValueAt(i, 2) + " não possui saldo suficiente.\n Favor verificar informações e refazer processo.");
                    }*/
                    }
                }
            }

            //Atualizar table com os itens
            readitenscobranca();
            //Pegar número de linhas com nota lançada depois de clicar no botão
            for (int i = 0; i < rc; i++) {
                if (!tableitensorcamento.getValueAt(i, 9).equals("")) {
                    numeronota2++;
                }
            }
            //Alterar status do pedido de acordo com o número de linhas com notas lançadas
            if (numeronota2 < rc) {
                spb.setStatus_cobranca("Parcialmente Faturado");
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
                Telas.AparecerTela(p);

                int row = tableitensorcamento.getSelectedRow();
                ItemPedidoServico.txtrow.setText(String.valueOf(row));
                ItemPedidoServico.txtid.setText(tableitensorcamento.getValueAt(row, 1).toString());
                ItemPedidoServico.txtcodigo.setText(tableitensorcamento.getValueAt(row, 2).toString());
                ItemPedidoServico.txtdesc.setText(tableitensorcamento.getValueAt(row, 3).toString());
                ItemPedidoServico.txtqtd.setText(tableitensorcamento.getValueAt(row, 4).toString());
                ItemPedidoServico.txtvalor.setText(tableitensorcamento.getValueAt(row, 5).toString());
                Dates.SetarDataJDateChooser(ItemPedidoServico.datePrazo, Dates.CriarDataCurtaDBComDataExistente(tableitensorcamento.getValueAt(row, 7).toString()));
            }
        }
        if (evt.getButton() == 3) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem das = new JMenuItem("Abrir OS");

            das.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String OSstring = tableitensorcamento.getValueAt(tableitensorcamento.getSelectedRow(), 8).toString();
                    if (OSstring.equals("")) {
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
                        OS.txtnumeroos.setText(OSstring);
                        OS.tabos.setSelectedIndex(1);

                        OSDAO od = new OSDAO();

                        for (OSBean ob : od.click(txtnumeroos.getText())) {
                            OS.txtabertura.setText(ob.getDataabertura());
                            OS.txtprevisao.setText(ob.getDataprevisao());
                            OS.txtstatus.setText(ob.getStatus());
                            OS.txtcliente.setText(ob.getCliente());
                            OS.txtdas.setText(ob.getDas());
                            OS.txtcodigo.setText(ob.getCodigo());
                            OS.txtdesc.setText(ob.getDescricao());
                            OS.txtinicial.setText(String.valueOf(ob.getQtdinicial()));
                            OS.txtfinal.setText(String.valueOf(ob.getQtdok()));
                            OS.txtmortas.setText(String.valueOf(ob.getQtdnaook()));
                            OS.txtnotes.setText(ob.getNotes());
                            if (ob.getTopo().equals("true")) {
                                OS.radiotopo.setSelected(true);
                            }
                            if (ob.getReconstrucao().equals("true")) {
                                OS.radioreconstrucao.setSelected(true);
                            }
                            if (ob.getCompleta().equals("true")) {
                                OS.radiocompleta.setSelected(true);
                            }
                            if (ob.getDesenho().equals("true")) {
                                OS.radiodesenho.setSelected(true);
                            }
                            OS.txtraio.setText(ob.getRaio());
                            OS.txtfrontal.setText(ob.getFrontal());

                        }

                        //Pegar documentos
                        OS.readdocs();

                        //Pegar processos
                        OS.readprocessos();

                        //Travar campos de acordo com status da op
                        OS.travarcampos();
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
            spb = new ServicoPedidoBean();

            String nota = JOptionPane.showInputDialog(rootPane, "Qual o número da nota de retorno?", "Nota de Retorno", JOptionPane.YES_NO_OPTION);
            for (int i = 0; i < rc; i++) {
                if (tableitensnota.getValueAt(i, 0).equals(true)) {
                    spinfb = new ServicoPedidoItensNFBean();

                    spinfb.setNfretorno(nota);
                    spinfb.setId(Integer.parseInt(tableitensnota.getValueAt(i, 1).toString()));

                    //nf = ? WHERE id = ?
                    spinfd.updatenotaretorno(spinfb);
                }
            }
            readitensretorno();

            checkStatusRetorno();

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
            if (tableitensorcamento.getValueAt(i, 0).equals(true) && !tableitensorcamento.getValueAt(i, 8).equals("")) {
                numerooss++;
            }
        }
        if (numerotrue == 0) {
            JOptionPane.showMessageDialog(rootPane, "Não foram selecionados itens para abrir OS.");
        } else if (numerooss > 0) {
            JOptionPane.showMessageDialog(rootPane, "Item(ns) com OS selecionado(s).");
        } else {
            vezes = numerotrue;
            for (int i = 0; i < tableitensorcamento.getRowCount(); i++) {
                if (tableitensorcamento.getValueAt(i, 0).equals(true)) {
                    String idos = Dates.CriarIdOS();
                    ob.setIdtela(idos);
                    String data = Dates.CriarDataCompletaParaDB();
                    ob.setDateabertura(data);
                    String dataPrevista = tableitensorcamento.getValueAt(i, 7).toString();
                    String dataPrevisao = Dates.CriarDataCurtaDBComDataExistente(dataPrevista);
                    ob.setDateprevisao(dataPrevisao);
                    ob.setStatus("Rascunho");
                    ob.setCliente(txtclientepedido.getText());
                    ob.setDas(txtnumeropedido.getText());
                    ob.setCodigo(tableitensorcamento.getValueAt(i, 2).toString());
                    ob.setDescricao(tableitensorcamento.getValueAt(i, 3).toString());
                    ob.setQtdinicial(Numeros.TransformarNumeroEmInt(tableitensorcamento.getValueAt(i, 4).toString()));
                    ob.setQtdok(Numeros.TransformarNumeroEmInt(tableitensorcamento.getValueAt(i, 4).toString()));
                    ob.setQtdnaook(0);
                    ob.setNotes("");
                    ob.setTopob(false);
                    ob.setReconstrucaob(false);
                    ob.setCompletab(false);
                    ob.setDesenhob(false);
                    ob.setRaio("");
                    ob.setFrontal("");

                    //idtela, dateabertura, dateprevisao, status, cliente, das, codigo, descricao, qtdinicial, qtdok, qtdnaook, notes, topob, reconstrucaob, completab, desenhob, raio, frontal
                    od.create(ob);

                    spib.setOs(idos);
                    spib.setId(Integer.parseInt(tableitensorcamento.getValueAt(i, 1).toString()));

                    //os = ? WHERE id = ?
                    spid.updateOS(spib);

                    //Documentos do Produto na OS
                    int idmaterial = 0;

                    for (ServicoMateriaisBean smbb : smd.readid(tableitensorcamento.getValueAt(i, 2).toString())) {
                        idmaterial = smbb.getId();
                    }

                    OSDocumentosDAO odd = new OSDocumentosDAO();
                    OSDocumentosBean odb = new OSDocumentosBean();

                    ServicoMateriaisDocumentosDAO smdd = new ServicoMateriaisDocumentosDAO();

                    for (ServicoMateriaisDocumentosBean smdb : smdd.read(idmaterial)) {
                        File fileoriginal = new File(smdb.getLocal());
                        File folder = new File("Q:/MIKE_ERP/os_arq/" + idos);
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

                        odb.setIdos(idos);
                        odb.setDesc(smdb.getDescricao());
                        odb.setLocal(filecopy.toString());

                        //idos, descricao, local
                        odd.create(odb);
                    }

//                    //Descobrir grupo e colocar processos do grupo na OS
//                    String grupo = "";
//                    for (ServicoMateriaisBean smb : smd.readgrupo(tableitensorcamento.getValueAt(i, 2).toString())) {
//                        grupo = smb.getGrupo_de_processos();
//                    }
//
//                    ServicoGrupoDeProcessosDAO sgpd = new ServicoGrupoDeProcessosDAO();
//
//                    int idgrupo = 0;
//                    for (ServicoGrupoDeProcessosBean sgpb : sgpd.readidgrupo(grupo)) {
//                        idgrupo = sgpb.getId();
//                    }
//
//                    ServicoGrupoDeProcessosItensDAO sgpid = new ServicoGrupoDeProcessosItensDAO();
//
//                    int ordem = 0;
//
                    OSProcessosDAO opd = new OSProcessosDAO();
                    OSProcessosBean opb = new OSProcessosBean();
//
//                    for (ServicoGrupoDeProcessosItensBean sgpib : sgpid.read(idgrupo)) {
//
                    String processo = "Rascunho";

                    opb.setIdos(idos);
                    opb.setProcesso(processo);
                    opb.setInicio("");
                    opb.setTermino("");
                    opb.setQtdok(0);
                    opb.setQtdnaook(0);
                    opb.setUsuario("");
                    opb.setOrdem(0);
                    opb.setDisponivel(Integer.parseInt(tableitensorcamento.getValueAt(i, 4).toString()));

                    //idos, processo, inicio, termino, qtdok, qtdnaook, usuario, ordem, disponivel
                    opd.create(opb);
//
//                        ordem++;
//                    }

                    F_UPDAO fd = new F_UPDAO();
                    F_UPBean fb = new F_UPBean();

                    fb.setDav(txtnumeropedido.getText());
                    fb.setOp(idos);
                    fb.setDataentrega(dataPrevisao);
                    fb.setMaterial(tableitensorcamento.getValueAt(i, 2).toString());
                    fb.setProcesso(processo);
                    fb.setDatacriacao(data);
                    fb.setNivel(5);
                    fb.setValor(Double.parseDouble(Valores.TransformarStringDinheiroEmStringDouble(tableitensorcamento.getValueAt(i, 6).toString())));
                    fb.setObservacao("");
                    fb.setCliente(txtclientepedido.getText());

                    //dav, op, dataentrega, material, processo, datacriacao, nivel, valor, observacao, cliente
                    fd.create(fb);

                    F_UP_HistDAO fuhd = new F_UP_HistDAO();
                    F_UP_HistBean fuhb = new F_UP_HistBean();

                    fuhb.setIdfup(fd.getId(idos));
                    fuhb.setProcesso(processo);

                    //idfup, processo, funcionario, data
                    fuhd.create(fuhb);
                }
            }
            vezes = 0;
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

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int numTrue = 0;
        for (int i = 0; i < tableitensorcamento.getRowCount(); i++) {
            if (tableitensorcamento.getValueAt(i, 0).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado!");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o(s) item(ns) selecionado(s)?\nNo caso de haver OS lançada no item, será excluída juntamente com o item.", "Excluir", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                for (int i = 0; i < tableitensorcamento.getRowCount(); i++) {
                    if (tableitensorcamento.getValueAt(i, 0).equals(true)) {
                        String op = tableitensorcamento.getValueAt(i, 8).toString();

                        if (!tableitensorcamento.getValueAt(i, 8).toString().equals("")) {
                            od.delete(op);
                        }

                        spid.delete(Integer.parseInt(tableitensorcamento.getValueAt(i, 1).toString()));

                        fud.deletePorOP(op);
                    }
                }
                readitenscobranca();
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        if (txtnumeropedido.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione um pedido primeiro!");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja cancelar o pedido?", "Cancelar Pedido", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                spb = new ServicoPedidoBean();

                spb.setStatus_cobranca("Cancelado");
                spb.setStatus_retorno("Cancelado");
                spb.setIdtela(txtnumeropedido.getText());

                //SET status_cobranca = ? WHERE idtela = ?
                spd.updatestatuscobranca(spb);
                //SET status_retorno = ? WHERE idtela = ?
                spd.updatestatusretorno(spb);

                for (ServicoPedidoBean spb2 : spd.click(txtnumeropedido.getText())) {
                    txtclientepedido.setText(spb2.getCliente());
                    txtcondicao.setText(spb2.getCondicao());
                    txtrepresentante.setText(spb2.getRepresentante());
                    txtvendedor.setText(spb2.getVendedor());
                    txtstatusretorno.setText(spb2.getStatus_retorno());
                    txtstatuscobranca.setText(spb2.getStatus_cobranca());
                    txtnotes.setText(spb2.getNotes());
                    txtorcamento.setText(String.valueOf(spb2.getIdorcamento()));
                    txtnfcliente.setText(spb2.getNfcliente());
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
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void txtclientepedidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtclientepedidoKeyReleased
        //InternalFrameProcura.procuraCliente(txtclientepedido, "ServiçoPedido");
    }//GEN-LAST:event_txtclientepedidoKeyReleased

    private void btnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllActionPerformed
        if (btnAll.getText().equals("Selecionar Todos")) {
            for (int i = 0; i < tableitensorcamento.getRowCount(); i++) {
                tableitensorcamento.setValueAt(true, i, 0);
                btnAll.setText("Desmarcar Todos");
            }
        } else {
            for (int i = 0; i < tableitensorcamento.getRowCount(); i++) {
                tableitensorcamento.setValueAt(false, i, 0);
                btnAll.setText("Selecionar Todos");
            }
        }

    }//GEN-LAST:event_btnAllActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        filltablepedidoorcamento();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        try {
            for (int i = 0; i < tableitensorcamento.getRowCount(); i++) {
                ServicoPedidoItensNFBean spinfb = new ServicoPedidoItensNFBean();

                spinfb.setIdpedido(txtnumeropedido.getText());
                spinfb.setCodigo(tableitensorcamento.getValueAt(i, 2).toString());
                spinfb.setDescricao(tableitensorcamento.getValueAt(i, 3).toString());
                spinfb.setQtde(tableitensorcamento.getValueAt(i, 4).toString());
                spinfb.setValor(tableitensorcamento.getValueAt(i, 5).toString());
                spinfb.setTotal(tableitensorcamento.getValueAt(i, 6).toString());
                spinfb.setNfretorno("");

                //idpedido, codigo, descricao, qtde, valor, total, nfretorno
                spinfd.create(spinfb);
            }

            JOptionPane.showMessageDialog(null, "Itens lançados com sucesso!");

            readitensretorno();
        } catch (Exception e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

            new Thread() {
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void btnSelecionarItensRetornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarItensRetornoActionPerformed
        if (btnSelecionarItensRetorno.getText().equals("Selecionar Todos")) {
            for (int i = 0; i < tableitensnota.getRowCount(); i++) {
                tableitensnota.setValueAt(true, i, 0);
            }
            btnSelecionarItensRetorno.setText("Desmarcar Todos");
        } else {
            for (int i = 0; i < tableitensnota.getRowCount(); i++) {
                tableitensnota.setValueAt(false, i, 0);
            }
            btnSelecionarItensRetorno.setText("Selecionar Todos");
        }
    }//GEN-LAST:event_btnSelecionarItensRetornoActionPerformed

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        filltablepedidoorcamento();
    }//GEN-LAST:event_cbStatusActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int numTrue = 0;

        for (int i = 0; i < tableitensnota.getRowCount(); i++) {
            if (tableitensnota.getValueAt(i, 0).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir os Itens de Retorno selecionados?", "Excluir Itens de Retorno", JOptionPane.YES_NO_OPTION);

            if (resp == 0) {
                DefaultTableModel model = (DefaultTableModel) tableitensnota.getModel();

                for (int i = 0; i < tableitensnota.getRowCount(); i++) {
                    if (tableitensnota.getValueAt(i, 0).equals(true)) {
                        if (tableitensnota.getValueAt(i, 1).equals("")) {
                            model.removeRow(i);
                        } else {
                            spinfd.delete(Integer.parseInt(tableitensnota.getValueAt(i, 1).toString()));
                        }
                    }
                }

                readitensretorno();

                checkStatusRetorno();

                lerPedido(txtnumeropedido.getText());
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCondicao;
    private javax.swing.JButton BtnProcurar;
    private javax.swing.JButton BtnRepresentante;
    private javax.swing.JButton BtnVendedor;
    private javax.swing.JButton btnAll;
    private javax.swing.JButton btnSelecionarItensRetorno;
    private static javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    public static javax.swing.JTable tabledocumentos;
    public static javax.swing.JTable tableitensnota;
    public static javax.swing.JTable tableitensorcamento;
    public static javax.swing.JTable tablepedidoservico;
    public static javax.swing.JTabbedPane tabpedidos;
    private javax.swing.JTextField txtPedidoCliente;
    private static javax.swing.JTextField txtPesquisa;
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
