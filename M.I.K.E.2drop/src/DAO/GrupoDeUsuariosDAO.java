/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.GrupoDeUsuariosBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import View.TelaPrincipal;
import View.administracao.GrupoDeUsuarios;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class GrupoDeUsuariosDAO {

    public void create(GrupoDeUsuariosBean gub) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO grupo_usuarios (nome, menuadministracao, submenuusuarios, submenugrupodeusuarios, submenurepresentantes, submenuregioesdeatuacao, menucomercial, submenuclientes, submenugrupodeclientes, submenufornecedores, menufinanceiro, submenucontasareceber, submenucontasapagar, submenucondicoesdepagamento, submenubancos, menucompras, submenusolicitacaodecompras, submenuorcamentodecompras, submenupedidodecompras, submenuinsumos, menulogistica, submenucarros, menuqualidade, submenuinstrumentosdemedicao, menuvendas, submenuorcamentosvenda, submenupedidosvenda, submenuops, submenuprodutosvenda, submenuprocessosvenda, submenugrupodeprocessosvenda, menuservicos, submenuorcamentosservico, submenupedidosservico, submenuoss, submenuprodutosservico, submenuprocessosservico, submenugrupodeprocessosservico, menuconfiguracoes, submenumenus) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, gub.getNome());
            stmt.setString(2, gub.getMenuadministracao());
            stmt.setString(3, gub.getSubmenuusuarios());
            stmt.setString(4, gub.getSubmenugrupodeusuarios());
            stmt.setString(5, gub.getSubmenurepresentantes());
            stmt.setString(6, gub.getSubmenuregioesdeatuacao());
            stmt.setString(7, gub.getMenucomercial());
            stmt.setString(8, gub.getSubmenuclientes());
            stmt.setString(9, gub.getSubmenugrupodeclientes());
            stmt.setString(10, gub.getSubmenufornecedores());
            stmt.setString(11, gub.getMenufinanceiro());
            stmt.setString(12, gub.getSubmenucontasareceber());
            stmt.setString(13, gub.getSubmenucontasapagar());
            stmt.setString(14, gub.getSubmenucondicoesdepagamento());
            stmt.setString(15, gub.getSubmenubancos());
            stmt.setString(16, gub.getMenucompras());
            stmt.setString(17, gub.getSubmenusolicitacaodecompras());
            stmt.setString(18, gub.getSubmenuorcamentodecompras());
            stmt.setString(19, gub.getSubmenupedidodecompras());
            stmt.setString(20, gub.getSubmenuinsumos());
            stmt.setString(21, gub.getMenulogistica());
            stmt.setString(22, gub.getSubmenucarros());
            stmt.setString(23, gub.getMenuqualidade());
            stmt.setString(24, gub.getSubmenuinstrumentosdemedicao());
            stmt.setString(25, gub.getMenuvendas());
            stmt.setString(26, gub.getSubmenuorcamentosvenda());
            stmt.setString(27, gub.getSubmenupedidosvenda());
            stmt.setString(28, gub.getSubmenuops());
            stmt.setString(29, gub.getSubmenuprodutosvenda());
            stmt.setString(30, gub.getSubmenuprocessosvenda());
            stmt.setString(31, gub.getSubmenugrupodeprocessosvenda());
            stmt.setString(32, gub.getMenuservicos());
            stmt.setString(33, gub.getSubmenuorcamentosservico());
            stmt.setString(34, gub.getSubmenupedidosservico());
            stmt.setString(35, gub.getSubmenuoss());
            stmt.setString(36, gub.getSubmenuprodutosservico());
            stmt.setString(37, gub.getSubmenuprocessosservico());
            stmt.setString(38, gub.getSubmenugrupodeprocessosservico());
            stmt.setString(39, gub.getMenuconfiguracoes());
            stmt.setString(40, gub.getSubmenumenus());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(GrupoDeUsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<GrupoDeUsuariosBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<GrupoDeUsuariosBean> listgu = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM grupo_usuarios");
            rs = stmt.executeQuery();

            while (rs.next()) {
                GrupoDeUsuariosBean gub = new GrupoDeUsuariosBean();

                gub.setId(rs.getInt("id"));
                gub.setNome(rs.getString("nome"));
                listgu.add(gub);
            }

        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(GrupoDeUsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listgu;
    }

    public List<GrupoDeUsuariosBean> readgrupocadastrado() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<GrupoDeUsuariosBean> listgub = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM grupo_usuarios WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(GrupoDeUsuarios.txtidgrupo.getText()));
            rs = stmt.executeQuery();

            while (rs.next()) {
                GrupoDeUsuariosBean gub = new GrupoDeUsuariosBean();

                gub.setNome(rs.getString("nome"));
                gub.setMenuadministracao(rs.getString("menuadministracao"));
                gub.setSubmenuusuarios(rs.getString("submenuusuarios"));
                gub.setSubmenugrupodeusuarios(rs.getString("submenugrupodeusuarios"));
                gub.setSubmenurepresentantes(rs.getString("submenurepresentantes"));
                gub.setSubmenuregioesdeatuacao(rs.getString("submenuregioesdeatuacao"));
                gub.setMenucomercial(rs.getString("menucomercial"));
                gub.setSubmenuclientes(rs.getString("submenuclientes"));
                gub.setSubmenugrupodeclientes(rs.getString("submenugrupodeclientes"));
                gub.setSubmenufornecedores(rs.getString("submenufornecedores"));
                gub.setMenufinanceiro(rs.getString("menufinanceiro"));
                gub.setSubmenucontasareceber(rs.getString("submenucontasareceber"));
                gub.setSubmenucontasapagar(rs.getString("submenucontasapagar"));
                gub.setSubmenucondicoesdepagamento(rs.getString("submenucondicoesdepagamento"));
                gub.setSubmenubancos(rs.getString("submenubancos"));
                gub.setMenucompras(rs.getString("menucompras"));
                gub.setSubmenusolicitacaodecompras(rs.getString("submenusolicitacaodecompras"));
                gub.setSubmenuorcamentodecompras(rs.getString("submenuorcamentodecompras"));
                gub.setSubmenupedidodecompras(rs.getString("submenupedidodecompras"));
                gub.setSubmenuinsumos(rs.getString("submenuinsumos"));
                gub.setMenulogistica(rs.getString("menulogistica"));
                gub.setSubmenucarros(rs.getString("submenucarros"));
                gub.setMenuqualidade(rs.getString("menuqualidade"));
                gub.setSubmenuinstrumentosdemedicao(rs.getString("submenuinstrumentosdemedicao"));
                gub.setMenuvendas(rs.getString("menuvendas"));
                gub.setSubmenuorcamentosvenda(rs.getString("submenuorcamentosvenda"));
                gub.setSubmenupedidosvenda(rs.getString("submenupedidosvenda"));
                gub.setSubmenuops(rs.getString("submenuops"));
                gub.setSubmenuprodutosvenda(rs.getString("submenuprodutosvenda"));
                gub.setSubmenuprocessosvenda(rs.getString("submenuprocessosvenda"));
                gub.setSubmenugrupodeprocessosvenda(rs.getString("submenugrupodeprocessosvenda"));
                gub.setMenuservicos(rs.getString("menuservicos"));
                gub.setSubmenuorcamentosservico(rs.getString("submenuorcamentosservico"));
                gub.setSubmenupedidosservico(rs.getString("submenupedidosservico"));
                gub.setSubmenuoss(rs.getString("submenuoss"));
                gub.setSubmenuprodutosservico(rs.getString("submenuprodutosservico"));
                gub.setSubmenuprocessosservico(rs.getString("submenuprocessosservico"));
                gub.setSubmenugrupodeprocessosservico(rs.getString("submenugrupodeprocessosservico"));
                gub.setMenuconfiguracoes(rs.getString("menuconfiguracoes"));
                gub.setSubmenumenus(rs.getString("submenumenus"));

                listgub.add(gub);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(GrupoDeUsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listgub;
    }

    public List<GrupoDeUsuariosBean> getmenus(String nivel) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<GrupoDeUsuariosBean> listgub = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM grupo_usuarios WHERE nome = ?");
            stmt.setString(1, TelaPrincipal.lblgrupo.getText());
            rs = stmt.executeQuery();

            while (rs.next()) {
                GrupoDeUsuariosBean gub = new GrupoDeUsuariosBean();

                gub.setMenuadministracao(rs.getString("menuadministracao"));
                gub.setSubmenuusuarios(rs.getString("submenuusuarios"));
                gub.setSubmenugrupodeusuarios(rs.getString("submenugrupodeusuarios"));
                gub.setSubmenurepresentantes(rs.getString("submenurepresentantes"));
                gub.setSubmenuregioesdeatuacao(rs.getString("submenuregioesdeatuacao"));
                gub.setMenucomercial(rs.getString("menucomercial"));
                gub.setSubmenuclientes(rs.getString("submenuclientes"));
                gub.setSubmenugrupodeclientes(rs.getString("submenugrupodeclientes"));
                gub.setSubmenufornecedores(rs.getString("submenufornecedores"));
                gub.setMenufinanceiro(rs.getString("menufinanceiro"));
                gub.setSubmenucontasareceber(rs.getString("submenucontasareceber"));
                gub.setSubmenucontasapagar(rs.getString("submenucontasapagar"));
                gub.setSubmenucondicoesdepagamento(rs.getString("submenucondicoesdepagamento"));
                gub.setSubmenubancos(rs.getString("submenubancos"));
                gub.setMenucompras(rs.getString("menucompras"));
                gub.setSubmenusolicitacaodecompras(rs.getString("submenusolicitacaodecompras"));
                gub.setSubmenuorcamentodecompras(rs.getString("submenuorcamentodecompras"));
                gub.setSubmenupedidodecompras(rs.getString("submenupedidodecompras"));
                gub.setSubmenuinsumos(rs.getString("submenuinsumos"));
                gub.setMenulogistica(rs.getString("menulogistica"));
                gub.setSubmenucarros(rs.getString("submenucarros"));
                gub.setMenuqualidade(rs.getString("menuqualidade"));
                gub.setSubmenuinstrumentosdemedicao(rs.getString("submenuinstrumentosdemedicao"));
                gub.setMenuvendas(rs.getString("menuvendas"));
                gub.setSubmenuorcamentosvenda(rs.getString("submenuorcamentosvenda"));
                gub.setSubmenupedidosvenda(rs.getString("submenupedidosvenda"));
                gub.setSubmenuops(rs.getString("submenuops"));
                gub.setSubmenuprodutosvenda(rs.getString("submenuprodutosvenda"));
                gub.setSubmenuprocessosvenda(rs.getString("submenuprocessosvenda"));
                gub.setSubmenugrupodeprocessosvenda(rs.getString("submenugrupodeprocessosvenda"));
                gub.setMenuservicos(rs.getString("menuservicos"));
                gub.setSubmenuorcamentosservico(rs.getString("submenuorcamentosservico"));
                gub.setSubmenupedidosservico(rs.getString("submenupedidosservico"));
                gub.setSubmenuoss(rs.getString("submenuoss"));
                gub.setSubmenuprodutosservico(rs.getString("submenuprodutosservico"));
                gub.setSubmenuprocessosservico(rs.getString("submenuprocessosservico"));
                gub.setSubmenugrupodeprocessosservico(rs.getString("submenugrupodeprocessosservico"));
                gub.setMenuconfiguracoes(rs.getString("menuconfiguracoes"));
                gub.setSubmenumenus(rs.getString("submenumenus"));

                listgub.add(gub);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(GrupoDeUsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listgub;
    }

    public void update(GrupoDeUsuariosBean gub) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE grupo_usuarios SET nome = ?, menuadministracao = ?, submenuusuarios = ?, submenugrupodeusuarios = ?, submenurepresentantes = ?, submenuregioesdeatuacao = ?, menucomercial = ?, submenuclientes = ?, submenugrupodeclientes = ?, submenufornecedores = ?, menufinanceiro = ?, submenucontasareceber = ?, submenucontasapagar = ?, submenucondicoesdepagamento = ?, submenubancos = ?, menucompras = ?, submenusolicitacaodecompras = ?, submenuorcamentodecompras = ?, submenupedidodecompras = ?, submenuinsumos = ?, menulogistica = ?, submenucarros = ?, menuqualidade = ?, submenuinstrumentosdemedicao = ?, menuvendas = ?, submenuorcamentosvenda = ?, submenupedidosvenda = ?, submenuops = ?, submenuprodutosvenda = ?, submenuprocessosvenda = ?, submenugrupodeprocessosvenda = ?, menuservicos = ?, submenuorcamentosservico = ?, submenupedidosservico = ?, submenuoss = ?, submenuprodutosservico = ?, submenuprocessosservico = ?, submenugrupodeprocessosservico = ?, menuconfiguracoes = ?, submenumenus = ? WHERE id = ?");
            stmt.setString(1, gub.getNome());
            stmt.setString(2, gub.getMenuadministracao());
            stmt.setString(3, gub.getSubmenuusuarios());
            stmt.setString(4, gub.getSubmenugrupodeusuarios());
            stmt.setString(5, gub.getSubmenurepresentantes());
            stmt.setString(6, gub.getSubmenuregioesdeatuacao());
            stmt.setString(7, gub.getMenucomercial());
            stmt.setString(8, gub.getSubmenuclientes());
            stmt.setString(9, gub.getSubmenugrupodeclientes());
            stmt.setString(10, gub.getSubmenufornecedores());
            stmt.setString(11, gub.getMenufinanceiro());
            stmt.setString(12, gub.getSubmenucontasareceber());
            stmt.setString(13, gub.getSubmenucontasapagar());
            stmt.setString(14, gub.getSubmenucondicoesdepagamento());
            stmt.setString(15, gub.getSubmenubancos());
            stmt.setString(16, gub.getMenucompras());
            stmt.setString(17, gub.getSubmenusolicitacaodecompras());
            stmt.setString(18, gub.getSubmenuorcamentodecompras());
            stmt.setString(19, gub.getSubmenupedidodecompras());
            stmt.setString(20, gub.getSubmenuinsumos());
            stmt.setString(21, gub.getMenulogistica());
            stmt.setString(22, gub.getSubmenucarros());
            stmt.setString(23, gub.getMenuqualidade());
            stmt.setString(24, gub.getSubmenuinstrumentosdemedicao());
            stmt.setString(25, gub.getMenuvendas());
            stmt.setString(26, gub.getSubmenuorcamentosvenda());
            stmt.setString(27, gub.getSubmenupedidosvenda());
            stmt.setString(28, gub.getSubmenuops());
            stmt.setString(29, gub.getSubmenuprodutosvenda());
            stmt.setString(30, gub.getSubmenuprocessosvenda());
            stmt.setString(31, gub.getSubmenugrupodeprocessosvenda());
            stmt.setString(32, gub.getMenuservicos());
            stmt.setString(33, gub.getSubmenuorcamentosservico());
            stmt.setString(34, gub.getSubmenupedidosservico());
            stmt.setString(35, gub.getSubmenuoss());
            stmt.setString(36, gub.getSubmenuprodutosservico());
            stmt.setString(37, gub.getSubmenuprocessosservico());
            stmt.setString(38, gub.getSubmenugrupodeprocessosservico());
            stmt.setString(39, gub.getMenuconfiguracoes());
            stmt.setString(40, gub.getSubmenumenus());
            stmt.setInt(41, gub.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(GrupoDeUsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
