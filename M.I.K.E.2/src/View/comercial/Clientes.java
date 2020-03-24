/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.comercial;

import Bean.ClientesBean;
import DAO.ClientesDAO;
import Methods.SendEmail;
import Methods.Telas;
import java.awt.AWTException;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import javax.swing.JDesktopPane;
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

    /**
     * Creates new form TelaCadastroCliente
     */
//    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    //DAO e Bean para criar
    static ClientesBean cb = new ClientesBean();
    static ClientesDAO cd = new ClientesDAO();

    public Clientes() {
        initComponents();
        readtableclientes();
        lblidrepresentante.setVisible(false);
//        btncep.setVisible(false);
//        btncnpj.setVisible(false);
    }

    private void procuraCep() {
        final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

        String cep = txtcep.getText().replace("-", "");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://viacep.com.br/ws/" + cep + "/json/")).build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JOptionPane.showMessageDialog(null, response.body());

            JSONObject obj = new JSONObject(response.body());
            txtlogradouro.setText(obj.getString("logradouro"));
            txtcomplemento.setText(obj.getString("complemento"));
            txtbairro.setText(obj.getString("bairro"));
            txtcidade.setText(obj.getString("localidade"));
            cbuf.setSelectedItem(obj.getString("uf"));
            txtnumero.requestFocus();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Clientes.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro!\n" + ex);
            try {
                SendEmail.EnviarErro(ex.toString());
            } catch (AWTException | IOException ex1) {
                Logger.getLogger(Clientes.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    private void procuraCnpj() {
        final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

        String cnpj = txtcnpj.getText().replace("-", "");
        cnpj = cnpj.replace("/", "");
        cnpj = cnpj.replace(".", "");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://www.receitaws.com.br/v1/cnpj/" + cnpj)).build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//            String jsonValido = response.body().replace(":", ": ");
//            jsonValido = jsonValido.replace(",", ",\n");
//            JOptionPane.showMessageDialog(null, response);
//            JOptionPane.showMessageDialog(null, response.body());
//            JOptionPane.showMessageDialog(null, jsonValido);

//            JSONObject obj = new JSONObject(response);
//            JOptionPane.showMessageDialog(null, obj.getString("nome"));
            JSONObject obj2 = new JSONObject(response.body());
            //JOptionPane.showMessageDialog(null, obj2.getString("nome"));
            txtrazao.setText(obj2.getString("nome"));
            txtlogradouro.setText(obj2.getString("logradouro"));
            txtnumero.setText(obj2.getString("numero"));
            txtcidade.setText(obj2.getString("municipio"));
            txtbairro.setText(obj2.getString("bairro"));
            txtcep.setText(obj2.getString("cep").replace(".", ""));
            cbuf.setSelectedItem(obj2.getString("uf"));
            txttelefone.setText(obj2.getString("telefone").replace(" ", ""));
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Clientes.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void zeracampos() {
        txtidcliente.setText("");
        txtnomecliente.setText("");
        txtrazao.setText("");
        txtcnpj.setText("");
        txtie.setText("");
        txttelefone.setText("");
        cbgrupo.setSelectedIndex(0);
        txtvendedor.setText("");
        lblidrepresentante.setText("");
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

        cd.preenchertabelaclientes().forEach((cb) -> {
            model.addRow(new Object[]{
                cb.getId(),
                cb.getNome(),
                cb.getRazaosocial()
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
        jPanel1 = new javax.swing.JPanel();
        tabclientes = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableclientes = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        txtpesquisa = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtidcliente = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtnomecliente = new javax.swing.JTextField();
        txtrazao = new javax.swing.JTextField();
        txtie = new javax.swing.JTextField();
        txtcnpj = new javax.swing.JFormattedTextField();
        txttelefone = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        cbgrupo = new javax.swing.JComboBox<>();
        btncnpj = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txtim = new javax.swing.JTextField();
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
        lblidrepresentante = new javax.swing.JLabel();
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
        tableclientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableclientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableclientes);
        if (tableclientes.getColumnModel().getColumnCount() > 0) {
            tableclientes.getColumnModel().getColumn(0).setMinWidth(40);
            tableclientes.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableclientes.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));

        txtpesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtpesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtpesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("ID");

        txtidcliente.setEditable(false);
        txtidcliente.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Cliente"));

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

        jLabel17.setText("Grupo");

        cbgrupo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Consumidor Final", "Revenda" }));

        btncnpj.setText("Procurar CNPJ");
        btncnpj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncnpjActionPerformed(evt);
            }
        });

        jLabel19.setText("Ins. Mun.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtrazao, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txttelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbgrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
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
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtnomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtrazao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtcnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncnpj)
                    .addComponent(jLabel19)
                    .addComponent(txtim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txttelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(cbgrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                        .addComponent(txtcomplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbairro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcidade, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbuf, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcep, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncep)
                        .addGap(104, 104, 104))))
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
                        .addComponent(btncep))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtbairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(txtcidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        lblidrepresentante.setText("jLabel19");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblidrepresentante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtrepresentante, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .addComponent(txtcondicao)
                    .addComponent(txtvendedor, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtrepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(lblidrepresentante))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtcondicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton6)))
        );

        jTabbedPane1.addTab("Dados", jPanel7);

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

        jButton9.setText("Excluir Documento");

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

        jTabbedPane1.addTab("Documentos", jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        tablecontatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Nome", "Cargo", "E-mail", "Celular"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
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

        jButton11.setText("Excluir Contato");

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

        jTabbedPane1.addTab("Contatos", jPanel9);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
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
        } else if (txtidcliente.getText().equals("")) {
////////////Criar Cliente
            cb.setNome(txtnomecliente.getText());
            cb.setRazaosocial(txtrazao.getText());
            cb.setCnpj(txtcnpj.getText());
            cb.setIe(txtie.getText());
            cb.setTelefone(txttelefone.getText());
            cb.setGrupo(cbgrupo.getSelectedItem().toString());
            cb.setVendedor(txtvendedor.getText());
            cb.setIdrepresentante(Integer.parseInt(lblidrepresentante.getText()));
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
            cb.setNome(txtnomecliente.getText());
            cb.setRazaosocial(txtrazao.getText());
            cb.setCnpj(txtcnpj.getText());
            cb.setIe(txtie.getText());
            cb.setTelefone(txttelefone.getText());
            cb.setGrupo(cbgrupo.getSelectedItem().toString());
            cb.setVendedor(txtvendedor.getText());
            cb.setIdrepresentante(Integer.parseInt(lblidrepresentante.getText()));
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
            cb.setId(Integer.parseInt(txtidcliente.getText()));
            //nome = ?, razaosocial = ?, cnpj = ?, ie = ?, telefone = ?, grupo = ?, vendedor = ?, idrepresentante = ?, representante = ?,condicaodepagamento = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, cep = ?, im = ? WHERE id = ?
            cd.update(cb);

            //Zerar Campos
            zeracampos();

            //Mudar para a primeira tab
            tabclientes.setSelectedIndex(0);

            //Atualizar table de clientes
            readtableclientes();
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
            txtidcliente.setText(tableclientes.getValueAt(tableclientes.getSelectedRow(), 0).toString());
            tabclientes.setSelectedIndex(1);

            cd.click(Integer.parseInt(txtidcliente.getText())).forEach((ClientesBean cb) -> {
                txtnomecliente.setText(cb.getNome());
                txtrazao.setText(cb.getRazaosocial());
                txtcnpj.setText(cb.getCnpj());
                txtie.setText(cb.getIe());
                txttelefone.setText(cb.getTelefone());
                cbgrupo.setSelectedItem(cb.getGrupo());
                txtvendedor.setText(cb.getVendedor());
                lblidrepresentante.setText(String.valueOf(cb.getIdrepresentante()));
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
            });
        }
    }//GEN-LAST:event_tableclientesMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JDesktopPane d = this.getDesktopPane();
        ProcurarVendedorClientes pv = new ProcurarVendedorClientes();

        d.add(pv);
        Dimension desk = d.getSize();
        Dimension jif = pv.getSize();
        pv.setLocation((desk.width - jif.width) / 2, (desk.height - jif.height) / 2);
        pv.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JDesktopPane d = this.getDesktopPane();
        ProcurarRepresentanteClientes pr = new ProcurarRepresentanteClientes();

        d.add(pr);
        Dimension desk = d.getSize();
        Dimension jif = pr.getSize();
        pr.setLocation((desk.width - jif.width) / 2, (desk.height - jif.height) / 2);
        pr.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        JDesktopPane d = this.getDesktopPane();
        ProcurarCondicaoDePagamentoClientes pc = new ProcurarCondicaoDePagamentoClientes();

        d.add(pc);
        Dimension desk = d.getSize();
        Dimension jif = pc.getSize();
        pc.setLocation((desk.width - jif.width) / 2, (desk.height - jif.height) / 2);
        pc.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btncepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncepActionPerformed
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
//        if (txtcep.getText().equals("")) {
//            JOptionPane.showMessageDialog(rootPane, "Coloque um CEP!");
//        } else {
//            try {
//                consultaCEP(txtcep.getText());
//                if (!consultaCEP(txtcep.getText()).getEnd().equals("")) {
//                    txtlogradouro.setText(consultaCEP(txtcep.getText()).getEnd());
//                    txtbairro.setText(consultaCEP(txtcep.getText()).getBairro());
//                    txtcidade.setText(consultaCEP(txtcep.getText()).getCidade());
//                    cbuf.setSelectedItem(consultaCEP(txtcep.getText()).getUf());
//                    txtnumero.requestFocus();
//                }
//            } catch (SigepClienteException ex) {
//                Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
//                JOptionPane.showMessageDialog(rootPane, "Erro ao consultar! \n" + ex);
//            } catch (SQLException_Exception ex) {
//                Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        if (txtcep.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Digite um CEP primeiro.");
            txtcep.requestFocus();
        } else {
            try {
                procuraCep();
            } catch (Exception ex) {
                Logger.getLogger(Clientes.class
                        .getName()).log(Level.SEVERE, null, ex);
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
            } catch (Exception ex) {
                Logger.getLogger(Clientes.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btncnpjActionPerformed

    private void txtpesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpesquisaKeyReleased
        DefaultTableModel model = (DefaultTableModel) tableclientes.getModel();
        model.setNumRows(0);
        
        cd.pesquisa(txtpesquisa.getText()).forEach(cb -> {
            model.addRow(new Object[]{
                cb.getId(),
                cb.getNome(),
                cb.getRazaosocial()
            });
        });
    }//GEN-LAST:event_txtpesquisaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncep;
    private javax.swing.JButton btncnpj;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbgrupo;
    private javax.swing.JComboBox<String> cbuf;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
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
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JLabel lblidrepresentante;
    private javax.swing.JTabbedPane tabclientes;
    public static javax.swing.JTable tableclientes;
    private javax.swing.JTable tablecontatos;
    private javax.swing.JTable tabledocs;
    private javax.swing.JTextField txtbairro;
    private static javax.swing.JFormattedTextField txtcep;
    private javax.swing.JTextField txtcidade;
    private javax.swing.JFormattedTextField txtcnpj;
    private javax.swing.JTextField txtcomplemento;
    public static javax.swing.JTextField txtcondicao;
    private javax.swing.JTextField txtidcliente;
    private javax.swing.JTextField txtie;
    private static javax.swing.JTextField txtim;
    private javax.swing.JTextField txtlogradouro;
    private javax.swing.JTextField txtnomecliente;
    private javax.swing.JTextField txtnumero;
    private javax.swing.JTextField txtpesquisa;
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
