/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.comercial;

import Bean.ClientesBean;
import DAO.ClientesContatosDAO;
import DAO.ClientesDAO;
import Methods.SendEmail;
import Methods.Telas;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Marcos Filho
 */
public class Clientes extends javax.swing.JInternalFrame {

    //DAO e Bean para criar
    static ClientesBean cb;
    static ClientesDAO cd = new ClientesDAO();
    static ClientesContatosDAO ccd = new ClientesContatosDAO();

    static int idCliente;

    public static int idRepresentante;

    /**
     * Creates new form TelaCadastroCliente
     */
    public Clientes() {
        initComponents();
        readtableclientes();
        idCliente = 0;
        idRepresentante = 0;
        radioJuridica.setSelected(true);
        tabPessoaEnabled();
    }

    private void procuraCep() {
        String cep = txtcep.getText().replace("-", "");
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        String charset = StandardCharsets.UTF_8.name();

        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();

            try (Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                System.out.println(responseBody);

                JSONObject obj = new JSONObject(responseBody);
                txtlogradouro.setText(obj.getString("logradouro"));
                txtcomplemento.setText(obj.getString("complemento"));
                txtbairro.setText(obj.getString("bairro"));
                txtcidade.setText(obj.getString("localidade"));
                cbuf.setSelectedItem(obj.getString("uf"));
                txtnumero.requestFocus();
            }
        } catch (MalformedURLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

            new Thread() {
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        } catch (IOException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

            new Thread() {
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        }
    }

    private void procuraCnpj() {
        String url = "https://www.receitaws.com.br/v1/cnpj/";
        String ss = StandardCharsets.UTF_8.name();

        String cnpj = txtcnpj.getText().replace("-", "");
        cnpj = cnpj.replace("/", "");
        cnpj = cnpj.replace(".", "");

        try {
            URLConnection connection = new URL(url + cnpj).openConnection();
            connection.setRequestProperty("Accept-Charset", ss);
            InputStream response = connection.getInputStream();

            try (Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                System.out.println(responseBody);

                JSONObject obj2 = new JSONObject(responseBody);
                txtrazao.setText(obj2.getString("nome"));
                txtlogradouro.setText(obj2.getString("logradouro"));
                txtnumero.setText(obj2.getString("numero"));
                txtcidade.setText(obj2.getString("municipio"));
                txtbairro.setText(obj2.getString("bairro"));
                txtcep.setText(obj2.getString("cep").replace(".", ""));
                cbuf.setSelectedItem(obj2.getString("uf"));
                txttelefone.setText(obj2.getString("telefone").replace(" ", ""));
            }
        } catch (MalformedURLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

            new Thread() {
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        } catch (IOException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

            new Thread() {
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        }
    }

    public void zeracampos() {
        idCliente = 0;
        txtnomecliente.setText("");
        txtrazao.setText("");
        txtcnpj.setText("");
        txtie.setText("");
        txttelefone.setText("");
        cbgrupo.setSelectedIndex(0);
        txtvendedor.setText("");
        txtrepresentante.setText("");
        txtcondicao.setText("");
        txtlogradouro.setText("");
        txtnumero.setText("");
        txtcomplemento.setText("");
        txtbairro.setText("");
        txtcidade.setText("");
        cbuf.setSelectedIndex(0);
        txtcep.setText("");
        txtnomecliente.requestFocus();
    }

    public static void readtableclientes() {
        DefaultTableModel model = (DefaultTableModel) tableclientes.getModel();
        model.setNumRows(0);

        if (txtPesquisa.getText().equals("")) {
            cd.preenchertabelaclientes().forEach((cb) -> {
                model.addRow(new Object[]{
                    cb.getId(),
                    false,
                    cb.getNome(),
                    cb.getRazaosocial()
                });
            });
        } else {
            cd.pesquisa(txtPesquisa.getText()).forEach(cb -> {
                model.addRow(new Object[]{
                    cb.getId(),
                    false,
                    cb.getNome(),
                    cb.getRazaosocial()
                });
            });
        }
    }

    private void tabPessoaEnabled() {
        tabPessoa.setEnabledAt(0, radioJuridica.isSelected());
        tabPessoa.setEnabledAt(1, radioFisica.isSelected());

        if (radioJuridica.isSelected()) {
            tabPessoa.setSelectedIndex(0);
        } else {
            tabPessoa.setSelectedIndex(1);
        }
    }

    public static void loadContatos() {
        DefaultTableModel model = (DefaultTableModel) tablecontatos.getModel();
        model.setNumRows(0);

        ccd.readTodos(idCliente).forEach(ccb -> {
            model.addRow(new Object[]{
                ccb.getId(),
                false,
                ccb.getNome(),
                ccb.getCargo(),
                ccb.getEmail(),
                ccb.getCelular()
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel6 = new javax.swing.JLabel();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        tabclientes = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableclientes = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        tabDadosCliente = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtnomecliente = new javax.swing.JTextField();
        txttelefone = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        cbgrupo = new javax.swing.JComboBox<>();
        radioJuridica = new javax.swing.JRadioButton();
        radioFisica = new javax.swing.JRadioButton();
        tabPessoa = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtrazao = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtcnpj = new javax.swing.JFormattedTextField();
        btncnpj = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtie = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtim = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        txtRG = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtCPF = new javax.swing.JFormattedTextField();
        checkBoleto = new javax.swing.JCheckBox();
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
        btncep = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtvendedor = new javax.swing.JTextField();
        txtrepresentante = new javax.swing.JTextField();
        txtcondicao = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabledocs = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablecontatos = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        jLabel6.setText("jLabel6");

        setClosable(true);
        setTitle("Cadastro de Clientes");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tableclientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Nome", "Razão Social"
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
        tableclientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableclientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableclientes);
        if (tableclientes.getColumnModel().getColumnCount() > 0) {
            tableclientes.getColumnModel().getColumn(0).setMinWidth(0);
            tableclientes.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableclientes.getColumnModel().getColumn(0).setMaxWidth(0);
            tableclientes.getColumnModel().getColumn(1).setMinWidth(35);
            tableclientes.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableclientes.getColumnModel().getColumn(1).setMaxWidth(35);
        }

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));

        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1261, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
        );

        tabclientes.addTab("Clientes", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Cliente"));

        jLabel1.setText("Nome");

        jLabel5.setText("Telefone");

        try {
            txttelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel17.setText("Grupo");

        cbgrupo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Consumidor Final", "Revenda" }));

        buttonGroup2.add(radioJuridica);
        radioJuridica.setText("Pessoa Jurídica");
        radioJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioJuridicaActionPerformed(evt);
            }
        });

        buttonGroup2.add(radioFisica);
        radioFisica.setText("Pessoa Física");
        radioFisica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFisicaActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Razão Social");

        jLabel3.setText("CNPJ");

        try {
            txtcnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        btncnpj.setText("Procurar CNPJ");
        btncnpj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncnpjActionPerformed(evt);
            }
        });

        jLabel4.setText("Ins. Est.");

        jLabel19.setText("Ins. Mun.");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtrazao))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncnpj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtie, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtim, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtrazao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19)
                        .addComponent(txtim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtcnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btncnpj)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPessoa.addTab("Pessoa Jurídica", jPanel11);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setText("CPF");

        jLabel20.setText("RG");

        try {
            txtCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRG, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(444, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20)
                    .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        tabPessoa.addTab("Pessoa Física", jPanel12);

        checkBoleto.setText("Gera Boleto");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnomecliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbgrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(radioJuridica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioFisica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkBoleto))
            .addComponent(tabPessoa)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtnomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txttelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(cbgrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioJuridica)
                    .addComponent(radioFisica)
                    .addComponent(checkBoleto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        btncep.setText("Procurar CEP");
        btncep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbuf, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcep, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncep)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtlogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcomplemento))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbairro, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcidade))))
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
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtbairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtcidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(txtcep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btncep))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(cbuf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Ligações Internas"));
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));

        jLabel14.setText("Vendedor");

        jLabel15.setText("Representante");

        jLabel16.setText("Condição de Pagamento");

        txtrepresentante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jButton3.setText("Procurar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Procurar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Procurar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtcondicao, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(txtrepresentante, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtvendedor, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(jButton4)
                    .addComponent(jButton3)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(txtvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtrepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtcondicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jButton6.setText("Novo");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton2.setText("Salvar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton6)))
        );

        tabDadosCliente.addTab("Dados", jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        tabledocs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Descrição", "Local", "Local Original"
            }
        ));
        jScrollPane2.setViewportView(tabledocs);
        if (tabledocs.getColumnModel().getColumnCount() > 0) {
            tabledocs.getColumnModel().getColumn(0).setMinWidth(0);
            tabledocs.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabledocs.getColumnModel().getColumn(0).setMaxWidth(0);
            tabledocs.getColumnModel().getColumn(1).setMinWidth(35);
            tabledocs.getColumnModel().getColumn(1).setPreferredWidth(35);
            tabledocs.getColumnModel().getColumn(1).setMaxWidth(35);
            tabledocs.getColumnModel().getColumn(4).setMinWidth(0);
            tabledocs.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabledocs.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jButton7.setText("Adicionar Documento");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton9.setText("Excluir Documento");
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton9))
                .addContainerGap())
        );

        tabDadosCliente.addTab("Documentos", jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        tablecontatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Nome", "Cargo", "E-mail", "Celular"
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
        jScrollPane3.setViewportView(tablecontatos);
        if (tablecontatos.getColumnModel().getColumnCount() > 0) {
            tablecontatos.getColumnModel().getColumn(0).setMinWidth(0);
            tablecontatos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablecontatos.getColumnModel().getColumn(0).setMaxWidth(0);
            tablecontatos.getColumnModel().getColumn(1).setMinWidth(35);
            tablecontatos.getColumnModel().getColumn(1).setPreferredWidth(35);
            tablecontatos.getColumnModel().getColumn(1).setMaxWidth(35);
        }

        jButton10.setText("Adicionar Contato");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Excluir Contato");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton11)))
        );

        tabDadosCliente.addTab("Contatos", jPanel9);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabDadosCliente)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabDadosCliente)
                .addContainerGap())
        );

        tabclientes.addTab("Dados do Cliente", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabclientes)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabclientes)
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (cbuf.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Coloque o endereço completo do Cliente!");
            cbuf.showPopup();
        } else if (txtnomecliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um nome para o cliente.");
            txtnomecliente.requestFocus();
        } else if (txtvendedor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione um vendedor para o cliente.");
            ProcurarVendedorClientes pvc = new ProcurarVendedorClientes();
            Telas.AparecerTela(pvc);
        } else if (txtrepresentante.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione um representante para o cliente.");
            ProcurarRepresentanteClientes prc = new ProcurarRepresentanteClientes();
            Telas.AparecerTela(prc);
        } else if (txtcondicao.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione uma condição de pagamento para o cliente.");
            ProcurarCondicaoDePagamentoClientes pcp = new ProcurarCondicaoDePagamentoClientes();
            Telas.AparecerTela(pcp);
        } else {
            if (idCliente == 0) {
////////////Criar Cliente
                cb = new ClientesBean();

                cb.setNome(txtnomecliente.getText());
                cb.setRazaosocial(txtrazao.getText());
                cb.setCnpj(txtcnpj.getText());
                cb.setIe(txtie.getText());
                cb.setTelefone(txttelefone.getText());
                cb.setGrupo(cbgrupo.getSelectedItem().toString());
                cb.setVendedor(txtvendedor.getText());
                cb.setIdrepresentante(idRepresentante);
                cb.setRepresentante(txtrepresentante.getText());
                cb.setCondicaodepagamento(txtcondicao.getText());
                cb.setLogradouro(txtlogradouro.getText());
                cb.setNumero(txtnumero.getText());
                cb.setComplemento(txtcomplemento.getText());
                cb.setBairro(txtbairro.getText());
                cb.setCidade(txtcidade.getText());
                cb.setUf(cbuf.getSelectedItem().toString());
                cb.setCep(txtcep.getText());
                cb.setIm(txtim.getText());
                cb.setCpf(txtCPF.getText());
                cb.setRg(txtRG.getText());
                cb.setBoleto(checkBoleto.isSelected());
                cb.setPj(radioJuridica.isSelected());

                //nome, razaosocial, cnpj, ie, telefone, grupo, vendedor, idrepresentante, representante,condicaodepagamento, logradouro, numero, complemento, bairro, cidade, uf, cep, im
                cd.create(cb);

////////////Criar Documentos
                for (int i = 0; i < tabledocs.getRowCount(); i++) {

                }

                //Zerar Campos
                zeracampos();

                //Mudar para a primeira tab
                tabclientes.setSelectedIndex(0);

                //Atualizar table de clientes
                readtableclientes();
            } else {
                cb = new ClientesBean();

                cb.setNome(txtnomecliente.getText());
                cb.setRazaosocial(txtrazao.getText());
                cb.setCnpj(txtcnpj.getText());
                cb.setIe(txtie.getText());
                cb.setTelefone(txttelefone.getText());
                cb.setGrupo(cbgrupo.getSelectedItem().toString());
                cb.setVendedor(txtvendedor.getText());
                cb.setIdrepresentante(idRepresentante);
                cb.setRepresentante(txtrepresentante.getText());
                cb.setCondicaodepagamento(txtcondicao.getText());
                cb.setLogradouro(txtlogradouro.getText());
                cb.setNumero(txtnumero.getText());
                cb.setComplemento(txtcomplemento.getText());
                cb.setBairro(txtbairro.getText());
                cb.setCidade(txtcidade.getText());
                cb.setUf(cbuf.getSelectedItem().toString());
                cb.setCep(txtcep.getText());
                cb.setIm(txtim.getText());
                cb.setCpf(txtCPF.getText());
                cb.setRg(txtRG.getText());
                cb.setBoleto(checkBoleto.isSelected());
                cb.setPj(radioJuridica.isSelected());
                cb.setId(idCliente);

                //nome = ?, razaosocial = ?, cnpj = ?, ie = ?, telefone = ?, grupo = ?, vendedor = ?, idrepresentante = ?, representante = ?,condicaodepagamento = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, cep = ?, im = ? WHERE id = ?
                cd.update(cb);

                //Zerar Campos
                zeracampos();

                //Mudar para a primeira tab
                tabclientes.setSelectedIndex(0);

                //Atualizar table de clientes
                readtableclientes();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int i = JOptionPane.showConfirmDialog(rootPane, "Deseja cadastrar um novo cliente?", "Novo Cadastro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (i == 0) {
            zeracampos();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tableclientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableclientesMouseClicked
        if (evt.getClickCount() == 2) {
            idCliente = Integer.parseInt(tableclientes.getValueAt(tableclientes.getSelectedRow(), 0).toString());
            tabclientes.setSelectedIndex(1);

            cd.click(idCliente).forEach((ClientesBean cb) -> {
                txtnomecliente.setText(cb.getNome());
                txtrazao.setText(cb.getRazaosocial());
                txtcnpj.setText(cb.getCnpj());
                txtie.setText(cb.getIe());
                txttelefone.setText(cb.getTelefone());
                cbgrupo.setSelectedItem(cb.getGrupo());
                txtvendedor.setText(cb.getVendedor());
                idRepresentante = cb.getIdrepresentante();
                txtrepresentante.setText(cb.getRepresentante());
                txtcondicao.setText(cb.getCondicaodepagamento());
                txtlogradouro.setText(cb.getLogradouro());
                txtnumero.setText(cb.getNumero());
                txtcomplemento.setText(cb.getComplemento());
                txtbairro.setText(cb.getBairro());
                txtcidade.setText(cb.getCidade());
                cbuf.setSelectedItem(cb.getUf());
                txtcep.setText(cb.getCep());
                txtim.setText(cb.getIm());
                if (cb.isPj()) {
                    radioJuridica.setSelected(true);
                } else {
                    radioFisica.setSelected(true);
                }
                txtCPF.setText(cb.getCpf());
                txtRG.setText(cb.getRg());
                checkBoleto.setSelected(cb.isBoleto());
            });

            tabPessoaEnabled();

            loadContatos();
        }
    }//GEN-LAST:event_tableclientesMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ProcurarVendedorClientes pv = new ProcurarVendedorClientes();
        Telas.AparecerTela(pv);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        ProcurarRepresentanteClientes pr = new ProcurarRepresentanteClientes();
        Telas.AparecerTela(pr);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ProcurarCondicaoDePagamentoClientes pc = new ProcurarCondicaoDePagamentoClientes();
        Telas.AparecerTela(pc);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btncepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncepActionPerformed
        if (txtcep.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um CEP primeiro.");
            txtcep.requestFocus();
        } else {
            try {
                procuraCep();
            } catch (Exception ex) {
                Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btncepActionPerformed

    private void btncnpjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncnpjActionPerformed
        if (txtcnpj.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um CNPJ primeiro.");
            txtcnpj.requestFocus();
        } else {
            try {
                procuraCnpj();
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
        }
    }//GEN-LAST:event_btncnpjActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        readtableclientes();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (idCliente == 0) {
            JOptionPane.showMessageDialog(null, "Salve ou selecione um cliente primeiro.");
        } else {
            AddContatoCliente acc = new AddContatoCliente();
            AddContatoCliente.idCliente = idCliente;
            Telas.AparecerTela(acc);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        int numTrue = 0;

        for (int i = 0; i < tablecontatos.getRowCount(); i++) {
            if (tablecontatos.getValueAt(i, 1).equals(true)) {
                numTrue++;
            }
        }

        if (numTrue == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum contato selecionado");
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "Deseja excluir o(s) contato(s) selecionado(s)?", "Excluir Contato", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                for (int i = 0; i < tablecontatos.getRowCount(); i++) {
                    if (tablecontatos.getValueAt(i, 1).equals(true)) {
                        int id = Integer.parseInt(tablecontatos.getValueAt(i, 0).toString());
                        ccd.delete(id);
                    }
                }
                loadContatos();
            }
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

    }//GEN-LAST:event_jButton9ActionPerformed

    private void radioJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioJuridicaActionPerformed
        tabPessoaEnabled();
        tabPessoa.setSelectedIndex(0);
    }//GEN-LAST:event_radioJuridicaActionPerformed

    private void radioFisicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioFisicaActionPerformed
        tabPessoaEnabled();
        tabPessoa.setSelectedIndex(1);
    }//GEN-LAST:event_radioFisicaActionPerformed

    private void tablecontatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablecontatosMouseClicked
        if (evt.getClickCount() == 2) {
            AddContatoCliente acc = new AddContatoCliente();
            AddContatoCliente.idContato = Integer.parseInt(tablecontatos.getValueAt(tablecontatos.getSelectedRow(), 0).toString());
            Telas.AparecerTela(acc);
        }
    }//GEN-LAST:event_tablecontatosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncep;
    private javax.swing.JButton btncnpj;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbgrupo;
    private javax.swing.JComboBox<String> cbuf;
    private javax.swing.JCheckBox checkBoleto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JPanel jPanel12;
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
    private javax.swing.JRadioButton radioFisica;
    private javax.swing.JRadioButton radioJuridica;
    private javax.swing.JTabbedPane tabDadosCliente;
    private javax.swing.JTabbedPane tabPessoa;
    private javax.swing.JTabbedPane tabclientes;
    public static javax.swing.JTable tableclientes;
    private static javax.swing.JTable tablecontatos;
    private javax.swing.JTable tabledocs;
    private javax.swing.JFormattedTextField txtCPF;
    private static javax.swing.JTextField txtPesquisa;
    private javax.swing.JTextField txtRG;
    private javax.swing.JTextField txtbairro;
    private static javax.swing.JFormattedTextField txtcep;
    private javax.swing.JTextField txtcidade;
    private javax.swing.JFormattedTextField txtcnpj;
    private javax.swing.JTextField txtcomplemento;
    public static javax.swing.JTextField txtcondicao;
    private javax.swing.JTextField txtie;
    private static javax.swing.JTextField txtim;
    private javax.swing.JTextField txtlogradouro;
    public static javax.swing.JTextField txtnomecliente;
    private javax.swing.JTextField txtnumero;
    private javax.swing.JTextField txtrazao;
    public static javax.swing.JTextField txtrepresentante;
    private javax.swing.JFormattedTextField txttelefone;
    public static javax.swing.JTextField txtvendedor;
    // End of variables declaration//GEN-END:variables

//    private static EnderecoERP consultaCEP(java.lang.String cep) throws SigepClienteException, SQLException_Exception {
//        br.com.correios.AtendeClienteService service = new br.com.correios.AtendeClienteService();
//        br.com.correios.AtendeCliente port = service.getAtendeClientePort();
//        return port.consultaCEP(cep);
//    }
}
