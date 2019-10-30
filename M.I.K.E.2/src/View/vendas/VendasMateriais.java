/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.vendas;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Marcos Filho
 */
public class VendasMateriais extends javax.swing.JInternalFrame {

    /**
     * Creates new form Produtos
     */
    public VendasMateriais() {
        initComponents();
        invisible();
    }

    public static void invisible() {
        lblfamilia.setVisible(false);
        cbfamilia.setVisible(false);
        lbltamanho.setVisible(false);
        cbtamanho.setVisible(false);
        lblcortes.setVisible(false);
        txtcortes.setVisible(false);
        lbltopo.setVisible(false);
        cbtopo.setVisible(false);
    }

    public static void cbfamiliaexcluir() {
        cbfamilia.removeAllItems();
        cbfamilia.addItem("Selecione");
    }

    public static void cbfamiliaincluir() {
        int selection = cbtipo.getSelectedIndex();
        switch (selection) {
            case 1:
                cbfamilia.addItem("12");
                cbfamilia.addItem("13");
                cbfamilia.addItem("14");
                cbfamilia.addItem("15");
                cbfamilia.addItem("16");
                cbfamilia.addItem("17");
                break;
            case 2:
                cbfamilia.addItem("12");
                cbfamilia.addItem("13");
                cbfamilia.addItem("14");
                cbfamilia.addItem("15");
                cbfamilia.addItem("16");
                cbfamilia.addItem("17");
                break;
            case 3:
                cbfamilia.addItem("6539");
                cbfamilia.addItem("338N");
                cbfamilia.addItem("340N");
                cbfamilia.addItem("6537KRE");
                cbfamilia.addItem("6537KRI");
                cbfamilia.addItem("6537LRE");
                cbfamilia.addItem("6537LRI");
                break;
            case 4:
                cbfamilia.addItem("6539");
                cbfamilia.addItem("338N");
                cbfamilia.addItem("340N");
                cbfamilia.addItem("6537KRE");
                cbfamilia.addItem("6537KRI");
                cbfamilia.addItem("6537LRE");
                cbfamilia.addItem("6537LRI");
                break;
            case 5:
                cbfamilia.addItem("2111");
                cbfamilia.addItem("212");
                break;
            case 6:
                cbfamilia.addItem("2111");
                cbfamilia.addItem("212");
                break;
            case 7:
                cbfamilia.addItem("SA");
                cbfamilia.addItem("SB");
                cbfamilia.addItem("SC");
                cbfamilia.addItem("SD");
                cbfamilia.addItem("SE");
                cbfamilia.addItem("SF");
                cbfamilia.addItem("SG");
                cbfamilia.addItem("SH");
                cbfamilia.addItem("SJ");
                cbfamilia.addItem("SK");
                cbfamilia.addItem("SL");
                cbfamilia.addItem("SM");
                cbfamilia.addItem("SN");
                cbfamilia.addItem("SGR");
                break;
            case 8:
                cbfamilia.addItem("SA");
                cbfamilia.addItem("SB");
                cbfamilia.addItem("SC");
                cbfamilia.addItem("SD");
                cbfamilia.addItem("SE");
                cbfamilia.addItem("SF");
                cbfamilia.addItem("SG");
                cbfamilia.addItem("SH");
                cbfamilia.addItem("SJ");
                cbfamilia.addItem("SK");
                cbfamilia.addItem("SL");
                cbfamilia.addItem("SM");
                cbfamilia.addItem("SN");
                cbfamilia.addItem("SGR");
                break;
            case 9:
                break;
            default:
                break;
        }
    }

    public static void cbtamanhoexcluir() {
        cbtamanho.removeAllItems();
        cbtamanho.addItem("Selecione");
    }

    public static void gerarcodigofresa() {
        //Variáveis para criar código
        String codigo;
        String desc;
        String ferr = "";
        String tipotopo = "";
        String cortes = "";
        String revchar = "";
        String revtipo = "";
        String raio = "";

        //Identificar se tem revestimento e qual é
        if (checkrevestimento.isSelected()) {
            revtipo = " Com Revestimento " + cbrevestimento.getSelectedItem().toString();
            int selection = cbrevestimento.getSelectedIndex();
            switch (selection) {
                case 1:
                    revchar = " T";
                    break;
                case 2:
                    revchar = " A";
                    break;
                case 3:
                    revchar = " AC";
                    break;
                default:
                    break;
            }
        }

        //Identificar se tem raio e qual medida
        if (checkraio.isSelected()) {
            raio = " R" + txtraio.getText();
        }

        //Identificar família da fresa e criar descrição
        int selection = cbfamilia.getSelectedIndex();
        switch (selection) {
            case 1:
                ferr = "Fresa Topo ";
                tipotopo = "Reto Metal Duro ";
                cortes = "2 Cortes ";
                break;
            case 2:
                ferr = "Fresa Topo ";
                tipotopo = "Reto Metal Duro ";
                cortes = "3 Cortes ";
                break;
            case 3:
                ferr = "Fresa Topo ";
                tipotopo = "Reto Metal Duro ";
                cortes = "4 Cortes ";
                break;
            case 4:
                ferr = "Fresa Topo ";
                tipotopo = "Esférico Metal Duro ";
                cortes = "2 Cortes ";
                break;
            case 5:
                ferr = "Fresa Topo ";
                tipotopo = "Esférico Metal Duro ";
                cortes = "4 Cortes ";
                break;
            case 6:
                ferr = "Fresa Topo ";
                tipotopo = "Esférico Metal Duro ";
                cortes = "3 Cortes ";
                break;
            default:
                break;
        }

        //Criar código/descrição
        codigo = cbfamilia.getSelectedItem().toString() + "-" + txtd1.getText() + cbtamanho.getSelectedItem().toString() + raio + revchar;
        desc = ferr + tipotopo + cortes + txtd1.getText() + "x" + txtl1.getText() + "x" + txtl2.getText() + "x" + txtd2.getText() + raio + revtipo;

        //Colocar código e descrição nos txt's
        txtcodigo.setText(codigo);
        txtdescricao.setText(desc);
    }

