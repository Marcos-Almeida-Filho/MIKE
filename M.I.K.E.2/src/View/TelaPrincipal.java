/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Bean.UsuariosBean;
import Connection.Session;
import DAO.GrupoDeUsuariosPermDAO;
import DAO.UsuariosDAO;
import View.comercial.Clientes;
import View.servicos.CotacaoServico;
import View.vendas.OP;
import View.arquivo.Email;
import Methods.Telas;
import View.Geral.MenusAbrir;
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
import View.TI.Tickets;
import View.administracao.Usuarios;
import View.arquivo.ProgramacaoMIKE;
import View.comercial.FaturamentoDiario;
import View.comercial.PlanejamentoFaturamento;
import View.compras.ComprasCotacao;
import View.configuracoes.Mensagens;
import View.configuracoes.Menus;
import View.financeiro.Cartoes;
import View.financeiro.Extratos;
import View.fiscal.NotasFiscais;
import View.logistica.LocaisArmazenagem;
import View.logistica.Unidades;
import View.qualidade.Medicoes;
import View.vendas.CotacaoVenda;
import View.vendas.PedidoVenda;
import View.vendas.ProcessosVendas;
import View.vendas.VM1;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public final class TelaPrincipal extends javax.swing.JFrame {

    static GrupoDeUsuariosPermDAO gupd = new GrupoDeUsuariosPermDAO();

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
        this.setExtendedState(TelaPrincipal.MAXIMIZED_BOTH);
        //menus();
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
//        GrupoDeUsuariosDAO gud = new GrupoDeUsuariosDAO();
////        TelaPrincipal.menuarquivo.setVisible(false);
//        gud.getmenus(Session.nivel).forEach(gub -> {
//            TelaPrincipal.menu_adm.setVisible(gub.isMenuadministracao());
//            TelaPrincipal.item_usuarios.setVisible(gub.isSubmenuusuarios());
//            TelaPrincipal.item_grupo_usuarios.setVisible(gub.isSubmenugrupodeusuarios());
//            TelaPrincipal.item_representantes.setVisible(gub.isSubmenurepresentantes());
//            TelaPrincipal.item_regioes_atuacao.setVisible(gub.isSubmenuregioesdeatuacao());
//            TelaPrincipal.menu_comercial.setVisible(gub.isMenucomercial());
//            TelaPrincipal.item_clientes.setVisible(gub.isSubmenuclientes());
//            TelaPrincipal.item_grupo_clientes.setVisible(gub.isSubmenugrupodeclientes());
//            TelaPrincipal.item_fornecedores.setVisible(gub.isSubmenufornecedores());
//            TelaPrincipal.menu_financeiro.setVisible(gub.isMenufinanceiro());
//            TelaPrincipal.item_car.setVisible(gub.isSubmenucontasareceber());
//            TelaPrincipal.item_cap.setVisible(gub.isSubmenucontasapagar());
//            TelaPrincipal.item_condicoes_pagamento.setVisible(gub.isSubmenucondicoesdepagamento());
//            TelaPrincipal.item_bancos.setVisible(gub.isSubmenubancos());
//            TelaPrincipal.menu_compras.setVisible(gub.isMenucompras());
//            TelaPrincipal.item_solicitacao_compras.setVisible(gub.isSubmenusolicitacaodecompras());
//            TelaPrincipal.item_orcamento_compras.setVisible(gub.isSubmenuorcamentodecompras());
//            TelaPrincipal.item_pedido_compras.setVisible(gub.isSubmenupedidodecompras());
//            TelaPrincipal.item_insumos_compras.setVisible(gub.isSubmenuinsumos());
//            TelaPrincipal.menu_logistica.setVisible(gub.isMenulogistica());
//            TelaPrincipal.item_carros.setVisible(gub.isSubmenucarros());
//            TelaPrincipal.menu_qualidade.setVisible(gub.isMenuqualidade());
//            TelaPrincipal.item_inst_med.setVisible(gub.isSubmenuinstrumentosdemedicao());
//            TelaPrincipal.menu_vendas.setVisible(gub.isMenuvendas());
//            TelaPrincipal.item_vendas_cotacoes.setVisible(gub.isSubmenuorcamentosvenda());
//            TelaPrincipal.item_vendas_pedido.setVisible(gub.isSubmenupedidosvenda());
//            TelaPrincipal.item_ops.setVisible(gub.isSubmenuops());
//            TelaPrincipal.item_vendas_produtos.setVisible(gub.isSubmenuprodutosvenda());
//            TelaPrincipal.item_vendas_processos.setVisible(gub.isSubmenuprocessosvenda());
//            TelaPrincipal.menu_servicos.setVisible(gub.isMenuservicos());
//            TelaPrincipal.item_servicos_cotacoes.setVisible(gub.isSubmenuorcamentosservico());
//            TelaPrincipal.item_servicos_pedido.setVisible(gub.isSubmenupedidosservico());
//            TelaPrincipal.item_oss.setVisible(gub.isSubmenuoss());
//            TelaPrincipal.item_servicos_produtos.setVisible(gub.isSubmenuprodutosservico());
//            TelaPrincipal.item_servicos_processo.setVisible(gub.isSubmenuprocessosservico());
//            TelaPrincipal.item_servicos_grupo_processo.setVisible(gub.isSubmenugrupodeprocessosservico());
//            TelaPrincipal.menu_configuracoes.setVisible(gub.isMenuconfiguracoes());
//            TelaPrincipal.item_menus.setVisible(gub.isSubmenumenus());
//        });
        int menu = TelaPrincipal.jMenuBar1.getMenuCount();
        for (int i = 0; i < menu; i++) {
            String menuname = TelaPrincipal.jMenuBar1.getMenu(i).getName();
            TelaPrincipal.jMenuBar1.getMenu(i).setVisible(gupd.readPerm(Session.idnivel, menuname));
            int sub = TelaPrincipal.jMenuBar1.getMenu(i).getItemCount();
            for (int j = 0; j < sub; j++) {
                String subname = TelaPrincipal.jMenuBar1.getMenu(i).getItem(j).getName();
                TelaPrincipal.jMenuBar1.getMenu(i).getItem(j).setVisible(gupd.readPerm(Session.idnivel, subname));
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

        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        lblnome = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu_arquivo = new javax.swing.JMenu();
        item_email = new javax.swing.JMenuItem();
        item_protocolos = new javax.swing.JMenuItem();
        item_prog_mike = new javax.swing.JMenuItem();
        item_sair = new javax.swing.JMenuItem();
        menu_adm = new javax.swing.JMenu();
        item_usuarios = new javax.swing.JMenuItem();
        item_grupo_usuarios = new javax.swing.JMenuItem();
        item_representantes = new javax.swing.JMenuItem();
        item_regioes_atuacao = new javax.swing.JMenuItem();
        menu_fiscal = new javax.swing.JMenu();
        item_nf = new javax.swing.JMenuItem();
        item_natureza = new javax.swing.JMenuItem();
        menu_comercial = new javax.swing.JMenu();
        item_clientes = new javax.swing.JMenuItem();
        item_grupo_clientes = new javax.swing.JMenuItem();
        item_fornecedores = new javax.swing.JMenuItem();
        item_categoria_preco = new javax.swing.JMenuItem();
        item_f_up = new javax.swing.JMenuItem();
        item_planejamento = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menu_financeiro = new javax.swing.JMenu();
        item_car = new javax.swing.JMenuItem();
        item_cap = new javax.swing.JMenuItem();
        item_condicoes_pagamento = new javax.swing.JMenuItem();
        item_bancos = new javax.swing.JMenuItem();
        item_extratos = new javax.swing.JMenuItem();
        item_conciliacao = new javax.swing.JMenuItem();
        item_cartoes = new javax.swing.JMenuItem();
        menu_compras = new javax.swing.JMenu();
        item_solicitacao_compras = new javax.swing.JMenuItem();
        item_orcamento_compras = new javax.swing.JMenuItem();
        item_pedido_compras = new javax.swing.JMenuItem();
        item_insumos_compras = new javax.swing.JMenuItem();
        item_tipo_insumos = new javax.swing.JMenuItem();
        menu_logistica = new javax.swing.JMenu();
        item_carros = new javax.swing.JMenuItem();
        item_rastreamento = new javax.swing.JMenuItem();
        item_unidades = new javax.swing.JMenuItem();
        item_locais = new javax.swing.JMenuItem();
        menu_ti = new javax.swing.JMenu();
        item_senhas = new javax.swing.JMenuItem();
        item_tickets = new javax.swing.JMenuItem();
        menu_qualidade = new javax.swing.JMenu();
        item_inst_med = new javax.swing.JMenuItem();
        item_iqf = new javax.swing.JMenuItem();
        item_medicoes = new javax.swing.JMenuItem();
        menu_rh = new javax.swing.JMenu();
        item_form = new javax.swing.JMenu();
        item_solic_hora_extra = new javax.swing.JMenuItem();
        item_atraso = new javax.swing.JMenuItem();
        menu_vendas = new javax.swing.JMenu();
        item_vendas_cotacoes = new javax.swing.JMenuItem();
        item_vendas_pedido = new javax.swing.JMenuItem();
        item_ops = new javax.swing.JMenuItem();
        item_vendas_produtos = new javax.swing.JMenuItem();
        item_vendas_processos = new javax.swing.JMenuItem();
        menu_servicos = new javax.swing.JMenu();
        item_servicos_cotacoes = new javax.swing.JMenuItem();
        item_servicos_pedido = new javax.swing.JMenuItem();
        item_oss = new javax.swing.JMenuItem();
        item_servicos_produtos = new javax.swing.JMenuItem();
        item_servicos_processo = new javax.swing.JMenuItem();
        item_servicos_grupo_processo = new javax.swing.JMenuItem();
        menu_terceiros = new javax.swing.JMenu();
        item_ots = new javax.swing.JMenuItem();
        menu_configuracoes = new javax.swing.JMenu();
        item_menus = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menu_ajuda = new javax.swing.JMenu();
        item_trocar_senha = new javax.swing.JMenuItem();
        item_mike = new javax.swing.JMenuItem();
        item_sobre = new javax.swing.JMenuItem();

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

        jDesktopPane1.setLayer(lblnome, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblnome, javax.swing.GroupLayout.DEFAULT_SIZE, 1161, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblnome)
                .addContainerGap(301, Short.MAX_VALUE))
        );

        menu_arquivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page.png"))); // NOI18N
        menu_arquivo.setText("Arquivo");
        menu_arquivo.setName("menu_arquivo"); // NOI18N

        item_email.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/email.png"))); // NOI18N
        item_email.setText("Email");
        item_email.setName("item_email"); // NOI18N
        item_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_emailActionPerformed(evt);
            }
        });
        menu_arquivo.add(item_email);

        item_protocolos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/text_list_bullets.png"))); // NOI18N
        item_protocolos.setText("Protocolos de Atendimento");
        item_protocolos.setName("item_protocolos"); // NOI18N
        item_protocolos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_protocolosActionPerformed(evt);
            }
        });
        menu_arquivo.add(item_protocolos);

        item_prog_mike.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bricks.png"))); // NOI18N
        item_prog_mike.setText("Programação MIKE");
        item_prog_mike.setName("item_prog_mike"); // NOI18N
        item_prog_mike.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_prog_mikeActionPerformed(evt);
            }
        });
        menu_arquivo.add(item_prog_mike);

        item_sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/door_out.png"))); // NOI18N
        item_sair.setText("Sair");
        item_sair.setName("item_sair"); // NOI18N
        item_sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_sairActionPerformed(evt);
            }
        });
        menu_arquivo.add(item_sair);

        jMenuBar1.add(menu_arquivo);

        menu_adm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/user_gray.png"))); // NOI18N
        menu_adm.setText("Administração");
        menu_adm.setName("menu_adm"); // NOI18N

        item_usuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/user.png"))); // NOI18N
        item_usuarios.setText("Usuários");
        item_usuarios.setName("item_usuarios"); // NOI18N
        item_usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_usuariosActionPerformed(evt);
            }
        });
        menu_adm.add(item_usuarios);

        item_grupo_usuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/group.png"))); // NOI18N
        item_grupo_usuarios.setText("Grupo de Usuários");
        item_grupo_usuarios.setName("item_grupo_usuarios"); // NOI18N
        item_grupo_usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_grupo_usuariosActionPerformed(evt);
            }
        });
        menu_adm.add(item_grupo_usuarios);

        item_representantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/group_go.png"))); // NOI18N
        item_representantes.setText("Representantes");
        item_representantes.setName("item_representantes"); // NOI18N
        item_representantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_representantesActionPerformed(evt);
            }
        });
        menu_adm.add(item_representantes);

        item_regioes_atuacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map.png"))); // NOI18N
        item_regioes_atuacao.setText("Regiões de Atuação");
        item_regioes_atuacao.setName("item_regioes_atuacao"); // NOI18N
        item_regioes_atuacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_regioes_atuacaoActionPerformed(evt);
            }
        });
        menu_adm.add(item_regioes_atuacao);

        jMenuBar1.add(menu_adm);

        menu_fiscal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page.png"))); // NOI18N
        menu_fiscal.setText("Fiscal");
        menu_fiscal.setName("menu_fiscal"); // NOI18N

        item_nf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_refresh.png"))); // NOI18N
        item_nf.setText("Notas Fiscais");
        item_nf.setName("item_nf"); // NOI18N
        item_nf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_nfActionPerformed(evt);
            }
        });
        menu_fiscal.add(item_nf);

        item_natureza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_gear.png"))); // NOI18N
        item_natureza.setText("Natureza de Operação");
        item_natureza.setName("item_natureza"); // NOI18N
        item_natureza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_naturezaActionPerformed(evt);
            }
        });
        menu_fiscal.add(item_natureza);

        jMenuBar1.add(menu_fiscal);

        menu_comercial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/phone.png"))); // NOI18N
        menu_comercial.setText("Comercial");
        menu_comercial.setName("menu_comercial"); // NOI18N

        item_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/group.png"))); // NOI18N
        item_clientes.setText("Clientes");
        item_clientes.setName("item_clientes"); // NOI18N
        item_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_clientesActionPerformed(evt);
            }
        });
        menu_comercial.add(item_clientes);

        item_grupo_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/group_add.png"))); // NOI18N
        item_grupo_clientes.setText("Grupo de Clientes");
        item_grupo_clientes.setName("item_grupo_clientes"); // NOI18N
        item_grupo_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_grupo_clientesActionPerformed(evt);
            }
        });
        menu_comercial.add(item_grupo_clientes);

        item_fornecedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/lorry.png"))); // NOI18N
        item_fornecedores.setText("Fornecedores");
        item_fornecedores.setName("item_fornecedores"); // NOI18N
        item_fornecedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_fornecedoresActionPerformed(evt);
            }
        });
        menu_comercial.add(item_fornecedores);

        item_categoria_preco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/chart_line.png"))); // NOI18N
        item_categoria_preco.setText("Categorias de Preço");
        item_categoria_preco.setName("item_categoria_preco"); // NOI18N
        item_categoria_preco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_categoria_precoActionPerformed(evt);
            }
        });
        menu_comercial.add(item_categoria_preco);

        item_f_up.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow_branch.png"))); // NOI18N
        item_f_up.setText("Follow Up");
        item_f_up.setName("item_f_up"); // NOI18N
        item_f_up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_f_upActionPerformed(evt);
            }
        });
        menu_comercial.add(item_f_up);

        item_planejamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/calendar_edit.png"))); // NOI18N
        item_planejamento.setText("Planejamento De Faturamento");
        item_planejamento.setName("item_planejamento"); // NOI18N
        item_planejamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_planejamentoActionPerformed(evt);
            }
        });
        menu_comercial.add(item_planejamento);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/calendar_view_day.png"))); // NOI18N
        jMenuItem1.setText("Faturamento Diário");
        jMenuItem1.setName("item_faturamento_diario"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menu_comercial.add(jMenuItem1);

        jMenuBar1.add(menu_comercial);

        menu_financeiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money.png"))); // NOI18N
        menu_financeiro.setText("Financeiro");
        menu_financeiro.setName("menu_financeiro"); // NOI18N

        item_car.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money_add.png"))); // NOI18N
        item_car.setText("Contas a Receber");
        item_car.setName("item_car"); // NOI18N
        item_car.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_carActionPerformed(evt);
            }
        });
        menu_financeiro.add(item_car);

        item_cap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money_delete.png"))); // NOI18N
        item_cap.setText("Contas a Pagar");
        item_cap.setName("item_cap"); // NOI18N
        item_cap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_capActionPerformed(evt);
            }
        });
        menu_financeiro.add(item_cap);

        item_condicoes_pagamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/money_dollar.png"))); // NOI18N
        item_condicoes_pagamento.setText("Condições de Pagamento");
        item_condicoes_pagamento.setName("item_condicoes_pagamento"); // NOI18N
        item_condicoes_pagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_condicoes_pagamentoActionPerformed(evt);
            }
        });
        menu_financeiro.add(item_condicoes_pagamento);

        item_bancos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/coins.png"))); // NOI18N
        item_bancos.setText("Bancos");
        item_bancos.setName("item_bancos"); // NOI18N
        item_bancos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_bancosActionPerformed(evt);
            }
        });
        menu_financeiro.add(item_bancos);

        item_extratos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_copy.png"))); // NOI18N
        item_extratos.setText("Extratos");
        item_extratos.setName("item_extratos"); // NOI18N
        item_extratos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_extratosActionPerformed(evt);
            }
        });
        menu_financeiro.add(item_extratos);

        item_conciliacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_code.png"))); // NOI18N
        item_conciliacao.setText("Conciliação Bancária");
        item_conciliacao.setName("item_conciliacao"); // NOI18N
        item_conciliacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_conciliacaoActionPerformed(evt);
            }
        });
        menu_financeiro.add(item_conciliacao);

        item_cartoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/creditcards.png"))); // NOI18N
        item_cartoes.setText("Cartões");
        item_cartoes.setName("item_cartoes"); // NOI18N
        item_cartoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_cartoesActionPerformed(evt);
            }
        });
        menu_financeiro.add(item_cartoes);

        jMenuBar1.add(menu_financeiro);

        menu_compras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart.png"))); // NOI18N
        menu_compras.setText("Compras");
        menu_compras.setName("menu_compras"); // NOI18N

        item_solicitacao_compras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart_add.png"))); // NOI18N
        item_solicitacao_compras.setText("Solicitação de Compra");
        item_solicitacao_compras.setName("item_solicitacao_compras"); // NOI18N
        item_solicitacao_compras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_solicitacao_comprasActionPerformed(evt);
            }
        });
        menu_compras.add(item_solicitacao_compras);

        item_orcamento_compras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart_edit.png"))); // NOI18N
        item_orcamento_compras.setText("Cotação de Compra");
        item_orcamento_compras.setName("item_orcamento_compras"); // NOI18N
        item_orcamento_compras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_orcamento_comprasActionPerformed(evt);
            }
        });
        menu_compras.add(item_orcamento_compras);

        item_pedido_compras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart_go.png"))); // NOI18N
        item_pedido_compras.setText("Pedido de Compra");
        item_pedido_compras.setName("item_pedido_compras"); // NOI18N
        item_pedido_compras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_pedido_comprasActionPerformed(evt);
            }
        });
        menu_compras.add(item_pedido_compras);

        item_insumos_compras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cart_put.png"))); // NOI18N
        item_insumos_compras.setText("Insumos");
        item_insumos_compras.setName("item_insumos_compras"); // NOI18N
        item_insumos_compras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_insumos_comprasActionPerformed(evt);
            }
        });
        menu_compras.add(item_insumos_compras);

        item_tipo_insumos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/basket.png"))); // NOI18N
        item_tipo_insumos.setText("Tipos de Insumo");
        item_tipo_insumos.setName("item_tipo_insumos"); // NOI18N
        item_tipo_insumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_tipo_insumosActionPerformed(evt);
            }
        });
        menu_compras.add(item_tipo_insumos);

        jMenuBar1.add(menu_compras);

        menu_logistica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/package_green.png"))); // NOI18N
        menu_logistica.setText("Logística");
        menu_logistica.setName("menu_logistica"); // NOI18N

        item_carros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/car.png"))); // NOI18N
        item_carros.setText("Carros");
        item_carros.setName("item_carros"); // NOI18N
        item_carros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_carrosActionPerformed(evt);
            }
        });
        menu_logistica.add(item_carros);

        item_rastreamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_go.png"))); // NOI18N
        item_rastreamento.setText("Rastreamento de Documentos");
        item_rastreamento.setName("item_rastreamento"); // NOI18N
        item_rastreamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_rastreamentoActionPerformed(evt);
            }
        });
        menu_logistica.add(item_rastreamento);

        item_unidades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tag_blue.png"))); // NOI18N
        item_unidades.setText("Cadastro de Unidades");
        item_unidades.setName("item_unidades"); // NOI18N
        item_unidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_unidadesActionPerformed(evt);
            }
        });
        menu_logistica.add(item_unidades);

        item_locais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/box.png"))); // NOI18N
        item_locais.setText("Locais de Armazenagem");
        item_locais.setName("item_locais"); // NOI18N
        item_locais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_locaisActionPerformed(evt);
            }
        });
        menu_logistica.add(item_locais);

        jMenuBar1.add(menu_logistica);

        menu_ti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/computer.png"))); // NOI18N
        menu_ti.setText("TI");
        menu_ti.setName("menu_ti"); // NOI18N

        item_senhas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/key.png"))); // NOI18N
        item_senhas.setText("Senhas");
        item_senhas.setName("item_senhas"); // NOI18N
        item_senhas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_senhasActionPerformed(evt);
            }
        });
        menu_ti.add(item_senhas);

        item_tickets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_code.png"))); // NOI18N
        item_tickets.setText("Tickets");
        item_tickets.setName("item_tickets"); // NOI18N
        item_tickets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_ticketsActionPerformed(evt);
            }
        });
        menu_ti.add(item_tickets);

        jMenuBar1.add(menu_ti);

        menu_qualidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/award_star_bronze_3.png"))); // NOI18N
        menu_qualidade.setText("Qualidade");
        menu_qualidade.setName("menu_qualidade"); // NOI18N

        item_inst_med.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/compress.png"))); // NOI18N
        item_inst_med.setText("Instrumentos de Medição");
        item_inst_med.setName("item_inst_med"); // NOI18N
        item_inst_med.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_inst_medActionPerformed(evt);
            }
        });
        menu_qualidade.add(item_inst_med);

        item_iqf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/lorry_error.png"))); // NOI18N
        item_iqf.setText("IQF");
        item_iqf.setName("item_iqf"); // NOI18N
        item_iqf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_iqfActionPerformed(evt);
            }
        });
        menu_qualidade.add(item_iqf);

        item_medicoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/chart_bar.png"))); // NOI18N
        item_medicoes.setText("Medições");
        item_medicoes.setName("item_medicoes"); // NOI18N
        item_medicoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_medicoesActionPerformed(evt);
            }
        });
        menu_qualidade.add(item_medicoes);

        jMenuBar1.add(menu_qualidade);

        menu_rh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/folder_user.png"))); // NOI18N
        menu_rh.setText("RH");
        menu_rh.setName("menu_rh"); // NOI18N

        item_form.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_copy.png"))); // NOI18N
        item_form.setText("Formulários");
        item_form.setName("item_form"); // NOI18N

        item_solic_hora_extra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/hourglass_add.png"))); // NOI18N
        item_solic_hora_extra.setText("Solicitação de Hora Extra");
        item_solic_hora_extra.setName("item_solic_hora_extra"); // NOI18N
        item_solic_hora_extra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_solic_hora_extraActionPerformed(evt);
            }
        });
        item_form.add(item_solic_hora_extra);

        item_atraso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/hourglass_delete.png"))); // NOI18N
        item_atraso.setText("Atraso/Saída Antecipada");
        item_atraso.setName("item_atraso"); // NOI18N
        item_atraso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_atrasoActionPerformed(evt);
            }
        });
        item_form.add(item_atraso);

        menu_rh.add(item_form);

        jMenuBar1.add(menu_rh);

        menu_vendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/wrench.png"))); // NOI18N
        menu_vendas.setText("Vendas");
        menu_vendas.setName("menu_vendas"); // NOI18N

        item_vendas_cotacoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/table_wrench.png"))); // NOI18N
        item_vendas_cotacoes.setText("Cotações");
        item_vendas_cotacoes.setName("item_vendas_cotacoes"); // NOI18N
        item_vendas_cotacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_vendas_cotacoesActionPerformed(evt);
            }
        });
        menu_vendas.add(item_vendas_cotacoes);

        item_vendas_pedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/table_wrench_go.png"))); // NOI18N
        item_vendas_pedido.setText("Pedidos");
        item_vendas_pedido.setName("item_vendas_pedido"); // NOI18N
        item_vendas_pedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_vendas_pedidoActionPerformed(evt);
            }
        });
        menu_vendas.add(item_vendas_pedido);

        item_ops.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_wrench.png"))); // NOI18N
        item_ops.setText("OP's");
        item_ops.setName("item_ops"); // NOI18N
        item_ops.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_opsActionPerformed(evt);
            }
        });
        menu_vendas.add(item_ops);

        item_vendas_produtos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cog_wrench.png"))); // NOI18N
        item_vendas_produtos.setText("Produtos");
        item_vendas_produtos.setName("item_vendas_produtos"); // NOI18N
        item_vendas_produtos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_vendas_produtosActionPerformed(evt);
            }
        });
        menu_vendas.add(item_vendas_produtos);

        item_vendas_processos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_wrench.png"))); // NOI18N
        item_vendas_processos.setText("Processos");
        item_vendas_processos.setName("item_vendas_processos"); // NOI18N
        item_vendas_processos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_vendas_processosActionPerformed(evt);
            }
        });
        menu_vendas.add(item_vendas_processos);

        jMenuBar1.add(menu_vendas);

        menu_servicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/wrench_orange.png"))); // NOI18N
        menu_servicos.setText("Serviços");
        menu_servicos.setName("menu_servicos"); // NOI18N

        item_servicos_cotacoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/table_wrench_orange.png"))); // NOI18N
        item_servicos_cotacoes.setText("Cotações");
        item_servicos_cotacoes.setName("item_servicos_cotacoes"); // NOI18N
        item_servicos_cotacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_servicos_cotacoesActionPerformed(evt);
            }
        });
        menu_servicos.add(item_servicos_cotacoes);

        item_servicos_pedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/table_wrench_orange_go.png"))); // NOI18N
        item_servicos_pedido.setText("Pedidos");
        item_servicos_pedido.setName("item_servicos_pedido"); // NOI18N
        item_servicos_pedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_servicos_pedidoActionPerformed(evt);
            }
        });
        menu_servicos.add(item_servicos_pedido);

        item_oss.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_wrench_orange.png"))); // NOI18N
        item_oss.setText("OS's");
        item_oss.setName("item_oss"); // NOI18N
        item_oss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_ossActionPerformed(evt);
            }
        });
        menu_servicos.add(item_oss);

        item_servicos_produtos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cog_wrench_orange.png"))); // NOI18N
        item_servicos_produtos.setText("Produtos");
        item_servicos_produtos.setName("item_servicos_produtos"); // NOI18N
        item_servicos_produtos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_servicos_produtosActionPerformed(evt);
            }
        });
        menu_servicos.add(item_servicos_produtos);

        item_servicos_processo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_wrench_orange.png"))); // NOI18N
        item_servicos_processo.setText("Processos");
        item_servicos_processo.setName("item_servicos_processo"); // NOI18N
        item_servicos_processo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_servicos_processoActionPerformed(evt);
            }
        });
        menu_servicos.add(item_servicos_processo);

        item_servicos_grupo_processo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_cascade_wrench_orange.png"))); // NOI18N
        item_servicos_grupo_processo.setText("Grupo de Processos");
        item_servicos_grupo_processo.setName("item_servicos_grupo_processo"); // NOI18N
        item_servicos_grupo_processo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_servicos_grupo_processoActionPerformed(evt);
            }
        });
        menu_servicos.add(item_servicos_grupo_processo);

        jMenuBar1.add(menu_servicos);

        menu_terceiros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/link.png"))); // NOI18N
        menu_terceiros.setText("Terceiros");
        menu_terceiros.setName("menu_terceiros"); // NOI18N

        item_ots.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_link.png"))); // NOI18N
        item_ots.setText("OT's");
        item_ots.setName("item_ots"); // NOI18N
        item_ots.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_otsActionPerformed(evt);
            }
        });
        menu_terceiros.add(item_ots);

        jMenuBar1.add(menu_terceiros);

        menu_configuracoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plugin.png"))); // NOI18N
        menu_configuracoes.setText("Configurações");
        menu_configuracoes.setName("menu_configuracoes"); // NOI18N

        item_menus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/application_cascade.png"))); // NOI18N
        item_menus.setText("Menus");
        item_menus.setName("item_menus"); // NOI18N
        item_menus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_menusActionPerformed(evt);
            }
        });
        menu_configuracoes.add(item_menus);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/email_open.png"))); // NOI18N
        jMenuItem2.setText("Mensagens Emails");
        jMenuItem2.setName("item_mensagens_email"); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menu_configuracoes.add(jMenuItem2);

        jMenuBar1.add(menu_configuracoes);

        menu_ajuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/information.png"))); // NOI18N
        menu_ajuda.setText("Ajuda");
        menu_ajuda.setName("menu_ajuda"); // NOI18N

        item_trocar_senha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow_refresh.png"))); // NOI18N
        item_trocar_senha.setText("Trocar Senha");
        item_trocar_senha.setName("item_trocar_senha"); // NOI18N
        item_trocar_senha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_trocar_senhaActionPerformed(evt);
            }
        });
        menu_ajuda.add(item_trocar_senha);

        item_mike.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        item_mike.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/lightbulb.png"))); // NOI18N
        item_mike.setText("M.I.K.E.");
        item_mike.setName("item_mike"); // NOI18N
        item_mike.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_mikeActionPerformed(evt);
            }
        });
        menu_ajuda.add(item_mike);

        item_sobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/page_white_text.png"))); // NOI18N
        item_sobre.setText("Sobre");
        item_sobre.setName("item_sobre"); // NOI18N
        item_sobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_sobreActionPerformed(evt);
            }
        });
        menu_ajuda.add(item_sobre);

        jMenuBar1.add(menu_ajuda);

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

    private void item_sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_sairActionPerformed
        this.dispose();
    }//GEN-LAST:event_item_sairActionPerformed

    private void item_usuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_usuariosActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Usuarios telauser = new Usuarios();
            Telas.AparecerTela(telauser);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_usuariosActionPerformed

    private void item_opsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_opsActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            OP ops = new OP();
            Telas.AparecerTelaAumentada(ops);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_opsActionPerformed

    private void item_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_emailActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Email email = new Email();
            Telas.AparecerTela(email);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_emailActionPerformed

    private void item_vendas_pedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_vendas_pedidoActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            PedidoVenda pv = new PedidoVenda();
            Telas.AparecerTelaAumentada(pv);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_vendas_pedidoActionPerformed

    private void item_vendas_cotacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_vendas_cotacoesActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            CotacaoVenda cv = new CotacaoVenda();
            Telas.AparecerTelaAumentada(cv);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_vendas_cotacoesActionPerformed

    private void item_sobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_sobreActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Sistema M.I.K.E®\n\nCriado por Marcos Filho\nTodos os direitos reservados");
    }//GEN-LAST:event_item_sobreActionPerformed

    private void item_mikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_mikeActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Tarefas t = new Tarefas();
            t.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_mikeActionPerformed

    private void item_servicos_cotacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_servicos_cotacoesActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            CotacaoServico tela = new CotacaoServico();
            Telas.AparecerTelaAumentada(tela);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_servicos_cotacoesActionPerformed

    private void item_servicos_pedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_servicos_pedidoActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            PedidoServico p = new PedidoServico();
            Telas.AparecerTelaAumentada(p);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_servicos_pedidoActionPerformed

    private void item_ossActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_ossActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            OS os = new OS();
            Telas.AparecerTelaAumentada(os);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_ossActionPerformed

    private void item_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_clientesActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Clientes telaclientes = new Clientes();
            Telas.AparecerTelaAumentada(telaclientes);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_clientesActionPerformed

    private void item_servicos_produtosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_servicos_produtosActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            ServicoMateriais tela = new ServicoMateriais();
            Telas.AparecerTelaAumentada(tela);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_servicos_produtosActionPerformed

    private void item_grupo_usuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_grupo_usuariosActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            GrupoDeUsuarios gu = new GrupoDeUsuarios();
            Telas.AparecerTela(gu);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_grupo_usuariosActionPerformed

    private void item_menusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_menusActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Menus m = new Menus();
            Telas.AparecerTela(m);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_menusActionPerformed

    private void item_representantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_representantesActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Representantes r = new Representantes();
            Telas.AparecerTela(r);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_representantesActionPerformed

    private void item_regioes_atuacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_regioes_atuacaoActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Regioes r = new Regioes();
            Telas.AparecerTela(r);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_regioes_atuacaoActionPerformed

    private void item_condicoes_pagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_condicoes_pagamentoActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            CondicoesDePagamento cp = new CondicoesDePagamento();
            Telas.AparecerTela(cp);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_condicoes_pagamentoActionPerformed

    private void item_capActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_capActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            ContasPagar c = new ContasPagar();
            Telas.AparecerTelaAumentada(c);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_capActionPerformed

    private void item_bancosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_bancosActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Bancos b = new Bancos();
            Telas.AparecerTelaAumentada(b);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_bancosActionPerformed

    private void item_fornecedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_fornecedoresActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Fornecedores f = new Fornecedores();
            Telas.AparecerTela(f);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_fornecedoresActionPerformed

    private void item_servicos_processoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_servicos_processoActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            ProcessosServico ps = new ProcessosServico();
            Telas.AparecerTela(ps);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_servicos_processoActionPerformed

    private void item_servicos_grupo_processoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_servicos_grupo_processoActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            GrupoDeProcessosServico gps = new GrupoDeProcessosServico();
            Telas.AparecerTela(gps);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_servicos_grupo_processoActionPerformed

    private void item_solicitacao_comprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_solicitacao_comprasActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            ComprasSolicitacao p = new ComprasSolicitacao();
            Telas.AparecerTelaAumentada(p);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_solicitacao_comprasActionPerformed

    private void item_inst_medActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_inst_medActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            InstrumentosMedicao p = new InstrumentosMedicao();
            Telas.AparecerTela(p);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_inst_medActionPerformed

    private void item_carrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_carrosActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {

        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_carrosActionPerformed

    private void item_nfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_nfActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            NotasFiscais nf = new NotasFiscais();
            Telas.AparecerTelaAumentada(nf);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_nfActionPerformed

    private void item_naturezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_naturezaActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {

        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_naturezaActionPerformed

    private void item_tipo_insumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_tipo_insumosActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            TiposInsumo p = new TiposInsumo();
            Telas.AparecerTela(p);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_tipo_insumosActionPerformed

    private void item_vendas_produtosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_vendas_produtosActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            VM1 p = new VM1();
            Telas.AparecerTelaAumentada(p);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_vendas_produtosActionPerformed

    private void item_rastreamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_rastreamentoActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            RastreamentoDocumentos p = new RastreamentoDocumentos();
            Telas.AparecerTela(p);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_rastreamentoActionPerformed

    private void item_categoria_precoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_categoria_precoActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            CategoriaDePreco p = new CategoriaDePreco();
            Telas.AparecerTelaAumentada(p);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_categoria_precoActionPerformed

    private void item_carActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_carActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            ContasReceber p = new ContasReceber();
            Telas.AparecerTelaAumentada(p);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_carActionPerformed

    private void item_insumos_comprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_insumos_comprasActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Insumos i = new Insumos();
            Telas.AparecerTelaAumentada(i);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_insumos_comprasActionPerformed

    private void item_f_upActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_f_upActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            F_UP f = new F_UP();
            Telas.AparecerTelaAumentada(f);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_f_upActionPerformed

    private void item_senhasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_senhasActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Senhas s = new Senhas();
            Telas.AparecerTela(s);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_senhasActionPerformed

    private void item_unidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_unidadesActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Unidades u = new Unidades();
            Telas.AparecerTela(u);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_unidadesActionPerformed

    private void item_solic_hora_extraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_solic_hora_extraActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            SolicitacaoHoraExtra she = new SolicitacaoHoraExtra();
            Telas.AparecerTelaAumentada(she);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_solic_hora_extraActionPerformed

    private void item_extratosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_extratosActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Extratos e = new Extratos();
            Telas.AparecerTelaAumentada(e);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_extratosActionPerformed

    private void item_vendas_processosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_vendas_processosActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            ProcessosVendas pv = new ProcessosVendas();
            Telas.AparecerTela(pv);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_vendas_processosActionPerformed

    private void item_medicoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_medicoesActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Medicoes m = new Medicoes();
            Telas.AparecerTela(m);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_medicoesActionPerformed

    private void item_cartoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_cartoesActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Cartoes c = new Cartoes();
            Telas.AparecerTela(c);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_cartoesActionPerformed

    private void item_planejamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_planejamentoActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            PlanejamentoFaturamento pf = new PlanejamentoFaturamento();
            Telas.AparecerTelaAumentada(pf);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_planejamentoActionPerformed

    private void item_prog_mikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_prog_mikeActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            ProgramacaoMIKE pm = new ProgramacaoMIKE();
            Telas.AparecerTelaAumentada(pm);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_prog_mikeActionPerformed

    private void item_locaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_locaisActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            LocaisArmazenagem la = new LocaisArmazenagem();
            Telas.AparecerTela(la);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_locaisActionPerformed

    private void item_trocar_senhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_trocar_senhaActionPerformed
        int resp = JOptionPane.showConfirmDialog(null, "Deseja realmente alterar sua senha?", "Alterar Senha", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            String senha = JOptionPane.showInputDialog(null, "Digite a nova senha.", "Nova senha", JOptionPane.YES_NO_OPTION);
            if (senha.length() == 0) {
                JOptionPane.showMessageDialog(null, "Nenhuma senha digitada.");
            } else {
                String conf_senha = JOptionPane.showInputDialog(null, "Confirme a nova senha.", "Confirmar nova senha", JOptionPane.YES_NO_OPTION);
                if (conf_senha.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Nenhuma senha de confirmação digitada.");
                } else {
                    if (senha.equals(conf_senha)) {
                        UsuariosDAO ud = new UsuariosDAO();

                        ud.updateSenha(senha, Session.nome);

                        TelaPrincipal tp = new TelaPrincipal();
                        tp.dispose();

                        TelaLogin tl = new TelaLogin();
                        tl.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Senhas diferentes.\nNão foi possível alterar sua senha.");
                    }
                }
            }
        }
    }//GEN-LAST:event_item_trocar_senhaActionPerformed

    private void item_grupo_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_grupo_clientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_item_grupo_clientesActionPerformed

    private void item_conciliacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_conciliacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_item_conciliacaoActionPerformed

    private void item_orcamento_comprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_orcamento_comprasActionPerformed
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            ComprasCotacao cc = new ComprasCotacao();
            Telas.AparecerTelaAumentada(cc);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }//GEN-LAST:event_item_orcamento_comprasActionPerformed

    private void item_pedido_comprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_pedido_comprasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_item_pedido_comprasActionPerformed

    private void item_iqfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_iqfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_item_iqfActionPerformed

    private void item_atrasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_atrasoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_item_atrasoActionPerformed

    private void item_otsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_otsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_item_otsActionPerformed

    private void item_protocolosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_protocolosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_item_protocolosActionPerformed

    private void item_ticketsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_ticketsActionPerformed
        Tickets t = new Tickets();
        MenusAbrir.acessoTela(evt, t);
    }//GEN-LAST:event_item_ticketsActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        FaturamentoDiario frame = new FaturamentoDiario();
        MenusAbrir.acessoTela(evt, frame);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Mensagens m = new Mensagens();
        MenusAbrir.acessoTela(evt, m);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
    private javax.swing.JMenuItem item_atraso;
    public static javax.swing.JMenuItem item_bancos;
    public static javax.swing.JMenuItem item_cap;
    public static javax.swing.JMenuItem item_car;
    public static javax.swing.JMenuItem item_carros;
    private javax.swing.JMenuItem item_cartoes;
    private javax.swing.JMenuItem item_categoria_preco;
    public static javax.swing.JMenuItem item_clientes;
    private javax.swing.JMenuItem item_conciliacao;
    public static javax.swing.JMenuItem item_condicoes_pagamento;
    public static javax.swing.JMenuItem item_email;
    private javax.swing.JMenuItem item_extratos;
    private javax.swing.JMenuItem item_f_up;
    private javax.swing.JMenu item_form;
    public static javax.swing.JMenuItem item_fornecedores;
    public static javax.swing.JMenuItem item_grupo_clientes;
    public static javax.swing.JMenuItem item_grupo_usuarios;
    public static javax.swing.JMenuItem item_inst_med;
    public static javax.swing.JMenuItem item_insumos_compras;
    private javax.swing.JMenuItem item_iqf;
    private javax.swing.JMenuItem item_locais;
    private javax.swing.JMenuItem item_medicoes;
    public static javax.swing.JMenuItem item_menus;
    public static javax.swing.JMenuItem item_mike;
    public static javax.swing.JMenuItem item_natureza;
    public static javax.swing.JMenuItem item_nf;
    public static javax.swing.JMenuItem item_ops;
    public static javax.swing.JMenuItem item_orcamento_compras;
    public static javax.swing.JMenuItem item_oss;
    private javax.swing.JMenuItem item_ots;
    public static javax.swing.JMenuItem item_pedido_compras;
    private javax.swing.JMenuItem item_planejamento;
    private javax.swing.JMenuItem item_prog_mike;
    private javax.swing.JMenuItem item_protocolos;
    private javax.swing.JMenuItem item_rastreamento;
    public static javax.swing.JMenuItem item_regioes_atuacao;
    public static javax.swing.JMenuItem item_representantes;
    public static javax.swing.JMenuItem item_sair;
    private javax.swing.JMenuItem item_senhas;
    public static javax.swing.JMenuItem item_servicos_cotacoes;
    public static javax.swing.JMenuItem item_servicos_grupo_processo;
    public static javax.swing.JMenuItem item_servicos_pedido;
    public static javax.swing.JMenuItem item_servicos_processo;
    public static javax.swing.JMenuItem item_servicos_produtos;
    public static javax.swing.JMenuItem item_sobre;
    private javax.swing.JMenuItem item_solic_hora_extra;
    public static javax.swing.JMenuItem item_solicitacao_compras;
    private javax.swing.JMenuItem item_tickets;
    private javax.swing.JMenuItem item_tipo_insumos;
    private javax.swing.JMenuItem item_trocar_senha;
    private javax.swing.JMenuItem item_unidades;
    public static javax.swing.JMenuItem item_usuarios;
    public static javax.swing.JMenuItem item_vendas_cotacoes;
    public static javax.swing.JMenuItem item_vendas_pedido;
    public static javax.swing.JMenuItem item_vendas_processos;
    public static javax.swing.JMenuItem item_vendas_produtos;
    public static javax.swing.JDesktopPane jDesktopPane1;
    public static javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel lblnome;
    public static javax.swing.JMenu menu_adm;
    public static javax.swing.JMenu menu_ajuda;
    public static javax.swing.JMenu menu_arquivo;
    public static javax.swing.JMenu menu_comercial;
    public static javax.swing.JMenu menu_compras;
    public static javax.swing.JMenu menu_configuracoes;
    public static javax.swing.JMenu menu_financeiro;
    public static javax.swing.JMenu menu_fiscal;
    public static javax.swing.JMenu menu_logistica;
    public static javax.swing.JMenu menu_qualidade;
    private javax.swing.JMenu menu_rh;
    public static javax.swing.JMenu menu_servicos;
    private javax.swing.JMenu menu_terceiros;
    private javax.swing.JMenu menu_ti;
    public static javax.swing.JMenu menu_vendas;
    // End of variables declaration//GEN-END:variables
}
