/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.GrupoDeUsuariosBean;
import Connection.ConnectionFactory;
import Connection.Session;
import Methods.SendEmail;
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
            stmt = con.prepareStatement("INSERT INTO grupo_usuarios (nome, menuadministracao, submenuusuarios, submenugrupodeusuarios, submenurepresentantes, submenuregioesdeatuacao, menufiscal, submenunotasfiscais, submenunaturezadeoperacao, menucomercial, submenuclientes, submenugrupodeclientes, submenufornecedores, submenucategoriadepreco, menufinanceiro, submenucontasareceber, submenucontasapagar, submenucondicoesdepagamento, submenubancos, menucompras, submenusolicitacaodecompras, submenuorcamentodecompras, submenupedidodecompras, submenuinsumos, submenutipodeproduto, menulogistica, submenucarros, submenurastreamentodedocumentos, menuqualidade, submenuinstrumentosdemedicao, submenuiqf, submenumedicoes, menuvendas, submenuorcamentosvenda, submenupedidosvenda, submenuops, submenuprodutosvenda, submenuprocessosvenda, submenugrupodeprocessosvenda, menuservicos, submenuorcamentosservico, submenupedidosservico, submenuoss, submenuprodutosservico, submenuprocessosservico, submenugrupodeprocessosservico, menuconfiguracoes, submenumenus) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, gub.getNome());
            stmt.setBoolean(2, gub.isMenuadministracao());
            stmt.setBoolean(3, gub.isSubmenuusuarios());
            stmt.setBoolean(4, gub.isSubmenugrupodeusuarios());
            stmt.setBoolean(5, gub.isSubmenurepresentantes());
            stmt.setBoolean(6, gub.isSubmenuregioesdeatuacao());
            stmt.setBoolean(7, gub.isMenufiscal());
            stmt.setBoolean(8, gub.isSubmenunotasfiscais());
            stmt.setBoolean(9, gub.isSubmenunaturezadeoperacao());
            stmt.setBoolean(10, gub.isMenucomercial());
            stmt.setBoolean(11, gub.isSubmenuclientes());
            stmt.setBoolean(12, gub.isSubmenugrupodeclientes());
            stmt.setBoolean(13, gub.isSubmenufornecedores());
            stmt.setBoolean(14, gub.isSubmenucategoriadepreco());
            stmt.setBoolean(15, gub.isMenufinanceiro());
            stmt.setBoolean(16, gub.isSubmenucontasareceber());
            stmt.setBoolean(17, gub.isSubmenucontasapagar());
            stmt.setBoolean(18, gub.isSubmenucondicoesdepagamento());
            stmt.setBoolean(19, gub.isSubmenubancos());
            stmt.setBoolean(20, gub.isMenucompras());
            stmt.setBoolean(21, gub.isSubmenusolicitacaodecompras());
            stmt.setBoolean(22, gub.isSubmenuorcamentodecompras());
            stmt.setBoolean(23, gub.isSubmenupedidodecompras());
            stmt.setBoolean(24, gub.isSubmenuinsumos());
            stmt.setBoolean(25, gub.isSubmenutipodeproduto());
            stmt.setBoolean(26, gub.isMenulogistica());
            stmt.setBoolean(27, gub.isSubmenucarros());
            stmt.setBoolean(28, gub.isSubmenurastreamentodedocumentos());
            stmt.setBoolean(29, gub.isMenuqualidade());
            stmt.setBoolean(30, gub.isSubmenuinstrumentosdemedicao());
            stmt.setBoolean(31, gub.isSubmenuiqf());
            stmt.setBoolean(32, gub.isSubmenumedicoes());
            stmt.setBoolean(33, gub.isMenuvendas());
            stmt.setBoolean(34, gub.isSubmenuorcamentosvenda());
            stmt.setBoolean(35, gub.isSubmenupedidosvenda());
            stmt.setBoolean(36, gub.isSubmenuops());
            stmt.setBoolean(37, gub.isSubmenuprodutosvenda());
            stmt.setBoolean(38, gub.isSubmenuprocessosvenda());
            stmt.setBoolean(39, gub.isSubmenugrupodeprocessosvenda());
            stmt.setBoolean(40, gub.isMenuservicos());
            stmt.setBoolean(41, gub.isSubmenuorcamentosservico());
            stmt.setBoolean(42, gub.isSubmenupedidosservico());
            stmt.setBoolean(43, gub.isSubmenuoss());
            stmt.setBoolean(44, gub.isSubmenuprodutosservico());
            stmt.setBoolean(45, gub.isSubmenuprocessosservico());
            stmt.setBoolean(46, gub.isSubmenugrupodeprocessosservico());
            stmt.setBoolean(47, gub.isMenuconfiguracoes());
            stmt.setBoolean(48, gub.isSubmenumenus());

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

    public List<GrupoDeUsuariosBean> readgrupocadastrado(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<GrupoDeUsuariosBean> listgub = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM grupo_usuarios WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                GrupoDeUsuariosBean gub = new GrupoDeUsuariosBean();

                gub.setNome(rs.getString("nome"));
                gub.setMenuadministracao(rs.getBoolean("menuadministracao"));
                gub.setSubmenuusuarios(rs.getBoolean("submenuusuarios"));
                gub.setSubmenugrupodeusuarios(rs.getBoolean("submenugrupodeusuarios"));
                gub.setSubmenurepresentantes(rs.getBoolean("submenurepresentantes"));
                gub.setSubmenuregioesdeatuacao(rs.getBoolean("submenuregioesdeatuacao"));
                gub.setMenufiscal(rs.getBoolean("menufiscal"));
                gub.setSubmenunotasfiscais(rs.getBoolean("submenunotasfiscais"));
                gub.setSubmenunaturezadeoperacao(rs.getBoolean("submenunaturezadeoperacao"));
                gub.setMenucomercial(rs.getBoolean("menucomercial"));
                gub.setSubmenuclientes(rs.getBoolean("submenuclientes"));
                gub.setSubmenugrupodeclientes(rs.getBoolean("submenugrupodeclientes"));
                gub.setSubmenufornecedores(rs.getBoolean("submenufornecedores"));
                gub.setSubmenucategoriadepreco(rs.getBoolean("submenucategoriadepreco"));
                gub.setMenufinanceiro(rs.getBoolean("menufinanceiro"));
                gub.setSubmenucontasareceber(rs.getBoolean("submenucontasareceber"));
                gub.setSubmenucontasapagar(rs.getBoolean("submenucontasapagar"));
                gub.setSubmenucondicoesdepagamento(rs.getBoolean("submenucondicoesdepagamento"));
                gub.setSubmenubancos(rs.getBoolean("submenubancos"));
                gub.setMenucompras(rs.getBoolean("menucompras"));
                gub.setSubmenusolicitacaodecompras(rs.getBoolean("submenusolicitacaodecompras"));
                gub.setSubmenuorcamentodecompras(rs.getBoolean("submenuorcamentodecompras"));
                gub.setSubmenupedidodecompras(rs.getBoolean("submenupedidodecompras"));
                gub.setSubmenuinsumos(rs.getBoolean("submenuinsumos"));
                gub.setSubmenutipodeproduto(rs.getBoolean("submenutipodeproduto"));
                gub.setMenulogistica(rs.getBoolean("menulogistica"));
                gub.setSubmenucarros(rs.getBoolean("submenucarros"));
                gub.setSubmenurastreamentodedocumentos(rs.getBoolean("submenurastreamentodedocumentos"));
                gub.setMenuqualidade(rs.getBoolean("menuqualidade"));
                gub.setSubmenuinstrumentosdemedicao(rs.getBoolean("submenuinstrumentosdemedicao"));
                gub.setSubmenuiqf(rs.getBoolean("submenuiqf"));
                gub.setSubmenumedicoes(rs.getBoolean("submenumedicoes"));
                gub.setMenuvendas(rs.getBoolean("menuvendas"));
                gub.setSubmenuorcamentosvenda(rs.getBoolean("submenuorcamentosvenda"));
                gub.setSubmenupedidosvenda(rs.getBoolean("submenupedidosvenda"));
                gub.setSubmenuops(rs.getBoolean("submenuops"));
                gub.setSubmenuprodutosvenda(rs.getBoolean("submenuprodutosvenda"));
                gub.setSubmenuprocessosvenda(rs.getBoolean("submenuprocessosvenda"));
                gub.setSubmenugrupodeprocessosvenda(rs.getBoolean("submenugrupodeprocessosvenda"));
                gub.setMenuservicos(rs.getBoolean("menuservicos"));
                gub.setSubmenuorcamentosservico(rs.getBoolean("submenuorcamentosservico"));
                gub.setSubmenupedidosservico(rs.getBoolean("submenupedidosservico"));
                gub.setSubmenuoss(rs.getBoolean("submenuoss"));
                gub.setSubmenuprodutosservico(rs.getBoolean("submenuprodutosservico"));
                gub.setSubmenuprocessosservico(rs.getBoolean("submenuprocessosservico"));
                gub.setSubmenugrupodeprocessosservico(rs.getBoolean("submenugrupodeprocessosservico"));
                gub.setMenuconfiguracoes(rs.getBoolean("menuconfiguracoes"));
                gub.setSubmenumenus(rs.getBoolean("submenumenus"));

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
            stmt.setString(1, Session.nivel);
            rs = stmt.executeQuery();

            while (rs.next()) {
                GrupoDeUsuariosBean gub = new GrupoDeUsuariosBean();

                gub.setNome(rs.getString("nome"));
                gub.setMenuadministracao(rs.getBoolean("menuadministracao"));
                gub.setSubmenuusuarios(rs.getBoolean("submenuusuarios"));
                gub.setSubmenugrupodeusuarios(rs.getBoolean("submenugrupodeusuarios"));
                gub.setSubmenurepresentantes(rs.getBoolean("submenurepresentantes"));
                gub.setSubmenuregioesdeatuacao(rs.getBoolean("submenuregioesdeatuacao"));
                gub.setMenufiscal(rs.getBoolean("menufiscal"));
                gub.setSubmenunotasfiscais(rs.getBoolean("submenunotasfiscais"));
                gub.setSubmenunaturezadeoperacao(rs.getBoolean("submenunaturezadeoperacao"));
                gub.setMenucomercial(rs.getBoolean("menucomercial"));
                gub.setSubmenuclientes(rs.getBoolean("submenuclientes"));
                gub.setSubmenugrupodeclientes(rs.getBoolean("submenugrupodeclientes"));
                gub.setSubmenufornecedores(rs.getBoolean("submenufornecedores"));
                gub.setSubmenucategoriadepreco(rs.getBoolean("submenucategoriadepreco"));
                gub.setMenufinanceiro(rs.getBoolean("menufinanceiro"));
                gub.setSubmenucontasareceber(rs.getBoolean("submenucontasareceber"));
                gub.setSubmenucontasapagar(rs.getBoolean("submenucontasapagar"));
                gub.setSubmenucondicoesdepagamento(rs.getBoolean("submenucondicoesdepagamento"));
                gub.setSubmenubancos(rs.getBoolean("submenubancos"));
                gub.setMenucompras(rs.getBoolean("menucompras"));
                gub.setSubmenusolicitacaodecompras(rs.getBoolean("submenusolicitacaodecompras"));
                gub.setSubmenuorcamentodecompras(rs.getBoolean("submenuorcamentodecompras"));
                gub.setSubmenupedidodecompras(rs.getBoolean("submenupedidodecompras"));
                gub.setSubmenuinsumos(rs.getBoolean("submenuinsumos"));
                gub.setSubmenutipodeproduto(rs.getBoolean("submenutipodeproduto"));
                gub.setMenulogistica(rs.getBoolean("menulogistica"));
                gub.setSubmenucarros(rs.getBoolean("submenucarros"));
                gub.setSubmenurastreamentodedocumentos(rs.getBoolean("submenurastreamentodedocumentos"));
                gub.setMenuqualidade(rs.getBoolean("menuqualidade"));
                gub.setSubmenuinstrumentosdemedicao(rs.getBoolean("submenuinstrumentosdemedicao"));
                gub.setSubmenuiqf(rs.getBoolean("submenuiqf"));
                gub.setSubmenumedicoes(rs.getBoolean("submenumedicoes"));
                gub.setMenuvendas(rs.getBoolean("menuvendas"));
                gub.setSubmenuorcamentosvenda(rs.getBoolean("submenuorcamentosvenda"));
                gub.setSubmenupedidosvenda(rs.getBoolean("submenupedidosvenda"));
                gub.setSubmenuops(rs.getBoolean("submenuops"));
                gub.setSubmenuprodutosvenda(rs.getBoolean("submenuprodutosvenda"));
                gub.setSubmenuprocessosvenda(rs.getBoolean("submenuprocessosvenda"));
                gub.setSubmenugrupodeprocessosvenda(rs.getBoolean("submenugrupodeprocessosvenda"));
                gub.setMenuservicos(rs.getBoolean("menuservicos"));
                gub.setSubmenuorcamentosservico(rs.getBoolean("submenuorcamentosservico"));
                gub.setSubmenupedidosservico(rs.getBoolean("submenupedidosservico"));
                gub.setSubmenuoss(rs.getBoolean("submenuoss"));
                gub.setSubmenuprodutosservico(rs.getBoolean("submenuprodutosservico"));
                gub.setSubmenuprocessosservico(rs.getBoolean("submenuprocessosservico"));
                gub.setSubmenugrupodeprocessosservico(rs.getBoolean("submenugrupodeprocessosservico"));
                gub.setMenuconfiguracoes(rs.getBoolean("menuconfiguracoes"));
                gub.setSubmenumenus(rs.getBoolean("submenumenus"));

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
            stmt = con.prepareStatement("UPDATE grupo_usuarios SET nome = ?, menuadministracao = ?, submenuusuarios = ?, submenugrupodeusuarios = ?, submenurepresentantes = ?, submenuregioesdeatuacao = ?, menufiscal = ?, submenunotasfiscais = ?, submenunaturezadeoperacao = ?, menucomercial = ?, submenuclientes = ?, submenugrupodeclientes = ?, submenufornecedores = ?, submenucategoriadepreco = ?, menufinanceiro = ?, submenucontasareceber = ?, submenucontasapagar = ?, submenucondicoesdepagamento = ?, submenubancos = ?, menucompras = ?, submenusolicitacaodecompras = ?, submenuorcamentodecompras = ?, submenupedidodecompras = ?, submenuinsumos = ?, submenutipodeproduto = ?, menulogistica = ?, submenucarros = ?, submenurastreamentodedocumentos = ?, menuqualidade = ?, submenuinstrumentosdemedicao = ?, submenuiqf = ?, submenumedicoes = ?, menuvendas = ?, submenuorcamentosvenda = ?, submenupedidosvenda = ?, submenuops = ?, submenuprodutosvenda = ?, submenuprocessosvenda = ?, submenugrupodeprocessosvenda = ?, menuservicos = ?, submenuorcamentosservico = ?, submenupedidosservico = ?, submenuoss = ?, submenuprodutosservico = ?, submenuprocessosservico = ?, submenugrupodeprocessosservico = ?, menuconfiguracoes = ?, submenumenus = ? WHERE id = ?");
            stmt.setString(1, gub.getNome());
            stmt.setBoolean(2, gub.isMenuadministracao());
            stmt.setBoolean(3, gub.isSubmenuusuarios());
            stmt.setBoolean(4, gub.isSubmenugrupodeusuarios());
            stmt.setBoolean(5, gub.isSubmenurepresentantes());
            stmt.setBoolean(6, gub.isSubmenuregioesdeatuacao());
            stmt.setBoolean(7, gub.isMenufiscal());
            stmt.setBoolean(8, gub.isSubmenunotasfiscais());
            stmt.setBoolean(9, gub.isSubmenunaturezadeoperacao());
            stmt.setBoolean(10, gub.isMenucomercial());
            stmt.setBoolean(11, gub.isSubmenuclientes());
            stmt.setBoolean(12, gub.isSubmenugrupodeclientes());
            stmt.setBoolean(13, gub.isSubmenufornecedores());
            stmt.setBoolean(14, gub.isSubmenucategoriadepreco());
            stmt.setBoolean(15, gub.isMenufinanceiro());
            stmt.setBoolean(16, gub.isSubmenucontasareceber());
            stmt.setBoolean(17, gub.isSubmenucontasapagar());
            stmt.setBoolean(18, gub.isSubmenucondicoesdepagamento());
            stmt.setBoolean(19, gub.isSubmenubancos());
            stmt.setBoolean(20, gub.isMenucompras());
            stmt.setBoolean(21, gub.isSubmenusolicitacaodecompras());
            stmt.setBoolean(22, gub.isSubmenuorcamentodecompras());
            stmt.setBoolean(23, gub.isSubmenupedidodecompras());
            stmt.setBoolean(24, gub.isSubmenuinsumos());
            stmt.setBoolean(25, gub.isSubmenutipodeproduto());
            stmt.setBoolean(26, gub.isMenulogistica());
            stmt.setBoolean(27, gub.isSubmenucarros());
            stmt.setBoolean(28, gub.isSubmenurastreamentodedocumentos());
            stmt.setBoolean(29, gub.isMenuqualidade());
            stmt.setBoolean(30, gub.isSubmenuinstrumentosdemedicao());
            stmt.setBoolean(31, gub.isSubmenuiqf());
            stmt.setBoolean(32, gub.isSubmenumedicoes());
            stmt.setBoolean(33, gub.isMenuvendas());
            stmt.setBoolean(34, gub.isSubmenuorcamentosvenda());
            stmt.setBoolean(35, gub.isSubmenupedidosvenda());
            stmt.setBoolean(36, gub.isSubmenuops());
            stmt.setBoolean(37, gub.isSubmenuprodutosvenda());
            stmt.setBoolean(38, gub.isSubmenuprocessosvenda());
            stmt.setBoolean(39, gub.isSubmenugrupodeprocessosvenda());
            stmt.setBoolean(40, gub.isMenuservicos());
            stmt.setBoolean(41, gub.isSubmenuorcamentosservico());
            stmt.setBoolean(42, gub.isSubmenupedidosservico());
            stmt.setBoolean(43, gub.isSubmenuoss());
            stmt.setBoolean(44, gub.isSubmenuprodutosservico());
            stmt.setBoolean(45, gub.isSubmenuprocessosservico());
            stmt.setBoolean(46, gub.isSubmenugrupodeprocessosservico());
            stmt.setBoolean(47, gub.isMenuconfiguracoes());
            stmt.setBoolean(48, gub.isSubmenumenus());
            stmt.setInt(49, gub.getId());

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