    public static void gerarcodigofresaespecial() {
        //Variáveis para criar código
        String codigo;
        String desc;
        String ferr = "";
        String tipotopo = "";
        String cortes = "";
        String revchar = "";
        String revtipo = "";
        String raio = "";

        //Identificar se tem revestimento e qual é
        if (checkrevestimento.isSelected()) {
            revtipo = " Com Revestimento " + cbrevestimento.getSelectedItem().toString();
            int selection = cbrevestimento.getSelectedIndex();
            switch (selection) {
                case 1:
                    revchar = " T";
                    break;
                case 2:
                    revchar = " A";
                    break;
                case 3:
                    revchar = " AC";
                    break;
                default:
                    break;
            }
        }

        //Identificar se tem raio e qual medida
        if (checkraio.isSelected()) {
            raio = " R" + txtraio.getText();
        }

        //Identificar se a fresa tem família ou número de cortes e criar descrição
        if (cbfamilia.getSelectedIndex() != 0) {
            int selection = cbfamilia.getSelectedIndex();
            switch (selection) {
                case 1:
                    ferr = "Fresa Topo ";
                    tipotopo = "Reto Metal Duro ";
                    cortes = "2 Cortes ";
                    break;
                case 2:
                    ferr = "Fresa Topo ";
                    tipotopo = "Reto Metal Duro ";
                    cortes = "3 Cortes ";
                    break;
                case 3:
                    ferr = "Fresa Topo ";
                    tipotopo = "Reto Metal Duro ";
                    cortes = "4 Cortes ";
                    break;
                case 4:
                    ferr = "Fresa Topo ";
                    tipotopo = "Esférico Metal Duro ";
                    cortes = "2 Cortes ";
                    break;
                case 5:
                    ferr = "Fresa Topo ";
                    tipotopo = "Esférico Metal Duro ";
                    cortes = "4 Cortes ";
                    break;
                case 6:
                    ferr = "Fresa Topo ";
                    tipotopo = "Esférico Metal Duro ";
                    cortes = "3 Cortes ";
                    break;
                default:
                    break;
            }
        } else {
            ferr = "Fresa Topo ";
            tipotopo = cbtopo.getSelectedItem().toString() + " Metal Duro ";
            cortes = txtcortes.getText() + " Cortes ";
        }

        //Criar código/descrição
        if (!txtcortes.getText().equals("")) {
            codigo = cbtipo.getSelectedItem().toString() + " " + txtcortes.getText() + "C " + txtd1.getText() + "x" + txtl1.getText() + "x" + txtl2.getText() + "x" + txtd2.getText() + raio + revchar;
            desc = ferr + tipotopo + cortes + txtd1.getText() + "x" + txtl1.getText() + "x" + txtl2.getText() + "x" + txtd2.getText() + raio + revtipo;
        } else {
            codigo = cbtipo.getSelectedItem().toString() + " " + cbfamilia.getSelectedItem().toString() + "-" + txtd1.getText() + "x" + txtl1.getText() + "x" + txtl2.getText() + "x" + txtd2.getText() + raio + revchar;
            desc = ferr + tipotopo + cortes + txtd1.getText() + "x" + txtl1.getText() + "x" + txtl2.getText() + "x" + txtd2.getText() + raio + revtipo;
        }

        //Colocar código e descrição nos txt's
        txtcodigo.setText(codigo);
        txtdescricao.setText(desc);
    }

    public static void gerarcodigobroca() {
        //Variáveis para criar código
        String codigo;
        String desc;
        String ferr = "Broca ";
        String tipoferr = "";
        String revchar = "";
        String revtipo = "";
        String raio = "";
        String richar = "";
        String ritipo = "";

        //Identificar se tem revestimento e qual é
        if (checkrevestimento.isSelected()) {
            revtipo = " Com Revestimento " + cbrevestimento.getSelectedItem().toString();
            int selection = cbrevestimento.getSelectedIndex();
            switch (selection) {
                case 1:
                    revchar = " T";
                    break;
                case 2:
                    revchar = " A";
                    break;
                case 3:
                    revchar = " AC";
                    break;
                default:
                    break;
            }
        }

        //Identificar se tem raio e qual medida
        if (checkraio.isSelected()) {
            raio = " R" + txtraio.getText();
        }

        //Identificar se tem refrigeração interna
        if (checkri.isSelected()) {
            richar = " RI";
            ritipo = " Com Refrigeração Interna";
        }

        //Identificar família da fresa e criar descrição
        int selection = cbfamilia.getSelectedIndex();
        switch (selection) {
            case 1:
                tipoferr = "6539 Diam. ";
                break;
            case 2:
                tipoferr = "338N Diam. ";
                break;
            case 3:
                tipoferr = "340N Diam. ";
                break;
            case 4:
                tipoferr = "6537KRE Diam. ";
                break;
            case 5:
                tipoferr = "6537KRI Diam. ";
                break;
            case 6:
                tipoferr = "6537LRE Diam. ";
                break;
            case 7:
                tipoferr = "6537LRI Diam. ";
                break;
            default:
                break;
        }

        //Criar código/descrição
        codigo = cbfamilia.getSelectedItem().toString() + " Diam. " + txtd1.getText() + richar + raio + revchar;
        desc = ferr + tipoferr + txtd1.getText() + "x" + txtl1.getText() + "x" + txtl2.getText() + "x" + txtd2.getText() + ritipo + raio + revtipo;

        //Colocar código e descrição nos txt's
        txtcodigo.setText(codigo);
        txtdescricao.setText(desc);
    }

