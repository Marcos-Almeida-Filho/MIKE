/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.CondicaoDePagamentoBean;
import Connection.ConnectionFactory;
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
public class CondicaoDePagamentoDAO {

    public void create(CondicaoDePagamentoBean cpb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO condicaodepagamento (nome, parcelas) VALUES (?,?)");
            stmt.setString(1, cpb.getNome());
            stmt.setInt(2, cpb.getParcelas());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<CondicaoDePagamentoBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CondicaoDePagamentoBean> listcpb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM condicaodepagamento");
            rs = stmt.executeQuery();

            while (rs.next()) {
                CondicaoDePagamentoBean cpb = new CondicaoDePagamentoBean();

                cpb.setId(rs.getInt("id"));
                cpb.setNome(rs.getString("nome"));
                cpb.setParcelas(rs.getInt("parcelas"));

                listcpb.add(cpb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcpb;
    }

    public List<CondicaoDePagamentoBean> clicktable(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CondicaoDePagamentoBean> listcpb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM condicaodepagamento WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CondicaoDePagamentoBean cpb = new CondicaoDePagamentoBean();

                cpb.setId(rs.getInt("id"));
                cpb.setNome(rs.getString("nome"));
                cpb.setParcelas(rs.getInt("parcelas"));

                listcpb.add(cpb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcpb;
    }

    public List<CondicaoDePagamentoBean> readid(String nome) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CondicaoDePagamentoBean> listcpb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM condicaodepagamento WHERE nome = ?");
            stmt.setString(1, nome);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CondicaoDePagamentoBean cpb = new CondicaoDePagamentoBean();

                cpb.setId(rs.getInt("id"));

                listcpb.add(cpb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcpb;
    }
    
    public List<CondicaoDePagamentoBean> pesquisa(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CondicaoDePagamentoBean> listub = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM condicaodepagamento WHERE id LIKE '%" + pesquisa + "%' OR nome LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                CondicaoDePagamentoBean ub = new CondicaoDePagamentoBean();

                ub.setId(rs.getInt("id"));
                ub.setNome(rs.getString("nome"));

                listub.add(ub);
            }
        } catch (SQLException e) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listub;
    }

    public void update(CondicaoDePagamentoBean cpb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE condicaodepagamento SET nome = ?, parcelas = ? WHERE id = ?");
            stmt.setString(1, cpb.getNome());
            stmt.setInt(2, cpb.getParcelas());
            stmt.setInt(3, cpb.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

}
