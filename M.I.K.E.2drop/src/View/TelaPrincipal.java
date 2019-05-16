/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Bean.GrupoDeUsuariosBean;
import Bean.UsuariosBean;
import DAO.GrupoDeUsuariosDAO;
import DAO.UsuariosDAO;
import View.comercial.Clientes;
import View.administracao.Usuarios;
import View.servicos.CotacaoServico;
import View.vendas.OPs;
import View.arquivo.Email;
import Methods.EmBreve;
import View.administracao.GrupoDeUsuarios;
import View.administracao.Regioes;
import View.administracao.Representantes;
import View.comercial.Fornecedores;
import View.configuracoes.Menus;
import View.financeiro.Bancos;
import View.financeiro.CondicoesDePagamento;
import View.financeiro.ContasPagar;
import View.qualidade.InstrumentosMedicao;
import View.servicos.GrupoDeProcessosServico;
import View.servicos.OS;
import View.servicos.ServicoMateriais;
import View.servicos.PedidoServico;
import View.servicos.ProcessosServico;
import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public final class TelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
        this.setExtendedState(TelaPrincipal.MAXIMIZED_BOTH);
        menus();
    }

    public void menus() {
        lbllogin.setText(TelaLogin.TxtLogin.getText());
        UsuariosDAO ud = new UsuariosDAO();
        String nivel = "";
        for (UsuariosBean ub : ud.checknivel(lbllogin.getText())) {
            nivel = ub.getNivel();
            lblnome.setText("Bem vindo(a) " + ub.getNome() + "!");
            lblapelido.setText(ub.getNome());
        }
        lblgrupo.setText(nivel);
        lblgrupo.setVisible(false);
        lbllogin.setVisible(false);
        lblapelido.setVisible(false);
        GrupoDeUsuariosDAO gud = new GrupoDeUsuariosDAO();
        Boolean status = true;
        for (GrupoDeUsuariosBean gub : gud.getmenus(nivel)) {
            if (gub.getMenuadministracao().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuadministracao.setVisible(status);
            if (gub.getSubmenuusuarios().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemusuarios.setVisible(status);
            if (gub.getSubmenugrupodeusuarios().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemgrupodeusuarios.setVisible(status);
            if (gub.getSubmenurepresentantes().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemrepresentantes.setVisible(status);
            if (gub.getSubmenuregioesdeatuacao().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemregioesdeatuacao.setVisible(status);
            if (gub.getMenucomercial().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menucomercial.setVisible(status);
            if (gub.getSubmenuclientes().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemclientes.setVisible(status);
            if (gub.getSubmenugrupodeclientes().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemgrupodeclientes.setVisible(status);
            if (gub.getSubmenufornecedores().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemfornecedores.setVisible(status);
            if (gub.getMenufinanceiro().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menufinanceiro.setVisible(status);
            if (gub.getSubmenucontasareceber().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemcontasareceber.setVisible(status);
            if (gub.getSubmenucontasapagar().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemcontasapagar.setVisible(status);
            if (gub.getSubmenucondicoesdepagamento().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemcondicoesdepagamento.setVisible(status);
            if (gub.getSubmenubancos().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitembancos.setVisible(status);
            if (gub.getMenucompras().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menucompras.setVisible(status);
            if (gub.getSubmenusolicitacaodecompras().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemsolicitacaodecompras.setVisible(status);
            if (gub.getSubmenuorcamentodecompras().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemorcamentodecompras.setVisible(status);
            if (gub.getSubmenupedidodecompras().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitempedidodecompras.setVisible(status);
            if (gub.getSubmenuinsumos().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuiteminsumos.setVisible(status);
            if (gub.getMenulogistica().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menulogistica.setVisible(status);
            if (gub.getSubmenucarros().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemcarros.setVisible(status);
            if (gub.getMenuqualidade().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuqualidade.setVisible(status);
            if (gub.getSubmenuinstrumentosdemedicao().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuiteminstrumentosmedicao.setVisible(status);
            if (gub.getMenuvendas().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuvendas.setVisible(status);
            if (gub.getSubmenuorcamentosvenda().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemvendasorcamentos.setVisible(status);
            if (gub.getSubmenupedidosvenda().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemvendaspedidos.setVisible(status);
            if (gub.getSubmenuops().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemvendasops.setVisible(status);
            if (gub.getSubmenuprodutosvenda().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemvendasprodutos.setVisible(status);
            if (gub.getSubmenuprocessosvenda().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemvendasprocessos.setVisible(status);
            if (gub.getSubmenugrupodeprocessosvenda().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemvendasgrupodeprocessos.setVisible(status);
            if (gub.getMenuservicos().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuservicos.setVisible(status);
            if (gub.getSubmenuorcamentosservico().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemservicosorcamentos.setVisible(status);
            if (gub.getSubmenupedidosservico().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemservicospedidos.setVisible(status);
            if (gub.getSubmenuoss().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemservicososs.setVisible(status);
            if (gub.getSubmenuprodutosservico().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemservicosprodutos.setVisible(status);
            if (gub.getSubmenuprocessosservico().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemservicosproccessos.setVisible(status);
            if (gub.getSubmenugrupodeprocessosservico().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemservicosgrupodeprocessos.setVisible(status);
            if (gub.getMenuconfiguracoes().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuconfiguracoes.setVisible(status);
            if (gub.getSubmenumenus().equals("false")) {
                status = false;
            } else {
                status = true;
            }
            TelaPrincipal.menuitemmenus.setVisible(status);
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
        jDesktopPane1 = new javax.swing.JDesktopPane();
        lblnome = new javax.swing.JLabel();
        lblgrupo = new javax.swing.JLabel();
        lbllogin = new javax.swing.JLabel();
        lblapelido = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuArquivo = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuadministracao = new javax.swing.JMenu();
        menuitemusuarios = new javax.swing.JMenuItem();
        menuitemgrupodeusuarios = new javax.swing.JMenuItem();
        menuitemrepresentantes = new javax.swing.JMenuItem();
        menuitemregioesdeatuacao = new javax.swing.JMenuItem();
        menucomercial = new javax.swing.JMenu();
        menuitemclientes = new javax.swing.JMenuItem();
        menuitemgrupodeclientes = new javax.swing.JMenuItem();
        menuitemfornecedores = new javax.swing.JMenuItem();
        menufinanceiro = new javax.swing.JMenu();
        menuitemcontasareceber = new javax.swing.JMenuItem();
        menuitemcontasapagar = new javax.swing.JMenuItem();
        menuitemcondicoesdepagamento = new javax.swing.JMenuItem();
        menuitembancos = new javax.swing.JMenuItem();
        menucompras = new javax.swing.JMenu();
        menuitemsolicitacaodecompras = new javax.swing.JMenuItem();
        menuitemorcamentodecompras = new javax.swing.JMenuItem();
        menuitempedidodecompras = new javax.swing.JMenuItem();
        menuiteminsumos = new javax.swing.JMenuItem();
        menulogistica = new javax.swing.JMenu();
        menuitemcarros = new javax.swing.JMenuItem();
        menuqualidade = new javax.swing.JMenu();
        menuiteminstrumentosmedicao = new javax.swing.JMenuItem();
        menuvendas = new javax.swing.JMenu();
        menuitemvendasorcamentos = new javax.swing.JMenuItem();
        menuitemvendaspedidos = new javax.swing.JMenuItem();
        menuitemvendasops = new javax.swing.JMenuItem();
        menuitemvendasprodutos = new javax.swing.JMenuItem();
        menuitemvendasprocessos = new javax.swing.JMenuItem();
        menuitemvendasgrupodeprocessos = new javax.swing.JMenuItem();
        menuservicos = new javax.swing.JMenu();
        menuitemservicosorcamentos = new javax.swing.JMenuItem();
        menuitemservicospedidos = new javax.swing.JMenuItem();
        menuitemservicososs = new javax.swing.JMenuItem();
        menuitemservicosprodutos = new javax.swing.JMenuItem();
        menuitemservicosproccessos = new javax.swing.JMenuItem();
        menuitemservicosgrupodeprocessos = new javax.swing.JMenuItem();
        menuconfiguracoes = new javax.swing.JMenu();
        menuitemmenus = new javax.swing.JMenuItem();
        menuajuda = new javax.swing.JMenu();
        menuitemmike = new javax.swing.JMenuItem();
        menuitemsobre = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("M.I.K.E.");

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));

        lblnome.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblnome.setForeground(new java.awt.Color(255, 255, 255));
        lblnome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblnome.setText("jLabel1");

        lblgrupo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblgrupo.setText("Grupo");

        lbllogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbllogin.setText("Login");

        lblapelido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblapelido.setText("Apelido");

        jDesktopPane1.setLayer(lblnome, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(lblgrupo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(lbllogin, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(lblapelido, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblnome, javax.swing.GroupLayout.DEFAULT_SIZE, 924, Short.MAX_VALUE)
            .addComponent(lblgrupo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbllogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblapelido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblnome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblgrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbllogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblapelido)
                .addContainerGap(184, Short.MAX_VALUE))
        );

        MenuArquivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page.png"))); // NOI18N
        MenuArquivo.setText("Arquivo");

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/email.png"))); // NOI18N
        jMenuItem12.setText("Email");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        MenuArquivo.add(jMenuItem12);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/door_out.png"))); // NOI18N
        jMenuItem1.setText("Sair");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        MenuArquivo.add(jMenuItem1);

        jMenuBar1.add(MenuArquivo);

        menuadministracao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/user_gray.png"))); // NOI18N
        menuadministracao.setText("Administração");

        menuitemusuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/user.png"))); // NOI18N
        menuitemusuarios.setText("Usuários");
        menuitemusuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemusuariosActionPerformed(evt);
            }
        });
        menuadministracao.add(menuitemusuarios);

        menuitemgrupodeusuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/group.png"))); // NOI18N
        menuitemgrupodeusuarios.setText("Grupo de Usuários");
        menuitemgrupodeusuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemgrupodeusuariosActionPerformed(evt);
            }
        });
        menuadministracao.add(menuitemgrupodeusuarios);

        menuitemrepresentantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/group_go.png"))); // NOI18N
        menuitemrepresentantes.setText("Representantes");
        menuitemrepresentantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemrepresentantesActionPerformed(evt);
            }
        });
        menuadministracao.add(menuitemrepresentantes);

        menuitemregioesdeatuacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map.png"))); // NOI18N
        menuitemregioesdeatuacao.setText("Regiões de Atuação");
        menuitemregioesdeatuacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemregioesdeatuacaoActionPerformed(evt);
            }
        });
        menuadministracao.add(menuitemregioesdeatuacao);

        jMenuBar1.add(menuadministracao);

        menucomercial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/phone.png"))); // NOI18N
        menucomercial.setText("Comercial");

        menuitemclientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/group.png"))); // NOI18N
        menuitemclientes.setText("Clientes");
        menuitemclientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemclientesActionPerformed(evt);
            }
        });
        menucomercial.add(menuitemclientes);

        menuitemgrupodeclientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/group_add.png"))); // NOI18N
        menuitemgrupodeclientes.setText("Grupo de Clientes");
        menucomercial.add(menuitemgrupodeclientes);

        menuitemfornecedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/lorry.png"))); // NOI18N
        menuitemfornecedores.setText("Fornecedores");
        menuitemfornecedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemfornecedoresActionPerformed(evt);
            }
        });
        menucomercial.add(menuitemfornecedores);

        jMenuBar1.add(menucomercial);

        menufinanceiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money.png"))); // NOI18N
        menufinanceiro.setText("Financeiro");

        menuitemcontasareceber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money_add.png"))); // NOI18N
        menuitemcontasareceber.setText("Contas a Receber");
        menufinanceiro.add(menuitemcontasareceber);

        menuitemcontasapagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money_delete.png"))); // NOI18N
        menuitemcontasapagar.setText("Contas a Pagar");
        menuitemcontasapagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemcontasapagarActionPerformed(evt);
            }
        });
        menufinanceiro.add(menuitemcontasapagar);

        menuitemcondicoesdepagamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money_dollar.png"))); // NOI18N
        menuitemcondicoesdepagamento.setText("Condições de Pagamento");
        menuitemcondicoesdepagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemcondicoesdepagamentoActionPerformed(evt);
            }
        });
        menufinanceiro.add(menuitemcondicoesdepagamento);

        menuitembancos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/coins.png"))); // NOI18N
        menuitembancos.setText("Bancos");
        menuitembancos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitembancosActionPerformed(evt);
            }
        });
        menufinanceiro.add(menuitembancos);

        jMenuBar1.add(menufinanceiro);

        menucompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart.png"))); // NOI18N
        menucompras.setText("Compras");

        menuitemsolicitacaodecompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart_add.png"))); // NOI18N
        menuitemsolicitacaodecompras.setText("Solicitação de Compra");
        menuitemsolicitacaodecompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemsolicitacaodecomprasActionPerformed(evt);
            }
        });
        menucompras.add(menuitemsolicitacaodecompras);

        menuitemorcamentodecompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart_edit.png"))); // NOI18N
        menuitemorcamentodecompras.setText("Cotação de Compra");
        menucompras.add(menuitemorcamentodecompras);

        menuitempedidodecompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart_go.png"))); // NOI18N
        menuitempedidodecompras.setText("Pedido de Compra");
        menucompras.add(menuitempedidodecompras);

        menuiteminsumos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart_put.png"))); // NOI18N
        menuiteminsumos.setText("Insumos");
        menucompras.add(menuiteminsumos);

        jMenuBar1.add(menucompras);

        menulogistica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/package_green.png"))); // NOI18N
        menulogistica.setText("Logística");

        menuitemcarros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/car.png"))); // NOI18N
        menuitemcarros.setText("Carros");
        menuitemcarros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemcarrosActionPerformed(evt);
            }
        });
        menulogistica.add(menuitemcarros);

        jMenuBar1.add(menulogistica);

        menuqualidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/award_star_bronze_3.png"))); // NOI18N
        menuqualidade.setText("Qualidade");

        menuiteminstrumentosmedicao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/compress.png"))); // NOI18N
        menuiteminstrumentosmedicao.setText("Instrumentos de Medição");
        menuiteminstrumentosmedicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuiteminstrumentosmedicaoActionPerformed(evt);
            }
        });
        menuqualidade.add(menuiteminstrumentosmedicao);

        jMenuBar1.add(menuqualidade);

        menuvendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/wrench.png"))); // NOI18N
        menuvendas.setText("Vendas");

        menuitemvendasorcamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/table_wrench.png"))); // NOI18N
        menuitemvendasorcamentos.setText("Cotações");
        menuitemvendasorcamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemvendasorcamentosActionPerformed(evt);
            }
        });
        menuvendas.add(menuitemvendasorcamentos);

        menuitemvendaspedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/table_wrench_go.png"))); // NOI18N
        menuitemvendaspedidos.setText("Pedidos");
        menuitemvendaspedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemvendaspedidosActionPerformed(evt);
            }
        });
        menuvendas.add(menuitemvendaspedidos);

        menuitemvendasops.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_wrench.png"))); // NOI18N
        menuitemvendasops.setText("OP's");
        menuitemvendasops.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemvendasopsActionPerformed(evt);
            }
        });
        menuvendas.add(menuitemvendasops);

        menuitemvendasprodutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cog_wrench.png"))); // NOI18N
        menuitemvendasprodutos.setText("Produtos");
        menuvendas.add(menuitemvendasprodutos);

        menuitemvendasprocessos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_wrench.png"))); // NOI18N
        menuitemvendasprocessos.setText("Processos");
        menuvendas.add(menuitemvendasprocessos);

        menuitemvendasgrupodeprocessos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_cascade_wrench.png"))); // NOI18N
        menuitemvendasgrupodeprocessos.setText("Grupo de Processos");
        menuvendas.add(menuitemvendasgrupodeprocessos);

        jMenuBar1.add(menuvendas);

        menuservicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/wrench_orange.png"))); // NOI18N
        menuservicos.setText("Serviços");

        menuitemservicosorcamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/table_wrench_orange.png"))); // NOI18N
        menuitemservicosorcamentos.setText("Cotações");
        menuitemservicosorcamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemservicosorcamentosActionPerformed(evt);
            }
        });
        menuservicos.add(menuitemservicosorcamentos);

        menuitemservicospedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/table_wrench_orange_go.png"))); // NOI18N
        menuitemservicospedidos.setText("Pedidos");
        menuitemservicospedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemservicospedidosActionPerformed(evt);
            }
        });
        menuservicos.add(menuitemservicospedidos);

        menuitemservicososs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_wrench_orange.png"))); // NOI18N
        menuitemservicososs.setText("OS's");
        menuitemservicososs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemservicosossActionPerformed(evt);
            }
        });
        menuservicos.add(menuitemservicososs);

        menuitemservicosprodutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cog_wrench_orange.png"))); // NOI18N
        menuitemservicosprodutos.setText("Produtos");
        menuitemservicosprodutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemservicosprodutosActionPerformed(evt);
            }
        });
        menuservicos.add(menuitemservicosprodutos);

        menuitemservicosproccessos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_wrench_orange.png"))); // NOI18N
        menuitemservicosproccessos.setText("Processos");
        menuitemservicosproccessos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemservicosproccessosActionPerformed(evt);
            }
        });
        menuservicos.add(menuitemservicosproccessos);

        menuitemservicosgrupodeprocessos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_cascade_wrench_orange.png"))); // NOI18N
        menuitemservicosgrupodeprocessos.setText("Grupo de Processos");
        menuitemservicosgrupodeprocessos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemservicosgrupodeprocessosActionPerformed(evt);
            }
        });
        menuservicos.add(menuitemservicosgrupodeprocessos);

        jMenuBar1.add(menuservicos);

        menuconfiguracoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plugin.png"))); // NOI18N
        menuconfiguracoes.setText("Configurações");

        menuitemmenus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_cascade.png"))); // NOI18N
        menuitemmenus.setText("Menus");
        menuitemmenus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemmenusActionPerformed(evt);
            }
        });
        menuconfiguracoes.add(menuitemmenus);

        jMenuBar1.add(menuconfiguracoes);

        menuajuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/information.png"))); // NOI18N
        menuajuda.setText("Ajuda");

        menuitemmike.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        menuitemmike.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/lightbulb.png"))); // NOI18N
        menuitemmike.setText("M.I.K.E.");
        menuitemmike.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemmikeActionPerformed(evt);
            }
        });
        menuajuda.add(menuitemmike);

        menuitemsobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_text.png"))); // NOI18N
        menuitemsobre.setText("Sobre");
        menuitemsobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemsobreActionPerformed(evt);
            }
        });
        menuajuda.add(menuitemsobre);

        jMenuBar1.add(menuajuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void menuitemusuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemusuariosActionPerformed
        Usuarios telauser = new Usuarios();
        jDesktopPane1.add(telauser);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = telauser.getSize();
        telauser.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        telauser.setVisible(true);
    }//GEN-LAST:event_menuitemusuariosActionPerformed

    private void menuitemvendasopsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemvendasopsActionPerformed
        try {
            // TODO add your handling code here:
            OPs ops = new OPs();
            jDesktopPane1.add(ops);
            Dimension desktopsize = jDesktopPane1.getSize();
            Dimension jinternalframesize = ops.getSize();
            ops.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
            ops.setVisible(true);
            ops.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Erro: " + ex);
        }
    }//GEN-LAST:event_menuitemvendasopsActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        Email email = new Email();
        jDesktopPane1.add(email);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = email.getSize();
        email.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        email.setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void menuitemvendaspedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemvendaspedidosActionPerformed
        // TODO add your handling code here:
        EmBreve.EmBreve();
    }//GEN-LAST:event_menuitemvendaspedidosActionPerformed

    private void menuitemvendasorcamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemvendasorcamentosActionPerformed
        // TODO add your handling code here:
        EmBreve.EmBreve();
    }//GEN-LAST:event_menuitemvendasorcamentosActionPerformed

    private void menuitemsobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemsobreActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(rootPane, "Sistema M.I.K.E®\n\nCriado por Marcos Filho\nTodos os direitos reservados");
    }//GEN-LAST:event_menuitemsobreActionPerformed

    private void menuitemmikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemmikeActionPerformed
        Tarefas t = new Tarefas();
        t.setVisible(true);
    }//GEN-LAST:event_menuitemmikeActionPerformed

    private void menuitemservicosorcamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemservicosorcamentosActionPerformed
        try {
            CotacaoServico tela = new CotacaoServico();
            jDesktopPane1.add(tela);
            tela.setVisible(true);
            tela.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Erro: " + ex);
        }
    }//GEN-LAST:event_menuitemservicosorcamentosActionPerformed

    private void menuitemservicospedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemservicospedidosActionPerformed
        PedidoServico p = new PedidoServico();
        jDesktopPane1.add(p);
        p.setVisible(true);
        try {
            p.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuitemservicospedidosActionPerformed

    private void menuitemservicosossActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemservicosossActionPerformed
        OS os = new OS();
        jDesktopPane1.add(os);
        os.setVisible(true);
        try {
            os.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuitemservicosossActionPerformed

    private void menuitemclientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemclientesActionPerformed
        try {
            Clientes telaclientes = new Clientes();
            jDesktopPane1.add(telaclientes);
            Dimension desktopsize = jDesktopPane1.getSize();
            Dimension jinternalframesize = telaclientes.getSize();
            telaclientes.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
            telaclientes.setVisible(true);
            telaclientes.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Erro: " + ex);
        }
    }//GEN-LAST:event_menuitemclientesActionPerformed

    private void menuitemservicosprodutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemservicosprodutosActionPerformed
        try {
            ServicoMateriais tela = new ServicoMateriais();
            jDesktopPane1.add(tela);
            Dimension desktopsize = jDesktopPane1.getSize();
            Dimension jinternalframesize = tela.getSize();
            tela.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
            tela.setVisible(true);
            tela.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(rootPane, "Erro: " + e);
        }
    }//GEN-LAST:event_menuitemservicosprodutosActionPerformed

    private void menuitemgrupodeusuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemgrupodeusuariosActionPerformed
        GrupoDeUsuarios gu = new GrupoDeUsuarios();
        jDesktopPane1.add(gu);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = gu.getSize();
        gu.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        gu.setVisible(true);
    }//GEN-LAST:event_menuitemgrupodeusuariosActionPerformed

    private void menuitemmenusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemmenusActionPerformed
        Menus m = new Menus();
        jDesktopPane1.add(m);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = m.getSize();
        m.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        m.setVisible(true);
    }//GEN-LAST:event_menuitemmenusActionPerformed

    private void menuitemrepresentantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemrepresentantesActionPerformed
        Representantes r = new Representantes();
        jDesktopPane1.add(r);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = r.getSize();
        r.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        r.setVisible(true);
    }//GEN-LAST:event_menuitemrepresentantesActionPerformed

    private void menuitemregioesdeatuacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemregioesdeatuacaoActionPerformed
        Regioes r = new Regioes();
        jDesktopPane1.add(r);
        Dimension desk = jDesktopPane1.getSize();
        Dimension rsize = r.getSize();
        r.setLocation((desk.width - rsize.width) / 2, (desk.height - rsize.height) / 2);
        r.setVisible(true);
    }//GEN-LAST:event_menuitemregioesdeatuacaoActionPerformed

    private void menuitemcondicoesdepagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemcondicoesdepagamentoActionPerformed
        CondicoesDePagamento cp = new CondicoesDePagamento();
        jDesktopPane1.add(cp);
        Dimension desk = jDesktopPane1.getSize();
        Dimension inf = cp.getSize();
        cp.setLocation((desk.width - inf.width) / 2, (desk.height - inf.height) / 2);
        cp.setVisible(true);
    }//GEN-LAST:event_menuitemcondicoesdepagamentoActionPerformed

    private void menuitemcontasapagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemcontasapagarActionPerformed
        ContasPagar c = new ContasPagar();
        jDesktopPane1.add(c);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.setVisible(true);
    }//GEN-LAST:event_menuitemcontasapagarActionPerformed

    private void menuitembancosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitembancosActionPerformed
        Bancos b = new Bancos();
        jDesktopPane1.add(b);
        try {
            b.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        b.setVisible(true);
    }//GEN-LAST:event_menuitembancosActionPerformed

    private void menuitemfornecedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemfornecedoresActionPerformed
        Fornecedores f = new Fornecedores();
        jDesktopPane1.add(f);
        Dimension jif = f.getSize();
        Dimension d = jDesktopPane1.getSize();
        f.setLocation((d.width - jif.width) / 2, (d.height - jif.height) / 2);
        f.setVisible(true);
    }//GEN-LAST:event_menuitemfornecedoresActionPerformed

    private void menuitemservicosproccessosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemservicosproccessosActionPerformed
        ProcessosServico ps = new ProcessosServico();
        jDesktopPane1.add(ps);
        Dimension jif = ps.getSize();
        Dimension desk = jDesktopPane1.getSize();
        ps.setLocation((desk.width - jif.width) / 2, (desk.height - jif.height) / 2);
        ps.setVisible(true);
    }//GEN-LAST:event_menuitemservicosproccessosActionPerformed

    private void menuitemservicosgrupodeprocessosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemservicosgrupodeprocessosActionPerformed
        GrupoDeProcessosServico gps = new GrupoDeProcessosServico();
        jDesktopPane1.add(gps);
        Dimension jif = gps.getSize();
        Dimension desk = jDesktopPane1.getSize();
        gps.setLocation((desk.width - jif.width) / 2, (desk.height - jif.height) / 2);
        gps.setVisible(true);
    }//GEN-LAST:event_menuitemservicosgrupodeprocessosActionPerformed

    private void menuitemsolicitacaodecomprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemsolicitacaodecomprasActionPerformed

    }//GEN-LAST:event_menuitemsolicitacaodecomprasActionPerformed

    private void menuiteminstrumentosmedicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuiteminstrumentosmedicaoActionPerformed
        InstrumentosMedicao p = new InstrumentosMedicao();
        jDesktopPane1.add(p);
        Dimension jif = p.getSize();
        Dimension desk = jDesktopPane1.getSize();
        p.setLocation((desk.width - jif.width) / 2, (desk.height - jif.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_menuiteminstrumentosmedicaoActionPerformed

    private void menuitemcarrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemcarrosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuitemcarrosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JMenu MenuArquivo;
    public static javax.swing.JDesktopPane jDesktopPane1;
    public static javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel lblapelido;
    public static javax.swing.JLabel lblgrupo;
    public static javax.swing.JLabel lbllogin;
    public static javax.swing.JLabel lblnome;
    public static javax.swing.JMenu menuadministracao;
    private javax.swing.JMenu menuajuda;
    public static javax.swing.JMenu menucomercial;
    public static javax.swing.JMenu menucompras;
    public static javax.swing.JMenu menuconfiguracoes;
    public static javax.swing.JMenu menufinanceiro;
    public static javax.swing.JMenuItem menuitembancos;
    public static javax.swing.JMenuItem menuitemcarros;
    public static javax.swing.JMenuItem menuitemclientes;
    public static javax.swing.JMenuItem menuitemcondicoesdepagamento;
    public static javax.swing.JMenuItem menuitemcontasapagar;
    public static javax.swing.JMenuItem menuitemcontasareceber;
    public static javax.swing.JMenuItem menuitemfornecedores;
    public static javax.swing.JMenuItem menuitemgrupodeclientes;
    public static javax.swing.JMenuItem menuitemgrupodeusuarios;
    public static javax.swing.JMenuItem menuiteminstrumentosmedicao;
    public static javax.swing.JMenuItem menuiteminsumos;
    public static javax.swing.JMenuItem menuitemmenus;
    private javax.swing.JMenuItem menuitemmike;
    public static javax.swing.JMenuItem menuitemorcamentodecompras;
    public static javax.swing.JMenuItem menuitempedidodecompras;
    public static javax.swing.JMenuItem menuitemregioesdeatuacao;
    public static javax.swing.JMenuItem menuitemrepresentantes;
    public static javax.swing.JMenuItem menuitemservicosgrupodeprocessos;
    public static javax.swing.JMenuItem menuitemservicosorcamentos;
    public static javax.swing.JMenuItem menuitemservicososs;
    public static javax.swing.JMenuItem menuitemservicospedidos;
    public static javax.swing.JMenuItem menuitemservicosproccessos;
    public static javax.swing.JMenuItem menuitemservicosprodutos;
    private javax.swing.JMenuItem menuitemsobre;
    public static javax.swing.JMenuItem menuitemsolicitacaodecompras;
    public static javax.swing.JMenuItem menuitemusuarios;
    public static javax.swing.JMenuItem menuitemvendasgrupodeprocessos;
    public static javax.swing.JMenuItem menuitemvendasops;
    public static javax.swing.JMenuItem menuitemvendasorcamentos;
    public static javax.swing.JMenuItem menuitemvendaspedidos;
    public static javax.swing.JMenuItem menuitemvendasprocessos;
    public static javax.swing.JMenuItem menuitemvendasprodutos;
    public static javax.swing.JMenu menulogistica;
    public static javax.swing.JMenu menuqualidade;
    public static javax.swing.JMenu menuservicos;
    public static javax.swing.JMenu menuvendas;
    // End of variables declaration//GEN-END:variables
}