    public static void travartxtdados() {
        //Travar campos do paneldiam
        for (int i = 0; i < paneldiam.getComponentCount(); i++) {
            Component c = paneldiam.getComponent(i);
            if (c instanceof JTextField) {
                c.setEnabled(false);
            }
        }

        //Travar campos do panelcomp
        for (int i = 0; i < panelcomp.getComponentCount(); i++) {
            Component c = panelcomp.getComponent(i);
            if (c instanceof JTextField) {
                c.setEnabled(false);
            }
        }

        //Travar campos do paneldadostxt
        for (int i = 0; i < paneldadostxt.getComponentCount(); i++) {
            Component c = paneldadostxt.getComponent(i);
            if (c instanceof JTextField) {
                c.setEnabled(false);
            }
        }
    }

    public static void txtfresa() {
        //String para procura de texto no nome
        String nome = "fresa";

        //Abrir campos do paneldiam
        for (int i = 0; i < 10; i++) {
            Component c = paneldiam.getComponent(i);
            String name = c.getName();
            if (name != null && name.contains(nome)) {
                c.setEnabled(true);
            }
        }

        //Abrir campos do panelcomp
        for (int i = 0; i < 10; i++) {
            Component c = panelcomp.getComponent(i);
            String name = c.getName();
            if (name != null && name.contains(nome)) {
                c.setEnabled(true);
            }
        }

        //Abrir campos do paneltxtdados
        for (int i = 0; i < paneldadostxt.getComponentCount(); i++) {
            Component c = paneldadostxt.getComponent(i);
            String name = c.getName();
            if (name != null && name.contains(nome)) {
                c.setEnabled(true);
            }
        }
    }

    public static void txtfresaespecial() {
        //String para procura de texto no nome
        String nome = "fe";

        //Abrir campos do paneldiam
        for (int i = 0; i < 10; i++) {
            Component c = paneldiam.getComponent(i);
            String name = c.getName();
            if (name != null && name.contains(nome)) {
                c.setEnabled(true);
            }
        }

        //Abrir campos do panelcomp
        for (int i = 0; i < 10; i++) {
            Component c = panelcomp.getComponent(i);
            String name = c.getName();
            if (name != null && name.contains(nome)) {
                c.setEnabled(true);
            }
        }

        //Abrir campos do paneltxtdados
        for (int i = 0; i < paneldadostxt.getComponentCount(); i++) {
            Component c = paneldadostxt.getComponent(i);
            String name = c.getName();
            if (name != null && name.contains(nome)) {
                c.setEnabled(true);
            }
        }
    }

    public static void txtbroca() {
        //String para procura de texto no nome
        String nome = "broca";

        //Abrir campos do paneldiam
        for (int i = 0; i < 10; i++) {
            Component c = paneldiam.getComponent(i);
            String name = c.getName();
            if (name != null && name.contains(nome)) {
                c.setEnabled(true);
            }
        }

        //Abrir campos do panelcomp
        for (int i = 0; i < 10; i++) {
            Component c = panelcomp.getComponent(i);
            String name = c.getName();
            if (name != null && name.contains(nome)) {
                c.setEnabled(true);
            }
        }

        //Abrir campos do paneltxtdados
        for (int i = 0; i < paneldadostxt.getComponentCount(); i++) {
            Component c = paneldadostxt.getComponent(i);
            String name = c.getName();
            if (name != null && name.contains(nome)) {
                c.setEnabled(true);
            }
        }
    }

