/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.financeiro;

import Bean.ClientesContatosBean;
import Connection.Session;
import DAO.CARDAO;
import DAO.CARDocumentosDAO;
import DAO.CARObsDAO;
import DAO.ClientesContatosDAO;
import DAO.ClientesDAO;
import DAO.EmailsDAO;
import DAO.EmailsEnviadosDAO;
import Methods.Dates;
import Methods.SendEmail;
import Methods.Telas;
import Methods.Valores;
import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class ContasReceber extends javax.swing.JInternalFrame {

    //DAOs para pesquisa
    CARDAO card = new CARDAO();
    CARDocumentosDAO cdd = new CARDocumentosDAO();
    CARObsDAO cod = new CARObsDAO();
    EmailsDAO ed = new EmailsDAO();
    EmailsEnviadosDAO eed = new EmailsEnviadosDAO();
    ClientesDAO cd = new ClientesDAO();
    ClientesContatosDAO ccd = new ClientesContatosDAO();

    /**
     * Creates new form ContasPagar
     */
    public ContasReceber() {
        initComponents();
        datasJDateChooser();
        readtablecar();
        getNewRenderedTable(tablecar, 8);
        //btnEnviarLembrete();
    }

    private void btnEnviarLembrete() {
        LocalDate date = LocalDate.now();

        if (date.isEqual(eed.getUltimoEnvio("car"))) {
            btnEnviarLembrete.setEnabled(false);
        } else {
            btnEnviarLembrete.setEnabled(true);
        }
    }

    private void enviarEmails(int days) {
        JOptionPane.showMessageDialog(null, "Enviando lembretes.");

        for (int i = 1; i <= days; i++) {
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.plusDays(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date = tomorrow.format(formatter);

            for (int j = 0; j < tablecar.getRowCount(); j++) {
                String dataNormal = tablecar.getValueAt(j, 6).toString();
                String dataParsed = dataNormal.substring(6, 10) + "-" + dataNormal.substring(3, 5) + "-" + dataNormal.substring(0, 2);
                LocalDate d = LocalDate.parse(dataParsed);

                if (today.compareTo(d) > 0) {
                    String mensagemAtrasada = ed.getTextoMensagem("Lembrete Conta a Receber Atrasada");

                    //Get Destinatário
                    int idCliente = Integer.parseInt(tablecar.getValueAt(j, 9).toString());
                    List<ClientesContatosBean> destinatarios = ccd.readDestinatariosBoleto(idCliente, true);

                    String notaFiscal = tablecar.getValueAt(j, 2).toString();
                    String parcela = tablecar.getValueAt(j, 4).toString();
                    String valorParcela = tablecar.getValueAt(j, 5).toString();

                    String titulo = "Nota Fiscal: " + notaFiscal + "\nParcela: " + parcela + "\nValor: R$ " + valorParcela + "\nVencimento: " + dataNormal;
                    String mensagemFinal = mensagemAtrasada.replace("${titulo}", titulo);

                    destinatarios.forEach((destinatario) -> {
                        SendEmail.LembreteCAR(destinatario.getEmail(), mensagemFinal);
                    });
                } else {
                    String mensagemAmanha = ed.getTextoMensagem("Lembrete Conta a Receber Amanhã");

                    if (date.equals(dataNormal)) {
                        //Get Destinatário
                        int idCliente = Integer.parseInt(tablecar.getValueAt(j, 9).toString());
                        List<ClientesContatosBean> destinatarios = ccd.readDestinatariosBoleto(idCliente, true);

                        String notaFiscal = tablecar.getValueAt(j, 2).toString();
                        String parcela = tablecar.getValueAt(j, 4).toString();
                        String valorParcela = tablecar.getValueAt(j, 5).toString();

                        String titulo = "Nota Fiscal: " + notaFiscal + "\nParcela: " + parcela + "\nValor: R$ " + valorParcela + "\nVencimento: " + dataNormal;
                        String mensagemFinal = mensagemAmanha.replace("${titulo}", titulo);

                        destinatarios.forEach((destinatario) -> {
                            SendEmail.LembreteCAR(destinatario.getEmail(), mensagemFinal);
                        });
                        SendEmail.LembreteCAR(Session.emailfabrica, mensagemFinal);
                    }
                }
            }
        }

        LocalDate d = LocalDate.now();
        String data = d.toString();
        String nome = "car";
        if (eed.verRegistro(nome)) {
            eed.updateUltimoEnvio(data, nome);
        } else {
            eed.create(nome, data);
        }

        JOptionPane.showMessageDialog(null, "Lembretes enviados com sucesso.");

        btnEnviarLembrete();
    }

    private static void valores() {
        double valorRecebido = 0, valorAReceber = 0, valorTotal = 0;
        for (int i = 0; i < tablecar.getRowCount(); i++) {
            double valorParcela = Valores.TransformarDinheiroEmValorDouble(tablecar.getValueAt(i, 5).toString());
            //valorRecebido = Valores.TransformarDinheiroEmValorDouble(tablecar.getValueAt(i, 5).toString());
            valorTotal += valorParcela;

            if (tablecar.getValueAt(i, 8).equals("")) {
                valorAReceber += valorParcela;
            } else {
                valorRecebido += Valores.TransformarDinheiroEmValorDouble(tablecar.getValueAt(i, 6).toString());
            }
        }

        txtTotal.setText(Valores.TransformarDoubleDBemDinheiroComLocal(valorTotal));
        txtAReceber.setText(Valores.TransformarDoubleDBemDinheiroComLocal(valorAReceber));
        txtRecebido.setText(Valores.TransformarDoubleDBemDinheiroComLocal(valorRecebido));
    }

    private static JTable getNewRenderedTable(final JTable table, int coluna) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                String status = (String) table.getModel().getValueAt(row, coluna);
                switch (status) {
                    case "Lançado":
                        setBackground(Color.BLUE);
                        setForeground(Color.BLACK);
                        break;
                    case "Aprovado":
                        setBackground(Color.GREEN);
                        setForeground(Color.BLACK);
                        break;
                    case "Automático":
                        setBackground(Color.ORANGE);
                        setForeground(Color.BLACK);
                        break;
                    default:
                        setBackground(table.getBackground());
                        setForeground(table.getForeground());
                        break;
                }
                return this;
            }
        });
        return table;
    }

    public static void datasJDateChooser() {
        Dates.SetarDataJDateChooser(jdateinicio, Dates.primeiroDiaDoMes());
        Dates.SetarDataJDateChooser(jdatefim, Dates.ultimoDiaDoMes());
    }

    public static void readtablecar() {
        DefaultTableModel model = (DefaultTableModel) tablecar.getModel();
        model.setNumRows(0);

        CARDAO card = new CARDAO();

        String pesquisa = txtPesquisa.getText();

        String dataInicio = Dates.CriarDataCurtaDBJDateChooser(jdateinicio.getDate());
        String dataFim = Dates.CriarDataCurtaDBJDateChooser(jdatefim.getDate());

        if (pesquisa.length() > 0) {
            switch (cbstatus.getSelectedItem().toString()) {
                case "Todos":
                    card.readtodosPesquisa(pesquisa, dataInicio, dataFim).forEach((carb) -> {
                        String datapagamento;
                        if (carb.getDatarecebimento() == null) {
                            datapagamento = "";
                        } else {
                            datapagamento = Dates.TransformarDataCurtaDoDB(carb.getDatarecebimento());
                        }
                        model.addRow(new Object[]{
                            false,
                            carb.getId(),
                            carb.getNotafiscal(),
                            carb.getCliente(),
                            carb.getParcela(),
                            Valores.TransformarDoubleDBemString(carb.getValorparcela()),
                            Valores.TransformarDoubleDBemString(carb.getValorrecebido()),
                            Dates.TransformarDataCurtaDoDB(carb.getDataparcela()),
                            datapagamento,
                            carb.getStatus(),
                            carb.getIdCliente()
                        });
                    });
                    break;
                default:
                    card.readstatusPesquisa(cbstatus.getSelectedItem().toString(), pesquisa, dataInicio, dataFim).forEach((carb) -> {
                        String datapagamento;
                        if (carb.getDatarecebimento() == null) {
                            datapagamento = "";
                        } else {
                            datapagamento = Dates.TransformarDataCurtaDoDB(carb.getDatarecebimento());
                        }
                        model.addRow(new Object[]{
                            false,
                            carb.getId(),
                            carb.getNotafiscal(),
                            carb.getCliente(),
                            carb.getParcela(),
                            Valores.TransformarDoubleDBemString(carb.getValorparcela()),
                            Valores.TransformarDoubleDBemString(carb.getValorrecebido()),
                            Dates.TransformarDataCurtaDoDB(carb.getDataparcela()),
                            datapagamento,
                            carb.getStatus(),
                            carb.getIdCliente()
                        });
                    });
                    break;
            }
        } else {
            switch (cbstatus.getSelectedItem().toString()) {
                case "Todos":
                    card.readtodos(dataInicio, dataFim).forEach((carb) -> {
                        String datapagamento;
                        if (carb.getDatarecebimento() == null) {
                            datapagamento = "";
                        } else {
                            datapagamento = Dates.TransformarDataCurtaDoDB(carb.getDatarecebimento());
                        }
                        model.addRow(new Object[]{
                            false,
                            carb.getId(),
                            carb.getNotafiscal(),
                            carb.getCliente(),
                            carb.getParcela(),
                            Valores.TransformarDoubleDBemString(carb.getValorparcela()),
                            Valores.TransformarDoubleDBemString(carb.getValorrecebido()),
                            Dates.TransformarDataCurtaDoDB(carb.getDataparcela()),
                            datapagamento,
                            carb.getStatus(),
                            carb.getIdCliente()
                        });
                    });
                    break;
                default:
                    card.readstatus(cbstatus.getSelectedItem().toString(), dataInicio, dataFim).forEach((carb) -> {
                        String datapagamento;
                        if (carb.getDatarecebimento() == null) {
                            datapagamento = "";
                        } else {
                            datapagamento = Dates.TransformarDataCurtaDoDB(carb.getDatarecebimento());
                        }
                        model.addRow(new Object[]{
                            false,
                            carb.getId(),
                            carb.getNotafiscal(),
                            carb.getCliente(),
                            carb.getParcela(),
                            Valores.TransformarDoubleDBemString(carb.getValorparcela()),
                            Valores.TransformarDoubleDBemString(carb.getValorrecebido()),
                            Dates.TransformarDataCurtaDoDB(carb.getDataparcela()),
                            datapagamento,
                            carb.getStatus(),
                            carb.getIdCliente()
                        });
                    });
                    break;
            }
        }

        valores();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablecar = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        cbstatus = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jdatefim = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jdateinicio = new com.toedter.calendar.JDateChooser();
        jButton6 = new javax.swing.JButton();
        txtTotal = new javax.swing.JTextField();
        txtAReceber = new javax.swing.JTextField();
        txtRecebido = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnEnviarLembrete = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jPanel2.setName("jPanel2"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setClosable(true);
        setTitle("Contas a Receber");
        setName("Form"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jButton1.setText("Incluir");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablecar.setAutoCreateRowSorter(true);
        tablecar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Nota Fiscal", "Cliente", "Parcela", "Valor Parcela", "Valor Recebido", "Data de Vencimento", "Data de Recebimento", "Status", "idCliente"
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
        tablecar.setName("tablecar"); // NOI18N
        tablecar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablecarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablecar);
        if (tablecar.getColumnModel().getColumnCount() > 0) {
            tablecar.getColumnModel().getColumn(0).setMinWidth(30);
            tablecar.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablecar.getColumnModel().getColumn(0).setMaxWidth(30);
            tablecar.getColumnModel().getColumn(1).setMinWidth(0);
            tablecar.getColumnModel().getColumn(1).setPreferredWidth(0);
            tablecar.getColumnModel().getColumn(1).setMaxWidth(0);
            tablecar.getColumnModel().getColumn(10).setMinWidth(0);
            tablecar.getColumnModel().getColumn(10).setPreferredWidth(0);
            tablecar.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel1.setText("Pesquisa");
        jLabel1.setName("jLabel1"); // NOI18N

        txtPesquisa.setName("txtPesquisa"); // NOI18N
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPesquisa)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro Status"));
        jPanel4.setName("jPanel4"); // NOI18N

        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pendente", "Cancelado", "Em Negociação", "Pago", "Prescrito", "Protestado", "Protesto Pago", "Pequenas Causas", "Pequenas Causas Pago", "Recuperação Judicial", "Recuperação Judicial Pago", "Todos" }));
        cbstatus.setName("cbstatus"); // NOI18N
        cbstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbstatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbstatus, 0, 202, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton3.setText("Receber em Lote");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Selecionar Todos");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro Data"));
        jPanel5.setName("jPanel5"); // NOI18N

        jdatefim.setDateFormatString("dd/MM/yyyy");
        jdatefim.setName("jdatefim"); // NOI18N

        jLabel2.setText("Final");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("Início");
        jLabel3.setName("jLabel3"); // NOI18N

        jdateinicio.setDateFormatString("dd/MM/yyyy");
        jdateinicio.setName("jdateinicio"); // NOI18N

        jButton6.setText("Atualizar");
        jButton6.setName("jButton6"); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jdateinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jdatefim, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton6)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jdateinicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jdatefim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtTotal.setEditable(false);
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setName("txtTotal"); // NOI18N

        txtAReceber.setEditable(false);
        txtAReceber.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAReceber.setName("txtAReceber"); // NOI18N

        txtRecebido.setEditable(false);
        txtRecebido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRecebido.setName("txtRecebido"); // NOI18N

        jLabel4.setText("Total");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText("A Receber");
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText("Recebido");
        jLabel6.setName("jLabel6"); // NOI18N

        btnEnviarLembrete.setText("Enviar Lembretes");
        btnEnviarLembrete.setName("btnEnviarLembrete"); // NOI18N
        btnEnviarLembrete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarLembreteActionPerformed(evt);
            }
        });

        jButton5.setText("Alterar Status");
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton2.setText("Estornar Recebimento");
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1104, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEnviarLembrete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRecebido, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAReceber, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAReceber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRecebido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(btnEnviarLembrete)
                    .addComponent(jButton5)
                    .addComponent(jButton2))
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
        AdicionarContasAReceber cp = new AdicionarContasAReceber();
        Telas.AparecerTela(cp);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbstatusActionPerformed
        readtablecar();
    }//GEN-LAST:event_cbstatusActionPerformed

    private void tablecarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablecarMouseClicked
        if (evt.getClickCount() == 2) {
            int id = Integer.parseInt(tablecar.getValueAt(tablecar.getSelectedRow(), 1).toString());
            ContaReceber acp = new ContaReceber(id);

            ContaReceber.txtid.setText(String.valueOf(id));
            ContaReceber.id = id;
            ContaReceber.idCliente = Integer.parseInt(tablecar.getValueAt(tablecar.getSelectedRow(), 10).toString());

            card.click(id).forEach(cb -> {
                ContaReceber.txtDataLancamento.setText(Dates.TransformarDataCurtaDoDB(cb.getDatalancamento()));
                ContaReceber.txtCliente.setText(cb.getCliente());
                ContaReceber.txtnf.setText(String.valueOf(cb.getNotafiscal()));
                ContaReceber.txtemissao.setText(Dates.TransformarDataCurtaDoDB(cb.getDataemissao()));
                ContaReceber.txttotal.setText(Valores.TransformarValorFloatEmDinheiro(String.valueOf(cb.getTotal())));
                ContaReceber.txtparcela.setText(cb.getParcela());
                ContaReceber.txtvalorparcela.setText(Valores.TransformarValorFloatEmDinheiro(String.valueOf(cb.getValorparcela())));
                ContaReceber.txtStatus.setText(cb.getStatus());
                ContaReceber.txtValorRecebido.setText(Valores.TransformarValorFloatEmDinheiro(String.valueOf(cb.getValorrecebido())));
                Dates.SetarDataJDateChooser(ContaReceber.dcDataRecebimento, cb.getDatarecebimento());
                Dates.SetarDataJDateChooser(ContaReceber.dcDataVencimento, cb.getDataparcela());
            });

            //DefaultTableModel para adicionar linhas
            DefaultTableModel modeldoc = (DefaultTableModel) ContaReceber.tabledocs.getModel();

            cdd.readitens(id).forEach(cdb -> {
                modeldoc.addRow(new Object[]{
                    cdb.getId(),
                    false,
                    cdb.getDescricao(),
                    cdb.getLocal()
                });
            });

            DefaultTableModel modelobs = (DefaultTableModel) ContaReceber.tableObs.getModel();

            cod.readObs(id).forEach(cob -> {
                modelobs.addRow(new Object[]{
                    cob.getId(),
                    false,
                    Dates.TransformarDataCurtaDoDB(cob.getData()),
                    cob.getUsuario(),
                    cob.getObs()
                });
            });

            Telas.AparecerTela(acp);
            ContaReceber.travacampos();
        }
    }//GEN-LAST:event_tablecarMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        /*PagarEmLote pel = new PagarEmLote();
        Telas.AparecerTela(pel);*/
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        readtablecar();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (jButton4.getText().equals("Selecionar Todos")) {
            for (int i = 0; i < tablecar.getRowCount(); i++) {
                tablecar.setValueAt(true, i, 0);
            }
            jButton4.setText("Desmarcar Todos");
        } else {
            for (int i = 0; i < tablecar.getRowCount(); i++) {
                tablecar.setValueAt(false, i, 0);
            }
            jButton4.setText("Selecionar Todos");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        readtablecar();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnEnviarLembreteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarLembreteActionPerformed
        try {
            int days = Integer.parseInt(JOptionPane.showInputDialog(null, "O vencimento deverá ser quantos dias à partir de hoje?", "Enviar Lembrete", JOptionPane.YES_NO_OPTION));

            enviarEmails(days);
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
    }//GEN-LAST:event_btnEnviarLembreteActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int numTrue = 0;

        for (int i = 0; i < tablecar.getRowCount(); i++) {
            if (tablecar.getValueAt(i, 0).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum registro selecionado.");
        } else {
            String[] options = {"Selecione", "Protestado", "Pequenas Causas", "Em Negociação", "Cancelado", "Prescrito", "Recuperação Judicial"};

            JComboBox cbStatus = new JComboBox();

            for (String option : options) {
                cbStatus.addItem(option);
            }

            JOptionPane.showMessageDialog(null, cbStatus, "Novo Status", JOptionPane.QUESTION_MESSAGE);

            String status = cbStatus.getSelectedItem().toString();

            if (!status.equals("Selecione")) {
                for (int i = 0; i < tablecar.getRowCount(); i++) {
                    if (tablecar.getValueAt(i, 0).equals(true)) {
                        int id = Integer.parseInt(tablecar.getValueAt(i, 1).toString());
                        card.updateStatus(status, id);
                    }
                }
                readtablecar();
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int numTrue = 0;

        for (int i = 0; i < tablecar.getRowCount(); i++) {
            if (tablecar.getValueAt(i, 0).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum registro selecionado.");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja estornar os recebimentos selecionados?", "Estornar Recebimentos", JOptionPane.YES_NO_OPTION);

            if (resp == 0) {
                for (int i = 0; i < tablecar.getRowCount(); i++) {
                    if (tablecar.getValueAt(i, 0).equals(true)) {
                        int id = Integer.parseInt(tablecar.getValueAt(i, 1).toString());

                        card.estornarRecebimento(id);
                    }
                }

                readtablecar();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnEnviarLembrete;
    public static javax.swing.JComboBox<String> cbstatus;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton5;
    public javax.swing.JButton jButton6;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JScrollPane jScrollPane1;
    public static com.toedter.calendar.JDateChooser jdatefim;
    public static com.toedter.calendar.JDateChooser jdateinicio;
    public static javax.swing.JTable tablecar;
    public static javax.swing.JTextField txtAReceber;
    public static javax.swing.JTextField txtPesquisa;
    public static javax.swing.JTextField txtRecebido;
    public static javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
