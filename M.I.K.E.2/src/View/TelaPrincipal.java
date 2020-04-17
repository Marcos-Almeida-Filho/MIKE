/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

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
import Methods.Telas;
import View.RH.SolicitacaoHoraExtra;
import View.administracao.GrupoDeUsuarios;
import View.administracao.Regioes;
import View.administracao.Representantes;
import View.comercial.CategoriaDePreco;
import View.comercial.F_UP;
import View.comercial.Fornecedores;
import View.compras.ComprasSolicitacao;
import View.compras.Insumos;
import View.compras.TiposInsumo;
import View.configuracoes.Menus;
import View.financeiro.Bancos;
import View.financeiro.CondicoesDePagamento;
import View.financeiro.ContasPagar;
import View.financeiro.ContasReceber;
import View.logistica.RastreamentoDocumentos;
import View.qualidade.InstrumentosMedicao;
import View.servicos.GrupoDeProcessosServico;
import View.servicos.OS;
import View.servicos.ServicoMateriais;
import View.servicos.PedidoServico;
import View.servicos.ProcessosServico;
import View.TI.Senhas;
import View.arquivo.ProgramacaoMIKE;
import View.comercial.PlanejamentoFaturamento;
import View.financeiro.Cartoes;
import View.financeiro.Extratos;
import View.fiscal.NotasFiscais;
import View.servicos.OS1;
import View.vendas.VendasMateriais;
import View.vendas.ProcessosVendas;
import java.awt.Toolkit;
import java.net.URL;
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
        initialize();
    }

    public void initialize() {
        URL resource = TelaPrincipal.class.getResource("/Images/logoMIKE.png");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(resource));
    }

    public void menus() {
        //DAO para pesquisar
        UsuariosDAO ud = new UsuariosDAO();

        //Retornar nível do acesso
        ud.checknivel(Session.login).forEach((UsuariosBean ub) -> {
            //Setar nível do acesso
            Session.nivel = ub.getNivel();
        });
        GrupoDeUsuariosDAO gud = new GrupoDeUsuariosDAO();
//        TelaPrincipal.menuarquivo.setVisible(false);
        gud.getmenus(Session.nivel).forEach(gub -> {
            TelaPrincipal.menuadministracao.setVisible(gub.isMenuadministracao());
            TelaPrincipal.menuitemusuarios.setVisible(gub.isSubmenuusuarios());
            TelaPrincipal.menuitemgrupodeusuarios.setVisible(gub.isSubmenugrupodeusuarios());
            TelaPrincipal.menuitemrepresentantes.setVisible(gub.isSubmenurepresentantes());
            TelaPrincipal.menuitemregioesdeatuacao.setVisible(gub.isSubmenuregioesdeatuacao());
            TelaPrincipal.menucomercial.setVisible(gub.isMenucomercial());
            TelaPrincipal.menuitemclientes.setVisible(gub.isSubmenuclientes());
            TelaPrincipal.menuitemgrupodeclientes.setVisible(gub.isSubmenugrupodeclientes());
            TelaPrincipal.menuitemfornecedores.setVisible(gub.isSubmenufornecedores());
            TelaPrincipal.menufinanceiro.setVisible(gub.isMenufinanceiro());
            TelaPrincipal.menuitemcontasareceber.setVisible(gub.isSubmenucontasareceber());
            TelaPrincipal.menuitemcontasapagar.setVisible(gub.isSubmenucontasapagar());
            TelaPrincipal.menuitemcondicoesdepagamento.setVisible(gub.isSubmenucondicoesdepagamento());
            TelaPrincipal.menuitembancos.setVisible(gub.isSubmenubancos());
            TelaPrincipal.menucompras.setVisible(gub.isMenucompras());
            TelaPrincipal.menuitemsolicitacaodecompras.setVisible(gub.isSubmenusolicitacaodecompras());
            TelaPrincipal.menuitemorcamentodecompras.setVisible(gub.isSubmenuorcamentodecompras());
            TelaPrincipal.menuitempedidodecompras.setVisible(gub.isSubmenupedidodecompras());
            TelaPrincipal.menuiteminsumos.setVisible(gub.isSubmenuinsumos());
            TelaPrincipal.menulogistica.setVisible(gub.isMenulogistica());
            TelaPrincipal.menuitemcarros.setVisible(gub.isSubmenucarros());
            TelaPrincipal.menuqualidade.setVisible(gub.isMenuqualidade());
            TelaPrincipal.menuiteminstrumentosmedicao.setVisible(gub.isSubmenuinstrumentosdemedicao());
            TelaPrincipal.menuvendas.setVisible(gub.isMenuvendas());
            TelaPrincipal.menuitemvendasorcamentos.setVisible(gub.isSubmenuorcamentosvenda());
            TelaPrincipal.menuitemvendaspedidos.setVisible(gub.isSubmenupedidosvenda());
            TelaPrincipal.menuitemvendasops.setVisible(gub.isSubmenuops());
            TelaPrincipal.menuitemvendasprodutos.setVisible(gub.isSubmenuprodutosvenda());
            TelaPrincipal.menuitemvendasprocessos.setVisible(gub.isSubmenuprocessosvenda());
            TelaPrincipal.menuservicos.setVisible(gub.isMenuservicos());
            TelaPrincipal.menuitemservicosorcamentos.setVisible(gub.isSubmenuorcamentosservico());
            TelaPrincipal.menuitemservicospedidos.setVisible(gub.isSubmenupedidosservico());
            TelaPrincipal.menuitemservicososs.setVisible(gub.isSubmenuoss());
            TelaPrincipal.menuitemservicosprodutos.setVisible(gub.isSubmenuprodutosservico());
            TelaPrincipal.menuitemservicosproccessos.setVisible(gub.isSubmenuprocessosservico());
            TelaPrincipal.menuitemservicosgrupodeprocessos.setVisible(gub.isSubmenugrupodeprocessosservico());
            TelaPrincipal.menuconfiguracoes.setVisible(gub.isMenuconfiguracoes());
            TelaPrincipal.menuitemmenus.setVisible(gub.isSubmenumenus());
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

        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        lblnome = new javax.swing.JLabel();
        btntestemenus = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuarquivo = new javax.swing.JMenu();
        menuitememail = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
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
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        menufinanceiro = new javax.swing.JMenu();
        menuitemcontasareceber = new javax.swing.JMenuItem();
        menuitemcontasapagar = new javax.swing.JMenuItem();
        menuitemcondicoesdepagamento = new javax.swing.JMenuItem();
        menuitembancos = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        menucompras = new javax.swing.JMenu();
        menuitemsolicitacaodecompras = new javax.swing.JMenuItem();
        menuitemorcamentodecompras = new javax.swing.JMenuItem();
        menuitempedidodecompras = new javax.swing.JMenuItem();
        menuiteminsumos = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menulogistica = new javax.swing.JMenu();
        menuitemcarros = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        menuqualidade = new javax.swing.JMenu();
        menuiteminstrumentosmedicao = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        menuvendas = new javax.swing.JMenu();
        menuitemvendasorcamentos = new javax.swing.JMenuItem();
        menuitemvendaspedidos = new javax.swing.JMenuItem();
        menuitemvendasops = new javax.swing.JMenuItem();
        menuitemvendasprodutos = new javax.swing.JMenuItem();
        menuitemvendasprocessos = new javax.swing.JMenuItem();
        menuservicos = new javax.swing.JMenu();
        menuitemservicosorcamentos = new javax.swing.JMenuItem();
        menuitemservicospedidos = new javax.swing.JMenuItem();
        menuitemservicososs = new javax.swing.JMenuItem();
        menuitemservicosprodutos = new javax.swing.JMenuItem();
        menuitemservicosproccessos = new javax.swing.JMenuItem();
        menuitemservicosgrupodeprocessos = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
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
        jDesktopPane1.setName("TelaPrincipal"); // NOI18N

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
                .addContainerGap(540, Short.MAX_VALUE)
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
                .addContainerGap(267, Short.MAX_VALUE))
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

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/text_list_bullets.png"))); // NOI18N
        jMenuItem5.setText("Protocolos de Atendimento");
        menuarquivo.add(jMenuItem5);

        jMenuItem17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bricks.png"))); // NOI18N
        jMenuItem17.setText("Programação MIKE");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        menuarquivo.add(jMenuItem17);

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

        menuitemcategoriapreco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/chart_line.png"))); // NOI18N
        menuitemcategoriapreco.setText("Categorias de Preço");
        menuitemcategoriapreco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemcategoriaprecoActionPerformed(evt);
            }
        });
        menucomercial.add(menuitemcategoriapreco);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow_branch.png"))); // NOI18N
        jMenuItem2.setText("Follow Up");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menucomercial.add(jMenuItem2);

        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/calendar_edit.png"))); // NOI18N
        jMenuItem15.setText("Planejamento De Faturamento");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        menucomercial.add(jMenuItem15);

        jMenuBar1.add(menucomercial);

        menufinanceiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money.png"))); // NOI18N
        menufinanceiro.setText("Financeiro");
        menufinanceiro.setName("menufinanceiro"); // NOI18N

        menuitemcontasareceber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money_add.png"))); // NOI18N
        menuitemcontasareceber.setText("Contas a Receber");
        menuitemcontasareceber.setName("menuitemcontasareceber"); // NOI18N
        menuitemcontasareceber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemcontasareceberActionPerformed(evt);
            }
        });
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

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_copy.png"))); // NOI18N
        jMenuItem12.setText("Extratos");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        menufinanceiro.add(jMenuItem12);

        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_code.png"))); // NOI18N
        jMenuItem13.setText("Conciliação Bancária");
        menufinanceiro.add(jMenuItem13);

        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/creditcards.png"))); // NOI18N
        jMenuItem14.setText("Cartões");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        menufinanceiro.add(jMenuItem14);

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
        menuiteminsumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuiteminsumosActionPerformed(evt);
            }
        });
        menucompras.add(menuiteminsumos);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/basket.png"))); // NOI18N
        jMenuItem1.setText("Tipos de Insumo");
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

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tag_blue.png"))); // NOI18N
        jMenuItem8.setText("Cadastro de Unidades");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        menulogistica.add(jMenuItem8);

        jMenuBar1.add(menulogistica);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/computer.png"))); // NOI18N
        jMenu1.setText("TI");

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/key.png"))); // NOI18N
        jMenuItem7.setText("Senhas");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuBar1.add(jMenu1);

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
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        menuqualidade.add(jMenuItem4);

        jMenuBar1.add(menuqualidade);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/folder_user.png"))); // NOI18N
        jMenu2.setText("RH");

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_copy.png"))); // NOI18N
        jMenu3.setText("Formulários");

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/hourglass_add.png"))); // NOI18N
        jMenuItem9.setText("Solicitação de Hora Extra");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/hourglass_delete.png"))); // NOI18N
        jMenuItem10.setText("Atraso/Saída Antecipada");
        jMenu3.add(jMenuItem10);

        jMenu2.add(jMenu3);

        jMenuBar1.add(jMenu2);

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
        menuitemvendasprocessos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemvendasprocessosActionPerformed(evt);
            }
        });
        menuvendas.add(menuitemvendasprocessos);

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

        jMenuItem16.setText("jMenuItem16");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        menuservicos.add(jMenuItem16);

        jMenuBar1.add(menuservicos);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/link.png"))); // NOI18N
        jMenu4.setText("Terceiros");

        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_link.png"))); // NOI18N
        jMenuItem11.setText("OT's");
        jMenu4.add(jMenuItem11);

        jMenuBar1.add(jMenu4);

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
        Telas.AparecerTela(telauser);
    }//GEN-LAST:event_menuitemusuariosActionPerformed

    private void menuitemvendasopsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemvendasopsActionPerformed
        OPs ops = new OPs();
        Telas.AparecerTelaAumentada(ops);
    }//GEN-LAST:event_menuitemvendasopsActionPerformed

    private void menuitememailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitememailActionPerformed
        Email email = new Email();
        Telas.AparecerTela(email);
    }//GEN-LAST:event_menuitememailActionPerformed

    private void menuitemvendaspedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemvendaspedidosActionPerformed
        EmBreve.EmBreve();
    }//GEN-LAST:event_menuitemvendaspedidosActionPerformed

    private void menuitemvendasorcamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemvendasorcamentosActionPerformed
        EmBreve.EmBreve();
    }//GEN-LAST:event_menuitemvendasorcamentosActionPerformed

    private void menuitemsobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemsobreActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Sistema M.I.K.E®\n\nCriado por Marcos Filho\nTodos os direitos reservados");
    }//GEN-LAST:event_menuitemsobreActionPerformed

    private void menuitemmikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemmikeActionPerformed
        Tarefas t = new Tarefas();
        t.setVisible(true);
    }//GEN-LAST:event_menuitemmikeActionPerformed

    private void menuitemservicosorcamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemservicosorcamentosActionPerformed
        CotacaoServico tela = new CotacaoServico();
        Telas.AparecerTelaAumentada(tela);
    }//GEN-LAST:event_menuitemservicosorcamentosActionPerformed

    private void menuitemservicospedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemservicospedidosActionPerformed
        PedidoServico p = new PedidoServico();
        Telas.AparecerTelaAumentada(p);
    }//GEN-LAST:event_menuitemservicospedidosActionPerformed

    private void menuitemservicosossActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemservicosossActionPerformed
        OS os = new OS();
        Telas.AparecerTelaAumentada(os);
    }//GEN-LAST:event_menuitemservicosossActionPerformed

    private void menuitemclientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemclientesActionPerformed
        Clientes telaclientes = new Clientes();
        Telas.AparecerTelaAumentada(telaclientes);
    }//GEN-LAST:event_menuitemclientesActionPerformed

    private void menuitemservicosprodutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemservicosprodutosActionPerformed
        ServicoMateriais tela = new ServicoMateriais();
        Telas.AparecerTelaAumentada(tela);
    }//GEN-LAST:event_menuitemservicosprodutosActionPerformed

    private void menuitemgrupodeusuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemgrupodeusuariosActionPerformed
        GrupoDeUsuarios gu = new GrupoDeUsuarios();
        Telas.AparecerTela(gu);
    }//GEN-LAST:event_menuitemgrupodeusuariosActionPerformed

    private void menuitemmenusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemmenusActionPerformed
        Menus m = new Menus();
        Telas.AparecerTela(m);
    }//GEN-LAST:event_menuitemmenusActionPerformed

    private void menuitemrepresentantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemrepresentantesActionPerformed
        Representantes r = new Representantes();
        Telas.AparecerTela(r);
    }//GEN-LAST:event_menuitemrepresentantesActionPerformed

    private void menuitemregioesdeatuacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemregioesdeatuacaoActionPerformed
        Regioes r = new Regioes();
        Telas.AparecerTela(r);
    }//GEN-LAST:event_menuitemregioesdeatuacaoActionPerformed

    private void menuitemcondicoesdepagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemcondicoesdepagamentoActionPerformed
        CondicoesDePagamento cp = new CondicoesDePagamento();
        Telas.AparecerTela(cp);
    }//GEN-LAST:event_menuitemcondicoesdepagamentoActionPerformed

    private void menuitemcontasapagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemcontasapagarActionPerformed
        ContasPagar c = new ContasPagar();
        Telas.AparecerTelaAumentada(c);
    }//GEN-LAST:event_menuitemcontasapagarActionPerformed

    private void menuitembancosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitembancosActionPerformed
        Bancos b = new Bancos();
        Telas.AparecerTelaAumentada(b);
    }//GEN-LAST:event_menuitembancosActionPerformed

    private void menuitemfornecedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemfornecedoresActionPerformed
        Fornecedores f = new Fornecedores();
        Telas.AparecerTela(f);
    }//GEN-LAST:event_menuitemfornecedoresActionPerformed

    private void menuitemservicosproccessosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemservicosproccessosActionPerformed
        ProcessosServico ps = new ProcessosServico();
        Telas.AparecerTela(ps);
    }//GEN-LAST:event_menuitemservicosproccessosActionPerformed

    private void menuitemservicosgrupodeprocessosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemservicosgrupodeprocessosActionPerformed
        GrupoDeProcessosServico gps = new GrupoDeProcessosServico();
        Telas.AparecerTela(gps);
    }//GEN-LAST:event_menuitemservicosgrupodeprocessosActionPerformed

    private void menuitemsolicitacaodecomprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemsolicitacaodecomprasActionPerformed
        ComprasSolicitacao p = new ComprasSolicitacao();
        Telas.AparecerTelaAumentada(p);
    }//GEN-LAST:event_menuitemsolicitacaodecomprasActionPerformed

    private void menuiteminstrumentosmedicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuiteminstrumentosmedicaoActionPerformed
        InstrumentosMedicao p = new InstrumentosMedicao();
        Telas.AparecerTela(p);
    }//GEN-LAST:event_menuiteminstrumentosmedicaoActionPerformed

    private void menuitemcarrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemcarrosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuitemcarrosActionPerformed

    private void menuitemnotasfiscaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemnotasfiscaisActionPerformed
        NotasFiscais nf = new NotasFiscais();
        Telas.AparecerTelaAumentada(nf);
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
        TiposInsumo p = new TiposInsumo();
        Telas.AparecerTela(p);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void menuitemvendasprodutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemvendasprodutosActionPerformed
        VendasMateriais p = new VendasMateriais();
        Telas.AparecerTelaAumentada(p);
    }//GEN-LAST:event_menuitemvendasprodutosActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        RastreamentoDocumentos p = new RastreamentoDocumentos();
        Telas.AparecerTela(p);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void menuitemcategoriaprecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemcategoriaprecoActionPerformed
        CategoriaDePreco p = new CategoriaDePreco();
        Telas.AparecerTelaAumentada(p);
    }//GEN-LAST:event_menuitemcategoriaprecoActionPerformed

    private void menuitemcontasareceberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemcontasareceberActionPerformed
        ContasReceber p = new ContasReceber();
        Telas.AparecerTelaAumentada(p);
    }//GEN-LAST:event_menuitemcontasareceberActionPerformed

    private void menuiteminsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuiteminsumosActionPerformed
        Insumos i = new Insumos();
        Telas.AparecerTelaAumentada(i);
    }//GEN-LAST:event_menuiteminsumosActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        F_UP f = new F_UP();
        Telas.AparecerTelaAumentada(f);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        Senhas s = new Senhas();
        Telas.AparecerTela(s);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        SolicitacaoHoraExtra she = new SolicitacaoHoraExtra();
        Telas.AparecerTelaAumentada(she);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        Extratos e = new Extratos();
        Telas.AparecerTelaAumentada(e);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void menuitemvendasprocessosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemvendasprocessosActionPerformed
        ProcessosVendas pv = new ProcessosVendas();
        Telas.AparecerTela(pv);
    }//GEN-LAST:event_menuitemvendasprocessosActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed

    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        Cartoes c = new Cartoes();
        Telas.AparecerTela(c);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        PlanejamentoFaturamento pf = new PlanejamentoFaturamento();
        Telas.AparecerTelaAumentada(pf);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        if (Session.nivel.equals("Administrador")) {
            OS1 os1 = new OS1();
            Telas.AparecerTelaAumentada(os1);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        ProgramacaoMIKE pm = new ProgramacaoMIKE();
        Telas.AparecerTelaAumentada(pm);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btntestemenus;
    public static javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    public static javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
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