    public static void txtbrocaespecial() {
        //String para procura de texto no nome
        String nome = "be";

        //Abrir campos do paneldiam
        for (int i = 0; i < 10; i++) {
            Component c = paneldiam.getComponent(i);
            String name = c.getName();
            if (name != null && name.contains(nome)) {
                c.setEnabled(true);
            }
        }

        //Abrir campos do panelcomp
        for (int i = 0; i < 10; i++) {
            Component c = panelcomp.getComponent(i);
            String name = c.getName();
            if (name != null && name.contains(nome)) {
                c.setEnabled(true);
            }
        }

        //Abrir campos do paneltxtdados
        for (int i = 0; i < paneldadostxt.getComponentCount(); i++) {
            Component c = paneldadostxt.getComponent(i);
            String name = c.getName();
            if (name != null && name.contains(nome)) {
                c.setEnabled(true);
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

        GroupRevestimento = new javax.swing.ButtonGroup();
        GroupTamanho = new javax.swing.ButtonGroup();
        tabmateriais = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        txtpesquisa = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablemateriaisvendas = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtcodigo = new javax.swing.JTextField();
        txtdescricao = new javax.swing.JTextField();
        txtstatus = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tabmaterialinfo = new javax.swing.JTabbedPane();
        panelobs = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableobs = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        paneldados = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbtipo = new javax.swing.JComboBox<>();
        paneldiam = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtd1 = new javax.swing.JTextField();
        txtd2 = new javax.swing.JTextField();
        txtd3 = new javax.swing.JTextField();
        txtd4 = new javax.swing.JTextField();
        txtd5 = new javax.swing.JTextField();
        panelcomp = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtl1 = new javax.swing.JTextField();
        txtl2 = new javax.swing.JTextField();
        txtl3 = new javax.swing.JTextField();
        txtl4 = new javax.swing.JTextField();
        txtl5 = new javax.swing.JTextField();
        cbfamilia = new javax.swing.JComboBox<>();
        lblfamilia = new javax.swing.JLabel();
        lbltamanho = new javax.swing.JLabel();
        cbtamanho = new javax.swing.JComboBox<>();
        btngerarcodigo = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        checkrevestimento = new javax.swing.JCheckBox();
        cbrevestimento = new javax.swing.JComboBox<>();
        checkraio = new javax.swing.JCheckBox();
        txtraio = new javax.swing.JTextField();
        checkri = new javax.swing.JCheckBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        paneldadostxt = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txthelice = new javax.swing.JTextField();
        txtnucleo = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtconcavidade = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtaliviotopo1 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtaliviotopo2 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtalivio1 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txtalivio2 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtespfilete = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtagressividade = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtfrontal = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        lblicon = new javax.swing.JLabel();
        lblcortes = new javax.swing.JLabel();
        txtcortes = new javax.swing.JTextField();
        lbltopo = new javax.swing.JLabel();
        cbtopo = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablecategoria = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txtmp = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        paneldocs = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabledocumentos = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        paneldesc = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabledesccli = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        panelmov = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setTitle("Materiais de Venda");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtpesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtpesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Desativado", "Todos" }));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, 0, 170, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        tablemateriaisvendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Código", "Descrição", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tablemateriaisvendas);
        if (tablemateriaisvendas.getColumnModel().getColumnCount() > 0) {
            tablemateriaisvendas.getColumnModel().getColumn(0).setMinWidth(0);
            tablemateriaisvendas.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablemateriaisvendas.getColumnModel().getColumn(0).setMaxWidth(0);
            tablemateriaisvendas.getColumnModel().getColumn(1).setMinWidth(180);
            tablemateriaisvendas.getColumnModel().getColumn(1).setPreferredWidth(180);
            tablemateriaisvendas.getColumnModel().getColumn(1).setMaxWidth(180);
            tablemateriaisvendas.getColumnModel().getColumn(3).setMinWidth(150);
            tablemateriaisvendas.getColumnModel().getColumn(3).setPreferredWidth(150);
            tablemateriaisvendas.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 357, Short.MAX_VALUE)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabmateriais.addTab("Materiais Cadastrados", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Material"));

        jLabel1.setText("Código");

        jLabel2.setText("Descrição");

        txtcodigo.setEditable(false);

        txtdescricao.setEditable(false);

        txtstatus.setEditable(false);

        jLabel15.setText("Status");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtdescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableobs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Usuário", "Data", "Observação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableobs);
        if (tableobs.getColumnModel().getColumnCount() > 0) {
            tableobs.getColumnModel().getColumn(0).setMinWidth(0);
            tableobs.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableobs.getColumnModel().getColumn(0).setMaxWidth(0);
            tableobs.getColumnModel().getColumn(1).setMinWidth(200);
            tableobs.getColumnModel().getColumn(1).setPreferredWidth(200);
            tableobs.getColumnModel().getColumn(1).setMaxWidth(200);
            tableobs.getColumnModel().getColumn(2).setMinWidth(150);
            tableobs.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableobs.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        jButton1.setText("Adicionar Observação");

        javax.swing.GroupLayout panelobsLayout = new javax.swing.GroupLayout(panelobs);
        panelobs.setLayout(panelobsLayout);
        panelobsLayout.setHorizontalGroup(
            panelobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1139, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelobsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        panelobsLayout.setVerticalGroup(
            panelobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelobsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        tabmaterialinfo.addTab("Observações", panelobs);

        jLabel3.setText("Tipo");

        cbtipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Fresa", "Fresa Especial", "Broca", "Broca Especial", "Alargador", "Alargador Especial", "Lima", "Lima Especial", "Ferramenta Especial" }));
        cbtipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbtipoActionPerformed(evt);
            }
        });

        paneldiam.setBackground(new java.awt.Color(255, 255, 255));
        paneldiam.setBorder(javax.swing.BorderFactory.createTitledBorder("Diâmetro"));

        jLabel4.setText("D1");

        jLabel5.setText("D2");

        jLabel6.setText("D3");

        jLabel7.setText("D4");

        jLabel8.setText("D5");

        txtd1.setEnabled(false);
        txtd1.setName("fresa-broca-fe-be"); // NOI18N
        txtd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtd1KeyReleased(evt);
            }
        });

        txtd2.setEnabled(false);
        txtd2.setName("fresa-broca-fe-be"); // NOI18N

        txtd3.setEnabled(false);
        txtd3.setName("fe-be"); // NOI18N

        txtd4.setEnabled(false);
        txtd4.setName("fe-be"); // NOI18N

        txtd5.setEnabled(false);
        txtd5.setName("fe-be"); // NOI18N

        javax.swing.GroupLayout paneldiamLayout = new javax.swing.GroupLayout(paneldiam);
        paneldiam.setLayout(paneldiamLayout);
        paneldiamLayout.setHorizontalGroup(
            paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldiamLayout.createSequentialGroup()
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd5))
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd4))
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd3))
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paneldiamLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtd2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paneldiamLayout.setVerticalGroup(
            paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldiamLayout.createSequentialGroup()
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtd3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtd4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldiamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtd5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelcomp.setBackground(new java.awt.Color(255, 255, 255));
        panelcomp.setBorder(javax.swing.BorderFactory.createTitledBorder("Comprimento"));

        jLabel9.setText("L1");

        jLabel10.setText("L2");

        jLabel11.setText("L3");

        jLabel12.setText("L4");

        jLabel13.setText("L5");

        txtl1.setEnabled(false);
        txtl1.setName("fresa-broca-fe-be"); // NOI18N
        txtl1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtl1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtl1FocusLost(evt);
            }
        });

        txtl2.setEnabled(false);
        txtl2.setName("fresa-broca-fe-be"); // NOI18N

        txtl3.setEnabled(false);
        txtl3.setName("fe-be"); // NOI18N

        txtl4.setEnabled(false);
        txtl4.setName("fe-be"); // NOI18N

        txtl5.setEnabled(false);
        txtl5.setName("fe-be"); // NOI18N

        javax.swing.GroupLayout panelcompLayout = new javax.swing.GroupLayout(panelcomp);
        panelcomp.setLayout(panelcompLayout);
        panelcompLayout.setHorizontalGroup(
            panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcompLayout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl5))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcompLayout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl4))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcompLayout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl3))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcompLayout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl2))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcompLayout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtl1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelcompLayout.setVerticalGroup(
            panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcompLayout.createSequentialGroup()
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtl3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtl4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelcompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtl5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        cbfamilia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cbfamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbfamiliaActionPerformed(evt);
            }
        });

        lblfamilia.setText("Família");

        lbltamanho.setText("Tamanho");

        cbtamanho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));

        btngerarcodigo.setText("Gerar Código/Descrição");
        btngerarcodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngerarcodigoActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Especificações"));

        checkrevestimento.setBackground(new java.awt.Color(255, 255, 255));
        checkrevestimento.setText("Revestimento");
        checkrevestimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkrevestimentoActionPerformed(evt);
            }
        });

        cbrevestimento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "TIN", "TIALN", "ALCRONA" }));
        cbrevestimento.setEnabled(false);

        checkraio.setText("Raio");
        checkraio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkraioActionPerformed(evt);
            }
        });

        txtraio.setEnabled(false);
        txtraio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtraioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtraioFocusLost(evt);
            }
        });

        checkri.setText("Refrigeração Interna");

        jCheckBox1.setText("Weldon");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(checkrevestimento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbrevestimento, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(checkraio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtraio, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(checkri)
            .addComponent(jCheckBox1)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkrevestimento)
                    .addComponent(cbrevestimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkraio)
                    .addComponent(txtraio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkri)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados para Construção"));

        jLabel14.setText("Hélice");

        txthelice.setEnabled(false);
        txthelice.setName("fresa-broca-fe-be"); // NOI18N
        txthelice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtheliceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtheliceFocusLost(evt);
            }
        });

        txtnucleo.setEnabled(false);
        txtnucleo.setName("fresa-broca-fe-be"); // NOI18N
        txtnucleo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtnucleoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtnucleoFocusLost(evt);
            }
        });

        jLabel25.setText("Núcleo");

        txtconcavidade.setEnabled(false);
        txtconcavidade.setName("fresa-fe"); // NOI18N
        txtconcavidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtconcavidadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtconcavidadeFocusLost(evt);
            }
        });

        jLabel36.setText("Concavidade");

        txtaliviotopo1.setEnabled(false);
        txtaliviotopo1.setName("fresa-fe"); // NOI18N
        txtaliviotopo1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtaliviotopo1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtaliviotopo1FocusLost(evt);
            }
        });

        jLabel37.setText("Alívio de Topo Primário");

        txtaliviotopo2.setEnabled(false);
        txtaliviotopo2.setName("fresa-fe"); // NOI18N
        txtaliviotopo2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtaliviotopo2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtaliviotopo2FocusLost(evt);
            }
        });

        jLabel38.setText("Alívio de Topo Secundário");

        txtalivio1.setEnabled(false);
        txtalivio1.setName("fresa-fe"); // NOI18N
        txtalivio1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtalivio1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtalivio1FocusLost(evt);
            }
        });

        jLabel39.setText("Alívio Primário");

        txtalivio2.setEnabled(false);
        txtalivio2.setName("fresa-fe"); // NOI18N
        txtalivio2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtalivio2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtalivio2FocusLost(evt);
            }
        });

        jLabel40.setText("Alívio Secundário");

        txtespfilete.setEnabled(false);
        txtespfilete.setName("fresa-fe"); // NOI18N
        txtespfilete.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtespfileteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtespfileteFocusLost(evt);
            }
        });

        jLabel41.setText("Espessura de Filete");

        txtagressividade.setEnabled(false);
        txtagressividade.setName("fresa-broca-fe-be"); // NOI18N
        txtagressividade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtagressividadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtagressividadeFocusLost(evt);
            }
        });

        jLabel42.setText("Agressividade");

        txtfrontal.setEnabled(false);
        txtfrontal.setName("broca-be"); // NOI18N
        txtfrontal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtfrontalFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtfrontalFocusLost(evt);
            }
        });

        jLabel16.setText("Frontal");

        javax.swing.GroupLayout paneldadostxtLayout = new javax.swing.GroupLayout(paneldadostxt);
        paneldadostxt.setLayout(paneldadostxtLayout);
        paneldadostxtLayout.setHorizontalGroup(
            paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldadostxtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel25)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txthelice, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(txtagressividade)
                    .addComponent(txtespfilete)
                    .addComponent(txtalivio2)
                    .addComponent(txtalivio1)
                    .addComponent(txtaliviotopo2)
                    .addComponent(txtaliviotopo1)
                    .addComponent(txtconcavidade, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtnucleo, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(txtfrontal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paneldadostxtLayout.setVerticalGroup(
            paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldadostxtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txthelice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnucleo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtconcavidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtaliviotopo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtaliviotopo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txtalivio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txtalivio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtespfilete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtagressividade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadostxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfrontal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane6.setViewportView(paneldadostxt);

        lblicon.setBackground(new java.awt.Color(255, 255, 255));
        lblicon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblicon.setOpaque(true);
        jScrollPane7.setViewportView(lblicon);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane7)
        );

        lblcortes.setText("Cortes");

        txtcortes.setEnabled(false);
        txtcortes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcortesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcortesFocusLost(evt);
            }
        });

        lbltopo.setText("Topo");

        cbtopo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Reto", "Esférico", "Toroidal" }));
        cbtopo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbtopoActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Categoria"));

        tablecategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Categoria"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(tablecategoria);
        if (tablecategoria.getColumnModel().getColumnCount() > 0) {
            tablecategoria.getColumnModel().getColumn(0).setMinWidth(0);
            tablecategoria.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablecategoria.getColumnModel().getColumn(0).setMaxWidth(0);
            tablecategoria.getColumnModel().getColumn(1).setMinWidth(30);
            tablecategoria.getColumnModel().getColumn(1).setPreferredWidth(30);
            tablecategoria.getColumnModel().getColumn(1).setMaxWidth(30);
        }

        jButton6.setText("Incluir");

        jButton7.setText("Excluir");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7)))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Matéria Prima"));

        jButton8.setText("Procurar");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtmp)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(txtmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addContainerGap())
        );

        javax.swing.GroupLayout paneldadosLayout = new javax.swing.GroupLayout(paneldados);
        paneldados.setLayout(paneldadosLayout);
        paneldadosLayout.setHorizontalGroup(
            paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneldadosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btngerarcodigo))
                    .addGroup(paneldadosLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblfamilia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbfamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbltamanho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbtamanho, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblcortes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcortes, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbltopo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbtopo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 219, Short.MAX_VALUE))
                    .addGroup(paneldadosLayout.createSequentialGroup()
                        .addGroup(paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(paneldadosLayout.createSequentialGroup()
                                .addComponent(paneldiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelcomp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        paneldadosLayout.setVerticalGroup(
            paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbfamilia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblfamilia)
                    .addComponent(lbltamanho)
                    .addComponent(cbtamanho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblcortes)
                    .addComponent(txtcortes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltopo)
                    .addComponent(cbtopo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(paneldadosLayout.createSequentialGroup()
                        .addGroup(paneldadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(paneldiam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelcomp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 28, Short.MAX_VALUE))
                    .addGroup(paneldadosLayout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btngerarcodigo)
                .addContainerGap())
        );

        tabmaterialinfo.addTab("Dados do Material", paneldados);

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

        jButton2.setText("Adicionar Documento");

        jButton3.setText("Excluir Documento");

        javax.swing.GroupLayout paneldocsLayout = new javax.swing.GroupLayout(paneldocs);
        paneldocs.setLayout(paneldocsLayout);
        paneldocsLayout.setHorizontalGroup(
            paneldocsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldocsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldocsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1139, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneldocsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        paneldocsLayout.setVerticalGroup(
            paneldocsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldocsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldocsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        tabmaterialinfo.addTab("Documentos", paneldocs);

        tabledesccli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Cliente", "Código", "Descrição"
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
        jScrollPane3.setViewportView(tabledesccli);
        if (tabledesccli.getColumnModel().getColumnCount() > 0) {
            tabledesccli.getColumnModel().getColumn(0).setMinWidth(0);
            tabledesccli.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabledesccli.getColumnModel().getColumn(0).setMaxWidth(0);
            tabledesccli.getColumnModel().getColumn(1).setMinWidth(40);
            tabledesccli.getColumnModel().getColumn(1).setPreferredWidth(40);
            tabledesccli.getColumnModel().getColumn(1).setMaxWidth(40);
            tabledesccli.getColumnModel().getColumn(2).setMinWidth(150);
            tabledesccli.getColumnModel().getColumn(2).setPreferredWidth(150);
            tabledesccli.getColumnModel().getColumn(2).setMaxWidth(150);
            tabledesccli.getColumnModel().getColumn(3).setMinWidth(150);
            tabledesccli.getColumnModel().getColumn(3).setPreferredWidth(150);
            tabledesccli.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        jButton4.setText("Adicionar Descrição");

        jButton5.setText("Excluir Descrição");

        javax.swing.GroupLayout paneldescLayout = new javax.swing.GroupLayout(paneldesc);
        paneldesc.setLayout(paneldescLayout);
        paneldescLayout.setHorizontalGroup(
            paneldescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldescLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1139, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneldescLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        paneldescLayout.setVerticalGroup(
            paneldescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldescLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneldescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap())
        );

        tabmaterialinfo.addTab("Descrição Por Cliente", paneldesc);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Tipo de Movimentação", "Estoque Anterior", "Qtde Movimentada", "Novo Estoque", "Usuário"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable1);

        javax.swing.GroupLayout panelmovLayout = new javax.swing.GroupLayout(panelmov);
        panelmov.setLayout(panelmovLayout);
        panelmovLayout.setHorizontalGroup(
            panelmovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelmovLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1139, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelmovLayout.setVerticalGroup(
            panelmovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelmovLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabmaterialinfo.addTab("Movimentação", panelmov);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabmaterialinfo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabmaterialinfo)
                .addContainerGap())
        );

        tabmateriais.addTab("Material", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabmateriais)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabmateriais)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbtipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtipoActionPerformed
        int selection = cbtipo.getSelectedIndex();
        switch (selection) {
            case 1:
                lblfamilia.setVisible(true);
                cbfamilia.setVisible(true);
                lbltamanho.setVisible(true);
                cbtamanho.setVisible(true);
                lblcortes.setVisible(false);
                txtcortes.setVisible(false);
                lbltopo.setVisible(false);
                cbtopo.setVisible(false);
                cbfamiliaexcluir();
                cbfamiliaincluir();
                cbtamanhoexcluir();
                cbtamanho.addItem("C");
                cbtamanho.addItem("S");
                cbtamanho.addItem("L");
                cbtamanho.addItem("X");
                travartxtdados();
                txtfresa();
                break;
            case 2:
                lblfamilia.setVisible(true);
                cbfamilia.setVisible(true);
                lbltamanho.setVisible(false);
                cbtamanho.setVisible(false);
                lblcortes.setVisible(true);
                txtcortes.setVisible(true);
                lbltopo.setVisible(true);
                cbtopo.setVisible(true);
                cbfamiliaexcluir();
                cbfamiliaincluir();
                travartxtdados();
                txtfresaespecial();
                break;
            case 3:
                lblfamilia.setVisible(true);
                cbfamilia.setVisible(true);
                lbltamanho.setVisible(false);
                cbtamanho.setVisible(false);
                lblcortes.setVisible(false);
                txtcortes.setVisible(false);
                lbltopo.setVisible(false);
                cbtopo.setVisible(false);
                cbfamiliaexcluir();
                cbfamiliaincluir();
                travartxtdados();
                txtbroca();
                break;
            case 4:
                lblfamilia.setVisible(true);
                cbfamilia.setVisible(true);
                lbltamanho.setVisible(false);
                cbtamanho.setVisible(false);
                lblcortes.setVisible(false);
                txtcortes.setVisible(false);
                lbltopo.setVisible(false);
                cbtopo.setVisible(false);
                cbfamiliaexcluir();
                cbfamiliaincluir();
                travartxtdados();
                txtbrocaespecial();
                break;
            case 5:
                lblfamilia.setVisible(true);
                cbfamilia.setVisible(true);
                lbltamanho.setVisible(false);
                cbtamanho.setVisible(false);
                lblcortes.setVisible(false);
                txtcortes.setVisible(false);
                lbltopo.setVisible(false);
                cbtopo.setVisible(false);
                cbfamiliaexcluir();
                cbfamiliaincluir();
                travartxtdados();
                break;
            case 6:
                lblfamilia.setVisible(true);
                cbfamilia.setVisible(true);
                lbltamanho.setVisible(false);
                cbtamanho.setVisible(false);
                lblcortes.setVisible(false);
                txtcortes.setVisible(false);
                lbltopo.setVisible(false);
                cbtopo.setVisible(false);
                cbfamiliaexcluir();
                cbfamiliaincluir();
                travartxtdados();
                break;
            case 7:
                lblfamilia.setVisible(true);
                cbfamilia.setVisible(true);
                lbltamanho.setVisible(false);
                cbtamanho.setVisible(false);
                lblcortes.setVisible(false);
                txtcortes.setVisible(false);
                lbltopo.setVisible(false);
                cbtopo.setVisible(false);
                cbfamiliaexcluir();
                cbfamiliaincluir();
                break;
            case 8:
                lblfamilia.setVisible(true);
                cbfamilia.setVisible(true);
                lbltamanho.setVisible(false);
                cbtamanho.setVisible(false);
                lblcortes.setVisible(false);
                txtcortes.setVisible(false);
                lbltopo.setVisible(false);
                cbtopo.setVisible(false);
                cbfamiliaexcluir();
                cbfamiliaincluir();
                break;
            case 9:
                txtcodigo.setEditable(true);
                txtcodigo.requestFocus();
                txtdescricao.setEditable(true);
                break;
            default:
                travartxtdados();
                invisible();
                break;
        }
    }//GEN-LAST:event_cbtipoActionPerformed

    private void btngerarcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngerarcodigoActionPerformed
        int selection = cbtipo.getSelectedIndex();
        switch (selection) {
            case 1:
                if (cbfamilia.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Escolha uma família primeiro!");
                } else if (cbtamanho.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Escolha um tamanho primeiro!");
                } else {
                    gerarcodigofresa();
                }
                break;
            case 2:
                if (cbfamilia.getSelectedIndex() == 0 & txtcortes.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Escolha uma família ou número de cortes primeiro!");
                } else if (!txtcortes.getText().equals("") && cbtopo.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Selecione um tipo de topo primeiro!");
                } else {
                    gerarcodigofresaespecial();
                }
                break;
            case 3:
                if (cbfamilia.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Escolha uma família primeiro!");
                } else {
                    gerarcodigobroca();
                }
                break;
            case 4:
//                tabtipo();
//                tabtipo.setVisible(true);
//                tabtipo.setSelectedIndex(selection - 1);
//                tabtipo.setEnabledAt(selection - 1, true);
                break;
            case 5:
//                tabtipo();
//                tabtipo.setVisible(true);
//                tabtipo.setSelectedIndex(selection - 1);
//                tabtipo.setEnabledAt(selection - 1, true);
                break;
            case 6:
//                tabtipo();
//                tabtipo.setVisible(true);
//                tabtipo.setSelectedIndex(selection - 1);
//                tabtipo.setEnabledAt(selection - 1, true);
                break;
            case 7:
//                tabtipo();
//                tabtipo.setVisible(true);
//                tabtipo.setSelectedIndex(selection - 1);
//                tabtipo.setEnabledAt(selection - 1, true);
                break;
            case 8:
//                tabtipo();
//                tabtipo.setVisible(true);
//                tabtipo.setSelectedIndex(selection - 1);
//                tabtipo.setEnabledAt(selection - 1, true);
                break;
            case 9:
//                tabtipo();
//                tabtipo.setVisible(true);
//                tabtipo.setSelectedIndex(selection - 1);
//                tabtipo.setEnabledAt(selection - 1, true);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Escolha um tipo de ferramenta primeiro!");
                break;
        }
    }//GEN-LAST:event_btngerarcodigoActionPerformed

    private void checkrevestimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkrevestimentoActionPerformed
        if (checkrevestimento.isSelected()) {
            cbrevestimento.setEnabled(true);
        } else {
            cbrevestimento.setEnabled(false);
            cbrevestimento.setSelectedIndex(0);
        }
    }//GEN-LAST:event_checkrevestimentoActionPerformed

    private void checkraioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkraioActionPerformed
        if (checkraio.isSelected()) {
            txtraio.setEnabled(true);
        } else {
            txtraio.setEnabled(false);
            txtraio.setText("");
        }
    }//GEN-LAST:event_checkraioActionPerformed

    private void cbfamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbfamiliaActionPerformed
        if (cbfamilia.getSelectedIndex() != 0) {
            txtcortes.setEnabled(false);
            txtcortes.setText("");
            cbtopo.setSelectedIndex(0);
        }
    }//GEN-LAST:event_cbfamiliaActionPerformed

    private void cbtopoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtopoActionPerformed
        if (cbtopo.getSelectedIndex() != 0) {
            cbfamilia.setSelectedIndex(0);
            txtcortes.setEnabled(true);
        }
    }//GEN-LAST:event_cbtopoActionPerformed

    private void txtconcavidadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtconcavidadeFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/concavidade.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtconcavidadeFocusGained

    private void txtconcavidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtconcavidadeFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtconcavidadeFocusLost

    private void txtaliviotopo1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo1FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/aliviotopo1.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtaliviotopo1FocusGained

    private void txtaliviotopo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo2FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/aliviotopo2.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtaliviotopo2FocusGained

    private void txtd1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtd1KeyReleased
        JDesktopPane desk = this.getDesktopPane();
        JPanel pane = new JPanel();
        pane.setSize(50, 50);
        desk.add(pane);
        pane.setLocation(txtd1.getX(), txtd1.getY());
        pane.setVisible(true);
    }//GEN-LAST:event_txtd1KeyReleased

    private void txtaliviotopo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo1FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtaliviotopo1FocusLost

    private void txtaliviotopo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtaliviotopo2FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtaliviotopo2FocusLost

    private void txtnucleoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnucleoFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/nucleo.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtnucleoFocusGained

    private void txtnucleoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnucleoFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtnucleoFocusLost

    private void txtheliceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtheliceFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/helice.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtheliceFocusGained

    private void txtalivio1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtalivio1FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/alivio1.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtalivio1FocusGained

    private void txtheliceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtheliceFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtheliceFocusLost

    private void txtalivio1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtalivio1FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtalivio1FocusLost

    private void txtalivio2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtalivio2FocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/alivio2.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtalivio2FocusGained

    private void txtalivio2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtalivio2FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtalivio2FocusLost

    private void txtespfileteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtespfileteFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/espfilete.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtespfileteFocusGained

    private void txtespfileteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtespfileteFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtespfileteFocusLost

    private void txtagressividadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtagressividadeFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/agressividade.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtagressividadeFocusGained

    private void txtagressividadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtagressividadeFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtagressividadeFocusLost

    private void txtcortesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcortesFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/numcortes.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtcortesFocusGained

    private void txtcortesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcortesFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtcortesFocusLost

    private void txtraioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtraioFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/raio.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtraioFocusGained

    private void txtraioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtraioFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtraioFocusLost

    private void txtl1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtl1FocusGained
        String resource;
        int ind = cbtipo.getSelectedIndex();
        switch (ind) {
            case 1:
                resource = "/Images/l1.png";
                break;
            case 2:
                resource = "/Images/l1.png";
                break;
            case 3:
                resource = "/Images/l1broca.png";
                break;
            case 4:
                resource = "/Images/l1broca.png";
                break;
            default:
                resource = "/Images/l1.png";
                break;
        }
        ImageIcon i = new ImageIcon(getClass().getResource(resource));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtl1FocusGained

    private void txtl1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtl1FocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtl1FocusLost

    private void txtfrontalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfrontalFocusGained
        ImageIcon i = new ImageIcon(getClass().getResource("/Images/frontal.png"));
        lblicon.setIcon(i);
    }//GEN-LAST:event_txtfrontalFocusGained

    private void txtfrontalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfrontalFocusLost
        lblicon.setIcon(null);
    }//GEN-LAST:event_txtfrontalFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.ButtonGroup GroupRevestimento;
    public javax.swing.ButtonGroup GroupTamanho;
    public static javax.swing.JButton btngerarcodigo;
    public static javax.swing.JComboBox<String> cbfamilia;
    public static javax.swing.JComboBox<String> cbrevestimento;
    public static javax.swing.JComboBox<String> cbtamanho;
    public static javax.swing.JComboBox<String> cbtipo;
    public static javax.swing.JComboBox<String> cbtopo;
    public static javax.swing.JCheckBox checkraio;
    public static javax.swing.JCheckBox checkrevestimento;
    public static javax.swing.JCheckBox checkri;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton5;
    public javax.swing.JButton jButton6;
    public javax.swing.JButton jButton7;
    public javax.swing.JButton jButton8;
    public javax.swing.JCheckBox jCheckBox1;
    public javax.swing.JComboBox<String> jComboBox1;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel25;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel36;
    public javax.swing.JLabel jLabel37;
    public javax.swing.JLabel jLabel38;
    public javax.swing.JLabel jLabel39;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel40;
    public javax.swing.JLabel jLabel41;
    public javax.swing.JLabel jLabel42;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel11;
    public javax.swing.JPanel jPanel12;
    public javax.swing.JPanel jPanel18;
    public javax.swing.JPanel jPanel19;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JScrollPane jScrollPane6;
    public javax.swing.JScrollPane jScrollPane7;
    public javax.swing.JScrollPane jScrollPane8;
    public javax.swing.JTable jTable1;
    public static javax.swing.JLabel lblcortes;
    public static javax.swing.JLabel lblfamilia;
    public javax.swing.JLabel lblicon;
    public static javax.swing.JLabel lbltamanho;
    public static javax.swing.JLabel lbltopo;
    public static javax.swing.JPanel panelcomp;
    public javax.swing.JPanel paneldados;
    public static javax.swing.JPanel paneldadostxt;
    public javax.swing.JPanel paneldesc;
    public static javax.swing.JPanel paneldiam;
    public javax.swing.JPanel paneldocs;
    public javax.swing.JPanel panelmov;
    public javax.swing.JPanel panelobs;
    public static javax.swing.JTable tablecategoria;
    public javax.swing.JTable tabledesccli;
    public javax.swing.JTable tabledocumentos;
    public javax.swing.JTable tablemateriaisvendas;
    public javax.swing.JTable tableobs;
    public static javax.swing.JTabbedPane tabmateriais;
    public static javax.swing.JTabbedPane tabmaterialinfo;
    public static javax.swing.JTextField txtagressividade;
    public static javax.swing.JTextField txtalivio1;
    public static javax.swing.JTextField txtalivio2;
    public static javax.swing.JTextField txtaliviotopo1;
    public static javax.swing.JTextField txtaliviotopo2;
    public static javax.swing.JTextField txtcodigo;
    public static javax.swing.JTextField txtconcavidade;
    public static javax.swing.JTextField txtcortes;
    public static javax.swing.JTextField txtd1;
    public static javax.swing.JTextField txtd2;
    public static javax.swing.JTextField txtd3;
    public static javax.swing.JTextField txtd4;
    public static javax.swing.JTextField txtd5;
    public static javax.swing.JTextField txtdescricao;
    public static javax.swing.JTextField txtespfilete;
    public static javax.swing.JTextField txtfrontal;
    public static javax.swing.JTextField txthelice;
    public static javax.swing.JTextField txtl1;
    public static javax.swing.JTextField txtl2;
    public static javax.swing.JTextField txtl3;
    public static javax.swing.JTextField txtl4;
    public static javax.swing.JTextField txtl5;
    public static javax.swing.JTextField txtmp;
    public static javax.swing.JTextField txtnucleo;
    public javax.swing.JTextField txtpesquisa;
    public static javax.swing.JTextField txtraio;
    public javax.swing.JTextField txtstatus;
    // End of variables declaration//GEN-END:variables
}
