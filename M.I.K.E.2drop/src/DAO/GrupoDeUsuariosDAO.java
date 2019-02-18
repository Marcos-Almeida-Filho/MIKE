/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.GrupoDeUsuariosBean;
import Connection.ConnectionFactory;
import View.TelaPrincipal;
import View.administracao.GrupoDeUsuarios;
import java.awt.HeadlessException;
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
            stmt = con.prepareStatement("INSERT INTO grupo_usuarios (nome, menuadministracao, submenuusuarios, submenugrupodeusuarios, submenurepresentantes, menucomercial, submenuclientes, submenugrupodeclientes, submenucondicoesdepagamento, menuvendas, submenuorcamentosvenda, submenupedidosvenda, submenuops, submenuprodutosvenda, menuservicos, submenuorcamentosservico, submenupedidosservico, submenuoss, submenuprodutosservico, menuconfiguracoes, submenumenus, menucompras, submenusolicitacaodecompras, submenupedidodecompras, submenuinsumos) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, gub.getNome());
            stmt.setString(2, gub.getMenuadministracao());
            stmt.setString(3, gub.getSubmenuusuarios());
            stmt.setString(4, gub.getSubmenugrupodeusuarios());
            stmt.setString(5, gub.getSubmenurepresentantes());
            stmt.setString(6, gub.getMenucomercial());
            stmt.setString(7, gub.getSubmenuclientes());
            stmt.setString(8, gub.getSubmenugrupodeclientes());
            stmt.setString(9, gub.getSubmenucondicoesdepagamento());
            stmt.setString(10, gub.getMenuvendas());
            stmt.setString(11, gub.getSubmenuorcamentosvenda());
            stmt.setString(12, gub.getSubmenupedidosvenda());
            stmt.setString(13, gub.getSubmenuops());
            stmt.setString(14, gub.getSubmenuprodutosvenda());
            stmt.setString(15, gub.getMenuservicos());
            stmt.setString(16, gub.getSubmenuorcamentosservico());
            stmt.setString(17, gub.getSubmenupedidosservico());
            stmt.setString(18, gub.getSubmenuoss());
            stmt.setString(19, gub.getSubmenuprodutosservico());
            stmt.setString(20, gub.getMenuconfiguracoes());
            stmt.setString(21, gub.getSubmenumenus());
            stmt.setString(22, gub.getMenucompras());
            stmt.setString(23, gub.getSubmenusolicitacaodecompras());
            stmt.setString(24, gub.getSubmenupedidodecompras());
            stmt.setString(25, gub.getSubmenuinsumos());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
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
                gub.setMenucomercial(rs.getString("menucomercial"));
                gub.setSubmenuclientes(rs.getString("submenuclientes"));
                gub.setSubmenugrupodeclientes(rs.getString("submenugrupodeclientes"));
                gub.setSubmenucondicoesdepagamento(rs.getString("submenucondicoesdepagamento"));
                gub.setMenuvendas(rs.getString("menuvendas"));
                gub.setSubmenuorcamentosvenda(rs.getString("submenuorcamentosvenda"));
                gub.setSubmenupedidosvenda(rs.getString("submenupedidosvenda"));
                gub.setSubmenuops(rs.getString("submenuops"));
                gub.setSubmenuprodutosvenda(rs.getString("submenuprodutosvenda"));
                gub.setMenuservicos(rs.getString("menuservicos"));
                gub.setSubmenuorcamentosservico(rs.getString("submenuorcamentosservico"));
                gub.setSubmenupedidosservico(rs.getString("submenupedidosservico"));
                gub.setSubmenuoss(rs.getString("submenuoss"));
                gub.setSubmenuprodutosservico(rs.getString("submenuprodutosservico"));
                gub.setMenuconfiguracoes(rs.getString("menuconfiguracoes"));
                gub.setSubmenumenus(rs.getString("submenumenus"));
                gub.setMenucompras(rs.getString("menucompras"));
                gub.setSubmenusolicitacaodecompras(rs.getString("submenusolicitacaodecompras"));
                gub.setSubmenupedidodecompras(rs.getString("submenupedidodecompras"));
                gub.setSubmenuinsumos(rs.getString("submenuinsumos"));

                listgub.add(gub);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
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
                gub.setMenucomercial(rs.getString("menucomercial"));
                gub.setSubmenuclientes(rs.getString("submenuclientes"));
                gub.setSubmenugrupodeclientes(rs.getString("submenugrupodeclientes"));
                gub.setSubmenucondicoesdepagamento(rs.getString("submenucondicoesdepagamento"));
                gub.setMenuvendas(rs.getString("menuvendas"));
                gub.setSubmenuorcamentosvenda(rs.getString("submenuorcamentosvenda"));
                gub.setSubmenupedidosvenda(rs.getString("submenupedidosvenda"));
                gub.setSubmenuops(rs.getString("submenuops"));
                gub.setSubmenuprodutosvenda(rs.getString("submenuprodutosvenda"));
                gub.setMenuservicos(rs.getString("menuservicos"));
                gub.setSubmenuorcamentosservico(rs.getString("submenuorcamentosservico"));
                gub.setSubmenupedidosservico(rs.getString("submenupedidosservico"));
                gub.setSubmenuoss(rs.getString("submenuoss"));
                gub.setSubmenuprodutosservico(rs.getString("submenuprodutosservico"));
                gub.setMenuconfiguracoes(rs.getString("menuconfiguracoes"));
                gub.setSubmenumenus(rs.getString("submenumenus"));
                gub.setMenucompras(rs.getString("menucompras"));
                gub.setSubmenusolicitacaodecompras(rs.getString("submenusolicitacaodecompras"));
                gub.setSubmenupedidodecompras(rs.getString("submenupedidodecompras"));
                gub.setSubmenuinsumos(rs.getString("submenuinsumos"));

                listgub.add(gub);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listgub;
    }

    public void update(GrupoDeUsuariosBean gub) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE grupo_usuarios SET nome = ?, menuadministracao = ?, submenuusuarios = ?, submenugrupodeusuarios = ?, submenurepresentantes = ?, menucomercial = ?, submenuclientes = ?, submenugrupodeclientes = ?, submenucondicoesdepagamento = ?, menuvendas = ?, submenuorcamentosvenda = ?, submenupedidosvenda = ?, submenuops = ?, submenuprodutosvenda = ?, menuservicos = ?, submenuorcamentosservico = ?, submenupedidosservico = ?, submenuoss = ?, submenuprodutosservico = ?, menuconfiguracoes = ?, submenumenus = ?, menucompras = ?, submenusolicitacaodecompras = ?, submenupedidodecompras = ?, submenuinsumos = ? WHERE id = ?");
            stmt.setString(1, gub.getNome());
            stmt.setString(2, gub.getMenuadministracao());
            stmt.setString(3, gub.getSubmenuusuarios());
            stmt.setString(4, gub.getSubmenugrupodeusuarios());
            stmt.setString(5, gub.getSubmenurepresentantes());
            stmt.setString(6, gub.getMenucomercial());
            stmt.setString(7, gub.getSubmenuclientes());
            stmt.setString(8, gub.getSubmenugrupodeclientes());
            stmt.setString(9, gub.getSubmenucondicoesdepagamento());
            stmt.setString(10, gub.getMenuvendas());
            stmt.setString(11, gub.getSubmenuorcamentosvenda());
            stmt.setString(12, gub.getSubmenupedidosvenda());
            stmt.setString(13, gub.getSubmenuops());
            stmt.setString(14, gub.getSubmenuprodutosvenda());
            stmt.setString(15, gub.getMenuservicos());
            stmt.setString(16, gub.getSubmenuorcamentosservico());
            stmt.setString(17, gub.getSubmenupedidosservico());
            stmt.setString(18, gub.getSubmenuoss());
            stmt.setString(19, gub.getSubmenuprodutosservico());
            stmt.setString(20, gub.getMenuconfiguracoes());
            stmt.setString(21, gub.getSubmenumenus());
            stmt.setString(22, gub.getMenucompras());
            stmt.setString(23, gub.getSubmenusolicitacaodecompras());
            stmt.setString(24, gub.getSubmenupedidodecompras());
            stmt.setString(25, gub.getSubmenuinsumos());
            stmt.setInt(26, gub.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
