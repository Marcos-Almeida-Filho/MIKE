/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.vendas;

import Bean.F_UPBean;
import Bean.F_UP_HistBean;
import Connection.Session;
import DAO.F_UPDAO;
import DAO.F_UP_HistDAO;
import DAO.OPDAO;
import DAO.OPMedicoesDAO;
import DAO.OPProcessosDAO;
import DAO.ProcessosVendasDAO;
import DAO.ProcessosVendasMedicoesDAO;
import DAO.VendasMateriaisDAO;
import DAO.VendasMateriaisMovDAO;
import Methods.Dates;
import Methods.SendEmail;
import Methods.SoNumeros;
import Methods.Telas;
import View.Geral.ProcurarMaterial;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class ProcessoOP extends javax.swing.JInternalFrame {

    static OPDAO od = new OPDAO();
    static OPProcessosDAO opd = new OPProcessosDAO();
    static OPMedicoesDAO omd = new OPMedicoesDAO();
    static ProcessosVendasDAO pvd = new ProcessosVendasDAO();
    static F_UP_HistDAO fuhd = new F_UP_HistDAO();
    static F_UPDAO fud = new F_UPDAO();
    static VendasMateriaisDAO vmd = new VendasMateriaisDAO();
    static VendasMateriaisMovDAO vmmd = new VendasMateriaisMovDAO();
    static ProcessosVendasMedicoesDAO pvmd = new ProcessosVendasMedicoesDAO();

    static ProcurarMaterial pm;

    static double qtdTotalProcesso = 0;
    static double qtdFinalOP = 0;
    static int idProcesso;

    static int iP = 0;

    static double qtdOkOP = 0;
    static double qtdNaoOkOP = 0;

    /**
     * Creates new form ProcessoOP
     *
     * @param idProcesso
     * @param qtdTotalProcesso
     */
    public ProcessoOP(int idProcesso, double qtdTotalProcesso) {
        initComponents();
        txtOk.setDocument(new SoNumeros());
        txtNaoOk.setDocument(new SoNumeros());
        ProcessoOP.idProcesso = idProcesso;
        ProcessoOP.qtdTotalProcesso = qtdTotalProcesso;
    }

    public static void readProcesso(int id) {
        opd.readProcesso(id).forEach(opb -> {
            txtProcesso.setText(opb.getProcesso());
            txtFuncionario.setText(opb.getUser());
            txtOk.setText(String.valueOf(opb.getQtdok()));
            txtNaoOk.setText(String.valueOf(opb.getQtdnaook()));
            txtMotivoNaoOk.setText(opb.getMotivo());
            txtObs.setText(opb.getObs());
            txtInicio.setText(Dates.TransformarDataCompletaDoDB(opb.getDatainicio()));
            txtTermino.setText(Dates.TransformarDataCompletaDoDB(opb.getDatafim()));
            qtdTotalProcesso = opb.getQtdtotal();
        });

        readMedicoesDoProcesso(id);
        camposPorStatus();
    }

    public static void camposPorStatus() {
        if (txtTermino.getText().equals("")) {
            txtOk.setEditable(true);
            txtNaoOk.setEditable(true);
            txtObs.setEditable(true);
            btnSalvar.setEnabled(true);
        } else {
            txtOk.setEditable(false);
            txtNaoOk.setEditable(false);
            txtObs.setEditable(false);
            btnSalvar.setEnabled(false);
        }
    }

    public static void readMedicoesDoProcesso(int id) {
        DefaultTableModel modelInspecao = (DefaultTableModel) tableInspecoes.getModel();
        modelInspecao.setNumRows(0);

        omd.readMedicoes(id).forEach(omb -> {
            modelInspecao.addRow(new Object[]{
                omb.getId(),
                omb.getMedida(),
                omb.getMaior(),
                omb.getMenor(),
                omb.getInstrumento()
            });
        });
    }

    public static void quantidades() {
        double qtdOk, qtdNaoOk, qtdProcesso;
        qtdOk = Double.parseDouble(txtOk.getText());
        qtdNaoOk = Double.parseDouble(txtNaoOk.getText());
        qtdProcesso = qtdOk + qtdNaoOk;

        if (qtdProcesso > qtdTotalProcesso) {
            JOptionPane.showMessageDialog(null, "Quantidades digitadas maiores do que as disponíveis na OP.");
            txtOk.setText("0");
            txtNaoOk.setText("0");
            txtOk.requestFocus();
        }
    }

    public static void qtdNaoOk(double qtdNaoOk) {
        if (qtdNaoOk > 0) {
            for (;;) {
                message:
                {
                    try {
                        String motivo = JOptionPane.showInputDialog(null, "Qual o motivo gerador das peças não conforme?", "Peças Não Conforme", JOptionPane.YES_NO_OPTION);
                        if (motivo.length() < 15) {
                            JOptionPane.showMessageDialog(null, "Motivo digitado muito curto. Explique melhor o ocorrido.");
                            break message;
                        } else {
                            txtMotivoNaoOk.setText(motivo);
                            opd.lancarMotivo(idProcesso, motivo);
                        }
                    } catch (NullPointerException e) {
                        JOptionPane.showMessageDialog(null, "Nenhum motivo digitado.");
                        break message;
                    }
                    break;
                }
            }
        } else if (qtdNaoOk == 0) {
            txtMotivoNaoOk.setText("");
            opd.lancarMotivo(iP, "");
        } else {
            txtNaoOk.setText("0");
            txtMotivoNaoOk.setText("");
            opd.lancarMotivo(iP, "");
        }
    }

    public static void criarOutroProcessoIgual() {
        double qtdOk = Double.parseDouble(txtOk.getText());
        double qtdNaoOk = Double.parseDouble(txtNaoOk.getText());
        double qtdTotal = qtdTotalProcesso - (qtdOk + qtdNaoOk);

        String processo = txtProcesso.getText();

        opd.create(OP.txtNumOP.getText(), processo, qtdTotal);

        int id = opd.idUltimoProcesso(OP.txtNumOP.getText(), processo);

        int id2 = pvd.idProcesso(processo);
        pvmd.readMedidas(id2).forEach(pvmb -> {
            omd.create(id, pvmb.getMedida(), "", "", "");
        });

        F_UPBean fub = new F_UPBean();
        fub.setProcesso(processo);
        fub.setOp(OP.txtNumOP.getText());

        //processo = ? WHERE op = ?
        fud.updateProcessoByOs(fub);

        //Criar novo histórico no F-UP
        F_UP_HistBean fuhb = new F_UP_HistBean();
        fuhb.setIdfup(fud.getId(OP.txtNumOP.getText()));
        fuhb.setProcesso(txtProcesso.getText());

        //idfup, processo
        fuhd.create(fuhb);
    }

    public static void criarProximoProcesso() {
        int resp = JOptionPane.showConfirmDialog(null, "Deseja encerrar a OP?", "Encerrar OP", JOptionPane.YES_NO_OPTION);

        if (resp == 0) {
            int idMaterialOP = od.getIdMaterialOP(OP.txtNumOP.getText());

            if (idMaterialOP == 0) {
                JOptionPane.showMessageDialog(null, "Material incorreto. Favor atualizá-lo.");

                pm = new ProcurarMaterial("ProcessoOP");
                Telas.AparecerTela(pm);
            } else {
                od.updateStatus(OP.txtNumOP.getText(), "Finalizado");

                int idMaterial = od.getIdMaterialOP(OP.txtNumOP.getText());

                double estoqueAtual = vmd.readEstoque(idMaterial);

                od.readOP(OP.txtNumOP.getText()).forEach(od -> {
                    qtdFinalOP = od.getQtdok();
                });

                F_UPBean fub = new F_UPBean();
                fub.setProcesso("Encerrado");
                fub.setOp(OP.txtNumOP.getText());

                //processo = ? WHERE op = ?
                fud.updateProcessoByOs(fub);

                double estoque = estoqueAtual + qtdFinalOP;

                vmd.updateEstoque(estoque, idMaterial);

                try {
                    vmmd.create(idMaterial, estoqueAtual, qtdFinalOP, estoque, OP.txtNumOP.getText(), Dates.CriarDataCurtaDBSemDataExistente(), Session.nome);
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

                OP.readOPs();
                OP.lerOP(OP.txtNumOP.getText());
            }
        } else {
            JComboBox cbProcessos = new JComboBox();

            pvd.readTodos().forEach(psb -> {
                cbProcessos.addItem(psb.getNome());
                iP++;
            });

            JOptionPane.showMessageDialog(null, cbProcessos, "Selecione o Próximo Processo", JOptionPane.QUESTION_MESSAGE);

            String processo = cbProcessos.getSelectedItem().toString();

            opd.create(OP.txtNumOP.getText(), processo, qtdOkOP);

            int id = opd.idUltimoProcesso(OP.txtNumOP.getText(), processo);

            int id2 = pvd.idProcesso(processo);
            pvmd.readMedidas(id2).forEach(pvmb -> {
                omd.create(id, pvmb.getMedida(), "", "", "");
            });

            F_UPBean fub = new F_UPBean();
            fub.setProcesso(processo);
            fub.setOp(OP.txtNumOP.getText());

            //processo = ? WHERE op = ?
            fud.updateProcessoByOs(fub);

            //Criar novo histórico no F-UP
            F_UP_HistBean fuhb = new F_UP_HistBean();
            fuhb.setIdfup(fud.getId(OP.txtNumOP.getText()));
            fuhb.setProcesso(cbProcessos.getSelectedItem().toString());

            //idfup, processo
            fuhd.create(fuhb);
        }
    }

    public void fecharProcesso(double qtdOk, double qtdNaoOk, String obs) {
        String data = Dates.CriarDataCompletaParaDB();

        int numVazio = 0;

        for (int i = 0; i < tableInspecoes.getRowCount(); i++) {
            if (tableInspecoes.getValueAt(i, 2).equals("") || tableInspecoes.getValueAt(i, 3).equals("") || tableInspecoes.getValueAt(i, 4).equals("")) {
                numVazio++;
            }
        }

        if (numVazio > 0) {
            JOptionPane.showMessageDialog(null, "Inspeções sem valor lançado.\nFavor lançar as medidas ou Instrumento antes de salvar.");
        } else {
            opd.fecharProcesso(ProcessoOP.idProcesso, data, qtdOk, qtdNaoOk, obs);

            od.readOP(OP.txtNumOP.getText()).forEach(ob -> {
                qtdNaoOkOP = ob.getQtdnaook() + Double.parseDouble(txtNaoOk.getText());
                qtdOkOP = ob.getQtd() - qtdNaoOkOP;
            });

            od.updateOPQtd(OP.txtNumOP.getText(), qtdOkOP, qtdNaoOkOP);

            fuhd.update(Session.nome, Dates.CriarDataCurtaDBSemDataExistente(), fud.getId(OP.txtNumOP.getText()), txtProcesso.getText());

            if (qtdOk + qtdNaoOk < qtdTotalProcesso) {
                criarOutroProcessoIgual();
            } else {
                criarProximoProcesso();
            }

            String op = OP.txtNumOP.getText();
            OP.lerOP(op);
            OP.lerProcessos(op);
            OP.lerInspecoes(op);
            dispose();
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

        jPanel1 = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtProcesso = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtFuncionario = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtInicio = new javax.swing.JTextField();
        txtTermino = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableInspecoes = new javax.swing.JTable();
        btnAddInspecao = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtOk = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNaoOk = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMotivoNaoOk = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Processo OP");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        btnSalvar.setText("Salvar");
        btnSalvar.setName("btnSalvar"); // NOI18N
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Processo"));
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel1.setText("Processo");
        jLabel1.setName("jLabel1"); // NOI18N

        txtProcesso.setEditable(false);
        txtProcesso.setName("txtProcesso"); // NOI18N

        jLabel2.setText("Funcionário");
        jLabel2.setName("jLabel2"); // NOI18N

        txtFuncionario.setEditable(false);
        txtFuncionario.setName("txtFuncionario"); // NOI18N

        jLabel6.setText("Início");
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText("Término");
        jLabel7.setName("jLabel7"); // NOI18N

        txtInicio.setEditable(false);
        txtInicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtInicio.setName("txtInicio"); // NOI18N

        txtTermino.setEditable(false);
        txtTermino.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTermino.setName("txtTermino"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFuncionario))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTermino))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtProcesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtTermino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setName("jPanel4"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tableInspecoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Medida", "Maior", "Menor", "Instrumento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableInspecoes.setName("tableInspecoes"); // NOI18N
        tableInspecoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableInspecoesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableInspecoes);
        if (tableInspecoes.getColumnModel().getColumnCount() > 0) {
            tableInspecoes.getColumnModel().getColumn(0).setMinWidth(0);
            tableInspecoes.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableInspecoes.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        btnAddInspecao.setText("Adicionar Inspeção");
        btnAddInspecao.setName("btnAddInspecao"); // NOI18N
        btnAddInspecao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddInspecaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddInspecao)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddInspecao))
        );

        jTabbedPane1.addTab("Inspeções", jPanel4);

        jPanel5.setName("jPanel5"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtObs.setColumns(20);
        txtObs.setRows(5);
        txtObs.setName("txtObs"); // NOI18N
        jScrollPane1.setViewportView(txtObs);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Observação", jPanel5);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Quantidades"));
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel3.setText("Qtde Conforme");
        jLabel3.setName("jLabel3"); // NOI18N

        txtOk.setName("txtOk"); // NOI18N
        txtOk.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtOkFocusGained(evt);
            }
        });
        txtOk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOkKeyReleased(evt);
            }
        });

        jLabel4.setText("Qtde Não Conforme");
        jLabel4.setName("jLabel4"); // NOI18N

        txtNaoOk.setName("txtNaoOk"); // NOI18N
        txtNaoOk.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNaoOkFocusGained(evt);
            }
        });
        txtNaoOk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNaoOkKeyReleased(evt);
            }
        });

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        txtMotivoNaoOk.setEditable(false);
        txtMotivoNaoOk.setColumns(20);
        txtMotivoNaoOk.setLineWrap(true);
        txtMotivoNaoOk.setRows(5);
        txtMotivoNaoOk.setWrapStyleWord(true);
        txtMotivoNaoOk.setName("txtMotivoNaoOk"); // NOI18N
        jScrollPane3.setViewportView(txtMotivoNaoOk);

        jLabel5.setText("Motivo das Peças Mortas");
        jLabel5.setName("jLabel5"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(32, 32, 32)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtOk, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                .addComponent(txtNaoOk))))
                    .addComponent(jLabel5))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNaoOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalvar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar)
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

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        double qtdNaoOk = Double.parseDouble(txtNaoOk.getText());
        double qtdOk = Double.parseDouble(txtOk.getText());
        String obs = txtObs.getText();

        qtdNaoOk(qtdNaoOk);
        fecharProcesso(qtdOk, qtdNaoOk, obs);
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtOkFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOkFocusGained
        txtOk.selectAll();
    }//GEN-LAST:event_txtOkFocusGained

    private void txtNaoOkFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNaoOkFocusGained
        txtNaoOk.selectAll();
    }//GEN-LAST:event_txtNaoOkFocusGained

    private void btnAddInspecaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddInspecaoActionPerformed

    }//GEN-LAST:event_btnAddInspecaoActionPerformed

    private void tableInspecoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInspecoesMouseClicked
        if (evt.getClickCount() == 2) {
            if (txtTermino.getText().equals("")) {
                InspecaoProcesso ip = new InspecaoProcesso(Integer.parseInt(tableInspecoes.getValueAt(tableInspecoes.getSelectedRow(), 0).toString()));
                InspecaoProcesso.lerInspecao();
                Telas.AparecerTela(ip);
            }
        }
    }//GEN-LAST:event_tableInspecoesMouseClicked

    private void txtOkKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOkKeyReleased
        quantidades();
    }//GEN-LAST:event_txtOkKeyReleased

    private void txtNaoOkKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNaoOkKeyReleased
        quantidades();
    }//GEN-LAST:event_txtNaoOkKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAddInspecao;
    public static javax.swing.JButton btnSalvar;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JTable tableInspecoes;
    public static javax.swing.JTextField txtFuncionario;
    public static javax.swing.JTextField txtInicio;
    public static javax.swing.JTextArea txtMotivoNaoOk;
    public static javax.swing.JTextField txtNaoOk;
    public static javax.swing.JTextArea txtObs;
    public static javax.swing.JTextField txtOk;
    public static javax.swing.JTextField txtProcesso;
    public static javax.swing.JTextField txtTermino;
    // End of variables declaration//GEN-END:variables
}
