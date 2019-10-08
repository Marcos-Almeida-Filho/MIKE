/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Bean.GrupoDeUsuariosBean;
import Bean.UsuariosBean;
import Connection.Session;
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
import View.comercial.CategoriaDePreco;
import View.comercial.Fornecedores;
import View.comercial.TipoFornecedor;
import View.compras.ComprasSolicitacao;
import View.compras.TiposProduto;
import View.configuracoes.Menus;
import View.financeiro.Bancos;
import View.financeiro.CondicoesDePagamento;
import View.financeiro.ContasPagar;
import View.logistica.RastreamentoDocumentos;
import View.qualidade.InstrumentosMedicao;
import View.servicos.GrupoDeProcessosServico;
import View.servicos.OS;
import View.servicos.ServicoMateriais;
import View.servicos.PedidoServico;
import View.servicos.ProcessosServico;
import View.vendas.VendasMateriais;
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
        btntestemenus.setVisible(false);
    }

    public void menus() {
        //DAO para pesquisar
        UsuariosDAO ud = new UsuariosDAO();

        //Retornar nível do acesso
        for (UsuariosBean ub : ud.checknivel(Session.login)) {
            //Setar nível do acesso
            Session.nivel = ub.getNivel();
        }
        GrupoDeUsuariosDAO gud = new GrupoDeUsuariosDAO();
        Boolean status;
//        TelaPrincipal.menuarquivo.setVisible(false);
        for (GrupoDeUsuariosBean gub : gud.getmenus(Session.nivel)) {
            status = !gub.getMenuadministracao().equals("false");
            TelaPrincipal.menuadministracao.setVisible(status);
            status = !gub.getSubmenuusuarios().equals("false");
            TelaPrincipal.menuitemusuarios.setVisible(status);
            status = !gub.getSubmenugrupodeusuarios().equals("false");
            TelaPrincipal.menuitemgrupodeusuarios.setVisible(status);
            status = !gub.getSubmenurepresentantes().equals("false");
            TelaPrincipal.menuitemrepresentantes.setVisible(status);
            status = !gub.getSubmenuregioesdeatuacao().equals("false");
            TelaPrincipal.menuitemregioesdeatuacao.setVisible(status);
            status = !gub.getMenucomercial().equals("false");
            TelaPrincipal.menucomercial.setVisible(status);
            status = !gub.getSubmenuclientes().equals("false");
            TelaPrincipal.menuitemclientes.setVisible(status);
            status = !gub.getSubmenugrupodeclientes().equals("false");
            TelaPrincipal.menuitemgrupodeclientes.setVisible(status);
            status = !gub.getSubmenufornecedores().equals("false");
            TelaPrincipal.menuitemfornecedores.setVisible(status);
            status = !gub.getMenufinanceiro().equals("false");
            TelaPrincipal.menufinanceiro.setVisible(status);
            status = !gub.getSubmenucontasareceber().equals("false");
            TelaPrincipal.menuitemcontasareceber.setVisible(status);
            status = !gub.getSubmenucontasapagar().equals("false");
            TelaPrincipal.menuitemcontasapagar.setVisible(status);
            status = !gub.getSubmenucondicoesdepagamento().equals("false");
            TelaPrincipal.menuitemcondicoesdepagamento.setVisible(status);
            status = !gub.getSubmenubancos().equals("false");
            TelaPrincipal.menuitembancos.setVisible(status);
            status = !gub.getMenucompras().equals("false");
            TelaPrincipal.menucompras.setVisible(status);
            status = !gub.getSubmenusolicitacaodecompras().equals("false");
            TelaPrincipal.menuitemsolicitacaodecompras.setVisible(status);
            status = !gub.getSubmenuorcamentodecompras().equals("false");
            TelaPrincipal.menuitemorcamentodecompras.setVisible(status);
            status = !gub.getSubmenupedidodecompras().equals("false");
            TelaPrincipal.menuitempedidodecompras.setVisible(status);
            status = !gub.getSubmenuinsumos().equals("false");
            TelaPrincipal.menuiteminsumos.setVisible(status);
            status = !gub.getMenulogistica().equals("false");
            TelaPrincipal.menulogistica.setVisible(status);
            status = !gub.getSubmenucarros().equals("false");
            TelaPrincipal.menuitemcarros.setVisible(status);
            status = !gub.getMenuqualidade().equals("false");
            TelaPrincipal.menuqualidade.setVisible(status);
            status = !gub.getSubmenuinstrumentosdemedicao().equals("false");
            TelaPrincipal.menuiteminstrumentosmedicao.setVisible(status);
            status = !gub.getMenuvendas().equals("false");
            TelaPrincipal.menuvendas.setVisible(status);
            status = !gub.getSubmenuorcamentosvenda().equals("false");
            TelaPrincipal.menuitemvendasorcamentos.setVisible(status);
            status = !gub.getSubmenupedidosvenda().equals("false");
            TelaPrincipal.menuitemvendaspedidos.setVisible(status);
            status = !gub.getSubmenuops().equals("false");
            TelaPrincipal.menuitemvendasops.setVisible(status);
            status = !gub.getSubmenuprodutosvenda().equals("false");
            TelaPrincipal.menuitemvendasprodutos.setVisible(status);
            status = !gub.getSubmenuprocessosvenda().equals("false");
            TelaPrincipal.menuitemvendasprocessos.setVisible(status);
            status = !gub.getSubmenugrupodeprocessosvenda().equals("false");
            TelaPrincipal.menuitemvendasgrupodeprocessos.setVisible(status);
            status = !gub.getMenuservicos().equals("false");
            TelaPrincipal.menuservicos.setVisible(status);
            status = !gub.getSubmenuorcamentosservico().equals("false");
            TelaPrincipal.menuitemservicosorcamentos.setVisible(status);
            status = !gub.getSubmenupedidosservico().equals("false");
            TelaPrincipal.menuitemservicospedidos.setVisible(status);
            status = !gub.getSubmenuoss().equals("false");
            TelaPrincipal.menuitemservicososs.setVisible(status);
            status = !gub.getSubmenuprodutosservico().equals("false");
            TelaPrincipal.menuitemservicosprodutos.setVisible(status);
            status = !gub.getSubmenuprocessosservico().equals("false");
            TelaPrincipal.menuitemservicosproccessos.setVisible(status);
            status = !gub.getSubmenugrupodeprocessosservico().equals("false");
            TelaPrincipal.menuitemservicosgrupodeprocessos.setVisible(status);
            status = !gub.getMenuconfiguracoes().equals("false");
            TelaPrincipal.menuconfiguracoes.setVisible(status);
            status = !gub.getSubmenumenus().equals("false");
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
        btntestemenus = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuarquivo = new javax.swing.JMenu();
        menuitememail = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        menuitemsair = new javax.swing.JMenuItem();
        menuadministracao = new javax.swing.JMenu();
        menuitemusuarios = new javax.swing.JMenuItem();
        menuitemgrupodeusuarios = new javax.swing.JMenuItem();
        menuitemrepresentantes = new javax.swing.JMenuItem();
        menuitemregioesdeatuacao = new javax.swing.JMenuItem();
        menufiscal = new javax.swing.JMenu();
        menuitemnotasfiscais = new javax.swing.JMenuItem();
        menuitemnatureza = new javax.swing.JMenuItem();
        menucomercial = new javax.swing.JMenu();
        menuitemclientes = new javax.swing.JMenuItem();
        menuitemgrupodeclientes = new javax.swing.JMenuItem();
        menuitemfornecedores = new javax.swing.JMenuItem();
        menuitemcategoriapreco = new javax.swing.JMenuItem();
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
        jMenuItem1 = new javax.swing.JMenuItem();
        menulogistica = new javax.swing.JMenu();
        menuitemcarros = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        menuqualidade = new javax.swing.JMenu();
        menuiteminstrumentosmedicao = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
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

        btntestemenus.setText("Teste Menus");
        btntestemenus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntestemenusActionPerformed(evt);
            }
        });

        jDesktopPane1.setLayer(lblnome, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(btntestemenus, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblnome, javax.swing.GroupLayout.DEFAULT_SIZE, 1160, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btntestemenus)
                .addGap(522, 522, 522))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblnome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btntestemenus)
                .addContainerGap(259, Short.MAX_VALUE))
        );

        menuarquivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page.png"))); // NOI18N
        menuarquivo.setText("Arquivo");
        menuarquivo.setName("menuarquivo"); // NOI18N

        menuitememail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/email.png"))); // NOI18N
        menuitememail.setText("Email");
        menuitememail.setName("menuitememail"); // NOI18N
        menuitememail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitememailActionPerformed(evt);
            }
        });
        menuarquivo.add(menuitememail);

        jMenuItem5.setText("Protocolos de Atendimento");
        menuarquivo.add(jMenuItem5);

        menuitemsair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/door_out.png"))); // NOI18N
        menuitemsair.setText("Sair");
        menuitemsair.setName("menuitemsair"); // NOI18N
        menuitemsair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemsairActionPerformed(evt);
            }
        });
        menuarquivo.add(menuitemsair);

        jMenuBar1.add(menuarquivo);

        menuadministracao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/user_gray.png"))); // NOI18N
        menuadministracao.setText("Administração");
        menuadministracao.setName("menuadministracao"); // NOI18N

        menuitemusuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/user.png"))); // NOI18N
        menuitemusuarios.setText("Usuários");
        menuitemusuarios.setName("menuitemusuarios"); // NOI18N
        menuitemusuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemusuariosActionPerformed(evt);
            }
        });
        menuadministracao.add(menuitemusuarios);

        menuitemgrupodeusuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/group.png"))); // NOI18N
        menuitemgrupodeusuarios.setText("Grupo de Usuários");
        menuitemgrupodeusuarios.setName("menuitemgrupodeusuarios"); // NOI18N
        menuitemgrupodeusuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemgrupodeusuariosActionPerformed(evt);
            }
        });
        menuadministracao.add(menuitemgrupodeusuarios);

        menuitemrepresentantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/group_go.png"))); // NOI18N
        menuitemrepresentantes.setText("Representantes");
        menuitemrepresentantes.setName("menuitemrepresentantes"); // NOI18N
        menuitemrepresentantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemrepresentantesActionPerformed(evt);
            }
        });
        menuadministracao.add(menuitemrepresentantes);

        menuitemregioesdeatuacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map.png"))); // NOI18N
        menuitemregioesdeatuacao.setText("Regiões de Atuação");
        menuitemregioesdeatuacao.setName("menuitemregioesdeatuacao"); // NOI18N
        menuitemregioesdeatuacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemregioesdeatuacaoActionPerformed(evt);
            }
        });
        menuadministracao.add(menuitemregioesdeatuacao);

        jMenuBar1.add(menuadministracao);

        menufiscal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page.png"))); // NOI18N
        menufiscal.setText("Fiscal");
        menufiscal.setName("menufiscal"); // NOI18N

        menuitemnotasfiscais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_refresh.png"))); // NOI18N
        menuitemnotasfiscais.setText("Notas Fiscais");
        menuitemnotasfiscais.setName("menuitemnotasfiscais"); // NOI18N
        menuitemnotasfiscais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemnotasfiscaisActionPerformed(evt);
            }
        });
        menufiscal.add(menuitemnotasfiscais);

        menuitemnatureza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_gear.png"))); // NOI18N
        menuitemnatureza.setText("Natureza de Operação");
        menuitemnatureza.setName("menuitemnatureza"); // NOI18N
        menuitemnatureza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemnaturezaActionPerformed(evt);
            }
        });
        menufiscal.add(menuitemnatureza);

        jMenuBar1.add(menufiscal);

        menucomercial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/phone.png"))); // NOI18N
        menucomercial.setText("Comercial");
        menucomercial.setName("menucomercial"); // NOI18N

        menuitemclientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/group.png"))); // NOI18N
        menuitemclientes.setText("Clientes");
        menuitemclientes.setName("menuitemclientes"); // NOI18N
        menuitemclientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemclientesActionPerformed(evt);
            }
        });
        menucomercial.add(menuitemclientes);

        menuitemgrupodeclientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/group_add.png"))); // NOI18N
        menuitemgrupodeclientes.setText("Grupo de Clientes");
        menuitemgrupodeclientes.setName("menuitemgrupodeclientes"); // NOI18N
        menucomercial.add(menuitemgrupodeclientes);

        menuitemfornecedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/lorry.png"))); // NOI18N
        menuitemfornecedores.setText("Fornecedores");
        menuitemfornecedores.setName("menuitemfornecedores"); // NOI18N
        menuitemfornecedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemfornecedoresActionPerformed(evt);
            }
        });
        menucomercial.add(menuitemfornecedores);

        menuitemcategoriapreco.setText("Categorias de Preço");
        menuitemcategoriapreco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemcategoriaprecoActionPerformed(evt);
            }
        });
        menucomercial.add(menuitemcategoriapreco);

        jMenuBar1.add(menucomercial);

        menufinanceiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money.png"))); // NOI18N
        menufinanceiro.setText("Financeiro");
        menufinanceiro.setName("menufinanceiro"); // NOI18N

        menuitemcontasareceber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money_add.png"))); // NOI18N
        menuitemcontasareceber.setText("Contas a Receber");
        menuitemcontasareceber.setName("menuitemcontasareceber"); // NOI18N
        menufinanceiro.add(menuitemcontasareceber);

        menuitemcontasapagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money_delete.png"))); // NOI18N
        menuitemcontasapagar.setText("Contas a Pagar");
        menuitemcontasapagar.setName("menuitemcontasapagar"); // NOI18N
        menuitemcontasapagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemcontasapagarActionPerformed(evt);
            }
        });
        menufinanceiro.add(menuitemcontasapagar);

        menuitemcondicoesdepagamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money_dollar.png"))); // NOI18N
        menuitemcondicoesdepagamento.setText("Condições de Pagamento");
        menuitemcondicoesdepagamento.setName("menuitemcondicoesdepagamento"); // NOI18N
        menuitemcondicoesdepagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemcondicoesdepagamentoActionPerformed(evt);
            }
        });
        menufinanceiro.add(menuitemcondicoesdepagamento);

        menuitembancos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/coins.png"))); // NOI18N
        menuitembancos.setText("Bancos");
        menuitembancos.setName("menuitembancos"); // NOI18N
        menuitembancos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitembancosActionPerformed(evt);
            }
        });
        menufinanceiro.add(menuitembancos);

        jMenuBar1.add(menufinanceiro);

        menucompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart.png"))); // NOI18N
        menucompras.setText("Compras");
        menucompras.setName("menucompras"); // NOI18N

        menuitemsolicitacaodecompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart_add.png"))); // NOI18N
        menuitemsolicitacaodecompras.setText("Solicitação de Compra");
        menuitemsolicitacaodecompras.setName("menuitemsolicitacaodecompras"); // NOI18N
        menuitemsolicitacaodecompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemsolicitacaodecomprasActionPerformed(evt);
            }
        });
        menucompras.add(menuitemsolicitacaodecompras);

        menuitemorcamentodecompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart_edit.png"))); // NOI18N
        menuitemorcamentodecompras.setText("Cotação de Compra");
        menuitemorcamentodecompras.setName("menuitemorcamentodecompras"); // NOI18N
        menucompras.add(menuitemorcamentodecompras);

        menuitempedidodecompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart_go.png"))); // NOI18N
        menuitempedidodecompras.setText("Pedido de Compra");
        menuitempedidodecompras.setName("menuitempedidodecompras"); // NOI18N
        menucompras.add(menuitempedidodecompras);

        menuiteminsumos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart_put.png"))); // NOI18N
        menuiteminsumos.setText("Insumos");
        menuiteminsumos.setName("menuiteminsumos"); // NOI18N
        menucompras.add(menuiteminsumos);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/basket.png"))); // NOI18N
        jMenuItem1.setText("Tipos de Produto");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menucompras.add(jMenuItem1);

        jMenuBar1.add(menucompras);

        menulogistica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/package_green.png"))); // NOI18N
        menulogistica.setText("Logística");
        menulogistica.setName("menulogistica"); // NOI18N

        menuitemcarros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/car.png"))); // NOI18N
        menuitemcarros.setText("Carros");
        menuitemcarros.setName("menuitemcarros"); // NOI18N
        menuitemcarros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemcarrosActionPerformed(evt);
            }
        });
        menulogistica.add(menuitemcarros);

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_go.png"))); // NOI18N
        jMenuItem6.setText("Rastreamento de Documentos");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        menulogistica.add(jMenuItem6);

        jMenuBar1.add(menulogistica);

        menuqualidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/award_star_bronze_3.png"))); // NOI18N
        menuqualidade.setText("Qualidade");
        menuqualidade.setName("menuqualidade"); // NOI18N

        menuiteminstrumentosmedicao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/compress.png"))); // NOI18N
        menuiteminstrumentosmedicao.setText("Instrumentos de Medição");
        menuiteminstrumentosmedicao.setName("menuiteminstrumentosmedicao"); // NOI18N
        menuiteminstrumentosmedicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuiteminstrumentosmedicaoActionPerformed(evt);
            }
        });
        menuqualidade.add(menuiteminstrumentosmedicao);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/lorry_error.png"))); // NOI18N
        jMenuItem3.setText("IQF");
        menuqualidade.add(jMenuItem3);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/chart_bar.png"))); // NOI18N
        jMenuItem4.setText("Medições");
        menuqualidade.add(jMenuItem4);

        jMenuBar1.add(menuqualidade);

        menuvendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/wrench.png"))); // NOI18N
        menuvendas.setText("Vendas");
        menuvendas.setName("menuvendas"); // NOI18N

        menuitemvendasorcamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/table_wrench.png"))); // NOI18N
        menuitemvendasorcamentos.setText("Cotações");
        menuitemvendasorcamentos.setName("menuitemvendasorcamentos"); // NOI18N
        menuitemvendasorcamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemvendasorcamentosActionPerformed(evt);
            }
        });
        menuvendas.add(menuitemvendasorcamentos);

        menuitemvendaspedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/table_wrench_go.png"))); // NOI18N
        menuitemvendaspedidos.setText("Pedidos");
        menuitemvendaspedidos.setName("menuitemvendaspedidos"); // NOI18N
        menuitemvendaspedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemvendaspedidosActionPerformed(evt);
            }
        });
        menuvendas.add(menuitemvendaspedidos);

        menuitemvendasops.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_wrench.png"))); // NOI18N
        menuitemvendasops.setText("OP's");
        menuitemvendasops.setName("menuitemvendasops"); // NOI18N
        menuitemvendasops.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemvendasopsActionPerformed(evt);
            }
        });
        menuvendas.add(menuitemvendasops);

        menuitemvendasprodutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cog_wrench.png"))); // NOI18N
        menuitemvendasprodutos.setText("Produtos");
        menuitemvendasprodutos.setName("menuitemvendasprodutos"); // NOI18N
        menuitemvendasprodutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemvendasprodutosActionPerformed(evt);
            }
        });
        menuvendas.add(menuitemvendasprodutos);

        menuitemvendasprocessos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_wrench.png"))); // NOI18N
        menuitemvendasprocessos.setText("Processos");
        menuitemvendasprocessos.setName("menuitemvendasprocessos"); // NOI18N
        menuvendas.add(menuitemvendasprocessos);

        menuitemvendasgrupodeprocessos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_cascade_wrench.png"))); // NOI18N
        menuitemvendasgrupodeprocessos.setText("Grupo de Processos");
        menuitemvendasgrupodeprocessos.setName("menuitemvendasgrupodeprocessos"); // NOI18N
        menuvendas.add(menuitemvendasgrupodeprocessos);

        jMenuBar1.add(menuvendas);

        menuservicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/wrench_orange.png"))); // NOI18N
        menuservicos.setText("Serviços");
        menuservicos.setName("menuservicos"); // NOI18N

        menuitemservicosorcamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/table_wrench_orange.png"))); // NOI18N
        menuitemservicosorcamentos.setText("Cotações");
        menuitemservicosorcamentos.setName("menuitemservicosorcamentos"); // NOI18N
        menuitemservicosorcamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemservicosorcamentosActionPerformed(evt);
            }
        });
        menuservicos.add(menuitemservicosorcamentos);

        menuitemservicospedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/table_wrench_orange_go.png"))); // NOI18N
        menuitemservicospedidos.setText("Pedidos");
        menuitemservicospedidos.setName("menuitemservicospedidos"); // NOI18N
        menuitemservicospedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemservicospedidosActionPerformed(evt);
            }
        });
        menuservicos.add(menuitemservicospedidos);

        menuitemservicososs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_wrench_orange.png"))); // NOI18N
        menuitemservicososs.setText("OS's");
        menuitemservicososs.setName("menuitemservicososs"); // NOI18N
        menuitemservicososs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemservicosossActionPerformed(evt);
            }
        });
        menuservicos.add(menuitemservicososs);

        menuitemservicosprodutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cog_wrench_orange.png"))); // NOI18N
        menuitemservicosprodutos.setText("Produtos");
        menuitemservicosprodutos.setName("menuitemservicosprodutos"); // NOI18N
        menuitemservicosprodutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemservicosprodutosActionPerformed(evt);
            }
        });
        menuservicos.add(menuitemservicosprodutos);

        menuitemservicosproccessos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_wrench_orange.png"))); // NOI18N
        menuitemservicosproccessos.setText("Processos");
        menuitemservicosproccessos.setName("menuitemservicosproccessos"); // NOI18N
        menuitemservicosproccessos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemservicosproccessosActionPerformed(evt);
            }
        });
        menuservicos.add(menuitemservicosproccessos);

        menuitemservicosgrupodeprocessos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_cascade_wrench_orange.png"))); // NOI18N
        menuitemservicosgrupodeprocessos.setText("Grupo de Processos");
        menuitemservicosgrupodeprocessos.setName("menuitemservicosgrupodeprocessos"); // NOI18N
        menuitemservicosgrupodeprocessos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemservicosgrupodeprocessosActionPerformed(evt);
            }
        });
        menuservicos.add(menuitemservicosgrupodeprocessos);

        jMenuBar1.add(menuservicos);

        menuconfiguracoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plugin.png"))); // NOI18N
        menuconfiguracoes.setText("Configurações");
        menuconfiguracoes.setName("menuconfiguracoes"); // NOI18N

        menuitemmenus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_cascade.png"))); // NOI18N
        menuitemmenus.setText("Menus");
        menuitemmenus.setName("menuitemmenus"); // NOI18N
        menuitemmenus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemmenusActionPerformed(evt);
            }
        });
        menuconfiguracoes.add(menuitemmenus);

        jMenuBar1.add(menuconfiguracoes);

        menuajuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/information.png"))); // NOI18N
        menuajuda.setText("Ajuda");
        menuajuda.setName("menuajuda"); // NOI18N

        menuitemmike.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        menuitemmike.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/lightbulb.png"))); // NOI18N
        menuitemmike.setText("M.I.K.E.");
        menuitemmike.setName("menuitemmike"); // NOI18N
        menuitemmike.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemmikeActionPerformed(evt);
            }
        });
        menuajuda.add(menuitemmike);

        menuitemsobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_text.png"))); // NOI18N
        menuitemsobre.setText("Sobre");
        menuitemsobre.setName("menuitemsobre"); // NOI18N
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

    private void menuitemsairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemsairActionPerformed
        this.dispose();
    }//GEN-LAST:event_menuitemsairActionPerformed

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

    private void menuitememailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitememailActionPerformed
        // TODO add your handling code here:
        Email email = new Email();
        jDesktopPane1.add(email);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = email.getSize();
        email.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        email.setVisible(true);
    }//GEN-LAST:event_menuitememailActionPerformed

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
        ComprasSolicitacao p = new ComprasSolicitacao();
        jDesktopPane1.add(p);
        Dimension jif = p.getSize();
        Dimension desk = jDesktopPane1.getSize();
        p.setLocation((desk.width - jif.width) / 2, (desk.height - jif.height) / 2);
        p.setVisible(true);
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

    private void menuitemnotasfiscaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemnotasfiscaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuitemnotasfiscaisActionPerformed

    private void menuitemnaturezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemnaturezaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuitemnaturezaActionPerformed

    private void btntestemenusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntestemenusActionPerformed
//        int menu = TelaPrincipal.jMenuBar1.getMenuCount();
//        for (int i = 0; i < menu; i++) {
//            JOptionPane.showMessageDialog(rootPane, TelaPrincipal.jMenuBar1.getMenu(i).getName());
//            int sub = TelaPrincipal.jMenuBar1.getMenu(i).getItemCount();
//            for (int j = 0; j < sub; j++) {
//                JOptionPane.showMessageDialog(rootPane, TelaPrincipal.jMenuBar1.getMenu(i).getItem(j).getName());
//            }
//        }
////////        Menus_1 p = new Menus_1();
////////        jDesktopPane1.add(p);
////////        Dimension desktopsize = jDesktopPane1.getSize();
////////        Dimension jinternalframesize = p.getSize();
////////        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
////////        p.setVisible(true);
    }//GEN-LAST:event_btntestemenusActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        TiposProduto p = new TiposProduto();
        jDesktopPane1.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void menuitemvendasprodutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemvendasprodutosActionPerformed
        VendasMateriais p = new VendasMateriais();
        jDesktopPane1.add(p);
//        Dimension desktopsize = jDesktopPane1.getSize();
//        Dimension jinternalframesize = p.getSize();
//        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        try {
            p.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        p.setVisible(true);
    }//GEN-LAST:event_menuitemvendasprodutosActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        RastreamentoDocumentos p = new RastreamentoDocumentos();
        jDesktopPane1.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
        p.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void menuitemcategoriaprecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemcategoriaprecoActionPerformed
        try {
            CategoriaDePreco p = new CategoriaDePreco();
            jDesktopPane1.add(p);
            p.setMaximum(true);
            p.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao abrir! Contate suporte!");
        }
    }//GEN-LAST:event_menuitemcategoriaprecoActionPerformed

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
    private javax.swing.JButton btntestemenus;
    public static javax.swing.JDesktopPane jDesktopPane1;
    public static javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel lblnome;
    public static javax.swing.JMenu menuadministracao;
    public static javax.swing.JMenu menuajuda;
    public static javax.swing.JMenu menuarquivo;
    public static javax.swing.JMenu menucomercial;
    public static javax.swing.JMenu menucompras;
    public static javax.swing.JMenu menuconfiguracoes;
    public static javax.swing.JMenu menufinanceiro;
    public static javax.swing.JMenu menufiscal;
    public static javax.swing.JMenuItem menuitembancos;
    public static javax.swing.JMenuItem menuitemcarros;
    private javax.swing.JMenuItem menuitemcategoriapreco;
    public static javax.swing.JMenuItem menuitemclientes;
    public static javax.swing.JMenuItem menuitemcondicoesdepagamento;
    public static javax.swing.JMenuItem menuitemcontasapagar;
    public static javax.swing.JMenuItem menuitemcontasareceber;
    public static javax.swing.JMenuItem menuitememail;
    public static javax.swing.JMenuItem menuitemfornecedores;
    public static javax.swing.JMenuItem menuitemgrupodeclientes;
    public static javax.swing.JMenuItem menuitemgrupodeusuarios;
    public static javax.swing.JMenuItem menuiteminstrumentosmedicao;
    public static javax.swing.JMenuItem menuiteminsumos;
    public static javax.swing.JMenuItem menuitemmenus;
    public static javax.swing.JMenuItem menuitemmike;
    public static javax.swing.JMenuItem menuitemnatureza;
    public static javax.swing.JMenuItem menuitemnotasfiscais;
    public static javax.swing.JMenuItem menuitemorcamentodecompras;
    public static javax.swing.JMenuItem menuitempedidodecompras;
    public static javax.swing.JMenuItem menuitemregioesdeatuacao;
    public static javax.swing.JMenuItem menuitemrepresentantes;
    public static javax.swing.JMenuItem menuitemsair;
    public static javax.swing.JMenuItem menuitemservicosgrupodeprocessos;
    public static javax.swing.JMenuItem menuitemservicosorcamentos;
    public static javax.swing.JMenuItem menuitemservicososs;
    public static javax.swing.JMenuItem menuitemservicospedidos;
    public static javax.swing.JMenuItem menuitemservicosproccessos;
    public static javax.swing.JMenuItem menuitemservicosprodutos;
    public static javax.swing.JMenuItem menuitemsobre;
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
